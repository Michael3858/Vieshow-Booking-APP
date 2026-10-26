<?php
require __DIR__ . '/conn.php';

connect();

$ID = $_POST["ID"];
$datas = array();
$mysql_qry = "SELECT booking_record.Ticket_ID, Movie_Name, Movie_EngName, Movie_Series, Screenings_Time, Theater_Name, Auditorium_Name, Seat, Type_Name, booking_ticket_type.Num as num1, Meal_Name, booking_meals.Num as num2, Total_Price, Serial_Number FROM (booking_record LEFT JOIN (booking_meals LEFT JOIN meals ON booking_meals.Meal_ID = meals.Meal_ID) ON booking_record.Ticket_ID = booking_meals.Ticket_ID), screenings, movie, theater, auditorium, booking_seat, booking_ticket_type, ticket_type WHERE booking_record.Screenings_ID = screenings.Screenings_ID AND screenings.Movie_ID = movie.Movie_ID AND screenings.Auditorium_ID = auditorium.Auditorium_ID AND theater.Theater_ID = auditorium.Theater_ID AND booking_record.Ticket_ID = booking_seat.Ticket_ID AND booking_record.Ticket_ID = booking_ticket_type.Ticket_ID AND booking_ticket_type.Type_ID = ticket_type.Type_ID AND booking_record.Ticket_ID = '$ID';";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>