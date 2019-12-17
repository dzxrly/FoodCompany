package service;

import DAO.HibernateUtils;
import model.AssemblyLine;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductionManagement {//用于生产分配界面

    public List SearchProductionPlan(String productionId){//搜索一个生产计划
        Session session= HibernateUtils.openSession();
        String hql="";
        List list =null;

        try{
            hql="from ProductPlan  where productionId = " + productionId;
            list = session.createQuery(hql).list();

            System.out.println("____________________OK!_________________");
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            throw e;
        }finally {
            session.close();
            return list;
        }
    }

    public List SearchAllProductionPlan(){//搜索全部的生产计划
        Session session= HibernateUtils.openSession();
        String hql="";
        List list =null;

        try{
            hql="from ProductPlan";
            list = session.createQuery(hql).list();

            System.out.println("____________________OK!_________________");
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            throw e;
        }finally {
            session.close();
            return list;
        }
    }

    public List RefreshAssembleLine(){//刷新流水线
        Session session= HibernateUtils.openSession();
        String hql="";
        List list =null;

        try{
            hql="from AssemblyLine";
            list = session.createQuery(hql).list();

            System.out.println("____________________OK!_________________");
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            throw e;
        }finally {
            session.close();
            return list;
        }
    }

    public int submitProductionPlanToAssemblyLine(String lineNumber, String planId,String startWorkTime,String endWorkTime,String priority,String stuffNumber){
        Session session= HibernateUtils.openSession();
        Transaction tx=null;
        int ans=0;
        String hql="";
        try{
            tx=session.beginTransaction();
            SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");//设置日期格式
            String Date = df.format(new Date()).toString();//获得当前的时分秒
//            startWorkTime = startWorkTime + Date;


            hql = "update AssemblyLine set planId ="+planId+",lineState = 0,startWorkTime = "+startWorkTime+",endWorkTime = "+endWorkTime+",priority ="+priority+",stuffNumber ="+stuffNumber+" where lineNumber  =" +lineNumber;
            Query query  = session.createQuery(hql);
            query.executeUpdate();
            System.out.println("____________________Update AssemblyLine_________________");
            hql = "update ProductPlan set productionState = 2 where planId = "+planId;
            query =session.createQuery(hql);
            query.executeUpdate();
            System.out.println("____________________Update ProductionPlan_________________");
            hql = "select pf.orderId from ProductPlan p,ProductionForm  pf where p.productionId =pf.productionId  and p.planId = "+planId;
            List list =session.createQuery(hql).list();
            Object ob =(Object) list.get(0);
            String orderId =ob.toString();
            System.out.println("____________________Select orderId"+orderId+"________________");
            hql ="update Orders set orderState = 1 where orderId = " +orderId;
            query =session.createQuery(hql);
            query.executeUpdate();
            System.out.println("_____________________Update Orders_______________");

            ans=1;
            tx.commit();
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            ans =0;
            throw e;
        }finally {
            session.close();
            return ans;
        }

    }

}
