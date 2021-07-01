package it.algos.base.libreria.test;

import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibTesto;
import junit.framework.TestCase;

import javax.swing.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class LibTestoTest extends TestCase {

    LibTesto libTesto;


    /**
     * Testa il metodo isStringaValida.
     */
    public static void testIsStringaValida() throws Exception {

        /* Stringa nulla */
        /* il risultato deve essere falso */
        if (Lib.Testo.isValida(null)) {
            fail("stringa nulla");
        }// fine del blocco if

        /* Stringa vuota */
        /* il risultato deve essere falso */
        if (Lib.Testo.isValida("")) {
            fail("spazio vuoto");
        }// fine del blocco if

        /* Stringa con uno spazio vuoto */
        /* il risultato deve essere falso */
        if (Lib.Testo.isValida(" ")) {
            fail("stringa vuota");
        }// fine del blocco if

        /* Stringa con un valore normale */
        /* il risultato deve essere vero */
        if (!Lib.Testo.isValida("un testo")) {
            fail("valore normale");
        }// fine del blocco if

        /* Stringa con un valore corto */
        /* il risultato deve essere vero */
        if (!Lib.Testo.isValida("a")) {
            fail("valore corto");
        }// fine del blocco if

        /* Stringa con un numero sotto forma di stringa */
        /* il risultato deve essere vero */
        if (!Lib.Testo.isValida("5")) {
            fail("numero sotto forma di stringa");
        }// fine del blocco if

        /* Oggetto diverso da stringa */
        /* il risultato deve essere falso */
        if (Lib.Testo.isValida(5)) {
            fail("numero");
        }// fine del blocco if
    }


    /**
     * Testa il metodo isStringaVuota.
     */
    public static void testIsStringaVuota() throws Exception {

        /* Stringa nulla */
        /* il risultato deve essere vero */
        if (!Lib.Testo.isVuota(null)) {
            fail("stringa nulla");
        }// fine del blocco if

        /* Stringa vuota */
        /* il risultato deve essere vero */
        if (!Lib.Testo.isVuota("")) {
            fail("spazio vuoto");
        }// fine del blocco if

        /* Stringa con uno spazio vuoto */
        /* il risultato deve essere vero */
        if (!Lib.Testo.isVuota(" ")) {
            fail("stringa vuota");
        }// fine del blocco if

        /* Stringa con un valore normale */
        /* il risultato deve essere falso */
        if (Lib.Testo.isVuota("un testo")) {
            fail("valore normale");
        }// fine del blocco if

        /* Stringa con un valore corto */
        /* il risultato deve essere falso */
        if (Lib.Testo.isVuota("a")) {
            fail("valore corto");
        }// fine del blocco if

        /* Stringa con un numero sotto forma di stringa */
        /* il risultato deve essere falso */
        if (Lib.Testo.isVuota("5")) {
            fail("numero sotto forma di stringa");
        }// fine del blocco if

        /* Oggetto diverso da stringa */
        /* il risultato deve essere vero */
        if (!Lib.Testo.isVuota(5)) {
            fail("numero");
        }// fine del blocco if
    }


    /**
     * Testa il metodo convertePuntiInBarre.
     */
    public static void testConvertePuntiInBarre() throws Exception {
        String originale;
        String convertita;
        String richiesta;

        /* Stringa nulla */
        /* il risultato deve essere vero */
        originale = null;
        richiesta = "";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("stringa nulla");
        }// fine del blocco if

        /* Stringa vuota */
        /* il risultato deve essere vero */
        originale = "";
        richiesta = "";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("stringa vuota");
        }// fine del blocco if

        /* Nessun punto */
        /* il risultato deve essere vero */
        originale = "alfa";
        richiesta = "alfa";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("nessun punto");
        }// fine del blocco if

        /* Singolo punto */
        /* il risultato deve essere vero */
        originale = ".";
        richiesta = "/";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("singolo punto");
        }// fine del blocco if

        /* Singolo punto iniziale*/
        /* il risultato deve essere vero */
        originale = ".alfa";
        richiesta = "/alfa";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("singolo punto iniziale");
        }// fine del blocco if

        /* Singolo punto finale*/
        /* il risultato deve essere vero */
        originale = "alfa.";
        richiesta = "alfa/";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("singolo punto finale");
        }// fine del blocco if

        /* Diversi punti */
        /* il risultato deve essere vero */
        originale = "alfa.beta.gamma";
        richiesta = "alfa/beta/gamma";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("diversi punti");
        }// fine del blocco if

        /* Diversi punti alle estremit�*/
        /* il risultato deve essere vero */
        originale = ".alfa.beta.gamma.";
        richiesta = "/alfa/beta/gamma/";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("diversi punti estremit�");
        }// fine del blocco if

        /* Diversi punti doppi alle estremit�*/
        /* il risultato deve essere vero */
        originale = ".alfa..beta.gamma..";
        richiesta = "/alfa//beta/gamma//";
        convertita = Lib.Testo.convertePuntiInBarre(originale);
        if (!convertita.equals(richiesta)) {
            fail("diversi punti doppi");
        }// fine del blocco if
    }


    /**
     * Testa il metodo converteBarreInPunti.
     */
    public static void testConverteBarreInPunti() throws Exception {
        String originale;
        String convertita;
        String richiesta;

        /* Stringa nulla */
        /* il risultato deve essere vero */
        originale = null;
        richiesta = "";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("stringa nulla");
        }// fine del blocco if

        /* Stringa vuota */
        /* il risultato deve essere vero */
        originale = "";
        richiesta = "";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("stringa vuota");
        }// fine del blocco if

        /* Nessuna barra */
        /* il risultato deve essere vero */
        originale = "alfa";
        richiesta = "alfa";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("nessuna barra");
        }// fine del blocco if

        /* Singola barra */
        /* il risultato deve essere vero */
        originale = "/";
        richiesta = ".";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("singola barra");
        }// fine del blocco if

        /* Singola barra iniziale*/
        /* il risultato deve essere vero */
        originale = "/alfa";
        richiesta = ".alfa";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("singola barra iniziale");
        }// fine del blocco if

        /* Singola barra finale*/
        /* il risultato deve essere vero */
        originale = "alfa/";
        richiesta = "alfa.";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("singola barra finale");
        }// fine del blocco if

        /* Diverse barre */
        /* il risultato deve essere vero */
        originale = "alfa/beta/gamma";
        richiesta = "alfa.beta.gamma";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("diverse barre");
        }// fine del blocco if

        /* Diverse barre alle estremit�*/
        /* il risultato deve essere vero */
        originale = "/alfa/beta/gamma/";
        richiesta = ".alfa.beta.gamma.";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("diverse barre estremit�");
        }// fine del blocco if

        /* Diverse barre doppie alle estremit�*/
        /* il risultato deve essere vero */
        originale = "/alfa//beta/gamma//";
        richiesta = ".alfa..beta.gamma..";
        convertita = Lib.Testo.converteBarreInPunti(originale);
        if (!convertita.equals(richiesta)) {
            fail("diverse barre doppie");
        }// fine del blocco if
    }


    public void testGetListaChiavi() throws Exception {
        LinkedHashMap<String, Object> collezione;
        ArrayList<String> richiesta;
        ArrayList<String> convertita;
        boolean testValido = true;

        collezione = new LinkedHashMap<String, Object>();

        /* lista di chiavi */
        richiesta = new ArrayList<String>();
        richiesta.add("alfa");
        richiesta.add("beta");
        richiesta.add("gamma");

        /* collezione di oggetti qualsiasi con chiave uguale alla lista */
        collezione.put(richiesta.get(0), new JLabel());
        collezione.put(richiesta.get(1), new JDialog());
        collezione.put(richiesta.get(2), new JTextField());

        /* recupera la lista di chiavi */
        convertita = Lib.Testo.getListaChiavi(collezione);

        /* la lista deve avere le stesse dimensioni della collezione */
        if (convertita.size() != collezione.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alle chiavi inserite */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("collezione");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* collezione di dimensioni differenti */
        collezione.put("delta", new JLabel());

        /* recupera la lista di chiavi */
        convertita = Lib.Testo.getListaChiavi(collezione);

        /* la lista deve avere le stesse dimensioni della collezione */
        if (convertita.size() != collezione.size()) {
            fail("collezione di dimensioni diverse");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* collezione di oggetti qualsiasi con una chiave diversa dalla lista */
        collezione.clear();
        collezione.put(richiesta.get(0), new JLabel());
        collezione.put("delta", new JDialog());
        collezione.put(richiesta.get(2), new JTextField());

        /* recupera la lista di chiavi */
        convertita = Lib.Testo.getListaChiavi(collezione);

        /* la lista deve avere le stesse dimensioni della collezione */
        if (convertita.size() != collezione.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alle chiavi inserite */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (testValido) {
            fail("collezione con chiave diversa");
        }// fine del blocco if

    }


    public void testPrimaMaiuscola() throws Exception {
        String originale;
        String convertita;
        String richiesta;

        /* normale */
        /* il risultato deve essere vero */
        originale = "alfetta";
        richiesta = "Alfetta";
        convertita = Lib.Testo.primaMaiuscola(originale);
        if (!convertita.equals(richiesta)) {
            fail("prima maiuscola normale");
        }// fine del blocco if

        /* maiuscolo */
        /* il risultato deve essere vero */
        originale = "ALFETTA";
        richiesta = "ALFETTA";
        convertita = Lib.Testo.primaMaiuscola(originale);
        if (!convertita.equals(richiesta)) {
            fail("tutto maiuscolo");
        }// fine del blocco if

        /* numero iniziale */
        /* il risultato deve essere vero */
        originale = "8dopo";
        richiesta = "8dopo";
        convertita = Lib.Testo.primaMaiuscola(originale);
        if (!convertita.equals(richiesta)) {
            fail("numero");
        }// fine del blocco if
    }


    public void testPrimaMinuscola() throws Exception {
        String originale;
        String convertita;
        String richiesta;

        /* normale */
        /* il risultato deve essere vero */
        originale = "Alfetta";
        richiesta = "alfetta";
        convertita = Lib.Testo.primaMinuscola(originale);
        if (!convertita.equals(richiesta)) {
            fail("prima minuscola normale");
        }// fine del blocco if

        /* minuscolo */
        /* il risultato deve essere vero */
        originale = "alfetta";
        richiesta = "alfetta";
        convertita = Lib.Testo.primaMinuscola(originale);
        if (!convertita.equals(richiesta)) {
            fail("tutto minuscolo");
        }// fine del blocco if

        /* numero iniziale */
        /* il risultato deve essere vero */
        originale = "8dopo";
        richiesta = "8dopo";
        convertita = Lib.Testo.primaMinuscola(originale);
        if (!convertita.equals(richiesta)) {
            fail("numero");
        }// fine del blocco if
    }


    public void testGetStringa() throws Exception {
        ArrayList<String> lista;
        String separatore;
        String convertita;
        String richiesta;

        lista = new ArrayList<String>();

        /* lista nulla */
        /* il risultato deve essere vero */
        separatore = ",";
        richiesta = "";
        convertita = Lib.Testo.getStringa(null, separatore);
        if (!convertita.equals(richiesta)) {
            fail("lista nulla");
        }// fine del blocco if

        /* lista vuota */
        /* il risultato deve essere vero */
        separatore = ",";
        richiesta = "";
        convertita = Lib.Testo.getStringa(lista, separatore);
        if (!convertita.equals(richiesta)) {
            fail("lista vuota");
        }// fine del blocco if

        /* separatore vuoto */
        /* il risultato deve essere vero */
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        separatore = "";
        richiesta = "alfabetagamma";
        convertita = Lib.Testo.getStringa(lista, separatore);
        if (!convertita.equals(richiesta)) {
            fail("separatore vuoto");
        }// fine del blocco if

        /* separatore nullo */
        /* il risultato deve essere vero */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        richiesta = "alfabetagamma";
        convertita = Lib.Testo.getStringa(lista, null);
        if (!convertita.equals(richiesta)) {
            fail("separatore nullo");
        }// fine del blocco if

        /* lista normale */
        /* il risultato deve essere vero */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        separatore = ",";
        richiesta = "alfa,beta,gamma";
        convertita = Lib.Testo.getStringa(lista, separatore);
        if (!convertita.equals(richiesta)) {
            fail("lista normale");
        }// fine del blocco if

        /* separatore ritorno a capo */
        /* il risultato deve essere vero */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        separatore = "\n";
        richiesta = "alfa\nbeta\ngamma";
        convertita = Lib.Testo.getStringa(lista, separatore);
        if (!convertita.equals(richiesta)) {
            fail("separatore ritorno a capo");
        }// fine del blocco if
    }


    public void testGetStringaSpazio() throws Exception {
        ArrayList<String> lista;
        String separatore = " ";
        String convertita;
        String richiesta;

        lista = new ArrayList<String>();

        /* separatore spazio */
        /* il risultato deve essere vero */
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        richiesta = "alfa beta gamma";
        convertita = Lib.Testo.getStringaSpazio(lista);
        if (!convertita.equals(richiesta)) {
            fail("separatore spazio");
        }// fine del blocco if
    }


    public void testGetStringaVirgola() throws Exception {
        ArrayList<String> lista;
        String separatore = ",";
        String convertita;
        String richiesta;
        String richiesto;
        int[] matriceInt;

        lista = new ArrayList<String>();

        /* separatore virgola */
        /* il risultato deve essere vero */
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        richiesta = "alfa,beta,gamma";
        convertita = Lib.Testo.getStringaVirgola(lista);
        if (!convertita.equals(richiesta)) {
            fail("separatore virgola");
        }// fine del blocco if

        //======================================================================

        /* array di interi */
        /* il risultato deve essere uguale al richiesto */
        matriceInt = new int[4];
        matriceInt[0] = 273;
        matriceInt[1] = -24;
        matriceInt[2] = 1250;
        matriceInt[3] = 0;
        richiesto = "273,-24,1250,0";

        richiesto = Lib.Testo.getStringaVirgola(lista);

        if (!richiesto.equals(richiesta)) {
            fail("interi separatore virgola");
        }// fine del blocco if
    }


    public void testGetStringaTab() throws Exception {
        ArrayList<String> lista;
        String separatore = "\t";
        String convertita;
        String richiesta;

        lista = new ArrayList<String>();

        /* separatore tab */
        /* il risultato deve essere vero */
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        richiesta = "alfa\tbeta\tgamma";
        convertita = Lib.Testo.getStringaTab(lista);
        if (!convertita.equals(richiesta)) {
            fail("separatore tab");
        }// fine del blocco if
    }


    public void testGetStringaCapo() throws Exception {
        ArrayList<String> lista;
        String separatore = "\n";
        String convertita;
        String richiesta;

        lista = new ArrayList<String>();

        /* separatore a capo */
        /* il risultato deve essere vero */
        lista.add("alfa");
        lista.add("beta");
        lista.add("gamma");
        richiesta = "alfa\nbeta\ngamma";
        convertita = Lib.Testo.getStringaCapo(lista);
        if (!convertita.equals(richiesta)) {
            fail("separatore a capo");
        }// fine del blocco if
    }


    public void testMaxCaratteri() throws Exception {
        ArrayList<String> lista;
        int risultato;
        int richiesto;

        /* numero di caratteri della stringa pi� lunga */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("");
        lista.add("gammadeltaiota");
        richiesto = lista.get(2).length();

        risultato = Lib.Testo.maxCaratteri(lista);

        if (!(risultato == richiesto)) {
            fail("numero di caratteri della stringa pi� lunga");
        }// fine del blocco if

        //======================================================================

        /* lista nulla */
        /* il risultato deve essere uguale al richiesto */
        richiesto = 0;

        risultato = Lib.Testo.maxCaratteri(null);

        if (!(risultato == richiesto)) {
            fail("lista nulla");
        }// fine del blocco if
        //======================================================================

        /* lista vuota */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        richiesto = 0;

        risultato = Lib.Testo.maxCaratteri(lista);

        if (!(risultato == richiesto)) {
            fail("lista vuota");
        }// fine del blocco if
    }


    public void testMinCaratteri() throws Exception {
        ArrayList<String> lista;
        int risultato;
        int richiesto;

        /* numero di caratteri della stringa pi� corta (zero) */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("");
        lista.add("gammadeltaiota");
        richiesto = lista.get(1).length();

        risultato = Lib.Testo.minCaratteri(lista);

        if (!(risultato == richiesto)) {
            fail("numero di caratteri della stringa pi� corta");
        }// fine del blocco if

        //======================================================================

        /* numero di caratteri della stringa pi� corta (uno) */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("x");
        lista.add("gammadeltaiota");
        richiesto = lista.get(1).length();

        risultato = Lib.Testo.minCaratteri(lista);

        if (!(risultato == richiesto)) {
            fail("numero di caratteri della stringa pi� corta");
        }// fine del blocco if

        //======================================================================

        /* lista nulla */
        /* il risultato deve essere uguale al richiesto */
        richiesto = 0;

        risultato = Lib.Testo.minCaratteri(null);

        if (!(risultato == richiesto)) {
            fail("lista nulla");
        }// fine del blocco if

        //======================================================================

        /* lista vuota */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        richiesto = 0;

        risultato = Lib.Testo.minCaratteri(lista);

        if (!(risultato == richiesto)) {
            fail("lista vuota");
        }// fine del blocco if
    }


    public void testMaxTesto() throws Exception {
        ArrayList<String> lista;
        String risultato;
        String richiesto;

        /* stringa pi� lunga */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("x");
        lista.add("gammadeltaiota");
        richiesto = lista.get(2);

        risultato = Lib.Testo.maxTesto(lista);

        if (!(risultato.equals(richiesto))) {
            fail("stringa pi� lunga");
        }// fine del blocco if

        //======================================================================

        /* lista nulla */
        /* il risultato deve essere uguale al richiesto */
        richiesto = "";

        risultato = Lib.Testo.maxTesto(null);

        if (!(risultato.equals(richiesto))) {
            fail("lista nulla");
        }// fine del blocco if

        //======================================================================

        /* lista vuota */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        richiesto = "";

        risultato = Lib.Testo.maxTesto(lista);

        if (!(risultato.equals(richiesto))) {
            fail("lista vuota");
        }// fine del blocco if
    }


    public void testMinTesto() throws Exception {
        ArrayList<String> lista;
        String risultato;
        String richiesto;

        /* stringa pi� corta */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        lista.add("alfa");
        lista.add("x");
        lista.add("gammadeltaiota");
        richiesto = lista.get(1);

        risultato = Lib.Testo.minTesto(lista);

        if (!(risultato.equals(richiesto))) {
            fail("stringa più corta");
        }// fine del blocco if

        //======================================================================

        /* lista nulla */
        /* il risultato deve essere uguale al richiesto */
        richiesto = "";

        risultato = Lib.Testo.minTesto(null);

        if (!(risultato.equals(richiesto))) {
            fail("lista nulla");
        }// fine del blocco if

        //======================================================================

        /* lista vuota */
        /* il risultato deve essere uguale al richiesto */
        lista = new ArrayList<String>();
        richiesto = "";

        risultato = Lib.Testo.minTesto(lista);

        if (!(risultato.equals(richiesto))) {
            fail("lista vuota");
        }// fine del blocco if
    }


    public void testFormatNumero() throws Exception {
        String originale;
        String risultato;
        String richiesto;

        originale = "-8";
        richiesto = "-8";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("negativo");
        }// fine del blocco if

        originale = "7";
        richiesto = "7";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("brevissimo");
        }// fine del blocco if

        originale = "125";
        richiesto = "125";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("numero breve");
        }// fine del blocco if

        originale = "1250";
        richiesto = "1.250";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("1250");
        }// fine del blocco if

        originale = "1250,15";
        richiesto = "1250,15";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("scartato");
        }// fine del blocco if

        originale = "12500";
        richiesto = "12.500";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("12.500");
        }// fine del blocco if

        originale = "125000";
        richiesto = "125.000";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("125.000");
        }// fine del blocco if

        originale = "1250150";
        richiesto = "1.250.150";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("1.250.150");
        }// fine del blocco if

        originale = "12501500";
        richiesto = "12.501.500";
        risultato = Lib.Testo.formatNumero(originale);
        if (!(risultato.equals(richiesto))) {
            fail("12.501.500");
        }// fine del blocco if

    }


    public void testReplaceAll() throws Exception {
        String risultato;
        String richiesto;
        String testo;
        String leva;
        String metti;
        String tag = "'";
        String escape = "\\'";


        testo = "testo di \\ con \\prova lettere";
        leva = "\\";
        metti = "xx";

        richiesto = "testo di xx con xxprova lettere";
        risultato = Lib.Testo.replaceAll(testo, leva, metti);
        if (!(risultato.equals(richiesto))) {
            fail("ciccato!");
        }// fine del blocco if


        testo = "direttore d'orchestra";
        richiesto = "direttore d\\'orchestra";
        risultato = Lib.Testo.replaceAll(testo, tag, escape);
        if (!(risultato.equals(richiesto))) {
            fail("orchestra!");
        }// fine del blocco if

        testo = "direttore d'orchestra e d'azienda";
        richiesto = "direttore d\\'orchestra e d\\'azienda";
        risultato = Lib.Testo.replaceAll(testo, tag, escape);
        if (!(risultato.equals(richiesto))) {
            fail("escape doppio errato");
        }// fine del blocco if

    }


    public void testGetTestoRandom() throws Exception {
        String risultato;
        String richiesto;

        risultato = Lib.Testo.getTestoRandom(10);
        risultato = Lib.Testo.getTestoRandom(10);
        risultato = Lib.Testo.getTestoRandom(10);
        risultato = Lib.Testo.getTestoRandom(10);
        risultato = Lib.Testo.getTestoRandom(10);
        risultato = Lib.Testo.getTestoRandom(10);
        risultato = Lib.Testo.getTestoRandom(10);
        if (Lib.Testo.isVuota(risultato)) {
            fail("ciccato");
        }// fine del blocco if
    }
}