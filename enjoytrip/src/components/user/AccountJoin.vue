<script setup>
import VButtonLong from "../common/VButtonLong.vue";
</script>

<template>
  <div>
    <div class="container">
      <div class="title">회원가입</div>
      <div class="form-container">
        <form action="" method="post">
          <label for="user-id">아이디</label><br />
          <input type="text" id="user-id" name="user-id" class="shortInput" />
          <button class="dupBtn">중복 검사</button><br />
          <hr />
          <label for="user-pwd">비밀번호</label><br />
          <input type="password" id="user-pwd" name="user-pwd" /><br />
          <hr />
          <label for="user-name">이름</label><br />
          <input type="text" id="user-name" name="user-name" /><br />
          <hr />
          <label for="user-email">이메일</label><br />
          <input type="email" id="user-email" name="user-email" class="shortInput" />
          <button class="dupBtn">중복 검사</button><br />
          <hr />
          <div class="button">
            <VButtonLong text="회원가입" />
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
  background-color: white;
  height: 45px;
  border-radius: 10px;
}
.dupBtn:hover {
  border-color: #ca0f0f;
}
</style>
