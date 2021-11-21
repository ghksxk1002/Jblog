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
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.9.0.js"></script> <!-- jQuery -->
<script type="text/javascript" src="${pageContext.request.contextPath}/assets/ejs/ejs.js"></script> <!-- ejs -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script> <!-- dialog -->
</head>
<script>
// 새로 입력되는 카테고리 처리 템플릿
var listEJS = new EJS({
	url : '${pageContext.request.contextPath}/assets/ejs/list-temlate.ejs'
});
// 리스트 출력해주는 템플릿
var listItemEJS = new EJS({
	url : '${pageContext.request.contextPath}/assets/ejs/listItem-temlate.ejs'
});

var messageBox = function(title, message, callback) {

	$('#dialog-message').attr('title', title);
	$('#dialog-message p').text(message);

	$('#dialog-message').dialog({
		modal : true,
		buttons : {
			"환인" : function() {
				$(this).dialog("close");
			}

		},
		close : callback
	});
}

var fetch = function() {
	
	var url = '${pageContext.request.contextPath}/api/category/list'
	$.ajax({
		url : url,
		dataType : 'json',
		type : 'post',
		success : function(response) {
			console.log(response)
			// 템플릿 안에서 response의 속성들에 접근할수 있따
			var html = listEJS.render(response);
			$(".admin-cat").append(html);
			
		}

	})
};

$(function(){
	// 카테고리 추가
	$("#add-category").click(function() {
		var vo = {};
		// id가 name인 엘리먼트의 입력된 카테고리 이름 가져오기
		vo.name = $('#name').val();
		vo.desc = $('#desc').val();
		
		console.log(vo.name);
		if (!vo.name) {
			messageBox('새카테고리작성', '카테고리명은 반드시 입력해야 합니다', function() {
				$('#name').focus();
			});
			return;
		}

		$.ajax({
			url : '${pageContext.request.contextPath}/api/category/add',
			type : 'post',
			dataType : 'json',
			contentType : 'application/json',
			data : JSON.stringify(vo),
			success : function(response) {
				console.log(response);

				if (response.result !== 'success') { // 서버측 에러 내용
					console.log(response.message);
					return;
				}
				
				// var html = render(response.data);
				var html = "";
				html = listItemEJS.render(response.data);
				console.log("확인");
				$(".admin-cat tr:last").after(html);
				$("#name").val('');
				$("#desc").val('');

			},
			error : function(xhr, code, message) { // 통신 에러
				console.error(status + ":" + error);
			}
		});

		console.log("ajax submit");
	});
	
	// 삭제 다이알로그 객체 만들기
	var dialogDelete = $('#dialog-delete-form')
			.dialog({
				autoOpen : false, // 바로 안뜨게 만들기
				modal : true,
				buttons : {
					"삭제" : function() {
						// 삭제 ajax 실행
						var no = $('#hidden-no').val();
						console.log(no)
						// 때ㅔ려야될 url
						var url = '${pageContext.request.contextPath}/api/category/delete/' + no;

							$.ajax({
									url : url,
									type : 'get',
									dataType : 'json',
									success : function(response) {
										console.log(response)
		
										$('.admin-cat tr[data-no='+ response.data+ ']').remove();
										dialogDelete.dialog('close');
									}
								});
							},
							"취소" : function() {
								$('#password-delete').val('');
								$(this).dialog("close");
							}
						}
					});
	// 글삭제 버튼	
	$(document).on('click', '.admin-cat td a', function(event) {
		event.preventDefault();
		console.log(this);
		var no = $(this).data('no');
		$("#hidden-no").val(no)

		dialogDelete.dialog('open');
	});
	
	fetch();
});
</script>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header-blog.jsp"/>
		<div id="wrapper">
			<div id="content" class="full-screen">
				<ul class="admin-menu">
					<li><a href="${pageContext.request.contextPath}/${authUser.id }/admin/basic">기본설정</a></li>
					<li class="selected">카테고리</li>
					<li><a href="${pageContext.request.contextPath}/${blogVo.id }/admin/write">글작성</a></li>
				</ul>
		      	<table class="admin-cat">
		      		<tr>
		      			<th>카테고리명</th>
		      			<th>포스트 수</th>
		      			<th>설명</th>
		      			<th>삭제</th>      			
		      		</tr>
		      		
				</table>
      	
      			<h4 class="n-c">새로운 카테고리 추가</h4>
     			
			      	<table id="admin-cat-add">
			      		<tr>
			      			<td class="category-name">카테고리명</td>
			      			<td><input type="text" id="name" value=""></td>
			      		</tr>
			      		<tr>
			      			<td class="t">설명</td>
			      			<td><input type="text" id="desc"></td>
			      		</tr>
			      		<tr>
			      			<td class="s">&nbsp;</td>
			      			<td><input id='add-category' type="submit" value="카테고리 추가"></td>
			      		</tr>      		      		
			      	</table> 
		      
			</div>
			
		</div>
		<div id="dialog-delete-form" title="메세지 삭제" style="display: none">
			<p class="validateTips normal">해당 카테고리의 포스트가 모두 삭제될수 있습니다.</p>
			<input type="hidden" id="hidden-no" value=""> 
		</div>
		<div id="dialog-message" title="새글작성" style="display: none">
			<p></p>
		</div>
	<c:import url="/WEB-INF/views/includes/footer-blog.jsp"/>
	</div>
</body>
</html>