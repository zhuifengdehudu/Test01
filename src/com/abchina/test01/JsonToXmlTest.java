package com.abchina.test01;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import com.abchina.utils.XmlUtils;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JsonToXmlTest {

	/**
	 * JSONתXML
	 * 
	 * @throws Exception
	 */
	@Test
	public void Test() throws Exception {
		int len = 0;
		File file = new File("C:\\Users\\yuming\\Desktop\\myTest\\test02.json");
		StringBuilder sb = new StringBuilder();
		if (file.isFile() && file.exists()) { // �ж��ļ��Ƿ����
			InputStreamReader read = new InputStreamReader(new FileInputStream(file));
			BufferedReader bufferedReader = new BufferedReader(read);
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				// System.out.println(line);
				if (len != 0) {
					sb.append("\r\n" + line);
				} else {
					sb.append(line);
				}
			}
			read.close();
		} else {
			System.out.println("�Ҳ���ָ�����ļ�");
		}
		String str = sb.toString();
		System.out.println("str:");
		System.out.println(str);
		// toXml(str);
		analyJson(str);
	}

	/**
	 * JSON�ַ���תXML
	 * 
	 * @param str
	 */
	private void toXml(String str) {
		JSONArray json = JSONObject.parseArray(str);
		String jsonToXml = XmlUtils.JsonToXml(json);
		System.out.println("xml:");
		System.out.println(jsonToXml);
		jsonToXml = jsonToXml.substring(10, jsonToXml.length() - 12);
		System.out.println("xml:ȥ�����ǩ");
		System.out.println(jsonToXml);
	}

	/**
	 * ����JSON�ַ���
	 * 
	 * @param str
	 */
	public void analyJson(String str) {
		JSONObject jsonObject = JSONObject.parseObject(str);
		Set<String> keySet = jsonObject.keySet();
		Iterator<String> it = keySet.iterator();
		while (it.hasNext()) {
			String key = it.next();
			String value = jsonObject.getString(key);
			Object object = jsonObject.get(key);
			System.out.println("key:" + key + "--value:" + value);
			if (object instanceof JSONObject) {
				analyJson(value);
			}
		}
	}
}
