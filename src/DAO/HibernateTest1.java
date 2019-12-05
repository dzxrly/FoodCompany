package DAO;

import model.Customer;
import model.ExperimentCustomer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateTest1 {

    public void saveCustomerTest(){
        Customer c=new Customer();
        c.setAccumulatedAmount("0");
        c.setAddress("浙江");;
        c.setBirthday(LocalDate.now());
        c.setCompanyName("山东大学威海");
        c.setEmail("530153667@qq.com");
        c.setLevel("0");
        c.setNumber("10000");
        c.setOrderQuantity("0");
        c.setPersonalName("陈博");
        c.setPhoneNumber("13857756176");;
        c.setType("老板");

        //使用hibernate中的API完成将Customer信息保存到mysql数据库的操作
        Configuration config =new Configuration().configure();
        SessionFactory sf = config.buildSessionFactory();
        Session se=sf.openSession();
        //开启事务
        se.beginTransaction();
        //操作
        se.save(c);
        //事务提交
        se.getTransaction().commit();
        se.close();
        sf.close();
    }
}
