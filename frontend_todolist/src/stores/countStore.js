import { defineStore } from "pinia"
import axios from 'axios'

export const countStore = defineStore('countStore', {
    state: () => {
        return {
            count_total: null,         // 帖子总数
            count_finish: null,        // 帖子完成数
        }
    },
    actions: {
        updateCount() {
            this.userid = localStorage.getItem("userid")
            this.username = localStorage.getItem("username")
            axios({
                method: "GET",
                url: `/api/count_finish/${this.userid}`,
                headers: ({
                    token: localStorage.getItem("token")
                })
            }).then(res => {
                this.count_finish = parseInt(res.data)
            })
            axios({
                method: "GET",
                url: `/api/count_total/${this.userid}`,
                headers: ({
                    token: localStorage.getItem("token")
                })
            }).then(res => {
                this.count_total = parseInt(res.data)
            })
        }
    },
    getters: {
        getPortion(state) {
            if (state.count_total === 0) {
                return 100
            }
            else if (state.count_finish === 0) {
                return 0
            }
            else {
                return parseInt((state.count_finish / state.count_total).toFixed(2) * 100)
            }
        },
        // 获取待办事项总数
        getListCount(state) {
            return state.count_total - state.count_finish
        }
    }
})