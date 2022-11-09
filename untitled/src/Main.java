import java.util.*;
import java.io.*;
class AddressBook{
    File archivo = new File("contactos.txt");
    HashMap<String,String> contactos = new HashMap<>();
    public void AddressBook() throws Exception{
        if (!archivo.exists()){
            System.out.println("creando el archivo contactos.txt");
            archivo.createNewFile();
        }
    }
    //Sirve para cargar el archivo y los datos
    public void load() throws Exception{
        try{
            //Preparar atributos para leer la linea
            BufferedReader reader = new BufferedReader(new FileReader("contactos.txt"));
            String linea = reader.readLine();
            //Esto sirve para evitar error por nulo
            if (linea == null){
                linea = "";
            }
            Scanner lector = new Scanner(linea);
            lector.useDelimiter(",");
            //Leer las lineas
            while (linea != null){
                //Para evitar error por nulo
                if (linea == ""){
                    break;
                }
                String nombre = lector.next();
                String numero = lector.next();
                contactos.put(numero,nombre);
                linea = reader.readLine();
                //Si la siguiente linea no esta vacia, entonces volver a preparar el scanner
                if (linea != null){
                    lector = new Scanner(linea);
                    lector.useDelimiter(",");
                }
            }
            reader.close();
            lector.close();
        }catch (FileNotFoundException e){
            System.out.println("No se encontro un archivo");
            e.printStackTrace();
        }
    }
    //Atributo para guardar la lista (con los numeros y telefonos introducidos)
    public void save(String numero, String nombre) throws Exception{
        //Primero obtener informacion para poder escribirse
        BufferedReader reader = new BufferedReader(new FileReader("contactos.txt"));
        String linea = reader.readLine();
        String guardado = "";
        while (linea != null){
            guardado += linea+"\n";
            linea = reader.readLine();
        }
        //Despues se guarda la informacion
        FileWriter cambios = new FileWriter("contactos.txt");
        guardado += nombre+","+numero;
        cambios.write(guardado);
        contactos.put(numero,nombre);
        cambios.close();
    }
    //Mostrar la lista de numeros
    public void list(){
        //Este metodo sirve para obtener las llaves y valores de los atributos
        contactos.entrySet().forEach(entry ->{
            System.out.println(entry.getKey()+", "+entry.getValue());
        });
    }
    //Eliminar un numero de la lista
    public void delete(String numeroEliminar) throws Exception{
        //Si existe el contacto
        if (contactos.containsKey(numeroEliminar)){
            //Eliminarlo
            contactos.remove(numeroEliminar);
            System.out.println("Contacto eliinado exitosamente");
            //Despues guardar esos cambios
            //Primero obtener informacion para poder escribirse
            BufferedReader reader = new BufferedReader(new FileReader("contactos.txt"));
            String linea = reader.readLine();
            String guardado = "";
            while (linea != null){
                if (!linea.contains(numeroEliminar)){
                    guardado += linea+"\n";
                }
                linea = reader.readLine();
            }
            //Despues se guarda la informacion
            FileWriter cambios = new FileWriter("contactos.txt");
            cambios.write(guardado);
            cambios.close();
        }else{
            System.out.println("No existe tal numero");
        }
    }
    //Crear un nuevo contacto
    public void create() throws Exception{
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String nombre = "";
        String numero = "";
        try{
            System.out.print("Ingrese el numero de la persona: ");
            numero = entrada.readLine();
            System.out.print("Ingrese el nombre de la persona: ");
            nombre = entrada.readLine();
            save(numero,nombre);
            System.out.println("Se agrego exitosamente el contacto, presione Enter para continuar");
        }catch (Exception e){
            System.out.println("Uno de los campos esta vacio, presione Enter para continuar");
        }
        entrada.readLine();
    }
}
public class Main {
    public static void main(String[] args) throws Exception{
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        AddressBook thing = new AddressBook();
        thing.AddressBook();
        thing.load();
        String respuesta = "";
        String numeroEliminar = "";
        //Ahora el menu de opciones
        do{
            System.out.println("Menu para los contactos de telefono");
            System.out.println("a) Ver tus contactos");
            System.out.println("b) Agregar un numero de telefono");
            System.out.println("c) Eliminar un numero de telefono");
            System.out.println("d) Salir");
            System.out.print("Escoga una de estas opciones: ");
            try{
                respuesta = entrada.readLine();
                switch(respuesta.charAt(0)){
                    case 'a':{
                        System.out.println("Esta es la lista:");
                        thing.list();
                        System.out.println("Presione Enter para continuar");
                        entrada.readLine();
                        break;
                    }
                    case 'b':{
                        thing.create();
                        break;
                    }
                    case 'c':{
                        try{
                            System.out.print("Introdusca el numero que desea eliminar: ");
                            numeroEliminar = entrada.readLine();
                            thing.delete(numeroEliminar);
                        }catch (Exception e){
                            System.out.println("Favor de introducir el numero");
                        }
                        System.out.println("Presione Enter para continuar");
                        entrada.readLine();
                    }
                }
            }catch (Exception e){
                System.out.println("Favor de introducir un valor, presione Enter para continuar");
                entrada.readLine();
                respuesta = "12345";
            }

        }while (respuesta.charAt(0) != 'd');
        thing.load();
    }
}