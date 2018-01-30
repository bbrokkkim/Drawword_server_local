<?php 

	ini_set('display_errors', '1');
	include_once("config.php");
	define("GOOGLE_API_KEY", 'AAAAT0KVr5U:APA91bGsfxi1Y_-vR9xkUA_C3xBzbqpsnYGuP-Zc9WnPgvq1kCP__RV50Ig-rw_80L5xvC1zfnvkx1BRMXLyLZdkqTf574AoNuFgmt-VCIq3uSX5BsgdISUFFyV8yjVdGILl_xHB3g1K'); 
	$user_iden = isset($_POST['user_iden']) ? $_POST['user_iden'] : "";
	$user_name = isset($_POST['user_name']) ? $_POST['user_name'] : "";
	$token = isset($_POST['token']) ? $_POST['token'] : "";
	$choice = isset($_POST['choice']) ? $_POST['choice'] : "";
	echo $user_iden;
	echo $user_name;
	echo $token;
	
	//토큰 지우기
	if ($choice == "2"){
		$query = "delete from users where user_iden = '$user_iden'";
		$result = mysqli_query($connect,$query)or die ("입력 실패");
		echo $query;
	}

	//토큰 입력
	else if (!$user_iden == ""){
		$query = "select * from users where user_iden = '$user_iden'";
		echo $query . "<br>";
		$result = mysqli_query($connect,$query)or die ("입력 실패");
		$count = mysqli_num_rows($result);
		if ($count == 0){
			$query = "insert into users (Token, user_iden,user_name) values ('$token','$user_iden','$user_name')";
			echo $query;
			$result = mysqli_query($connect,$query)or die ("입력 실패");
		}
		else {
			$query = "delete from where user_iden = '$user_iden'";
			$result = mysqli_query($connect,$query)or die ("입력 실패");	
			$query = "insert into users (Token, user_iden,user_name) values ('$token','$user_iden','$user_name')";
			echo $query;
			$result = mysqli_query($connect,$query)or die ("입력 실패");
		}
	}
		// $data = mysqli_fetch_array($result);
		

?>