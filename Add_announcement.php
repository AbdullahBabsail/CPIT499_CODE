<?php

include("config.php");

date_default_timezone_set("Asia/Muscat");
$RegDate= date("Y/m/d - h:i a");

$Activity_Type = strip_tags(trim($_POST["Activity_Type"]));
$Activity_Time = strip_tags(trim($_POST["Activity_Time"]));
$Activity_Date = strip_tags(trim($_POST["Activity_Date"]));
$Activity_Details = strip_tags(trim($_POST["Activity_Details"]));
$Activity_Location = strip_tags(trim($_POST["Activity_Location"]));
$Activity_Owner = strip_tags(trim($_POST["Activity_Owner"]));


if ($Activity_Type<>"" && $Activity_Time<>"" && $Activity_Details<>"" && $Activity_Location<>"" && $Activity_Owner<>""){

  $sql_query = "INSERT INTO announcement (Type,Time,Date_Added,Date_Activity,Location,Details,Owner)VALUES('$Activity_Type','$Activity_Time','$RegDate','$Activity_Date','$Activity_Location','$Activity_Details','$Activity_Owner')";
	$dbResult = $conn->query($sql_query);
	if ($dbResult === TRUE) { 
		echo "Announcement Created";
		} else {
		 echo"Sorry Something Went Wrong";
		}
	}else{
        echo"Sorry We Are Out Of Servies";
    }


?>
