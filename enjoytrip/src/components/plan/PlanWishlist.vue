<script setup>
import VSelect from "@/components/common/VSelect.vue";
import PlanWishlistItem from "@/components/plan/item/PlanWishlistItem.vue";

import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";
import { getWishlist } from "@/api/plan";

const planStore = usePlanStore();
const router = useRouter();

const { storeBox } = storeToRefs(planStore);

// import { } from "@/api/plan";

const checkedItems = ref([]);
const wishlist = ref([]);

const param = ref({
  userId: "",
  sidoCode: "",
  gugunCode: "",
  startDate: "",
  endDate: "",
});

onMounted(() => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  getWishlist(
    decodeToken.userId,
    ({ data }) => {
      // console.log("getWishlist!!", data);
      for (let i = 0; i < data.length; i++) {
        wishlist.value.push(data[i]);
      }
    },
    (error) => {
      console.log(error);
    }
  );
});

const goPlanMake = function () {
  // console.log("goPlanMake!!");
  // console.log(checkedItems.value);
  // console.log(storeBox + " , " + storeBox.value);
  // storeBox.value = [...checkedItems.value];
  storeBox.value = [];
  for (let i = 0; i < checkedItems.value.length; i++) {
    storeBox.value.push(checkedItems.value[i]);
  }
  console.log("storeBox!! ", storeBox.value);

  router.push({ name: "plan-detail" });
};

const checkBoxEvent = function (param) {
  // console.log("checkBoxEvent 수신!!", param);
  // console.log(param.contentId);
  for (let i = 0; i < checkedItems.value.length; i++) {
    if (checkedItems.value[i].contentId == param.contentId) {
      checkedItems.value.splice(i, 1);
      return;
    }
  }

  checkedItems.value.push(param);
};
</script>

<template>
  <div
    style="
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100%;
    "
  >
    <div class="container">
      <div class="title">찜 주머니</div>
      <div class="subTitle">찜한 장소를 계획에 추가해보세요!!</div>
      <div class="scroll">
        <PlanWishlistItem
          v-for="wish in wishlist"
          :key="wish.contentId"
          :wish="wish"
          @checkBoxEvent="checkBoxEvent"
        />
      </div>
      <button @click="goPlanMake">완료</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border: 2px solid rgb(187, 187, 187);
  border-radius: 10px;
  padding: 30px;
  width: 80%;
  min-width: 600px;
  margin-top: 30px;
  height: 100%;
  max-height: 1000px;
  height: 80vh;
}

.title {
  font-size: 2rem;
  padding-bottom: 30px;
}

.subTitle {
  padding-bottom: 20px;
}

input[type="date"] {
  width: 200px;
  height: 40px;
  font-size: 1.2rem;
  border-radius: 10px;
  margin: 0px 10px;
  border-color: #d20000;
  text-align: center;
}

hr {
  width: 90%;
  margin-top: 50px;
}

button {
  width: 100px;
  height: 50px;
  font-size: 1.1rem;
  font-weight: bold;
  margin-top: 30px;
  border-radius: 10px;
  background-color: #c62f2f;
  border: 0px;
  color: white;
}

.subTitle {
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  /* background-color: rgb(219, 219, 219); */
  /* border-radius: 10px; */
  /* height: 20px; */
}
.scroll {
  overflow-y: auto;
  flex: 1; /* 추가: 남은 공간을 모두 차지하도록 설정 */
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
</style>
