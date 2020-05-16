layui.use(['form', 'layer', 'table', 'laytpl','upload'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		upload = layui.upload;
		table = layui.table;
        var publisher = getCookie("username");
	//题库列表
	table.render({
		elem: '#questionList',
		url: 'http://localhost:8080/Question/queryQuestionByKeyword',
		cellMinWidth: 95,
		//toolbar: '#exportOut',
		method:"post",
		id:'questionListTable',
		where:{publisher:publisher},
		page: true,
		//height: "full-125",
		// limits: [5,10, 15, 20, 25],
		// limit: 10,
		cols: [
			[{
					type: "checkbox",
					fixed: "left",
					width: 50
				},
				{
					field: 'qid',
					title: '编号',
					align: 'center',
					width: 75
				},
				{
					field: 'title',
					title: '题目'
				},
				{
					field: 'questiontype',
					title: '题目类型',
					align: 'center',
				},
				{
					field: 'answer',
					title: '题目答案',
					align: 'center'
				},
				{
					field: 'difficultyLevel',
					title: '难易程度',
					align: 'center',
					templet: function(d) {
						if (d.difficultyLevel == "0") {
							return "初级题目";
						} else if (d.difficultyLevel == "1") {
							return "中级题目";
						} else if (d.difficultyLevel == "2") {
							return "高级题目";
						} 
					}
				},
				{
					field: 'birthday',
					title: '添加时间',
					align: 'center'
					// templet: function(d) {
					// 	return d.masteryLevel*100+"%"
					// }
				},
				{
					title: '操作',
					minWidth: 175,
					templet: '#questionListBar',
					fixed: "right",
					align: "center"
				}
			]
		]
	});
	//列表操作
	table.on('tool(questionList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		if (layEvent === 'edit') { //编辑
			editQuestion(data);
		}
	});
	//批量导出
	$("#exportOutImpl").click(function() {
		var checkStatus = table.checkStatus('questionListTable');
		if(checkStatus.data.length===0){
			layer.msg("请选择要导出的数据",{time:2000});
		}else{
			var qids=[];
			for(var x=0;x<checkStatus.data.length;x++){
				qids.push(checkStatus.data[x].qid)
			};
			console.log(qids);
			$.post("http://localhost:8080/export/exportQuestion",{qids:qids},function(res){
				layer.msg(res.msg,{time:1000});
		  })
		}
	})
	//批量导入
	 upload.render({ //允许上传的文件后缀
	    elem: '#excel'
	    ,url: 'http://localhost:8080/export/exportInQuestion'
	    ,accept: 'file' //普通文件
	    ,exts: 'xlsx|xls' //只允许上传excel文件
		,data: {
           username: publisher
         }
	    ,done: function(res){
	      layer.msg(res.msg,{time:2000})
	    }
	  });
	
	
    //打开这个界面,就去向服务器请求正常启用的题型
	$.get("http://localhost:8080/questionType/queryAllEnableQT",{username:publisher},function(data){
			if(data!=null){
				for(var x=0;x<data.length;x++){
					$("#type").append("<option value="+data[x].tid+" >"+data[x].name+"</option>");
				}
			}
		 form.render();	
		})
    //获取用户所有的题库
    $.post("http://localhost:8080/questionBank/selectAllEnableQBByUserName",{username:publisher},function(data){
		if(data!=null){
			for(var x=0;x<data.length;x++){
				$("#qbname").append("<option value="+data[x].qbid+" >"+data[x].qbname+"</option>");
			}
		}
		form.render();	
	})
	
	form.on("select",function(data){
		var types = $("#type").val();
		var qbid = $("#qbname").val();
		if(types==="全部"){
			types = null;
		};
		if(qbid ==="全部"){
			qbid = null;
		}
		tableReload(types,qbid);
	})
	
	//添加题目的点击事件
	$(".addNews_btn").click(function() {
		addQuestion();
	})
	
	//打开增加题目页面
	function addQuestion(edit) {
		var index = layui.layer.open({
			title: "添加题目",
			type: 2,
			area:['80%','80%'],
			content: "questionAdd.html",
			success: function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				setTimeout(function() {
					layui.layer.tips('点击此处返回题目列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		})
		//layui.layer.full(index);
		//window.sessionStorage.setItem("index", index);
		//改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
		// $(window).on("resize", function() {
		// 	layui.layer.full(window.sessionStorage.getItem("index"));
		// })
	}
	
	
     //编辑题目信息
	function editQuestion(data) {
		var indexEditQuestion = layui.layer.open({
			title: "编辑题目",
			type: 2,
			content: "editQuestion.html",
			area: ['90%', '90%'],
			success: function(layero, indexEditUser) { //弹出后回调函数
				var body = layui.layer.getChildFrame('body', indexEditQuestion); //得到子页面
				body.find("#qid").val(data.qid); //id
				body.find("#title").val(data.title); //题目
				body.find(".difficultyLevel input[value=" + data.difficultyLevel + "]").prop("checked", "checked"); //难易程度
				body.find("#type").val(data.type) //难易程度
				body.find("#about").val(data.about); //标签
				body.find("#answer").val(data.answer); //题目答案
				body.find("#analysis").text(data.analysis); //题目解析
				if(data.optionA!=null&&optionB!=null){//是选择题
					$("#selectOrText").hide();
					$("#selectHtml").show();//将选项展示
					body.find(".selectOptionA").val(data.optionA);
					body.find(".selectOptionB").val(data.optionB);
					body.find(".selectOptionC").val(data.optionC);
					body.find(".selectOptionD").val(data.optionD);
				}else{//填空题和设计题
					body.find("#content").val(data.content); //具体描述
					$("#selectOrText").attr("style","display:block;");//选项展示
					$("#selectHtml").attr("style","display:none;");//将具体要求进行隐藏
				}
				form.render();
				setTimeout(function() {
					layui.layer.tips('点击此处返回题目列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		})
	}
	//表格重载
	function tableReload(types,qbid){
		//alert(types);
		var tableIns = table.render({
			elem: '#questionList',
			url: 'http://localhost:8080/Question/queryQuestionByType',
			cellMinWidth: 95,
			//toolbar: '#exportOut',
			method:"post",
			where:{
				type:types,
				qbid:qbid,
				publisher:publisher
			},
			page:true,
			//height: "full-125",
			limits: [10, 15, 20, 25],
			limit: 20,
			id: "questionListTable",
			cols: [
				[{
						type: "checkbox",
						fixed: "left",
						width: 50
					},
					{
						field: 'qid',
						title: '编号',
						align: 'center',
						width: 75
					},
					{
						field: 'title',
						title: '题目'
					},
					{
						field: 'questiontype',
						title: '题目类型',
						align: 'center',
						
					},
					{
						field: 'answer',
						title: '题目答案',
						align: 'center'
					},
					{
						field: 'masteryLevel',
						title: '掌握程度',
						align: 'center',
						templet: function(d) {
							return d.masteryLevel*100+"%"
						}
					},
					{
						field: 'difficultyLevel',
						title: '难易程度',
						align: 'center',
						templet: function(d) {
							if (d.difficultyLevel == "0") {
								return "初级题目";
							} else if (d.difficultyLevel == "1") {
								return "中级题目";
							} else if (d.difficultyLevel == "2") {
								return "高级题目";
							} 
						}
					},
					{
						title: '操作',
						minWidth: 175,
						templet: '#questionListBar',
						fixed: "right",
						align: "center"
					}
				]
			]
		});
	}
	
	function getCookie(name){
	  var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	  if(arr=document.cookie.match(reg)){
		   return unescape(arr[2]);
	  }else{
		 return null;
	  }
	}
	
})
