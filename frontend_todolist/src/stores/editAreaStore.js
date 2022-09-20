import { defineStore } from "pinia"
export const editAreaStore = defineStore('editAreaStore', {
    state: () => {
        return {
            content_storage: '' // 输入内容实时备份值
        }
    },
    actions: {
        store_content(content_changed) {
            this.content_storage = content_changed
        }
    }
})