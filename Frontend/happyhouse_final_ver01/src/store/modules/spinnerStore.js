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
      console.log("state.isLoading : ", { isLoading });
      if (isLoading) {
        state.refCount++;
        state.isLoading = true;
      } else if (state.refCount > 0) {
        state.refCount--;
        state.isLoading = state.refCount > 0;
      }
    },
  },
};

export default spinnerStore;
