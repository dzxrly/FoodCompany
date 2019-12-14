package service;

import DAO.HibernateUtils;
import model.Stuff;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class StuffAdd {

    public int addStuff(String personalName,String passward,int level,String personalId ,String address,String phoneNumber,String email,int gender){
        Session session = HibernateUtils.openSession();
        Transaction tx=null;
        int ans= 0;
        Stuff s=new Stuff();
        s.setPersonalName(personalName);
        s.setPassword(passward);
        s.setLevel(level);
        s.setPersonalID(personalId);
        s.setAddress(address);
        s.setPhoneNumber(phoneNumber);
        s.setEmail(email);
        s.setGender(gender);
        try{
            tx=session.beginTransaction();
            session.save(s);
            ans = 1;//1表示成功
            tx.commit();
        }catch ( RuntimeException e){
            tx.rollback();
            ans=0;//0表示失败
            throw e;
        }finally {
            session.close();
            return ans;
        }
    }
}
//
//    StuffAdd sa=new StuffAdd();
//
//        sa.addStuff("李小白","10086",1,"372988177766567898","青岛市鲁迅公园","17965476687","875677890@qq,com",1);
