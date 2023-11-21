<script setup>
import { onMounted, } from 'vue';
import { useRouter } from "vue-router";

const router = useRouter();

const goPlan = function () {
    router.push({ name: "plan-create" });
}

const goPhoto = function () {
    router.push({ name: "board-photo-list" });
}


onMounted(() => {
    const sc1 = document.createElement("script");
    sc1.src = "https://code.jquery.com/jquery-3.7.1.min.js";
    sc1.integrity = "sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=";
    sc1.crossOrigin = "anonymous";
    sc1.defer = true;

    const sc2 = document.createElement("script");
    sc2.src = "src/fullpage/jquery.fullPage.js";
    sc2.defer = true;

    const sc3 = document.createElement("script");
    sc3.src = "src/fullpage/main.js";
    sc3.defer = true;

    sc1.onload = () => {
        document.head.appendChild(sc2);
        sc2.onload = () => {
            document.head.appendChild(sc3);
        };
    };

    document.head.appendChild(sc1);
});


</script>
<template>
    <main id="fullpage">
        <section id="sec1" class="section">
            <div id="frame">
                <div class='video-container'>
                    <video muted autoplay loop class='fullpage-video'>
                        <source src="../assets/main/sea.mp4" type="video/mp4">
                    </video>
                </div>
            </div>
        </section>
        <section id="sec2" class="section">
            <div class='frame2'>
                <span id='frame2Title'>EnjoyTrip 과 함께 계획을 세워보세요!</span>
                <img src='../assets/main/southkorea.png'>
                <!-- <img src='../assets/map.png'> -->
                <button id='frame2Button' @click='goPlan'>계획 세우러 가기</button>
            </div>
        </section>
        <section id="sec3" class="section">
            <div class='frame3'>
                <div class='frame3Title'>
                    여행 사진을 함께 공유해보세요!
                </div>
                <div class='frame3Images'>
                    <img src='../assets/main/mars.jpg' @click='goPhoto'>
                    <img src='../assets/main/night.jpg' @click='goPhoto'>
                    <img src='../assets/main/winter.jpg' @click='goPhoto'>
                </div>
                <div>
                    <button id='frame2Button' @click='goPhoto'>사진 공유하러 가기</button>
                </div>
            </div>
        </section>

    </main>
</template>

<style scoped>

/* section1 */

#frame{
    width: 100vw;
    height: 100vh;
}

.video-container {
    /* 부모 요소인 #frame의 크기에 맞게 비디오 요소 크기 조절 */
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}

.fullpage-video {
    /* 부모 요소인 .video-container의 크기에 맞게 비디오 조절 */
    width: 100%;
    height: 100%;
    object-fit: cover;
}

/* section2 */


.frame2 {
    margin-top: 10%;
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
    align-items: center;
    width: 100%;
    height: 100%;
}

#frame2Title {
    font-size: 2rem;
    font-weight: bold;
}

#frame2Button {
    width: 500px;
    height: 65px;
    background-color: white;
    border-radius: 10px;
    font-size: 1.3rem;
    cursor: pointer;
    border: none;
    border: 2px solid #000;
}

#frame2Button:hover {
    font-weight: bold;
    border: 2px solid #ca0f0f;
}

.frame2 img {
    height: 50%;
    width: 30%;
    border: 2px white solid;
    border-radius: 13px;
    background-color: #f1f1f1;
    padding: 5px;
}

/* frame3 */ 

.frame3{
    display: flex;
    flex-direction: column;
    height: 100%;
    align-items: center;
    justify-content:space-evenly;

} 

.frame3Images{
    display:flex;
    /* justify-content: space-between; */
    gap : 50px;
}

.frame3Images img {
    height: 300px; 
    width: 300px; 
    border-radius: 13px;
    padding: 5px;
    cursor: pointer;
}

.frame3Images img:hover{
    filter: brightness(0.5);
}

.frame3Title{
    font-size: 2rem;
    font-weight: bold;
}


</style>