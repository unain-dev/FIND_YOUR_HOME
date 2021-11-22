<template>
	<div>
		<div id="map"></div>
		<div class="button-group">
			<!-- <button @click="displayMarker(markerPositions)">marker set</button> -->
			<button @click="displayMarker">marker set</button>
			<button @click="useGeocorder">geocoder test</button>
			<button @click="displayInfoWindow">infowindow</button>
			<button @click="initMap">initMap</button>
		</div>
	</div>
</template>

<script>
import { mapState } from "vuex";

const houseStore = "houseStore";

export default {
	name: "KakaoMap",
	computed: {
		...mapState(houseStore, ["house", "houses", "sidos", "guguns"]),
	},
	data() {
		return {
			map: null,
			// center: null, // 지도의 중심좌표
			// markerPositions: [
				// [37.499590490909185, 127.0263723554437],
				// [37.499427948430814, 127.02794423197847],
				// [37.498553760499505, 127.02882598822454],
				// [37.497625593121384, 127.02935713582038],
				// [37.49629291770947, 127.02587362608637],
				// [37.49754540521486, 127.02546694890695],
				// [37.49646391248451, 127.02675574250912],
			// ],
			positions: [],
			markers: [],
			infowindow: null,
		};
	},
	mounted() {
		const API = process.env.VUE_APP_KAKAO_MAP_API_KEY;
		if (window.kakao && window.kakao.maps) {
			this.initMap();
		} else {
			const script = document.createElement("script");
			/* global kakao */
			script.onload = () => kakao.maps.load(this.initMap);
			script.src =
				"//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" + API + "&libraries=services";
				// "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" + API
			document.head.appendChild(script);
		}
	},
	// updated() { 
	// 	this.displayMarker();
	// }, 
	methods: {
		initMap() {
			// const center = new kakao.maps.LatLng(37.566826, 126.9786567);
			const container = document.getElementById("map");
			const options = {
				center: new kakao.maps.LatLng(37.566826, 126.9786567),
				level: 3, // 지도의 확대 레벨
			};
			this.map = new kakao.maps.Map(container, options);
			// this.displayMarker();
		},

		useGeocorder() {
			const geocoder = new kakao.maps.services.Geocoder();
			this.positions = [];
			for (let i = 0; i < this.houses.length; i++) {
				const address = this.sidos.value + " " + this.guguns.value + " " + this.houses[i].dongName + " " + this.houses[i].jibun + " " + this.houses[i].aptName;
				// console.log(address);
				geocoder.addressSearch(address, (result) => {
					console.log("result: " + result);
					this.positions.push([this.houses[i].aptName, result[0].y, result[0].x]);
				});
			}
			console.log(this.positions);
		},

		getGeocoder(house, geocoder) {
			// console.log("geocoder call");
			const address = house.dongName + " " + house.jibun + " ";// + this.house.aptName;
			//console.log(address);
			geocoder.addressSearch(address, (result) => {
					// console.log("result: " + result);
					// this.positions.push([this.house.aptName, result[0].y, result[0].x]);
					house.lat = result[0].y;
					house.lng = result[0].x;
				});
		},

		displayMarker() {
			const markerPositions = [];
			const geocoder = new kakao.maps.services.Geocoder();

			for (let index = 0; index < this.houses.length; index++) {
				this.getGeocoder(this.houses[index], geocoder);
				if (this.houses[index].lat == null) continue;
				// console.log(this.houses[index].aptName + " " + this.houses[index].lat + ", " + this.houses[index].lng)
				markerPositions.push([this.houses[index].lat, this.houses[index].lng]);
			}
			console.log(markerPositions);

			if (this.markers.length > 0) {
				this.markers.forEach((marker) => marker.setMap(null));
			}

			const positions = markerPositions.map(
				(position) => new kakao.maps.LatLng(...position)
			);

			if (positions.length > 0) {
				this.markers = positions.map(
					(position) =>
						new kakao.maps.Marker({
							map: this.map,
							position,
						})
				);

				const bounds = positions.reduce(
					(bounds, latlng) => bounds.extend(latlng),
					new kakao.maps.LatLngBounds()
				);

				this.map.setBounds(bounds);
			}
		},
		displayInfoWindow() {
			if (this.infowindow && this.infowindow.getMap()) {
				//이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
				this.map.setCenter(this.infowindow.getPosition());
				return;
			}

			var iwContent = '<div style="padding:5px;">아파트이름</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
				iwPosition = new kakao.maps.LatLng(37.566826, 126.9786567), //인포윈도우 표시 위치입니다
				iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

			this.infowindow = new kakao.maps.InfoWindow({
				map: this.map, // 인포윈도우가 표시될 지도
				position: iwPosition,
				content: iwContent,
				removable: iwRemoveable,
			});

			this.map.setCenter(iwPosition);
		},
	},
};
</script>

<style scoped>
#map {
	width: 100%;
	height: 400px;
}

.button-group {
	margin: 10px 0px;
}

button {
	margin: 0 3px;
}
</style>
