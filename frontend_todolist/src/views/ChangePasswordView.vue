<template>
  <div class="flex items-center justify-center h-screen bg-ChangePasswordView">
    <div class="w-72">
      <div class="text-white text-xl text-center mb-2">更改密码</div>
      <el-form :rules="rules" :model="user" ref="form">
        <el-form-item prop="password">
          <el-input
            placeholder="请输入原密码"
            :prefix-icon="Lock"
            v-model="user.password"
            show-password
            @paste.capture.prevent
            @keyup.enter="submit"
            class="h-11" />
        </el-form-item>
        <el-form-item prop="new_password">
          <el-input
            placeholder="请输入更改密码"
            :prefix-icon="Lock"
            v-model="user.new_password"
            show-password
            @paste.capture.prevent
            @keyup.enter="submit"
            class="h-11" />
        </el-form-item>
        <el-form-item prop="new_password_confirm">
          <el-input
            placeholder="请再次输入更改密码"
            :prefix-icon="Lock"
            v-model="user.new_password_confirm"
            show-password
            @paste.capture.prevent
            @keyup.enter="submit"
            class="h-11" />
        </el-form-item>
        <el-form-item>
          <el-button type="info" class="h-11 w-full" @click="return_home">返回</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" class="h-11 w-full" @click="submit">更改密码</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script setup>
import { Lock } from '@element-plus/icons-vue'
import { reactive, ref } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { SHA256 } from '@/utils/sha256'

const username = localStorage.getItem('username')
const userid = localStorage.getItem('userid')
const user = reactive({
  password: '',
  new_password: '',
  new_password_confirm: ''
})

const rules = reactive({
  password: [{ required: true, message: '请输入原密码', trigger: 'blur' }],
  new_password: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
  new_password_confirm: [{ required: true, message: '请确定新密码', trigger: 'blur' }]
})

const check = () => {
  if (user.new_password === user.password) {
    ElMessage({
      showClose: true,
      message: '密码似乎没变哦',
      type: 'warning'
    })
  } else if (user.new_password_confirm !== user.new_password) {
    ElMessage({
      showClose: true,
      message: '两次密码不一致',
      type: 'error'
    })
    return false
  } else {
    return true
  }
}

const router = useRouter()
const return_home = () => history.back()
const form = ref('')

const submit = () => {
  form.value.validate((valid) => {
    if (valid && check()) {
      axios({
        method: 'POST',
        url: '/api/change_password',
        data: {
          userid: userid,
          username: username,
          password: SHA256(user.password),
          new_password: SHA256(user.new_password)
        },
        headers: {
          token: localStorage.getItem('token')
        }
      }).then((res) => {
        if (res.data !== true) {
          ElMessage({
            showClose: true,
            message: '用户名或密码错误',
            type: 'error'
          })
        } else {
          ElMessage({
            showClose: true,
            message: '更改成功',
            type: 'success'
          })
          router.push({ name: 'login' })
        }
      })
    } else {
      if (!valid) {
        ElMessage({
          showClose: true,
          message: '用户名和密码不能为空哦',
          type: 'warning'
        })
      }
      return false
    }
  })
}
</script>
