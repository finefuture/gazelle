# gazelle 

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

**JPA** JPA(Java Persistence API)是Sun官方提出的Java持久化规范。它为Java开发人员提供了一种对象/关系映射工具来管理Java应用中的关系数据。

**gazelle** 是一个小巧而轻便的JPA组件，因此命名为gazelle，意为小羚羊。它是基于hibernate提供的JPA2.1规范的，因此只要是实现了JPA2.1规范的框架（例如4.3版本以上的hibernate）都可以无缝切换底层实现。它同时也提供了方便的链式调用以及可植入式的where条件过滤.

#### 快速入门:

##### 工程依赖:
+ JDK1.7或更高版本
+ 依赖管理工具: Maven3.x版本/Gradle3.1以上版本

##### [最新版本OSS下载](https://oss.sonatype.org/#nexus-search;quick~io.github.finefuture)
##### [最新版本Maven中央仓库下载](http://search.maven.org/#search%7Cga%7C1%7Cio.github.finefuture%20gazelle)
##### Maven依赖:
```xml
<dependency>
    <groupId>io.github.finefuture</groupId>
    <artifactId>gazelle</artifactId>
    <version>1.0</version>
</dependency>
```

### 使用简介 ###

###### 1. 注册gazelle所需组件到spring:

```java

@Configuration
public class GazelleConfiguration {

    @PersistenceContext
    EntityManager entityManager;

    @Bean
    public Criterion criterion () {
        return new Criterion(jpa());
    }

    @Bean
    public Jpa jpa () {
        return new Jpa(entityManager);
    }

    @Bean
    public Special special () {
        return new Special(jpa());
    }

    @Bean
    public Where where () {
        return new Where(jpa());
    }

    @Bean
    public Recovery recovery () {
        return new Recovery(jpa(), special(), where());
    }

    @Bean
    public Setter setter () {
        return new Setter(jpa());
    }

}
```

###### 2. 创建服务基类:

```java

@Service
public class MagicService {

    @Autowired
    public Criterion criterion;

    @Autowired
    public Where where;

    @Autowired
    public Special special;

    @Autowired
    public Jpa jpa;

    @Autowired
    public Setter setter;

}
```

###### 3. 具体服务类:

```java

@Service
public class ShopService extends MagicService{

    public List<Map<String, Object>> get (boolean auditStatus, String shopName, int first, int max) {
        jpa.register(Shop.class, Operation.tupleQuery)
                .aggregate();
        special.init()
                .sum("del", "li")
                .all();
        where.init()
                .checkNullValue()//默认两个过滤条件（checkNullValue和checkEmptyValue）
                .addChecker("methodName", Checker.class)//植入where条件过滤
		.and()
                .eq("auditStatus", auditStatus)
                .eq("shopName", shopName)
		.or()
		.like("id", "%BC85")
                .asc("createTime", "updateTime")
                .desc("del","logo")
                .first(first)
                .max(max);
        return criterion.findSpecToList(special, where);//推荐使用findSpec(special,where)返回Tuple元组
    }
}
```

###### 4. 手动回收ThreadLocal存放的数据:

```java

@Aspect
@Component
public class RecoveryAspect {

    @Autowired
    private Recovery recovery;

    @Pointcut("execution(* org.gra4j.SimpleJpa.service..*.*(..))")
    public void point () {}

    @After("point()")
    public void after () {
        recovery.recover();
    }
}
```
##### [示例代码](https://github.com/finefuture/gazelle-example)

#### 其他
- qq交流群: 634217275
- 邮件交流: 1120170646@qq.com

