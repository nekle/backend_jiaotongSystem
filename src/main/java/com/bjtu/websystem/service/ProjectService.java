package com.bjtu.websystem.service;

import com.bjtu.websystem.model.datasetModels.Project;

import java.util.List;

/**
 * @author zhangsan
 * @date 2021/4/6
 */
public interface ProjectService {
	/**
	    * @description: 创建一个项目
	    * @author Nekkl
	    * @date: 2021/4/6 19:42
	    * @param: [project]
	    * @return: void
	 */
	// 项目读写
	public boolean createProject(Project project);
	public Project getProjectById(Integer id);

	// 项目列表
	public List<Project> getProjectsByPage(int currentPage, int pageSize);
	public long getPageSum(int pageSize);
	public Integer getProjectSum();

	// 项目搜索
    public List<Project> searchProject(String condition, Integer currentPage, Integer pageSize);
	public long getSearchProjectPageSum(String condition, int pageSize);
	public Integer getSearchProjectSum(String condition);

	// 项目管理
	public Integer openProject(int id);
	public Integer closeProject();
	public Project getOpenedProject();
}
