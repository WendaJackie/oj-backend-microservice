# OJ在线判题系统

## 温习

温故而知新，首先先让我们来温习几个层的作用

我们在做这个项目之前，肯定要先设置数据库表，分别有user、question、questionSubmit这三个不同的表。

### 层次结构

然后根据表来一一设计我们的层次结构。

首先是dao层，dao层是数据层，直接进行数据库的读写操作，返回数据对象DO，DO与数据库中的表是一一对应的。

然后是service层，为业务层，用来实现业务逻辑。能调用dao层或者service层，返回数据对象DO或者业务对象BO，BO通常由DO转化、整合而来，可以包含多个DO的属性，也可以是只包含一个DO的部分属性。通常为了简便，如果无需转化，service也可以直接返回DO。外部调用（HTTP、RPC）方法也在这一层，对于外部调用来说，service一般会将外部调用返回的DTO转化为BO。

controller层为控制层，主要处理外部请求，调用service层，将service层返回的的BO/DO转化为DTO/VO并封装成统一返回对象返回给调用方。如果返回数据用于前端模版渲染则返回VO，否则一般返回DTO。不论是DTO还是VO，一般都会对BO/DO中的数据进行一些转化和整合，比如将gender属性中的0转化“男”，1转化为“女”等。

### RestController

RestController的作用相当于Controller加ResponseBody共同作用的结果，但采用RestController请求方式一般会采用Restful风格的形式。

Controller的作用：声明该类是Controller层的Bean，将该类声明进入Spring容器中进行管理

ResponseBody的作用：表明该类的所有方法的返回值都直接进行提交而不经过视图解析器，且返回值的数据自动封装为json的数据格式

RestController的作用：包含上面两个的作用，且支持Restful风格的数据提交方式

### Restful风格：

get：获取数据时用的请求方式

post：增加数据时的请求方式（create）

put：更新数据时的请求方式（update）

delete：删除数据时的请求方式




## 项目启动的前置条件

### redis

C:\Users\zwd>redis-cli
127.0.0.1:6379> keys
(error) ERR wrong number of arguments for 'keys' command
127.0.0.1:6379> select 1
OK
127.0.0.1:6379[1]> keys *

### Nacos

D:\software\nacos\bin>startup.cmd -m standalone



## Lab 1（上）

前端初始化环境、版本的折磨

node 和 npm 版本要一致才行

02：13：59

在main.ts中要加入

createApp(App).use(ArcoVue).use(store).use(router).mount("#app");

use(ArcoVue)主要是这个，这个不use就没有用网页的格式



## Lab1（下）

### 创建了前端的github库和后端的github库

### 分别把它们push上去

（1）从代码库下载 springboot-init 模板（已经在本地的话直接复制）

（2）ctrl+shift+R全局替换 springboot-init 为项目名（yuoj-backend）

（3）全局替换springbootinit 包名为新的包名（yuoj）

（4）修改 springbootinit 文件夹的名称为新的包名对应的名称（yuoj）

（5）本地新建数据库，直接执行 sql/create_table.sql 脚本，修改库名为 yuoj，执行即可

（6）改 application.yml 配置，修改 MySQL 数据库的连接库名、账号密码，端口号（8121）



### 后端模块中的各个栏目的作用：

（1）先阅读 README.md

（2）sql/create_table.sql 定义了数据库的初始化建库建表语句

（3）sql/post_es_mapping.json 帖子表在 ES 中的建表语句

（4）aop：用于全局权限校验、全局日志记录

（5）common：万用的类，比如通用响应类

（6）config：用于接收 application.yml 中的参数，初始化一些客户端的配置类（比如对象存储客户端）

（7）constant：定义常量

（8）controller：接受请求，操作数据库，SpringMVC经典的类

（9）esdao：类似 mybatis 的 mapper，用于操作 ES

（10）exception：异常处理相关，定义了全局异常处理器

（11）job：任务相关（定时任务、单次任务）

