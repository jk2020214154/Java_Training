import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.*;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: SDTBU_LY
 * Date: 2022-12-05
 * Time: 14:38
 */
public class GUI extends JFrame{
    //设计登陆界面
    JPanel panel1 = new JPanel();
    JTextField usernameinput = new JTextField(20);
    JPasswordField passwordinput = new JPasswordField(20);
    JLabel labeluser =new JLabel("用户名 : ");
    JLabel labelpassword = new JLabel(" 密 码 : ");
    JButton buttonlogin = new JButton("登陆");
    JButton buttonlogout = new JButton("退出");

    JButton buttonhelp = new JButton("帮助");


    public GUI(String title,int width,int height){
        //此处应设置尺寸,再居中
        this.setSize(width,height);//设置长宽
        init(title);
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
        panel1.add(buttonlogin);
        panel1.add(buttonlogout);

        buttonhelp.setBounds(360,410,100,30);
        panel1.add(buttonhelp);

    }


}
