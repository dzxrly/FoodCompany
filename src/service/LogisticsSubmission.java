package service;

import DAO.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class LogisticsSubmission {

    public List logisticsSubmit(){
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        List list = null;

//        private IntegerProperty logisticsId = new SimpleIntegerProperty();
//        private StringProperty logisticsNumber = new SimpleStringProperty();
//        private IntegerProperty logisticsType = new SimpleIntegerProperty();//物流类型0为普通陆运 1为海运 2为空运
//        private StringProperty companyName = new SimpleStringProperty();
//        private StringProperty deliveryDate = new SimpleStringProperty();
//        private StringProperty destination = new SimpleStringProperty();
//        private IntegerProperty orderId = new SimpleIntegerProperty();
        try{
            hql="update Logistics set logisticsId=:logisticsId ,";

        }catch (RuntimeException e){
            throw e;
        }
        finally {
            session.close();
            return list;
        }
    }
}
