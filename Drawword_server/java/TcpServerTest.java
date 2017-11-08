
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
 
public class TcpServerTest {
 
    public static void main(String[] args) {
        ServerSocket serverSocket = null; //서버의 소켓
        int port = 8000; //포트 번호
         
        try{
            serverSocket = new ServerSocket(port); //포트에 서버소켓을 붙인다(Bind)
            System.out.println(getTime() + " 서버가 준비되었습니다.");
             
            Socket socket = serverSocket.accept(); // 클라이언트의 접속을 허가한다.(Accept)
            InetAddress clientAddress = socket.getInetAddress(); // 클라이언트의 주소를 가져온다.
            System.out.println(getTime() + clientAddress + " 에서 클라이언트가 접속했습니다.");
 
            OutputStream out = socket.getOutputStream(); //클라이언트 소켓의 바이트 스트림을 가져온다.
            InputStream in =socket.getInputStream(); //클라이언트 소켓의 바이트 스트림을 입력한다.
             
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
             
            String recvStr = null; //받은 문자열
            while((recvStr = br.readLine()) != null){
                System.out.println(getTime() + clientAddress +"로부터 받은 문자열 : " + recvStr);
                pw.println(recvStr);    //메시지를 클라이언트에게 전송
                pw.flush(); //버퍼를 비움
            }
             
            pw.close(); //스트림 닫기
            br.close(); //버퍼 닫기
            socket.close(); //소켓 닫기
        }catch(Exception e){
            e.printStackTrace(); //예외 처리
        }
    }
    static String getTime(){
        SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]"); //날짜 출력
        return f.format(new Date());
    }
}

