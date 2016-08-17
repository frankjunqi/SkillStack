### 四. Android 性能-TC-Tool（blockcanary）

> BlockCanary是一个Android平台的一个非侵入式的性能监控组件，应用只需要实现一个抽象类，提供一些该组件需要的上下文环境，就可以在平时使用应用的时候检测主线程上的各种卡慢问题，并通过组件提供的各种信息分析出原因并进行修复。


1. [代码来源于，github](https://github.com/baoyongzhang/blockcanary)；
2. [源码分析文档](https://github.com/markzhai/AndroidPerformanceMonitor/blob/master/README_CN.md)；
3. 概要原理：  
![image](https://github.com/markzhai/AndroidPerformanceMonitor/raw/master/art/flow-cn.png)  
4. 目的：
    - 发现主线程（UI Thread）中，原子操作耗时过长的堆栈信息列表。
    - 主线程（UI Thread），允许原子耗时在 1ms~30ms; 理想的状态所有情况都在16ms 以下，在GPU渲染的时候尽可能能命中，达到60fps； 