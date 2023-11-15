<script setup>
import VButtonLong from "../common/VButtonLong.vue";

import { ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
// import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
// const { changeMenuState } = useMenuStore();

const loginUser = ref({
  userId: "",
  userPwd: "",
});

const login = async () => {
  console.log("login ing!!!! !!!");
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  console.log("111. ", token);
  console.log("isLogin: ", isLogin);
  if (isLogin) {
    console.log("로그인 성공아닌가???");
    getUserInfo(token);
    // changeMenuState();
    // 메뉴 변경!!
  }
  router.push("/");
};
</script>

<template>
  <div>
    <div class="container">
      <div class="title">로그인</div>
      <div class="form-container">
        <form action="" method="post">
          <label for="user-id">아이디</label><br />
          <input type="text" v-model="loginUser.userId" id="user-id" /><br />
          <hr />
          <label for="user-pwd">비밀번호</label><br />
          <input type="password" v-model="loginUser.userPwd" id="user-pwd" /><br />
          <hr />
          <div class="button">
            <VButtonLong text="로그인" @click.prevent="login" />
          </div>
        </form>
        <div class="user">
          <!-- <a href="">아이디 찾기</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
          <router-link :to="{ name: 'user-id-search' }">아이디 찾기</router-link
          >&nbsp;&nbsp;|&nbsp;&nbsp;
          <!-- <a href="">비밀번호 찾기</a>&nbsp;&nbsp;|&nbsp;&nbsp; -->
          <router-link :to="{ name: 'user-pwd-search' }">비밀번호 찾기</router-link
          >&nbsp;&nbsp;|&nbsp;&nbsp;
          <!-- <a href="">회원가입</a> -->
          <router-link :to="{ name: 'user-join' }">회원가입</router-link>
        </div>
        <div class="social">
          <VButtonLong text="네이버로 로그인" color=" #43E528" />
        </div>
        <div class="social">
          <VButtonLong text="구글로 로그인" />
        </div>
        <div class="social">
          <VButtonLong text="카카오로 로그인" color="#FCF314" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
a {
  text-decoration: none;
  color: black;
}
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 40px;
  height: 100%;
}
.title {
  font-size: 2rem;
  padding-bottom: 90px;
}
.form-container {
  display: flex;
  flex-direction: column;
}
input {
  border: 0px;
  width: 500px;
  height: 50px;
  outline: 0px;
  font-size: 1.2rem;
  margin: 0px;
  margin-top: 20px;
}
hr {
  margin-top: 0px;
  margin-bottom: 20px;
}
.button {
  margin: 50px 0px;
}
.user {
  display: flex;
  justify-content: center;
  margin-bottom: 50px;
}
.social {
  margin-top: 20px;
}
</style>
