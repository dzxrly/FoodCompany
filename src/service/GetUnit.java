package service;

import DAO.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

//传相应id获取单位
public class GetUnit {
    public String getMaterialUnit(int materialId){
        Session session = HibernateUtils.openSession();
        String hql="";
        String unit="";
        List list=null;
        try{
            hql="select materialType from material where materialId="+String.valueOf(materialId);
            list=session.createQuery(hql).list();
            Object ob =(Object) list.get(0);
            unit=ob.toString();
        }catch (RuntimeException e){
            System.out.println("--cannot get----");
            throw e;
        }finally {
            session.close();
            return unit;
        }
    }

    public String getGoodsUnit(int goodsId){
        Session session = HibernateUtils.openSession();
        String hql="";
        String unit="";
        try{

        }catch (RuntimeException e){
            System.out.println("--cannot get----");
            throw e;
        }finally {
            session.close();
            return unit;
        }
    }

    public String getStockUnit(int goodsId){
        Session session = HibernateUtils.openSession();
        String hql="";
        String unit="";
        try{

        }catch (RuntimeException e){
            System.out.println("--cannot get----");
            throw e;
        }finally {
            session.close();
            return unit;
        }
    }
}
