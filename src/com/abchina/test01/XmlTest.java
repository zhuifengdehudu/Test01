package com.abchina.test01;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.junit.Test;

import com.abchina.utils.XmlUtils;
import com.abchina.utils.XmlUtilsNew;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author yuming
 */
public class XmlTest {

	/**
	 * 获取特定XML节点下全部内容
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	@Test
	public void xmlTest01() throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("C:\\Users\\yuming\\Desktop\\myTest\\01.xml"));

		List nodes = doc.selectNodes("//transFrom");
		for (int i = 0; i < nodes.size(); i++) {
			Element elem = (Element) nodes.get(i);
			System.out.println(elem.asXML());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Test
	public void xmlTest001() throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("C:\\Users\\yuming\\Desktop\\myTest\\01.xml"));

		
		// 获得xml对象
		Map map = new HashMap();
		// 获得命名空间
		String rootNsURI = doc.getRootElement().getNamespaceURI();
		System.out.println("rootNsURI:" + rootNsURI);
		String nsURI = "http://vo.web.userauth.bospub.abchina.com";
		map.put("xmlns", nsURI);
		// 创建解析路径，就是在普通的解析路径前加上map里的key值
		XPath x = doc.createXPath("//xmlns:commonVo");
		x.setNamespaceURIs(map);
		// 这样就拿到结果了
		List<Node> nodes = x.selectNodes(doc);
		
//		List nodes = doc.selectNodes("//transFrom");
		for (int i = 0; i < nodes.size(); i++) {
			Element elem = (Element) nodes.get(i);
			System.out.println(elem.asXML());
		}
	}

	/**
	 * 从指定节点开始,递归遍历所有子节点
	 * 
	 * @throws Exception
	 */
	@Test
	public void xmlTest02() throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("C:\\Users\\yuming\\Desktop\\myTest\\01.xml"));

		Element root = doc.getRootElement();// 获取根节点
		this.getNodes(root);// 从根节点开始遍历所有节点
	}

	/**
	 * 从指定节点开始,递归遍历所有子节点
	 */
	@SuppressWarnings("unchecked")
	public void getNodes(Element node) {
		System.out.println("--------------------");

		// 当前节点的名称、文本内容和属性
		System.out.println("当前节点名称：" + node.getName());// 当前节点名称
		System.out.println("当前节点内容：" + node.getTextTrim());// 当前节点名称
		List<Attribute> listAttr = node.attributes();// 当前节点的所有属性的list
		for (Attribute attr : listAttr) {// 遍历当前节点的所有属性
			String name = attr.getName();// 属性名称
			String value = attr.getValue();// 属性的值
			System.out.println("属性名称：" + name + "属性值：" + value);
		}

		// 递归遍历当前节点所有的子节点
		List<Element> listElement = node.elements();// 所有一级子节点的list
		for (Element e : listElement) {// 遍历所有一级子节点
			this.getNodes(e);// 递归
		}
	}

	/**
	 * XML转为JSON
	 * 
	 * @throws Exception
	 */
	@Test
	public void xmlTest03() throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("C:\\Users\\yuming\\Desktop\\01.xml"));

		String asXML = doc.asXML();
		System.out.println("asXML:");
		System.out.println(asXML);
		String xmlToJSON = XmlUtils.xmlToJSON(asXML);
		System.out.println("xmlToJSON:");
		System.out.println(xmlToJSON);
		
//		org.json.JSONObject rootObject = new org.json.JSONObject(xmlToJSON);
//		System.out.println("rootObject:");
//		System.out.println(rootObject);
//		String jsonToXml = XmlUtils.JsonToXml(rootObject);
//		System.out.println("jsonToXml:");
//		System.out.println(jsonToXml);
	}

	/**
	 * 把XML转为JSON格式
	 * 
	 * @throws Exception
	 */
	@Test
	public void xmlTest04() throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("C:\\Users\\yuming\\Desktop\\01.xml"));

		String asXML = doc.asXML();
		System.out.println("asXML:");
		System.out.println(asXML);
		JSONObject xml2json = XmlUtils.xml2JSON(asXML.getBytes("utf-8"));
		String xmlToJSON = xml2json.toJSONString();
		System.out.println("xmlToJSON:");
		System.out.println(xmlToJSON);

	}

	/**
	 * 测试JSON转XML
	 * @throws Exception
	 */
	@Test
	public void xmlTest05() throws Exception {
		JSONObject rootObject = new JSONObject();

		JSONArray dataArray = new JSONArray();
		JSONObject dataObject1 = new JSONObject();
		dataObject1.put("PERSONID", "35020500200610000000000701355117");
		dataObject1.put("XM", "吴聪楠");
		dataObject1.put("SFZH", "350624198908052530");
		dataArray.add(dataObject1);
		JSONObject dataObject2 = new JSONObject();
		dataObject2.put("PERSONID", "35020500200610000000000701355116");
		dataObject2.put("XM", "吴聪楠2");
		dataObject2.put("SFZH", "350624198908052531");
		dataArray.add(dataObject2);

		JSONObject dataRootObject = new JSONObject();
		dataRootObject.put("Row", dataArray);
		JSONObject dataObject = new JSONObject();
		dataObject.put("Status", "00");
		dataObject.put("ErrorMsg", "");
		dataObject.put("Data", dataRootObject);
		rootObject.put("ROOT", dataObject);

		System.out.println(rootObject.toString());
		System.out.println(XmlUtils.JsonToXml(rootObject));

	}
	
	/**
	 * XML转为JSON
	 * 
	 * @throws Exception
	 */
	@Test
	public void xmlTest06() throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("C:\\Users\\yuming\\Desktop\\myTest\\01.xml"));

		String asXML = doc.asXML();
		System.out.println("asXML:");
		System.out.println(asXML);
		
		String xmlUserFulStr = XmlUtils.xml2UserFulString(asXML.getBytes("utf-8"));
		System.out.println("xmlUserFulStr:");
		System.out.println(xmlUserFulStr);
		
		String xmlToJson = XmlUtilsNew.xmlToJson(xmlUserFulStr);
		System.out.println("xmlToJson:");
		System.out.println(xmlToJson);
		
	}
}