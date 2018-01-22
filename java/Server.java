// package tcp_connect;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.lang.Character.Subset;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.io.EOFException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.net.SocketException;

public class Server {
    static Cqlconnect cqlconnect = new Cqlconnect();
        
    static ArrayList<ConnectionToClient> clients = new ArrayList<>();
    static ArrayList<Client_info> client_list = new ArrayList<>();
    static JavaDB javaDB = new JavaDB();
    public static void main(String[] args) {
        try {
            System.out.println("시작");      
            System.out.println("대기중....");
            Socket socket = null;
            ServerSocket server = new ServerSocket(8000);
            int line = 0;
            while ((socket = server.accept()) != null) {
                
                System.out.println(socket.getInetAddress() + " 로부터 연결요청이 들어왔습니다.");
                new ServerThread(socket,line).start();
                line = line + 1;
            }
            server.close();
        } catch (Exception e) {
            System.out.println("끊김~**************************************************");
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
        static ArrayList<RoomInfo> roomList = new ArrayList<>();
        String check_room_master = "";
        
        int get = 0;
        String to_cass ="";
        String ment_cass = "";
        boolean time_break = true;
        int ment_num = 0;
        int info = 0;
        boolean ment = true;
        boolean break_thread = false;
        String room_enter_user_list = "";
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
        String answer = "";
        String x = "";
        String y = "";
        String answercountList = "";
        String tagger_or_not = "";
        int line;
        // String[4] info = 
        boolean still_connect = false;
        boolean exit_room_coonect_checker = true;
        ServerThread(Socket socket,int line) {
            this.socket = socket;
            this.line = line;
            this.conToClient = new ConnectionToClient(socket);
            clients.add(conToClient);
        }
        public void run() {
            try {
                // String input = new String(buf, "UTF-8");
                while( (input = conToClient.read())!=null){
                    System.out.println("--------------------------------");
                    if (ment == true){
                        int idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        input = input.substring(idx + 1);
                        idx = input.indexOf("《");
                        user_name = input.substring(0,idx);
                        tagger_or_not = input.substring(idx + 1);
                        System.out.println( room_num + "번방으로.");
                        System.out.println( user_name + "님이 입장하였습니다.");
                        System.out.println(tagger_or_not + "타입");
                        boolean overlap_check = true;
                        String savement = "";
                        for(int i = 0; i < client_list.size(); i++){
                            if (room_num.equals(client_list.get(i).getRoomNum()) && user_name.equals(client_list.get(i).getUserName())){
                                savement = client_list.get(i).getsaveMent();
                                /*client_list.remove(i);
                                clients.remove(i);*/
                                // client_list.get(i).modifySocket();
                                overlap_check = false;

                                System.out.println( user_name + "님이 잠깐 끊겼습니다 LINE = " + line  );
                            }
                        }
                        if (tagger_or_not.equals("2") || tagger_or_not.equals("3")) {
                            for (int i = 0; i < roomList.size(); i++) {
                                if (roomList.get(i).getRoomNum().equals(room_num)) {
                                    get = i;
                                    System.out.println(get);
                                }
                            }
                            roomList.get(get).addUser(user_name, line);
                            if (tagger_or_not.equals("3")) {
                                System.out.println("3");
                                for (int i = 0; i < roomList.get(get).getSize(); i++) {
                                    if (roomList.get(get).getRoomUser(i).equals(user_name)) {
                                        roomList.get(get).getRoomturnModify(i);
                                        System.out.println(roomList.get(get).getRoomUser(i));
                                    }
                                }
                            }
                        }
                        
                        /*if (overlap_check == false){
                            client_list.remove(i);
                            clients.remove(i);
                            System.out.println("잠시 끊긴  사람");
                            
                        }
                        else if (overlap_check == true) {
                            System.out.println("새로 들어온 사람");
                            client_info = new Client_info(socket,room_num,user_name,"wait");
                            client_list.add(client_info);
                        }*/
                        client_info = new Client_info(socket,room_num,user_name,"wait",savement,"0",line);
                        client_list.add(client_info);
                        

                        user_list = check_user_list(room_num);
                        System.out.println(user_list + "1111111");
                        sendToAll("《2"+user_list+"《"+ "\n",room_num);
/*                        if (!savement.equals("")){
                            System.out.println("세이브메세지가 있습니다.");
                            sendToSomeone(savement,room_num,user_name);
                                
                        }
                        else {
                            System.out.println("세이브메세지가 없습니다.");
                            
                        }*/
                        // sendToAll("《1《test1》+"+"\n"+"《1《test1》++"+"\n"+"《1《test1》+++"+"\n",room_num);



                        check_still_connect.start();
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
                        // System.out.println(input);

                        int idx = input.indexOf("《");
                        tcp_type = input.substring(0,idx);
                        // System.out.println(tcp_type + "번타입 ");
                        
                        // System.out.println(user_name);
                        // input = input.substring(idx + 1);
                        // System.out.println(input);
                        input = input.substring(idx + 1);    
                        idx = input.indexOf("《");
                        room_num = input.substring(0,idx);
                        user_name = input.substring(idx + 1);
                        // System.out.println(room_num  + "번방");
                        // System.out.println(user_name);

                        //채팅
                        if (tcp_type.equals("0")){

                            user_list = check_user_list(room_num);
                            System.out.println(user_list + "1111111");
                            sendToAll("《2"+user_list+"《"+ "\n",room_num);
                        }
                        else if (tcp_type.equals("1")){
                            System.out.println("number: " + room_num + " || message: " + user_name);
                            idx = user_name.indexOf("》");
                            to_cass = user_name.substring(0,idx);
                            ment_cass = user_name.substring(idx + 1);
                            // System.out.println(to_cass + "~~~~!@!@!@" + ment_cass);
                            cqlconnect.insert(room_num,to_cass,ment_cass);
                            sendToAll("《1《"+user_name+ "\n",room_num);
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
                                
                                /*roomList.get(get).setAnswer();
                                answer = roomList.get(get).getAnswer();
                                sendToSomeone("《6《"+user_name+"《"+answer+"《"+"\n",room_num,roomList.get(get).getRoomUser(0));
                                */sendToExcept("《6.1《"+user_name+"《"+"\n",room_num,roomList.get(get).getRoomUser(0));
                                timer.start(); 

                                System.out.println("스타트!!!!!!!1 : " + room_num + " || type"+ check_room_master);
                                
                                javaDB.connect_db(room_num,1);
                            }
                            else if(check_room_master.equals("3")){
                                System.out.println("레디");
                                sendToAll("《2"+ user_list + "《" + "\n",room_num);
                            }
                            
                        }
                        //그림 그리기
                        else if (tcp_type.equals("3")){
                            System.out.println("《3《"+user_name+ "||" +  room_num);
                            sendToAll("《3《"+user_name + "\n",room_num);
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
                            
                            if (user_name.contains("《")) {
                                idx = user_name.indexOf("《");
                                user_name = user_name.substring(0,idx);
                            }
                            
                            //c쿼리에서 단어 빼내서 ex> "test"
                            // roomList.get(get).setAnswer();
                            answer = roomList.get(get).getAnswer();
                            System.out.println(answer+"");
                            System.out.println("《6《"+room_num+"《"+user_name+"《"+answer+"《"+"  \\  " + user_name);
                            // sendToSomeone("《6《"+user_name+"《"+answer+"《"+"\n",room_num,user_name);
                            // sendToExcept("《6.1《"+user_name+"《"+"\n",room_num,user_name);
                            // sendToAll("《8《65"+"\n" ,room_num);
                            // timer.start();    
                        }

                        //정답 시도
                        else if (tcp_type.equals("7")){
                            answer = roomList.get(get).getAnswer();
                            idx = user_name.indexOf("《");
                            to = user_name.substring(0,idx);
                            content = user_name.substring(idx+1);
                            

                            // System.out.println("7");
                            cqlconnect.insert(room_num,to,content);
                            if (answer.equals(content)){
                                time_break = false;
                                roomList.get(get).setTimeBreaker();
                                System.out.println("right");
                                for (int i = 0; i < roomList.size(); i++) {
                                    if (roomList.get(i).getRoomNum().equals(room_num)) {
                                        System.out.println(to + " 가 맞춤");
                                        roomList.get(i).setAnswerCount(to);
                                    }
                                }
                                sendToAll("《7.5《"+user_name+"《"+ answer + "\n",room_num);
                                /*for (int i = 0; i < roomList.get(get).getSize(); i++) {
                                    System.out.println("다음턴");
                                    //다음 턴
                                    if (roomList.get(get).getRoomturn(i).equals("2")){
                                        
                                        if (next == true) {
                                            roomList.get(get).getRoomturnModify(i);
                                            sendToSomeone("《6.5《"+"\n", room_num, roomList.get(get).getRoomUser(i));
                                            System.out.println("아이디 : " + roomList.get(get).getRoomUser(i));
                                            
                                            break;
                                            
                                        }
                                    }
                                    //게임 끝
                                    else {
                                        answercountList = roomList.get(i).getAnwserCountList() + "《";
                                        sendToAll("《6.8" + answercountList, room_num);
                                    }
                                }*/

                                
                            }
                            else if (!answer.equals(content)){    
                                System.out.println("wrong");
                                sendToAll("《7《"+user_name+ "\n",room_num);
                            }

                            
                        }
                        else if (tcp_type.equals("8")) {
                            
                        }


                        //방 나가기
                        else if (tcp_type.equals("10")){
                            idx = user_name.indexOf("》");
                            user_name = user_name.substring(0,idx);
                            System.out.println(user_name);
                            exit_room(user_name,room_num);
                            user_list = check_user_list(room_num);
                            System.out.println("유저 리스트트트틑 :" + user_list);
                            
                            sendToAll("《2"+user_list+"《"+ "\n",room_num);
                            if (check_room_master.equals("2")){
                                sendToSomeone("《6.5"+ user_list + "《" + "\n", room_num, roomList.get(get).getRoomUser(0));
                                sendToExcept("《2.5"+ user_list + "《" + "\n", room_num, roomList.get(get).getRoomUser(0));
                                
                                sendToExcept("《6.1《"+user_name+"《"+"\n",room_num,roomList.get(get).getRoomUser(0));
                                timer.start(); 

                                System.out.println("스타트!!!!!!!1 : " + room_num + " || type"+ check_room_master);
                                
                                javaDB.connect_db(room_num,1);
                            }
                            else if(check_room_master.equals("3")){
                                System.out.println("레디");
                                sendToAll("《2"+ user_list + "《" + "\n",room_num);
                               
                            }
                            
                            
                            exit_room_coonect_checker = false;
                            
                            break;
                        }
                        /*else if (tcp_type.equals("11")) {
                            
                            idx = user_name.indexOf("》");
                            user_name = user_name.substring(0,idx);
                            System.out.println(user_name);
//                            exit_room(user_name,room_num);
//                            user_list = check_user_list(room_num);
                            System.out.println(user_list);
                            System.out.println("onstop 상태");
                            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                                System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                                if ( user_name.equals( client_list.get(i).getUserName() )){

                                    System.out.println("client name"+client_list.get(i).getUserName());
                                    client_list.get(i).onStopActivity();
                                }   
                                // System.out.println(clients.get(i));
                            }
                            //type 13 : onstop일때 client thread 닫기 위해
                            exit_room(user_name,room_num);
                            user_list = check_user_list(room_num);
                            // sendToSomeone("《13《"+ "\n",room_num,user_name);
                            break;
                        }*/
                        else if (tcp_type.equals("12")) {
                            
                            idx = user_name.indexOf("》");
                            user_name = user_name.substring(0,idx);
                            System.out.println(user_name);
                            /*exit_room(user_name,room_num);
                            user_list = check_user_list(room_num);*/
                            System.out.println(user_list);
                            System.out.println("onrestart 상태");
                            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                                System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                                if (room_num.equals(client_list.get(i).getRoomNum())){
                                    System.out.println("onrestart 상태!!!!!!!!");
                                    System.out.println("ment : "+client_list.get(i).getsaveMent());/*if (client_list.get(i).statusActivity() == 1) {
                                        System.out.println("방번호 : " + room_num);
                                        clients.get(i).write(message);
                                        System.out.println( "메세지 : " + message);
                                    }
                                    else if (client_list.get(i).statusActivity() == 1) {
                                        client_list.get(i).saveMent(message);
                                    }*/
                                    client_list.get(i).onRestartActivity();
                                    System.out.println("ment : "+client_list.get(i).getsaveMent());
                                    client_list.get(i).resetsaveMent();
                                    sendToSomeone(client_list.get(i).getsaveMent(),room_num,user_name);
                                }   
                                // System.out.println(clients.get(i));
                            }
                        }
                        else if (tcp_type.equals("13")){
                            still_connect = true;
                            // System.out.println(user_name + " : 13연결@@@@@@@@@@@@@@@@@@@@@@@@@@");
                        }
                        //게임 끝
                        else 
                            System.out.println("해당없음");
                    }
                }
            } catch (Exception e) {
                System.out.println("끊김*********************************************************************************");

            } 
             /*catch (EOFException e){
                System.out.println("EOFException*********************************************************************************");

            } catch (IOException e){
                System.out.println("IOEException *********************************************************************************");
            } catch (UnknownHostException e){
                System.out.println("UnknownHostException*********************************************************************************");

            }*/

        }

