package service;

import DAO.HibernateUtils;
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

}
