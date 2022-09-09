import { defineStore } from "pinia"
import axios from 'axios'
export const countStore = defineStore('countStore', {
    state: () => {
        return {
            count_total: 0,         // 帖子总数
<<<<<<< HEAD
            count_finish: 0,        //帖子完成数
            login_status: false,
            login_retry: false,
            userid: null,
            username:''
=======
            count_finish: 0        //帖子完成数
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
        }
    },
    actions: {
        updateCount() {
            axios({
                method: "GET",
<<<<<<< HEAD
                url: `http://152.136.154.181:8060/count_finish/${this.userid}`,
=======
                url: "http://152.136.154.181:8060/count_finish",
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
            }).then(res => {
                this.count_finish = parseInt(res.data)
            })
            axios({
                method: "GET",
<<<<<<< HEAD
                url: `http://152.136.154.181:8060/count_total/${this.userid}`,
=======
                url: "http://152.136.154.181:8060/count_total",
>>>>>>> 92346e44a1242d4e91f4a0938f50d67ff2487026
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