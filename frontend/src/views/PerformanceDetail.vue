<template>
  <div>
    <h-nav></h-nav>
    <div id="pDetail-overview" style="width:100%;">
      <h1>{{ performance.title }}</h1>
      <v-row style="padding:10px;">
          <img
            :src="performance.poster"
            height="400px"
            alt="공연 포스터"/>
          <figcaption style="margin:10px 10px;">
            <h4>| 공연 장소 | {{ performance.place }}</h4>
            <h4>| 공연 기간 | {{ min }} ~ {{ max }}</h4>
            <h4>| 관람 시간 | {{ performance.running }}분</h4>
            <hr>
            <v-row style="margin:5px 0px;">
            <h4> | 좌석 정보 | </h4>
            <div style="margin:10px;">
              <h4 v-for="(price, i) in performancePrice" :key="i">
                {{ price.grade }}석 {{ price.price }}원
              </h4>
            </div>
            </v-row>
          </figcaption>
      </v-row>
      <v-row style="padding:10px;">
        <div style="margin-left:30px;">
          <h4>| 날짜 선택 |</h4>
          <v-text-field
                v-model="computedDateFormatted"
                hint="공연관람 날짜를 선택해주세요."
                readonly
                prepend-icon="📆"
                style="font-size:23px; width:300px;"
          ></v-text-field>
          <v-date-picker
            v-model="date"
            width="350"
            class="mt-4"
            :min= min
            :max= max
            :first-day-of-week="1"
            color="#FDDAB4"
            no-title
            style="font-size:18px;"
            elevation="15"
          ></v-date-picker>
        </div>
        <div style="margin-left:40px;">
          <h4>| 시간 선택 |</h4>
          <v-text-field
                v-model="computedTimeFormatted"
                hint="공연관람 시간을 선택해주세요."
                readonly
                prepend-icon="⌚"
                style="font-size:23px; width:300px;"
          ></v-text-field>
          <div v-for="(item,i) in performanceDate" :key="i">
            <div v-if="date==performanceDate[i].date">
                <v-btn @click="selecttime(i)" x-large block color="#FDDAB4" style="width: 180px;"><h4>{{performanceDate[i].time}}</h4></v-btn>
            </div>
          </div>
          <div style="margin:50px;">
            <v-btn @click="ticket" x-large block color="#FF4155"><h4>예매하기</h4></v-btn>
          </div>

          <v-dialog v-model="modal" max-width="80%">
            <SelectSeat :key="reloadKey" @test="test" :modalData="modalData"></SelectSeat>
          </v-dialog>
            
        </div>
      </v-row>
      <v-spacer></v-spacer>
      <v-row wrap style="margin-top:50px;">
          <v-btn-toggle v-model="toggle_exclusive" align="center">
            <v-btn v-on:click="selecttab(true)" x-large block color="#FDDAB4"><h4>🔎 공연 상세정보</h4></v-btn>
            <v-btn v-on:click="selecttab(false)" x-large block color="#FDDAB4"><h4>🚩 공연장 정보</h4></v-btn>
          </v-btn-toggle>
      </v-row>
      <div>
        
        
        <div v-show="this.tabs">
          <div>
            <h4>공지사항</h4>
              {{performance.notice}}
          </div>
          <div>
            <img
              :src="`https://j3b101.p.ssafy.io/api/file/${performance.detail}`"
              width="60%"
              height="auto"
              style="margin:50px; float:center;"
              alt="공연 상세정보 포스터"/>
          </div>
        </div>

        <div v-show="!this.tabs">
          <div>
            <h3>공연장 정보</h3>
              <h4>{{performance.location}}</h4>
              <h4>{{performance.place}}</h4>
          </div>
            <br>
            <div id="map" tyle="display:none;">
            </div>
              <div v-show="message">
                {{this.message}}
              </div>
        </div>
      </div>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>
