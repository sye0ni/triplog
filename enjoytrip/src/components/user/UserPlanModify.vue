<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import VSelect from "@/components/common/VSelect.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import { registPlanNthDetail } from "@/api/plan";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";
import PlanSearch from "@/components/common/PlanSearch.vue";
import PlanDetailTempList from "@/components/plan/PlanDetailTempList.vue";
import PlanDetailList from "@/components/plan/PlanDetailList.vue";
import WishlistDetail from "@/components/plan/WishlistDetail.vue";

const planStore = usePlanStore();
const router = useRouter();

const { planCreateInfo, planBox } = storeToRefs(planStore);

const startpos = ref(0);
const diffpos = ref(0);
const isEnable = ref(false);
const range = 50;
const leftWidth = ref("70%");
const rightWidth = ref("100%");
const separatorWidth = ref("3px");

let item3minWidth = 1000;
const MIN_WIDTH = 900; // 최소 허용 너비
const MAX_WIDTH = window.innerWidth; // 최대 허용 너비 (현재 창 너비에서 100px 제외)

const onMouseMove = (event) => {
  if (isEnable.value) {
    const pos = event.clientX;
    diffpos.value = startpos.value - pos;
    const width = window.innerWidth / 2;

    if (diffpos.value > -(width - range) && diffpos.value < width - range) {
      console.log("move ", diffpos.value, width, range);
      let newWidth = width - diffpos.value;

      let tmp = 0;
      if (!planFoldToggle.value) {
        tmp -= 300;
      }
      if (!tempFoldToggle.value) {
        tmp -= 280;
      }

      // newWidth = Math.max(MIN_WIDTH, Math.min(newWidth, MAX_WIDTH)); // Ensure it stays within the limits
      newWidth = Math.max(item3minWidth + tmp, Math.min(newWidth, MAX_WIDTH));

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

const attractionList = ref([]);
const startMap = function (arg) {
  // kakao map 으로 attraction list 전달
  // console.log("검색결과!!!");
  // console.log(arg);
  attractionList.value = arg;
};

const showModal = ref(false);

const toggleModal = () => {
  showModal.value = !showModal.value;
};

const attraction = ref({});
const showDetail = function (arg) {
  attraction.value = arg;
  toggleModal();
};

// -- 지도 끝

const param = ref({
  list: "",
  planId: "",
  userPlanNth: "",
});

const period = ref();

const goModifyPlan = function () {
  console.log("go modify plan!!");

  for (let i = 0; i < planBox.value.length; i++) {
    console.log(i);
    param.value.planId = planCreateInfo.value.planId;
    param.value.list = planBox.value[i];
    param.value.userPlanNth = i + 1;
    registPlanNthDetail(
      param.value,
      ({ data }) => {
        console.log("data", data);
      },
      (error) => {
        console.log(error);
        alert("계획 수정에 실패했습니다.");
        return;
      }
    );
  }

  alert("계획 수정이 완료되었습니다.");
  let tmp = { planId: planCreateInfo.value.planId };

  router.push({
    name: "plan-list-detail",
    params: { planId: planCreateInfo.value.planId },
  });
};

// --
onMounted(() => {
  console.log("modify onMounted", planBox.value.length);
  period.value = planBox.value.length;
});

const tempFoldToggle = ref(true);
const tempFold = function () {
  tempFoldToggle.value = !tempFoldToggle.value;
  console.log("toggle: ", tempFoldToggle.value);

  if (tempFoldToggle.value && planFoldToggle.value) {
    leftWidth.value = 1000 + "px";
  } else if (tempFoldToggle.value) {
    leftWidth.value = 800 + "px";
  }
};
const planFoldToggle = ref(true);
const planFold = function () {
  planFoldToggle.value = !planFoldToggle.value;
  console.log("toggle: ", planFoldToggle.value);

  if (tempFoldToggle.value && planFoldToggle.value) {
    leftWidth.value = 1000 + "px";
  } else if (planFoldToggle.value) {
    leftWidth.value = 700 + "px";
  }
};
</script>

<template>
  <div>
    <div class="borderContainer">
      <button class="modifyBtn" @click="goModifyPlan">수정완료</button>
      <div class="foldToggle">
        <div>
          <label>
            <input role="switch" type="checkbox" checked @click="tempFold" />
            <span>보관함</span>
          </label>
        </div>
        <div>
          <label>
            <input role="switch" type="checkbox" checked @click="planFold" />
            <span>계획</span>
          </label>
        </div>
      </div>
      <div class="d2 mapContainer" :style="{ width: rightWidth }">
        <VKakaoMap />
      </div>
      <div class="d1" :style="{ width: leftWidth }">
        <div class="subContainer">
          <div class="subItem search">
            검색
            <PlanSearch
              @send-attrlist="startMap"
              @move-map="moveMap"
              @show-detail="showDetail"
            />
          </div>

          <div class="subItem tempBox" v-show="tempFoldToggle">
            보관함
            <PlanDetailTempList />
          </div>
          <div class="subItem plan" v-show="planFoldToggle">
            <div class="subTitle">여행계획</div>
            <PlanDetailList v-for="index in period" :key="index" :nth="index" />
          </div>
        </div>
      </div>
      <div
        class="d3"
        :style="{ left: leftWidth }"
        @mousedown="onMouseDown"
      ></div>
      <div class="modal">
        <Transition v-if="showModal">
          <WishlistDetail @click="toggleModal" :attraction="attraction" />
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

.modifyBtn {
  background-color: #d20000;
  border: none;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  padding: 10px;
  border-radius: 5px;
  width: 90px;

  position: absolute;
  right: 0px;
  z-index: 10;
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
  /* max-width: 550px; */
}

.tempBox {
  min-width: 250px;
  max-width: 300px;
}

.plan {
  min-width: 200px;
  max-width: 350px;
  padding: 10px;
  padding-top: 0px;
  overflow-y: auto;
}

.subTitle {
  padding-bottom: 20px;
}

button {
  cursor: pointer;
}

/* toggle */
label {
  display: inline-flex;
  align-items: center;
  gap: 0.5rem;
  cursor: pointer;
}

[type="checkbox"] {
  appearance: none;
  position: relative;
  border: max(2px, 0.1em) solid gray;
  border-radius: 1.25em;
  width: 2.25em;
  height: 1.25em;
}

[type="checkbox"]::before {
  content: "";
  position: absolute;
  left: 0;
  width: 1em;
  height: 1em;
  border-radius: 50%;
  transform: scale(0.8);
  background-color: gray;
  transition: left 250ms linear;
}

[type="checkbox"]:checked {
  background-color: #d20000;
  border-color: #d20000;
}

[type="checkbox"]:checked::before {
  background-color: white;
  left: 1em;
}

[type="checkbox"]:disabled {
  border-color: lightgray;
  opacity: 0.7;
  cursor: not-allowed;
}

[type="checkbox"]:disabled:before {
  background-color: lightgray;
}

[type="checkbox"]:disabled + span {
  opacity: 0.7;
  cursor: not-allowed;
}

[type="checkbox"]:focus-visible {
  outline-offset: max(2px, 0.1em);
  outline: max(2px, 0.1em) solid #d20000;
}

[type="checkbox"]:enabled:hover {
  box-shadow: 0 0 0 max(4px, 0.2em) lightgray;
}

/* Global CSS */
body {
  display: grid;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

fieldset {
  border: none;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

*,
*::before,
*::after {
  box-sizing: border-box;
}

.foldToggle {
  position: absolute;
  top: 0;
  /* right: 0; */
  z-index: 100;
}
</style>
