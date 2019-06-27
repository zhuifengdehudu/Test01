package com.abchina.test01;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.cn.study.Student;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * json-lib,ʵ��JSON��java����֮��Ļ�ת
 */
public class convertObject {
	
	@Test
	public void convertObject1() throws Exception {
		
		Student stu = new Student();
		stu.setName("JSON");
		stu.setAge("23");
		stu.setAddress("������������");
		
		Student stu2 = new Student();
		stu2.setName("JSON2");
		stu2.setAge("232");
		stu2.setAddress("������������2");
		
		List<Student> stuList = new ArrayList<Student>();
		stuList.add(stu);
		stuList.add(stu2);
		
		// 1��ʹ��JSONObject
		JSONObject json = JSONObject.fromObject(stu);
		// 2��ʹ��JSONArray
		JSONArray array = JSONArray.fromObject(stuList);

		String strJson = json.toString();
		String strArray = array.toString();

		System.out.println("strJson:" + strJson);
		System.out.println("strArray:" + strArray);
	}
}
