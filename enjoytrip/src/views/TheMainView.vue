<script setup>
import { onMounted } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();

const goPlan = function () {
  router.push({ name: "plan-create" });
};

const goPhoto = function () {
  router.push({ name: "board-photo-list" });
};

const goJoin = function () {
  router.push({ name: "user-join" });
};
</script>
<template>
  <main id="fullpage">
    <section id="sec1" class="section">
      <div class="main1Container">
        <img src="@/assets/main/TripLogBig2.png" alt="" style="width: 300px" />
        <div class="main1Text">
          <div class="main1TextClass">사진 한 장이 여행의 기억을 간직합니다.</div>
          <br />
          <div class="main1TextClass">함께 공유하며 여행의 아름다움을 느껴보세요.</div>
        </div>
        <button class="joinBtn" @click="goJoin">회원가입</button>
      </div>

      <div id="frame">
        <div class="video-container">
          <video muted autoplay loop class="fullpage-video">
            <source src="../assets/main/sea.mp4" type="video/mp4" />
          </video>
        </div>
      </div>
    </section>
    <!-- <section id="sec2" class="section">
      <div id="frame2">
        <span id="frame2Title">EnjoyTrip 과 함께 계획을 세워보세요!</span>
        <img src="../assets/main/southkorea.png" />
        <button id="frame2Button" @click="goPlan">계획 세우러 가기</button>
      </div>
    </section> -->
    <section id="sec3" class="section">
      <div id="frame3">
        <div class="frame3Title">여행 사진을 함께 공유해보세요!</div>
        <div class="frame3Images">
          <img src="../assets/main/mars.jpg" @click="goPhoto" />
          <img src="../assets/main/night.jpg" @click="goPhoto" />
          <img src="../assets/main/winter.jpg" @click="goPhoto" />
        </div>
        <div>
          <button id="frame2Button" @click="goPhoto">사진 공유하러 가기</button>
        </div>
      </div>
    </section>
  </main>
</template>

<style scoped>
/* section1 */

#frame {
  width: 100vw;
  height: 100vh;
}

#sec1 {
  display: flex;
  justify-content: center;
  align-items: center;
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

.main1Container {
  position: absolute;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 60vh;
  /* border: 1px solid black; */
  padding: 50px;
  border-radius: 10px;
  background-color: #f1f1f1af;
}

.main1Text {
  text-align: center;
  padding: 10px;
}

.joinBtn {
  border: none;
  font-size: 1.1rem;
  padding: 10px;
  border-radius: 5px;
  width: 100px;
  margin-top: 20px;
  background-color: #a30c0c;
  color: white;
  cursor: pointer;
  z-index: 1000;
}
/* section2 */

#frame2 {
  /* margin-top: 10%; */
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
  width: 100vw;
  height: 100vh;
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
  background-color: #ca0f0f;
  /* border: 2px solid #000; */
  /* border: 2px solid #ca0f0f; */
  color: white;
}

#frame2Button:hover {
  /* font-weight: bold; */
  /* border: 2px solid #ca0f0f; */
  border: none;
  background-color: #a30c0c;
  color: white;
}

#frame2 img {
  height: 50%;
  width: 30%;
  border: 2px white solid;
  border-radius: 13px;
  background-color: #f1f1f1;
  padding: 5px;
}

/* frame3 */

#frame3 {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-evenly;
  width: 100vw;
  height: 100vh;
}

.frame3Images {
  display: flex;
  /* justify-content: space-between; */
  gap: 50px;
}

.frame3Images img {
  height: 300px;
  width: 300px;
  border-radius: 13px;
  padding: 5px;
  cursor: pointer;
}

.frame3Images img:hover {
  filter: brightness(0.5);
}

.frame3Title {
  font-size: 2rem;
  font-weight: bold;
}

#sec3 {
  background-color: #f3f3f3d2;
}
</style>
