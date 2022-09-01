<template>
    <!-- 要给外层div指定合适的宽度 -->
    <div class="container">
        <div class="card card-height">
            <div class="card-header">
                <div class="row">
                </div>
                <NavBar />
            </div>
            <div class="card-body">
                <div class="row title-adj">
                    <div class="rol-8">日历</div>
                    <div class="rol-4"></div>
                </div>
                <div class="row">
                    <div class="col-1"></div>
                    <div class="col-10">
                        <div style="width:600px;margin-top:16px;margin-left:16px">
                            <baidu-calendar @change="change" :date="date" :range="range" />
                        </div>
                    </div>
                    <div class="col-1"></div>
                </div>


            </div>
        </div>
    </div>

</template>
    <script setup>
import { ref } from "vue";
import NavBar from "@/components/navBar.vue";
//date改变触发事件
const change = (obj) => {
    console.log('obj', obj)
}
const date = ref("") //设置日期，若不设置，则默认为今天
const range = [2002, 2102] //设置年份范围 默认[2010,2030]
</script>
    <style scoped>
    .card-height {
        height: 800px;
    }
    
    .title-adj {
        font-size: 50px;
    }
    </style>