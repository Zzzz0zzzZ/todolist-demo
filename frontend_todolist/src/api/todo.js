import axios from "axios";

// 添加待办
export function addTodo(tcontent, deadline) {
    return axios({
        url: '/api/add',
        method: 'POST',
        headers: ({
            token: localStorage.getItem("token")
        }),
        data: ({
            userid: localStorage.getItem("userid"),
            content: tcontent,
            deadline: deadline
        })
    })
}

// 更新待办日期
export function updateTodoDate(id, deadline) {
    return axios({
        url: '/api/update',
        method: 'POST',
        data: ({
            id: id,
            userid: localStorage.getItem("userid"),
            deadline: deadline
        }),
        headers: ({
            token: localStorage.getItem("token")
        })
    })
}
// 获取所有待办
export function getAllTodos() {
    return axios({
        url: `/api/todos/${localStorage.getItem("userid")}`,
        method: 'GET',
        headers: ({
            token: localStorage.getItem("token")
        })
    })
}


