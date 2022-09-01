<template>
    <div class="card hover-when-mouse-on">
        <div class="card-body">
            <div class="row">
                <h5 class="card-title">Today</h5>
                <h6 class="card-subtitle mb-2 text-muted">{{ today_date }} {{ today_week }}</h6>
                <div class="today-info-body">
                    <el-progress type="dashboard" :percentage="todo_portion" v-if="todo_portion < 100">
                        <template #default="{ percentage }">
                            <span class="percentage-value">{{ percentage }}%</span>
                            <span class="percentage-label">今日待办</span>
                        </template>
                    </el-progress>
                    <el-progress type="dashboard" :percentage="todo_portion" status="success" v-else>
                        <template #default="{ percentage }">
                            <span class="percentage-value">{{ percentage }}%</span>
                            <span class="percentage-label">今日待办</span>
                        </template>
                    </el-progress>
                </div>
            </div>
            <p class="card-text">城市：{{ today_weather_city }}</p>
            <p class="card-text">天气：{{ today_weather_forecast.type }}</p>
            <p class="card-text">气温：{{ today_weather_forecast.low }} - {{ today_weather_forecast.high }}</p>
        </div>
    </div>
</template>

<script setup>
import { computed } from '@vue/reactivity';
import $ from 'jquery';
import { onMounted, ref } from 'vue';

const task_done = ref(0);
const task_all = ref(0);

onMounted(() => {

    setInterval(() => {
        $.ajax({
            url: "http://152.136.154.181:8060/count_finish",
            type: "GET",
            success(resp) {
                task_done.value = parseInt(resp)
            }
        })

        $.ajax({
            url: "http://152.136.154.181:8060/count_total",
            type: "GET",
            success(resp) {
                task_all.value = parseInt(resp)
            }
        })
    }, 1000)
})

let todo_portion = computed(() => {
    if (task_all.value === 0) {
        return 100;
    }
    else if (task_done.value === 0) {
        return 0;
    }
    else {
        return parseInt((task_done.value / task_all.value).toFixed(2) * 100);
    }
});

const today_weather_city = ref("");
const today_weather_forecast = ref({});
const today_week = ref("")
const date = new Date();
const today_date = date.getFullYear() + "年" + (date.getMonth() + 1) + "月" + date.getDate() + "日";

$.ajax({
    type: 'GET',
    url: 'http://wthrcdn.etouch.cn/weather_mini?city=北京',
    dataType: 'JSON',
    error: function () {
        alert('网络错误');
    },
    success: function (res) {
        today_weather_city.value = res.data.city;
        today_weather_forecast.value = res.data.forecast[0];
        today_week.value = res.data.forecast[0].date;
        let lenn = today_week.value.length
        today_week.value = today_week.value.substring(lenn - 3, lenn)

    }
});


</script>

<style scoped>
.demo-progress .el-progress--line {
    margin-bottom: 15px;
    width: 350px;
}

.demo-progress .el-progress--circle {
    margin-right: 15px;
}

.today-info-body {
    text-align: center;
}

.hover-when-mouse-on:hover {
    box-shadow: 0px 0px 10px grey;
    transition: 0.3s;
}

.percentage-value {
    display: block;
    margin-top: 10px;
    font-size: 28px;
}

.percentage-label {
    display: block;
    margin-top: 10px;
    font-size: 12px;
}

.demo-progress .el-progress--line {
    margin-bottom: 15px;
    width: 350px;
}

.demo-progress .el-progress--circle {
    margin-right: 15px;
}

.router-link-active {
    text-decoration: none;
    color: #fff;
}

a {
    text-decoration: none;
    color: #fff;
}
</style>