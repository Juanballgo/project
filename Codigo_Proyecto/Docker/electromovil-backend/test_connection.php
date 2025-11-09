<?php

$start = microtime(true);

try {
    // Test MySQL
    echo "Testing MySQL connection...\n";
    $dsn = "mysql:host=db;port=3306;dbname=electromovil";
    $start_db = microtime(true);
    $pdo = new PDO($dsn, 'electromovil', 'secret');
    echo "MySQL connected in " . number_format((microtime(true) - $start_db) * 1000, 2) . "ms\n";
    
    // Test file session write
    echo "\nTesting file session write...\n";
    $start_sess = microtime(true);
    $sess_file = "/var/www/html/storage/framework/sessions/test_" . uniqid();
    file_put_contents($sess_file, "test");
    echo "Session write took " . number_format((microtime(true) - $start_sess) * 1000, 2) . "ms\n";
    unlink($sess_file);
    
    echo "\nConnection tests completed in " . number_format((microtime(true) - $start) * 1000, 2) . "ms\n";
} catch (Exception $e) {
    echo "Error: " . $e->getMessage() . "\n";
}