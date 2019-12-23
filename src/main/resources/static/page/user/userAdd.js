layui.use(['form','layer'],function(){
    var form = layui.form
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    form.on("submit(addUser)",function(data){
		var password = $("#password").val();
		var enterpassword =$("#enterpassword").val()
		if(password!=enterpassword){
			top.layer.msg("两次输入密码不匹配！");
		}else{
			//弹出loading
			var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
			 //实际使用时的提交信息
			 $.post("http://10.2.244.72:8080/User/addUser",{
			     username : $(".userName").val(),  //登录名
				 password : $(".password").val(),
			     email : $(".userEmail").val(),  //邮箱
			     sex : data.field.sex,  //性别
			     level : data.field.userGrade,  //会员等级
			     status : data.field.userStatus,    //用户状态
			     descriton : $("#descriton").text(),    //用户简介
			 },function(res){
			      top.layer.close(index);
			      top.layer.msg(res.msg);
			      layer.closeAll("iframe");
			      //刷新父页面
			      parent.location.reload();
			 })
			// setTimeout(function(){
			//     top.layer.close(index);
			//     top.layer.msg("用户添加成功！");
			//     layer.closeAll("iframe");
			//     //刷新父页面
			//     parent.location.reload();
			// },2000);
			return false;
		}
    })
	

    //格式化时间
    function filterTime(val){
        if(val < 10){
            return "0" + val;
        }else{
            return val;
        }
    }
    //定时发布
    var time = new Date();
    var submitTime = time.getFullYear()+'-'+filterTime(time.getMonth()+1)+'-'+filterTime(time.getDate())+' '+filterTime(time.getHours())+':'+filterTime(time.getMinutes())+':'+filterTime(time.getSeconds());

})