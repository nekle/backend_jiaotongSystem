package com.bjtu.websystem.common.utils.createDir;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 根据路径创建文件夹
 * @date 2021/4/7 19:39
 */

import java.io.File;

public class DirectoryUtil {

	private static String WIN_SEPARATOR = new String("\\");
	private static String LINUX_SEPARATOR = new String("/");

	public boolean createParentDir(String path) throws Exception {
		String systemSeparator = File.separator;
		if (systemSeparator.equals(WIN_SEPARATOR)) {
			return createParentDirWIN(path);
		} else if (systemSeparator.equals(LINUX_SEPARATOR)) {
			return createParentDirLinux(path);
		}
		return false;
	}

	/**
	 * @description: windows环境下
	 * @author Nekkl
	 * @date: 2021/4/7 19:40
	 * @param: [path]
	 * @return: boolean result
	 */
	public boolean createParentDirWIN(String path) throws Exception {

		//Split中特殊字符分割： http://blog.csdn.net/myfmyfmyfmyf/article/details/37592711
		// \ 用 “\\\\”
		String[] pathArr = path.split("\\\\");
		System.out.println("length : " + pathArr.length);
		boolean result = false;
		StringBuffer tmpPath = new StringBuffer();

		for (int i = 0; i < pathArr.length; i++) {
			tmpPath.append(pathArr[i]).append(WIN_SEPARATOR);

			if (0 == i) {
				continue;
			}

			File file = new File(tmpPath.toString());

			if (!file.exists()) {

				result = file.mkdir();

			} else {
				result = false;
			}


		}

		return result;

	}


	//Linux
	public boolean createParentDirLinux(String path) throws Exception {

		String[] pathArr = path.split(LINUX_SEPARATOR);
		boolean result = false;
		StringBuffer tmpPath = new StringBuffer();

		for (int i = 0; i < pathArr.length; i++) {
			tmpPath.append(pathArr[i]).append(LINUX_SEPARATOR);

			File file = new File(tmpPath.toString());
			if (!file.exists()) {
				result = file.mkdir();

			} else {
				result = false;
			}
		}

		return result;

	}

	public static void main(String[] args) {
		DirectoryUtil directoryUtil = new DirectoryUtil();
		try {
			directoryUtil.createParentDir("C:\\Users\\Nekkl\\Desktop\\test_abc\\0_net_data_1\\1_GRD_data");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}