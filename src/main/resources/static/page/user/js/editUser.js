layui.use(['form', 'layer'], function() {
	var form = layui.form
	layer = parent.layer === undefined ? layui.layer : top.layer,
		$ = layui.jquery;
	form.on("submit(editUser)", function(data) {
		var id = $("#id").val();
		var username = $("#username").val();
		var email = $("#email").val();
		var sex = data.field.sex;
		var level = $("#level").val();
		var status = $("#status").val();
		var descrition = $("#descrition").text();
		layer.confirm('确定要修改的数据了吗?', {
			icon: 3,
			title: '提示'
		}, function(index) {
			$.post("http://localhost:8080/User/editUser", {
				id: id,
				username: username, //登录名
				email: email, //邮箱
				sex: sex, //性别
				level: level, //会员等级
				status: status, //用户状态
				descriton: descrition, //用户简介
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
