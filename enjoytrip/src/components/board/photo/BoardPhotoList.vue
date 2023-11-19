<script setup>
import { ref, onMounted, watch } from "vue";
import VSelect from "../../common/VSelect.vue";
import BoardPhotoListItem from "./item/BoardPhotoListItem.vue";
import BoardPhotoWrite from "./BoardPhotoWrite.vue";
import BoardPhotoDetail from "./BoardPhotoDetail.vue";
import { listPhoto, getSigun } from "@/api/boardPhoto.js";
import { useMemberStore } from "@/stores/user";
import InfiniteLoading from "v3-infinite-loading";
import "v3-infinite-loading/lib/style.css";
import { storeToRefs } from "pinia";

const memberStore = useMemberStore();
const { isLogin, userInfo } = storeToRefs(memberStore);


const selectSortOption = ref([
    { text: "검색조건", value: "" },
    { text: "최신순", value: "latest" },
    { text: "오래된순", value: "oldest" },
    { text: "인기순", value: "popular" },
]);

const selectAreaOption = ref([
    { text: "시/군", value: "" },
    {
        text: "전체", value: {
            sidoCode: 0,
            gugunCode: 0
        }
    }
]);

const selectedSort = ref(""); // 정렬 select 값
const selectedArea = ref(""); // 지역 select 값


onMounted(() => {
    // 시 군 받아와 
    getSigun(
        ({ data }) => {
            data.forEach(item => {
                selectAreaOption.value.push({
                    text: item.sigunName,
                    value: {
                        sidoCode: item.sidoCode,
                        gugunCode: item.gugunCode
                    }
                });
            })
        },
        (error) => {
            console.log(error);
        }
    )

    if (isLogin.value) {
        param.value.userId = userInfo.value.userId;
    }
})

const param = ref({
    page: 1,
    word: "", // 단어 검색
    order: "", // 정렬
    sidocode: "",
    guguncode: "",
    userId: ""
})

// const paramWatcher = watch(param, (newValue, oldValue) => {
//     if (newValue !== oldValue) {
//         infiniteHandler();
//     }
// }, { deep: true });


// 정렬 
const sort = function (arg) {
    // console.log("선택된 값!!!", arg);

    param.value.order = arg; // 정렬 조건 추가 
    param.value.page = 1;
    param.value.word = ""; // 검색어 초기화 

    word.value = "";
    console.log("파라미터 상태", param.value);

    getIdentifier.value += 1;
    photos.value = [];

}

// 검색 (지역 select)
const searchArea = function (arg) {
    // photos.value = [];
    param.value.word = "";
    word.value = "";
    // console.log("선택된 값!!!!", arg); // sidoCode, gugunCode 있음

    if (arg.sidoCode === 0 && arg.gugunCode === 0) { // '전체' 선택
        param.value.sidocode = '';
        param.value.guguncode = '';
    }
    else {
        param.value.sidocode = arg.sidoCode;
        param.value.guguncode = arg.gugunCode;
    }
    param.value.page = 1;
    console.log("파라미터 상태", param.value);
    getIdentifier.value += 1;
    photos.value = [];

}


const word = ref("");

// 검색어 입력 검색 
const searchWord = () => {
    param.value.word = word.value;
    param.value.order = "";
    param.value.sidocode = "",
        param.value.guguncode = "",
        param.value.page = 1;

    console.log("파라미터 상태", param.value);

    selectedArea.value = "";
    selectedSort.value = '';

    getIdentifier.value += 1;
    photos.value = [];

}

let photos = ref([]); // 받아올 photoList 배열
const message = ref('');



const infiniteHandler = async $state => {

    console.log("스크롤 로딩!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    console.log("page=", param.value.page); // 현재 페이지
    console.log("photo=", photos.value);


    // page + 1 씩 하면서 반복적으로 4개씩 받아오기 
    await listPhoto(
        param.value,
        ({ data }) => {
            setTimeout(() => {
                if (data.length > 0) {
                    param.value.page = param.value.page + 1;

                    photos.value.push(...data);

                    if (data.length < 4) $state.complete();
                    else $state.loaded();
                }
                else {
                    $state.complete();
                }
            }, 400)
        },
        (error) => {
            console.log(error);
        }
    )

}


const writeModal = ref(false);
const isWriteModalOpen = function () {
    writeModal.value = !writeModal.value;
}


const detailModal = ref(false);
let photoEmit = ref({});

const isDetailModalOpen = function (arg) {
    // photoId.value = arg.boardPhotoId;
    photoEmit.value = arg;
    detailModal.value = !detailModal.value;
}

const isReload = function () {
    location.reload();
}

// 검색 조건 및 정렬 변경 시마다 identifier 값 변경
const getIdentifier = ref(0);

</script>

<template>
    <div>
        <div class="button">
            <div class="find">
                <span>검색 </span>
                <input type="text" placeholder="검색어..." @keyup.enter="searchWord" v-model="word" />
                <button id="findButton" @click="searchWord">
                    <i class="fa-solid fa-magnifying-glass" style="color: #000000"></i>
                </button>
            </div>
            <div class="secondBox">
                <div>
                    <span>정렬 </span>
                    <VSelect :selectOption="selectSortOption" @on-key-select="sort" v-model="selectedSort" />
                    <span>지역 </span>
                    <VSelect :selectOption="selectAreaOption" @on-key-select="searchArea" v-model="selectedArea" />
                </div>
                <div>
                    <!-- 로그인해야 보임 -->
                    <template v-if="isLogin">
                        <button type="button" id="writeButton" @click="isWriteModalOpen">글쓰기</button>
                        <Transition v-if="writeModal">
                            <BoardPhotoWrite @cancel-write='isWriteModalOpen' @write-photo="isReload" />
                        </Transition>
                    </template>
                </div>
            </div>
        </div>


        <div class='listContainer'>
            <div class="listItem">
                <BoardPhotoListItem v-for='photo in photos' :key='photo.boardPhotoId' :photo='photo'
                    @show-detail="isDetailModalOpen">
                </BoardPhotoListItem>
            </div>

            <infinite-loading spinner="waveDots" @infinite="infiniteHandler" :identifier="getIdentifier">
            </infinite-loading>

            <Transition v-if="detailModal">
                <BoardPhotoDetail :photoEmit='photoEmit' @cancel-detail="isDetailModalOpen" @delete-detail="isReload" />
            </Transition>

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

#writeButton {
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
    align-items: center;
}

.listContainer {
    display: flex;
    flex-direction: column;
    justify-content: center;
}

.spinner {
    border: 1px solid red;
    display: none;
}
</style>
