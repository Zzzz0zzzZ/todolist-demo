<template>
  <div class="card h-screen w-[1500px] mx-auto relative flex flex-col">
    <NavBar class="absolute top-0 h-20 w-full" />
    <div class="flex pt-20 pb-16 m-4 gap-4 h-full">
      <EventList class="w-3/4" />
      <div class="flex flex-col w-1/4 gap-2">
        <TodayCard />
        <UserSettings />
        <ExtraCard />
      </div>
    </div>
    <Footer class="absolute bottom-0 h-16 w-full" />
  </div>
</template>

<script setup>
import { useRoute, useRouter } from 'vue-router'
import TodayCard from '@/components/TodayCard.vue'
import NavBar from '@/components/NavBar.vue'
import EventList from '@/components/EventList.vue'
import ExtraCard from '@/components/ExtraCard.vue'
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
