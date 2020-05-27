<?php

require_once '../../Private/database.php';


error_reporting(E_ALL);
ini_set('display_errors', 1);

$con = db_connect();
$randoms = $_GET['reknr'];

$query = mysqli_query($con, "SELECT INLOGPOGINGEN FROM REKENINGEN WHERE REKENINGNUMMER = '" . $randoms . "'");


header('Content-Type: application/json');
echo json_encode([
    'success' => true ,
    'inlogpoging' => mysqli_fetch_all($query, MYSQLI_ASSOC)

    //'resultaat' => $_GET['saldo'] , 'resultaat 2' => $_GET['name']
]);
