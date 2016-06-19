## hadoop大数据平台架构与实践
## 一：hadoop概述：
#### 1. 主要课题：
1. 大数据技术的相关概念；
2. hadoop的架构和运行机制；
3. 实战：hadoop的安装和配置；
4. 实战：hadoop开发；

#### 2. 目标：
1. 掌握大数据存储与处理技术的原理（理论知识）；
2. 掌握hadoop的使用和开发能力；

#### 3. 建议：
1. 结合书本；
2. 实践经验；

#### 4. 课程预备知识：
1. linux常用命令；
2. java编程基础；

## 二：hadoop前世今生：
#### 1. 无处不在的大数据:
1. 交通；
2. 金融；
3. 科学数据；
4. 零售数据；
5. 物联网数据；
6. 社交网络数据等；

#### 2. 数据存储&分析:
1. Google大数据技术：
    - MapReduce；
    - BigTable；
    - GFS；
2. 革命性：
    - 成本降低，能用pc机，就不用大型机和高端存储；
    - 软件容错硬件故障视为常态，通过软件保证可靠性；
    - 简化并行分布式计算，无须控制节点同步和数据交换（map & reduce）；
3. 模仿google的开源实现：hadoop

## 三：功能和优势：
1. hadoop是什么：
    - 开源；
    - 分布式存储；
    - 分布式计算平台；
2. hadoop的组成：
    - HDFS：分布式文件系统，存储海量数据；
    - MapReduce：并行处理框架，实现任务分解和调度；
3. hadoop可以做什么：
    - 搭建大型数据仓库，PB级数据的存储、处理、分析、统计等业务
        - 搜索引擎数据分析；
        - 商业智能数据分析；
        - 日志分析；
        - 数据挖掘；
4. hadoop优势：
    - 高扩展；
    - 低成本；
    - 成熟生态圈；hadoop的工具；
        - hive；
        - hbase；
    - 工作：开发hadoop任务 & hadoop的运维；
    
## 四：hadoop生态系统以及版本：
1. hadoop ecosystem map:
    - HDFS
    - MapReduce
    - 开源工具:
        - hive: sql语句；
        - Hbase：
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/2768BADBC29243318DF2837DFF6B69D0)
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/59FEF70BBA874A30ACE485F52D4D8336)
        - zookeeper:
            - 监控hadoop集群中的每一个节点的状态；
            - 维护集群的配置；
            - 维护集群节点的一致性等；
2. hadoop版本：
    - 问下同程公司的hadoop的选择的版本;

## 五：hadoop的安装配置：
1. 准备liunx环境；
    - 租用云主机（阿里云、UnistaStack主机等）Ubantu；
2. jdk；
    - jdk，设置环境变量：
        - apt-get install ***；
        - vim /etc/profile
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/576F61463F194157AA467389BB1AEF4F)
        - source /etc/profile : 生效环境变量；
3. 配置hadoop；
    - 下载安装hadoop：
        - wget命令下载；
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/56103125445D4C36AB971306ECCD174E)
        - 解压缩：tar -zxvf XXX.tar.gz
    - 配置文件，config：
        - hadoop-env.sh：hadoop配置环境文件；
            - JAVA_HOME节点的修改；
            - echo JAVA_HOME :查看JAVA_HOME的环境变量值；
        - core-site.xml
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/C6D7F2347BE34058855554B2BE0A47A9)
        - hdfs-site.xml
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/79E2734820F34607AE477EE6B8163C3F)
        - mapred-site.xml
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/782B695B26AC48639052754BAA793B38)
        - hadoop环境变量配置：
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/7B33C322D037457E85C9500179AED98A)
        - 对hadoop的namenode格式化：hadoop namenode -format
        - hadoop的命令：start-all.sh jps
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/E84E5C0EDF394E0CB7EE3B0793D54DBD)
        ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/CD5772465B4E4C4ABB8231156EBA4166)


## Hadoop组成部分

## 一： 文件系统，HDFS:

#### 1. 文件系统组成，HDFS
![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/6069E558A3F041FCB101EBBAC2C154A9)
- 块（Block）：
    - 固定大小，默认大小64M。
    - HDFS的文件被分成块进行存储。
    - 文件存储处理的逻辑单元（查找、存储的最小单元）；
- 2个节点：
    - NameNode： 管理节点，存放文件元数据，元数据包括：
        - 文件与数据块的**映射表**；
        - 数据块与数据节点的**映射表**；
    - DataNode：是HDFS的工作节点：
        - 存放数据块；
            
#### 2. HDFS中数据管理与容错：
1. 数据块副本：
    - 每个数据块3个副本，分布在两个机架内的3个节点；
    - 在一个机架中存在2份，另一个机架存1份；
    - 在第一个机架中要是DataNode1坏了，可以用DataNode2 3中的数据；当第一个机架坏了，那么就到第二个机架中去取，从而保证数据的可靠性；
    ![iamge](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/1B69A3E5A6044BC3A28169BAA58DC23B)
   
