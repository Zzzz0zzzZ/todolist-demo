<template>
  <div class="card card-hover p-4">
    <div class="flex justify-around">
      <el-button plain @click="toAuthor">联系作者</el-button>
      <el-button plain @click="showBox">显示设置</el-button>
    </div>
  </div>
</template>

<script setup>
import { ElMessage, ElMessageBox } from 'element-plus'

const showBox = () => {
  let ddl = localStorage.getItem('ddl')
  let ddl_str = `当前${ddl}天`
  ElMessageBox.prompt(`设置ddl剩余多少天时，颜色变红: ${ddl ? ddl_str : '默认3天'}`, 'ddl<视觉提醒服务>', {
    confirmButtonText: '确认设置',
    cancelButtonText: '再想想',
    inputPlaceholder: 'xxx(天)',
    inputPattern: /^\d+$/,
    inputErrorMessage: '只能为数字, 且不能为空!'
  }).then(({ value }) => {
    localStorage.setItem('ddl', value)
    ElMessage.success(`${value}`)
    window.location.reload()
  })
}

const toAuthor = () => {
  window.open('https://github.com/Zzzz0zzzZ/todolist-demo', '_blank')
}
</script>
