package it.atm;

public class account {
    private String cardid;
    private String username;
    private char sex;
    private String password;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    private double money;
    private double limit;    //限制

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getUsername() {
        return username +(sex == '男'?"先生":"女士");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }


}
