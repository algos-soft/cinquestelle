package it.algos.base.campo.logica.test;

import it.algos.base.campo.logica.CLCalcolato;
import junit.framework.TestCase;

import java.util.ArrayList;


public class CLCalcolatoTest extends TestCase {

    CLCalcolato clCalcolato;


    public void testEsegueOperazione() throws Exception {
        Object risultato = null;
        boolean continua = true;
        ArrayList<Object> valori;
        int intVal;
        int intValProg = 0;
        double doubleVal = 0;
        double doubleValProg = 0;
        int a;
        int b;
        double c;
        int richiesto;
        double richiestoReale;
        int risultatoIntero;
        double risultatoReale;
        Object valore;
        Object valUno;
        Object valDue;


        valori = new ArrayList<Object>();
        a = 17;
        b = 25;
        richiesto = 42;

        valori.add(a);
        valori.add(b);

        /* traverso tutta la collezione */
        for (Object val : valori) {
            if (val instanceof Integer) {
                intVal = (Integer)val;
                intValProg += intVal;
            }// fine del blocco if
        } // fine del ciclo for-each
        risultato = intValProg;

        if (!(risultato instanceof Integer)) {
            fail("non è un intero");
            continua = false;
        }// fine del blocco if

        if (continua) {
            risultatoIntero = (Integer)risultato;
            if (risultatoIntero != richiesto) {
                fail("non è quello giusto");
            }// fine del blocco if
        }// fine del blocco if


        valori = new ArrayList<Object>();
        a = 17;
        b = 25;
        c = 12.5;
        richiestoReale = 54.5;

        valori.add(a);
        valori.add(b);
        valori.add(c);

        /* traverso tutta la collezione */
        for (Object val : valori) {
            if (val instanceof Integer) {
                intVal = (Integer)val;
                doubleValProg += intVal;
            }// fine del blocco if
            if (val instanceof Double) {
                doubleVal = (Double)val;
                doubleValProg += doubleVal;
            }// fine del blocco if
        } // fine del ciclo for-each
        risultato = doubleValProg;

        if (!(risultato instanceof Double)) {
            fail("non è un piffero");
            continua = false;
        }// fine del blocco if

        if (continua) {
            risultatoReale = (Double)risultato;
            if (risultatoReale != richiestoReale) {
                fail("non è quello giusto");
            }// fine del blocco if
        }// fine del blocco if


        valori = new ArrayList<Object>();
        a = 7;
        b = 2;
        richiesto = 14;

        valori.add(a);
        valori.add(b);

        valore = valori.get(0);
        if (valore instanceof Integer) {
            intValProg = (Integer)valore;
        }// fine del blocco if

        /* traverso la collezione iniziando dal secondo elemento */
        if (valore instanceof Integer) {
            for (int k = 1; k < valori.size(); k++) {
                valore = valori.get(k);
                if (valore instanceof Integer) {
                    intVal = (Integer)valore;
                    intValProg *= intVal;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if
        risultato = intValProg;

        if (!(risultato instanceof Integer)) {
            fail("non è un intero (prodotto)");
            continua = false;
        }// fine del blocco if

        if (continua) {
            risultatoIntero = (Integer)risultato;
            if (risultatoIntero != richiesto) {
                fail("non è quello giusto (prodotto)");
            }// fine del blocco if
        }// fine del blocco if


        valori = new ArrayList<Object>();
        a = 7;
        c = 2.5;
        richiestoReale = 17.5;

        valori.add(a);
        valori.add(c);

        valore = valori.get(0);
        if (valore instanceof Integer) {
            intVal = (Integer)valore;
            doubleValProg = new Double(intVal);
        }// fine del blocco if
        if (valore instanceof Double) {
            doubleValProg = (Double)valore;
        }// fine del blocco if

        /* traverso la collezione iniziando dal secondo elemento */
        for (int k = 1; k < valori.size(); k++) {
            valore = valori.get(k);
            if (valore instanceof Integer) {
                intVal = (Integer)valore;
                doubleVal = new Double(intVal);
            }// fine del blocco if
            if (valore instanceof Double) {
                doubleVal = (Double)valore;
            }// fine del blocco if
            doubleValProg *= doubleVal;

        } // fine del ciclo for
        risultato = doubleValProg;

        if (!(risultato instanceof Double)) {
            fail("non è un intero (prodotto2)");
            continua = false;
        }// fine del blocco if

        if (continua) {
            risultatoReale = (Double)risultato;
            if (risultatoReale != richiestoReale) {
                fail("non è quello giusto (prodotto2)");
            }// fine del blocco if
        }// fine del blocco if


    }
}