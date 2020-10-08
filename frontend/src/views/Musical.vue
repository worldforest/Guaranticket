<template>
    <div>
        <h-nav></h-nav>
        <div id="performance-overview" class="container" style="padding-left: 200px;">
            <h1>뮤지컬</h1>
            <div class="container px-5 py-3">
                <v-row>
                    <div v-for="(musical,i) in Musicals" :key="i">
                        <div v-if="musical.category == 1">
                            <img :src="`https://j3b101.p.ssafy.io/api/file/${musical.poster}`"
                                style="cursor:pointer;"
                                height="280px" width="218px"
                                @click="performanceDetail(musical)" alt="뮤지컬">
                            <div style="width:218px; height:100px; font-size: 18px;">
                                {{ musical.title }}
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
            Musicals: []
        };
    },
    methods: {
       
    },
    created() {
        findAll(
            res => {
                this.Musicals=res.data
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