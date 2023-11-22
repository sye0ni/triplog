<script setup>
import { ref, onMounted, onBeforeUnmount } from "vue";
import VSelect from "@/components/common/VSelect.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import { gugun, getAttractionList, modifyWishlist } from "@/api/plan";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";
import PlanSearchItem2 from "./PlanSearchItem2.vue";
import VRadio from "./VRadio.vue";

const emits = defineEmits(["showDetail", "sendAttrlist", "moveMap"]);
const planStore = usePlanStore();
const router = useRouter();

const { wishlist } = storeToRefs(planStore);

// -- 구군 얻어오기 시작
const { sidoCode, attractionType } = storeToRefs(planStore);

const selectOptionSido = sidoCode;
let selectOptionGugun = ref([{ text: "구/군", value: "" }]);

const param = ref({
  userId: "",
  sidoCode: "",
  gugunCode: "",
  contentTypeId: "",
  keyword: "",
});

const gugunCode = ref("3");

const searchText = ref("");

const changeKey = (val) => {
  console.log("시/도 선택한 조건 : " + val);
  param.value.sidoCode = val;
  param.value.gugunCode = "";
  selectOptionGugun.value = [{ text: "구/군", value: "" }];
  // gugunCode.value = "";
  searchText.value = "";
  console.log(param.value);
  param.value.keyword = searchText.value;

  gugun(
    param.value,
    ({ data }) => {
      for (let i = 0; i < data.length; i++) {
        const tmp = {
          text: "",
          value: ""
        };
        tmp.text = data[i].gugunName;
        tmp.value = data[i].gugunCode;
        selectOptionGugun.value.push(tmp);
      }
      // gugunCode.value = "5"; // 1번 selectbox 가 바뀌면 구군코드 초기화 
      console.log(gugunCode.value);
    },
    (error) => {
      console.log("시도 선택 error ", error);
    }
  );
};

const changeKey2 = (val) => {
  console.log("구/군 선택" + val);
  param.value.gugunCode = val;
  searchText.value = "";
  param.value.keyword = searchText.value; // 검색어 초기화 

  // if (val == "") {
  //   param.value.contentTypeId = null;
  // }

  // if (
  //   param.value.sidoCode == "" ||
  //   param.value.gugunCode == "" ||
  //   param.value.contentTypeId == ""
  // ) {
  //   return;
  // }

  getAttractionList(param.value, ({ data }) => {
    console.log("select2 변화!!!!!!!!!!!");
    // console.log(data.length + "get attractionList ,", data);
    attractionList.value = [];
    sendAttrList.value.length = 0;
    for (let i = 0; i < data.length; i++) {
      for (let j = 0; j < wishlist.value.length; j++) {
        if (data[i].contentId == wishlist.value[j].contentId) {
          data[i].isLike = true;
          console.log("like!!", data[i]);
        }
      }
      data[i].idx = i;
      attractionList.value.push(data[i]);
      sendAttrList.value.push(data[i]);
    }
    emits("sendAttrlist", sendAttrList.value);
  }),
    (error) => {
      console.log(error);
    };
};

// 여행지 목록 가져오기
const attractionList = ref([]);
const sendAttrList = ref([]);

const type = ref("");

const changeRadio = function (val) {
  console.log("change!!", val);
  param.value.contentTypeId = val;
  searchText.value = "";
  param.value.keyword = searchText.value; // 검색어 초기화 
  console.log(param.value);

  // if (val == "") { // '전체' 선택 
  //   param.value.contentTypeId = "";
  // }
  // if (
  //   param.value.sidoCode == "" ||
  //   param.value.gugunCode == ""
  // ) {
  //   return;
  // } // 선택되지 않은 값이 있으면 x

  getAttractionList(
    param.value,
    ({ data }) => {
      attractionList.value.length = 0;
      sendAttrList.value.length = 0;
      // console.log(wishlist.value.length + "?????");
      for (let i = 0; i < data.length; i++) {
        data[i].idx = i;
        for (let j = 0; j < wishlist.value.length; j++) {
          // console.log(data[i].contentId + ", " + wishlist.value[j].contentId);
          if (data[i].contentId == wishlist.value[j].contentId) {
            data[i].isLike = true;
            console.log("like!!", data[i]);
          }
        }
        attractionList.value.push(data[i]);
        sendAttrList.value.push(data[i]);
      } // attractionList 생성

      emits("sendAttrlist", sendAttrList.value);
    },
    (error) => {
      console.log(error);
    }
  );

  // 좋아요 여부 포함
};

// 검색어 입력시 
const searchAttrs = function () {
  if (searchText.value == '') {
    alert("검색어를 입력하세요!");
  }
  else {
    // console.log("검색어!!!!", searchText.value);
    // console.log("선택1 시/도: ", param.value.sidoCode);
    // console.log("선택2 구/군: ", param.value.gugunCode);
    // console.log("선택3 타입: ", param.value.contentTypeId);

    param.value.keyword = searchText.value;

    console.log("검색어 입력: ", param.value);

    getAttractionList(param.value,
      ({ data }) => {
        attractionList.value = [];
        sendAttrList.value.length = 0;
        for (let i = 0; i < data.length; i++) {
          for (let j = 0; j < wishlist.value.length; j++) {
            if (data[i].contentId == wishlist.value[j].contentId) {
              data[i].isLike = true;
              console.log("like!!", data[i]);
            }
          }
          data[i].idx = i;
          attractionList.value.push(data[i]);
          sendAttrList.value.push(data[i]);
        }
        emits("sendAttrlist", sendAttrList.value);
      }
    )


  }
}

