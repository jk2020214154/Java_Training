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
    JLabel label=new JLabel("������ְԱid:");
    JTextField inputid=new JTextField(15);
    JButton buttonsearch=new JButton("����");
    JButton buttonflush=new JButton("ˢ��");
    JPanel panel2=new JPanel();
    JPanel panel3=new JPanel();
    JPanel panel4=new JPanel();

    JPanel panel5=new JPanel();
    JButton buttonadd=new JButton("��Ӽ�¼");
    JButton buttondelete=new JButton("ɾ����¼");
    JButton buttonupdate=new JButton("�޸ļ�¼");
    JButton buttonexit=new JButton("�˳�ϵͳ");

    JButton buttonhelp=new JButton("ʹ�ð���");

    //sql���
    Connection con;
    Statement statement;
    PreparedStatement preparedstatement;
    ResultSet resultset;

    int Dialogflag=0;

    public GUI2(Frame owner,boolean modal) {
        super(owner,modal);
        this.setTitle("ְԱ��Ϣ����ϵͳ");
        this.setSize(600,600);
        this.setLocationRelativeTo(null);//ˮƽ���з���
        tableinit();
        sqlinit();
        DefaultTableCellRenderer cr = new DefaultTableCellRenderer();
        cr.setHorizontalAlignment(JLabel.CENTER);

        String thead[]={"���id","����name","�Ա�sex","����birth","��ַaddr","����salary"};
        String tbody[][]=to_list(Main.data);
        TableModel dataModel = new DefaultTableModel(tbody,thead);
        table.setDefaultRenderer(Object.class,cr);
        table.setModel(dataModel);

        clickinit();//�����¼���Ϊ��ʼ��

        this.setVisible(true);//���ÿɼ���
        this.setResizable(false);//���������޸ĳߴ�

    }
    public void tableinit() {
        table.getTableHeader().setReorderingAllowed(false);//���ò��������ƶ�
        table.getTableHeader().setResizingAllowed(false);//������ͷ���϶����������еĴ�С
        table.setEnabled(false);//�����Ƿ����ô����
        tablePanel.setBounds(50,50,500,100);
        tablePanel.setViewportView(table);//����һ���ӿڣ�������������������ͼ
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
            System.out.println("���ݿ������ѳɹ�����!");
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

        try {
            con = DriverManager.getConnection("jdbc:derby:E:\\��ѧ\\רҵ��\\3.1Java\\ʵѵ\\�ƶ���˾;create=true");
            System.out.println("���ݿ��ѳɹ�����!");

            statement = con.createStatement();
            String sql1="Select * from ְԱ��";
            resultset=statement.executeQuery(sql1);
            System.out.println("��ѯ�ɹ�!");
            Main.data.clear();
            while(resultset.next()){
                Staff temp=new Staff();
                temp.id=resultset.getString("id").replace(" ","");
                temp.name=resultset.getString("����").replace(" ","");
                temp.sex=resultset.getString("�Ա�").replace(" ", "");
                temp.sqldate=resultset.getDate("����");
                temp.to_string();
                temp.address=resultset.getString("��ַ").replace(" ", "");
                temp.salary=resultset.getFloat("����");
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
        if(Dialogflag==1) {//�����ε�����ֶ������,ֻ�������һ������
            return ;
        }
        Dialogflag=1;
        //System.out.println("hello");
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(400,210);
        temp.setLocationRelativeTo(null);//ˮƽ���з���
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
        JLabel label_id=new JLabel("    ���");
        JLabel label_info1=new JLabel("1.�޸ļ�¼������ԭ��ְԱ��id�������޸ĵ���Ϣ");
        JLabel label_info2=new JLabel("2.��Ӽ�¼��������Ҫ��ӵ�ְԱ����Ϣ����");
        JLabel label_info3 = new JLabel("3.ɾ����¼��������Ҫɾ����ְԱ��id�ٵ��ɾ������");
        JLabel label_info4 = new JLabel("4.��ѯ��¼��������Ҫ��ѯ��ְԱ��id�ٵ����������");
        JLabel label_info5 = new JLabel("5.ˢ�¼�¼�������ݿ��е���Ϣ���¼����ڱ����");
        JLabel label_info6 = new JLabel("6.�˳�ϵͳ���رճ���");

        JTextField input_id=new JTextField(15);
        JPanel panel_1=new JPanel();
        JPanel panel_2=new JPanel();
        JPanel panel_3 = new JPanel();
        JButton button_1=new JButton("ɾ��");
        JButton button_2 = new JButton(button);
        panel_3.setLayout(new BorderLayout());
        if(op==4){//ʹ�ð���

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
                    JOptionPane.showMessageDialog(null,"�ü�¼��Ϊ��,����ɾ��!","����",JOptionPane.ERROR_MESSAGE);
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


                            String thead[] = {"���id", "����name", "�Ա�sex", "����birth", "��ַaddr", "����salary"};
                            String tbody[][] = to_list(Main.data);
                            TableModel dataModel = new DefaultTableModel(tbody, thead);
                            table.setModel(dataModel);

                            try {
                                con = DriverManager.getConnection("jdbc:derby:E:\\��ѧ\\רҵ��\\3.1Java\\ʵѵ\\�ƶ���˾;create=true");
                                System.out.println("���ݿ��ѳɹ�����!");
                                String sql = "delete from ְԱ�� where id=?";
                                preparedstatement = con.prepareStatement(sql);
                                preparedstatement.setString(1, content);
                                preparedstatement.executeUpdate();
                                con.close();
                                System.out.println("���ݿ��ѹر�!");
                            } catch (SQLException eve) {
                                System.out.println(eve);
                            }
                            ;
                            JOptionPane.showMessageDialog(null, "ɾ���ɹ�!", "ɾ����¼", JOptionPane.PLAIN_MESSAGE);

                            break;
                        }
                    }
                    if (flag == 0) {
                        input_id.setText("");
                        JOptionPane.showMessageDialog(null, "������¼������,����������!", "����", JOptionPane.ERROR_MESSAGE);
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
        if(Dialogflag==1) {//�����ε�����ֶ������,ֻ�������һ������
            return ;
        }
        Dialogflag=1;
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(420,260);
        temp.setLocationRelativeTo(null);//ˮƽ���з���
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {//���ùرռ�����
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
        JLabel label_1=new JLabel("   ��ţ�");
        JLabel label_2=new JLabel("   ������");
        JLabel label_3= new JLabel("  �Ա�");
        JLabel label_4= new JLabel("  ������");
        JLabel label_5= new JLabel("  ��ַ��");
        JLabel label_6= new JLabel("  ���ʣ�");
        JTextField input_1=new JTextField(15);
        JTextField input_2=new JTextField(15);
        JTextField input_3=new JTextField(15);
        JTextField input_4=new JTextField(15);
        JTextField input_5=new JTextField(15);
        JTextField input_6=new JTextField(15);

        JButton button1=new JButton(button);
        JButton button2=new JButton("ȡ��");

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
                        if(!sta.sex.equals("��")&&!sta.sex.equals("Ů"))
                        {
                            JOptionPane.showMessageDialog(null, "�Ա�Ӧֻ����Ů,����������!", "����", JOptionPane.ERROR_MESSAGE);
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
                                JOptionPane.showMessageDialog(null, "���빤�ʲ�Ϊ����,����������!", "����", JOptionPane.ERROR_MESSAGE);
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
                                JOptionPane.showMessageDialog(null, "������¼������Ѵ���,����������!", "����", JOptionPane.ERROR_MESSAGE);
                                input_1.setText("");input_2.setText("");input_3.setText("");
                                input_4.setText("");input_5.setText("");input_6.setText("");
                                flag = 0;
                                break;
                            }
                        }
                        if (flag == 1) {
                            try {
                                con = DriverManager.getConnection("jdbc:derby:E:\\��ѧ\\רҵ��\\3.1Java\\ʵѵ\\�ƶ���˾;create=true");
                                System.out.println("���ݿ��ѳɹ�����!");
                                String sql = "Insert into ְԱ�� values(?,?,?,?,?,?)";
                                preparedstatement = con.prepareStatement(sql);
                                preparedstatement.setString(1, sta.id);
                                preparedstatement.setString(2, sta.name);
                                preparedstatement.setString(3, sta.sex);
                                preparedstatement.setDate(4, sta.sqldate);
                                preparedstatement.setString(5, sta.address);
                                preparedstatement.setFloat(6, sta.salary);
                                preparedstatement.executeUpdate();
                                JOptionPane.showMessageDialog(null, "��ӳɹ�!", "��Ӽ�¼", JOptionPane.PLAIN_MESSAGE);
                                sqlinit();
                                String thead[] = {"���id", "����name", "�Ա�sex", "����birth", "��ַaddr", "����salary"};
                                String tbody[][] = to_list(Main.data);
                                TableModel dataModel = new DefaultTableModel(tbody, thead);
                                table.setModel(dataModel);
                                con.close();
                                System.out.println("���ݿ��ѹر�!");
                            } catch (SQLException eve) {
                                System.out.println(eve);
                            }
                        }
                }
                else{
                    JOptionPane.showMessageDialog(null, "������Ų�Ӧ�пհ��ַ�,����������!", "����", JOptionPane.ERROR_MESSAGE);
                    input_1.setText("");input_2.setText("");input_3.setText("");
                    input_4.setText("");input_5.setText("");input_6.setText("");
                }
            });

        }
    }

    public void new_Dialog3(String title,String button,int op){
        if(Dialogflag==1) {//�����ε�����ֶ������,ֻ�������һ������
            return ;
        }
        Dialogflag=1;
        JDialog temp=new JDialog(this);
        temp.setTitle(title);
        temp.setSize(600,260);
        temp.setLocationRelativeTo(null);//ˮƽ���з���
        temp.setVisible(true);
        temp.addWindowListener(new WindowAdapter() {//���ùرռ�����
            public void windowClosing(WindowEvent eve){
                Dialogflag=0;
            }
        });
        JLabel label_1=new JLabel("   ԭ��ţ�");
        JLabel label_11=new JLabel("   ����ţ�");
        JLabel label_2=new JLabel("   ԭ������");
        JLabel label_12=new JLabel("   ��������");
        JLabel label_3= new JLabel("  ԭ�Ա�");
        JLabel label_13= new JLabel("  ���Ա�");
        JLabel label_4= new JLabel("  ԭ������");
        JLabel label_14= new JLabel("  �³�����");
        JLabel label_5= new JLabel("  ԭ��ַ��");
        JLabel label_15= new JLabel("  �µ�ַ��");
        JLabel label_6= new JLabel("  ԭ���ʣ�");
        JLabel label_16= new JLabel("  �¹��ʣ�");
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
        JButton button2=new JButton("ȡ��");
        JButton button3=new JButton("ȷ�����");

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
                JOptionPane.showMessageDialog(null,"�ü�¼��Ϊ��,����ɾ��!","����",JOptionPane.ERROR_MESSAGE);
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
                                JOptionPane.showMessageDialog(null, "ͬ��ԭ���ݳɹ�!", "�޸ļ�¼", JOptionPane.PLAIN_MESSAGE);
                                break;
                            }
                        }
                        if (flag == 0) {
                            JOptionPane.showMessageDialog(null, "������¼����Ų�����,����������!", "����", JOptionPane.ERROR_MESSAGE);
                            input_1.setText("");
                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "������Ų�Ӧ�пհ��ַ�,����������!", "����", JOptionPane.ERROR_MESSAGE);
                        input_1.setText("");
                    }
                });

                button1.addActionListener(e->{
                    int flag=0;
                    int flaggg[]=new int[7];
                    String tempid=input_1.getText().replace(" ","");

                    try {
                        con = DriverManager.getConnection("jdbc:derby:E:\\��ѧ\\רҵ��\\3.1Java\\ʵѵ\\�ƶ���˾;create=true");
                        System.out.println("���ݿ��ѳɹ�����!");
                        Staff sta=new Staff();
                        sta.id=input_11.getText().replace(" ","");
                        if(flag>=0) {
                            if (sta.id != null && !sta.id.trim().equals("")) {
                                if (sta.id.equals(input_1.getText().replace(" ", ""))) {
                                    flag = -1;
                                    JOptionPane.showMessageDialog(null, "�������ԭ�����ͬ,����������!", "����", JOptionPane.ERROR_MESSAGE);
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
                                            JOptionPane.showMessageDialog(null, "��������¼���е������ͬ,����������!", "����", JOptionPane.ERROR_MESSAGE);
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
                                    JOptionPane.showMessageDialog(null, "��������ԭ������ͬ,����������!", "����", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//��Ȼ�и�Ԫ��
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
                                    JOptionPane.showMessageDialog(null, "���Ա���ԭ�Ա���ͬ,����������!", "����", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//��Ȼ�и�Ԫ��
                                    if (!sta.sex.equals("��") && !sta.sex.equals("Ů")) {
                                        flag = -1;
                                        JOptionPane.showMessageDialog(null, "�Ա�Ӧֻ����Ů,����������!", "����", JOptionPane.ERROR_MESSAGE);
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
                                    JOptionPane.showMessageDialog(null, "�³�����ԭ������ͬ,����������!", "����", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//��Ȼ�и�Ԫ��
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
                                    JOptionPane.showMessageDialog(null, "�µ�ַ��ԭ��ַ��ͬ,����������!", "����", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//��Ȼ�и�Ԫ��
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
                                    JOptionPane.showMessageDialog(null, "�¹�����ԭ������ͬ,����������!", "����", JOptionPane.ERROR_MESSAGE);
                                    input_11.setText("");
                                    input_12.setText("");
                                    input_13.setText("");
                                    input_14.setText("");
                                    input_15.setText("");
                                    input_16.setText("");
                                } else {//��Ȼ�и�Ԫ��
                                    if (isNumeric(tempsalary) == false) {
                                        flag = -1;
                                        JOptionPane.showMessageDialog(null, "���빤�ʲ�Ϊ����,����������!", "����", JOptionPane.ERROR_MESSAGE);
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
                                        String sql = "update ְԱ�� set id=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setString(1, sta.id);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                        tempid = sta.id;
                                    }else if(i==2){
                                        String sql = "update ְԱ�� set ����=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        System.out.println(sta.name + "???");
                                        preparedstatement.setString(1, sta.name);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==3){
                                        String sql = "update ְԱ�� set �Ա�=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setString(1, sta.sex);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==4){
                                        String sql = "update ְԱ�� set ����=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setDate(1, sta.sqldate);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==5){
                                        String sql = "update ְԱ�� set ��ַ=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setString(1, sta.address);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }else if(i==6){
                                        sta.salary = Float.parseFloat(tempsalary);
                                        String sql = "update ְԱ�� set ����=? where id=?";
                                        preparedstatement = con.prepareStatement(sql);
                                        preparedstatement.setFloat(1, sta.salary);
                                        preparedstatement.setString(2, tempid);
                                        preparedstatement.executeUpdate();
                                    }
                                }
                            }

                            JOptionPane.showMessageDialog(null, "�޸ĳɹ�!", "�޸ļ�¼", JOptionPane.PLAIN_MESSAGE);
                            sqlinit();
                            String thead[] = {"���id", "����name", "�Ա�sex", "����birth", "��ַaddr", "����salary"};
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
                        System.out.println("���ݿ��ѹر�!");
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
            String thead[]={"���id","����name","�Ա�sex","����birth","��ַaddr","����salary"};
            String tbody[][]=to_list(Main.data);
            TableModel dataModel = new DefaultTableModel(tbody,thead);
            table.setModel(dataModel);
        });

        buttonsearch.addActionListener(e->{
            String content=inputid.getText();
            if(content!=null&&!content.trim().equals("")) {
                String sql = "select * from ְԱ�� where id=?";
                int cnt = 0;
                try {
                    con = DriverManager.getConnection("jdbc:derby:E:\\��ѧ\\רҵ��\\3.1Java\\ʵѵ\\�ƶ���˾;create=true");
                    System.out.println("���ݿ��ѳɹ�����!");
                    preparedstatement = con.prepareStatement(sql);
                    preparedstatement.setString(1, content);
                    resultset = preparedstatement.executeQuery();
                    System.out.println("���ݿ��ѹر�!");
                    Main.data.clear();

                    while (resultset.next()) {
                        cnt++;
                        Staff temp = new Staff();
                        temp.id = resultset.getString("id").replace(" ", "");
                        temp.name = resultset.getString("����").replace(" ", "");
                        temp.sex = resultset.getString("�Ա�").replace(" ", "");
                        temp.sqldate = resultset.getDate("����");
                        temp.to_string();
                        temp.address = resultset.getString("��ַ").replace(" ", "");

                        temp.salary = resultset.getFloat("����");
                        Main.data.add(temp);
                        System.out.println(temp.get_id() + "          |" + temp.get_name() + "       |" + temp.get_sex() + "   |" + temp.get_day() + "    |" + temp.get_address() + "                |" + temp.get_salary());
                    }
                    if (cnt == 0) {
                        JOptionPane.showMessageDialog(null, "������¼������,����������!", "����", JOptionPane.ERROR_MESSAGE);
                        inputid.setText("");
                    }
                    con.close();
                } catch (SQLException eve) {
                    System.out.println(eve);

                }
                if(cnt==0)
                    sqlinit();
                String thead[]={"���id","����name","�Ա�sex","����birth","��ַaddr","����salary"};
                String tbody[][]=to_list(Main.data);
                TableModel dataModel = new DefaultTableModel(tbody,thead);
                table.setModel(dataModel);
            }
            else {
                JOptionPane.showMessageDialog(null, "�����ʽ��Ӧ�пհ��ַ�,����������!", "����", JOptionPane.ERROR_MESSAGE);
                inputid.setText("");
            }
        });

        buttonadd.addActionListener(e->{
            new_Dialog2("��Ӽ�¼(���ڵĸ�ʽΪyyyy-mm-dd,�������Ϊ��Ч����)","���",1);
        });

        buttonupdate.addActionListener(e->{
            new_Dialog3("�޸ļ�¼(����ԭ��ź���ȷ�����,��ʾԭ��¼����Ϣ,ֻ������д����Ϣ�����޸�,�������Ϊ��Ч����)","�޸�",2);
        });

        buttondelete.addActionListener(e -> {
           new_Dialog1("ɾ����¼","ȡ��",3);

        });
        buttonhelp.addActionListener(e -> {
           new_Dialog1("ʹ�ð���","����",4);
        });


    }

}
