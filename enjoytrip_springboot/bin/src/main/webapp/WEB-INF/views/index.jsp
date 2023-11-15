<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<style>
.highlight-text {
	color: gold;
	font-weight: bold;
}

#goPlanBtn {
	/* align-self: center; */
	/* border: 0px; */
	/* border-radius: 10px; */
	/* background-color: azure; */
	/* font-weight: bold; */
	width: 200px;
	font-size: 0.8rem;
}
</style>
</head>
<body>
	<%@ include file="/WEB-INF/views/include/nav.jsp"%>

	<%-- 페이지만의 내용 --%>
	<!-- 화면 이미지1 시작 -->
	<div>
		<img src="./resource/main_img.jpg" class="img-fluid"
			style="min-height: 700px; min-width: 1300px;"
			alt="..." />
		<div class="card-img-overlay"
			style="display: flex; flex-direction: column; justify-content: center; margin-left: 80px; margin-bottom: 100px;">
			<h5 class="card-title" style="color: white; margin-bottom: 50px">
				지금 <span class="highlight-text">Enjoy!!!Trip</span>과 함께 우리 지역의 관광지를
				알아보고<br />나만의 여행 계획을 만들어보세요!!!
			</h5>
			<button type="button" class="btn btn-primary" id="goPlanBtn">
				여행계획 바로가기</button>
		</div>
	</div>

	<%@ include file="/WEB-INF/views/include/footer.jsp"%>