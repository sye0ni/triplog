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
    const wishlist = ref([]); // 사용자의 찜목록
    const storeBox = ref([]); // 보관함 (찜주머니에서 남은것들이 저장되어 넘어옴 / detail에서도 사용)
    const planBox = ref([]); // 여행계획 보관함..

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

    const attractionType = ref([
      {
        title: "관광지",
        value: "12",
      },
      {
        title: "문화시설",
        value: "14",
      },
      {
        title: "여행코스",
        value: "25",
      },
      {
        title: "행사/공연/축제",
        value: "15",
      },
      {
        title: "레포츠",
        value: "28",
      },
      {
        title: "숙박",
        value: "32",
      },
      {
        title: "쇼핑",
        value: "38",
      },
      {
        title: "음식점",
        value: "39",
      },
      {
        title: "전체",
        value: "",
      },
    ]);

    return {
      sidoCode,
      wishlist,
      storeBox,
      planBox,
      attractionType,
    };
  },
  {
    persist: { storage: sessionStorage },
  }
);
