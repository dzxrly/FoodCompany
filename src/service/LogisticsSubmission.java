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
        lg.setDeliveryDate(deliveryDate);//发货日期
        lg.setDestination(destination);

        try {
            tx = session.beginTransaction();
            //物流订单提交 应该把orders表的订单状态改为运输中
            hql = "update Orders set orderState = 2 where orderId = " + String.valueOf(lg.getOrderId());
            session.save(lg);
            Query query = session.createQuery(hql);
            query.executeUpdate();
            tx.commit();

        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public int LogisticsJudge(String orderId) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        int ans = 0;
        try {
            tx = session.beginTransaction();
            //物流订单提交 应该把orders表的订单状态改为运输中
            hql = "from Logistics where orderId = " + orderId;
            Query query = session.createQuery(hql);
            List list = query.list();
            if (list.toString() != "[]" && list != null) {
                ans = 0;//表示物流表里面有 无法修改
                System.out.println("__________________not null____________");
            } else {
                ans = 1;//表示物流表里面没有 需要生成
                System.out.println("_______________________null____________");
            }
        } catch (RuntimeException e) {
            ans = 0;
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }
}

//Test
//LogisticsSubmission ls = new LogisticsSubmission();
//ls.logisticsSubmit("20191217", 9, 0, "EMS", "2019-12-17", "泰安路89号");

