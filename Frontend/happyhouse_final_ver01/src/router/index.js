import Vue from "vue";
import VueRouter from "vue-router";
import Home from "@/views/Home.vue";
import Instargram from "@/views/Instargram.vue";

import Member from "@/views/Member.vue";
import MemberLogin from "@/components/user/MemberLogin.vue";
import MemberJoin from "@/components/user/MemberJoin.vue";
import MemberMyPage from "@/components/user/MemberMyPage.vue";
import MemberUpdate from "@/components/user/MemberUpdate.vue";

import Board from "@/views/Board.vue";
import BoardList from "@/components/board/BoardList.vue";
import BoardWrite from "@/components/board/BoardWrite.vue";
import BoardView from "@/components/board/BoardView.vue";
import BoardUpdate from "@/components/board/BoardUpdate.vue";

import House from "@/views/House.vue";

import store from "@/store/index.js";

Vue.use(VueRouter);

// 라우터에서 store 사용하는 방법. 라우터에서는 직접적으로 store 사용 못함. 이 방식으로 해야 함.
// https://router.vuejs.org/kr/guide/advanced/navigation-guards.html
const onlyAuthUser = async (to, from, next) => {
  isAuth()
    .then(() => {
      next();
    })
    .catch((error) => {
      alert("로그인이 필요한 페이지입니다..");
      console.log(error);
      router.push({ name: "SignIn" });
    });
};

const isAuth = async () => {
  console.log(store); // store 안에서 어떤 것을 쓸 수 있는지 확인 가능.

  // 콘솔로 확인한 사용가능한 함수들을 토대로 호출함.
  const checkUserInfo = store.getters["memberStore/checkUserInfo"]; // 특수문자 /가 포함되어 잇으므로 []로 감싸서 호출 해야함.
  const getUserInfo = store._actions["memberStore/getUserInfo"];
  let token = sessionStorage.getItem("access-token");

  // 서버에 가서 정상적인 토큰인지 검사해라.
  if (checkUserInfo == null && token) {
    await getUserInfo(token);
  }

  // 유효하지 않은 토큰인 경우.
  if (checkUserInfo === null) {
    //   alert("로그인이 필요한 페이지입니다..");
    //   router.push({ name: "SignIn" });
    // reject("rejected");
    throw new Error("유효하지 않은 토큰");
    // return false;
  } else {
    // 정상 로그인 한 경우.
    console.log("로그인 했다.");
    // next();
    return true;
  }
};

const isYourArticle = async (to, from, next) => {
  isAuth()
    .then((result) => {
      console.log("result : ", result);
      if (result) {
        const checkUserInfo = store.getters["memberStore/checkUserInfo"]; // 특수문자 /가 포함되어 잇으므로 []로 감싸서 호출 해야함.
        // console.log("checkUserInfo : ", checkUserInfo);

        if (checkUserInfo.userid == to.params.author || checkUserInfo.admin) {
          console.log("it's your article");
          next();
        } else {
          console.log("userid : ", checkUserInfo.userid);
          console.log("params.author : ", to.params.author);
          alert("접근 권한이 없습니다.");
          // router.push({ name: "SignIn" });
        }
      }
    })
    .catch((error) => {
      alert("로그인이 필요한 페이지입니다..");
      console.log(error);
      router.push({ name: "SignIn" });
    });
};

// router.beforeEach((to, from, next) => {
//   store.commit("spinnerStore/startSpinner");
//   setTimeout(() => {
//     next();
//   }, 1);
// });

// router.afterEach(() => {
//   store.commit("spinnerStore/endSpinner");
// });

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/instargram",
    name: "Instargram",
    component: Instargram,
  },
  {
    path: "/user",
    name: "Member",
    component: Member,
    children: [
      {
        path: "singin",
        name: "SignIn",
        component: MemberLogin,
      },
      {
        path: "singup",
        name: "SignUp",
        component: MemberJoin,
      },
      {
        path: "mypage",
        name: "MyPage",
        beforeEnter: onlyAuthUser,
        component: MemberMyPage,
      },
      {
        path: "update",
        name: "Update",
        beforeEnter: onlyAuthUser,
        component: MemberUpdate,
      },
    ],
  },
  {
    path: "/board",
    name: "Board",
    component: Board,
    redirect: "/board/list",
    children: [
      {
        path: "list",
        name: "BoardList",
        component: BoardList,
      },
      {
        path: "write",
        name: "BoardWrite",
        beforeEnter: onlyAuthUser, // 라우팅 들어가기 전에 이거 한번 돌려 봐라 = 로그인 햇는지 안했는지 확인해라.
        component: BoardWrite,
      },
      {
        path: "detail/:articleno/:author",
        name: "BoardView",
        beforeEnter: isYourArticle,
        component: BoardView,
      },
      {
        path: "update/:articleno",
        name: "BoardUpdate",
        component: BoardUpdate,
      },
    ],
  },
  {
    path: "/house",
    name: "House",
    component: House,
  },
  {
    path: "*",
    redirect: "/",
  },
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
});

export default router;
