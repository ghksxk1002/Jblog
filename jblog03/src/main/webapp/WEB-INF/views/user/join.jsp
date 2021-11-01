<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<!-- 제이쿼리 함수 소환 -->
<script src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script>
<script>
$(function(){
	$("#btn-check-id").click(function() {
		var id = $("#id").val();
		if(id == '') {
			return;
		}
		
		console.log(id);
		$.ajax({
			url: "${pageContext.request.contextPath }/user/api/checkid?id=" + id,
			type: "get",
			dataType: "json",
			error:function(xhr, status, e){
				console.log(status, e);
			},
			success: function(response) {
				console.log(response);
				if(response.result!= "success"){
					console.error(response.message);
					return;
				}
				if(response.date) {
					alert("존재하는 아이디입니다. 다른 아이디을 사용하세요.");
					$("#id")
						.val("")
						.focus();
					return;
				}
				
				$("#btn-check-id").hide();
				$("#img-check-id").show();
			}
		});		
	});	
});
</script>
</head>
<body>
	<div class="center-content">
		<h1 class="logo" style="background:url(${pageContext.request.contextPath}/assets/images/logo.jpg) no-repeat 0 0">JBlog</h1>
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<form:form modelAttribute="userVo" class="join-form" id="join-form" method="post" action="${pageContext.request.contextPath}/user/join">
			<label class="block-label" for="name">이름</label> 
			<form:input path="name" />
					<p style="text-align:left; padding-left:0; color: #f00 "> 
						<spring:hasBindErrors name="userVo">
							<!-- name 이라는 필드에 에러가 있다면 오류내용 출력 -->
							<c:if test="${errors.hasFieldErrors('name') }">
								<spring:message code="${errors.getFieldError('name').codes[0] }" />
							</c:if>
						</spring:hasBindErrors>
					</p>
			
			<label class="block-label" for="blog-id">아이디</label>
			<form:input path="id" />  
			<input id="btn-check-id" type="button" value="id 중복체크">
			<img id="img-check-id" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">
			<p style="text-align:left; padding-left:0; color: #f00 "> 
						<spring:hasBindErrors name="userVo">
							<!-- name 이라는 필드에 에러가 있다면 오류내용 출력 -->
							<c:if test="${errors.hasFieldErrors('id') }">
								<spring:message code="${errors.getFieldError('id').codes[0] }" />
							</c:if>
						</spring:hasBindErrors>
			</p>
					
			<label class="block-label">패스워드</label>
			<form:input path="password" />
			<p style="text-align:left; padding-left:0; color: #f00 "> 
				<spring:hasBindErrors name="userVo">
							<!-- name 이라는 필드에 에러가 있다면 오류내용 출력 -->
							<c:if test="${errors.hasFieldErrors('password') }">
								<spring:message code="${errors.getFieldError('password').codes[0] }" />
							</c:if>
						</spring:hasBindErrors>
			</p>

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form:form>
	</div>
</body>
</html>
