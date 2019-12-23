layui.use(['form', 'layer', 'table', 'laytpl'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		table = layui.table;

	//用户列表
	var tableIns = table.render({
		elem: '#userList',
		url: 'http://10.2.244.72:8080/User/queryUserByKeyword',
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
		id: "userListTable",
		cols: [
			[{
					type: "checkbox",
					fixed: "left",
					width: 50
				},
				{
					field: 'id',
					title: '编号',
					align: 'center',
					width: 80
				},
				{
					field: 'username',
					title: '用户名',
					width: 120,
					align: 'center'
				},
				{
					field: 'sex',
					title: '性别',
					width: 80,
					align: 'center'
				},
				{
					field: 'level',
					title: '用户等级',
					align: 'center',
					width: 150,
					templet: function(d) {
						if (d.level == "0") {
							return "注册会员";
						} else if (d.level == "1") {
							return "中级会员";
						} else if (d.level == "2") {
							return "高级会员";
						} else if (d.level == "3") {
							return "钻石会员";
						} else if (d.level == "4") {
							return "超级会员";
						}
					}
				},
				{
					field: 'email',
					title: '用户邮箱',
					align: 'center',
					minWidth: 150,
					templet: function(d) {
						return '<a class="layui-blue" href="mailto:' + d.email + '">' + d.email + '</a>';
					}
				},
				{
					field: 'userEndTime',
					title: '最后登录时间',
					align: 'center',
					minWidth: 150
				},
				{
					field: 'birthday',
					title: '注册日期',
					align: 'center',
					minWidth: 150
				},
				{
					field: 'status',
					title: '用户状态',
					width: 110,
					templet: '#checkboxTpl',
				},
				{
					title: '操作',
					templet: '#userListBar',
					// fixed: "right",
					align: "center"
				}
			]
		]
	});

	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		if ($(".searchVal").val() != '') {
			var frontkeyword = $(".searchVal").val()
			tableIns.reload({
				page: {
					curr: 1 //重新从第 1 页开始
				},
				where: {
					keyword: frontkeyword //搜索的关键字
				}
			})
		} else {
			layer.msg("请输入搜索的内容");
		}
	});

	//添加用户
	function addUser(edit) {
		var index = layui.layer.open({
			title: "添加用户",
			type: 2,
			content: "../userAdd.html",
			success: function(layero, index) {
				var body = layui.layer.getChildFrame('body', index);
				if (edit) {
					body.find(".userName").val(edit.userName); //登录名
					body.find(".userEmail").val(edit.userEmail); //邮箱
					body.find(".userSex input[value=" + edit.userSex + "]").prop("checked", "checked"); //性别
					body.find(".userGrade").val(edit.userGrade); //会员等级
					body.find(".userStatus").val(edit.userStatus); //用户状态
					body.find(".userDesc").text(edit.userDesc); //用户简介
					form.render();
				}
				setTimeout(function() {
					layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
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





	$(".addNews_btn").click(function() {
		addUser();
	})

	//批量封禁/解封
	$(".delAll_btn").click(function() {
		var checkStatus = table.checkStatus('userListTable'),
			data = checkStatus.data,
			newsId = [];
		if (data.length > 0) {
			for (var i in data) {
				newsId.push(data[i].id);
			}
			layer.confirm('确定封禁选中的用户？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				$.ajax({
					url: "http://10.2.244.72:8080/User/changeStatus",
					data: {
						array: newsId
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
					  tableIns.reload();
					  layer.close(index);
					}
				})
			})
		} else {
			layer.msg("请选择需要封禁的用户");
		}
	})

	//列表操作
	table.on('tool(userList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		if (layEvent === 'edit') { //编辑
			editUser(data);
		}
	});
	//编辑用户信息
	function editUser(data) {
		var indexEditUser = layui.layer.open({
			title: "编辑用户",
			type: 2,
			content: "../editUser.html",
			area: ['90%', '80%'],
			success: function(layero, indexEditUser) { //弹出后回调函数
				var body = layui.layer.getChildFrame('body', indexEditUser); //得到子页面
				body.find("#id").val(data.id); //id
				body.find("#username").val(data.username); //登录名
				body.find("#email").val(data.email); //邮箱
				body.find(".userSex input[value=" + data.sex + "]").prop("checked", "checked"); //性别
				body.find("#level").val(data.level); //会员等级
				body.find("#status").val(data.status); //用户状态
				body.find("#descrition").text(data.descrition); //用户简介
				form.render();
				setTimeout(function() {
					layui.layer.tips('点击此处返回用户列表', '.layui-layer-setwin .layui-layer-close', {
						tips: 3
					});
				}, 500)
			}
		})
	}
	//更改用户状态
	function changeUserStatus(data) {
		$.ajax({
			url: "http://10.2.244.72:8080/User/changeStatus",
			data: {
				array: data
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
			  tableIns.reload();
			  layer.close(index);
			}
		})
	}
	//锁定解锁用户	
	form.on('checkbox(lockDemo)', function(obj) {
		 layer.confirm("确定要执行该操作吗?", function(index) {
			var userid=obj.value;
			changeUserStatus(userid)
		 })
	});


})
