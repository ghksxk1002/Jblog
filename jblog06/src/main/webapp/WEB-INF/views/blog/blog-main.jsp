<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header-blog.jsp"/>
		<div id="wrapper">
			<div id="content">
				<ul class="blog-list">
				<c:forEach items="${map.postList}" var="vo" varStatus="status">
					<li><a href="${pageContext.request.contextPath}/${blogVo.id}/${vo.categoryNo}/${vo.no}">${vo.title}</a><span>${vo.regDate}</span></li>
				</c:forEach>
				</ul>
				<div class="blog-content">
					<h4>${map.postVo.title }</h4>
					<p>${map.postVo.contents }</p>
					<c:if test="${authUser.id == blogVo.id && not empty map.postVo.no }">
					<br/>
						<a href="${pageContext.servletContext.contextPath }/${authUser.id }/delete/${map.postVo.no}" class="delete">
						<img src="${pageContext.request.contextPath}/assets/images/delete.jpg"></a>
					</c:if>
				</div>
			</div>
		</div>

		<div id="extra">
			<div class="blog-logo">
				<img src="${pageContext.request.contextPath}${blogVo.logo}">
			</div>
		</div>
	<c:import url="/WEB-INF/views/includes/navigation-blog.jsp"/>
	<c:import url="/WEB-INF/views/includes/footer-blog.jsp"/>
	</div>
</body>
</html>