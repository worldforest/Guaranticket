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
          <v-col><h4 v-if="this.row!=''">선택한 좌석 | {{String.fromCharCode(64+row)}}행 {{this.col}}열</h4></v-col>
          </v-row>
        </div>
        <div style="max-width:80%; margin:0 auto;">
          <v-row v-for="i in 6" :key="i" style="max-width:100%; padding: auto;">
            <v-col v-for="j in 6" :key="j" style="padding-left:0px; padding-right:0px;">
              <v-btn color="#FF4155" disabled tile x-large v-if="check[(i-1)*6+j]==true"></v-btn>
              <v-btn tile x-large v-else @click="selectSeat(i,j)">
               {{String.fromCharCode(64+i)}} {{j}}
              </v-btn>
            </v-col>
          </v-row>
        </div>
        <div style="justify-content: center; align-items: center;">
          <v-btn
            id="next-btn"
            color="#FF4155"
            @click="e1 = 2, checkSeat()"
          >
            👉다음단계
          </v-btn>
          <v-btn id="prev-btn" @click="prev">
            👈취소하기
          </v-btn>
        </div>
      </v-stepper-content>
      <v-stepper-content step="2">
        <div>
          <v-row>
            <v-img
            :src="this.performance.poster"
            max-width="30%"
            height="auto"
            style="padding:0px; margin:0px;"
            ></v-img>
            <v-col>
              <h4>{{this.performance.title}}</h4>
              <h4>{{this.performance.place}}</h4>
              <h4>{{this.row}}행 {{this.col}}열</h4>
              {{this.performancePrice[0].grade}}
              {{this.grade}}
              <!-- <h4 v-if="this.grade===this.performancePrice[0].grade">{{this.grade}}석 {{this.perfromance[0].price}}원</h4>
              <h4 v-else>{{this.grade}}석 {{this.perfromance[1].price}}원</h4> -->
              <!-- <h4>{{this.grade}}석 {{this.price}}원</h4> -->
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
          💰 결제하기
        </v-btn>
        <v-btn id="prev-btn" @click="e1 = 2">
          👈뒤로가기
        </v-btn>
      </v-stepper-content>
    </v-stepper-items>
    </v-stepper>
  </div>
</transition>
</template>

<script>
import { findAll, pay } from "@/api/ticket.js";
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
      grade: String,
      price: ''
    }
  },
  created() {
    console.log(this.modalData.pid)
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
      this.$router.push({
        name: 'performanceDetail',
        params: {pid: this.pid}
      });
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
            console.log(response.data)
            if(response.data == "NOTEMPTY"){
              this.$router.go(-1);
            }
            else{
              window.location.href = response.data;
            }
          },
          error => {
              console.log(error);
          }
      )
    },
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
          this.grade="S";
        }else{
          this.grade="R";
        }
        // for (let i = 0; i <= this.performancePrice.length; i++) {
        //   const element = this.performancePrice[i];

        //   if(this.grade.toUpperCase===element.grade.toUpperCase){
        //     this.price=element.price;
        //   }
        // }
      }
    },
  }

}
</script>

<style scoped>
  #next-btn{
    margin-top: 20px;
    font-size: 20px;
    height: 50px;
    float: right;
  }
  #prev-btn{
    margin-top: 20px;
    font-size: 20px;
    height: 50px;
    float: left;
  }
  h4{
    font-size: 23px;
    /* padding-bottom: 10px ; */
  }
</style>