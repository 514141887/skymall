package com.hecheng.utils;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 
* @ClassName: CachFactory
* @Description: 静态缓存
*
 */
public class CachFactory {


	private Map<String, Object> factoryMap = new ConcurrentHashMap<>();
    
	
	private CachFactory() {
		 
	}
    
	public static CachFactory getInstance() {  
        return CachInnerClass.instance;       
    }  
     
    /**
     * 
    * @ClassName: CachInnerClass
    * @Description: 使用静态内部类 只在第一次被引用时加载
    *
     */
    static class CachInnerClass {  
        private static CachFactory  instance = new CachFactory();  
    }  
    
    /**
     * <P> 将对象数据放入缓存</P>
     *
     * @param key
     * @param value
     */
	public void put(String key,Object value) {
		factoryMap.put(key, value);
	}

	 /**
	  * <P>  判断是否存在key为cachName的map</P>
	  *
	  * @param cachName
	  * @return
	  */
	public boolean isExist(String key) {
		if (factoryMap.containsKey(key)) {
			return true;
		}
		return false;
	}
    
	 /**
	  * <P> 获取key的对象 </P>
	  *
	  * @param key
	  * @return
	  */
	public Object get(String key) {
		if (factoryMap.containsKey(key)) {
			return factoryMap.get(key);
		}
		return null;
	}
	
	
	
	
}
