layui.use(['form', 'layer'], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
		
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
		layer.confirm('确定要修改的数据了吗?', {
			icon: 3,
			title: '提示'
		}, function(index) {
			$.post("http://10.2.244.72:8080/Question/updateQuestion", {
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
				optionD:optionD
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
    	if(data.value==0){//选择题
    		$("#selectOrText").hide();
    		$("#selectHtml").show();
    	}else{
			$("#selectOrText").show();
			$("#selectHtml").hide();
		}
    })


	//格式化时间
	function filterTime(val) {
		if (val < 10) {
			return "0" + val;
		} else {
			return val;
		}
	}
	//定时发布
	var time = new Date();
	var submitTime = time.getFullYear() + '-' + filterTime(time.getMonth() + 1) + '-' + filterTime(time.getDate()) + ' ' +
		filterTime(time.getHours()) + ':' + filterTime(time.getMinutes()) + ':' + filterTime(time.getSeconds());

})
