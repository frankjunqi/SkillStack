## zookeeper的笔记

### 一：分布式概念
1. 分布式领域CAP理论：
	1. C：consistency（一致性）,数据一致更新，所以数据变动都是同步；
	2. A：availability（可用性），好的响应性能；
	3. P：partition tolerance（分区容错性），可靠性；
2. 分布式定理：
	- 任何分布式系统只可同时满足2点，没法三者兼顾

3. 分布式忠告：
	- 架构师不要将精力浪费在如何设计能满足三者的完美分布式系统，而是应该取舍；
	- 任何的分布式架构都需要根据具体的业务的场景进行算法的选择，即：技术的选型；

### 二：zookeeper基本概念
1. 原理篇:
	1. http://cailin.iteye.com/blog/2014486/

### 二：zoopkeeper分布式锁算法
1. paxos算法：
	1. http://www.jdon.com/artichect/paxos.html

### 三：Consual分布式锁RAFT算法
1. RAFT算法：
	1. http://www.jdon.com/artichect/raft.html