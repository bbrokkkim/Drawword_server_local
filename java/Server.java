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
        String room_name,user_iden;
        RoomInfo roomInfo;
        ArrayList<RoomInfo> roomList = new ArrayList<>();
        String check_room_master = "";
        
        int get = 0;
        
        
        int ment_num = 0;
        int info = 0;
        boolean ment = true;
        String input ="";
        String room_num = "";
        String user_name = "";
        String user_list = "";
        String content = "";
        String to = "";
        String tcp_type = "";
        String ready_check = "";
        String thick = "";
        String color = "";
        String x = "";
        String y = "";
        // String[4] info = 
        ServerThread(Socket socket) {
            this.socket = socket;
            this.conToClient = new ConnectionToClient(socket);
            clients.add(conToClient);
            
        }
        public void run() {
            try {
                

                
                
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

                        user_list = check_user_list(room_num);
                        System.out.println(user_list + "1111111");
                        sendToAll("《2"+user_list+"《"+ "\n",room_num);
                        
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
                        
                        System.out.println(user_name);
                        // input = input.substring(idx + 1);
                        // System.out.println(input);
                        input = input.substring(idx + 1);    
                        idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        user_name = input.substring(idx + 1);
                        System.out.println(room_num  + "번방");
                        // System.out.println(user_name);

                        //채팅
                        if (tcp_type.equals("1")){
                            System.out.println("number: " + room_num + " || message: " + user_name);
                            sendToAll("《1《"+user_name+ "\n",room_num);
                            timer.start();
                        }
                        //레디 상태
                        else if (tcp_type.equals("2.5") || tcp_type.equals("2")){
                            System.out.println(user_name);
                            idx = user_name.indexOf("》");
                            ready_check = user_name.substring(idx+1);
                            user_name = user_name.substring(0,idx);
                            System.out.print(user_name+ " | ");
                            System.out.println(ready_check+ "입니다");
                            
                            user_ready(user_name,ready_check);
                            
                            user_list = check_user_list(room_num);
                            System.out.println("유저리스트 : " + user_list + " || type"+ check_room_master);
                            if (check_room_master.equals("2")){
                                sendToSomeone("《6.5"+ user_list + "《" + "\n", room_num, roomList.get(get).getRoomUser(0));
                                sendToExcept("《2.5"+ user_list + "《" + "\n", room_num, roomList.get(get).getRoomUser(0));
                            }
                            else if(check_room_master.equals("3")){
                                System.out.println("레디");
                                sendToAll("《2"+ user_list + "《" + "\n",room_num);
                            }
                            
                        }
                        //그림 그리기
                        else if (tcp_type.equals("3")){
                            System.out.println("타입 3");
                            System.out.println("《3《"+user_name+ "||" +  room_num);
                            System.out.println(roomList.get(0).getRoomUser(0));
                            sendToAll("《3《"+user_name + "\n",room_num);
                            // sendToSomeone("《6.5《", room_num, roomList.get(0).getRoomUser(0));
                        }
                        else if (tcp_type.equals("4")){
                            System.out.println("《4《"+user_name+ "||" +  room_num);
                            sendToAll("《4《"+user_name + "\n",room_num);
                        }
                        else if (tcp_type.equals("5")){
                            System.out.println("line_end");
                            sendToAll("《5《"+user_name+"\n",room_num);
                        }
                        //여기까지

                        //턴 확인
                        else if (tcp_type.equals("6")){
                            System.out.println("턴 시작");
                            //c쿼리에서 단어 빼내서 ex> "test"
                            sendToSomeone("《6《"+room_num+"《"+user_name+"《"+"\n",room_num,user_name);
                            sendToExcept("《7《"+room_num+"《"+user_name+"《"+"\n",room_num,user_name);
                            sendToAll("《8《65" ,room_num);
                
                            timer.start();
                        }
                        //정답 시도
                        else if (tcp_type.equals("7")){
                            System.out.println("number: " + room_num + " || message: " + user_name);
                            idx = user_name.indexOf("》");
                            to = user_name.substring(0,idx);
                            content = user_name.substring(idx+1);

                            sendToAll("《7《"+user_name+ "\n",room_num);
                        }
                        else if (tcp_type.equals("8")) {
                            
                        }


                        //방 나가기
                        else if (tcp_type.equals("10")){
                            idx = user_name.indexOf("》");
                            user_name = user_name.substring(0,idx);
                            System.out.println(user_name);
                            exit_room(user_name);
                            user_list = check_user_list(room_num);
                            System.out.println(user_list);
                            
                            // sendToAll(user_list+ "\n",room_num);
                            break;
                        }

                        else 
                            System.out.println("해당없음");
                    }
                }
            } catch (Exception e) {
            }
        }

        public void check_answer(String answer, String user_name){
            String real = "test";
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (user_name.equals(client_list.get(i).getUserName())){
                    client_list.get(1).setScore();
                }
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
            System.out.println(message);
            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                if ( room_num.equals( client_list.get(i).getRoomNum() )){
                    System.out.println("방번호 : " + room_num);
                    clients.get(i).write(message);
                    System.out.println( "메세지 : " + message);
                }   
                // System.out.println(clients.get(i));
            }
        }
        public void sendToSomeone (String message,String room_num,String user_name){
            
            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                if ( room_num.equals( client_list.get(i).getRoomNum() ) 
                    && user_name.equals(client_list.get(i).getUserName())){
                    System.out.println("이름" + client_list.get(i).getUserName());
            System.out.println("someone : " + message);
                    clients.get(i).write(message);
                }
            }
        }

        public void sendToExcept (String message,String room_num,String user_name){
            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                if ( room_num.equals( client_list.get(i).getRoomNum() ) 
                    && !user_name.equals(client_list.get(i).getUserName())){
                    System.out.println("이름" + client_list.get(i).getUserName());
                     System.out.println("except : " + message);
                    clients.get(i).write(message);
                }
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
                check_room_master = "2";
                roomInfo = new RoomInfo(room_num);
                roomList.add(roomInfo);
                int get = getRoomUserSize(room_num);
                System.out.println("모두 다");
                for (int i = 0; i < client_list.size(); i++) {
                    if (room_num.equals( client_list.get(i).getRoomNum()) ){
                        roomList.get(get).addUser(client_list.get(i).getUserName());
                    }
                }
                // System.out.println("《6.5《" + roomList.get(get).getRoomUser(0));

            }
            else {
                check_room_master = "3";
                System.out.println("아직 ||" + user_list_json + " || " + room_num + " || " + check_room_master);

            }

            System.out.println("사이즈" + client_list.size());

            return user_list_json;
        }
        public int getAllUserSize(String room_num) {
            int get = 0;
            for (int i = 0; i < client_list.size(); i++) {
                if (room_num.equals( client_list.get(i).getRoomNum()) ){
                    get = i;
                }
            }
            return get;
        }
        public int getRoomUserSize(String room_num) {
            get = 0;
            for (int i = 0; i < roomList.size(); i++) {
                if (room_num.equals( roomList.get(i).getRoomNum()) ){
                    get = i;
                }
            }
            
            return get;
        }
        Thread timer = new Thread() {
            public void run() {
                int time = 60;
                for (int i = 0; i < 60; i++) {
                     sendToAll("《8《" + time+"《",room_num);
                    System.out.println(time);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        
                    }
                    time = time - 1;
                }
                sendToAll("《8《65" ,room_num);
                sendToSomeone("《6.5《", room_num+"《", roomList.get(0).getRoomUser(1));
            }
        };
    }
    

    public static class RoomInfo {
        ArrayList<String> room_user_list;
        int time;
        String answer;
        String room_num;
        RoomInfo(String room_num){
            this.room_num = room_num;
            room_user_list = new ArrayList<>();
        }
        //유저 입장
        public void addUser(String user_name) {
            room_user_list.add(user_name);
        }
        //유저 퇴장
        public void delUser(String user_name) {
            for (int i = 0; i < room_user_list.size(); i++) {
                if (user_name.equals(room_user_list.get(i))){
                    room_user_list.remove(i);
                }
            }
        }
        //답 긁어오기
        public String getAnswer() {
            return "test";
        }
        public String getRoomNum() {
            return room_num;
        }
        public int getRoomUserList() {
            return room_user_list.size();
        }
        public String getRoomUser(int no) {
            return room_user_list.get(no);
        }
        
    }


    public static class Client_info {
        Socket socket;
        String room_num,user_name,readycheck;
        int score;
        Client_info(Socket socket, String room_num, String user_name, String readycheck){
            this.socket = socket;
            this.room_num = room_num;
            this.user_name = user_name;
            this.readycheck = readycheck;
            this.score = 0;
        }

        //setter
        public void setReadycheck(String readycheck) {
            this.readycheck = readycheck;
        }

        public void setScore(){
            this.score = this.score + 1;
        }

        public void resetScore(){
            this.score = 0;
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
        public int getScore(){
            return this.score;
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


                            /*System.out.println(room_num);
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
                            System.out.println(y);*/


                            /*System.out.println(room_num);
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
                            System.out.println(y);*/