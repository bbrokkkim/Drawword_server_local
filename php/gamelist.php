<?php 
	include_once("config.php");

	$choice = $_POST['choice'];
	$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : "";
	$room_name = isset($_POST['room_name']) ? $_POST['room_name'] : "";


	if ($choice == 1){
		$query = "insert into room_info (room_num,room_status,today,del_status) values ('$room_name','wait','','live')";
	}
	//투데이 만들기1


?>