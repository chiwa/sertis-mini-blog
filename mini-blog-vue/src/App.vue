<template>
  <div id="app">
    <h1>Sertis Mini Blog</h1>
    <Login msg="Sertis Mini Blog"/>
    <Card msg="Sertis Mini Blog"/>
  </div>
</template>

<script>
import Login from './components/Login.vue'
import Card from './components/Card.vue'

export default {
  name: 'App',
  components: {
    Login, Card
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
