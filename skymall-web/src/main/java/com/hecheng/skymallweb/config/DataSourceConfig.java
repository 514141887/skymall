package com.hecheng.skymallweb.config;

import com.hecheng.skymallweb.utils.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


@Configuration
public class DataSourceConfig {

    private static final String DATASOURCE_WRITE = "write";

    private static final String DATASOURCE_READ1 = "read1";

    private static final String DATASOURCE_READ2 = "read2";

    private int readSize = 2;

    @Value("${mybatis.type-aliases-package}")
    private String entity;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        try {
            SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
            //     sessionFactoryBean.setDataSource(roundRobinDataSouce);
            //第一步 数据源配置
            sessionFactoryBean.setDataSource(roundRobinDataSouceProxy());

            //第二步 别名配置 读取配置
            sessionFactoryBean.setTypeAliasesPackage(entity);

            //第三步 设置mapper.xml文件所在位置
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/*Mapper.xml");
            sessionFactoryBean.setMapperLocations(resources);

            return sessionFactoryBean.getObject();
        } catch (IOException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Bean
    @Primary
    public DataSource dynamicDataSource() {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        //
        return dynamicDataSource;
    }

    @Value("${datasource.write.password}")
    private String write;


    /**
     * 多数据源配置1. 写库1
     */
    @Bean(DATASOURCE_WRITE)
    @ConfigurationProperties(prefix = "datasource.write")
    public DataSource writeDataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 多数据源配置2. 读库1
     */
    @Bean(DATASOURCE_READ1)
    @ConfigurationProperties(prefix = "datasource.read1")
    public DataSource read1DataSource() {
        return DataSourceBuilder.create().build();
    }

    /**
     * 多数据源配置3. 读库2
     */
    @Bean(DATASOURCE_READ2)
    @ConfigurationProperties(prefix = "datasource.read2")
    public DataSource read2DataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public WRInterceptor mapperInterceptor() {
        return new WRInterceptor();
    }



    /**
    *  动态数据源配置
    */
    public static class DynamicDataSource extends AbstractRoutingDataSource implements BeanFactoryAware {

        private static ThreadLocal<String> currentLookupKeyLocal = new ThreadLocal<String>();
        private BeanFactory beanFactory;

        public static String getCurrentLookupKey() {
            return currentLookupKeyLocal.get();
        }

        public static void setCurrentLookupKey(String key) {
            currentLookupKeyLocal.set(key);
        }

        @Override
        protected Object determineCurrentLookupKey() {
            return currentLookupKeyLocal.get();
        }

        @Override
        public void afterPropertiesSet() {
            //
            Map<String, DataSource> targetDataSources = BeanFactoryUtils.beansOfTypeIncludingAncestors((ListableBeanFactory) beanFactory, DataSource.class);
            super.setTargetDataSources(new HashMap<Object, Object>(targetDataSources));
            //
            super.afterPropertiesSet();
        }

        @Override
        public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
            this.beanFactory = beanFactory;
        }
    }


    /**
    *  拦截所有方法,根据方法名称,动态设置数据源
    */
    @Aspect
    private static class WRInterceptor {

        @Around("execution(* com.hecheng.skymallweb.service..*.*(..))")
        public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
            // 获取到当前执行的方法名
            String methodName = joinPoint.getSignature().getName();
            if (isSlave(methodName)) {
                // 标记为读库,可以自定义选择数据源
                Random random = new Random();
                int randomNum = random.nextInt(3);
                if (randomNum == 0) {
                    DynamicDataSource.setCurrentLookupKey(DATASOURCE_READ1);
                } else if (randomNum == 1) {
                    DynamicDataSource.setCurrentLookupKey(DATASOURCE_READ2);
                } else if (randomNum == 2) {
                    DynamicDataSource.setCurrentLookupKey(DATASOURCE_WRITE);
                }
            } else {
                // 标记为写库
                DynamicDataSource.setCurrentLookupKey(DATASOURCE_WRITE);
            }
            return joinPoint.proceed();
        }

        /**
         * 判断是否为读库
         *
         * @param methodName
         * @return
         */
        private Boolean isSlave(String methodName) {
            // 方法名以query、find、get开头的方法名走从库
            return StringUtils.startsWithAny(methodName, new String[]{"query", "find", "get","count","select"});
        }

    }

    @Bean(name = "roundRobinDataSouceProxy")
    public AbstractRoutingDataSource roundRobinDataSouceProxy() {

        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        //把所有数据库都放在targetDataSources中,注意key值要和determineCurrentLookupKey()中代码写的一致，
        //否则切换数据源时找不到正确的数据源
        targetDataSources.put(DATASOURCE_WRITE, writeDataSource());
        targetDataSources.put(DATASOURCE_READ1, read1DataSource());
        targetDataSources.put(DATASOURCE_READ2, read2DataSource());

        //     MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(readSize);

        //路由类，寻找对应的数据源
        AbstractRoutingDataSource proxy = new AbstractRoutingDataSource() {
            private AtomicInteger count = new AtomicInteger(0);

            /**
             * 这是AbstractRoutingDataSource类中的一个抽象方法，
             * 而它的返回值是你所要用的数据源dataSource的key值，有了这个key值，
             * targetDataSources就从中取出对应的DataSource，如果找不到，就用配置默认的数据源。
             */
            @Override
            protected Object determineCurrentLookupKey() {
                String typeKey = DynamicDataSource.getCurrentLookupKey();

                if (null == typeKey) {
                    //	System.err.println("使用数据库write.............");
                    //    return DataSourceType.write.getType();
                    return DATASOURCE_WRITE;
                } else if (DATASOURCE_WRITE.equals(typeKey)) {
                    System.err.println("使用数据库write.............");

                    return DATASOURCE_WRITE;
                }

                //读库， 简单负载均衡
                int number = count.getAndAdd(1);
                int lookupKey = number % readSize;
                System.err.println("使用数据库read-" + (lookupKey + 1));
                return DATASOURCE_READ1 + (lookupKey + 1);
            }
        };

        proxy.setDefaultTargetDataSource(writeDataSource());//默认库
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //事务管理
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager((DataSource) SpringContextUtil.getBean("roundRobinDataSouceProxy"));
    }


}
