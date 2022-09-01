<template>
    <div class="container mar">
        <div class="card hover-when-mouse-on">
            <div class="card-body ">
                <div class="row">
                    <div class="col">
                        <router-link :to="{ name: 'calender' }" class="router-link-active">
                            <button type="button" class="btn btn-outline-primary">查看日历</button>
                        </router-link>
                    </div>


                    <div class="col">
                        <a target="_blank" href="https://github.com/Zzzz0zzzZ/todolist-demo" class="router-link-active">
                            <button type="button" class="btn btn-outline-secondary">联系作者</button>
                        </a>
                    </div>

                </div>
            </div>
        </div>
    </div>

</template>

<script setup></script>

<style scoped>
.mar {
    margin-top: 5px;
    padding: 0px;
}

.router-link-active {
    text-decoration: none;
    color: #fff;
}

.hover-when-mouse-on:hover {
    box-shadow: 0px 0px 10px grey;
    transition: 0.3s;
}

a {
    text-decoration: none;
    color: #fff;
}
</style>