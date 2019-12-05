package service;

import DAO.querySql;
import javafx.beans.property.StringProperty;


//验证用户的number和密码是否合法 -1.用户名不存在 0.密码错误 1.成功
public class UserInfoCheck {
    /*
    //登录时
    public String userLogin(String number,String pwd){
        String res;
        querySql ql=new querySql();
        if(ql.checked(number,pwd)==1)
        {
            res="成功!";
            //System.out.println("成功！");
        }
        else if (ql.checked(number,pwd)==0)
        {
            res="psw error!";
            //System.out.println("psw error!");
        }
        else if (ql.checked(number,pwd)==-1) {
            res="name doesn't exist!";
            //System.out.println("name doesn't exist!");
        }
        else
        {
            res="???";
            //System.out.println("???");
        }
        return res;
    }

    //注册时
    public String userSignUp(String number,String pwd){
        String res;
        querySql ql=new querySql();
        if(ql.checked(number,pwd)==1)
        {
            res="注册成功!";
            //System.out.println("成功！");
        }
        else if (ql.checked(number,pwd)==0)
        {
            res="密码不合法!";
            //System.out.println("psw error!");
        }
        else if (ql.checked(number,pwd)==-1) {
            res="用户名不合法";
            //System.out.println("name doesn't exist!");
        }
        else if
        {
            res="email不合法";
            //System.out.println("???");
        }
        return res;
    }
    */
}
