<template>
  <div class="card h-screen w-[1500px] mx-auto">
    <navBar />
    <div class="flex h-full m-3 gap-3">
      <EventList class="w-3/4" />
      <div class="flex flex-col w-1/4 gap-2">
        <TodayCard />
        <UserSettings />
        <checkCalender />
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import TodayCard from '@/components/TodayCard.vue'
import navBar from '@/components/navBar.vue'
import EventList from '@/components/EventList.vue'
import checkCalender from '@/components/checkCalender.vue'
import { countStore } from '@/stores/countStore'
import UserSettings from '@/components/UserSettings.vue'
import Footer from '@/components/Footer.vue'

const store = countStore()
store.updateCount()
const route = useRoute()
const router = useRouter()
const userid = localStorage.getItem('userid')

if (route.path !== `/todo/todolist/${userid}`) {
  router.push({ name: 'login' })
}
</script>
