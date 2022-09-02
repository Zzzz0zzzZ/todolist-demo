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
                    <template v-for="(content, index) in content_list.value">
                        <div class="row margin-1 hover-when-mouse-on" v-if="content.status === 0" :key="index">
                            <div class="card">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-1">
                                            <el-button type="success" :icon="Check" @click="complete_a_todo(content)"
                                                circle />
                                        </div>
                                        <div class="col-10 content-style">{{ content.content }}</div>
                                        <div class="col-1">
                                            <el-button type="danger" :icon="Delete" @click="delete_a_todo(content)"
                                                circle />
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

<script >
import { Check, Delete, Edit } from '@element-plus/icons-vue';
import { reactive, toRaw } from 'vue';
import $ from 'jquery';
import editAreaVue from './editArea.vue';
import { countStore } from '@/stores/countStore';

export default {
    name: "EventList",
    components: {
        editAreaVue,
    },
    setup() {
        const content_list = reactive([]);
        const store = countStore();
        const addTodo = (todoObj) => {
            $.ajax({
                url: "http://152.136.154.181:8060/add",
                type: "POST",
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify({
                    "userid": 1,
                    "content": todoObj.content
                }),
                success(resp) {
                    console.log(resp);
                    content_list.value.unshift(todoObj)
                    $.ajax({
                        url: "http://152.136.154.181:8060/todos",
                        type: "GET",
                        success(resp) {
                            content_list.value = JSON.parse(resp);
                            content_list.value = content_list.value.reverse();
                            store.updateCount()
                        }
                    });
                }
            })
        }

        $.ajax({
            url: "http://152.136.154.181:8060/todos",
            type: "GET",
            success(resp) {
                content_list.value = JSON.parse(resp);
                content_list.value = content_list.value.reverse();
            }
        });

        let delete_a_todo = (content) => {
            $.ajax({
                url: "http://152.136.154.181:8060/delete",
                type: "POST",
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(toRaw(content)),
                success(resp) {
                    console.log(resp);
                    content.status = 1;
                    store.updateCount()
                }
            })
        }

        let complete_a_todo = (content) => {
            let content_ori = toRaw(content);
            $.ajax({
                url: "http://152.136.154.181:8060/update",
                type: "POST",
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify({
                    id: content_ori.id,
                    userid: content_ori.userid,
                    content: content_ori.content,
                    status: 1
                }),
                success(resp) {
                    console.log(resp);
                    content.status = 1;
                    store.updateCount()
                }
            })
        }

        return {
            store,
            Check,
            Delete,
            Edit,
            delete_a_todo,
            complete_a_todo,
            content_list,
            addTodo,
        }
    }
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
    box-shadow: 0px 0px 5px grey;
    transition: 0.3s;
}

.div-aaa {
    height: 500px;
}
</style>