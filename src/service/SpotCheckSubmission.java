package service;

import DAO.HibernateUtils;
import model.SpotCheckForm;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SpotCheckSubmission {
    public int submitSpotCheck(int planId, String goodsName, double sumQuantity, String goodsUnit, double checkQuantity, double unqualifiedQuantity, double passRate, int isQualified, int stuffNumber, String date) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;

        SpotCheckForm s = new SpotCheckForm();
        s.setPlanId(planId);
        s.setSumQuantity(sumQuantity);
        s.setCheckQuantity(checkQuantity);
        s.setUnqualifiedQuantity(unqualifiedQuantity);
        s.setPassRate(passRate);
        s.setIsQualified(isQualified);
        s.setStuffNumber(stuffNumber);

        SimpleDateFormat df = new SimpleDateFormat(" HH:mm:ss");//设置日期格式
        String outTime = df.format(new Date()).toString();//获得日期
        date = date + outTime;
        System.out.println(date);

        s.setDate(date);
        s.setGoodsName(goodsName);
        s.setGoodsUnit(goodsUnit);


        try {
            tx = session.beginTransaction();
            session.save(s);
            ans = 1;//1表示提交成功
            tx.commit();
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            tx.rollback();
            ans = 0;// 0表示提交失败
            throw e;
        } finally {
            session.close();
            return ans;
        }

    }

}
