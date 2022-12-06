/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: SDTBU_LY
 * Date: 2022-12-05
 * Time: 10:01
 */
import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection con;
        String sql1="Insert into 计科20 values(?,?,?,?)";
        String sql2="Select * from 计科20";
        PreparedStatement preparedstatement;
        Statement statement;
        ResultSet resultset;
        Scanner cin = new Scanner(System.in);

        int op;
        int number;
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
            while(true) {
                System.out.print("请输入是否插入数据(1插入,非1退出插入):");
                op = cin.nextInt();
                if (op != 1)
                    break;
                System.out.print("请依次输入序号,姓名,性别,年龄(中间用空格隔开):");
                number = cin.nextInt();
                name = cin.next();
                sex = cin.next();
                age = cin.nextDouble();
                preparedstatement.setInt(1, number);
                preparedstatement.setString(2, name);
                preparedstatement.setString(3, sex);
                preparedstatement.setDouble(4, age);
                preparedstatement.executeUpdate();
                System.out.println("数据插入成功!");
            }
            statement=con.createStatement();
            resultset=statement.executeQuery(sql2);
            System.out.println("计科20的表的内容如下:");
            System.out.println("序号       |姓名       |性别      |年龄       ");

            while(resultset.next()){
                number=resultset.getInt("number");
                name=resultset.getString("name");
                sex=resultset.getString("sex");
                age=resultset.getDouble("age");
                System.out.println(number+"          |"+name+"       |"+sex+"   |"+age);
            }
            con.close();
            System.out.println("数据库已成功退出!");
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }

}
