<template>
  <div class="card card-hover p-3">
    <div>Today</div>
    <div class="text-grey text-sm">{{ today_date }} {{ today_week }}</div>
    <div class="text-center mt-2">
      <el-progress type="dashboard" :percentage="store.getPortion" :status="store.getPortion === 100 ? 'success' : undefined">
        <template #default="{ percentage }">
          <div class="mt-2.5 text-3xl">{{ percentage }}%</div>
          <div class="text-sm">今日待办</div>
        </template>
      </el-progress>
    </div>
    <div>城市：{{ today_weather_city }}</div>
    <div>天气：{{ today_weather_forecast_type }}</div>
    <div>气温：{{ today_weather_forecast_low }} - {{ today_weather_forecast_high }}</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { countStore } from '@/stores/countStore'

const store = countStore()
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
  url: 'https://v0.yiketianqi.com/api?unescape=1&version=v61&appid=65939434&appsecret=tBWLUn2Q'
}).then((res) => {
  today_weather_city.value = res.data.city
  today_weather_forecast_type.value = res.data.wea
  today_weather_forecast_high.value = res.data.tem1 + '℃'
  today_weather_forecast_low.value = res.data.tem2 + '℃'
  today_week.value = res.data.week
})
</script>
