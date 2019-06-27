package com.abchina.test01;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cn.study.Student;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json-lib,实现JSON和java对象之间的互转
 */
public class convertObject {
	
	@Test
	public void convertObject1() throws Exception {
		
		Student stu = new Student();
		stu.setName("JSON");
		stu.setAge("23");
		stu.setAddress("北京市西城区");
		
		Student stu2 = new Student();
		stu2.setName("JSON2");
		stu2.setAge("232");
		stu2.setAddress("北京市西城区2");
		
		List<Student> stuList = new ArrayList<Student>();
		stuList.add(stu);
		stuList.add(stu2);
		
		// 1、使用JSONObject
		JSONObject json = JSONObject.fromObject(stu);
		// 2、使用JSONArray
		JSONArray array = JSONArray.fromObject(stuList);

		String strJson = json.toString();
		String strArray = array.toString();

		System.out.println("strJson:" + strJson);
		System.out.println("strArray:" + strArray);
	}
}
