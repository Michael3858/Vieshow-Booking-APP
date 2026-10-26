<?php
function database_setup_error($code) {
    http_response_code(503);
    header('Content-Type: application/json; charset=utf-8');
    echo json_encode(array('error' => $code), JSON_UNESCAPED_UNICODE);
    exit;
}

function connect() {
    global $con;
    $config_file = __DIR__ . '/config.php';
    if (!is_file($config_file)) {
        database_setup_error('database_config_missing');
    }
    $config = require $config_file;
    if (!is_array($config)) {
        database_setup_error('database_config_invalid');
    }
    foreach (array('host', 'username', 'password', 'database') as $key) {
        if (!array_key_exists($key, $config) || !is_string($config[$key])) {
            database_setup_error('database_config_invalid');
        }
    }
    if ($config['host'] === '' || $config['username'] === '' || $config['database'] === '') {
        database_setup_error('database_config_invalid');
    }
    if (!function_exists('mysqli_connect')) {
        database_setup_error('database_driver_unavailable');
    }

    mysqli_report(MYSQLI_REPORT_OFF);
    $port = isset($config['port']) ? (int) $config['port'] : 3306;
    $con = @mysqli_connect($config['host'], $config['username'], $config['password'], $config['database'], $port);
    if (!$con) {
        database_setup_error('database_connection_failed');
    }
    if (!@mysqli_set_charset($con, 'utf8mb4')) {
        database_setup_error('database_charset_failed');
    }
    return $con;
}

function close() {
    global $con;
    if ($con) {
        mysqli_close($con);
    }
}
