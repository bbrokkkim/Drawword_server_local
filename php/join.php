<?php 
	session_start();
	include('config.php');

	$name = isset($_POST['name']) ? $_POST['name'] : "";
	$id = isset($_POST['id']) ? $_POST['id'] : "";
	$pwd = isset($_POST['pwd']) ? $_POST['pwd'] : "";
	$phone = isset($_POST['phone']) ? $_POST['phone'] : "";
	$sex = isset($_POST['sex']) ? $_POST['sex'] : "";
	$photo_uri = isset($_POST['photo_uri']) ? $_POST['photo_uri'] : "";
	$rotate = isset($_POST['rotate']) ? $_POST['rotate'] : "";
	$choice = $_POST['choice'];
	$uri = $id.".jpg";



	if ($sex == 'male') {
		$int_sex = 1; 
	}
	else if ($sex == 'female'){
		$int_sex = 2;
	}
	//사진
	if ($choice == 1){
		// echo $photo_uri;
		// echo $choice;
		if ($photo_uri != "null"){
			$file_path = "/var/www/html/user_photo/";
	     	// echo $_FILES['uploadedfile']['name'];
	    	$file_path = $file_path . basename( $_FILES['uploadedfile']['name']);
	    	// echo $file_path;
	    	$photo_uri = $file_path;
	    	if(move_uploaded_file($_FILES['uploadedfile']['tmp_name'], ($file_path))) {
		        // echo "success";
		    } 
		    else{
		    	// echo $_FILES['uploadedfile']['error'];
		    	// echo "fail";
		    }
		    $photo_uri = $uri;
		}
		$token = rand(10000000, 99999999);

		$query = "select * from user_list where id = '$id'";
		$result = mysqli_query($connect,$query)or die ("입력 실패");
		$total_row = mysqli_num_rows($result);
		if (!$total_row == 0) {
			echo "overlap";
			exit;	
		}

		$query = "insert into user_list(name,id,pwd,phone,sex,photo_uri,rotate) values ('$name','$id','$pwd','$phone',$int_sex,'$photo_uri','$rotate')";
		$result = mysqli_query($connect,$query)or die ("입력 실패");
		$query = "select * from user_list where id  ='$id'";
		$result = mysqli_query($connect,$query);
		$row = mysqli_fetch_array($result);
		$user_table = array();
		$test = array('iden' => $row['iden'],'id' => $id,'token' => $token, 'photo_uri' => $photo_uri, 'rotate' => $rotate);
		array_push($user_table, $test);
		$json = json_encode($user_table);
		echo $json;
		
	}

	else if ($choice == 4){
		$phone = "facebook";
		$row_query = "select * from user_list where id = '$id'";
		$row_result = mysqli_query($connect,$row_query);
		$total_row = mysqli_num_rows($row_result);
		if ($total_row == 0) {
			
			echo $uri;
			$query = "insert into user_list(name,id,pwd,phone,sex,photo_uri) values ('$name','$id','$pwd','$phone',$int_sex,'$uri')";
			$result = mysqli_query($connect,$query)or die ("입력 실패");
		}
		$row_query = "select * from user_list where id = '$id'";
		//echo $row_query;
		$row_result = mysqli_query($connect,$row_query);
		$row_ = mysqli_fetch_array($row_result);
		$iden = $row_['iden'];
		
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

	else if ($choice == 5){
		echo "회원가입";
	}
	else if ($choice == 6){
		echo "로그인";
	}

?>