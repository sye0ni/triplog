<script setup>
import { ref, onMounted, watch } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";

const planStore = usePlanStore();

const { storeBox, wishlist } = storeToRefs(planStore);

const props = defineProps({ item: Object });
const emits = defineEmits(["likeChange", "showDetail", "selectAttr"]);

const addEvent = function () {
  console.log("wish");

  if (props.item.isLike) {
    for (let i = 0; i < wishlist.value.length; i++) {
      if (wishlist.value[i].contentId == props.item.contentId) {
        wishlist.value.splice(i, 1);
        emits("likeChange", i, props.item.idx);
        return;
      }
    }
  }

  emits("likeChange", -1, props.item.idx);
  wishlist.value.push(props.item);
};

const showDetails = function () {
  console.log("상세보기");
  console.log(props.item); // 받은 고대로 돌려줘 
  emits("showDetail", props.item);
}


const attractionClick = function () { // 클릭한 요소 넘겨주기 
  // console.log("지도 이동해야...");
  // 나를 던져야해 
  emits("selectAttr", props.item); // 받은 고대로 돌려줘 
};
</script>

<template>
  <tr class="line">
    <!-- <td><img :src="item.firstImage" alt="" /></td> -->
    <td :title="item.title" @click="attractionClick">
      <b class="name">{{ item.title }}</b>
    </td>
    <td :title="item.addr1" @click="attractionClick">
      <span>{{ item.addr1 }}</span>
    </td>
    <td title="상세보기"><i class="fa-solid fa-magnifying-glass" @click='showDetails'></i></td>
    <td class="put" @click="addEvent">
      <span v-show="item.isLike == true">
        <i class="fa-solid fa-heart" style="color: #d20000"></i>
      </span>
      <span v-show="item.isLike == undefined">
        <i class="fa-regular fa-heart" style="color: #d20000"></i>
      </span>
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
