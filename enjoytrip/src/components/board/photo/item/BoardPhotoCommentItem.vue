<script setup>
import { ref, onMounted } from "vue";
import { useMemberStore } from "@/stores/user";
import { storeToRefs } from "pinia";

const toggleContentState = ref(true);
const memberStore = useMemberStore();
const { isLogin, userInfo } = storeToRefs(memberStore);

onMounted(() => {
    console.log(userInfo.value.userId);
    console.log(props.comment.userId);
    console.log(isLogin.value);
});

const props = defineProps({
    comment: Object
})

const emit = defineEmits(["modifyComment", "deleteComment"]);
const commentInput = ref("");

const modifyComments = function () {

    // console.log(props.comment.value.boardPhotoId);
    console.log(props.comment.commentId);

    // 수정하러 가기 
    if (!toggleContentState.value) {
        if (props.comment.content.length == 0) {
            alert("내용을 입력하세요!");
            commentInput.value.focus();
        }
        else if (confirm("정말로 수정하시겠습니까?")) {
            emit("modifyComment", props.comment);
            toggleContentState.value = !toggleContentState.value;
        }
    }

    else {
        toggleContentState.value = !toggleContentState.value;
        commentInput.value.focus();
    }

}

const deletePhotos = function () {
    if (confirm("정말로 삭제하시겠습니까?")) {
        emit("deleteComment", props.comment);
    }
}


</script>

<template>
    <div class="container">
        <div class="box1">
            <span id="userId" style="font-weight: bold;">{{ comment.userId }}</span>
            <!-- 작성자의 경우에만 보이게 -->
            <div class="box1-1">
                <template v-if="isLogin && comment.userId==userInfo.userId">
                    <button @click.prevent='modifyComments'>수정</button>
                    <button @click.prevent='deletePhotos'>삭제</button>
                </template>
                <span style="font-size: x-small;">{{ comment.registerTime }}</span>
                <!-- <i class="fa-solid fa-ellipsis"></i> -->

            </div>
        </div>
        <div class="box2">
            <textarea :readOnly="toggleContentState" v-model="props.comment.content" ref="commentInput"></textarea>
        </div>
    </div>
</template>

<style scoped>
textarea {
    border-width: 0;
    resize: none;
    width: 98%;
    max-width: 98%;
    height: auto;

}

textarea:focus {
    outline: none;
}

/* Chrome, Safari 등 Webkit 기반 브라우저에서 스크롤바 스타일 지정 */
::-webkit-scrollbar {
    width: 6px;
}

::-webkit-scrollbar-track {
    background-color: transparent;
}

::-webkit-scrollbar-thumb {
    border-radius: 3px;
    background-color: lightgray;
}

::-webkit-scrollbar-button {
    width: 0;
    height: 0;
}

.container {
    display: flex;
    flex-direction: column;
    margin: 0px 5px;
    margin-top: 10px;
}

.box1 {
    height: 40%;
    /* background-color: lavender; */
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.box1-1 i {
    margin-left: 10px;
}

.box2 {
    /* height: 60%; */
    /* height: auto; */
    /* background-color: lightblue; */
    display: flex;
    /* justify-content: space-between; */
    /* align-items: center; */
    margin-top: 5px;
}

button {
    padding: 2px 2px;
    /* margin: 5px; */
    /* margin-bottom: 10px; */
    /* border-radius: 10px; */
    background-color: white;
    font-weight: 700;
    font-size: 0.6rem;
    margin-right: 5px;
    cursor: pointer;
    border: 0px;
}
</style>