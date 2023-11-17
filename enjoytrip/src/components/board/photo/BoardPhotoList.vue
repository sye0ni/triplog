<script setup>
import { ref, onMounted } from "vue";
import VSelect from "../../common/VSelect.vue";
import BoardPhotoListItem from "./item/BoardPhotoListItem.vue";
import BoardPhotoWrite from "./BoardPhotoWrite.vue";
import { listPhoto } from "@/api/boardPhoto.js";
import { useMemberStore } from "@/stores/user";
import InfiniteLoading from "v3-infinite-loading";
import "v3-infinite-loading/lib/style.css";

const memberStore = useMemberStore();

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

let page = ref({ page: 1 });
let photos = ref([]); // 받아올 photoList 배열


const infiniteHandler = async $state => {
  console.log("스크롤 로딩!!");
  console.log("page=", page.value.page); // 현재 페이지

  // page + 1 씩 하면서 반복적으로 4개씩 받아오기 
  await listPhoto(
    page.value,
    ({ data }) => {
      setTimeout(() => {

        console.log("get photo list", data); // data에 photo list 

        page.value.page = page.value.page + 1; // 페이지 증가
        photos.value.push(...data);
        console.log("photos:" + photos.value);
        console.log(photos.value[0]);

        if (data.length < 4) $state.complete();
        else $state.loaded();

      }, 800)
    },
    (error) => {
      console.log(error);
    }
  )
}

const modal = ref(false);
const isModalOpen = function () {
  modal.value = !modal.value;
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
          <button type="button" id="writeButton" @click="isModalOpen">글쓰기</button>
          <Transition v-if="modal">
            <BoardPhotoWrite @cancel-event='isModalOpen' />
          </Transition>
        </div>
      </div>
    </div>

    <div class='listContainer'>
      <div class="listItem">
        <BoardPhotoListItem v-for='photo in photos' :key='photo.boardPhotoId' :photo='photo'>
        </BoardPhotoListItem>
      </div>
      <infinite-loading @infinite="infiniteHandler"></infinite-loading>
    </div>



  </div>
</template>

<style scoped>
.modal-enter-active,
.modal-leave-active {
  transition: opacity 0.4s;
}


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
  justify-content: center;
  flex-wrap: wrap;
}

.listContainer {
  display: flex;
  flex-direction: column;
  /* justify-content:center; */
}

.spinner {
  border: 1px solid red;
  display: none;
}
</style>
