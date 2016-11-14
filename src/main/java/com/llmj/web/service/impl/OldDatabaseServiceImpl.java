package com.llmj.web.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.llmj.web.mapper.OldDatabaseMapper;
import com.llmj.web.service.IOldDatabaseService;

@Service
public class OldDatabaseServiceImpl implements IOldDatabaseService {
	@Resource
	private OldDatabaseMapper databaseMapper;

	@Override
	public Integer queryOldDataNum(String oldDatabaseName) {
		return databaseMapper.queryOldDataNum(oldDatabaseName);
	}

	@Override
	public Map<String, Object> queryOldData(int rowNum, String oldDatabaseName) {
		Map<String, Object> map = new HashMap<>();
		map.put("rowNum", rowNum);
		map.put("oldDatabaseName", oldDatabaseName);
		return databaseMapper.queryOldData(map);
	}

}
