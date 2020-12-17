<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户添加</title>
<%@ include file="/static/base/common.jspf"%>
<script type="text/javascript" src="${ctx}/static/js/hp_form.js"></script>
</head>
<body>
	<div class="body_main">
		<form class="layui-form layui-form-pane" action="${ctx}/user/add">
			<div class="layui-form-item">
				<label class="layui-form-label">昵称</label>
				<div class="layui-input-block">
					<input type="text" name="nickname" autocomplete="off"
						placeholder="请输入昵称" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">用户名</label>
				<div class="layui-input-block">
					<input type="text" name="userName" autocomplete="off"
						placeholder="请输入用户名" class="layui-input" onblur="checkUserName()">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">密码</label>
				<div class="layui-input-block">
					<input type="text" name="password" autocomplete="off"
						placeholder="请输入密码" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话</label>
				<div class="layui-input-block">
					<input type="tel" name="tel" lay-verify="required" autocomplete="off"
						placeholder="请输入电话" class="layui-input" onblur="checkTel()">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">性别</label>
				<div class="layui-input-block">
					<select name="sex" lay-verify="required">
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
						placeholder="请输入邮箱" class="layui-input" onblur="checkEmail()">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">头像</label>
				<div class="layui-input-block">
					<input type="file" name="userImg" autocomplete="off"
						placeholder="请输入头像" class="layui-input">
				</div>
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
					<button class="layui-btn" lay-submit="" lay-filter="demo1">立即提交</button>
					<button type="reset" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
	<script type="text/javascript" src="${ctx}/static/plugins/jquery.min.js"></script>
<script type="text/javascript">
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

	function checkEmail(){
		let email = $("input[name='email']");
		let str = "^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$";
		if (email.val().match(str)){
			email.css('color','lightgreen');
			return true;
		}else{
			email.css('color','red');
			layer.msg("输入的邮箱不合法")
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
