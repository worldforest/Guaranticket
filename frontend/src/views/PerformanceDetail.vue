<template>
  <v-container text-xs-center>
    <h-nav></h-nav>
    <div id="pDetail-overview">
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
          <v-col>
            <h4 v-for="(price, i) in performancePrice" :key="i">
              {{ price.grade }}석 {{ price.price }}원
            </h4>
          </v-col>
          </v-row>
        </figcaption>
        <v-col>
          <h4>| 날짜 선택 |</h4>
          <v-text-field
                v-model="date"
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
            color="#FDDAB4"
            no-title scrollable
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
        </v-col>
        <v-col style="margin-left:0px;">
          <h4>| 시간 선택 |</h4>
          <div v-for="(item,i) in performanceDate" :key="i">
            <div v-if="date==performanceDate[i].date">
              <v-btn x-large  block tile class="selectBar" color="#FDDAB4" style="width: 180px;"><h4>{{performanceDate[i].time}}</h4></v-btn>
            </div>
          </div>
        </v-col>
        <!-- {{performanceDate[0].time}}
        {{this.date}} -->
      </v-row>
      <v-spacer></v-spacer>
      <v-layout row>
        <v-flex xs12>
        <v-btn-toggle v-model="toggle_exclusive" rounded >
          <v-btn x-large block tile class="selectBar" color="#FF4155"><h4>공연 상세정보</h4></v-btn>
          <v-btn x-large block tile class="selectBar" color="#FF4155"><h4>공연장 정보</h4></v-btn>
        </v-btn-toggle>
        </v-flex>
      </v-layout>
    </div>
    <router-view></router-view>
  </v-container>
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
      min: '',
      max: '',
      menu: false,
      performance: [],
      performanceDate: [],
      performancePrice: [],
      pid: '',
      toggle_exclusive: undefined,
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
        this.min=this.performance.ticketingStartDate;
        this.max=this.performance.ticketingEndDate;
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
  }
}
</script>

<style>
  h4{
    padding: 10px;
  }
  .selectBar{
    width:50%;
    font-size:23px;
  }
</style>