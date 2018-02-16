<?php 
	ini_set('display_errors', '1');
	session_start();
	include_once("config.php");
	$choice = $_POST['choice'];
	$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
	$user_id = isset($_POST['user_id']) ? $_POST['user_id'] : "";
	$friend_iden = isset($_POST['friend_iden']) ? $_POST['friend_iden'] : "";
	$friend_id = isset($_POST['friend_id']) ? $_POST['friend_id'] : "";
	$token = isset($_POST['token']) ? $_POST['token'] : "";

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
	//친구추가
	else if ($choice == 2){
		//친구 아이디 유무 판단
		$query = "select * from friend_list where my_iden ='$user_iden' and friend_id = '$friend_id'";
		$result = mysqli_query($connect,$query);
		$count_check = mysqli_num_rows($result);
		$query = "select * from user_list where id = '$friend_id'";
		$result = mysqli_query($connect,$query);
		$row = mysqli_fetch_array($result);
		$friend_iden = $row['iden'];
		$count = mysqli_num_rows($result);
		if ($count == 0){
			echo "cantfind";
			exit();
		}
		else if ($count_check != 0){
			echo "already";
			exit();
		}
		else {
			$query = "insert into friend_list (my_iden,my_id,friend_iden,friend_id,check_friend) values ('$user_iden','$user_id','$friend_iden','$friend_id','0')";
			// echo $query . "    ||    ";
			$result = mysqli_query($connect,$query);
			$query = "select friend.my_iden, list.iden, list.id, list.photo_uri, list.ment,list.rotate from user_list as list join friend_list as friend ON friend.friend_iden = list.iden where my_iden = '$user_iden' order by list.id asc";
			// echo $query;
			$result = mysqli_query($connect,$query);
			$count = mysqli_num_rows($result);
			// echo $query;

			$arr = array();	
			for ($i=0; $i < $count; $i++) { 
				$data = mysqli_fetch_assoc($result)or die("추출실패~~");
				array_push($arr, $data);

			}	

			$json = json_encode($arr);
			// echo $friend_iden ." || ". $friend_id;
			
			echo $json;
		}
	}
	else if ($choice == 3){
		$query = "delete from friend_list where my_iden = '$user_iden' and friend_iden = '$friend_iden'";
		$result = mysqli_query($connect,$query);
		// echo $query;
		$query = "select friend.my_iden, list.iden, list.id, list.photo_uri, list.ment, list.rotate from user_list as list join friend_list as friend ON friend.friend_iden = list.iden where my_iden = '$user_iden' order by list.id asc";
		$result = mysqli_query($connect,$query);
		
		$count = mysqli_num_rows($result);
			// echo $query;
		$arr = array();	
		for ($i=0; $i < $count; $i++) { 
			$data = mysqli_fetch_assoc($result)or die("추출실패~~");
			array_push($arr, $data);

		}	
		$json = json_encode($arr);
		echo $json;
	}
	else if ($choice== 4){
		$query = "select * from friend_list where friend_iden = '$user_iden' and check_friend = '0'";
		// echo $query;
		$result = mysqli_query($connect,$query);
		$count = mysqli_num_rows($result);
		if ($count == 0){
			echo "nothing";
			exit;
		}
		else {
			$arr = array();	
			for ($i = 0; $i < $count; $i++) {
				$data = mysqli_fetch_assoc($result)or die("추출실패~~");
				array_push($arr, $data);
			}	
			$json = json_encode($arr);
			echo $json;
		}
	}
	else if ($choice == 5){
		//친구 아이디 유무 판단
		$type = $_POST['type'];
		$query = "select * from user_list where id = '$friend_id'";
		// echo $query;
		$result = mysqli_query($connect,$query);
		$row = mysqli_fetch_array($result);
		$friend_iden = $row['iden'];

		if ($type == 1){
			$query = "insert into friend_list (my_iden,my_id,friend_iden,friend_id,check_friend) values ('$user_iden','$user_id','$friend_iden','$friend_id','1')";
			$result = mysqli_query($connect,$query);
		}
		$query = "update friend_list set check_friend = '1' where my_iden = '$friend_iden'";
		$result = mysqli_query($connect,$query);
		
	}

?>