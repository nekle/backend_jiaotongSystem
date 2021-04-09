package com.bjtu.websystem.controller;/**
 * @author zhangsan
 * @date 2021/4/6
 */

import com.bjtu.websystem.common.utils.createDir.DirectoryUtil;
import com.bjtu.websystem.model.Project;
import com.bjtu.websystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/6 19:21
 */
@Controller
@CrossOrigin
public class ProjectController {
	@Autowired
	ProjectService projcetService;

	/**
	 * @description: 创建项目
	 * @author Nekkl
	 * @date: 2021/4/7 19:25
	 * @param: [name, scene, path, dir]
	 * @return: String 项目创建结果
	 */
	@RequestMapping(value = "/createProject", method = RequestMethod.POST)
	@ResponseBody
	public String createProject(
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "scene", required = true) String scene,
			@RequestParam(value = "path", required = true) String path,
			@RequestParam(value = "dir", required = true) String dir
	) {
		// 新建一个项目对象
		Project project = new Project();
		project.setName(name);
		project.setScene(scene);
		project.setPath(path);
		project.setDir(dir);
		// 项目初始状态为:创建成功 1
		project.setStatus(1);
		//设置日期格式
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
		// new Date()为获取当前系统时间，也可使用当前时间戳
		String date = df.format(new Date());
		project.setDate(date);

		try {
			// 创建文件夹
			DirectoryUtil directoryUtil = new DirectoryUtil();
			String[] dirs = new String[23];
			dirs[0] = "\\0_net_data\\1_GRD_data";
			dirs[1] = "\\0_net_data_1\\1_GRD_data";
			dirs[2] = "\\1_input\\1_person_data";
			dirs[3] = "\\1_input\\2_soccar_data";
			dirs[4] = "\\1_input\\3_parameter";
			dirs[5] = "\\1_input\\4_screen";
			dirs[6] = "\\1_input\\5_VRP";
			dirs[7] = "\\1_input\\6_GRD";
			dirs[8] = "\\1_input\\7_pic";
			dirs[9] = "\\5_QTset_data";
			dirs[10] = "\\data";
			dirs[11] = "\\GUI_data\\flight_time";
			dirs[12] = "\\GUI_data\\flight_time_bar";
			dirs[13] = "\\GUI_data\\person_escape_time_bar";
			dirs[14] = "\\GUI_data\\person_escape_time_line";
			dirs[15] = "\\GUI_data\\person_travel_time_bar";
			dirs[16] = "\\GUI_data\\person_wait_time_bar";
			dirs[17] = "\\GUI_data\\person_walking_time_bar";
			dirs[18] = "\\GUI_data\\pubcar_travel_loss_line";
			dirs[19] = "\\GUI_data\\wait_loss_bar";
			dirs[20] = "\\GUI_data\\walking_loss_bar";
			dirs[21] = "\\roads";
			dirs[22] = "\\GUI_data\\pubcar_travel_loss_line";

			boolean result = false;

			for (int i = 0; i < dirs.length; ++i) {

				// 初始化创建结果
				result = false;

				try {

					String newDir = dir + dirs[i];
					result = directoryUtil.createParentDir(newDir);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			// 项目信息插入数据库
			result = projcetService.createProject(project);

			if (result) {
				return "项目创建成功";
			} else {
				return "项目创建失败11";
			}

		} catch (DataAccessException e) {
			// 捕获项目创建失败的异常（项目的dir重复，因为数据库中设置为了unique）
			return "项目创建失败";
		}

	}

	/**
	 * @description: 根据页码和页大小 get 项目
	 * @author Nekkl
	 * @date: 2021/4/7 19:24
	 * @param: [currentPage, pageSize]
	 * @return:
	 */
	@RequestMapping(value = "/getProjectsByPage", method = RequestMethod.GET)
	@ResponseBody
	public List<Project> getProjectsByPage(
			@RequestParam(value = "currentPage", required = true) int currentPage,
			@RequestParam(value = "pageSize", required = true) int pageSize
	) {
		return projcetService.getProjectsByPage(currentPage, pageSize);
	}

	/**
	 * @description: 根据每页大小获得项目总页数
	 * @author Nekkl
	 * @date: 2021/4/7 19:25
	 * @param: [pageSize]
	 * @return: Integer pageSum
	 */
	@RequestMapping(value = "/getProjectsPageSum", method = RequestMethod.GET)
	@ResponseBody
	public long getProjectsPageSum(
			@RequestParam(value = "pageSize", required = true) int pageSize
	) {
		return projcetService.getPageSum(pageSize);
	}


	/**
	 * @description: 获取项目总数
	 * @author Nekkl
	 * @date: 2021/4/7 20:52
	 * @param: []
	 * @return: Integer 项目总数
	 */
	@RequestMapping(value = "/getProjectSum", method = RequestMethod.GET)
	@ResponseBody
	public Integer getProjectSum() {
		return projcetService.getProjectSum();
	}

	/**
	 * @description: 上传文件到服务器本地
	 * @author Nekkl
	 * @date: 2021/4/7 23:31
	 * @param: [cross, link, link_points, grds, map_img, map_info, id]
	 * @return: String 上传结果
	 */
	@PostMapping(value = "/postParamFiles")
	@ResponseBody
	public String postParamFiles(
			@RequestPart("cross") MultipartFile cross,
			@RequestPart("link") MultipartFile link,
			@RequestPart("link_points") MultipartFile link_points,
			@RequestPart("grds") MultipartFile[] grds,
			@RequestPart("map_img") MultipartFile map_img,
			@RequestPart("map_info") MultipartFile map_info,
			@RequestParam(value = "project_id", required = true) Integer id
	)  {

		// 判断是否成功收到文件
		if (cross.isEmpty() || link.isEmpty() || link_points.isEmpty() || grds.length == 0 || map_img.isEmpty() || map_info.isEmpty()) {
			return "文件上传失败，请检查文件后重新上传";
		}

		// 获取对应的项目信息
		Project project = projcetService.getProjectById(id);

		// 写入本地文件
		try {
			cross.transferTo(new File(project.getDir() + "\\0_net_data\\" + cross.getOriginalFilename()));
			link.transferTo(new File(project.getDir() + "\\0_net_data\\" + link.getOriginalFilename()));
			for (MultipartFile grd : grds
			) {
				grd.transferTo(new File(project.getDir() + "\\0_net_data\\1_GRD_data\\" + grd.getOriginalFilename()));
			}
			map_img.transferTo(new File(project.getDir() + "\\0_net_data\\" + map_img.getOriginalFilename()));
			map_info.transferTo(new File(project.getDir() + "\\0_net_data\\" + map_info.getOriginalFilename()));
		} catch (IOException e) {
			e.printStackTrace();
			return "文件上传失败：在写入文件的时候出错";
		}

		return "文件上传成功";
	}

	/**
	    * @description: 按照条件模糊搜索名称或者场景满足的项目
	    * @author Nekkl
	    * @date: 2021/4/9 11:34
	    * @param: [condition, currentPage, pageSize]
	    * @return: List<Project> 满足条件的项目列表
	 */
	@GetMapping(value = "/searchProject")
	@ResponseBody
	public List<Project> searchProject(@RequestParam String condition, @RequestParam Integer currentPage, @RequestParam Integer pageSize){
			return projcetService.searchProject(condition, currentPage, pageSize);
	}

	/**
	    * @description: 获取搜索结果的项目总数
	    * @author Nekkl
	    * @date: 2021/4/9 11:35
	    * @param: [condition]
	    * @return: Integer 项目总数
	 */
	@GetMapping(value = "/getSearchProjectSum")
	@ResponseBody
	public Integer getSearchProjectSum(@RequestParam String condition){
		return projcetService.getSearchProjectSum(condition);
	}

	/**
	    * @description: 根据条件和页大小，获取总页数
	    * @author Nekkl
	    * @date: 2021/4/9 11:36
	    * @param: [condition, pageSize]
	    * @return: long 总页数
	 */
	@GetMapping(value = "/getSearchProjectPageSum")
	@ResponseBody
	public long getSearchProjectPageSum(@RequestParam String condition, @RequestParam Integer pageSize){
		return projcetService.getSearchProjectPageSum(condition, pageSize);
	}
}
