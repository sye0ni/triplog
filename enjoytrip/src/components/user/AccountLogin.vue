<script setup>
import VButtonLong from "../common/VButtonLong.vue";

import { ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { getWishlist } from "@/api/plan";

const router = useRouter();

const memberStore = useMemberStore();
const planStore = usePlanStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { wishlist } = storeToRefs(planStore);

const loginUser = ref({
  userId: "",
  userPwd: "",
});

const message = ref("");

const login = async () => {
  console.log("login ing!!!! !!!");
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  if (isLogin.value) {
    getUserInfo(token);
    // 찜목록 불러오기
    let decodeToken = jwtDecode(token);
    getWishlist(
      decodeToken.userId,
      ({ data }) => {
        console.log("login->getWishlist!!", data);
        wishlist.value.length = 0;
        for (let i = 0; i < data.length; i++) {
          wishlist.value.push(data[i]);
        }
        console.log("wish:", wishlist.value);
      },
      (error) => {
        console.log(error);
      }
    );
    router.push("/");
  } else {
    message.value = "아이디 또는 비밀번호를 다시 확인해주세요.";
  }
};
</script>

<template>
  <div>
    <div class="container">
      <div class="title">로그인</div>
      <div class="form-container">
        <form action="" method="post">
          <label for="user-id">아이디</label><br />
          <input type="text" v-model="loginUser.userId" id="user-id" autofocus /><br />
          <hr />
          <label for="user-pwd">비밀번호</label><br />
          <input type="password" v-model="loginUser.userPwd" id="user-pwd" /><br />
          <hr />
          <div class="message">{{ message }}</div>
          <div class="button">
            <VButtonLong
              text="로그인"
              @click.prevent="login"
              bgcolor="#ca0f0f"
              color="white"
              border="none"
            />
          </div>
        </form>
        <div class="user">
          <router-link :to="{ name: 'user-id-search' }"> <span></span> 아이디 찾기</router-link>
          &nbsp;&nbsp;| &nbsp;&nbsp;
          <router-link :to="{ name: 'user-pwd-search' }">비밀번호 찾기</router-link>&nbsp;&nbsp; |
          &nbsp;&nbsp;
          <router-link :to="{ name: 'user-join' }">회원가입</router-link>
        </div>
        <!-- <div class="social">
          <VButtonLong text="네이버로 로그인" bgcolor=" #43E528" />
        </div>
        <div class="social">
          <VButtonLong text="구글로 로그인" />
        </div>
        <div class="social">
          <VButtonLong text="카카오로 로그인" bgcolor="#FCF314" />
        </div> -->
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

.message {
  color: #ca0f0f;
}
</style>
