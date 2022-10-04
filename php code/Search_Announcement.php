<?php

include("config.php");


$Activity_Type = strip_tags(trim($_POST["Activity_Type"]));
$Activity_Time = strip_tags(trim($_POST["Activity_Time"]));

if($Activity_Type<>"" && $Activity_Time<>""){

    $sql="SELECT * FROM announcement WHERE Type LIKE '$Activity_Type' AND Time LIKE '$Activity_Time'";

    $result=mysqli_query($conn,$sql);
    
    $announcements=array();
    $announcements['data']=array();
    
    
    while ($row = mysqli_fetch_array($result)){
       
        $index['Type']=$row['Type'];
        $index['Time']=$row['Time'];
        $index['Date']=$row['Date_Activity'];
        $index['Details']=$row['Details'];
        $index['Location']=$row['Location'];
        $index['Owner']=$row['Owner'];
        array_push($announcements ['data'],$index);
    }
    $announcements ['success']= "good";
    
    echo json_encode($announcements);
    
    mysqli_close($conn);
    
}else{
    $announcements ['success']= "bad";
}
