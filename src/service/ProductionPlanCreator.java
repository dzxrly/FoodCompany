package service;

import DAO.HibernateUtils;
import model.ProductDetailPlan;
import model.ProductPlan;
import model.ProductionDetailForm;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductionPlanCreator {//生产计划管理界面

    public ProductPlan createProductionPlan(int productionId, int stuffNumber, String startTime, String endTime, String orderId) {// 创建生产计划表 更新productionForm的planState置1 更新productionState置1
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
        p.setOrderId(Integer.valueOf(orderId));

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String date = dt.format(new Date()).toString();//获得日期
        p.setBuildTime(date);

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
            p = null;
            throw e;
        } finally {
            session.close();
            return p;
        }
    }


    //创建细节表
    public void createProductionDetailPlan(ProductPlan p, String productionId) {//p是刚刚建好的ProductionPlan主表 productionId是productionDetailForm表的 生产编号
        Session session = HibernateUtils.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            String hql = "from ProductionDetailForm  where productionId = " + productionId;
            List list = session.createQuery(hql).list();
            for (int i = 0; i < list.size(); i++) {
                ProductDetailPlan pd = new ProductDetailPlan();
                ProductionDetailForm pdf = (ProductionDetailForm) list.get(i);
                pd.setPlanId(p.getPlanId());
                pd.setPlanType(p.getPlanType());
                pd.setGoodsId(pdf.getGoodsId());
                pd.setGoodsName(pdf.getGoodsName());
                pd.setGoodsUnit(pdf.getGoodsUnit());
                pd.setQuantity(pdf.getProductionQuantity());
                pd.setStuffNumber(p.getStuffNumber());
                session.save(pd);
            }
            tx.commit();
        } catch (RuntimeException e) {


            tx.rollback();
        } finally {

            session.close();
        }
    }

}


//        ProductionPlanCreator p = new ProductionPlanCreator();
//        p.createProdcutionPlan(6,10002,"2019-12-18","2019-12-21","21");

//        ProductionPlanCreator p = new ProductionPlanCreator();
//        ProductPlan pp = p.createProdcutionPlan(5, 10006, "2019-12-18", "2019-12-30", "21");
//        p.createProductionDetailPlan(pp,"5");
