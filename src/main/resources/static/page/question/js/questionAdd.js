layui.use(['form','layer','layedit'],function(){
        var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
		layedit = layui.layedit,
        $ = layui.jquery;
		form.render();
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
	   //弹出loading
	   //var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
	    //实际使用时的提交信息
		$.ajax({
				url: "http://10.2.244.72:8080/Question/createQuestion",
				data: {
					title : title,  //登录名
					about : about,
					type : type,  //邮箱
					difficultyLevel : difficultyLevel,  //性别
					content : content,  //会员等级
					answer : answer,    //用户状态
					optionA : optionA ,   //用户简介
					optionB : optionB ,
					optionC : optionC ,
					optionD : optionD ,
					analysis : analysis
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
	form.on("select",function(data){
		if(data.value==0){//选择题
			$("#selectOrText").empty();
			$("#selectOrText").append(selectHtml);
		}else if(data.value==1){ //填空题
			$("#selectOrText").empty();
			$("#selectOrText").append(textHtml);
		}else if(data.value==2){//设计题
			$("#selectOrText").empty();
			$("#selectOrText").append(textHtml);
		}
	})

   
    
})