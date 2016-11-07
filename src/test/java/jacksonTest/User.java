package jacksonTest;

import java.util.List;

/**
 * Created by Administrator on 2016/6/26.
 */
public class User {

    private String name;

    private Gender gender;

    private List<Account> accounts;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
