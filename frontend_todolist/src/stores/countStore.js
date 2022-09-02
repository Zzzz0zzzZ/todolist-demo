import { defineStore } from "pinia"
import $ from 'jquery'

export const countStore = defineStore('countStore', {
    state: () => {
        return {
            count_total: 0,         // 帖子总数
            count_finish: 0        //帖子完成数
        }
    },
    actions: {
        updateCount() {
            $.ajax({
                url: "http://152.136.154.181:8060/count_finish",
                type: "GET",
                success(resp) {
                    this.count_finish = parseInt(resp)
                }
            })
            $.ajax({
                url: "http://152.136.154.181:8060/count_total",
                type: "GET",
                success(resp) {
                    this.count_total = parseInt(resp)
                }
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