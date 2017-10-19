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
                String ready_check = "";
                String thick = "";
                String color = "";
                String x = "";
                String y = "";
                
                // String input = new String(buf, "UTF-8");
                while( (input = conToClient.read())!=null){
                    System.out.println("--------------------------------");
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
                        

                        String user_list = check_user_list(room_num);
                        System.out.println(user_list);
                        sendToAll(user_list+ "\n",room_num);
                        

                        ment = false;
                    }
                    

                    else {
                        /*if (input.equals("fin!@#!@#》")){
                            exit_room(user_name);
                            String user_list = check_user_list(room_num);
                            System.out.println(user_list);
                            
                            sendToAll(user_list+ "\n",room_num);
                            break;
                        }*/
                        System.out.println(input);

                        int idx = input.indexOf("《");
                        tcp_type = input.substring(0,idx);
                        System.out.println(tcp_type + "번타입 ");
                        

                        // input = input.substring(idx + 1);
                        // System.out.println(input);
                        input = input.substring(idx + 1);    
                        idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        user_name = input.substring(idx + 1);
                        System.out.println(room_num  + "번방");
                        // System.out.println(user_name);


                        if (tcp_type.equals("1")){
                            System.out.println("number: " + room_num + " || message: " + user_name);
                            sendToAll("《1《"+user_name+ "\n",room_num);
                        }
                        else if (tcp_type.equals("2")){
                            System.out.println(user_name);
                            idx = user_name.indexOf("》");
                            ready_check = user_name.substring(idx+1);
                            user_name = user_name.substring(0,idx);
                            System.out.print(user_name+ " | ");
                            System.out.println(ready_check+ "입니다");
                            
                            user_ready(user_name,ready_check);
                            
                            String user_list = check_user_list(room_num);
                            System.out.println(user_list);
                            
                            sendToAll(user_list+ "\n",room_num);
                        }
                        else if (tcp_type.equals("3")){
                            System.out.println("ㅁㄴㅇㄹ");
                        }
                        else if (tcp_type.equals("4")){
                            System.out.println(room_num);
                            idx = user_name.indexOf("《");
                            color = user_name.substring(idx + 1);
                            user_name = user_name.substring(0,idx);
                            System.out.println(user_name);

                            idx = color.indexOf("《");
                            thick = color.substring(idx+1);
                            color = color.substring(0,idx);
                            System.out.println(color);
                            
                            idx = thick.indexOf("《");
                            x = thick.substring(idx+1);
                            thick = thick.substring(0,idx);
                            System.out.println(thick);

                            idx = x.indexOf("《");
                            y = x.substring(idx+1);
                            x = x.substring(0,idx);
                            System.out.println(x);
                            
                            idx = y.indexOf("《");
                            y = y.substring(0,idx);
                            System.out.println(y);
                            
                        }


                        else if (tcp_type.equals("10")){
                            idx = user_name.indexOf("》");
                            user_name = user_name.substring(0,idx);
                            System.out.println(user_name);
                            exit_room(user_name);
                            String user_list = check_user_list(room_num);
                            System.out.println(user_list);
                            
                            sendToAll(user_list+ "\n",room_num);
                            break;
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
                if (user_name.equals(client_list.get(i).getUserName())){
                    System.out.println(client_list.get(i).getUserName() + " 나감");
                    client_list.remove(i);
                    clients.remove(i);
                }
            }
        }

        public void user_ready(String user_name,String ready) {
            for (int i =0 ;i < client_list.size() ; i ++) {
                System.out.println(user_name + "||" + client_list.get(i).getUserName());
                if (user_name.equals(client_list.get(i).getUserName())){
                    if (ready.equals("wait")) {
                        client_list.get(i).setReadycheck("ready");
                        System.out.println("ready!");
                    }
                    else {
                        client_list.get(i).setReadycheck("wait");
                        System.out.println("wait!");
                    }
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
            int check = 0;
            boolean start = true;

            for (int i = 0 ; i < client_list.size() ; i ++ ){
                if ( room_num.equals( client_list.get(i).getRoomNum() )){
                    
                    user_list_json = 
                             user_list_json 
                    + "《" + client_list.get(i).getUserName() 
                    + "》" + client_list.get(i).getReadycheck();

                    if (client_list.get(i).getReadycheck().equals("ready")){
                        System.out.println("시작");
                        
                    }
                    else {
                        start = false;
                    }
                    check = check + 1;
                    System.out.println("testtttttt");

                }
            }
            System.out.println(check + "||" + client_list.size());
            if (start && check > 1){
                user_list_json = "《5"+ user_list_json;
                System.out.println("모두다");
            }
            else{
                user_list_json = "《2"+ user_list_json;
                System.out.println("아직");
            }

            System.out.println("바뀐 사이즈" + client_list.size());

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


