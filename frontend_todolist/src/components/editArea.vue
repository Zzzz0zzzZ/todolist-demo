<template>
    <div class="input-group flex-nowrap todo-header">
        <input type="text" class="form-control" placeholder="新建待办事项，按回车键确认" aria-describedby="addon-wrapping"
            v-model="content" @keyup.enter="add">
    </div>
</template>

<script setup>
import { ref, defineProps, onUpdated, onMounted } from 'vue'
import { editAreaStore } from '../stores/editAreaStore'

const edStore = editAreaStore()
const content = ref('')
const props = defineProps({
    addTodo: {
        type: Function,
        required: true
    }
})
const add = () => {
    const todoObj = { content: content.value }
    props.addTodo(todoObj)
    content.value = ''
}
// 切换回来/初始化时，更改content值
onMounted(() => {
    content.value = edStore.content_storage
})
// 监听输入框内容，变化时存入editAreaStore.content_storage，实现页面切换后输入框内容仍然保持不变
onUpdated(() => {
    edStore.store_content(content.value)
})
</script>

<style scoped>
.todo-header input {
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 4px 7px;
    margin: 4px;
}

.todo-header input:focus {
    outline: none;
    border-color: rgba(82, 168, 236, 0.8);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
}
</style>