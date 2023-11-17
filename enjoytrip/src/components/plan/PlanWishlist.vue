<script setup>
import VSelect from "@/components/common/VSelect.vue";
import PlanWishlist from "@/components/plan/item/PlanWishlistItem.vue";

import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";

const planStore = usePlanStore();
const router = useRouter();

const { sidoCode } = storeToRefs(planStore);

import { gugun, registPlan } from "@/api/plan";

const param = ref({
  userId: "",
  sidoCode: "",
  gugunCode: "",
  startDate: "",
  endDate: "",
});
</script>

<template>
  <div style="display: flex; align-items: center; justify-content: center">
    <div class="container">
      <div class="title">찜 주머니</div>
      <div>찜한 장소를 계획에 추가해보세요!!</div>
      <PlanWishlist />
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 2px solid rgb(187, 187, 187);
  border-radius: 10px;
  padding: 30px;
  min-width: 600px;
  margin-top: 30px;
  height: 100%;
  /* width: 800px; */
}

.title {
  font-size: 2rem;
  padding-bottom: 30px;
}

.line1 {
  display: flex;
  justify-content: center;
  align-items: center;
  /* text-align: center; */
}

.line1 > * {
  font-size: 1.3rem;
  /* padding: 20px; */
  margin: 10px 50px;
  width: 120px;
  height: 40px;
  text-align: center;
}

.line2 {
  display: flex;
  align-items: center;
  padding: 20px;
}

.line2 > * {
  display: flex;
  align-items: center;
  padding: 20px;
  font-size: 1.3rem;
}

input[type="date"] {
  width: 200px;
  height: 40px;
  font-size: 1.2rem;
  border-radius: 10px;
  margin: 0px 10px;
  border-color: #d20000;
  text-align: center;
}

hr {
  width: 90%;
  margin-top: 50px;
}

button {
  width: 180px;
  height: 50px;
  font-size: 1rem;
  margin-top: 60px;
  border-radius: 10px;
  background-color: #c62f2f;
  border: 0px;
  color: white;
}

.subTitle {
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  /* background-color: rgb(219, 219, 219); */
  /* border-radius: 10px; */
  /* height: 20px; */
}
</style>
