<?php

    define("PRIVATE_PATH", dirname(__FILE__));
    define("PROJECT_PATH", dirname(PRIVATE_PATH));
    define("PUBLIC_PATH", PROJECT_PATH . '/public');
    define("SHARED_PATH", PRIVATE_PATH . '/shared');

define("WWW_ROOT", '/getbank/Public/');

    /* $public_end = strpos($_SERVER['SCRIPT_NAME'], '/public') + 7;
    $doc_root = substr($_SERVER['SCRIPT_NAME'], 0, $public_end);
    define("WWW_ROOT", $doc_root);
    Dit is een automatische 'rootfinder' maar die werkt niet met xampp.*/

    require_once('functions.php');
    require_once('database.php');

    $db = db_connect();





?>
