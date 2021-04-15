package com.bjtu.websystem;

import com.bjtu.websystem.common.utils.toFile.EditVars;
import com.bjtu.websystem.common.utils.toMySQL.CsvToMysql;
import com.bjtu.websystem.mapper.CrossMapper;
import com.bjtu.websystem.mapper.LinkMapper;
import com.bjtu.websystem.mapper.ProjectMapper;
import com.bjtu.websystem.mapper.toFileMappers.BusPointMapper;
import com.bjtu.websystem.mapper.toFileMappers.CivilPointMapper;
import com.bjtu.websystem.mapper.OtherSettingsMapper;
import com.bjtu.websystem.mapper.toFileMappers.SettlePointMapper;
import com.bjtu.websystem.service.ProjectService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class WebsystemApplicationTests {

	@Autowired
	CrossMapper crossMapper;
	@Autowired
	LinkMapper linkMapper;
	@Autowired
	BusPointMapper busPointMapper;
	@Autowired
	SettlePointMapper settlePointMapper;
	@Autowired
	CivilPointMapper civilPointMapper;
	@Autowired
	OtherSettingsMapper otherSettingsMapper;

	@Autowired
	ProjectService projectService;
	@Autowired
	ProjectMapper projectMapper;

	// MySQL数据库表列名
	private static final String CROSS_NAMES = "id,name,longitude,latitude,type,vehicle_sum,vehicle_start_time,vehicle_start_interval,passenger_sum,car_sum,truck_sum,motor_sum,capacity";
	private static final String LINK_NAMES = "id,cross1,cross2,lane,speed_limit";
	private static final String OTHER_SETTINGS_NAMES = "bus_capacity,car_capacity,truck_capacity,motor_capacity,weather_factor,season_factor,ground_factor,distribution_type,a,b,mu1,sigma1,mu2,sigma2,lemda1,lemda2,hour,minute,un1,un2";

	@Test
	void contextLoads() {
	}

	@Test
	public void testPage(){
//		List<Project> projects =  projectService.getProjectsByPage(1,4);
//		System.out.println(projects.toArray().toString());
//		SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");//设置日期格式
//		String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
//		System.out.println(date);
//		Project project = projectService.getOpenedProject();
//		System.out.println(project.getIs_open());
		EditVars editVars = new EditVars();
		String dir = "C:\\Users\\Nekkl\\Desktop\\un_un";
		editVars.writeAllPoints(dir, crossMapper);
		editVars.writeBUSPoints(dir, busPointMapper);
		editVars.writeCIVILPoints(dir, civilPointMapper);
		editVars.writeSETTLEPoints(dir, settlePointMapper);
		editVars.writeAllLinks(dir, linkMapper);
		editVars.writeOtherSettings(dir, otherSettingsMapper);
	}

	@Test
	public void removeMySQL(){
		crossMapper.delete(null);
		linkMapper.delete(null);
		otherSettingsMapper.delete(null);
	}

	@Test
	public void testCsvToMysql(){
		CsvToMysql csvToMysql = new CsvToMysql();
		String dir = "C:\\un1_un1\\5_QTset_data\\";
		try {
			csvToMysql.csvToMysql(dir + "ALLpoints.csv", CROSS_NAMES, 13, "cross_tbl", true);
			csvToMysql.csvToMysql(dir + "ALLlinks.csv", LINK_NAMES, 5, "link_tbl", false);
			csvToMysql.csvToMysql(dir + "OTHERsettings.csv", OTHER_SETTINGS_NAMES, 20, "other_settings_tbl", false);
		}catch (Exception e){
			e.printStackTrace();
		}

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
