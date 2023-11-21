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
  // planBox.value.push([]);
});
</script>

<template>
  <div style="width: 100%">
    <div class="container">
      <div class="nth">{{ nth }}일차</div>
      <div class="itemContainer" style="min-height: 100px; width: 100%">
        <draggable
          v-model="planBox[nth - 1]"
          group="plan"
          @start="drag = true"
          @end="drag = true"
          item-key="contentId"
        >
          <template #item="{ element, index }">
            <PlanDetailListItem :item="element" :order="index" />
          </template>
        </draggable>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  min-height: 100px;
  width: 100%;
  /* max-width: 300px; */
  overflow-y: auto;
  /* border: 1px solid black; */
  border-bottom: 1px solid rgb(197, 197, 197);
  margin-bottom: 10px;
}

.title {
  text-align: center;
  margin-bottom: 20px;
}

.itemContainer {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.nth {
  font-weight: bold;
  font-size: 1.1rem;
  padding-bottom: 10px;
  text-align: center;
}
</style>
