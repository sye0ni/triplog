<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/head.jsp"%>
<nav class="navbar navbar-expand-lg bg-light navbar-light shadow sticky-top">
      <div class="container-md">
        <a class="navbar-brand fw-bold" href="${root}/index.jsp" >
          enjoy trip
        </a>

        <!-- 메뉴 버튼 -->
        <button
          class="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#collapsibleNavbar"
        >
          <span class="navbar-toggler-icon"></span>
        </button>

        <div div class="collapse navbar-collapse" id="collapsibleNavbar">
          <ul class="navbar-nav ms-auto me-2 notLogin">
            <li class="nav-item">
              <a class="nav-link text-black navText" href="${root}/attraction/infoMap.jsp">지역별여행지</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-black navText" href="#">나의여행계획</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-black navText" href="${root}/article?action=list">게시판</a>
            </li>
            
        <!-- 로그인 전 -->
        	<c:if test="${empty sessionScope.userDto}">
            <li class="nav-item">
              <a class="nav-link text-black navText" href="${root}/user/login.jsp"
                >로그인</a
              >
            </li>
            </c:if>
          <!-- 로그인 후 -->
        	<c:if test="${!empty sessionScope.userDto}">
            <li class="nav-item">
              <a class="nav-link text-black navText" href="${root}/userController?action=check"
                >마이페이지</a>
            </li>
            <li class="nav-item">
              <a class="nav-link text-black navText" id="logoutBtn" href="${root}/userController?action=logout">로그아웃</a>
            </li>
            </c:if>
          </ul>
        </div>
      </div>
    </nav>