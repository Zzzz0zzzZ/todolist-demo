import { defineStore } from "pinia"
import axios from 'axios'
export const countStore = defineStore('countStore', {
    state: () => {
        return {
            count_total: 0,         // 帖子总数
            count_finish: 0        //帖子完成数
        }
    },
    actions: {
        updateCount() {
            axios({
                method: "GET",
                url: "http://152.136.154.181:8060/count_finish",
            }).then(res => {
                this.count_finish = parseInt(res.data)
            })
            axios({
                method: "GET",
                url: "http://152.136.154.181:8060/count_total",
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
        }
    }
})