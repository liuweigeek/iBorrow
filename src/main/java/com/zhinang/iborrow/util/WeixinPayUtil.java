package com.zhinang.iborrow.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.zhinang.iborrow.entity.WeixinPrePayData;

public class WeixinPayUtil {

	public static WeixinPrePayData sentPost(Map<String, Object> map, String key) {

		URL url = null;
		HttpURLConnection connection = null;
		BufferedReader breader = null;
		StringBuffer strb = new StringBuffer();

		try {
			url = new URL("https://api.mch.weixin.qq.com/pay/unifiedorder");
			connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();

			strb = new StringBuffer();
			OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
			writer.write(getXML(map, key));
			writer.flush();
			writer.close();

			breader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
			String str;
			while ((str = breader.readLine()) != null) {
				strb.append(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			breader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		connection.disconnect();
		/*System.out.println("strb is " + strb.toString());*/
		return new WeixinPrePayData(strb.toString());
	}

	/**
	 * 32位随机字符
	 * 
	 * @return
	 */
	public static String getRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 32; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	public static String getXML(Map<String, Object> map, String key) throws Exception {

		Iterator<String> iterator = map.keySet().iterator();
		StringBuffer strb = new StringBuffer();
		StringBuffer _strb = new StringBuffer();
		while (iterator.hasNext()) {
			String _key = iterator.next().toString();
			String _val = map.get(_key).toString();
			_strb.append("<" + _key + ">" + _val + "</" + _key + ">");
		}

		strb.append(" <xml> ");
		strb.append("<sign>" + sign(map, key) + "</sign>");
		strb.append(_strb);
		strb.append(" </xml> ");

		return strb.toString();

	}

	/**
	 * 签名算法sign
	 * 
	 * @param map
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String sign(Map<String, Object> map, String key) {

		Set<String> set = map.keySet();
		Iterator<String> iterator = set.iterator();
		List<String> list = new ArrayList<String>();
		while (iterator.hasNext()) {
			String _key = iterator.next().toString();
			if (("".equals(map.get(_key))) || (null == map.get(_key))) {
				continue;
			}
			if ("sign".equals(_key)) {
				continue;
			}
			list.add(_key);
		}

		Object[] objects = list.toArray();
		Arrays.sort(objects);

		StringBuffer strb = new StringBuffer();
		for (int i = 0; i < objects.length; i++) {
			strb.append(objects[i] + "=" + map.get(objects[i]) + "&");
		}

		strb.append("key=" + key);

		return md5(strb.toString());
	}

	public static String md5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(str.getBytes("UTF-8"));
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < bytes.length; i++) {
				int val = ((int) bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String extract(String str, String key) {
		/*List<String> valList = new ArrayList<String>();*/
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			StringReader sr = new StringReader(str);
			InputSource is = new InputSource(sr);
			Document document = db.parse(is);
			Element root = document.getDocumentElement();
			return root.getElementsByTagName(key).item(0).getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
