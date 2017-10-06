<?php 
ini_set('display_errors', '1');
	include_once("config.php");

	$choice = $_POST['choice'];
	$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : "";
	$room_name = isset($_POST['room_name']) ? $_POST['room_name'] : "";


	//방 생성
	if ($choice == 1){
		date_default_timezone_set("Asia/Seoul");
		$date_ = DATE("Y-m-d-H-i D");
		$date = (String) $date_;
		
		if ( $room_name == ""){
			$room_name = "재밌는 게임 해요!";
		}

		$query = "insert into room_info (room_name,room_status,del_status,today) values ('$room_name','wait','live','$date')";
		echo $query;
		$result = mysqli_query($connect,$query);
	}
	else if ($choice == 2){
		$query = "select * from room_info where room_status = 'wait' and del_status = 'live'";
		// echo $query;
		$result = mysqli_query($connect, $query);
		$count = mysqli_num_rows($result);
		if ($count == 0){
			echo "nothing";
			exit;
		}
		$arr = array();
		for ($i = 0; $i < $count; $i++) {
			$data = mysqli_fetch_assoc($result)or die("추출실패");
			array_push($arr, $data);
		}
		$json = json_encode($arr);
		echo $json;
	}


	

?>