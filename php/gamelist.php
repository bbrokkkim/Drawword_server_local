<?php 
ini_set('display_errors', '1');
	include_once("config.php");

	$choice = $_POST['choice'];
	$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : "";
	$room_name = isset($_POST['room_name']) ? $_POST['room_name'] : "";
	$limit = isset($_POST['limit']) ? $_POST['limit'] : "";


	//방 생성
	if ($choice == 1){
		date_default_timezone_set("Asia/Seoul");
		$date_ = DATE("Y-m-d-H-i D");
		$date = (String) $date_;
		
		if ( $room_name == ""){
			$room_name = "재밌는 게임 해요!";
		}

		$query = "insert into room_info (room_name,room_status,del_status,today) values ('$room_name','wait','live','$date')";
		$result = mysqli_query($connect,$query);
		$query = "select * from room_info order by iden desc limit 1";
		$result = mysqli_query($connect,$query);
		$data = mysqli_fetch_array($result);
		echo $data['iden'];
	}
	else if ($choice == 2){
		$highlimit = $limit + 20;
		$query = "select * from room_info where del_status = 'live' limit $limit , $highlimit";
		// echo $query;
		$result = mysqli_query($connect, $query);
		$count = mysqli_num_rows($result);
		// echo $count . "||||";
		if ($count == 0){
			echo "nothing";
			exit;
		}
		$arr = array();
		for ($i = 0; $i < $count; $i++) {
			$data = mysqli_fetch_assoc($result);
			array_push($arr, $data);
		}
		$json = json_encode($arr);
		echo $json;
	}
	else if ($choice == 3){
		$room_num = isset($_POST['room_num']) ? $_POST['room_num'] : "";	
		$query = "select * from room_info where iden = $room_num and room_status = 'wait' ";
		$result = mysqli_query($connect, $query);
		$count = mysqli_num_rows($result);
		// echo $query;
		if ($count == 0){
			echo "nothing";
		}
		else {
			echo "pass";
		}
	}
	else if ($choice == 4){

		$query = "select * from room_info where del_status = 'live' and iden > $limit limit 0 ,20;
		";
		// echo $query;
		$result = mysqli_query($connect, $query);
		$count = mysqli_num_rows($result);
		// echo $count . "||||";
		if ($count == 0){
			echo "nothing";
			exit;
		}
		$arr = array();
		for ($i = 0; $i < $count; $i++) {
			$data = mysqli_fetch_assoc($result);
			array_push($arr, $data);
		}
		$json = json_encode($arr);
		echo $json;

	}
?>