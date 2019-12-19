package service;

import DAO.HibernateUtils;
import DAO.querySql;
import javafx.beans.property.StringProperty;
import model.Stuff;
import org.hibernate.Session;
import org.hibernate.Transaction;


//验证用户的number和密码是否合法 -1.用户名不存在 0.密码错误 1.成功
public class UserInfoCheck {

    public String isValidNumber(String number, String pw) {
        //int res = 1;
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String ans = "1";
        try {

            tx = session.beginTransaction();
            Stuff stuff = (Stuff) session.get(Stuff.class, Integer.valueOf(number));
            System.out.println("_____________________________________" + stuff.getPassword());
            if (stuff.getPassword().equals(pw)) {
                //res = 0;
                ans = "0";
                System.out.println("_____________________________________" + ans);
                //得到登录状态码和权限状态码组成最后输出的字符串
                System.out.println("--------------------------------" + stuff.getType());
                ans+=stuff.getLevel();
                ans += stuff.getType();
                //权限写进properties
                PropertiesOperation propertiesOperation = new PropertiesOperation();
                propertiesOperation.writeProperties("userConfig.properties", "UserLevel", String.valueOf(stuff.getType()));
                propertiesOperation.writeProperties("userConfig.properties", "LoginUserName", stuff.getPersonalName());
                propertiesOperation.writeProperties("userConfig.properties", "LoginUserNumber", String.valueOf(stuff.getNumber()));
            } else if (!stuff.getPassword().equals(pw)) ans = "2";
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("_____________________________ERROR______________________________________");
            throw e;
        } finally {
            session.close();
            System.out.println("_____________________________CLOSE______________________________________");
            return ans;
        }
    }

}
