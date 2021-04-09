package com.bjtu.websystem.service.Impl;
/**
 * @author zhangsan
 * @date 2021/4/6
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.websystem.mapper.ProjectMapper;
import com.bjtu.websystem.model.Project;
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

		if (projectMapper.insert(project) == 1){
			return true;
		}

		return false;

	}

	@Override
	public List<Project> getProjectsByPage(int currentPage, int pageSize) {

		// 获取IPage对象
		IPage<Project> projectIPage = projectMapper.selectPage(new Page<>(currentPage, pageSize),null);
		// IPage对象通过getRecords()方法获取结果列表
		List<Project> projects =projectIPage.getRecords();


		return projects;
	}

	@Override
	public long getPageSum(int pageSize) {
		// 获取IPage对象
		IPage<Project> projectIPage = projectMapper.selectPage(new Page<>(1, pageSize),null);
		// IPage对象通过getRecords()方法获取结果列表
		long pageSum =projectIPage.getPages();

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
				new QueryWrapper<Project>().like("name",condition).or().like("scene",condition));
		// IPage对象通过getRecords()方法获取结果列表
		List<Project> projects =projectIPage.getRecords();

		return projects;

	}

	@Override
	public long getSearchProjectPageSum(String condition, int pageSize) {

		// 获取IPage对象
		IPage<Project> projectIPage = projectMapper.selectPage(
				new Page<>(1, pageSize),
				new QueryWrapper<Project>().like("name",condition).or().like("scene",condition));
		return projectIPage.getPages();

	}

	@Override
	public Integer getSearchProjectSum(String condition) {

		return projectMapper.selectCount(new QueryWrapper<Project>().like("name",condition).or().like("scene",condition));

	}

}
