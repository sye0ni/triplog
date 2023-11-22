<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";
import { deleteUser, dupCheck, modifyUserInfo } from "@/api/account.js";

const router = useRouter();
const memberStore = useMemberStore();
const { userLogout } = memberStore;

const { userInfo } = storeToRefs(memberStore);

const message = ref("");

const emailRef = ref("");

const emailReadOnly = ref(true);

const emailInput = ref("");

const param = ref({
  userId: "",
  type: "name or email",
  value: "",
});

const nameRef = ref("");
const nameInput = ref("");
const isNameModifying = ref(false);

const nameModify = function () {
  console.log("nameModify ing!!");
  // nameRef.value = userInfo.value.userName;

  // 수정중
  if (isNameModifying.value) {
    console.log("입력 ", nameRef.value);

    console.log(param.value.value + ", " + userInfo.value.userName);
    if (param.value.value == userInfo.value.userName) {
      isNameModifying.value = !isNameModifying.value;
      return;
    }

    console.log("사용 가능한 이름");
    console.log("이름 수정하러 가자");
    param.value.userId = userInfo.value.userId;
    param.value.type = "name";
    param.value.value = nameRef.value;
    modifyUserInfo(
      param.value,
      ({ data }) => {
        console.log("modifyname data:", data);
        userInfo.value.userName = nameRef.value;
        alert("이름 수정 완료");
      },
      (error) => {
        console.log("modifyname fail", error);
        alert("이름 수정 실패");
      }
    );
  }

  isNameModifying.value = !isNameModifying.value;
  if (isNameModifying.value) {
    nameInput.value.focus();
  }
};

const regex = new RegExp("[a-z0-9]+@[a-z]+\.[a-z]{2,3}");

const emailModify = function () {
  console.log("emailModify ing!!");

  if (!emailReadOnly.value) {
    console.log(emailRef.value + " , " + regex.test(emailRef.value));
    if (regex.test(emailRef.value)) {
      console.log("이메일 수정하러 가자!!");
      param.value.type = "email";
      param.value.value = emailRef.value;
      if (param.value.value == userInfo.value.userEmail) {
        emailReadOnly.value = !emailReadOnly.value;
        return;
      }
      dupCheck(
        param.value,
        ({ data }) => {
          console.log("사용 가능한 이메일", data);
          console.log("이메일 변경하러 가자");
          param.value.userId = userInfo.value.userId;
          modifyUserInfo(
            param.value,
            ({ data }) => {
              console.log("modifyemail data:", data);
              userInfo.value.userEmail = emailRef.value;
              alert("이메일 수정 완료");
            },
            (error) => {
              console.log("modifyemail fail", error);
              alert("이메일 수정 실패");
            }
          );
        },
        (error) => {
          console.log("사용 불가능 이메일", error);
          message.value = "사용 불가능한 이메일입니다.";
        }
      );
    } else {
      // message.value = "이메일 형식을 확인해주세요";
      alert("이메일 형식을 확인해주세요.");
      emailInput.value.focus();
      return;
    }
  }

  emailReadOnly.value = !emailReadOnly.value;
  if (!emailReadOnly.value) {
    emailInput.value.focus();
  }
};

const pwdModify = function () {
  console.log("pwdModify function");
  router.push({ name: "user-pwd-check" });
};

const userDelete = function () {
  console.log("회원탈퇴!!");
  if (confirm("회원탈퇴 하시겠습니까?")) {
    console.log("yes", userInfo.value.userId);
    deleteUser(
      userInfo.value.userId,
      ({ data }) => {
        userLogout(userInfo.value.userId);
        console.log("data", data);
        alert("회원탈퇴가 완료되었습니다.");
        router.push("/");
      },
      ({ response }) => {
        console.log("error response", response);
        alert("회원탈퇴 실패");
      }
    );
  } else {
    console.log("no");
  }
};

onMounted(() => {
  emailRef.value = userInfo.value.userEmail;
  nameRef.value = userInfo.value.userName;
  console.log("mounted!!", emailRef.value);
});

const goPlanList = function () {
  console.log("여행 계획 목록 바로가기");
  router.push({ name: "plan-list" });
};

const goPhotoList = function () {
  console.log("내 글 목록 바로가기");
  router.push({ name: "user-myphoto" });
};

const goWhishlist = function () {
  console.log("찜 목록 바로가기");
};
</script>

