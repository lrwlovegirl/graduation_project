layui.use(['form', 'layer'], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
	var username =getCookie("username");
	var qid = $("#qid").val();
	//获取所有的题型
	$.get("http://localhost:8080/questionType/queryAllEnableQT",{username:username},function(data){
			if(data!=null){
				for(var x=0;x<data.length;x++){
					$("#type").append("<option value="+data[x].tid+" >"+data[x].name+"</option>");
				}
			}
		 form.render();	
		})	
	//获取所有的知识点
	$.post("http://localhost:8080/kpoint/findAllKpointByQid",{qid:qid,username:username},function(data){
		console.log(data)
		for(var x=0;x<data.length;x++){
			if(data[x].mark){
				$("#about").append("<option value="+data[x].kid+" selected "+" >"+data[x].kname+"</option>");
			}else{
				$("#about").append("<option value="+data[x].kid+" >"+data[x].kname+"</option>");
			}
		}	
		form.render();
	})	
	//获取所有的题库
	$.post("http://localhost:8080/questionBank/findAllQuestionBankByQid",{qid:qid,username:username},function(data){
		for(var x=0;x<data.length;x++){
			if(data[x].mark){
				$("#qbank").append("<option value="+data[x].qbid+" selected "+" >"+data[x].qbname+"</option>");
			}else{
				$("#qbank").append("<option value="+data[x].qbid+" >"+data[x].qbname+"</option>");
			}
		}	
		form.render();
	})	
// ####################################################################################################
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
		
		
	form.on("submit(editQuestion)", function(data) {
		var qid = $("#qid").val();
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
		var qbid = $("#qbank").val();
		layer.confirm('确定要修改的数据了吗?', {
			icon: 3,
			title: '提示'
		}, function(index) {
			$.post("http://localhost:8080/Question/updateQuestion", {
				qid: qid,
				title:title,
				about:about,
				type:type,
				difficultyLevel:difficultyLevel,
				content:content,
				answer:answer,
				analysis:analysis,
				optionA:optionA,
				optionB:optionB,
				optionC:optionC,
				optionD:optionD,
				qbid:qbid
				
			}, function(res) {
				layer.msg(res.msg, {
					time: 2000
				});
				//layer.close(indexEditUser);
				setTimeout(function() {
					layer.closeAll('iframe')
				}, 1000); //关闭所有的iframe层
				//刷新父页面
				setTimeout(function() {
					parent.location.reload()
				}, 1000);
			})
		});
		return false;
	})

    form.on("select",function(data){
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

	

})
