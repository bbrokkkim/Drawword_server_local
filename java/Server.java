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
    static ArrayList<Client_info> client_list = new ArrayList<>();
    public static void main(String[] args) {
        try {
            System.out.println("시작!!");      
            System.out.println("대기중....");
            Socket socket = null;
            ServerSocket server = new ServerSocket(8000);
            while ((socket = server.accept()) != null) {
                System.out.println(socket.getInetAddress() + " 로부터 연결요청이 들어왔습니다.");
                new ServerThread(socket).start();
            }
            server.close();
        } catch (Exception e) {
        }
    }
                /*room_num = bufferedReader.readLine();
                room_name = bufferedReader.readLine();
                user_iden = bufferedReader.readLine();
                user_name = bufferedReader.readLine();
                System.out.println(room_num);
                System.out.println(room_name);
                System.out.println(user_iden);
                System.out.println(user_name);*/
    public static class ServerThread extends Thread {
        Socket socket;
        ConnectionToClient conToClient;
        Client_info client_info;
        String room_num,room_name,user_iden,user_name;
        // String[4] info = 
        ServerThread(Socket socket) {
            this.socket = socket;
            this.conToClient = new ConnectionToClient(socket);
            clients.add(conToClient);
            
        }
        public void run() {
            try {
                int ment_num = 0;
                int info = 0;
                boolean ment = true;
                String input ="";
                System.out.println("확인");
                String room_num = "";
                String user_name = "";
                System.out.println(room_num+"번방");

                
                // String input = new String(buf, "UTF-8");
                while( (input = conToClient.read())!=null){
                    // System.out.println(input);
                    //conToClient.write(input);
                    if (ment == true){
                        int idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        user_name = input.substring(idx + 1);
                        System.out.println( room_num + "번방을 입장했다.");
                        System.out.println( user_name);
                        client_info = new Client_info(socket,room_num,user_name);
                        client_list.add(client_info);
                            
                        /*if (info ==0 ){
                            room_num = input;
                            info = 1;
                        }
                        else if (info == 1){
                            user_name = input;
                            
                            // String asdf = client_list.get(0).getRoomNum();
                            System.out.println( room_num + "번방을 입장했다.");*/
                            ment = false;
                        // }
                    }
                    

                    else {
                        if (input.equals("fin!@#!@#")){
                            System.out.println("사용자 나감");
                            break;
                        }

                        int idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        user_name = input.substring(idx + 1);
                        
                        System.out.println("number: " + room_num + " || message: " + user_name);
                        sendToAll(user_name+ "\n",room_num);
                    }
                }
            } catch (Exception e) {
            }
        }

        public void sendToAll(String message,String room_num){
            /*for(ConnectionToClient client :clients){
                client.write(message+"\n");
                System.out.println(clients);
                
            }*/
            System.out.println("test" );

            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                if ( room_num.equals( client_list.get(i).getRoomNum() )){
                    // System.out.println("맞음");
                    clients.get(i).write(message);
                }
                // System.out.println(clients.get(i));
                
            }
        }
    }

    public static class Client_info {
        Socket socket;
        String room_num,user_name;

        Client_info(Socket socket, String room_num, String user_name){
            this.socket = socket;
            this.room_num = room_num;
            this.user_name = user_name;
        }
        public void setRoomNum(String room_num) {
            this.room_num = room_num;
        }

        public String getUserName(){
            return user_name;
        }
        public String getRoomNum(){
            return room_num;
        }
    }

    public static class ConnectionToClient {
        Socket socket;
        BufferedReader br;
        PrintWriter printWriter;
        ObjectOutputStream oos;
        String room_num;
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