onMounted(() => {
  // console.log("planSearch!!", attractionType);
});

const likeChange = function (likeIdx, idx) {
  console.log("likeChange 받음!", likeIdx + " " + idx);
  console.log(attractionList.value[idx]);
  if (likeIdx == -1) {
    attractionList.value[idx].isLike = true;
  } else {
    attractionList.value[idx].isLike = undefined;
  }
};

const wishParam = ref({
  userId: "",
  wishlist: {},
});

const makeWishlist = function () {
  console.log("찜주머니 만들러 가자!!");

  // pinia에 저장해놓은 wishlist 들고 만들러 감
  let token = sessionStorage.getItem("accessToken");
  let decodeToken = jwtDecode(token);

  wishParam.value.userId = decodeToken.userId;
  wishParam.value.wishlist = wishlist.value;
  console.log("wishparam", wishParam.value);
  modifyWishlist(
    wishParam.value,
    ({ data }) => {
      console.log("modify wish=>", data);
      alert("찜주머니가 만들어졌습니다!!");
    },
    (error) => {
      console.log("modify wish error ->", error);
    }
  );
};

const showDetail = function (arg) {
  emits("showDetail", arg);
};

// 여행지 하나 클릭
const moveMap = function (arg) {
  emits("moveMap", arg);
};
</script>

<template>
  <div>
    <!--  -->
    <div class="subItem search">
      <div class="title">찜 주머니 만들기</div>
      <div class="subTitle">
        찜 버튼을 눌러 여행 장소를 주머니에 담고 <br />
        만들기 버튼을 눌러주세요
      </div>

      <div class="select">
        <div class='selectSelect'>
          <div class="searchInputWrapper">
            <input class='searchInput' type='text' placeholder='검색어를 입력하세요' v-model='searchText' />
            <i class="searchBtn fa-solid fa-magnifying-glass" @click="searchAttrs"></i>
          </div>
          <VSelect :selectOption="selectOptionSido" @onKeySelect="changeKey" />
          <VSelect :selectOption="selectOptionGugun" @onKeySelect="changeKey2" :index="gugunCode" />
        </div>
      </div>

      <div class="pt-2">
        <div class="radio">
          <VRadio v-for="attraction in attractionType" :key="attraction.title" :item="attraction" v-model="type"
            @changeValue="changeRadio" />
        </div>
        <!--  -->
      </div>

      <div class="scroll">
        <table class="table custom-table">
          <thead>
            <tr>
              <!-- <th scope="col" class="three">사진</th> -->
              <th scope="col" class="colTitle">제목</th>
              <th scope="col" class="addr">주소</th>
              <th scope="col" class="detail" title="상세보기">
                <i class="fa-solid fa-magnifying-glass"></i>
              </th>
              <th scope="col" class="wish">찜</th>
            </tr>
          </thead>
          <tbody>
            <PlanSearchItem2 v-for="item in attractionList" :key="item.contentId" :item="item" @like-change="likeChange"
              @show-detail="showDetail" @select-attr="moveMap" />
          </tbody>
        </table>
      </div>
      <button class="makeBtn" @click="makeWishlist">만들기</button>
    </div>
  </div>
</template>

<style scoped>
* {
  box-sizing: border-box;
}

.subItem {
  /* border-right: 1px solid #b8b8b8; */
  height: 85vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
}

.select {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.searchInputWrapper {
  display: flex;
  align-items: center;
  width: 250px;
  /* 필요에 따라 조정 */
  border: 2px solid;
  border-radius: 10px;
  overflow: hidden;
  height: 35px;
  padding: 5px 5px;
}


.searchInput {
  flex: 1;
  padding: 5px;
  border: none;
  outline: none;
  /* display: flex; */
  /* justify-content: space-between; */
  /* align-items: center; */
}


.searchBtn {
  cursor: pointer;
}

.title {
  font-size: 1.5rem;
  font-weight: bold;
  padding-bottom: 10px;
}

.subTitle {
  text-align: center;
  padding-bottom: 10px;
}

.search {
  width: 100%;
  /* min-width: 400px; */
}

.selectSelect {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.selectSelect>* {
  width: 250px;
  margin: 10px 0px;
}

.scroll {
  overflow-y: auto;
}

.colTitle {
  width: 30%;
}

.detail {
  width: 40px;
  padding: 0px;
  /* display: flex; */
  /* justify-content: center; */
}

.wish {
  width: 40px;
}

.radio {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 10px;
}

/* table */
table {
  border-collapse: collapse;
  table-layout: fixed;
  width: 100%;
}

.custom-table th {
  border-bottom: 2px solid #333;
  text-align: center;
  padding: 10px 10px;
}

thead {
  vertical-align: bottom;
}

.makeBtn {
  margin-top: auto;
  width: 90px;
  min-height: 40px;
  height: 40px;
  font-size: 1rem;
  font-weight: bold;
  border-radius: 10px;
  background-color: #c62f2f;
  border: none;
  color: white;
  cursor: pointer;
}
</style>
