<template>
  <div>
    <b-container
      v-if="houses && houses.length != 0"
      class="bv-example-row mt-3"
    >
      <house-list-row
        v-for="(house, index) in houses"
        :key="index"
        :house="house"
      />
    </b-container>
    <div v-else-if="check_isLoading" class="spinner-container">
      <div class="inner-spinner-container">
        <div class="lds-ellipsis">
          <div></div>
          <div></div>
          <div></div>
          <div></div>
        </div>
        <div>Loading...</div>
      </div>
    </div>
    <b-container v-else class="bv-example-row mt-3">
      <b-row>
        <b-col><b-alert show>주택 목록이 없습니다.</b-alert></b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import HouseListRow from "@/components/house/HouseListRow.vue";
// import LoadingBar from "@/components/LoadingBar.vue";
// import axios from "axios";
import { mapState } from "vuex";

// const houseStore = "houseStore";
// const spinnerStore = "spinnerStore";

export default {
  name: "HouseList",
  components: {
    HouseListRow,
  },
  data() {
    return {};
  },
  computed: {
    // ...mapState(houseStore, ["houses"]),
    // ...mapState(spinnerStore, ["isLoading", "refCount"]),

    ...mapState({
      houses: (state) => state.houseStore.houses,
      isLoading: (state) => state.spinnerStore.isLoading,
      refCount: (state) => state.spinnerStore.refCount,
    }),

    check_isLoading() {
      return this.$store.getters.getIsLoading;
    },
  },
  created() {},
};
</script>

<style>
/** https://loading.io/css/ */
.lds-ellipsis {
  display: inline-block;
  position: relative;
  width: 64px;
  height: 64px;
}
.lds-ellipsis div {
  position: absolute;
  top: 27px;
  width: 11px;
  height: 11px;
  border-radius: 50%;
  background: #ddd;
  animation-timing-function: cubic-bezier(0, 1, 1, 0);
}
.lds-ellipsis div:nth-child(1) {
  left: 6px;
  animation: lds-ellipsis1 0.6s infinite;
}
.lds-ellipsis div:nth-child(2) {
  left: 6px;
  animation: lds-ellipsis2 0.6s infinite;
}
.lds-ellipsis div:nth-child(3) {
  left: 26px;
  animation: lds-ellipsis2 0.6s infinite;
}
.lds-ellipsis div:nth-child(4) {
  left: 45px;
  animation: lds-ellipsis3 0.6s infinite;
}
@keyframes lds-ellipsis1 {
  0% {
    transform: scale(0);
  }
  100% {
    transform: scale(1);
  }
}
@keyframes lds-ellipsis3 {
  0% {
    transform: scale(1);
  }
  100% {
    transform: scale(0);
  }
}
@keyframes lds-ellipsis2 {
  0% {
    transform: translate(0, 0);
  }
  100% {
    transform: translate(19px, 0);
  }
}

.spinner-container {
  text-align: center;
}

.inner-spinner-container {
  display: inline-block;
}
</style>
