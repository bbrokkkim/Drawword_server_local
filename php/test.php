<?php 
include_once ("config.php");

// 	for ($i = 3; $i < 10 ; $i++) {
// 		$st = (String)$i;
// 		$as = (String) ($i + 75) ;
// 		$asdf = "kkk".$st;
// 		$query = "insert into friend_list (my_iden,my_id,friend_iden,friend_id) values('56','test1','$as','$asdf')";
// 		echo $query;
// 		$result = mysqli_query($connect,$query)or die ("입력 실패");

		
// 	}
// 	
	$room_n = "testroom";
	$room_name;
	$date = "20";
 	for ($i = 100; $i < 200; $i++) {
 		$room_name = $room_n.$i;
		$query = "insert into room_info (room_name,room_status,del_status,today) values ('$room_name','wait','live','$date')";
		echo $query;
		$result = mysqli_query($connect,$query);
 		
 	}
	

 ?>