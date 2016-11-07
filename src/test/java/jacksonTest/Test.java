package jacksonTest;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/26.
 */
public class Test {

    public static void main(String[] args) throws Exception {

        /*User user = new User();
        user.setName("菠萝大象");
        user.setGender(Gender.MALE);
        List<Account> accounts = new ArrayList<Account>();
        Account account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(1900.2));
        account.setCardId("423335533434");
        account.setDate(new Date());
        accounts.add(account);
        account = new Account();
        account.setId(2);
        account.setBalance(BigDecimal.valueOf(5000));
        account.setCardId("625444548433");
        account.setDate(new Date());
        accounts.add(account);
        user.setAccounts(accounts);*/

        Map<String,String> people = new HashMap<String, String>();
        people.put("name","韩远强");
        people.put("age","22");

        ObjectMapper mapper = new ObjectMapper();

        //这是辅助设置，控制格式化输出。
        mapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, Boolean.TRUE);

        //使用jackson的ObjectMapper 的writeValueAsString方法可以把pojo类输出成json字符串
        String json = mapper.writeValueAsString(people);

        System.out.println("Java2Json: "+json);

        System.out.println("--------------------------------------");
        people = mapper.readValue(json, Map.class);

        System.out.println("Json2Java: "+mapper.writeValueAsString(people));

    }
}
