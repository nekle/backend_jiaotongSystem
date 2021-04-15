package com.bjtu.websystem.common.utils.cordConvertionUtils;/**
 * @author zhangsan
 * @date 2021/4/3
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/3 11:38
 */

@Data
@AllArgsConstructor
public class GPS2Three {
	/**
	* three.js 地图组件宽度 默认组件是正方形，故只设一个宽即可
	*/
	private double width;
	/**
	* 地图最左侧纬度
	*/
	private double left;
	/**
	* 地图最右侧纬度
	*/
	private double right;
	private double top;
	private double bottom;


	/**
	    * @description: 将GPS坐标转化成three.js像素坐标，返回格式是{x, y}
	    * @author Nekkl
	    * @date: 2021/4/3 12:31
	    * @param: [lon, lat]
	    * @return: double[] pos
	 */
	public  double[] GPS2Pixel(double lon, double lat){
		double  unitX = width / (right - left);
		double  unitY = width / (top - bottom);
		double  centerX = (left + right) / 2;
		double  centerY = (top + bottom) / 2;

		double x = (lon - centerX) * unitX;
		double y = (lat - centerY) * unitY;
		double[] pos = {x, y};
		return  pos;

	}
}
