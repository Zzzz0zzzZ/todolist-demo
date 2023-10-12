<template>
  <div class="container card-height-adjust">
    <div class="card card-adjust-size">
      <div class="card-header">
        <div class="row">
          <NavBar />
        </div>
      </div>
      <div class="card-body">
        <div class="row">
          <div class="col-9">
            <EventList />
          </div>
          <div class="col-3">
            <div class="row">
              <TodayCard />
            </div>
            <div class="row">
              <UserSettings />
            </div>
            <div class="row">
              <CheckCalender />
            </div>
            <div class="row">
              <ShowSettings />
            </div>
          </div>
        </div>
      </div>
      <div class="card-footer text-muted">
        <div class="row">
          <div class="col-4"></div>
          <div class="col-4">
            <span>@contributors:&nbsp;&nbsp;&nbsp;</span>
            <img src="../assets/user_photo_1.png" class="adj-footer-img" />
            <a target="_blank" href="https://github.com/Zzzz0zzzZ" class="adj-link-style">Zzzz0zzzZ</a>
            <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <img src="@/assets/user_photo_2.png" class="adj-footer-img" />
            <a target="_blank" href="https://github.com/teashirtt" class="adj-link-style">teashirtt</a>
            <div class="col-4"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import TodayCard from '../components/TodayCard.vue'
import NavBar from '@/components/NavBar.vue'
import EventList from '@/components/EventList.vue'
import CheckCalender from '@/components/CheckCalender.vue'
import { countStore } from '@/stores/countStore'
import { useRoute, useRouter } from 'vue-router'
import UserSettings from '@/components/UserSettings'
import ShowSettings from '@/components/ShowSettings'

const store = countStore()
store.updateCount()
const route = useRoute()
const router = useRouter()
const userid = localStorage.getItem('userid')

if (route.path !== `/todo/todolist/${userid}`) {
  router.push({ name: 'login' })
}
</script>

<style scoped>
.card-height-adjust {
  height: 100vh;
}

.adj-link-style {
  text-decoration: none;
  color: gray;
}

.adj-footer-img {
  border-radius: 50%;
  width: 30px;
}

.div-head {
  background-color: black;
}

.div-footer {
  background-color: black;
}

.card-adjust-size {
  size-adjust: 100%;
  height: 100%;
}

.div-main {
  height: 80%;
  width: 80%;
  display: flex;
  justify-content: center;
}
</style>
