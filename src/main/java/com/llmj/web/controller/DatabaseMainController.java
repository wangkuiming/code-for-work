package com.llmj.web.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.llmj.util.constant.Config;
import com.llmj.web.controller.base.BaseController;
import com.llmj.web.service.INewDatabaseService;
import com.llmj.web.service.IOldDatabaseService;

@Controller
@RequestMapping("copyDatabase")
public class DatabaseMainController extends BaseController {

	@Resource
	private IOldDatabaseService oldDatabaseService;
	@Resource
	private INewDatabaseService newDatabaseService;

	@RequestMapping("start")
	public void start(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("转移数据库数据详情                     start ======================start ");
		try {
			String oldDatabaseName = Config.getStringValue("oldDatabaseName");
			String newDatabaseName = Config.getStringValue("newDatabaseName");
			// 查询共多少条数据
			Integer num = oldDatabaseService.queryOldDataNum(oldDatabaseName);
			LOGGER.error("旧" + oldDatabaseName + "数据库到新" + newDatabaseName + "数据库共需要转移" + num + "条数据");
			for (int i = 1; i <= num; i++) {
				LOGGER.info("转移第" + i + "条数据开始!");
				// 查询数据
				Map<String, Object> map = oldDatabaseService.queryOldData(i, oldDatabaseName);
				// 保存数据
				try {
					newDatabaseService.insertData(map);
					LOGGER.info("转移第" + i + "条数据结束!");
				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.error("转移第" + i + "条数据出现问题! 订单号:"+map.get("ORDER_NO"));
				}
			}
		} catch (Exception e) {
			LOGGER.error("调用转移数据库数据 详情异常,异常信息:{}", e.getMessage(), e);
			e.printStackTrace();
		} finally {
			LOGGER.info("转移数据库数据    start =============  end ");
		}
	}
}
