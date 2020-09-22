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
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { login } from "../api/user.js";
import { findByUserId as findWallet } from "../api/wallet.js";
import HNav from "@/components/common/HNav";

export default {
  components: {
    HNav
  },
  data() {
    return {
      user: {
        email: "",
        password: ""
      }
    };
  },
  methods: {
    login() {
      const scope = this;

      login(
        this.user.email,
        this.user.password,
        function(response) {
          scope.$store.commit("setIsSigned", true);
          scope.$store.commit("setUserId", response.data.id);

          findWallet(
            response.data.id,
            function(response) {
              if (response.status == 200) {
                scope.$store.commit("setWalletAddress", response.data.address);
              } else {
                alert("Unexpected status code: " + response.status);
              }
            },
            function(err) {
              if (err.response != 404) {
                console.error(err);
                //alert("지갑 정보를 찾지 못했습니다.");
              }
            }
          );

          scope.$router.push("/");
        },
        function(error) {
          console.error(error);
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
