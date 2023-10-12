<template>
  <div class="box">
    <el-upload
      class="avatar-uploader"
      :show-file-list="false"
      action="/api/setphoto"
      :on-success="handleAvatarSuccess"
      :headers="{ token: token }"
      :before-upload="beforeAvatarUpload"
      :auto-upload="false"
      ref="uploadRef"
      :on-change="changeAvatar"
      :data="{ userid: userid }">
      <img v-if="imageUrl" :src="imageUrl" class="avatar" />
      <el-icon v-else class="avatar-uploader-icon">
        <Plus />
      </el-icon>
    </el-upload>
    <br />
    <el-button type="primary" plain @click="submitUpload">上传</el-button>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import * as imageConversion from 'image-conversion'

const imageUrl = ref('')
const token = localStorage.getItem('token')
const uploadRef = ref()
const userid = localStorage.getItem('userid')
const emit = defineEmits(['change'])

const handleAvatarSuccess = () => {
  emit('change')
}

const beforeAvatarUpload = (rawFile) => {
  if (rawFile.type !== 'image/jpeg') {
    ElMessage.error('Avatar picture must be JPG format!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 1) {
    ElMessage.error('Avatar picture size can not exceed 1MB!')
    return false
  } else if (rawFile.size / 1024 / 1024 > 0.05) {
    // 图像压缩至 50KB
    return new Promise((resolve) => {
      imageConversion.compressAccurately(rawFile, 50).then((res) => {
        resolve(res)
      })
    })
  }
  return true
}

const changeAvatar = (uploadFile) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw)
}

const submitUpload = () => {
  uploadRef.value.submit()
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.box {
  text-align: center;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
