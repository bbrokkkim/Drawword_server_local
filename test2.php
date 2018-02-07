<?php 

	ini_set('display_errors', '1');
	ini_set('max_execution_time', 3000);
	include_once("/var/www/html/dom_parser.php");

	$type_lotte = isset($_GET['type'])?$_GET['type']:"";

	$link='http://www.lottecinema.co.kr/LCWS/Cinema/CinemaData.aspx?nocashe=0.2995354485150339';

	$param = '{"MethodName":"GetCinemaByArea","channelType":"HO","osType":"Chrome","osVersion":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36","multiLanguageID":"KR","divisionCode":"1","detailDivisionCode":"2"}';
// {"MethodName":"GetMoviePlayDates","channelType":"HO","osType":"Chrome","osVersion":"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36","multiLanguageID":"KR"}'
	$data = array('paramList' => $param);
	$request = array(
		'http' => array(
			'header' => 'Content-Type:application/x-www-form-urlencoded',
		    'method' => 'POST',
		    'content' => http_build_query($data)
		)
	);


	$ar_day = array();

	$context = stream_context_create($request);

	$html = file_get_html($link, false, $context);

	
	$day_cut = explode('"PlayDate":"', $html);

	$day_cnt = count($day_cut);
	
	$ar_month = array();
	$ar_date = array();
	for ($i=1; $i < $day_cnt; $i++) { 
		$day = explode('","IsPlayDate":"', $day_cut[$i]);
		$month = substr($day[0], 5,2);
		$date = substr($day[0], 8,2);
		// echo $month."/".$date."<br>";
		// echo $day[0]."<br>";
		array_push($ar_day, $day[0]);
		array_push($ar_month,$month);
		array_push($ar_date,$date);
		//echo $ar_day[$i-1]."<br>";
	}
	
	

	$link = 'http://www.lottecinema.co.kr/LCHS/Contents/Cinema/Cinema-Detail.aspx?divisionCode=1&detailDivisionCode=1&cinemaID=1013';
	$html = file_get_html($link);
	$div = $html->find('div.gnb div.depth',2);
	$fr_local = $div->find('div.depth_03 ul li a');
	$fr_local_name = $div->find('div.depth_03 ul li a');
	$ar_local_name = array();
	$ar_local_code = array();
	$asd = 0;
	$local_cnt = count($fr_local_name);

	foreach ($fr_local_name as $key) {
		//echo $key."<br>asdasda";
		

		//echo $key->plaintext."<br>";
		//echo "string";
		if ($asd > 7){
			array_push($ar_local_name,$key->plaintext);
		}
		$asd = $asd + 1;

	}
	$movie_code_name = explode('cinemaID',$fr_local_name[1]);
	$movie_code = explode('cinemaID',$fr_local[1]);
	/*foreach ($movie_code as $key) {
		echo $key;
	}*/
	/*for ($o=0; $o < $local_cnt ; $o++) { 

		echo $ar_movie_name[$o]."<br>";
	}*/

	$div_dep = $html->find('div.depth_03');
	$asd = count($div_dep);
	
//	$movie_code = explode('cinemaID=',$div_dep[2]);

	//$cnt = count($movie_code);
	$ar_local_code = array();
	for ($p=1; $p < $asd; $p++) { 
		//echo $div_dep[$p]."<br>";
		
		$movie_code = explode('cinemaID=',$div_dep[$p]);
		$sdf = count($movie_code);
		
		
		for ($q=1; $q < $sdf; $q++) { 
			//echo $movie_code[$q]."<br>asdasd<br>";
			$movie_code_as = explode('" title="', $movie_code[$q]);
			array_push($ar_local_code,$movie_code_as[0]);
			//echo $q."|||||".$movie_code_as[0]."<br>";
				
			
		}
				
	}
	$cnt = count($ar_local_code);
	$local = array();
	for($r = 0 ; $r < $cnt-1 ; $r++){
		//echo ($i)." ";
		$test = array('local' => $ar_local_name[$r]);
		array_push($local, $test);
	//echo($ar_local_code[$r] ."||".$ar_local_name[$r]."<br>-----------------<br>");
		
	}
	if ($type_lotte == 1) {
		$json_lotte = json_encode($local);
		echo $json_lotte;
		// echo "string";
	}
?>