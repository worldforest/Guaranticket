<template>
    <div>
        <h-nav></h-nav>
        <div id="main-overview" class="container">
        <v-container>
            <v-layout>
                <v-flex>
                    <h2>콘서트</h2>
                </v-flex>
            </v-layout>
            <div class="container px-5 py-3">
                <v-row>
                    <div v-for="(concert,i) in Concerts" :key="i">
                        <div v-if="concert.category == 0">
                        <img :src="concert.poster" height="350" width="250" @click="performanceDetail(concert)" alt="콘서트">
                        <figcaption>
                            <div class="fig-author">
                                {{concert.title}}
                            </div>
                        </figcaption>
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