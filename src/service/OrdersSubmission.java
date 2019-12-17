package service;

import DAO.HibernateUtils;
import DAO.PlusDay;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.query.Query;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrdersSubmission {

    public Orders createMainOrders(int orderType, String companyName, String customerName, String customerPhone, String customerAddress, double totalSum, int stuffNumber, String endDate) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        Orders od = new Orders();
        int number = 0;
        String date = null;
        try {
            od.setOrderType(orderType);
            od.setCustomerName(customerName);
            od.setCustomerPhone(customerPhone);
            od.setCustomerAddress(customerAddress);
            od.setTotalSum(totalSum);
            od.setStuffNumber(stuffNumber);
            od.setCompanyName(companyName);
            od.setEndDate(endDate);
            od.setOrderState(0);
            od.setPaymentState(0);
            tx = session.beginTransaction();
            List list = session.createQuery("select c.number from Customer c where c.phoneNumber = " + customerPhone).list();
            for (int i = 0; i < list.size(); i++) {
                Object ob = (Object) list.get(i);
                number = (Integer) ob;//获得客户number
            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            date = df.format(new Date()).toString();//获得日期
            od.setCustomerNumber(number);
            od.setBuildDate(date);
            session.save(od);
            System.out.println("______________________insert right!____________________");
            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            System.out.println("______________________Can not Insert!____________________");
            throw e;
        } finally {
            session.close();
            return od;
        }
    }

    public int createSpotOrders(Orders od, int goodsNumber, String goodsName, Double orderQuantity) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        OrderSpotGoods osg = new OrderSpotGoods();
        int ans = 0;
        try {
            osg.setOrderId(od.getOrderId());
            osg.setOrderType(od.getOrderType());
            osg.setStuffNumber(od.getStuffNumber());
            osg.setCustomerNumber(od.getCustomerNumber());
            osg.setGoodsName(goodsName);
            osg.setGoodsNumber(goodsNumber);
            osg.setOrderQuantity(orderQuantity);
            osg.setBuildDate(od.getBuildDate());

            tx = session.beginTransaction();
            session.save(osg);
            System.out.println("_____________________________Insert into OSG______________________________");
            ans = 1;//1表示提交成功

            //订单提交后 更新成品库表
            String hql = "Update ShippingDepartment set stocks = stocks - :orderQuantity where goodsId = :goodsNumber";
            Query query = session.createQuery(hql);
            query.setDouble("orderQuantity", orderQuantity);
            query.setInteger("goodsNumber", goodsNumber);
            query.executeUpdate();
            System.out.println("_____________________________Update on SD______________________________");
            tx.commit();

        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not Insert into OSG_______________________________");
            ans = 0;//0表示提交失败
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return ans;
        }
    }

    public int createBookOrders(Orders od, int goodsNumber, String goodsName, Double orderQuantity, Double stocks, Double procedureQuantity) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        OrderBookGoods obg = new OrderBookGoods();
        String hql = "";
        int ans = 0;
        try {
            obg.setOrderId(od.getOrderId());
            obg.setOrderType(od.getOrderType());
            obg.setStuffNumber(od.getStuffNumber());
            obg.setCustomerNumber(od.getCustomerNumber());
            obg.setGoodsName(goodsName);
            obg.setGoodsNumber(goodsNumber);
            obg.setOrderQuantity(orderQuantity);
            obg.setBuildDate(od.getBuildDate());

            tx = session.beginTransaction();
            session.save(obg);
            System.out.println("_____________________________Insert into OSG______________________________");
            ans = 1;//1表示提交成功
            Query query;
            //订单提交后 更新成品库表
            if (stocks > orderQuantity) {
                hql = "Update ShippingDepartment set stocks = stocks - :orderQuantity where goodsId = :goodsNumber";
                obg.setProducedQuantity(0);
                query = session.createQuery(hql);
                query.setDouble("orderQuantity", orderQuantity);
                query.setInteger("goodsNumber", goodsNumber);
            } else {//库存<订单数量
                hql = "Update ShippingDepartment set stocks = 0 where goodsId = :goodsNumber";
                obg.setProducedQuantity(procedureQuantity);
                query = session.createQuery(hql);
                query.setInteger("goodsNumber", goodsNumber);
            }

            query.executeUpdate();
            System.out.println("_____________________________Update on SD______________________________");
            tx.commit();

        } catch (RuntimeException e) {
            System.out.println("_____________________________Can not Insert into OSG_______________________________");
            ans = 0;//0表示提交失败
            tx.rollback();
            throw e;
        } finally {
            session.close();
            return ans;
        }

    }


    public String Judge(GoodsInfo[] gd, String endDate) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        String Return = null;
        double time = 0;//计算订单表所有商品完成时所需的时间
