<template>
  <div class="crime-level-container">
    <!-- <p>{{ sidos }} {{ guguns }}</p> -->
    <p v-show="false">시도 구군코드 : {{ gugunCode }}</p>
    <div v-if="level != null">
      <p class="level" :style="{ color: fontColor }">
        범죄 안전레벨 {{ value }}
      </p>
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
      gugunCode: function (state) {
        if (state.houseStore.gugunCode != null) {
          const params = {
            gugunCode: state.houseStore.gugunCode,
          };

          crimeLevel(
            params,
            (response) => {
              let safe = response.data.ratio / 100;

              if (safe > 0) {
                this.level = safe;
                console.log(this.level);
                if (safe <= 1) {
                  this.value = "😆 아주 좋음 😆";
                  this.fontColor = "rgb(0, 255, 255)";
                } else if (safe <= 2) {
                  this.value = "😊 좋음 😊";
                  this.fontColor = "rgb(127, 255, 0)";
                } else if (safe <= 3) {
                  this.value = "😐 보통 😐";
                  this.fontColor = "yellow";
                } else if (safe <= 4) {
                  this.value = "😥 나쁨 😥";
                  this.fontColor = "orange";
                } else if (safe <= 5 || safe > 5) {
                  this.value = "😰 매우 나쁨 😰";
                  this.fontColor = "red";
                }
              } else {
                this.level = null;
                this.value = null;
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
