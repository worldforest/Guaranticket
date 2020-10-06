<template>
  <div class="container" style="text-align: center;">
    <h-nav></h-nav>
    <div class="row">
      <div id="login-form" class="col-md-6 mx-auto bg-white">
        <h3 style="font-weight: bold;">로그인</h3>
        <div class="mt-4">
          <!--로그인 정보(이메일, 비밀번호) 입력-->
          <div class="form-group">
            <label for="email">email</label>
            <input
              type="text"
              class="form-control"
              id="email"
              v-model="user.email"
              placeholder="이메일"
            />
          </div>
          <div class="form-group">
            <label for="password">비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="password"
              v-model="user.password"
              placeholder="비밀번호"
            />
          </div>
          <!--로그인 버튼-->
          <button type="submit" class="btn btn-primary" @click="login">
            로그인
          </button>
          <br>
          <!--비밀번호 찾기, 회원가입 페이지로 이동-->
          <div id="links">
            <router-link
              :to="{ name: 'findpw' }"
              style="color: #FF4155;"
            >
              비밀번호를 잊어버리셨나요?
            </router-link>
            <br>
            <h6 style="margin-top: 40px; color: gray;">아직 개런티켓의 회원이 아니신가요?🤔</h6>
            <router-link
              :to="{ name: 'signup' }"
              style="color: #FF4155;"
            >
              <strong>회원가입</strong>
            </router-link>
          </div>
          <v-divider></v-divider>
          <v-subheader>테스트용 로그인</v-subheader>
          <v-btn @click="testLogin(users[0])">일반</v-btn>
          <v-btn @click="testLogin(users[1])">기업</v-btn>
          <v-btn @click="testLogin(users[2])">관리자</v-btn>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { findByEmail, findById, login } from "@/api/user.js";
import { findByUserId as findWallet } from "@/api/wallet.js";
import HNav from "@/components/common/HNav";

export default {
  components: {
    HNav
  },
  data() {
    return {
      users : [
        // 일반
        {
          email : "ming___jee@naver.com",
          password : "1234zxcv",
        },
        // 기업
        {
          email : "test@test.com",
          password : "qqqq1111",
        },
        // 관리자
        {
          email: "admin@admin.com",
          password: "qqqq1111"
        }
      ],
      user: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    testLogin(user){
      const storage = window.localStorage;
      storage.setItem("jwt-auth-token", "");
      login(
        user.email,
        user.password,
        response => {
          if(response.data.status){
            this.$store.commit("setIsSigned", true);
            storage.setItem("jwt-auth-token", response.data.data);
            this.$router.push("/");
            this.$router.go(this.$router.currentRoute);
          }
          else{
            alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
          }
        },
        error => {
          alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
        }
      );
    },
    login() {
      const scope = this;
      const storage = window.localStorage;
      storage.setItem("jwt-auth-token", "");
      login(
        this.user.email,
        this.user.password,
        response => {
          if(response.data.status){
            scope.$store.commit("setIsSigned", true);
            storage.setItem("jwt-auth-token", response.data.data);
            this.$router.push("/");
            this.$router.go(this.$router.currentRoute);
          }
          else{
            alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
          }
            // console.log(this.user.email)

          // findByEmail(this.user.email, 
          //   response => {
          //     console.log(response);
          //   },
          //   error => {

          //   }
          // );


          // findWallet(
          //   response.data.id,
          //   function(response) {
          //     if (response.status == 200) {
          //       scope.$store.commit("setWalletAddress", response.data.address);
          //     } else {
          //       alert("Unexpected status code: " + response.status);
          //     }
          //   },
          //   function(err) {
          //     if (err.response != 404) {
          //       console.error(err);
          //       //alert("지갑 정보를 찾지 못했습니다.");
          //     }
          //   }
          // );

        },
        error => {
          // console.error(error);
          alert("유저 이메일 혹은 비밀번호가 일치하지 않습니다.");
        }
      );
    }
  }
};
</script>

<style scoped>
  label {
    float: left
  }
  button {
    color: white;
    background: #FF4155;
    border: #FF4155
  }
  #links {
    margin-top: 15px;
  }
  img {
    width: 15%
  }
</style>