（12）manager：服务层（一般是定义一些公用的服务、对接第三方 API 等）

（13）mapper：mybatis 的数据访问层，用于操作数据库

（14）model：数据模型、实体类、包装类、枚举值

（15）service：服务层，用于编写业务逻辑

（16）utils：工具类，各种各样公用的方法

（17）wxmp：公众号相关的包

（18）test：单元测试

（19）MainApplication：项目启动入口

（20）Dockerfile：用于构建 Docker 镜像



02:14:31

02:28:00



### 全局权限管理优化

（1）新建 access\index.ts 文件，把原有的路由拦截、权限校验逻辑放在独立的文件中。

优势：只要不引入、就不会开启、不会对项目有影响

（2）编写权限管理和自动登录逻辑



## Lab2

创建数据库表的时候：

judgeConfig 判题配置（json 对象）：

●时间限制 timeLimit

●内存限制 memoryLimit

为什么存json对象，因为只需要改变对象内部的字段，而不需要修改数据库表。



### 后端开发流程

（1）根据功能设计库表

（2）自动生成对数据库基本的增删改查（mapper 和 service 层的基本功能）

（3）编写 Controller 层，实现基本的增删改查和权限校验（复制粘贴）

（4）去根据业务定制开发新的功能 / 编写新的代码



小知识：什么情况下要加业务前缀？什么情况下不加？ JudgeCase 或 QuestionJudgeCase ？
**加业务前缀的好处，防止多个表都有类似的类，产生冲突；不加的前提，因为可能这个类是多个业务之间共享的，能够复用的。**



下载一款idea插件，可以自动根据json生成对象的实体类



**定义 VO 类：作用是专门给前端返回对象，可以节约网络传输大小、或者过滤字段（脱敏）、保证安全性。**



在写Question的校验方法时，直接可以使用question.allget，可以得到实体类的全部属性，然后一个一个去考虑。



定义对象QuestionSubmitQueryRequest中的状态Status时候，其是一个数字，使用包装类Integer而不是Int：

**因为前端不传的时候，没必要给它一个默认值0。就可以使用包装类。**





## Lab3

本期是纯前端页面开发

index.ts好像有问题，对照源码看看，因为视频里可以不登录直接访问主页，我这个只要进入localhost:8080就默认进入了登录页面

已经改变了，可以进入主页了，改变方式是在@/src/access中的index.ts文件中，把const loginUser变成了let loginUser，随后去动态获取登录的用户信息。

01：08：05 根源，直接卡死了，原来是没用toRaw();

01：19：29，改了HomeView的名称，但是前端还是会卡死，一直不出界面，查询看看解决办法 = > 事实证明是后端登录的时候卡住了，一直报没有登陆的异常，把之前按照源码修改的登陆逻辑复原，就跑通了，代码编辑器也能正常使用。



小知识，在JetBrains全家桶可以自定义模板



03：18：05 卡在这里，先把后端的代码再校验一下



## Lab4

一开始如果代码下载下来，遇到一大堆报红，大概率就是Windows和Linux系统的prettier插件不兼容，出现的问题，看视频的前3分钟的操作，进行处理。

然后再重新安装一下依赖。



代码沙箱判题服务的前置任务

（需要使用虚拟机）

以下是题目的示例：（MarkDown格式）

## Description

Calculate a+b

## Input

Two integer a,b (0<=a,b<=10)

## Output

Output a+b

## Sample Input

```
1 2
```

## Sample Output

```
3
```

## Hint

Q: Where are the input and the output?

A: Your program shall always read input from stdin (Standard Input) and write output to stdout (Standard Output). For example, you can use 'scanf' in C or 'cin' in C++ to read from stdin, and use 'printf' in C or 'cout' in C++ to write to stdout.

You shall not output any extra data to standard output other than that required by the problem, otherwise you will get a "Wrong Answer".

