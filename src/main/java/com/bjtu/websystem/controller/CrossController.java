package com.bjtu.websystem.controller;

import com.bjtu.websystem.common.utils.readBinaryFile.BinaryToFile;
import com.bjtu.websystem.common.utils.GPS;
import com.bjtu.websystem.common.utils.GPS2Three;
import com.bjtu.websystem.common.utils.GPSConverterUtils;
import com.bjtu.websystem.model.Cross;
import com.bjtu.websystem.model.readFileModels.ReturnedCarInfoModel;
import com.bjtu.websystem.service.CrossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.List;

@Controller
@CrossOrigin
public class CrossController {

	@Autowired
	CrossService crossService;


	@RequestMapping("/getAllCrosses")
	@ResponseBody
	public List<Cross> getAllCrosses() {
		List<Cross> GPSCrosses = crossService.getAllCrosses();

		for (int i = 0; i < GPSCrosses.size(); ++i) {
			Cross myCross = GPSCrosses.get(i);
			// 坐标转换
			double longitude = myCross.getLongitude();
			double latitude = myCross.getLatitude();
			GPS myGPS = GPSConverterUtils.gps84_To_Gcj02(latitude, longitude);

			//修改为转换后的值
			GPSCrosses.set(
					i,
					new Cross(
							myCross.getId(),
							myCross.getName(),
							myGPS.getLon(),
							myGPS.getLat(),
							myCross.getType(),
							myCross.getCar_sum(),
							myCross.getCar_start_time(),
							myCross.getCar_start_interval(),
							myCross.getPassenger_sum(),
							myCross.getCar_passenger_sum(),
							myCross.getTruck_sum(),
							myCross.getMotor_sum(),
							myCross.getCapacity()
					)
			);
		}
		return GPSCrosses;
	}

	@RequestMapping("/getCrossByType/{type}")
	@ResponseBody
	public List<Cross> getCrossByType(@PathVariable int type) {
		return crossService.getCrossByType(type);
	}

	@RequestMapping("/getPath")
	@ResponseBody
	public ReturnedCarInfoModel getPath() throws IOException, InterruptedException {
	    /**
	    *  文件路径
	    */
		String carInfoPath = "C:/Users/Nekkl/Desktop/carInfo.txt";
		String crossGridPath = "C:/Users/Nekkl/Desktop/cross_xy_LONLAT.csv";
		String linkGridPath = "C:/Users/Nekkl/Desktop/grid_xy_LONLAT.csv";

		/**
		* 坐标转换参数，生成坐标转换器
		*/
		double width = 500;
		double left = 117.421875000;
		double right = 117.553710938;
		double top = 23.906250000;
		double bottom = 23.774414063;
		GPS2Three converter = new GPS2Three(width, left, right, top, bottom);

		return BinaryToFile.readData(carInfoPath, crossGridPath, linkGridPath, converter);
	}
}
