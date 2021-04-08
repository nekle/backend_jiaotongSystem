package com.bjtu.websystem.model.readFileModels;/**
 * @author zhangsan
 * @date 2021/3/31
 */

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.LinkedList;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/3/31 22:49
 */
@Data
@AllArgsConstructor
public class Trail {
//	private HashMap<Integer, double[]> trailHashMap;
	private LinkedList<Coordinate> coordinates;
	private LinkedList<Integer> times;
	private int carType;
	private int startTime;
	private int endTime;
}

