<script setup>
import VButtonLong from "../common/VButtonLong.vue";

import { ref } from "vue";
import { pwdcheck } from "@/api/account";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { useRouter } from "vue-router";

const memberStore = useMemberStore();
const router = useRouter();

const { userInfo } = storeToRefs(memberStore);

const userPwd = ref("");
const message = ref("");

const param = ref({
  userId: "",
  value: "",
});

const passwordCheck = function () {
  console.log("pwdCheck!!");
  console.log("userInfo", userInfo);
  console.log("userInfo", userInfo.value.userId);
  param.value.userId = userInfo.value.userId;
  param.value.value = userPwd.value;
  console.log(param.value);
  pwdcheck(
    param.value,
    ({ data }) => {
      console.log(data);
      message.value = data;
      router.push({ name: "user-pwd-modify" });
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
      <div class="title">비밀번호 확인</div>
      <div class="form-container">
        <label for="user-pwd">비밀번호</label>
        <input
          type="password"
          v-model="userPwd"
          id="user-pwd"
          class="shortInput"
          placeholder="현재 비밀번호를 입력 해주세요.."
          @keyup.enter="passwordCheck"
        />
        <hr />
        <div class="message">
          {{ message }}
        </div>
        <div class="button">
          <VButtonLong text="비밀번호 변경" @click="passwordCheck" />
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
.button {
  margin: 50px 0px;
}

.message {
  color: #ca0f0f;
}
</style>
