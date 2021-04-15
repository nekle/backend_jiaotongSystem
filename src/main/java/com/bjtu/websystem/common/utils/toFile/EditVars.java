package com.bjtu.websystem.common.utils.toFile;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bjtu.websystem.mapper.CrossMapper;
import com.bjtu.websystem.mapper.LinkMapper;
import com.bjtu.websystem.mapper.OtherSettingsMapper;
import com.bjtu.websystem.mapper.toFileMappers.BusPointMapper;
import com.bjtu.websystem.mapper.toFileMappers.CivilPointMapper;
import com.bjtu.websystem.mapper.toFileMappers.SettlePointMapper;
import com.bjtu.websystem.model.datasetModels.Cross;
import com.bjtu.websystem.model.datasetModels.Link;
import com.bjtu.websystem.model.datasetModels.OtherSettings;
import com.bjtu.websystem.model.datasetModels.ToStrings;
import com.bjtu.websystem.model.datasetModels.toFileModels.BusPoint;
import com.bjtu.websystem.model.datasetModels.toFileModels.CivilPoint;
import com.bjtu.websystem.model.datasetModels.toFileModels.SettlePoint;
import com.csvreader.CsvWriter;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 将数据库里的修改输入到文件中
 * @date 2021/4/12 20:30
 */
public class EditVars {

	/**
	*  公共车辆源
	*/
	private static final int BUS_POINT = 1;

	/***
	 * 电厂
	 */
	private static final int CIVIL_POINT1 = 2;

	/***
	 * 集合点
	 */
	private static final int CIVIL_POINT2 = 3;

	/***
	 * 安置点
	 */
	private static final int SETTLE_POINT = -1;


	/**
	 * @description: 读取数据库数据，将修改后的数据写入AllPoints.csv
	 * @author Nekkl
	 * @date: 2021/4/12 21:02
	 * @param: [fileName, crossMapper] 文件名， mapper
	 * @return: boolean 操作是否成功
	 */
	public boolean writeAllPoints(String dir, CrossMapper crossMapper) {

		String fileName = dir + "\\5_QTset_data\\ALLPoints.csv";
		// 读取所有修改后的cross数据
		List<Cross> crossList = crossMapper.selectList(null);
		return write(fileName, crossList);

	}

	public boolean writeAllLinks(String dir, LinkMapper linkMapper) {

		String fileName = dir + "\\5_QTset_data\\ALLLinks.csv";
		// 读取所有修改后的link数据
		List<Link> linkList = linkMapper.selectList(null);
		return write(fileName, linkList);

	}

	public boolean writeBUSPoints(String dir, BusPointMapper busPointMapper) {

		String fileName = dir + "\\5_QTset_data\\BUSPoints.csv";
		// 读取所有修改后的公共车辆源数据
		QueryWrapper<BusPoint> queryWrapper = new QueryWrapper<BusPoint>().eq("type", BUS_POINT);
		List<BusPoint> busPointList = busPointMapper.selectList(queryWrapper);
		return write(fileName, busPointList);

	}

	public boolean writeCIVILPoints(String dir, CivilPointMapper civilPointMapper) {

		String fileName = dir + "\\5_QTset_data\\CIVILPoints.csv";
		// 读取所有修改后的居民点数据
		QueryWrapper<CivilPoint> queryWrapper = new QueryWrapper<CivilPoint>().eq("type", CIVIL_POINT1).or().eq("type", CIVIL_POINT2);
		List<CivilPoint> civilPointList = civilPointMapper.selectList(queryWrapper);
		return write(fileName, civilPointList) && write(dir + "\\5_QTset_data\\CIVILPoints1.csv", civilPointList);

	}

	public boolean writeSETTLEPoints(String dir, SettlePointMapper settlePointMapper) {

		String fileName = dir + "\\5_QTset_data\\SETTLEPoints.csv";
		// 读取所有修改后的安置点数据
		QueryWrapper<SettlePoint> queryWrapper = new QueryWrapper<SettlePoint>().eq("type", SETTLE_POINT);
		List<SettlePoint> settlePointList = settlePointMapper.selectList(queryWrapper);
		return write(fileName, settlePointList) && write(dir + "\\5_QTset_data\\SETTLEPoints1.csv", settlePointList);

	}

	public boolean writeOtherSettings(String dir, OtherSettingsMapper otherSettingsMapper) {

		String fileName = dir + "\\5_QTset_data\\OTHERsettings.csv";
		// 读取修改后的其他设置数据
		List<OtherSettings> otherSettings = otherSettingsMapper.selectList(null);
		return write(fileName, otherSettings);

	}

	public boolean write(String fileName, List<? extends ToStrings> list) {

		CsvWriter csvWriter = new CsvWriter(fileName, ',', Charset.forName("gbk"));
		String[] values;
		try {
			for (int i = 0; i < list.size(); ++i) {
				values = list.get(i).toStrings();
				csvWriter.writeRecord(values);
			}
			csvWriter.flush();
			csvWriter.close();
			return true;
		} catch (Exception e) {
			csvWriter.close();
			e.printStackTrace();
			return false;
		}

	}
}
