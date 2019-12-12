package service;

import DAO.HibernateTest1;
import DAO.HibernateUtils;
import javafx.beans.property.StringProperty;
import model.OrderBookGoods;
import model.OrderSpotGoods;
import model.Orders;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersSubmission {

    public Orders createMainOrders(int orderType, String companyName, String customerName, String customerPhone, String customerAddress, double totalSum, int stuffNumber) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        Orders od = new Orders();
        int number = 0;
        String date = null;
        try {
            od.setOrderType(orderType);
            od.setCustomerName(customerName);
            od.setCustomerPhone(customerPhone);
            od.setCustomerAddress(customerAddress);
            od.setTotalSum(totalSum);
            od.setStuffNumber(stuffNumber);
            od.setCompanyName(companyName);
            od.setOrderState(0);
            od.setPaymentState(0);
            tx = session.beginTransaction();
            List list = session.createQuery("select c.number from Customer c where c.phoneNumber = " + customerPhone).list();
            for (int i = 0; i < list.size(); i++) {
                Object ob = (Object) list.get(i);
                number = (Integer) ob;//获得客户number
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            date = df.format(new Date()).toString();//获得日期
            od.setCustomerNumber(number);
            od.setBuildDate(date);
            session.save(od);
            System.out.println("______________________insert right!____________________");
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("______________________Can not Insert!____________________");
            throw e;
        } finally {
            session.close();
            return od;
        }
    }

    public int createSpotOrders(Orders od, int goodsNumber, String goodsName, int orderQuantity) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        OrderSpotGoods osg = new OrderSpotGoods();
        int ans = 0;
        try {
            osg.setOrderId(od.getOrderId());
            osg.setOrderType(od.getOrderType());
            osg.setStuffNumber(od.getStuffNumber());
            osg.setCustomerNumber(od.getCustomerNumber());
            osg.setGoodsName(goodsName);
            osg.setGoodsNumber(goodsNumber);
            osg.setOrderQuantity(orderQuantity);
            osg.setBuildDate(od.getBuildDate());

            tx = session.beginTransaction();
            session.save(osg);
            System.out.println("_____________________________Insert into OSG______________________________");
            ans = 1;//1表示提交成功

            //订单提交后 更新成品库表
            String hql = "Update ShippingDepartment set stocks = stocks - :orderQuantity where goodsId = :goodsNumber";
            Query query = session.createQuery(hql);
            query.setInteger("orderQuantity", orderQuantity);
            query.setInteger("goodsNumber", goodsNumber);
            query.executeUpdate();
            System.out.println("_____________________________Update on SD______________________________");
            tx.commit();

        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not Insert into OSG_______________________________");
            ans = 0;//0表示提交失败
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }

    public String CreateBookOrders(Orders od, int goodsNumber, String goodsName, int orderQuantity) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String lastDate = "";
        OrderBookGoods obg = new OrderBookGoods();
        try {
            obg.setOrderId(od.getOrderId());
            obg.setOrderType(od.getOrderType());
            obg.setStuffNumber(od.getStuffNumber());
            obg.setCustomerNumber(od.getCustomerNumber());
            obg.setGoodsName(goodsName);
            obg.setGoodsNumber(goodsNumber);
            obg.setOrderQuantity(orderQuantity);
            obg.setBuildDate(od.getBuildDate());

            tx = session.beginTransaction();
            session.save(obg);
            lastDate = "1";//表示提交成功

            //订单提交后，立马将某件商品的库存更新


            if (1 == 1) {//可在截至日期内完成，该工作由后台做 直接建订单表


            } else {//不可在截至日期完成，返回一个最快完成的流水线的日期

            }


            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return lastDate;
        }

    }


}

//Test 预定订单表的生成语句
//        OrdersSubmission Obs=new OrdersSubmission();
//        Orders od = Obs.createMainOrders(0,"李天添","17863107716","上海",7300,10002,"购物广场");
//        Obs.createSpotOrders(od,1,"香蕉",100);
//        Obs.createSpotOrders(od,2,"苹果",200);
//        Obs.createSpotOrders(od,3,"猪肉",100);


