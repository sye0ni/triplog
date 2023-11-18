<script setup>
import { ref } from "vue";
const props = defineProps({
  wish: Object,
});

const emit = defineEmits(["checkBoxEvent"]);

const isChecked = ref(false);
const checkBoxEvent = function () {
  console.log("checkbox click!");

  // if (!isChecked.value) {
  emit("checkBoxEvent", props.wish);
  // }
};
</script>

<template>
  <div class="out">
    <div class="container2">
      <div class="contents">
        <img :src="wish.firstImage" alt="" />
        <div v-if="!wish.firstImage" class="empty"></div>
        <b class="name">{{ wish.title }}</b>
        <span>{{ wish.addr1 }}</span>
      </div>
      <!-- <div class="menu">
        <i class="fa-solid fa-ellipsis-vertical" style="color: #d20000"></i>
      </div> -->
    </div>
    <input type="checkbox" @click="checkBoxEvent" v-model="isChecked" />
  </div>
</template>

<style scoped>
img {
  height: 50px;

  border-radius: 10px;
  margin-right: 10px;
}

.empty {
  width: 75px;
}

.out {
  width: 85%;
  min-width: 600px;
  max-width: 900px;
  display: flex;
  align-items: center;
  justify-content: center;
}

.container2 {
  background-color: rgba(217, 217, 217, 0.16);
  border: 1px solid black;
  padding: 10px;
  margin: 10px 0px;
  border-radius: 10px;
  width: 90%;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.contents {
  display: flex;
  align-items: center;
}
.menu {
  cursor: pointer;
}

b {
  width: 220px;
}

input[type="checkbox"] {
  /* width: 20px; */
  margin: 10px;
  transform: scale(2);
  /* border: 2px solid #2196f3; */
}
</style>
