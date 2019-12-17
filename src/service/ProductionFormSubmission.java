package service;

import DAO.HibernateUtils;
import model.ProductionDetailForm;
import model.ProductionForm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ProductionFormSubmission {

    public ProductionForm createProductionForm(int orderId, String endTime, int stuffNumber) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String hql = "";
        ProductionForm pf = new ProductionForm();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String buildTime = df.format(new Date()).toString();//获得日期
        pf.setOrderId(orderId);
        pf.setBuildTime(buildTime);
        pf.setEndTime(endTime);
        pf.setStuffNumber(stuffNumber);
        pf.setPlanState(0);
        pf.setProductionState(0);

        try {
            tx = session.beginTransaction();
            //sum(应该生产数量/单位生产时间)
            hql = "select sum(o.producedQuantity/p.productQuantityPerTime) from OrderBookGoods o,ProductUnit  p where o.goodsNumber = p.goodsId and orderId = " + orderId;
            List list = session.createQuery(hql).list();
            Object ob = (Object) list.get(0);
            double userTime = Double.parseDouble(ob.toString()) / 24;
            pf.setUserTime(userTime);

            session.save(pf);
            System.out.println("_______________insert successfully 1________");
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("____________________Can not submit 1_________________");
            throw e;
        } finally {
            session.close();
            return pf;
        }
    }

    public int createProductionDetailForm(ProductionForm pf, int goodId, String goodsName, Double productionQuantity, Double stocks) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        ProductionDetailForm pdf = new ProductionDetailForm();
        pdf.setProductionId(pf.getProductionId());
        pdf.setOrderId(pf.getOrderId());
        pdf.setGoodsId(goodId);
        pdf.setGoodsName(goodsName);
        pdf.setProductionQuantity(productionQuantity);
        pdf.setStocks(stocks);
        try {
            tx = session.beginTransaction();
            session.save(pdf);
            ans = 1;
            System.out.println("_______________insert successfully 2________");
            tx.commit();
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit 2_________________");
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }
}

//        ProductionFormSubmission pfs=new ProductionFormSubmission();
//        ProductionForm pf = pfs.createProductionForm(21,"2019-12-14",10003);
//        for(int i=0;i<3;i++)
//            pfs.createProductionDetailForm(pf,1,"苹果",300,100);
