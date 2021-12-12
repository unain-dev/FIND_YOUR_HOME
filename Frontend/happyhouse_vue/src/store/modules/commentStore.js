import { getComments } from "@/api/comment";

const boardStore = {
  namespaced: true,
  state: {
    comments: [],
  },
  getters: {},
  mutations: {
    SET_COMMENT(state, payload) {
      state.comments = payload;
    },
  },
  actions: {
    actionComments({ commit }, articleno) {
      getComments(
        articleno,
        (response) => {
          commit("SET_COMMENT", response.data);
        },
        (error) => {
          console.log("댓글 불러오는 중 에러 : ", error);
        }
      );
    },
  },
};

export default boardStore;
