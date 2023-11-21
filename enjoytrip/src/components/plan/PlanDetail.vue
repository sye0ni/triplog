<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import VSelect from "@/components/common/VSelect.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import { gugun } from "@/api/plan";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";
import PlanSearch from "@/components/common/PlanSearch.vue";
import PlanDetailTempList from "@/components/plan/PlanDetailTempList.vue";
import PlanDetailList from "./PlanDetailList.vue";

const planStore = usePlanStore();
const router = useRouter();

const { planCreateInfo } = storeToRefs(planStore);

const startpos = ref(0);
const diffpos = ref(0);
const isEnable = ref(false);
const range = 50;
const leftWidth = ref("70%");
const rightWidth = ref("100%");
const separatorWidth = ref("3px");

const MIN_WIDTH = 1100; // 최소 허용 너비
const MAX_WIDTH = window.innerWidth; // 최대 허용 너비 (현재 창 너비에서 100px 제외)

const onMouseMove = (event) => {
  if (isEnable.value) {
    const pos = event.clientX;
    diffpos.value = startpos.value - pos;
    const width = window.innerWidth / 2;

    if (diffpos.value > -(width - range) && diffpos.value < width - range) {
      console.log("move ", diffpos.value, width, range);
      let newWidth = width - diffpos.value;
      newWidth = Math.max(MIN_WIDTH, Math.min(newWidth, MAX_WIDTH)); // Ensure it stays within the limits

      leftWidth.value = newWidth + "px";
      // leftWidth.value = width - diffpos.value + "px";
    }
  }
};

const onMouseUp = () => {
  isEnable.value = false;
};

const onMouseDown = (event) => {
  startpos.value = event.clientX + diffpos.value;
  isEnable.value = true;

  document.addEventListener("mousemove", onMouseMove);
  document.addEventListener("mouseup", onMouseUp);
};

// --- 페이지 나누기 끝

// --- planCreateInfo

// --- 지도
</script>

<template>
  <div>
    <div class="borderContainer">
      <div class="d2 mapContainer" :style="{ width: rightWidth }">
        <VKakaoMap />
      </div>
      <div class="d1" :style="{ width: leftWidth }">
        <!--  -->
        <div class="subContainer">
          <div class="subItem search">
            검색
            <PlanSearch />
            <button class="makeBtn">만들기</button>
          </div>

          <div class="subItem tempBox">
            보관함
            <PlanDetailTempList />
          </div>
          <div class="subItem plan">
            <div class="subTitle">여행계획</div>
            <PlanDetailList v-for="index in planCreateInfo.period" :key="index" :nth="index" />
          </div>
        </div>
      </div>
      <div class="d3" :style="{ left: leftWidth }" @mousedown="onMouseDown"></div>
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

.makeBtn {
  /* position: absolute; */
  /* z-index: 10; */
  /* right: 0; */
  /* margin: 30px 50px; */
  /* margin-bottom: 10vh; */
  background-color: #d20000;
  border: none;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  width: 90px;
  /* background-color: aqua; */
  /* top: 20; */
  /* left: 0; */
  /* float: right; */
}

.borderContainer {
  display: flex;
  height: 85vh;
  position: relative;
  padding: 20px;
}

.subContainer {
  display: flex;
}

.subItem {
  border-right: 2px solid #b8b8b8;
  height: 85vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  /* margin-bottom: 20px; */
}

.d1 {
  position: absolute;
  top: 20;
  left: 0;
  height: 100%;
  float: left;
  border-right: 1px solid #b8b8b8;
  margin-right: -1px;
  background-color: white;
  z-index: 2;
  /* border: 1px solid #b8b8b8; */
}

.d2 {
  float: left;
  height: 100%;
}

.d3 {
  float: left;
  width: 5px;
  height: 100%;
  /* background-color: #888888; */
  cursor: col-resize;
  position: absolute;
  z-index: 1;
  margin: 0px;
  /* border: 2px solid salmon; */
  border-left: 2px solid #b8b8b8;
}

.search {
  min-width: 400px;
  max-width: 550px;
}

.tempBox {
  min-width: 250px;
  max-width: 300px;
}

.plan {
  min-width: 200px;
  /* width: 200px; */
  /* display: flex;
  justify-content: center;
  align-items: center; */
  padding: 10px;
  padding-top: 0px;
}

.subTitle {
  padding-bottom: 20px;
}
</style>
