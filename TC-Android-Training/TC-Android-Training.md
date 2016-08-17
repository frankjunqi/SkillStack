### Android 性能优化指标（平衡Design与Performance）
1. Android渲染机制；
2. 内存&GC；
3. 电量优化；

> 渲染性能从线程：
> 1. UI thread；
> 2. 异步线程；

> 渲染性能从内存：
> 1. 内存有关的的runtime性能，在Android设备上,性能和内存是密不可分的,系统的总内存占用会影响到系统的每个进程的性能表现, 垃圾回收机制也会对runtime的性能表现产生重要影响；
> 2. 与内存无关的性能；

> 一个应用的本质：  
> 数据的产生，数据的消费，数据的流转。  
> 数据的读写，数据的存储，数据的交换。

### 感谢
1. [Android 官方优化中文文献（hukai）](http://hukai.me/blog/categories/android/)；
    - [Android性能优化典范 - 第1季](http://hukai.me/android-performance-patterns/)；
        - [Android性能优化之渲染篇](http://hukai.me/android-performance-render/)；
        - [Android性能优化之运算篇](http://hukai.me/android-performance-compute/);
        - [Android性能优化之内存篇](http://hukai.me/android-performance-memory/)；
        - [Android性能优化之电量篇](http://hukai.me/android-performance-battery/);
    - [Android性能优化典范 - 第2季](http://hukai.me/android-performance-patterns-season-2/)；
    - [Android性能优化典范 - 第3季](http://hukai.me/android-performance-patterns-season-3/)；
    - [Android性能优化典范 - 第4季](http://hukai.me/android-performance-patterns-season-4/)；
    - [Android性能优化典范 - 第5季](http://hukai.me/android-performance-patterns-season-5/)；
    - [Android内存优化之OOM](http://hukai.me/android-performance-oom/);
    - [Android开发最佳实践](http://hukai.me/android-dev-patterns/);
2. Android 官方优化视频（Google出品）：
    - [(中字)Android 性能模式 第一季](http://www.youku.com/playlist_show/id_26009606.html?sf=20300);
    - [(中文)Android 性能模式 第二季](http://www.youku.com/playlist_show/id_26016201.html?sf=10600);
    - [(中文)Android 性能模式 第三季](http://www.youku.com/playlist_show/id_26946826.html?sf=11100);
    - [(中文)Android 性能模式 第四季](http://www.youku.com/playlist_show/id_26946827.html?sf=10700);
    - [(英文)Android 性能模式 第五季](http://www.youku.com/playlist_show/id_26771407.html?sf=20200);
    - [Android Performance Patterns](http://www.youku.com/playlist_show/id_26240424.html?sf=10100);
3. [Android Google开发者订阅号](http://i.youku.com/i/UMjczOTc0NDkzNg==/playlists)；
4. 其他文献：
    - [Android开发, III: 规范与性能](http://www.devtf.cn/?p=768)；
    - [Android的性能优化](https://segmentfault.com/a/1190000005019408)；
    - [Overdraw优化](https://segmentfault.com/a/1190000003693347)；
    - [Android UI性能优化详解](http://mrpeak.cn//android/2016/01/11/android-performance-ui)；
    - [Android应用开发性能优化完全分析](http://blog.csdn.net/yanbober/article/details/48394201)；
    - [Android系统Choreographer机制实现过程](http://www.360doc.com/content/15/0701/19/10366845_481984948.shtml)；
    - [正确使用Android性能分析工具——TraceView](http://blog.jobbole.com/78995/)；
    - [GPU在移动多媒体处理的运用之GPU基础概念篇](http://gad.qq.com/article/detail/7081230)；
    - [Android 显示原理简介](http://djt.qq.com/article/view/987)；
    - [了解Android 4.1，之三：黄油项目 —— 运作机理及新鲜玩意](http://article.yeeyan.org/view/37503/304664)；
