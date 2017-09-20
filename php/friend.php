<?php 
	ini_set('display_errors', '1');
	session_start();
	session_cache_expire(14400);
	include_once("config.php");
	$choice = $_POST['choice'];
	$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : "";
	$friend_iden = isset($_POST['friend_iden']) ? $_POST['friend_iden'] : "";
	$friend_id = isset($_POST['friend_id']) ? $_POST['friend_id'] : "";
	$token = isset($_POST['token']) ? $_POST['token'] : "";
	if (!$token == $_SESSION['token']){
		echo "problem";
	}

	if ($choice == 1){
		$query = "select friend.my_iden, list.iden, list.id, list.photo_uri, list.ment from user_list as list join friend_list as friend ON friend.friend_iden = list.iden where my_iden = '$user_iden'";
/*		echo $query;
		*/

		$result = mysqli_query($connect,$query)or die ("입력 실패");
		// $data = mysqli_fetch_array($result);
		$arr = array();
		$count = mysqli_num_rows($result);
		for ($i=0; $i < $count; $i++) { 
		$data = mysqli_fetch_assoc($result)or die("추출실패");
		array_push($arr, $data);

		}

		$json = json_encode($arr);
		echo $json;
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