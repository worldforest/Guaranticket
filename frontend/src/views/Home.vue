<template>
  <div>
    <h-nav></h-nav>
    <!-- main 중앙 광고 -->
    <div id="main-overview" class="container">
      <div class="row" style="width:100%;">
        <div class="col-md-12">
          <v-carousel
            cycle
            :show-arrows="false"
            hide-delimiter-background
          >
            <v-carousel-item
                v-for="(item,i) in mainPosters"
                :key="i"
                :src="item.src"
              >
               {{item.title}}
            </v-carousel-item>
          </v-carousel>
        </div>
      </div>
    </div>
    <!-- top 5 공연 포스터 -->
    <div class="container px-5 py-3">
      <div style="width:100%;">
        <h2 style="float:left; width:60%; height:50px;">인기있는 공연 top5</h2>
        <div style="margin-bottom:8px;float:right; width:40%;height:50px; padding-top:20px;text-align: right; ">
          <button @click="topFive(0)" style="float:left; font-size:15px;margin: 0px 15px;">콘서트</button>
          <button @click="topFive(1)" style="float:left; font-size:15px;margin: 0px 15px;">뮤지컬</button>
          <button @click="topFive(2)" style="float:left; font-size:15px;margin: 0px 15px;">스포츠</button>
        </div>
      </div>
      <div id="main-poster" class="flex-container" style="max-width:100%; height:auto;">
        <v-row>
        <v-card v-for="(item, i) in poster" :key="i">
          <img
            :src="item.poster"
            height="auto"
            alt="인기 공연"
            @click="performanceDetail(item)"/>
          <v-card-title style="width:200px">
            <div class="fig-author">{{ item.title }}</div>
          </v-card-title>
          <v-card-text>
            <div class="fig-author">{{ item.startDate }} ~ {{ item.endDate }}</div>
          </v-card-text>
        </v-card>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
import { findAll } from "@/api/performance.js";
import HNav from "../components/common/HNav";

export default {
  components: {
    HNav,
  },
  data() {
    return {
      poster:[],
      concert: [],
      musical: [],
      sports: [],
      mainPosters:[
        {
          src : 'https://www.gangel.kr/files/attach/images/287/543/397/001/ced86dfc54a61132ac249ea4b051ccaa.jpg',
          title : '오마이걸 Fall in Love',
        },
        {
          src : 'https://spnimage.edaily.co.kr/images/Photo/files/NP/S/2019/01/PS19011400024.jpg',
          title : 'BLACK PINK 세번째 단독 콘서트',
        },
        {
          src: 'https://t1.daumcdn.net/cfile/tistory/2705294E565BCB2411',
          title: '전두엽을 마비시키는 뮤지컬 시카고',
        },
        {
          src: 'https://post-phinf.pstatic.net/MjAyMDA3MTVfMTc5/MDAxNTk0NzgyMjU3Njk1.sB2F1KobN9u47olZIDqVP4DhiPxJ7w0hHyO0e7EKnyEg.Qy8YeAC4u05XRlNsZltvIyaRVu-uo3AAddH3ENBFObMg.JPEG/2020_%EB%AE%A4%EC%A7%80%EC%BB%AC_%ED%82%B9%ED%82%A4%EB%B6%80%EC%B8%A0_%EB%94%B0%EB%A1%9C%EB%98%90%EA%B0%99%EC%9D%B4_%EC%BB%A8%EC%85%89%ED%8F%AC%EC%8A%A4%ED%84%B0_%28%EA%B0%80%EB%A1%9C%29.jpg?type=w1200',
          title: '함게하는 순간, 삶이 짜릿해진다. 킹키부츠',
        },
      ],
    }
  },
  methods: {
    topFive(category){
    var that = this;
      if(category==0){
        that.poster=this.concert
      }
      else if(category==1){
        that.poster=this.musical
      }
      else if(category==2){
        that.poster=this.sports
      }
    },
    performanceDetail(performance){
      var performanceId = performance.pid;
      this.$router.push({
        name: 'performanceDetail',
        params: {pid: performanceId}
      });
    }
  },
  created() {
    findAll(
      res => {
      this.poster=res.data;

      this.poster.forEach(post => {
        if(post.category==0){
          this.concert.push(post)
        }
        else if(post.category==1){
          this.musical.push(post)
        }
        else if(post.category==2){
          this.sports.push(post)
        }
      });
      this.poster=this.concert
    }
    );
  }
};
</script>

<style>
.v-card--reveal {
  align-items: center;
  bottom: 0;
  justify-content: center;
  opacity: .5;
  position: absolute;
  width: 100%;
}
</style>
