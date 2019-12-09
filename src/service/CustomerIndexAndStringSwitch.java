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
}
