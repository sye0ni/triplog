import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";
import { gugun } from "@/api/plan";

// import { userConfirm, findById, tokenRegeneration, logout } from "@/api/account.js";
import { httpStatusCode } from "@/util/http-status";

export const usePlanStore = defineStore(
  "planStore",
  () => {
    const sidoCode = ref([
      {
        text: "시/도",
        value: "",
      },
      {
        text: "서울",
        value: "1",
      },
      {
        text: "인천",
        value: "2",
      },
      {
        text: "대전",
        value: "3",
      },
      {
        text: "대구",
        value: "4",
      },
      {
        text: "광주",
        value: "5",
      },
      {
        text: "부산",
        value: "6",
      },
      {
        text: "울산",
        value: "7",
      },
      {
        text: "세종특별자치시",
        value: "8",
      },
      {
        text: "경기도",
        value: "31",
      },
      {
        text: "강원도",
        value: "32",
      },
      {
        text: "충청북도",
        value: "33",
      },
      {
        text: "충청남도",
        value: "34",
      },
      {
        text: "경상북도",
        value: "35",
      },
      {
        text: "경상남도",
        value: "36",
      },
      {
        text: "전라북도",
        value: "37",
      },
      {
        text: "전라남도",
        value: "38",
      },
      {
        text: "제주도",
        value: "39",
      },
    ]);

    return {
      sidoCode,
    };
  },
  {
    persist: { storage: sessionStorage },
  }
);
