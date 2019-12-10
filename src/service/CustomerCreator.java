package service;

import DAO.HibernateUtils;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CustomerCreator {

    public int createCustomer(String personalName, String companyName, String type,int level, String address, String email, String phoneNumber){
        int ans = 1 ;

        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        Customer c= new Customer();
        CustomerIndexAndStringSwitch cis=new CustomerIndexAndStringSwitch();

        c.setPersonalName(personalName);
        c.setCompanyName(companyName);

        int t=cis.returnTypeIndexByString(type);
        c.setType(t);

        c.setLevel(level);
        c.setAddress(address);
        c.setPhoneNumber(phoneNumber);
        try{
            tx=session.beginTransaction();
            session.save(c);
            tx.commit();
            ans = 1 ;//1 表示成功
        }catch(RuntimeException e){
            tx.rollback();
            System.out.println("__________________________Can't insert into table customer________________________");
            ans = 0; //0表示不成功
        }
        finally {
            session.close();
            return ans;
        }
    }
}
