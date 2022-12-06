/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: SDTBU_LY
 * Date: 2022-12-05
 * Time: 10:45
 */

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection con;
        String sql1="Select name,sex,age from 计科20 where age= ?";
        PreparedStatement preparedstatement;
        ResultSet resultset;
        Scanner cin = new Scanner(System.in);

        String name, sex;
        double age;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            System.out.println("数据库驱动已成功启动!");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        try{
            con=DriverManager.getConnection("jdbc:derby:E:\\大学\\专业课\\3.1Java\\实训\\山东工商学院;create=true");
            System.out.println("数据库已成功连接!");
            preparedstatement=con.prepareStatement(sql1);
            System.out.print("请输入年龄:");
            age=cin.nextDouble();
            preparedstatement.setDouble(1,age);
            resultset=preparedstatement.executeQuery();
            System.out.println("查询的结果的内容如下:");
            //System.out.println("序号       |姓名       |性别      |年龄       ");
            int cnt=0;
            while(resultset.next()){
                cnt++;
                name=resultset.getString("name");
                sex=resultset.getString("sex");
                age=resultset.getDouble("age");
                System.out.println(cnt+". "+name+", "+sex);
            }
            System.out.println("共计:"+cnt+"个");
            con.close();
            System.out.println("数据库已成功退出!");
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }

}
