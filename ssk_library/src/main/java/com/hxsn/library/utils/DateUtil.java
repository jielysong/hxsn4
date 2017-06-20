package com.hxsn.library.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

	public static String formatLongToTimeStr(Long l) {
		int hour = 0;
		int minute = 0;
		int second = 0;

		second = l.intValue() / 1000;

		if (second > 60) {
			minute = second / 60;
			second = second % 60;
		} else {
			return second + "秒";
		}
		if (minute > 60) {
			hour = minute / 60;
			minute = minute % 60;
		} else {
			return minute + "分钟" + second + "秒";
		}
		return hour + "小时" + minute + "分钟" + second + "秒";
	}

	/**
	 * 返回参数date格式化后的字符串
	 * 
	 */
	public static String getFormatMonthDate(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串
	 * 
	 */
	public static String getFormatHHDate(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串
	 * 
	 */
	public static String getFormatHmDate(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串
	 * 
	 */
	public static String getFormatDate(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串yyyyMMddHHmmss
	 * 
	 */
	public static String getFormatDateStr(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串
	 * 
	 */
	public static String getFormatDate(long date) {
		String dateStr = "";
		if (date != 0) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串 MM月dd日
	 */
	public static String getFormatDateNoYear(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串 yyyy年MM月dd日
	 */
	public static String getFormatDateNoTimeChinese(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 根据将日期转换为formatStr指定格式的字符串
	 */
	public static String getFormatDateByFormat(Date date, String format) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串 yyyy年MM月dd日
	 */
	public static String getFormatDateNoTimeChinese(long date) {
		Date datetime = new Date();
		String dateStr = "";
		if (datetime != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串 yyyy-MM-dd
	 */
	public static String getFormatDateNoTime(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 返回参数date格式化后的字符串 MM-dd
	 */
	public static String getFormatDate_M_D(Date date) {
		String dateStr = "";
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
			dateStr = dateFormat.format(date);
		}
		return dateStr;
	}

	/**
	 * 获取当前日期 yyyyMMdd
	 * 
	 * @return
	 */
	public static String getCurrentDate() {
		String pattern = "yyyyMMdd";
		String dateStr = getCurrentDate(pattern);
		return dateStr;
	}

	/**
	 * 获取当前日期 yyyy年MM月dd日
	 * 
	 * @return
	 */
	public static String getCurrentDateChinese() {
		String pattern = "yyyy年MM月dd日";
		String dateStr = getCurrentDate(pattern);
		return dateStr;
	}

	/**
	 * @param pattern
	 * @return
	 */
	public static String getCurrentDate(String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String dateStr = dateFormat.format(new Date());
		return dateStr;
	}

	/**
	 * 获取当前天之前a天的时间
	 * 
	 * @param a
	 */
	public static Date getAddDayDate(int a) {
		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day + a);
		// 投票的有效期30天
		return calendar.getTime();

	}

	/**
	 * 获取当前天之前a天的时间
	 * 
	 * @param a
	 */
	public static Date getAddDayHour(int a) {
		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		calendar.set(Calendar.HOUR_OF_DAY, hour + a);
		// 投票的有效期30天
		return calendar.getTime();

	}

	/**
	 * 获取当前天之前a天的时间
	 * 
	 * @param a
	 */
	public static Date getSubDayDate(int a) {
		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		if (a == 1) {
			calendar.set(Calendar.HOUR_OF_DAY, 1);
			return calendar.getTime();
		}
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day - a);
		// 投票的有效期30天
		return calendar.getTime();

	}

	/**
	 * 获取当前天之前a天的时间
	 * 
	 * @param a
	 */
	public static String getSubDayDateString(int a) {
		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		if (a == 1) {
			calendar.set(Calendar.HOUR_OF_DAY, 1);
			return getFormatDate(calendar.getTime());
		}
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day - a);
		// 投票的有效期30天
		return getFormatDate(calendar.getTime());

	}

	/**
	 * 获取当前天之后a天的时间
	 * 
	 * @param a
	 */
	public static String getAddDayDateString(int a) {
		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		if (a == 1) {
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			return getFormatDate(calendar.getTime());
		}
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day + a);

		// 投票的有效期30天
		return getFormatDate(calendar.getTime());

	}

	/**
	 * 获取当前天之前a天的时间
	 * 
	 * @param a
	 */
	public static String getSubDayDateStringWithoutTime(int a) {
		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		if (a == 1) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			return getFormatDateNoTime(calendar.getTime());
		}
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day - a);
		// 投票的有效期30天
		return getFormatDateNoTime(calendar.getTime());

	}

	public static Date addDateOfDay(Date date, int num) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, num);

		date = calendar.getTime();

		return date;
	}

	/**
	 * 获取当前天之后a天的时间
	 * 
	 * @param a
	 */
	public static String getAddDayDateStringWithoutTime(int a) {
		Calendar calendar = Calendar.getInstance();
		// getTime()方法是取得当前的日期，其返回值是一个java.util.Date类的对象
		if (a == 1) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			return getFormatDateNoTime(calendar.getTime());
		}
		int day = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, day + a);

		// 投票的有效期30天
		return getFormatDateNoTime(calendar.getTime());

	}

	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 获取当前年份
	 *
	 */
	public static int getCurrentYear() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		return year;

	}

	/**
	 * 获取当前月
	 *
	 */
	public static int getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(Calendar.MONTH);
		return i + 1;

	}

	public static int getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(Calendar.DAY_OF_YEAR);
		return i + 1;

	}

	/**
	 * 根据当前日期然会long类型的日期
	 * 
	 * @throws ParseException
	 */
	public static long getFormatDateLong(Date date) throws ParseException {
		long dateLong = 0;
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = dateFormat.format(date);
			Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			dateLong = newDate.getTime();
		}
		return dateLong;
	}

	/**
	 * 根据当前日期然会long类型的日期
	 * 
	 * @throws ParseException
	 */
	public static Date formatDateWithoutTime(Date date) {

		Date newDate = null;
		if (date != null) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = dateFormat.format(date);
			try {
				newDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newDate;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static Timestamp getCurTimestamp() {
		return new Timestamp(new Date().getTime());
	}
}
