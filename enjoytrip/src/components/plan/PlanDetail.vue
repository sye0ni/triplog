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

const MIN_WIDTH = 1100; // 최소 허용 너비
const MAX_WIDTH = window.innerWidth; // 최대 허용 너비

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
  // console.log(arg); // 받아온 여행지를 wishlistdetail 로 넘겨야함
  attraction.value = arg;
  // console.log(attraction.value);
  toggleModal();
};

// --- planCreateInfo

const param = ref({
  list: "",
  planId: "",
  userPlanNth: "",
});

const goMakePlanDetail = function () {
  console.log("go make plan!!");

  // param.value.planBox = JSON.parse(JSON.stringify(planBox.value[0]));
  // console.log(JSON.parse(JSON.stringify(planBox.value[0])), "gg");
  // console.log(param.value);

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
        alert("계획 등록에 실패했습니다.");
        return;
      }
    );
  }

  alert("계획 생성 완료!");
  let tmp = { planId: planCreateInfo.value.planId };

  router.push({
    name: "plan-list-detail",
    params: { planId: planCreateInfo.value.planId },
  });
};
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
            <PlanSearch
              @send-attrlist="startMap"
              @move-map="moveMap"
              @show-detail="showDetail"
            />
            <button class="makeBtn" @click="goMakePlanDetail">만들기</button>
          </div>

          <div class="subItem tempBox">
            보관함
            <PlanDetailTempList />
          </div>
          <div class="subItem plan">
            <div class="subTitle">여행계획</div>
            <PlanDetailList
              v-for="index in planCreateInfo.period"
              :key="index"
              :nth="index"
            />
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
  overflow-y: auto;
}

.subTitle {
  padding-bottom: 20px;
}

button {
  cursor: pointer;
}
</style>
