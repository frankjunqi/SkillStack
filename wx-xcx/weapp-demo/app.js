// 创建应用程序对象
App( {
  globalData: {
    windowWidth : 0
  },

  onLaunch() {
    // 应用程序启动时触发一次
    console.log( 'App Launch' );
    wx.getSystemInfo( {
      success: function( res ) {
        //windowWidth : res.windowWidth
        console.log( res.model )
        console.log( res.pixelRatio )
        console.log( res.windowWidth )
        console.log( res.windowHeight )
        console.log( res.language )
        console.log( res.version )
      }
    })
  },

  onShow() {
    // 当应用程序进入前台显示状态时触发
    console.log( 'App Show' )
  },

  onHide() {
    // 当应用程序进入后台状态时触发
    console.log( 'App Hide' )
  }

})
