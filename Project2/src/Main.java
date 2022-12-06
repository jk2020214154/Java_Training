/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: SDTBU_LY
 * Date: 2022-12-05
 * Time: 11:03
 */



import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Connection con;
        Statement statement;
        PreparedStatement preparedstatement;
        ResultSet resultset;
        Scanner cin = new Scanner(System.in);

        int op;
        String day;
        String id,name,sex,address;
        float salary;

        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            System.out.println("数据库驱动已成功启动!");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        try{
            con=DriverManager.getConnection("jdbc:derby:E:\\大学\\专业课\\3.1Java\\实训\\移动公司;create=true");
            System.out.println("数据库已成功连接!");

            statement=con.createStatement();
            String sql1="Create table 职员表 (id char(6) primary key,姓名 varchar(10),性别 char(6),出生 date,地址 varchar(50),工资 float)";
            //System.out.println(sql1);
            //statement.execute(sql1);

            String sql2="Insert into 职员表 values(?,?,?,?,?,?)";

            preparedstatement=con.prepareStatement(sql2);

            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd"); //设置日期控制格式

            String sql3="select * from 职员表";

            while(true) {
                System.out.print("请输入是否插入数据(1插入,非1退出插入):");
                op = cin.nextInt();
                if (op != 1)
                    break;
                System.out.print("请依次输入id,姓名,性别,出生,地址,工资(中间用空格隔开):");
                id = cin.next();
                name = cin.next();
                sex = cin.next();
                day = cin.next();
                address = cin.next();
                salary = cin.nextFloat();

                java.util.Date date = format.parse(day); //保存为java的日期类
                java.sql.Date sqldate = new java.sql.Date(date.getTime());//保存为sql的日期类


                preparedstatement.setString(1, id);
                preparedstatement.setString(2, name);
                preparedstatement.setString(3, sex);
                preparedstatement.setDate(4, sqldate);
                preparedstatement.setString(5, address);
                preparedstatement.setFloat(6, salary);
                preparedstatement.executeUpdate();
                System.out.println("数据插入成功!");
            }

            resultset=statement.executeQuery(sql3);
            System.out.println("职员表的内容如下:");
            System.out.println("id              |名字           |性别      |出生          |地址                |工资");
            while(resultset.next()){
                id=resultset.getString("id");
                name=resultset.getString("姓名");

                sex=resultset.getString("性别");
                java.sql.Date date=resultset.getDate("出生");
                address=resultset.getString("地址");
                salary=resultset.getFloat("工资");
                System.out.println(id+"          |"+name+"       |"+sex+"   |"+date.toString()+"    |"+address+"                |"+salary);
            }

            con.close();
            System.out.println("数据库已成功退出!");
        }
        catch (SQLException e){
            System.out.println(e);
        }
        catch (ParseException e)
        {
            System.out.println(e);
        }

    }

}


//1 王林 男 1976-12-12 吉林 6654
//2 翠花 女 1982-10-12 北京 1654
//3 花翠 女 1984-12-12 大连 5654
//4 林王 男 1978-10-12 上海 2654