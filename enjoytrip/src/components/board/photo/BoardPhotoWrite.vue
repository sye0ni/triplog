<script setup>

</script>

<template>
    <transition name="modal" appear>
        <div class="modal modal-overlay" @click.self="$emit('close')">
            <div class="modal-window">
                <div class="modal-content" style="background-color: tomato;">
                    <div class="box1" style="background-color: green;">
                        <button style="padding: 10px 15px;">사진 업로드</button>
                    </div>
                    <div class="rightBox" style="background-color: slategray;">
                        <div class="box2" style="background-color: blue;">
                            <button>등록</button>
                            <button>취소</button>
                        </div>
                        <div class="box3" style="background-color: palevioletred;">
                            <textarea placeholder="글을 입력하세요..."></textarea>
                        </div>
                        <div class="box4" style="background-color: tan;">
                            <span>장소</span>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </transition>
</template>

<style scoped>
/* CSS 코드로 변환된 스타일 */
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


/* 오버레이 트랜지션 */
.modal-enter-active,
.modal-leave-active {
    transition: opacity 0.4s;
}

/* 오버레이에 포함된 모달 윈도의 트랜지션 */
.modal-enter-active .modal-window,
.modal-leave-active .modal-window {
    transition: opacity 0.4s, transform 0.4s;
}

/* 딜레이가 적용된 모달 윈도가 제거된 후에 오버레이가 사라짐 */
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
}

.box2 {
    height: 10%;
    width: 100%;
    display: flex;
    justify-content: flex-end;
}


.box3 {
    height: 80%;
    width: 100%;
}

.box4 {
    height: 10%;
    width: 100%;
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

textarea {
    border-width: 0;
    resize: none;
    width: 100%;
    height: 100%;
    max-width: 100%;
    background-color: rgb(247, 247, 247);
    padding: 3%;
}

textarea:focus {
    outline: none;
}
</style>