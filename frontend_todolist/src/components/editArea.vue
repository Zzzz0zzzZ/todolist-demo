<template>
    <div class="todo-header">
        <input type="text" placeholder="新建待办事项，按回车键确认" v-model="content" @keyup.enter="add" />
    </div>
</template>

<script setup>
import { ref, defineProps } from 'vue'

const content = ref('')
const props = defineProps({
    addTodo: {
        type: Object
    }
})
const add = () => {
    const todoObj = { content: content.value }
    props.addTodo(todoObj)
    content.value = ''
}
</script>

<style scoped>
.todo-header input {
    width: 560px;
    height: 28px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
    padding: 4px 7px;
}

.todo-header input:focus {
    outline: none;
    border-color: rgba(82, 168, 236, 0.8);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075), 0 0 8px rgba(82, 168, 236, 0.6);
}
</style>