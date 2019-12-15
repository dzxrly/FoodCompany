package service;

import DAO.HibernateUtils;
import model.Income;
import model.Payment;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BillSubmission {

    public int submitBill(int orderType, int stuffNumber, double Price, int Type, int orderId, String payCardNumber, String Item) {//Type 对应收入或支出下拉框里面的的东西
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = 0;
        try {
            tx=session.beginTransaction();
            if (orderType == 0) {//0为收入账单
                Income in = new Income();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String incomeDate = df.format(new Date()).toString();//获得日期
                System.out.println("____________________" + incomeDate);
                in.setStuffNumber(stuffNumber);
                in.setIncomePrice(Price);// 总金额
                in.setIncomeTime(incomeDate);
                in.setIncomeType(Type);//0表示值支付了预付款 1表示付了尾款 2表示一次性付了全款
                in.setIncomeItem(Item);
                in.setOrderId(orderId);
                in.setPayCardNumber(payCardNumber);
                session.save(in);
                System.out.println("___________________Insert Successfully______________");
            } else {//1为支出账单
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String paymentDate = df.format(new Date()).toString();//获得日期
                Payment p = new Payment();
                p.setStuffNumber(stuffNumber);
                p.setPaymentPrice(Price);
                p.setPayTime(paymentDate);
                p.setPaymentType(Type);
                p.setPaymentItems(Item);
                session.save(p);
                System.out.println("___________________Insert Successfully______________");
            }
            ans = 1;//1表示创建成功
            tx.commit();
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit1_________________");
            tx.rollback();
            ans = 0;//0表示不能创建
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }

    public int Judge(String orderId) {//在收入账单时 创建完点击提交订单编号先判断 若orders表中有 则返回创建失败 订单编号重复 返回一个最大的编号值 否则返回-1
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        int ans = -1;
        String hql = "";
        try {
            hql = "from Orders where orderId = " + orderId;
            List list = session.createQuery(hql).list();
            if (!list.toString().equals("[]") && list != null) {//表示存在这个订单号 创建失败 返回最大的订单号 + 1
                hql = "select max(orderId) from Orders";
                List list1 = session.createQuery(hql).list();
                Object ob = (Object) list1.get(0);
                ans = (int) ob + 1;
                System.out.println("__________________________" + ans + "________________________________");
            } else {
                return -1;
            }
        } catch (RuntimeException e) {
            System.out.println("____________________Can not submit2_________________");
            throw e;
        } finally {
            session.close();
            return ans;// -1表示可以进行收入账单的创建 其他值表示不可创建订单 返回一个订单编号的最大值
        }
    }

}

//Test
//        BillSubmission bs=new BillSubmission();
//        bs.Judge("11");
//        System.out.println(bs.submitBill(1,10002,54232.2,1,17,"13212738123","买了十吨椰子皮"));
