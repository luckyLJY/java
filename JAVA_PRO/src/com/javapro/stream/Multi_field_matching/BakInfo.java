package com.javapro.stream.Multi_field_matching;

import java.util.List;

public class BakInfo implements Comparable<BakInfo> {

    //�û���
    private String user_name;
    //״̬
    private String status;
    //����
    private Integer age;

    //���
    private Double balance;


    public BakInfo(String user_name, String status,Integer age, Double balance){
        this.user_name = user_name;
        this.status=status;
        this.age = age;
        this.balance = balance;
    }

    public String toString(){
        return String.format("%s\t%s\t%s\t%s",this.user_name,this.status,this.age.toString(),this.balance.toString());
    }

    public static void printBakInfo(List<BakInfo> bakInfos){
        System.out.println("[�ͻ�����]\t[״̬]\t[����]\t[���]");
        System.out.println("----------------------------------------------------------");
        bakInfos.forEach(s->System.out.println(s.toString()));
        System.out.println(" ");
    }

    public int compareToAge(Integer ob) {
        return this.age.compareTo(ob);
        //return 1;
    }
    public double compareToBalance(Double ob) {
        return this.balance.compareTo(ob);
        //return 1;
    }
    

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

	@Override
	public int compareTo(BakInfo o) {
		// TODO Auto-generated method stub
		return 0;
	}
}