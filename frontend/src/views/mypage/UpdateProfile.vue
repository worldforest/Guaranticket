<template>
  <div>
    <h-nav></h-nav>
      <div class="container" style="width:600px; margin-top:8rem; text-align: center;">
        <h3>회원정보 수정 페이지</h3>
        <v-form ref="form" v-model="validations.form">
            <v-row>
                <v-col cols="3">
                    <v-subheader>이메일</v-subheader>
                </v-col>
                <v-col cols="3">
                    <v-text-field
                        v-model="email1"
                        :rules="[() => !!email1 || '이메일을 입력해주세요.']"
                        placeholder="이메일을 입력하세요."
                        required outlined disabled
                    ></v-text-field>
                </v-col>
                <v-col cols="6">
                    <v-text-field
                        prepend-icon="mdi-at"
                        ref="email"
                        :rules="[() => !!email2 || '이메일을 입력해주세요.']"
                        v-model="email2"
                        placeholder="선택해주세요."
                        required outlined disabled
                    >
                    </v-text-field>
                </v-col>
            </v-row>
            <!-- <v-text-field
                v-model="user.password"
                :append-icon="pwdShow ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[rules.password.required, rules.password.min]"
                :type="pwdShow ? 'text' : 'password'"
                @click:append="pwdShow = !pwdShow"
                placeholder="비밀번호를 입력해주세요."
                outlined
            ></v-text-field> -->
            <v-row>
              <v-col cols="3">
                  <v-subheader>이름</v-subheader>
              </v-col>
              <v-col cols="9">
                <v-text-field
                    ref="name"
                    v-model="user.name"
                    :rules="[() => !!user.name || '이름을 입력해주세요.']"
                    placeholder="이름을 입력해주세요."
                    required outlined
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="3">
                  <v-subheader>휴대폰</v-subheader>
              </v-col>
              <v-col cols="9">
                <v-text-field
                    v-model="user.phone"
                    :rules="[() => !!user.phone || '휴대폰 번호를 입력해주세요.']"
                    placeholder="휴대폰 번호를 입력해주세요."
                    required
                    outlined
                >
                </v-text-field>
              </v-col>
            </v-row>
            
            <v-row>
              <v-col cols="3">
                <v-subheader>성별</v-subheader>
              </v-col>
              <v-col cols="4.5">
                  <v-btn class="gender_btn" ref="male" @click="user.gender='남'" :color="user.gender=='남'?'#FF4155':'grey'" x-large outlined width="100%">
                      남성
                  </v-btn>
              </v-col>
              <v-col cols="4.5">
                  <v-btn class="gender_btn" ref="female" @click="user.gender='여'" :color="user.gender=='여'?'#FF4155':'grey'" x-large outlined width="100%">
                      여성
                  </v-btn>
              </v-col>
            </v-row>

            <v-row>
              <v-col cols="3">
                <v-subheader>
                  생년월일
                </v-subheader>
              </v-col>
              <v-col cols="9">
                <v-menu
                  ref="birthMenu"
                  v-model="birthMenu"
                  :close-on-content-click="false"
                  transition="scale-transition"
                  offset-y
                  min-width="290px"
                >
                    <template v-slot:activator="{ on, attrs }">
                      <v-text-field
                        ref="birth"
                        v-model="user.birth"
                        :rules="[() => !!user.birth || '생년월일을 입력해주세요.']"
                        required
                        placeholder="생년월일을 입력해주세요."
                        outlined
                        readonly
                        v-bind="attrs"
                        v-on="on"
                      ></v-text-field>
                    </template>
                    <v-date-picker
                      ref="picker"
                      v-model="user.birth"
                      :max="new Date().toISOString().substr(0, 10)"
                      min="1950-01-01"
                      @change="$refs.birthMenu.save(user.birth)"
                    ></v-date-picker>
                </v-menu>
              </v-col>
            </v-row>

            <v-row v-if="user.type==3">
              <v-col cols="3">
                <v-subheader>
                  사업자번호
                </v-subheader>
              </v-col>
              <v-col cols="9">
                <v-text-field
                  v-model="user.businessNumber"
                  :rules="[() => !!user.businessNumber || '사업자 번호를 입력해주세요.']"
                  placeholder="사업자 번호를 입력해주세요."
                  required
                  outlined
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row v-if="user.type==3">
              <v-col cols="3">
                <v-subheader>
                  상호명
                </v-subheader>
              </v-col>
              <v-col cols="9">
                <v-text-field
                  v-model="user.companyName"
                  :rules="[() => !!user.companyName || '상호를 입력해주세요.']"
                  placeholder="상호를 입력해주세요."
                  required
                  outlined
                ></v-text-field>
              </v-col>
            </v-row>

            <v-row v-if="user.type==3">
              <v-col cols="3">
                <v-subheader>
                  사업자대표
                </v-subheader>
              </v-col>
              <v-col cols="9">
                <v-text-field
                    v-model="user.representativeName"
                    :rules="[() => !!user.representativeName || '대표자명을 입력해주세요.']"
                    placeholder="대표자명을 입력해주세요."
                    required
                    outlined
                ></v-text-field>
              </v-col>
            </v-row>

            <v-btn @click="$router.go(-1)" class="mx-3 mb-3 action_btn" large width="30%" color="grey lighten-2">취소</v-btn>
            <v-btn @click="update" class="mx-3 mb-3 white--text action_btn" large width="30%" color="#FF4155">변경하기</v-btn>
            </v-form>
      </div>
  </div>
</template>

<script>
import HNav from "@/components/common/HNav";
import axios from "axios";
import { dispatch, findById, update } from "@/api/user.js";
import { API_BASE_URL } from "@/config";

export default {
  components: {
    HNav
  },
  data: () => {
      return {
        emails: ['naver.com', 'gmail.com', 'hanmail.com'],

        validations : {
            email : false,
            form : false,
            auth : false,
        },

        pwdShow: false,
        pwdConfirmShow: false,

        authMenu : false,
        birthMenu : false,

        authNumber : "",
        enterAuthNumber : "",
    
        email1 : "",
        email2 : "",
        user: {
            email1: "",
            email2: "",
            password: "",
            passwordConfirm: "",
            name: "",
            gender : "",
            birth : "",
            phone: "",
            type : "",

            businessNumber : "",
            companyName : "",
            representativeName : "",
        },
        rules: {
            password : {
                required: value => !!value || '비밀번호를 입력해주세요.',
                min: value => {
                    const pattern = /^[A-Za-z0-9+]{8,}$/; 
                    var checkNum = !value ? false : value.search(/[0-9]/g);
                    var checkEng = !value ? false : value.search(/[a-z]/ig);
                    if(checkNum < 0 || checkEng < 0 || !pattern.test(value))
                        return "영문,숫자 포함 8 자리이상이어야 합니다.";
                    else
                        return true;
                },
            }
        },
    };
  },
  created() {
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
              console.log(error)
            })
        },
        error => {
          console.log(error)
        }
      )
  },
  methods : {
    update() {
      update(this.user,
        response => {
          alert("변경되었습니다.");
          this.$router.push("/");
        },
        error => {
          console.log(error);
        }
      )
    }
  }
}
</script>

<style scoped>
.row {
  margin-bottom : -25px;
}
.gender_btn{
  margin-bottom : 30px;
}
.action_btn{
  margin-top : 30px;
}
</style>