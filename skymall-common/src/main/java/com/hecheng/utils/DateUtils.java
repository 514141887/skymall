package com.hecheng.utils;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类
 * @author Administrator
 *
 */
@Slf4j
public class DateUtils {

	/** 缺省日期格式 */
	public static final String DEFAULT_DATE_FORMAT_DEFAULT = "yyyy-MM-dd";

	/** 紧凑日期格式 */
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";

	/** 缺省长日期格式 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH-mm";

	/** 紧凑长日期格式 */
	public static final String COMPACT_DATETIME_FORMAT = "yyyyMMddHHmm";

	/** 长日期格式,精确到秒 */
	public static final String DEFAULT_DATETIME_FORMAT_SEC = "yyyy-MM-dd HH:mm:ss";

	/** 紧凑长日期格式,精确到秒 */
	public static final String COMPACT_DATETIME_FORMAT_SEC = "yyyyMMddHHmmss";

	/** 紧凑长日期格式,精确到秒 优化长度 */
	public static final String COMPACT_DATETIME_FORMAT_YEAR = "yyyyMMddHHmmss";

	private static Map<String, SimpleDateFormat> formats = new HashMap<String, SimpleDateFormat>();

	static {
		formats.put(DEFAULT_DATE_FORMAT_DEFAULT, new SimpleDateFormat(DEFAULT_DATE_FORMAT_DEFAULT));
		formats.put(COMPACT_DATE_FORMAT, new SimpleDateFormat(COMPACT_DATE_FORMAT));
		formats.put(DEFAULT_DATETIME_FORMAT, new SimpleDateFormat(DEFAULT_DATETIME_FORMAT));
		formats.put(COMPACT_DATETIME_FORMAT, new SimpleDateFormat(COMPACT_DATETIME_FORMAT));
		formats.put(DEFAULT_DATETIME_FORMAT_SEC, new SimpleDateFormat(DEFAULT_DATETIME_FORMAT_SEC));
		formats.put(COMPACT_DATETIME_FORMAT_SEC, new SimpleDateFormat(COMPACT_DATETIME_FORMAT_SEC));
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * <p>
	 * 将日期格式化为字符串
	 * </p>
	 * 
	 * @param date
	 * @param formate
	 * @return
	 * @author Jonathan
	 */
	public static String formate(Date date, String formate) {

		if (date == null)
			return null;
		SimpleDateFormat simpleDateFormat = formats.get(formate);
		return simpleDateFormat.format(date);
	}

	/**
	 * <p>
	 * 将日期格式化为字符串
	 * </p>
	 * 
	 * @param date
	 * @param formate
	 * @return
	 * @author Jonathan
	 */
	public static String formate(String date, String formate) {
		SimpleDateFormat simpleDateFormat = formats.get(formate);
		return simpleDateFormat.format(date);
	}

	/**
	 * <p>
	 * 将当前日期格式化为字符串
	 * </p>
	 * 
	 * @param formate
	 * @return
	 * @author Jonathan
	 */
	public static String formateCurrent(String formate) {

		SimpleDateFormat simpleDateFormat = formats.get(formate);
		return simpleDateFormat.format(new Date());
	}

	/**
	 * <p>
	 * 解析日期字段
	 * </p>
	 * 
	 * @param dateStr
	 * @param formate
	 * @return
	 * @author Jonathan
	 */
	public static Date parse(String dateStr, String formate) {
		SimpleDateFormat simpleDateFormat = formats.get(formate);
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			log.error("用格式{}解析{}时出错", formate, dateStr);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * 解析日期字段
	 * </p>
	 * 
	 * @param dateStr
	 * @param formate
	 * @return
	 * @author Jonathan
	 */
	public static Date parse(String dateStr) {
		SimpleDateFormat simpleDateFormat = formats.get(DEFAULT_DATE_FORMAT_DEFAULT);
		try {
			return simpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			log.error("用格式{}解析{}时出错", DEFAULT_DATE_FORMAT_DEFAULT, dateStr);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * 计算两个时间差
	 * </p>
	 * 
	 * @param startDate
	 * @param endDate
	 * @param timeUnit
	 *            （时间单位）TimeUnit
	 * @return int
	 * @author mjy
	 */
	public static int timeDiff(Date startDate, Date endDate, TimeUnit timeUnit) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(endDate);
		c2.setTime(startDate);
		long time1 = c1.getTimeInMillis();
		long time2 = c2.getTimeInMillis();
		Long timeDiff = null;
		if (timeUnit != null) {
			switch (timeUnit) {
			case DAYS: {
				timeDiff = (time1 - time2) / (1000 * 3600 * 24);
				break;
			}
			case HOURS: {
				timeDiff = (time1 - time2) / (1000 * 3600);
				break;
			}
			case MINUTES: {
				timeDiff = (time1 - time2) / (1000 * 60);
				break;
			}
			case SECONDS: {
				timeDiff = (time1 - time2) / 1000;
				break;
			}
			default: {
				// 默认返回月
				timeDiff = (time1 - time2) / (1000 * 3600 * 24) / 30;
			}
			}
		} else {
			timeDiff = (time1 - time2) / (1000 * 3600 * 24) / 30;
		}
		return timeDiff.intValue();

	}

	/**
	 * 
	 * <p>
	 * 计算从现在到endTime之间的天数
	 * </p>
	 * 
	 * @param endTime
	 * @return
	 * @throws Exception
	 * @author wangzh
	 */
	public static int diffDays(Date endTime) throws Exception {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String nowStr = sdf.format(now);
		now = sdf.parse(nowStr);
		String endStr = sdf.format(endTime);
		endTime = sdf.parse(endStr);
		long ret = ((endTime.getTime() - now.getTime()) / (1000 * 3600 * 24));
		return (int) ret;
	}

	/**
	 * 
	 * <p>
	 * 根据生日获取年龄:限定格式为yyyy-MM-dd
	 * </p>
	 * 
	 * @param birthday
	 * @return
	 * @throws Exception
	 * @author lidongfu
	 * @throws ParseException
	 */
	public static String getAgeByString(String birthday) throws ParseException {
		if (birthday == null || birthday.trim().length() == 0)
			return null;
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthday is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(formats.get(DEFAULT_DATE_FORMAT_DEFAULT).parse(birthday));
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age + "";
	}

	/**
	 * 
	 * <p>
	 * 根据指定格式将指定String类型的生日转为年龄
	 * </p>
	 * 
	 * @param birthday
	 * @param format
	 * @return
	 * @throws Exception
	 * @author mjy
	 */
	public static String getAgeByStringFormat(String birthday, String format) {
		if (birthday == null || birthday.trim().length() == 0)
			return null;
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			// throw new
			return "0";
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		try {
			cal.setTime(formats.get(format).parse(birthday));
		} catch (ParseException e) {
			log.error("时间解析错误{}", e.getMessage());
		}
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age + "";
	}

	/**
	 * 
	 * <p>
	 * 根据生日获取年龄
	 * </p>
	 * 
	 * @param birthday
	 * @return
	 * @throws Exception
	 * @author lidongfu
	 */
	public static String getAgeByDate(Date birthday) {
		if (birthday == null)
			return null;
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthday is before Now.It's unbelievable!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH) + 1;
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthday);
		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
		int age = yearNow - yearBirth;
		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				// monthNow==monthBirth
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				// monthNow>monthBirth
				age--;
			}
		}
		return age + "";
	}

