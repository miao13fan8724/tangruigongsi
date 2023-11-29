package it.atm;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
public class ATM {
    private ArrayList<account> accounts = new ArrayList<>();    //创建一个容器 为accoun
    private Scanner sc = new Scanner(System.in);
    private account loginacc;//记录登录的用户

    //启动系统展示欢迎界面
    public void start(){
        while (true) {
            System.out.println("===欢迎您进入到了ATM系统===");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    //用户登录
                    login();
                    break;
                case 2:
                    //用户开户
                    createaccount();
                    break;
                default:
                    System.out.println("没有该操作~~");
            }
        }
    }

    /* 完成用户的登录操作*/
    private void login(){
        System.out.printf("==系统登录==");
        //1.判断系统中是否存在账户对象 存在才能登录 如果不存在我们直接结束登录操作
        if (accounts.size()==0){
            return ;//跳过该登陆操作
        }
        while (true) {
            System.out.printf("请输入您的登陆卡号");
            String cardid = sc.next();
            //3.判断这个卡号是否存在
            account acc = getaccountbyacrdid(cardid);
            if(acc ==null){
                //说明这个卡号不存在
                System.out.printf("您输入的登陆卡号不存在请确认");
            }else{
                while (true) {
                    //卡号不存在接着让用户输入密码
                    System.out.printf("请输入登陆密码");
                    String password = sc.next();
                    //4.判读密码是否正确
                    if(acc.getPassword().equals(password)){
                        loginacc = acc;
                        //密码正确登陆成功
                        System.out.printf("恭喜您，"+acc.getUsername()+"成功登录"+"您的卡号是"+acc.getCardid());
                        showusercommand();
                        return;
                        //......
                    }else{
                        System.out.printf("您输入的密码不正确，请确认~~");
                    }
                }
            }
        }
    }
    /*定义一个方法*/
    private void showusercommand(){
        while(true){
            System.out.printf(loginacc.getUsername()+"您可以选择如下账户进行的账户处理===");
            System.out.printf("1.查询账户");
            System.out.printf("2.存款");
            System.out.printf("3.取款");
            System.out.printf("4.转账");
            System.out.printf("5.密码修改");
            System.out.printf("6.退出");
            System.out.printf("7.注销当前账户");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    //查询账户
                    showloginaccount();
                    break;
                case 2:
                    //存款
                    depositmoney();
                    break;
                case 3:
                    //取款
                    drawmoney();
                    break;
                case 4:
                    //转账
                    transfermoney();
                    break;
                case 5:
                    //密码修改
                    updatepassword();
                    break;
                case 6:
                    //退出
                    System.out.printf(loginacc.getUsername()+"您推出系统成功");
                    return ;
                case 7:
                    //注销当前账户
                    if (deleteaccount()){
                        //今日这里代表销户成功
                        return;
                    }
                    break;
                default:
                    System.out.printf("您当前的操作是不存在的");

            }
        }
    }
    //进行账户的密码的修改
    private void updatepassword() {
        System.out.printf("==账户的密码修改操作==");
        while (true) {
            System.out.printf("请您输入当前账户的的密码");
            String password = sc.next();

            //2.认证当前密码是否正确
            if(loginacc.getPassword().equals(password)){
                //认证通过
                while (true) {
                    //3.真正开始修改密码
                    System.out.printf("请您输入新密码");
                    String newpassword = sc.next();
                    System.out.printf("请您输入确认密码");
                    String okpassword = sc.next();

                    //4.判断两次的密码是否一至
                    if(okpassword.equals(newpassword)){
                        //真正开始修改密码
                        loginacc.setPassword(okpassword);
                        System.out.printf("恭喜您您的密码修改成功");
                        return ;
                    }else{
                        System.out.printf("您输入的两次妈妈不一致");
                    }
                }

            }else{
                System.out.printf("当前输入的密码不正确");
            }
        }
    }

    /*账户操作*/
    private boolean deleteaccount() {
        System.out.printf("==进行销户操作");
        //进行销户确认
        System.out.printf("请问您确认销户吗");
        String command = sc.next();
        switch(command){
            case "y":
                //确定要销户的人
                //2.判断用户的账户中是否有钱 loginacc
                if(loginacc.getMoney()==0){
                    //真的销户
                    accounts.remove(loginacc);
                    System.out.printf("您的账户已经成功销户");
                }else {
                    System.out.printf("对不起您的帐户中存在金币不允许销户");
                    return false;
                }
            default:
                System.out.printf("好的，您的销户保留");
                return false;
        }
    }

    /*进行转账*/
    private void transfermoney() {
        System.out.printf("==用户转账");
        //判断系统中是否存在其他账户
        if(accounts.size()<2){
            System.out.printf("当前系统中只有您一个账户，无法为其他账户进行转账");
            return;
        }
        if(loginacc.getMoney()==0){
            System.out.printf("您自己都没钱都别转了");
            return;
        }
        //3.进行转账
        while (true) {
            System.out.printf("请您输入对方的卡号");
            String cardid = sc.next();
            //4.判断这个卡号是否正确
            account acc = getaccountbyacrdid(cardid);
            if(acc==null){
                System.out.printf("您输入的卡号不存在");
            }else{
                String name = "*"+acc.getUsername().substring(1);//从字符串中第二个字符截取
                System.out.printf("请您输入["+name+"]姓氏");
                String prename = sc.next();
                if(acc.getUsername().startsWith(prename)){
                    while (true) {
                        System.out.printf("请您输入转账给对方的金额");
                        double money = sc.nextDouble();
                        if(loginacc.getMoney()>=money){
                            //转给对方
                            //更新自己账户的余额
                            loginacc.setMoney(loginacc.getMoney()-money);
                            //更新对方账户的余额
                            acc.setMoney(acc.getMoney()+money);
                            System.out.printf("您转账成功了");
                            return;    //跳出整个转账操作
                        }else{
                            System.out.printf("您的余额不足，无法给对方转那么多钱，最多可以转"+loginacc.getMoney());
                        }
                    }
                }else{
                    System.out.printf("对不起，您认证的姓氏有问题");
                }
            }
        }
    }

    //存钱
    private void drawmoney(){
        System.out.printf("==取钱操作");
        //1.判断账户余额是否达到了100元，如果不够100元 就不让账户取钱
        if(loginacc.getMoney()<100){
            System.out.printf("您的账户不够100元，不允许取钱~");
            return;
        }
        //2.让用户输入取款金额
        while (true) {
            System.out.printf("请您输入取款金额");
            double money = sc.nextDouble();
            //3.判断账户是否足够
            if(loginacc.getMoney()>=money){
                //账户中的余额是足够的
                if(money>loginacc.getLimit()){
                    System.out.printf("您当前取款金额超过了每次上线，您每次最多可取"+loginacc.getLimit());
                }else{
                    //代表可以开始取钱，更新当亲账户的余额即可
                    loginacc.setMoney(loginacc.getMoney()-money);
                    System.out.printf("您取款"+money+"成功，取款后您剩余");
                    break;
                }
            }else{
                System.out.printf("余额不足");
            }
        }
    }
    private void depositmoney() {
        System.out.printf("==存钱操作==");
        System.out.printf("请您出入存款金额");
        double money = sc.nextDouble();

        //更新当前余额中的余额
        loginacc.setMoney(loginacc.getMoney()+money);
        System.out.printf("恭喜您，存钱："+money+"成功，存款后余额是："+loginacc.getMoney());
    }

    //展示当前登陆的账户信息
    private  void showloginaccount(){
        System.out.printf("卡号"+loginacc.getCardid());
        System.out.printf("户主"+loginacc.getUsername());
        System.out.printf("性别"+loginacc.getSex());
        System.out.printf("余额"+loginacc.getMoney());
        System.out.printf("每次取现的额度"+loginacc.getLimit());
    }
    /*完成用户开户的操作*/
    private void createaccount(){
        //1.创建一个用户对象用于封装用户的开户信息
        account acc = new account();

        //2.需要用户自己输入用户的信息 赋值给账户对接
        System.out.printf("请您输入您的账户名称：");
        String name = sc.next();
        acc.setUsername(name);
        while (true) {
            System.out.printf("请您输入您的性别:");
            char sex = sc.next().charAt(0);
            if(sex =='男' || sex == '女'){
                acc.setSex(sex);
                break;
            }else{
                System.out.printf("您输入的性别有误~只能输入男或女");
            }
        }


        while (true) {
            System.out.printf("请您输入您的账户密码:");
            String password = sc.next();
            System.out.println("请您输入您的正确密码:");
            String okpassword = sc.next();
            //判断两次密码是否一样
            if(okpassword.equals(password)){
                acc.setPassword(okpassword);
                break;
            }else{
                System.out.printf("您输入的两次密码不一致");
            }
        }


        System.out.println("请您输入你的取现额度");
        double limit = sc.nextDouble();
        acc.setLimit(limit);

        //我们要为这个账户生成一个卡号（有体统自动生成 八位数字表示 不能和其他账户的数字重合
        String newcardid = createCardid();
        acc.setCardid(newcardid);
        //3.把这个用户对象存入到账户集合中
        accounts.add(acc);
        System.out.printf("恭喜你"+acc.getUsername()+"开户成功，您的卡号是："+acc.getCardid());
    }
    /*返回一个八位数的卡号而且这个卡号和其他卡号不重复*/
    private String createCardid(){
        while (true) {
            //定一个String类型用来记住8位数字为卡号
            String cardid="";
            Random r = new Random();
            for(int i=0;i<8;i++){
                int data = r.nextInt(10);
                cardid += data;
            }
            //3.判断卡号是否重复
            account acc = getaccountbyacrdid(cardid);
            if(acc == null){
                //说明cardid没有找到账户对象因此擦让弟弟没有与其他账户重复可以返回作为一个新的卡号
                return cardid;
            }
        }
    }
    /*根据卡号查询账户的对象返回*/
    private account getaccountbyacrdid(String cardid){
        for (int i = 0; i < accounts.size(); i++) {
           account acc = accounts.get(i);
           if (acc.getCardid().equals(cardid)){
               return acc;
           }
        }
        return null;
    }
}
