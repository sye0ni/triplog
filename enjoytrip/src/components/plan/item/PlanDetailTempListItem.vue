<script setup>
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";

const planStore = usePlanStore();
const { storeBox } = storeToRefs(planStore);

const props = defineProps({
  item: Object,
});

const deleteItem = function () {
  console.log("보관함에서 제거!!");
  for (let i = 0; i < storeBox.value.length; i++) {
    if (storeBox.value[i].contentId == props.item.contentId) {
      storeBox.value.splice(i, 1);
    }
  }
};
</script>

<template>
  <div>
    <div class="container">
      <div>
        <div class="title">{{ item.title }}</div>
        <div class="addr">{{ item.addr1 }}</div>
      </div>
      <div class="out" title="보관함에서 제거" @click="deleteItem">
        <i
          class="fa-solid fa-right-to-bracket fa-flip-both"
          style="color: #d20000"
        ></i>
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
  display: flex;
  align-items: center;
  justify-content: space-between;
  border: 1px solid rgb(151, 151, 151);
  border-radius: 10px;
  padding: 10px;
  margin-bottom: 20px;
  cursor: pointer;
}

.title {
  font-weight: bold;
}

.addr {
  font-size: 0.8rem;
}

.out {
  cursor: pointer;
}
</style>
