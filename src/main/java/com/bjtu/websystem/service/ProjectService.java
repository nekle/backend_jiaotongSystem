package com.bjtu.websystem.service;

import com.bjtu.websystem.model.Project;

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
	public boolean createProject(Project project);
	public List<Project> getProjectsByPage(int currentPage, int pageSize);
	public long getPageSum(int pageSize);
	public Integer getProjectSum();
	public Project getProjectById(Integer id);
    public List<Project> searchProject(String condition, Integer currentPage, Integer pageSize);
}
