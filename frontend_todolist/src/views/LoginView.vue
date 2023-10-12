<template>
  <div class="box">
    <div class="login-container">
      <h2 style="color: white">登录</h2>
      <el-form :rules="rules" :model="user" ref="form">
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" :prefix-icon="UserFilled" class="item" v-model="user.username" @keyup.enter="submit" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            :prefix-icon="Lock"
            class="item"
            v-model="user.password"
            show-password
            @paste.capture.prevent="handlePaste"
            @keyup.enter="submit" />
        </el-form-item>
        <el-row v-if="error_submit >= 3">
          <el-col :span="15">
            <el-form-item prop="verifycode">
              <el-input
                placeholder="请输入验证码"
                :prefix-icon="Lock"
                class="item"
                v-model="user.verifycode"
                @paste.capture.prevent="handlePaste"
                @keyup.enter="submit" />
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <img src="/api/getCode" ref="verifycode" @click="refresh" />
          </el-col>
        </el-row>
        <div class="link">
          <router-link :to="{ path: '/todo/register' }" class="link">没有用户名?请注册</router-link>
        </div>
        <el-form-item>
          <el-button type="primary" class="item" @click="submit">登录</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { UserFilled, Lock } from '@element-plus/icons-vue'
import { ElMessage, ElNotification } from 'element-plus'
import axios from 'axios'
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { SHA256 } from '@/utils/sha256'

const user = reactive({
  username: '',
  password: '',
  verifycode: ''
})

const rules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  verifycode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
})

const form = ref('')
const router = useRouter()
const error_submit = ref(0)

const submit = () => {
  form.value.validate((valid) => {
    if (valid) {
      axios({
        method: 'POST',
        url: '/api/login',
        data: {
          username: user.username,
          password: SHA256(user.password),
          error_submit: error_submit.value,
          verifycode: user.verifycode
        }
      }).then((res) => {
        if (res.data.message === 'verifycode_error') {
          ElMessage({
            showClose: true,
            message: '验证码错误',
            type: 'error'
          })
          error_submit.value++
          refresh()
        } else if (res.data.message === 'user_error') {
          ElMessage({
            showClose: true,
            message: 'Oops, 用户名或密码错误',
            type: 'error'
          })
          error_submit.value++
        } else if (res.data.message === 'success') {
          localStorage.setItem('token', res.data.token)
          localStorage.setItem('userid', res.data.userid)
          localStorage.setItem('username', res.data.username)
          router.push({ path: `/todo/todolist/${res.data.userid}` })
          ElNotification({
            title: `welcome ! ${localStorage.getItem('username')}`,
            center: 'true',
            duration: '2500'
          })
        }
      })
    } else {
      if (!valid) {
        if (user.username === '' || user.password === '') {
          ElMessage({
            showClose: true,
            message: '用户名和密码不能为空哦',
            type: 'warning'
          })
        } else {
          ElMessage({
            showClose: true,
            message: '验证码不能为空哦',
            type: 'warning'
          })
        }
      }
      return false
    }
  })
}

const verifycode = ref('')
const refresh = () => {
  verifycode.value.src = '/api/getCode?time' + new Date().getTime()
}
</script>

<style scoped>
.box {
  display: flex;
  height: 100vh;
  justify-content: center;
  align-items: center;
  background-color: mediumslateblue;
}

.login-container {
  width: 300px;
}

.item {
  width: 100%;
  height: 45px;
}

h2 {
  text-align: center;
  margin-bottom: 15px;
}

.link {
  color: indigo;
  text-align: right;
}
</style>
