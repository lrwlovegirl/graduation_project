<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
		<!-- Google Chrome Frame也可以让IE用上Chrome的引擎: -->
		<meta name="renderer" content="webkit">
		<!--国产浏览器高速模式-->
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="author" content="穷在闹市" />
		<!-- 作者 -->
		<meta name="revised" content="穷在闹市.v3, 2019/05/01" />
		<!-- 定义页面的最新版本 -->
		<meta name="description" content="网站简介" />
		<!-- 网站简介 -->
		<meta name="keywords" content="搜索关键字，以半角英文逗号隔开" />
		<title>ADi达斯网上商城</title>

		<!-- 公共样式 开始 -->
		<link rel="stylesheet" type="text/css" href="../../css/base.css">
		<link rel="stylesheet" type="text/css" href="../../css/iconfont.css">
		<script type="text/javascript" src="../../framework/jquery-1.11.3.min.js"></script>
		<link rel="stylesheet" type="text/css" href="../../layui/css/layui.css">
		<script type="text/javascript" src="../../layui/layui.js"></script>
		<!-- 滚动条插件 -->
		<link rel="stylesheet" type="text/css" href="../../css/jquery.mCustomScrollbar.css">
		<script src="../../framework/jquery-ui-1.10.4.min.js"></script>
		<script src="../../framework/jquery.mousewheel.min.js"></script>
		<script src="../../framework/jquery.mCustomScrollbar.min.js"></script>
		<script src="../../framework/cframe.js"></script><!-- 仅供所有子页面使用 -->
		<!-- 公共样式 结束 -->
        <script src="../../js/ItemOneJS.js"></script>
        
	</head>

	<body>
		<div class="cBody">
			<div class="console">
					

				<script>
					layui.use('form', function() {
						var form = layui.form;
				
						//监听提交
						form.on('submit(formDemo)', function(data) {
							layer.msg(JSON.stringify(data.field));
							return false;
						});
					});
				</script>
			</div>
			
			<table class="layui-table">
				<thead>
					<tr>
						<th>订单编号</th>
						<th>订单总价</th>
						<th>收获人</th>
						<th>收获地址</th>
						<th>收获电话</th>
						<th>创建日期</th>
						<th>订单状态</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="tbd">	
				</tbody>
			</table>
			
			<!-- layUI 分页模块 -->
			<div id="pages"></div>
			
			
			<!-- js部分 -->
		<script type="text/javascript">
		
	function showReocrd(pageNo,pageSize){
        $.get("/XIIX/orders/OrdersSplit.action",
        {
            pageNo:pageNo,
            pageSize:pageSize
        },
        function (data) {
        	//加载后台返回的List集合数据
        	
            for (var i = 0; i < data.length; i++) {

                var td = $("<td></td>").text(data[i].oid);
                var td2 = $("<td></td>").text(data[i].allpay);
                var td3 = $("<td></td>").text(data[i].username);
                var td4 = $("<td></td>").text(data[i].address);
                var td5 = $("<td></td>").text(data[i].telephone);
                var td6 = $("<td></td>").text(data[i].cdate);
                if(data[i].status==0){
                  var td7 = $("<td></td>").text("待发货");
                  var td8 = $("<td></td>").html("<button class='layui-btn layui-btn-xs' onclick='findByOid(\""+data[i].oid+"\")' id='btn1' >查看详情</button><button class='layui-btn layui-btn-xs' onclick='sendgoods(\""+data[i].oid+"\",this)' id='btn2' >发货</button></td></tr>");
                }
                if(data[i].status==1){
                  var td7 = $("<td></td>").text("已发货,等待收获");
                     var td8 = $("<td></td>").html("<button class='layui-btn layui-btn-xs' onclick='findByOid(\""+data[i].oid+"\")' id='btn1' >查看详情</button></td></tr>");
                }
                if(data[i].status==2){
                   var td7 = $("<td></td>").text("订单完成");
                   var td8 = $("<td></td>").html("<button class='layui-btn layui-btn-xs' onclick='findByOid(\""+data[i].oid+"\")' id='btn1' >查看详情</button></td></tr>");
                }
                
               
                var tr = $("<tr></tr>").append(td, td2, td3,td4,td5,td6,td7,td8);
               
                $('#tbd').append(tr);
            }
        },
        "json"
    );
}
		
		    console.log("订单个数： "+"${ordersCount}")
			var total = ${ordersCount};
			//先初始化加载首页，十条数据
			showReocrd(1, 5);
		
			layui.use([ 'laypage', 'jquery' ], function() {
		
				var laypage = layui.laypage,
					$ = layui.$;
				$("#pages").each(function(i, the) {
					laypage.render({
						elem : pages, //注意，这里的 test1 是 ID，不用加 # 号
						count : total, //数据总数，从服务端得到
						limit : 5, //每页显示条数
						limits : [ 10, 20, 30 ],
						curr : 1, //起始页
						groups : 5, //连续页码个数
						prev : '上一页', //上一页文本
						netx : '下一页', //下一页文本
						first : 1, //首页文本
						last : 100, //尾页文本
						layout : [ 'prev', 'page', 'next', 'limit', 'refresh', 'skip' ], //跳转页码时调用
						jump : function(obj, first) { //obj为当前页的属性和方法，第一次加载first为true
							//非首次加载 do something
							if (!first) {
								//清空以前加载的数据
								$('#tbd').empty();
								//调用加载函数加载数据
								showReocrd(obj.curr, obj.limit);
							}
						}
					});
				})
			})	
		   
		 
		</script>
			
		</div>
	</body>

</html>