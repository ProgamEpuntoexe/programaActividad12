import java.util.*;
import java.io.*;
class AddressBook{
    File archivo = new File("contactos.txt");
    public void AddressBook() throws Exception{
        if (archivo.exists()){

        }else{
            System.out.println("creando el archivo contactos.txt");
            archivo.createNewFile();
        }
    }
}
public class Main {
    public static void main(String[] args) throws Exception{
        AddressBook thing = new AddressBook();
        thing.AddressBook();
    }
}