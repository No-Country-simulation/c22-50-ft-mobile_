<?php
    include 'queries.php';
    require_once 'database.php';
    
    $uri = parse_url($_SERVER['REQUEST_URI'], PHP_URL_PATH);
    $method = $_SERVER['REQUEST_METHOD'];

    //Para dividir la uri en partes. La idea es que una de las partes diga qué tabla/s manipular.
    $segmentos = explode('/', $uri);
    $tabla = "";

    if(isset($segmentos[2])){
        $tabla .= $segmentos[2];
    }
    
    if (!empty($tabla)) {
        if(isset($method))
            switch ($method) {
                case 'GET':
                    manejarGet($tabla);
                    break;
                case 'POST':
                    manejarPost($tabla);
                    break;
                case 'PUT':
                    manejarPut($tabla);
                    break;
                case 'DELETE':
                    manejarDelete($tabla);
                    break;
                default:
                    header("HTTP/1.1 405 Method Not Allowed");
                    echo json_encode(["message" => "Metodo no permitido"]);
                    break;
        } else {
            header("HTTP/1.1 404 Not Found");
            echo json_encode(["message" => "Metodo no ingresado"]);
        }
    } else {
        header("HTTP/1.1 404 Not Found");
            echo json_encode(["message" => "Tabla no ingresada"]);
    }

    function manejarGet($tabla): void{
        $condicion = "";
        $columnas = "*";

        if(!empty($_GET["condicion"])){
            $condicion .= $_GET["condicion"];
        }

        if(!empty($_GET["columnas"])){
            $columnas = $_GET["columnas"];
        }

        select($tabla, $columnas, $condicion);
    }

    function manejarPost($tabla): void{
        $data = json_decode(file_get_contents("php://input"));
        $columnas = "";
        $valores = "";

        if(!empty($data->valores)){
            $valores = $data->valores;
        }

        if(!empty($data->columnas)){
            $columnas = $data->columnas;
        }

        insert($tabla, $columnas, $valores);
    }

    function manejarPut($tabla): void{
        $data = json_decode(file_get_contents("php://input"));
        $setters = "";
        $condicion = "";

        if(!empty($data->setters)){
            $setters = $data->setters;
        }

        if(!empty($data->condicion)){
            $condicion = $data->condicion;
        }

        update($tabla, $setters, $condicion);
    }

    function manejarDelete($tabla){
        $condicion = "";

        if(!empty($_GET["condicion"])){
            $condicion = $_GET["condicion"];
        }

        deleteSQL($tabla, $condicion);
    }
?>