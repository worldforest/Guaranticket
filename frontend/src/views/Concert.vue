<template>
    <div>
        <h-nav></h-nav>
        <div id="performance-overview" class="container" style="padding-left: 200px;">
            <h1>콘서트</h1>
            <div class="container px-5 py-3" style="margin-right: 10px;">
                <v-row>
                    <div v-for="(concert,i) in Concerts" :key="i">
                        <div v-if="concert.category == 0">
                            <img :src="`https://j3b101.p.ssafy.io/api/file/${concert.poster}`"
                                style="cursor:pointer;"
                                height="280px" width="218px"
                                @click="performanceDetail(concert)" alt="콘서트">
                            <div style="width:218px; height:100px; font-size: 18px;">
                                {{ concert.title }}
                            </div>
                        </div>
                    </div>
                </v-row>
            </div>
        </div>
    </div>
</template>

<script>
import { findAll } from "@/api/performance.js";
import axios from "axios";

// const perform = getAllPerform();

export default {
    data() {
        return {
            Concerts: []
        };
    },
    methods: {
       
    },
    created() {
        findAll(
            res => {
                this.Concerts=res.data
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