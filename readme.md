# ToDoList

## 项目地址

[To-Do List](http://152.136.154.181:4547/todo/login)

<br/>

## 项目功能

##### 已完成

1. [event_list] 记录待办事项，实现对待办事项的增改删查动能
2. [today_card] 实时获取城市天气信息
3. [calendar] 日历功能
4. [profile_photo] 用户头像上传

##### 待完成

1. 样式美化
2. 用户身份认证

<br/>

## 项目构建

##### 前端

Vue3 + Bootstrap + Element Plus 

##### 后端

Springboot + MyBatis-Plus + MySQL + Redis

<br/>

## 更新日志

> 本项目自 2022/9/20 的提交后新增更新日志

##### 2022/12/08

---

- 修复调用`API`时的`token`无法区分不同用户的`bug`

##### 2022/12/05

---

- 完成`Springboot`整合`Redis`

##### 2022/12/04

---

- 用户头像上传时将会被压缩至`50KB`
- 登录欢迎弹窗持续时间缩短为`2.5s`

##### 2022/10/25

---

- 更换天气数据`API`接口   [天气API接口](http://www.tianqiapi.com/)
- 新增登录成功时的欢迎弹窗

##### 2022/10/24

---

> 1024 程序员节快乐 😀

- 新增用户上传头像功能
- 修复由`url`与`localStorage`数据不同导致的路由跳转`bug`
- 取消修改用户名时将不再保留相关记录

##### 2022/10/23

---

- 新增拦截器，调用`API`时需要使用`token`验证
- 后端依赖升级
- `jwt token`过期时间更新为两天

##### 2022/10/20

---

- 修复"路由跳转历史"冗余的缺陷

##### 2022/10/07

---

- 更改`TodayCard`组件待办比例的逻辑, 优化用户体验

##### 2022/10/03

---

- 完成<到期日>功能

##### 2022/9/24

---

- 修复用户名可以为`空格`的程序缺陷

##### 2022/9/22

---

- 新增更改用户名功能
- 修复由更改`localStorage`所带来的安全漏洞
- 更改密码时不再需要输入用户名