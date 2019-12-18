package service;

import DAO.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class MaterialApplySubmission {//用于生产车间 原料申请的提交 提交只更新库存

    public int submitMaterialApply(String planId, String materialId, String quantity) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        String hql = "";
        List list = null;

        try {
            tx = session.beginTransaction();
            hql = "update Material set stocks = stocks -" + quantity + "where materialId =  " + materialId;
            session.createQuery(hql).executeUpdate();

            ans = 1;
            tx.commit();
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            ans = 0;
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }
}


//        MaterialApplySubmission m =new MaterialApplySubmission();
//        m.submitMaterialApply("6","3","100");
