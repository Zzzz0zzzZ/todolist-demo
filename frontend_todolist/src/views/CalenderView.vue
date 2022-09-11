<template>
    <div class="container">
        <div class="card card-height">
            <div class="card-header">
                <div class="row">
                    <NavBar />
                </div>
            </div>
            <div class="card-body">
                <div class="row title-adj">
                    <div class="rol-8">日历</div>
                    <div class="rol-4"></div>
                </div>
                <div class="row">
                    <div class="col-1"></div>
                    <div class="col-10">
                        <div style="width:600px;margin-top:16px;margin-left:16px">
                            <baidu-calendar @change="change" :date="date" :range="range" />
                        </div>
                    </div>
                    <div class="col-1"></div>
                </div>
            </div>
            <div class="card-footer text-muted">
                <div class="row">
                    <div class="col-4"></div>
                    <div class="col-4">
                        <span>@contributers:&nbsp;&nbsp;&nbsp;</span>
                        <img src="@/assets/user_photo.png" class="adj-footer-img">
                        <a target="_blank" href="https://github.com/Zzzz0zzzZ" class="adj-link-style">Zzzz0zzzZ</a>
                        <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <img src="@/assets/user_photo_2.png" class="adj-footer-img">
                        <a target="_blank" href="https://github.com/teashirtt" class="adj-link-style">teashirtt</a>
                        <div class="col-4"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref } from "vue"
import NavBar from "@/components/navBar.vue"

const date = ref('')                    //设置日期，若不设置，则默认为今天
const range = [2002, 2102]              //设置年份范围 默认[2010,2030]
</script>

<style scoped>
.card-height {
    height: 900px;
}

.title-adj {
    font-size: 50px;
}

.adj-footer-img {
    border-radius: 50%;
    width: 30px;
}

.adj-link-style {
    text-decoration: none;
    color: gray;
}
</style>