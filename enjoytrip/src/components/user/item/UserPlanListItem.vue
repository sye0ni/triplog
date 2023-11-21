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
</script>

<template>
  <div>
    <div class="container">
      <div class="left">
        <img src="@/assets/image.png" alt="" />
        <div>
          <div>
            <span class="title" v-if="item.planName == null" style="margin-right: 5px"
              >{{ userInfo.userName }}님의 {{ item.userPlanNth }}번째 계획</span
            >
            <span v-else>{{ item.planName }}</span>
            <span>{{ item.location }}</span>
          </div>
          <div class="period">{{ item.startDate }}~{{ item.endDate }}</div>
        </div>
      </div>
      <div class="right" @click="dropMenu">
        <i class="fa-solid fa-bars" style="color: #000000; cursor: pointer"></i>
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

.right {
  display: flex;
  align-items: center;
}

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
}

.period {
  font-size: 0.8rem;
}
</style>
