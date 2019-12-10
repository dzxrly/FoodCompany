package service;

import DAO.HibernateUtils;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerUpdate {
    public void updateCustomer(String personalName, String companyName, Integer type, Integer level, String address, String email, String phoneNumber, int number) {
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
            String hql = "update Customer set personalName=:personalName,companyName=:companyName,type=:type ,level= :level,address= :address," +
                    "email=:email,phoneNumber=:phoneNumber where number=:number";
            Query query=session.createQuery(hql);
            query.setString("personalName", personalName);
            query.setString("companyName", companyName);
            query.setInteger("type",type);
            query.setInteger("level",level);
            query.setString("address", address);
            query.setString("email", email);
            query.setString("phoneNumber", phoneNumber);
            query.setInteger("number",number);
            query.executeUpdate();

            tx.commit();
        } catch (RuntimeException e) {

            tx.rollback();

            System.out.println("__________________________Can't insert into table customer________________________");
            throw e;
        } finally {
            session.close();
        }
    }
}
