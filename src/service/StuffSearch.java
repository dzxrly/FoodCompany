package service;

import DAO.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

//员工信息管理界面的员工搜索
public class StuffSearch {
    public List searchAll(int index,String test){
        Session session = HibernateUtils.openSession();
        String sql="";
        List list=null;
        try{
            if(index==0){//员工id

            }
            else if(index==1){//员工姓名

            }
            else if(index==2){

            }
        }catch (RuntimeException e){
            System.out.println("---can not search----");
            throw e;
        }finally {
            session.close();
            return list;
        }
    }

    public List searchByIndex(int index,String test){
        Session session = HibernateUtils.openSession();
        String sql="";
        List list=null;
        try{
            if(index==0){//员工id

            }
            else if(index==1){//员工姓名

            }
            else if(index==2){

            }
        }catch (RuntimeException e){
            System.out.println("---can not search----");
            throw e;
        }finally {
            session.close();
            return list;
        }
    }
}
