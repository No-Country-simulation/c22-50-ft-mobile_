<?php
//MySQL definido en el puerto 3306. Cambiar el puerto si se presentan problemas; por default MySQL viene en el puerto 3306.
//Poner acá la ip de la máquina que hostea del servidor
$host = '127.0.0.1:3306'; 
$nombreBD = 'crowdfundingdb';
$username = 'root'; 
$password = '';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$nombreBD", $username, $password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
    //echo "Conexión exitosa a la base de datos!\n\n";
} catch (PDOException $e) {
    die("Conexión fallida: " . $e->getMessage());
}
?>
