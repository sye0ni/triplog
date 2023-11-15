<script setup>
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, userInfo, getUserInfo } = memberStore;
</script>

<template>
  <nav class="navbar fixed-top">
    <div class="sitename">
      <router-link :to="{ name: 'main' }">Enjoy Trip</router-link>
    </div>
    <div class="list">
      <router-link :to="{ name: 'main' }">홈</router-link>
      <router-link :to="{ name: 'plan' }"> | 계획 세우기</router-link>
      <!-- <a href=""> | 계획 세우기</a> -->
      <a href=""> | 여행지 찜</a>
      <router-link :to="{ name: 'board-photo' }"> | 후기 공유</router-link>
      <!-- <a href=""> | 후기 공유</a> -->
      <router-link :to="{ name: 'board-qna' }"> | Q&A</router-link>
      <a href="" class="user"> | 마이페이지</a>
      <a href="" class="user"> | 로그아웃</a>
      <router-link :to="{ name: 'user-login' }"> | 로그인</router-link>
      <!-- <a href="" class="user"> | 로그인</a> -->
    </div>
  </nav>
</template>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  /* align-items: baseline; */
  align-items: center;
  padding: 10px 5%;
}

.fixed-top {
  position: static;
  top: 0;
  right: 0;
  left: 0;
  z-index: 1030; /* 필요에 따라 조절할 수 있습니다. */
}

.sitename {
  font-size: 2rem;
  font-weight: bold;
  min-width: 200px;
  margin-left: 10px;
}

.list {
  min-width: 400px;
  /* white-space: nowrap; */
}

a {
  text-decoration: none;
  color: black;
}

.user {
  color: gray;
}
</style>
