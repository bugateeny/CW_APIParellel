package files;

public class CallOut_Payload {

    public static String jobCallOutAPI(String siteAccount, String description)

    {
        return "{\n" +
                "    \"companyNumber\": \"01\",\n" +
                "    \"contact\": \"John\",\n" +
                "    \"description\": \""+description+"\",\n" +
                "    \"siteAccount\": \""+siteAccount+"\",\n" +
                "    \"orderNumber\": \"DODDS-REF4\",\n" +
                "    \"priority\": \"1\"\n" +
                "}";
    }

    public static String jobUpdateAPI (String contact, String updateDescription) {
        return "{\n" +
                "    \"companyNumber\": \"01\",\n" +
                "    \"contact\": \""+contact+"\",\n" +
                "    \"description\": \""+updateDescription+"\",\n" +
                "    \"orderNumber\": \"DODDS-REF1\",\n" +
                "    \"priority\": \"2\"\n" +
                "}";

    }

    public static String  updateSpecificJob () {
        return "jobs/id/";      //  path param for POST/DELETE specific job
    }

    public static String  getSpecificJob () {
        return "jobs/reactive/id/";      //  path param for GET specific job
    }


}
