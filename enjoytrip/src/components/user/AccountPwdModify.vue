<script setup>
import VButtonLong from "../common/VButtonLong.vue";

import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { modifyPwd, findByRandomToken } from "@/api/account";

const route = useRoute();
const router = useRouter();
const memberStore = useMemberStore();

const { isLogin, userInfo } = storeToRefs(memberStore);

const userPwd = ref("");
const userPwdConfirm = ref("");

const message = ref("");

const param = ref({
  userId: "",
  type: "password",
  value: "",
});

const getId = ref({
  token: "",
});

const modifyPassword = function () {
  console.log("modify password!");
  message.value = "";

  if (userPwd.value == "" || userPwdConfirm.value == "") {
    message.value = "비밀번호를 입력해주세요";
    return;
  }

  if (userPwd.value == userPwdConfirm.value) {
    // 변경하러가자
    if (isLogin.value) {
      console.log("로그인된 경우");
      // 로그인 후 변경하는 경우
      param.value.userId = userInfo.value.userId;
    } else {
      // 비밀번호 변경 링크를 통해 들어오는 경우
      console.log("로그인 안된 경우", route.query.token);
      getId.value.token = route.query.token;
      findByRandomToken(
        getId.value,
        ({ data }) => {
          console.log("data: ", data);
          param.value.userId = data;
        },
        (error) => {
          console.log("error!", error);
        }
      );
    }

    param.value.value = userPwd.value;

    console.log("비밀번호 변경하러 가자");
    modifyPwd(
      param.value,
      ({ data }) => {
        console.log("modify pwd data:", data);
        if (isLogin.value) {
          // 임시!!
          // 마이페이지로 이동해야함
          router.push({ name: "user-mypage" });
        } else {
          router.push({ name: "user-login" });
        }
      },
      (error) => {
        console.log(error);
      }
    );
  } else {
    message.value = "비밀번호를 확인해주세요";
  }
};

const messageDelete = function () {
  message.value = "";
};
</script>

<template>
  <div>
    <div class="container">
      <div class="title">비밀번호 확인</div>
      <div class="form-container">
        <label for="user-pwd">비밀번호</label>
        <input type="password" v-model="userPwd" id="user-pwd" class="shortInput" autofocus />
        <hr />
        <label for="user-pwd-new">비밀번호 확인</label>
        <input
          type="password"
          v-model="userPwdConfirm"
          id="user-pwd-new"
          class="shortInput"
          @keyup.enter="modifyPassword"
        />
        <hr />
        <div class="message">{{ message }}</div>
        <div class="button">
          <VButtonLong
            text="비밀번호 변경"
            @click.prevent="modifyPassword"
            @keyup="messageDelete"
          />
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
