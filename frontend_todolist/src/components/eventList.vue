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
                <div class="row margin-1 hover-when-mouse-on" v-for="(content, index) in content_list.value"
                    :key="index">
                    <div class="card">
                        <div class="card-body">
                            <div class="row">
                                <div class="col-1">

                                    <el-button type="success" :icon="Check" circle />

                                </div>
                                <div class="col-10">{{ content.content }}</div>
                                <div class="col-1">
                                    <el-button type="danger" :icon="Delete" @click="delete_a_todo" circle />
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
                <div class="row"></div>

            </div>
        </div>
    </div>

</template>



<script >
import { Check, Delete, Edit } from '@element-plus/icons-vue';
import { reactive, ref } from 'vue';
import $ from 'jquery';
import { computed } from '@vue/reactivity';

export default {
    name: "EventList",
    setup() {
        const content_list = reactive({});

        $.ajax({
            url: "http://152.136.154.181:8060/demo/todos",
            type: "GET",
            success(resp) {
                content_list.value = JSON.parse(resp);

            }
        });
        const content_list_disp = computed((content_list) => {
            content_list.filter((item) => {
                return item.status == 0
            })
        })

        console.log(content_list_disp);


        let delete_a_todo = () => {
            console.log("aaa");
        }

        return {
            Check,
            Delete,
            Edit,
            delete_a_todo,
            content_list,
            content_list_disp
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