User programs are not allowed to open and read from/write to files. You will get a "Runtime Error" or a "Wrong Answer"if you try to do so.



在学习的视频里，MarkDown的语法格式里：##（两个井号键） 就是二级段落 即ctrl + 2；



### 架构师设计：静态工厂

### 写了一个配置，代理增强，根据参数去动态生成沙箱



```java
List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
```

查看一下stream流和lambda表达式的用法



```java
@Resource
@Lazy
private JudgeService judgeService;
```

循环依赖，循环调用，让它懒加载一下，加一个@Lazy的注解



```java
CompletableFuture.runAsync(() -> {
    judgeService.doJudge(questionSubmitId);
});
```

查一下这个代码，是干什么的



担心现在报bug了，我们没有在数据库中得到status中的结果

我从02：48：04开始排查：

一直到03：20没有发现异常和错误，没有错误，为啥拿不到JudgeInfo？

```axios
openapi --input http://localhost:8121/api/v2/api-docs --output ./generated --client axios
```

### 注意：

每次重新执行上面的代码生成前端调用的service后，都要去OpenAPI.tx文件中去把WITH_CREDENTIALS改成true，不然传输会有问题。

后端看到了02：16：14

重新排查前端 => 也没有问题！

在测试类里面，输入example，没反应，没有调用示例代码沙箱

```java
"${codesandbox.value:example}"
```

是value不是type；



最终 => 晚上7点半：看到02:56:52处，终于排查出了问题：

在JudgeServiceImpl中，传入：questionSubmitId，而不是之前的questionId

```java
// 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行，也能让用户即时看到状态
QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
questionSubmitUpdate.setId(questionSubmitId);
```

就改这一个，终于得出了数据库中的JudgeInfo的信息，改BUG结束！



## Lab5 代码沙箱原生实现

新建了一个SpringBoot项目

核心实现思路：用程序代替人工，用程序来操作命令行，去编译执行代码
核心依赖：Java 进程类 Process 

（1）把用户的代码保存为文件
（2）编译代码，得到 class 文件
（3）执行代码，得到输出结果
（4）收集整理输出结果
（5）文件清理，释放空间
（6）错误处理，提升程序健壮性

### 小知识

### cgroup

 什么是 cgroup？ 

​	cgroup 是 Linux 内核提供的一种机制，可以用来限制进程组（包括子进程）的资源使用，例如内存、CPU、磁盘 I/O 等。通过将 Java 进程放置在特定的 cgroup 中，你可以实现限制其使用的内存和 CPU 数。

 小知识 - 常用 JVM 启动参数 

1内存相关参数： 

○ -Xms: 设置 JVM 的初始堆内存大小。

 ○ -Xmx: 设置 JVM 的最大堆内存大小。 

○ -Xss: 设置线程的栈大小。 

○ -XX:MaxMetaspaceSize: 设置 Metaspace（元空间）的最大大小。

○ -XX:MaxDirectMemorySize: 设置直接内存（Direct Memory）的最大大小。

2垃圾回收相关参数： 

○ -XX:+UseSerialGC: 使用串行垃圾回收器。

 ○ -XX:+UseParallelGC: 使用并行垃圾回收器。 

○ -XX:+UseConcMarkSweepGC: 使用 CMS 垃圾回收器。 

○ -XX:+UseG1GC: 使用 G1 垃圾回收器。

3线程相关参数：

 ○ -XX:ParallelGCThreads: 设置并行垃圾回收的线程数。 

○ -XX:ConcGCThreads: 设置并发垃圾回收的线程数。

 ○ -XX:ThreadStackSize: 设置线程的栈大小。

4JIT 编译器相关参数： 

○ -XX:TieredStopAtLevel: 设置 JIT 编译器停止编译的层次。

5其他资源限制参数： 

○ -XX:MaxRAM: 设置 JVM 使用的最大内存。


正则表达式，contend，布隆过滤器



### 安全管理器

安全管理器优点：

