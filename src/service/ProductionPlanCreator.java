package service;

import DAO.HibernateUtils;
import model.ProductPlan;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.DecimalFormat;
import java.util.List;

public class ProductionPlanCreator {//生产计划管理界面

    public int createProdcutionPlan(int productionId, int stuffNumber, String startTime, String endTime, String orderId) {// 创建生产计划表 更新productionForm的planState置1 更新productionState置1

        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        String hql = "";
        ProductPlan p = new ProductPlan();
        p.setProductionId(productionId);
        p.setStuffNumber(stuffNumber);
        p.setStartTime(startTime);
        p.setEndTime(endTime);
        p.setProductionState(0);
        if (orderId != "0") {
            p.setPlanId(0);
        } else {
            p.setPlanType(1);
        }
        try {
            tx = session.beginTransaction();
            //生产生产计划表
            hql = "select sum(o.producedQuantity/p.productQuantityPerTime) from OrderBookGoods o,ProductUnit  p where o.goodsNumber = p.goodsId and orderId = " + orderId;
            List list = session.createQuery(hql).list();
            Object ob = (Object) list.get(0);
            double cycle = Double.parseDouble(ob.toString()) / 24;
            DecimalFormat df = new DecimalFormat("#.00");
            cycle = Double.parseDouble(df.format(cycle));

            System.out.println(cycle);
            p.setProductionCycle(cycle);
            session.save(p);
            //更新生产表单中的两个状态
            hql = "update ProductionForm set planState = 1,productionState = 1 where productionId = " + productionId;
            Query query = session.createQuery(hql);
            query.executeUpdate();

            ans = 1;
            tx.commit();
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            ans = 0;
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }

}


//        ProductionPlanCreator p = new ProductionPlanCreator();
//        p.createProdcutionPlan(6,10002,"2019-12-18","2019-12-21","21");
