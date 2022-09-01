<template>
    <div class="todo-header">
        <!-- 双向绑定 v-model="title",为了获取用户输入的内容，并当用户输入完毕清空input表单内容，@keyup.enter 绑定键盘事件 -->
        <input type="text" placeholder="新建待办事项，按回车键确认" v-model="title" @keyup.enter="add" />
    </div>
</template>
 
<script>
// 引入 nanoid 生成标准化随机的字符串，用于做 Id 
import { nanoid } from 'nanoid'
export default {
    name: 'MyHeader',
    props: ['addTodo'],  //从App组件那接收来的addTodo方法
    data() {
        return {
            title: ''
        }
    },
    methods: {
        add() {
            const todoObj = { id: nanoid(), content: this.title, status: 0 }  //按下回车生成数据
            console.log(todoObj);
            this.addTodo(todoObj)   //把生成的数据作为参数传递给addTodo
            this.title = ''  //清空input表单
        }
    }
}
</script>
 
<style scoped>
/*header*/
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