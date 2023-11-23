<script setup>
import PlanDetailTempListItem from "@/components/plan/item/PlanDetailTempListItem.vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import draggable from "vuedraggable";

const planStore = usePlanStore();
const { storeBox } = storeToRefs(planStore);

// ----- 드래그...

const startDrag = function () {
  console.log("dragStart!!");
};
const dragEnd = function () {
  console.log("dragEnd!!");
};
</script>

<template>
  <div>
    <div class="container">
      <div class="subText">
        <i class="fa-solid fa-right-to-bracket fa-flip-both" style="color: #d20000"></i>
        버튼을 눌러 항목을 보관함에서 제거해보세요
      </div>
      <div style="height: 100%">
        <draggable
          v-model="storeBox"
          group="plan"
          @start="drag = true"
          @end="drag = true"
          item-key="contentId"
          style="height: 100%"
        >
          <template #item="{ element }">
            <PlanDetailTempListItem :item="element" />
          </template>
        </draggable>
      </div>
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
