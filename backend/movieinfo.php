<?php
require __DIR__ . '/conn.php';

connect();

$ID = $_POST["ID"];
$datas = array();
$mysql_qry = "SELECT Movie_Name, Movie_EngName, Movie_Series, Screenings_Time, Theater_Name, Auditorium_Name, screenings.Auditorium_ID FROM screenings, movie, theater, auditorium WHERE screenings.Movie_ID = movie.Movie_ID AND screenings.Auditorium_ID = auditorium.Auditorium_ID AND theater.Theater_ID = auditorium.Theater_ID AND screenings.Screenings_ID = '$ID';";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>