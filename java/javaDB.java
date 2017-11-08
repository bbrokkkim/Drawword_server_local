
import java.sql.*;

public class javaDB {

    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://mariadb.ceqw0wwolo9b.ap-northeast-2.rds.amazonaws.com/drawword";

    static final String USERNAME = "root";
    static final String PASSWORD = "KKKKKKKK";

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            System.out.println("\n- MySQL Connection");
            stmt = conn.createStatement();
            
            String sql;
            String room_num;
            sql = "insert into user_list(name,id,pwd,phone,sex,photo_uri) values ('name','id','pwd','phone',1,'photo_uri')";
            sql = "update room_info set room_status = 'already' where iden = 16";
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                String groupName = rs.getString("groupName");
                String memberName = rs.getString("memberName");

                System.out.print("\n** Group : " + groupName);
                System.out.print("\n    -> Member: " + memberName);
            }
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se1){
            se1.printStackTrace();
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("\n\n- MySQL Connection Close");
    }
}