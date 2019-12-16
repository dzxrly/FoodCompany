package service;

import DAO.HibernateUtils;
import org.hibernate.Session;

import java.util.List;

//这个类里包含了所有生产车间的功能
public class ProductWorkshop {
    //原料申请界面功能
    public List materialAllSearch() {
        List list = null;
        String hql = "";
        Session session = HibernateUtils.openSession();
        try {
            hql = "from Material";
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("-----can not search-------");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List materialFuzzySearch(int index, String text) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {

            if (index == 0) { //用原料编号查询
                hql = "from Material where materialId=" + text;
            } else if (index == 1) {//用原料名称模糊查询
                hql = "from Material where materialName like '%" + text + "%'";
            }
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("-----can not search-------");
            throw e;
        } finally {
            session.close();
            return list;//返回null表示查无此原料
        }
    }

    public List productPlanSearchAll() {//返回生产计划表字段
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {
            hql = "from ProductPlan";
            list = session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("---can not search----");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }

    public List productPlanSearchById(int planId) {
        Session session = HibernateUtils.openSession();
        String hql = "";
        List list = null;
        try {
            hql = "from ProductPlan where planId=" + String.valueOf(planId);
            list=session.createQuery(hql).list();
        } catch (RuntimeException e) {
            System.out.println("---can not search----");
            throw e;
        } finally {
            session.close();
            return list;
        }
    }
    //提交按钮点击后的 字段更新 表更新 等等操作
    public void submit(){

    }
    //生产流程管理界面

    //产品销毁界面
}
