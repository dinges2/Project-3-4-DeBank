<?php

require_once '../../Private/database.php';


error_reporting(E_ALL);
ini_set('display_errors', 1);

$con = db_connect();
$randoms = $_GET['reknr'];

$query = mysqli_query($con, "SELECT SALDO FROM REKENINGEN WHERE REKENINGNUMMER = '" . $randoms . "'");


header('Content-Type: application/json');
echo json_encode([
    'success' => true ,
    'saldo' => mysqli_fetch_all($query, MYSQLI_ASSOC)
    
]);
