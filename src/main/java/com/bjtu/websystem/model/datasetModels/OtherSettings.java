package com.bjtu.websystem.model.datasetModels;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Field;

/**
 * @author Nekkl
 * @version 1.0
 * @description: 其他参数设置
 * @date 2021/4/13 16:40
 */
@TableName("other_settings_tbl")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherSettings extends ToStrings {
	private int busCapacity;
	private int carCapacity;
	private int truckCapacity;
	private int motorCapacity;
	private int weatherFactor;
	private int seasonFactor;
	private int groundFactor;
	private int distributionType;
	private float a;
	private float b;
	private float mu1;
	private float sigma1;
	private float mu2;
	private float sigma2;
	private float lemda1;
	private float lemda2;
	private int hour;
	private int minute;
	private int un1;
	private int un2;

	@Override
	public String[] toStrings() {

		Class cl = this.getClass();
		Field[] fields = cl.getDeclaredFields();
		int length = fields.length;

		String[] strings = new String[length];

		for (int i = 0; i < length; ++i) {

			Field field = fields[i];
			field.setAccessible(true);
			try{
				strings[i] = field.get(this) + "";
			}catch (Exception e){
				e.printStackTrace();
			}

		}

		return strings;

	}
}
