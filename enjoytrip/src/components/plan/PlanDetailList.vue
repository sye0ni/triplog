<script setup>
import PlanDetailListItem from "@/components/common/PlanDetailListItem.vue";
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import draggable from "vuedraggable";

const planStore = usePlanStore();
const { planBox } = storeToRefs(planStore);

const props = defineProps({ nth: Number });

// -- drag

onMounted(() => {
  console.log("detaillist onmounted ", props.nth, planBox.value);
  planBox.value.push([]);
  // console.log(planBox);
  // console.log("잉?" + planBox.value[0].plan.contentId);
  // console.log(planBox.value[props.nth].InstanceType);
});
</script>

<template>
  <div style="width: 100%">
    <div class="container">
      <div>{{ nth }}일차</div>
      <!-- <div class="title">여행계획</div> -->
      <!-- <div class="itemContainer"> -->
      <!-- <PlanDetailListItem v-for="item in planBox" :key="item.contentId" :item="item" /> -->
      <!-- <PlanDetailListItem :item="temp" /> -->
      <!-- {{ planBox[nth - 1] }} -->
      <div style="background-color: aquamarine; min-height: 100px; width: 100%">
        <draggable
          v-model="planBox[nth - 1]"
          group="plan"
          @start="drag = true"
          @end="drag = true"
          item-key="contentId"
        >
          <template #item="{ element }">
            <PlanDetailListItem :item="element" />
          </template>
        </draggable>
        <!-- </div> -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  /* height: 100px; */
  /* height: 80vh; */
  min-height: 100px;
  width: 100%;
  background-color: azure;
  border: 1px solid powderblue;
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
