package com.llmj.web.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.llmj.web.mapper.NewDatabaseMapper;
import com.llmj.web.service.INewDatabaseService;

@Service
public class NewDatabaseServiceImpl implements INewDatabaseService{

	@Resource
	private NewDatabaseMapper databaseMapper;
	
	@Override
	public void insertData(Map<String, Object> map) {
		databaseMapper.insertData(map);
	}

}
