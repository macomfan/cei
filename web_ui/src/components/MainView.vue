<template>
  <!-- <ModelView></ModelView> -->
  <div>
    <el-tabs v-model="editableTabsValue" type="card" closable @tab-remove="removeTab" v-show="isDisplay">
      <el-tab-pane v-for="(item) in editableTabs" :key="item.name" :label="item.title" :name="item.name">
        <span slot="label"><i class="el-icon-date" style="width: 25px;"></i>{{item.title}}</span>
        <!-- {{item.content}} -->
        <component v-bind:is="item.content" ref="child"></component>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  import ModelView from '../views/ModelView.vue'
  import Bus from '../system/eventbus.js'

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
        editableTabsValue: '0',
        editableTabs: [],
        tabIndex: 0
      }
    },
    computed: {
      isDisplay: function() {
        return this.editableTabsValue !== '0'
      }
    },
    methods: {
      addTab(modelName) {
        let newTabName = ++this.tabIndex + '';
        this.editableTabs.push({
          title: modelName,
          name: newTabName,
          content: 'ModelView'
        });
        this.editableTabsValue = newTabName;
      },
      removeTab(targetName) {
        let tabs = this.editableTabs;
        let activeName = this.editableTabsValue;
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

        this.editableTabsValue = activeName;
        this.editableTabs = tabs.filter(tab => tab.name !== targetName);
      }
    },
    mounted() {
      Bus.subscribe(Bus.UI_ADD_MODEL, (modelName) => {
        this.addTab(modelName)
      })
    }
  }
</script>
