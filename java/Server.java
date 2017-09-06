import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static ArrayList<ConnectionToClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            System.out.println("시작!!");
            ServerSocket server = new ServerSocket(8000);
            Socket socket = null;
            System.out.println("대기중....");
            while ((socket = server.accept()) != null) {
                
                System.out.println(socket.getInetAddress() + " 로부터 연결요청이 들어왔습니다.");
                new ServerThread(socket).start();
            }
            server.close();
        } catch (Exception e) {
        }
    }

    public static class ServerThread extends Thread {
        Socket socket;
        ConnectionToClient conToClient;
        ServerThread(Socket socket) {
            this.socket = socket;
            this.conToClient = new ConnectionToClient(socket);
            clients.add(conToClient);
        }
        public void run() {
            try {
                String input ="";
                // String input = new String(buf, "UTF-8");
                while( (input = conToClient.read())!=null){
                    // System.out.println(input);
                    //conToClient.write(input);
                    byte[] buf = input.getBytes("UTF-8"); 
                    String input_utf = new String(buf,"UTF-8");
                    System.out.println("message: " + input_utf);

                if (input_utf.equals("김경관")){
                    System.out.println("맞음");
                }
                else
                    System.out.println("틀림");

                    sendToAll(input_utf+"\n");
                }
            } catch (Exception e) {
            }
        }

        public void sendToAll(String message){
            /*for(ConnectionToClient client :clients){
                client.write(message+"\n");
                System.out.println(clients);
                
            }*/
            for (int i = 0 ; i < clients.size() ; i ++ ) {
                clients.get(i).write(message);

                System.out.println(clients.get(i));
                
            }
        }
    }

    public static class ConnectionToClient {
        Socket socket;
        BufferedReader br;
        PrintWriter printWriter;
        ObjectOutputStream oos;

        ConnectionToClient(Socket socket) {
            this.socket = socket;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                oos = new ObjectOutputStream(socket.getOutputStream());
                printWriter = new PrintWriter(oos,true);
            } catch (Exception e) {
               System.out.println(e);
            }
        }
        public String read(){
            try{
                return br.readLine();
            }catch(Exception e){
                System.out.println(e);
                return null;
            }
        }
        public void write(Object obj) {
            try {
                oos.writeObject(obj);
                oos.flush();
                /*printWriter.println(obj);
                printWriter.flush();*/
                

                System.out.println("보냄!");
            } catch (Exception e) {
               System.out.println(e);
            }
        }
    }
}

