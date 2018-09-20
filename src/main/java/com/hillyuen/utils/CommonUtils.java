package com.hillyuen.utils;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用的字符串操作类
 * @author guanshengliao
 * @Description 通用的字符串操作类
 * @version V1.0
 * @Date 2018年3月6日 下午3:58:27 
 * @Copyright Copyright © 2017 深圳花儿绽放网络科技股份有限公司. All rights reserved.
 */
public class CommonUtils {

	private static char[] numbersAndLetters = null;
	private static char[] numbersLetters = null;

	private static final int[] sizeTable = { 9, 99, 999, 9999, 99999, 999999, 9999999, 99999999, 999999999,
			Integer.MAX_VALUE };

	static {
		numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ")
				.toCharArray();
		numbersLetters = ("0123456789").toCharArray();
	}

	// 地球平均半径（单位：千米）
	private static final double EARTH_RADIUS = 6370.856;
	
	/**
	 * 平台密码加密私钥
	 */
	private static final String PRIVATE_KEY = "huaer-(365)";

	/**
	 * 默认编码格式
	 */
	private static final String DEFAULTCHARSET = "UTF-8";
	/**
	 * 用来将字节转换成 16 进制表示的字符
	 */
	private static final char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f' };

	/**
	 * 转换Date类型为字符串类型
	 * 
	 * @param value
	 * @return
	 */
	public static String getSimpleDate(Date value) {
		return getSimpleDate(value, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 转换Date类型为中文字符串类型
	 * 
	 * @param value
	 * @return
	 */
	public static String getChinieseDate(Date value) {
		return getSimpleDate(value, "yyyy年MM月dd日HH时mm分");
	}

	/**
	 * 转换Date类型为字符串类型
	 * 
	 * @param value
	 * @return
	 */
	public static String getSimpleDate(Date value, String pattern) {
		if(null == value){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(value);
	}

	/**
	 * 判断字符串是否空或空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为非空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * 返回trim后的字符串，如空字符串，则直接返回空。
	 * 
	 * @param str
	 * @return
	 */
	public static String trimToEmpty(String str) {
		if (str == null || str.trim().length() == 0) {
			return null;
		}
		return str.trim();
	}

	/**
	 * 返回trim后的字符串，如果为null,则返回""
	 * 
	 * @param str
	 * @return
	 */
	public static String trimToEmptyForce(String str) {
		if (str == null || str.trim().length() == 0) {
			return "";
		}
		return str.trim();
	}

	/**
	 * 判断字符是否为数值型
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		try {
			Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}

	}

	/**
	 * 返回日期是否在指定日期之间
	 * @param date
	 * @param start
	 * @param end
     * @return
     */
	public static Boolean betweenDates(Date date,Date start,Date end)
	{
		try{
			if(date.before(end) && date.after(start)) {
				return true;
			}
		}catch(Exception e)
		{
			return false;
		}
		return false;
	}
	
	/**
	 * 返回日期是否在指定日期之间(包括相等情况)
	 * @param date
	 * @param start
	 * @param end
     * @return
     */
	public static boolean betweenDatesWithBound(Date date,Date start,Date end) {
		try {
			if (date.getTime() == start.getTime() || date.getTime() == end.getTime()) {
				return true;
			}else if(date.before(end) && date.after(start)) {
				return true;
			}
		} catch (Exception e) {}
		return false;
	}

	/**
	 * 获取几月后的时间
	 * 
	 * @param d
	 * @param m
	 * @return
	 */
	public static Date nextMonth(Date d, int m) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MONTH, now.get(Calendar.MONTH) + m);
		return now.getTime();
	}

	/**
	 * 获取几天后的时间
	 * 
	 * @param d
	 * @param day
	 * @return
	 */
	public static Date nextDate(Date d, int day) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
		return now.getTime();
	}

