<?php

$name=$_POST["name"];
$status="1";

$servername = "localhost";
$username = "id10666014_pratik";
$password = "UU]edlgb4uB-ZIcr";
$dbname = "id10666014_pratik_api";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

// Check connection
if ($conn->connect_error) 
{
    die("Connection failed: " . $conn->connect_error);
}

$sql = "INSERT INTO `indus_university_user`(`name`, `status`) VALUES ('".$name."','".$status."')";

$output = array();

if ($result) 
{
	$output[] = array("msg"=>"Successfully","type"=>"insert");
} 
else 
{
    $output[]= array("msg"=>"Error Occur","type"=>"insert");
}
$conn->close();
echo json_encode($output);


?>