<script>
import SelectSeat from '@/views/Ticket/SelectSeat.vue'
import { findById,finddateById,findpriceById } from "@/api/performance.js";
import HNav from "@/components/common/HNav";

export default {
  components: {
    HNav,
    SelectSeat
  },
  data() {
    return {
      date: '',
      min: '',
      max: '',
      menu: false,
      performance: [],
      performanceDate: [],
      performancePrice: [],
      pid: '',
      toggle_exclusive: undefined,
      selectTime: '',
      selectDate: '',
      tabs: true,
      modal: false,
      modalData: {
        pid: "",
        date: "",
        time: "",
      },
      message: "",
      reloadKey : 0,
    }
  },
  mounted() {
        const script = document.createElement("script");
      /* global kakao */
      script.onload = () => kakao.maps.load(this.initMap);
      // script.setAttribute('src', "https//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d0aeea25c41e9c988d77fbd1738d8d25&libraries=services")
      script.src = "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=d0aeea25c41e9c988d77fbd1738d8d25&libraries=services";
      document.head.appendChild(script);
  },
  created() {
    var scope = this;
    var pid = this.$route.params.pid;
    this.pid=pid;
    findById(
      pid,
      res => {
        this.performance = res.data
        this.min=this.performance.startDate;
        this.max=this.performance.endDate;
        this.date=this.min;
      }
    ),
    finddateById(
      pid,
      res => {
        this.performanceDate = res.data
      }
    ),
    findpriceById(
      pid,
      res => {
        this.performancePrice = res.data
      }
    )
  },
  computed: {
    computedDateFormatted() {
      return this.formatDate(this.date)
    },
    computedTimeFormatted() {
      return this.formatTime(this.selectTime)
    },
  },
  methods: {
    test(){
      this.modal = false;
      this.modalData.pid="";
      this.modalData.time="";
      this.modalData.date="";
    },
    initMap() {
      var container = document.getElementById("map"); //지도를 담을 영역의 DOM 레퍼런스
      var options = {
        //지도를 생성할 때 필요한 기본 옵션
        center: new kakao.maps.LatLng(33.450701, 126.570667), //지도의 중심좌표.
        level: 3 //지도의 레벨(확대, 축소 정도)
      };
      
      var map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
      // this.searchMap();
       // 주소-좌표 변환 객체를 생성합니다
      var geocoder = new kakao.maps.services.Geocoder();
      // 주소로 좌표를 검색합니다
      geocoder.addressSearch(this.performance.location, (result, status) => {
        // 정상적으로 검색이 완료됐으면 
        if (status === kakao.maps.services.Status.OK) {
          var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            // 결과값으로 받은 위치를 마커로 표시합니다
            var marker = new kakao.maps.Marker({
                map: map,
                position: coords
            });
            
            container.style.width = '500px';
            container.style.height = '400px';
            container.style.margin='50px'; 
            map.relayout();
            // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
            map.setCenter(coords);
            
        } 
        else{
          this.message="주소를 검색할 수 없습니다."
        }
      });
    },
    formatDate (date) {
      if (!date) return null

      const [year, month, day] = date.split('-')
      return `${year}년 ${month}월 ${day}일`
    },
    formatTime (time) {
      if (!time) return null

      const [hours, minutes] = time.split(':')
      return `${hours}시 ${minutes}분`
    },
    selecttime(pid) {
      this.selectDate=this.date
      this.selectTime=this.performanceDate[pid].time
    },
    selecttab(select) {
      this.tabs=select
      if(select == false){
        this.initMap();
      }  
    },
    ticket(){
      if(this.selectTime==''){
        alert("시간을 선택해주세요.")
      }
      else{
        this.modal=true;
        this.modalData.pid=this.pid;
        this.modalData.date=this.selectDate;
        this.modalData.time=this.selectTime;
        this.reloadKey++;

      }
    }
  }
}
</script>

<style scoped>
  h4{
    padding: 10px;
    font-size:23px;
  }
</style>