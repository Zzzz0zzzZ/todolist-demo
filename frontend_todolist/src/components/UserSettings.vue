<template>
  <div class="container">
    <div class="msg-text">消息提醒服务</div>
    <el-radio-group v-model="set" fill="#2c70f4">
      <el-radio-button label="关闭" @click="handleChangeNotification(0)" />
      <el-radio-button label="开启" @click="handleChangeNotification(1)" />
    </el-radio-group>
  </div>
</template>
<script setup>

import {onBeforeMount, ref} from "vue";
import axios from "axios";
import {ElMessage} from "element-plus";

const set = ref("")

onBeforeMount(() => {
  axios({
    url: `/loc/settings/${localStorage.getItem("userid")}`,
    method: 'GET',
    headers: ({
      token: localStorage.getItem("token")
    }),
  }).then(res => {
    set.value = (res.data === 1) ? "开启" : "关闭"
    console.log(res)
  })
})

const handleChangeNotification = function (t) {
  axios({
    url: `/loc/settings`,
    method: 'POST',
    headers: ({
      token: localStorage.getItem("token")
    }),
    data: ({
      userid: localStorage.getItem("userid"),
      notification: t
    })
  }).then(() => {
    ElMessage.success("更改设置成功")
  })
}
</script>

<style scoped>
.container {
  margin-top: 5px;
  padding: 16px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  border: 1px solid #d3d3d3;
  border-radius: 5px;
}

.container:hover {
  box-shadow: 0 0 10px #808080;
  transition: 0.3s;
}

.msg-text::after {
  content: "(beta)";
}
</style>