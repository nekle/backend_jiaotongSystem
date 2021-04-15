package com.bjtu.websystem.model.datasetModels;/**
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
 * @description: 项目对象，存储一个项目的有关信息
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
	private Integer id;
	private String name;
	private String scene;
	private String path;
	private String dir;
	private Integer status;
	private String date;
	private Integer isOpen;
}
