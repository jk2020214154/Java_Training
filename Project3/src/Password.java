import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

/**
 * Create with IntelliJ IDEA.
 * Description:
 * User: SDTBU_LY
 * Date: 2022-12-05
 * Time: 16:20
 */
import java.util.Map;

public class Password {

    public boolean checkPass(String username,String password)
    {

        String tempuser=username.replace(" ","");//把用户名多余空格去掉

        //System.out.println(tempuser);
        //System.out.println(password);
        return password.equals(GUI.map.get(tempuser));
    }
}

class Staff{
    String id;//序号
    String name;//姓名
    String sex;//性别
    String day;//日期
    java.util.Date date;//日期相关
    java.sql.Date sqldate;//数据库日期
    String address;//地址
    float salary;//工资

    public Staff(){}

    public Staff(String id,String name,String sex,String day,String address,float salary) {
        this.id = id;this.name = name;this.sex = sex;
        this.day = day;this.address = address;this.salary=salary;
    }

    public void to_sql() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(day);
            sqldate = new java.sql.Date(date.getTime());
        } catch (ParseException e) {
            System.out.println(e);
        }
    }
    public void to_string() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        day=sqldate.toString();
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            System.out.println(e);
        }
    }
    public void set_id(String id) {
        this.id = id;
    }
    public String get_id() {
        return id;
    }
    public void set_name(String name) {
        this.name = name;
    }
    public String get_name() {
        return name;
    }
    public void set_sex(String sex) {
        this.sex = sex;
    }

    public String get_sex(){
        return sex;
    }

    public void set_day(String day) {
        this.day = day;
    }

    public String get_day(){
        return day;
    }

    public void set_sqldate(java.sql.Date sqldate) {
        this.sqldate = sqldate;
    }

    public void set_address(String address){
        this.address = address;
    }

    public String get_address(){
        return address;
    }
    public void set_salary(float salary){
        this.salary = salary;
    }

    public float get_salary(){
        return salary;
    }

}
