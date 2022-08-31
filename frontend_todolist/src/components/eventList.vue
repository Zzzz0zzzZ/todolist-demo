<template>
    <div class="container">
        <div class="card">
            <div class="card-body">
                <div class="row">
                    <div class="col-11 justify-left">待办事项</div>

                    <div class="col-1">
                        <!-- <button type="button" class="btn btn-primary btn-sm">新建</button> -->
                        <el-button type="primary" :icon="Edit" circle />
                    </div>
                </div>
                <template v-for="(content, index) in content_list.value">
                    <div class="row margin-1 hover-when-mouse-on" v-if="content.status === 0" :key="index">
                        <div class="card">
                            <div class="card-body">
                                <div class="row">
                                    <div class="col-1">

                                        <el-button type="success" :icon="Check" circle />

                                    </div>
                                    <div class="col-10">{{ content.content }}</div>
                                    <div class="col-1">
                                        <el-button type="danger" :icon="Delete" @click="delete_a_todo(content)"
                                            circle />
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                </template>
                <div class="row"></div>

            </div>
        </div>
    </div>

</template>



<script >
import { Check, Delete, Edit } from '@element-plus/icons-vue';
import { reactive, toRaw } from 'vue';
import $ from 'jquery';

export default {
    name: "EventList",
    setup() {
        const content_list = reactive({});

        $.ajax({
            url: "http://152.136.154.181:8060/todos",
            type: "GET",
            success(resp) {
                // console.log(resp);
                content_list.value = JSON.parse(resp);
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
                }
            })
        }

        return {
            Check,
            Delete,
            Edit,
            delete_a_todo,
            content_list
        }
    }
}






</script>

<style scoped>
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
</style>