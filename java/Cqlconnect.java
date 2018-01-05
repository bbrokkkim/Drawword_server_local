import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class Cqlconnect {
	Cluster cluster = null;
	Session session = null;
	public Cqlconnect(){
		System.out.println("try cassandra connect"); 
		cluster = Cluster.builder()                                                    // (1)
		            .addContactPoint("127.0.0.1")
		            .build();
		System.out.println("cassandra connecting.............................");     
        session = cluster.connect("drawword");    	
		System.out.println("complete cassandra connect");     
	
	}
    public void insert(String room_num,String user_id,String contents) {

		try {
		    System.out.println("-----------------------------------------------------------------------------"); 
	/*	cluster = Cluster.builder()                                                    // (1)
		            .addContactPoint("127.0.0.1")
		            .build();
        session = cluster.connect("drawword");         
	*/	    
		                                      // (2)
		    String query = "insert into chatting (room_num,user_id,contents,time) values ("+room_num+",'"+user_id+"','"+contents+"',toUnixTimestamp(now()))";
		    ResultSet rs = session.execute(query);    // (3)
		    Row row = rs.one();
		    // System.out.println(row.getString("contents")+"     testsetsetset");                          // (4)
			System.out.println(query);
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