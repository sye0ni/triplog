<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import VSelect from "@/components/common/VSelect.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import { gugun } from "@/api/plan";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";
import PlanSearch2 from "@/components/common/PlanSearch2.vue";
import PlanDetailTempList from "@/components/plan/PlanDetailTempList.vue";
import WishlistDetail from "@/components/plan/WishlistDetail.vue";

const planStore = usePlanStore();
const router = useRouter();

const startpos = ref(0);
const diffpos = ref(0);
const isEnable = ref(false);
const range = 50;
const leftWidth = ref("40%");
const rightWidth = ref("100%");
const separatorWidth = ref("3px");

const MIN_WIDTH = 400; // 최소 허용 너비
const MAX_WIDTH = window.innerWidth - 700; // 최대 허용 너비 (현재 창 너비에서 100px 제외)

const onMouseMove = (event) => {
  if (isEnable.value) {
    const pos = event.clientX;
    diffpos.value = startpos.value - pos;

    const width = window.innerWidth / 2;

    if (diffpos.value > -(width - range) && diffpos.value < width - range) {
      let newWidth = width - diffpos.value;
      newWidth = Math.max(MIN_WIDTH, Math.min(newWidth, MAX_WIDTH)); // Ensure it stays within the limits

      leftWidth.value = newWidth + "px";
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

const showModal = ref(false);

const toggleModal = () => {
  showModal.value = !showModal.value;
}

const attraction = ref({});
const showDetail = function (arg) {
  // console.log(arg); // 받아온 여행지를 wishlistdetail 로 넘겨야함 
  attraction.value = arg;
  // console.log(attraction.value);
  toggleModal();
}

const attractionList = ref([]);
const startMap = function (arg) {
  // kakao map 으로 attraction list 전달
  // console.log("검색결과!!!");
  // console.log(arg);
  attractionList.value = arg;
}

const selectAttr = ref({});
const moveMap = function (arg) {
  console.log("선택한 요소!!!");
  console.log(arg);
  selectAttr.value = arg;
}

// --- 페이지 나누기 끝

// --- 지도
</script>

<template>
  <div class="bigContainer">
    <div class="borderContainer">
      <div class="d2 mapContainer" :style="{ width: rightWidth }">
        <VKakaoMap :attractionList='attractionList' :attraction='selectAttr' />
      </div>
      <div class="d1" :style="{ width: leftWidth }">
        <!--  -->
        <div class="subItem search">
          <PlanSearch2 @show-detail='showDetail' @send-attrlist='startMap' @move-map='moveMap' />
        </div>
      </div>
      <div class="d3" :style="{ left: leftWidth }" @mousedown="onMouseDown">
      </div>
      <div class='modal'>
        <Transition v-if="showModal">
          <WishlistDetail @click='toggleModal' :attraction='attraction' />
        </Transition>
      </div>
    </div>

  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

.modal {
  /* position:relative; */
  position: fixed;
  top: 20%;
  right: 20%;
  /* background-color: gray; */
  z-index: 3;
}

.borderContainer {
  display: flex;
  height: 85vh;
  position: relative;
  padding: 20px;
}

.subItem {
  /* border-right: 2px solid #b8b8b8; */
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
  border-left: 2px solid #b8b8b8;
}

.search {
  min-width: 400px;
  /* max-width: 550px; */
}
</style>
