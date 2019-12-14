package service;

public class ProductionFinanceSubmission {

    public int submitProductionFinance(int stuffNumber,double price){//支出表添加一条记录
        int ans= 0;
        BillSubmission b = new BillSubmission();
        ans = b.submitBill(1,stuffNumber,price,1,0,null,"原料成本");

        return ans;//1表示提交成功
    }
}
