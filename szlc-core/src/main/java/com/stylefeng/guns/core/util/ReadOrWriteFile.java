package com.stylefeng.guns.core.util;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * 文件读写工具类
 */
@Component
public class ReadOrWriteFile {

	/**
	 * 文件写入
	 * @param folderPath 文件目录路径
	 * @param filename 文件名（含后缀）
	 * @param data 文件内容
	 * @return
	 */
	public static String write(String folderPath, String fileName, String data) {
		// 创建文件夹
		File dirPath = new File(folderPath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		try {
			//文件全路径(含后缀)
			File file = new File(folderPath + fileName);
			
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			BufferedWriter bufferWritter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "GBK"));

			// 如果是html文件加入编码、自适应代码
			if(fileName != null && fileName.indexOf(".html") != -1){
				// 编码代码
				String h5Code = "<meta charset=\"GBK\"/>";
				if(data!= null && data.indexOf(h5Code) == -1) data = h5Code + data;

				// 自适应代码
				h5Code = "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"/>";
				if(data!= null && data.indexOf(h5Code) == -1) data = h5Code + data;
			}

			bufferWritter.write(data);
			bufferWritter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	/**
	 * 读取文件
	 * @param folderPath 文件目录路径
	 * @param filename 文件名（含后缀）
	 * @return
	 */
	public static String read(String folderPath, String filename) {
		//如果包含文件夹头，则忽略不加
		if (folderPath.contains("resource/upload/")) {
			folderPath = "";
		}
		
		//读取的文件
		File file = new File(folderPath + filename);
		
		try {
			//处理找不到文件机制
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (Exception e) {
				return "";
			}
			
			//返回文件内容
			StringBuffer sb = new StringBuffer();
			
			// 一次读一个字符
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "GBK"));
			int tempchar;
			while ((tempchar = reader.read()) != -1) {
				// 对于windows下，\r\n这两个字符在一起时，表示一个换行。
				// 但如果这两个字符分开显示时，会换两次行。
				// 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
				if (((char) tempchar) != '\r') {
					// System.out.print((char) tempchar);
					sb.append((char) tempchar);
				}
			}
			reader.close();
			
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }

}
