package service;

import DAO.HibernateUtils;
import model.ReturnRecord;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class OrderRefundSubmission {

    public int submitOrderRefund(String orderId, String customerName, int customerNumber, int stuffNumber, double totalReturnPrice) {//退款单提交 会在删除收入表income中与该orderId相关的行 更新orders表的payStatement字段为4表示已经退款 在退款记录表里面插入一条新的记录
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;

        try {
            tx = session.beginTransaction();
            //删除income表的记录
            String hql1 = "delete from Income i where i.orderId = " + orderId;
            Query query = session.createQuery(hql1);
            query.executeUpdate();
            //更新Orders表中的字段
            String hql2 = "update Orders set paymentState = 4 where orderId = " + orderId;
            query = session.createQuery(hql2);
            query.executeUpdate();
            //退款记录表中插入数据
            ReturnRecord rr = new ReturnRecord();
            rr.setOrderId(Integer.parseInt(orderId));
            rr.setCustomerName(customerName);
            rr.setCustomerNumber(customerNumber);
            rr.setStuffNumber(stuffNumber);
            rr.setTotalReturnPrice(totalReturnPrice);
            session.save(rr);

            ans = 1;//1表示提交成功
            tx.commit();
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            ans = 0;//0表示提交失败
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return ans;
        }

    }

    public int Judge(String orderId) {//首先根据单号判断是否在订单表中有 如果没有 返回1 否则查询 退货表中，若不在退货表 则不能退货 否则 提交退货信息
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        String hql = "";
        List list = null;

        try {
            hql = "from Orders where paymentState != 4 and orderId = " + orderId;//查询表中payment字段为4 且orderId 存在的记录
            list = session.createQuery(hql).list();
            if (!list.toString().equals("[]") && list != null) {//表示订单表里面有这个订单记录 那么就看是否满足退货条件 满足退货条件会在退货表里面生成一条记录
                String hql1 = "select r.id from ReturnBill r where r.orderId = " + orderId;
                List list1 = session.createQuery(hql1).list();
                if (!list1.toString().equals("[]") && list != null) {
                    System.out.println("____________________ ReturnBill have the data");
                    ans = 1;//1表示list中有数据 可以进行查询 可以退款
                } else {
                    System.out.println("____________________ ReturnBill don't have the data____________");
                    ans = 0;//0表示list为空 不可进行退款 不会进行查询 不能进行退款
                }
            } else {
                System.out.println("_____________________Can not refund Goods___________");
                ans = 2;//表示
            }
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit_________________");
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }

}


//        OrderRefundSubmission of=new OrderRefundSubmission();
//        of.submitOrderRefund("101","肖扬",11,10002,8548.98);
//        of.Judge("20");
