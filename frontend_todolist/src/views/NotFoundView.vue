<template>
  <div class="">
    <img src="../assets/404_img.jpg" alt="" />
    <h1>404</h1>
    <div id="btn">
      <el-button type="info" plain size="large" @click="tohome">返回</el-button>
    </div>
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

<style lang="scss" scoped>
* {
  margin: 0;
}

img {
  width: 100%;
  height: 100%;
  z-index: -1;
  position: absolute;
}

h1 {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 390px;
  color: azure;
  font-size: 150px;
}

#btn {
  text-align: center;
}
</style>
