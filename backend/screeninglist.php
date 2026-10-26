<?php
require __DIR__ . '/conn.php';

connect();

$ID = $_POST["ID"];
$date1 = $_POST["date1"];
$date2 = $_POST["date2"];
$datas = array();
$mysql_qry = "SELECT movie.Movie_ID, Movie_Name, Screenings_Time FROM movie, screenings, auditorium WHERE movie.Movie_ID = screenings.Movie_ID AND screenings.Auditorium_ID = auditorium.Auditorium_ID AND Theater_ID = '$ID' and (Screenings_Time LIKE '$date1%' or Screenings_Time LIKE '$date2%') ORDER BY movie.Movie_ID, Screenings_Time ASC;";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>