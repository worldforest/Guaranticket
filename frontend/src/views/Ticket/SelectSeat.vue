<template>
<transition name="modal" appear>
  <div>
    <!-- <h-nav></h-nav> -->
    <v-stepper v-model="e1">
    <v-stepper-header>
      <v-stepper-step
        :complete="e1 > 1"
        step="1"
        color="#FF4155"
      ><h4>좌석 선택하기</h4>
      </v-stepper-step>
      <!-- <v-divider></v-divider> -->

      <v-stepper-step
        :complete="e1 > 2"
        step="2"
        color="#FF4155"
      ><h4>예매내역 확인하기</h4>
      </v-stepper-step>

      <!-- <v-divider></v-divider> -->

      <v-stepper-step step="3" color="#FF4155">
        <h4>결제하기</h4>
      </v-stepper-step>
    </v-stepper-header>
    <v-stepper-items>
      <v-stepper-content step="1">
        <div style="text-align:center;">
          <v-row>
          <v-col><h4>날짜 | {{this.date}}</h4></v-col>
          <v-col><h4>시간 | {{this.time}}</h4></v-col>
          <v-col><h4 v-if="this.row!=''">선택한 좌석 | {{String.fromCharCode(64+col)}}행 {{this.row}}열</h4></v-col>
          </v-row>
        </div>
        <div style="max-width:80%; margin:0 auto;">
          <v-btn-toggle mandatory>
            <v-row v-for="i in 6" :key="i" style="max-width:100%; padding: auto;">
              <v-col v-for="j in 6" :key="j" style="padding-left:0px; padding-right:0px;">
                <v-btn color="#FF4155" disabled tile x-large v-if="check[(i-1)*6+j]==true"></v-btn>
                <v-btn tile x-large v-else @click="selectSeat(i,j)">
                {{String.fromCharCode(64+j)}} {{i}}
                </v-btn>
              </v-col>
            </v-row>
          </v-btn-toggle>
        </div>
        <div style="justify-content: center; align-items: center;">
          <v-btn
            id="next-btn"
            color="#FF4155"
            tile
            @click="e1 = 2, checkSeat()"
          >
            <h4>👉다음단계</h4>
          </v-btn>
          <v-btn id="prev-btn" @click="prev">
            <h4>👈취소하기</h4>
          </v-btn>
        </div>
      </v-stepper-content>
      <v-stepper-content step="2">
        <div>
          <v-row>
            <v-img
            :src="`https://j3b101.p.ssafy.io/api/file/${this.performance.poster}`"
            max-width="30%"
            height="auto"
            style="padding:0px; margin:0px;"
            ></v-img>
            <v-col>
              <h4>{{this.performance.title}}</h4>
              <h4>{{this.performance.place}}</h4>
              <h4>{{this.row}}행 {{this.col}}열</h4>
              <h4>{{this.grade}}석 {{this.price}}원</h4>
            </v-col>
          </v-row>
        </div>
        <v-btn
          id="next-btn"
          color="#FF4155"
          @click="e1 = 3"
        >
          <h4>👉다음단계</h4>
        </v-btn>
        <v-btn id="prev-btn" @click="e1 = 1">
          <h4>👈뒤로가기</h4>
        </v-btn>
      </v-stepper-content>
      <v-stepper-content step="3">
        <div>
          결제하기
        </div>
        <v-btn
          id="next-btn"
          color="#FF4155"
          @click="pay"
        >
          <h4>💰 결제하기</h4>
        </v-btn>
        <v-btn id="prev-btn" @click="e1 = 2">
          <h4>👈뒤로가기</h4>
        </v-btn>
      </v-stepper-content>
    </v-stepper-items>
    </v-stepper>
  </div>
</transition>
</template>

<script>
import { setContratAddress, findAll, pay } from "@/api/ticket.js";
import { findById, findpriceById } from "@/api/performance.js";
import HNav from "@/components/common/HNav";

import axios from 'axios'


export default {
  components: {
    HNav,
  },
  props: {
    modalData: Object,
  },
  data() {
    return {
      pid: '',
      date: '',
      time: '',
      seatRow: '',
      tickets: [],
      performance: [],
      performancePrice: [],
      check: {},
      e1: 1,
      row: '',
      col: '',
      grade: "",
      price: ''
    }
  },
  created() {
    // var scope = this;
    var pid = this.modalData.pid;
    var date = this.modalData.date;
    var time = this.modalData.time;
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
      },
      err => {
        if(err.status==404){}
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
    prev(){
      this.$emit("test");
    },
    pay(){
        pay(
          this.pid,
          (this.row-1)*6+(this.col-1)+1,
          this.date,
          this.time,
          this.grade,
          this.price,
          response => {
            window.location.href = response.data;
          }
      )
    },
    selectSeat(row, col){
      console.log((row-1)*6+col);
      this.row=row;
      this.col=col;
    },
    checkSeat(){
      if(this.row==''||this.col==''){
        alert("좌석을 선택해주세요!")
        this.e1=1;
      }
      else{
        if((this.row-1)*6+this.col>18){
          this.grade="S";
          if(this.grade==this.performancePrice[0].grade){
            this.price=this.performancePrice[0].price;
          }
          else{
            this.price=this.performancePrice[1].price;
          }
        }else{
          this.grade="R";
          if(this.grade==this.performancePrice[0].grade){
            this.price=this.performancePrice[0].price;
          }
          else{
            this.price=this.performancePrice[1].price;
          }
        }
      }
    },
  }

}
</script>

<style scoped>
  #next-btn{
    margin-top: 2vh;
    font-size: 1.6vw;
    height: 7vmin;
    float: right;
  }
  #prev-btn{
    margin-top: 2vh;
    font-size: 1.6vw;
    height: 7vmin;
    float: left;
  }
  #seat-btn{
    height: 6vmin;
    width: 6vmax;
  }
  h4{
    font-size: 2vw;
    /* padding-bottom: 10px ; */
  }
  .v-btn--active{
    color:#FF4155;
  }
</style>