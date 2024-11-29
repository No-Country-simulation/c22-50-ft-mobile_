<?php
    require_once 'database.php';

    function select($tabla, $columnas = "*",  $condicion = ""): void{
        global $pdo;
        try{
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
                echo json_encode([["message" => "No hay coincidencias",
                                        "type" => "mensaje"]]);
            }
        } catch(PDOException $e){
            echo json_encode([["message" => "ERROR:" . $e->getMessage(),
                                     "type" => "mensaje"]]);
        }
    }

    function insert($tabla, $columnas, $valores): void{
        global $pdo;
        
        try{
            $query = "INSERT INTO $tabla";

            if(!empty($columnas)){
                $query .= " ($columnas)"; 
            }

            $query .= "VALUES ($valores)";
            $stmt = $pdo->prepare($query); 

            $stmt->execute();
            echo json_encode(["message" => "La insercion de los valores ' $valores ' 
                                                   en la ' $tabla ' fue exitosa",
                                                  "type" => "mensaje"]);
        } catch(PDOException $e){
            echo json_encode(["message" => "No se pudo insertar los valores ' $valores ' 
                                                   en la tabla ' $tabla '. ERROR:" . $e->getMessage(),
                                    "type" => "mensaje"]);
        }
    }

    function deleteSQL($tabla, $condicion): void {
        global $pdo;
        
        try{
            $query = "DELETE FROM $tabla WHERE $condicion";
            $stmt = $pdo->prepare($query);

            $stmt->execute();
            echo json_encode(["message" => "La eliminación de registros de la tabla ' $tabla ' 
                                                   fue exitosa. Condicion = ' $condicion '",
                                    "type" => "mensaje"]);
        } catch(PDOException $e){
            echo json_encode(["message" => "Error. No se pudo eliminar registros de la tabla ' $tabla '
                                                   que cumplan con la condicion ' $condicion '. ERROR:" . $e->getMessage(),
                                    "type" => "mensaje"]);
        }
    }

    function update($tabla, $setters, $condicion): void{
        global $pdo;

        try{
            $query = "UPDATE $tabla SET $setters WHERE $condicion";

            $stmt = $pdo->prepare($query);

            $stmt->execute();
                
            echo json_encode(["message" => "Tabla ' $tabla ' actualizada.
                                                   Valores afectados: $setters, condicion: $condicion",
                                    "type" => "mensaje"]);
        } catch(PDOException $e){
            echo json_encode(["message" => "Error. No se pudo actualizar la tabla $tabla. 
                                                    Valores a cambiar: $setters, condicion: $condicion. ERROR:". $e->getMessage(),
                                    "type" => "mensaje"]);
        }
        
        
    }
?>