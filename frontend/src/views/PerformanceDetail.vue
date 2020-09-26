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
            <h4>| 공연 기간 | {{ performance.startDate }} ~ {{ performance.endDate }}</h4>
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
          <div>
          <h4>| 날짜 선택 |</h4>
          <v-text-field
                v-model="computedDateFormatted"
                hint="공연관람 날짜를 선택해주세요."
                persistent-hint
                readonly
                prepend-icon="📆"
                style="font-size:23px; width:300px;"
          ></v-text-field>
          <v-date-picker
            v-model="date"
            width="350"
            class="mt-4"
            startDate= :min
            :min= min
            :max= max
            locale="ko-KR"
            :first-day-of-week="1"
            color="#FDDAB4"
            no-title
            style="font-size:18px;"
            elevation="15"
            show-current=min
          ></v-date-picker>
          <!-- <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            :return-value.sync="date"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="date"
                prepend-icon="📆"
                readonly
                v-bind="attrs"
                v-on="on"
                style="font-size:23px;"
              ></v-text-field>
            </template>
            <v-date-picker v-model="date" :min=min :max=max color="#FDDAB4" no-title scrollable>

              <v-btn text  @click="menu = false">취소</v-btn>
              <v-btn text color="#FF4155" @click="$refs.menu.save(date)">선택</v-btn>
            </v-date-picker>
          </v-menu> -->
          </div>
          <div>
          <h4>| 시간 선택 |</h4>
          <div v-for="(item,i) in performanceDate" :key="i">
            <div v-if="date==performanceDate[i].date">
              <v-btn @click="selecttime(i)" x-large block tile class="selectBar" color="#FDDAB4" style="width: 180px;"><h4>{{performanceDate[i].time}}</h4></v-btn>
            </div>
          </div>
          </div>
      </v-row>
      <v-spacer></v-spacer>
      <v-layout row style="float:center;">
        <v-btn-toggle v-model="toggle_exclusive" rounded >
          <v-btn x-large block tile class="selectBar" color="#FF4155"><h4>공연 상세정보</h4></v-btn>
          <v-btn x-large block tile class="selectBar" color="#FF4155"><h4>공연장 정보</h4></v-btn>
        </v-btn-toggle>
      </v-layout>
    </div>
    <div>
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import { findById,finddateById,findpriceById } from "@/api/performance.js";
import HNav from "../components/common/HNav";


export default {
  components: {
    HNav,
  },
  data() {
    return {
      date: new Date().toISOString().substr(0, 10),
      // dateFormatted: formatDate(new Date().toISOString().substr(0, 10)),
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
    }
  },
  mounted() {
    var scope = this;
    var pid = this.$route.params.pid;
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
  },
 watch: {
    date (val) {
      this.dateFormatted = this.formatDate(this.date)
    },
  },
  methods: {
    formatDate (date) {
      if (!date) return null

      const [year, month, day] = date.split('-')
      return `${year}년 ${month}월 ${day}일`
    },
    selecttime(pid) {
      this.selectDate=this.date
      this.selectTime=this.performanceDate[pid].time
    }
    // detailPage(page){
    //   if(page==0){
    //     console.log("들어왔니?1")
    //     this.$router.push({
    //       name: 'performanceDetail.Detai',
    //     })
    //   }
    //   else if(page==1){
    //      console.log("들어왔니?2")
    //   }
    // }
  }
}
</script>

<style>
  h4{
    padding: 10px;
  }
  .selectBar{
    font-size:23px;
  }
</style>