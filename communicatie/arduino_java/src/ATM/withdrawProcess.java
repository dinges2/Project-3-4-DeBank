package ATM;

import static ATM.PinAutomaat.noOfBills;

public class withdrawProcess {
    static int tenCounter = 5;
    static int twentyCounter = 10;
    static int fiftyCounter = 10;

    public static int getTenCounter() {
        return tenCounter;
    }

    public static int getTwentyCounter() {
        return twentyCounter;
    }

    public static int getFiftyCounter() {
        return fiftyCounter;
    }

    public static String optionTen(String saldo) {
        if(tenCounter - 1 >= 0) {
            if(Integer.valueOf(saldo) - 10 >= 0) {
                tenCounter = tenCounter - 1;
                return "ok";
            }
            return "saldo false";
        }
        return "biljet false";
    }

    public static String optionTwenty(String option, String saldo) {
        if(option.equals("option1")) {
            if(tenCounter - 2 >= 0) {
                if(Integer.valueOf(saldo) - 20 >= 0) {
                    tenCounter = tenCounter - 2;
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }
        else if(option.equals("option2")) {
            if(twentyCounter - 1 >= 0) {
                if(Integer.valueOf(saldo) - 20 >= 0) {
                    twentyCounter = twentyCounter - 1;
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }
        return "";
    }

    public static String optionFifty(String option, String saldo) {
        if(option.equals("option1")) {
            if(tenCounter - 5 >= 0) {
                if(Integer.valueOf(saldo) - 50 >= 0) {
                    tenCounter = tenCounter - 5;
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }
        else if(option.equals("option2")) {
            if(twentyCounter - 2 >= 0 && tenCounter - 1 >= 0) {
                if(Integer.valueOf(saldo) - 50 >= 0) {
                    twentyCounter = twentyCounter - 2;
                    tenCounter = tenCounter - 1;
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }

        return "";
    }

    public static String optionHundred(String option, String saldo) {
        if(option.equals("option1")) {
            if(twentyCounter - 3 >= 0 && tenCounter - 4 >= 0) {
                if(Integer.valueOf(saldo) - 100 >= 0) {
                    twentyCounter = twentyCounter - 3;
                    tenCounter = tenCounter - 4;
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }
        else if(option.equals("option2")) {
            if(twentyCounter - 5 >= 0) {
                if(Integer.valueOf(saldo) - 100 >= 0) {
                    twentyCounter = twentyCounter - 5;
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }

        return "";
    }

    public static String optionSeventy(String saldo) {
        if(fiftyCounter - 1 >= 0 && twentyCounter - 1 >= 0) {
            if(Integer.valueOf(saldo) - 70 >= 0) {
                fiftyCounter = fiftyCounter - 1;
                twentyCounter = twentyCounter - 1;
                return "ok";
            }
            return "saldo false";
        }
        return "biljet false";
    }

    public static String optionChoice(String option, String saldo, int amount) {
        if(option.equals("option1")) {
            if(twentyCounter - noOfBills[1] >= 0 && tenCounter - noOfBills[2] >= 0 && fiftyCounter - noOfBills[0] >= 0) {
                if(Integer.valueOf(saldo) - amount >= 0) {
                    fiftyCounter = fiftyCounter - noOfBills[0];
                    twentyCounter = twentyCounter - noOfBills[1];
                    tenCounter = tenCounter - noOfBills[2];
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }
        else if(option.equals("option2")) {
            if(twentyCounter - noOfBills[1] >= 0 && tenCounter - noOfBills[2] >= 0 && fiftyCounter - noOfBills[0] >= 0) {
                if(Integer.valueOf(saldo) - amount >= 0) {
                    fiftyCounter = fiftyCounter - noOfBills[0];
                    twentyCounter = twentyCounter - noOfBills[1];
                    tenCounter = tenCounter - noOfBills[2];
                    return "ok";
                }
                return "saldo false";
            }
            return "biljet false";
        }

        return "";
    }
}
