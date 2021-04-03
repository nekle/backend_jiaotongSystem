package com.bjtu.websystem.model;/**
 * @author zhangsan
 * @date 2021/4/3
 */

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/3 20:33
 */
@Data
@AllArgsConstructor
public class ReturnedCarInfoModel {
	private Geometry[] geometries;
	private int startTime;
	private int endTime;
}
