<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid ">
            <a target="_blank" class="navbar-brand " href="https://github.com/Zzzz0zzzZ/todolist-demo">
                <div class="font-title">
                    To-Do List
                </div>
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse adj-where" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item ">
                        <img class="img-fluid img-adj" src="../../src/assets/user_photo.png" alt="">
                    </li>
                </ul>
            </div>
        </div>
        <router-link :to="{ name: 'home' }" class="router-link-active ">
            <div class="font-title aaa home-style">
                Home
            </div>
        </router-link>
    </nav>
</template>

<style scoped>
.home-style {
    text-decoration: underline;
}

.aaa {
    cursor: pointer;
    font-size: large;
    margin-right: 25px;
}

.user-photo {
    border-radius: 50%;
    line-height: 50%;
}

.router-link-active {
    text-decoration: none;
    color: black;
}

.img-adj {
    height: 50px;
    border-radius: 50%;
}

.adj-where {
    text-align: right;
}

.font-title {

    font-family: "站酷酷黑 Regular";
    font-weight: bolder;
    src: url("//at.alicdn.com/wf/webfont/thn1lMFJ8K4a/CBopGTQxjP2mk9OlxDMGZ.woff2") format("woff2"),
        url("//at.alicdn.com/wf/webfont/thn1lMFJ8K4a/QP6dA6oMVAoUu_UG5Cc73.woff") format("woff");
    font-display: swap;
}

.font-title:hover {
    text-shadow: 0px 0px 10px grey;
    /* text-decoration: underline; */
    font-size: larger;
    transition: 0.3s;
}
</style>