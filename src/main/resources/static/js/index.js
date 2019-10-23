//此方法是实现增加学生的操作，界面会弹框
function add(){
	/*layui.use('layer', function(){
		var layer = layui.layer;
		layer.open({
			type: 2, 
			content: '/CRUD/school_add.html' ,
			area:['80%','80%'],
			offset: 'auto',
			title:'录入信息'
		});
	});  */
	 window.location.href="/School/add";
}

//实现模糊查询
function find(){
	var keyword = $("#keyword").val();
	layui.use('table', function(){
		var table = layui.table;
		//第一个实例
		table.render({
		 elem: '#demo'
		 ,url: "/School/listSchool?keyword="+keyword+"" //数据接口
		 ,page: true //开启分页
		 ,cols: [[ //表头
		           {field:'sid', title:'学校编号',  sort: true}
		 	      ,{field:'name', title:'学校名称'}
		 	      ,{field:'author', title:'学校负责人'}
		 	      ,{field:'tel',title:'学校电话'}	
		 	      ,{field:'content', title:'学校简介'}
		 	      ,{field:'photo', title:'学校照片',templet : '<div><img src="<%=basePath%>/upload/image/{{d.photopath}}"></div>'}
		 	      ,{field:'provice', title:'学校省份'}
		 	      ,{fixed:'right',title:'操作',toolbar : '#barDemo'}
			]]
		});

	});
	window.location.reload();
}




