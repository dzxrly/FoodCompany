package service;

import DAO.HibernateUtils;
import model.AssemblyLine;
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
                hql = "select g.goodsId,g.goodsName,g.goodsPrice,g.goodsQualityTime,s.stocks,g.goodsUnit from Goods as g,ShippingDepartment as s where g.goodsId=s.goodsId and g.goodsName like '%" + name + "%'";
            else
                hql = "select g.goodsId,g.goodsName,g.goodsPrice,g.goodsQualityTime,s.stocks,g.goodsUnit from Goods as g,ShippingDepartment as s where g.goodsId=s.goodsId";

            Query query = session.createQuery(hql);
            list = query.list();

//            for (int i = 0; i < list.size(); i++) {
//                Object[] obj = (Object[]) list.get(i);
//
//                String s1 = ((Integer) obj[0]).toString();//goodsId
//                String s2 = (String) obj[1];//goodsName
//                String s3 = ((Double) obj[2]).toString();//goodsPrice
//                String s4 = ((Integer) obj[3]).toString();//goodsQualityTime 保质期
//                String s5 = ((Integer) obj[4]).toString();//stocks
//
//                System.out.println(s1 + s2 + s3 + s4 + s5);
//            }
        } catch (RuntimeException e) {
            System.out.println("_____________Can not search_____________________");
        } finally {
            session.close();
            return list;
        }
    }

    public Double searchExactGoods(String goodsId){
        Session session= HibernateUtils.openSession();
        Double price = 0.0;
        String hql="";

        try{
            hql="select goodsPrice from Goods where goodsId ="+ goodsId;
            List list = session.createQuery(hql).list();
            Object ob= (Object) list.get(0);

            price=(double) ob;
            System.out.println("____________"+ price+"______________");
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            throw e;
        }finally {
            session.close();
            return price;
        }
    }
}
