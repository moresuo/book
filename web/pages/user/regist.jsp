<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>尚硅谷会员注册页面</title>
<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		//页面加载完成之后
		$(function (){

			//点击验证码图片切换验证码
			$("#code_img").click (function (){
				this.src="${basePath}kaptcha.jpg?d="+new Date();
			})

			//用户名输入框失去焦点事件发送ajax请求判断用户名是否存在
			$("#username").blur(function (){
				//获取用户名
				var usernameValue=this.value;
				//判断用户名不能为空
				if(usernameValue==null){
					$("#errorSpan").html("用户名不能为空");
					return;
				}
				//发送ajax请求验证
				$.getJSON("http://localhost:8080/book/userServlet","action=existsUsername&username="+usernameValue,function (data){

					if(data.res){

						//说明用户存在
						$("#errorSpan").html("用户名已存在");
					}else{
						//说明用户不存在，可用
						$("#errorSpan").html("");
					}
				});
			});

			//给注册按钮绑定单击事件
			$("#sub_btn").click(function (){
				//验证用户名
				//1、获取用户名输入框里面的内容
				var username=$("#username").val();
				//2、创建正则表达式
				var usernamePatt = /^\w{5,12}$/;
				//3、使用test方法验证
				if(!usernamePatt.test(username)){
					//4、提示用户名不合法
					$("span.errorMsg").text("用户名不合法");
					return false;
				}



				//验证密码
				//1、获取密码
				var password=$("#password").val();
				//2、创建正则表达式
				var passwordPatt = /^\w{5,12}$/;
				//3、使用test方法验证
				if(!passwordPatt.test(password)){
					//4、提示密码不合法
					$("span.errorMsg").text("密码不合法");
					return false;
				}



				//验证确认密码
				//1、获取确认密码内容
				var repwd=$("#repwd").val();
				//2、和密码比较
				if(repwd!=password){
					//3、提示用户
					$("span.errorMsg").text("确认密码不一致");
					return false;
				}




				//验证邮箱
				//1、获取邮箱内容
				var email=$("#email").val();
				//2、创建正则表达式
				var emailPatt=/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
				//3、使用test方法验证
				if(!emailPatt.test(email)){
					$("span.errorMsg").text("邮箱格式不正确");
					return false;
				}

				//验证码，只需要有输入就行，后面再完善
				var codeText=$("#code").val();
				//去掉验证码空格
				codeText = $.trim(codeText);
				if(codeText==null||codeText==""){
					$("span.errorMsg").text("验证码不合法");
					return false;
				}




			})
		});
	</script>
<style type="text/css">
	.login_form{
		height:420px;
		margin-top: 25px;
	}
	
</style>
</head>
<body>
		<div id="login_header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
		</div>
		
			<div class="login_banner">
			
				<div id="l_content">
					<span class="login_word">欢迎注册</span>
				</div>
				
				<div id="content">
					<div class="login_form">
						<div class="login_box">
							<div class="tit">
								<h1>注册尚硅谷会员</h1>
								<span class="errorMsg" id="errorSpan">
<%--									<%=request.getAttribute("msg")==null?"":request.getAttribute("msg")%>--%>
									${requestScope.msg}
								</span>
							</div>
							<div class="form">
								<form action="userServlet" method="post">
									<input type="hidden" name="action" value="regist">
									<label>用户名称：</label>
									<input class="itxt" type="text" placeholder="请输入用户名"
										   autocomplete="off" tabindex="1" name="username" id="username"
											value="${requestScope.username}"
									/>
									<br />
									<br />
									<label>用户密码：</label>
									<input class="itxt" type="password" placeholder="请输入密码"
										   autocomplete="off" tabindex="1" name="password" id="password" />
									<br />
									<br />
									<label>确认密码：</label>
									<input class="itxt" type="password" placeholder="确认密码"
										   autocomplete="off" tabindex="1" name="repwd" id="repwd" />
									<br />
									<br />
									<label>电子邮件：</label>
									<input class="itxt" type="text" placeholder="请输入邮箱地址"
										   autocomplete="off" tabindex="1" name="email" id="email"
											value="${requestScope.email}"
									/>
									<br />
									<br />
									<label>验证码：</label>
									<input class="itxt" type="text" name="code" style="width: 80px;" id="code"/>
									<img alt="" id="code_img" src="kaptcha.jpg" style="float: right; margin-right: 40px;width: 100px;height: 30px">
									<br />
									<br />
									<input type="submit" value="注册" id="sub_btn" />
									
								</form>
							</div>
							
						</div>
					</div>
				</div>
			</div>
		<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>