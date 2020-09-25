<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
      <h3>티켓거래 목록</h3>
      <div>
          <table class="table table-striped" style="width: 100%; margin: auto;">
              <thead class="thead-dark">
                  <tr>
                      <th>거래번호</th>
                      <th>분류</th>
                      <th>공연명</th>
                      <th>날짜</th>
                      <th>회차(시간대)</th>
                      <th>좌석 등급</th>
                      <th>가격</th>
                  </tr>
              </thead>
              <tbody>
                  <tr v-for="(deal, index) in deal_list" :key="index" :deal_list="deal_list">
                      <td>{{ index+1 }}</td>
                      <td>{{ deal.category }}</td>
                      <!--티켓거래 상세보기 페이지로 이동-->
                      <td><router-link :to="{ name: 'dealdetail', params: {did: deal.did}}">{{ deal.title }}</router-link></td>
                      <td>{{ deal.date }}</td>
                      <td>{{ deal.time }}</td>
                      <td>{{ deal.grade }}석</td>
                      <td>{{ deal.price }}원</td>
                  </tr>
              </tbody>
          </table>
          <!--거래 중인 티켓이 없을 경우 메세지 출력-->
          <div v-show="msg" id="msg">
            {{ this.msg }}
          </div>
      </div>
    </div>

  </div>
</template>

<script>
import HNav from "@/components/common/HNav";
import axios from "axios";
import { API_BASE_URL } from "@/config";

export default {
  components: {
    HNav
  },
  data: () => {
      return {
          deal_list: [],
          msg: ""
      }
  },
  created() {
      axios
        .get(API_BASE_URL + '/api/deal')
        .then(res => {
            this.deal_list = res.data;
            for (var i in this.deal_list) {
                // 공연 분류 번호에 따라 문자열로 변환
                switch(this.deal_list[i].category) {
                    case "0":
                        this.deal_list[i].category = "콘서트";
                        break;
                    case "1":
                        this.deal_list[i].category = "뮤지컬";
                        break;
                    case "2":
                        this.deal_list[i].category = "스포츠";
                        break;
                }
                // 가격 (원화 기준으로 콤마 표시)
                this.deal_list[i].price = Number(this.deal_list[i].price).toLocaleString();
            }
        })
        .catch(err => {
            if(err.response.status === 404) { // if (list == null || list.isEmpty())인 경우
                this.msg = "거래 중인 티켓이 없습니다.";
            } else {
                console.log("created axios get method error!")
            }
        })
  },
}
</script>

<style scoped>
 h3 {
     margin-top: 100px;
     font-weight: bold;
     margin-bottom: 10px;
 }
 #msg {
     padding: 30px 0;
     background: #f2f2f2;
 }
</style>