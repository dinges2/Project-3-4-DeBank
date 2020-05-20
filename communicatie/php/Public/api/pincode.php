<?php

require_once '../../Private/database.php';


error_reporting(E_ALL);
ini_set('display_errors', 1);

$con = db_connect();
$randoms = $_GET['reknr'];
$pincode = $_GET['pincode'];

$query = mysqli_query($con, "SELECT REKENINGNUMMER, PINCODE FROM REKENINGEN WHERE REKENINGNUMMER = '" . $randoms . "' AND PINCODE = '" . $pincode . "' ");


header('Content-Type: application/json');
echo json_encode([
    'success' => true ,
    'rekeningnummer' => mysqli_fetch_all($query, MYSQLI_ASSOC)
    //'resultaat' => $_GET['saldo'] , 'resultaat 2' => $_GET['name']
]);
