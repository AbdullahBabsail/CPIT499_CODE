<?php

include("config.php");

$UserName = strip_tags(trim($_POST["UserName"]));
$PassWord = strip_tags(trim($_POST["PassWord"]));

if ($UserName <> "" && $PassWord <> "") {
       
        
$md5Pass = md5($PassWord); 
$check = array();
$check ['data']=array();
$sql_UsersLogIn = "SELECT * FROM user WHERE Username LIKE '$UserName' AND Password LIKE '$md5Pass' LIMIT 1";

$uLogInCheck = $conn->query($sql_UsersLogIn);    
    if ($uLogInCheck->num_rows > 0) {  
        $row = mysqli_fetch_assoc($uLogInCheck);
        $ds['username']=$row['Username'];
        $ds['email']=$row ['Email'];
        $ds['fullname']=$row ['FullName'];
        $ds['phone']=$row ['PhoneNumber'];


        array_push($check ['data'],$ds);
        $check ['success']= "LogIn_OK";
        echo json_encode($check);
        mysqli_close($conn);

    }else{
        $check ['success']= "LogIn_Error";
        echo json_encode($check);
        mysqli_close($conn);
    } 
} else{

    $check ['success']= "LogIn_Error";
        echo json_encode($check);
        mysqli_close($conn);

} 

?> 

