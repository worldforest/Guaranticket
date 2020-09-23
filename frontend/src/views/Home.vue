<template>
  <div>
    <h-nav></h-nav>
    <!-- main 중앙 광고 -->
    <div id="main-overview" class="container">
      <div class="row">
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
      <h3 style="float:left; width:60%; height:50px;">인기있는 공연 top5</h3>
      <div style="margin-bottom:8px;float:right; width:40%;height:50px; padding-top:20px;text-align: right; ">
        <button @click="topFive(0)" style="float:left; font-size:15px;margin: 0px 15px;">콘서트</button>
        <button @click="topFive(1)" style="float:left; font-size:15px;margin: 0px 15px;">뮤지컬</button>
        <button @click="topFive(2)" style="float:left; font-size:15px;margin: 0px 15px;">스포츠</button>
        </div>
      </div>
      <div class="flex-container" style="max-width:100%;">
        <v-row>
        <figure v-for="(item, i) in poster" :key="i">
          <img :src="item.poster" height="300px" alt="인기 공연"/>
          <figcaption>
            <div class="fig-author">{{ item.title }}</div>
            <div class="fig-author">{{ item.startDate }} ~ {{ item.endDate }}</div>
          </figcaption>
        </figure>
        </v-row>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { performance } from "@/api/performance.js";
import HNav from "../components/common/HNav";
// import StepFlow from '@/components/common/StepFlow';

export default {
  components: {
    HNav,
    // StepFlow
  },
  data() {
    return {
      //mainPosters[0] : {title...} <--콘서트 axios->res this.mainPoster[0] = res.data
      //mainPosters[1] : {title...} <--뮤지컬
      //mainPosters[2] : {title...} <--스포츠
      topPosters: [],
      poster:{
       pid:'',
       title: '',
       poster: '',
       startDate: '',
       endDate: ''
      },
      concert: {
       pid:'',
       title: '',
       poster: '',
       startDate: '',
       endDate: ''
      },
      musical: {
       pid:'',
       title: '',
       poster: '',
       startDate: '',
       endDate: ''
      },
      sports: {
       pid:'',
       title: '',
       poster: '',
       startDate: '',
       endDate: ''
      },
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
      // topPosters: [
      //    {
      //     src: 'https://ticketimage.interpark.com/Play/image/large/19/19016252_p.gif',
      //     title: '폴킴 투어 콘서트, 마음',
      //   },
      //   {
      //     src: 'https://newsimg.sedaily.com/2019/07/10/1VLMYZNEU7_1.jpg',
      //     title: '마마무 대구 콘서트'
      //   },
      //   {
      //     src: 'https://img.hankyung.com/photo/201710/BF.14992624.1.jpg',
      //     title: '청하 팬미팅'
      //   },
      //   {
      //     src: 'https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTUjd4BVK3H_X0r4pCw9hEOyXLmk-TEGAbeZg&usqp=CAU',
      //     title: '에이핑크 콘서트'
      //   },
      //   {
      //     src: 'https://cdnticket.melon.co.kr/resource/image/upload/ticketopen/2019/04/20190419182948c3ebdccf-47ea-4b40-a71b-3bf6b549d43f.jpg',
      //     title: '안녕, 나의 우주 : 정승환 콘서트'
      //   }
      // ]
    }
  },
  methods: {
    topFive(category){
    var that = this;
      if(category==0){
        that.poster=this.concert
        console.log(this.concert)
      }
      else if(category==1){
        that.poste=this.musical
        console.log(this.musical)
      }
      else if(category==2){
        that.poster=this.sports
        console.log(this.sports)
      }
    }
  },
  created() {
    axios
    .get("http://localhost:8080/api/performance")
    .then(res => {
      this.poster=res.data
      var len = this.poster.length;
      var that = this;
      for (let i = 0; i < len; i++) {
        var post = this.poster[i]
        if(this.poster[i].category=='0'){
          // this.concert=this.poster[i]
          that.concert.push(post)
          console.log(i)
          console.log(this.concert)
        }
        else if(this.poster[i].category=='1'){
          that.musical.push(this.poster[i])
          console.log(i)
          console.log(this.musical)
        }
        else if(this.poster[i].category=='2'){
          that.sports.push(this.poster[i])
          console.log(i)
          console.log(this.sports)
        }
      }
    })
    .catch();
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
