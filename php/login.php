<?php 
	ini_set('display_errors', '1');
	//session_start();
	include('config.php');

	


	$id = $_POST['id'];
	$pwd = $_POST['pwd'];
	$auto = isset($_POST['auto']) ? $_POST['auto']: "";
	$query = "select * from user_list where id = '$id'";	
	$result = mysqli_query($connect,$query)or die ("mysql 입력 실패");
	$data = mysqli_fetch_array($result);
	$token = rand(10000000, 99999999);
	//echo $data[pwd];

	if ($pwd != $data['pwd'] || $data['pwd'] == "" ){
		$user_table = array();
		$test = array('iden' => "wrong" , 'id' => "wrong" , 'token' => "wrong" ,'photo_uri' => "wrong", 'rotate' => "wrong");
		array_push($user_table,$test);		
		$json = json_encode($user_table);
		echo $json;
	}
	else if ($pwd == $data['pwd']){
		$user_info = array();
		$server_iden = $data['iden'];
		$photo_uri = $data['photo_uri'];
		session_start();
		session_cache_expire(14400);
		$_SESSION['id'] = $id;
		$_SESSION['token'] = $token;
		$user_table = array();
		$row_query = "select * from user_list where id = '$id'";
		$row_result = mysqli_query($connect,$row_query);
		$row = mysqli_fetch_array($row_result);
		$rotate = $row['rotate'];	
		$test = array('iden' => $data['iden'], 'id' => $data['id'] , 'token' => $token, 'photo_uri' => $photo_uri, 'rotate' => $rotate);
		array_push($user_table, $test);
		
		$json = json_encode($user_table);
		echo $json;
	}
	else if ($auto = 1){
		session_start();
		$_SESSION['id'] = $id;
		$_SESSION{'token'}= $token;
	}
?>