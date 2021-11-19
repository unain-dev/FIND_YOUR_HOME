import jwt_decode from "jwt-decode";
import { login } from "@/api/member.js";
import { findById } from "../../api/member";

const memberStore = {
  namespaced: true, // 이걸 true로 해야 다른 컴포넌트에서 namespace로 actions나 다른 애들 호출 가능.
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
  },
  actions: {
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            // response로 온 access-toekn 데이터를 가져옴.
            // 토큰 이름이 access-token인 것임.
            let token = response.data["access-token"];

            // 정상적으로 로그인이 된 경우
            commit("SET_IS_LOGIN", true); // isLogin state를 true로 마킹.
            commit("SET_IS_LOGIN_ERROR", false); // isLoginEror state를 false로 마킹.

            //토큰 정보를 session storage에 저장.
            sessionStorage.setItem("access-token", token);
          } else {
            // 로그인이 거절된 경우.
            commit("SET_IS_LOGIN", false); // isLogin state를 false로 마킹.
            commit("SET_IS_LOGIN_ERROR", true); // isLoginEror state를 true로 마킹.
          }
        },
        () => {}
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token); // 토큰을 복호화.
      findById(
        decode_token.userid, // 디코딩 된 토큰 안에 있는 userid를 빼서 파라미터로 전달.
        (response) => {
          if (response.data.message === "success") {
            // 정상 로그인된 경우, usrInfo state에 유저 정보 저장.
            commit("SET_USER_INFO", response.data.userInfo);
          } else {
            console.log("유저 정보 없음!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
  },
};

export default memberStore;
