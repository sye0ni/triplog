<script setup>
import PlanDetailTempListItem from "@/components/plan/item/PlanDetailTempListItem.vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";

const planStore = usePlanStore();
const { storeBox } = storeToRefs(planStore);
</script>

<template>
  <div>
    <div class="container">
      <!-- {{ storeBox }} -->
      <div class="subText">
        <i
          class="fa-solid fa-right-to-bracket fa-flip-both"
          style="color: #d20000"
        ></i>
        버튼을 눌러 항목을 보관함에서 제거해보세요
      </div>
      <PlanDetailTempListItem
        v-for="item in storeBox"
        :key="item.contentId"
        :item="item"
      />
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}
.container {
  width: 100%;
  padding: 20px 10px;
  /* background-color: aqua; */
  overflow-y: auto;
  height: 83vh;
}

.subText {
  padding-bottom: 20px;
}
</style>
