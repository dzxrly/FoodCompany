package DAO;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateTest1 {
    // 保存一个Customer
/*
    private StringProperty personalName = new SimpleStringProperty(); //姓名.
    private StringProperty companyName = new SimpleStringProperty(); //公司名
    private IntegerProperty  number = new SimpleIntegerProperty(); //顾客ID
    private IntegerProperty level = new SimpleIntegerProperty(); //客户星级，用数字表示，分为1~5，5为最高.
    private StringProperty address = new SimpleStringProperty(); //住址
    private StringProperty email = new SimpleStringProperty(); //邮箱.
    private StringProperty phoneNumber = new SimpleStringProperty(); //手机号.
    private IntegerProperty type = new SimpleIntegerProperty();//顾客类型.
    private StringProperty accumulatedAmount;//累计交易额
    private StringProperty orderQuantity;//客户完成的订单数量

 */
    public void saveCustomerTest() {
        // 创建一个Customer
        Customer c = new Customer();
        c.setPersonalName("肖扬");
        c.setCompanyName("Park");
        c.setLevel(1);
        c.setAddress("浙江-温州");
        c.setEmail("66qwe66@163.com");
        c.setPhoneNumber("131651239");
        c.setType(1);
        c.setAccumulatedAmount(232.123);
        c.setOrderQuantity(10);


        // 使用Hibernate的API来完成将Customer信息保存到mysql数据库中的操作

        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        // 开启事务
        session.beginTransaction();

        // 操作
        session.save(c);

        // 事务提交
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    public void findCustomerByIdTest() {
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        // 开启事务
        session.beginTransaction();

        // 根据业务来编写代码
        // Customer c = session.get(Customer.class, 1);
        Customer c = session.load(Customer.class, 1);

        System.out.println("-----------------------" + c.getPhoneNumber() + "-----------------------------");

        // 事务提交
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
