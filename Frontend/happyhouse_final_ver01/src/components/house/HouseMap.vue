<template>
  <div>
    <p>hi!!</p>
    <div id="map"></div>
  </div>
</template>

<script>
import { mapMutations, mapState } from "vuex";
const houseStore = "houseStore";

export default {
  name: "HouseMap",

  computed: {
    ...mapState(houseStore, ["houses", "house"]),
  },
  data() {
    return {
      map: null,
      markerPosition: [],
      markerPositions: [],
      markers: [],
      infowindow: null,
    };
  },
  watch: {
    // detail apt 선택이 변경됐을 경우 마커 하나 업데이트
    house: function () {
      console.log("house change");
      this.displayMarker();
    },
    // apt 리스트가 변경됐을 경우 다중 마커 업데이트
    houses: function () {
      console.log("houses change");
      this.displayMarkers();
    },
  },
  updated() {
    this.displayMarker();
    console.log("markerPositions : ", this.markerPosition);
    console.log("house : ", this.house);

    console.log("updated map");
  },
  mounted() {
    console.log("mounted");
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
  methods: {
    ...mapMutations(houseStore, ["CLEAR_HOUSE"]),
    initMap() {
      const container = document.getElementById("map");
      const options = {
        center: new kakao.maps.LatLng(37.566826, 126.9786567),
        level: 3,
      };
      this.map = new kakao.maps.Map(container, options);
    },

    // 상세보기 - 마커 하나 띄우기
    displayMarker() {
      this.markerPosition = [[this.house.lat, this.house.lng]];
      this.markerPositions = [];
      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = this.markerPosition.map(
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

    // 아파트 리스트 보기 - 다중 마커 띄우기
    displayMarkers() {
      this.markerPosition = [];
      this.markerPositions = [];
      for (let i = 0; i < this.houses.length; i++) {
        if (this.houses[i].lat == null) {
          console.warn(this.houses[i].aptName + " 좌표가 없습니다.");
          continue;
        }
        this.markerPositions.push([this.houses[i].lat, this.houses[i].lng]);
      }

      if (this.markers.length > 0) {
        this.markers.forEach((marker) => marker.setMap(null));
      }

      const positions = this.markerPositions.map(
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
