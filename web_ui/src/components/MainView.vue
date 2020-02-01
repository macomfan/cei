<template>
  <!-- <ModelView></ModelView> -->
  <div>
    <el-tabs v-model="editableTabsName" type="card" closable @tab-remove="closeTab" v-show="isDisplay">
      <el-tab-pane v-for="(item) in editableTabs" :key="item.name" :label="item.title" :name="item.name">
        <span slot="label">
          <span style="color: firebrick;" v-if="isChanged(item)"><i class="el-icon-date" style="width: 25px;"></i>{{item.title}}</span>
          <span v-else><i class="el-icon-date" style="width: 25px;"></i>{{item.title}}</span>
        </span>
        <!-- {{item.content}} -->
        <component v-bind:is="item.content" :tableName="editableTabsName" :tableValue="getTableValue()" ref="child"
          @onUserChanged="onUserChanged"></component>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import ModelView from '../views/ModelView.vue'
  import Checker from '../utils/checker.js'
  import Bus from '../system/eventbus.js'
  import Dialog from '../utils/dialog.js'

  export default {
    components: {
      ModelView
    },

    data() {
      return {
        // editableTabsValue: '1',
        // editableTabs: [{
        //   title: 'Symbol',
        //   name: '1',
        //   content: 'ModelView'
        // }],
        // tabIndex: 1,
        editableTabsName: '',
        editableTabs: [],
      }
    },
    computed: {
      isDisplay: function() {
        return this.editableTabsName !== ''
      },
    },
    methods: {
      checkHasChanged() {

      },

      getTableValue() {
        if (this.editableTabsName !== '') {
          var item = this.editableTabs.find(item => item.name === this.editableTabsName)
          return item.title
        }
        return ''
      },

      isChanged(item) {
        return item.changed !== 0
      },

      onUserChanged(name) {
        window.console.log("onUserChanged " + name)
        var changedTables = this.editableTabs.filter(p => p.name === name)
        if (Checker.isNotEmpty(changedTables)) {
          changedTables[0].changed = 1
        }
      },

      addTab(tabName) {
        var item = this.editableTabs.find(p => p.name === tabName)
        if (Checker.isNull(item)) {
          this.editableTabs.push({
            title: tabName,
            name: tabName,
            content: 'ModelView',
            changed: 0
          });
        }
        this.editableTabsName = tabName;
      },

      removeTab(targetName) {
        let tabs = this.editableTabs;
        let activeName = this.editableTabsName;
        if (activeName === targetName) {
          tabs.forEach((tab, index) => {
            if (tab.name === targetName) {
              let nextTab = tabs[index + 1] || tabs[index - 1];
              if (nextTab) {
                activeName = nextTab.name;
              }
            }
          });
        }

        this.editableTabsName = activeName;
        this.editableTabs = tabs.filter(tab => tab.name !== targetName);
      },

      closeTab(targetName) {
        var item = this.editableTabs.find(p => p.name === targetName)
        if (Checker.isNotNull(item)) {
          if (item.changed !== 0) {
            Dialog.showConfirm('Notice', "All unsubmited change will be ignored!", () => {
              this.removeTab(targetName)
            }, () => {
              this.editableTabsName = this.editableTabsName;
            })
          } else {
            this.removeTab(targetName)
          }
        }
      }
    },
    
    mounted() {
      Bus.subscribe(Bus.UI_ADD_MODEL, this, (modelName) => {
        this.addTab(modelName)
      })
      Bus.subscribe(Bus.UI_OPEN_MODEL, this, (model) => {
        this.addTab(model)
      })
    },
    unmounted() {
      Bus.unsubscribeAll(this)
    }
  }
</script>
