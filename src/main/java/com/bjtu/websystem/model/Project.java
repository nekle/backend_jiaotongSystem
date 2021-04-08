package com.bjtu.websystem.model;/**
 * @author zhangsan
 * @date 2021/4/6
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/6 19:14
 */
@Data
@TableName("project_tbl")
@AllArgsConstructor
@NoArgsConstructor
public class Project {
	/**
	 * value = “数据库中的id名
	 * type = IdType.AUTO: 自增模式
	 */
	@TableId(type = IdType.AUTO)
	private int id;
	private String name;
	private String scene;
	private String path;
	private String dir;
	private int status;
	private String date;
}
