<script setup>
import VSelect from "@/components/common/VSelect.vue";
import UserWishlistItem from "@/components/user/item/UserWishlistItem.vue";

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

onMounted(() => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  getWishlist(
    decodeToken.userId,
    ({ data }) => {
      for (let i = 0; i < data.length; i++) {
        wishlist.value.push(data[i]);
      }
    },
    (error) => {
      console.log(error);
    }
  );
});

const checkBoxEvent = function (param) {
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
  <div class="container">
    <div class="title">찜 주머니</div>
    <hr />
    <div class="innerContainer">
      <UserWishlistItem
        v-for="wish in wishlist"
        :key="wish.contentId"
        :wish="wish"
        @checkBoxEvent="checkBoxEvent"
      />
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100%;
}

.innerContainer {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
  align-items: center;
}

.title {
  width: 90%;
  font-size: 1.4rem;
  font-weight: 600;
  padding-top: 40px;
  padding-bottom: 10px;

  min-width: 130px;
  text-align: left;
}

button {
  background-color: #ffffff00;
  border-radius: 10px;
  border: 1px solid black;
  font-size: 0.7rem;
  height: 33px;
  width: 76px;
}

button:hover {
  border-color: #ca0f0f;
  cursor: pointer;
}

hr {
  margin-top: 0px;
  margin-bottom: 20px;
  width: 90%;
  /* min-width: 500px; */
}
</style>
