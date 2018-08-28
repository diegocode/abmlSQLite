/*
 * abmSQLite.java
 * 
 * Ejemplo de abm con SQLite
 *
 */
 
import java.util.Scanner;
import java.sql.*;

public class abmlSQLite {

    public static void main( String args[] ){
    
        Connection conexionBase = null;
        Statement stmt = null;
        
        String sql;
        
        // Para ingreso por teclado
        Scanner tecInput = new Scanner(System.in);
        
        // auxiliar
        String confirmaBorrado = "";
        
        // variables para datos de mascotas
        int idMascota = 0;
        String nombreMascota = "";
        int edadMascota = 0;
        String especieMascota = "";
        String razaMascota = "";
        
        // establece conexión
        // y si no existe la tabla la crea
        try {
            Class.forName("org.sqlite.JDBC");
            conexionBase = DriverManager.getConnection("jdbc:sqlite:veterinaria.db");
            
            stmt = conexionBase.createStatement();
                
            sql = "CREATE TABLE IF NOT EXISTS mascotas " +
                        "(id        INTEGER PRIMARY KEY," +
                        " nombre    TEXT    NOT NULL, " + 
                        " edad      INTEGER     NOT NULL, " + 
                        " especie   TEXT, " + 
                        " raza      TEXT)"; 
            
            stmt.executeUpdate(sql);
            stmt.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }   
        
        
        // loop principal
        
        char opc = ' ';
        boolean salir = false;
        
        while(opc != 's') {
            System.out.println("=============================");
            System.out.println("Elija opción:");
            System.out.println("(A)gregar");
            System.out.println("(B)orrar");
            System.out.println("(M)odificar");
            System.out.println("(L)istar");
            System.out.println("(S)alir");
            System.out.println();
            System.out.print("> ");
            
            try {
                opc = tecInput.nextLine().toLowerCase().charAt(0);
            } catch (Exception e){
                opc = '_';
            }
            
            switch (opc) {
                // Agregar mascota
                case 'a':
                    System.out.println("Ingrese nueva mascota:");
                    System.out.print("- Nombre: ");
                    nombreMascota = tecInput.nextLine();
                    System.out.print("- Edad: ");
                    edadMascota = tecInput.nextInt();
                    tecInput = new Scanner(System.in);
                    System.out.print("- Especie: ");
                    especieMascota = tecInput.nextLine();
                    System.out.print("- Raza: ");
                    razaMascota = tecInput.nextLine();
                    
                    try {
                        stmt = conexionBase.createStatement();
                        sql = String.format(
                                "INSERT INTO mascotas (nombre, edad, especie, raza) " +
                                "VALUES ('%s', %d, '%s', '%s');", 
                                    nombreMascota, edadMascota, especieMascota, razaMascota
                                ); 
                                
                        System.out.println(sql);
                        stmt.executeUpdate(sql);
                        stmt.close();
                    } catch ( Exception e ) {
                        System.err.println( e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }                       
                    break;
                
                // Borrar mascota
                case 'b':
                    System.out.println("Ingrese codigo a borrar: ");
                    System.out.print("- Codigo: ");
                    idMascota = tecInput.nextInt();
                    tecInput = new Scanner(System.in);
                    
                    try {
                        sql = String.format(
                                "SELECT * FROM mascotas WHERE id = %d;", idMascota
                              );
                        ResultSet rs = stmt.executeQuery(sql);
                                            
                        if ( rs.next() ) {
                            idMascota = rs.getInt("id");
                            nombreMascota = rs.getString("nombre");
                            edadMascota  = rs.getInt("edad");
                            especieMascota = rs.getString("especie");
                            razaMascota = rs.getString("raza");
                            
                            System.out.printf("%d\t%s\t\t\t\t%d\t\t%s\t\t%s\n", 
                                        idMascota,
                                        nombreMascota,
                                        edadMascota,
                                        especieMascota,
                                        razaMascota
                            );
                            
                        } else {
                            System.out.println("El codigo no existe");
                            idMascota = -1;  // Para indicar código inexistente 
                        }
                        System.out.println();
                        System.out.println();
                        rs.close();
                        
                        stmt.close();
                    
                    } catch (Exception e) {
                        System.err.println( e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                    
                    if (idMascota >= 0) {
                        System.out.println("Para confirmar borrado ingrese DEL");
                        tecInput = new Scanner(System.in);
                        confirmaBorrado = tecInput.nextLine();
                        
                        if (confirmaBorrado.equals("DEL")) {
                            try {
                                stmt = conexionBase.createStatement();
                                sql = String.format(
                                        "DELETE FROM mascotas WHERE id = %d;", idMascota
                                        ); 
                                        
                                System.out.println(sql);
                                stmt.executeUpdate(sql);
                                stmt.close();
                            } catch ( Exception e ) {
                                System.err.println( e.getClass().getName() + ": " + e.getMessage());
                                System.exit(0);
                            }
                        }
                    }
                    
                    break;
                
                // Modificar mascota
                case 'm':
                    System.out.println("Ingrese codigo a modificar: ");
                    System.out.print("- Codigo: ");
                    
                    idMascota = tecInput.nextInt();
                    tecInput = new Scanner(System.in);
                    
                    try {
                        sql = String.format(
                                "SELECT * FROM mascotas WHERE id = %d;", idMascota
                              );
                        ResultSet rs = stmt.executeQuery(sql);
                                            
                        if ( rs.next() ) {
                            idMascota = rs.getInt("id");
                            nombreMascota = rs.getString("nombre");
                            edadMascota  = rs.getInt("edad");
                            especieMascota = rs.getString("especie");
                            razaMascota = rs.getString("raza");
                            
                            System.out.printf("%d\t%s\t\t\t\t%d\t\t%s\t\t%s\n", 
                                        idMascota,
                                        nombreMascota,
                                        edadMascota,
                                        especieMascota,
                                        razaMascota
                            );
                            
                        } else {
                            System.out.println("El codigo no existe");
                            idMascota = -1;  // Para indicar código inexistente 
                        }
                        System.out.println();
                        System.out.println();
                        rs.close();
                        
                        stmt.close();
                    
                    } catch (Exception e) {
                        System.err.println( e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }
                    
                    if (idMascota >= 0) {
                        System.out.println("");
                        System.out.print("- Nombre: ");
                        nombreMascota = tecInput.nextLine();
                        System.out.print("- Edad: ");
                        edadMascota = tecInput.nextInt();
                        tecInput = new Scanner(System.in);
                        System.out.print("- Especie: ");
                        especieMascota = tecInput.nextLine();
                        System.out.print("- Raza: ");
                        razaMascota = tecInput.nextLine();
                        
                        try {
                            stmt = conexionBase.createStatement();
                            sql = String.format(
                                    "UPDATE mascotas SET " + 
                                        "nombre = '%s', edad = %d, especie = '%s', raza = '%s' " + 
                                    "WHERE id = %d;",  
                                        nombreMascota, 
                                        edadMascota, 
                                        especieMascota, 
                                        razaMascota, 
                                        idMascota
                                    ); 
                                    
                            System.out.println(sql);
                            stmt.executeUpdate(sql);
                            stmt.close();
                        } catch (Exception e) {
                            System.err.println( e.getClass().getName() + ": " + e.getMessage());
                            System.exit(0);
                        }
                    }                
                    break;
                    
                // Listado de mascotas
                case 'l':
                    try {
                        sql = "SELECT * FROM mascotas;";
                        ResultSet rs = stmt.executeQuery(sql);
                        
                        System.out.println("cod\tnombre\t\t\t\tedad\t\tespecie\t\traza");
                        
                        while ( rs.next() ) {
                            idMascota = rs.getInt("id");
                            nombreMascota = rs.getString("nombre");
                            edadMascota  = rs.getInt("edad");
                            especieMascota = rs.getString("especie");
                            razaMascota = rs.getString("raza");
                            
                            System.out.printf("%d\t%s\t\t\t\t%d\t\t%s\t\t%s\n", 
                                        idMascota,
                                        nombreMascota,
                                        edadMascota,
                                        especieMascota,
                                        razaMascota
                            );
                        }
                        System.out.println();
                        System.out.println();
                        rs.close();
                        
                        stmt.close();
                        
                    } catch ( Exception e ) {
                        System.err.println( e.getClass().getName() + ": " + e.getMessage());
                        System.exit(0);
                    }                   
                    break;
                
                // sale del programa
                case 's':
                    salir = true;
                    break;
                
                default:
                    System.out.println("opcion incorrecta");
                    System.out.println();
                    
            } // cierra switch
        } // cierra while
        
        // cierra conexión a la base
        try {
            conexionBase.close();
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        
    } // cierra main  
} // cierra class
