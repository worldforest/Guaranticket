<template>
  <nav class="navbar navbar-expand fixed-top navbar-dark basic-color">
    <div class="container">
      <img id="nav-icon" src="../../../public/images/guaranticket.svg">
      <router-link id="navbar-brand" class="navbar-brand" to="/"> GuaranTicket</router-link>
      <div
        class="navbar-collapse offcanvas-collapse"
        id="navbarsExampleDefault"
      >
        <ul class="navbar-nav ml-auto">
          <li class="nav-item">
            <router-link class="nav-link" to="/concert"
              >콘서트</router-link
            >
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/musical">뮤지컬</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/sports">스포츠</router-link>
          </li>
          <!--티켓거래 기능 제거하기로 함... bye...-->
          <!-- <li class="nav-item">
            <router-link class="nav-link" to="/deallist">티켓거래</router-link>
          </li> -->
          <li class="nav-item" v-if="!$store.state.isSigned">
            <router-link class="nav-link" to="/login">로그인</router-link>
          </li>
          <!--마이페이지(일반회원): 로그인 시 활성화-->
          <!--추후 user type=0,1,2으로 구분해서 네비바 구성 다르게 띄울 것!!-->
          <!--
            0-일반
            1-기업승인중
            2-기업
            3-관리자
          -->
          <li class="nav-item" v-if="$store.state.isSigned && user.type==0">
            <div class="text-center">
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <li text
                    v-bind="attrs"
                    v-on="on"
                    class="nav-link"
                  >
                  마이페이지
                  </li>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(item, index) in user_items"
                    :key="index"
                    @click="movePages(item.to)"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </li>

          <!--마이페이지(기업회원): 로그인 시 활성화-->
          <li class="nav-item" v-if="$store.state.isSigned && user.type==2">
            <div class="text-center">
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <li text
                    v-bind="attrs"
                    v-on="on"
                    class="nav-link"
                  >
                  기업회원
                  </li>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(item, index) in biz_items"
                    :key="index"
                    @click="movePages(item.to)"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </li>

          <!--마이페이지(관리자): 로그인 시 활성화-->
          <li class="nav-item" v-if="$store.state.isSigned && user.type==3">
            <div class="text-center">
              <v-menu offset-y>
                <template v-slot:activator="{ on, attrs }">
                  <li text
                    v-bind="attrs"
                    v-on="on"
                    class="nav-link"
                  >
                  관리자
                  </li>
                </template>
                <v-list>
                  <v-list-item
                    v-for="(item, index) in admin_items"
                    :key="index"
                    @click="movePages(item.to)"
                  >
                    <v-list-item-title>{{ item.title }}</v-list-item-title>
                  </v-list-item>
                </v-list>
              </v-menu>
            </div>
          </li>
        </ul>
        <!-- <br> -->
        <!-- <v-btn @click="deployContract">스마트컨트랙트 배포</v-btn> -->
        <!-- <v-btn @click="getContract">컨트랙트 값 가져오기</v-btn> -->
      </div>
    </div>
  </nav>
</template>

<script>
import { dispatch, findById } from "@/api/user.js";

import Web3 from "web3";
import { ADMIN_ACCOUNT, ADMIN_ACCOUNT_PRIVATE_KEY, KEY_VALUE_DATA, BLOCKCHAIN_URL } from '../../config';
import { KEY_VALUE_ABI } from '../../config/ABIs.js';

export default {
  data: () => ({
    user : {},
    user_items: [
      { title: '예매내역 확인', to: '/purchaselist' },
      { title: '회원정보 수정', to: '/updateprofile' },
      { title: '비밀번호 변경', to: '/update/password' },
      { title: '로그아웃', to: '/logout' }
    ],
    biz_items: [
      { title: '공연등록', to: '/performance/submission' },
      { title: '회원정보 수정', to: '/updateprofile' },
      { title: '비밀번호 변경', to: '/update/password' },
      { title: '로그아웃', to: '/logout' }
    ],
    admin_items: [
      { title: '공연등록 관리', to: '/confirmperformance' },
      { title: '회원정보 수정', to: '/updateprofile' },
      { title: '비밀번호 변경', to: '/update/password' },
      { title: '로그아웃', to: '/logout' }
    ]
  }),
  created() {
    if(this.$store.state.isSigned){
      dispatch(
        response => {
          var uid = response.data.data;
          findById(uid,
            response => {
              this.user = response.data.data;
              var email = this.user.email;
              this.email1 = email.split('@')[0];
              this.email2 = email.split('@')[1];
            },
            error => {
              // console.log(error)
            })
        },
        error => {
          // console.log(error)
        }
      )
    }
  },
  methods: {
    getContract(){
      // 티켓 구매 트랜잭션의 주소값
      var address = "0x8F7f2E88F2Df00c44ca83adF32661c92CC93655b";
      var web3 = new Web3(BLOCKCHAIN_URL);
      var contract = new web3.eth.Contract(KEY_VALUE_ABI, address);
      contract.methods.getValue1(0).call().then(
        // TO-DO : 티켓 아이디
        // console.log
      );
      
      contract.methods.getValue2(0).call().then(
        // TO-DO : 유저 아이디
        // console.log
      );
      
    },
    async deployContract(){
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
      keyvaluestoreContract.methods.setValue("티켓아이디", "유저아이디").send(
        {from : ADMIN_ACCOUNT}
      ).then(
        response => {
          // console.log(response)
          keyvaluestoreContract.methods.getValue1(0).call(
            {from : ADMIN_ACCOUNT}
          ).then(// console.log)
          keyvaluestoreContract.methods.getValue2(0).call(
            {from : ADMIN_ACCOUNT}
          ).then(// console.log)
        }
      )
    },
    movePages(link){
      this.$router.push(link);
    }
  }
};
</script>

<style>
  #navbar-brand{
    font-family: 'Sansita Swashed', cursive;
    padding-left: 10px;
  }
  #nav-icon {
    height: 40px;
    padding-right: 0.5rem;
    margin: 0px;
    padding: 0px;
  }
</style>
