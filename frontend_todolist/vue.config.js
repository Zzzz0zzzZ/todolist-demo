const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      // 配置跨域
      '/api': {
        target: 'http://152.136.154.181:8060',
        pathRewrite: { '^/api': '' },
        ws: true,
        changeOrigin: true
      },
      '/loc':{
        target: 'http://127.0.0.1:8060',
        pathRewrite: { '^/loc': '' },
        ws: true,
        changeOrigin: true
      }
    }
  }
})
