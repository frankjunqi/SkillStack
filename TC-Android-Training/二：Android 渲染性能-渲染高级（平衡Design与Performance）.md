### 二. Android 性能-渲染高级（平衡Design与Performance）

0. UI Thread（Main Thread）  
![image](http://hukai.me/images/android_perf_5_threading_main_thread.png)

1. Rendering Pipeline: Common problems  
    - 渲染依赖的核心组件：
        - CPU：负责Measure Layout Record Excute 计算操作；
        - GPU：负责Rasterzation（栅格化）；
    - Rendering Pipeline（CPU GPU 涉及的性能）：   
    ![image](http://hukai.me/images/android_performance_course_render_problems.jpg)

2. Visualize and Fix Overdraw - Quiz & Solution   
    - 对比案例：  
    ![image](http://hukai.me/images/android_perf_course_overdraw_compare.png)  
    - [上述案例代码，出品于Udacity](https://github.com/udacity/ud825-render/tree/1.11_chat_with_overdraws)；
    - 如何优化上述方案：
        - 移除Window默认的Background；
        - 移除XML布局文件中非必需的Background；
        - 按需显示占位背景图片；
    - 景区案例： 
        - 景区详情页面:  
        ![image](https://github.com/frankjunqi/SkillStack/blob/master/TC-Android-Training/pic/scenery_detail.png?raw=true)   ![image](https://github.com/frankjunqi/SkillStack/blob/master/TC-Android-Training/pic/scenery_detail_performace.png?raw=true)   
        - 景区首页页面：  
        ![image](https://github.com/frankjunqi/SkillStack/blob/master/TC-Android-Training/pic/scenery_mainpage.png?raw=true)   ![image](https://github.com/frankjunqi/SkillStack/blob/master/TC-Android-Training/pic/scenery_mainpage_performace.png?raw=true)
        - 景区订单页面：   
        ![image](https://github.com/frankjunqi/SkillStack/blob/master/TC-Android-Training/pic/scenery_orderdetail.png?raw=true)   ![image](https://github.com/frankjunqi/SkillStack/blob/master/TC-Android-Training/pic/scenery_orderdetail_performance.png?raw=true)

3. Layouts, Invalidations and Perf  
    - XML布局对象-->CPU-->DisplayList-->GPU渲染；
    - View Property属性改变（移动位置）： Execute Display List；  
    ![image](http://hukai.me/images/android_perf_course_displaylist_execute.png)
    - 修改了View中的某些**可见组件**的**内容**:必须重新创建dispalylsit；  
    ![image](http://hukai.me/images/android_perf_course_displaylist_invalidation.png)
    - 案例：  
        > 任何时候View中的**绘制内容**发生**变化**时，都会需要**重新创建DisplayList**，渲染DisplayList，更新到屏幕上等一系列操作。这个流程的表现性能取决于你的View的复杂程度，View的状态变化以及渲染管道的执行性能。举个例子，假设某个Button的大小需要增大到目前的两倍，在增大Button大小之前，需要通过父View重新计算并摆放其他子View的位置。修改View的大小会触发整个HierarcyView的重新计算大小的操作。如果是修改View的位置则会触发HierarchView重新计算其他View的位置。如果布局很复杂，这就会很容易导致严重的性能问题。   

        ![image](http://hukai.me/images/android_perf_course_displaylist_kick_off.png)

4. Tool: Hierarchy Viewer: Walkthrough   
    - 提升布局性能的关键点是尽量保持**布局层级的扁平化**，避免出现重复的嵌套布局;  
    ![image](http://hukai.me/images/android_perf_course_hierarchy_1.png)
    ![image](http://hukai.me/images/android_perf_course_hierarchy_2.png)
    
    - 性能指标说明（红，黄， 绿 ）：  
    ![image](http://hukai.me/images/android_perf_course_hierarchy_3.png)
        - 布局的Measure;
        - 布局的Layout; 
        - 布局的Executive;    
    - [layout案例代码，出自于Udacity](https://github.com/udacity/ud825-render/tree/1.31_comparing_layouts)；

5. Android-monitors-GPU：  
    - Activity出错堆栈信息详解，如何阅读：
    
    - Android-Monitors_GPU：  
        
        ![image](https://github.com/frankjunqi/SkillStack/blob/master/TC-Android-Training/pic/GPU_monitor.png?raw=true)   
        - Android M版本开始，GPU Profiling工具把渲染操作拆解成如下8个详细的步骤：   
        ![image](http://hukai.me/images/android_perf_5_gpu_profiling_8steps.png)
        - 旧版对应的含义：   
            ![image](http://hukai.me/images/android_perf_5_gpu_profiling_3steps.png)
            - 旧版 Process ---> Swap Buffer;
            - 旧版 Excute ---> Command issue;
            - 旧版 Update ---> Draw;
        - 新加的对应含义：
            - Sync & Upload：通常表示的是准备当前界面上有待绘制的图片所耗费的时间，为了减少该段区域的执行时间，我们可以减少屏幕上的图片数量或者是缩小图片本身的大小。（如果大于0.4ms，表示很多bitmap正在发送到GPU）
            - Measure &Layout：这里表示的是布局的onMeasure与onLayout所花费的时间，一旦时间过长，就需要仔细检查自己的布局是不是存在严重的性能问题。
            - Animation：表示的是计算执行动画所需要花费的时间，包含的动画有ObjectAnimator，ViewPropertyAnimator，Transition等等。一旦这里的执行时间过长，就需要检查是不是使用了非官方的动画工具或者是检查动画执行的过程中是不是触发了读写操作等等。（一般小于2ms）   
            - Input Handling：表示的是系统处理输入事件所耗费的时间，粗略等于对于的事件处理方法所执行的时间。一旦执行时间过长，意味着在处理用户的输入事件的地方执行了复杂的操作。（一般要小于2ms）
            - Misc Time/VsyncDelay：如果稍加注意，我们可以在开发应用的Log日志里面看到这样一行提示：I/Choreographer(691): Skipped XXX frames! The application may be doing too much work on its main thread。这意味着我们在主线程执行了太多的任务，导致UI渲染跟不上vSync的信号而出现掉帧的情况。






