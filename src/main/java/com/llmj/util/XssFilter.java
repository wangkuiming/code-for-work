/**
 * 对参数进行XSS过滤
 */

package com.llmj.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XssFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(XssFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {

		
		HttpServletRequest req = (HttpServletRequest) request;
		
		System.out.println(req.getRequestURI());
		XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper(
				(HttpServletRequest) request);
		filterChain.doFilter(xssRequest, response);

	}

	public void init(FilterConfig conf) throws ServletException {
		logger.info("PreventXSSInject Filter staring...");
	}

	public void destroy() {

	}

}
