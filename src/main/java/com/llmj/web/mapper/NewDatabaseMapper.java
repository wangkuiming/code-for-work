package com.llmj.web.mapper;

import java.util.Map;

import com.llmj.web.mapper.base.NewBaseMapper;

public interface NewDatabaseMapper extends NewBaseMapper {

	void insertData(Map<String, Object> map);

}
