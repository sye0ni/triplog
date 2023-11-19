<script setup>
import VSelect from "../../common/VSelect.vue";
import { ref, onMounted } from "vue";
import { getSigun, writePhoto } from '@/api/boardPhoto.js';

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";


const memberStore = useMemberStore();

const { isLogin, userInfo } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;

const emit = defineEmits(['cancelWrite', 'writePhoto']);
const content = ref('');
const place = ref('');
const option = ref('');

const selectedFiles = ref([]);

const selectOption = ref([{
    text: "시/군",
    value: ''
}]);

onMounted(() => {
    // 시 군 받아와 
    getSigun(
        ({ data }) => {
            data.forEach(item => {
                selectOption.value.push({
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
});


const images = ref([]);

const currIdx = ref(0);


const getFileName = async (files) => { // 이미지 url 을 배열에 저장 , 사용자가 파일을 업로드할 때마다 실행
    // console.log(files);

    const promises = []; // FileReader의 작업을 담을 배열
    // selectedFiles.value = [];

    for (let i = 0; i < files.length; i++) {
        const file = files[i];
        selectedFiles.value.push(file);
        const promise = new Promise((resolve, reject) => {
            const reader = new FileReader();

            reader.onload = (e) => {
                images.value.push(e.target.result); // 이미지 URL을 배열에 추가하여 저장
                resolve(); // Promise 완료
            };

            reader.onerror = reader.onabort = reject; // 에러 발생 시 reject
            reader.readAsDataURL(file); // 파일을 읽어 이미지 URL로 변환
        });

        promises.push(promise); // 생성된 Promise를 배열에 추가
    }

    try {
        await Promise.all(promises); // 모든 Promise가 완료될 때까지 기다림
        console.log('모든 파일 처리 완료');
    } catch (err) {
        console.error('파일 처리 중 에러 발생:', err);
    }
};


const writePhotos = async () => {

    // 하나라도 입력되지 않았으면 alert 
    if (content.value == "" || option.value.sidoCode == undefined || option.value.gugunCode == undefined || place.value === '') {
        alert("모든 항목을 입력하세요!");
    }

    else {

        let photoJson = JSON.stringify({
            userId: userInfo.value.userId,
            content: content.value,
            sidoCode: option.value.sidoCode,
            gugunCode: option.value.gugunCode,
            place: place.value
        });

        let formData = new FormData();

        // 이미지 파일들을 FormData에 추가
        for (let i = 0; i < selectedFiles.value.length; i++) {
            formData.append('file', selectedFiles.value[i]);
        }

        let photoDto = new Blob([photoJson], { type: 'application/json' });
        formData.append("photoDto", photoDto);

        writePhoto(
            formData,
            ({ data }) => {
                console.log("파일 업로드 성공!!!!");
                emit("writePhoto");
            },
            (error) => {
                console.log(error);
                alert("업로드 할 수 없는 파일입니다!");
            }
        )
    }
}

</script>

<template>
    <transition name="modal" appear>
        <div class="modal modal-overlay" @click.self="$emit('close')">
            <div class="modal-window">
                <div class="modal-content">
                    <div class="box1">
                        <template v-if='images.length == 0'>
                            <label for="file">
                                <div class="btn-upload">사진 업로드</div>
                            </label>
                            <input multiple type="file" name="file" id="file" style='display: none;'
                                @change='getFileName($event.target.files)'>
                        </template>

                        <template v-else>
                            <div class='preview-image'>
                                <i v-if='currIdx > 0' id='idxMinus' class="fa-solid fa-circle-chevron-left fa-xl"
                                    @click='currIdx--'></i>
                                <img :src=images[currIdx] style='height:100%'>
                                <i v-if='currIdx < images.length - 1' id='idxPlus'
                                    class="fa-solid fa-circle-chevron-right fa-xl" @click='currIdx++'></i>
                            </div>
                            <div>
                                <label for="file">
                                    <i class="fa-solid fa-circle-plus fa-xl" id='uploadIcon'></i>
                                </label>
                                <input multiple type="file" name="file" id="file" style='display: none;'
                                    @change='getFileName($event.target.files)'>
                            </div>
                        </template>

                    </div>

                    <div class="rightBox">
                        <div class="box2">
                            <button @click.prevent='writePhotos'>등록</button>
                            <button @click.prevent="$emit('cancelWrite')">취소</button>
                        </div>
                        <div class="box3">
                            <textarea placeholder="글을 입력하세요." v-model='content'></textarea>
                        </div>
                        <div class="box4">
                            <span>장소</span>
                            <VSelect :selectOption="selectOption" v-model='option' />
                            <input type="text" placeholder="상세 위치를 입력하세요." v-model='place'>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<style scoped>
#uploadIcon {
    position: absolute;
    right: 1%;
    bottom: 3%;
    cursor: pointer;
}

#idxMinus {
    position: absolute;
    top: 50%;
    left: 10px;
    cursor: pointer;
}

#idxPlus {
    position: absolute;
    top: 50%;
    right: 10px;
    cursor: pointer;
}

i {
    color: rgba(0, 0, 0, 0.7);
}

.preview-image {
    height: 80%;
    width: 100%;
    /* background-color: aqua; */
    position: relative;
}

.preview-image img {
    height: 100%;
    width: 100%;
}

.modal.modal-overlay {
    display: flex;
    align-items: center;
    justify-content: center;
    position: fixed;
    z-index: 30;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
}

.modal-window {
    background: #fff;
    border-radius: 4px;
    overflow: hidden;
    width: 80%;
    height: 80%;
    display: inline-block;
}

.modal-content {
    /* margin: 2% 1%; */
    display: flex;
    height: 100%;
}

.rightBox {
    display: flex;
    flex-direction: column;
    height: 100%;
    width: 40%;
}


.modal-enter-active,
.modal-leave-active {
    transition: opacity 0.4s;
}


.modal-enter-active .modal-window,
.modal-leave-active .modal-window {
    transition: opacity 0.4s, transform 0.4s;
}

.modal-leave-active {
    transition: opacity 0.6s ease 0.4s;
}

.modal-enter,
.modal-leave-to {
    opacity: 0;
}

.modal-enter .modal-window,
.modal-leave-to .modal-window {
    opacity: 0;
    transform: translateY(-20px);
}

.box1 {
    height: 100%;
    width: 60%;
    display: flex;
    justify-content: center;
    align-items: center;
    border-right: solid 1px lightgray;
    position: relative;
    /* 부모 요소에 relative position을 추가합니다. */

}

.box2 {
    height: 10%;
    width: 100%;
    display: flex;
    justify-content: end;
    align-items: center;
    border-bottom: solid 1px lightgray;
    padding-right: 4%;
}


.box3 {
    height: 80%;
    width: 100%;
}

.box4 {
    height: 10%;
    width: 100%;
    display: flex;
    justify-content: space-evenly;
    align-items: center;
    border-top: solid 1px lightgray;

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

.btn-upload {
    width: 150px;
    height: 30px;
    background: #fff;
    border: 2px solid black;
    padding: 5px 5px;
    border-radius: 13px;
    font-weight: bold;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;

}

.btn-upload:hover {
    border-color: #d20000;
}

span {
    font-weight: bold;
}

input {
    outline: none;
    background: none;
    border-width: 0 0 1px;
    /* size:50px; */
    width: 50%;
}

input:focus {
    border-width: 0 0 2px;
}

.class {
    width: 50%;
}

textarea {
    border-width: 0;
    resize: none;
    width: 100%;
    height: 100%;
    max-width: 100%;
    /* background-color: rgb(247, 247, 247); */
    padding: 2%;
}

textarea:focus {
    outline: none;
}
</style>