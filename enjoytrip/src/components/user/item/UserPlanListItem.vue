<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";

const props = defineProps({
  item: Object,
});

const router = useRouter();

const memberStore = useMemberStore();
const planStore = usePlanStore();

const { isLogin, userInfo } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { wishlist } = storeToRefs(planStore);

const dropMenu = function () {
  console.log("dropMenu");
};

const goPlanDetail = function () {
  console.log("goPlanDetail!!", props.item.planId);
  router.push({
    name: "plan-list-detail",
    params: { planId: props.item.planId },
  });
};
const goPlanNameEdit = function () {};
const goPlanDayEdit = function () {};
const goPlanEdit = function () {};
const goDelete = function () {
  console.log("delete!!");
};
</script>

<template>
  <div>
    <div class="container">
      <div class="left">
        <img src="@/assets/image.png" alt="" />
        <div>
          <div>
            <span v-if="item.planName == null" class="title"
              >{{ userInfo.userName }}님의 {{ item.userPlanNth }}번째 계획</span
            >
            <span v-else class="title">{{ item.planName }}</span>
            <span>{{ item.location }}</span>
          </div>
          <div class="period">{{ item.startDate }}~{{ item.endDate }}</div>
        </div>
      </div>
      <div class="right">
        <i class="fa-solid fa-bars" style="color: #000000; cursor: pointer"></i>
        <div class="options">
          <ul class="option" @click="goPlanDetail">
            계획 상세보기
          </ul>
          <ul class="option" @click="goPlanNameEdit">
            여행 이름 수정
          </ul>
          <ul class="option" @click="goPlanDayEdit">
            여행 날짜 수정
          </ul>
          <ul class="option" @click="goPlanEdit">
            계획 수정
          </ul>
          <ul class="option" @click="goDelete">
            계획 삭제
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}
.container {
  min-width: 400px;
  width: 90%;
  /* width: 70vh; */
  min-height: 60px;
  display: flex;
  /* align-items: center; */
  justify-content: space-between;
  border: 2px solid rgb(151, 151, 151);
  border-radius: 10px;
  padding: 5px 10px;
  /* margin: 10px; */
  margin-bottom: 20px;
}

.left {
  display: flex;
  /* justify-content: center; */
  align-items: center;
}

/*
.right {
  display: flex;
  align-items: center;
} */

img {
  /* background-color: rgb(179, 23, 23); */
  width: 40px;
  height: 40px;
  margin-right: 10px;
  border-radius: 10px;
}

.title {
  font-weight: bold;
  color: rgb(59, 59, 59);
  margin-right: 5px;
}

.period {
  font-size: 0.8rem;
}

.right {
  position: relative;
}

.options {
  position: absolute;
  background-color: rgb(224, 224, 224);
  border-radius: 10px;
  padding: 10px;
  top: 0;
  right: 0;
  transform: translateY(20%);
  white-space: nowrap;
  display: none;
  z-index: 10;
}

.option {
  /* border-bottom: 1px solid black; */
  cursor: pointer;
}

.option:hover {
  color: rgb(158, 40, 40);
}

.options ul {
  /* list-style: none; */
  margin: 0;
  padding: 0;
}

.options ul a {
  text-decoration: none;
  color: #000000;
}

.options ul li {
  display: inline-block;
  margin-right: 10px;
  padding: 8px;
}

.right:hover .options {
  display: block;
}
</style>
