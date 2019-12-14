package service;

import DAO.HibernateUtils;
import DAO.PlusDay;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import model.FinancialTable;
import model.Income;
import model.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FinancialSheetSearch {

    public List searchId(String id, int index) {// index=0 0表示仅查询收入表 1表示仅查询支出表
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        String hql = "";
        List list = null;
        try {
            if(index == 0){
                hql = "select i.incomeId,i.incomeType,i.incomePrice,i.incomeTime from Income i where i.incomeId =" + id;
                list= session.createQuery(hql).list();
                System.out.println("_________OK!_______________" + (String) ((Object[])list.get(0))[3]);//如果list为空 ((Object[])list.get(0))[3]得不到就会报错
            }
            else{
                hql = "select paymentId,paymentType,paymentPrice,payTime from Payment where paymentId =" + id;
                list= session.createQuery(hql).list();
            }
//            FinancialTable f=(FinancialTable) list.get(0);
//            System.out.println("_________"+f.getDate()+"_______________");

        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public void searchAll(int index1, int index2) {// index1=0 0表示仅查询收入表 1表示仅查询支出表  index2=0表示全部 1表示近一个月 2表示近半年 3表示近一年

        Session session= HibernateUtils.openSession();;
        int ans=0;
        String hql="";

        try{
            if(index1 == 0){//表示收入表查询全部
                if(index2==0){//查询全部
                    hql="from Income";
                }else if(index2==1){//查询近一个月
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String nowDate = df.format(new Date()).toString();//获得日期
                    String startDate =PlusDay.plusDay(-30,nowDate);

                    hql="from Income where incomeTime >= " + startDate;
                    System.out.println(hql + "________"+startDate);
                }else if(index2==2){//查询近半年

                }else{//查询近一年

                }
            }
            else{//表示支出表查询全部被
                if(index2==0){

                }else if(index2==1){

                }else if(index2==2){

                }else{

                }
            }
            List list=session.createQuery(hql).list();
            for(int i=0;i<list.size();i++){
                if(index1==0){
                    Income in=(Income) list.get(i);
                    System.out.println("______"+in+"________");
                }else{
                    Payment pay=(Payment) list.get(i);
                    System.out.println("______"+pay+"________");
                }
            }

            ans=1;
        }catch(RuntimeException e){
            System.out.println("____________________Can not submit_________________");
            throw e;
        }finally {
            session.close();
            return ;
        }
    }
}


//        FinancialSheetSearch fsh=new FinancialSheetSearch();
//        fsh.searchId("7");


//                FinancialTable f= new FinancialTable();
//                Object[] ob = (Object[]) list.get(0);
//                f.setId((int) ob[0]);
//                f.setPrice((double) ob[1]);
//                f.setDate((String) ob[2]);
//                f.setType(1);

//
//    FinancialSheetSearch fsh = new FinancialSheetSearch();
//        fsh.searchAll(0,1);