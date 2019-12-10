package service;

import DAO.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerSearch {

    public List FuzzySearch(String name){
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try{
            tx=session.beginTransaction();
            String hql="from Customer where personalName like '%"+name+"%'";
            Query query =session.createQuery(hql);
            list = query.list();

        }catch (RuntimeException e) {
            System.out.println("_____________________Can not Search_________________");
            throw e;
        }finally {
            session.close();
            return list;
        }
    }

    public List AllSearch(){
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try{
            tx=session.beginTransaction();
            String hql="from Customer ";
            Query query =session.createQuery(hql);
            list = query.list();

        }catch (RuntimeException e) {
            System.out.println("_____________________Can not Search_________________");
            throw e;
        }finally {
            session.close();
            return list;
        }
    }
}
