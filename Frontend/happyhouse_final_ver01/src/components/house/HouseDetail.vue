<template>
  <b-container v-if="house" class="bv-example-row">
    <b-col>
      <b-row>
        <b-col
          ><h3>{{ house.aptName }}</h3></b-col
        >
      </b-row>
      <b-row class="mb-2 mt-1">
        <b-col>
          <!-- <house-map /> -->
          <house-detail-map :address="house" />
        </b-col>
      </b-row>
    </b-col>

    <b-col>
      <b-card>
        <b-list-group flush>
          <b-list-group-item>일련번호 : {{ house.aptCode }}</b-list-group-item>
          <b-list-group-item
            >아파트 이름 : {{ house.aptName }}</b-list-group-item
          >
          <b-list-group-item>법정동 : {{ house.dongName }}</b-list-group-item>
          <b-list-group-item>층수 : {{ house.floor }}층</b-list-group-item>
          <b-list-group-item
            >거래금액 :
            {{
              (parseInt(house.recentPrice.replace(",", "")) * 10000) | price
            }}원</b-list-group-item
          >
          <b-list-group-item
            >도로명주소 : {{ house.roadName }}</b-list-group-item
          >
          <b-list-group-item
            >건축년도 : {{ house.buildYear }}년</b-list-group-item
          >
          <b-list-group-item>위도 : {{ house.lat }}</b-list-group-item>
          <b-list-group-item>경도 : {{ house.lng }}</b-list-group-item>
        </b-list-group>
      </b-card>
    </b-col>
  </b-container>
</template>

<script>
import { mapState } from "vuex";
// import HouseMap from "@/components/house/HouseMap.vue";
import HouseDetailMap from "@/components/house/HouseDetailMap.vue";

const houseStore = "houseStore";

export default {
  name: "HouseDetail",
  components: {
    // HouseMap,
    HouseDetailMap,
  },
  computed: {
    ...mapState(houseStore, ["house"]),
    // house() {
    //   return this.$store.state.house;
    // },
  },
  filters: {
    price(value) {
      if (!value) return value;
      return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
  },
};
</script>

<style></style>
