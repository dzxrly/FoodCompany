package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

//顾客
public class Customer {
    private StringProperty firstName; //姓
    private StringProperty lastName; //名
    private StringProperty number; //顾客ID
    private StringProperty password; //登陆密码
    private StringProperty level; //客户星级，用数字表示，0为最高
    private StringProperty personalID; //身份证
    private StringProperty address; //住址
    private StringProperty phoneNumber; //手机号
    private StringProperty type;//顾客类型
    private ObjectProperty<LocalDate> birthday; //生日
    private StringProperty accumulatedAmount;//累计交易额
    private StringProperty orderQuantity;//客户完成的订单数量
}
