<script setup>
import { ref } from "vue";
import { deleteWish } from "@/api/plan";
import { jwtDecode } from "jwt-decode";

const props = defineProps({
  wish: Object,
});

const param = ref({
  userId: "",
  contentId: "",
});

const goDeleteWish = function () {
  console.log("go deleteWish!!!");

  console.log("item ", props.wish);
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);

  param.value.userId = decodeToken.userId;
  param.value.contentId = props.wish.contentId;
  deleteWish(
    param.value,
    ({ data }) => {
      console.log("data");
      location.reload(true);
    },
    (error) => {
      console.log(error);
    }
  );
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
      <i
        class="fa-regular fa-trash-can"
        style="color: #9a0e0e"
        @click="goDeleteWish"
        title="찜 삭제"
      ></i>
      <!-- <i class="fa-regular fa-square-minus" style="color: #830c0c"></i> -->
    </div>
  </div>
</template>

<style scoped>
img {
  height: 35px;

  border-radius: 10px;
  margin-right: 10px;
}

.empty {
  width: 50px;
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
  height: 35px;
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

.deleteBtn {
}
</style>
