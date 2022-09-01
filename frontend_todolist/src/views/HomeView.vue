<template>

  <div class="container">
    <div class="card card-adjust-size ">
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
              <CheckCalender />
            </div>
          </div>
        </div>
      </div>
      <div class="card-footer text-muted">
        <div class="row">
          <div class="col-4"></div>
          <div class="col-4">
            <span>@contributers:&nbsp;&nbsp;&nbsp;</span>
            <img src="@/assets/user_photo.png" class="adj-footer-img">
            <a target="_blank" href="https://github.com/Zzzz0zzzZ" class="adj-link-style">Zzzz0zzzZ</a>

            <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
            <img src="@/assets/user_photo_2.png" class="adj-footer-img">
            <a target="_blank" href="https://github.com/teashirtt" class="adj-link-style">teashirtt</a>

            <div class="col-4"></div>
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
import CheckCalender from "@/components/checkCalender.vue";


// @ is an alias to /src



export default {
  name: "HomeView",
  components: {
    TodayCard,
    NavBar,
    EventList,
    CheckCalender,
  },

}
</script>

<style scoped>
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
