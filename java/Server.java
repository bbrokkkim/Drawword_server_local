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
                String room_num = "";
                String user_name = "";
                String tcp_type = "";

                
                // String input = new String(buf, "UTF-8");
                while( (input = conToClient.read())!=null){
                    // System.out.println(input);
                    //conToClient.write(input);
                    if (ment == true){
                        int idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        user_name = input.substring(idx + 1);
                        System.out.println( room_num + "번방으로.");
                        System.out.println( user_name + "님이 입장하였습니다.");
                        client_info = new Client_info(socket,room_num,user_name,"wait");
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
                        if (input.equals("fin!@#!@#》")){
                            exit_room(user_name);
                            break;
                        }
                        System.out.println(input);
                        int idx = input.indexOf("《");
                        tcp_type = input.substring(0,idx);
                        System.out.println(tcp_type);
                        

                        input = input.substring(idx + 1);
                        System.out.println(input);
                            
                        idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        user_name = input.substring(idx + 1);
                        System.out.println(room_num);
                        System.out.println(user_name);


                        if (tcp_type.equals("1")){
                            System.out.println("number: " + room_num + " || message: " + user_name);
                            sendToAll("《1《"+user_name+ "\n",room_num);
                        }
                        else if (tcp_type.equals("2")){
                            user_ready(user_name);
                            String user_list = check_user_list(room_num);
                            System.out.println(user_list);
                            
                            sendToAll("《2"+user_list+ "\n",room_num);
                        }
                        else if (tcp_type.equals("3")){
                            System.out.println("ㅁㄴㅇㄹ");
                        }
                        else 
                            System.out.println("aaa");
                    }
                }
            } catch (Exception e) {
            }
        }

        public void exit_room(String user_name){
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (user_name.equals(client_info.getUserName())){
                    System.out.println(client_list.get(i).getUserName() + " 나감");
                    client_list.remove(i);
                    clients.remove(i);
                }
            }
        }

        public void user_ready(String user_name) {
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (user_name.equals(client_info.getUserName())){
                    if (client_list.get(i).getReadycheck().equals("wait")) {
                        client_list.get(i).setReadycheck("ready");
                    }
                    else
                        client_list.get(i).setReadycheck("wait");
                
                    return;
                }
            }
        }
        
        public void sendToAll(String message,String room_num){
            // System.out.println("test");
            System.out.println("사이즈" + client_list.size());
            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                // System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                if ( room_num.equals( client_list.get(i).getRoomNum() )){
                    // System.out.println("맞음");
                    clients.get(i).write(message);
                }
                // System.out.println(clients.get(i));
            }
        }

        public String check_user_list(String room_num){
            String user_list_json = "";
            for (int i = 0 ; i < client_list.size() ; i ++ ){
                if ( room_num.equals( client_list.get(i).getRoomNum() )){
                    
                    user_list_json = user_list_json + "《" + client_list.get(i).getUserName() + "》" + client_list.get(i).getReadycheck();

                    
                }
            }
            return user_list_json;
        }
    }

    public static class Client_info {
        Socket socket;
        String room_num,user_name,readycheck;

        Client_info(Socket socket, String room_num, String user_name, String readycheck){
            this.socket = socket;
            this.room_num = room_num;
            this.user_name = user_name;
            this.readycheck = readycheck;
        }

        //setter
        public void setReadycheck(String readycheck) {
            this.readycheck = readycheck;
        }

        //getter
        public String getUserName(){
            return user_name;
        }
        public String getRoomNum(){
            return room_num;
        }
        public String getReadycheck(){
            return readycheck;
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


