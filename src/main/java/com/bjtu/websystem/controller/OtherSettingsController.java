package com.bjtu.websystem.controller;

import com.bjtu.websystem.common.utils.toFile.EditVars;
import com.bjtu.websystem.mapper.OtherSettingsMapper;
import com.bjtu.websystem.model.datasetModels.OtherSettings;
import com.bjtu.websystem.service.OtherSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Nekkl
 * @version 1.0
 * @description: othersettings
 * @date 2021/4/14 19:59
 */
@Controller
@CrossOrigin
public class OtherSettingsController {

	@Autowired
	OtherSettingsService otherSettingsService;
	@Autowired
	OtherSettingsMapper otherSettingsMapper;
	/***
	 * code 请求响应代码
	 */
	private static final int SUCCESS = 0;
	private static final int FAILURE = 1;
	private static final int EXCEPTION = 2;

	@GetMapping("/getProjectOtherParams")
	@ResponseBody
	public Map<String, Object> getProjectOtherParams() {

		Map<String, Object> resMap = new HashMap<String, Object>(2);
		OtherSettings otherSettings = otherSettingsService.getOtherSettings();
		if (otherSettings != null) {
			resMap.put("data", otherSettings);
			resMap.put("code", SUCCESS);
		} else {
			resMap.put("data", null);
			resMap.put("code", FAILURE);
		}
		return resMap;

	}

	@PostMapping("editProjectOtherParams")
	@ResponseBody
	public Map<String, Object> editProjectOtherParams(@RequestBody OtherSettings otherSettings) {

		Map<String, Object> resMap = new HashMap<String, Object>(1);

		int result = otherSettingsService.updateOtherSettings(otherSettings);

		EditVars editVars = new EditVars();
		String dir = "C:\\Users\\Nekkl\\Desktop\\un_un";

		if (result == 1) {

			if (editVars.writeOtherSettings(dir, otherSettingsMapper)){
				resMap.put("code", SUCCESS);
			}else {
				resMap.put("code", FAILURE);
			}

		} else {
			resMap.put("code", FAILURE);
		}

		return resMap;

	}
}
