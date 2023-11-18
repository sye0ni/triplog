<script setup>
// import { ref } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";

const planStore = usePlanStore();

const { storeBox } = storeToRefs(planStore);

const props = defineProps({ item: Object });

const addEvent = function () {
  console.log("add");

  for (let i = 0; i < storeBox.value.length; i++) {
    // console.log(i);
    if (storeBox.value[i].contentId == props.item.contentId) {
      alert("이미 보관함에 추가된 항목입니다.");
      return;
    }
  }

  storeBox.value.push(props.item);
};

const attractionClick = function () {
  console.log("지도 이동해야...");
};
</script>

<template>
  <tr class="line" @click="attractionClick">
    <!-- <td><img :src="item.firstImage" alt="" /></td> -->
    <td :title="item.title">
      <b class="name">{{ item.title }}</b>
    </td>
    <td :title="item.addr1">
      <span>{{ item.addr1 }}</span>
    </td>
    <td class="put" @click="addEvent" title="보관함에 담기">
      <i class="fa-solid fa-right-to-bracket" style="color: #d20000"></i>
    </td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
  color: black;
}

td {
  border-bottom: 1px solid #afa9a9;
  padding: 10px 10px;
  white-space: nowrap; /* 텍스트를 한 줄로 표시 */
  overflow: hidden; /* 넘치는 부분을 숨김 */
  text-overflow: ellipsis; /* 넘치는 부분에 ...을 표시 */
}

tr:hover {
  background-color: #f8cbcb49;
}

img {
  width: 50px;
}

.put {
  cursor: pointer;
}

.line {
  cursor: pointer;
}
</style>