        public void check_answer(String answer, String user_name){
            String real = "test";
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (user_name.equals(client_list.get(i).getUserName())){
                    client_list.get(1).setScore();
                }
            }
        }

        public void overlap_user(String user_name,String room_num){
            boolean exit_room_check = true;
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (user_name.equals(client_list.get(i).getUserName())){
                    System.out.println(client_list.get(i).getUserName() + " 나감 line " + line);
                    client_list.remove(i);
                    clients.remove(i);
                    System.out.println("1");
                }
            }
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (room_num.equals(client_list.get(i).getRoomNum())) {
                    exit_room_check = false;
                    System.out.println("55");
                }
            }
        }

        public void exit_room(String user_name,String room_num){
            boolean exit_room_check = true;
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (user_name.equals(client_list.get(i).getUserName()) && line == client_list.get(i).getLine() ){
                    System.out.println(client_list.get(i).getUserName() + " 나감 line " + line);
                    // clients.get(i).disconnect();
                    client_list.remove(i);
                    clients.get(i).disconnect();
                    clients.remove(i);
                    System.out.println("1");
                }
            }
            for (int i =0 ;i < client_list.size() ; i ++) {
                if (room_num.equals(client_list.get(i).getRoomNum()) ) {
                    exit_room_check = false;
                    System.out.println("55");
                }
            }

            System.out.println("3");
            if (exit_room_check == true) {
                System.out.println(room_num + "번방이 없어졌습니다.");
                javaDB.connect_db(room_num, 2);
            }
            else {
                System.out.println(room_num + "번방이 유지되고 있습니다.");
            }
        }

        public void user_ready(String user_name,String ready) {
            for (int i =0 ;i < client_list.size() ; i ++) {
                System.out.println(user_name + "||" + client_list.get(i).getUserName());
                if (user_name.equals(client_list.get(i).getUserName()) && client_list.get(i).getLine() == line){
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
            // System.out.println(message);
            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                // System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                if ( room_num.equals( client_list.get(i).getRoomNum() )){
                    if (client_list.get(i).statusActivity() == 1) {
//                         System.out.println("방번호 : " + room_num);
                        clients.get(i).write(message);
                        // System.out.println( "메세지 : " + message);

                    }
                    else if (client_list.get(i).statusActivity() == 0) {
                        client_list.get(i).saveMent(message);
                        System.out.println( client_list.get(i).getUserName() +" 의 세이브메세지 : " + message );
                        System.out.println( client_list.get(i).getUserName() +"세이브메세지 총 량 : " + client_list.get(i).getsaveMent() );
                    }
                }   
                // System.out.println(clients.get(i));
            }
        }
        
        public void sendToSomeone (String message,String room_num,String user_name){
            
            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                // System.out.println("한명한테 데이터 보내기 시도중");
                // System.out.println("room_num || " + client_list.get(i).getRoomNum() + "||" + room_num);
                // System.out.println("user_name || " + client_list.get(i).getUserName() + "||" +user_name );
                // System.out.println("someone : " + client_list.get(i).getUserName()  +" user name  "+user_name +" ||  "+message);
                if ( room_num.equals( client_list.get(i).getRoomNum() ) 
                    && user_name.equals(client_list.get(i).getUserName())){
                    // System.out.println("이름" + client_list.get(i).getUserName());
                    // System.out.println("message : " + message);
                    clients.get(i).write(message);
                }
            }
        }

        public void sendToExcept (String message,String room_num,String user_name){
            for (int i = 0 ; i < client_list.size() ; i ++ ) {
                if ( room_num.equals( client_list.get(i).getRoomNum() ) 
                    && !user_name.equals(client_list.get(i).getUserName())){
                    // System.out.println("이름" + client_list.get(i).getUserName());
                    //  System.out.println("except : " + message);
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
                get = getRoomUserSize(room_num);
                System.out.println("확인 : " + get);
                System.out.println("모두 다");
                for (int i = 0; i < client_list.size(); i++) {
                    if (room_num.equals( client_list.get(i).getRoomNum()) ){
                        roomList.get(get).addUser(client_list.get(i).getUserName(),client_list.get(i).getLine());
                        client_list.get(get).enterGameroom();
                    }
                }
                System.out.println("방번호 사이즈!!!!!" + roomList.get(get).getSize());
                // System.out.println("《6.5《" + roomList.get(get).getRoomUser(0));
                roomList.get(get).getRoomturnModify(0);
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
        //하트비트 쓰레드
        Thread check_still_connect = new Thread() {
            public void run() {
                String user = user_name;
                int count = 0;
                while(true){
                    try { 
                        // System.out.println("다시 시도
                        if (exit_room_coonect_checker == false){
                            System.out.println("방 나감");
                            break;
                        }
                        Thread.sleep(5000);
                        /*sendToSomeone("《13《"+user_name+"》\n",room_num,user_name);
                        if (exit_room_coonect_checker == false){
                            System.out.println("방 나감");
                            break;
                        }
                        Thread.sleep(5000);*/
                        boolean check_get = true;
                        for (int j = 0; j < 3; j++) {
                            
                            int re_enter = 0;
                            for(int z = 0; z < client_list.size(); z++) {
                                if (client_list.get(z).getUserName().equals(user)) {
                                    re_enter = re_enter + 1;
                                }
                            }
                            if (re_enter > 1) {
                                exit_room_coonect_checker = false;
                                still_connect = false;
                                break;
                            }

                            sendToSomeone("《13《"+user+"》\n",room_num,user);
                            if (exit_room_coonect_checker == false){
                                System.out.println("방 나감");
                                break;
                            }
                            if (still_connect == false){
                                for (int i = 0 ; i < client_list.size() ; i ++ ) {
//                                    System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                                    if ( user.equals( client_list.get(i).getUserName() )){

                                        // System.out.println("client name"+client_list.get(i).getUserName());
                                        // client_list.get(i).onStopActivity();
                                    }   
                                    // System.out.println(clients.get(i));
                                }
                                //type 13 : onstop일때 client thread 닫기 위해
//                                exit_room(user_name,room_num);
//                                user_list = check_user_list(room_num);
                                // sendToSomeone("《13《"+ "\n",room_num,user_name);
                                check_get = false;
                            }
                            else if (still_connect == false && check_get == false){
                                for (int i = 0 ; i < client_list.size() ; i ++ ) {
                                    System.out.println("비교 : " + room_num + "||" + client_list.get(i).getRoomNum());
                                    if (room_num.equals(client_list.get(i).getRoomNum())){
                                        System.out.println("연결 상태!!!!!!!!");
                                        client_list.get(i).onRestartActivity();
                                        System.out.println("ment : "+client_list.get(i).getsaveMent());
                                        sendToSomeone(client_list.get(i).getsaveMent(),room_num,user);
                                        client_list.get(i).resetsaveMent();
                                    }
                                }
                                check_get = true;
                            }
                            // System.out.println(room_num + " 번 방 ( " + user+" ) LINE =  "+ line +"       "+ (j + 1) + " 번쨰 리스폰 중");
                            Thread.sleep(3000);

                        }
                        //끊겼을떄
                        if (still_connect == false){
                            System.out.println(room_num + " 번 방 ( " + user+" ) LINE =  "+ line + " 끊김");
                            boolean reconnectboolean = false;
                            for (int j = 0; j < roomList.get(get).getSize(); j++) {

                                System.out.println(roomList.get(get).getRoomLine(j) +" | |   | | "+ line );
                                System.out.println(roomList.get(get).getRoomUser(j)+" | |   | | "+ user);
                                if (roomList.get(get).getRoomUser(j).equals(user) && roomList.get(get).getRoomLine(j) != line) {
                                    reconnectboolean = true;
                                    System.out.println(roomList.get(get).getSize() + " 방 사이즈!1");
                                    
                                    roomList.get(get).delUser(user);
                                    System.out.println(roomList.get(get).getSize() + " 방 사이즈!2");
                                    break;
                                }
                                
                            }
                            for (int j = 0; j < roomList.get(get).getSize(); j++) {          
                                if (roomList.get(get).getRoomUser(j).equals(user) && roomList.get(get).getRoomLine(j) == line) {
                                    
                                    System.out.println(roomList.get(get).getSize() + " 방 사이즈!1");
                                    
                                    roomList.get(get).delUser(user);
                                    System.out.println(roomList.get(get).getSize() + " 방 사이즈!2");
                                    break;
                                }
                                
                            }   
                                
                            if (!reconnectboolean) {
                                //소켓 끊어졌다는걸 array로 명시
                                //게임 시작 후 소켓 끊어진 유저 체크
                                roomList.get(get).addDisconnectUserList(user);
                                roomList.get(get).delUser(client_list.get(get).getUserName());
                                System.out.println(roomList.get(get).getRoomNum() +"번 인원수 : "+ roomList.get(get).getRoomUserList());   
                                System.out.println("소켓 끊긴 리스트 사이즈!!" + roomList.get(get).getDisconnectUserList().size());
                            }
                            else { 
                                System.out.println("소켓이 끊겼다 다시 붙었다!!!");
                            }
                            //하트비트 교환시 소켓 끊겼다 가정 하여 list에서 제외
                            for (int i = 0; i < client_list.size(); i++) {
                                if (client_list.get(i).getUserName().equals(user) && client_list.get(i).getLine() == line) {
                                    System.out.println(user + "의 line1 " + client_list.get(i).getLine() +"||"+ line);
                                    exit_room(user,room_num);
                                    user_list = check_user_list(room_num);
                                    sendToAll("《2"+user_list+"《"+ "\n",room_num);
                                    
                                }
                                else {
                                    System.out.println(user + "의 line2 " + client_list.get(i).getLine() +"||"+ line);
                                }

                            }
                            break_thread = true;
                            ServerThread.interrupted();
                            System.out.println(user + "    LINE = " + line + "의 소켓 종료");
                            break;
                        }
                        //안끊겼을때
                        else if (still_connect == true){
                            System.out.println(room_num + " 번 방 ( " + user + " ) LINE = " + line +"연결 유지 됨" );
                            
                            still_connect = false;  
                            count = 3;
                        }
                    } catch (InterruptedException e) {
                            e.printStackTrace();
                    }
                }
            }
        };

        Thread timer = new Thread() {
            public void run() {
                ArrayList<String> disconnectUserList = new ArrayList<>();
                String tagger = roomList.get(get).getRoomUser(0);
                boolean disconnect_boolean = false;
                roomList.get(get).setAnswer();
                answer = roomList.get(get).getAnswer();
                sendToSomeone("《6《"+user_name+"《"+answer+"《"+"\n",room_num,roomList.get(get).getRoomUser(0));
                sendToExcept("《6.1《"+user_name+"《"+"\n",room_num,roomList.get(get).getRoomUser(0));
                while(true){
                    int socket_count = 0;
                    int time = 60;
                    for (int i = 0; i < 10; i++) {
                        disconnect_boolean = false;
                        if (i > 50){
                            sendToAll("《8《0" + time+"《"+"\n",room_num);    
                        }
                        else {
                            sendToAll("《8《" + time+"《"+"\n",room_num);
                        }
//                        System.out.println(time);
                        // System.out.println(user_name + "\\\\ 이름~~~");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        time = time - 1;
                        disconnectUserList = roomList.get(get).getDisconnectUserList();
                        for (int j = 0; j < disconnectUserList.size(); j++) {
                            if (tagger.equals(disconnectUserList.get(j))) {
                                if (socket_count == 3) {
                                disconnect_boolean = true;
                                roomList.get(get).setTimeBreaker();
                                System.out.println(tagger  + " 가 소켓이 끊겼습니다.");
                                socket_count = 0;
                                }
                                socket_count = socket_count + 1;
                            
                                
                            }
                            System.out.println(disconnectUserList.get(j) + " |0| " + tagger);
                        }
//                        System.out.println(roomList.get(get).getDisconnectUserList().size() +"크기는 요렇다");
//                        System.out.println(disconnectUserList.size() + "크기는 이렇다   " + roomList.get(get).getRoomNum() + " 번방");
                        if (roomList.get(get).getTimeBreak() == false){
                            // time_break = true;
                            roomList.get(get).setTimeStarter();
//                            System.out.println("이벤트 때문에 턴                                끝남");
                            /*roomList.get(get).setAnswer();
                            answer = roomList.get(get).getAnswer();
                            sendToSomeone("《6《"+user_name+"《"+answer+"《"+"\n",room_num,user_name);
                            sendToExcept("《6.1《"+user_name+"《"+"\n",room_num,user_name);
                            timer.start();*/
                            break;
                        }
                        else {
//                            System.out.println("이벤트 때문에 턴                        안    끝남");
                        }
                        
                    }
                    if (disconnect_boolean == false) {
                        sendToAll("《8《65《"+"\n" ,room_num);
                    }
                    else if (disconnect_boolean == true) {
                        System.out.println(tagger + "가 소켓이 끊겼습니다.!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                        sendToAll("《14《"+tagger+"\n" ,room_num);
                        roomList.get(get).delDisconnectUserList(tagger);
                    }

                    if (roomList.get(get).getSize() < 2){
                        System.out.println("방에 혼자 남았습니다. 방을 종료 합니다.");
                        sendToAll("《15《"+tagger+"\n" ,room_num);
                        break;
                    }

                    boolean next = true;
                    System.out.println("몇번째 방?? : " + get );
                    System.out.println("현재 게임진행중인 방 갯수: " + roomList.size());
                    System.out.println("방에 있는 인원수 " +roomList.get(get).getSize());
                                         // roomList.get(get).getSize()
                    for (int i = 0; i < roomList.get(get).getSize(); i++) {
                        System.out.println("다음턴");
                        //다음 턴
                        if (roomList.get(get).getRoomturn(i).equals("2")) {
                            // System.out.println(roomList.get(get).getRoomUser(i));
                            roomList.get(get).getRoomturnModify(i);
                            System.out.println(i);
                            System.out.println(roomList.get(get).getRoomUser(i)+" dddddd :   "+roomList.get(get).getRoomturn(i));
                            // sendToSomeone("《6.5《"+"\n", room_num, roomList.get(get).getRoomUser(i));
                            
                            /*sendToSomeone("《6《"+user_name+"《"+answer+"《"+"\n",room_num,user_name);
                            sendToExcept("《6.1《"+user_name+"《"+"\n",room_num,user_name);*/
                            // timer.start();
                            
                            roomList.get(get).setAnswer();
                            answer = roomList.get(get).getAnswer();
                            sendToSomeone("《6《"+user_name+"《"+answer+"《"+"\n",room_num,roomList.get(get).getRoomUser(i));
                            sendToExcept("《6.1《"+user_name+"《"+"\n",room_num,roomList.get(get).getRoomUser(i));
                            
                            //sendToExcept("《6.5《"+"\n", room_num, roomList.get(get).getRoomUser(i));
                            System.out.println("아이디dldldldld : " + roomList.get(get).getRoomUser(i));    
                            next = false;
                            break;
                        }
                    }
                    //게임 끝
                    if (next == true){
                        System.out.println("끝!!!!!!!!!!!!111" );
                        answercountList = roomList.get(get).getAnwserCountList();
                        sendToAll("《0《" + answercountList+"\n", room_num);
                        System.out.println("끝 : " + answercountList);
                        break;
                    }
                    //게임 끝
                    else if (next == false){
                        System.out.println("아직 안끝낫다~ ");
                        continue;
                    }
                }
                System.out.println("게임이 끝났습니다.");
            }
        };
    }
    
    public static class RoomStatus{
        ArrayList<Integer> a; 
    }
    

    public static class RoomInfo {
        Room_client_list room_client_list;
        ArrayList<Room_client_list> room_user_list;
        ArrayList<String> room_user_turn;
        ArrayList<String> disconnect_userList;
        int time;
        int line;
        String answer = null;
        String room_num;
        String status = "2";
        boolean time_break = true;
        RoomInfo(String room_num){
            this.room_num = room_num;
            room_user_list = new ArrayList<>();
            room_user_turn = new ArrayList<>();
            disconnect_userList = new ArrayList<>();
        }
        //유저 입장
        public void addUser(String user_name, int line) {
            room_client_list = new Room_client_list(user_name,line);
            room_user_list.add(room_client_list);
            room_user_turn.add("2");
        }
        //유저 퇴장
        public void delUser(String user_name) {
            for (int i = 0; i < room_user_list.size(); i++) {
                System.out.println(user_name + " |||| " + room_user_list.get(i).getUserName());
                if (user_name.equals(room_user_list.get(i).getUserName())){
                    room_user_list.remove(i);
                    room_user_turn.remove(i);
                }
            }
        }
        public void setAnswer(){
            answer = javaDB.getAnswer();
        }
        //답 긁어오기
        public String getAnswer() {
            return answer;
        }
        public void addDisconnectUserList(String user_name) {
            disconnect_userList.add(user_name);
        }
        public void delDisconnectUserList(String user_name) {
            for (int i = 0; i < disconnect_userList.size(); i++) {
                if (disconnect_userList.get(i).equals(user_name)) {
                    disconnect_userList.remove(i);
                }
            }
        }
        public ArrayList<String> getDisconnectUserList(){
            return disconnect_userList;
        }
        public String getRoomNum() {
            return room_num;
        }
        public int getRoomUserList() {
            return room_user_list.size();
        }
        public void getRoomturnModify(int no) {
            room_user_turn.set(no, "1");
        }
        public void setTimeBreaker() {
            time_break = false;
        }
        public void setTimeStarter() {
            time_break = true;
        }
        public boolean getTimeBreak() {
            return time_break;
        }
        public String getRoomUser(int no) {
            return room_user_list.get(no).getUserName();
        }
        public int getRoomLine(int no) {
            return room_user_list.get(no).getLine();
        }
        public String getRoomturn(int no) {
            return room_user_turn.get(no);
        }
        public String getStatus() {
            return status;
        }
        public int getSize() {
            return room_user_list.size();
        }
        public void setAnswerCount(String user_name) {
            System.out.println("카운트 함수 들어옴");
            System.out.println("방에 있는 사람 수"+room_user_list.size());
//            System.out.println(room_user_list.get(0).getUserName());
            //정답 카운트 만들고잇음!!!!!!!!!!!!!!
            for (int i = 0; i < room_user_list.size(); i++) {
                if (room_user_list.get(i).getUserName().equals(user_name)) {
                    System.out.println(room_user_list.get(i).getUserName() + "의 점수가 올랐습니다.");
                    System.out.println("현재점수 : " + room_user_list.get(i).getUserCount());
                    room_user_list.get(i).setUserCount();
                    System.out.println("점수 오름");
                }
            }
        }
        public String getAnwserCountList() {
            String list = "";
            //카운트 리스트 정리해서 리턴
            for (int j = 0; j <room_user_list.size(); j++) {
                list = list + room_user_list.get(j).getUserName() + " : " + room_user_list.get(j).getUserCount() + "《";
            }
            return list;
        }
    }

    public static class Room_client_list{
        String user_name;
        int user_count = 0;
        int line;
        Room_client_list(String user_name,int line){
            this.user_name = user_name;
            this.line = line;
        }
        public void setUserCount(){
            System.out.println("현재점수123123 ");
            
            user_count = user_count + 1;
            System.out.println("현재점수 "  + user_count);
        }
        public int getLine() {
            return line;
        }
        public String getUserName(){
            return user_name;
        }
        public int getUserCount(){
            return user_count;
        }

    }


    public static class Client_info {
        Socket socket;
        String room_num,user_name,readycheck;
        int score;
        int statusactivity = 1;
        int line;
        String savement = "";
        String status_room;
        Client_info(Socket socket, String room_num, String user_name, 
                String readycheck,String savement,String status_room,int line){
            this.socket = socket;
            this.room_num = room_num;
            this.user_name = user_name;
            this.readycheck = readycheck;
            this.savement = savement;
            this.status_room = status_room;
            this.score = 0;
            this.line = line;
        }
        public void modifySocket(Socket socket){
            this.socket = socket;
        }

        //setter
        public void setReadycheck(String readycheck) {
            this.readycheck = readycheck;
        }

        public void setScore(){
            this.score = this.score + 1;
        }
        public void setRoomNum(String room_num){
            this.room_num = room_num;
        }
        public void enterGameroom() {
            status_room = "1";
        }
        public void resetScore(){
            this.score = 0;
        }
        //getter
        public int getLine() {
            return line;
        }
        public String getCheckEnterRoom() {
            return status_room;
        }
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
        public void onStopActivity () {
            statusactivity = 0;
        }
        public void onRestartActivity () {
            statusactivity = 1;
        }
        public int statusActivity() {
            return statusactivity;
        }
        public void saveMent(String ment) {
            savement = ""+savement + ment;
        }
        public String getsaveMent() {
            /*String tempment = savement;
            savement = "";*/
            return savement;
        }
        public void resetsaveMent(){
            savement = "";
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
               System.out.println(e + "1111111111112222222222222222222222222");
            }
        }
        public void disconnect(){
            try {
              socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void modifySocket(Socket socket){
            this.socket = socket;
            try {                
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                oos = new ObjectOutputStream(socket.getOutputStream());
                printWriter = new PrintWriter(oos,true);
            } catch (Exception e) {
                System.out.println(e + "2222222222222222222222222");
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

                // System.out.println("보냄!");
            } catch (Exception e) {
//               System.out.println(e +  "2222222222222222222222222333333333333");
            }
        }
    }

    public static class JavaDB  {
        String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
        String DB_URL = "jdbc:mysql://mariadb.ceqw0wwolo9b.ap-northeast-2.rds.amazonaws.com/drawword";
        String USERNAME = "root";
        String PASSWORD = "KKKKKKKK";
        
        Statement stmt = null;        
        ResultSet rs = null;
        public JavaDB(){
            try{
            System.out.println("mariadb connected");
            Class.forName(JDBC_DRIVER);                
            Connection conn = null;
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            stmt = conn.createStatement();
            }catch(SQLException se1){
                se1.printStackTrace();
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }

        public String getAnswer(){
            String answer = null;
            try{
                rs = stmt.executeQuery("select * from word order by rand() limit 1;");
                
                while(rs.next()){
                    answer = rs.getString("word");
                    // String memberName = rs.getString("memberName");

                    System.out.print("\n** Group : " + answer);
                    // System.out.print("\n    -> Member: " + memberName);
                }

            }catch(SQLException se1){
                se1.printStackTrace();
            }catch(Exception ex){
                ex.printStackTrace();
            }
            return answer;

        }


        public void connect_db (String room_num , int type)  {  
            /*String JDBC_DRIVER = "org.mariadb.jdbc.Driver";  
            String DB_URL = "jdbc:mysql://mariadb.ceqw0wwolo9b.ap-northeast-2.rds.amazonaws.com/drawword";

            String USERNAME = "root";
            String PASSWORD = "KKKKKKKK";  
            */
            /*System.out.println("\n- MySQL Connection");
            */    
            /*Connection conn = null;
            Statement stmt = null;
            */
           try{
                
         /*       conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
                System.out.println("\n- MySQL Connection");
                stmt = conn.createStatement();
         */       
                String sql = null;
                // sql = "insert into user_list(name,id,pwd,phone,sex,photo_uri) values ('name','id','pwd','phone',1,'photo_uri')";
                if (type == 1){
                    sql = "update room_info set room_status = 'already' where iden = " + room_num;
                }
                else if (type == 2){
                    sql = "update room_info set del_status = 'dead' , room_status = 'already' where iden = " + room_num;
                }
                System.out.println(sql);
                rs = stmt.executeQuery(sql);

                
                /*rs.close();
                stmt.close();
                conn.close();*/
            }catch(SQLException se1){
                se1.printStackTrace();
            }catch(Exception ex){
                ex.printStackTrace();
            }/*finally{
                try{
                    if(stmt!=null)
                        stmt.close();
                }catch(SQLException se2){
                }
                try{
                    if(conn!=null)
                        conn.close();
                }catch(SQLException se){
                    se.printStackTrace();
                }
            }*/
            // System.out.println("\n\n- MySQL Connection Close");

        }  
      
    }
}
