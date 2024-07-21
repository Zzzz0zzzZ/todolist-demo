<template>
  <div class="card hover-when-mouse-on">
    <div class="p-3 flex justify-around">
      <router-link :to="{ name: 'calender' }" class="router-link-active">
        <button type="button" class="btn btn-outline-primary">查看日历</button>
      </router-link>
      <button type="button" class="btn btn-outline-secondary" @click="showBox">显示设置</button>
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
</script>
