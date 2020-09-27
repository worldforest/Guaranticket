<template>
  <div class="text-center">
    <h-nav></h-nav>
     <v-container style="width:600px; margin-top:8rem;">
        <h2 class="mb-10">공연 등록</h2>
        <v-form ref="form">
                <v-row>
                    <v-col cols="3">
                        <v-subheader>타이틀</v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-text-field 
                            v-model="performance.title"
                            placeholder="공연제목을 입력해주세요"
                            :rules="[() => !!performance.title]"
                            outlined  dense
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="3">
                        <v-subheader>카테고리</v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-select
                        v-model="performance.category"
                        placeholder="카테고리를 선택해주세요"
                        :rules="[() => !!performance.category]"
                        :items="items"
                        item-text="category"
                        item-value="value"
                        outlined dense
                        ></v-select>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="3">
                        <v-subheader>위치</v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-dialog v-model="placeDialog" persistent scrollable max-width="500">
                            <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                v-model="performance.location"
                                placeholder="공연위치를 입력해주세요"
                                :rules="[() => !!performance.location]"
                                outlined readonly dense
                                v-bind="attrs" v-on="on"
                                ></v-text-field>
                            </template>
                            <v-card>
                                <v-card-title class="headline white--text" style="background-color:#FF4155;">
                                    공연장 검색
                                    <v-spacer></v-spacer>
                                    <v-btn @click="placeDialog=false" icon color="white">
                                        <v-icon>mdi-close</v-icon>
                                    </v-btn>    
                                </v-card-title>
                                <v-card-text class="pb-0" style="max-height:600px;">
                                    <v-text-field
                                        class="mt-10"
                                        v-model="keyword"
                                        outlined dense
                                        placeholder="공연장소를 검색하세요."
                                        append-icon="mdi-magnify"
                                        @keyup.enter="searchPlace"
                                        @click:append="searchPlace"
                                    ></v-text-field>
                                    <v-card
                                        v-for="(place, i) in places"
                                        :key="i"
                                        class="mx-auto"
                                        max-width="600"
                                        outlined
                                    >
                                        <v-list-item class="pt-4" two-line>
                                            <v-list-item-content class="pb-0">
                                                <v-list-item-title class="title">
                                                    {{place.place_name}}
                                                </v-list-item-title>
                                                <v-list-item-subtitle>
                                                    {{place.road_address_name}}
                                                </v-list-item-subtitle>
                                            </v-list-item-content>
                                        </v-list-item>

                                        <v-card-actions class="py-0">
                                            <v-spacer></v-spacer>
                                            <v-btn text color="#FF4155" @click="selectPlace(place)">선택</v-btn>
                                        </v-card-actions>
                                    </v-card>
                                        

                                </v-card-text>
                                <v-card-actions>
                                    <v-spacer></v-spacer>
                                    <v-btn class="text-h6" color="#FF4155" text @click="placeDialog = false">닫기</v-btn>
                                </v-card-actions>
                            </v-card>
                        </v-dialog>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="3">
                        <v-subheader>장소</v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-text-field
                            v-model="performance.place"
                            placeholder="공연장소를 입력해주세요"
                            :rules="[() => !!performance.place]"
                            outlined  dense
                        ></v-text-field>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="3">
                        <v-subheader>공연정보</v-subheader>
                    </v-col>
                    <v-col cols="4.5">
                        <v-text-field
                            type="number"
                            v-model="performance.running"
                            label="러닝타임"
                            :rules="[() => !!performance.running]"
                            suffix="분"
                            outlined  dense
                        ></v-text-field>
                    </v-col>
                    <v-col cols="4.5">
                        <v-text-field
                            type="number"
                            v-model="performance.attendance"
                            label="수용인원"
                            :rules="[() => !!performance.attendance]"
                            suffix="명"
                            outlined  dense
                        ></v-text-field>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="3">
                        <v-subheader>공연기간</v-subheader>
                    </v-col>
                    <v-col cols="4.5">
                        <v-menu
                            ref="performanceMenu"
                            v-model="performanceMenu"
                            :close-on-content-click="false"
                            :return-value.sync="dates"
                            transition="scale-transition"
                            offset-y 
                            min-width="290px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="performance.startDate"
                                    :rules="[() => !!performance.startDate]"
                                    label="공연시작일"
                                    outlined 
                                    readonly dense
                                    v-bind="attrs" v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker v-model="dates" no-title
                                range scrollable show-current
                                :min="new Date().toISOString().substr(0, 10)"
                                max="2021-01-01"
                            >
                                <v-spacer></v-spacer>
                                <v-btn text color="primary" @click="performanceMenu = false">Cancel</v-btn>
                                <v-btn text color="primary" @click="setDates('PERFORMANCE')">OK</v-btn>
                            </v-date-picker>
                        </v-menu>
                    </v-col>
                    <v-col cols="4.5">
                        <v-text-field
                            v-model="performance.endDate"
                            :rules="[() => !!performance.endDate]"
                            label="공연종료일" dense
                            outlined  readonly
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-row>
                    <v-col cols="3">
                        <v-subheader>공연시간</v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-combobox
                        v-model="performance.times"
                        :rules="[() => performance.times.length!=0]"
                        :items="times"
                        multiple outlined
                        small-chips dense
                        @change="sortTimes"
                        ></v-combobox>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="3">
                        <v-subheader>공지사항</v-subheader>
                    </v-col>
                    <v-col class="" cols="9">
                        <v-textarea
                        v-model="performance.notice"
                        outlined
                        ></v-textarea>
                    </v-col>
                </v-row>
                <v-divider></v-divider>

                <v-row>
                    <v-col cols="3">
                        <v-subheader>티켓등급</v-subheader>
                    </v-col>
                    <v-col class="" cols="9">
                        <v-combobox
                        v-model="performance.grades"
                        :items="grades"
                        :rules="[() => performance.grades.length!=0]"
                        multiple chips outlined dense
                        >
                        <template v-slot:selection="data">
                            <v-chip
                            :key="JSON.stringify(data.item)"
                            v-bind="data.attrs"
                            :input-value="data.selected"
                            :disabled="data.disabled"
                            @click:close="data.parent.selectItem(data.item)"
                            >
                            <v-avatar
                                class="accent white--text"
                                left
                                v-text="data.item.slice(0, 1).toUpperCase()"
                            ></v-avatar>
                            {{ data.item }}
                            </v-chip>
                        </template>
                        </v-combobox>
                    </v-col>
                </v-row>
                <v-row v-if="performance.grades.length>0">
                    <v-col cols="3">
                        <v-subheader>티켓가격</v-subheader>
                    </v-col>
                    <v-col cols="4.5" v-for="(grade, index) in performance.grades" :key="grade">
                        <v-text-field
                            v-model="performance.prices[index]"
                            :rules="[() => !!performance.prices[index]]"
                            suffix="원"
                            outlined dense
                        >
                            <template v-slot:prepend-inner>
                                <v-avatar
                                    size="20"
                                    class="accent mt-0 mr-2 white--text"
                                    v-text="grade"
                                ></v-avatar>
                            </template>
                        </v-text-field>
                    </v-col>
                </v-row>
                 <v-row>
                    <v-col cols="3">
                        <v-subheader>판매기간</v-subheader>
                    </v-col>
                    <v-col cols="4.5">
                        <v-menu
                            ref="ticketMenu"
                            v-model="ticketMenu"
                            :close-on-content-click="false"
                            :return-value.sync="dates"
                            transition="scale-transition"
                            offset-y 
                            min-width="290px"
                        >
                            <template v-slot:activator="{ on, attrs }">
                                <v-text-field
                                    v-model="performance.ticketingStartDate"
                                    :rules="[() => !!performance.ticketingStartDate]"
                                    label="티켓판매시작일"
                                    outlined 
                                    readonly dense
                                    v-bind="attrs" v-on="on"
                                ></v-text-field>
                            </template>
                            <v-date-picker v-model="dates" no-title
                                range scrollable show-current
                                :min="new Date().toISOString().substr(0, 10)"
                                max="2021-01-01"
                            >
                                <v-spacer></v-spacer>
                                <v-btn text color="primary" @click="ticketMenu = false">Cancel</v-btn>
                                <v-btn text color="primary" @click="setDates('TICKET')">OK</v-btn>
                            </v-date-picker>
                        </v-menu>
                    </v-col>
                    <v-col cols="4.5">
                        <v-text-field
                            v-model="performance.ticketingEndDate"
                            :rules="[() => !!performance.ticketingEndDate]"
                            label="티켓판매종료일" dense
                            outlined  readonly
                        ></v-text-field>
                    </v-col>
                </v-row>
                <v-divider></v-divider>
                <v-row>
                    <v-col cols="3">
                        <v-subheader>
                            메인이미지
                        </v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-file-input
                            ref="file1"
                            v-model="poster"
                            :rules="[() => !!poster]"
                            placeholder="메인이미지를 업로드해주세요"
                            prepend-icon=""
                            prepend-inner-icon="mdi-paperclip"
                            @click:prepend-inner="$refs.file1.$refs.input.click()"
                            @change="upload(poster,'POSTER')"
                            outlined dense
                            :show-size="1000"
                        >
                            <template v-slot:selection="{ text }">
                                <v-chip small label color="primary">
                                    {{ text }}
                                </v-chip>
                            </template>
                        </v-file-input>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="3">
                        <v-subheader>
                            상세이미지
                        </v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-file-input
                            ref="file2"
                            v-model="detail"
                            :rules="[() => !!detail]"
                            placeholder="상세이미지를 업로드해주세요"
                            prepend-icon=""
                            prepend-inner-icon="mdi-paperclip"
                            @click:prepend-inner="$refs.file2.$refs.input.click()"
                            @change="upload(detail,'DETAIL')"
                            outlined dense
                            :show-size="1000"
                        >
                            <template v-slot:selection="{ text }">
                                <v-chip small label color="primary">
                                    {{ text }}
                                </v-chip>
                            </template>
                        </v-file-input>
                    </v-col>
                </v-row>

                <v-divider></v-divider>
                <v-row justify="center">
                    <v-col>
                        <v-btn @click="$router.go(-1)" class="mx-3 mb-3" large width="30%" color="grey lighten-2">취소</v-btn>
                        <v-btn @click="submit" class="mx-3 mb-3 white--text" large width="30%" color="#FF4155">등록하기</v-btn>
                    </v-col>
                </v-row>

        </v-form>
     </v-container>
  </div>
