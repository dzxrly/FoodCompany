package service;

import java.text.NumberFormat;

public class DoubleFormatService {
    // 四舍五入，保留number类型数值的decimalDigit位小数位数
    // 传入double类型的数值和想要保留的小数位数
    public double numberFormat(double number, int decimalDigit) {
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(decimalDigit);
        double result = Double.valueOf(numberFormat.format(number));
        return result;
    }

    public String getSubstringPrice(Double price, int decimalDigit) {
        String str = String.valueOf(price);
        if (str.length() <= decimalDigit) return str;
        else return str.substring(0, decimalDigit);
    }
}
