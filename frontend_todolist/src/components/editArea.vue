<template>
  <div class="text-area-btn">
    <el-button @click="dialogFormVisible = true" type="primary" round plain>新增待办</el-button>
    &nbsp;&nbsp;&nbsp;
    <el-button @click="onCLickDeleteAllEvents" type="danger" round plain :disabled="disabledDeleteAll">一键删除</el-button>
    <el-dialog v-model="delDialogVisible" width="30%" top="30vh">
      <div>确认清空所有待办事项?</div>
      <template #footer>
        <span class="dialog-footer">
          <span>
            <el-button @click="delDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="deleteAllEvents">
              确认
            </el-button>
          </span>
        </span>
      </template>
    </el-dialog>
  </div>
  <el-dialog class="el-dialog-new" v-model="dialogFormVisible" title="新增一条待办">
    <el-form :model="form">
      <el-form-item label="截止时间" :label-width="formLabelWidth">
        <el-date-picker v-model="form.date" type="date" placeholder="选择截止日期" value-format="YYYY-MM-DD"
          :shortcuts="shortcuts" />
      </el-form-item>
      <el-form-item label="待办内容" :label-width="formLabelWidth">
        <el-input v-model="form.content"
                  autocomplete="off"
                  :autosize="{ minRows: 6, maxRows: 10 }"
                  type="textarea"
                  placeholder="您的待办内容..."/>
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
import { ref, reactive } from 'vue'
import { ElMessage } from "element-plus"
import { addTodo } from "@/api/todo"
import { defineEmits } from "vue"
import axios from 'axios'

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

// 处理删除逻辑
const disabledDeleteAll = ref(false)  // 是否允许删除
const delDialogVisible = ref(false)

const props = defineProps({
  todo_count: {
    type: Object,
    required: true,
  },
})
const onCLickDeleteAllEvents = () => {
  //  没有待办时候不允许删除
  //  有待办时候弹框提示
  if (props.todo_count.length === 0) {
    ElMessage.warning("无任何待办事项")
  } else {
    delDialogVisible.value = true
  }
}

// 删除所有_逻辑
const deleteAllEvents = () => {
  axios({
    url: `/api/delete-all`,
    method: 'POST',
    data: {
      userid: localStorage.getItem("userid")
    },
    headers: ({
      token: localStorage.getItem("token")
    })
  }).then(() => {
    ElMessage.success("删除成功")
    emit('refresh')
  })
  delDialogVisible.value = false
}
</script>

<style scoped>
.text-area-btn {
  display: flex;
  flex-direction: row-reverse;
  flex-grow: 1;
  margin-right: 5px;
}

.el-dialog-new {
  display: flex;
  justify-content: flex-start;
  flex-direction: row;

}
</style>