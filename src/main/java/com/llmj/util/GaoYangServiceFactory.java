package com.llmj.util;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.client.HessianProxyFactory;
import com.llmj.service.GaoyangService;

public class GaoYangServiceFactory {
	
	private static Logger log = LoggerFactory.getLogger(GaoYangServiceFactory.class);
	
	private static int nodeCount;
	
	private static class LazyHolder{
		private static final GaoYangServiceFactory INSTANCE= new GaoYangServiceFactory();
	}
	public static GaoYangServiceFactory getInstance() {
		return LazyHolder.INSTANCE;
	}
    private GaoYangServiceFactory(){
    	init();
    }
    private static Map<String, GaoyangService> gyServiceMap = new  HashMap<String, GaoyangService>();
   
    /**
     * 实例化
     * PassportServiceFactory.instanceService()<BR>
     * <P>Author :  hz </P>  
     * <P>Date : 2014-1-24 </P>
     * @return
     */
    public GaoyangService instanceService(){
		Random rnd = new Random();
		int i = rnd.nextInt(nodeCount);
		String index = "node" + i;
		if (gyServiceMap.get(index) != null) {
			return gyServiceMap.get(index);
		}
    	return null;
    }

    /**
     * 初始化
     * PassportServiceFactory.init()<BR>
     * <P>Author :  hz </P>  
     * <P>Date : 2014-1-24 </P>
     */
	private static void init(){
		XMLConfiguration wsConfigManager = null;
    	try {
    		wsConfigManager = new XMLConfiguration("llmj-pay-gaoyang-service.xml");
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		List<Object> nodeList = wsConfigManager.getList("cluster.snode.id");
		if(nodeList.size()==0) return ;
		
		nodeCount = nodeList.size();
		for(int nodeIndex=0;nodeIndex<nodeList.size();nodeIndex++){
			String url = wsConfigManager.getString("cluster.snode("+nodeIndex+").url");
			String serviceName = wsConfigManager.getString("cluster.snode("+nodeIndex+").serviceName");
			String hessianServiceName = wsConfigManager.getString("cluster.snode("+nodeIndex+").hessianServiceName");
			String wsdlURL = "";
			if(StringUtils.isNotBlank(serviceName))
			{
				wsdlURL = "http://"+url + "/"+serviceName+"/"+hessianServiceName;
			}
			else
			{
				wsdlURL = "http://"+url + "/" +hessianServiceName;
			}
			log.info("url:==>" + wsdlURL);
			HessianProxyFactory hp = new HessianProxyFactory();
			hp.setOverloadEnabled(true);
			try {
				gyServiceMap.put("node" + nodeIndex, (GaoyangService) hp.create(GaoyangService.class,wsdlURL));
			} catch (MalformedURLException e) {
			}
		}
	}
	
}