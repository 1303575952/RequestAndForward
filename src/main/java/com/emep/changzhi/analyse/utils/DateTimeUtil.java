package com.emep.changzhi.analyse.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.util.StringUtils;

import javax.sound.midi.SysexMessage;

/**
 * <pre>
 * Copy right information:
 * Project:emep-environment-common-config
 * File Name:DateTimeUtil.java
 * JDK version used:1.7.0_45
 * Commends:日期时间工具类
 *
 *
 * Modification history	:
 * date			ver.	author			what is modified
 * ---------	----	---------	---------------------------
 * 2017年8月31日		1.0		xiey		        new
 * </pre>
 * 
 * @author wz
 * @version 4.0
 */
public class DateTimeUtil {
	
	// 默认日期格式
	public static final String DATE_DEFAULT_FORMAT = "yyyy-MM-dd"; 
	// 默认时间格式
	public static final String DATETIME_DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";
	// 默认日期时间格式
	public static final String TIME_DEFAULT_FORMAT = "HH:mm:ss";
	
	//历法  JDK7.0及以上
	private static Calendar gregorianCalendar = null;
	
	static{
		gregorianCalendar = new GregorianCalendar();
	}


	/**
	 * @author wz
	 * @Description: 返回上一年结束时间 返回Date
	 * @date 2018/4/2 10:17
	 * @version V1.0
	 */
	public static Date stampToStringEndYear() throws ParseException{
		int now_date = getNowYear(new Date());
		now_date = now_date-1;
		String end_date = now_date+"-12-31 23:59:59";
		Date date =  stringToDate(end_date,DATETIME_DEFAULT_FORMAT);
		return 	date;
	}

	/**
	 * @author wz
	 * @Description: 返回上一年开始时间 返回Date
	 * @date 2018/4/2 10:17
	 * @version V1.0
	 */
	public static Date stampToStringSaveYear() throws ParseException{
		int new_date = getNowYear(new Date());
		new_date = new_date-1;
		String end_date = new_date+"-01-01 00:00:00";
		Date date =  stringToDate(end_date,DATETIME_DEFAULT_FORMAT);
		return 	date;
	}

