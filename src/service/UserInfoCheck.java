package service;

import DAO.HibernateUtils;
import DAO.querySql;
import javafx.beans.property.StringProperty;
import model.Stuff;
import org.hibernate.Session;
import org.hibernate.Transaction;


//验证用户的number和密码是否合法 -1.用户名不存在 0.密码错误 1.成功
public class UserInfoCheck {

    public int isValidNumber(String number, String pw) {
        int res = 1;
        Session session = HibernateUtils.openSession();
        Transaction tx = null;

        try {

            tx = session.beginTransaction();
            Stuff stuff = (Stuff) session.get(Stuff.class, Integer.valueOf(number));
            System.out.println("_____________________________________" + stuff.getPassword().toString());
            if (stuff.getPassword().toString().equals(pw)) {
                res = 0;
                System.out.println("_____________________________________" + res);

            } else if (!stuff.getPassword().toString().equals(pw)) res = 2;
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("_____________________________ERROR______________________________________");
            throw e;
        } finally {
            session.close();
            System.out.println("_____________________________CLOSE______________________________________");
            return res;
        }
    }

}
