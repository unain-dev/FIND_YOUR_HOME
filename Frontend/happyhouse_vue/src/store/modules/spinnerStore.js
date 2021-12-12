const spinnerStore = {
  namespace: true,
  state: {
    refCount: 0,
    isLoading: false,
  },
  getters: {
    getIsLoading: (state) => {
      return state.isLoading;
    },
  },
  mutations: {
    loading(state, isLoading) {
      state.isLoading = isLoading;

      // if (isLoading == true) {
      //   state.refCount++;
      //   state.isLoading = true;
      //   // } else if (state.refCount > 0) {
      //   //   state.refCount--;
      //   //   state.isLoading = state.refCount > 0;
      // } else if (isLoading == false) {
      //   state.isLoading = false;
      // }
      console.log("state.isLoading : ", state.isLoading);
    },
  },
};

export default spinnerStore;
