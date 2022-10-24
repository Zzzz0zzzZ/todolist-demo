<template>
    <div class="demo-date-picker">
        <div class="block">
            <el-date-picker style="width:105px" v-model="value" type="date" :placeholder="get_placeholder_value()"
                :shortcuts="shortcuts" size="small" value-format="YYYY-MM-DD" />
        </div>
    </div>
</template>
  
<script setup>
import { onUpdated, ref } from 'vue'
import { defineProps } from 'vue'
import { defineEmits } from 'vue'
import axios from 'axios'

const emit = defineEmits(['refresh', 'show_clear_btn', 'hide_clear_btn'])   // 父组件eventList中的方法
const props = defineProps({
    cont: {
        type: Object,
        required: true
    },
})
const value = ref('')   // picker选择的值
const shortcuts = [     // 快捷选择
    {
        text: '今天',
        value: new Date(),
    },
    {
        text: '明天',
        value: () => {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24)
            return date
        },
    },
    {
        text: '一周后',
        value: () => {
            const date = new Date()
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7)
            return date
        },
    }
]

// 未设置：显示[设置]  已设置：显示[更改]   ｜并根据是否设置的状态，决定是否显示[清空]按钮
let get_placeholder_value = () => {
    let placeholder_value = ''
    if (props.cont.deadline === '' || props.cont.deadline === null || props.cont.deadline === undefined) {
        placeholder_value = '设置截至日期'
        emit('hide_clear_btn')
    }
    else {
        placeholder_value = '更改截止日期'
        emit('show_clear_btn')
    }
    return placeholder_value
}

// 持久化 [deadline]
onUpdated(() => {
    if (value.value != '' && value.value != null) {
        axios({
            url: '/api/update',
            method: 'POST',
            data: ({
                id: props.cont.id,
                deadline: value.value
            }),
            headers: ({
                "token": localStorage.getItem("token")
            })
        }).then(() => {
            emit('refresh')
            value.value = null
        })
    }
})
</script>

<style scoped>
.demo-date-picker {
    display: flex;
    width: 100%;
    padding: 0;
    flex-wrap: wrap;
}

.demo-date-picker .block {
    padding: 0px 0;
    text-align: center;
    border-right: solid 1px var(--el-border-color);
    flex: 1;
}

.demo-date-picker .block:last-child {
    border-right: none;
}

.demo-date-picker .demonstration {
    display: block;
    color: var(--el-text-color-secondary);
    font-size: 14px;
    margin-bottom: 20px;
}
</style>
  