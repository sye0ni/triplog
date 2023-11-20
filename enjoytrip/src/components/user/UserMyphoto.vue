<script setup>
import UserMyPhotoItem from './item/UserMyPhotoItem.vue';
import BoardPhotoDetail from '../board/photo/BoardPhotoDetail.vue';
import { getMyPhoto } from "@/api/account.js";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { ref, onMounted } from "vue";

const memberStore = useMemberStore();

const { userInfo } = storeToRefs(memberStore);

const myPhotos = ref({}); // 객체 배열 

onMounted(() => {

    getMyPhoto(
        userInfo.value.userId,
        ({ data }) => {
            console.log(data);
            myPhotos.value = data; // 나의 게시글로 설정

        },
        (error) => {
            console.log(error);
        }
    )

})

const modal = ref(false); // modal 띄울지 말지 결정
const photoEmit = ref({});

const toggleModal = function (arg) {
    modal.value = !modal.value;
    photoEmit.value = arg;
}

const isReload = function () {
    location.reload();
}
</script>

<template>
    <div class="borderContainer">
        <div class="area">
            <div id="title">내가 공유한 후기</div>
            <hr>
            <div class="list">
                <UserMyPhotoItem v-for="photo in myPhotos" :photo="photo" @show-detail="toggleModal"></UserMyPhotoItem>
            </div>
        </div>
        <Transition v-if="modal">
            <BoardPhotoDetail :photoEmit='photoEmit' @cancel-detail="toggleModal" @delete-detail="isReload" />
        </Transition>
    </div>
</template>

<style scoped>
* {
    box-sizing: border-box;
}

.borderContainer {
    display: flex;
    justify-content: center;
    overflow-x: auto;
    padding: 0% 5%;
}

.area {
    /* margin: 10px; */
    width: 100%;
}

.list {

    display: flex;
    flex-wrap: wrap;

    align-items: flex-start;
    width: 100%;
    gap: 4%;

}

#title {
    font-size: 1.4rem;
    font-weight: 600;
    padding-top: 40px;
    padding-bottom: 0px;
    min-width: 130px;
}
</style>
