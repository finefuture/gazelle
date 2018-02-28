import org.gra4j.gazelle.example.entity.User;
import org.gra4j.gazelle.JPAQuery.BasicOperation;
import org.gra4j.gazelle.repository.JpaContext;
import org.junit.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Example {
    /**
     * 手动进行entity manager的管理
     */
    private static EntityManagerFactory entityManagerFactory;
    private static EntityManager entityManager;

    /**
     * 此对象封装了对实体类的操作
     */
    private static BasicOperation<User, Long> operation;

    @BeforeClass
    public static void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("PersistenceUnit");
        entityManager = entityManagerFactory.createEntityManager();

        // 设置Gazelle的entity manager
        JpaContext.setEntityManager(entityManager);

        operation = new BasicOperation<>(entityManager, User.class);
    }

    @AfterClass
    public static void close() {
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    public void test1() {
        // 向数据库插入一条数据
        entityManager.getTransaction().begin();

        User user = new User(1L, "tom");
        operation.save(user);

        entityManager.getTransaction().commit();

        // 检验上一步是否成功插入数据
        User user1 = operation.findOne(1L);
        Assert.assertTrue(user1 != null);
    }

    /**
     * 测试更新功能
     */
    @Test
    public void test2() {
        User user = operation.findOne(1L);
        user.setName("jerry");

        entityManager.getTransaction().begin();
        operation.update(user);
        entityManager.getTransaction().commit();

        User userUpdated = operation.findOne(1L);
        Assert.assertTrue(userUpdated != null);
        Assert.assertTrue(userUpdated.getName().equals(user.getName()));
    }

    /**
     * 测试删除功能
     */
    @Test
    public void test3() {
        entityManager.getTransaction().begin();
        operation.delete(1L);
        entityManager.getTransaction().commit();

        User user = operation.findOne(1L);
        Assert.assertTrue(user == null);
    }
}
