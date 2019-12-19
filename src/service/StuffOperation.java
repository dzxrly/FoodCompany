package service;

import DAO.HibernateUtils;
import model.Customer;
import model.Stuff;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class StuffOperation {
    public int StuffUpdate(int Number, String name, int type, int level, String address, String phoneNumber, String email, String personalId, int gender) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        int flag = 0;
        Stuff sf = new Stuff();
        try {
            tx = session.beginTransaction();
            sf = session.get(Stuff.class, Number);
            sf.setGender(gender);
            sf.setAddress(address);
            sf.setPersonalName(name);
            sf.setPhoneNumber(phoneNumber);
            sf.setEmail(email);
            sf.setLevel(level);
            sf.setType(type);
            sf.setPersonalID(personalId);
            session.update(sf);
            tx.commit();
            flag = 1;
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("--cannot update---");
            flag = 0;
            throw e;
        } finally {
            session.close();
            return flag;
        }
    }

    //返回0表示没有删除 返回1表示已删除
    public int stuffDelete(int number) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
//        String hql = "";
        int state = 0;
        try {
            tx = session.beginTransaction();
//            hql = "delete Stuff where number =" + String.valueOf(number);
            Object Stuff = session.get(model.Stuff.class, number); // 要先获取到这个对象
            session.delete(Stuff); // 删除的是实体对象
            state=1;
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return state;
        }
    }
}

//        StuffOperation so= new StuffOperation();
//        System.out.println(so.StuffUpdate(10016,"bbb",2,1,"ccc","22222222222","222@qq.com","222222222222222222",0));
//        so.stuffDelete(10016);
