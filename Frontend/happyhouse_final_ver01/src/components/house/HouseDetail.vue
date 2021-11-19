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
					<house-map />
				</b-col>
			</b-row>
		</b-col>

		<b-col>
			<b-row>
				<b-col>
					<b-alert show variant="secondary"
						>일련번호 : {{ house.aptCode }}</b-alert
					>
				</b-col>
			</b-row>
			<b-row>
				<b-col>
					<b-alert show variant="primary"
						>아파트 이름 : {{ house.aptName }}
					</b-alert>
				</b-col>
			</b-row>
			<b-row>
				<b-col>
					<!-- <b-alert show variant="info">법정동 : {{ house.법정동 }} </b-alert> -->
					<b-alert show variant="info">법정동 : {{ house.dongName }} </b-alert>
				</b-col>
			</b-row>
			<b-row>
				<b-col>
					<b-alert show variant="warning">층수 : {{ house.floor }}층</b-alert>
				</b-col>
			</b-row>
			<b-row>
				<b-col>
					<b-alert show variant="danger"
						>거래금액 :
						{{
							(parseInt(house.recentPrice.replace(",", "")) * 10000) | price
						}}원</b-alert
					>
				</b-col>
			</b-row>
			<b-row>
				<b-col>
					<b-alert show variant="primary">위도 : {{ house.lat }}</b-alert>
				</b-col>
				<b-col>
					<b-alert show variant="info">경도 : {{ house.lng }}</b-alert>
				</b-col>
				<b-col>
					<b-alert show variant="warning"
						>도로명주소 : {{ house.roadName }}</b-alert
					>
				</b-col>
				<b-col>
					<b-alert show variant="danger"
						>건축년도 : {{ house.buildYear }}년</b-alert
					>
				</b-col>
			</b-row>
		</b-col>
	</b-container>
</template>

<script>
import { mapState } from "vuex";
import HouseMap from "@/components/house/HouseMap.vue";

const houseStore = "houseStore";

export default {
	name: "HouseDetail",
	components: {
		HouseMap,
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
