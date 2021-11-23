<template>
  <div>
    <!-- <p>{{ sidos }} {{ guguns }}</p> -->
    <p v-show="false">시도 구군코드 : {{ gugunName }}</p>
    <div v-if="level != null">선택 시군구의 범죄 노출 레벨 : {{ level }}</div>
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
      //   sidoName: (state) => {
      //     console.log(state.houseStore.sidoName);

      //     return state.houseStore.sidoName;
      //   },
      gugunName: function (state) {
        if (state.houseStore.gugunName != null) {
          const params = {
            sidoName: state.houseStore.sidoName,
            gugunName: state.houseStore.gugunName,
          };

          crimeLevel(
            params,
            (response) => {
              // console.log(response.data.rate);

              if (response.data.rate < 0) {
                this.level = null;
              } else {
                this.level = response.data.rate;
              }
            },
            (error) => {
              console.log(error);
            }
          );
        }
      },
    }),
  },
};
</script>

<style></style>
