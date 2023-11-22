<script setup>
import { ref, onMounted, onBeforeMount } from "vue";
import VSelect from "@/components/common/VSelect.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
import { gugun, getAttractionList } from "@/api/plan";
import { storeToRefs } from "pinia";
import { usePlanStore } from "@/stores/plan";
import { jwtDecode } from "jwt-decode";
import { useRouter } from "vue-router";
import PlanSearchItem from "@/components/common/PlanSearchItem.vue";
import VRadio from "./VRadio.vue";
import SidoSelect from "@/components/plan/item/SidoSelect.vue";
import GugunSelect from "@/components/plan/item/GugunSelect.vue";

const planStore = usePlanStore();
const router = useRouter();

// -- 구군 얻어오기 시작
const { sidoCode, attractionType, planCreateInfo } = storeToRefs(planStore);

const emits = defineEmits(["sendAttrlist", "moveMap"]);

const selectOptionSido = sidoCode;
let selectOptionGugun = ref([{ text: "구/군", value: "" }]);

const param = ref({
  userId: "",
  sidoCode: "",
  gugunCode: "",
  contentTypeId: "",
  keyword: "",
});

const gugunCode = ref("0");
const searchText = ref("");

const changeKey = (val) => {
  console.log("시/도 선택한 조건 : " + val);
  param.value.sidoCode = val;
  param.value.gugunCode = "";
  gugunCode.value = "";
  selectOptionGugun.value = [{ text: "구/군", value: "" }];
  searchText.value = "";
  console.log(param.value);
  param.value.keyword = searchText.value;

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
  searchText.value = "";
  param.value.keyword = searchText.value; // 검색어 초기화

  // if (val == "") {
  //   param.value.contentTypeId = null;
  // }

  if (
    param.value.sidoCode == "" ||
    param.value.gugunCode == "" ||
    param.value.contentTypeId == ""
  ) {
    return;
  }

  getAttractionList(param.value, ({ data }) => {
    console.log(data.length + "get attractionList ,", data);
    attractionList.value = [];
    sendAttrList.value.length = 0;
    for (let i = 0; i < data.length; i++) {
      attractionList.value.push(data[i]);
      sendAttrList.value.push(data[i]);
    }
    emits("sendAttrList", sendAttrList.value);
  }),
    (error) => {
      console.log(error);
    };
};

// 여행지 목록 가져오기
const attractionList = ref([]);

const type = ref("");

const sendAttrList = ref([]);

const changeRadio = function (val) {
  console.log("change!!", val);
  param.value.contentTypeId = val;
  searchText.value = "";
  param.value.keyword = searchText.value; // 검색어 초기화

  if (val == "") {
    param.value.contentTypeId = null;
  }
  if (
    param.value.sidoCode == "" ||
    param.value.gugunCode == "" ||
    param.value.contentTypeId == ""
  ) {
    return;
  }

  getAttractionList(
    param.value,
    ({ data }) => {
      console.log(data.length + "get attractionList ,", data);
      attractionList.value.length = 0;
      sendAttrList.value.length = 0;
      for (let i = 0; i < data.length; i++) {
        attractionList.value.push(data[i]);
        sendAttrList.value.push(data[i]);
      }

      emits("sendAttrlist", sendAttrList.value);
    },
    (error) => {
      console.log(error);
    }
  );
};

// 검색어 입력시
const searchAttrs = function () {
  if (searchText.value == "") {
    alert("검색어를 입력하세요!");
  } else {
    // console.log("검색어!!!!", searchText.value);
    // console.log("선택1 시/도: ", param.value.sidoCode);
    // console.log("선택2 구/군: ", param.value.gugunCode);
    // console.log("선택3 타입: ", param.value.contentTypeId);

    param.value.keyword = searchText.value;

    console.log("검색어 입력: ", param.value);

    getAttractionList(
      param.value,
      ({ data }) => {
        attractionList.value.length = 0;
        sendAttrList.value.length = 0;
        for (let i = 0; i < data.length; i++) {
          attractionList.value.push(data[i]);
          sendAttrList.value.push(data[i]);
        }
        emits("sendAttrlist", sendAttrList.value);
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

const moveMap = function (arg) {
  emits("moveMap", arg);
};

const showDetail = function (arg) {
  emits("showDetail", arg);
};

const planSidoCode = ref();
onBeforeMount(() => {
  console.log("planSearch!!", attractionType);
  planSidoCode.value = planCreateInfo.value.sidoCode;
  console.log("planSidoCode:", planSidoCode.value);

  param.value.sidoCode = planSidoCode.value;
  param.value.gugunCode = planCreateInfo.value.gugunCode;
  gugunCode.value = planCreateInfo.value.gugunCode;
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
});
</script>

<template>
  <div>
    <!--  -->
    <div class="subItem search">
      <div class="select">
        <!-- <VSelect :selectOption="selectOptionSido" @onKeySelect="changeKey" />
        <VSelect
          :selectOption="selectOptionGugun"
          @onKeySelect="changeKey2"
          :index="gugunCode"
        /> -->
        <SidoSelect
          :selectOption="selectOptionSido"
          @onKeySelect="changeKey"
          :index="planSidoCode"
        />
        <GugunSelect
          :selectOption="selectOptionGugun"
          @onKeySelect="changeKey2"
          :index="gugunCode"
        />
      </div>

      <div class="pt-2">
        <div class="radio">
          <VRadio
            v-for="attraction in attractionType"
            :key="attraction.title"
            :item="attraction"
            v-model="type"
            @changeValue="changeRadio"
          />
        </div>
        <!--  -->
      </div>

      <div class="scroll">
        <table class="table custom-table">
          <thead>
            <tr>
              <th scope="col" class="title">제목</th>
              <th scope="col" class="addr">주소</th>
              <th scope="col" class="detail" title="상세보기">
                <i class="fa-solid fa-magnifying-glass"></i>
              </th>
              <th scope="col" class="put">
                <i class="fa-solid fa-inbox" style="color: #990000"></i>
              </th>
            </tr>
          </thead>
          <tbody>
            <PlanSearchItem
              v-for="item in attractionList"
              :key="item.contentId"
              :item="item"
              @select-attr="moveMap"
              @show-detail="showDetail"
            />
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped>
.detail {
  width: 40px;
  padding: 0px;
  /* display: flex; */
  /* justify-content: center; */
}

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

/* .select > * {
  width: 250px;
  margin: 10px 0px;
} */

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

.selectSelect {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.selectSelect > * {
  width: 250px;
  margin: 10px 0px;
}

.searchBtn {
  cursor: pointer;
}

.search {
  width: 100%;
  /* min-width: 400px; */
}

.scroll {
  overflow-y: auto;
}

.title {
  width: 30%;
}

.put {
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
</style>
