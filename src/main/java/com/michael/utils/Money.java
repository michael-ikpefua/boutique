package com.michael.utils;

import org.springframework.stereotype.Component;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

@Component
public class Money {

    public String formatMoneyToLocalCurrency(String moneyInStringFormat) {

        Double money = Double.parseDouble(moneyInStringFormat);
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(money);

    }

    public double getFormattedMoneyToNumber(String moneyInString) {
        String money = moneyInString.replace("₦", "");

        String money1 = money.replace(",", "");

        Double moneyInDoubleFormat = Double.parseDouble(money1);

        return  moneyInDoubleFormat;
    }


}
