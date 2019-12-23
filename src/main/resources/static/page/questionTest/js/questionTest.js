layui.use(['util', 'laydate', 'layer'], function(){
	var util = layui.util
	  ,laydate = layui.laydate
	  ,layer = layui.layer;
	
	 var thisTimer, setCountdown = function(y, M, d, H, m, s){
	    var endTime = new Date(y||0, M||0, d||1, H||0, m||0, s||0) //结束日期
	    ,serverTime = new Date(); //假设为当前服务器时间，这里采用的是本地时间，实际使用一般是取服务端的
	     
	    clearTimeout(thisTimer);
	    util.countdown(endTime, serverTime, function(date, serverTime, timer){
			console.log(date)
			
	      var str =  "距离考试结束时间还剩: "+date[1] + '时' +  date[2] + '分' + date[3] + '秒';
	      lay('#test2').html(str);
	      thisTimer = timer;
	    });
	  };
	  setCountdown(0,0,0,1,0,0);
	  
	  
	  
	  
	  
	  
	  
	  
//打开考试通知	  
$(function() {
	  layer.confirm('考试将有10道选择题,5道填空题,2道设计题,确定进行考试吗？', {
	  	icon: 3,
	  	title: '提示信息'
	  }, function(index) {
		  $.ajax({
			  url:"http://10.2.244.72:8080/Question/selectQuestion",
			  data: {},
			  dataType: "json",
			  type: "post",
			  traditional: true, //防止深度序列化
			  cache: false,
			  async: false,
			  success: function(data) {
			  	layer.msg(data.msg, {
			  	 	 	time: 2000
			   });
		  })//ajax结束
		  layer.close(index);
	   }) 
	  })//确定框
});


