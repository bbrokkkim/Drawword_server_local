<?php 
	include_once("config.php")
	$choice = $_POST['choice'];
	$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : "";
	$friend_iden = isset($_POST['friend_iden']) ? $_POST['friend_iden'] : "";
	$friend_id = isset($_POST['friend_id']) ? $_POST['friend_id'] : "";

	if ($choice == 1){
		$query = "select friend_iden,friend_id from friend_list where my_iden = '$user_iden'";
		$result = mysqli_query($query,$connect);
		$data = mysqli_fetch_array($result);
	}
	else if ($choice == 2){
		$query = "insert into friend_list (my_iden,my_id,friend_iden,friend_id) values('$user_iden','$user_id','friend_iden','friend_id')";
		$result = mysqli_query($query);
		$data = mysqli_fetch_array($result);
	}
	else if ($choice == 3){
		echo "error";
	}
?>