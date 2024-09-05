import java.util.*;
import java.io.*;
import java.lang.*;
import java.sql.*;
class Employee{
    String cp_name;
    int cp_quantity;
    public Employee(String cp_name,int cp_quantity){
	this.cp_name=cp_name;
	this.cp_quantity=cp_quantity;
    }
    public void insert_product(String cp_name,int cp_quantity)throws Exception{
	try{
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","26mahiK!");
	Statement st1= con.createStatement();
	ResultSet rs=st1.executeQuery("select * from product where prod_name= '"+cp_name+"'");
	rs.next();
	PreparedStatement ps=con.prepareStatement("insert into cart values(?,?,?,?)");
	ps.setInt(1,rs.getInt(1));
	ps.setString(2,rs.getString(2));
	ps.setInt(3,rs.getInt(3));
	ps.setInt(4,rs.getInt(4));
	int p=rs.getInt(4);
	p=p-cp_quantity;
	int r=rs.getInt(3)*cp_quantity;
	int x=ps.executeUpdate();
	update_product(cp_name,cp_quantity,p,r);
	ps.close();
	con.close();
    	}
        catch(Exception e){
	    e.printStackTrace();
        }
    }
    public void update_product(String cp_name,int cp_quantity,int p,int r)throws Exception{
        try{
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","26mahiK!");
        PreparedStatement ps1=con.prepareStatement("update cart set item_quantity="+cp_quantity+", item_cost="+r+" where item_name='"+cp_name+"'");
	int a=ps1.executeUpdate();
	ps1.close();
	PreparedStatement ps2=con.prepareStatement("update product set quantity="+p+" where prod_name='"+cp_name+"'");
	int b=ps2.executeUpdate();
	ps2.close();
	con.close();
	}
	catch(Exception e5){
	    e5.printStackTrace();
	}
    }
    public void delete_product()throws Exception{
	Scanner sc=new Scanner(System.in);
	System.out.println("Enter 1 to update or reduce quantity, Enter 2 to delete the product");
	int del_val=sc.nextInt();
	if(del_val==1){
	    System.out.println("Enter quantity required:");
	    int del_quant=sc.nextInt();
	    System.out.println("Enter name of product:");
	    String del_product=sc.nextLine();
	    del_product=sc.nextLine();
	    try{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","26mahiK!");
		PreparedStatement ps5=con.prepareStatement("delete from cart where item_name='"+del_product+"'");
	        int f=ps5.executeUpdate();
		insert_product(del_product,del_quant);
		ps5.close();
	        con.close();
	    }
	    catch(Exception e8){
		e8.printStackTrace();
	    }
	}
	else if(del_val==2){
	    System.out.println("Enter the product:");
	    String del_prod=sc.nextLine();
	    del_prod=sc.nextLine();
	    try{
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","26mahiK!");
		Statement st2= con.createStatement();
		ResultSet rs2=st2.executeQuery("select item_quantity from cart where item_name= '"+del_prod+"'");
		rs2.next();
		int q=rs2.getInt(1);
		PreparedStatement ps3=con.prepareStatement("update product set quantity="+q+" where prod_name='"+del_prod+"'");
		int c=ps3.executeUpdate();
		ps3.close();
		PreparedStatement ps4=con.prepareStatement("delete from cart where item_name='"+del_prod+"'");
		int d=ps4.executeUpdate();
		disp_cart();
		ps4.close();
		con.close();
	    }
	    catch(Exception e1){
		e1.printStackTrace();
	    }
	}
	else{
	    delete_product();
	}
    }
    public void disp_cart()throws Exception{
	try{
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","26mahiK!");
	    Statement st3= con.createStatement();   
            ResultSet rs3=st3.executeQuery("select * from cart");
            while(rs3.next()){
	        System.out.println(rs3.getInt(1)+"\t"+rs3.getString(2)+"\t"+rs3.getInt(3)+"\t"+rs3.getInt(4));
	    }
	}
	catch(Exception e6){
	    e6.printStackTrace();
	}
    }
    public void bill()throws Exception{
	try{
	    disp_cart();
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","26mahiK!");
	    Statement st4= con.createStatement();   
            ResultSet rs4=st4.executeQuery("select sum(item_cost) from cart");
            while(rs4.next()){
	        System.out.println(rs4.getInt(1)+"\t");
	    }
	}
	catch(Exception e7){
	    e7.printStackTrace();
	}
    }
}
class Customer{
    String c_name,cp_name;
    double c_phno;
    int cp_quantity;
    public Customer(String c_name,double c_phno){
	this.c_name=c_name;
	this.c_phno=c_phno;
    }
    Employee emp;
    public void connectionmaking()throws Exception{
	try{
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/abc","root","26mahiK!");
        Statement st= con.createStatement();   
        ResultSet rs1=st.executeQuery("select * from product");
        while(rs1.next()){
	    System.out.println(rs1.getInt(1)+"\t"+rs1.getString(2)+"\t"+rs1.getInt(3)+"\t"+rs1.getInt(4));
	}
	st.executeUpdate("create table cart (item_id int ,item_name varchar(30),item_cost int,item_quantity int)");
	rs1.close();
	con.close();
	}
	catch(Exception e3){
	    e3.printStackTrace();
	}
    }
    public void shopping()throws Exception{
    try{
	Scanner sc=new Scanner(System.in);
        System.out.println("Enter the product quantity");
        cp_quantity=sc.nextInt();
	System.out.println("Enter the product:");
        cp_name=sc.nextLine();
	cp_name=sc.nextLine();
	emp=new Employee(cp_name,cp_quantity);
        emp.insert_product(cp_name,cp_quantity);
    }
    catch(Exception e2){
	e2.printStackTrace();
    }
    }
    public void delete_product1()throws Exception{
	try{
	emp.delete_product();
	}
	catch(Exception e10){
	    e10.printStackTrace();
	}
    }
    public void bill1()throws Exception{
	try{
	emp.bill();
	}
	catch(Exception e9){
	    e9.printStackTrace();
	}
    }
}
class StoreManagementSystem1{
    public static void main(String[] args)throws Exception{
	try{
	Scanner sc=new Scanner(System.in);
	int w=0;
	System.out.println("Welcome to the store!");
	System.out.println("Enter your name:");
	String c_name=sc.nextLine();
	System.out.println("Enter your phno:");
	double c_phno=sc.nextDouble(); 
	Customer cust=new Customer(c_name,c_phno);
	cust.connectionmaking();
	while(w==0){
        System.out.println("Enter 1 to add products, 2 to remove products, 3 to bill the products, 4 to exit");
	int val=sc.nextInt();
	switch(val){
	    case 1:
		cust.shopping();
		break;
	    case 2:
		cust.delete_product1();
		break;
	    case 3:
		cust.bill1();
		break;
	    case 4:
		w=1;
		break;
	    default:
		System.out.println("Enter valid choice");
		break;
	}
	}
	}
	catch(Exception e4){
	    e4.printStackTrace();
	}
    }
} 