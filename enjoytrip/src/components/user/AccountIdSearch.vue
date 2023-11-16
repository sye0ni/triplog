<script setup>
import VButtonLong from "../common/VButtonLong.vue";

import { ref } from "vue";
import { searchIdPwd } from "@/api/account";

const message = ref("");
const param = ref({
  type: "id",
  value: "",
});

const findId = function () {
  console.log("아이디찾기중");
  searchIdPwd(
    param.value,
    ({ data }) => {
      console.log("get find id ", data);
      message.value = data;
    },
    ({ response }) => {
      console.log(response);
      message.value = response.data;
    }
  );
};
</script>

<template>
  <div>
    <div class="container">
      <div class="title">아이디 찾기</div>
      <div class="form-container">
        <label for="user-email">이메일</label>
        <input
          type="email"
          v-model="param.value"
          id="user-email"
          class="shortInput"
          autofocus
          placeholder="회원가입한 이메일 입력"
          @keyup.enter="findId"
        />
        <hr />

        <div class="message">
          {{ message }}
        </div>

        <div class="button">
          <VButtonLong text="아이디 찾기" @click.prevent="findId" />
        </div>

        <div class="user">
          <router-link :to="{ name: 'user-id-search' }">아이디 찾기</router-link
          >&nbsp;&nbsp;|&nbsp;&nbsp;
          <router-link :to="{ name: 'user-pwd-search' }">비밀번호 찾기</router-link
          >&nbsp;&nbsp;|&nbsp;&nbsp;
          <router-link :to="{ name: 'user-join' }">회원가입</router-link>
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
  width: 100%;
}
.user {
  display: flex;
  justify-content: center;
  margin-bottom: 50px;
}

.button {
  margin: 50px 0px;
}

.message {
  color: #ca0f0f;
}
</style>
