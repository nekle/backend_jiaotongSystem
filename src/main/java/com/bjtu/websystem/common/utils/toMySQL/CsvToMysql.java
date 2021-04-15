package com.bjtu.websystem.common.utils.toMySQL;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bjtu.websystem.common.utils.cordConvertionUtils.GPS;
import com.bjtu.websystem.common.utils.cordConvertionUtils.GPSConverterUtils;
import com.csvreader.CsvReader;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 读取CSV文件里的数据，输入MySQL数据库
 * @date 2021/4/13 15:57
 */
public class CsvToMysql {

	/**
	 * @description: 读取csv 到 MySQL数据库
	 * @author Nekkl
	 * @date: 2021/4/13 15:59
	 * @param: [fileName] 文件完整路径
	 * @return: void
	 */
	public void csvToMysql(String filePath, String names, int length, String tableName, boolean convert) throws IOException, SQLException {

		// 配置连接MySQL数据库
		Connection conn = DriverManager.getConnection("jdbc:mySql://localhost:3306/jiaotongdata?serverTimezone=GMT%2B8", "root", "123456");

		// SQL语句声明
		Statement statement = conn.createStatement();
		// 构造CsvReader
		CsvReader reader = new CsvReader(filePath, ',', Charset.forName("gbk"));

//        // 读取头，因为文件第一行是无意义的数字，所以读取后不使用
//        reader.readHeaders();
//        // len表示的是有几个列, cross.csv 有length列
//        int len=reader.getHeaders().length;

		//整个while就是为了组装成为 插入语句的形式，读一条，插入一条
		// 若不需要进行坐标转换
		if (!convert) {

			while (reader.readRecord()) {

				//tmp就是组装好的插入语句，即insert into tableName(属性）values（内容）；
				String tmp = "insert into " + tableName + "(" + names + ")values(" + reader.get(0).replaceAll("锘�", "");
				for (int i = 1; i < length - 1; i++) {
					tmp += "," + "'" + reader.get(i).replaceAll("'", "\\\\'") + "'";
				}
				tmp += "," + "'" + reader.get(length - 1).replaceAll("'", "\\\\'") + "');";

				//执行插入sql语句
				statement.execute(tmp);

			}

		} else {

			// 若需要进行坐标转换
			while (reader.readRecord()) {
				//tmp就是组装好的插入语句，即insert into tableName(属性）values（内容）；
				String tmp = "insert into " + tableName + "(" + names + ")values(" + reader.get(0).replaceAll("锘�", "");
				tmp += "," + "'" + reader.get(1).replaceAll("'", "\\\\'") + "'";

				// 进行经纬度转换
				// 获取初始值
				Double longitude = Double.parseDouble(reader.get(2).replaceAll("'", "\\\\'"));
				Double latitude = Double.parseDouble(reader.get(3).replaceAll("'", "\\\\'"));
				// 坐标转换
				GPS myGPS = GPSConverterUtils.gps84_To_Gcj02(latitude, longitude);
				// 转换后的坐标插入sql语句
				tmp += "," + "'" + myGPS.getLon() + "'";
				tmp += "," + "'" + myGPS.getLat() + "'";

				// 读取其他数据
				for (int i = 4; i < length - 1; i++) {
					tmp += "," + "'" + reader.get(i).replaceAll("'", "\\\\'") + "'";
				}
				tmp += "," + "'" + reader.get(length - 1).replaceAll("'", "\\\\'") + "');";

				//执行插入sql语句
				statement.execute(tmp);

			}

		}


		// 关闭阅读器流， sql语句， MySQL数据库连接
		reader.close();
		statement.close();
		conn.close();

	}

	public void myCsvToMysql(String filePath, int length, BaseMapper mapper) throws IOException {

		// 构造CsvReader
		CsvReader reader = new CsvReader(filePath, ',', Charset.forName("utf-8"));

		//整个while就是为了组装成为 插入语句的形式，读一条，插入一条
		while (reader.readRecord()) {


		}
	}


}
