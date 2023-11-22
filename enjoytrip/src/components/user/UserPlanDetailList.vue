<script setup>
import PlanDetailListItem from "@/components/common/PlanDetailListItem.vue";
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import draggable from "vuedraggable";

const planStore = usePlanStore();
const { planDetailBox } = storeToRefs(planStore);

const props = defineProps({ nth: Number });
const emits = defineEmits(["showMap"])
// -- drag

onMounted(() => {
  console.log("detaillist onmounted ", props.nth, planDetailBox.value);
  // planDetailBox.value.push([]);
});

const showMap = function () {
  console.log("throwthrowthrow");
  emits("showMap", props.nth);
}


</script>

<template>
  <div style="width: 100%">
    <div class="container">
      <div class="nth" @click="showMap">{{ nth }}일차</div>
      <div class="itemContainer" style="min-height: 100px; width: 100%">
        <PlanDetailListItem v-for="(item, index) in planDetailBox[nth - 1]" :key="item.contentId" :item="item"
          :order="index" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  height: 100%;
  width: 100%;
  min-width: 250px;
  /* max-width: 300px; */
  overflow-y: auto;
  /* border: 1px solid black; */
  background-color: #fdfdfd;
  border-left: 1px solid gainsboro;
  border-right: 1px solid gainsboro;
  border-radius: 10px;
  margin-right: 10px;
  padding-top: 10px;

  /* border-bottom: 1px solid rgb(197, 197, 197); */
  margin-bottom: 10px;
  /* padding-right: 10px; */
  cursor: pointer;
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
