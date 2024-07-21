<template>
  <div class="flex items-center justify-center h-screen bg-RegisterView">
    <div class="w-72">
      <div class="text-white text-xl text-center mb-2">注册</div>
      <el-form :rules="rules" :model="user" ref="form">
        <el-form-item prop="username">
          <el-input placeholder="请输入用户名" :prefix-icon="UserFilled" v-model="user.username" @keyup.enter="submit" class="h-11" />
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            placeholder="请输入密码"
            :prefix-icon="Lock"
            v-model="user.password"
            show-password
            @paste.capture.prevent
            @keyup.enter="submit"
            class="h-11" />
        </el-form-item>
        <el-form-item prop="password_confirm">
          <el-input
            placeholder="请再次输入密码"
            :prefix-icon="Lock"
            v-model="user.password_confirm"
            show-password
            @paste.capture.prevent
            @keyup.enter="submit"
            class="h-11" />
        </el-form-item>
        <el-row>
          <el-col :span="15">
            <el-form-item prop="verifycode">
              <el-input
                placeholder="请输入验证码"
                :prefix-icon="Lock"
                v-model="user.verifycode"
                @paste.capture.prevent
                @keyup.enter="submit"
                class="h-11" />
            </el-form-item>
          </el-col>
          <el-col :span="9">
            <img :src="() => '/api/getCode'" ref="verifycode" @click="refresh" />
          </el-col>
        </el-row>
        <div class="text-right mb-1">
          <div @click="onClickAlreadyRegister">已注册?点此登录</div>
        </div>
        <el-form-item>
          <el-button type="primary" class="h-11 w-full" @click="submit">注册</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { UserFilled, Lock } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { SHA256 } from '@/utils/sha256'

const user = reactive({
  username: '',
  password: '',
  password_confirm: '',
  verifycode: ''
})

const rules = reactive({
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  password_confirm: [{ required: true, message: '请确认密码', trigger: 'blur' }],
  verifycode: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
})

const check = () => {
  if (user.password_confirm !== user.password) {
    ElMessage({
      showClose: true,
      message: '两次密码不一致',
      type: 'error'
    })
    return false
  } else if (user.username.match(/^[ ]*$/)) {
    ElMessage({
      showClose: true,
      message: '用户名不能全为为空格哦',
      type: 'error'
    })
    return false
  } else {
    return true
  }
}

const form = ref('')
const router = useRouter()
const onClickAlreadyRegister = () => history.back()
const submit = () => {
  form.value.validate((valid) => {
    if (valid && check()) {
      axios({
        method: 'POST',
        url: '/api/register',
        data: {
          username: user.username,
          password: SHA256(user.password),
          verifycode: user.verifycode
        }
      }).then((res) => {
        if (res.data === 'verifycode_error') {
          ElMessage({
            showClose: true,
            message: '验证码输入错误',
            type: 'error'
          })
          refresh()
        } else if (res.data === 'username_error') {
          ElMessage({
            showClose: true,
            message: '用户名太受欢迎了',
            type: 'error'
          })
        } else if (res.data === 'success') {
          ElMessage({
            showClose: true,
            message: '注册成功',
            type: 'success'
          })
          router.push({ name: 'login' })
        }
      })
    } else {
      if (!valid) {
        if (user.username === '' || user.password === '' || user.password_confirm === '') {
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
// FIXME 修复下
const refresh = () => {
  verifycode.value.src = '/api/getCode?time' + new Date().getTime()
}
</script>