1）权限控制很灵活

2）实现简单

安全管理器缺点：

1）如果要做比较严格的权限限制，需要自己去判断哪些文件、包名需要允许读写。粒度太细了，难以精细化控制。

2）安全管理器本身也是 Java 代码，也有可能存在漏洞。本质上还是程序层面的限制，没深入系统的层面。





## Lab6

新建了一个虚拟机，用vmware station，iso印象文件用ubuntu 17的版本

进入页面，需要更改resolution（分辨率）、语言、输入法、时间等等。

为了Java开发，我们需要安装ssh，docker，jdk8，maven，都是用sudo apt install这样的命令去安装的。



### 使用docker部署，提升系统的安全性，使程序和宿主机隔离

本地ubuntu虚拟机的  ip地址：192.168.153.128

docker容器：

理解为对一系列应用程序、服务和环境的封装，从而把程序运行在一个隔离的、密闭的、隐私的空间内，对外整体提供服务

### 对应题目：Docker 能实现哪些资源的隔离？

1）Docker 运行在 Linux 内核上

2）CGroups：实现了容器的资源隔离，底层是 Linux Cgroup 命令，能够控制进程使用的资源

3）Network 网络：实现容器的网络隔离，docker 容器内部的网络互不影响

4）Namespaces 命名空间：可以把进程隔离在不同的命名空间下，每个容器他都可以有自己的命名空间，不同的命名空间下的进程互不影响。

5）Storage 存储空间：容器内的文件是相互隔离的，也可以去使用宿主机的文件

启动示例，得到容器示例id ：

zwd@zwd-virtual-machine:~$ sudo docker create hello-world
25d992db39b3c7541325546fd8bce67d4244b5b45f0dc4825fbf3e9663c70046

以上是container id！不是进程id！

创建容器后docker会给它命一个特别奇葩的名字！哈哈哈



java.nio.file.NoSuchFileException: C:\Users\zwd\AppData\Local\Temp\jdk-build13770425189794911197txt
 caused by: C:\Users\zwd\AppData\Local\Temp\jdk-build13770425189794911197txt



如果无法启动程序，修改 settings 的compiler 配置：-Djdk.lang.Process.launchMechanism=vfork



拉取镜像，创建容器，得到了一个Container的Id：

CreateContainerResponse(id=8528b6a88c8e3a68caa9de24e73fb40ea9c020ef216053494c17a47bfd12c59d, warnings=[])



在容器里查看日志，一定要加一个await：

// 阻塞等待日志输出

```java
dockerClient.logContainerCmd(containerId)

​        .withStdErr(true)

​        .withStdOut(true)

​        .exec(logContainerResultCallback)

​        .awaitCompletion();
```

Linux虚拟机下，示例执行

docker exec unruffled_jang java -cp /app Main 1 3

注：容器的名称是Linux系统随机起的名称，就是指的是这个“unruffled_jang”。

### Docker容器的安全性

超时控制，可以设置一个标志

内存资源怎么限制  HostConfig 的 withMemory

限制网络资源，创建容易时，设置网络配置为关闭

```java
CreateContainerResponse createContainerResponse = containerCmd

​        .withHostConfig(hostConfig)

​        .withNetworkDisabled(true)
```

Linux 自带的一些安全管理措施，比如 seccomp：

示例 seccomp 配置文件 profile.json：

```json
{

  "defaultAction": "SCMP_ACT_ALLOW",

  "syscalls": [

​    {

​      "name": "write",

​      "action": "SCMP_ACT_ALLOW"

​    },

​    {

​      "name": "read",

​      "action": "SCMP_ACT_ALLOW"

​    }

  ]

}
```



## Lab7（上）

单体改造微服务

使用模板，定义了一个抽象类JavaCodeSandboxTemplate，然后分别实现原生的，以及docker的Java代码沙箱

注意：注解@RequestBody用于接收前端传递给后端的、JSON对象的字符串，这些数据位于请求体中，适合处理的数据为非Content-

