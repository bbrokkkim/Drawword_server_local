
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
            String answer;
            ResultSet rs = stmt.executeQuery("select * from word order by rand() limit 1;");

                // System.out.print(rs.getString("groupName"));
            while(rs.next()){
                answer = rs.getString("word");
                // String memberName = rs.getString("memberName");

                System.out.print("\n** Group : " + answer);
                // System.out.print("\n    -> Member: " + memberName);
            }
            // rs.close();
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