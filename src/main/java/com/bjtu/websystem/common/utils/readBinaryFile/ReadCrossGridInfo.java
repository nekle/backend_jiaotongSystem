package com.bjtu.websystem.common.utils.readBinaryFile;


import com.bjtu.websystem.common.utils.cordConvertionUtils.GPS;
import com.bjtu.websystem.common.utils.cordConvertionUtils.GPS2Three;
import com.bjtu.websystem.model.readFileModels.CrossGrid;
import com.csvreader.CsvReader;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/7 10:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadCrossGridInfo extends Thread {
	private String filePath;
	private GPS2Three converter;
	private CrossGrid[] crossGrids;
	private CountDownLatch countDownLatch;
	@Override
	public void run() {

		try {
			// 获取记录条数（行数）
			BufferedReader br = new BufferedReader(new FileReader(this.filePath));
			String tmpStr = "";
			int rowCount = 0;
			while (br.readLine() != null) {
				rowCount++;
			}
			this.crossGrids = new CrossGrid[rowCount];
			int j = 0;
			//CSV文件阅读器
			CsvReader reader = new CsvReader(this.filePath, ',', Charset.forName("gbk"));
			GPS gps = new GPS(0, 0);

			// 整个while就是为了组装成为 插入语句的形式
			while (reader.readRecord()) {

				// 读取坐标
				double longitude = Double.valueOf(reader.get(1));
				double latitude = Double.valueOf(reader.get(2));

				// 坐标转换成Three.js坐标
				double[] lonLat = this.converter.GPS2Pixel(longitude, latitude);

				// 生成一个坐标对象
				this.crossGrids[j++] = new CrossGrid(
						(int) Float.parseFloat(reader.get(0)),
						lonLat[0],
						lonLat[1]
				);
			}
			System.out.println("读取cross grid完成");
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.countDownLatch.countDown();
	}

}
