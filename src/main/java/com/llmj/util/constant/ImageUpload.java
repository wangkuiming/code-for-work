/**
 * 
 */
package com.llmj.util.constant;

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.llmj.common.utils.UUIDUtil;

/**
 * 图片地址上传工具类
 * 
 * @Title: ImageUpload.java
 * @Description:
 * @author Hanzhonghua
 * @date 2016年10月19日下午2:01:32
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ImageUpload {

	public static final String CERTIFICATESIMAGE = "certificatesImage";// 认证证件照片文件夹

	public static final String CARIMAGE = "carImage";// 车辆照片文件夹

	public static final String GOODSIMAGE = "goodsImage";// 货物照片文件夹

	public static final String HEADIMAGE = "headImage";// 个人头像照片文件夹

	public static final String STOREIMAGE = "storeImage";// 仓库照片

	public static final String ORDERIMAGE = "orderImage";// 订单回单照片

	public static final String COMPANYMAGE = "companyImage";// 订单回单照片

	public static Logger LOGGER = LoggerFactory.getLogger(ImageUpload.class);

	public static String upLoadImg(MultipartFile file, String type)
	        throws Exception {
		String imgUrl = null;
		JSONObject dataInfo = new JSONObject();
		dataInfo.put("type", type);

		if (CARIMAGE.equals(type)) {
			dataInfo.put("oper",
			        JSONArray.parseArray("[{'opt':'zoom','w':200,'h':200}]"));
		} else if (GOODSIMAGE.equals(type)) {
			dataInfo.put("oper",
			        JSONArray.parseArray("[{'opt':'zoom','w':200,'h':200}]"));
		} else if (HEADIMAGE.equals(type)) {
			dataInfo.put("oper", JSONArray.parseArray(
			        "[{'opt':'zoom','w':130,'h':130},{'opt':'zoom','w':100,'h':100},{'opt':'zoom','w':80,'h':80},{'opt':'zoom','w':60,'h':60}]"));
		} else if (STOREIMAGE.equals(type)) {
			dataInfo.put("oper",
			        JSONArray.parseArray("[{'opt':'zoom','w':200,'h':200}]"));
		} else if (ORDERIMAGE.equals(type)) {
			dataInfo.put("oper",
			        JSONArray.parseArray("[{'opt':'zoom','w':600,'h':400}]"));
		}else if (CERTIFICATESIMAGE.equals(type)) {
			dataInfo.put("oper",
			        JSONArray.parseArray("[{'opt':'zoom','w':200,'h':200}]"));
		}

		PostMethod postMethod = new PostMethod(
		        Config.getStringValue("pic_url"));
		StringPart sp = new StringPart("data", dataInfo.toString());

		File f = null;
		try {
			String filePath = file.getOriginalFilename();
			String renameFile = UUIDUtil.getUUID32Str()
			        + filePath.substring(filePath.lastIndexOf("."));
			// 上传的文件
			f = new File(renameFile);
			// 转存文件
			file.transferTo(f);

			FilePart fp = new FilePart("files", f);

			Part[] parts = { fp, sp };

			// 对于MIME类型的请求，httpclient建议全用MulitPartRequestEntity进行包装
			MultipartRequestEntity mre = new MultipartRequestEntity(parts,
			        postMethod.getParams());

			postMethod.setRequestEntity(mre);
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams()
			        .setConnectionTimeout(50000);// 设置连接时间
			int status = client.executeMethod(postMethod);
			if (status == HttpStatus.SC_OK) {
				String returnStr = postMethod.getResponseBodyAsString();
				// {"reUrl":"http://qb-pic.lenglianmajia.com/carImage/original/10244ab7779c42398dec3f288094366f.jpg","recode":0}
				JSONObject resultJson = JSONObject.parseObject(returnStr);
				if ("0".equals(resultJson.getString("recode"))) {
					String reUrl = resultJson.getString("reUrl");
					// imgUrl eg.c73009b1f60b4c00afa55c308304112b.jpg
					imgUrl = reUrl.substring(reUrl.lastIndexOf("/") + 1);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			LOGGER.info("上传图片图片异常，异常信息：", ex);
		} finally {
			if (f != null && f.exists()) {
				f.delete();
			}
		}
		return imgUrl;
	}
}
