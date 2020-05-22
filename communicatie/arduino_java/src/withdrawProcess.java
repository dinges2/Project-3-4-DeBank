public class withdrawProcess {
    static int tenCounter = 10;
    static int twentyCounter = 10;
    static int fiftyCounter = 10;

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
        return "";
    }
}
