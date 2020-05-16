layui.use(['form','layer','layedit'],function(){
        var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
		layedit = layui.layedit,
        $ = layui.jquery;
		form.render();
		var username =getCookie("username")
		
		
		
    form.on("submit(addQuestion)",function(data){
		var title=$("#title").val();
		var about=$("#about").val();
		var type=$("#type").val();
		var difficultyLevel = data.field.difficultyLevel;
		var content =$("#content").val();
		var answer = $("#answer").val();
		var analysis =$("#analysis").val();
		var optionA = $(".selectOptionA").val();
		var optionB = $(".selectOptionB").val();
		var optionC = $(".selectOptionC").val();
		var optionD = $(".selectOptionD").val();
		var qbank = $("#qbank").val();
	   //弹出loading
	   //var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	    //实际使用时的提交信息
		$.ajax({
				url: "http://localhost:8080/Question/createQuestion",
				data: {
					title : title,  
					about : about,
					type : type,  
					difficultyLevel : difficultyLevel,  //性别
					content : content,  //会员等级
					answer : answer,    //用户状态
					optionA : optionA ,   //用户简介
					optionB : optionB ,
					optionC : optionC ,
					optionD : optionD ,
					analysis : analysis,
					publisher: username,
					qbid:qbank
				},
				dataType: "json",
				type: "post",
				traditional: true, //防止深度序列化
				cache: false,
				async: false,
				success: function(data) {
					layer.msg(data.msg, {
					 	 	time: 2000
				 });
				 setTimeout(function(){
				     layer.closeAll("iframe");
				     //刷新父页面
				     parent.location.reload();
				 },2000);
				}
			})
			 return false;
    })
	//获取所有的可用题型
	$.get("http://localhost:8080/questionType/queryAllEnableQT",{username:username},function(data){
		if(data!=null){
			for(var x=0;x<data.length;x++){
				$("#type").append("<option value="+data[x].tid+" >"+data[x].name+"</option>");
			}
		}
		form.render();
	})
	//获取所有的知识点
	$.post("http://localhost:8080/kpoint/findAllKpoint",{username:username},function(data){
		if(data!=null){
			for(var x=0;x<data.length;x++){
				$("#about").append("<option value="+data[x].kname+" >"+data[x].kname+"</option>");
			}
		}
		form.render();
	})
	//获取该用户所有的题库
	$.post("http://localhost:8080/questionBank/selectAllEnableQBByUserName",{username:username},function(data){
		if(data!=null){
			for(var x=0;x<data.length;x++){
				$("#qbank").append("<option value="+data[x].qbid+" >"+data[x].qbname+"</option>");
			}
		}
	})
	
	function getCookie(name){
	  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	  if(arr=document.cookie.match(reg)){
		   return unescape(arr[2]);
	  }else{
		 //
		 return null;
	  }
	}
	
	
	
	
	//题目类型点击事件
	var selectHtml =`
	<div class="layui-form-item">
         <label class="layui-form-label">选&nbsp;项&nbsp;&nbsp;&nbsp;A:</label>
    <div class="layui-input-block">
      <input type="text" name="option" lay-verify="title" autocomplete="off" placeholder="请输入A选项" class="layui-input selectOptionA">
    </div>
  </div>`+`
  <div class="layui-form-item">
         <label class="layui-form-label">B:</label>
    <div class="layui-input-block">
      <input type="text" name="option" lay-verify="title" autocomplete="off" placeholder="请输入B选项" class="layui-input selectOptionB">
    </div>
  </div>
  `+
  `<div class="layui-form-item">
         <label class="layui-form-label">C:</label>
    <div class="layui-input-block">
      <input type="text" name="option" lay-verify="title" autocomplete="off" placeholder="请输入C选项" class="layui-input selectOptionC">
    </div>
  </div>`
  +`
  <div class="layui-form-item">
         <label class="layui-form-label">D:</label>
    <div class="layui-input-block">
      <input type="text" name="option" lay-verify="title" autocomplete="off" placeholder="请输入D选项" class="layui-input selectOptionD">
    </div>
  </div>
  `;
var textHtml=`
<div class="layui-form-item layui-row layui-col-xs12">
			<label class="layui-form-label">具体要求</label>
			<div class="layui-input-block"">
				<textarea placeholder="请输入具体要求" name="content" class="layui-textarea userDesc"></textarea>
			</div>
		</div>
`;
	form.on("select(questiontypeFilter)",function(data){
		var type = data.value;
		$.post("http://localhost:8080/questionType/isSelectType",{type:type},function(res){
			if(res){
				$("#selectOrText").empty();
				$("#selectOrText").append(selectHtml);
			}else{
				$("#selectOrText").empty();
				$("#selectOrText").append(textHtml);
			}
		})
		
		// if(data.value==1){//选择题
		// 	$("#selectOrText").empty();
		// 	$("#selectOrText").append(selectHtml);
		// }else{
		// 	$("#selectOrText").empty();
		// 	$("#selectOrText").append(textHtml);
		// }
		// else if(data.value==1){ //填空题
		// 	$("#selectOrText").empty();
		// 	$("#selectOrText").append(textHtml);
		// }else if(data.value==2){//设计题
		// 	$("#selectOrText").empty();
		// 	$("#selectOrText").append(textHtml);
		// }
	})

   
    
})