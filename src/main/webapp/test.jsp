<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'test.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
	<h2>添加个人认证信息</h2>
	<form action="<%=basePath%>mjPersonInfoAuthCtl/personInfoAuth.shtml"
		method="post" enctype="multipart/form-data">
		<input type="file" name="idcardImg"> <input type="file"
			name="drivingImg"> <input type="file" name="taxiLicenseImg">
		<input type="text" name="client_type" value="1"> <input type="text" name="uuid" value="1"><input
			type="text" name="data"
			value='{"phone":"13899997777","type":"1","username":"laowang","userId":"50819ab3c0954f828d0851da576cbc31","cardno":"12342342344234"}' />
		<input type="submit" value="上传">
	</form>
	<h2>添加企业认证信息</h2>
	<form action="<%=basePath%>enterprise/enterpriseAuthentication.shtml"
		method="post" enctype="multipart/form-data">
		<input type="file" name="businessLicenseImg"> <input
			type="file" name="transportImg"> <input type="file"
			name="doorImg"> <input type="text" name="client_type"
			value="1"> <input type="text" name="data"
			value='{"userId": "7714d0d83c7f47f4bcfac62b9a1bf101","name": "黄竹的鸭场","type": "1","province": "19","city": "228","area": "1147","street": "黄竹老家","phone": "13888888888","licenseno": "A647435353","certifies": "dsg435436","permits": "fds54567","status": "1","principalName": "黄竹"}' />
		<input type="submit" value="上传">
	</form>
	<!-- 添加仓库 -->
	<h2>添加仓库测试</h2>
	<form action="<%=basePath%>mjWarehouseCtl/addMjWarehouse.shtml"
		method="post" enctype="multipart/form-data">
		<input type="file" name="file"> <input type="text"
			name="client_type" value="1"> <input type="text" name="data"
			value='{"area":"1","city":"1","contacts":"张三","id":"1","imgurl":"imgUrl","isinvoice":"0","latitude":"111","longitude":"112","name":"qw","phone":"15535355533","province":"1","remark":"备注","street":"北京市","userId":"7714d0d83c7f47f4bcfac62b9a1bf101","warehouseProperty":[{"type":"1","attribute":"1","value":"","typeName":"仓库类型","attributeName":"驶入式"},{"type":"1","attribute":"1","value":"","typeName":"仓库类型","attributeName":"横梁式"},{"type":"2","attribute":"1","value":"","typeName":"仓库增值服务","attributeName":"城配"},{"type":"3","attribute":"1","value":"2312","typeName":"仓库面积","attributeName":"常温"},{"type":"4","attribute":"1","value":"12","typeName":"价格","attributeName":"/天/平"}]}' />
		<input type="submit" value="上传">
	</form>

	<h2>添加车辆测试</h2>
	<form action="<%=basePath%>mjCarinfoCtl/addMjCarinfo.shtml"
		method="post" enctype="multipart/form-data">
		<input type="file" name="imgUrl"> <input type="file"
			name="drivingImg"> <input type="file" name="transportImg">
		<input type="text" name="client_type" value="1"> <input
			type="text" name="data"
			value='{"bulky":"11111","carno":"22224","category":"1","driver":"5555","heavy":"1","latitude":"1","longitude":"1","phone":"55555","status":2,"type":"1","userId":"7714d0d83c7f47f4bcfac62b9a1bf101","vehicle":"2"}' />
		<input type="submit" value="上传">
	</form>

	<h2>用户修改个人头像</h2>
	<form action="<%=basePath%>loginCtl/changHeadPic.shtml" method="post" enctype="multipart/form-data">
		<input type="file" name="file"> 
		<input type="text" name="client_type" value="1"> 
		<input type="text" name="data" value='{"userId":"7714d0d83c7f47f4bcfac62b9a1bf101"}' /> 
		<input type="submit" value="上传">
	</form>


	<h2>发布车源</h2>
	<form action="<%=basePath%>mjCarinfoCtl/addCarResource.shtml"
		method="get">
		<input type="text" name="client_type" value="1"> <input
			type="text" name="data"
			value='{ "_version_": "1512523368505540609","certificAtion": "1","coldStoreFlag": "1","cube": "1","fromArea": "379","fromAreaName": "西城区","fromCity": "33","fromCityName": "北京市","fromProvince": "2","fromProvinceName": "北京市","goodsTime": 1442221547000,"goodsType": "1", "installEtime": 936979200000,"installStime": 936979200000,"isinvoice": "2", "price": 1,"priceType": "1","toArea": "4700","toAreaName": "零陵区", "toCity": "228","toCityName": "永州市","toProvince": "19","toProvinceName": "湖南省","userId": "c413b4b93c674597a563e704090705ef", "userImgUrl": "http://img1.2345.com/duoteimg/qqTxImg/2013/12/ka_3/04-054658_103.jpg","userName": "王永吉","weight": 1,"fromStreet": "湖南省永州市零陵区","toStreet": "湖南省永州市零陵区","startTime": "1990-01-05","endTime": "1990-01-05","contacts": "黄竹","phone": "18669001623","remark": "这里是备注", "carId": "38c816a3c2cb43d3a41a28aa861da42a"}' />
		<input type="submit" value="上传">
	</form>
	
	<hr style="font-size:14px;"/>
	
	<h2>货找车-我要找车搜索</h2>
    <form action="<%=basePath%>searchCarCtl/searchCar.shtml" method="post">
		<input type="text" name="client_type" value="2">
		<input type="text" name="uuid" value="1">
		<input  type="text" name="data" value='{"startNo":"0","pageSize":"10","userId":"7714d0d83c7f47f4bcfac62b9a1bf101"}'/>
		<input type="submit" value="查询">
	</form>
	
	<h2>用户提现</h2>
    <form action="<%=basePath%>mjTransferCtl/transferCash.shtml" method="post">
		<input type="text" name="client_type" value="1">
		<input  type="text" name="data" value='{"cardholder":"周玉芳","bankBranchName":"厦门银行股份有限公司重庆南岸支行","userId": "c413b4b93c674597a563e704090705ef","payPassword":"123456","amount": "10","withdrawBankName": "厦门银行","withdrawCardNo": "6222001901100106378"}'/>
		<input type="submit" value="提现">
	</form>
  </body>
</html>
