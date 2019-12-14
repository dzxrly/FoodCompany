package service;

import DAO.HibernateUtils;
import model.Stuff;
import org.hibernate.Session;
import org.hibernate.Transaction;

//个人信息表的获取
public class PersonalInfoGet {
    //个人信息显示 返回stuff类
    public Stuff stuffInfoView(int stuffNumber) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        Stuff sf = new Stuff();
        try {
            sf = session.get(Stuff.class, stuffNumber);
            System.out.println(sf.getNumber() + " " + sf.getPersonalID());
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not get___________________________");
            sf = null;
            throw e;
        } finally {
            session.close();
            return sf;
        }
    }

    //个人信息修改（密码和别的信息）返回状态
    public int stuffInfoUpdate(int stuffNumber, int gender, String name, String phoneNumber, String address, String email) {
        //状态码为0表示修改成功 1表示修改失败
        int state = 0;
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        Stuff sf = new Stuff();
        try {
            tx = session.beginTransaction();
            sf = session.get(Stuff.class, stuffNumber);
            sf.setGender(gender);
            sf.setAddress(address);
            sf.setPersonalName(name);
            sf.setPhoneNumber(phoneNumber);
            sf.setEmail(email);
            session.update(sf);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            state=1;
            System.out.println("-------cannot update-------");
            throw e;
        } finally {
            session.close();
            return state;
        }

    }

    public int stuffUpdatePsw(int stuffNumber, String oldPsw, String newPsw) {
        //状态码为0表示修改成功，状态码为1表示原密码输入和存的不同
        int state = 0;
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        Stuff sf = new Stuff();
        try {
            tx=session.beginTransaction();
            sf = session.get(Stuff.class, stuffNumber);
            if (!sf.getPassword().equals(oldPsw)) state = 1;
            else sf.setPassword(newPsw);
            session.update(sf);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("----------can not update---------");
            throw e;
        } finally {
            session.close();
            return state;
        }

    }
}
