layui.use(['form', 'layer', 'table', 'laytpl'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		table = layui.table;

	//题库列表
	var tableIns = table.render({
		elem: '#questionList',
		url: 'http://10.2.244.72:8080/Question/queryQuestionByKeyword',
		cellMinWidth: 95,
		method:"post",
		page: {
			layout: ['count', 'prev', 'page', 'next'],
			curr: 1, //设定初始在第 1 页
			limit: 10, //每页多少数据
			groups: 5 //只显示 5 个连续页码
		},
		height: "full-125",
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
					field: 'type',
					title: '题目类型',
					align: 'center',
					templet: function(d) {
						if (d.type == "0") {
							return "选择题";
						} else if (d.type == "1") {
							return "填空题";
						} else if (d.type == "2") {
							return "设计题";
						} 
					}
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
					field: 'publisher',
					title: '出题人',
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

	//关键词搜索
	$(".search_btn").on("click", function() {
		
	});
	
	form.on("select",function(data){
		var types = $("#type").val();
		tableReload(types);
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
		layui.layer.full(index);
		window.sessionStorage.setItem("index", index);
		//改变窗口大小时，重置弹窗的宽高，防止超出可视区域（如F12调出debug的操作）
		$(window).on("resize", function() {
			layui.layer.full(window.sessionStorage.getItem("index"));
		})
	}
	
	//列表操作
	table.on('tool(questionList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		if (layEvent === 'edit') { //编辑
			editQuestion(data);
		} 
	});
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
				if(data.type===0){//是选择题
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
	
	function tableReload(types){
		var tableIns = table.render({
			elem: '#questionList',
			url: 'http://10.2.244.72:8080/Question/queryQuestionByType',
			cellMinWidth: 95,
			method:"post",
			where:{
				type:types
			},
			page: {
				layout: ['count', 'prev', 'page', 'next'],
				curr: 1, //设定初始在第 1 页
				limit: 10, //每页多少数据
				groups: 5 //只显示 5 个连续页码
			},
			height: "full-125",
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
						field: 'type',
						title: '题目类型',
						align: 'center',
						templet: function(d) {
							if (d.type == "0") {
								return "选择题";
							} else if (d.type == "1") {
								return "填空题";
							} else if (d.type == "2") {
								return "设计题";
							} 
						}
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
						field: 'publisher',
						title: '出题人',
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
	
	
	
	
	
})
