<?php

include("config.php");

$RegDate= date("Y/m/d - h:i a");

$sql="SELECT * FROM announcement";

$result=mysqli_query($conn,$sql);

$announcements=array();


while ($row = mysqli_fetch_array($result)){
   
    $index['Type']=$row['Type'];
    $index['Time']=$row['Time'];
    $index['Date']=$row['Date_Activity'];
    $index['Details']=$row['Details'];
    $index['Location']=$row['Location'];
    $index['Owner']=$row['Owner'];
    array_push($announcements,$index);


}

echo json_encode($announcements);

mysqli_close($conn);


?>
