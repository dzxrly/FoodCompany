package service;

import DAO.HibernateUtils;
import javafx.scene.control.TableRow;
import model.Income;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderPaymentSubmission {
    public int submitOrderPayment(int index, int stuffNumber, double incomePrice, int orderId, String payCardNumber) {//index 0表示值支付了预付款 1表示付了尾款 2表示一次性付了全款
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        String incomeItem = null;
        String paymentState = null;
        try {
            tx = session.beginTransaction();
            if (index == 0) {//0表示只付了预付款 收入表里面添加数据 order表的付款状态更新
                paymentState = "1";
            } else if (index == 1) {//表示付了尾款
                paymentState = "2";
            } else {
                paymentState = "2";
            }
            Income in = new Income();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String incomeDate = df.format(new Date()).toString();//获得日期
            System.out.println("____________________" + incomeDate);
            in.setStuffNumber(stuffNumber);
            in.setIncomePrice(incomePrice);// 总金额
            in.setIncomeTime(incomeDate);
            in.setIncomeType(index);//0表示值支付了预付款 1表示付了尾款 2表示一次性付了全款
            in.setIncomeItem(incomeItem);
            in.setOrderId(orderId);
            in.setPayCardNumber(payCardNumber);
            session.save(in);

            String hql = "update Orders set paymentState =" + paymentState + "where orderId =" + String.valueOf(in.getOrderId());//1表示付了预付款
            Query query = session.createQuery(hql);
            query.executeUpdate();
            System.out.println("_____________________update successful__________________");

            ans = 1;//1表示提交成功
            tx.commit();
        } catch (RuntimeException e) {
            ans = 0;//0表示提交失败
            System.out.println("______________________Failure______________");
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return ans;
        }

    }
}

//        OrderPaymentSubmission ops=new OrderPaymentSubmission();
//        ops.submitOrderPayment(1,3,1000,13,"381723781231");
