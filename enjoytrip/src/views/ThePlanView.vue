<script setup>
import { ref, onMounted } from "vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import VSelect from "@/components/common/VSelect.vue";

// const serviceKey = import.meta.env.VITE_OPEN_API_SERVICE_KEY;
const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const param = ref({
  serviceKey: VITE_OPEN_API_SERVICE_KEY,
  pageNo: 1,
  numOfRows: 20,
  zscode: 0,
});
</script>

<template>
  <div class="borderContainer">
    <div class="area">
      <div id="title">Plan View</div>
      <div class="list">
        <VKakaoMap />
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

#title {
  padding-left: 5%;
  font-size: 1.4rem;
  font-weight: 600;
  padding-top: 40px;
  padding-bottom: 0px;
  min-width: 130px;
}
</style>
