package com.llmj.web.mapper;

import java.util.Map;

import com.llmj.web.mapper.base.OldBaseMapper;

public interface OldDatabaseMapper extends OldBaseMapper {

	public Integer queryOldDataNum(String oldDatabaseName) ;

	public Map<String, Object> queryOldData(Map<String, Object> map);

}
