package Controllers;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class SmsController {
    // Find your Account Sid and Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC0cfb1cd5a140c5785bd88418cfc1076f";
    public static final String AUTH_TOKEN = "c4e9304c219c36b33c830acf9d152007";

    public static void Sms() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+21698104254"),
                new com.twilio.type.PhoneNumber("+13345186638"),


                "Reponse effectuee a un client ").create();

        System.out.println(message.getSid());
    }
}