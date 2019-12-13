package service;

import DAO.HibernateUtils;
import javafx.beans.property.*;
import model.CustomerOrder;
import model.OrderBookGoods;
import model.OrderSpotGoods;
import model.Orders;
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
            if (orderType == "0") {//0 现货订单的查询
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

    public List searchCustomerAndOrder(String text) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        List list = null;

        try {
            hql = "select o.orderId,c.personalName,c.companyName,c.number,c.level,c.address,c.email,c.phoneNumber,c.type,o.orderType,o.endDate,o.totalSum,o.orderState,o.paymentState,o.stuffNumber from Orders as o,Customer as c where o.customerPhone = c.phoneNumber and o.orderId = " + text;
            Query query = session.createQuery(hql);
            list = query.list();
//            CustomerOrder cto = new CustomerOrder();
//            Object[] co = (Object[]) list.get(0);
//            cto.setOrderId((int) co[0]);
//            cto.setPersonalName((String) co[1]);
//            cto.setCompanyName((String) co[2]);
//            cto.setNumber((int) co[3]);
//            cto.setLevel((int) co[4]);
//            cto.setEmail((String) co[5]);
//            cto.setPersonalName((String) co[6]);
//            cto.setType((int) co[7]);
//            System.out.println("_____________" + cto + "________________");
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }
}


//    OrdersSearch os=new OrdersSearch();
//    os.searchCustomerAndOrder("11");