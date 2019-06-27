package com.abchina.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class XmlUtils {

	/**
	 * XML转JSON（有命名空间时，转换会丢失部分内容）
	 * 
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String xmlToJSON(String xml) {
		JSONObject obj = new JSONObject();
		try {
			InputStream is = new ByteArrayInputStream(xml.getBytes("utf-8"));
			SAXBuilder sb = new SAXBuilder();
			Document doc = sb.build(is);
			Element root = doc.getRootElement();
			// 递归root,直到找到out下的子节点
			Element e = getUserfulRoot(root);

			Map map = iterateElement(e);
			obj.put(e.getName(), map);
			return obj.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private static Element getUserfulRoot(Element root) {
		List childrenList1 = root.getChildren();
		Element element1 = (Element) childrenList1.get(0);
		List childrenList2 = element1.getChildren();
		Element element2 = (Element) childrenList2.get(0);
		List childrenList3 = element2.getChildren();
		Element element3 = (Element) childrenList3.get(0);
		// List childrenList4 = element3.getChildren();
		// Element element4 = (Element) childrenList4.get(0);
		return element3;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map iterateElement(Element root) {
		List childrenList = root.getChildren();
		Element element = null;
		Map map = new HashMap();
		List list = null;
		for (int i = 0; i < childrenList.size(); i++) {
			list = new ArrayList();
			element = (Element) childrenList.get(i);
			if (element.getChildren().size() > 0) {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(iterateElement(element));
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), iterateElement(element));
				}
			} else {
				if (root.getChildren(element.getName()).size() > 1) {
					if (map.containsKey(element.getName())) {
						list = (List) map.get(element.getName());
					}
					list.add(element.getTextTrim());
					map.put(element.getName(), list);
				} else {
					map.put(element.getName(), element.getTextTrim());
				}
			}
		}
		return map;
	}

	/**
	 * XML转JSON（不丢失内容，但是会多“[]”符号）
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static JSONObject xml2JSON(byte[] xml) throws Exception {
		JSONObject json = new JSONObject();
		InputStream is = new ByteArrayInputStream(xml);
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(is);
		Element root = doc.getRootElement();
		// 递归root,直到找到out下的子节点
		Element e = getUserfulRoot(root);
		XMLOutputter xmlOut = new XMLOutputter();
		String res = xmlOut.outputString(e);
		System.out.println("res" + res);

		json.put(e.getName(), iterateElement2(e));
		return json;
	}

	/**
	 * 字符串格式的XML去除多余节点，再返回字符串格式XML JDOM实现
	 * 
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static String xml2UserFulString(byte[] xml) throws Exception {
		InputStream is = new ByteArrayInputStream(xml);
		SAXBuilder sb = new SAXBuilder();
		Document doc = sb.build(is);
		Element root = doc.getRootElement();
		// 递归root,直到找到out下的子节点
		Element e = getUserfulRoot(root);
		// 转回String
		XMLOutputter xmlOut = new XMLOutputter();
		String res = xmlOut.outputString(e);
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map iterateElement2(Element element) {
		List node = element.getChildren();
		Element et = null;
		Map map = new HashMap();
		List list = null;
		for (int i = 0; i < node.size(); i++) {
			list = new ArrayList();
			et = (Element) node.get(i);
			if (et.getTextTrim().equals("")) {
				if (et.getChildren().size() == 0)
					continue;
				if (map.containsKey(et.getName())) {
					list = (List) map.get(et.getName());
				}
				list.add(iterateElement2(et));
				map.put(et.getName(), list);
			} else {
				if (map.containsKey(et.getName())) {
					list = (List) map.get(et.getName());
				}
				map.put(et.getName(), et.getTextTrim());
				// list.add(et.getTextTrim());
				// map.put(et.getName(), list);
			}
		}
		return map;
	}

	/**
	 * JSON转XML
	 * 
	 * @param json
	 * @return
	 */
	public static String JsonToXml(Object json) {
		if (json == null) {
			return null;
		} else {
			Element elements = new Element("xml");
			getXMLFromObject(json, "xml", elements);
			XMLOutputter xmlOut = new XMLOutputter();
			String res = xmlOut.outputString(elements);
			return res;
		}
	}

	private static void getXMLFromObject(Object obj, String tag, Element parent) {
		if (obj == null) {
			return;
		}
		Element child;
		String eleStr;
		Object childValue;
		if (obj instanceof JSONObject) {
			JSONObject jsonObject = (JSONObject) obj;
			for (Object temp : jsonObject.keySet()) {
				eleStr = temp.toString();
				childValue = jsonObject.get(temp);
				child = new Element(eleStr);
				if (childValue instanceof JSONArray)
					getXMLFromObject(childValue, eleStr, parent);
				else {
					parent.addContent(child);
					getXMLFromObject(childValue, eleStr, child);
				}
			}
		} else if (obj instanceof JSONArray) {
			JSONArray jsonArray = (JSONArray) obj;
			for (int i = 0; i < jsonArray.size(); i++) {
				childValue = jsonArray.get(i);
				child = new Element(tag);
				parent.addContent(child);
				getXMLFromObject(childValue, tag, child);
			}
		} else if (obj instanceof Date) {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parent.setText(sf.format((Date) obj));
		} else {
			parent.setText(obj.toString());
		}
	}
}
