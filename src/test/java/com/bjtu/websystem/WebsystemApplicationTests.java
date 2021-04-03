package com.bjtu.websystem;

import com.bjtu.websystem.mapper.CrossMapper;
import com.bjtu.websystem.model.Cross;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebsystemApplicationTests {

	@Autowired
	CrossMapper crossMapper;
	@Test
	void contextLoads() {
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
