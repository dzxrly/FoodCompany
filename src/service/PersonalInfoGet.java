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
        try{
            sf = session.get(Stuff.class, stuffNumber);
            System.out.println(sf.getNumber()+" "+sf.getPersonalID());
        }catch(RuntimeException e){
            System.out.println("_____________________________Can not get___________________________");
            sf=null;
            throw e;
        }finally {
            session.close();
            return sf;
        }
    }

    //个人信息修改（密码和别的信息）返回状态
    public int stuffInfoUpdate(int gender,String name,String phoneNumber,String address,String email) {
        int state=0;
        return state;
    }

    public int stuffUpdatePsw(String newPsw){
        int state=0;
        return state;
    }
}
