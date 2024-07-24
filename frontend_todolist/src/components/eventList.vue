<template>
  <div class="card h-full p-3">
    <div class="flex justify-between mb-2">
      <div class="font-bold text-lg flex align-middle">
        待办事项
        <el-dropdown>
          <span class="text-lg flex items-center justify-center">
            <el-icon class="el-icon--right">
              <arrow-down />
            </el-icon>
          </span>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item @click="sort_by_ddl">按截止时间排序</el-dropdown-item>
              <el-dropdown-item @click="sort_by_create_time">按创建时间排序</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
      <editAreaVue @refresh="refresh_list" :todo_count="toRaw(content_list.value)" />
    </div>
    <div v-for="(content, index) in content_list.value" :key="content + index">
      <div class="row mx-1.5 my-2 card-hover" v-if="content.status === 0">
        <div class="card p-3" @click="show_date_picker(index)">
          <div class="flex">
            <div class="w-1/12">
              <el-button type="success" :icon="Check" @click="complete_a_todo(content)" circle />
            </div>
            <div class="w-7/12">{{ content.content }}</div>
            <div class="w-1/4" :style="set_ddl_color(content.deadline)">{{ content.deadline }}{{ getDayLeft(content.deadline) }}</div>
            <div class="w-1/12">
              <el-popconfirm
                title="确定要删除吗？请三思而后行！"
                confirm-button-text="确认"
                cancel-button-text="算了"
                :icon="Delete"
                icon-color="red"
                @confirm="delete_a_todo(content)">
                <template #reference>
                  <el-button @click.stop type="danger" :icon="Delete" circle />
                </template>
              </el-popconfirm>
            </div>
          </div>
          <div class="flex justify-between" v-show="show_picker[index]">
            <div class="ml-20">
              <datePicker :cont="content" @refresh="refresh_list" @show_clear_btn="show_clear_btn(index)" @hide_clear_btn="hide_clear_btn(index)" />
            </div>
            <div class="mr-48">
              <button v-show="show_clr_btn[index]" type="button" class="btn btn-light btn-sm" style="color: gray" @click="clear_deadline(content)">
                清空日期
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="text-center mt-3 text-grey">------已经到底啦------</div>
  </div>
</template>

<script setup>
import { reactive, ref, toRaw } from 'vue'
import axios from 'axios'
import dayjs from 'dayjs'
import { Check, Delete, ArrowDown } from '@element-plus/icons-vue'
import editAreaVue from './EditArea.vue'
import { countStore } from '@/stores/countStore'
import datePicker from './DatePicker.vue'

const content_list = reactive([])
const store = countStore()
const userid = localStorage.getItem('userid')
const show_picker = ref([]) // 是否展开选择框
const latest_pick = ref(0) // 最后展开的框的下标
const click_complete = ref(false) // 是否点击[完成]按钮
const show_clr_btn = ref([]) // 是否显示[清空]按钮
const INF_MIN = -0x3f3f3f3f // 如果没设置截止日期则赋值为无穷大

const day_diff = (ddl) => (ddl ? dayjs(ddl).diff(new Date().toISOString().split('T')[0], 'day') : INF_MIN) // 计算参数ddl和当前相差的天数

axios({
  url: `/api/todos/${userid}`,
  method: 'GET',
  headers: {
    token: localStorage.getItem('token')
  }
}).then((res) => {
  content_list.value = res.data
  content_list.value = content_list.value.reverse()
  // 初始默认按照截止日期排序
  sort_by_ddl()
  // 构造数组，每个框分配一个标志，默认不展开[false]，默认不显示[清空]->[false]
  for (let idx = 0; idx < store.getListCount; idx++) {
    show_picker.value.push(false)
    show_clr_btn.value.push(false)
  }
})

// 排序方式-按截止日期排序
const sort_by_ddl = () => {
  content_list.value.sort((a, b) => {
    if (day_diff(a.deadline) === INF_MIN && day_diff(b.deadline) === INF_MIN) return a.id - b.id
    else return day_diff(a.deadline) - day_diff(b.deadline)
  })
}

// 排序方式-按创建日期排序
const sort_by_create_time = () => {
  content_list.value.sort((a, b) => {
    return b.id - a.id
  })
}

const delete_a_todo = (content) => {
  axios({
    url: '/api/delete',
    method: 'POST',
    data: toRaw(content),
    headers: {
      token: localStorage.getItem('token')
    }
  }).then(() => {
    content.status = 1
    store.updateCount()
  })
}

const complete_a_todo = (content) => {
  // [完成]按钮点击标志，与框点击事件区分开
  click_complete.value = true
  let content_ori = toRaw(content)
  axios({
    url: '/api/update',
    method: 'POST',
    data: {
      id: content_ori.id,
      userid: content_ori.userid,
      content: content_ori.content,
      status: 1
    },
    headers: {
      token: localStorage.getItem('token')
    }
  }).then(() => {
    content.status = 1
    store.updateCount()
    click_complete.value = false
  })
}

const show_date_picker = (idx) => {
  // 点框而不是点框中的按钮时 --> [避免完成一个事项时弹出选择框]
  if (!click_complete.value) {
    // 如果一直选的是一个，就完成：展示/隐藏
    if (latest_pick.value === idx) {
      show_picker.value[idx] = !show_picker.value[idx]
    }
    // 如果选了另外一个，就完成：隐藏当前选的，展示新的
    else {
      show_picker.value[latest_pick.value] = false
      show_picker.value[idx] = !show_picker.value[idx]
      latest_pick.value = idx
    }
  }
}

// 刷新
const refresh_list = () => {
  axios({
    url: `/api/todos/${userid}`,
    method: 'GET',
    headers: {
      token: localStorage.getItem('token')
    }
  }).then((res) => {
    content_list.value = res.data
    content_list.value = content_list.value.reverse()
    sort_by_ddl()
    store.updateCount()
  })
}

// 清空deadline
const clear_deadline = (content) => {
  axios({
    url: '/api/update',
    method: 'POST',
    data: {
      id: content.id,
      userid: userid,
      deadline: ''
    },
    headers: {
      token: localStorage.getItem('token')
    }
  }).then(() => {
    refresh_list()
  })
}

// 设置显示[清空]按钮，供子组件<DatePicker />调用
const show_clear_btn = (idx) => {
  show_clr_btn.value[idx] = true
}

// 设置隐藏[清空]按钮，供子组件<DatePicker />调用
const hide_clear_btn = (idx) => {
  show_clr_btn.value[idx] = false
}

const getDayLeft = (ddl) => (ddl ? `(剩${dayjs(ddl).diff(new Date().toISOString().split('T')[0], 'day')}天)` : '')

// 动态设置deadline日期颜色
const set_ddl_color = (ddl) => {
  let text_color = ''
  let ddl_on_change_color = localStorage.getItem('ddl') || 3
  if (ddl) {
    // 计算deadline剩余日期
    let todayDate = new Date()
    let today = todayDate.toISOString().split('T')[0]
    let days_left = dayjs(ddl).diff(today, 'day')
    // 根据日期设置字体颜色
    if (days_left > ddl_on_change_color) {
      text_color = 'color: #7ebf50' // 按钮绿
    } else if (days_left >= 0) {
      text_color = 'color: #e47470; text-decoration: underline;' // 按钮红
    } else {
      text_color = 'color: grey; text-decoration: line-through;' // 灰色
    }
  }
  return text_color
}
</script>

<style scoped>
/*@apply hover:shadow-card duration-300 rounded-8*/
/*border-radius: 8%;
  box-shadow: 0px 0px 5px grey;
  transition: 0.3s;*/
</style>
