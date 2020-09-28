<template>
    <div>
        <h-nav></h-nav>
        <div id="performance-overview" class="container">
        <v-container>
            <v-layout>
                <v-flex>
                    <h1>콘서트</h1>
                </v-flex>
            </v-layout>
            <div class="container px-5 py-3">
                <v-row>
                    <div v-for="(concert,i) in Concerts" :key="i">
                        <div v-if="concert.category == 0">
                            <img :src="concert.poster" height="auto" max-width="100%" @click="performanceDetail(concert)" alt="콘서트">
                            <div style="width:80%;">
                                {{concert.title}}
                            </div>
                        </div>
                    </div>
                </v-row>
            </div>
        </v-container>
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