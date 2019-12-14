package service;

import DAO.HibernateUtils;
import DAO.PlusDay;
import org.hibernate.Session;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductionFormSearch {//生产财务规划界面的部分功能

    public List searchProductionForm(int index){
        Session session= HibernateUtils.openSession();
        String hql="";
        List list=null;
        try{
            if(index == 0){
                hql="from ProductionForm";
            }else if(index ==1){//
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String nowDate = df.format(new Date()).toString();//获得日期
                String startDate = PlusDay.plusDay(-30, nowDate);
                System.out.println("________" + startDate);
                hql = "from ProductionForm where buildTime > date_format('" + startDate + "','%Y-%m-%d %H:%i:%s')";
            }else if(index == 2){
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String nowDate = df.format(new Date()).toString();//获得日期
                String startDate = PlusDay.plusDay(-180, nowDate);
                System.out.println("________" + startDate);
                hql = "from ProductionForm where buildTime > date_format('" + startDate + "','%Y-%m-%d %H:%i:%s')";
            } else{
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String nowDate = df.format(new Date()).toString();//获得日期
                String startDate = PlusDay.plusDay(-365, nowDate);
                System.out.println("________" + startDate);
                hql = "from ProductionForm where buildTime > date_format('" + startDate + "','%Y-%m-%d %H:%i:%s')";
            }

            list = session.createQuery(hql).list();
        }catch (RuntimeException e){
            throw e;
        }
        finally {
            session.close();
            return list;
        }
    }

}
