package service;

import DAO.HibernateUtils;
import model.Stuff;
import org.hibernate.Session;

import java.util.List;

//员工信息管理界面的员工搜索
public class StuffSearch {
    //test为空
    public List searchAll() {
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {
            hql = "from Stuff";
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("---can not search----");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    //test不为空
    public List searchByIndex(int index, String test) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {
            if (index == 0) {//员工id
                hql = "from Stuff where number=" + test;
            } else if (index == 1) {//员工姓名
                hql = "from Stuff where personalName like '%" + test + "%'";
            } else if (index == 2) {//部门id
                if (test.equals("老板")) test = "0";
                else if (test.equals("销售部")) test = "1";
                else if (test.equals("财务部")) test = "2";
                else if (test.equals("生产计划科")) test = "3";
                else if (test.equals("生产车间")) test = "4";
                else if (test.equals("成品库")) test = "5";
                else if (test.equals("原料库")) test = "6";
                hql = "from Stuff where type=" + test;
            } else if (index == 3) {//性别
                if (test.equals("男")) test = "0";
                else test = "1";
                hql = "from Stuff where gender=" + test;
            }
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("---can not search----");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public Stuff getStuffById(int stuffNumber) {
        Session session = HibernateUtils.openSession();
        Stuff stuff = new Stuff();
        try {
            stuff = session.get(Stuff.class, stuffNumber);
        } catch (RuntimeException e) {
            stuff = null;
            throw e;
        } finally {
            session.close();
            return stuff;
        }
    }
}
