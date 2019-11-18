package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

//员工信息
public class Stuff {
    private StringProperty firstName; //姓
    private StringProperty lastName; //名
    private StringProperty number; //员工ID
    private StringProperty password; //登陆密码
    private StringProperty level; //权限等级，用数字表示，0为最高
    private StringProperty personalID; //身份证
    private StringProperty address; //住址
    private StringProperty phoneNumber; //手机号
    private StringProperty type;//员工类型
    private ObjectProperty<LocalDate> birthday; //生日


}
