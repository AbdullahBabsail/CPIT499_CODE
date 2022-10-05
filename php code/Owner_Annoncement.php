<?php

include("config.php");


$UserName = strip_tags(trim($_POST["User_name"]));


if($UserName<>""){

    $sql="SELECT * FROM announcement WHERE Owner LIKE '$UserName'"; 

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
