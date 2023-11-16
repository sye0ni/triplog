<script setup>
import VButtonLong from "../common/VButtonLong.vue";

import { ref, watch } from "vue";
import { dupCheck, userJoin } from "@/api/account";
import { useRouter } from "vue-router";

const router = useRouter();

const idMessage = ref("");
const emailMessage = ref("");
const joinBtnMessage = ref("");

const isValidId = ref(false);
const isValidEmail = ref(false);

const joinUser = ref({
  userId: "",
  userName: "",
  userPwd: "",
  userEmail: "",
});

const userIdRef = ref("");
const userEmailRef = ref("");

const param = ref({
  type: "",
  value: "",
});

watch(userIdRef, (newValue, oldValue) => {
  param.value.type = "id";
  param.value.value = newValue;
  dupCheck(
    param.value,
    ({ data }) => {
      idMessage.value = data;
      isValidId.value = true;
    },
    ({ response }) => {
      console.log(response);
      idMessage.value = response.data;
      isValidId.value = false;
    }
  );
});

const regex = new RegExp("[a-z0-9]+@[a-z]+\.[a-z]{2,3}");

watch(userEmailRef, (newValue, oldValue) => {
  param.value.type = "email";
  param.value.value = newValue;

  // if (newValue.length > 4) {
  if (regex.test(newValue)) {
    dupCheck(
      param.value,
      ({ data }) => {
        emailMessage.value = data;
        isValidEmail.value = true;
      },
      ({ response }) => {
        console.log(response);
        emailMessage.value = response.data;
        isValidEmail.value = false;
      }
    );
  } else {
    emailMessage.value = "형식을 확인해주세요";
  }
  // }
});

const join = function () {
  // 값 대입
  joinUser.value.userId = userIdRef.value;
  joinUser.value.userEmail = userEmailRef.value;

  console.log("join ing!!", joinUser.value);
  if (joinUser.value.userId == "") {
    joinBtnMessage.value = "아이디를 입력해주세요";
  } else if (joinUser.value.userName == "") {
    joinBtnMessage.value = "이름을 입력해주세요";
  } else if (joinUser.value.userPwd == "") {
    joinBtnMessage.value = "비밀번호를 입력해주세요";
  } else if (joinUser.value.userEmail == "") {
    joinBtnMessage.value = "이메일을 입력해주세요";
  } else {
    joinBtnMessage.value = "";
    // 회원가입
    if (isValidId.value && isValidEmail.value) {
      userJoin(
        joinUser.value,
        ({ data }) => {
          console.log("join data ", data);
          router.push({ name: "user-login" });
        },
        ({ response }) => {
          console.log(response);
          alert("회원가입 실패");
        }
      );
    } else {
      joinBtnMessage.value = "아이디 혹은 이메일을 확인해주세요";
    }
  }
};
</script>

<template>
  <div>
    <div class="container">
      <div class="title">회원가입</div>
      <div class="form-container">
        <form action="" method="post">
          <label for="user-id">아이디</label><br />
          <input type="text" v-model="userIdRef" id="user-id" class="shortInput" />
          <!-- <button class="dupBtn">중복 확인</button><br /> -->
          <hr />
          <div class="message">{{ idMessage }}</div>
          <label for="user-name">이름</label><br />
          <input type="text" v-model="joinUser.userName" id="user-name" /><br />
          <hr />
          <label for="user-pwd">비밀번호</label><br />
          <input type="password" v-model="joinUser.userPwd" id="user-pwd" /><br />
          <hr />
          <label for="user-email">이메일</label><br />
          <input type="email" v-model="userEmailRef" id="user-email" class="shortInput" />
          <!-- <button class="dupBtn">중복 확인</button><br /> -->
          <hr />
          <div class="message">{{ emailMessage }}</div>
          <div class="button" @click.prevent="join">
            <VButtonLong text="회원가입" />
          </div>
          <div class="message">
            {{ joinBtnMessage }}
          </div>
        </form>
        <div class="social">
          <VButtonLong text="네이버로 회원가입" color=" #43E528" />
        </div>
        <div class="social">
          <VButtonLong text="구글로 회원가입" />
        </div>
        <div class="social">
          <VButtonLong text="카카오로 회원가입" color="#FCF314" />
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
.shortInput {
  width: 410px;
}
.button {
  margin: 20px 0px;
}
.social {
  margin-top: 20px;
}
.dupBtn {
  border: 0px;
  /* background-color: #ca0f0f2d; */
  height: 45px;
  border-radius: 10px;
  /* color: white; */
}
.dupBtn:hover {
  border-color: #ca0f0f;
}

.message {
  margin-bottom: 20px;
  color: #ca0f0f;
}
</style>
