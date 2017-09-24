<?php 

	ini_set('display_errors', '1');
	$host = "mariadb.ceqw0wwolo9b.ap-northeast-2.rds.amazonaws.com";
	$user = "root";
	$password = "KKKKKKKK";
	$database = "drawword";
	$connect = mysqli_connect($host,$user,$password,$database) or die("mysql 연결 실패");
	
?>