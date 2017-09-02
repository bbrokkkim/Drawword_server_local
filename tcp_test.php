<?php 
ini_set('display_errors', '1');
	// 무한정 실행하기 위해 시간한계를 0으로 설정한다. 
	set_time_limit (0); 

	// 대기할 IP 주소와 포트번호를 설정한다 
	$address = '0.0.0.0'; 
	$port = 8003;
// $asdf = $_SERVER['REMOTE_ADDR'];
// echo $asdf;
// ; 123.456.789.000
// 접속한 사용자의 ip 주소 불러오기

// $as = $_SERVER['SERVER_PORT'];


// ; 80, 8080 ...
// 사용되고 있는 포트 불러오기
	// TCP 소켓을 만든다.
	$sock = socket_create(AF_INET, SOCK_STREAM, SOL_TCP) or die ('Could not create socket'); 
	// socket_create(IN, type, protocol)
	// IP 주소와 포트번호를 소켓에 결합 
	$bind = socket_bind($sock, $address, $port) or die(socket_strerror($bind)); 
	// 접속을 위해 대기를 시작한다
	socket_listen($sock) or die ('Could not listen');
	// socket_listen(socket) 
	$i = 0;


	/* 들어오는 요청을 받아들이고 자식 프로세스로 그들을 처리한다 */ 
	// echo "string";
	

	
 
 	while(true){
 		if(isset($client1)){
			$client2 = socket_accept($sock);
		}

 		else
			$client1 = socket_accept($sock);	
	
		while (true) {
					

			$user_id = socket_read($client1, 1024);	
			socket_write($client1, $user_id);
			echo $i;
			echo $user_id;
			
			$user_ment = socket_read($client1, 1024);
			echo $user_ment;
			socket_write($clien t1, $user_ment);
			

			
			
		}
		$input_trim = trim($user_ment); 
			// sleep(5);
			if (!$input_trim == "test"){
			 	echo "good";
			 	$i = $i + 1;
			 	continue;
			}
				
			else if ($input_trim == "end"){
				echo "fin";
				echo $input;
				break;
			}
			$i = $i + 1;
	}
//	$input = socket_read($client, 1024); 
	// sleep(5);
//		echo $input;
	// 입력받은 문자열에서 공백을 제거한다. 
	//$output = ereg_replace("[ \t\n\r]","",$input).chr(0); 
	//echo $output;
//	echo "test";
	// 클라이언드에 출력을 보낸다. 
///	socket_write($client, $input); 

	echo "end";

	// 자식 프로세스를 닫는다 
	socket_close($client); 

	// 주 소켓을 닫는다 
	socket_close($sock); 

	function thread(){

	}


 ?>