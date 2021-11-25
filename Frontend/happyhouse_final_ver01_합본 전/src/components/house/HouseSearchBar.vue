<template>
  <b-row class="mt-4 mb-4 text-center">
    <!-- <b-col class="sm-3">
      <b-form-input
        v-model.trim="dongCode"
        placeholder="동코드 입력...(예 : 11110)"
        @keypress.enter="sendKeyword"
      ></b-form-input>
    </b-col>
    <b-col class="sm-3" align="left">
      <b-button variant="outline-primary" @click="sendKeyword">검색</b-button>
    </b-col> -->
    <b-col class="sm-3">
      <b-form-select
        v-model="sidoCode"
        :options="sidos"
        @change="gugunList"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select
        v-model="gugunCode"
        :options="guguns"
        @change="dongList"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-form-select
        v-model="dongCode"
        :options="dongs"
        @change="searchApt"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3">
      <b-input type="text" value="아파트 검색 준비중" />
    </b-col>
  </b-row>
</template>

<script>
import { mapState, mapActions, mapMutations } from "vuex";

/*
  namespaced: true를 사용했기 때문에 선언해줍니다.
  index.js 에서 modules 객체의 '키' 이름입니다.

  modules: {
    키: 값
    memberStore: memberStore,
    houseStore: houseStore
  }  
*/
const houseStore = "houseStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
    };
  },
  computed: {
    ...mapState(houseStore, [
      "sidos",
      "guguns",
      "dongs",
      "sidoName",
      "gugunName",
    ]),
    // sidos() {
    //   return this.$store.state.sidos;
    // },
  },
  created() {
    // this.$store.dispatch("getSido");
    // this.sidoList();
    this.CLEAR_SIDO_LIST();
    this.getSido();
  },
  methods: {
    ...mapActions(houseStore, [
      "getSido",
      "getGugun",
      "getDong",
      "getHouseList",
    ]),
    ...mapMutations(houseStore, [
      "CLEAR_SIDO_LIST",
      "CLEAR_GUGUN_LIST",
      "CLEAR_DONG_LIST",
      // "SET_SIDO_NAME",
      // "SET_GUGUN_NAME",
      "SET_GUGUN_CODE",
      "CLEAR_GUGUN_CODE",
      "CLEAR_HOUSE_LIST",
      "SET_DONG_CODE",
      "CLEAR_DONG_CODE",
    ]),
    // sidoList() {
    //   this.getSido();
    // },
    gugunList() {
      this.CLEAR_HOUSE_LIST();
      this.CLEAR_GUGUN_LIST();
      this.CLEAR_GUGUN_CODE();
      this.CLEAR_DONG_CODE();
      this.gugunCode = null;
      if (this.sidoCode) {
        this.getGugun(this.sidoCode);
      }
    },
    dongList() {
      this.CLEAR_DONG_LIST();
      this.dongCode = null;

      console.log("this gugunCode", this.gugunCode);

      if (this.gugunCode) {
        this.SET_GUGUN_CODE(this.gugunCode);
        this.getDong(this.gugunCode);
      }
    },

    async searchApt() {
      this.$store.commit("loading", true, { root: true });

      if (this.dongCode) {
        await this.getHouseList(this.dongCode);
        this.SET_DONG_CODE(this.dongCode);
      }
    },
  },
};
</script>

<style></style>
