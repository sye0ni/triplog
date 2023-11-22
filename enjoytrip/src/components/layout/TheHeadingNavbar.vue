<script setup>
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { useRouter } from "vue-router";

const memberStore = useMemberStore();
const router = useRouter();

const { isLogin, userInfo } = storeToRefs(memberStore);
const { userLogout } = memberStore;

const logout = async () => {
  console.log("logout ing!!!");
  await userLogout(userInfo.userId);
  router.push({ name: "main" });
};
</script>

<template>
  <nav class="navbar fixed-top">
    <div class="sitename">
      <router-link :to="{ name: 'main' }">Enjoy Trip</router-link>
    </div>
    <div class="list">
      <router-link :to="{ name: 'main' }">홈</router-link>
      <router-link :to="{ name: 'plan' }"> | 계획 세우기</router-link>
      <router-link :to="{ name: 'wishlist' }"> | 여행지 찜</router-link>
      <!-- <a href=""> | 여행지 찜</a> -->
      <router-link :to="{ name: 'board-photo' }"> | 후기 공유</router-link>
      <router-link :to="{ name: 'board-qna' }"> | Q&A</router-link>
      <span v-show="isLogin">
        <router-link :to="{ name: 'user-mypage' }"> | 마이페이지</router-link>
        <a href="#" class="user" @click="logout"> | 로그아웃</a>
      </span>
      <span v-show="!isLogin">
        <router-link :to="{ name: 'user-login' }"> | 로그인</router-link>
      </span>
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
  background-color: white;
  z-index:1030;
}

.fixed-top {
  position: static;
  /* position:fixed; */
  top: 0;
  right: 0;
  left: 0;
  z-index: 1030; 
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
