<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
		<div id="header">
			<h1>${blogVo.title }</h1>
			<ul>
				<c:if test="${empty authUser }">
					<li><a href="${pageContext.request.contextPath}/user">로그인</a></li>
				</c:if>
				<!-- 현제들어와있는 id와 이 블로그의 id가 같으면 보이게 -->
				<c:if test="${not empty authUser && authUser.id == blogVo.id  }">
					<li><a href="${pageContext.request.contextPath}/user/logout">로그아웃</a></li>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}/admin/basic">블로그 관리</a></li>
				</c:if>
					<li><a href="${pageContext.request.contextPath}/${authUser.id}">내 블로그로 가기</a></li>
			</ul>
		</div>