Type。使用注解@RequestBody可以将body里面所有的JSON字符串绑定到后端相应的Java Bean上，后端再进行数据解析和业务操作。

```java
@PostMapping("/executeCode")
ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest){
    if (executeCodeRequest == null) {
        throw new RuntimeException("请求参数为空");
    }
    return javaNativeCodeSandbox.executeCode(executeCodeRequest);
}
```

暴露api，调用接口



代码沙箱暴露在公网，不做任何的权限校验，我去刷量，你沙箱肯定是不安全的



API开放平台项目也是用了微服务，不是用了Spring Cloud的才叫做微服务项目，

微服务只是一种项目的思想，我们使用dubbo实现了分布式，注册、消费、转发，本来想使用zookeeper的，但是因为版本号的Bug问题没用



微服务的划分，Question



### ctrl + shift +F ，全局查找，查找在项目中出现的任何的变量，语句，和代码！



前端有Bug，一个是ViewQuestionsView，没有这个s，应该是ViewQuestionView，还有就是登陆的时候还在console登陆信息，然后就是修改题目页面，修改完了之后没有自动跳转到修改的页面去，而是停留在了那个页面



提交示例代码：

```
public class Main {
    public static void main(String[] args) {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);
        System.out.println(a + b);
    }
}
```

在judgeServiceImpl中：

```java
@Value("${codesandbox.type:example}")
private String type;
```

这个注解，一定要写成codesandbox.type，type不能写成value，不然识别不到yml里面远程代码沙箱的配置，这样就用不到远程的沙

箱，数据库question_submit表中的数据得不到正确的响应结果（即status为1），表示执行结果正确



idea调试：
使用f7 调试的时候遇到方法体的时候会进入到方法体内部   每个方法依次执行
使用f8 调试的时候 遇到方法体不会进入方法内部  只会依次执行一行一行的代码，可以监控到变量的值
使用f9 调试的时候 只会执行到打断点的地方
f9 放行，或者你新打一个断点，会按照顺序去执行到你打断点的地方去



多亏了调试，才看出来原来不是用的远程代码沙箱，哦耶！



### 注意：

远程的CodeSandbox是部署在虚拟机上的，如果要在后端项目中使用远程的服务器的话，最好把虚拟机也启动起来，如果沙箱部分报错，那么就无法正确地使用沙箱得到结果。



扩展：每隔一段时间刷新一下提交状态，因为后端是异步判题的

还有就是题目提交的列表有点太窄了，不方便我们进行查看，题目提交号和日期都会跑到下一行去，怎么解决？



### 微服务改造

服务：提供某类功能的代码

微服务：专注于提供某类特定功能的代码，而不是把所有的代码全部放到同一个项目里。会把整个大的项目按照一定的功能、逻辑进行拆分，拆分为多个子模块，每个子模块可以独立运行、独立负责一类功能，子模块之间相互调用、互不影响。

分布式：把一个大的项目部署到多台机器上，微服务：把一个大的项目进行拆分

分布式锁和单机锁的区别？

分布式不一定是微服务

微服务也不一定是分布式

分布式是和单机进行对立的

微服务是和整个项目进行对标的

绝大部分都会使用分布式来部署的，一个服务器挂机了还能用别的服务器

### 版本非常重要！

选用Spring Cloud Alibaba 2021.0.5.0 适配Spring Boot 2.X版本

1、Spring Cloud Gateway：网关

2、Nacos：服务注册和配置中心

3、Sentinel：熔断限流

4、Seata：分布式事务

5、RocketMQ：消息队列，削峰填谷

6、Docker：使用Docker进行容器化部署

7、Kubernetes：使用k8s进行容器化部署



业务功能：

（1）用户服务（yuoj-backend-user-service：8102 端口）

​	（a）注册（后端已实现）

​	（b）登录（后端已实现，前端已实现）

