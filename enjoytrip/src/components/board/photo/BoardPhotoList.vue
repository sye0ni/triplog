<script setup>
import { ref, onMounted } from "vue";
import VSelect from "../../common/VSelect.vue";
// import BoardPhotoListItem from "./item/BoardPhotoListItem.vue";
import { listPhoto } from "@/api/boardPhoto.js";
import { useMemberStore } from "@/stores/user";

const memberStore = useMemberStore();
const { userInfo } = memberStore;

const selectSortOption = ref([
  { text: "검색조건", value: "" },
  { text: "최신순", value: "latest" },
  { text: "오래된순", value: "oldest" },
  { text: "인기순", value: "popular" },
]);
const selectAreaOption = ref([
  { text: "검색조건", value: "" },
  { text: "시/군", value: "sigun" },
]);

const page = ref(1);
const photos = ref([]); // 받아올 photoList 배열

onMounted(() => {
  // console.log(userInfo.value);
  infiniteHandler();
})


const infiniteHandler = function () {
  console.log("스크롤 로딩!!");
  // page + 1 씩 하면서 반복적으로 4개씩 받아오기 !!
  listPhoto(
    page.value,
    "ssafy",
    ({ data }) => {
      console.log("get photo list", data); // data에 photo list 
      page.value = page.value + 1;
      photos.value = data.value; // list 라는 속성을 갖도록 설정해야함 ! 
    },
    (error) => {
      console.log(error);
    }
  )
}

</script>

<template>
  <div>
    <div class="button">
      <div class="find">
        <span>검색 </span>
        <input type="text" placeholder="검색어..." />
        <button id="findButton">
          <i class="fa-solid fa-magnifying-glass" style="color: #000000"></i>
        </button>
      </div>
      <div class="secondBox">
        <div>
          <span>정렬 </span>
          <VSelect :selectOption="selectSortOption" />
          <span>지역 </span>
          <VSelect :selectOption="selectAreaOption" />
        </div>
        <div>
          <!-- 로그인해야 보임 -->
          <button type="button" id="writeButton">글쓰기</button>
        </div>
      </div>
    </div>
    <!-- <template>
                    <div class="listItem">
                      <BoardPhotoListItem v-for="photo in photos" :key="photo.boardPhotoId" :photo="photo" />
                    </div>
                    <infinite-loading @infinite="infiniteHandler" spinner="bubbles"></infinite-loading>
                  </template> -->


  </div>
</template>

<style scoped>
button {
  padding: 5px 10px;
  /* margin: 5px; */
  /* margin-bottom: 10px; */
  border-radius: 13px;
  background-color: white;
  font-weight: 700;
  font-size: 0.9rem;
}

button:hover {
  cursor: pointer;
  border-color: #d20000;
}

span {
  font-weight: 700;
  font-size: 0.9rem;
}

input {
  border: 2px solid;
  border-radius: 13px;
  height: 30px;
  padding: 0px 5px;
  /* margin: 5px; */
  margin-right: 0px;
}

input:focus {
  outline-color: #d20000;
}

#findButton {
  border: none;
}

#wirteButton {
  display: inline-block;
}

.secondBox {
  margin-top: 1%;
  display: flex;
  justify-content: space-between;
}

.listItem {
  display: flex;
  justify-content: space-evenly;
}
</style>
