<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a target="_blank" class="navbar-brand " href="https://github.com/Zzzz0zzzZ/todolist-demo">
                <div class="font-title">
                    To-Do List
                </div>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse adj-where" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item ">
                        <img class="img-fluid img-adj" src="../../src/assets/user_photo.png" alt="">
                    </li>
                </ul>
            </div>
        </div>
        <router-link :to="{ path: `/todo/todolist/${userid}` }" class="router-link-active ">
            <div class="font-title hometitle home-style">
                Home
            </div>
        </router-link>
        <span>&nbsp;|&nbsp;&nbsp;&nbsp;&nbsp;</span>
        <div class="userpart">
            <el-dropdown>
                <span class="el-dropdown-link">
                    {{username}}
                    <el-icon class="el-icon--right">
                        <arrow-down />
                    </el-icon>
                </span>
                <template #dropdown>
                    <el-dropdown-menu>
                        <el-dropdown-item @click="dialogVisible=true">更改用户名</el-dropdown-item>
                        <el-dropdown-item @click="change_password">更改密码</el-dropdown-item>
                        <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                    </el-dropdown-menu>
                </template>
            </el-dropdown>
        </div>
        <el-dialog v-model="dialogVisible" title="更改用户名" width="30%" draggable>
            <div class="box">
                <div class="login-container">
                    <el-button :plain="true" v-if="false">error</el-button>
                    <el-form :rules="rules" :model="user" ref="form">
                        <el-form-item prop="new_username">
                            <el-input placeholder="请输入新用户名" :prefix-icon="UserFilled" class="item"
                                v-model="user.new_username" @keyup.enter="change_username" />
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
    </nav>
</template>


<script setup>
import { useRouter } from 'vue-router'
import { ArrowDown, UserFilled } from '@element-plus/icons-vue'
import { countStore } from '@/stores/countStore'
import { ElMessage } from 'element-plus'
import axios from 'axios'
import { reactive, ref } from 'vue'

const user = reactive({
    new_username: ''
})

const rules = reactive({
    new_username: [
        { required: true, message: '请输入新用户名', trigger: 'blur' }
    ]
})

const form = ref('')
const store = countStore()
const username = store.username
const userid = store.userid
const router = useRouter()
const dialogVisible = ref(false)

const logout = () => {
    localStorage.removeItem("token")
    store.username = null
    store.userid = null
    router.push({ name: "login" })
}

const check = () => {
    if (user.new_username.match(/^[ ]*$/)) {
        return false
    } else {
        return true
    }
}

const change_username = () => {
    dialogVisible.value = false
    form.value.validate((valid) => {
        if (valid && check()) {
            axios({
                method: 'post',
                url: 'http://152.136.154.181:8060/change_username',
                data: {
                    "username": username,
                    "new_username": user.new_username
                }
            }).then(res => {
                if (res.data == false) {
                    ElMessage({
                        showClose: true,
                        message: 'Oops, 用户名重复',
                        type: 'error'
                    })
                } else {
                    localStorage.setItem("username", user.new_username)
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

const change_password = () => {
    router.push({ name: "change_password" })
}
</script>

<style scoped>
.home-style {
    text-decoration: underline;
}

.navbar {
    height: 80px;
}

.hometitle {
    cursor: pointer;
    font-size: large;
    margin-right: 12px;
}

.user-photo {
    border-radius: 50%;
    line-height: 50%;
}

.router-link-active {
    text-decoration: none;
    color: black;
}

.img-adj {
    height: 50px;
    border-radius: 50%;
}

.adj-where {
    text-align: right;
}

.font-title {
    font-family: "站酷酷黑 Regular";
    font-weight: bolder;
    src: url("//at.alicdn.com/wf/webfont/thn1lMFJ8K4a/CBopGTQxjP2mk9OlxDMGZ.woff2") format("woff2"),
        url("//at.alicdn.com/wf/webfont/thn1lMFJ8K4a/QP6dA6oMVAoUu_UG5Cc73.woff") format("woff");
    font-display: swap;
}

.font-title:hover {
    text-shadow: 0px 0px 10px grey;
    font-size: larger;
    transition: 0.3s;
}

.example-showcase .el-dropdown-link {
    cursor: pointer;
    color: var(--el-color-primary);
    display: flex;
    align-items: center;
}

.el-dropdown-link {
    font-size: large;
    display: flex;
    height: 10vh;
    justify-content: center;
    align-items: center;
}

.userpart {
    display: flex;
    margin-right: 6px;
    width: auto;
}
</style>