​	（c）用户管理

（2）题目服务（yuoj-backend-question-service：8103）

​	（a）创建题目（管理员）

​	（b）删除题目（管理员）

​	（c）修改题目（管理员）

​	（d）搜索题目（用户）

​	（e）在线做题（题目详情页）

​	（f）题目提交

（3）判题服务（yuoj-backend-judge-service，8104 端口，较重的操作）

​	（a）执行判题逻辑

​	（b）错误处理（内存溢出、安全性、超时）

​	（c）自主实现 代码沙箱（安全沙箱）

​	（d）开放接口（提供一个独立的新服务）

代码沙箱服务本身就是独立的，不用纳入 Spring Cloud 的管理



Nacos 要选用2.2.0的版本

像上次的API网关项目用的Nacos版本是2.1.2，先启动后端项目（前提条件是后端写好了各种服务，并且能调试通过，然后配置和注解什么的都没有写错），再到soft目录下启动这个Nacos

这次的Nacos是2.2.0版本，它位于software目录下，用这个命令启动，也不会报错！Nacos和Spring版本得有个对应！

```shell
startup.cmd -m standalone
```

redis在soft目录下，要启动redis的一个客户端，使用指令：

```shell
redis-cli
```

然后select 1，因为我们在后端的application.yml里写的配置：

```java
redis:
  database: 1
  host: localhost
  port: 6379
  timeout: 5000
```

最后再在终端里 keys *，可用于查找所有符合给定模式pattern的key 。



建议用脚手架创建项目：https://start.aliyun.com/



如果新建SpringBoot的初始化模块的时候报错，提示你连接不上start.spring.io，可能的问题出在了：

[修复初始化模块网络Bug]: https://blog.csdn.net/fashionjay2019/article/details/116429807?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522169483531816800222898540%2522%252C%2522scm%2522%253A%252220140713.130102334..%2522%257D&amp;request_id=169483531816800222898540&amp;biz_id=0&amp;utm_medium=distribute.pc_search_result.none-task-blog-2~all~sobaiduend~default-1-116429807-null-null.142

刚才在pom.xml文件中找不到yuoj-backend-service-client的快速输入，可能是新建模块的时候出现了网络的Bug，导致整个项目识别不到这个模块，重新创建一遍就好了

02:32:35



在外层的pom.xml中引入：

```xml
<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-collections4 -->

​        <dependency>

​            <groupId>org.apache.commons</groupId>

​            <artifactId>commons-collections4</artifactId>

​            <version>4.4</version>

​        </dependency>
```



服务内部调用

题目服务依赖用户服务，但是代码已经分到不同的包，找不到对应的 Bean --> 可以使用 Open Feign 组件实现跨服务的远程调用。





## Lab7（下）

### Open Feign

Http 调用客户端，提供了更方便的方式来让你远程调用其他服务，不用关心服务的调用地址
Nacos 注册中心获取服务调用地址

1）梳理服务的调用关系，确定哪些服务（接口）需要给内部调用

用户服务：没有其他的依赖

题目服务：

userService.getById(userId)

userService.getUserVO(user) --> 获取用户的封装类

userService.listByIds(userIdSet)

userService.isAdmin(loginUser)

userService.getLoginUser(request)

judgeService.doJudge(questionSubmitId)

判题服务：

questionService.getById(questionId)

questionSubmitService.getById(questionSubmitId)

questionSubmitService.updateById(questionSubmitUpdate)

2）确认要提供哪些服务

Feign没有从数据库中查取信息的能力，直接从session中取得用户user的信息就足够了



为什么不再继承 IService，因为IService是MyBatis的一个提供增删改查的基本接口，里面定义了很多增删改查的方法，但是很多方法本项目中用不到。

如果不这样去修改的话，改造成微服务项目后，很多的服务都还要走远程的调用，很麻烦。



使用Feign了后，在service-client上写@FeignClient(name = "yuoj-backend-user-service", path = "/api/user")注解

