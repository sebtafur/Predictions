
import sun.java2d.d3d.D3DDrawImage;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.sql.Timestamp;
import java.util.Date;

public class Excel {
    String texto = "";
    String partes[] = null;

    String[] open;

    Double openMax;

    double raizOpen;

    Double openMin;
    List<Double> listaOpen = new ArrayList<>();
    List<String> listaOpenTime = new ArrayList<>();
    List<String> listaClose = new ArrayList<>();

    String[] open_old = null;





    public String leerExcel(String direccion) {

        try {
            BufferedReader bf = new BufferedReader(new FileReader(direccion));
            String temp = "";
            String bfread;
            MetodosNumericos metodosNumericos = new MetodosNumericos();
            double MetodoBiseccion;

            while ((bf.readLine()) != null) {

                partes = bf.readLine().split(",0");
                open_old = partes[0].split(",");

                String date;
                date = testToDateTime(open_old[0]);
                System.out.println(date);
                listaOpenTime.add(open_old[0].toString());
                listaOpen.add(Double.parseDouble(open_old[1]));
                listaClose.add(open_old[2].toString());
                imprimirLinea();

            }

            openMax = getMaxOpen(listaOpen);
            openMin = getMinOpen(listaOpen);
            raizOpen=metodosNumericos.MetodoBiseccion(openMin, openMax, 0.00001);
            imprimirOpenTime();
            imprimirOpen();
            imprimirClose();
            bf.close();
            bfread = null;
            partes = null;

        } catch (Exception e) {
            System.err.println("no se encontro archivo");
            throw new RuntimeException(e);
        }
        return texto;
    }

    private void imprimirOpenTime()
    {
        for (String str:listaOpenTime)
        {

            System.out.println(str+"OpenTime");
        }
    }

    private void imprimirClose()
    {
        for (String str:listaClose)
        {

            System.out.println(str+"Close");
        }
    }


    public void imprimirLinea()
    {
        for (int i = 0; i < partes.length; i++) {
            System.out.println(partes[i] + " | ");
        }

    }

    public void imprimirOpen() {

        for (Double str:listaOpen)
        {

            System.out.println(str+"Open");
        }

    }
    public String testToDateTime(String fecha) {
        //unix time
        Long unixTime = Long.valueOf(fecha);
        //millis time
        //Long millisTime = unixTime * 1000;

        String fechaConvertida;

        Instant instant = Instant.ofEpochMilli(unixTime);

        // system default time zone
        ZoneId defaultZoneId = ZoneId.systemDefault();
        //custom time zone
        ZoneId customZoneId = ZoneId.of("America/Bogota");

        LocalDateTime defaultLocalDateTime = LocalDateTime.ofInstant(instant, defaultZoneId);
        LocalDateTime customLocalDateTime = LocalDateTime.ofInstant(instant, customZoneId);

        //String s = defaultLocalDateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        System.out.println(defaultLocalDateTime); //2021-11-05T10:03:58
        System.out.println(customLocalDateTime); //2021-11-04T22:03:58

        fechaConvertida=defaultLocalDateTime+"___"+customLocalDateTime;

        return fechaConvertida;

    }

    public double getMaxOpen(List<Double> lista)
    {
        double max;
        max= lista.stream().max(Comparator.comparing( v->v)).
                orElseThrow(NoSuchElementException::new);
        return max;
    }
    public Double getMinOpen(List<Double> lista)
    {
        Double min;
        min= lista.stream().min(Comparator.comparing( v->v)).
                orElseThrow(NoSuchElementException::new);
        return min;
    }
}
