package service;

import DAO.HibernateUtils;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CustomerUpdate {
    public List updateCustomer(String personalName, String companyName, int type, int level, String address, String email, String phoneNumber){
        List list=null;
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        Customer c = new Customer();
        c.setPersonalName(personalName);
        c.setCompanyName(companyName);
        c.setType(type);
        c.setLevel(level);
        c.setAddress(address);
        c.setPhoneNumber(phoneNumber);
        c.setEmail(email);
        try {
            tx = session.beginTransaction();
            String hql="";
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("__________________________Can't insert into table customer________________________");
        } finally {
            session.close();
            return list;
        }
    }
}
