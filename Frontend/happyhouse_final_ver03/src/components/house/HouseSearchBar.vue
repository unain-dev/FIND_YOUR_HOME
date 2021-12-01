<template>
  <b-row class="mt-4 mb-4 text-center">
    <b-col class="sm-3" v-if="!checked">
      <b-form-select
        v-model="sidoCode"
        :options="sidos"
        @change="gugunList"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3" v-if="!checked">
      <b-form-select
        v-model="gugunCode"
        :options="guguns"
        @change="dongList"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3" v-if="!checked">
      <b-form-select
        v-model="dongCode"
        :options="dongs"
        @change="searchApt"
      ></b-form-select>
    </b-col>
    <b-col class="sm-3" v-if="checked">
      <b-form-select v-model="dongCode" @change="searchApt">
        <option
          v-for="(area, index) in areas"
          :key="index"
          :value="area.dongCode"
        >
          {{ area.sidoName + " " + area.gugunName + " " + area.dongName }}
        </option></b-form-select
      ></b-col
    >
    <b-col class="sm-3" v-if="userInfo != null">
      <b-form-checkbox v-model="checked" name="check-button" switch size="md">
        관심지역으로 검색
      </b-form-checkbox>
    </b-col>
    <div v-show="false">{{ changeChecked }}</div>
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
const memberStore = "memberStore";

export default {
  name: "HouseSearchBar",
  data() {
    return {
      sidoCode: null,
      gugunCode: null,
      dongCode: null,
      checked: false,
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
    ...mapState(memberStore, ["isLogin", "userInfo", "areas"]),
  },
  watch: {
    changeChecked: function () {
      console.log(this.checked);
      this.toggle();
      return this.checked;
    },
  },
  created() {
    this.CLEAR_SIDO_LIST();
    this.getSido();
    console.log(this.userInfo);
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
    ...mapMutations(memberStore, ["CLEAR_AREA_LIST"]),
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
        if (this.checked) {
          this.gugunCode = this.dongCode.substr(0, 6);
          this.SET_GUGUN_CODE(this.gugunCode);
        }

        await this.getHouseList(this.dongCode);
        this.SET_DONG_CODE(this.dongCode);
      }
    },
    toggle() {
      this.$store.commit("loading", false, { root: true });
    },
  },
};
</script>

<style></style>
