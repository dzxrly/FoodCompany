package service;

import DAO.HibernateUtils;
import model.Customer;
import model.DestroyRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

//产品销毁界面
public class DestroyOperation {
    public List stockSearchAll() {
        Session session = HibernateUtils.openSession();
        List list = null;
        String hql = "";
        try {
            hql = "from ShippingDepartment";
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("cannot search---");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List stockSearchByTest(int index, String test) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {
            if (index == 0) {//按照成品库商品id查
                hql = "from ShippingDepartment where goodsId=" + test;
            } else if (index == 1) {//按照成品库商品名字查
                hql = "from ShippingDepartment where goodsName like '%" + test + "%'";
            }
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("cannot search---");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public int createDestroyRecord(String name,int stuffNumber,String destroyTime,String destroyNote) {
        int ans = 1;

        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        DestroyRecord dr = new DestroyRecord();
        dr.setDestroyNote(destroyNote);
        dr.setDestroyTime(destroyTime);
        dr.setStuffNumber(stuffNumber);
        dr.setGoodsName(name);
        try {
            tx = session.beginTransaction();
            session.save(dr);
            tx.commit();
            ans = 1;//1 表示成功
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("__________________________Can't insert into table customer________________________");
            ans = 0; //0表示不成功
        } finally {
            session.close();
            return ans;
        }
    }
}
