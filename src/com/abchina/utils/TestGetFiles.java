package com.abchina.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class TestGetFiles {

	/**
	 * ��ȡһ���ļ����µ������ļ�ȫ·��
	 * 
	 * @param path
	 * @param listFileName
	 */
	public static void getAllFileName(String path, ArrayList<String> listFileName) {
		File file = new File(path);
		File[] files = file.listFiles();
		String[] names = file.list();
		if (names != null) {
			String[] completNames = new String[names.length];
			for (int i = 0; i < names.length; i++) {
				completNames[i] = path + names[i];
			}
			listFileName.addAll(Arrays.asList(completNames));
		}
		for (File a : files) {
			if (a.isDirectory()) {// ����ļ����������ļ��У���ȡ���ļ����µ������ļ�ȫ·����
				getAllFileName(a.getAbsolutePath() + "\\", listFileName);
			}
		}
	}

	public static void main(String[] args) {
		ArrayList<String> listFileName = new ArrayList<String>();
		getAllFileName("D:\\MyDownloads\\", listFileName);
		for (String name : listFileName) {
			// if (name.contains(".txt") || name.contains(".properties")) {
			System.out.println(name);
			// }
		}
	}

}
