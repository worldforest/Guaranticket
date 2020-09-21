<template>
    <div class="text-center">
        <v-container>
        <v-form
            ref="form"
            v-model="valid"
        >
            <v-row>
                <v-col class="pr-0 pb-0" cols="4">
                    <v-text-field
                        v-model="user.email1"
                        :rules="[() => !!user.email1 || '이메일을 입력해주세요.']"
                        label="이메일"
                        required
                        outlined
                    ></v-text-field>
                </v-col>
                <v-col class="pb-0" cols="5">
                    <v-autocomplete
                        prepend-icon="mdi-at"
                        ref="email"
                        v-model="user.email2"
                        :items="emails"
                        placeholder="선택해주세요."
                        required
                        outlined
                    >
                    </v-autocomplete>
                </v-col>
                <v-col class="pl-0" cols="3">
                    <v-btn x-large width="100%" color="grey lighten-2 font-weight-bold">중복확인</v-btn>
                </v-col>
            </v-row>
            <v-text-field
                v-model="user.password"
                :append-icon="pwdShow ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="rules.password"
                :type="pwdShow ? 'text' : 'password'"
                @click:append="pwdShow = !pwdShow"
                placeholder="비밀번호를 입력해주세요."
                outlined
            ></v-text-field>
            <v-text-field
                v-model="user.passwordConfirm"
                :append-icon="pwdConfirmShow ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="rules.password"
                :type="pwdConfirmShow ? 'text' : 'password'"
                @click:append="pwdConfirmShow = !pwdConfirmShow"
                placeholder="비밀번호를 확인해주세요."
                outlined
            ></v-text-field>

            <v-text-field
                ref="name"
                v-model="user.name"
                :rules="[() => !!user.name || '이름을 입력해주세요.']"
                placeholder="이름을 입력해주세요."
                required
                outlined
            ></v-text-field>
            <v-text-field
                ref="birth"
                v-model="user.birth"
                :rules="[() => !!user.birth || '출생년도를 입력해주세요.']"
                required
                placeholder="출생년도를 입력해주세요."
                outlined
            ></v-text-field>
            <v-row> 
                <v-col  class="pt-0" cols="6">
                        <v-btn large outlined width="100%">
                            남자
                        </v-btn>
                </v-col>
                <v-col   class="pt-0" cols="6">
                        <v-btn large outlined width="100%">
                            여자
                        </v-btn>
                </v-col>

                <!-- <v-col cols="6">
                    <v-row class="mx-auto">
                        성별을 선택해주세요
                    </v-row>
                </v-col>
                <v-col cols="6">
                    <v-row class="mx-auto">
                        <v-spacer></v-spacer>
                        <v-btn small fab dark flat color="grey">
                            <v-icon dark>mdi-account</v-icon>         
                        </v-btn>
                        <v-spacer></v-spacer>
                        <v-btn small fab dark flat color="grey">
                            <v-icon dark>mdi-face-woman</v-icon>         
                        </v-btn>
                        <v-spacer></v-spacer>
                    </v-row>
                </v-col> -->
            </v-row>
            <v-btn class="mx-3 mb-3" large width="30%" color="grey lighten-2">취소</v-btn>
            <v-btn class="mx-3 mb-3" large width="30%" color="primary">가입하기</v-btn>
            </v-form>
        </v-container>
    </div>
</template>

<script>
import { signup } from "../api/user.js";

export default {
  data() {
    return {
        emails: ['naver.com', 'gmail.com', 'hanmail.com', '직접 입력'],
        errorMessages: '',
        pwdShow: false,
        pwdConfirmShow: false,
        valid : "",
        user: {
            email1: "",
            email2: "",
            password: "",
            passwordConfirm: "",
            name: "",
            birth : "",
        },
        rules: 
        {
            // password : value => {
            //     required: value => !!value || '비밀번호를 입력해주세요.',
            //     min: value => {
            //         const pattern = /^[A-Za-z0-9+]{8,}$/; 
            //         var checkNum = !value ? false : value.search(/[0-9]/g);
            //         var checkEng = !value ? false : value.search(/[a-z]/ig);
            //         if(checkNum < 0 || checkEng < 0 || !pattern.test(value))
            //             return "영문,숫자 포함 8 자리이상이어야 합니다.";
            //         else
            //             return true;
            //     },
            //     emailMatch: () => ('The email and password you entered don\'t match'),
            // }
        },


    };
  },
    computed: {
        form () {
            return {
                name: this.name,
                address: this.address,
                city: this.city,
                state: this.state,
                zip: this.zip,
                country: this.country,
            }
        },
    },

  methods: {
    register() {
      var vm = this;

      if (this.user.password === this.user.passwordConfirm) {
        signup(
          this.user.email,
          this.user.name,
          this.user.password,
          function() {
            alert("회원가입이 완료되었습니다.");
            vm.$router.push("/");
          },
          function(error) {
            console.error(error);
          }
        );
      } else {
        alert("비밀번호가 일치하지 않습니다.");
      }
    }
  }
};
</script>

<style></style>
