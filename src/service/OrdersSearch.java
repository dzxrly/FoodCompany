package service;

import DAO.HibernateUtils;
import javafx.beans.property.*;
import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrdersSearch {

    public List OrderFuzzySearch(int index, String text) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        List list = null;

        try {
            if (index == 0) {//订单号查询
                hql = "from Orders where orderId =" + text;
            } else if (index == 1) {//客户名称
                hql = "from Orders where customerName like '%" + text + "%'";
            } else {//公司名称
                hql = "from Orders where companyName like '%" + text + "%'";
            }
            Query query = session.createQuery(hql);
            list = query.list();
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List searchAllOrders() {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        List list = null;
        try {
            hql = "from Orders";
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List searchSpotOrBookOrder(String orderId, String orderType) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        List list = null;
        try {
            if (orderType.equals("0")) {//0 现货订单的查询
                hql = "from OrderSpotGoods where orderId = " + orderId;
            } else {//1 预定订单的查询
                hql = "from OrderBookGoods where orderId = " + orderId;
            }
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public CustomerOrder searchCustomerAndOrder(String text) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        List list = null;
        CustomerOrder cto = new CustomerOrder();
        try {
            hql = "select o.orderId,c.personalName,c.companyName,c.number,c.level,c.address,c.email,c.phoneNumber,c.type,o.orderType,o.endDate,o.totalSum,o.orderState,o.paymentState,o.stuffNumber from Orders as o,Customer as c where o.customerPhone = c.phoneNumber and o.orderId = " + text;
            Query query = session.createQuery(hql);
            list = query.list();

            Object[] co = (Object[]) list.get(0);

            cto.setOrderId((int) co[0]);
            cto.setPersonalName((String) co[1]);
            cto.setCompanyName((String) co[2]);
            cto.setNumber((int) co[3]);
            cto.setLevel((int) co[4]);
            cto.setAddress((String) co[5]);
            cto.setEmail((String) co[6]);
            cto.setPhoneNumber((String) co[7]);
            cto.setType((int) co[8]);
            cto.setOrderType((int) co[9]);
            cto.setEndDate((String) co[10]);
            cto.setTotalSum((double) co[11]);
            cto.setOrderState((int) co[12]);
            cto.setPaymentState((int) co[13]);
            cto.setStuffNumber((int) co[14]);
//            System.out.println("_____________" + cto + "________________");
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            cto = null;
            throw e;
        } finally {
            session.close();
            return cto;
        }
    }


    public List searchOrderAndStocks(String orderId) {//查询 预定订单表与库存表中库存的连接结果 新生成类为 OrderStocks
        //订单需求量检查界面
        System.out.println("!!!!!");
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        try {
            tx = session.beginTransaction();
            String hql = "select sd.goodsId,gd.goodsName,obg.orderQuantity,gd.goodsPrice,sd.stocks from OrderBookGoods obg, ShippingDepartment sd ,Goods gd where obg.goodsNumber = sd.goodsId and sd.goodsId = gd.goodsId and obg.orderId = " + orderId;
            list = session.createQuery(hql).list();
            for (int i = 0; i < list.size(); i++) {
                Object[] ob = (Object[]) list.get(i);

                OrderStocks os = new OrderStocks();
                os.setGoodsId((int) ob[0]);
                os.setGoodsName((String) ob[1]);
                os.setOrderQuantity((int) ob[2]);
                os.setGoodsPrice((double) ob[3]);
                os.setStocks((int) ob[4]);
                System.out.println("___________" + os + "_________________");
            }

        } catch (RuntimeException e) {
            System.out.println("___________________Can not search_________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }
}


//    OrdersSearch os=new OrdersSearch();
//    os.searchCustomerAndOrder("11");


// test searchOrderAndStocks
//        OrdersSearch os =new OrdersSearch();
//        List list = os.searchOrderAndStocks("21");
//        if(list.toString() !="[]"){
//            System.out.println("_________not null__________");
//        }

//    Session session = HibernateUtils.openSession();
//    String sql="";
//    List list=null;
//        try{
//
//                }catch (RuntimeException e){
//                System.out.println("---can not search----");
//                throw e;
//                }finally {
//                session.close();
//                return list;
//                }