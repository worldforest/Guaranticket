<template>
  <div>
    <h-nav></h-nav>
      <div class="container" style="text-align: center;">
        <h3>예매내역 목록</h3>
        <div>
          <table class="table table-striped" style="width: 100%; margin: auto;">
              <thead class="thead-dark">
                  <tr>
                      <th>예매번호</th>
                      <th>분류</th>
                      <th>공연명</th>
                      <th>날짜</th>
                      <th>회차(시간대)</th>
                      <th>좌석</th>
                      <th>블록에 기록하기</th>
                      <!-- <th>가격</th> -->
                  </tr>
              </thead>
              <tbody>
                  <tr v-for="(item, index) in purchase_list" :key="index" :purchase_list="purchase_list">
                      <td>{{ index+1 }}</td>
                      <td>{{ item.category }}</td>
                      <!--예매내역 상세보기 페이지로 이동-->
                      <td><router-link :to="{ name: 'purchasedetail', params: {tid: item.tid}}">{{ item.title }}</router-link></td>
                      <td>{{ item.date }}</td>
                      <td>{{ item.time }}</td>
                      <td>{{ item.grade }}석 {{ item.seat }}</td>
                      <td v-if="!!item.contractAddress">{{ item.contractAddress }}</td>
                      <td v-else><v-btn @click="deployContract(item)" color="success">기록하기</v-btn></td>
                      <!-- <td>{{ item.price }}원</td> -->
                  </tr>
              </tbody>
          </table>
          <!--예매내역이 없을 경우 메세지 출력-->
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
import { setContratAddress } from "../../api/ticket.js";

import Web3 from "web3";
import { ADMIN_ACCOUNT, ADMIN_ACCOUNT_PRIVATE_KEY, KEY_VALUE_DATA, BLOCKCHAIN_URL } from '../../config';
import { KEY_VALUE_ABI } from '../../config/ABIs.js';

export default {
  components: {
    HNav
  },
  data: () => {
      return {
        purchase_list: [],
        msg: ""
      }
  },
  created() {
    // 넘어온 uid와 pid가 있으면
    if(Object.keys(this.$route.query).length > 0){
      var uid = this.$route.query.uid;
      var tid = this.$route.query.tid;
      this.deployContract(tid, uid);

    }

      axios
        .get(API_BASE_URL + '/api/ticket/uid', { headers : { "jwt-auth-token" : localStorage.getItem("jwt-auth-token")}})
        .then(res => {
          res.data.forEach(ticket => {
            var seat = "";
            if(ticket.seatNumber > 18) {
              ticket.seatNumber -= 18;
            }
            if(ticket.seatNumber % 6 == 0) { // 6의 배수일 때
              seat = (ticket.seatNumber/6) + "행 6열";
            } else {
              seat = parseInt(ticket.seatNumber/6)+1 + "행 " + (ticket.seatNumber%6) + "열";
            }
            ticket["seat"] = seat;
            switch (ticket.category) {
              case "0":
                ticket.category = '콘서트';
                break;
              case "1":
                ticket.category = '뮤지컬';
                break;
              case "2":
                ticket.category = '스포츠';
                break;
            }
            this.purchase_list.push(ticket);
          });
        })
        .catch(err => {
          if(err.response.status === 404) { // if (list == null || list.isEmpty())인 경우
            this.msg = "예매내역이 존재하지 않습니다.";
          }
          console.log("created axios get method error!")
        })
  },
  methods : {
    async deployContract(tid, uid){
      console.log(tid);
      console.log(uid);
      var web3 = new Web3(BLOCKCHAIN_URL);
      var keyvaluestoreContract = new web3.eth.Contract(KEY_VALUE_ABI);
      var keyvaluestore = keyvaluestoreContract.deploy({data:KEY_VALUE_DATA});
      var options = {
        data : keyvaluestore.encodeABI(),
        gas : await keyvaluestore.estimateGas(),
      }
      var signedTransaction = await web3.eth.accounts.signTransaction(options, ADMIN_ACCOUNT_PRIVATE_KEY);
      var hash = await web3.eth.sendSignedTransaction(signedTransaction.rawTransaction);
      keyvaluestoreContract.options.address = hash.contractAddress;
      console.log(hash.contractAddress)
      await setContratAddress(tid, hash.contractAddress,
        response => {
          console.log(response);
          keyvaluestoreContract.methods.setValue(tid+"", uid+"").send(
            {from : ADMIN_ACCOUNT}
          ).then(
            response => {
              console.log(response)
              keyvaluestoreContract.methods.getValue1(0).call(
                {from : ADMIN_ACCOUNT}
              ).then(console.log)
              keyvaluestoreContract.methods.getValue2(0).call(
                {from : ADMIN_ACCOUNT}
              ).then(console.log)
              alert("티켓 등록이 완료되었습니다.");
            }
          )
        },
        error => {
          alert("스마트 컨트랙트 배포에 실패했습니다.");
        }
      )
      
      // ticket["contractAddress"] = hash.contractAddress;




    },
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