import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: SDTBU_LY
 * Date: 2022-12-05
 * Time: 14:38
 */
public class GUI extends JFrame{
    //设计登陆界面
    int GUIflag=0;
    public static Map<String,String> map=new HashMap<String,String>();
    JPanel panel1 = new JPanel();
    JTextField usernameinput = new JTextField(20);
    JPasswordField passwordinput = new JPasswordField(20);
    JLabel labeluser =new JLabel("用户名 : ");
    JLabel labelpassword = new JLabel(" 密 码 : ");
    JButton buttonlogin = new JButton("登陆");
    JButton buttonlogout = new JButton("退出");

    JButton buttonregister =new JButton("注册");
    JButton buttonhelp = new JButton("帮助");


    public GUI(String title,int width,int height){
        //此处应设置尺寸,再居中
        this.setSize(width,height);//设置长宽
        init(title);
        map.put("admin","root");//默认用户为admin,密码为root
        this.setVisible(true);//设置可见性
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//设置默认关闭方式
        buttonlogin.addActionListener(e->{
            String contentusername=usernameinput.getText();
            String contentpasswd=String.valueOf(passwordinput.getPassword());//此处不能使用toString会变为其它内容
            //其中去掉首尾空白字符是否有实质内容
            if((contentusername!=null&&!contentusername.trim().equals(""))&&(contentpasswd!=null&&!contentpasswd.trim().equals("")))
            {
                if(!new Password().checkPass(contentusername,contentpasswd)){
                    JOptionPane.showMessageDialog(null,"账号密码错误,请重新输入","消息提示",JOptionPane.ERROR_MESSAGE);
                    usernameinput.setText("");
                    passwordinput.setText("");
                }
                else{
                    this.dispose();
                    //登录成功,新界面显示
                    new GUI2(this,true);
                    System.exit(0);
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"无效用户,请重新输入","消息提示",JOptionPane.ERROR_MESSAGE);
                usernameinput.setText("");
                passwordinput.setText("");
            }
        });
        buttonlogout.addActionListener(e->{System.exit(0);});
        buttonhelp.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"默认用户名为:admin,密码是:root","帮助",JOptionPane.PLAIN_MESSAGE);
        });
        buttonregister.addActionListener(e->{
            new_Dialog("注册用户","取消");
        });
    }

    public void init(String title) {
        this.setTitle(title);
        this.setLocationRelativeTo(null);//水平居中放置
        ImageIcon icon=new ImageIcon("favicon.png");
        this.setIconImage(icon.getImage());//改变左上角图标
        panel1.setBorder(new EmptyBorder(5,5,5,5));//设置边框
        this.setContentPane(panel1);//获得面板内容
        panel1.setLayout(null);//清空布局,方便添加组件

        JLabel label2=new JLabel("    职员信息管理系统");
        label2.setFont(new Font("Dialog", Font.BOLD, 20));

        label2.setBounds(140,120,200,30);
        panel1.add(label2);

        //设置字体前景色和位置和字体
        labeluser.setForeground(Color.BLUE);
        labeluser.setBounds(70,180,100,25);
        labeluser.setFont(new Font("Dialog", Font.BOLD, 20));
        labelpassword.setForeground(Color.BLUE);
        labelpassword.setBounds(80,230,100,25);
        labelpassword.setFont(new Font("Dialog", Font.BOLD, 20));

        panel1.add(labeluser);//添加到面板
        panel1.add(labelpassword);//添加到面板


        usernameinput.setBounds(160,180,220,25);
        passwordinput.setBounds(160,230,220,25);
        panel1.add(usernameinput);
        panel1.add(passwordinput);

        buttonlogin.setBounds(140,300,100,30);
        buttonlogout.setBounds(260,300,100,30);
        buttonregister.setBounds(360,360,100,30);
        panel1.add(buttonlogin);
        panel1.add(buttonlogout);
        panel1.add(buttonregister);

        buttonhelp.setBounds(360,410,100,30);
        panel1.add(buttonhelp);

    }
    public void new_Dialog(String title,String button){
        if(GUIflag==1)
            return ;
        GUIflag=1;

        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(400,140);
        temp.setLocationRelativeTo(null);//水平居中放置
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent eve){
                GUIflag=0;
            }
        });

        JLabel label_1=new JLabel("   用户名：");
        JLabel label_2=new JLabel("    密  码 ：");
        JTextField input_1=new JTextField(15);
        JPasswordField input_2=new JPasswordField(15);
        JButton button1=new JButton("注册");
        JButton button2=new JButton(button);

        JPanel panel_1 = new JPanel();
        JPanel panel_2 = new JPanel();
        JPanel panel_3 = new JPanel();

        panel_1.add(label_1);
        panel_1.add(input_1);
        panel_2.add(label_2);
        panel_2.add(input_2);
        panel_3.add(button1);
        panel_3.add(button2);

        temp.setLayout(new BorderLayout());
        temp.add(panel_1,BorderLayout.PAGE_START);
        temp.add(panel_2,BorderLayout.CENTER);
        temp.add(panel_3,BorderLayout.PAGE_END);
        temp.setVisible(true);


        button2.addActionListener(e->{
            GUIflag=0;
            temp.dispose();
        });

        button1.addActionListener(e -> {
           String tempuser=input_1.getText().replace(" ","");
           String temppass=String.valueOf(input_2.getPassword());
           if((tempuser!=null&&!tempuser.trim().equals(""))&&(temppass!=null&&!temppass.trim().equals(""))) {
               if (map.get(tempuser) != null) {
                   JOptionPane.showMessageDialog(null, "该用户已存在,请重新输入", "消息提示", JOptionPane.ERROR_MESSAGE);
                   input_1.setText("");
                   input_2.setText("");
               } else {
                   map.put(tempuser, temppass);
                   JOptionPane.showMessageDialog(null, "注册成功!", "注册用户", JOptionPane.PLAIN_MESSAGE);
                   input_1.setText("");
                   input_2.setText("");
               }
           }
           else {
               JOptionPane.showMessageDialog(null,"无效用户,请重新输入","消息提示",JOptionPane.ERROR_MESSAGE);
               input_1.setText("");
               input_2.setText("");
           }
        });
    }
}


