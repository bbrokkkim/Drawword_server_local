import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    static List<ConnectionToClient> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8001);
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

    static class ServerThread extends Thread {
        Socket socket;
        ConnectionToClient conToClient;
        ServerThread(Socket socket) {
            this.socket = socket;
            this.conToClient = new ConnectionToClient(socket);
            clients.add(conToClient);
        }
        public void run() {
            try {
                String input = "";
                while( (input = conToClient.read())!=null){
                    // System.out.println(input);
                    //conToClient.write(input);
                    sendToAll(input);
                }
            } catch (Exception e) {
            }
        }

        public void sendToAll(String message){
            for(ConnectionToClient client :clients){
                System.out.println("message: " + message);
                client.write(message);
            }
        }
    }

    static class ConnectionToClient {
        Socket socket;
        BufferedReader br;
        ObjectOutputStream oos;

        ConnectionToClient(Socket socket) {
            this.socket = socket;
            try {
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                oos = new ObjectOutputStream(socket.getOutputStream());

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
            } catch (Exception e) {
               System.out.println(e);
            }
        }
    }
}