//        double time1 = 0;
//        double time2 = 0;
//        double time3 = 0;
        try {

            for (int i = 0; i < gd.length; i++) {
                String hql2 = "select p.productQuantityPerTime from ProductUnit p where p.goodsId = " + gd[i].getGoodsId();
                String hql7 = "select s.stocks from ShippingDepartment s where s.goodsId = " + gd[i].getGoodsId();
                List templist = session.createQuery(hql2).list();
                List templist1 = session.createQuery(hql7).list();
                Object ob = (Object) templist.get(0);
                Double t = (Double) ob;//t是 id为 gd[i].getGoodsId的 单位时间生产数量
                Object ob1 = (Object) templist1.get(0);
                Double stocks = (Double) ob1;
                if (stocks < gd[i].getPayNumber()) {
                    time = time + (gd[i].getPayNumber() - stocks) / t;
                }
            }
            time = Math.ceil(time / 24);
//            System.out.println(time);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = sdf.parse(endDate);
            Date date2 = sdf.parse(sdf.format(new Date()));
            long day = (date1.getTime() - date2.getTime()) / (1000 * 60 * 60 * 24);//endDate与系统当前时间相差天数
            System.out.println(day);

            if (time > day) {//表示不能在要求的endDate完成 需要求出最早完成的流水线的时间 返回的是 可以完成的完成这批产品的最早的日期
                String hql3 = "from AssemblyLine where lineState = 1";
                List list1 = session.createQuery(hql3).list();
                if (list1.toString() != "[]" && list1 != null) {//表示有空闲的流水线 但是时间不满足 用系统当前时间加完成这批订单所需的时间time作为返回值
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                    String firstEndDate = df.format(new Date()).toString();//获得系统日期
                    System.out.println("_________________not null_______________" + firstEndDate);
                    Return = PlusDay.plusDay((int) time, firstEndDate);
                    System.out.println("_________________not null_______________" + Return);
                } else {//表示无空闲流水线 且时间不满足最早完成的流水线的时间 返回的是 可以完成的完成这批产品的最早的日期
                    String hql1 = "from AssemblyLine where lineState = 0 order by endWorkTime asc";
                    List ASL = session.createQuery(hql1).list();//流水线状态表
                    String firstEndDate = ((AssemblyLine) ASL.get(0)).getEndWorkTime();//最早完成的流水线的号
                    System.out.println("_________________null_______________" + firstEndDate);
                    Return = PlusDay.plusDay((int) time, firstEndDate);
                    System.out.println("_________________null_______________" + Return);
                }
            } else {
                //首先根据优先权判断是否为预定订单计划或者季度生产计划
                String hql4 = "select count(*) from AssemblyLine where priority = 1 and lineState = 0";//查询优先级为1且处于工作状态的流水线个数oj
                List list2 = session.createQuery(hql4).list();
                Object oj = (Object) list2.get(0);
                if (Integer.parseInt(oj.toString()) == 8) {//表示所有流水线都被预定订单的计划单占用 要返回最先完成的订单的日期
                    String hql5 = "from AssemblyLine order by endWorkTime asc";
                    List list3 = session.createQuery(hql5).list();
                    String firstEndDate = ((AssemblyLine) list3.get(0)).getEndWorkTime();
                    Return = PlusDay.plusDay((int) time, firstEndDate);
//                    System.out.println("______________________"+firstEndDate+"______________ "+time+"_______ "+Return);
                } else {//优先级为1的流水线的个数小于8
                    String hql6 = "from AssemblyLine where  lineState = 1";
                    List list4 = session.createQuery(hql6).list();
                    if (list4.toString() != "[]" && list4 != null) {//有空出来的流水线 且可以保证当天开工不会逾期
                        Return = "1";
//                        System.out.println("________________________________NOT NULL________________________" + oj);
                    } else {//没有空的流水线 但是有正在生产季度计划产品流水线 在这儿进行流水线的调度
                        Return = "1";
                    }
                }
            }
        } catch (RuntimeException e) {
            throw e;
        } finally {
            session.close();
            return Return;
        }
    }


}


//Test 现货订单表的生成语句
//        OrdersSubmission Obs=new OrdersSubmission();
//        Orders od = Obs.createMainOrders(0,"李天添","17863107716","上海",7300,10002,"购物广场");
//        Obs.createSpotOrders(od,1,"香蕉",100);
//        Obs.createSpotOrders(od,2,"苹果",200);
//        Obs.createSpotOrders(od,3,"猪肉",100);

//    Test 预定订单表的生成语句
//    OrdersSubmission Obs=new OrdersSubmission();
//    Orders od = Obs.createMainOrders(1,"家家悦","李天添","17863107716","邯郸",7300,10003,"2019-12-25");
//        Obs.createBookOrders(od,1,"香蕉",100);
//                Obs.createBookOrders(od,2,"苹果",200);
//                Obs.createBookOrders(od,3,"猪肉",1、/0);

//Test Judge
//        GoodsInfo[] gd=new GoodsInfo[3];
//        gd[0]=new GoodsInfo(1,"香蕉",5.5,180,78,600.0);
//        gd[1]=new GoodsInfo(2,"苹果",6.0,360,577,300.0);
//        gd[2]=new GoodsInfo(3,"猪肉",55.5,7,778,500.0);
//
//        OrdersSubmission os=new OrdersSubmission();
//        os.Judge(gd,"2019-12-18");


//        GoodsInfo[] gd = new GoodsInfo[3];
//        gd[0]=new GoodsInfo(1,"香蕉",5.5,180,1000,100.0);
//        gd[1]=new GoodsInfo(2,"苹果",6.0,360,1000,200.0);
//        gd[2]=new GoodsInfo(3,"猪肉",55.5,7,2000,100.0);
//                System.out.println(gd.length);
//
//                OrdersSubmission os=new OrdersSubmission();
//                System.out.println(os.Judge(gd,"2019-12-18"));




