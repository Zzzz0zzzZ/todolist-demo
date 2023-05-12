<template>
  <div class="text-area-btn">
    <el-button @click="dialogFormVisible = true" round>新增待办</el-button>
  </div>
  <el-dialog v-model="dialogFormVisible" title="新增一条待办">
    <el-form :model="form">
      <el-form-item label="待办内容" :label-width="formLabelWidth">
        <el-input v-model="form.content" autocomplete="off" />
      </el-form-item>
      <el-form-item label="截止时间" :label-width="formLabelWidth">
        <el-date-picker
          v-model="form.date"
          type="date"
          placeholder="选择截止日期"
          value-format="YYYY-MM-DD"
          :shortcuts="shortcuts"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button type="primary" @click="add">
          添加
        </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, reactive} from 'vue'
import {ElMessage} from "element-plus";
import {addTodo} from "@/api/todo";
import {defineEmits} from "vue";

const emit = defineEmits(['refresh'])
const dialogFormVisible = ref(false)
const formLabelWidth = '140px'
const form = reactive({
  content: '',
  date: ''
})
const shortcuts = [     // 快捷选择
  {
    text: '今天',
    value: new Date(),
  },
  {
    text: '明天',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() + 3600 * 1000 * 24)
      return date
    },
  },
  {
    text: '一周后',
    value: () => {
      const date = new Date()
      date.setTime(date.getTime() + 3600 * 1000 * 24 * 7)
      return date
    },
  }
]
const add = async () => {
    if (form.content.replace(/\s+/g, '') && form.date) {
      await addTodo(form.content, form.date)
      form.content = ""
      form.date = ""
      dialogFormVisible.value = false
      emit('refresh')
    }
    else {
      ElMessage.warning("待办内容和截止时间不能为空！")
    }
}
</script>

<style scoped>
.text-area-btn {
  display: flex;
  flex-direction: row-reverse;
  flex-grow: 1;
  margin-right: 5px;
}
</style>