	/**
	 * <p>
	 * 获取当前时间
	 * </p>
	 * 
	 * @return
	 * @author Jonathan
	 */
	static public Date getCurrentTime() {
		return Calendar.getInstance().getTime();
	}

	/**
	 * 获取下几个月的 下几号
	 * 
	 * @param addMonth
	 *            增加 几个月
	 * @param addDay
	 *            每个月 增加几天
	 * @return
	 */
	public static Date nextSomeMothDate(int addMonth, int addDay) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, addDay);
		calendar.add(Calendar.MONTH, addMonth);
		return calendar.getTime();
	}

	/**
	 * 
	* @Title: calTimeDiff
	* @Description: 计算两个时间之间相差的天时分秒
	* @param startTime
	* @param endTime
	* @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String calTimeDiff(Date startTime, Date endTime) {
		SimpleDateFormat dfs = new SimpleDateFormat(DEFAULT_DATETIME_FORMAT_SEC);
		long between = 0;
		try {
			formate(startTime, DEFAULT_DATETIME_FORMAT_SEC);
			Date begin = dfs.parse(formate(startTime, DEFAULT_DATETIME_FORMAT_SEC));
			Date end = dfs.parse(formate(endTime, DEFAULT_DATETIME_FORMAT_SEC));
			between = (end.getTime() - begin.getTime());// 得到两者的毫秒数
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		long day = between / (24 * 60 * 60 * 1000);
		long hour = (between / (60 * 60 * 1000) - day * 24);
		long min = ((between / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (between / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		long ms = (between - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
		String timeDiff = day + "天" + hour + "小时" + min + "分" + s + "秒" + ms + "毫秒";
		System.out.println(day + "天" + hour + "小时" + min + "分" + s + "秒" + ms + "毫秒");
		return timeDiff;
	}
     
	/**
	 * 
	* @Title: getNextDay
	* @Description: 将当期时间向前或者向后推进到当天的23:59:59
	* @param d
	* @param delay
	* @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String getNextDay(Date d, String delay) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			String mdate = "";
			long myTime = (d.getTime() / 1000) + Integer.parseInt(delay) * 24 * 60 * 60;
			d.setTime(myTime * 1000);
			mdate = format.format(d);
			return mdate;
		} catch (Exception e) {
			return "";
		}
	}
    
	/**
	 * 
	* @Title: getTimeDiff
	* @Description: 获取当期时间与当前后一天的23:59:59之间的时间间隔
	* @param currentTime
	* @return    设定文件
	* @return String    返回类型
	* @throws
	 */
	public static String getTimeDiff(Date currentTime){
		if(isToday(currentTime)){//如果是当前天
			return formate(currentTime,DEFAULT_DATETIME_FORMAT_SEC);
		}
		return calTimeDiff(currentTime, parse(getNextDay(new Date(),"1"),DEFAULT_DATETIME_FORMAT_SEC)) ;
	}
	
	public static boolean isToday(Date date){
        SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
        if(fmt.format(date).toString().equals(fmt.format(new Date()).toString())){//格式化为相同格式
          return true;
        }else {
          return false;
        }
	}
	
	public static void main(String[] args) throws ParseException {
		System.out.println(getNextDay(new Date(),"1"));
		System.out.println(calTimeDiff(parse("2017-03-02 14:00:00", DEFAULT_DATETIME_FORMAT_SEC), parse(getNextDay(new Date(),"1"),DEFAULT_DATETIME_FORMAT_SEC)));
		// System.err.println(timeDiff(InterestUtils.SF.parse("2016/08/13
		// 23:59:59"), InterestUtils.SF.parse("2017/01/08 01:00:00"),
		// TimeUnit.DAYS));
		// System.err.println(InterestUtils.SF.format(new Date()));
	}

}
