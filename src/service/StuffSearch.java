package service;

import DAO.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

//员工信息管理界面的员工搜索
public class StuffSearch {
    //test为空
    public List searchAll(int index, String test) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {
            if (index == 0) {//员工id精确查询
                hql = "from Stuff";
            } else if (index == 1) {//员工姓名
                hql = "from Stuff";
            } else if (index == 2) {//部门id
                hql = "from Stuff";
            } else if (index == 3) {//性别
                hql = "from Stuff";
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
                hql = "from Stuff where type=" + test;
            } else if (index == 3) {//性别
                if(test.equals("男")) test="0";
                else test="1";
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
}
