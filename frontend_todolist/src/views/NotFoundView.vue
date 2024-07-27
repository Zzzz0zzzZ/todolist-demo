<template>
  <div class="flex flex-col gap-3 justify-center items-center h-screen bg-[url('@/assets/404_img.jpg')] bg-cover">
    <div class="text-6xl text-white">404</div>
    <el-button type="info" plain size="large" @click="tohome">返回</el-button>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()
const userid = localStorage.getItem('userid')
const tohome = () => {
  if ((localStorage.getItem('token') === '') | (localStorage.getItem('token') === null)) {
    router.push({ name: 'login' })
  } else {
    router.push({ path: `/todo/todolist/${userid}` })
  }
}
</script>
