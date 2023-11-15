<script setup>
import VButtonLong from "../common/VButtonLong.vue";
</script>

<template>
  <div>
    <div class="container">
      <div class="title">아이디 찾기</div>
      <div class="form-container">
        <label for="user-email">이메일</label>
        <input type="email" id="user-email" name="user-email" class="shortInput" />
        <hr />
        <div class="button">
          <VButtonLong text="아이디 찾기" />
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
</style>
