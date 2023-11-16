<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";

const router = useRouter();
const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

const emailRef = ref("");

const emailReadOnly = ref(true);

const emailModify = function () {
  console.log("emailModify ing!!");
  emailReadOnly.value = !emailReadOnly.value;
};

const pwdModify = function () {
  console.log("pwdModify function");
  router.push({ name: "user-pwd-check" });
};

onMounted(() => {
  emailRef.value = userInfo.value.userEmail;
  console.log("mounted!!", emailRef.value);
});
</script>

<template>
  <!-- <div> -->
  <div class="container">
    <div class="title">마이페이지</div>
    <div class="innerContainer">
      <div class="infoBox">
        <div id="myInfoText">내정보</div>
        <div id="nameInfo">
          {{ userInfo.userId }}({{ userInfo.userName }})님
          <button>수정하기</button>
        </div>
        <div class="userInfo">
          <input type="text" :value="emailRef" :readonly="emailReadOnly" />
          <button @click="emailModify">수정하기</button>
        </div>
        <div class="userInfo">
          <span>비밀번호</span>
          <button @click="pwdModify">수정하기</button>
        </div>
        <div class="delete">회원탈퇴</div>
      </div>
      <div>
        <div class="listBox">
          <div>여행 계획 목록</div>
        </div>
        <div class="listBox">
          <div>내 글 목록 바로가기</div>
        </div>
        <div class="listBox">
          <div>찜 목록 바로가기</div>
        </div>
      </div>
    </div>
  </div>
  <!-- </div> -->
</template>

<style scoped>
* {
  box-sizing: border-box;
}
a {
  text-decoration: none;
  color: black;
}

input {
  background-color: #00000000;
  border: none;
  outline: none;
}

input:hover {
  outline: none;
}
.container {
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  padding: 40px;
  height: 100%;
}

.innerContainer {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
}

.title {
  font-size: 2rem;
  padding-bottom: 50px;
}

.infoBox {
  background: rgba(217, 217, 217, 0.16);
  border: 2px solid #d5d5d5;
  border-radius: 10px;
  padding: 50px 10px 30px;
  width: 400px;
  height: 700px;
  display: flex;
  flex-direction: column;
  margin: 0px 50px;
}

.listBox {
  background: rgba(217, 217, 217, 0.16);
  border: 2px solid #d5d5d5;
  border-radius: 10px;

  display: flex;
  align-items: center;

  padding: 10px;
  margin-bottom: 30px;
  width: 600px;
  height: 70px;
  font-size: 1.2rem;

  cursor: pointer;
}

.listBox:hover {
  color: #ca0f0f;
}

#myInfoText {
  text-align: center;
  font-size: 1.7rem;
  font-weight: bold;
}

#nameInfo {
  text-align: center;
  font-size: 1.2rem;
  padding: 10px;
  padding-top: 30px;
  padding-bottom: 50px;
}

.userInfo {
  padding: 20px;
  display: flex;
  justify-content: space-between;
}

.delete {
  color: #ca0f0f;
  margin-top: auto;
  text-align: center;
  cursor: pointer;
  font-size: 0.8rem;
}

button {
  background-color: #ffffff00;
  border-radius: 10px;
  border: 1px solid black;
  font-size: 0.6rem;
  height: 33px;
  width: 70px;
}

button:hover {
  border-color: #ca0f0f;
  cursor: pointer;
}
</style>
