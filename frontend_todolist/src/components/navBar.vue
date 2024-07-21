<template>
  <div class="h-24 w-full flex items-center px-3 justify-between bg-bar">
    <div class="flex items-center gap-3">
      <a target="_blank" href="https://github.com/Zzzz0zzzZ/todolist-demo">
        <div class="font-bold text-lg hover:text-xl duration-300">To-Do List</div>
      </a>
      <img class="w-12 h-12 rounded-full hover:rotate-[720deg] duration-500" :src="url" />
    </div>
    <div class="flex items-center">
      <router-link :to="{ path: `/todo/todolist/${userid}` }">
        <div class="font-bold cursor-pointer underline">Home</div>
      </router-link>
      <span class="mx-2.5">|</span>
      <el-dropdown>
        <span class="flex items-center text-lg">
          {{ username }}
          <el-icon class="el-icon--right">
            <arrow-down />
          </el-icon>
        </span>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="to_change_username">更改用户名</el-dropdown-item>
            <el-dropdown-item @click="to_change_password">更改密码</el-dropdown-item>
            <el-dropdown-item @click="to_change_email">更改邮箱</el-dropdown-item>
            <el-dropdown-item @click="profile = true">上传头像</el-dropdown-item>
            <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </div>
  <el-dialog v-model="dialogVisible" title="更改用户名" width="30%" draggable>
    <div class="box">
      <div class="login-container">
        <el-form :rules="rules" :model="user" ref="form">
          <el-form-item prop="new_username">
            <el-input
              placeholder="请输入新用户名"
              :prefix-icon="UserFilled"
              class="item"
              v-model="user.new_username"
              @keyup.enter="change_username" />
          </el-form-item>
        </el-form>
      </div>
    </div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="change_username">更改</el-button>
      </span>
    </template>
  </el-dialog>
  <el-dialog v-model="profile" title="上传头像" width="30%" draggable v-if="uploadview">
    <profilePhoto @change="upload" />
  </el-dialog>
</template>

<script setup>
import { useRouter } from 'vue-router'
import { ArrowDown, UserFilled } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import axios from 'axios'
import { reactive, ref } from 'vue'
import profilePhoto from './ProfilePhoto.vue'

const user = reactive({
  new_username: ''
})

const rules = reactive({
  new_username: [{ required: true, message: '请输入新用户名', trigger: 'blur' }]
})

const form = ref('')
const username = localStorage.getItem('username')
const userid = localStorage.getItem('userid')
const router = useRouter()
const dialogVisible = ref(false)

const logout = () => {
  router.push({ name: 'login' })
}

const check = () => {
  return !user.new_username.match(/^[ ]*$/)
}

const to_change_username = () => {
  user.new_username = ''
  dialogVisible.value = true
}

const change_username = () => {
  dialogVisible.value = false
  form.value.validate((valid) => {
    if (valid && check()) {
      axios({
        method: 'POST',
        url: '/api/change_username',
        data: {
          username: username,
          new_username: user.new_username
        },
        headers: {
          token: localStorage.getItem('token')
        }
      }).then((res) => {
        if (res.data === false) {
          ElMessage({
            showClose: true,
            message: 'Oops, 用户名重复',
            type: 'error'
          })
        } else {
          localStorage.setItem('username', user.new_username)
          router.go(0)
        }
      })
    } else {
      if (!check()) {
        ElMessage({
          showClose: true,
          message: '用户名不能全为空格哦',
          type: 'warning'
        })
      }
      return false
    }
  })
}

const to_change_password = () => {
  router.push({ name: 'change_password' })
}

const to_change_email = () => {
  ElMessageBox.prompt(`更改新邮箱,原邮箱为: ${localStorage.getItem('email') || '未设置'}`, '启用<待办每日提醒服务>', {
    confirmButtonText: '确认修改',
    cancelButtonText: '放弃修改',
    inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
    inputErrorMessage: '不合法的邮箱',
    inputPlaceholder: 'example@email.com'
  }).then(({ value }) => {
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
      localStorage.setItem('email', value)
      ElMessage({
        type: 'success',
        message: `设置成功，您的邮箱是：${value}`
      })
      window.location.reload()
    })
  })
}

const url = ref('')
const get_url = () => {
  axios({
    url: `/api/getphoto/${userid}`,
    method: 'GET',
    responseType: 'blob',
    headers: {
      token: localStorage.getItem('token')
    }
  }).then((res) => {
    if (res.data.size !== 0) {
      let blob = new window.Blob([res.data])
      url.value = window.URL.createObjectURL(blob)
    } else {
      url.value =
        'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fitem%2F201909%2F23%2F20190923182909_LPaCx.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1669194227&t=4a2d6114b8faf2bb5c9c83e060fc61d7'
    }
  })
}

get_url()

const uploadview = ref(true)
const profile = ref(false)
const upload = () => {
  router.go(0)
  get_url()
  ElMessage.success('上传成功')
}
</script>
