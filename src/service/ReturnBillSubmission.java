package service;

import DAO.HibernateUtils;
import model.Logistics;
import model.ReturnBill;
import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ReturnBillSubmission {

    public int submitReturnBill(int stuffNumber, int customerId, int orderId, String note, int isQuality) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        ReturnBill rb = new ReturnBill();
        rb.setStuffNumber(stuffNumber);
        rb.setCustomerId(customerId);
        rb.setOrderId(orderId);
        rb.setNote(note);
        rb.setIsQuality(isQuality);
        int ans = 0;
        try {
            tx = session.beginTransaction();
            session.save(rb);
            tx.commit();
            ans = 1;//1表示提交成功
            System.out.println("_____________Successful———————————");
        } catch (RuntimeException e) {
            ans = 0;//0表示提交失败
            System.out.println("_____________Error———————————");
            tx.rollback();
        } finally {
            session.close();
            return ans;
        }
    }

}

//        ReturnBillSubmission rb=new ReturnBillSubmission();
//        rb.submitReturnBill(10002,1,23,"商品质量出现问题");
