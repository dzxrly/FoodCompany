package service;

import DAO.HibernateUtils;
import com.mysql.cj.x.protobuf.MysqlxExpr;
import model.Material;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MaterialManagement {

    public List searchMaterial(String materialId){//根据id精确查询
        Session session= HibernateUtils.openSession();
        Transaction tx=null;
        int ans=0;
        String hql="";
        List list= null;

        try{
            tx=session.beginTransaction();
            hql="from Material where materialId = " +materialId;
            list = session.createQuery(hql).list();
            ans=1;
            tx.commit();
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            ans = 0;
            throw e;
        }finally {
            session.close();
            return list;
        }
    }


    public List searchMaterialAll(){

        Session session= HibernateUtils.openSession();
        Transaction tx=null;
        int ans=0;
        String hql="";
        List list= null;

        try{
            tx=session.beginTransaction();
            hql="from Material" ;
            list = session.createQuery(hql).list();
            ans=1;
            tx.commit();
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            ans = 0;
            throw e;
        }finally {
            session.close();
            return list;
        }
    }

    public int AddMaterial(String materialName,double materialPrice,int stuffNumber,int materialQualityTime,int materialType,double stocks){
        Session session= HibernateUtils.openSession();
        Transaction tx=null;
        int ans=0;
        Material m =new Material();

        m.setMaterialName(materialName);
        m.setMaterialPrice(materialPrice);
        m.setStuffNumber(stuffNumber);
        m.setMaterialQualityTime(materialQualityTime);
        m.setMaterialType(materialType);
        m.setStocks(stocks);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String storeTime = df.format(new Date()).toString();//获得日期
        m.setStoreTime(storeTime);
        try{
            tx=session.beginTransaction();
            session.save(m);
            ans=1;//1表示成功
            tx.commit();
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            ans =0;//0表示失败
            throw e;
        }finally {
            session.close();
            return ans;
        }
    }

    public int UpdateMaterial(int materialId,String materialName,double materialPrice,int stuffNumber,int materialQualityTime,int materialType,double stocks){
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans =0 ;

        Material m =new Material();
        m.setMaterialId(materialId);
        m.setMaterialName(materialName);
        m.setMaterialPrice(materialPrice);
        m.setStuffNumber(stuffNumber);
        m.setMaterialQualityTime(materialQualityTime);
        m.setMaterialType(materialType);
        m.setStocks(stocks);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String storeTime = df.format(new Date()).toString();//获得日期
        m.setStoreTime(storeTime);

        try {
            tx = session.beginTransaction();
            session.update(m);// 操作
            ans =1;
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            ans =0  ;
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }

    public List productionPlanMaterialSearch(String planId){//用于生产车间的原料申请 显示一个生产计划所需的 原料名称 原料的数量 原料单位 原料库存量 原料单位
        Session session= HibernateUtils.openSession();
        Transaction tx=null;
        String hql="";
        List list= null;
        try{
            tx=session.beginTransaction();
            hql ="select productionId from ProductPlan where planId = " +planId;
            list = session.createQuery(hql).list();
            Object ob = (Object) list.get(0);
            String productionId = ob.toString();
            System.out.println("_____________" + productionId);
            hql ="select DISTINCT m.materialName,p.productionQuantity * m.perQuantity,t.materialUnit, m.materialStocks,t.materialUnit from ProductionDetailForm p,MaterialToGoods m,Material t where p.goodsId = m.goodsId and m.materialId = t.materialId and p.productionId = "+ productionId;
            list = session.createQuery(hql).list();
            tx.commit();
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            throw e;
        }finally {
            session.close();
            return list;
        }

    }

}


//        MaterialManagement m =new MaterialManagement();
//        m.AddMaterial("膨化剂量",20,10002,365,1,1000);
//        m.UpdateMaterial(9,"硼化剂",10,10003,365,1,200);



//test    productionPlanMaterialSearch
//        MaterialManagement m = new MaterialManagement();
//        List list  = m.productionPlanMaterialSearch("6");
//        for(int i=0;i<list.size();i++){
//            Object[] ob1 = (Object [])list.get(i);
//            System.out.println(ob1[0].toString()+ob1[1].toString()+ob1[2].toString()+ob1[3].toString()+ob1[4].toString());
//        }