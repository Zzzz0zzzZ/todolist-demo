<template>
    <div class="container ">
        <div class="card">
            <div class="card-body overflow-auto">
                <div class="row list-header-margin">
                    <div class="col-2 justify-left">
                        待办事项
                        <el-dropdown>
                            <span class="el-dropdown-link">
                                
                                <el-icon class="el-icon--right">
                                    <arrow-down />
                                </el-icon>
                            </span>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item @click="sort_by_ddl">按截止时间排序</el-dropdown-item>
                                    <el-dropdown-item @click="sort_by_creattime">按创建时间排序</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                    </div>
                    <div class="col-1">
                    </div>
                    <div class="col-9">
                        <editAreaVue :addTodo="addTodo" />
                    </div>
                </div>
                <div class="div-aaa overflow-auto">
                    <template v-for="(content, index) in content_list.value">
                        <div class="row margin-1 hover-when-mouse-on" v-if="content.status === 0" :key="index">
                            <div class="card" @click="show_date_picker(index)">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-1">
                                            <el-button type="success" :icon="Check" @click="complete_a_todo(content)"
                                                circle />
                                        </div>
                                        <div class="col-7 content-style">{{ content.content }}</div>
                                      <div class="col-3" :style="set_ddl_color(content.deadline)" id="ddl-text">
                                        {{ content.deadline }}{{getDayLeft(content.deadline)}}
                                      </div>
                                        <div class="col-1">
                                            <el-popconfirm title="确定要删除吗？请三思而后行！" confirm-button-text="确认"
                                                cancel-button-text="算了" :icon="Delete" icon-color="red"
                                                @confirm="delete_a_todo(content)">
                                                <template #reference>
                                                    <el-button @click.stop type="danger" :icon="Delete" circle />
                                                </template>
                                            </el-popconfirm>
                                        </div>
                                        <div class="row" v-show="show_picker[index]">
                                            <div class="col-1"></div>
                                            <div class="col-2">
                                                <datePicker :cont="content" @refresh="refresh_list"
                                                    @show_clear_btn="show_clear_btn(index)"
                                                    @hide_clear_btn="hide_clear_btn(index)" />
                                            </div>
                                            <div class="col-6"></div>
                                            <div class="col-2">
                                                <button v-show="show_clr_btn[index]" type="button"
                                                    class="btn btn-light btn-sm" style="color:gray"
                                                    @click="clear_deadline(content)">&nbsp;&nbsp;清空日期&nbsp;&nbsp;</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </template>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { Check, Delete,ArrowDown } from '@element-plus/icons-vue'
import {reactive, ref, toRaw} from 'vue'
import editAreaVue from './editArea.vue'
import { countStore } from '@/stores/countStore'
import axios from 'axios'
import DatePicker from './DatePicker.vue'
import dayjs from 'dayjs'

const content_list = reactive([])
const store = countStore()
const userid = localStorage.getItem("userid")
const show_picker = ref([])             // 是否展开选择框
const latest_pick = ref(0)              // 最后展开的框的下标
const click_complete = ref(false)       // 是否点击[完成]按钮
const show_clr_btn = ref([])            // 是否显示[清空]按钮
const INF_MAX = 0x3f3f3f3f              // 如果没设置截止日期则赋值为无穷大

const day_diff = (ddl) => ddl ? dayjs(ddl).diff(new Date().toISOString().split('T')[0], 'day') : INF_MAX   // 计算参数ddl和当前相差的天数

axios({
    url: `/api/todos/${userid}`,
    method: 'GET',
    headers: ({
        token: localStorage.getItem("token")
    })
}).then(res => {
    content_list.value = res.data
    content_list.value = content_list.value.reverse()
    // 初始默认按照截止日期排序
    sort_by_ddl()
    // 构造数组，每个框分配一个标志，默认不展开[false]，默认不显示[清空]->[false]
    for (let idx = 0; idx < store.getListCount; idx++) {
        show_picker.value.push(false)
        show_clr_btn.value.push(false)
    }
})

// 排序方式-按截止日期排序
const sort_by_ddl = () => {
    content_list.value.sort((a, b) => {
      if (day_diff(a.deadline) === INF_MAX && day_diff(b.deadline) === INF_MAX) return a.id - b.id
      else return day_diff(a.deadline) - day_diff(b.deadline)
    })
}

// 排序方式-按创建日期排序
const sort_by_creattime = () => {
    content_list.value.sort((a, b) => {
        return b.id - a.id
    })
}

