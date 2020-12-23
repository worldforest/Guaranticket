<template>
    <div class="text-center">
        <h-nav></h-nav>
        <v-container style="width:600px; margin-top:8rem;">
        <h2 class="mb-10">일반회원가입</h2>
        <v-form ref="form" v-model="validations.form">
            <v-row>
                <v-col class="pr-0 pb-0" cols="4">
                    <v-text-field
                        v-model="user.email1"
                        :rules="[() => !!user.email1 || '이메일을 입력해주세요.']"
                        placeholder="이메일을 입력하세요."
                        required
                        outlined
                    ></v-text-field>
                </v-col>
                <v-col class="pb-0" cols="8">
                    <v-autocomplete
                        prepend-icon="mdi-at"
                        ref="email"
                        :rules="[() => !!user.email2 || '이메일을 입력해주세요.']"
                        v-model="user.email2"
                        :items="emails"
                        placeholder="선택해주세요."
                        required
                        outlined
                    >
                        <template v-slot:append-outer>
                            <v-btn style="top:-17px;" @click="duplicationValid" x-large width="90%" color="grey lighten-2 font-weight-bold">
                                중복확인
                            </v-btn>
                        </template>
                    </v-autocomplete>
                </v-col>
            </v-row>
            <v-text-field
                v-model="user.password"
                :append-icon="pwdShow ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[rules.password.required, rules.password.min]"
                :type="pwdShow ? 'text' : 'password'"
                @click:append="pwdShow = !pwdShow"
                placeholder="비밀번호를 입력해주세요."
                outlined
            ></v-text-field>
            <v-text-field
                v-model="user.passwordConfirm"
                :append-icon="pwdConfirmShow ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[() => user.password == user.passwordConfirm || '패스워드가 다릅니다.']"
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
                v-model="user.phone"
                :rules="[() => !!user.phone || '휴대폰 번호를 입력해주세요.']"
                placeholder="휴대폰 번호를 입력해주세요."
                required
                outlined
            >
                <template v-slot:append-outer>
                    <v-btn style="top:-17px;" @click="sms" x-large width="90%" color="grey lighten-2 font-weight-bold">
                        문자인증
                    </v-btn>
                    <v-dialog
                        v-model="authMenu"
                        max-width="350"
                        persistent 
                        >
                        <v-card>
                            <v-card-title class="white--text" style="background-color:#FF4155;">
                                SMS 본인 인증
                                <v-spacer></v-spacer>
                                <v-btn @click="authMenu=false" icon color="white">
                                    <v-icon>mdi-close</v-icon>
                                </v-btn>
                            </v-card-title>

                            <v-card-text class="pt-5 pb-0">
                                <v-text-field
                                    v-model="enterAuthNumber"
                                    placeholder="인증번호를 입력해주세요."
                                    :rules="[() => enterAuthNumber==authNumber || '인증번호를 다시 확인해주세요.']"
                                    required
                                    outlined
                                    dense
                                >
                                    <template v-slot:append-outer>
                                        <v-btn style="top:-6px;" 
                                        class="pa-0 white--text"
                                        color="#FF4155"
                                        @click="authValid"
                                    >
                                    확인
                                    </v-btn>
                                    </template>
                                </v-text-field>
                            </v-card-text>
                        </v-card>
                    </v-dialog>
                </template>
            </v-text-field>

            <v-row> 
                <v-col class="pt-0 pb-7" cols="6" style="margin-left: -15px;">
                    <v-btn ref="male" @click="user.gender='남'" :color="user.gender=='남'?'#FF4155':'grey'" x-large outlined width="100%">
                        남성
                    </v-btn>
                </v-col>
                <v-col class="pt-0 pb-7" cols="6">
                    <v-btn ref="female" @click="user.gender='여'" :color="user.gender=='여'?'#FF4155':'grey'" x-large outlined width="100%">
                        여성
                    </v-btn>
                </v-col>
            </v-row>

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

           <div style="margin-left: 130px;">
                <v-btn @click="$router.go(-1)" class="mx-3 mb-3" large width="30%" color="grey lighten-2">취소</v-btn>
                <v-btn @click="doJoin" class="mx-3 mb-3 white--text" large width="30%" color="#FF4155">가입하기</v-btn>
           </div>
            </v-form>
        </v-container>
    </div>
</template>

<script>
import { signup } from "@/api/user.js";
import { findByEmail } from "@/api/user.js";
import { sendSMS } from "@/api/user.js";

export default {
  data() {
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

        user: {
            email1: "",
            email2: "",
            password: "",
            passwordConfirm: "",
            name: "",
            phone : "",
            gender : "",
            birth : "",

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
    watch: {
        birthMenu (val) {
            val && setTimeout(() => (this.$refs.picker.activePicker = 'YEAR'))
        },
    },
    computed: {
    },
    methods: {
        authValid(){
            if(this.authNumber == this.enterAuthNumber){
                this.validations.auth = true;
                this.authMenu = false;
            }
        },
        sms(){
            // if(!this.user.phone){
            //     alert("휴대폰 번호를 입력해주세요.");
            //     return;
            // }
            this.authMenu = true;
            sendSMS(this.user.phone,
                response => {
                    this.authNumber = response.data;
                },
                error => {
                    // console.log(error);
                }
            )
        },
        duplicationValid(){
            if(!this.user.email1 || !this.user.email2){
                alert("이메일을 입력해주세요.");
                return;
            }
            findByEmail(
                this.user.email1 + '@' + this.user.email2, 
                response => {
                    if(!response.data.data){
                        alert("사용가능한 이메일입니다.");
                        this.validations.email = true;
                    }
                    else{
                        alert("존재하는 이메일입니다.");
                        this.validations.email = false;
                    }
                },
                error => {
                    alert("이메일을 확인해주세요.");
                    this.validations.email = false;
                }
            )
        },
        doJoin(){
            if(!this.validations.email)
                alert("이메일 중복확인이 필요합니다.");
            // else if(!this.validations.auth)
            //     alert("휴대폰 인증이 필요합니다.");
            else if(!this.user.gender)
                alert("성별을 선택하세요.");
            else if(this.$refs.form.validate()){
                signup(
                    this.user.email1 + '@' + this.user.email2, 
                    this.user.password,
                    this.user.name,
                    this.user.phone,
                    this.user.gender,
                    this.user.birth,
                    this.user.businessNumber,
                    this.user.companyName,
                    this.user.representativeName,
                    response => {
                        alert("회원가입이 완료되었습니다.");
                        this.$router.push("/");
                    },
                    error => {
                        // console.log(error);
                    }
                )
            }
        },
    }
};
</script>

<style></style>
