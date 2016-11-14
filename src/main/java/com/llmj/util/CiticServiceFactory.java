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
import com.llmj.service.CiticService;

public class CiticServiceFactory {
	
	private static Logger log = LoggerFactory.getLogger(CiticServiceFactory.class);
	
	private static int nodeCount;
	
	private static class LazyHolder{
		private static final CiticServiceFactory INSTANCE= new CiticServiceFactory();
	}
	public static CiticServiceFactory getInstance() {
		return LazyHolder.INSTANCE;
	}
    private CiticServiceFactory(){
    	init();
    }
    private static Map<String, CiticService> citicServiceMap = new  HashMap<String, CiticService>();
   
    /**
     * 实例化
     * PassportServiceFactory.instanceService()<BR>
     * <P>Author :  hz </P>  
     * <P>Date : 2014-1-24 </P>
     * @return
     */
    public CiticService instanceService(){
		Random rnd = new Random();
		int i = rnd.nextInt(nodeCount);
		String index = "node" + i;
		if (citicServiceMap.get(index) != null) {
			return citicServiceMap.get(index);
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
    		wsConfigManager = new XMLConfiguration("llmj-pay-citic-service.xml");
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
				citicServiceMap.put("node" + nodeIndex, (CiticService) hp.create(CiticService.class,wsdlURL));
			} catch (MalformedURLException e) {
			}
		}
	}
	
}



