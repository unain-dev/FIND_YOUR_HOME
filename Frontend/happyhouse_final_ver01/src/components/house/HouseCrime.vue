<template>
  <div>
    <!-- <p>{{ sidos }} {{ guguns }}</p> -->
    <p>시도 구군코드 : {{ sidoName }} {{ gugunName }}</p>
    <div>선택 시군구의 범죄 노출 레벨 : {{ level }}</div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { crimeLevel } from "@/api/house.js";

export default {
  data() {
    return {
      level: null,
    };
  },
  computed: {
    ...mapState({
      sidoName: (state) => {
        console.log(state.houseStore.sidoName);

        return state.houseStore.sidoName;
      },
      gugunName: (state) => {
        const params = {
          sidoName: state.houseStore.sidoName,
          gugunName: state.houseStore.gugunName,
        };
        crimeLevel(
          params,
          ({ data }) => {
            this.level = data.rate;
          },
          (error) => {
            console.log(error);
          }
        );

        return state.houseStore.gugunName;
      },
    }),
  },
};
</script>

<style></style>
