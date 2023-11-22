<script setup>
import { ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { deletePlan, getPlan, modifyPlan } from "@/api/plan";

const props = defineProps({
  item: Object,
  index: String,
});

const emits = defineEmits(["deletePlan"]);

const router = useRouter();
const route = useRoute();

const memberStore = useMemberStore();
const planStore = usePlanStore();

const { isLogin, userInfo } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { planBox } = storeToRefs(planStore);

const dropMenu = function () {
  console.log("dropMenu");
};

const param = ref({
  userId: "",
  type: "planDate | planName",
  planId: "",
  startDate: "",
  endDate: "",
});

const startDate = ref("");
const endDate = ref("");
const period = ref();

const isPlanNameEdit = ref(false);
const isPlanPeriodEdit = ref(false);

const nameVmodel = ref("");
const nameRef = ref("");

const goPlanDetail = function () {
  console.log("goPlanDetail!!", props.item.planId);
  router.push({
    name: "plan-list-detail",
    params: { planId: props.item.planId },
  });
};

const goPlanNameEdit = function () {
  isPlanNameEdit.value = !isPlanNameEdit.value;
  if (isPlanNameEdit.value) {
    nameRef.value.focus();
  } else {
    param.value.type = "planName";
    param.value.planName = nameVmodel.value;
    modifyPlan(
      props.item.planId,
      param.value,
      ({ data }) => {
        console.log("data", data);
        location.reload(true);
        alert("계획이 수정되었습니다.");
      },
      (error) => {
        console.log(error);
        alert("계획 수정에 실패하였습니다.");
      }
    );
  }
};
const goPlanDateEdit = function () {
  isPlanPeriodEdit.value = !isPlanPeriodEdit.value;
  if (isPlanPeriodEdit.value) {
    nameRef.value.focus();
  } else {
    param.value.type = "planDate";
    param.value.startDate = startDate.value;
    param.value.endDate = endDate.value;

    modifyPlan(
      props.item.planId,
      param.value,
      ({ data }) => {
        console.log("data", data);
        location.reload(true);
        alert("계획이 수정되었습니다.");
      },
      (error) => {
        console.log(error);
        alert("계획 수정에 실패하였습니다.");
      }
    );
  }
};
const goPlanEdit = function () {
  // planBox -> 현재 계획 집어넣은 후에 수정페이지로 이동하기
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  console.log("route ", route.params);

  param.value.userId = decodeToken.userId;
  console.log("userId", decodeToken.userId);
  getPlan(
    props.item.planId,
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
    params: { planId: props.item.planId },
  });
};
const goDelete = function () {
  console.log("delete!!");
  deletePlan(
    props.item.planId,
    ({ data }) => {
      console.log("data", data);
      location.reload(true);
      alert("계획이 삭제되었습니다.");
    },
    (error) => {
      console.log("error!", error);
    }
  );
};
</script>

<template>
  <div class="PlanListItemTopContainer">
    <div class="container">
      <div class="left">
        <!-- <img src="@/assets/image.png" alt="" /> -->
        <!-- <i
          class="fa-regular fa-folder-open"
          style="color: #5c5c5c; padding-right: 5px"
        ></i> -->
        <i
          class="fa-solid fa-location-dot"
          style="color: #9a0e0e; padding-right: 5px"
        ></i>
        <div>
          <div>
            <span v-if="item.planName == null" class="title"
              >{{ userInfo.userName }}님의 {{ item.userPlanNth }}번째 계획</span
            >
            <span v-else class="title">{{ item.planName }}</span>
            <span>{{ item.location }}</span>
          </div>
          <div class="period">{{ item.startDate }}~{{ item.endDate }}</div>
        </div>
      </div>
      <div class="right">
        <i class="fa-solid fa-bars" style="color: #000000; cursor: pointer"></i>
        <div class="options">
          <ul class="option" @click="goPlanDetail">
            계획 상세보기
          </ul>
          <ul class="option" @click="goPlanNameEdit">
            여행 이름 수정
          </ul>
          <ul class="option" @click="goPlanDateEdit">
            여행 날짜 수정
          </ul>
          <ul class="option" @click="goPlanEdit">
            계획 수정
          </ul>
          <ul class="option" @click="goDelete">
            계획 삭제
          </ul>
        </div>
      </div>
    </div>
    <div>
      <div class="edit" v-show="isPlanNameEdit">
        <!-- <div class="edit"> -->
        <input
          class="planName"
          type="text"
          ref="nameRef"
          v-model="nameVmodel"
          @keyup.enter="goPlanNameEdit"
        />
        <button class="modifyBtn" @click="goPlanNameEdit">수정하기</button>
      </div>
      <div class="edit" v-show="isPlanPeriodEdit">
        <span>시작일</span
        ><input
          class="plandate"
          v-model="startDate"
          type="date"
          :max="endDate"
        />
        <span>종료일</span
        ><input
          class="plandate"
          v-model="endDate"
          type="date"
          :min="startDate"
        />
        <button class="modifyBtn" @click="goPlanDateEdit">수정하기</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

/* input:hover {
  outline: none;
} */

.PlanListItemTopContainer {
  display: flex;
  flex-direction: column;
  justify-content: center;
  width: 80%;
  margin-bottom: 20px;
}
.container {
  min-width: 400px;
  width: 90%;
  /* width: 70vh; */
  min-height: 60px;
  display: flex;
  /* align-items: center; */
  justify-content: space-between;
  border: 2px solid rgb(151, 151, 151);
  border-radius: 10px;
  padding: 5px 10px;
  /* margin: 10px; */
  /* margin-bottom: 20px; */
}

.edit {
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.left {
  display: flex;
  /* justify-content: center; */
  align-items: center;
}

img {
  /* background-color: rgb(179, 23, 23); */
  width: 40px;
  height: 40px;
  margin-right: 10px;
  border-radius: 10px;
}

.title {
  font-weight: bold;
  color: rgb(59, 59, 59);
  margin-right: 5px;
}

.period {
  font-size: 0.8rem;
}

/* edit */
.planName {
}

input[type="text"] {
  outline: none;
  border: none;
  margin-top: 10px;
  border-bottom: 2px solid rgb(214, 214, 214);
  text-align: center;
  width: 100%;
  max-width: 200px;
  font-size: 1rem;
  /* border-bottom: 1px solid black; */
}
.plandate {
  font-size: 1rem;
  margin-left: 20px;
  margin-right: 20px;
  /* border-radius: 5px; */
}

.modifyBtn {
  margin-left: 10px;
  height: 30px;
  border: none;
  border-radius: 5px;
  background-color: #d20000;
  color: white;
  font-weight: bold;
  cursor: pointer;
}

/* dropDown */

.right {
  position: relative;
  /* padding-top: 10px; */
  top: 25%;
}

.options {
  position: absolute;
  background-color: rgb(224, 224, 224);
  border-radius: 10px;
  padding: 10px;
  top: 0;
  right: 0;
  transform: translateY(20%);
  white-space: nowrap;
  display: none;
  z-index: 10;
}

.option {
  /* border-bottom: 1px solid black; */
  cursor: pointer;
}

.option:hover {
  color: rgb(158, 40, 40);
}

.options ul {
  /* list-style: none; */
  margin: 0;
  padding: 0;
}

.options ul a {
  text-decoration: none;
  color: #000000;
}

.options ul li {
  display: inline-block;
  margin-right: 10px;
  padding: 8px;
}

.right:hover .options {
  display: block;
}
</style>
