<template>
    <div class="text-center">
        <h-nav></h-nav>
        <v-container style="width:600px; margin-top:8rem;">
        <h2 class="mb-10">기업회원가입</h2>
        <v-form ref="form" v-model="formValid">
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
                <v-col class="pb-0" cols="5">
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
                    </v-autocomplete>
                </v-col>
                <v-col class="pl-0" cols="3">
                    <v-btn @click="duplicationVaild" x-large width="100%" color="grey lighten-2 font-weight-bold">
                        중복확인
                    </v-btn>
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
                v-model="user.businessNumber"
                :rules="[() => !!user.businessNumber || '사업자 번호를 입력해주세요.']"
                placeholder="사업자 번호를 입력해주세요."
                required
                outlined
            ></v-text-field>

            <v-text-field
                v-model="user.companyName"
                :rules="[() => !!user.companyName || '상호를 입력해주세요.']"
                placeholder="상호를 입력해주세요."
                required
                outlined
            ></v-text-field>

            <v-text-field
                v-model="user.representativeName"
                :rules="[() => !!user.representativeName || '대표자명을 입력해주세요.']"
                placeholder="대표자명을 입력해주세요."
                required
                outlined
            ></v-text-field>
            <v-row> 
                <v-col class="pt-0 pb-7" cols="6">
                    <v-btn ref="male" @click="user.gender='남'" :color="user.gender=='남'?'#FF4155':'grey'" x-large outlined width="100%">
                        남성
                    </v-btn>
                </v-col>
                <v-col class="pt-0 pb-7" cols="6">
                    <v-btn ref="female" @click="user.gender='여'" :color="user.gender=='여'?'#FF4155':'grey'" x-large outlined width="100%">
                        여성
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

            <v-btn @click="$router.go(-1)" class="mx-3 mb-3" large width="30%" color="grey lighten-2">취소</v-btn>
            <v-btn @click="doJoin" class="mx-3 mb-3 white--text" large width="30%" color="#FF4155">가입하기</v-btn>
            </v-form>
        </v-container>
    </div>
</template>

<script>
import { signup } from "../api/user.js";
import { findByEmail } from "../api/user.js";

export default {
  data() {
    return {
        emails: ['naver.com', 'gmail.com', 'hanmail.com'],
        emailVaild : false,
        formValid : false,
        pwdShow: false,
        pwdConfirmShow: false,
        birthMenu : false,
        user: {
            email1: "",
            email2: "",
            password: "",
            passwordConfirm: "",
            name: "",
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
        duplicationVaild(){
            findByEmail(
                this.user.email1 + '@' + this.user.email2, 
                response => {
                    alert("사용가능한 이메일입니다.");
                    this.emailVaild = true;
                },
                error => {
                    alert("존재하는 이메일입니다.");
                    this.duplicationVaild = false;
                }
            )
        },
        doJoin(){
            if(!this.emailVaild)
                alert("이메일 중복확인이 필요합니다.");
            else if(!this.user.gender)
                alert("성별을 선택하세요.");
            else if(this.$refs.form.validate()){
                signup(
                    this.user.email1 + '@' + this.user.email2, 
                    this.user.password,
                    this.user.name,
                    this.user.businessNumber,
                    this.user.companyName,
                    this.user.representativeName,
                    this.user.gender,
                    this.user.birth,
                    response => {
                        alert("회원가입이 완료되었습니다.");
                        this.$router.push("/");
                    },
                    error => {
                        console.log(error);
                    }
                )
            }
        },
    }
};
</script>

<style></style>
