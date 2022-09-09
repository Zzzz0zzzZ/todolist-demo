<template>
    <div class="box">
        <div class="login-container">
            <el-button :plain="true" v-if="false">error</el-button>
            <el-button text v-if="false"></el-button>
            <h2 style="color:white">登录</h2>
            <el-form :rules="rules" :model="user" ref="form">
                <el-form-item prop="username">
                    <el-input placeholder="请输入用户名" :prefix-icon="UserFilled" class="item" v-model="user.username" />
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" :prefix-icon="Lock" class="item" v-model="user.password"
                        show-password @paste.capture.prevent="handlePaste"/>
                </el-form-item>
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
import { ElMessage } from 'element-plus'
import axios from 'axios';
import { reactive, ref, toRaw } from 'vue'
import { useRouter } from 'vue-router'
import { countStore } from '@/stores/countStore'
const user = reactive({
    username: '',
    password: ''
})

const rules = reactive({
    username: [
        { required: true, message: 'Please input Name', trigger: 'blur' }
    ],
    password: [
        { required: true, message: 'Please input Password', trigger: 'blur' }
    ]
})

const form = ref('')
const router = useRouter()
const store = countStore()

// if (store.login_retry === true) {
//     ElMessageBox.alert('请先登录', {
//         callback: () => {
//             ElMessage({
//                 type: 'info',
//                 message: '请先登录',
//             })
//         },
//     })
// }

const submit = () => {
    form.value.validate((valid) => {
        if (valid) {
            axios({
                method: 'post',
                url: 'http://152.136.154.181:8060/login',
                data: toRaw(user)
            }).then((resp) => {
                if (resp.data === null) {
                    ElMessage({
                        showClose: true,
                        message: 'Oops, 用户名或密码错误',
                        type: 'error',
                    })
                } else {
                    store.login_status = true
                    store.userid = resp.data.userid
                    store.username = resp.data.username
                    router.push({
                        name: 'home',
                        params: {
                            userid: resp.data.userid
                        }
                    })
                }
            })
        } else {
            ElMessage({
                showClose: true,
                message: '请求超时',
                type: 'warning',
            })
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
    /* margin-top: px; */
}
</style>