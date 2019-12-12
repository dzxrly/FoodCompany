package service;

import DAO.HibernateUtils;
import model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class CustomerSearch {
    public List NameFuzzySearch(String name) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Customer where personalName like '%" + name + "%'";
            Query query = session.createQuery(hql);
            list = query.list();

        } catch (RuntimeException e) {
            System.out.println("_____________________Can not Search_________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }


    public List CompanyFuzzySearch(String companyName) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Customer where companyName like '%" + companyName + "%'";
            Query query = session.createQuery(hql);
            list = query.list();

        } catch (RuntimeException e) {
            System.out.println("_____________________Can not Search_________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List PhoneNumberFuzzySearch(String phoneNumber) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Customer where phoneNumber like '%" + phoneNumber + "%'";
            Query query = session.createQuery(hql);
            list = query.list();

        } catch (RuntimeException e) {
            System.out.println("_____________________Can not Search_________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List EmailFuzzySearch(String email) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Customer where email like '%" + email + "%'";
            Query query = session.createQuery(hql);
            list = query.list();

        } catch (RuntimeException e) {
            System.out.println("_____________________Can not Search_________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List AllSearch() {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            String hql = "from Customer ";
            Query query = session.createQuery(hql);
            list = query.list();

        } catch (RuntimeException e) {
            System.out.println("_____________________Can not Search_________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }
}
