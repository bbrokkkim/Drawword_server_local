<?php 
	ini_set('display_errors', '1');

	define('GOOGLE_API_KEY', 'AAAAT0KVr5U:APA91bGsfxi1Y_-vR9xkUA_C3xBzbqpsnYGuP-Zc9WnPgvq1kCP__RV50Ig-rw_80L5xvC1zfnvkx1BRMXLyLZdkqTf574AoNuFgmt-VCIq3uSX5BsgdISUFFyV8yjVdGILl_xHB3g1K'); 

/*	$tokens = 'dEmDpHVAabA:APA91bGgfmSnXrO3DpZm66vjM8nMBn3iCGY6Aob94xjbCD06DGiIYqZxs5kQnWNShcfs_Ed_y4BTzkqOQ111JJagZgPo6NcwdL8QoYptBqy67PqWjQBV1H2jj6nCHFhMi8KrtoOu06yO';*/
/*	$room_num = '99';
	$room_name = "testroom79";
	$user_name = '김경관';
*/	
	include_once("config.php");

	$user_name = isset($_POST['user_name']) ? $_POST['user_name'] : "";
	$room_num = isset($_POST['room_num']) ? $_POST['room_num'] : "";
	$room_name = isset($_POST['room_name']) ? $_POST['room_name'] : "";
	$user_list = isset($_POST['user_list']) ? $_POST['user_list'] : "";
	// $row = '{"invate_list":["kkk1","kkk2","kkk3","kkk3","kkk3","kkk5","kkk7"]}';
	
	echo "asdfasdf";
	$list = json_decode($user_list,true);
	$array = $list['invate_list'];
	// var_dump($list);
	// echo count($array);
	foreach ($list['invate_list'] as $invate_row) {
		$query = "select * from users where user_name = '$invate_row'";
		$result = mysqli_query($connect,$query)or die ("입력 실패");
		$row = mysqli_fetch_array($result);
		$token = $row['Token'];
		// echo $query;
		echo $token;
			
		$message = array("room_num" => $room_num,
						"room_name" => $room_name,
	                    "user_name" => $user_name,
	                    "type" => "1"
	                    );
		// echo $message;
		// echo $myMessage;
		$send_nofi = send_notification($token,$message);
		echo $send_nofi;
		
	}
	function send_notification ($tokens, $message)
	{

		$json = json_encode($tokens);
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'to' => $tokens,
			 'data' => $message
			);

		$headers = array(
			'Authorization:key =' . GOOGLE_API_KEY,
			'Content-Type: application/json'
			);

	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}
?>