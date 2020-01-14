<template>
<!--  <div id="app">
    <el-row>
      <el-col :span="24">
        <div style="text-align: left;">Welcome to CEI</div>
      </el-col>
      <el-row>
        <el-col :span="24" style="text-align: left;">
          <MainMenuBar></MainMenuBar>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="2">
          <NavBar></NavBar>
        </el-col>
        <el-col :span="20">
          <HelloWorld msg="Welcome to Your Vue.js App" />
        </el-col>
      </el-row>
    </el-row>
    <table style="width: 100%;">
      <tr>WWW</tr>
      <tr style="background-color: #409EFF; height: 1px;"></tr>
      <tr>222</tr>
    </table>
  </div> -->
  <div id="app" style="width: 100%;">
    <table style="width: 100%;">
      <tr><td style="background-color: aliceblue;"><div style="margin: 10px;">Welcome to CEI</div></td></tr>
      <tr style="height: 1px; background-color: #2C3E50;"><td></td></tr>
      <tr><td style="background-color:#EEEEEE"><MainMenuBar></MainMenuBar></td></tr>
      <tr><td>
      <table>
        <tr>
          <td style="width: 200px;"><NavBar></NavBar></td>
          <td style="width: 1000px; vertical-align: top;"><MainView></MainView></td>
        </tr>
      </table>
      </td></tr>
    </table>
    
  </div>
  
</template>

<script>
  import MainView from './components/MainView.vue'
  import MainMenuBar from './components/MainMenuBar.vue'
  import NavBar from './components/NavBar.vue'

  export default {
    name: 'app',
    components: {
      MainView,
      MainMenuBar,
      NavBar
    }
  }
</script>

<style>
  #app {
    font-family: Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    color: #2c3e50;
  }
</style>
