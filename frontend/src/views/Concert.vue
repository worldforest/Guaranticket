<template>
    <div>
        <h-nav></h-nav>
        <div style="margin: 10vmax 8vmin 0 8vmin ">
            <h1>콘서트</h1>
            <div style="margin: 2vmax 0 0 0; ">
                <v-row>
                    <div v-for="(concert,i) in Concerts" :key="i">
                        <div v-if="concert.category == 0">
                            <img :src="`https://j3b101.p.ssafy.io/api/file/${concert.poster}`"
                                height="400vmin" width="300vmin" @click="performanceDetail(concert)" alt="콘서트">
                            <div>
                                {{concert.title}}
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