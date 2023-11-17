<script setup>
import VButtonLong from "@/components/common/VButtonLong.vue";

import { ref } from "vue";
import { searchIdPwd } from "@/api/account";

const message = ref("");
const param = ref({
  type: "password",
  value: "",
});

const findPwd = function () {
  console.log("서버에서 비밀번호 찾기");
  searchIdPwd(
    param.value,
    ({ data }) => {
      console.log("get find pwd ", data);
      message.value = data;
    },
    ({ response }) => {
      console.log(response.data);
      message.value = response.data;
    }
  );
};
</script>

<template>
  <div>
    <div class="container">
      <div class="title">비밀번호 찾기</div>
      <div class="form-container">
        <label for="user-id">아이디</label>
        <input
          type="text"
          v-model="param.value"
          id="user-id"
          class="shortInput"
          @keyup.enter="findPwd"
          autofocus
        />
        <hr />
        <div class="message">
          {{ message }}
        </div>
        <div class="button">
          <VButtonLong text="비밀번호 찾기" @click="findPwd" @keyup.enter="findPwd" />
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
