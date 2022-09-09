<template>
    <div class="box">
        <div class="login-container">
            <el-button :plain="true" v-if="false">error</el-button>
            <h2 style="color:white">注册</h2>
            <el-form :rules="rules" :model="user" ref="form">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" :prefix-icon="UserFilled" class="item" v-model="user.username" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" :prefix-icon="Lock" class="item" v-model="user.password"
                        show-password @paste.capture.prevent="handlePaste"/>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请再次输入密码" :prefix-icon="Lock" class="item" v-model="password_confirm"
                        show-password @paste.capture.prevent="handlePaste"/>
                </el-form-item>
                <div class="link">
                    <router-link :to="{ path: '/todo/login' }" class="link">已注册?点此登录</router-link>
                </div>
                <el-form-item>
                    <el-button type="primary" class="item" @click="submit">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { UserFilled, Lock } from '@element-plus/icons-vue'
import { reactive, ref, toRaw } from 'vue'
import axios from 'axios';
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
const user = reactive({
    username: '',
    password: ''
})

const password_confirm = ref('')
const rules = reactive({
    username: [
        { required: true, message: 'Please input Name', trigger: 'blur' }
    ],
    password: [
        { required: true, message: 'Please input Password', trigger: 'blur' }
    ],
    password_confirm: [
        { required: true, message: 'Please input Password', trigger: 'blur' }
    ],
})

const check = () => {
    if (password_confirm.value !== user.password) {
        ElMessage({
            showClose: true,
            message: '两次密码不一致',
            type: 'error',
        })
        return false
    } else {
        return true
    }
}

const form = ref('')
const router = useRouter()
const submit = () => {
    form.value.validate((valid) => {
        if (valid && check()) {
            axios({
                method: 'post',
                url: 'http://152.136.154.181:8060/register',
                data: toRaw(user)
            }).then((resp) => {
                if (resp.data !== true) {
                    ElMessage({
                        showClose: true,
                        message: '用户名太受欢迎了',
                        type: 'error',
                    })
                } else {
                    ElMessage({
                        showClose: true,
                        message: '注册成功',
                        type: 'success',
                    })
                    router.push({
                        name: 'login',
                    })
                }
            })
        } else {
            return false;
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
    background-color: darkolivegreen;
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
    /* margin-top: px; */
}
</style>
