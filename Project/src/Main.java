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
        String sql1="Insert into �ƿ�20 values(?,?,?,?)";
        String sql2="Select * from �ƿ�20";
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
            System.out.println("���ݿ������ѳɹ�����!");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        try{
            con=DriverManager.getConnection("jdbc:derby:E:\\��ѧ\\רҵ��\\3.1Java\\ʵѵ\\ɽ������ѧԺ;create=true");
            System.out.println("���ݿ��ѳɹ�����!");
            preparedstatement=con.prepareStatement(sql1);
            while(true) {
                System.out.print("�������Ƿ��������(1����,��1�˳�����):");
                op = cin.nextInt();
                if (op != 1)
                    break;
                System.out.print("�������������,����,�Ա�,����(�м��ÿո����):");
                number = cin.nextInt();
                name = cin.next();
                sex = cin.next();
                age = cin.nextDouble();
                preparedstatement.setInt(1, number);
                preparedstatement.setString(2, name);
                preparedstatement.setString(3, sex);
                preparedstatement.setDouble(4, age);
                preparedstatement.executeUpdate();
                System.out.println("���ݲ���ɹ�!");
            }
            statement=con.createStatement();
            resultset=statement.executeQuery(sql2);
            System.out.println("�ƿ�20�ı����������:");
            System.out.println("���       |����       |�Ա�      |����       ");

            while(resultset.next()){
                number=resultset.getInt("number");
                name=resultset.getString("name");
                sex=resultset.getString("sex");
                age=resultset.getDouble("age");
                System.out.println(number+"          |"+name+"       |"+sex+"   |"+age);
            }
            con.close();
            System.out.println("���ݿ��ѳɹ��˳�!");
        }
        catch (SQLException e){
            System.out.println(e);
        }

    }

}
