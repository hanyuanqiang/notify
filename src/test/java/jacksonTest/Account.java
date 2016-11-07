package jacksonTest;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Administrator on 2016/6/26.
 */
public class Account {

    private Integer id;

    private String cardId;

    private BigDecimal balance;


    private Date date;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Integer getId() {
        return id;

    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
