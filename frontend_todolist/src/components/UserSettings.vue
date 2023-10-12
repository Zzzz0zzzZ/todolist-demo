<template>
  <div class="container">
    <div class="inner-flex">
      <div class="msg-text">消息提醒服务</div>
      <el-radio-group v-model="set" fill="#2c70f4">
        <el-radio-button label="关闭" @click="handleChangeNotification(0)" />
        <el-radio-button label="开启" @click="handleChangeNotification(1)" />
      </el-radio-group>
    </div>
    <div style="color: grey; padding-left: 6px">当前邮箱：{{ dispEmail() }}</div>
  </div>
</template>
<script setup>
import { onBeforeMount, ref } from 'vue'
import axios from 'axios'
import { ElMessage, ElMessageBox } from 'element-plus'

const set = ref('')

const showBox = () => {
  ElMessageBox.prompt('请输入您的个人邮箱地址', '启用<待办每日提醒服务>', {
    confirmButtonText: '确认开启',
    cancelButtonText: '暂不使用',
    inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
    inputErrorMessage: '不合法的邮箱',
    inputPlaceholder: 'example@email.com'
  })
    .then(({ value }) => {
      axios({
        url: `/api/setEmails`,
        method: 'POST',
        headers: {
          token: localStorage.getItem('token')
        },
        data: {
          userid: localStorage.getItem('userid'),
          notification: 1,
          email: value
        }
      }).then(() => {
        ElMessage({
          type: 'success',
          message: `设置成功，您的邮箱是：${value}`
        })
        set.value = '开启'
        getUserEmail()
      })
    })
    .catch(() => {
      set.value = '关闭'
    })
}
const getUserEmail = () => {
  axios({
    url: `/api/eSettings/${localStorage.getItem('userid')}`,
    method: 'GET',
    headers: {
      token: localStorage.getItem('token')
    }
  }).then((res) => {
    localStorage.removeItem('email')
    localStorage.setItem('email', res.data)
    dispEmail()
  })
}
const dispEmail = () => localStorage.getItem('email') || '未设置邮箱'
onBeforeMount(() => {
  getUserEmail()
  axios({
    url: `/api/settings/${localStorage.getItem('userid')}`,
    method: 'GET',
    headers: {
      token: localStorage.getItem('token')
    }
  }).then((res) => {
    if (res.data !== '') {
      set.value = res.data === 1 ? '开启' : '关闭'
    } else {
      // 弹窗 设置邮箱
      showBox()
    }
  })
})
const cnt = ref(0)
const handleChangeNotification = function (t) {
  cnt.value += 1
  if (cnt.value % 2 === 1) {
    if (t === 0 && localStorage.getItem('email') === '') {
      return
    }
    if (t === 1 && localStorage.getItem('email') === '') {
      showBox()
    }
    if (localStorage.getItem('email') !== '') {
      axios({
        url: `/api/settings`,
        method: 'POST',
        headers: {
          token: localStorage.getItem('token')
        },
        data: {
          userid: localStorage.getItem('userid'),
          notification: t
        }
      }).then(() => {
        ElMessage.success('更改设置成功')
      })
    }
  }
}
</script>

<style scoped>
.container {
  margin-top: 5px;
  padding: 16px;
  border: 1px solid #d3d3d3;
  border-radius: 5px;
}

.container:hover {
  box-shadow: 0 0 10px #808080;
  transition: 0.3s;
}

.inner-flex {
  display: flex;
  justify-content: space-around;
  align-items: center;
}

.msg-text::after {
  content: '(beta)';
}
</style>
