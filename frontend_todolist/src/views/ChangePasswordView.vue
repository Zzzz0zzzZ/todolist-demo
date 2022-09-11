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
                <el-form-item prop="password">
                    <el-input placeholder="请输入更改密码" :prefix-icon="Lock" class="item" v-model="user.new_password"
                        show-password @paste.capture.prevent="handlePaste" @keyup.enter="submit" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入更改密码" :prefix-icon="Lock" class="item" v-model="new_password_confirm"
                        show-password @paste.capture.prevent="handlePaste" @keyup.enter="submit" />
                </el-form-item>
                <div class="link">

                    <router-link :to="{ path: `/todo/todolist/${userid}` }" class="link">返回</router-link>
                </div>
                <el-form-item>
                    <el-button type="primary" class="item" @click="submit" @keyup.enter="submit">更改密码</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup>
import { UserFilled, Lock } from '@element-plus/icons-vue'
import { reactive, ref, toRaw } from 'vue'
import axios from 'axios'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'

let userid = parseInt(sessionStorage.getItem("userid"))
const user = reactive({
    username: '',
    password: '',
    new_password: ''
})

const new_password_confirm = ref('')
const rules = reactive({
    username: [
        { required: true, message: 'Please input Name', trigger: 'blur' }
    ],
    password: [
        { required: true, message: 'Please input Password', trigger: 'blur' }
    ],
    new_password: [
        { required: true, message: 'Please input Password', trigger: 'blur' }
    ],
    new_password_confirm: [
        { required: true, message: 'Please input Password', trigger: 'blur' }
    ],
})

const check = () => {
    if (user.new_password === user.password) {
        ElMessage({
            showClose: true,
            message: '密码似乎没变哦',
            type: 'warning',
        })
    } else if (new_password_confirm.value !== user.new_password) {
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
                url: 'http://152.136.154.181:8060/change_password',
                data: toRaw(user)
            }).then((resp) => {
                if (resp.data !== true) {
                    ElMessage({
                        showClose: true,
                        message: '用户名或密码错误',
                        type: 'error',
                    })
                } else {
                    ElMessage({
                        showClose: true,
                        message: '更改成功',
                        type: 'success',
                    })
                    sessionStorage.removeItem("islogin")
                    router.push({
                        name: 'login',
                    })
                }
            })
        } else {
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
    background-color: saddlebrown;
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
