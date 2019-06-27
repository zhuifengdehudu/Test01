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
	 * ��ȡ�ض�XML�ڵ���ȫ������
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

		
		// ���xml����
		Map map = new HashMap();
		// ��������ռ�
		String rootNsURI = doc.getRootElement().getNamespaceURI();
		System.out.println("rootNsURI:" + rootNsURI);
		String nsURI = "http://vo.web.userauth.bospub.abchina.com";
		map.put("xmlns", nsURI);
		// ��������·������������ͨ�Ľ���·��ǰ����map���keyֵ
		XPath x = doc.createXPath("//xmlns:commonVo");
		x.setNamespaceURIs(map);
		// �������õ������
		List<Node> nodes = x.selectNodes(doc);
		
//		List nodes = doc.selectNodes("//transFrom");
		for (int i = 0; i < nodes.size(); i++) {
			Element elem = (Element) nodes.get(i);
			System.out.println(elem.asXML());
		}
	}

	/**
	 * ��ָ���ڵ㿪ʼ,�ݹ���������ӽڵ�
	 * 
	 * @throws Exception
	 */
	@Test
	public void xmlTest02() throws Exception {

		SAXReader reader = new SAXReader();
		Document doc = reader.read(new File("C:\\Users\\yuming\\Desktop\\myTest\\01.xml"));

		Element root = doc.getRootElement();// ��ȡ���ڵ�
		this.getNodes(root);// �Ӹ��ڵ㿪ʼ�������нڵ�
	}

	/**
	 * ��ָ���ڵ㿪ʼ,�ݹ���������ӽڵ�
	 */
	@SuppressWarnings("unchecked")
	public void getNodes(Element node) {
		System.out.println("--------------------");

		// ��ǰ�ڵ�����ơ��ı����ݺ�����
		System.out.println("��ǰ�ڵ����ƣ�" + node.getName());// ��ǰ�ڵ�����
		System.out.println("��ǰ�ڵ����ݣ�" + node.getTextTrim());// ��ǰ�ڵ�����
		List<Attribute> listAttr = node.attributes();// ��ǰ�ڵ���������Ե�list
		for (Attribute attr : listAttr) {// ������ǰ�ڵ����������
			String name = attr.getName();// ��������
			String value = attr.getValue();// ���Ե�ֵ
			System.out.println("�������ƣ�" + name + "����ֵ��" + value);
		}

		// �ݹ������ǰ�ڵ����е��ӽڵ�
		List<Element> listElement = node.elements();// ����һ���ӽڵ��list
		for (Element e : listElement) {// ��������һ���ӽڵ�
			this.getNodes(e);// �ݹ�
		}
	}

	/**
	 * XMLתΪJSON
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
	 * ��XMLתΪJSON��ʽ
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
	 * ����JSONתXML
	 * @throws Exception
	 */
	@Test
	public void xmlTest05() throws Exception {
		JSONObject rootObject = new JSONObject();

		JSONArray dataArray = new JSONArray();
		JSONObject dataObject1 = new JSONObject();
		dataObject1.put("PERSONID", "35020500200610000000000701355117");
		dataObject1.put("XM", "����");
		dataObject1.put("SFZH", "350624198908052530");
		dataArray.add(dataObject1);
		JSONObject dataObject2 = new JSONObject();
		dataObject2.put("PERSONID", "35020500200610000000000701355116");
		dataObject2.put("XM", "����2");
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
	 * XMLתΪJSON
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