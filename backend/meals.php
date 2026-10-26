<?php
require __DIR__ . '/conn.php';

connect();

$ID = $_POST["ID"];
$datas = array();
$mysql_qry = "SELECT DISTINCT meals.Meal_Classify, meals.Meal_Name, meals.Meal_Price FROM screenings, auditorium, theater_meals, meals WHERE screenings.Auditorium_ID = auditorium.Auditorium_ID AND theater_meals.Theater_ID = auditorium.Theater_ID AND theater_meals.Meal_ID = meals.Meal_ID AND screenings.Screenings_ID = '$ID' ORDER BY meals.Meal_Classify;";
$result = mysqli_query($con, $mysql_qry);
while ($row = mysqli_fetch_assoc($result)) {
       $datas[] = $row;
}	

print(json_encode($datas,JSON_UNESCAPED_UNICODE));
close();

?>