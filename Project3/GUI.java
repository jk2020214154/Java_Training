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
    //��Ƶ�½����
    int GUIflag=0;
    public static Map<String,String> map=new HashMap<String,String>();
    JPanel panel1 = new JPanel();
    JTextField usernameinput = new JTextField(20);
    JPasswordField passwordinput = new JPasswordField(20);
    JLabel labeluser =new JLabel("�û��� : ");
    JLabel labelpassword = new JLabel(" �� �� : ");
    JButton buttonlogin = new JButton("��½");
    JButton buttonlogout = new JButton("�˳�");

    JButton buttonregister =new JButton("ע��");
    JButton buttonhelp = new JButton("����");


    public GUI(String title,int width,int height){
        //�˴�Ӧ���óߴ�,�پ���
        this.setSize(width,height);//���ó���
        init(title);
        map.put("admin","root");//Ĭ���û�Ϊadmin,����Ϊroot
        this.setVisible(true);//���ÿɼ���
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//����Ĭ�Ϲرշ�ʽ
        buttonlogin.addActionListener(e->{
            String contentusername=usernameinput.getText();
            String contentpasswd=String.valueOf(passwordinput.getPassword());//�˴�����ʹ��toString���Ϊ��������
            //����ȥ����β�հ��ַ��Ƿ���ʵ������
            if((contentusername!=null&&!contentusername.trim().equals(""))&&(contentpasswd!=null&&!contentpasswd.trim().equals("")))
            {
                if(!new Password().checkPass(contentusername,contentpasswd)){
                    JOptionPane.showMessageDialog(null,"�˺��������,����������","��Ϣ��ʾ",JOptionPane.ERROR_MESSAGE);
                    usernameinput.setText("");
                    passwordinput.setText("");
                }
                else{
                    this.dispose();
                    //��¼�ɹ�,�½�����ʾ
                    new GUI2(this,true);
                    System.exit(0);
                }
            }
            else {
                JOptionPane.showMessageDialog(null,"��Ч�û�,����������","��Ϣ��ʾ",JOptionPane.ERROR_MESSAGE);
                usernameinput.setText("");
                passwordinput.setText("");
            }
        });
        buttonlogout.addActionListener(e->{System.exit(0);});
        buttonhelp.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,"Ĭ���û���Ϊ:admin,������:root","����",JOptionPane.PLAIN_MESSAGE);
        });
        buttonregister.addActionListener(e->{
            new_Dialog("ע���û�","ȡ��");
        });
    }

    public void init(String title) {
        this.setTitle(title);
        this.setLocationRelativeTo(null);//ˮƽ���з���
        ImageIcon icon=new ImageIcon("favicon.png");
        this.setIconImage(icon.getImage());//�ı����Ͻ�ͼ��
        panel1.setBorder(new EmptyBorder(5,5,5,5));//���ñ߿�
        this.setContentPane(panel1);//����������
        panel1.setLayout(null);//��ղ���,����������

        JLabel label2=new JLabel("    ְԱ��Ϣ����ϵͳ");
        label2.setFont(new Font("Dialog", Font.BOLD, 20));

        label2.setBounds(140,120,200,30);
        panel1.add(label2);

        //��������ǰ��ɫ��λ�ú�����
        labeluser.setForeground(Color.BLUE);
        labeluser.setBounds(70,180,100,25);
        labeluser.setFont(new Font("Dialog", Font.BOLD, 20));
        labelpassword.setForeground(Color.BLUE);
        labelpassword.setBounds(80,230,100,25);
        labelpassword.setFont(new Font("Dialog", Font.BOLD, 20));

        panel1.add(labeluser);//��ӵ����
        panel1.add(labelpassword);//��ӵ����


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
        temp.setLocationRelativeTo(null);//ˮƽ���з���
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent eve){
                GUIflag=0;
            }
        });

        JLabel label_1=new JLabel("   �û�����");
        JLabel label_2=new JLabel("    ��  �� ��");
        JTextField input_1=new JTextField(15);
        JPasswordField input_2=new JPasswordField(15);
        JButton button1=new JButton("ע��");
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
                   JOptionPane.showMessageDialog(null, "���û��Ѵ���,����������", "��Ϣ��ʾ", JOptionPane.ERROR_MESSAGE);
                   input_1.setText("");
                   input_2.setText("");
               } else {
                   map.put(tempuser, temppass);
                   JOptionPane.showMessageDialog(null, "ע��ɹ�!", "ע���û�", JOptionPane.PLAIN_MESSAGE);
                   input_1.setText("");
                   input_2.setText("");
               }
           }
           else {
               JOptionPane.showMessageDialog(null,"��Ч�û�,����������","��Ϣ��ʾ",JOptionPane.ERROR_MESSAGE);
               input_1.setText("");
               input_2.setText("");
           }
        });
    }
}


