### 三. Android 性能-TC-Render-Performance

1. TC-Render Performance：  
    1. 在动画和用户交互时避免复杂操作；
        - 动画的每一帧都是一次渲染；
        - 动画出现时UI线程里更应该避免复杂的操作；
    2. Layout：
        - Measurement和Layout是非常复杂的操作,view的层级关系越复杂,处理起来就越耗时；
        - Measurement和layout发生在UI线程(所有需要改动activity里view的操作都在UI线程中进行). 这就意味着如果一个程序正在执行一个流畅的动画的时候被告知需要在UI线程中同时执行layout操作, 结果动画肯定要受罪了.   
        - 如：
            > 假设你的程序可以在13毫秒内绘制完成一个指定的动画,这是在16毫秒的规定范围内的(Google官方推荐每秒60帧的刷新频率).如果这时一个事件触发了需要耗时5毫秒的一个layout动作,那么这个layout操作会在动画的下一帧绘制之前执行,这就会将总绘制耗时增加到18毫秒,结果就是动画效果有一个明显的跳帧； 

            > 为了避免这种情况的发生,layout操作要在动画开始前或动画完成后进行.还有就是,尽量使用不会触发layout操作的动画效果.比如,view的translationX和translationY属性会影响post-layout属性.而LayoutParams的属性又会触发一个layout操作去产生作用,所以类似这种属性的动画效果会影响已经比较合理的ui显示； 

    3. Inflation:  
        - view的inflation过程只能在UI线程完成,如果操作不当会变成一个非常耗时的过程(view的层级关系越深, inflation过程就越耗时)Inflation过程可以通过主动inflate一个view或view树触发,也可以通过启动一个不同的的activity时隐性触发,隐性触发会发生在UI线程中,进而会造成activity在inflation过程中的动画卡顿；
        
            > 为了避免这种情况,应该等待当前的动画结束后再触发view的inflation操作或者activity的启动操作. 还有一种情况就是,为了避免多type的list在滚动时的inflation相关问题,可以考虑预先inflate不同type的view.比如,RecyclerView支持预设一个可以产生不同type的ItemView的RecycledViewPool.  
            
    4. 快速启动（ViewStub）:
        - 使用条件： 如果有一些UI界面并不需要在第一次启动的时候展示,那么就不要初始化它们，用ViewStub 作为sub-hierarchies的临时占位对象,这样随时可以填充为真正的UI元素； 
        - 只要有可能就尽量避免类似解码很大的bitmap这样的耗时操作.尽量避免因为内存分配或垃圾回收引起的内存抖动. 用工具去监控程序的启动时间, 发现并消除遇到的瓶颈；
        
    5. 避免在Application对象中的初始化操作：  
        - 要有**新的进程启动**, Application类就会创建新的对象；
        - Application的初始化是否可以通过单利的形式进行创建；
        - 永远不要在Application对象中触发网络请求.因为Application对象也许会在Service或者BroadcastReceiver启动时被创建;此时触发网络操作会使一段特定频率下请求数据更新的代码变成对服务器的DDoS攻击代码.  
        
    6. 避免**View复杂的层级**关系：
        - 界面层级关系中的view越多, 系统进行一般的操作需要消耗的时间就越长；  
        
            >比如inflation, layout和rendering过程(许多无用的内容占据了很多内存; view本身也很能占据内存,尤其是自定义控件带来的更多数据).要找到最节约资源的方式去组织view中的控件.在某些场景下用自定义view或者自定义layout可以避免复杂的view层级关系.用一个单一的view去画一些文字和icon也许比用一系列组合viewgroup来实现一样的效果更节省资源.在交互界面中如何组合控件有一个准则:如果用户可以和某一UI元素产生交互(比如touch事件,获取focus等),那么这个UI元素应该是一个独立的view而不应该和其他元素组合；  
            
    7. 避免在靠近view层级关系**顶层**的地方使用RelativeLayout：  
        - 使用RelativeLayout非常消耗资源；
        
            > RelativeLayout是一个用起来很方便的控件,因为它允许工程师们用相对布局摆放子控件.在许多情况下,这是个解决问题非常有必要的方案.但是,一定要明白使用RelativeLayout非常消耗资源.因为RelativeLayout会触发两次measurement过程来保证正确的处理了所有子元素的关系.更糟的是,它会和view层级关系中其他的RelativeLayout一起产生更坏的后果.想象一下一个view层级关系的顶部是一个RelativeLayout;这本来就将所有子view的measurement次数变成了原来的两倍.此时如果另外一个RelativeLayout是顶部那个RelativeLayout的子view,那么这时它下面所有子view的measurement次数又变成了原来的两倍,也就是所它下面的所有子view都经历了四次measurement过程； 
            
            > 所以要尽量使用不需要两次measure过程的控件,比如LinearLayout或者自定义layout.如果一定要用相对布局的方案,可以考虑用一个自定义的GridLayout,可以预处理view的相对关系,从而避免了两次measure的问题.
    
    8. 避免在UI线程中的复杂操作: 
        > 拖延UI线程会导致动画和界面绘制过程的滞后,造成用户可以感知到的卡顿.在UI线程(比如onDraw()方法onLayout()方法,或者一些UI线程中被调用的和view展示有关的方法)避免一些众所周知的耗时操作.比如调用webservice或执行其它网络请求(会抛出NetworkOnMainThreadException),或者是访问数据库.相反,应该考虑用Loader或者其它模块异步操作完成后再通知UI线程修改界面.可以用StrictMode模块监控这种问题；
        
        > 不可以在UI线程访问数据库和文件的另外一个重要原因是Android设备通常并不善于处理IO的并发.即使你的程序闲置的时候, 其它的程序也许在高负荷的访问磁盘I/O(比如谷歌商店在更新软件).结果就是有可能会导致ANR发生,或者至少会导致你的程序出现的严重卡顿；
        
        > 总的来说, 只要可以放在异步处理的任务就尽量放在异步处理;UI线程需要做的应该只是和UI相关的核心操作,比如控制界面上元素的属性或者是绘制过程；
    
    9. 把程序的唤醒次数降到最低：
    
        > 广播接收者被用来接受其它程序发来的消息或者事件.但是如果超出实际需要,响应过多的广播会导致程序被频繁的唤醒,从而影响整个系统的性能表现和资源消耗.应该在程序不需要接受某个广播的时候反注册掉该广播接收者.注册广播接收者时也要只选择程序需要监听的Intent.
        
    10. 布局复杂度的优化:
        - 在Android中单独的布局性能：FrameLayout>LinearLayout>RelativeLayout; 
        - 三个标签减少布局的层级结构：
            - include: 用于一些复用性很高的布局文件,比如一个App的标题栏
            - merge：减少View树的层级（注意：merge标签只能作为XML布局的根标签使用）
            - ViewStub：懒加载那些只有在特定情况下才会出现的界面（比如没有网络的时候），性能要高于给View设置visiblity="gone"
    
    11. Overdraw 的优化：
        - 善用merge标签，合并冗余节点；
        - 去掉window的默认背景；
        - 在自定义布局中去掉没必要的背景重叠；
        - 善用9patch来做背景；
            > 必须将图片制作成9patch才行，因为Android2D渲染器只对9patch有这个优化，否则，一张普通的Png，就算你把中间的部分设置成透明，也不会减少这次overDraw；


