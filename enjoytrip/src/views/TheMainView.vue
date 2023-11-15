<script setup>
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, userInfo, getUserInfo } = memberStore;
</script>

<template>
  <div>is Login : {{ isLogin }}</div>
  <hr />
  <div>user Login : {{ userLogin }}</div>
  <hr />
  <div>getUserInfo : {{ getUserInfo }}</div>
  <hr />
  <div>UserInfo : {{ userInfo }}</div>
  <hr />
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
  <div>Main Page</div>
</template>

<style scoped></style>
