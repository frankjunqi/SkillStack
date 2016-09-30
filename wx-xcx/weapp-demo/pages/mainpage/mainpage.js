Page( {
    data: {
        // text:"这是一个页面"
        advertismentList: [{ "imageUrl": "","title":"" }],
        loading: true
    },

    onLoad: function( options ) {
        // 页面初始化 options为页面跳转所带来的参数
        var mainpageUrl = "https://raw.githubusercontent.com/frankjunqi/SkillStack/master/wx-xcx/icon/mainpage.json";
        requestData( this, mainpageUrl );

    },
    onReady: function() {
        // 页面渲染完成
    },
    onShow: function() {
        // 页面显示
    },
    onHide: function() {
        // 页面隐藏
    },
    onUnload: function() {
        // 页面关闭
    }
});

/**
 * 请求数据
 * @param that Page的对象，用其进行数据的更新
 * @param targetUrl 请求Url
 */
function requestData( that, targetUrl ) {
    wx.request( {
        url: targetUrl,
        header: {
            "Content-Type": "application/json"
        },
        success: function( res ) {
            //将获得的各种数据写入itemList，用于setData
            console.log( res );
            var itemList = [];
            itemList = res.data.response.body.advertismentList;
            that.setData( {
                advertismentList: itemList,
                loading: false
            });
        }
    });
}