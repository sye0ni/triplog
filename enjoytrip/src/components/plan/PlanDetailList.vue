<script setup>
import PlanDetailListItem from "@/components/common/PlanDetailListItem.vue";
import { ref } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";

const planStore = usePlanStore();
const { planBox } = storeToRefs(planStore);

const temp = ref({
  title: "군포식당",
  addr1: "경기도 군포시 군포로556번길 6",
});

// -- drag
const dragEnter = function (event) {
  console.log("dragEnter");
  x;
};

const dragOver = function (event) {
  console.log("dragOver!");
};
const dropItem = function (event) {
  const item = JSON.parse(event.dataTransfer.getData("text/plan"));

  // 드롭 이벤트 정의
  console.log("dropped item:", item);
  // for(let i = 0; i < planBox.value.length; i++) {
  //   if(planBox.value[i].conten)
  // }
  planBox.value.push(item);
};
</script>

<template>
  <div style="width: 100%">
    <div class="container">
      <!-- <div class="title">여행계획</div> -->
      <div
        class="itemContainer"
        @dragenter.prevent="dragEnter"
        @dragover.prevent="dragOver"
        @drop.prevent="dropItem"
      >
        <PlanDetailListItem v-for="item in planBox" :key="item.contentId" :item="item" />
        <!-- <PlanDetailListItem :item="temp" /> -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  /* height: 100%; */
  height: 80vh;
  width: 100%;
  background-color: azure;
  overflow-y: auto;
  /* display: flex; */
  /* flex-direction: column; */
  /* align-items: center; */
  /* justify-content: center; */
}
.title {
  text-align: center;
  margin-bottom: 20px;
}

.itemContainer {
  /* background-color: aqua; */
  height: 100%;
  height: 80vh;
  width: 100%;
  /* width: 200px; */

  display: flex;
  flex-direction: column;
  align-items: center;
  /* justify-content: center; */
}
</style>
