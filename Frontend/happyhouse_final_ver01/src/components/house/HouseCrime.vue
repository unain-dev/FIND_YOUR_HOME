<template>
  <div class="crime-level-container">
    <!-- <p>{{ sidos }} {{ guguns }}</p> -->
    <p v-show="false">시도 구군코드 : {{ gugunName }}</p>
    <div v-if="value != null">
      <p>선택 시군구의 범죄 안전 레벨 : {{ value }}</p>
      <div>
        <b-form-rating
          v-model="value"
          readonly
          variant="info"
          class="mb-2"
        ></b-form-rating>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { crimeLevel } from "@/api/house.js";

export default {
  data() {
    return {
      level: null,
      value: null,
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

              if (response.data.rate <= 0) {
                this.level = null;
                this.value = null;
              } else {
                this.level = response.data.rate;
                let temp = response.data.rate;
                if (temp > 5) {
                  this.value = 5;
                } else if (temp <= 0) {
                  this.value = 0.5;
                }
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

<style>
.crime-level-container {
  margin: 30px;
}
</style>
