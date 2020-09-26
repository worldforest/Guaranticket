<template>
<div class="container">
  <v-row>
    <h3>Location</h3>
      <v-row style="margin:50px;">
      <!-- <v-text-field
        v-model="date"
        readonly
        prepend-icon="📆"
      ></v-text-field> -->
      <v-date-picker
        v-model="date"
        class="mt-4"
        color="#FDDAB4"
        elevation="15"
      ></v-date-picker>
      </v-row>
  </v-row>
</div>
</template>

<script>
export default {
  data() {
    return {
      date: '',
    }
  }

}
</script>

<style>

</style>