package com.llmj.web.controller.base;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.llmj.common.utils.LLMJCookieUtil;

public class BaseController {
	@Resource
	protected HttpServletRequest request;

	public static Logger LOGGER = LoggerFactory.getLogger(BaseController.class);
	
	/**
     * 获取用户userId
     * @return
     */
    protected String getCookiesUserId() {
    	return LLMJCookieUtil.cookieUtil.getIdentity(request).get(LLMJCookieUtil.USER_ID);
    }
	
	
}
