package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//用jdbc连接MySQL数据库
public class jdbcConnection {
    public void initializeDB(){
        Connection con;
        //jdbc驱动
        String driver="com.mysql.cj.jdbc.Driver";
        //数据库是FoodCompany
        String url="jdbc:mysql://47.102.218.224:3306/FoodCompany?&useSSL=false&serverTimezone=UTC";
        String user="root";
        String password="cb990204";
        try{
            //注册jdbc驱动程序
            Class.forName(driver);
            //建立连接
            con= DriverManager.getConnection(url,user,password);
            if(!con.isClosed()){
                System.out.println("successfully connected!");
            }
            else System.out.println("bad!");
            con.close();
        }catch (ClassNotFoundException e){
            System.out.println("database driver was not loaded!");

        }catch(SQLException e){
            e.printStackTrace();
            System.out.println("database connection failed");
        }
    }
}

