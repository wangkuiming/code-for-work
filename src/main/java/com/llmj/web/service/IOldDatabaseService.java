package com.llmj.web.service;

import java.util.Map;

public interface IOldDatabaseService {

	Integer queryOldDataNum(String oldDatabaseName);

	Map<String, Object> queryOldData(int i, String oldDatabaseName);

}
