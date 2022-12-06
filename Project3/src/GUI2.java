import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.ArrayList;

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
                    JOptionPane.showMessageDialog(null,"当前记录为空,请勿删除!","警告",JOptionPane.ERROR_MESSAGE);
                    input_id.setText("");
                }
                //System.out.println(content+"??");
                else {
                    int flag = 0;
                    for (int i = 0; i < Main.data.size(); i++) {
                        if (content.equals(Main.data.get(i).get_id().replace(" ", ""))) {
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

    public void new_Dialog2(String title){
        if(Dialogflag==1) {//避免多次点击出现多个窗口,只允许出现一个窗口
            return ;
        }
        Dialogflag=1;
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(400,300);
        temp.setLocationRelativeTo(null);//水平居中放置
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {//设置关闭监视器
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
    }

    public void new_Dialog3(String title){
        if(Dialogflag==1) {//避免多次点击出现多个窗口,只允许出现一个窗口
            return ;
        }
        Dialogflag=1;
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(400,300);
        temp.setLocationRelativeTo(null);//水平居中放置
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {//设置关闭监视器
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
    }

    public void clickinit() {
        buttonexit.addActionListener(e->{
            System.exit(0);
        });
        buttonflush.addActionListener(e->{
            sqlinit();

            String thead[]={"序号id","姓名name","性别sex","出生birth","地址addr","工资salary"};
            String tbody[][]=to_list(Main.data);
            TableModel dataModel = new DefaultTableModel(tbody,thead);
            table.setModel(dataModel);
        });

        buttonadd.addActionListener(e->{
            new_Dialog2("添加记录");
        });

        buttonupdate.addActionListener(e->{
            new_Dialog3("修改记录");
        });

        buttondelete.addActionListener(e -> {
           new_Dialog1("删除记录","取消",3);

        });
        buttonhelp.addActionListener(e -> {
           new_Dialog1("使用帮助","返回",4);
        });


    }

}
