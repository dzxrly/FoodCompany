package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
    public static SessionFactory sessionFactory;

    static {
        sessionFactory =new Configuration().configure().buildSessionFactory();
    }

    //获取全局唯一的sessionFactory
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    //从唯一的sessionFactory中打开一个session
    public static Session openSession() {
        return sessionFactory.openSession();
    }
}
