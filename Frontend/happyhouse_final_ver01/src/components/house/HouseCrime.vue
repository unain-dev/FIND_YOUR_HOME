<template>
  <div class="crime-level-container">
    <!-- <p>{{ sidos }} {{ guguns }}</p> -->
    <p v-show="false">시도 구군코드 : {{ gugunName }}</p>
    <div v-if="level != null">
      <p class="level" :style="{ color: fontColor }">
        범죄 안전레벨 {{ value }}
      </p>
      <!-- <div>
        <b-form-rating
          readonly
          id="rating-inline"
          inline
          :value="value"
        ></b-form-rating>
      </div> -->
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
      fontColor: "black",
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
            gugunCode: state.houseStore.gugunCode,
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
                if (this.level == 1) {
                  this.value = "😆 아주 좋음 😆";
                  this.fontColor = "rgb(0, 255, 255)";
                } else if (this.level == 2) {
                  this.value = "😊 좋음 😊";
                  this.fontColor = "rgb(127, 255, 0)";
                } else if (this.level == 3) {
                  this.value = "😐 보통 😐";
                  this.fontColor = "yellow";
                } else if (this.level == 4) {
                  this.value = "😥 나쁨 😥";
                  this.fontColor = "orange";
                } else if (this.level == 5) {
                  this.value = "😰 매우 나쁨 😰";
                  this.fontColor = "red";
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
  margin-top: 15px;
  margin-bottom: 30px;
}
.level {
  text-align: left;
  font-size: 18px;
  text-shadow: -1px 0 black, 0 1px black, 1px 0 black, 0 -1px black;
}
</style>
