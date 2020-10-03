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
                  <v-btn @click="selecttime(i)" x-large block color="#FDDAB4" style="width: 180px;"><h4 style="font-size:23px;">{{performanceDate[i].time}}</h4></v-btn>
              </div>
            </div>
            <div style="margin:50px;">
              <v-btn @click="ticket()" x-large block color="#FF4155"><h4 style="font-size:23px;">예매하기</h4></v-btn>
            </div>
            <div style="margin:50px;">
              <v-btn x-large block color="#FF4155" @click="pay"><h4 style="font-size:23px;">결제하기(값은 일단 지정한값이 들어감)</h4></v-btn>
            </div>
          </div>
      </v-row>
      <v-spacer></v-spacer>
      <v-row wrap style="margin-top:50px;">
          <v-btn-toggle v-model="toggle_exclusive" align="center">
            <v-btn v-on:click="selecttab(true)" x-large block color="#FDDAB4"><h4 style="font-size:23px;">🔎 공연 상세정보</h4></v-btn>
            <v-btn v-on:click="selecttab(false)" x-large block color="#FDDAB4"><h4 style="font-size:23px;">🚩 공연장 정보</h4></v-btn>
          </v-btn-toggle>
      </v-row>
      <div>
        <div v-if="this.tabs==true">
          <div>
            <h4>공지사항</h4>
              {{performance.notice}}
          </div>
          <div>
            <img
              :src="performance.detail"
              width="60%"
              height="auto"
              style="margin:50px; float:center;"
              alt="공연 상세정보 포스터"/>
          </div>
        </div>
        <div v-else-if="this.tabs==false">
          <div>
            <div>
              <h4>공연장 정보</h4>
                {{performance.place}}
                <br>
                {{performance.location}}으로 카카오map에서 검색하기
            </div>
            <div>
              <img
                src="https://ticketimage.interpark.com/PlayDictionary/DATA/PlayDic/PlayDicUpload/040002/10/11/0400021011_1941_1111.gif"
                width="60%"
                height="auto"
                style="margin:50px; float:center;"
                alt="공연 상세정보 포스터"/>
            </div>
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
import { findById,finddateById,findpriceById } from "@/api/performance.js";
import HNav from "@/components/common/HNav";

import axios from 'axios'


export default {
  components: {
    HNav,
  },
  data() {
    return {
      date: '',
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
      tabs: true
    }
  },
  // mounted() {
  //   var scope = this;
  //   var pid = this.$route.params.pid;
  // },
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
//  watch: {
//     date (val) {
//       this.dateFormatted = this.formatDate(this.date)
//     },
    
//   },
  methods: {
    pay(){
        const ticket = {
          pid: 2,
          uid : 8,
          seatNumber : 10,
          date : "2020-10-11",
          time : "15:00",
          grade : "R",
          price : "15000"
        };
        axios.post("http://localhost:8080/api/kakaoPay",ticket)
        .then(res =>{
            // let payUrl = res.data.next_redirect_pc_url
            console.log("hj")
            console.log(res)
            window.location.href = res.data;
            // this.$router.push(res.data);
        })
        .catch(e =>{
            alert("에러가 발생했습니다. 다시 시도해주세요")
            this.$router.push('/')
        })
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
    },
    ticket(){
      this.$router.push({
        name: 'selectSeat',
        params: {
          pid: this.pid,
          date: this.selectDate,
          time: this.selectTime
        }
      })
    }
  }
}
</script>

<style>
  h4{
    padding: 10px;
  }
</style>