<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加</title>
<%@ include file="/static/base/common.jspf"%>
<%--<script type="text/javascript" src="${ctx}/static/js/hp_form.js"></script>--%>
</head>
<body>
	<div class="body_main">
		<form class="layui-form layui-form-pane"  enctype="multipart/form-data" >
			<div class="layui-form-item">
				<label class="layui-form-label">昵称</label>
				<div class="layui-input-block">
					<input type="text" name="nickname" autocomplete="off"
						placeholder="请输入昵称" class="layui-input"  >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input type="text" name="userName" autocomplete="off"
						placeholder="请输入用户名" class="layui-input" onblur="checkUserName()" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="text" name="password" autocomplete="off"
						placeholder="请输入密码" class="layui-input" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话</label>
				<div class="layui-input-block">
					<input type="tel" name="tel"  autocomplete="off"
						placeholder="请输入电话" class="layui-input" onblur="checkTel()" >
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<select name="sex" lay-verify="required" id="sex">
<%--						<option value=""></option>--%>
						<option value="1" selected>男</option>
						<option value="-1">女</option>
					</select>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">邮箱</label>
				<div class="layui-input-block">
					<input type="text" name="email" autocomplete="off"
						placeholder="请输入邮箱" class="layui-input" lay-verify="email" >
				</div>
			</div>

			<div class="layui-upload">
				<label class="layui-form-label">头像</label>
<%--				<input type="hidden" name="file" id="pic">--%>
				<input type="file" class="layui-btn" id="upload" name="file">
			</div>


			<div class="layui-form-item">
				<label class="layui-form-label">状态</label>
				<div class="layui-input-block">
					<input type="checkbox" checked="" name="status" lay-skin="switch"
						lay-filter="switchTest" lay-text="可用|禁用">
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit="" lay-filter="demo1" id="sub">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/static/plugins/jquery.min.js"></script>
	<script type="text/javascript" src="${ctx}/static/js/axios.js"></script>
<script type="text/javascript">

	layui.use('form', function() {
		var form = layui.form;

		//通用弹出层表单提交方法
		form.on('submit(demo1)', function(data){
			console.log(data.field);
			var userInfo = JSON.stringify(data.field);
			var formData = new FormData()
			var img = document.getElementById("upload").files[0];
			formData.append("user",userInfo);
			formData.append("img",img)
			console.log(formData)
			$.ajax({
				type : "post",
				url : "${ctx}/user/addNginx",
				data : formData,
				processData : false,
				contentType : false,
				async:false,
				success : function(res){
					if (res.msg=="success") {
						parent.closeLayer(res.msg);
					}else{
						layer.msg('操作失败：' + res.msg, {icon: 2, time: 2000});
					}}
			})
			return false;
		})

	});


	<%--layui.use('upload', function(){--%>
	<%--	var upload = layui.upload;--%>
	<%--	//执行实例--%>
	<%--	upload.render({--%>
	<%--		elem: '#upload' //绑定元素--%>
	<%--		,url: '${pageContext.request.contextPath}/user/add' //上传接口--%>
	<%--		,accpet:'images'--%>
	<%--		,auto:false--%>
	<%--		,bindAction:'#sub'--%>
	<%--		,multiple:false--%>
	<%--		,before:function (){--%>
	<%--			this.data={--%>
	<%--				nickname:$("input[name='nickname']").val(),--%>
	<%--				userName:$("input[name='userName']").val(),--%>
	<%--				password:$("input[name='password']").val(),--%>
	<%--				tel:$("input[name='tel']").val(),--%>
	<%--				sex:$("#sex").val(),--%>
	<%--				email:$("input[name='email']").val(),--%>
	<%--				status:$("input[name='status']").val(),--%>
	<%--			}--%>
	<%--		}--%>
	<%--		,done: function(res){--%>
	<%--			//上传完毕回调--%>
	<%--			console.log(res)--%>
	<%--			if (res.msg=="success"){--%>
	<%--				parent.layer.msg(res.msg, {icon: 1, time: 1500});--%>
	<%--				let index = parent.layer.getFrameIndex(window.name);--%>
	<%--				//先得到当前iframe层的索引--%>
	<%--				parent.layer.close(index);--%>
	<%--				//再执行关闭--%>
	<%--			}--%>
	<%--			else {--%>
	<%--				layer.msg(res.msg, {icon: 2, time: 1500});--%>
	<%--			}--%>
	<%--		}--%>
	<%--		,error: function(res){--%>
	<%--			//请求异常回调--%>
	<%--			console.log(res)--%>
	<%--			layer.msg("未知异常", {icon: 2});--%>


	<%--		}--%>
	<%--	});--%>

	$('input').focus(function () {
		let Name = $(this).prop('name');
		$("input[name=Name]").css('color','black')
	})

	function checkTel(){
		let tel = $("input[name='tel']");
		let str = "0?(13|14|15|17|18|19)[0-9]{9}";
		if (tel.val().match(str)){
			tel.css('color','lightgreen');
			return true;
		}else{
			tel.css('color','red');
			layer.msg("输入的手机号不合法")
			return false;
		}
	}


	function checkUserName () {
		let username = $("input[name='userName']")
		$.ajax({
			url:'${ctx}/user/checkUserName?username='+username.val(),
			method:'get',
			dataType:'json',
			sync:false,
			success:function (res) {
				if (res.msg!='success'){
					username.css('color','red')
					layer.msg(res.msg);
					return false;
				}else{
					username.css('color','lightgreen')
					return true;
				}
			},
			error:function (res) {
				return false;
				alert(res.msg);
			}

		})
	}
</script>
</body>
</html>
