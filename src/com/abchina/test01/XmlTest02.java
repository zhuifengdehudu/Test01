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
		// 创建SAXReader对象
		SAXReader reader = new SAXReader();
		// 读取文件 转换成Document
		Document document = reader.read(new File("C:\\Users\\yuming\\Desktop\\myTest\\s.xml"));
		// document转换为String字符串
		String documentStr = document.asXML();
		System.out.println("document 字符串：" + documentStr);
		// 获取根节点
		Element root = document.getRootElement();
		// 根节点转换为String字符串
		String rootStr = root.asXML();
		System.out.println("root 字符串：" + rootStr);
		// 获取其中student1节点
		Element student1Node = root.element("student1");
		// student1节点转换为String字符串
		String student1Str = student1Node.asXML();
		System.out.println("student1Str 字符串：" + student1Str);
	}

	/**
	 * 读入TXT文件
	 */
	@Test
	public void readFile() {
		String pathname = "input.txt"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
		// 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
		// 不关闭文件会导致资源的泄露，读写文件都同理
		// Java7的try-with-resources可以优雅关闭文件，异常时自动关闭文件；详细解读https://stackoverflow.com/a/12665271
		// 建立一个对象，它把文件内容转成计算机能读懂的语言
		try (FileReader reader = new FileReader(pathname); BufferedReader br = new BufferedReader(reader)) {
			String line;
			// 网友推荐更加简洁的写法
			while ((line = br.readLine()) != null) {
				// 一次读入一行数据
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 写入TXT文件
	 */
	@Test
	public void writeFile() {
		try {
			File writeName = new File("output.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
			writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
			try (FileWriter writer = new FileWriter(writeName); BufferedWriter out = new BufferedWriter(writer)) {
				out.write("我会写入文件啦1\r\n"); // \r\n即为换行
				out.write("我会写入文件啦2\r\n"); // \r\n即为换行
				out.flush(); // 把缓存区内容压入文件
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
