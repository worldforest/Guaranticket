<template>
    <div>
        <h-nav></h-nav>
        <div id="main-overview" class="container">
        <v-container>
            <v-layout>
                <v-flex>
                    <h2>스포츠</h2>
                </v-flex>
            </v-layout>
            <div class="container px-5 py-3">
                <v-row>
                    <div v-for="(sports,i) in Sports" :key="i">
                        <div v-if="sports.category == 2">
                        <img :src="sports.poster" height="350" width="250" @click="performanceDetail(sports)" alt="스포츠">
                        <figcaption>
                            <div class="fig-author">
                                {{sports.title}}
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