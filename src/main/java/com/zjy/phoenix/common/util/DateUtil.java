/**
 * Copyright &copy; 2017-2027 <a href="http://www.cnony.com">JeeHook</a> All rights reserved.
 */
package com.zjy.phoenix.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author cnony
 * @version 2017-4-15
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
	
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};




	public static Date strToDate(String str) {
		if (str == null){
			return null;
		}
		try {
			return parseDate(str, parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String dateToStr(Date date) {
		String formatDate = DateFormatUtils.format(date, DEFAULT_PATTERN);
		return formatDate;
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDateStr(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}


	/**
	* @Description:  获取确定时间  确定格式的日期字符串  得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	* @param： null
	* @return:
	* @Author: ZhangJianYong
	* @Date: 19/4/12
	*/
	public static String getConfirmDate(Date date,String pattern) {
		return DateFormatUtils.format(date, pattern);
	}

	/**
	* @Description:  日期字符串中的日期格式
	* @param： null
	* @return:
	* @Author: ZhangJianYong
	* @Date: 19/4/12
	*/
	public static String getDatePattern(String date) {
		
		//String regex = "(\\d{4})-(\\d{1,2})-(\\d{1,2})\\s(\\d{1,2}):(\\d{1,2}):(\\d{1,2})";
		
		char[] charArray = date.toCharArray();
		//- 出现次数
		int counth = 0;
		// / 出现次数
		int countx = 0;
		//. 出现次数
		int countd = 0;
		//: 出现次数
	    int countm = 0;
		for (char c : charArray) {
			if(c=='-') {
				counth++;
			}
			if(c=='/') {
				countx++;
			}
			if(c=='.') {
				countd++;
			}
			if(c==':') {
				countm++;
			}
		}
		if(countm==0) {
			if(countd==1) {
				return "yyyy.MM";
			}
			if(counth==1) {
				return "yyyy-MM";
			}
			if(countx==1) {
				return "yyyy/MM";
			}
			if(countd==2) {
				return "yyyy.MM.dd";
			}
			if(counth==2) {
				return "yyyy-MM-dd";
			}
			if(countx==2) {
				return "yyyy/MM/dd";
			}
		}else if(countm==1) {
			if(countd==2) {
				return "yyyy.MM.dd HH:mm";
			}
			if(counth==2) {
				return "yyyy-MM-dd HH:mm";
			}
			if(countx==2) {
				return "yyyy/MM/dd HH:mm";
			}
			
		}else if(countm==2) {
			
			if(countd==2) {
				return "yyyy.MM.dd HH:mm:ss";
			}
			if(counth==2) {
				return "yyyy-MM-dd HH:mm:ss";
			}
			if(countx==2) {
				return "yyyy/MM/dd HH:mm:ss";
			}
		}
		return null;
	}

	public static String getDateByPatternByMilliseconds(long parseLong, String pattern) {
		// TODO Auto-generated method stub
		return DateFormatUtils.format(parseLong,pattern);
	}


}
