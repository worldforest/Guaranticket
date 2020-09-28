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
        <div>
          좌석선택
          <div>
          <v-row v-for="i in 6" :key="i" style="margin:0px; padding:0px;">
            <v-col v-for="j in 6" :key="j" style="margin:0px; padding:10px;">
              <v-btn v-if="check[(i-1)*6+j]==true" color="#FF4155"></v-btn>
              <v-btn v-else @click="selectSeat(i,j)">{{i}},{{j}}</v-btn>
            </v-col>
          </v-row>
          </div>
        </div>
        <v-btn
          color="#FF4155"
          @click="e1 = 2"
        >
          다음단계
        </v-btn>
        <v-btn text>
          취소하기
        </v-btn>
      </v-stepper-content>
      <v-stepper-content step="2">
        <div>
          예매내역 확인
        </div>
        <v-btn
          color="#FF4155"
          @click="e1 = 3"
        >
          다음단계
        </v-btn>
        <v-btn text>
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
        <v-btn text>
          뒤로가기
        </v-btn>
      </v-stepper-content>
    </v-stepper-items>
    </v-stepper>
  </div>
</template>

<script>
import { findAll, create } from "@/api/ticket.js";
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
      check: {},
      e1: 1,
      row: '',
      col: '',
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
      }
    )
  },
  methods: {
    selectSeat(row, col){
      this.row=row;
      this.col=col;
    }
  }

}
</script>

<style>

</style>