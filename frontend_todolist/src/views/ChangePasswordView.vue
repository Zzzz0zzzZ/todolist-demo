<template>
    <div class="box">
        <div class="login-container">
            <el-button :plain="true" v-if="false">error</el-button>
            <h2 style="color:white">更改密码</h2>
            <el-form :rules="rules" :model="user" ref="form">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" :prefix-icon="UserFilled" class="item" v-model="user.username"
                        @keyup.enter="submit" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入原密码" :prefix-icon="Lock" class="item" v-model="user.password"
                        show-password @paste.capture.prevent="handlePaste" @keyup.enter="submit" />
                </el-form-item>
                <el-form-item prop="new_password">
                    <el-input placeholder="请输入更改密码" :prefix-icon="Lock" class="item" v-model="user.new_password"
                        show-password @paste.capture.prevent="handlePaste" @keyup.enter="submit" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请再次输入更改密码" :prefix-icon="Lock" class="item" v-model="new_password_confirm"
                        show-password @paste.capture.prevent="handlePaste" @keyup.enter="submit" />
                </el-form-item>
                <el-form-item>
                    <el-button type="info" class="item" @click="return_home">返回</el-button>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" class="item" @click="submit">更改密码</el-button>
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
import { SHA256 } from '../utils/sha256'

let userid = parseInt(sessionStorage.getItem("userid"))
const user = reactive({
    username: '',
    password: '',
    new_password: ''
})

const new_password_confirm = ref('')
const rules = reactive({
    username: [
        { required: true, message: '请输入用户名', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入原密码', trigger: 'blur' }
    ],
    new_password: [
        { required: true, message: '请输入新密码', trigger: 'blur' }
    ]
})

const check = () => {
    if (user.new_password === user.password) {
        ElMessage({
            showClose: true,
            message: '密码似乎没变哦',
            type: 'warning'
        })
    } else if (new_password_confirm.value !== user.new_password) {
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
const return_home = () => {
    router.push({ path: `/todo/todolist/${userid}` })
}
const form = ref('')

const submit = () => {
    form.value.validate((valid) => {
        if (valid && check()) {
            axios({
                method: 'post',
                url: 'http://152.136.154.181:8060/change_password',
                data: {
                    "username": user.username,
                    "password": SHA256(user.password),
                    "new_password": SHA256(user.new_password)
                }
            }).then((resp) => {
                if (resp.data !== true) {
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
                    sessionStorage.removeItem("islogin")
                    router.push({ name: 'login' })
                }
            })
        } else {
            if (!valid) {
                ElMessage({
                    showClose: true,
                    message: '用户名和密码不能为空哦',
                    type: 'warning',
                })
            }
            return false
        }
    })
}
</script>

<style scoped>
.box {
    display: flex;
    height: 100vh;
    justify-content: center;
    align-items: center;
    background-color: darkslategrey;
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
</style>
