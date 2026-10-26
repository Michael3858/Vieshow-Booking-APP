<?php
require __DIR__ . '/conn.php';

connect();

$acc = $_POST["acc"];
$datas = array();
$mysql_qry = "SELECT Booking_Time, State, Theater_Name, Movie_Name, Screenings_Time, Total_Price, Ticket_ID FROM booking_record, screenings, movie, theater, auditorium WHERE booking_record.Screenings_ID = screenings.Screenings_ID AND screenings.Movie_ID = movie.Movie_ID AND screenings.Auditorium_ID = auditorium.Auditorium_ID AND theater.Theater_ID = auditorium.Theater_ID AND Booking_Payment = '線上付款' AND User_Email = '$acc' order by Booking_Time desc;";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>