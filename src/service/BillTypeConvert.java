package service;

public class BillTypeConvert {
    public String getAccTypeByType(int type, int accType) {
        if (type == 0) {
            switch (accType) {
                default:
                    return "预付款";
                case 0:
                    return "预付款";
                case 1:
                    return "尾款";
                case 2:
                    return "全款";
            }
        } else {
            switch (accType) {
                default:
                    return "税务";
                case 0:
                    return "税务";
                case 1:
                    return "原料";
            }
        }
    }

    public String getTypeByIndex(int index) {
        switch (index) {
            default:
                return "收入账单";
            case 0:
                return "收入账单";
            case 1:
                return "支出账单";
        }
    }
}