<template>
  <!-- <div> -->
  <div class="container">
    <div class="title">마이페이지</div>
    <div class="innerContainer">
      <div class="infoBox">
        <div id="myInfoText">내정보</div>
        <div id="nameInfo">
          {{ userInfo.userId }}({{ userInfo.userName }})님
          <button @click="nameModify">수정하기</button>
          <div v-show="isNameModifying" class="nameEdit">
            <input
              id="nameEdit"
              type="text"
              v-model="nameRef"
              ref="nameInput"
            />
            <hr style="width: 200px" />
          </div>
        </div>

        <div class="userInfo">
          <input
            class="infoText"
            type="text"
            v-model="emailRef"
            :readonly="emailReadOnly"
            ref="emailInput"
            id="userEmail"
          />
          <button @click="emailModify">수정하기</button>
        </div>
        <!-- <div class="message">{{ message }}</div> -->
        <div class="userInfo">
          <span class="infoText">비밀번호</span>
          <button @click="pwdModify">수정하기</button>
        </div>
        <div class="delete" @click="userDelete">회원탈퇴</div>
      </div>
      <div>
        <div class="listBox">
          <div @click="goPlanList">여행 계획 목록</div>
        </div>
        <div class="listBox">
          <div @click="goPhotoList">내 글 목록 바로가기</div>
        </div>
        <div class="listBox">
          <div @click="goWhishlist">찜 목록 바로가기</div>
        </div>
      </div>
    </div>
  </div>
  <!-- </div> -->
</template>

<style scoped>
* {
  box-sizing: border-box;
}
a {
  text-decoration: none;
  color: black;
}

input {
  background-color: #00000000;
  border: none;
  outline: none;
  /* font-size: 1.2rem; */
}

.infoText {
  font-size: 1.1rem;
  display: block;
  max-width: 300px;
}

input:hover {
  outline: none;
}
.container {
  display: flex;
  flex-direction: column;
  /* justify-content: center; */
  align-items: center;
  padding: 40px;
  height: 100%;
}

.innerContainer {
  display: flex;
  width: 100%;
  height: 100%;
  justify-content: center;
}

.title {
  font-size: 2rem;
  padding-bottom: 50px;
}

.infoBox {
  background: rgba(217, 217, 217, 0.16);
  border: 2px solid #d5d5d5;
  border-radius: 10px;
  padding: 50px 30px 30px;
  width: 450px;
  /* min-width: 300px; */
  /* width: 60vh; */
  height: 700px;
  display: flex;
  flex-direction: column;
  margin: 0px 50px;
}

.listBox {
  background: rgba(217, 217, 217, 0.16);
  border: 2px solid #d5d5d5;
  border-radius: 10px;

  display: flex;
  align-items: center;

  padding: 10px;
  margin-bottom: 30px;
  width: 600px;
  width: 50vh;
  height: 70px;
  font-size: 1.2rem;

  cursor: pointer;
}

.listBox:hover {
  color: #ca0f0f;
}

#myInfoText {
  text-align: center;
  font-size: 1.7rem;
  font-weight: bold;
}

#nameInfo {
  text-align: center;
  font-size: 1.2rem;
  padding: 10px;
  padding-top: 30px;
  padding-bottom: 30px;
}

.userInfo {
  /* padding: 20px; */
  padding-bottom: 50px;
  display: flex;
  justify-content: space-between;
}

.delete {
  color: #ca0f0f;
  margin-top: auto;
  text-align: center;
  cursor: pointer;
  font-size: 0.8rem;
}

button {
  background-color: #ca0f0f;
  border-radius: 10px;
  /* border: 1px solid black; */
  border: none;
  font-size: 0.7rem;
  height: 33px;
  width: 76px;
  min-width: 76px;
  color: white;
}

button:hover {
  border-color: #ca0f0f;
  cursor: pointer;
}

/* 이름 수정 */
.nameEdit {
  /* background-color: #fff; */
  /* display: flex; */
  /* justify-content: center; */
  /* align-items: center; */
  text-align: center;
  justify-content: center;
}

#nameEdit {
  /* background-color: #d5d5d5; */
  text-align: center;
  font-size: 1rem;
  padding-top: 20px;
}

hr {
  margin-top: 0px;
  margin-bottom: 0px;
}
</style>
