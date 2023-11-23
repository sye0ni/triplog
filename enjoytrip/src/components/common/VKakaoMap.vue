<script setup>
import { ref, watch, onMounted } from "vue";

var map;
const positions = ref([]);
const markers = ref([]);

const props = defineProps({
    attractionList: Array,
    attraction: Object,
});

watch(
    () => props.attraction, // markers.value.push(marker);
    () => {

        positions.value = [];
        let obj = {};
        obj.latlng = new kakao.maps.LatLng(
            props.attraction.latitude,
            props.attraction.longitude
        )
        obj.title = props.attraction.title;
        positions.value.push(obj);
        loadMarkers();

        // 이동할 위도 경도 위치를 생성합니다
        var moveLatLon = new kakao.maps.LatLng(
            props.attraction.latitude,
            props.attraction.longitude
        );

        // 지도 중심을 부드럽게 이동시킵니다
        // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
        map.panTo(moveLatLon);
    },
    { deep: true }
);

watch(
    // attraction 목록이 바뀌면..
    () => props.attractionList,
    () => {
        console.log("지도 변화!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        console.log(props.attractionList.length);
        // console.log(props.attractionList);
        positions.value = [];
        props.attractionList.forEach((attraction) => {
            let obj = {};
            obj.latlng = new kakao.maps.LatLng(
                attraction.latitude,
                attraction.longitude
            );
            obj.title = attraction.title;
            obj.address = attraction.addr1;
            obj.image = attraction.first_image;
            obj.zipcode = attraction.zipcode;
            positions.value.push(obj);
        });

        loadMarkers();
    },
    // 객체나 배열 안의 내용이 바뀔 때는 깊은 감시 해야함!!!!!!!
    { deep: true }
);

onMounted(() => {
    // kakao라는 객체가 있으면 만들어라
    if (window.kakao && window.kakao.maps) {
        initMap();
    } else {
        // 처음 들어오면 여기 걸림
        const script = document.createElement("script");
        // autoload=false 주의!!
        // 스크립트 로드할 때 만들어짐
        script.src = `//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${import.meta.env.VITE_KAKAO_MAP_SERVICE_KEY
            }&libraries=services,clusterer`;
        /* global kakao */
        script.onload = () => kakao.maps.load(() => initMap());
        document.head.appendChild(script);

    }
});

const initMap = () => {
    const container = document.getElementById("map");
    const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
    };
    map = new kakao.maps.Map(container, options);

    // loadMarkers();
};

const loadMarkers = () => {
        console.log("마커 로드!!!");
        // 현재 표시되어있는 marker들이 있다면 map에 등록된 marker를 제거한다.
        deleteMarkers();

        // 마커를 생성합니다
        markers.value = [];
        positions.value.forEach((position) => {
            const marker = new kakao.maps.Marker({
                map: map, // 마커를 표시할 지도
                position: position.latlng, // 마커를 표시할 위치
            });

        markers.value.push(marker);

        // 마커 클릭 시 커스텀 오버레이 표시
        kakao.maps.event.addListener(marker, 'click', function () {
            // 커스텀 오버레이 생성
            const content = document.createElement('div');
            const info = document.createElement('span');
            info.appendChild(document.createTextNode(position.title+" "));
            content.appendChild(info);
            const closeBtn = document.createElement('button');
            closeBtn.appendChild(document.createTextNode('닫기'));

            closeBtn.onclick = function () {
                overlay.setMap(null);
            };
            content.appendChild(closeBtn);

            const overlay = new kakao.maps.CustomOverlay({
                map: map,
                position: position.latlng, // 마커 위치에 오버레이 표시
                content: content,
                yAnchor: 4,
            });

            content.style.backgroundColor = '#fff';
            content.style.padding = '10px';
            content.style.border = '1px solid #ccc';
            content.style.borderRadius = '5px';
            content.style.boxShadow = '3px 3px 5px rgba(0, 0, 0, 0.3)';

            // 닫기 버튼 스타일 설정
            closeBtn.style.border = 'none';
            closeBtn.style.outline = 'none';
            closeBtn.style.backgroundColor = '#c62f2f'; // 버튼 배경색 설정
            closeBtn.style.color = 'white'; // 버튼 텍스트 색상 설정
            closeBtn.style.borderRadius = '5px';
            closeBtn.style.cursor = 'pointer';

            // 오버레이 표시
            overlay.setMap(map);
        });


    });

    // 4. 지도를 이동시켜주기
    // 배열.reduce( (누적값, 현재값, 인덱스, 요소)=>{ return 결과값}, 초기값);
    const bounds = positions.value.reduce(
        (bounds, position) => bounds.extend(position.latlng),
        new kakao.maps.LatLngBounds()
    );

    map.setBounds(bounds);
};


const deleteMarkers = () => {
    console.log("마커삭제할배열크기@!@@@@@@@@@@@@@", markers.value.length);
    if (markers.value.length > 0) {
        markers.value.forEach((marker) => {
            marker.setMap(null);
            console.log("마커삭제");
        });
        markers.value.splice(0, markers.value.length); // markers 배열 비우기
    }
};

</script>

<template>
    <div id="map"></div>
</template>

<style>
#map {
    width: 100%;
    /* height: 700px; */
    height: 85vh;
}

.wrap {
    position: absolute;
    left: 0;
    bottom: 40px;
    width: 288px;
    height: 132px;
    margin-left: -144px;
    text-align: left;
    overflow: hidden;
    font-size: 12px;
    font-family: "Malgun Gothic", dotum, "돋움", sans-serif;
    line-height: 1.5;
}

.wrap * {
    padding: 0;
    margin: 0;
}

.wrap .info {
    width: 286px;
    height: 120px;
    border-radius: 5px;
    border-bottom: 2px solid #ccc;
    border-right: 1px solid #ccc;
    overflow: hidden;
    background: #fff;
}

.wrap .info:nth-child(1) {
    border: 0;
    box-shadow: 0px 1px 2px #888;
}

.info .title {
    padding: 5px 0 0 10px;
    height: 30px;
    background: #eee;
    border-bottom: 1px solid #ddd;
    font-size: 18px;
    font-weight: bold;
}

.info .close {
    position: absolute;
    top: 10px;
    right: 10px;
    color: #888;
    width: 17px;
    height: 17px;
    background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/overlay_close.png");
}

.info .close:hover {
    cursor: pointer;
}

.info .body {
    position: relative;
    overflow: hidden;
}

.info .desc {
    position: relative;
    margin: 13px 0 0 90px;
    height: 75px;
}

.desc .ellipsis {
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.desc .jibun {
    font-size: 11px;
    color: #888;
    margin-top: -2px;
}

.info .img {
    position: absolute;
    top: 6px;
    left: 5px;
    width: 73px;
    height: 71px;
    border: 1px solid #ddd;
    color: #888;
    overflow: hidden;
}

.info:after {
    content: "";
    position: absolute;
    margin-left: -12px;
    left: 50%;
    bottom: 0;
    width: 22px;
    height: 12px;
    background: url("https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/vertex_white.png");
}

.info .link {
    color: #5085bb;
}
</style>
