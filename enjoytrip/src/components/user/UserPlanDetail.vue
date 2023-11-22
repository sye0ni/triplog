<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import VSelect from "@/components/common/VSelect.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import { getPlan } from "@/api/plan";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter, useRoute } from "vue-router";
import UserPlanDetailList from "@/components/user/UserPlanDetailList.vue";
import WishlistDetail from "@/components/plan/WishlistDetail.vue";

const planStore = usePlanStore();
const router = useRouter();
const route = useRoute();

const { planDetailBox, planBox } = storeToRefs(planStore);

const startpos = ref(0);
const diffpos = ref(0);
const isEnable = ref(false);
const range = 50;
const leftWidth = ref("50%");
const rightWidth = ref("100%");
const separatorWidth = ref("3px");

const MIN_WIDTH = 800; // 최소 허용 너비
const MAX_WIDTH = window.innerWidth; // 최대 허용 너비 (현재 창 너비에서 100px 제외)

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
// --- 페이지 나누기 끝

// --- 여행계획 얻기
const param = ref({
  userId: "",
});

const period = ref();

onMounted(() => {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  console.log("route ", route.params);
  //   let planId = toString(route.params);
  param.value.userId = decodeToken.userId;
  console.log("userId", decodeToken.userId);
  getPlan(
    route.params.planId,
    param.value,
    ({ data }) => {
      console.log("plan data", data);
      planDetailBox.value = [];
      period.value = data[0].period;
      console.log("period", period.value);
      for (let i = 0; i < period.value; i++) {
        planDetailBox.value.push([]);
      }

      for (let i = 0; i < data.length; i++) {
        console.log(data[i].userPlanNth - 1);
        planDetailBox.value[data[i].userPlanNth - 1].push(data[i]);
      }

      console.log("planDetailBox", planDetailBox.value);
    },
    (error) => {
      console.log(error);
    }
  );
});

const goPlanList = function () {
  router.push({ name: "plan-list" });
};

const goPlanEdit = function () {
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  console.log("route ", route.params);

  param.value.userId = decodeToken.userId;
  console.log("userId", decodeToken.userId);
  getPlan(
    route.params.planId,
    param.value,
    ({ data }) => {
      console.log("plan data", data);
      //   planList.value = [];
      planBox.value = [];
      period.value = data[0].period;
      console.log("period", period.value);
      for (let i = 0; i < period.value; i++) {
        planBox.value.push([]);
      }

      for (let i = 0; i < data.length; i++) {
        console.log(data[i].userPlanNth - 1);
        planBox.value[data[i].userPlanNth - 1].push(data[i]);
      }

      console.log("planBox", planBox.value);
    },
    (error) => {
      console.log("데이터 못가져옴", error);
    }
  );

  router.push({
    name: "plan-modify",
    params: { planId: route.params.planId },
  });
};

// --- temp
const tmplist = ref([
  {
    text: "일",
    value: "1",
  },
  {
    text: "이",
    value: "2",
  },
  {
    text: "삼",
    value: "",
  },
]);
</script>

<template>
  <div>
    <div class="borderContainer">
      <div class="d2 mapContainer" :style="{ width: rightWidth }">
        <VKakaoMap />
      </div>
      <div class="d1" :style="{ width: leftWidth }">
        <!--  -->
        <select>
          <option value="1">일</option>
          <option value="2">이</option>
          <option value="3" :selected="true">삼</option>
        </select>

        <div class="subContainer">
          <button @click="goPlanList">목록으로</button>
          <button @click="goPlanEdit">수정하기</button>
          <div class="subItem plan">
            <UserPlanDetailList v-for="index in period" :key="index" :nth="index" />
          </div>
        </div>
      </div>
      <div class="d3" :style="{ left: leftWidth }" @mousedown="onMouseDown"></div>
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

button {
  margin-left: 10px;
  margin-bottom: 10px;
  border: none;
  border-radius: 5px;
  height: 30px;
  width: 70px;
  background-color: #d20000;
  color: white;
  /* font-weight: bold; */
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
  background-color: #d20000;
  border: none;
  color: white;
  font-size: 1rem;
  font-weight: bold;
  padding: 10px;
  border-radius: 10px;
  width: 90px;
}

.borderContainer {
  display: flex;
  height: 85vh;
  position: relative;
  padding: 20px;
}

.subItem {
  /* border-right: 2px solid #b8b8b8; */
  height: 80vh;
  /* height: 100%; */
  width: 100%;
  display: flex;
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
  overflow-x: auto;
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
  cursor: col-resize;
  position: absolute;
  z-index: 1;
  margin: 0px;
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

button {
  cursor: pointer;
}
</style>
