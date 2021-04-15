package com.bjtu.websystem.controller;

import com.bjtu.websystem.model.datasetModels.Cross;
import com.bjtu.websystem.service.CrossService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
public class CrossController {

	@Autowired
	CrossService crossService;

	/***
	 * code 请求响应代码
	 */
	private static final int SUCCESS = 0;
	private static final int FAILURE = 1;
	private static final int EXCEPTION = 2;

	@RequestMapping("/getAllCrosses")
	@ResponseBody
	public Map<String, Object> getAllCrosses() {

		Map<String, Object> resMap = new HashMap<>(2);

		// 获得GPSCrosses
		List<Cross> GPSCrosses = crossService.getAllCrosses();

		// 判断操作是否成功

		if (GPSCrosses == null){

			// 失败
			resMap.put("data", null);
			resMap.put("code", FAILURE);

		}{

			// 成功
			resMap.put("data", GPSCrosses);
			resMap.put("code", SUCCESS);

		}

		return resMap;

	}

	@GetMapping("/getCrossByType")
	@ResponseBody
	public List<Cross> getCrossByType(@RequestParam int type) {
		return crossService.getCrossByType(type);
	}


	/**
	    * @description: 修改cross
	    * @author Nekkl
	    * @date: 2021/4/13 21:44
	    * @param: [cross] 修改后的cross实体
	    * @return: java.util.Map<java.lang.String,java.lang.Object> 修改结果
	 */
	@PostMapping("/editCrossById")
	@ResponseBody
	public Map<String, Object> editCrossById(@RequestBody Cross cross ){

		Map<String, Object> resMap = new HashMap<String, Object>(1);
		int result = crossService.editCrossById(cross);
		if (result == 1){
			resMap.put("code", SUCCESS);
			return resMap;
		}else if (result == 0){
			resMap.put("code", FAILURE);
			return resMap;
		}
		resMap.put("code", EXCEPTION);

		return resMap;

	}
}
