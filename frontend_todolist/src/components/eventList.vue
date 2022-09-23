<template>
    <div class="container ">
        <div class="card">
            <div class="card-body overflow-auto">
                <div class="row list-header-margin">
                    <div class="col-2 justify-left">待办事项</div>
                    <div class="col-1"></div>
                    <div class="col-9">
                        <editAreaVue :addTodo="addTodo" />
                    </div>
                </div>
                <div class="div-aaa overflow-auto">
                    <draggable v-model="content_list.value" item-key="index" animation="100" @start="drag=true" @end="drag=false">
                        <template #item="{element}" >
                            <div class="row margin-1 hover-when-mouse-on" v-if="element.status === 0">
                                <div class="card" >
                                    <div class="card-body">
                                        <div class="row">
                                            <div class="col-1">
                                                <el-button type="success" :icon="Check"
                                                    @click="complete_a_todo(element)" circle />
                                            </div>
                                            <div class="col-10 content-style">{{ element.content }}</div>
                                            <div class="col-1">
                                                <el-popconfirm title="确定要删除吗？请三思而后行！" confirm-button-text="确认"
                                                    cancel-button-text="算了" :icon="Delete" icon-color="red"
                                                    @confirm="delete_a_todo(element)">
                                                    <template #reference>
                                                        <el-button type="danger" :icon="Delete" circle />
                                                    </template>
                                                </el-popconfirm>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </template>
                    </draggable>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { Check, Delete } from '@element-plus/icons-vue'
import { reactive, toRaw, ref } from 'vue'
import editAreaVue from './editArea.vue'
import { countStore } from '@/stores/countStore'
import axios from 'axios'
import draggable from 'vuedraggable'

const content_list = reactive([])
const store = countStore()
const userid = store.userid
const drag = ref(false)

axios({
    method: 'GET',
    url: `http://152.136.154.181:8060/todos/${userid}`
}).then(res => {
    content_list.value = res.data
    content_list.value = content_list.value.reverse()
})

const addTodo = (todoObj) => {
    axios({
        url: 'http://152.136.154.181:8060/add',
        method: 'POST',
        data: ({
            "userid": userid,
            "content": todoObj.content
        })
    }).then(() => {
        content_list.value.unshift(todoObj)
        axios({
            url: `http://152.136.154.181:8060/todos/${userid}`,
            method: 'GET'
        }).then(res => {
            content_list.value = res.data
            content_list.value = content_list.value.reverse()
            store.updateCount()
        })
    })
}

let delete_a_todo = (content) => {
    axios({
        url: 'http://152.136.154.181:8060/delete',
        method: 'POST',
        data: toRaw(content)
    }).then(() => {
        content.status = 1
        store.updateCount()
    })
}

let complete_a_todo = (content) => {
    let content_ori = toRaw(content)
    axios({
        url: 'http://152.136.154.181:8060/update',
        method: 'POST',
        data: ({
            id: content_ori.id,
            userid: content_ori.userid,
            content: content_ori.content,
            status: 1
        })
    }).then(() => {
        content.status = 1
        store.updateCount()
    })
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
</style>