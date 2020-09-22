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
                        <img :src="concert.poster" height="350" width="250" alt="콘서트">
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
import { findAll, findById } from "@/api/performance.js";
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
        axios
        .get("http://localhost:8080/api/performance")
        .then(res => {
            this.Concerts=res.data
        })
        .catch();

        // findAll(
        //     function(response) {
        //         console.log(response.request.status)
        //         console.log(response.data)
        //         console.log(response.data[1].title)
        //     }
        // )
    },

}
</script>

<style>

</style>