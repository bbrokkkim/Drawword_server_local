<?php 
	ini_set('display_errors', '1');
	//session_start();
	include('config.php');

	$id = $_GET['id'];
	$pwd = $_GET['pwd'];
	$auto = isset($_GET['auto']) ? $_GET['auto']: "";
	$query = "select * from user_list where id = '$id'";	
	$result = mysqli_query($connect,$query)or die ("mysql 입력 실패");
	$data = mysqli_fetch_array($result);
	$token = rand(1000, 9999);
	//echo $data[pwd];

	if ($pwd != $data['pwd'] || $data['pwd'] == "" ){
		$user_table = array();
		$test = array('iden' => "wrong");
		array_push($user_table,$test);		
		$test = array('id' => "wrong");
		array_push($user_table,$test);
		$test = array('token' => "wrong");
		array_push($user_table,$test);
		$json = json_encode($user_table);
		echo $json;
	}
	else if ($pwd == $data['pwd']){
		$user_info = array();
		$server_iden = $data['iden'];

		session_start();
		$_SESSION['id'] = $id;
		$_SESSION['token'] = $token;
		$user_table = array();


		$test_iden = array('iden' => $data['iden']);
		array_push($user_table, $test_iden);
		$test = array('id' => $data['id']);
		array_push($user_table, $test);
		$test = array('token' => "$token"); 
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