<template>
  <div id="ticket-overview">
    <h-nav></h-nav>
    <v-stepper v-model="e1">
    <v-stepper-header>
      <v-stepper-step
        :complete="e1 > 1"
        step="1"
        color="#FF4155"
      >좌석 선택하기
      </v-stepper-step>
      <v-divider></v-divider>

      <v-stepper-step
        :complete="e1 > 2"
        step="2"
        color="#FF4155"
      >예매내역 확인하기
      </v-stepper-step>

      <v-divider></v-divider>

      <v-stepper-step step="3" color="#FF4155">
        결제하기
      </v-stepper-step>
    </v-stepper-header>
    <v-stepper-items>
      <v-stepper-content step="1">
        <div style="margin:30px;">
          <h3>날짜 | {{this.date}}</h3>
          <h3>시간 | {{this.time}}</h3>
        </div>
        <div style="margin:50px; max-width:100%;">
          <v-row v-for="i in 6" :key="i" style="max-width:100%;">
            <v-col v-for="j in 6" :key="j" style="padding-left:0px; padding-right:0px;">
              <v-btn color="#FF4155" disabled tile x-large v-if="check[(i-1)*6+j]==true"></v-btn>
              <v-btn tile x-large v-else @click="selectSeat(i,j)">{{i}},{{j}}</v-btn>
            </v-col>
          </v-row>
        </div>
        <div style="justify-content: center; align-items: center;">
          <v-btn
            color="#FF4155"
            @click="e1 = 2, checkSeat()"
          >
            다음단계
          </v-btn>
          <v-btn @click="e1 = 1">
            취소하기
          </v-btn>
        </div>
      </v-stepper-content>
      <v-stepper-content step="2">
        <div>
          <v-row>
            <v-col>
              <v-img
              :src="this.performance.poster"
              max-width="60%"
              height="auto"
              ></v-img>
            </v-col>
            <v-col>
              <h4 style="font-size:23px;">{{this.performance.title}}</h4>
              <h4 style="font-size:23px;">{{this.performance.place}}</h4>
              <h4 style="font-size:23px;">{{this.row}}행 {{this.col}}열</h4>
              <!-- <h4 v-if="((row-1)*6+col)>=18">S석</h4>
              <h4 v-else-if="((row-1)*6+col)<18">R</h4> -->
              <h4>{{this.grade}}석 {{this.price}}원</h4>
            </v-col>
          </v-row>
        </div>
        <v-btn
          color="#FF4155"
          @click="e1 = 3"
        >
          다음단계
        </v-btn>
        <v-btn @click="e1 = 1">
          뒤로가기
        </v-btn>
      </v-stepper-content>
      <v-stepper-content step="3">
        <div>
          결제하기
        </div>
        <v-btn
          color="#FF4155"
          @click="e1 = 1"
        >
          다음단계
        </v-btn>
        <v-btn @click="e1 = 2">
          뒤로가기
        </v-btn>
      </v-stepper-content>
    </v-stepper-items>
    </v-stepper>
  </div>
</template>

<script>
import { findAll, create } from "@/api/ticket.js";
import { findById, findpriceById } from "@/api/performance.js";
import HNav from "@/components/common/HNav";

export default {
  components: {
    HNav,
  },
  data() {
    return {
      pid: '',
      date: '',
      time: '',
      tickets: [],
      performance: [],
      performancePrice: [],
      check: {},
      e1: 1,
      row: '',
      col: '',
      grade: '',
      price: ''
    }
  },
  created() {
    var scope = this;
    var pid = this.$route.params.pid;
    var date = this.$route.params.date;
    var time = this.$route.params.time;
    findAll(
      pid, date, time,
      res => {
        this.tickets = res.data;
        for (const i in this.tickets) {
          if (this.tickets.hasOwnProperty(i)) {
            const element = this.tickets[i];
            this.check[element.seatNumber]=true;
          }
        }
        this.pid=pid;
        this.date=date;
        this.time=time;
      }
    ),
    findById(
      pid,
      res => {
        this.performance = res.data
      }
    ),
    findpriceById(
      pid,
      res => {
        this.performancePrice = res.data
      }
    )
  },
  methods: {
    selectSeat(row, col){
      this.row=row;
      this.col=col;
    },
    checkSeat(){
      if(this.row==0||this.col==0){
        alert("좌석을 선택해주세요!")
        this.e1=1;
      }
      else{
        if((this.row-1)*6+this.col>=18){
          this.grade='S';
        }else{
          this.grade='R';
        }
        for (let i = 0; i <= this.performancePrice.length; i++) {
          const element = this.performancePrice[i];

          if(this.grade.toUpperCase===element.grade.toUpperCase){
            this.price=element.price;
          }
        }
        alert(this.row+"열"+this.col+"행")
      }
    },
  }

}
</script>

<style>

</style>