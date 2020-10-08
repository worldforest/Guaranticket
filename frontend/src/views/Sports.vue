<template>
    <div>
        <h-nav></h-nav>
        <div id="performance-overview" class="container" style="padding-left: 200px;">
            <h1>스포츠</h1>
            <div class="container px-5 py-3">
                <v-row>
                    <div v-for="(sports,i) in Sports" :key="i">
                        <div v-if="sports.category == 2">
                        <img :src="`https://j3b101.p.ssafy.io/api/file/${sports.poster}`"
                            height="280px" width="218px"
                            @click="performanceDetail(sports)" alt="스포츠">
                            <div style="width:218px; height:100px; font-size: 18px;">
                                {{ sports.title }}
                            </div>
                        </div>
                    </div>
                </v-row>
            </div>
        </div>
    </div>
</template>

<script>
import { findAll, findById } from "@/api/performance.js";
import axios from "axios";


export default {
    data() {
        return {
            Sports: []
        };
    },
    methods: {
       
    },
    created() {
        findAll(
            res => {
                this.Sports=res.data
            }
        )
    },
    methods: {
        performanceDetail(performance){
            var performanceId = performance.pid;
            this.$router.push({
                name: 'performanceDetail',
                params: {pid: performanceId}
            });
        },
    }

}
</script>

<style>

</style>