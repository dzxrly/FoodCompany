package service;

public class RefundQuestCheck { //封装退款检测
    private OrderRefundSubmission orderRefundSubmission = new OrderRefundSubmission();
    private String orderId;

    public RefundQuestCheck(String orderId) {
        this.orderId = orderId;
    }
    public RefundQuestCheck(int orderId) {
        this.orderId = String.valueOf(orderId);
    }

    public boolean getCheckRes() {
        switch (orderRefundSubmission.Judge(orderId)) {
            default:
                return true;
            case 0:
                return true;
            case 1:
                return false;
        }
    }
}
