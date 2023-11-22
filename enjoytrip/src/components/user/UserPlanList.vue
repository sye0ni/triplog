<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plan";
import { getPlanList, getSidoGugunName } from "@/api/plan";
import { jwtDecode } from "jwt-decode";
import { deleteUser, dupCheck, modifyUserInfo } from "@/api/account.js";
import UserPlanListItem from "@/components/user/item/UserPlanListItem.vue";

const router = useRouter();
const memberStore = useMemberStore();
const { userLogout } = memberStore;

const { userInfo } = storeToRefs(memberStore);

const planStore = usePlanStore();

const { planDetailBox } = storeToRefs(planStore);

const param = ref({
  userId: "",
  sidoCode: "",
  gugunCode: "",
});

const planList = ref([]);

onMounted(() => {
  // 계획 불러오기
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  //   let planId = toString(route.params);
  param.value.userId = decodeToken.userId;
  getPlanList(
    param.value,
    ({ data }) => {
      for (let i = 0; i < data.length; i++) {
        planList.value.push(data[i]);
        param.value.sidoCode = data[i].sidoCode;
        param.value.gugunCode = data[i].gugunCode;
        getSidoGugunName(
          param.value,
          ({ data }) => {
            planList.value[i].location = data;
          },
          (error) => {
            console.log(error);
          }
        );
      }
    },
    (error) => {
      console.log(error);
    }
  );
});
</script>

<template>
  <div class="container">
    <!-- <div class="right dropdown" @click="dropMenu"></div> -->
    <div class="title">여행 계획 목록</div>
    <hr />
    <div class="innerContainer">
      <UserPlanListItem
        v-for="(item, index) in planList"
        :key="item.planId"
        :item="item"
        :index="index"
      />
    </div>
  </div>
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
  /* font-size: 1.2rem; */
}

input:hover {
  outline: none;
}
.container {
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  /* padding: 40px; */
  height: 100%;
}

.innerContainer {
  display: flex;
  flex-direction: column;
  /* margin: auto; */
  width: 100%;
  height: 100%;
  /* justify-content: center; */
  align-items: center;
  /* overflow-y: auto; */
}

.title {
  width: 90%;
  font-size: 1.4rem;
  font-weight: 600;
  padding-top: 40px;
  padding-bottom: 10px;

  min-width: 130px;
  text-align: left;
}

button {
  background-color: #ffffff00;
  border-radius: 10px;
  border: 1px solid black;
  font-size: 0.7rem;
  height: 33px;
  width: 76px;
}

button:hover {
  border-color: #ca0f0f;
  cursor: pointer;
}

hr {
  margin-top: 0px;
  margin-bottom: 20px;
  width: 90%;
  /* min-width: 500px; */
}
</style>
