<script setup>
import VSelect from "@/components/common/VSelect.vue";
import { ref, onMounted } from "vue";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";

const planStore = usePlanStore();
const router = useRouter();

const { sidoCode } = storeToRefs(planStore);

import { gugun, registPlan } from "@/api/plan";

const selectOptionSido = sidoCode;
let selectOptionGugun = ref([{ text: "구/군", value: "" }]);

const param = ref({
  userId: "",
  sidoCode: "",
  gugunCode: "",
  startDate: "",
  endDate: "",
});

const gugunCode = ref("0");

const changeKey = (val) => {
  console.log("시/도 선택한 조건 : " + val);
  param.value.sidoCode = val;
  param.value.gugunCode = "";
  gugunCode.value = "1";
  selectOptionGugun.value = [{ text: "구/군", value: "" }];
  gugun(
    param.value,
    ({ data }) => {
      for (let i = 0; i < data.length; i++) {
        const tmp = {
          text: "",
          value: "",
        };

        tmp.text = data[i].gugunName;
        tmp.value = data[i].gugunCode;
        selectOptionGugun.value.push(tmp);
      }
    },
    (error) => {
      console.log("시도 선택 error ", error);
    }
  );
};

const changeKey2 = (val) => {
  console.log("구/군 선택" + val);
  param.value.gugunCode = val;

  console.log(param.value);
};

const startDate = ref("");
const endDate = ref("");

onMounted(() => {
  console.log("planCreate onMounted!!");
  console.log("sidoCode ", sidoCode.value);
});

const createPlan = function () {
  console.log("createPlan");
  param.value.startDate = startDate.value;
  param.value.endDate = endDate.value;
  if (param.value.sidoCode == "" || param.value.gugunCode == "") {
    alert("여행지를 선택해주세요");
    return;
  } else if (param.value.startDate == "" || param.value.endDate == "") {
    alert("여행날짜를 선택해주세요");
    return;
  }

  console.log("여행 계획 만들러 가자11");
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);
  param.value.userId = decodeToken.userId;
  registPlan(
    param.value,
    ({ data }) => {
      console.log("createPlan data", data);
      router.push({ name: "plan-wishlist" });
    },
    (error) => {
      console.log("createPlan error", error);
    }
  );
};
</script>

<template>
  <div style="display: flex; align-items: center; justify-content: center">
    <div class="container">
      <!-- <div> -->
      <div class="title">여행을 시작해보세요!</div>
      <div class="line1">
        <div class="subTitle">여행지</div>
        <VSelect :selectOption="selectOptionSido" @onKeySelect="changeKey" />
        <VSelect :selectOption="selectOptionGugun" @onKeySelect="changeKey2" :index="gugunCode" />
      </div>
      <hr />
      <div class="line2">
        <div class="subTitle">여행 날짜</div>
        <div>시작일 <input v-model="startDate" class="date" type="date" :max="endDate" /></div>
        <div>종료일 <input v-model="endDate" class="date" type="date" :min="startDate" /></div>
      </div>
      <div>
        <button @click="createPlan">시작하기</button>
      </div>
      <!-- </div> -->
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
  padding: 50px;
  min-width: 600px;
  margin-top: 30px;
  /* width: 800px; */
}

.title {
  font-size: 2rem;
  padding-bottom: 50px;
}

.line1 {
  display: flex;
  justify-content: center;
  align-items: center;
  /* text-align: center; */
}

.line1 > * {
  font-size: 1.3rem;
  /* padding: 20px; */
  margin: 10px 50px;
  width: 120px;
  height: 40px;
  text-align: center;
}

.line2 {
  display: flex;
  align-items: center;
  padding: 20px;
}

.line2 > * {
  display: flex;
  align-items: center;
  padding: 20px;
  font-size: 1.3rem;
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
  width: 180px;
  height: 50px;
  font-size: 1rem;
  margin-top: 60px;
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
</style>
