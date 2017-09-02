<?php 

	session_start();
	include('config.php');

	$name = isset($_GET['name']) ? $_GET['name'] : "";
	$id = isset($_GET['id']) ? $_GET['id'] : "";
	$pwd = isset($_GET['pwd']) ? $_GET['pwd'] : "";
	$phone = isset($_GET['phone']) ? $_GET['phone'] : "";
	$sex = isset($_GET['sex']) ? $_GET['sex'] : "";
	$choice = $_GET['choice'];

	if ($sex == 'male') {
		$int_sex = 1;
	}
	else if ($sex == 'female'){
		$int_sex = 2;
	}
	if ($choice == 1){
		$query = "insert into user_list(name,id,pwd,email,sex) values ('$name','$id','$pwd','$phone',$int_sex)";
		$result = mysqli_query($connect,$query)or die ("입력 실패");
	}
	else if ($choice == 4){
		$phone = "facebook";
		$row_query = "select * from user_list where id = '$id'";
		$row_result = mysqli_query($connect,$row_query);
		$total_row = mysqli_num_rows($row_result);
		if ($total_row == 0) {
			$query = "insert into user_list(name,id,pwd,email,sex) values ('$name','$id','$pwd','$phone',$int_sex)";
			$result = mysqli_query($connect,$query)or die ("입력 실패");
		}
		$row_query = "select * from user_list where id = '$id'";
		//echo $row_query;
		$row_result = mysqli_query($connect,$row_query);
		$row_ = mysqli_fetch_array($row_result);
		$iden = $row_['iden'];
		echo $iden;
	}
	else if ($choice == 1 && $_SESSION['foo'] == false){
		echo "인증체크 해주세요";
	}
	else if ($choice == 2){
		$query = "SELECT * FROM user_list";
		$result = mysqli_query($connect,$query)or die ("입력 실패");

	}
	else if ($choice == 3){
		$query = "select * from user_list where id ='$id'";
		$result = mysqli_query($connect,$query)or die ("입력 실패");
		$total_row = mysqli_num_rows($result);
		//echo $total_row;
		if ($total_row == 0 && $choice == 3) {
			$overlap_test = "아이디를 쓸 수 있습니다.";
			//echo $row['id']."||";
			echo $overlap_test;
		}
		else if ($total_row > 0 && $choice == 3){
			$overlap_test = "아이디를 쓸 수 없습니다.";
			echo $overlap_test;
		}
	} 

 ?>