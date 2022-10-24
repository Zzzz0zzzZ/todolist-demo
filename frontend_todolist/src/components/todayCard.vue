<template>
    <div class="card hover-when-mouse-on">
        <div class="card-body">
            <div class="row">
                <h5 class="card-title">Today</h5>
                <h6 class="card-subtitle mb-2 text-muted">{{ today_date }} {{ today_week }}</h6>
                <div class="today-info-body">
                    <el-progress type="dashboard" :percentage="store.getPortion" v-if="store.getPortion < 100">
                        <template #default="{ percentage }">
                            <span class="percentage-value">{{ percentage }}%</span>
                            <span class="percentage-label">今日待办</span>
                        </template>
                    </el-progress>
                    <el-progress type="dashboard" :percentage="store.getPortion" status="success" v-else>
                        <template #default="{ percentage }">
                            <span class="percentage-value">{{ percentage }}%</span>
                            <span class="percentage-label">今日待办</span>
                        </template>
                    </el-progress>
                </div>
            </div>
            <p class="card-text">城市：{{ today_weather_city }}</p>
            <p class="card-text">天气：{{ today_weather_forecast_type }}</p>
            <p class="card-text">气温：{{ today_weather_forecast_low }} - {{ today_weather_forecast_high }}</p>
        </div>
    </div>
</template>

<script setup>
import { ref } from 'vue'
import { countStore } from '@/stores/countStore'
import axios from 'axios'

let store = countStore()
store.updateCount()

const today_weather_city = ref('')
const today_weather_forecast_type = ref('')
const today_weather_forecast_high = ref('')
const today_weather_forecast_low = ref('')
const today_week = ref('')
const date = new Date()
const today_date = date.getFullYear() + '年' + (date.getMonth() + 1) + '月' + date.getDate() + '日'

axios({
    method: 'GET',
    url: 'https://v0.yiketianqi.com/api?unescape=1&version=v61&appid=43429654&appsecret=qSjImVc6',
}).then(res => {
    today_weather_city.value = res.data.city
    today_weather_forecast_type.value = res.data.wea
    today_weather_forecast_high.value = res.data.tem1 + "℃"
    today_weather_forecast_low.value = res.data.tem2 + "℃"
    today_week.value = res.data.week
})
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