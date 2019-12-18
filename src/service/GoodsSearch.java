package service;

import DAO.HibernateUtils;
import model.AssemblyLine;
import model.Goods;
import model.ShippingDepartment;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class GoodsSearch {
    public List searchGoods(String name) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        String hql = null;
        try {

            tx = session.beginTransaction();
            if (name != null)
                hql = "select g.goodsId,g.goodsName,g.goodsPrice,g.goodsQualityTime,s.stocks,g.goodsUnit,g.requiredQuantity from Goods as g,ShippingDepartment as s where g.goodsId=s.goodsId and g.goodsName like '%" + name + "%'";
            else
                hql = "select g.goodsId,g.goodsName,g.goodsPrice,g.goodsQualityTime,s.stocks,g.goodsUnit,g.requiredQuantity from Goods as g,ShippingDepartment as s where g.goodsId=s.goodsId";

            Query query = session.createQuery(hql);
            list = query.list();
        } catch (RuntimeException e) {
            System.out.println("_____________Can not search_____________________");
        } finally {
            session.close();
            return list;
        }
    }

    public List searchGoodsByName(String name) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        String hql = null;
        try {

            tx = session.beginTransaction();
            if (name != null)
                hql = "from Goods where goodsName like '%" + name + "%'";
            else
                hql = "from Goods";

            Query query = session.createQuery(hql);
            list = query.list();
        } catch (RuntimeException e) {
            System.out.println("_____________Can not search_____________________");
        } finally {
            session.close();
            return list;
        }
    }

    public Double searchExactGoods(String goodsId) {
        Session session = HibernateUtils.openSession();
        Double price = 0.0;
        String hql = "";

        try {
            hql = "select goodsPrice from Goods where goodsId =" + goodsId;
            List list = session.createQuery(hql).list();
            Object ob = (Object) list.get(0);

            price = (double) ob;
            System.out.println("____________" + price + "______________");
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            throw e;
        } finally {
            session.close();
            return price;
        }
    }

    public Goods searchGoodsById(int goodsId) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        Goods goods = new Goods();
        try {
            goods = session.get(Goods.class, goodsId);
        } catch (RuntimeException e) {
            goods = null;
            throw e;
        } finally {
            session.close();
            return goods;
        }
    }

    public ShippingDepartment searchStockInfoByGoodsId(int goodsId) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        ShippingDepartment shippingDepartment = new ShippingDepartment();
        try {
            shippingDepartment = session.get(ShippingDepartment.class, goodsId);
        } catch (RuntimeException e) {
            shippingDepartment = null;
            throw e;
        } finally {
            session.close();
            return shippingDepartment;
        }
    }

    public List searchAllGoods() {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        String hql = null;
        try {
            tx = session.beginTransaction();
            hql = "select g.goodsId,g.goodsName,g.goodsPrice,g.goodsQualityTime,s.stocks,g.goodsUnit,g.requiredQuantity from Goods as g,ShippingDepartment as s where g.goodsId=s.goodsId";
            Query query = session.createQuery(hql);
            list = query.list();
        } catch (RuntimeException e) {
            System.out.println("_____________Can not search_____________________");
        } finally {
            session.close();
            return list;
        }
    }

    public List getAllGoods() {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        List list = null;
        String hql = null;
        try {
            hql = "from Goods";
            Query query = session.createQuery(hql);
            list = query.list();
        } catch (RuntimeException e) {
            System.out.println("_____________Can not search_____________________");
        } finally {
            session.close();
            return list;
        }
    }
}
