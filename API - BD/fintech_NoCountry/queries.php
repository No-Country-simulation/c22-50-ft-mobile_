<?php
    require_once 'database.php';

    function select($tabla, $columnas = "*",  $condicion = ""): void{
        global $pdo;
        $query = "SELECT $columnas FROM $tabla";

        if(!empty($condicion)){
            $query .= " WHERE $condicion";
        }

        $stmt = $pdo->prepare($query); 
        $stmt->execute();
        $resultados = $stmt->fetchAll(PDO::FETCH_ASSOC);

        if ($resultados) {
            //Esto es para después identificarlo con el polymorphicJsonAdapterFactory de Moshi
            foreach ($resultados as &$resultado) {
                $resultado['type'] = "$tabla";
            } 

            echo json_encode($resultados);
        } else {
            echo json_encode([["message" => "Usuario no encontrado",
                                     "type" => "mensaje"]]);
        }
    }

    function insert($tabla, $columnas, $valores): void{
        global $pdo;
        $query = "INSERT INTO $tabla";

        if(!empty($columnas)){
            $query .= " ($columnas)"; 
        }

        $query .= "VALUES ($valores)";
        $stmt = $pdo->prepare($query); 

        if($stmt->execute()){
            echo json_encode(["message" => "La insercion de los valores ' $valores ' 
                                                   en la ' $tabla ' fue exitosa",
                                    "type" => "mensaje"]);
        } else {
            echo json_encode(["message" => "Error. No se pudo insertar los valores ' $valores ' 
                                                   en la tabla ' $tabla '",
                                    "type" => "mensaje"]);
        }
    }

    function deleteSQL($tabla, $condicion): void {
        global $pdo;
        
        $query = "DELETE FROM $tabla WHERE $condicion";
        $stmt = $pdo->prepare($query);

        if ($stmt->execute()) {
            echo json_encode(["message" => "La eliminación de registros de la tabla ' $tabla ' 
                                                   fue exitosa. Condicion = ' $condicion '",
                                    "type" => "mensaje"]);
        } else {
            echo json_encode(["message" => "Error. No se pudo eliminar registros de la tabla ' $tabla '
                                                   que cumplan con la condicion ' $condicion '",
                                    "type" => "mensaje"]);
        }
    }

    function update($tabla, $setters, $condicion): void{
        global $pdo;
        
        $query = "UPDATE $tabla SET $setters WHERE $condicion";

        $stmt = $pdo->prepare($query);

        if ($stmt->execute()) {
            
            echo json_encode(["message" => "Tabla ' $tabla ' actualizada.
                                                   Valores afectados: $setters, condicion: $condicion",
                                    "type" => "mensaje"]);
        } else {
            echo json_encode(["message" => "Error. No se pudo actualizar la tabla $tabla. 
                                                   Valores a cambiar: $setters, condicion: $condicion",
                                    "type" => "mensaje"]);
        }
    }
?>