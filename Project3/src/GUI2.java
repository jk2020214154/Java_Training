import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: SDTBU_LY
 * Date: 2022-12-05
 * Time: 16:47
 */
public class GUI2 extends JDialog {
    JScrollPane tablePanel=new JScrollPane();
    JTable table=new JTable();
    JPanel panel=new JPanel();
    JPanel panel1=new JPanel();
    JLabel label=new JLabel("请输入职员id:");
    JTextField inputid=new JTextField(15);
    JButton buttonsearch=new JButton("搜索");
    JButton buttonflush=new JButton("刷新");
    JPanel panel2=new JPanel();
    JPanel panel3=new JPanel();
    JPanel panel4=new JPanel();

    JPanel panel5=new JPanel();
    JButton buttonadd=new JButton("添加记录");
    JButton buttondelete=new JButton("删除记录");
    JButton buttonupdate=new JButton("修改记录");
    JButton buttonexit=new JButton("退出系统");

    JButton buttonhelp=new JButton("使用帮助");

    //sql相关
    Connection con;
    Statement statement;
    PreparedStatement preparedstatement;
    ResultSet resultset;

    int Dialogflag=0;

    public GUI2(Frame owner,boolean modal) {
        super(owner,modal);
        this.setTitle("职员信息管理系统");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);//水平居中放置
        tableinit();
        sqlinit();
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);

        String thead[]={"序号id","姓名name","性别sex","出生birth","地址addr","工资salary"};
        String tbody[][]=to_list(Main.data);
        TableModel dataModel = new DefaultTableModel(tbody,thead);
        table.setDefaultRenderer(Object.class,cr);
        table.setModel(dataModel);

        clickinit();//设置事件行为初始化

        this.setVisible(true);//设置可见性
        this.setResizable(false);//不可重新修改尺寸

    }
    public void tableinit() {
        table.getTableHeader().setReorderingAllowed(false);//设置不可整列移动
        table.getTableHeader().setResizingAllowed(false);//设置在头间拖动来调整各列的大小
        table.setEnabled(false);//设置是否启用此组件
        tablePanel.setBounds(50,50,500,100);
        tablePanel.setViewportView(table);//创建一个视口，并在括号内设置其视图
        panel2.add(tablePanel);
        panel1.add(label);
        panel1.add(inputid);
        panel1.add(buttonsearch);
        panel1.add(buttonflush);
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));
        panel4.setLayout(new FlowLayout(FlowLayout.CENTER,100,10));

        panel3.add(buttonadd);
        panel3.add(buttonupdate);
        panel4.add(buttondelete);
        panel4.add(buttonhelp);
        panel4.add(buttonexit);

        panel5.setLayout(new BorderLayout());
        panel5.add(panel3,BorderLayout.PAGE_START);
        panel5.add(panel4,BorderLayout.CENTER);

        panel.setLayout(new BorderLayout());
        panel.add(panel1,BorderLayout.PAGE_START);

        panel.add(panel2,BorderLayout.CENTER);
        panel.add(panel5,BorderLayout.PAGE_END);
        this.add(panel);
    }

    public String[][] to_list(ArrayList<Staff> data) {
        String tempbody[][]=new String[data.size()][6];
        for(int i=0;i<data.size();i++){
            Staff staff=data.get(i);
            tempbody[i][0]=staff.get_id();
            tempbody[i][1]=staff.get_name();
            tempbody[i][2]=staff.get_sex();
            tempbody[i][3]=staff.get_day();
            tempbody[i][4]=staff.get_address();
            tempbody[i][5]=Float.toString(staff.get_salary());
        }
        return tempbody;
    }
    public void sqlinit() {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            System.out.println("数据库驱动已成功启动!");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        try {
            con = DriverManager.getConnection("jdbc:derby:E:\\大学\\专业课\\3.1Java\\实训\\移动公司;create=true");
            System.out.println("数据库已成功连接!");

            statement = con.createStatement();
            String sql1="Select * from 职员表";
            resultset=statement.executeQuery(sql1);
            System.out.println("查询成功!");
            Main.data.clear();
            while(resultset.next()){
                Staff temp=new Staff();
                temp.id=resultset.getString("id").replace(" ","");
                temp.name=resultset.getString("姓名").replace(" ","");
                temp.sex=resultset.getString("性别").replace(" ", "");
                temp.sqldate=resultset.getDate("出生");
                temp.to_string();
                temp.address=resultset.getString("地址").replace(" ", "");
                temp.salary=resultset.getFloat("工资");
                Main.data.add(temp);
                System.out.println(temp.get_id()+"          |"+temp.get_name()+"       |"+temp.get_sex()+"   |"+temp.get_day()+"    |"+temp.get_address()+"                |"+temp.get_salary());
            }
            con.close();
        }
        catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void new_Dialog1(String title,String button,int op){
        if(Dialogflag==1) {//避免多次点击出现多个窗口,只允许出现一个窗口
            return ;
        }
        Dialogflag=1;
        //System.out.println("hello");
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(400,210);
        temp.setLocationRelativeTo(null);//水平居中放置
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
        JLabel label_id=new JLabel("    序号");
        JLabel label_info1=new JLabel("1.修改记录：输入原有职员的id并输入修改的信息");
        JLabel label_info2=new JLabel("2.添加记录：输入需要添加的职员的信息即可");
        JLabel label_info3 = new JLabel("3.删除记录：输入需要删除的职员的id再点击删除即可");
        JLabel label_info4 = new JLabel("4.查询记录：输入需要查询的职员的id再点击搜索即可");
        JLabel label_info5 = new JLabel("5.刷新记录：将数据库中的信息重新加载在表格中");
        JLabel label_info6 = new JLabel("6.退出系统：关闭程序");

        JTextField input_id=new JTextField(15);
        JPanel panel_1=new JPanel();
        JPanel panel_2=new JPanel();
        JPanel panel_3 = new JPanel();
        JButton button_1=new JButton("删除");
        JButton button_2 = new JButton(button);
        panel_3.setLayout(new BorderLayout());
        if(op==4){//使用帮助

            //panel_1.setLayout(new BorderLayout());
            panel_1.add(label_info1);
            panel_1.add(label_info2);

            panel_1.add(label_info3);
            panel_1.add(label_info4);
            panel_1.add(label_info5);
            panel_1.add(label_info6);
            panel_2.add(button_2);
            panel_3.add(panel_1,BorderLayout.CENTER);
            panel_3.add(panel_2,BorderLayout.PAGE_END);
            temp.add(panel_3);
            temp.setVisible(true);
            button_2.addActionListener(e->{
                temp.dispose();
                Dialogflag=0;
            });
        }
        else if(op==3){

            temp.setSize(400,110);
            panel_1.add(label_id);
            panel_1.add(input_id);
            panel_2.add(button_1);
            panel_2.add(button_2);
            panel_3.add(panel_1,BorderLayout.CENTER);
            panel_3.add(panel_2,BorderLayout.PAGE_END);
            temp.add(panel_3);
            temp.setVisible(true);

            button_1.addActionListener(e -> {
                String content=input_id.getText();
                content=content.replaceAll(" ","");
                if(Main.data.size()==0) {
                    JOptionPane.showMessageDialog(null,"该记录表为空,请勿删除!","警告",JOptionPane.ERROR_MESSAGE);
                    input_id.setText("");
                }
                //System.out.println(content+"??");
                else {
                    int flag = 0;
                    for (int i = 0; i < Main.data.size(); i++) {
                        if (content.equals(Main.data.get(i).get_id())) {
                            //System.out.println("???");
                            flag = 1;
                            input_id.setText("");
                            Main.data.remove(i);


                            String thead[] = {"序号id", "姓名name", "性别sex", "出生birth", "地址addr", "工资salary"};
                            String tbody[][] = to_list(Main.data);
                            TableModel dataModel = new DefaultTableModel(tbody, thead);
                            table.setModel(dataModel);

                            try {
                                con = DriverManager.getConnection("jdbc:derby:E:\\大学\\专业课\\3.1Java\\实训\\移动公司;create=true");
                                System.out.println("数据库已成功连接!");
                                String sql = "delete from 职员表 where id=?";
                                preparedstatement = con.prepareStatement(sql);
                                preparedstatement.setString(1, content);
                                preparedstatement.executeUpdate();
                                con.close();
                                System.out.println("数据库已关闭!");
                            } catch (SQLException eve) {
                                System.out.println(eve);
                            }
                            ;
                            JOptionPane.showMessageDialog(null, "删除成功!", "删除记录", JOptionPane.PLAIN_MESSAGE);

                            break;
                        }
                    }
                    if (flag == 0) {
                        input_id.setText("");
                        JOptionPane.showMessageDialog(null, "该条记录不存在,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                    }
                }

            });

            button_2.addActionListener(e->{
                temp.dispose();
                Dialogflag=0;
            });
        }
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }


    public void new_Dialog2(String title,String button,int op){
        if(Dialogflag==1) {//避免多次点击出现多个窗口,只允许出现一个窗口
            return ;
        }
        Dialogflag=1;
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(420,260);
        temp.setLocationRelativeTo(null);//水平居中放置
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {//设置关闭监视器
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
        JLabel label_1=new JLabel("   序号：");
        JLabel label_2=new JLabel("   姓名：");
        JLabel label_3= new JLabel("  性别：");
        JLabel label_4= new JLabel("  出生：");
        JLabel label_5= new JLabel("  地址：");
        JLabel label_6= new JLabel("  工资：");
        JTextField input_1=new JTextField(15);
        JTextField input_2=new JTextField(15);
        JTextField input_3=new JTextField(15);
        JTextField input_4=new JTextField(15);
        JTextField input_5=new JTextField(15);
        JTextField input_6=new JTextField(15);

        JButton button1=new JButton(button);
        JButton button2=new JButton("取消");

        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();
        JPanel panel_3 = new JPanel();
        JPanel panel_4 = new JPanel();
        JPanel panel_5 = new JPanel();
        JPanel panel_6 = new JPanel();
        JPanel panel_7 = new JPanel();
        JPanel panel_8 = new JPanel();
        JPanel panel_10 = new JPanel();
        panel_1.add(label_1);panel_1.add(input_1);
        panel_2.add(label_2);panel_2.add(input_2);
        panel_3.add(label_3);panel_3.add(input_3);
        panel_4.add(label_4);panel_4.add(input_4);
        panel_5.add(label_5);panel_5.add(input_5);
        panel_6.add(label_6);panel_6.add(input_6);
        panel_10.add(button1);panel_10.add(button2);
        panel_7.setLayout(new BorderLayout());
        panel_8.setLayout(new BorderLayout());
        temp.setLayout(new BorderLayout());

        panel_7.add(panel_1,BorderLayout.PAGE_START);
        panel_7.add(panel_2,BorderLayout.CENTER);
        panel_7.add(panel_3,BorderLayout.PAGE_END);
        panel_8.add(panel_4,BorderLayout.PAGE_START);
        panel_8.add(panel_5,BorderLayout.CENTER);
        panel_8.add(panel_6,BorderLayout.PAGE_END);

        temp.add(panel_7,BorderLayout.PAGE_START);
        temp.add(panel_8,BorderLayout.CENTER);
        temp.add(panel_10,BorderLayout.PAGE_END);
        temp.setVisible(true);

        button2.addActionListener(e->{
            Dialogflag=0;
            temp.dispose();
        });

        if(op==1) {
            button1.addActionListener(e->{
                int flag = 1;
                Staff sta=new Staff();
                sta.id=input_1.getText().replace(" ","");
                if(sta.id!=null&&!sta.id.trim().equals("")) {
                        sta.name = input_2.getText().replace(" ", "");
                        sta.sex = input_3.getText().replace(" ", "");
                        if(!sta.sex.equals("男")&&!sta.sex.equals("女"))
                        {
                            JOptionPane.showMessageDialog(null, "性别应只有男女,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                            input_1.setText("");input_2.setText("");input_3.setText("");
                            input_4.setText("");input_5.setText("");input_6.setText("");
                            flag=0;
                        }
                        if(flag==1) {
                            sta.day = input_4.getText().replace(" ", "");
                            sta.to_sql();
                            sta.address = input_5.getText().replace(" ", "");
                            String num=input_6.getText().replace(" ", "");
                            if (isNumeric(num) == true)
                                sta.salary = Float.parseFloat(num);
                            else {
                                JOptionPane.showMessageDialog(null, "输入工资不为数字,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                input_1.setText("");
                                input_2.setText("");
                                input_3.setText("");
                                input_4.setText("");
                                input_5.setText("");
                                input_6.setText("");
                                flag = 0;
                            }
                        }
                        for (int i = 0; i < Main.data.size()&&flag==1; i++) {
                            if (sta.id.equals(Main.data.get(i).id)) {
                                JOptionPane.showMessageDialog(null, "该条记录的序号已存在,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                input_1.setText("");input_2.setText("");input_3.setText("");
                                input_4.setText("");input_5.setText("");input_6.setText("");
                                flag = 0;
                                break;
                            }
                        }
                        if (flag == 1) {
                            try {
                                con = DriverManager.getConnection("jdbc:derby:E:\\大学\\专业课\\3.1Java\\实训\\移动公司;create=true");
                                System.out.println("数据库已成功连接!");
                                String sql = "Insert into 职员表 values(?,?,?,?,?,?)";
                                preparedstatement = con.prepareStatement(sql);
                                preparedstatement.setString(1, sta.id);
                                preparedstatement.setString(2, sta.name);
                                preparedstatement.setString(3, sta.sex);
                                preparedstatement.setDate(4, sta.sqldate);
                                preparedstatement.setString(5, sta.address);
                                preparedstatement.setFloat(6, sta.salary);
                                preparedstatement.executeUpdate();
                                JOptionPane.showMessageDialog(null, "添加成功!", "添加记录", JOptionPane.PLAIN_MESSAGE);
                                sqlinit();
                                String thead[] = {"序号id", "姓名name", "性别sex", "出生birth", "地址addr", "工资salary"};
                                String tbody[][] = to_list(Main.data);
                                TableModel dataModel = new DefaultTableModel(tbody, thead);
                                table.setModel(dataModel);
                                con.close();
                                System.out.println("数据库已关闭!");
                            } catch (SQLException eve) {
                                System.out.println(eve);
                            }
                        }
                }
                else{
                    JOptionPane.showMessageDialog(null, "输入序号不应有空白字符,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                    input_1.setText("");input_2.setText("");input_3.setText("");
                    input_4.setText("");input_5.setText("");input_6.setText("");
                }
            });

        }
    }

    public void new_Dialog3(String title,String button,int op){
        if(Dialogflag==1) {//避免多次点击出现多个窗口,只允许出现一个窗口
            return ;
        }
        Dialogflag=1;
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(600,260);
        temp.setLocationRelativeTo(null);//水平居中放置
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {//设置关闭监视器
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
        JLabel label_1=new JLabel("   原序号：");
        JLabel label_11=new JLabel("   新序号：");
        JLabel label_2=new JLabel("   原姓名：");
        JLabel label_12=new JLabel("   新姓名：");
        JLabel label_3= new JLabel("  原性别：");
        JLabel label_13= new JLabel("  新性别：");
        JLabel label_4= new JLabel("  原出生：");
        JLabel label_14= new JLabel("  新出生：");
        JLabel label_5= new JLabel("  原地址：");
        JLabel label_15= new JLabel("  新地址：");
        JLabel label_6= new JLabel("  原工资：");
        JLabel label_16= new JLabel("  新工资：");
        JTextField input_1=new JTextField(15);
        JTextField input_2=new JTextField(15);
        input_2.setEditable(false);
        JTextField input_3=new JTextField(15);
        input_3.setEditable(false);
        JTextField input_4=new JTextField(15);
        input_4.setEditable(false);
        JTextField input_5=new JTextField(15);
        input_5.setEditable(false);
        JTextField input_6=new JTextField(15);
        input_6.setEditable(false);
        JTextField input_11=new JTextField(15);
        JTextField input_12=new JTextField(15);
        JTextField input_13=new JTextField(15);
        JTextField input_14=new JTextField(15);
        JTextField input_15=new JTextField(15);
        JTextField input_16=new JTextField(15);
        JButton button1=new JButton(button);
        JButton button2=new JButton("取消");
        JButton button3=new JButton("确定序号");

        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();
        JPanel panel_3 = new JPanel();
        JPanel panel_4 = new JPanel();
        JPanel panel_5 = new JPanel();
        JPanel panel_6 = new JPanel();
        JPanel panel_7 = new JPanel();
        JPanel panel_8 = new JPanel();
        JPanel panel_10 = new JPanel();
        panel_1.add(label_1);panel_1.add(input_1);panel_1.add(label_11);panel_1.add(input_11);
        panel_2.add(label_2);panel_2.add(input_2);panel_2.add(label_12);panel_2.add(input_12);
        panel_3.add(label_3);panel_3.add(input_3);panel_3.add(label_13);panel_3.add(input_13);
        panel_4.add(label_4);panel_4.add(input_4);panel_4.add(label_14);panel_4.add(input_14);
        panel_5.add(label_5);panel_5.add(input_5);panel_5.add(label_15);panel_5.add(input_15);
        panel_6.add(label_6);panel_6.add(input_6);panel_6.add(label_16);panel_6.add(input_16);
        panel_10.add(button1);panel_10.add(button2);panel_10.add(button3);
        panel_7.setLayout(new BorderLayout());
        panel_8.setLayout(new BorderLayout());
        temp.setLayout(new BorderLayout());

        panel_7.add(panel_1,BorderLayout.PAGE_START);
        panel_7.add(panel_2,BorderLayout.CENTER);
        panel_7.add(panel_3,BorderLayout.PAGE_END);
        panel_8.add(panel_4,BorderLayout.PAGE_START);
        panel_8.add(panel_5,BorderLayout.CENTER);
        panel_8.add(panel_6,BorderLayout.PAGE_END);

        temp.add(panel_7,BorderLayout.PAGE_START);
        temp.add(panel_8,BorderLayout.CENTER);
        temp.add(panel_10,BorderLayout.PAGE_END);
        temp.setVisible(true);



        if(op==2) {
            if(Main.data.size()==0){
                JOptionPane.showMessageDialog(null,"该记录表为空,请勿删除!","警告",JOptionPane.ERROR_MESSAGE);
            }
            else{
                button2.addActionListener(e->{
                    Dialogflag=0;
                    temp.dispose();
                });
                button3.addActionListener(e -> {
                    String tempid=input_1.getText().replace(" ","");
                    int flag=0;
                    if(tempid!=null&&!tempid.trim().equals(""))
                    {
                        for (int i = 0; i < Main.data.size(); i++) {
                            if (tempid.equals(Main.data.get(i).id.replace(" ","")))
                            {
                                flag=1;
                                input_2.setText(Main.data.get(i).name);
                                input_3.setText(Main.data.get(i).sex);
                                input_4.setText(Main.data.get(i).day);
                                input_5.setText(Main.data.get(i).address);
                                input_6.setText(Float.toString(Main.data.get(i).salary));
                                JOptionPane.showMessageDialog(null, "同步原数据成功!", "修改记录", JOptionPane.PLAIN_MESSAGE);
                                break;
                            }
                        }
                        if (flag == 0) {
                            JOptionPane.showMessageDialog(null, "该条记录的序号不存在,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                            input_1.setText("");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "输入序号不应有空白字符,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                        input_1.setText("");
                    }
                });

                button1.addActionListener(e->{
                    int flag=0;
                    int flaggg[]=new int[7];
                    String tempid=input_1.getText().replace(" ","");

                    try {
                        con = DriverManager.getConnection("jdbc:derby:E:\\大学\\专业课\\3.1Java\\实训\\移动公司;create=true");
                        System.out.println("数据库已成功连接!");
                        Staff sta=new Staff();
                        sta.id=input_11.getText().replace(" ","");
                        if(flag>=0) {
                            if (sta.id != null && !sta.id.trim().equals("")) {
                                if (sta.id.equals(input_1.getText().replace(" ", ""))) {
                                    flag = -1;
                                    JOptionPane.showMessageDialog(null, "新序号与原序号相同,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {
                                    int flagg = 0;
                                    for (int i = 0; i < Main.data.size(); i++) {
                                        if (sta.id.equals(Main.data.get(i).id)) {
                                            flagg = 1;
                                            flag = 0;
                                            JOptionPane.showMessageDialog(null, "新序号与记录表中的序号相同,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                            input_11.setText("");
                                            input_12.setText("");
                                            input_13.setText("");
                                            input_14.setText("");
                                            input_15.setText("");
                                            input_16.setText("");
                                            break;
                                        }
                                    }
                                    if (flagg == 0) {
                                        flaggg[1] = 1;
                                        flag++;
                                    }
                                }
                            }
                        }
                        if(flag>=0) {
                            sta.name = input_12.getText().replace(" ", "");
                            if (sta.name != null && !sta.name.trim().equals("")) {
                                if (sta.name.equals(input_2.getText().replace(" ", ""))) {
                                    flag = -1;
                                    JOptionPane.showMessageDialog(null, "新名字与原名字相同,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//必然有该元素
                                    flaggg[2] = 1;
                                    flag++;
                                }
                            }
                        }
                        if(flag>=0) {
                            sta.sex = input_13.getText().replace(" ", "");
                            if (sta.sex != null && !sta.sex.trim().equals("")) {
                                if (sta.name.equals(input_3.getText().replace(" ", ""))) {
                                    flag = -1;
                                    JOptionPane.showMessageDialog(null, "新性别与原性别相同,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//必然有该元素
                                    if (!sta.sex.equals("男") && !sta.sex.equals("女")) {
                                        flag = -1;
                                        JOptionPane.showMessageDialog(null, "性别应只有男女,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                        input_11.setText("");
                                        input_12.setText("");
                                        input_13.setText("");
                                        input_14.setText("");
                                        input_15.setText("");
                                        input_16.setText("");
                                    } else {
                                        flaggg[3] = 1;
                                        flag++;
                                    }
                                }
                            }
                        }
                        if(flag>=0) {
                            sta.day = input_14.getText().replace(" ", "");
                            sta.to_sql();
                            if (sta.day != null && !sta.day.trim().equals("")) {
                                if (sta.day.equals(input_4.getText().replace(" ", ""))) {
                                    flag = -1;
                                    JOptionPane.showMessageDialog(null, "新出生与原出生相同,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//必然有该元素
                                    flaggg[4] = 1;
                                    flag++;
                                }
                            }
                        }
                        if(flag>=0) {
                            sta.address = input_15.getText().replace(" ", "");
                            if (sta.address != null && !sta.address.trim().equals("")) {
                                if (sta.address.equals(input_5.getText().replace(" ", ""))) {
                                    flag = -1;
                                    JOptionPane.showMessageDialog(null, "新地址与原地址相同,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//必然有该元素
                                    flaggg[5] = 1;
                                    flag++;
                                }
                            }
                        }
                        String tempsalary = input_16.getText().replace(" ", "");
                        if(flag>=0) {
                            if (tempsalary != null && !tempsalary.trim().equals("")) {
                                if (tempsalary.equals(input_6.getText().replace(" ", ""))) {
                                    flag = -1;
                                    JOptionPane.showMessageDialog(null, "新工资与原工资相同,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//必然有该元素
                                    if (isNumeric(tempsalary) == false) {
                                        flag = -1;
                                        JOptionPane.showMessageDialog(null, "输入工资不为数字,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                                        input_11.setText("");
                                        input_12.setText("");
                                        input_13.setText("");
                                        input_14.setText("");
                                        input_15.setText("");
                                        input_16.setText("");
                                    } else {
                                        flaggg[6] = 1;
                                        flag++;
                                    }
                                }
                            }
                        }
                        if(flag>0) {
                            for(int i=1;i<=6;i++)
                            {
                                if(flaggg[i]==1){
                                    if(i==1){
                                        String sql = "update 职员表 set id=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setString(1, sta.id);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                        tempid = sta.id;
                                    }else if(i==2){
                                        String sql = "update 职员表 set 姓名=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        System.out.println(sta.name + "???");
                                        preparedstatement.setString(1, sta.name);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==3){
                                        String sql = "update 职员表 set 性别=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setString(1, sta.sex);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==4){
                                        String sql = "update 职员表 set 出生=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setDate(1, sta.sqldate);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==5){
                                        String sql = "update 职员表 set 地址=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setString(1, sta.address);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==6){
                                        sta.salary = Float.parseFloat(tempsalary);
                                        String sql = "update 职员表 set 工资=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setFloat(1, sta.salary);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }
                                }
                            }

                            JOptionPane.showMessageDialog(null, "修改成功!", "修改记录", JOptionPane.PLAIN_MESSAGE);
                            sqlinit();
                            String thead[] = {"序号id", "姓名name", "性别sex", "出生birth", "地址addr", "工资salary"};
                            String tbody[][] = to_list(Main.data);
                            TableModel dataModel = new DefaultTableModel(tbody, thead);
                            table.setModel(dataModel);
                            input_1.setText("");input_11.setText("");input_2.setText("");input_12.setText("");
                            input_3.setText("");input_13.setText("");input_4.setText("");input_14.setText("");
                            input_5.setText("");input_15.setText("");input_6.setText("");input_16.setText("");
                        }
                        else{
                            input_11.setText("");input_12.setText("");input_13.setText("");
                            input_14.setText("");input_15.setText("");input_16.setText("");
                        }
                        con.close();
                        System.out.println("数据库已关闭!");
                    }catch (SQLException eve) {
                        System.out.println(eve);
                    }
                });
            }
        }

    }



    public void clickinit() {
        buttonexit.addActionListener(e->{
            System.exit(0);
        });
        buttonflush.addActionListener(e->{
            sqlinit();
            inputid.setText("");
            String thead[]={"序号id","姓名name","性别sex","出生birth","地址addr","工资salary"};
            String tbody[][]=to_list(Main.data);
            TableModel dataModel = new DefaultTableModel(tbody,thead);
            table.setModel(dataModel);
        });

        buttonsearch.addActionListener(e->{
            String content=inputid.getText();
            if(content!=null&&!content.trim().equals("")) {
                String sql = "select * from 职员表 where id=?";
                int cnt = 0;
                try {
                    con = DriverManager.getConnection("jdbc:derby:E:\\大学\\专业课\\3.1Java\\实训\\移动公司;create=true");
                    System.out.println("数据库已成功连接!");
                    preparedstatement = con.prepareStatement(sql);
                    preparedstatement.setString(1, content);
                    resultset = preparedstatement.executeQuery();
                    System.out.println("数据库已关闭!");
                    Main.data.clear();

                    while (resultset.next()) {
                        cnt++;
                        Staff temp = new Staff();
                        temp.id = resultset.getString("id").replace(" ", "");
                        temp.name = resultset.getString("姓名").replace(" ", "");
                        temp.sex = resultset.getString("性别").replace(" ", "");
                        temp.sqldate = resultset.getDate("出生");
                        temp.to_string();
                        temp.address = resultset.getString("地址").replace(" ", "");

                        temp.salary = resultset.getFloat("工资");
                        Main.data.add(temp);
                        System.out.println(temp.get_id() + "          |" + temp.get_name() + "       |" + temp.get_sex() + "   |" + temp.get_day() + "    |" + temp.get_address() + "                |" + temp.get_salary());
                    }
                    if (cnt == 0) {
                        JOptionPane.showMessageDialog(null, "该条记录不存在,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                        inputid.setText("");
                    }
                    con.close();
                } catch (SQLException eve) {
                    System.out.println(eve);

                }
                if(cnt==0)
                    sqlinit();
                String thead[]={"序号id","姓名name","性别sex","出生birth","地址addr","工资salary"};
                String tbody[][]=to_list(Main.data);
                TableModel dataModel = new DefaultTableModel(tbody,thead);
                table.setModel(dataModel);
            }
            else {
                JOptionPane.showMessageDialog(null, "输入格式不应有空白字符,请重新输入!", "警告", JOptionPane.ERROR_MESSAGE);
                inputid.setText("");
            }
        });

        buttonadd.addActionListener(e->{
            new_Dialog2("添加记录(日期的格式为yyyy-mm-dd,且输入均为有效内容)","添加",1);
        });

        buttonupdate.addActionListener(e->{
            new_Dialog3("修改记录(输入原序号后点击确定序号,显示原纪录的信息,只对已填写的信息进行修改,且输入均为有效内容)","修改",2);
        });

        buttondelete.addActionListener(e -> {
           new_Dialog1("删除记录","取消",3);

        });
        buttonhelp.addActionListener(e -> {
           new_Dialog1("使用帮助","返回",4);
        });


    }

}
