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
            <TodayCard :task_done="task_done" :task_all="task_all" @change_td="change_td" @change_ta="change_ta" />
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
import { ref } from 'vue';
import $ from 'jquery';
// @ is an alias to /src



export default {
  name: "HomeView",
  components: {
    TodayCard,
    NavBar,
    EventList
  },
  setup() {
    const task_done = ref(0);
    const task_all = ref(0);

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

    const change_td = () => {
      task_done.value++;
    }

    const change_ta = () => {
      task_done.value--;
    }

    return {
      task_all,
      task_done,
      change_td,
      change_ta

    }
  }
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
