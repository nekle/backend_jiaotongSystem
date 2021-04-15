package com.bjtu.websystem.service.Impl;
/**
 * @author zhangsan
 * @date 2021/4/6
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.websystem.mapper.ProjectMapper;
import com.bjtu.websystem.model.datasetModels.Project;
import com.bjtu.websystem.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Nekkl
 * @version 1.0
 * @description: TODO
 * @date 2021/4/6 19:42
 */
@Service
public class ProjectServiceImpl implements ProjectService {
	@Autowired
	ProjectMapper projectMapper;

	@Override
	public boolean createProject(Project project) {

		if (projectMapper.insert(project) == 1) {
			return true;
		}

		return false;

	}

	@Override
	public List<Project> getProjectsByPage(int currentPage, int pageSize) {

		// 获取IPage对象
		IPage<Project> projectIPage = projectMapper.selectPage(new Page<>(currentPage, pageSize), null);
		// IPage对象通过getRecords()方法获取结果列表
		List<Project> projects = projectIPage.getRecords();


		return projects;
	}

	@Override
	public long getPageSum(int pageSize) {
		// 获取IPage对象
		IPage<Project> projectIPage = projectMapper.selectPage(new Page<>(1, pageSize), null);
		// IPage对象通过getRecords()方法获取结果列表
		long pageSum = projectIPage.getPages();

		return pageSum;
	}

	/**
	 * @description: 获取项目总数
	 * @author Nekkl
	 * @date: 2021/4/7 20:50
	 * @param: []
	 * @return:
	 */
	@Override
	public Integer getProjectSum() {
		return projectMapper.selectCount(null);
	}

	@Override
	public Project getProjectById(Integer id) {
		return projectMapper.selectById(id);
	}

	@Override
	public List<Project> searchProject(String condition, Integer currentPage, Integer pageSize) {

		// 获取IPage对象
		IPage<Project> projectIPage = projectMapper.selectPage(
				new Page<>(1, pageSize),
				new QueryWrapper<Project>().like("name", condition).or().like("scene", condition));
		// IPage对象通过getRecords()方法获取结果列表
		List<Project> projects = projectIPage.getRecords();

		return projects;

	}

	@Override
	public long getSearchProjectPageSum(String condition, int pageSize) {

		// 获取IPage对象
		IPage<Project> projectIPage = projectMapper.selectPage(
				new Page<>(1, pageSize),
				new QueryWrapper<Project>().like("name", condition).or().like("scene", condition));
		return projectIPage.getPages();

	}

	@Override
	public Integer getSearchProjectSum(String condition) {

		return projectMapper.selectCount(new QueryWrapper<Project>().like("name", condition).or().like("scene", condition));

	}


	// 项目管理

	/**
	 * @description: 项目打开
	 * @author Nekkl
	 * @date: 2021/4/11 11:20
	 * @param: [id] 项目id
	 * @return: boolean 项目打开结果
	 */
	@Override
	public Integer openProject(int id) {

		String columnName = "is_open";
		Integer openedNum = projectMapper.selectCount(new QueryWrapper<Project>().eq(columnName, 1));
		if (openedNum != 0) {
			if (openedNum > 1) {
				// 被打开的项目数量超过1，异常
				return 2;
			}
			// 已经有项目被打开
			return 1;
		}

		// 根据id获取项目
		Project project = projectMapper.selectById(id);

		// 将项目开启状态设为开启
		project.setIsOpen(1);
		System.out.println(project.getName());
		// 更新数据库中项目开启状态
		if (projectMapper.updateById(project) == 1) {
			// 开启成功
			return 0;
		}
		// 开启失败,异常
		return 2;

	}

	/**
	 * @description: 关闭已经打开的项目
	 * @author Nekkl
	 * @date: 2021/4/11 11:23
	 * @param: []
	 * @return: boolean 关闭的结果
	 */
	@Override
	public Integer closeProject() {

		// 待检测的数据库列名
		String columnName = "is_open";
		Integer openedNum = projectMapper.selectCount(new QueryWrapper<Project>().eq(columnName, 1));
		// 判断是否有未被关闭的项目
		if (openedNum != 1) {

			if (openedNum == 1) {
				return 1;
			}
			return 2;

		}

		// 拿到即将被关闭的项目实体
		Project projectToBeClosed = projectMapper.selectOne(new QueryWrapper<Project>().eq(columnName, 1));
		// 将其打开标识符设为 0 已经关闭
		projectToBeClosed.setIsOpen(0);
		// 更新数据库
		projectMapper.updateById(projectToBeClosed);
		return 0;

	}

	/**
	 * @description: 获得打开的项目
	 * @author Nekkl
	 * @date: 2021/4/11 11:24
	 * @param: []
	 * @return: com.bjtu.websystem.model.datasetModels.Project 打开的项目
	 */
	@Override
	public Project getOpenedProject() {

		// 待检测的数据库列名
		String columnName = "is_open";
		// 获取被打开的项目数量
		Integer openedNum = projectMapper.selectCount(new QueryWrapper<Project>().eq(columnName, 1));

		if (openedNum == 1) {
			// 被打开的项目数量正常
			// 拿到被打开的的项目实体
			Project project = projectMapper.selectOne(new QueryWrapper<Project>().eq(columnName, 1));
			return project;
		}

		// 没有被打开的项目或被打开的项目数量异常(>2)
		return null;

	}

}
