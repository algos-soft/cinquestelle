package it.algos.base.libreria.test;

import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibMat;
import junit.framework.TestCase;

public class LibMatTest extends TestCase {

    LibMat libMat;


    public void testGetInt() throws Exception {
        String testo;

        int richiesto;
        int ottenuto;


        testo = "1827";
        richiesto = 1827;

        ottenuto = Lib.Mat.getInt(testo);

        if (ottenuto != richiesto) {
            fail("ciccato");
        }// fine del blocco if


        testo = "1.827";
        richiesto = 1827;

        ottenuto = Lib.Mat.getInt(testo);

        if (ottenuto != richiesto) {
            fail("punti");
        }// fine del blocco if


        testo = "18,27";
        richiesto = 1827;

        ottenuto = Lib.Mat.getInt(testo);

        if (ottenuto != richiesto) {
            fail("virgole");
        }// fine del blocco if


        testo = "18,27,963";
        richiesto = 1827963;

        ottenuto = Lib.Mat.getInt(testo);

        if (ottenuto != richiesto) {
            fail("entrambe");
        }// fine del blocco if


        testo = "pippo";
        richiesto = 0;

        ottenuto = Lib.Mat.getInt(testo);

        if (ottenuto != richiesto) {
            fail("nullo");
        }// fine del blocco if

    }


    public void testGetPer() throws Exception {
        int totale;
        int parziale;
        int richiesto;
        int ottenuto;

        totale = 200;
        parziale = 100;
        richiesto = 50;

        ottenuto = Lib.Mat.getPer(totale, parziale);
        if (ottenuto != richiesto) {
            fail("50");
        }// fine del blocco if


        totale = 50;
        parziale = 6;
        richiesto = 12;

        ottenuto = Lib.Mat.getPer(totale, parziale);
        if (ottenuto != richiesto) {
            fail("12");
        }// fine del blocco if
    }


    public void testIsMultiplo() throws Exception {
        int numero;
        int moltiplicatore;
        boolean richiesto;
        boolean ottenuto;

        moltiplicatore = 100;
        numero = 137;
        richiesto = false;

        ottenuto = Lib.Mat.isMultiplo(numero, moltiplicatore);
        if (ottenuto != richiesto) {
            fail("multiplo errato");
        }// fine del blocco if

        numero = 200;
        richiesto = true;

        ottenuto = Lib.Mat.isMultiplo(numero, moltiplicatore);
        if (ottenuto != richiesto) {
            fail("multiplo esatto");
        }// fine del blocco if

        numero = 54;
        richiesto = false;

        ottenuto = Lib.Mat.isMultiplo(numero, moltiplicatore);
        if (ottenuto != richiesto) {
            fail("multiplo esatto");
        }// fine del blocco if

    }


    public void testGetNum() throws Exception {
        String numeroInglese;
        int moltiplicatore;
        double richiesto;
        double ottenuto;
        String richiestoStr;
        String ottenutoStr;

        /* */
        numeroInglese = "2,3470.50";
        richiesto = 23470.50;

        ottenuto = Lib.Mat.getNum(numeroInglese);
        if (ottenuto != richiesto) {
            fail("23,470.50");
        }// fine del blocco if

        /* */
        numeroInglese = "287";
        richiesto = 287;

        ottenuto = Lib.Mat.getNum(numeroInglese);
        if (ottenuto != richiesto) {
            fail("287");
        }// fine del blocco if

        /* */
        numeroInglese = "3,780,900";
        richiesto = 3780900;

        ottenuto = Lib.Mat.getNum(numeroInglese);
        if (ottenuto != richiesto) {
            fail("3,780,900");
        }// fine del blocco if

        /* */
        numeroInglese = "4,500.325";
        richiesto = 4500.325;

        ottenuto = Lib.Mat.getNum(numeroInglese);
        if (ottenuto != richiesto) {
            fail("4,500.325");
        }// fine del blocco if

        /* */
        numeroInglese = "4,500.3";
        richiesto = 4500.3;

        ottenuto = Lib.Mat.getNum(numeroInglese);
        if (ottenuto != richiesto) {
            fail("4,500.3");
        }// fine del blocco if

        /* */
        numeroInglese = "4,500.3";
        richiesto = 4500.3;

        ottenuto = Lib.Mat.getNum2(numeroInglese);
        if (ottenuto != richiesto) {
            fail("4,500.3");
        }// fine del blocco if

        /* */
        numeroInglese = "8,000.3456";
        richiesto = 8000.34;

        ottenuto = Lib.Mat.getNum2(numeroInglese);
        if (ottenuto != richiesto) {
            fail("8,000.3456");
        }// fine del blocco if

        /* */
        numeroInglese = "40.4";
        richiestoStr = "40,4";

        ottenutoStr = Lib.Mat.getValNumIng(numeroInglese);
        if (!ottenutoStr.equals(richiestoStr)) {
            fail("40.4");
        }// fine del blocco if

        /* */
        numeroInglese = "1,540.4";
        richiestoStr = "1.540,4";

        ottenutoStr = Lib.Mat.getValNumIng(numeroInglese);
        if (!ottenutoStr.equals(richiestoStr)) {
            fail("1,540.4");
        }// fine del blocco if

        /* */
        String numeroItaliano;
        numeroItaliano = "2.987";
        richiestoStr = "2.987";

        ottenutoStr = Lib.Testo.formatNumIta(numeroItaliano);
        if (!ottenutoStr.equals(richiestoStr)) {
            fail("2.987");
        }// fine del blocco if

        /* */
        numeroItaliano = "40,40";
        richiestoStr = "40,40";

        ottenutoStr = Lib.Testo.formatNumIta(numeroItaliano);
        if (!ottenutoStr.equals(richiestoStr)) {
            fail("40,40");
        }// fine del blocco if

        /* */
        numeroItaliano = "40.40";
        richiestoStr = "40,40";

        ottenutoStr = Lib.Testo.formatNumIta(numeroItaliano);
        if (!ottenutoStr.equals(richiestoStr)) {
            fail("40.40");
        }// fine del blocco if

    }
}