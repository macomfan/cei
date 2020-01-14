<template>
  <el-menu default-active="2" class="el-menu-vertical-demo" @open="handleOpen" @close="handleClose">
    <el-submenu index="1">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>cei</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>huobipro</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>huobipro</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>huobipro</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>huobipro</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>huobipro</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>huobipro</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>huobipro</span>
      </template>
      <el-menu-item-group>
        <template slot="title">Models</template>
        <el-menu-item index="1-1" @click="handleClick">Symbol</el-menu-item>
        <el-menu-item index="1-2">SymbolData</el-menu-item>
      </el-menu-item-group>
      <el-menu-item-group title="">
        <el-menu-item index="1-3">Clients</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
  </el-menu>
</template>

<script>
  export default {
    methods: {
      handleOpen(key, keyPath) {
        window.console.log(key, keyPath);
      },
      handleClose(key, keyPath) {
        window.console.log(key, keyPath);
      },
      handleClick() {
        window.console.log("click");
      }
    }
  }
</script>

<style>
</style>
