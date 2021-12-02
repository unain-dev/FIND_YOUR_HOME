import jwt_decode from "jwt-decode";
import { login } from "@/api/member.js";
import { findById } from "../../api/member";
import { idcheck } from "@/api/member.js";
import { register } from "@/api/member.js";
import { updateInfo } from "@/api/member.js";
import { deleteUser } from "@/api/member.js";
import { getUserArea } from "@/api/member.js";
import { deleteArea } from "@/api/member.js";   // 추가
import { registerArea } from "@/api/member.js";   // 추가

const memberStore = {
  namespaced: true,
  state: {
    isLogin: false,
    isLoginError: false,
    userInfo: null,
    checkid: true,
    registerUser: false,
    updateUser: false,
    areas: [],
  },
  getters: {
    checkUserInfo: function (state) {
      return state.userInfo;
    },
  },
  mutations: {
    SET_AREA_LIST: (state, areas) => {
      areas.forEach((area) => {
        state.areas.push({
          dongCode: area.dongCode,
          sidoName: area.sidoName,
          gugunName: area.gugunName,
          dongName: area.dongName,
        });
      });
      console.log(state.areas);
    },
    CLEAR_AREA_LIST: (state) => {
      state.areas = [];
    },
    SET_IS_LOGIN: (state, isLogin) => {
      state.isLogin = isLogin;
      console.log("setIsLogin : ", state.isLogin);
    },
    SET_IS_LOGIN_ERROR: (state, isLoginError) => {
      state.isLoginError = isLoginError;
    },
    SET_USER_INFO: (state, userInfo) => {
      state.isLogin = true;
      state.userInfo = userInfo;
    },
    CHECK_USER_ID: (state, checkid) => {
      state.checkid = checkid;
    },
    REGISTER_USER: (state, registerUser) => {
      state.registerUser = registerUser;
    },
    UPDATE_USER: (state, updateUser) => {
      state.updateUser = updateUser;
    },
  },
  actions: {
    async getArea({ commit }, userid) {
      const params = {
        user: userid,
      };
      await getUserArea(
        params,
        ({ data }) => {
          commit("SET_AREA_LIST", data);
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userConfirm({ commit }, user) {
      await login(
        user,
        (response) => {
          if (response.data.message === "success") {
            let token = response.data["access-token"];
            commit("SET_IS_LOGIN", true);
            commit("SET_IS_LOGIN_ERROR", false);
            sessionStorage.setItem("access-token", token);
          } else {
            commit("SET_IS_LOGIN", false);
            commit("SET_IS_LOGIN_ERROR", true);
          }
        },
        () => {}
      );
    },
    getUserInfo({ commit }, token) {
      let decode_token = jwt_decode(token);
      findById(
        decode_token.userid,
        (response) => {
          if (response.data.message === "success") {
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
    async userRegister({ commit }, user) {
      await register(
        user,
        (response) => {
          if (response.data === "success") {
            alert("회원가입이 완료되었습니다.");
            commit("REGISTER_USER", true);
          } else {
            console.log("가입 불가!!");
            commit("REGISTER_USER", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userIdCheck({ commit }, userid) {
      await idcheck(
        userid,
        (response) => {
          if (response.data === "success") {
            commit("CHECK_USER_ID", true);
          } else {
            commit("CHECK_USER_ID", false);
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userUpdate({ commit }, user) {
      await updateInfo(
        user,
        (response) => {
          if (response.data === "success") {
            commit("UPDATE_USER", true);
            alert("회원정보가 수정되었습니다.");
          } else {
            commit("UPDATE_USER", false);
            console.log("수정 불가!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    async userDelete({ commit }, userid) {
      await deleteUser(
        userid,
        (response) => {
          if (response.data === "success") {
            alert("회원탈퇴 되셨습니다.");
            commit("SET_IS_LOGIN", false);
            commit("SET_USER_INFO", null);
          } else {
            console.log("탈퇴 불가!!");
          }
        },
        (error) => {
          console.log(error);
        }
      );
    },
    // ---- areaDelete 추가 ----
    async areaDelete({ commit }, userid) {
      await deleteArea(
        userid,
        (response) => {
          if (response.data === "success") {
            console.log(commit)
          } else {
            console.log("지역 삭제 불가");
          }
        },
        (error) => {
          console.log(error);
          console.log("haha");
        }
      );
    },
    // ---- areaRegister 추가 ----
    async areaRegister({ commit }, user) {
      await registerArea(
        user,
        (response) => {
          if (response.data === "success") {
            console.log(commit)
          } else {
            console.log("지역 등록 불가");
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
