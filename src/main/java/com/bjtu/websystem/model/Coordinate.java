package com.bjtu.websystem.model;/**
 * @author zhangsan
 * @date 2021/4/1
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/1 10:32
 */
@Data
@AllArgsConstructor
public class Coordinate{

	/**
	* double[] threePos 坐标，格式:{x, y, z}
	*/
	private double[] threePos;
}
