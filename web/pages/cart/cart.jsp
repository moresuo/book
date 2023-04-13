<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>购物车</title>
	<%@ include file="/pages/common/head.jsp"%>
	<script type="text/javascript">
		$(function (){
			//给删除绑定单击事件
			$("a.deleteItem").click(function (){
				return confirm("你确定要删除【"+$(this).parent().parent().find("td:first").text()+"】吗？");
			})
			//给清空绑定单击事件
			$("#clearCart").click(function (){
				return confirm("你确定要清空吗");
			})

			//给输入框绑定修改事件
			$(".updateCount").change(function (){
				//获取商品名称
				var name = $(this).parent().parent().find("td:first").text();
				var count=this.value;
				var bookId=$(this).attr("bookId");
				if(confirm("你确定要将["+name+"]的数量修改为["+count+"]吗")){
					location.href="http://localhost:8080/book/cartServlet?action=updateCount&count="+count+"&id="+bookId;
				}else{
					this.value=this.defaultValue;
				}
			})
		})
	</script>
</head>
<body>
	
	<div id="header">
			<img class="logo_img" alt="" src="static/img/logo.gif" >
			<span class="wel_word">购物车</span>
		<%@ include file="/pages/common/login_success_menu.jsp"%>
	</div>
	
	<div id="main">
	
		<table>
			<tr>
				<td>商品名称</td>
				<td>数量</td>
				<td>单价</td>
				<td>金额</td>
				<td>操作</td>
			</tr>
			<%--如果购物车非空就循环遍历所有图书信息--%>
			<c:if test="${not empty sessionScope.cart.items}">
				<c:forEach items="${sessionScope.cart.items}" var="entry">
					<tr>
						<td>${entry.value.name}</td>
						<td>
							<input bookId="${entry.value.id}" type="text" value="${entry.value.count}" class="updateCount" style="width: 80px">
						</td>
						<td>${entry.value.price}</td>
						<td>${entry.value.totalPrice}</td>
						<td><a href="cartServlet?action=deleteItem&id=${entry.value.id}" class="deleteItem">删除</a></td>
					</tr>
				</c:forEach>

			</c:if>
			<%--如果购物车为空--%>
			<c:if test="${empty sessionScope.cart.items}">
				<tr>
					<td colspan="5"><a href="index.jsp">购物车为空，快去添加商品吧</a></td>
				</tr>
			</c:if>

			
		</table>
		<c:if test="${not empty sessionScope.cart.items}">
			<div class="cart_info">
				<span class="cart_span">购物车中共有<span class="b_count">${sessionScope.cart.totalCount}</span>件商品</span>
				<span class="cart_span">总金额<span class="b_price">${sessionScope.cart.totalPrice}</span>元</span>
				<span class="cart_span"><a href="cartServlet?action=clearCart" id="clearCart">清空购物车</a></span>
				<span class="cart_span"><a href="orderServlet?action=createOrder">去结账</a></span>
			</div>
		</c:if>
		

	
	</div>

	<%@ include file="/pages/common/footer.jsp"%>
</body>
</html>