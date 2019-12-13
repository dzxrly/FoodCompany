package service;

import DAO.HibernateUtils;
import model.Logistics;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogisticsSubmission {

    public void logisticsSubmit(String logisticsNumber, int orderId, int logisticsType, String logisticsCompanyName, String deliveryDate, String destination) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        Logistics lg = new Logistics();
        lg.setLogisticsNumber(logisticsNumber);
        lg.setOrderId(orderId);
        lg.setLogisticsType(logisticsType);
        lg.setCompanyName(logisticsCompanyName);
        lg.setDeliveryDate(deliveryDate);
        lg.setDestination(destination);

        try {
            tx = session.beginTransaction();
            session.save(lg);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}

//Test
//        LogisticsSubmission ls=new LogisticsSubmission();
//        ls.logisticsSubmit("20191211",9,0,"顺丰","2019-12-13","德州路23号");
