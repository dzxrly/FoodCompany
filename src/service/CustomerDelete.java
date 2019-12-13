package service;

import DAO.HibernateUtils;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerDelete {

    public int deleteCustomer(int number) {
        int ans = 0;
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hql = "delete Customer as c where c.number=?";
            Object Customer = session.get(Customer.class, number); // 要先获取到这个对象
            session.delete(Customer); // 删除的是实体对象

            ans = 1;//1表示删除成功
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            ans = 0;//0表示删除失败
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }
}
