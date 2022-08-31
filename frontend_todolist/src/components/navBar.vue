<template>

    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">To-Do List</a>
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
    </nav>
</template>


<script setup></script>

<style scoped>
.user-photo {
    border-radius: 50%;
    line-height: 50%;
}

.img-adj {
    height: 50px;
    border-radius: 50%;
}

.adj-where {
    text-align: right;
}
</style>