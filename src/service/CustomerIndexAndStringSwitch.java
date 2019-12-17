package service;

public class CustomerIndexAndStringSwitch {
    //用于对顾客类型和顾客星级进行int与string的转换

    public String returnCustomerTypeByIndex(int index) {
        switch (index) {
            default:
                return null;
            case 0:
                return "个人";
            case 1:
                return "公司/企业";
        }
    }

    public String returnCustomerLevelByIndex(int index) {
        switch (index) {
            default:
                return null;
            case 0:
                return "一星";
            case 1:
                return "二星";
            case 2:
                return "三星";
            case 3:
                return "四星";
            case 4:
                return "五星";
        }
    }

    public int returnTypeIndexByString(String input) {
        switch (input) {
            default:
                return 0;
            case "个人":
                return 0;
            case "公司/企业":
                return 1;
        }
    }

    public int returnLevelIndexByString(String input) {
        switch (input) {
            default:
                return 0;
            case "一星":
                return 0;
            case "二星":
                return 1;
            case "三星":
                return 2;
            case "四星":
                return 3;
            case "五星":
                return 4;
        }
    }

    public String returnStatusByIndex(int index) {
        switch (index) {
            default:
                return "未生产";
            case 0:
                return "未生产";
            case 1:
                return "生产中";
            case 2:
                return "运输中";
            case 3:
                return "已到达";
        }
    }

    public String returnOrderTypeByIndex(int index) {
        switch (index) {
            default:
                return "现货订单";
            case 0:
                return "现货订单";
            case 1:
                return "预定订单";
        }
    }

    public Double getPrepaymentBySumAndLevel(Double sum, int level) {
        switch (level) {
            default:
                return sum * 0.3;
            case 0:
                return sum * 0.3;
            case 1:
                return sum * 0.25;
            case 2:
                return sum * 0.2;
            case 3:
                return sum * 0.15;
            case 4:
                return sum * 0.1;
        }
    }

    public Double getRespaymentBySumAndLevel(Double sum, int level) {
        switch (level) {
            default:
                return sum * 0.7;
            case 0:
                return sum * 0.7;
            case 1:
                return sum * 0.75;
            case 2:
                return sum * 0.8;
            case 3:
                return sum * 0.85;
            case 4:
                return sum * 0.9;
        }
    }

    public String getDepNameByIndex(int index) {
        switch (index) {
            default:
                return "老板";
            case 0:
                return "老板";
            case 1:
                return "销售部";
            case 2:
                return "财务部";
            case 3:
                return "生产计划科";
            case 4:
                return "生产车间";
            case 5:
                return "成品库";
            case 6:
                return "原料库";
        }
    }

    public String getStuffLevelByIndex(int index) {
        switch (index) {
            default:
                return "老板";
            case 0:
                return "老板";
            case 1:
                return "经理";
            case 2:
                return "员工";
        }
    }

    public String getGenderByIndex(int index) {
        switch (index) {
            default:
                return "男";
            case 0:
                return "男";
            case 1:
                return "女";
        }
    }
}
