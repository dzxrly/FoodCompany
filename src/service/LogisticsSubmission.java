package service;

import DAO.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogisticsSubmission {

    public void logisticsSubmit(String logisticsNumber,int orderId,int logisticsType,String logisticsCompanyName,String deliveryDate,String destination) throws ParseException {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        Date deliverydate=null;

//        private StringProperty logisticsNumber = new SimpleStringProperty();
//        private IntegerProperty logisticsType = new SimpleIntegerProperty();
//        private StringProperty companyName = new SimpleStringProperty();
//        private StringProperty deliveryDate = new SimpleStringProperty();
//        private StringProperty destination = new SimpleStringProperty();
//        private IntegerProperty orderId = new SimpleIntegerProperty();
        try{
            tx=session.beginTransaction();
            DateFormat format= new SimpleDateFormat("yyyy-MM-dd");
            try {
                deliverydate = format.parse(deliveryDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hql="update Logistics set logisticsNumber=:logisticsNumber, logisticsType=:logisticsType,companyName=:logisticsCompanyName,deliveryDate=:deliberyDate,destination=:destination,orderId=:orderId";
            Query query =session.createQuery(hql);
            query.setString("logisticsNumber",logisticsNumber);//物流单编号
            query.setInteger("orderId",orderId);
            query.setInteger("logisticsType",logisticsType);//物流类型0为普通陆运 1为海运 2为空运
            query.setString("logisticsCompanyName",logisticsCompanyName);
            //query.setCalendarDate("deliveryDate",deliverydate);
            query.setString("destination",destination);
            query.executeUpdate();
            tx.commit();

        }catch (RuntimeException e){
            tx.rollback();
            throw e;
        }
        finally {
            session.close();
        }
    }
}


//Test
//    LogisticsSubmission ls=new LogisticsSubmission();
//        ls.logisticsSubmit("20191211",9,0,"顺丰","2019-12-13","德州路23号");