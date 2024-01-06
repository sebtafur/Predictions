import java.io.*;
import java.util.*;
import java.*;



public class Main {


    public static void main(String[] args)
    {
        String ruta;
        String text;
        ruta="C:\\Users\\sebastian.tafur\\Downloads\\BNBBUSD-15m-2023-10-23.csv";
        Excel excel=new Excel();
        text=excel.leerExcel(ruta);

        System.out.println(text+"-----------------------------END___________________________");


    }


}