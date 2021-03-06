import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
public class CassandraCql {
    private static java.sql.Connection con = null;
    
    public static void main(String[] a){
        try {
    	    CassandraCql sample = new CassandraCql();

	        String DB_URL = "jdbc:cassandra://localhost:9160/drawword_chat";
	        // "jdbc:cassandra://localhost:9042/niranjan"
	        String USERNAME = "cassandra";
	        String PASSWORD = "cassandra"; 
            
            Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
            
        	
            con=DriverManager.getConnection(DB_URL);
            String Columnname="subject";
           	
            /* -- Functions to perform on Keyspace --*/
            /*createColumnFamily();
            pouplateData();
            deleteDaDB_URLta();
            updateData();
            listData();
            dropColumnFamily("news");
           	*/

    /*       	String data=
		         "insert into chat_content (iden,contents,room_num,room_status,user_id) values (7,'aa',1,1,'as')"; 
        	Statement st = con.createStatement();
       		 st.execute(data);
*/
            String query = "select * from drawword_chat.chat_content";


            Statement stmt = con.createStatement();
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                System.out.println(result.getString("contents"));
                System.out.println(result.getString("user_id"));
            }
            con.close();

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public static void createColumnFamily() throws SQLException {
        String data="CREATE columnfamily news (key int primary key, category text , linkcounts int ,url text)";
        Statement st = con.createStatement();
        st.execute(data);
        }

    public static void dropColumnFamily(String name) throws SQLException {
        String data="drop columnfamily "+ name +";";
        Statement st = con.createStatement();
        st.execute(data);
	}
         
	public static void pouplateData() throws SQLException {
		String data=
			"BEGIN BATCH \n"+   
			"insert into news (key, category, linkcounts,url) values ('user5','class',71,'news.com') \n"+ 
			"insert into news (key, category, linkcounts,url) values ('user6','education',15,'tech.com') \n"+
			"insert into news (key, category, linkcounts,url) values ('user7','technology',415,'ba.com') \n"+
			"insert into news (key, category, linkcounts,url) values ('user8','travelling',45,'google.com/teravel') \n"+
			"APPLY BATCH;";   
		Statement st = con.createStatement();
		st.executeUpdate(data);
	}
     public static void deleteData() throws SQLException {
         String data= 
         "BEGIN BATCH \n"+
         "delete from  news where key='user5' \n"+
         "delete  category from  news where key='user2' \n"+
         "APPLY BATCH;";   
         Statement st = con.createStatement();
         st.executeUpdate(data);
     }
     public static void updateData() throws SQLException {
         String t = "update news set category='sports', linkcounts=1 where key='user5'";          
         Statement st = con.createStatement();
         st.executeUpdate(t);
     }
     public static void listData() throws SQLException {
         String t = "SELECT * FROM news";          
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(t);
         while(rs.next())
         {
	         System.out.println(rs.getString("KEY"));
	         for(int j=1;j<rs.getMetaData().getColumnCount()+1;j++)
	         {
	    	     System.out.println(rs.getMetaData().getColumnName(j) +" : "+rs.getString(rs.getMetaData().getColumnName(j)));
	         }
         }
     }
    
}