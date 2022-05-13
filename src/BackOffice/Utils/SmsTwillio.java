package BackOffice.Utils;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
public class SmsTwillio {

    public static final String ACCOUNT_SID = "AC36ea5a7aee503c2106e2075600786146";
    public static final String AUTH_TOKEN = "32a95cb151330e11b46a51c7d2cbe602";
    public static void sms() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("++21644252463"),
                        new com.twilio.type.PhoneNumber("+18647341936"),
                        " Merci Pour Votre reservation chez nous ").create();
        System.out.println(message.getSid());
    }
    
}