	/**
	 * @author wz
	 * @Description: 返回上一月结束时间 返回Date
	 * @date 2018/4/2 10:17
	 * @version V1.0
	 */
	public static Date stampToStringEnd() throws ParseException{
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_DEFAULT_FORMAT);
		int lastMonthMaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
		String gtime = sdf.format(c.getTime());
		Date date= stringToDate(gtime,DATETIME_DEFAULT_FORMAT);
		return date;
	}
	/**
	 * @author wz
	 * @Description: 返回上一月开始时间 返回Date
	 * @date 2018/4/2 10:17
	 * @version V1.0
	 */
	public static Date stampToStringSave() throws ParseException{
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, -1);
		int lastMonthMaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-01  00:00:00");
		String gtime2 = sdf2.format(c.getTime());
		Date date= stringToDate(gtime2,DATETIME_DEFAULT_FORMAT);
		return date;
	}

	/**
	 * 时间戳转Date时间
	 * @param time 时间戳字符串
	 * @return	时间Date
	 * @throws ParseException
	 */

	public static Date stampToDate(String time){
		long lt = new Long(time);
		Date date = new Date(lt);
		return date;
	}

	/**
	 * 时间戳转字符串时间
	 * @param time 时间戳字符串
	 * @param dateTimeFormat 需要返回的格式
	 * @return	时间字符串
	 * @throws ParseException
	 */
	public static String stampToString(String time,String dateTimeFormat){
		String res;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateTimeFormat);
		long lt = new Long(time);
		Date date = new Date(lt);
		res = simpleDateFormat.format(date);
		return res;
	}
	/**
	 * 获取天数0点整的时间戳
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getStartDate(String date)throws ParseException{
		String str  = date+" 00:00:00";
		Date date1 = stringToDate(str,DATETIME_DEFAULT_FORMAT);
		return String.valueOf(date1.getTime());
	}
	/**
	 * 获取天数23:59:59秒点整的时间戳
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getEndDate(String date)throws ParseException{
		String str  = date+" 23:59:59";
		Date date1 = stringToDate(str,DATETIME_DEFAULT_FORMAT);
		return String.valueOf(date1.getTime());
	}




	/**
	 * @Title: getDateTimeForDate 
	 * @Description:得到时间（返回时间类型）
	 * make by wz on 2017年8月31日 下午5:49:57
	 * @param date 要转化的日期
	 * @param dateTimeFormat 日期格式 
	 * @return 按日期形式，返回Date
	 * @throws ParseException 转化异常
	 */
	public static Date getDateTimeForDate(Date date,String dateTimeFormat) throws ParseException{
		if(null==date) {return null;}
		if(StringUtils.isEmpty(dateTimeFormat)) {dateTimeFormat = DATETIME_DEFAULT_FORMAT;}
		return DateTimeUtil.stringToDate(DateTimeUtil.getDateTimeForString(date,dateTimeFormat),dateTimeFormat);
	}
	
	/**
	 * @Title: getNowDateTimeForDate 
	 * @Description:得到现在时间（返回时间类型）
	 * make by wz on 2017年8月31日 下午4:59:07
	 * @param dateTimeFormat  时间格式   默认yyyy-MM-dd HH:mm:ss
	 * @return 按传入格式，返回时间
	 * @throws ParseException 转化异常
	 */
	public static Date getNowDateTimeForDate(String dateTimeFormat) throws ParseException{
		return DateTimeUtil.getDateTimeForDate(new Date(),dateTimeFormat);
	}
	
	/**
	 * @Title: getDateTimeForString 
	 * @Description:得到时间（返回字符串类型）
	 * make by wz on 2017年8月31日 下午5:21:47
	 * @param date 时间  默认 系统时间
	 * @param dateTimeFormat 时间格式   默认yyyy-MM-dd HH:mm:ss
	 * @return 按传入格式，返回时间字符串
	 */
	public static String getDateTimeForString(Date date,String dateTimeFormat){
		if(StringUtils.isEmpty(dateTimeFormat)) {dateTimeFormat = DATETIME_DEFAULT_FORMAT;}
		if(null==date) {date = new Date();}
		SimpleDateFormat formatter = new SimpleDateFormat(dateTimeFormat);
		return formatter.format(date); 
	}
	
	/**
	 * @Title: getNowDateTimeForString 
	 * @Description:得到现在时间（返回字符串类型）
	 * make by wz on 2017年8月31日 下午5:06:31
	 * @param dateTimeFormat 时间格式   默认yyyy-MM-dd HH:mm:ss
	 * @return 按传入格式，返回现在时间字符串
	 */
	public static String getNowDateTimeForString(String dateTimeFormat){
		return  DateTimeUtil.getDateTimeForString(new Date(),dateTimeFormat);
	}
	
	/**
	 * @Title: getDateTimeForLong 
	 * @Description:得到时间（返回Long类型）
	 * make by wz on 2017年9月1日 上午8:38:49
	 * @param date 时间  默认 系统时间
	 * @param dateTimeFormat 时间格式   默认yyyy-MM-dd HH:mm:ss
	 * @return 返回传入格式的long类型
	 * @throws ParseException 转化异常
	 */
	public static Long getDateTimeForLong(Date date,String dateTimeFormat) throws ParseException{
		return  DateTimeUtil.DateToLong(date,dateTimeFormat);
	}
	
	/**
	 * @Title: getNowDateTimeForLong 
	 * @Description:得到现在时间（返回Long类型）
	 * make by wz on 2017年8月31日 下午6:14:16
	 * @param dateTimeFormat 时间格式   默认yyyy-MM-dd HH:mm:ss
	 * @return 返回传入格式的long类型
	 * @throws ParseException 转化异常
	 */
	public static Long getNowDateTimeForLong(String dateTimeFormat) throws ParseException{
		return  DateTimeUtil.DateToLong(new Date(),dateTimeFormat);
	}
	
	/**
	 * @Title: string4Date 
	 * @Description: String转化为Date
	 * make by wz on 2017年8月31日 下午5:32:30
	 * @param dateStr 时间字符串
	 * @param dateTimeFormat 时间格式字符串
	 * @return 返回date类型的数据
	 * @throws ParseException 转化异常
	 */
	public static Date stringToDate(String dateStr, String dateTimeFormat) throws ParseException{
		if(StringUtils.isEmpty(dateStr) || StringUtils.isEmpty(dateTimeFormat)) {return null;}
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);  
	    return sdf.parse(dateStr); 
	}
	
	/**
	 * @Title: DateToLong 
	 * @Description:date转Long
	 * make by wz on 2017年8月31日 下午6:05:55
	 * @param date 要转化的时间
	 * @param dateTimeFormat 时间格式字符串
	 * @return 返回Long 类型数据
	 * @throws ParseException 转化异常
	 */
	public static Long DateToLong(Date date,String dateTimeFormat) throws ParseException{
		if(null==date) {return null;}
		if(StringUtils.isEmpty(dateTimeFormat)) {dateTimeFormat = DATETIME_DEFAULT_FORMAT;}
		return DateTimeUtil.getDateTimeForDate(date,dateTimeFormat).getTime();
	}
	
	/**
	 * @Title: getDayOfWeek 
	 * @Description:得到给定日为每周第几天
	 * make by wz on 2017年9月1日 上午9:32:47
	 * @param date 时间
	 * @return 返回改时间的每周第几天
	 */
	public static Integer getDayOfWeek(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * @Title: getNowDayOfWeek 
	 * @Description:得到现在为每周第几天
	 * make by wz on 2017年9月1日 上午9:35:31
	 * @return 返回Integer
	 */
	public static Integer getNowDayOfWeek(){
		return DateTimeUtil.getDayOfWeek(new Date());
	}
	
	/**
	 * @Title: getDayOfMonth 
	 * @Description:得到给定日为每月第几天
	 * make by wz on 2017年9月1日 上午9:33:15
	 * @param date 要转换的日期
	 * @return 返回Integer
	 */
	public static Integer getDayOfMonth(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * @Title: getNowDayOfMonth 
	 * @Description:得到当前时间  月第几天
	 * make by wz on 2017年9月1日 上午9:36:19
	 * @return 返回Integer
	 */
	public static Integer getNowDayOfMonth(){
		return DateTimeUtil.getDayOfMonth(new Date());
	}
	
	/**
	 * @Title: getDayOfYear 
	 * @Description:得到给定日为每年第几天
	 * make by wz on 2017年9月1日 上午9:33:32
	 * @param date 要转换的日期
	 * @return 返回Integer
	 */
	public static Integer getDayOfYear(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * @Title: getNowDayOfYear 
	 * @Description:得到现在为每年第几天
	 * make by wz on 2017年9月1日 上午9:37:04
	 * @return 返回Integer
	 */
	public static Integer getNowDayOfYear(){
		return DateTimeUtil.getDayOfYear(new Date());
	}
	
	/**
	 * @Title: getWeekOfMonth 
	 * @Description:得到给定日期   月第几周
	 * make by wz on 2017年9月1日 上午9:40:26
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getWeekOfMonth(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * @Title: getNowWeekOfMonth 
	 * @Description:得到本月第几周
	 * make by wz on 2017年9月1日 上午9:43:13
	 * @return 返回Integer
	 */
	public static Integer getNowWeekOfMonth(){
		return DateTimeUtil.getWeekOfMonth(new Date());
	}
	
	/**
	 * @Title: getWeekOfWeekMonth 
	 * @Description:得到给定日期  月日历第几周 
	 * make by wz on 2017年9月1日 上午10:50:40
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getWeekOfWeekMonth(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	
	/**
	 * @Title: getNowWeekOfWeekMonth 
	 * @Description: 得到本月日历第几周 
	 * make by wz on 2017年9月1日 上午10:50:46
	 * @return 返回Integer
	 */
	public static Integer getNowWeekOfWeekMonth(){
		return DateTimeUtil.getWeekOfWeekMonth(new Date());
	}
	
	/**
	 * @Title: getWeekOfDate 
	 * @Description:得到给定日期   年第几周
	 * make by wz on 2017年9月1日 上午9:23:36
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getWeekOfYear(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * @Title: getNowWeekOfYear 
	 * @Description:得到本年第几周
	 * make by wz on 2017年9月1日 上午9:44:33
	 * @return 返回Integer
	 */
	public static Integer getNowWeekOfYear(){
		return DateTimeUtil.getWeekOfYear(new Date());
	}
	
	/**
	 * @Title: getMonthOfYear 
	 * @Description:得到给定日期月份
	 * make by wz on 2017年9月1日 下午4:19:27
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getMonthOfYear(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.MONTH);
	}
	
	/**
	 * @Title: getNowMonthOfYear 
	 * @Description:得到当前月份
	 * make by wz on 2017年9月1日 下午4:21:28
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getNowMonthOfYear(Date date){
		return getMonthOfYear(new Date());
	}
	
	/**
	 * @Title: getYear 
	 * @Description:得到给定日期年份
	 * make by wz on 2017年9月1日 下午4:19:45
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getYear(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.YEAR);
	}
	
	/**
	 * @Title: getNowYear 
	 * @Description:得到当前年份
	 * make by wz on 2017年9月1日 下午4:22:19
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getNowYear(Date date){
		return getYear(new Date());
	}
	
	/**
	 * @Title: getHour 
	 * @Description:得到给定日期小时
	 * make by wz on 2017年9月1日 下午4:30:56
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getHour(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * @Title: getNowHour 
	 * @Description:得到现在的小时
	 * make by wz on 2017年9月1日 下午4:31:24
	 * @return 返回Integer
	 */
	public static Integer getNowHour(){
		return getHour(new Date());
	}
	
	/**
	 * @Title: getMin 
	 * @Description:得到给定日期的分钟
	 * make by wz on 2017年9月1日 下午4:33:12
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getMin(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.MINUTE);
	}
	
	/**
	 * @Title: getNowMin 
	 * @Description:得到现在分钟
	 * make by wz on 2017年9月1日 下午4:33:33
	 * @return 返回Integer
	 */
	public static Integer getNowMin(){
		return getMin(new Date());
	}
	
	/**
	 * @Title: getSec 
	 * @Description:得到给定分钟秒数
	 * make by wz on 2017年9月1日 下午4:35:19
	 * @param date 要转化的日期
	 * @return 返回Integer
	 */
	public static Integer getSec(Date date){
		gregorianCalendar.setTime(date);
		return gregorianCalendar.get(Calendar.SECOND);
	}
	
	/**
	 * @Title: getNowSec 
	 * @Description:得到现在的秒数
	 * make by wz on 2017年9月1日 下午4:35:50
	 * @return 返回Integer
	 */
	public static Integer getNowSec(){
		return getSec(new Date());
	}
	
	/**
	 * @Title: mathDate 
	 * @Description:增减给定日期
	 * make by wz on 2017年9月1日 上午10:59:42
	 * @param date 被增加日期
	 * @param changedate 改变数
	 * @param calendarField 改变域值
	 * @return 返回日期
	 */
	public static Date mathDate(Date date,int changedate,int calendarField){
		gregorianCalendar.setTime(date);
		gregorianCalendar.add(calendarField, changedate);
        return gregorianCalendar.getTime();
	}
	
	/**
	 * @Title: mathDayOfDate 
	 * @Description:给定日期变化天
	 * make by wz on 2017年9月1日 上午11:08:44
	 * @param date 被增加日期
	 * @param changedate 改变天数
	 * @return 返回日期
	 */
	public static Date mathDayOfDate(Date date,int changedate){
		return DateTimeUtil.mathDate(date, changedate, Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * @Title: mathNowDayOfDate 
	 * @Description:当前日期变化天
	 * make by wz on 2017年9月1日 上午11:09:39
	 * @param changedate 改变天数
	 * @return 返回日期
	 */
	public static Date mathNowDayOfDate(int changedate){
		return DateTimeUtil.mathDate(new Date(), changedate, Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * @Title: mathWeekOfDate 
	 * @Description:给定日期变化周
	 * make by wz on 2017年9月1日 上午11:12:32
	 * @param date 被改变提起
	 * @param changedate 变化周数
	 * @return返回日期
	 */
	public static Date mathWeekOfDate(Date date,int changedate){
		return DateTimeUtil.mathDate(date, changedate, Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * @Title: mathNowWeekOfDate 
	 * @Description:当前日期变化周
	 * make by wz on 2017年9月1日 上午11:12:52
	 * @param changedate 变化周数
	 * @return 返回日期
	 */
	public static Date mathNowWeekOfDate(int changedate){
		return DateTimeUtil.mathDate(new Date(), changedate, Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * @Title: mathMothOfDate 
	 * @Description:给定日期变化月
	 * make by wz on 2017年9月1日 上午11:15:20
	 * @param date 要变化的日期
	 * @param changedate 变化月数
	 * @return 返回日期
	 */
	public static Date mathMothOfDate(Date date,int changedate){
		return DateTimeUtil.mathDate(date, changedate, Calendar.MONTH);
	}
	
	
	/**
	 * @Title: mathNowMothOfDate 
	 * @Description:当前日期变化月
	 * make by wz on 2017年9月1日 上午11:15:42
	 * @param changedate 要变化得月数
	 * @return 返回日期
	 */
	public static Date mathNowMothOfDate(int changedate){
		return DateTimeUtil.mathDate(new Date(), changedate, Calendar.MONTH);
	}
	
	/**
	 * @Title: mathYearOfDate 
	 * @Description:给定日期变化年
	 * make by wz on 2017年9月1日 上午11:18:15
	 * @param date 要变化的日期
	 * @param changedate 要变化年数
	 * @return 返回日期
	 */
	public static Date mathYearOfDate(Date date,int changedate){
		return DateTimeUtil.mathDate(date, changedate, Calendar.YEAR);
	}
	
	/**
	 * @Title: mathNowYearOfDate 
	 * @Description:当前日期变化年
	 * make by wz on 2017年9月1日 上午11:18:39
	 * @param changedate 要变化的年数
	 * @return 返回日期
	 */
	public static Date mathNowYearOfDate(int changedate){
		return DateTimeUtil.mathDate(new Date(), changedate, Calendar.YEAR);
	}
	
	/**
	 * @Title: mathHourOfDate 
	 * @Description:给定日期变化小时
	 * make by wz on 2017年9月1日 上午11:29:49
	 * @param date 要变化的日期
	 * @param changedate 要变化的小时
	 * @return 返回日期
	 */
	public static Date mathHourOfDate(Date date,int changedate){
		return DateTimeUtil.mathDate(date, changedate, Calendar.HOUR);
	}
	
	/**
	 * @Title: mathNowHourOfDate 
	 * @Description:当前日期变化小时
	 * make by wz on 2017年9月1日 上午11:30:58
	 * @param changedate 要变化日期
	 * @return 返回日期
	 */
	public static Date mathNowHourOfDate(int changedate){
		return DateTimeUtil.mathDate(new Date(), changedate, Calendar.HOUR);
	}
	
	/**
	 * @Title: mathMinOfDate 
	 * @Description:给定日期变化分钟
	 * make by wz on 2017年9月1日 上午11:30:11
	 * @param date 要变化的日期
	 * @param changedate 变化分钟
	 * @return 返回日期
	 */
	public static Date mathMinOfDate(Date date,int changedate){
		return DateTimeUtil.mathDate(date, changedate, Calendar.MINUTE);
	}
	
	/**
	 * @Title: mathNowMinOfDate 
	 * @Description:当前日期变化分
	 * make by wz on 2017年9月1日 上午11:31:15
	 * @param changedate 变化分钟
	 * @return 返回日期
	 */
	public static Date mathNowMinOfDate(int changedate){
		return DateTimeUtil.mathDate(new Date(), changedate, Calendar.MINUTE);
	}
	
	/**
	 * @Title: mathSecOfDate 
	 * @Description:给定日期变化秒
	 * make by wz on 2017年9月1日 上午11:30:33
	 * @param date 要变化的日期
	 * @param changedate 变化秒数
	 * @return 返回日期
	 */
	public static Date mathSecOfDate(Date date,int changedate){
		return DateTimeUtil.mathDate(date, changedate, Calendar.SECOND);
	}
	
	/**
	 * @Title: mathNowSecOfDate 
	 * @Description:当前日期变化秒
	 * make by wz on 2017年9月1日 上午11:31:39
	 * @param changedate 变化秒数
	 * @return 返回的时间
	 */
	public static Date mathNowSecOfDate(int changedate){
		return DateTimeUtil.mathDate(new Date(), changedate, Calendar.SECOND);
	}
	
	/**
	 * @Title: compareDate 
	 * @Description:比较两个日期 相等返回0，小于返回1 大于返回-1
	 * make by wz on 2017年9月1日 下午12:02:14
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 大于返回1  小于返回-1  等于返回0
	 */
	public static int compareDate(Date beginDate,Date endDate){
		int result = 0;
        if (beginDate.getTime() < endDate.getTime()) {result = 1;}
        if (beginDate.getTime() > endDate.getTime()) {result = -1;}
        return result;
	}
	
	/**
	 * @Title: compareDate 
	 * @Description:比较两个日期 相等返回0，小于返回1 大于返回-1
	 * make by wz on 2017年9月1日 下午2:01:37
	 * @param beginDate 开始时间
	 * @param beginFormat 开始时间格式
	 * @param endDate 结束时间
	 * @param endFormat 结束时间格式
	 * @return 大于返回1  小于返回-1  等于返回0
	 * @throws ParseException 转化异常
	 */
	public static int compareDate(String beginDate, String beginFormat, String endDate, String endFormat) throws ParseException{
        return DateTimeUtil.compareDate(DateTimeUtil.stringToDate(beginDate, beginFormat),DateTimeUtil.stringToDate(endDate, endFormat));
	}
	
	/**
	 * @Title: compareDateBoolean 
	 * @Description:比较两个时间  第一个时间小于第二个时间返回真 否则否
	 * make by wz on 2017年9月1日 下午2:11:02
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @return 真为true,否为false
	 */
	public static boolean compareDateBoolean(Date beginDate,Date endDate){
		boolean result = false;
		if(DateTimeUtil.compareDate(beginDate,endDate)>=0) {result = true;}
        return result;
	}
	
	/**
	 * @Title: compareDateBoolean 
	 * @Description:比较两个时间  第一个时间小于第二个时间返回真 否则否
	 * make by wz on 2017年9月1日 下午2:11:43
	 * @param beginDate 日期字符串
	 * @param beginFormat 格式
	 * @param endDate 日期字符串	
	 * @param endFormat 格式
	 * @return 真返回true ,假返回false
	 * @throws ParseException 转化异常
	 */
	public static boolean compareDateBoolean(String beginDate, String beginFormat, String endDate, String endFormat) throws ParseException{
        return DateTimeUtil.compareDateBoolean(DateTimeUtil.stringToDate(beginDate, beginFormat),DateTimeUtil.stringToDate(endDate, endFormat));
	}
	
	/**
	 * @Title: getDateArrayDate 
	 * @Description:得到时间区间内 时间集合
	 * make by wz on 2017年9月1日 下午3:56:07
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param changDate 步长
	 * @param CalendarField 类型
	 * @return 返回时间类型集合
	 * @throws ParseException 转化异常
	 */
	public static ArrayList<Date> getDateArrayDate(Date beginDate, Date endDate, int changDate, int CalendarField) throws ParseException{
		ArrayList<Date> result = new ArrayList<Date>();
		//起始时间小于截止时间有效
		while(DateTimeUtil.compareDateBoolean(beginDate,endDate)){
			Date chageresult = DateTimeUtil.mathDate(beginDate, changDate, CalendarField);
			result.add(beginDate);
			if(DateTimeUtil.compareDateBoolean(chageresult,endDate)) {beginDate = chageresult;}
			else {break;}
		}
		return result;
	}
	
	/**
	 * @Title: getDateArrayString 
	 * @Description:得到时间区间内 时间集合
	 * make by wz on 2017年9月1日 下午3:56:07
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param changDate 步长
	 * @param CalendarField 类型
	 * @return 返回字符串集合
	 * @throws ParseException 转化异常
	 */
	public static ArrayList<String> getDateArrayString(String beginDate, String beginFormat, String endDate, String endFormat, int changDate, int CalendarField) throws ParseException{
		ArrayList<String> result = new ArrayList<String>();
		//起始时间小于截止时间有效
		while(DateTimeUtil.compareDateBoolean(beginDate,beginFormat,endDate,endFormat)){
			Date chageresult = DateTimeUtil.mathDate(DateTimeUtil.stringToDate(beginDate, beginFormat), changDate, CalendarField);
			String chageresultString = DateTimeUtil.getDateTimeForString(chageresult, beginFormat);
			result.add(beginDate);
			if(DateTimeUtil.compareDateBoolean(chageresultString,beginFormat,endDate,endFormat)){ beginDate = chageresultString;}
			else {break;}
		}
		return result;
	}
	
	/**
	 * @Title: getMinDateArrayString 
	 * @Description:得到时间区间内 分钟集合
	 * make by wz on 2017年9月1日 下午4:03:44
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param changDate 步长
	 * @return 返回字符串集合
	 */
	public static ArrayList<String> getMinDateArrayString(String beginDate, String beginFormat, String endDate, String endFormat, int changDate) throws ParseException{
		return DateTimeUtil.getDateArrayString(beginDate,beginFormat,endDate,endFormat,changDate,Calendar.MINUTE);
	}
	
	/**
	 * @Title: getHourDateArrayString 
	 * @Description:得到时间区间内 小时集合
	 * make by wz on 2017年9月1日 下午4:03:44
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param changDate 步长
	 * @return 返回字符串集合
	 */
	public static ArrayList<String> getHourDateArrayString(String beginDate, String beginFormat, String endDate, String endFormat, int changDate) throws ParseException{
		return DateTimeUtil.getDateArrayString(beginDate,beginFormat,endDate,endFormat,changDate,Calendar.HOUR);
	}
	/**
	 * @Title: getDayDateArrayString 
	 * @Description:得到时间区间内 天集合
	 * make by wz on 2017年9月1日 下午4:03:44
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param changDate 步长
	 * @return 返回字符串集合
	 */
	public static ArrayList<String> getDayDateArrayString(String beginDate, String beginFormat, String endDate, String endFormat, int changDate) throws ParseException{
		return DateTimeUtil.getDateArrayString(beginDate,beginFormat,endDate,endFormat,changDate,Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * @Title: getMonthDateArrayString 
	 * @Description:得到时间区间内 月集合
	 * make by wz on 2017年9月1日 下午4:03:44
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param changDate 步长
	 * @return 返回字符串集合
	 */
	public static ArrayList<String> getMonthDateArrayString(String beginDate, String beginFormat, String endDate, String endFormat, int changDate) throws ParseException{
		return DateTimeUtil.getDateArrayString(beginDate,beginFormat,endDate,endFormat,changDate,Calendar.MONTH);
	}
	
	/**
	 * @Title: getYearDateArrayString 
	 * @Description:得到时间区间内 年集合
	 * make by wz on 2017年9月1日 下午4:03:44
	 * @param beginDate 开始时间
	 * @param endDate 结束时间
	 * @param changDate 步长
	 * @return 返回字符串集合
	 */
	public static ArrayList<String> getYearDateArrayString(String beginDate, String beginFormat, String endDate, String endFormat, int changDate) throws ParseException{
		return DateTimeUtil.getDateArrayString(beginDate,beginFormat,endDate,endFormat,changDate,Calendar.YEAR);
	}
	
	public static void main(String[] args) throws ParseException {
		Date a= mathHourOfDate(new Date(),-48);
		String b = getDateTimeForString(a,"yyyy-MM-dd HH");
		System.out.println(b+":00:00");
	}
	
	
}