const addTodo = (todoObj) => {
    axios({
        url: '/api/add',
        method: 'POST',
        headers: ({
            token: localStorage.getItem("token")
        }),
        data: ({
            userid: userid,
            content: todoObj.content
        })
    }).then(() => {
        content_list.value.unshift(todoObj)
        axios({
            url: `/api/todos/${userid}`,
            method: 'GET',
            headers: ({
                token: localStorage.getItem("token")
            })
        }).then(res => {
            content_list.value = res.data
            content_list.value = content_list.value.reverse()
            // 新增框，默认将原来打开的框关闭
            show_picker.value[latest_pick.value] = false
            show_picker.value.push(false)
            show_clr_btn.value.unshift(false)   // 新增的框初始化没有设置deadline,不显示清空按钮
            store.updateCount()
        })
    })
}

const delete_a_todo = (content) => {
    axios({
        url: '/api/delete',
        method: 'POST',
        data: toRaw(content),
        headers: ({
            token: localStorage.getItem("token")
        })
    }).then(() => {
        content.status = 1
        store.updateCount()
    })
}

const complete_a_todo = (content) => {
    // [完成]按钮点击标志，与框点击事件区分开
    click_complete.value = true
    let content_ori = toRaw(content)
    axios({
        url: '/api/update',
        method: 'POST',
        data: ({
            id: content_ori.id,
            userid: content_ori.userid,
            content: content_ori.content,
            status: 1
        }),
        headers: ({
            token: localStorage.getItem("token")
        })
    }).then(() => {
        content.status = 1
        store.updateCount()
        click_complete.value = false
    })
}

const show_date_picker = (idx) => {
    // 点框而不是点框中的按钮时 --> [避免完成一个事项时弹出选择框]
    if (!click_complete.value) {
        // 如果一直选的是一个，就完成：展示/隐藏
        if (latest_pick.value === idx) {
            show_picker.value[idx] = !show_picker.value[idx]
        }
        // 如果选了另外一个，就完成：隐藏当前选的，展示新的
        else {
            show_picker.value[latest_pick.value] = false
            show_picker.value[idx] = !show_picker.value[idx]
            latest_pick.value = idx
        }
    }
}

// 刷新
const refresh_list = () => {
    axios({
        url: `/api/todos/${userid}`,
        method: 'GET',
        headers: ({
            token: localStorage.getItem("token")
        })
    }).then(res => {
        content_list.value = res.data
        content_list.value = content_list.value.reverse()
        store.updateCount()
    })
}

// 清空deadline
const clear_deadline = (content) => {
    axios({
        url: '/api/update',
        method: 'POST',
        data: ({
            id: content.id,
            userid: userid,
            deadline: ''
        }),
        headers: ({
            token: localStorage.getItem("token")
        })
    }).then(() => {
        refresh_list()
    })
}

// 设置显示[清空]按钮，供子组件<DatePicker />调用
const show_clear_btn = (idx) => {
    show_clr_btn.value[idx] = true
}

// 设置隐藏[清空]按钮，供子组件<DatePicker />调用
const hide_clear_btn = (idx) => {
    show_clr_btn.value[idx] = false
}

const getDayLeft = (ddl) => ddl ? `(剩${dayjs(ddl).diff(new Date().toISOString().split('T')[0], 'day')}天)` : ""

// 动态设置deadline日期颜色
const set_ddl_color = (ddl) => {
    let text_color = ''
    let ddl_on_change_color = localStorage.getItem('ddl') || 3
    if (ddl) {
        // 计算deadline剩余日期
        let todayDate = new Date()
        let today = todayDate.toISOString().split('T')[0]
        let days_left = dayjs(ddl).diff(today, 'day')
        // 根据日期设置字体颜色
        if (days_left > ddl_on_change_color) {
            text_color = 'color: #7ebf50'   // 按钮绿
        }
        else if (days_left >= 0) {
            text_color = 'color: #e47470; text-decoration: underline;'  // 按钮红
        }
        else {
            text_color = 'color: grey; text-decoration: line-through;'      // 灰色
        }
    }
    return text_color
}
</script>

<style scoped>
.list-header-margin {
    margin-bottom: 5px;
}

.wc-bg {
    background-color: lightgreen;
    display: flex;
    justify-content: center;
    border-radius: 30%;
}

.justify-left {
    font-display: left;
    font-weight: bold;
    font-size: larger;
}

.margin-1 {
    margin-top: 10px;
    margin-left: 5px;
    margin-right: 5px;
}

.hover-when-mouse-on:hover {
    border-radius: 8%;
    box-shadow: 0px 0px 5px grey;
    transition: 0.3s;
}

.div-aaa {
    height: 500px;
}

#ddl-text {
  font-size: 16px;
}

.el-dropdown-link {
    font-size: large;
    display: flex;
    height: 3.5vh;
    justify-content: center;
    align-items: center;
}
</style>