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
                    <div v-for="(concert,i) in Concerts" :key="i">
                        <div v-if="concert.category == 2">
                        <img :src="concert.poster" height="350" width="250" alt="스포츠">
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
            console.log(res.data)
            this.Concerts=res.data
        })
        .catch();
    },

}
</script>

<style>

</style>