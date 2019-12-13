package service;

import DAO.HibernateUtils;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import model.FinancialTable;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class FinancialSheetSearch {

    public void searchId(String id) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        String hql = "";
        List list = null;
        try {
            hql="select i.incomeId,i.incomePrice from Income i where i.incomeId =" + id;
            list = session.createQuery(hql).list();
            for(int i=0;i<list.size();i++){
                Object[] ob= (Object[]) list.get(i);
                int Id=(int) ob[0];
                double Price=(double) ob[1];
                System.out.println("____________"+Id+"________"+Price+"__________");
            }
            hql="select paymentId,paymentPrice from Payment where paymentId =" + id;
            list = session.createQuery(hql).list();
            for(int i=0;i<list.size();i++){
                Object[] ob= (Object[]) list.get(i);
                int Id=(int) ob[0];
                double Price=(double) ob[1];
                System.out.println("____________"+Id+"________"+Price+"__________");
            }

        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            throw e;
        } finally {
            session.close();
        }
    }
}


//        FinancialSheetSearch fsh=new FinancialSheetSearch();
//        fsh.searchId("7");
