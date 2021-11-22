<template>
	<div>
		<div id="map"></div>
		<div class="button-group">
			<!-- <button @click="displayMarker(markerPositions)">marker set</button> -->
			<button @click="displayMarker">marker set</button>
			<button @click="displayMarker([])">marker set 3 (empty)</button>
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
		...mapState(houseStore, ["house", "houses"]),
	},
	data() {
		return {
			map: null,
			center: null, // 지도의 중심좌표
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
				"//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=" + API;
			document.head.appendChild(script);
		}
	},
	updated() {
		this.displayMarker();
	},
	methods: {
		initMap() {
			this.center = new kakao.maps.LatLng(37.566826, 126.9786567);
			const container = document.getElementById("map");
			const options = {
				center: this.center,
				level: 3, // 지도의 확대 레벨
			};
			this.map = new kakao.maps.Map(container, options);
			this.displayMarker();
		},

		// map.js를 가져오겠다
		displayMarkers() {
			const fragment = document.createDocumentFragment();
			const bounds = new kakao.maps.LatLngBounds();
			var listStr = "";

			removeMarker();

			for (let i = 0; i < this.houses.length; i++) {
				const placePosition = new kakao.maps.LatLng(this.houses[i].lat, this.houses[i].lng);
				const marker = addMarker(placePosition, i);
				const itemEl = getListItem(i, this.houses[i]);	// 검색 결과 항목 Element를 생성

				bounds.extend(placePosition);

				(function (marker, title, code, place) {
					kakao.maps.event.addListener(marker, "click", function () {
						displayInfowindow(marker, title, place);
						console.log(title + " " + code);
					});

					kakao.maps.event.addListener(map, "click", function () {
						console.log(customOverlay);
						customOverlay.setMap(null);
					});

					itemEl.onmouseover = function () {
						displayInfowindow(marker, title);
					};

					itemEl.onmouseout = function () {
						customOverlay.setMap(null);
					};
				})(marker, places[i].aptName, places[i].aptCode, places[i]);

				fragment.appendChild(itemEl);
			}
			// 검색된 장소 위치를 기준으로 지도 범위를 재설정
			map.setBounds(bounds);
		},

		// 지도에 표시되고 있는 마커를 모두 제거
		removeMarker() {
			for (let i = 0; i < this.markers.length; i++) {
				this.markers[i].setMap(null);
			}
			this.markers = [];
		},

		// 마커를 생성하고 지도 위에 마커를 표시
		addMarker(position, idx, title) {
			const imgSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png";
			const imgSize = new kakao.maps.Size(36, 37);
			const imgOptions = {
				spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
				spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
				offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
			};
			const markerImg = new kakao.maps.MarkerImage(imgSrc, imgSize, imgOptions);
			const marker = new kakao.maps.Marker({
				position: position,
				image: markerImg,
			});

			marker.setMap(map);
			this.markers.push(marker);

			return marker;
		},



		displayMarker() {
			const markerPositions = [];

			for (let index = 0; index < this.houses.length; index++) {
				if (this.houses[index].lat == null) continue;
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
				iwPosition = this.center, //인포윈도우 표시 위치입니다
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
