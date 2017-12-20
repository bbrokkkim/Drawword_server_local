import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class Cqlconnect{
	/*static Cluster cluster = null;
	static Session session = null;*/
	/*public Cqlconnect(){
		System.out.println("setsetetesetsetset11111111111111111111111111111111"); 
		cluster = Cluster.builder()                                                    // (1)
		            .addContactPoint("127.0.0.1")
		            .build();
        session = cluster.connect("drawword");         
		
	}*/
    public static void insert(String room_num,String user_id,String contents) {

		Cluster cluster = null;
		Session session = null;
		try {
		    System.out.println("-----------------------------------------------------------------------------"); 
		cluster = Cluster.builder()                                                    // (1)
		            .addContactPoint("127.0.0.1")
		            .build();
        session = cluster.connect("drawword");         
		    
		                                      // (2)
		    
		    ResultSet rs = session.execute("insert into chatting (room_num,user_id,contents,time) values ("+room_num+",'"+user_id+"','"+contents+"',toUnixTimestamp(now()))");    // (3)
		    Row row = rs.one();
		    // System.out.println(row.getString("contents")+"     testsetsetset");                          // (4)
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
		    // System.out.println(cluster+"tttttttttttt");                          // (4)			
		    /*if (cluster != null){
		     cluster.close();                                          // (5)
		    }*/
			
		}

	}

}