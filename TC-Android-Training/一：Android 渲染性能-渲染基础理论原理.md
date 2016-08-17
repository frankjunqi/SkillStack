### 一. Android 性能-渲染基础理论原理

0. UI Thread（Main Thread）  
![image](http://hukai.me/images/android_perf_5_threading_main_thread.png)

1. Render Perfermance  
    ![image](http://hukai.me/images/draw_per_16ms.png)
    ![image](http://hukai.me/images/vsync_over_draw.png)
    - 帧率（GPU是一秒绘制操作帧数），屏幕刷新率（硬件决定）；
    - Android 系统每隔16ms发出VSYNC信号；
    - 程序16ms需要执行完成；
    - 丢帧现象（卡顿（渲染性能））:
        - 布局复杂度（HieraychyViewer）,（Layout,Measure,布局深度 >> CPU）,TraceView观察CPU执行；
    - 显示刷新机制:
        > 一般我们在绘制UI的时候，都会采用一种称为“双缓冲”的技术。双缓冲意味着要使用两个缓冲区（SharedBufferStack中），其中一个称为Front Buffer，另外一个称为Back Buffer。UI总是先在Back Buffer中绘制，然后再和Front Buffer交换，渲染到显示设备中。理想情况下，这样一个刷新会在16ms内完成（60FPS），下图就是描述的这样一个刷新过程（Display处理前Front Buffer，CPU、GPU处理Back Buffer。   

        ![image](http://djt.qq.com/upload/public/common/2013/12/images/13162452459.jpg?nothumb=false)    
    
        - 但现实情况并非这么理想。
        1. 时间从0开始，进入第一个16ms：Display显示第0帧，CPU处理完第一帧后，GPU紧接其后处理继续第一帧。三者互不干扰，一切正常。
        2. 时间进入第二个16ms：因为早在上一个16ms时间内，第1帧已经由CPU，GPU处理完毕。故Display可以直接显示第1帧。显示没有问题。但在本16ms期间，CPU和GPU却并未及时去绘制第2帧数据（注意前面的空白区），而是在本周期快结束时，CPU/GPU才去处理第2帧数据。
        3. 时间进入第3个16ms，此时Display应该显示第2帧数据，但由于CPU和GPU还没有处理完第2帧数据，故Display只能继续显示第一帧的数据，结果使得第1帧多画了一次（对应时间段上标注了一个Jank）。
        4. 通过上述分析可知，此处发生Jank的关键问题在于，为何第1个16ms段内，CPU/GPU没有及时处理第2帧数据？原因很简单，CPU可能是在忙别的事情，不知道该到处理UI绘制的时间了。可CPU一旦想起来要去处理第2帧数据，时间又错过了！
     - 为解决这个问题，Android 4.1中引入了VSYNC，这类似于时钟中断。结果如下图所示(每收到VSYNC中断，CPU就开始处理各帧数据。)：    
     ![image](http://djt.qq.com/upload/public/common/2013/12/images/13162610595.jpg?nothumb=false)   
        > 上图中，CPU和GPU处理数据的速度似乎都能在16ms内完成，而且还有时间空余，也就是说，CPU/GPU的FPS（帧率，Frames Per Second）要高于Display的FPS。确实如此。由于CPU/GPU只在收到VSYNC时才开始数据处理，故它们的FPS被拉低到与Display的FPS相同。但这种处理并没有什么问题，因为Android设备的Display FPS一般是60，其对应的显示效果非常平滑。
        
    - 如果CPU/GPU的FPS小于Display的FPS，会是什么情况呢？    
    ![image](http://djt.qq.com/upload/public/common/2013/12/images/13162647832.jpg?nothumb=false)
        1. 在第二个16ms时间段，Display本应显示B帧，但却因为GPU还在处理B帧，导致A帧被重复显示。
        2. 同理，在第二个16ms时间段内，CPU无所事事，因为A Buffer被Display在使用。B Buffer被GPU在使用。注意，一旦过了VSYNC时间点，CPU就不能被触发以处理绘制工作了。
    - 三重缓冲区,不研究，自己学习原理吧：
        > 为什么CPU不能在第二个16ms处开始绘制工作呢？原因就是只有两个Buffer（Android 4.1之前）。如果有第三个Buffer的存在，CPU就能直接使用它，而不至于空闲。出于这一思路就引出了三重缓冲区（Android 4.1）   
        
2. Understanding Overdraw  
    - Tool: Show GPU Overdraw; 
    - Overdraw（过度绘制）描述的是屏幕上的某个像素在同一个帧的时间内被绘制了多次（布局层叠（都设背景））；浪费CPU GPU资源； 
    ![image](http://hukai.me/images/overdraw_hidden_view.png)
    - Overdraw程度：
        - 0X 无过度绘制，白色（像素只画了一次）；
        - 1X 蓝色（像素绘制了两次。大片的蓝色还是可以接受的（若整个窗口是蓝色的，可以摆脱一层））；
        - 2X 淡绿（像素绘制了三次。中等大小的绿色区域是可以接受的但你应该尝试优化、减少它们）；
        - 3X 淡红（像素绘制了四次，小范围可以接受）；
        - 4X 深红（像素绘制了五次或者更多。这是错误的，要修复它们）；  
        ![image](http://hukai.me/images/overdraw_options_view.png)
    - 绘制的前提： UI布局重叠背景色；
    - 如：某个Activity有一个背景，然后里面的Layout又有自己的背景，同时子View又分别有自己的背景。导致OVerdraw。So 必须保证在0x，1X Overdraw；

3. Understanding VSYNC  
    - Refresh Rate：屏幕一秒内刷新屏幕次数，取定硬件固定参数（60Hz）；
    - Frame Rate：代表GPU在一秒内绘制操作的帧数，60fps；
    - Refresh Rate & Frame Rate 匹配（LAG，JANK，HITCHING原因）：
        - Refresh Rate == Frame Rate  
        ![image](http://hukai.me/images/vsync_gpu_hardware.png)
        - Refresh Rate <= Frame Rate  
        ![image](http://hukai.me/images/vsync_gpu_hardware_not_sync.png)
        - Refresh Rate >= Frame Rate  
        ![image](http://hukai.me/images/vsync_gpu_hardware_not_sync2.png)
    - 图像渲染里面的双重与三重缓存机制，[Android 显示原理简介](http://djt.qq.com/article/view/987)；
    - [GPU在移动多媒体处理的运用之GPU基础概念篇](http://gad.qq.com/article/detail/7081230)；
4. Tool: Profile GPU Rendering  
    - Tool: Profile GPU Redering;  
    ![image](http://hukai.me/images/tools_gpu_profile_three_color.png)
        - StatusBar, NavBar, Activity GPU;  
        - 绿色的横线，代表16ms;  
        - Process,Execute,Update:
            - Process（黄色）代表CPU等待GPU处理的时间;
            - Execute（红色）代表OpenGL渲染Display List所需要的时间;
            - Update（蓝色）代表测量绘制Display List的时间;
5. Why 60fps  
    - 人眼与大脑之间的协作无法感知超过60fps的画面更新，这意味着每一帧你只有16ms=1000/60的时间来处理所有的任务；
    - 12fps（手动快速翻动书籍的帧率）；  
    - 24fps（人眼感知的是连续线性的运动，运动模糊，电影胶圈通常使用的帧率，减少费用支出）；  
    - 低于30fps（低于30fps是无法顺畅表现绚丽的画面内容） ；  
    - 60fps（尽量保证） ；  
    - 超过60fps是没有必要的；  
6. Android,UI and the GPU  
    -  Android是如何利用GPU进行画面渲染？  
    ![image](http://hukai.me/images/gpu_rasterization.png)
        - Resterization栅格化是绘制那些Button，Shape，Path，String，Bitmap等组件最基础的操作。它把那些组件拆分到不同的像素上进行显示。这是一个很费时的操作，GPU的引入就是为了加快栅格化的操作。  
    - Resterization栅格化过程：  
    ![image](http://hukai.me/images/gpu_cpu_rasterization.png)  
        - CPU负责：把UI组件计算成Polygons，Texture纹理，然后交给GPU进行栅格化渲染;
        - OpenGL ES：每次从CPU转移到GPU是一件很麻烦的事情，所幸的是OpenGL ES可以把那些需要渲染的纹理Hold在GPU Memory里面，在下次需要渲染的时候直接进行操作。所以如果你**更新**了GPU所hold住的纹理内容，那么之前保存的状态就丢失了。 
        - 常见控件渲染：
            - Bitmaps，Drawables：打包到统一的Texture纹理当中，然后再传递到GPU里面，这意味着每次你需要使用这些资源的时候，都是直接从纹理里面进行获取渲染；
            - 图片： 先经过CPU的计算加载到内存中，然后传递给GPU进行渲染；
            - 文字： 先经过CPU换算成纹理，然后再交给GPU进行渲染，回到CPU绘制单个字符的时候，再重新引用经过GPU渲染的内容；
            - 动画： 更复杂；
    - **每一帧16ms以内处理完所有的CPU与GPU计算，绘制，渲染等等操作**；
7. Invalidations,Layouts,and Performance  
    - Android系统是如何处理UI组件的更新操作：
        - XML布局；
        - CPU计算polygons textures；
        - 产生displaylist，opengles命令；
        - GPU绘制；
    - 一个View的第一次创建 & 之后的更新：
        - View的第一次创建：
            - View CPU计算；  
            - DisplayList；  
            - GPU渲染；  
        - View位置移动（滑动屏幕）：
            - 仅仅需要额外操作一次渲染指令；
        - View中组件属性变化，导致某些组件可见或者不可见（第一次在GPU中的displaylist无效了）：
            - View CPU计算；  
            - DisplayList；  
            - GPU渲染；
    - 如何提高渲染的dispalylist高可命中和使用：  
    ![image](http://hukai.me/images/layout_three_steps.png)  
        - Layout triggered；
        - Invalidated view；
        - **Property** changed；  
    - View的 Visible Invisible GONE:  
        - Visible: 可见；
        - Invisible（不可见）：
            - 但这个View仍然会占用在xml文件中所分配的布局空间，不重新layout ；
            - This view is invisible, but it still takes up space for layout purposes.
        - GONE（不可见）：
            - 但这个View在ViewGroup中不保留位置，会重新layout，不再占用空间，那后面的view就会取代他的位置；
            - This view is invisible, and it doesn't take any space for layout purposes.
        - [（来自stackoverfolow）](http://stackoverflow.com/questions/11556607/android-difference-between-invisible-and-gone)：  
            - I'd like to add to the right and successful answers, that if you initialize a view with visibility as View.GONE, the view could have been not initialized and you will get some random errors.
            - For example if you initialize a layout as View.GONE and then you try to start an animation, from my experience I've got my animation working randomly times. Sometimes yes, sometimes no.
            - So before handling (resizing, move, whatever) a view, you have to init it as View.VISIBLE or View.INVISIBLE to render it (draw it) in the screen, and then handle it.
        - **SO，在GONE Invisible需要慎重使用。**
    - 注意：
        - 任何时候View中的**绘制内容发生变化**时，都会重新执行创建DisplayList，渲染DisplayList，更新到屏幕上等一系列操作；
        - 这个流程的表现性能取决于你的View的复杂程度，View的状态变化以及渲染管道的执行性能；
    - Tool:
        - Monitor GPU Rendering;
        - Show GPU view updates;
        - HierarchyViewer;
        - TraceView;
8. Overdraw,Cliprect,QuickReject  
    - 非可见的UI组件（区分view的visible gone invisible，这个是view的层叠导致的过度绘制）进行绘制更新会导致Overdraw;
        - 系统组件：Android系统会通过避免绘制那些完全不可见的组件来尽量减少Overdraw；
        - 自定义组件： 
            - 通过[canvas.clipRect()](http://developer.android.com/reference/android/graphics/Canvas.html)来帮助系统识别那些可见的区域。这个方法可以指定一块矩形区域，只有在这个区域内才会被绘制，其他的区域会被忽视。这个API可以很好的帮助那些有多组重叠组件的自定义View来控制显示的区域。同时clipRect方法还可以帮助节约CPU与GPU资源，在clipRect区域之外的绘制指令都不会被执行，那些部分内容在矩形区域内的组件，仍然会得到绘制。
            - [clipped代码案例，出品于Udacity](https://github.com/udacity/ud825-render/tree/1.22_card_overlaps_clipped)；
            - 还可以使用[canvas.quickreject()](http://developer.android.com/reference/android/graphics/Canvas.html)来判断是否没和某个矩形相交，从而跳过那些非矩形区域内的绘制操作。
    - 案例：  
    ![image](http://hukai.me/images/android_perf_course_clip_2.png)  
    - [cards上述案例代码，出品于Udacity](https://github.com/udacity/ud825-render/tree/1.21_overlapping_cards)；

### 二. 内存&GC
1. Memory Churn and performance
2. Garbage Collection in Android
3. Performance Cost of Memory Leaks
4. Memory Performance
5. Tool:Memory Monitor

### 三. 电量优化
1. Battery performance
2. Understanding Battery Drain on Android
3. Battary Drain and WakeLocks