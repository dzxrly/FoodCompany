package service;

import DAO.HibernateUtils;
import model.ShippingDepartment;
import model.Stuff;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;


//成品库的操作函数
public class ShippingDepOperation {
    //出入库信息管理
    //查询
    public List stockSearchAll() {
        Session session = HibernateUtils.openSession();
        List list = null;
        String hql = "";
        try {
            hql = "from ShippingDepartment";
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("cannot search---");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List stockSearchByTest(int index, String test) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {
            if (index == 0) {//按照成品库商品id查
                hql = "from ShippingDepartment where goodsId=" + test;
            } else if (index == 1) {//按照成品库商品名字查
                hql = "from ShippingDepartment where goodsName like '%" + test + "%'";
            }
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("cannot search---");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    //修改
    public int stockUpdate(int goodsId, String state, double quantity) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        List list = null;
        int res = 0;//1为操作成功 0为操作失败
        ShippingDepartment sd = new ShippingDepartment();
        try {
            tx = session.beginTransaction();
            sd = session.get(ShippingDepartment.class, goodsId);
            hql = "select stocks from ShippingDepartment where goodsId=" + String.valueOf(goodsId);
            list = session.createQuery(hql).list();
            Object ob = (Object) list.get(0);
            if (state.equals("入库")) {
                quantity = quantity + Double.valueOf(ob.toString());
                sd.setStocks(quantity);
            } else if (state.equals("出库")) {
                quantity = Double.valueOf(ob.toString()) - quantity;
                sd.setStocks(quantity);
            }
            res=1;
            session.update(sd);
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("--cannot update---");
            throw e;
        } finally {
            session.close();
            return res;
        }
    }


    //提货信息管理
    // 第一个函数返回左边的各项信息 第二个函数返回右边的商品列表
    public List pickingManagement(int orderId) {
        Session session = HibernateUtils.openSession();
        List res = null;
        String hql = "";
        try {
            hql = "from Orders where orderId =" + orderId;
            Query query = session.createQuery(hql);
            res = query.list();
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            throw e;
        } finally {
            session.close();
            return res;
        }
    }

    public int getOrderType(int orderId) {
        Session session = HibernateUtils.openSession();
        List res = null;
        String hql = "";
        int type = -1;
        try {
            //tr=session.beginTransaction();
            hql = "select orderType from Orders where orderId =" + String.valueOf(orderId);
            Query query = session.createQuery(hql);
            res = query.list();
            Object ob = (Object) res.get(0);
            type = Integer.valueOf(ob.toString());
            //System.out.println(type);
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            throw e;
        } finally {
            session.close();
            return type;
        }
    }

    public List showGoodsList(int orderId) {
        Session session = HibernateUtils.openSession();
        List res = null;
        String hql = "";
        try {
            //System.out.println(type);
            //现货订单
            if (getOrderType(orderId) == 0) {
                hql = "from OrderSpotGoods where orderId=" + String.valueOf(orderId);
                res = session.createQuery(hql).list();
            }
            //预定订单
            else if (getOrderType(orderId) == 1) {
                hql = "from OrderBookGoods where orderId=" + String.valueOf(orderId);
                res = session.createQuery(hql).list();
            }
        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not search___________________________");
            throw e;
        } finally {
            session.close();
            return res;
        }
    }

    //生产入库数量管理
    public List searchPlanId(int planId) {
        Session session = HibernateUtils.openSession();
        List list = null;
        String hql = "";
        try {
            hql = "from ProductDetailPlan where planId=" + String.valueOf(planId);
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("---can not search---");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List searchAllId() {
        Session session = HibernateUtils.openSession();
        List list = null;
        String hql = "";
        try {
            hql = "from ProductDetailPlan";
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("---can not search---");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }
}
