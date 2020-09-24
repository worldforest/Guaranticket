<template>
    <div id="main-overview" class="container">
        <h3>Detail</h3>
        {{performance.notice}}
        <img
          :src="performance.detail"
          width="80%"
          height="100%"
          alt="공연 상세정보 포스터"/>
    </div>
</template>

<script>
import { findById } from "@/api/performance.js";
export default {
    data() {
        return {
            performance: [],
        }
    },
    created() {
        var scope = this;
        var pid = this.$route.params.pid;
        this.pid=pid;
        findById(
            pid,
            res => {
                this.performance = res.data
            }
        )
    }
}
</script>

<style>

</style>