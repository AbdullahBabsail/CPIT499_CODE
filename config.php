
<?php

$servername = "127.0.0.1";
$username = "root";
$password = "";
$dbname = "pwu"; 


$conn = mysqli_connect($servername, $username, $password, $dbname);

if (!$conn) {
    die("Connection failed: " . mysqli_connect_error());
    //echo "Connection Estabilished";
}
//else{
    //echo"Connection Error";

//}


mysqli_set_charset($conn,"utf8");
header('Content-Type: text/html; charset=utf-8');

?>