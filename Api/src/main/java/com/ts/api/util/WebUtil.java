package com.ts.api.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class WebUtil {
	/**
	 * @method setSession(String key, Object value)
	 * @Description 设置session map类型
	 * @param key   键
	 * @param value 值
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static void setSession(String key, Object value) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.getSession().setAttribute(key, value);
	}

	/**
	 * @method getSession(String key)
	 * @Description 通过键获取session
	 * @param key 键
	 * @return
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static Object getSession(String key) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getSession().getAttribute(key);
	}

	/**
	 * @method setRequest(String key, Object value)
	 * @Description 向session中保存数据 可在页面中获取
	 * @param key   键
	 * @param value 值
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static void setRequest(String key, Object value) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.setAttribute(key, value);
	}

	/**
	 * @method removeSession(String key)
	 * @Description 通过键来移除session中的值
	 * @param key
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static void removeSession(String key) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		request.getSession().removeAttribute(key);
	}

	/**
	 * @method getContetPath()
	 * @Description 获取工程名称
	 * @return request.getContextPath()
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String getContetPath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getContextPath();// 获取工程名称
	}

	/**
	 * @method getRealPath(String dir)
	 * @Description 获取工程目录
	 * @param dir
	 * @return
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String getRealPath(String dir) {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getServletPath();// 得到工程目录
	}

	/**
	 * @method getUrlPath()
	 * @Description 得到相对地址
	 * @return
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String getUrlPath() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		StringBuffer url = request.getRequestURL(); // 得到相对地址
		return url.delete(url.length() - request.getRequestURI().length(), url.length())
				.append(request.getServletPath()).toString();
	}

	/**
	 * @method getUri()
	 * @Description 得到uri
	 * @return
	 * @author dy
	 * @date 2020年12月21日
	 */
	public static String getUri() {
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = ((ServletRequestAttributes) ra).getRequest();
		return request.getRequestURI();
	}

	/**
	 * @method String isAdminSearchName(String searchName)
	 * @Description 判断查询名称是否正确
	 * @param searchName 查询名称
	 * @return
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String isAdminSearchName(String searchName) {
		if (!(searchName.equals("username") || searchName.equals("name") || searchName.equals("talk")
				|| searchName.equals("phone") || searchName.equals("idCard") || searchName.equals("email")
				|| searchName.equals("address") || searchName.equals("baoDanUsername") || searchName.equals("trueName")
				|| searchName.equals("isDj"))) {
			return "username";
		}
		return searchName;
	}

	/**
	 * @method urlDecode(String searchValue)
	 * @Description URL解码
	 * @param searchValue 编码后的URL地址
	 * @return searchValue
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String urlDecode(String searchValue) {
		try {
			searchValue = URLDecoder.decode(searchValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "";
		}
		return searchValue;
	}

	/**
	 * @method urlEncoding(String searchValue)
	 * @Description 对URL进行编码
	 * @param searchValue URL地址
	 * @return searchValue
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String urlEncoding(String searchValue) {
		try {
			if (searchValue != null && !searchValue.equals("")) {
				searchValue = new String(searchValue.getBytes("ISO-8859-1"), "UTF-8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			searchValue = "";
		}
		return searchValue;
	}

	/**
	 * @method randomLetter(Integer count)
	 * @Description 随机获取数字和大小写字母组成count位的字符串
	 * @param count 字符串位数
	 * @return str
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String randomLetter(Integer count) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String str = "";
		for (int i = 0; i < count; i++) {
			str += chars.charAt((int) (Math.random() * 62));
		}
		return str;
	}

	/**
	 * @method parseDate(String t)
	 * @Description 将时间字符串转换为时间"yyyy-MM-dd"
	 * @param t 时间字符串
	 * @return time
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static Date parseDate(String t) {
		if (t == null) {
			return null;
		}
		Date time = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		try {
			time = format.parse(t);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * @method parseDateHHMMSS(String date)
	 * @Description 将字符串转换为时间"yyyy-MM-dd HH:mm:ss"
	 * @param date 时间字符串
	 * @return
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static Date parseDateHHMMSS(String date) {
		if (date == null) {
			return null;
		}
		Date time = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			time = format.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	public static String formatDouble(String format, Double d) {
		DecimalFormat f = new DecimalFormat(format);
		return f.format(d);
	}

	public static String urlEncode(String str, String encoding) {
		try {
			return URLEncoder.encode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String urlDecode(String str, String encoding) {
		try {
			return URLDecoder.decode(str, encoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @method createFile(String realPath)
	 * @Description 创建文件不能创建文件夹
	 * @param realPath
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static void createFile(String realPath) {
		File file = new File(realPath);
		if (file.exists()) {
			if (file.exists()) {
				file.delete();
			}
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @method getTime(Integer lastMonth)
	 * @Description 获取距当月lastMonth个月的最初时刻和最后时刻 如：2016-9-30 00:00:00,2016-9-30
	 *              23:59:59
	 * @param lastMonth 月数
	 * @return date map类型
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static Map<String, String> getTime(Integer lastMonth) {
		Map<String, String> date = new HashMap<String, String>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -lastMonth);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()));
		cal.set(Calendar.DAY_OF_MONTH, 1);
		date.put("startTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 00:00:00");
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.DATE, -1);
		date.put("month", new SimpleDateFormat("yyyy-MM").format(cal.getTime()));
		date.put("endTime", new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime()) + " 59:59:59");
		return date;
	}

	/**
	 * @method formatDateYYYYMMDD(Date time)
	 * @Description 格式化时间为："yyyy-MM-dd"字符串
	 * @param time
	 * @return
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String formatDateYYYYMMDD(Date time) {
		if (time != null) {
			return new SimpleDateFormat("yyyy-MM-dd").format(time);
		}
		return null;
	}

	/**
	 * @method monthFirst()
	 * @Description 获取当月最初时刻 如：2016-9-30 00:00:00
	 * @return new Date()
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static Date monthFirst() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format2.parse(format.format(c.getTime()) + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * @method monthLast()
	 * @Description 获取当月最后时刻 如：2016-09-01 23:59:59
	 * @return new Date()
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static Date monthLast() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 0);
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return format2.parse(format.format(c.getTime()) + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date();
	}

	/**
	 * @method getMyIP()
	 * @Description 获取外网IP
	 * @return
	 * @throws IOException
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String getMyIP() throws IOException {
		InputStream ins = null;
		try {
			URL url = new URL("http://1212.ip138.com/ic.asp");
			URLConnection con = url.openConnection();
			ins = con.getInputStream();
			InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
			BufferedReader bReader = new BufferedReader(isReader);
			StringBuffer webContent = new StringBuffer();
			String str = null;
			while ((str = bReader.readLine()) != null) {
				webContent.append(str);
			}
			int start = webContent.indexOf("[") + 1;
			int end = webContent.indexOf("]");

			return webContent.substring(start, end);
		} finally {
			if (ins != null) {
				ins.close();
			}
		}
	}

	/**
	 * @method getMyIPLocal()
	 * @Description 获取内网IP
	 * @return
	 * @throws IOException
	 * @author deng
	 * @date 2016年9月14日
	 */
	public static String getMyIPLocal() throws IOException {
		InetAddress ia = InetAddress.getLocalHost();
		return ia.getHostAddress();
	}

	/**
	 * @method String getRandomCharAndNumr(Integer length)
	 * @Description 获取随机字母数字组合
	 * @param length 字符串长度
	 * @return
	 * @author dy
	 * @date 2017年11月10日
	 */
	public static String getRandomCharAndNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (b) { // 字符串
				// int choice = random.nextBoolean() ? 65 : 97; 取得65大写字母还是97小写字母
				boolean c = random.nextBoolean();
				if (c) {
					str += (char) (97 + random.nextInt(26));// 取得小写字母
				} else {
					str += (char) (65 + random.nextInt(26));// 取得大写字母
				}
			} else { // 数字
				str += String.valueOf(random.nextInt(10));
			}
		}
		return str;
	}

	/**
	 * 随机获取指定长度的纯数字字符串
	 * 
	 * @param length 长度
	 * @return
	 */
	public static String getRandomNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			str += String.valueOf(random.nextInt(10));
		}
		return str;
	}

}
