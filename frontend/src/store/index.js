import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    isSigned: false, // 로그인 여부
    user: {
      id: 0, // 사용자 아이디 저장
      walletAddress: null
    },
    allPerform: [],
  },
  mutations: {
    setIsSigned(state, isSigned) {
      state.isSigned = isSigned;
    },
    setUserId(state, id) {
      state.user.id = id;
    },
    setWalletAddress(state, address) {
      state.user.walletAddress = address;
    },
    logout(state) {
      state.isSigned = false;
      state.user.id = 0;
      state.user.walletAddress = null;
    },
    // getAllPerform(state, response){
    //   state.concert = response;
    //   allPerform=response;
    //   console.log(response)
    // }
  },
  actions: {},
  modules: {}
});