并且需要修改每个服务提供者的 context-path 全局请求路径



服务提供者：理解为接口的实现类，实际提供服务的模块（服务注册到注册中心上）
服务消费者：理解为接口的调用方，需要去找到服务提供者，然后调用。（其他服务从注册中心寻找） --> 简单理解为前端



get请求打上requestparam注解，post请求打上requestbody注解（json格式的话）。

### 查值的话GettingMapping

3）对于用户服务，有一些不利于远程调用参数传递、或者实现起来非常简单（理解就是和公共方法的工具类），可以直接用默认方法，无需远程调用，节约性能。



```java
/**
 * 根据 id 获取用户列表
 * @param idList
 * @return
 */
@Override
@GetMapping("/get/ids")
public List<User> listByIds(@RequestParam("idList") Collection<Long> idList) {
    return userService.listByIds(idList);
}
```

为什么要写Collection，不写map，或者set，因为MyBatis本来传递的这个就是一个集合，写集合类相关的都可以。



4）修改各业务服务的调用代码为 feignClient

5）编写 feignClient 服务的实现类，注意要和之前定义的客户端保持一致



6）开启 Nacos 的配置，让服务之间能够互相发现

所有模块引入 Nacos 依赖，然后给业务服务（包括网关）增加配置：

## （来自阿里云的中文文档教学）

注意右上角的版本要选择2021.0.5

```yaml
spring:

​    cloud:

​        nacos:

​          discovery:

​            server-addr: 127.0.0.1:8848
```

如果你要上线，要改这个端口地址！！！

给业务服务项目启动类打上注解，开启服务发现、找到对应的客户端 Bean 的位置

```java
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.yupi.yuojbackendserviceclient.service"})
```

不用给网关打注解，网关打一个服务发现的注解即可，就是上面那个。



01：03：18，网关有主类，要开启服务发现的注解

7）启动项目，测试依赖能否注入，能否完成相互调用

## 微服务网关！

是什么：Gateway 聚合所有的接口，统一接受处理前端的请求

为什么？

所有的服务端口不同，增大了前端调用成本；

所有服务是分散的，你可需要集中进行管理、操作，比如集中解决跨域、鉴权、接口文档、服务的路由、接口安全性、流量染色、限流。

缺点：自定义功能比较麻烦

### 接口路由

写配置文件，uri：转发到哪个服务，predicates：规则

```yaml
spring:

  cloud:

​    nacos:

​      discovery:

​        server-addr: 127.0.0.1:8848

​    gateway:

​      routes:

​        \- id: yuoj-backend-user-service

​          uri: lb://yuoj-backend-user-service

​          predicates:

​            \- Path=/api/user/**

​        \- id: yuoj-backend-question-service

​          uri: lb://yuoj-backend-question-service

​          predicates:

​            \- Path=/api/question/**

​        \- id: yuoj-backend-judge-service

​          uri: lb://yuoj-backend-judge-service

​          predicates:

​            \- Path=/api/judge/**

  application:

​    name: yuoj-backend-gateway

  main:

​    web-application-type: reactive

server:

  port: 8101
```

### Knife4j

使用 Knife4j 接口文档生成器



pom.xml文件和application.yml都对，就是打不开localhost:8101/doc.html#/home，是因为pom.xml没有刷新出来，点进去网关新加的那个依赖，发现有的报红，没有加载出来，再次刷新maven，然后以debug方式启动这4个服务，再看！发现可以登上去了



登陆了之后，再次打开一个无痕的页面，直接去调试question-service里的doQuestionSubmit，提示未登录，这很正常。然后我们用Knife4j去调试，用正常的有痕窗口去登陆，登陆后，我们发现userService里的getLoginUser可以拿到登陆用户的信息，但是question-service里的doQuestionSubmit里，它却提示“未登录”，拿不到登陆用户的信息，这说明它没有实现session的共享，还是一个单机的项目，没有变成分布式的，session还是单击存储的。



