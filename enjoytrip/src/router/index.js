import { createRouter, createWebHistory } from "vue-router";

import MainView from "@/views/TheMainView.vue";
import BoardQnAView from "@/views/TheBoardQnAView.vue";
import PlanView from "@/views/ThePlanView.vue";
import WishlistView from "@/views/TheWishlistView.vue";
import AccountView from "@/views/TheAccountView.vue";
import BoardPhotoView from "@/views/TheBoardPhotoView.vue";
import UserView from "@/views/TheUserView.vue";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/user";

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem("accessToken");

  if (userInfo.value != null && token) {
    await getUserInfo(token);
  }
  if (!isValidToken.value || userInfo.value === null) {
    next({ name: "user-login" });
  } else {
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "main",
      component: MainView,
    },
    {
      path: "/user",
      name: "user",
      component: AccountView,
      redirect: { name: "user-login" },
      children: [
        {
          path: "login",
          name: "user-login",
          component: () => import("@/components/user/AccountLogin.vue"),
        },
        {
          path: "join",
          name: "user-join",
          component: () => import("@/components/user/AccountJoin.vue"),
        },
        {
          path: "idsearch",
          name: "user-id-search",
          component: () => import("@/components/user/AccountIdSearch.vue"),
        },
        {
          path: "pwdcheck",
          name: "user-pwd-check",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/AccountPwdCheck.vue"),
        },
        {
          path: "pwdmodify",
          name: "user-pwd-modify",
          component: () => import("@/components/user/AccountPwdModify.vue"),
        },
        {
          path: "pwdsearch",
          name: "user-pwd-search",
          component: () => import("@/components/user/AccountPwdSearch.vue"),
        },
      ],
    },
    {
      path: "/users",
      name: "users",
      component: UserView,
      redirect: { name: "user-mypage" },
      children: [
        {
          path: "mypage",
          name: "user-mypage",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserMypage.vue"),
        },
        {
          path: "myphoto",
          name: "user-myphoto",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/UserMyphoto.vue"),
        },
        {
          path: "plan/list",
          name: "plan-list",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/userPlanList.vue"),
        },
        {
          path: "plan/list/:planId",
          name: "plan-list-detail",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/userPlanDetail.vue"),
        },
        {
          path: "plan/modify/:planId",
          name: "plan-modify",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/user/userPlanModify.vue"),
        },
      ],
    },
    {
      path: "/plan",
      name: "plan",
      component: PlanView,
      redirect: { name: "plan-create" },
      children: [
        {
          path: "",
          name: "plan-create",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/plan/PlanCreate.vue"),
        },
        {
          path: "wishlist",
          name: "plan-wishlist",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/plan/PlanWishlist.vue"),
        },
        {
          path: "detail",
          name: "plan-detail",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/plan/PlanDetail.vue"),
        },
      ],
    },
    {
      path: "/wishlist",
      name: "wishlist",
      beforeEnter: onlyAuthUser,
      component: WishlistView,
    },
    {
      path: "/board/photo",
      name: "board-photo",
      component: BoardPhotoView,
      redirect: { name: "board-photo-list" },
      children: [
        {
          path: "",
          name: "board-photo-list",
          component: () => import("@/components/board/photo/BoardPhotoList.vue"),
        },
      ],
    },
    {
      path: "/board/qna",
      name: "board-qna",
      component: BoardQnAView,
      redirect: { name: "board-qna-list" },
      children: [
        {
          path: "",
          name: "board-qna-list",
          component: () => import("@/components/board/qna/BoardQnAList.vue"),
        },
        {
          path: ":articleno",
          name: "board-qna-detail",
          component: () => import("@/components/board/qna/BoardQnADetail.vue"),
        },
        {
          path: "write",
          name: "board-qna-write",
          beforeEnter: onlyAuthUser,
          component: () => import("@/components/board/qna/BoardQnAWrite.vue"),
        },
      ],
    },
  ],
});

export default router;
