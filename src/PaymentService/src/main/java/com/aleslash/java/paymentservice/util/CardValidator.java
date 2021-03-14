package com.aleslash.java.paymentservice.util;

import java.util.regex.Pattern;

public class CardValidator {

    //adaptado do site https://www.geeksforgeeks.org/program-credit-card-number-validation/
    //incluindo definição de tipo do site https://stackoverflow.com/questions/20377978/credit-card-type-and-validation

    //Cartoes de exemplo:
    //Discover 6011-6011-6011-6611 737
    //Visa 4000-0600-0000-0006 737
    //Master 6771-7980-2500-0004 737

    private String _cardNumber;

    public CardValidator(String cardNumber) {
        _cardNumber = cardNumber.replaceAll("-", "");
    }

    public boolean isValid() {
        long number = Long.parseLong(_cardNumber);
        return (getSize(number) >= 13 &&
                getSize(number) <= 16) &&
                (prefixMatched(number, 4) ||
                        prefixMatched(number, 5) ||
                        prefixMatched(number, 37) ||
                        prefixMatched(number, 6)) &&
                ((sumOfDoubleEvenPlace(number) +
                        sumOfOddPlace(number)) % 10 == 0);
    }

    public String getCartType() {
        switch (CardType.detect(_cardNumber)){
            case JCB:
                return "JCB";
            case VISA:
                return "VISA";
            case DISCOVER:
                return "DISCOVER";
            case MASTERCARD:
                return "MASTERCARD";
            case DINERS_CLUB:
                return "DINERS_CLUB";
            case CHINA_UNION_PAY:
                return "CHINA_UNION_PAY";
            case AMERICAN_EXPRESS:
                return "AMERICAN_EXPRESS";
            case UNKNOWN:
            default:
                return "UNKNOWN";
        }
    }

    // Get the result from Step 2
    private int sumOfDoubleEvenPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 2; i >= 0; i -= 2)
            sum += getDigit(Integer.parseInt(num.charAt(i) + "") * 2);

        return sum;
    }

    // Return this number if it is a single digit, otherwise,
    // return the sum of the two digits
    private int getDigit(int number)
    {
        if (number < 9)
            return number;
        return number / 10 + number % 10;
    }

    // Return sum of odd-place digits in number
    private int sumOfOddPlace(long number)
    {
        int sum = 0;
        String num = number + "";
        for (int i = getSize(number) - 1; i >= 0; i -= 2)
            sum += Integer.parseInt(num.charAt(i) + "");
        return sum;
    }

    // Return true if the digit d is a prefix for number
    private boolean prefixMatched(long number, int d)
    {
        return getPrefix(number, getSize(d)) == d;
    }

    // Return the number of digits in d
    private int getSize(long d)
    {
        String num = d + "";
        return num.length();
    }

    // Return the first k number of digits from
    // number. If the number of digits in number
    // is less than k, return number.
    private long getPrefix(long number, int k)
    {
        if (getSize(number) > k) {
            String num = number + "";
            return Long.parseLong(num.substring(0, k));
        }
        return number;
    }
}

enum CardType {

    UNKNOWN,
    VISA("^4[0-9]{12}(?:[0-9]{3}){0,2}$"),
    MASTERCARD("^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$"),
    AMERICAN_EXPRESS("^3[47][0-9]{13}$"),
    DINERS_CLUB("^3(?:0[0-5]\\d|095|6\\d{0,2}|[89]\\d{2})\\d{12,15}$"),
    DISCOVER("^6(?:011|[45][0-9]{2})[0-9]{12}$"),
    JCB("^(?:2131|1800|35\\d{3})\\d{11}$"),
    CHINA_UNION_PAY("^62[0-9]{14,17}$");

    private Pattern pattern;

    CardType() {
        this.pattern = null;
    }

    CardType(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }

    public static CardType detect(String cardNumber) {

        for (CardType cardType : CardType.values()) {
            if (null == cardType.pattern) continue;
            if (cardType.pattern.matcher(cardNumber).matches()) return cardType;
        }
        return UNKNOWN;
    }

}