单体项目引入了redis依赖，分布式项目并没有引入redis依赖！

redis不引入也不报错，就是不正常，达不到预期的结果。

### **分布式 Session 登录**

引入两个依赖，

```yaml
<!-- redis -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.session</groupId>
    <artifactId>spring-session-data-redis</artifactId>
</dependency>
```

一个是springboot的redis依赖，一个是session依赖于redis传输的依赖



0918记：又遇到Knife4j启动异常，这是因为在new一个对象的时候没有使用它的默认构造方法，导致这个对象可能为空了！



扩展：可以在网关实现 Sentinel 接口限流降级，参考教程https://sca.aliyun.com/zh-cn/docs/2021.0.5.0/user-guide/sentinel/overview



扩展：可以使用 JWT Token 实现用户登录，在网关层面通过 token 获取登录信息，实现鉴权

Redisson RateLimiter 也可以实现限流？！



企业内部一般使用 API（RPC、HTTP）实现跨部门、跨服务的调用（因为开发的语言可能不一样），数据格式和调用代码全部自动生成，保持统一，同时解耦。人家不可能跟你一样去改SpringCloud。

### 消息队列

解耦判题服务和题目服务

引入的好处：持久化、好监控、好管理、持久应用，解耦应用，不会因为一个应用挂掉了而影响另一个。

这里使用rabbitMQ，先安装erLang语言，然后再安装rabbitMQ，rabbitMQ服务会在本地自启动，登陆localhost:15672，密码都是

guest，如果远程部署的话，要增加账户并且修改密码（为了安全性），详见BI。

RabbitMQ启动后自停止的解决方案：cmd要用管理员权限去启动！

```shell
Microsoft Windows [版本 10.0.22621.2283]
(c) Microsoft Corporation。保留所有权利。

C:\Windows\System32>D:\software\RabbitMQ Server\rabbitmq_server-3.12.0\sbin
'D:\software\RabbitMQ' 不是内部或外部命令，也不是可运行的程序
或批处理文件。

C:\Windows\System32>cd D:\software\RabbitMQ Server\rabbitmq_server-3.12.0\sbin

//再输入一个D:才能重定位到这个指定目录下，如果是E盘，则是E:

C:\Windows\System32>D:

D:\software\RabbitMQ Server\rabbitmq_server-3.12.0\sbin>rabbitmq-service.bat remove
D:\Erlang OTP\erts-13.2.2\bin\erlsrv: Service RabbitMQ removed from system.

D:\software\RabbitMQ Server\rabbitmq_server-3.12.0\sbin>set RABBITMQ_BASE=D:\software\RabbitMQ Server\rabbitmq_server-3.12.0

D:\software\RabbitMQ Server\rabbitmq_server-3.12.0\sbin>rabbitmq-service.bat install
D:\Erlang OTP\erts-13.2.2\bin\erlsrv: Service RabbitMQ added to system.

D:\software\RabbitMQ Server\rabbitmq_server-3.12.0\sbin>rabbitmq-plugins enable rabbitmq_management
Enabling plugins on node rabbit@LAPTOP-7DG3FKGD:
rabbitmq_management
The following plugins have been configured:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch
Applying plugin configuration to rabbit@LAPTOP-7DG3FKGD...
The following plugins have been enabled:
  rabbitmq_management
  rabbitmq_management_agent
  rabbitmq_web_dispatch

set 3 plugins.
Offline change; changes will take effect at broker restart.

D:\software\RabbitMQ Server\rabbitmq_server-3.12.0\sbin>
```



启动的话，要写好各个地方的生产者和消费者



——>初始登陆后，发现没有分页功能，将原本的backend里的JsonConfig和MyBatisPlusConfig粘贴过来！

MyBatisPlusConfig粘贴到消费的生产和服务方就行了！



03：06：48，看完了。项目的所有流程已跑通。
