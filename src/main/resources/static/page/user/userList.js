layui.use(['form', 'layer', 'table', 'laytpl'], function() {
	var form = layui.form,
		layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery,
		laytpl = layui.laytpl,
		table = layui.table;

	//用户列表
	var tableIns = table.render({
		elem: '#userList',
		url: 'http://10.2.244.72:8080/User/queryAllUser',
		cellMinWidth: 95,
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
					title: '用户编号',
					align: 'center'
				},
				{
					field: 'username',
					title: '用户名'
				},
				{
					field: 'sex',
					title: '用户性别',
					templet: function(d) {
						if (d.sex == 0) {
							return "女";
						} else if (d.sex == 1) {
							return "男";
						} else if (d.sex == 2) {
							return "保密";
						}
					}
				},
				{
					field: 'status',
					title: '用户状态',
					templet: function(d) {
						return d.status == "0" ? "正常使用" : "限制使用";
					}
				},
				{
					field: 'level',
					title: '用户等级',
					align: 'center',
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
					title: '操作',
					minWidth: 175,
					templet: '#userListBar',
					fixed: "right",
					align: "center"
				}
			]
		]
	});

	//搜索【此功能需要后台配合，所以暂时没有动态效果演示】
	$(".search_btn").on("click", function() {
		if ($(".searchVal").val() != '') {
			table.reload("newsListTable", {
				page: {
					curr: 1 //重新从第 1 页开始
				},
				where: {
					key: $(".searchVal").val() //搜索的关键字
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

	//批量删除
	$(".delAll_btn").click(function() {
		var checkStatus = table.checkStatus('userListTable'),
			data = checkStatus.data,
			newsId = [];
		if (data.length > 0) {
			for (var i in data) {
				newsId.push(data[i].newsId);
			}
			layer.confirm('确定删除选中的用户？', {
				icon: 3,
				title: '提示信息'
			}, function(index) {
				// $.get("删除文章接口",{
				//     newsId : newsId  //将需要删除的newsId作为参数传入
				// },function(data){
				tableIns.reload();
				layer.close(index);
				// })
			})
		} else {
			layer.msg("请选择需要删除的用户");
		}
	})

	//列表操作
	table.on('tool(userList)', function(obj) {
		var layEvent = obj.event,
			data = obj.data;
		if (layEvent === 'edit') { //编辑
			editUser(data);
		} else if (layEvent === 'usable') { //启用禁用
			var _this = $(this),
				usableText = "是否确定禁用此用户？",
				btnText = "已禁用";
			if (_this.text() == "已禁用") {
				usableText = "是否确定启用此用户？",
					btnText = "已启用";
			}
			layer.confirm(usableText, {
				icon: 3,
				title: '系统提示',
				cancel: function(index) {
					layer.close(index);
				}
			}, function(index) {
				_this.text(btnText);
				changeUserStatus(data.id);
				layer.close(index);
			}, function(index) {
				changeUserStatus(data.id);
				layer.close(index);
			});
		} 
	});
     //编辑用户信息
	function editUser(data) {
		var indexEditUser = layui.layer.open({
			title: "编辑用户",
			type: 2,
			content: "user/editUser.html",
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
	function changeUserStatus(data){
		$.post("http://10.2.244.72:8080/User/changeStatus", {
			array: data,
		}, function(res) {
			layer.msg(res.msg, {
				time: 2000
			});
		})
	}
	
	
	
})