</template>
<script>
import HNav from "@/components/common/HNav";
import { create, imgUpload } from "../api/performance.js";
import axios from "axios";

export default {
    components: {
        HNav
    },
    data() {
        return {
            performance : {
                title : "",
                poster : [],
                detail : [],
                category : "",
                location : "",
                place : "",
                running : "",
                ticketingStartDate : "",
                ticketingEndDate : "",
                startDate : "",
                endDate : "",
                attendance : "",
                notice : "",

                grades : [],
                prices : [],
                times : [],

                uid : 74
            },
            poster : [],
            detail : [],

            items : [
                { category : '콘서트', value : 0 },
                { category : '뮤지컬', value : 1 },
                { category : '스포츠', value : 2 },
            ],
            placeDialog : false,
            ticketMenu : false,
            performanceMenu : false,
            keyword  : "",
            places : [],
            dates : [],

            grades : ['R', 'S'],
            times : ['09:00','10:00','11:00','12:00','13:00','14:00',
            '15:00','16:00','17:00','18:00','19:00',
            '20:00','21:00','22:00','23:00'],
            ps : "",
        }
    },
    computed : {
    },
    watch : {
        performance : {
            deep : true,
            handler() {
                if(this.performance.grades.length == 2){
                    if(this.performance.grades[0] == 'S'){
                        var temp = this.performance.grades[0];
                        this.performance.grades[0] = this.performance.grades[1];
                        this.performance.grades[1] = temp;
                    }
                }
            },
        },
    },
    mounted() {
        const API_KEY = "9108903d91a007c3d4302e91cee2bd6a";
        if (window.kakao && window.kakao.maps) {
            this.initKakaoAPI();
        } else {
            const script = document.createElement("script");
            script.onload = () => kakao.maps.load(this.initKakaoAPI);
            script.src = `https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=${API_KEY}&libraries=services`;
            document.head.appendChild(script);
        }
    },
    methods : {
        sortTimes(){
            this.performance.times.sort();
        },
        submit(){
            this.upload();
            if(this.$refs.form.validate()){
                create(this.performance, 
                    response => {
                        alert("공연이 등록되었습니다.");
                        this.$router.push("/");
                    },
                    error => {
                        console.error(error)
                    }
                );
            }
        },
        setDates(type){
            if(this.dates.length == 1)
                this.dates.push(this.dates[0]);
            if(this.dates[0] > this.dates[1])
                this.swap(this.dates);
            switch (type) {
                case "TICKET":
                    this.performance.ticketingStartDate = this.dates[0];
                    this.performance.ticketingEndDate = this.dates[1];
                    this.ticketMenu = false;
                    break;
                case "PERFORMANCE":
                    this.performance.startDate = this.dates[0];
                    this.performance.endDate = this.dates[1];
                    this.performanceMenu = false;
                    break;
            }
            this.dates = [];
        },
        swap(dates) {
            let temp = this.dates[0];
            this.dates[0] = this.dates[1];
            this.dates[1] = temp;
        },
        initKakaoAPI() {
            this.ps = new kakao.maps.services.Places();
        },
        selectPlace(place){
            this.performance.place = place.place_name;
            this.performance.location = place.road_address_name;
            this.places = [];
            this.placeDialog = false;
        },
        searchPlace() {
            this.ps.keywordSearch(this.keyword, this.placesSearchCB); 
            this.keyword = "";
        },
        placesSearchCB (data, status, pagination) {
            if (status === kakao.maps.services.Status.OK) {
                var places = [];
                data.forEach(place => {
                    if(place.category_group_code=="CT1"){
                        places.push(place);
                    }
                });
                this.places = places;
            }       
        },
        upload(uploadFile, type) {
            if(!uploadFile)
                return;
            
            const file = new FormData();
            file.append('file',uploadFile);
            imgUpload(file, 
                response => {
                    switch (type) {
                        case 'DETAIL':
                            this.performance.detail = response.data;
                            break;
                        case 'POSTER':
                            this.performance.poster = response.data;
                            break;
                    }
                    var uploadImg = `http://localhost:8080/api/file/${response.data}`;
                },
                error => {
                    alert("이미지 전송 실패");
                }
            )
        },
    }
}
</script>
<style scoped>
.v-chip--select{
    margin-bottom : 1px;
}
.row{
    margin-bottom : -30px;
}
</style>