import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

public class Cqlcassandra{
	public static void main(String[] a){
		Cluster cluster = null;
		try {
		    System.out.println("-----------------------------------------------------------------------------"); 
		    cluster = Cluster.builder()                                                    // (1)
		            .addContactPoint("127.0.0.1")
		            .build();
		    Session session = cluster.connect("drawword");                                           // (2)
		    System.out.println("setsetetesetsetset11111111111111111111111111111111");
		    // String con = "소방관a기차a새장a검문소a짜임새a엿장수a카레이서a약속a세차장a해적선a물안개a물안경a개회식a장나라a퇴학a애인a철학a풍차a대피소a진공청소기a질풍a사춘기a잠수a어뢰a전화국a핸드폰a가수a영화배우a연기자a";
			int idx;
			String word;
			/*while (true){
				if (con.contains("a")){
                    idx = con.indexOf("a");
                    word = con.substring(0,idx);
                    con = con.substring(idx + 1);
				    ResultSet rs = session.execute("insert into word (word) values ('"+word+"')");    // (3)
		
				}
				else{
					break;
				}
			}*/ 
		    // ResultSet rs = session.execute("insert into chatting (room_num,user_id,contents,time) values ("+room_num+",'"+user_id+"','"+contents+"',toUnixTimestamp(now()))");    // (3)
		    // Row row = rs.one();
		ResultSet rs = session.execute("select count(*) from word");    // (3)
		Row row = rs.one();
		String row_length = String.valueOf(row);
		String row_cut = row_length.substring(4,6);
		int row_count = Integer.parseInt(row_cut);
		System.out.println(row_count	+ "||asdasd");
		rs = session.execute("select * from word limit " + row_count);    // (3)
		row = rs.one();
		System.out.println(row.getString("word")	+ "||");
		
		}
		catch (Exception e){
			e.printStackTrace();
		}
		finally {
		    System.out.println(cluster+"tttttttttttt");                          // (4)			
		    /*if (cluster != null){
		     cluster.close();                                          // (5)
		    }*/
			
		}

	}
}