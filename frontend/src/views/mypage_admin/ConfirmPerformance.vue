<template>
  <div>
    <h-nav></h-nav>
    <div class="container" style="text-align: center;">
        <h3>관리자 공연등록 관리(승인) 페이지</h3>
        <div>
          <table class="table table-striped" style="width: 100%; margin: auto;">
              <thead class="thead-dark">
                  <tr>
                      <th>신청번호</th>
                      <th>공연명</th>
                      <th>신청일</th>
                      <th>신청자</th>
                  </tr>
              </thead>
              <tbody>
                  <tr v-for="(item, index) in submission_list" :key="index" :submission_list="submission_list">
                      <td>{{ item.sid }}</td>
                      <td><router-link :to="{ name: 'confirmperformancedetail',
                           params: {pid: item.pid, sid: item.sid}}">{{ performance_list[index].title }}</router-link></td>
                      <td>{{ item.submitDate }}</td>
                      <td>{{ user_list[index].data.name }}</td>
                  </tr>
              </tbody>
          </table>
          <!--승인요청 중인 공연목록이 없을 경우 메세지 출력-->
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
        submission_list: [],
        performance_list: [],
        user_list: [],
        msg: "",
        title: ""
      }
  },
  created() {
      axios
        .get(API_BASE_URL + '/api/performance/submission')
        .then(res => {
          this.submission_list = res.data;
          if(this.submission_list.length == 0) {
            this.msg = "승인을 요청 중인 공연이 없습니다.";
          } else {
            for (var i in this.submission_list) {
              // 신청일 데이터 가공
              this.submission_list[i].submitDate = this.submission_list[i].submitDate.substring(0,10);
              // 공연정보
              axios
                .get(API_BASE_URL + '/api/performance/' + this.submission_list[i].pid)
                .then(res => {
                  this.performance_list.push(res.data);
                })
                .catch(err => {
                  console.log("공연정보 get error!")
                })
              // 신청자 정보
              axios
                .get(API_BASE_URL + '/api/users/' + this.submission_list[i].uid)
                .then(res => {
                  this.user_list.push(res.data);
                })
                .catch(err => {
                  console.log("신청자 정보 get error!")
                })
            }
          }
          console.log(this.performance_list)
        })
        .catch(err => {
          console.log("created axios get method error!")
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