<script setup>
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";

const planStore = usePlanStore();
const { attractionType } = storeToRefs(planStore);

const props = defineProps({
  item: Object,
  order: Number,
});

const emits = defineEmits([ 'showDetail']);
const type = ref("");

onMounted(() => {
  console.log("type: ", props.item.contentTypeId);
  console.log("pinia ", attractionType);

  for (let i = 0; i < attractionType.value.length; i++) {
    // console.log(i);
    if (props.item.contentTypeId == attractionType.value[i].value) {
      type.value = attractionType.value[i].title;
      return;
    }
  }
});

const showDetail = function () {
  // console.log(props.item.contentId);
  emits('showDetail', props.item.contentId);
}

</script>

<template>
  <div>
    <div class="container" @click='showDetail'>
      <div class="left">
        <div class="order">{{ order + 1 }}</div>
        <div class="type">{{ type }}</div>
      </div>
      <div>
        <div class="title">{{ item.title }}</div>
        <div class="addr">{{ item.addr1 }} {{ item.addr2 }}</div>
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
  width: 280px;
  min-height: 80px;
  display: flex;
  align-items: center;
  /* justify-content: center; */
  border: 1px solid rgb(151, 151, 151);
  border-radius: 10px;
  padding: 10px;
  /* margin: 10px; */
  margin-bottom: 20px;
  cursor: pointer;
}
.left {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding-right: 5px;
}
.order {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #e3e3e3;
  border-radius: 100%;
  width: 30px;
  min-width: 30px;
  height: 30px;
  margin-left: 5px;
  margin-right: 5px;
}

.type {
  text-align: center;
  font-size: 0.6rem;
  /* font-weight: bold;  */
  width: 40px;
  min-width: 40px;
}

.title {
  font-size: 0.9rem;
  font-weight: bold;
}

.addr {
  font-size: 0.7rem;
}
</style>
