package com.abchina.test01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

public class XmlTest02 {

	@Test
	public void xmlTest01() throws Exception {
		// ����SAXReader����
		SAXReader reader = new SAXReader();
		// ��ȡ�ļ� ת����Document
		Document document = reader.read(new File("C:\\Users\\yuming\\Desktop\\myTest\\s.xml"));
		// documentת��ΪString�ַ���
		String documentStr = document.asXML();
		System.out.println("document �ַ�����" + documentStr);
		// ��ȡ���ڵ�
		Element root = document.getRootElement();
		// ���ڵ�ת��ΪString�ַ���
		String rootStr = root.asXML();
		System.out.println("root �ַ�����" + rootStr);
		// ��ȡ����student1�ڵ�
		Element student1Node = root.element("student1");
		// student1�ڵ�ת��ΪString�ַ���
		String student1Str = student1Node.asXML();
		System.out.println("student1Str �ַ�����" + student1Str);
	}

	/**
	 * ����TXT�ļ�
	 */
	@Test
	public void readFile() {
		String pathname = "input.txt"; // ����·�������·�������ԣ�д���ļ�ʱ��ʾ���·��,��ȡ����·����input.txt�ļ�
		// ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw;
		// ���ر��ļ��ᵼ����Դ��й¶����д�ļ���ͬ��
		// Java7��try-with-resources�������Źر��ļ����쳣ʱ�Զ��ر��ļ�����ϸ���https://stackoverflow.com/a/12665271
		// ����һ�����������ļ�����ת�ɼ�����ܶ���������
		try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader)) {
			String line;
			// �����Ƽ����Ӽ���д��
			while ((line = br.readLine()) != null) {
				// һ�ζ���һ������
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * д��TXT�ļ�
	 */
	@Test
	public void writeFile() {
		try {
			File writeName = new File("output.txt"); // ���·�������û����Ҫ����һ���µ�output.txt�ļ�
			writeName.createNewFile(); // �������ļ�,��ͬ�����ļ��Ļ�ֱ�Ӹ���
			try (FileWriter writer = new FileWriter(writeName); BufferedWriter out = new BufferedWriter(writer)) {
				out.write("�һ�д���ļ���1\r\n"); // \r\n��Ϊ����
				out.write("�һ�д���ļ���2\r\n"); // \r\n��Ϊ����
				out.flush(); // �ѻ���������ѹ���ļ�
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
