<template>
  <div class="container">
    <div class="card">
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
            <TodayCard />
          </div>
        </div>
      </div>
    </div>


  </div>
</template>


<script>
import TodayCard from "../components/todayCard.vue";
import NavBar from "@/components/navBar.vue";
import EventList from "@/components/eventList.vue";

// @ is an alias to /src



export default {
  name: "HomeView",
  components: {
    TodayCard,
    NavBar,
    EventList
  },
}
</script>

<style scoped>
.div-head {
  background-color: black;
}

.div-main {
  background-color: aqua;
}

.div-footer {
  background-color: black;
}
</style>
