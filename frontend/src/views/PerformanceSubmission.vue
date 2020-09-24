<template>
  <div class="text-center">
    <h-nav></h-nav>
     <v-container style="width:600px; margin-top:8rem;">
        <h2 class="mb-10">공연 등록</h2>
        <v-form>
                <v-row>
                    <v-col cols="3">
                        <v-subheader>타이틀</v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-text-field 
                            v-model="performance.title"
                            placeholder="공연제목을 입력해주세요"
                            outlined clearable
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
                        :items="items"
                        item-text="category"
                        item-value="value"
                        outlined
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
                                outlined readonly
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
                                        outlined
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
                            outlined clearable
                        ></v-text-field>
                    </v-col>
                </v-row>

                <v-row>
                    <v-col cols="3">
                        <v-subheader>러닝타임</v-subheader>
                    </v-col>
                    <v-col cols="9">
                        <v-text-field
                            type="number"
                            v-model="performance.running"
                            placeholder="러닝타임을 입력해주세요"
                            outlined clearable
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
                                    label="공연시작일"
                                    outlined clearable
                                    readonly
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
                            label="공연종료일"
                            outlined clearable readonly
                        ></v-text-field>
                    </v-col>
                </v-row>


                <v-row>
                    <v-col cols="3">
                        <v-subheader>티켓판매기간</v-subheader>
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
                                    v-model="performance.ticketStartDate"
                                    label="티켓판매시작일"
                                    outlined clearable
                                    readonly
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
                            v-model="performance.ticketEndDate"
                            label="티켓판매종료일"
                            outlined clearable readonly
                        ></v-text-field>
                    </v-col>
                </v-row>

                <v-subheader>티켓판매기간</v-subheader>
                <v-row>
                    <v-col class="pt-0" cols="12">
                        <v-textarea
                        v-model="performance.notice"
                        outlined
                        ></v-textarea>
                    </v-col>
                </v-row>

        </v-form>
        <div v-text="performance">
        </div>

     </v-container>
  </div>
</template>
<script>
import HNav from "@/components/common/HNav";

export default {
    data() {
        return {
            performance : {
                title : "",
                poster : "",
                detail : "",
                category : "",
                location : "",
                place : "",
                running : "",
                ticketStartDate : "",
                ticketEndDate : "",
                startDate : "",
                endDate : "",
                attendance : "",
                notice : "",
            },

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
            
            ps : "",
        }
    },
    mounted() {
      if (window.kakao && window.kakao.maps) {
        this.initKakaoAPI();
      } else {
        const script = document.createElement("script");
        script.onload = () => kakao.maps.load(this.initKakaoAPI);
        script.src =
          "https://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=9108903d91a007c3d4302e91cee2bd6a&libraries=services";
        document.head.appendChild(script);
      }
    },
    methods : {
        setDates(type){
            if(this.dates.length == 1)
                this.dates.push(this.dates[0]);
            if(this.dates[0] > this.dates[1])
                this.swap(this.dates);
            switch (type) {
                case "TICKET":
                    this.performance.ticketStartDate = this.dates[0];
                    this.performance.ticketEndDate = this.dates[1];
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
    }
}
</script>
<style scoped>
.row{
    margin-bottom : -40px;
}
</style>