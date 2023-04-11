package com.proyectoipc.archivos;
import com.proyectoipc.Entidades.*;
import com.proyectoipc.modelo.*;
import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author elvis_agui
 */
public class LectorArchivio {

    public static ArrayList<String> errores = new ArrayList<>();
    ConsulDB inserts = new ConsulDB();
    InsertsCampos opDB = new InsertsCampos();
    CosultDBaux auxDB = new CosultDBaux();
    
    /**
     * lee el archivo elegido
     * @param archivo
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void leerFichero(File archivo) throws FileNotFoundException, IOException {
        int cont = 0;
        while (cont != 6) {
            FileReader fr = new FileReader(archivo);
            BufferedReader br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                verificarEntidad(separarEnditad(linea), cont);
            }
            fr.close();
            cont++;
        }

    }

    public String[] separarEnditad(String linea) {
        String campos[] = null;
        campos = linea.split("\\(");
        return campos;
    }

    /**
     * llama al resto de funciones para realizas los registros
     *
     * @param campos
     * @param turno
     */
    public void verificarEntidad(String campos[], int turno) {
        if (turno == 0 && campos[0].equals(Entidades.USUARIO.name())) {
            insertarUsuario(limpiar(campos[1]));
        }
        if (turno == 1 && campos[0].equals(Entidades.PIEZA.name())) {
            insertarPieza(limpiar(campos[1]));
        }
        if (turno == 2 && campos[0].equals(Entidades.MUEBLE.name())) {
            insertarMueble(limpiar(campos[1]));
        }
        if (turno == 3 && campos[0].equals(Entidades.ENSAMBLE_PIEZAS.name())) {
            insertarPiezaMueble(limpiar(campos[1]));
        }
        if (turno == 4 && campos[0].equals(Entidades.ENSAMBLAR_MUEBLE.name())) {
            auxDB.EnsamblarMueble();
            insertarEnsamble(limpiar(campos[1]));
        }
        if (turno == 5 && campos[0].equals(Entidades.CLIENTE.name())) {
            insertarCliente(limpiar(campos[1]));
        }

    }

    public void insertarPieza(ArrayList<String> lista) {
        try {
            if (opDB.repitPiez(lista.get(0), Double.parseDouble(lista.get(1)))) {
                Pieza insert = inserts.buscarPieza(opDB.nombreCorrecto(lista.get(0), false));
                insert.setCantidad(insert.getCantidad() + 1);
                opDB.Actualizar(insert, lista.get(0));
            } else {
                if (opDB.repitPorNomPie(lista.get(0))) {
                    Random r = new Random();
                    char c = (char) (r.nextInt(26) + 'a');
                    Pieza insert = new Pieza(lista.get(0) + "-" + c, Double.parseDouble(lista.get(1)), 1);
                    inserts.CrearPieza(insert);
                } else {
                    Pieza insert = new Pieza(lista.get(0), Double.parseDouble(lista.get(1)), 1);
                    inserts.CrearPieza(insert);
                }

            }
        } catch (NumberFormatException e) {
            errores.add("no es numero " + lista.get(1));
        } catch (IndexOutOfBoundsException ex) {
            errores.add("Formato no Permitido no sea Cagon");
        }

    }

    public void insertarPiezaMueble(ArrayList<String> lista) {
        try {

            if (opDB.existPieza(lista.get(0), true) && opDB.existPieza(lista.get(1), false)) {
                Pieza_Muble insert = new Pieza_Muble(opDB.nombreCorrecto(lista.get(0), true), opDB.nombreCorrecto(lista.get(1), false), Integer.parseInt(lista.get(2)));
                auxDB.ensambePiesMueble(insert);
            } else {
                errores.add("Error La pieza o El mueble no existe " + lista.get(0) + " " + lista.get(1));
            }

        } catch (IndexOutOfBoundsException ex) {
            errores.add("Formato no Permitido no sea Cagon");
        } catch (NumberFormatException e) {
            errores.add("no es Numero " + lista.get(1));
        }
    }

    public void insertarMueble(ArrayList<String> lista) {
        try {
            Mueble insert = new Mueble(lista.get(0), Double.parseDouble(lista.get(1)));
            insert.setCosto(0);
            opDB.insertMueble(insert);
        } catch (IndexOutOfBoundsException ex) {
            errores.add("Formato no Permitido no sea Cagon");
        } catch (NumberFormatException e) {
            errores.add("no es Numero " + lista.get(1));
        }
    }

    public void insertarUsuario(ArrayList<String> lista) {
        if (opDB.repetidoUs(lista.get(0))) {
            errores.add("usuario repetido " + lista.get(0));
        } else {
            try {
                Usuario insert = new Usuario();
                insert.setNombre(lista.get(0));
                insert.setContra(lista.get(1));
                insert.setRol(Integer.parseInt(lista.get(2)));
                insert.setActivo(true);
                opDB.insertUsuario(insert);
            } catch (NumberFormatException e) {
                errores.add("Rol De Usuario no es Numero");
            } catch (IndexOutOfBoundsException ex) {
                errores.add("Formato no Permitido no sea Cagon");
            }

        }

    }

    /**
     * registra un ensamble en la base de datos
     *
     * @param lista
     */
    public void insertarEnsamble(ArrayList<String> lista) {
        try {
            if (opDB.yaTienePieza(lista.get(0)) && auxDB.existeUsuario(opDB.obtenerConstrasña(lista.get(1)))) {
                Ensamble insert = new Ensamble();
                insert.setId(Ensamble.id());
                insert.setMueble(opDB.nombreCorrecto(lista.get(0), true));
                insert.setGanancia(auxDB.calcGanancia(insert.getMueble()));
                insert.setEnsamblador(opDB.obtenerConstrasña(lista.get(1)));
                insert.setFecha(Ensamble.getFecha(lista.get(2)));
                insert.setEnSala(true);
                auxDB.guardarEnsamble(insert);
            }
        } catch (IndexOutOfBoundsException ex) {
            errores.add("Formato no Permitido no sea Cagon");
        } catch (ParseException  ex) {
            System.out.println("error* * "+ ex.getMessage());
            errores.add("Formato de fecha no Permitido no sea Cagon");
        }

    }

    /**
     * registra al cliente en la base de datso
     *
     * @param lista
     */
    public void insertarCliente(ArrayList<String> lista) {
        try {
            String direccionAux = "";
            Cliente inser = new Cliente();
            inser.setNombre(lista.get(0));
            inser.setNit(lista.get(1));
            if (lista.size() == 3) {
                inser.setDireccion(lista.get(2));
            } else {
                for (int i = 2; i < lista.size(); i++) {
                    direccionAux += lista.get(i);
                }
                inser.setDireccion(direccionAux);
            }
            opDB.insertarCliente(inser);

        } catch (IndexOutOfBoundsException ex) {
            errores.add("Formato no Permitido no sea Cagon");
        }

    }

    public ArrayList<String> limpiar(String linea) {
        ArrayList<String> lista = new ArrayList<>();
        String subCampo[] = null;
        String cadena = linea.substring(0, linea.length() - 1);
        subCampo = cadena.split(",");
        for (int i = 0; i < subCampo.length; i++) {
            String lineaLimp = subCampo[i].replace("\"", "");
            lista.add(lineaLimp);
        }

        return lista;
    }

}