	/**
	 * 获取几小时后的时间
	 * 
	 * @param d
	 * @param hour
	 * @return
	 */
	public static Date nextHour(Date d, int hour) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) + hour);
		return now.getTime();
	}

	/**
	 * 获取几分钟后的时间
	 * 
	 * @param d
	 * @param minute
	 * @return
	 */
	public static Date nextMinute(Date d, int minute) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.MINUTE, now.get(Calendar.MINUTE) + minute);
		return now.getTime();
	}
	
	/**
	 * 获取几秒钟后的时间
	 * 
	 * @param d
	 * @param second
	 * @return
	 */
	public static Date nextSecond(Date d, int second) {
		Calendar now = Calendar.getInstance();
		now.setTime(d);
		now.set(Calendar.SECOND, now.get(Calendar.SECOND) + second);
		return now.getTime();
	}

	/**
	 * 获取两个日期的间隔天数
	 * 
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int dayInterval(Date startDay, Date endDay) {
		return (int) ((endDay.getTime() - startDay.getTime()) / (24 * 60 * 60 * 1000));
	}
	
	/**
	 * 获取两个日期的间隔分钟
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public static int minuteInterval(Date startDay, Date endDay) {
		return (int) ((endDay.getTime() - startDay.getTime()) / (60 * 1000));
	} 

	/**
	 * 两个时间相差距离多少天多少小时多少分多少秒
	 * 
	 * @return String 返回值为：xx天xx小时xx分xx秒
	 */
	public static String getDistanceTime(Date startTime, Date endTime) {
		long day = 0;
		long hour = 0;
		long min = 0;
		long sec = 0;
		long time1 = startTime.getTime();
		long time2 = endTime.getTime();
		long diff;
		if (time1 < time2) {
			diff = time2 - time1;
		} else {
			diff = time1 - time2;
		}
		day = diff / (24 * 60 * 60 * 1000);
		hour = (diff / (60 * 60 * 1000) - day * 24);
		min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
		sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		return day + "天" + hour + "小时" + min + "分" + sec + "秒";
	}
	
	public static String getHourByMinute(Integer minute) {
		long hour = minute / 60;
		long min = minute % 60;
		if (hour > 0) {
			return hour + "小时" + min + "分钟";
		}else {
			return min + "分钟";
		}
	}

	/**
	 * 转换String类型为Date类型
	 * 
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date getSimpleDate(String value) throws ParseException {
		if(null == value || "".equals(value.trim())){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.parse(value);
	}

	/**
	 * 转换String类型为Date类型
	 *
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date getSimpleDateBy(String value, String pattern) throws ParseException {
		if(null == value || "".equals(value.trim())){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.parse(value);
	}

	/**
	 * 转换String类型为Date类型
	 *
	 * @param value
	 * @return
	 * @throws ParseException
	 */
	public static Date getSimpleDate2(String value) throws ParseException {
		if(null == value || "".equals(value.trim())){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.parse(value);
	}

	/**
	 * 把时分秒置为0
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateOnly(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	
	/**
	 * 拿取本周周一的0点0分0秒时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getMondayDate(){
		Date date = new Date();
		Date nextDate = CommonUtils.nextDate(date, -(date.getDay()-1));
		nextDate = getDateOnly(nextDate);
		return nextDate;
	}
	
	/**
	 * 首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String getUpperName(String name) {
		byte[] items = name.getBytes();
		items[0] = (byte) ((char) items[0] - 'a' + 'A');
		return new String(items);
	}

	/**
	 * 判断字符串是否合法(不含有非法字符或中文) 若数组中其中一字符串含有非法字符，返回false，反之，返回true
	 * 
	 * @param sarray
	 * @return
	 */
	public static boolean judgeIllegalChar(String[] sarray) {
		boolean result = true;
		if (sarray != null) {
			Pattern pattern = Pattern.compile("^\\w+$");
			for (int i = 0; i < sarray.length; i++) {
				if (!pattern.matcher(sarray[i]).matches()) {
					result = false;
					break;
				}
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 判断字符串是否为合法邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean judgeEmail(String email) {
		boolean result = false;
		if (email != null) {
			Pattern pattern = Pattern.compile("^([a-z0-9A-Z._-])+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
			if (pattern.matcher(email).matches()) {
				result = true;
			}
		}
		return result;
	}

	/**
	 * 判断字符串是否合法(不含有非法字符，可含中文) 若数组中其中一字符串含有非法字符，返回false，反之，返回true
	 * 
	 * @param sarray
	 * @return
	 */
	public static boolean judgeIllegalCharAndChinese(String[] sarray) {
		boolean result = true;
		if (sarray != null) {
			Pattern pattern = Pattern.compile("^[\u4e00-\u9fa5a-zA-Z0-9_-]+$");
			for (int i = 0; i < sarray.length; i++) {
				if (sarray[i] == null || !pattern.matcher(sarray[i]).matches()) {
					result = false;
					break;
				}
			}
		} else {
			result = false;
		}
		return result;
	}

	/**
	 * 判断字符串是否合法(不含 ' 字符，可含中文) 若数组中其中一字符串含有非法字符，返回false，反之，返回true
	 * 
	 * @param sarray
	 * @return
	 */
	public static boolean judgePartIllegalCharAndChinese(String[] sarray) {
		boolean result = true;
		if (sarray != null) {
			Pattern pattern = Pattern.compile("[^\']+");
			for (int i = 0; i < sarray.length; i++) {
				if (sarray[i] == null || !pattern.matcher(sarray[i]).matches()) {
					result = false;
					break;
				}
			}
		} else {
			result = false;
		}
		return result;
	}

	public static String apiEncode(Integer uid, Integer wuid) {
		String randString = UUID.randomUUID().toString();
		String startString = randString.substring(0, 4);
		String endString = randString.substring(0, 10);
		String midString = "C";
		wuid = wuid << 2;
		uid = uid << 3;
		return startString + uid + midString + wuid + endString;
	}

	public static Integer[] apiDecode(String key) {
		key = key.substring(0, key.length() - 10).substring(4);
		String[] array = key.split("C");
		Integer uid = Integer.parseInt(array[0]);
		uid = uid >> 3;
		Integer wuid = Integer.parseInt(array[1]);
		wuid = wuid >> 2;
		return new Integer[] { uid, wuid };
	}

	/**
	 * 分享链接编码
	 * 
	 * @param uid
	 * @return
	 */
	public static String shareEncode(Integer uid) {
		String randString = UUID.randomUUID().toString();
		String startString = randString.substring(0, 4);
		String endString = randString.substring(0, 10);
		String midString = "C";
		uid = uid << 3;
		return startString + uid + midString + endString;
	}

	/**
	 * 分享链接解码
	 * 
	 * @param key
	 * @return
	 */
	public static Integer shareDecode(String key) {
		key = key.substring(0, key.length() - 10).substring(4);
		String[] array = key.split("C");
		Integer uid = Integer.parseInt(array[0]);
		uid = uid >> 3;
		return uid;
	}

	public static final String randomString(int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersAndLetters[new Random().nextInt(71)];
		}
		return new String(randBuffer);
	}

	public static final String randomNumber(int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersLetters[new Random().nextInt(10)];
		}
		return new String(randBuffer);
	}
	/**
	 * 定制一种格式的随机数，设定开头和长度
	 */
	public static final String randomCusNumber(int first,int length) {
		if (length < 1) {
			return null;
		}
		char[] randBuffer = new char[length];
		for (int i = 0; i < randBuffer.length; i++) {
			randBuffer[i] = numbersLetters[new Random().nextInt(10)];
			if(i==0){randBuffer[i]=(char)(first+48);}
		}
		return new String(randBuffer);
	}
	

	/**
	 * 截取左边max个字符,ascll码大于255算两个字符，字符过长以...结尾
	 * 
	 * @param s
	 * @param max
	 * @return
	 */
	public static String left(String s, int max) {
		char[] cs = s.toCharArray();
		int count = 0;
		int last = cs.length;
		for (int i = 0, len = last; i < len; i++) {
			if (cs[i] > 255) {
				count += 2;
			} else {
				count++;
			}
			if (count > max) {
				last = i + 1;
				break;
			}
		}
		if (count <= max) {
			return s;
		}
		max -= 3;
		for (int i = last - 1; i >= 0; i--) {
			if (cs[i] > 255) {
				count -= 2;
			} else {
				count--;
			}
			if (count <= max) {
				return s.substring(0, i) + "...";
			}
		}
		return "...";
	}

	@SuppressWarnings("restriction")
	public static String base64Encode(String s) {
		return new sun.misc.BASE64Encoder().encode(s.getBytes());
	}

	@SuppressWarnings("restriction")
	public static String base64Decode(String s) {
		try {
			return new String(new sun.misc.BASE64Decoder().decodeBuffer(s));
		} catch (IOException e) {
			return "";
		}
	}

	/**
	 * 从指定的时间截取年月日。将时分秒毫秒都设置为0
	 * 
	 * @param source
	 *            原始时间
	 * @return 将时分秒毫秒都设置为0的日期
	 */
	public static Date trimDate(Date source) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 返回指定时间在当天的最后时间，便于查询
	 * @param source
	 * @return
	 */
	public static Date fillDate(Date source) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(source);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static boolean contains(String target, String[] list) {
		boolean contain = false;
		for (String item : list) {
			if (item.equals(target)) {
				contain = true;
				break;
			}
		}
		return contain;
	}

	/**
	 * 计算精度和维度之间的距离
	 * 
	 * @param a_x_point
	 * @param a_y_point
	 * @param b_x_point
	 * @param b_y_point
	 * @return
	 */
	public static long calDistance(Double a_x_point, Double a_y_point, Double b_x_point, Double b_y_point) {
		Double R = new Double(EARTH_RADIUS);
		Double dlat = (b_x_point - a_x_point) * Math.PI / 180;
		Double dlon = (b_y_point - a_y_point) * Math.PI / 180;
		Double aDouble = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(a_x_point * Math.PI / 180)
				* Math.cos(b_x_point * Math.PI / 180) * Math.sin(dlon / 2) * Math.sin(dlon / 2);
		Double cDouble = 2 * Math.atan2(Math.sqrt(aDouble), Math.sqrt(1 - aDouble));
		long d = Math.round((R * cDouble) * 1000);
		return d;
	}

	/**
	 * 计算某个经纬度的周围某段距离的正方形的四个点
	 * 
	 * @return
	 */
	public static Map<SquarePoint, Point> calSquarePoint(Double longitude, Double latitude, Double distance) {
		Double longitudeRad = 2 * Math.asin(Math.sin(distance / (2 * EARTH_RADIUS)) / Math.cos(deg2rad(latitude)));
		Double longitudeDeg = rad2deg(longitudeRad);

		Double latitudeRad = distance / EARTH_RADIUS;
		Double latitudeDeg = rad2deg(latitudeRad);

		Map<SquarePoint, Point> squarePoint = new HashMap<SquarePoint, Point>(4, 1);
		squarePoint.put(SquarePoint.LEFT_TOP, new Point(longitude - longitudeDeg, latitude + latitudeDeg));
		squarePoint.put(SquarePoint.RIGHT_TOP, new Point(longitude + longitudeDeg, latitude + latitudeDeg));
		squarePoint.put(SquarePoint.LEFT_BOTTOM, new Point(longitude - longitudeDeg, latitude - latitudeDeg));
		squarePoint.put(SquarePoint.RIGHT_BOTTOM, new Point(longitude + longitudeDeg, latitude - latitudeDeg));
		return squarePoint;
	}

	public static enum SquarePoint {
		LEFT_TOP, RIGHT_TOP, LEFT_BOTTOM, RIGHT_BOTTOM
	}

	public static class Point {
		/**
		 * 经度
		 */
		private Double longtitude;
		/**
		 * 纬度
		 */
		private Double latitude;

		public Point(Double longtitude, Double latitude) {
			super();
			this.longtitude = longtitude;
			this.latitude = latitude;
		}

		/**
		 * @see #longtitude
		 * @return the longtitude
		 */
		public Double getLongtitude() {
			return longtitude;
		}

		/**
		 * @see #longtitude
		 * @param longtitude
		 *            the longtitude to set
		 */
		public void setLongtitude(Double longtitude) {
			this.longtitude = longtitude;
		}

		/**
		 * @see #latitude
		 * @return the latitude
		 */
		public Double getLatitude() {
			return latitude;
		}

		/**
		 * @see #latitude
		 * @param latitude
		 *            the latitude to set
		 */
		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

	}

	/**
	 * 角度转弧度
	 * 
	 * @param deg
	 * @return
	 */
	public static Double deg2rad(Double deg) {
		return 2 * Math.PI * deg / 360;
	}

	/**
	 * 弧度转角度
	 * 
	 * @param rad
	 * @return
	 */
	public static Double rad2deg(Double rad) {
		return 360 * rad / (2 * Math.PI);
	}

	/**
	 * 获取字符串的长度，中文占一个字符,英文数字占半个字符
	 * 
	 * @param value
	 *            指定的字符串
	 * @return 字符串的长度
	 */
	public static double length(String value) {
		double valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 1;
			} else {
				valueLength += 0.5;
			}
		}
		return Math.ceil(valueLength);
	}

	/**
	 * 生成一个随机码
	 * 
	 * @return
	 */
	public static String getRandomCode() {
		SimpleDateFormat millSecondFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String prefix = millSecondFormat.format(new Date());
		Random random = new Random();
		int number = random.nextInt(100);
		return prefix + number;
	}
	/**
	 * 获取一个int常量的位数
	 * 
	 * @param source
	 * @return
	 */
	public static int sizeOfInt(int source) {
		for (int i = 0;; i++) {
			if (source <= sizeTable[i]) {
				return i + 1;
			}
		}
	}

	/**
	 * 计算两个时间之间的秒数，注意不能超过50年
	 * 
	 * @param start
	 *            开始时间
	 * @param end
	 *            结束时间
	 * @return
	 */
	public static int calSecondsBetween(Date start, Date end) {
		long times = (end.getTime() - start.getTime()) / 1000;
		return (int) times;
	}
	/**
	 * 哦按段字符串是否为合法IP
	 * @param ip
	 * @author liujie
	 * @return
	 */
	public static boolean isIP(String ip){
		if(null == ip || "".equals(ip)){
			return false;
		}
		String regEx = "^([0-9]|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.([0-9]|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.([0-9]|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.([0-9]|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])$";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}
	/**
	 * 判断是否为合法手机号码
	 * @param tel
	 * @author liujie
	 * @return
	 */
	public static boolean isTel(String tel){
		if(null == tel || "".equals(tel)){
			return false;
		}
		String regEx = "0?(13|14|15|18|17)[0-9]{9}";
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(tel);
		return matcher.matches();
	}
	/**
	 * 判断当前调用是否符合概率值
	 * @param relative 总相对值
	 * @param probability 幸运概率
	 * @author liujie
	 * @return 是否幸运
	 */
	public static boolean isLuck(int relative, int probability){
		if(relative == 0 || relative < probability){
			return false;
		}
		Random random = new Random();
		return (random.nextInt(relative)+1)<=probability;
	}
	/**
	 * 获取一个区间内的随机值（默认包含最大和最小值）
	 * @param max 最大值
	 * @param min 最小值
	 * @return
	 */
	public static int getRandomNumber(int max,int min){
		Random random = new Random();
		return random.nextInt(max+1-min)+min;
	}
	/**
	 * 获取一个区间的随机数（包含最大和最小值）
	 * @param max 最大值 
	 * @param min 最小值
	 * @param decimal 保留小数点后几位
	 * @return 随机数
	 */
	public static double getRandomNumBer(double max, double min, int decimal){
		if(max < min){
			return 0d;
		}
		Random random = new Random();
		int factor = 10 * decimal;
		int maxInt = (int)(max * factor);
		int minInt = (int)(min * factor);
		return (random.nextInt(maxInt+1-minInt)+minInt)*1.0/factor;
	}
	
	
	/**
	 * 将字符串拆分成数组
	 * @param source 源字符串
	 * @param ignoreBlank 是否忽略空格
	 * @return
	 */
	public static char[] splitString(String source, boolean ignoreBlank){
		if(StringUtils.isBlank(source)){
			return null;
		}
		if(ignoreBlank){
			return source.replace(" ", "").toCharArray();
		}else{
			return source.toCharArray();
		}
	}
	/**
	 * 将数组根据指定拼接符拼接成字符串
	 * @param source 源数组
	 * @param separator 分隔符
	 * @return
	 */
	public static String joint(String[] source, char separator){
		if(source == null || source.length <= 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < source.length-1; i++) {
			sb.append(separator).append(source[i]);
		}
		sb.append(source[source.length-1]);
		return sb.toString();
	}

	/**
	 * 将数组根据指定拼接符拼接成字符串
	 * @param source 源数组
	 * @param separator 分隔符
	 * @return
	 */
	public static String joint(char[] source, char separator){
		if(source == null || source.length <= 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < source.length-1; i++) {
			sb.append(source[i]).append(separator);
		}
		sb.append(source[source.length-1]);
		return sb.toString();
	}
	/**
	 * 通过MD5加密方式加密指定的字符串
	 * @param source 需要加密的字符串
	 * @param charSet 编码格式（为null或""使用默认编码格式utf-8）
	 * @param privateKey 私钥 （为null或""使用默认私钥）
	 * @return 加密后的字符串
	 */
	public static String encryptByMD5(String source, String charSet, String privateKey) {
		if (source == null || "".equals(source)) {
			return null;
		}
		try {
			if(privateKey == null || "".equals(privateKey.trim())){
				source = source + PRIVATE_KEY;
			}else{
				source = source + privateKey;
			}
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charSet != null && !"".equals(charSet)) {
				md.update(source.getBytes(charSet));
			} else {
				md.update(source.getBytes(DEFAULTCHARSET));
			}
			byte[] encryptStr = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = encryptStr[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 通过MD5加密方式加密指定的字符串(不使用密钥)
	 * @param source 需要加密的字符串
	 * @param charSet 编码格式（为null或""使用默认编码格式utf-8）
	 * @return 加密后的字符串
	 */
	public static String encryptByMD5(String source, String charSet) {
		if (source == null || "".equals(source)) {
			return null;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			if (charSet != null && !"".equals(charSet)) {
				md.update(source.getBytes(charSet));
			} else {
				md.update(source.getBytes(DEFAULTCHARSET));
			}
			byte[] encryptStr = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = encryptStr[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
     * 获取一个32位的随机字符串
     * @return
     */
    public static String getUuid()
    {
    	UUID uuid = UUID.randomUUID();
        return uuid.toString().toUpperCase().replace("-", "");
    }
    
    /**
     * 返回一个随机时间
     * @param begin
     * @param end
     * @return
     */
    public static long random(long begin, long end) {
		long rtn = begin + (long) (Math.random() * (end - begin));
		return rtn;
	}
    /**
     * 返回指定长度的随机数
     * @param length
     * @return
     */
    public static String getFixLenthString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();

    }
    
    /**
	 * 判断指定位置的开关是否开启
	 * @param menu
	 * @param index
	 * @return
	 */
	public static boolean isON(long menu,int index){
		if(index<1){
			return false;
		}
		return (menu & (int)Math.pow(2,index-1))>0?true:false;
	}
	/**
	 * 判断对应开关位置的数值
	 * @param index 第几位开关
	 * @return
	 */
	public static int switchValue(int index){
		if(index <= 0){
			return 0;
		}
		return (int)Math.pow(2,index-1);
	}
	/**
	 * 格式化xml字符串数据
	 * @param xmlData
	 * @return
	 */
	public static Map<String,String> formatXML(String xmlData){
		Map<String,String> map = null;
        try {   
             DocumentBuilderFactory factory = DocumentBuilderFactory   
                     .newInstance();
             DocumentBuilder builder = factory.newDocumentBuilder();   
             Document doc = builder.parse(new InputSource(new StringReader(xmlData.replace("\n", ""))));
             Element root = doc.getDocumentElement();   
             NodeList books = root.getChildNodes();   
             if (books != null) {
            	 map = new HashMap<String,String>();
                 for (int i = 0; i < books.getLength(); i++) {   
                     Node book = books.item(i);
                     map.put(book.getNodeName(), book.getFirstChild().getNodeValue());   
                 }   
             }
             return map;
         } catch (Exception e) {   
             e.printStackTrace();
             return null;
         }   
     }

	/**
	 * 将整型转化为字符串
	 * @param param 
	 * @return 为空返回""
	 */
	public static String intToStr(Integer param , String defaultValue){
		if(param == null){
			return defaultValue;
		}
		return param.toString();
	}
	
	/**
	 * 将字节转化为字符串
	 * @param param
	 * @param defaultValue
	 * @return
	 */
	public static String byteToStr(Byte param , String defaultValue){
		if(param == null){
			return defaultValue;
		}
		return param.toString();
	}
	
	/**
	 * 将字符串转换为整型
	 * @param str
	 * @return
	 */
	public static Integer strToInt(String str, Integer defaultValue) {
		if (StringUtils.isBlank(str)) {
			return defaultValue;
		}
		return Integer.parseInt(str);
	}
	
	/**
	 * 将字符串转换为字节
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static Byte strToByte(String str, Byte defaultValue){
		if (StringUtils.isBlank(str)) {
			return defaultValue;
		}
		return Byte.parseByte(str);
	}
	/**
	 * 为空字符串装换为空串
	 * @param value
	 * @return
	 */
	public static String strNullToEmpty(String value){
		if(value == null){
			return "";
		}else{
			return value;
		}
	}
	/**
	 * 拆分红包奖金池
	 * @param totalAmount 当前奖金池
	 * @param totalNum 当前数量
	 * @param min 最小金额
	 * @param times 平均值倍数
	 * @return
	 */
	public static Integer splitRedpackPool(int totalAmount, int totalNum , int min, int times){
		if(totalAmount <= 0 || totalNum <= 0){
			return 0;
		}
		if(totalNum * min > totalAmount){
			return 0;
		}
		if(totalNum == 1){
			return totalAmount;
		}
		int awardPool = totalAmount - totalNum * min;
		if(awardPool == 0){
			return min;
		}
		/**理论平均值**/
		int average = totalAmount/totalNum;
		/**红包理论最大值**/
		int max = average * times;
		if(max > awardPool){
			max = awardPool;
		}
		Random random = new Random();
		int randomValue = 0;
		if(random.nextInt(1) > 0){
			randomValue = min + random.nextInt(average - min);
		}else{
			if(max - average > 0){
				randomValue = min + random.nextInt(max - average);
			}
		}
		return randomValue;
	}
	
	/**
	 * 识别字符串中的全角和半角
	 * @param chars 字符串
	 * @param type 0：半角，1：全角
	 * @return
	 */
	public static int discernChar(String chars, int type){
		if(null == chars || "".equals(chars)){
			return 0;
		}
        char[] _chars = chars.toCharArray();
        int _b = 0;
        int _q = 0;
        for (int i = 0; i < _chars.length; i++) {
            String temp = String.valueOf(_chars[i]);
            // 判断是全角字符
            if (temp.matches("[^\\x00-\\xff]")) {
            	_q++;
            }
            // 判断是半角字符
            else {
            	_b++;
            }
        }
        if(0 == type){
        	return _b;
        }else if(1 == type){
        	return _q;
        }else{
        	return 0;
        }
         
	}
	/**
	 * 获取秒级时间戳
	 * @return
	 */
	public static int getSecondTimestamp(){  
	    Date date = new Date();
	    String timestamp = String.valueOf(date.getTime());  
	    int length = timestamp.length();  
	    if (length > 3) {  
	        return Integer.valueOf(timestamp.substring(0,length-3));  
	    } else {  
	        return 0;  
	    }  
	}  
	
	/**
	 * 系统生成SN码（9位随机码+6位奖品索引）
	 * @param trophyId 奖品id
	 * @return
	 */
	public static String generateSNCode(int trophyId){
		StringBuffer SNCode = new StringBuffer(32);
		SNCode.append(getUuid().substring(0, 9));
		SNCode.append(String.format("%06d", trophyId));
		
		return SNCode.toString();
	}
	
	/**
	 * SHA1算法
	 * @param str
	 * @return
	 */
	public static String getSHA1(String str) {
		try {
			// SHA1签名生成
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			md.update(str.getBytes());
			byte[] digest = md.digest();

			StringBuffer hexstr = new StringBuffer();
			String shaHex = "";
			for (int i = 0; i < digest.length; i++) {
				shaHex = Integer.toHexString(digest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexstr.append(0);
				}
				hexstr.append(shaHex);
			}
			return hexstr.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 校验当前环境是否是windows环境
	 * @return
	 */
	public static boolean isWindows() {
		Properties properties = System.getProperties();
		String os = properties.getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("windows")>-1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 随机从某个数字里去除num位不重复的数
	 * @param num
	 * @param start
	 * @param end
	 * @return
	 */
	public static int[] randomNum(int num, int start, int end) {
		if (num > (end - start)) {
			num = end - start;
		}
		int[] array = new int[num];
		for (int i = 0; i < num; i++) {
			array[i] = (int) (Math.random() * (end - start) + start);
			for (int j = 0; j < i; j++) {
				if (array[i] == array[j]) {
					i = i - 1;
					break;
				}
			}
		}
		return array;
	}
	
}
