import { defineStore } from "pinia";
import $ from 'jquery';

export const countStore = defineStore('countStore', {
    // 相当于 data
    state: () => {
        return {
            count_total: 0,         // 帖子总数
            count_finish: 0    //帖子完成数
        }
    },
    // 相当于 methods
    actions: {
        updateCount() {
            $.ajax({
                url: "http://152.136.154.181:8060/count_finish",
                type: "GET",
                success(resp) {
                    this.count_finish = parseInt(resp)
                    // console.log("updateCount_finish", this.count_finish);
                }
            })
            $.ajax({
                url: "http://152.136.154.181:8060/count_total",
                type: "GET",
                success(resp) {
                    this.count_total = parseInt(resp)
                    // console.log("updateCount_total", this.count_total);
                }
            })
        }
    },
    // 相当于 computed 调用时可直接当作属性
    getters: {
        getPortion(state) {
            // console.log("getPortion_func called", state.count_finish, state.count_total);
            if (state.count_total === 0) {
                return 100;
            }
            else if (state.count_finish === 0) {
                return 0;
            }
            else {
                return parseInt((state.count_finish / state.count_total).toFixed(2) * 100);
            }
        }
    }
})