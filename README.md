# gazelle 

[![License](https://img.shields.io/badge/license-Apache%202-4EB1BA.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)

**JPA** JPA(Java Persistence API)是Sun官方提出的Java持久化规范。它为Java开发人员提供了一种对象/关系映射工具来管理Java应用中的关系数据。

**gazelle** 是一款类似于Spring data jpa的组件（哈哈，以后简历上应该可以写熟练Spring data jpa了），但是它提供了比Spring data jpa更多的功能（下面例子里会说一些，但是还有更多的功能希望读者自己发掘，哈哈）。它是基于hibernate提供的JPA2.1规范的。

**gazelle提供了事务管理功能，一种是Jpa原生的TransactionManager，一种是植入的springTransactionManager**
**事务测试**
```java
	@Transactional(rollbackFor = Exception.class)
    public void testTX () {
        shopRepository.delete("014A7A16-2297-474D-B3C4-D8F9B9E976A3");
        shopRepository.delete("1111111111111");//这里会报错，将回滚
        GazelleQuery.update(Shop.class)
                    .setter().set("shopName", "XXX").build()
                    .where().eq("id", "35847C13-40DE-4885-8FBE-2C1DD39F7860").build()
                    .execute();
    }
```

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
    <version>2.1</version>
</dependency>
```

### 使用简介 ###

##### 第一步. 咱们先配置gazelle（其实还有一步，先配置EntityManager,haha,最后面的示例项目链接里面有QAQ）:

```java

@Configuration
@EnableGazelleRepository(basePackages = "org.gra4j.gazelleExample.crud.dao.jpa")
public class GazelleConfiguration {

    @PersistenceContext
    EntityManager entityManager;

	@Autowired
    PlatformTransactionManager tx;

    @Bean
	@PostConstruct
    public Jpa jpa () {
        Jpa jpa = new Jpa(entityManager);
        JpaContext.setEntityManager(entityManager);
		JpaContext.setTransactionType(TransactionalType.spring);//TransactionalType.jpa
		JpaContext.setSpringTransactionManager(tx);
        return jpa;
    }

}
```

##### 第二步. 其实咱有两种方式运用gazelle 0.0，比如:
###### 第一种，使用dao层接口的方式(还有@TupleQuery、@Delete、@Update啊之类大家自己发掘啊，嘤嘤嘤)：

```java

public interface ShopRepository extends GazelleRepository<Shop, String> {

    @Query(@Where(and   = @And({@Expression(ops = ExpressionOps.eq,key = "del"),
                                @Expression(ops = ExpressionOps.eq,key = "shopName")}),
//                  or    = @Or({@Expression(ops = ExpressionOps.like,key = "id",value = "%BC85"),
//                               @Expression(ops = ExpressionOps.in)}),
                  order = @Order(asc = "createTime"),
                  check = {CheckOps.checkNullValue, CheckOps.checkEmptyValue}))
    List<Shop> find (@ExpParam Object del, @ExpParam Object shopName,
                     @PageParam(PageType.first) int first);

    @SqlQuery(value = "select * from shop where del=:del order by create_time limit 10", isNative = true, result = Shop.class)
    List<Shop> find (@ExpParam("del") Integer del);

	@Update(set = {"shopName"},where = @Where(and = @And(@Expression(ops = ExpressionOps.eq, key="shopName"))))
    int update(@ExpParam Object shopName, @ModifyParam String name);

}
```

####### 第二种，使用GazelleQuery（这里面也有很多功能哇~）：

```java

public List<Shop> find () {

        Shop one = (Shop) GazelleQuery.basic(Shop.class).findOne("5BD0E7D5-CE2A-4A8A-9261-363BFD928FBD");
        System.out.println(one);
        return GazelleQuery.query().nativeQuery("select * from Shop limit 10", Shop.class);
//        return GazelleQuery.basic(Shop.class).findAll();
//        Shop save = (Shop) GazelleQuery.basic(Shop.class).save(new Shop());
//        return GazelleQuery.select(Shop.class).list();
    }
```

##### 额，好像没了，其实不足的地方还有很多，多多提意见吧（虽然我不一定能解决QAQ），那就这样吧，下面是示例代码，哈哈

##### [示例代码](https://github.com/finefuture/gazelle-example)

#### 其他
- qq交流群: 634217275
- 邮件交流: 1120170646@qq.com

