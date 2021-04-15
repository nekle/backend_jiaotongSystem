package com.bjtu.websystem.model.datasetModels;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Nekkl
 * @version 1.0
 * @description: Link类 表示一个链接
 * @date 2021/4/12 21:09
 */
@TableName("link_tbl")
@Data
@AllArgsConstructor
public class Link extends ToStrings {
	private int id;
	private int cross1;
	private int cross2;
	private int lane;
	private int speedLimit;

	@Override
	public String[] toStrings() {

		String[] values = new String[5];
		values[0] = id + "";
		values[1] = cross1 + "";
		values[2] = cross2 + "";
		values[3] = lane + "";
		values[4] = speedLimit + "";

		return values;
	}
}
