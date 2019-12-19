package service;

import DAO.HibernateUtils;
import model.WorkshopToStockRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DeliveryToStock {

    public int delivery(int planId, int planType, int orderId, int deliveryStuffNumber, double cycle) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        String hql = "";
        WorkshopToStockRecord w = new WorkshopToStockRecord();
        try {
            tx = session.beginTransaction();

            w.setPlanId(planId);
            w.setPlanType(planType);
            w.setOrderId(orderId);
            w.setDeliverStuffNumber(deliveryStuffNumber);

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String outTime = df.format(new Date()).toString();//获得日期
            w.setOutTime(outTime);
            w.setProductCycle(cycle);

            session.save(w);
            ans = 1;//1表示成功
            tx.commit();
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            ans = 0;//0表示失败
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }
}

//        DeliveryToStock d =new DeliveryToStock();
//        d.delivery(10,1,21,10003,2.3);
