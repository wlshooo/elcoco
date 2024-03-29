const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig( {
  lintOnSave:false,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8080',
        changeOrigin: true,
        pathRewrite: {
          '^/api:': ''
        }
      }
    }
  }
});