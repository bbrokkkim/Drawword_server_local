<?php 
	ini_set('display_errors', '1');
	include_once("config.php");
	
		$choice = $_POST['choice'];
		$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
		$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : "";
		$room_name = isset($_POST['room_name']) ? $_POST['room_name'] : "";
		$limit = isset($_POST['limit']) ? $_POST['limit'] : "";
		if ( $room_name == ""){
			$room_name = "재밌는 게임 해요!";
		}


		


	//방 생성
	if ($choice == 1){
		date_default_timezone_set("Asia/Seoul");
		$date_ = DATE("Y-m-d-H-i D");
		$date = (String) $date_;
		
		$query = "insert into room_info (room_name,room_status,del_status,today) values ('$room_name','wait','live','$date')";
		$result = mysqli_query($connect,$query);
		$query = "select * from room_info order by iden desc limit 1";
		$result = mysqli_query($connect,$query);
		$data = mysqli_fetch_array($result);
		echo $data['iden'];
	}
	//방 새로고침
	else if ($choice == 0){
		//40개씩 가지고 온다.
		$highlimit = $limit + 40;
		$query = "select * from room_info where del_status = 'live' and room_status = 'wait' order by iden desc limit 40";
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
	//방 가지고 오기
	else if ($choice == 2){
		//40개씩 가지고 온다.
		$highlimit = $limit + 40;
		$query = "select * from room_info where del_status = 'live' and room_status = 'wait' order by iden desc limit $limit , $highlimit";
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
	//방 유무 여부
	else if ($choice == 3){
		$room_num = isset($_POST['room_num']) ? $_POST['room_num'] : "";	
		$query = "select * from room_info where iden = $room_num and room_status = 'wait' order by iden  desc";
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
	//페이징 이용한 다음 방 확인
	else if ($choice == 4){

		$query = "select * from room_info where del_status = 'live' and room_status = 'wait' and iden < $limit order by iden desc limit 0 ,40;
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