package com.example.mq1.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class DateUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getMonthStartTime(getServerTime()));
	}
	public static String getNextDay(String appDate) {
		return getFutureDay(appDate, "yyyy-MM-dd", 1);
	}
	public static String getFutureDay(String appDate, int days) {
		return getFutureDay(appDate, "yyyy-MM-dd", days);
	}
	public static String getFutureMonth(String appDate, String format, int months) {
		String future = "";
		try{
			Calendar calendar = GregorianCalendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			Date date = simpleDateFormat.parse(appDate);
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, months);
			date = calendar.getTime();
			future = simpleDateFormat.format(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		return future;
	}
	public static String getFutureDay(String appDate, String format, int days) {
		String future = "";
		try{
			Calendar calendar = GregorianCalendar.getInstance();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			Date date = simpleDateFormat.parse(appDate);
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			date = calendar.getTime();
			future = simpleDateFormat.format(date);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return future;
	}
	public static Date getFutureDay(Date date,int days) {
		try{
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.DATE, days);
			return calendar.getTime();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Date getFutureHour(Date date,int hours) {
		try{
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.HOUR_OF_DAY, hours);
			return calendar.getTime();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static Date getFutureMonth(Date date,int months) {
		try{
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(date);
			calendar.add(Calendar.MONTH, months);
			return calendar.getTime();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	public static int getDifferenceDay(String minDate,String maxDate,String format) {
		long diffTime = 0;
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			long minTime = simpleDateFormat.parse(minDate).getTime();
			long maxTime = simpleDateFormat.parse(maxDate).getTime();
			long diff = maxTime - minTime;
			diffTime =  diff / (24 * 60 * 60 * 1000);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return (int)diffTime;
	}
	public static long getDifferenceTime(String minDate,String maxDate,String format) {
		long diffTime = 0;
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
			long minTime = simpleDateFormat.parse(minDate).getTime();
			long maxTime = simpleDateFormat.parse(maxDate).getTime();
			diffTime = maxTime - minTime;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return diffTime;
	}
	public static Calendar getEarliestDate(Calendar currentDate, int dayOfWeek,
			int hourOfDay, int minuteOfHour, int secondOfMinite) {
		//计算当前时间的WEEK_OF_YEAR,DAY_OF_WEEK, HOUR_OF_DAY, MINUTE,SECOND等各个字段值
		int currentWeekOfYear = currentDate.get(Calendar.WEEK_OF_YEAR);
		int currentDayOfWeek = currentDate.get(Calendar.DAY_OF_WEEK);
		int currentHour = currentDate.get(Calendar.HOUR_OF_DAY);
		int currentMinute = currentDate.get(Calendar.MINUTE);
		int currentSecond = currentDate.get(Calendar.SECOND);

		//如果输入条件中的dayOfWeek小于当前日期的dayOfWeek,则WEEK_OF_YEAR需要推迟一周
		boolean weekLater = false;
		if (dayOfWeek < currentDayOfWeek) {
			weekLater = true;
		} else if (dayOfWeek == currentDayOfWeek) {
			//当输入条件与当前日期的dayOfWeek相等时，如果输入条件中的
			//hourOfDay小于当前日期的
			//currentHour，则WEEK_OF_YEAR需要推迟一周	
			if (hourOfDay < currentHour) {
				weekLater = true;
			} else if (hourOfDay == currentHour) {
                 //当输入条件与当前日期的dayOfWeek, hourOfDay相等时，
                 //如果输入条件中的minuteOfHour小于当前日期的
				//currentMinute，则WEEK_OF_YEAR需要推迟一周
				if (minuteOfHour < currentMinute) {
					weekLater = true;
				} else if (minuteOfHour == currentSecond) {
                     //当输入条件与当前日期的dayOfWeek, hourOfDay， 
                     //minuteOfHour相等时，如果输入条件中的
                    //secondOfMinite小于当前日期的currentSecond，
                    //则WEEK_OF_YEAR需要推迟一周
					if (secondOfMinite < currentSecond) {
						weekLater = true;
					}
				}
			}
		}
		if (weekLater) {
			//设置当前日期中的WEEK_OF_YEAR为当前周推迟一周
			currentDate.set(Calendar.WEEK_OF_YEAR, currentWeekOfYear + 1);
		}
		// 设置当前日期中的DAY_OF_WEEK,HOUR_OF_DAY,MINUTE,SECOND为输入条件中的值。
		currentDate.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		currentDate.set(Calendar.HOUR_OF_DAY, hourOfDay);
		currentDate.set(Calendar.MINUTE, minuteOfHour);
		currentDate.set(Calendar.SECOND, secondOfMinite);
		return currentDate;
	}
	/**
	 * 获取月日历
	 * @param month
	 * @param format
	 * @return
	 */
	public static String[] getCalendar(String month,String format){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(parse(month,format));
		int currMonthDays = calendar.getActualMaximum(Calendar.DATE);
		String[] monthDayArray = new String[currMonthDays];
		for(int i = 0; i < currMonthDays; i++){
			int n = i + 1;
			if(n < 10){
				monthDayArray[i] = new StringBuffer("0").append(String.valueOf(n)).toString();
			}else{
				monthDayArray[i] = String.valueOf(n);
			}			
		}
		return monthDayArray;
	}
	/**
	 * 获取时间段内日期
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getCalendar(String startDateStr,String endDateStr,String format){
		Date startDate = parse(startDateStr, format);
		Date endDate = parse(endDateStr, format);
		List<String> dateList = new ArrayList<String>();
		dateList.add(startDateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		while(true){
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			Date date = calendar.getTime();
			if(!endDate.after(date)){
				break;
			}
			dateList.add(format(date,format));
		}
		if (startDate.compareTo(endDate) != 0){
			dateList.add(endDateStr);
		}
		return dateList;
	}
	
	public static List<String> getYears(String startDateStr,String endDateStr,String format){
		Date startDate = parse(startDateStr, format);
		Date endDate = parse(endDateStr, format);
		List<String> dateList = new ArrayList<String>();
		dateList.add(startDateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		while(true){
			calendar.add(Calendar.YEAR, 1);
			Date date = calendar.getTime();
			if(!endDate.after(date)){
				break;
			}
			dateList.add(format(date,format));
		}
		dateList.add(endDateStr);
		return dateList;
	}
	/**
	 * 字符串转日期
	 * @param str
	 * @param format
	 * @return
	 */
	public static Date parse(String str,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("字符串转日期错误",e);
		}
		return date;
	}
	/**
	 * 日期转字符串
	 * @param str
	 * @param format
	 * @return
	 */
	public static String format(Date date,String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	/**
	 * 格式化时间
	 * @param str
	 * @param srcFormat
	 * @param targetFormat
	 * @return
	 */
	public static final String getFormatTime(String str,String srcFormat,String targetFormat){
		Date date = null;
		String retString = str;
		SimpleDateFormat srcFormatter = new SimpleDateFormat(srcFormat);
		try {
			date = srcFormatter.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("字符串转日期错误",e);
		}
		SimpleDateFormat targetFormatter = new SimpleDateFormat(targetFormat);
		retString = targetFormatter.format(date);
		return retString;
	}
	/**
	 * 比较两个日期字符串先后
	 * @param appDate1
	 * @param appDate2
	 * @param format
	 * @return
	 */
	public static final int compareTo(String appDate1,String appDate2,String format){
		Date date1 = parse(appDate1,format);
		Date date2 = parse(appDate2,format);
		return date1.compareTo(date2);
	}
	public static int getWeekFromTime(String time,String timeFormat){
        Calendar calendar = Calendar.getInstance();
        int weekIndex = 0;
        try {
        	 	SimpleDateFormat sdf = new SimpleDateFormat(timeFormat);
			calendar.setTime(sdf.parse(time));
			calendar.set(Calendar.DAY_OF_WEEK, 2);
	        weekIndex = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	        if (weekIndex < 0){
	        		weekIndex = 0;
	        }
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
        return weekIndex;
	}
	public static String getChineseWeekFromTime(String time,String timeFormat){
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
        return weekDays[getWeekFromTime(time, timeFormat)];
	}
	public static final String getServerTime(String format){
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return format(date, format);
	}
	public static final Date getServerTime(){
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return date;
	}
	public static final Date add(Date date,int unit,int time){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(unit, time);
		return calendar.getTime();
	}
	
	public static Date getFutureMinute(Date startTime, int minute) {
		try{
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTime(startTime);
			calendar.add(Calendar.MINUTE, minute);
			Date futureDate = calendar.getTime();
			return futureDate;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static Date getDayStartTime(Date date) { 
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);  
        return calendar.getTime();  
    }
	public static Date getDayEndTime(Date date) { 
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);  
        return calendar.getTime();  
    }
	public static Date getMonthStartTime(Date date) { 
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);  
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);  
        return calendar.getTime();  
    }
	public static Date getMonthEndTime(Date date) { 
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH,1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);  
        calendar.set(Calendar.MINUTE, 0);  
        calendar.set(Calendar.SECOND, 0);  
        calendar.set(Calendar.MILLISECOND, 0);  
        return calendar.getTime();  
    }
	
	public static Date getWeekStartTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		return getDayStartTime(calendar.getTime());  
	}


	// 秒转换成两位的时间，格式：HH:mm:ss
	public static String turnSecondsToTimestring(int seconds) {
		String result = "";
		int hour = 0, min = 0, second = 0;
		hour = seconds / 3600;
		min = (seconds - hour * 3600) / 60;
		second = seconds - hour * 3600 - min * 60;
		if (hour < 10) {
			result += "0" + hour + ":";
		} else {
			result += hour + ":";
		}
		if (min < 10) {
			result += "0" + min + ":";
		} else {
			result += min + ":";
		}
		if (second < 10) {
			result += "0" + second;
		} else {
			result += second;
		}

		return result;

	}

	/**
	 * 判断两个时间段是否有交集
	 * @param aStartTime
	 * @param aEndTime
	 * @param bStartTime
	 * @param bEndTime
	 * @return
	 */
	public static boolean onUnion(Date aStartTime,Date aEndTime,Date bStartTime,Date bEndTime){
		long a = aStartTime.getTime();
		long b = aEndTime.getTime();
		long x = bStartTime.getTime();
		long y = bEndTime.getTime();
		if (x < b && a < y){
			return true;
		}else {
			return false;
		}
	}
}