2. 心跳监测：
    - DataNode定期向NameNode发送心跳消息；
    ![iamge](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/845241FBFFF1450082BE53BFC5FED88B)

3. 二级NameNode（SceondaryNameNode）:
    - 二级NameNode（SceondaryNameNode）定期同步**元数据映像文件**（即：主NameNode中的2张隐射表）和修改日志NameNode发生故障时，备胎转正；
    ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/19C25AF986ED4A32A8ACED00A70B428B)
    
#### 3. HDFS中文件的读写操作：
1. HDFS读取文件流程：
    - 客户端发起文件读取请求到NameNode：请求包括java程序，或者一个命令都可以等；
    - NameNode根据映射表进行查询得到元数据给客户端（文件包括的块block，块block在哪些DataNo的中可以找到）；
    - 客户端根据元数据去取DataNode中的block，读取然后组装成文件；
    ![iamge](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/B2267ED08E27483C93F9575AC1AE2758)

2. HDFS写入文件的流程：
    - 客户端发送存请求，然后对文件进行拆分成block，固定大小；
    - NameNode检查可用的DataNodes给到客户端（可用的，当前在线，足够磁盘控件的DataNodes）；
    - 客户端就把拆分的block进行写入；
    - 写入完成一个后，继续**同一个机架**写入第二个，在继续写入到**机架2**中，这就是流水线复制；
    - 都写好后，跟新元数据中的NameNode的的映射表；
    - 然后继续写第二块block，流程一样；
    ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/D6A27B59DA064843BADA33D233292C0F)


#### 4. HDFS的特点
1. 数据龙余数据，用3个备份，来防止硬件的容错；
2. 流式的数据访问；一次写入，多次多取，一旦写入，没办法修改，除非把之前的block删除，重新写入。
3. 存储大文件；如果小文件，NameNode的压力比较大，一个小文件也需要元数据等。

#### 5. 适用性和局限性
1. 一：
    - 适合数据批量读写，吞吐量高；
    - 不适合交互式应用，低延时很难满足；
2. 二：
    - 适合一次写入多次读取，顺序读写；
    - 不支持多用户并发写相同文件；


#### 6. HDFS命令行操作演示：
1. hadoop命令写本地文件到HDFS文件系统中；
    - hadoop fs -put 文件名 /目录
2. hadoop命令从HDFS文件系统中读文件；
    - hadoop fs -get HDFS文件 保存到本地目录/文件名
3. hadoop格式化操作：
    - hadoop namenode -format
4. hadoop命令查看HDFS文件系统的目录：
    - hadoop fs -ls / or hadoop fs -ls /user
5. hadoop命令创建HDFS文件系统的文件夹：
    - hadoop fs -mkdir 文件夹名
6. hadoop命令查看HDFS文件内容：
    - hadoop fs cat 文件夹
7. hadoop查看当前HDFS文件的信息：
    - hadoop dfsadmin -report

## 二：并行计算框架，MapReduce
1. 概念：分而治之，一个大任务分成多个小的子任务（map），并行执行后，合并结果（reduce）。
2. 案例：
    - 1000副扑克牌中少一张，怎么找出；
    ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/D9556EDF30AB4838A8CD538021056C7E)
    - 100GB的网站访问日志文件，找出访问次数最多的IP地址；
    ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/7538891E69184BE7A342CA06053B674C)
3. MapReduce的运行过程：
    - 基本概念：
        1. job & task： 一个job ---> 多个task（maptask & reducetask）
        2. hadoop mapreduce中体系结构的2个节点：
            - jobtracker：jobtracker把job放到候选队列中，并且拿出一个job进行处理。
                - 作业调度（先到先服务、公平调度器）；
                - 分配任务，监控任务执行进度（任务分给tasktracker的时候，tasktracker需要定期收集进度状态）；
                - 监控tasktracker的状态；
            - tasktracker：部署的时候与DataNode同一组位节点；（移动计算代替移动数据）
                - 执行任务；
                - 汇报任务状态；
            ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/9C7A1258EC8A44329722EDBEF237481C)
    - mapreduec具体执行过程：
        - 过程：
        - mapreduce的容错机制（Jvm的错误等）：
            - 重复执行；重复4次，则放弃；
            - 推测执行；当某一个tasktracker执行效率很慢的时候，hadoop中会新开启一个tasktracker做相同的工作，谁先执行完成，就把另一个执行取消。
            ![image](http://note.youdao.com/yws/public/resource/2247d5cf89e05ae767281a5d463dd9e0/D9629B270BE0411B9AD08734C31107D0)

## 三：hadoop的入门的案例
1. WordCount单词计数（hadoop中的hello world程序）：
2. 利用mapreduce进行排序：