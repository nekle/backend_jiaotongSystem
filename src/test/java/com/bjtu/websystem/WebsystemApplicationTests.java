package com.bjtu.websystem;

import com.bjtu.websystem.mapper.CrossMapper;
import com.bjtu.websystem.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
class WebsystemApplicationTests {

	@Autowired
	CrossMapper crossMapper;
	@Autowired
	ProjectService projectService;

	@Test
	void contextLoads() {
	}

	@Test
	public void testPage(){
//		List<Project> projects =  projectService.getProjectsByPage(1,4);
//		System.out.println(projects.toArray().toString());
		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");//设置日期格式
		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
		System.out.println(date);
	}
	@Test
	public void testSelect() {
//		Cross myCross = new Cross(5,"newcross",1,1,1,1,1,1,1,1);
		System.out.println(("----- selectAll method test ------"));
		System.out.println(crossMapper.selectById(5));
//		crossMapper.insert(myCross);
//		List<Cross> crosses = crossMapper.selectList(null);
//		System.out.println(crosses);

	}
}
