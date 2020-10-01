<template>
    <div>
        <h-nav></h-nav>
        <div class="container" style="width:600px; margin-top:8rem; text-align: center;">
            <h3>비밀번호 변경 페이지</h3>
            <v-form ref="form">
                <v-row>
                    <v-col>
                        <v-text-field
                            v-model="password.origin.value"
                            :rules="[() => !!password.origin.value || '현재 비밀번호를 입력해주세요.']"
                            :append-icon="password.origin.show ? 'mdi-eye' : 'mdi-eye-off'"
                            @click:append="password.origin.show = !password.origin.show"
                            :type="password.origin.show ? 'text' : 'password'"
                            placeholder="현재 비밀번호"
                            required outlined
                        ></v-text-field>
                        
                        <v-text-field
                            v-model="password.new.value"
                            :rules="[rules.password.required, rules.password.min]"
                            :append-icon="password.new.show ? 'mdi-eye' : 'mdi-eye-off'"
                            @click:append="password.new.show = !password.new.show"
                            :type="password.new.show ? 'text' : 'password'"
                            placeholder="새 비밀번호"
                            required outlined
                            hide-details
                        ></v-text-field>
                        <v-text-field
                            v-model="password.newConfirm.value"
                            :rules="[() => password.new.value == password.newConfirm.value  || '패스워드가 다릅니다.' ]"
                            :append-icon="password.newConfirm.show ? 'mdi-eye' : 'mdi-eye-off'"
                            @click:append="password.newConfirm.show = !password.newConfirm.show"
                            :type="password.newConfirm.show ? 'text' : 'password'"
                            placeholder="새 비밀번호 확인"
                            required outlined
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
import { dispatch, findById, update, updatePwd } from "@/api/user.js";
import { API_BASE_URL } from "@/config";

export default {
    components: {
        HNav
    },
    data : () => {
        return {
            user : {},
            password : {
                origin : { value : "", show : false },
                new : { value : "", show : false },
                newConfirm : { value : "", show : false },
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
        }
    },
    created() {
        dispatch(
            response => {
                var uid = response.data.data;
                findById(uid,
                    response => {
                        this.user = response.data.data;
                    },
                    error =>{
                        console.log(error);
                    })
            },
            error => {
                console.log(error);
            }
        )
    },
    methods : {
        update(){
            if(!this.$refs.form.validate())
                return;
            else if(this.password.new.value != this.password.newConfirm.value){
                alert("비밀번호를 확인해주세요.")
                return;
            }

            updatePwd(this.password.origin.value, this.password.new.value,
                response => {
                    if(response.data.status){
                        alert("비밀번호가 변경되었습니다.");
                        this.$router.push("/");
                    }
                    else{
                        if(response.data.data == "INVALID_PASSWORD")
                            alert("현재 비밀번호를 다시 확인해주세요.");
                        else if(response.data.data == "SAME_PASSWORD")
                            alert("현재 비밀번호와 다른 비밀번호를 입력해주세요.")
                    }
                },
                error => {
                    console.log(error);
                }
            )
        }
    },
}
</script>
<style scoped>

</style>