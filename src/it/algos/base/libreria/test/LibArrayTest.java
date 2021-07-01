package it.algos.base.libreria.test;

import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibArray;

import javax.swing.*;
import java.util.ArrayList;

import junit.framework.TestCase;

public class LibArrayTest extends TestCase {

    LibArray libArray;

    /**
     * tag per la stringa virgola
     */
    private static final String VIRGOLA = ",";


    public void testCreaLista() throws Exception {
        String separatore;
        char sepChar;
        String stringa;
        ArrayList<String> richiesta;
        ArrayList<String> convertita;
        boolean testValido = true;
        String[] arrayString;
        int[] arrayInt;
        ArrayList<Integer> richiestaInt;
        ArrayList<Integer> convertitaInt;
        boolean[] arrayBool;
        ArrayList<Boolean> richiestaBool;
        ArrayList<Boolean> convertitaBool;
        Object[] arrayObj;
        ArrayList<Object> richiestaObj;
        ArrayList<Object> convertitaObj;

        richiesta = new ArrayList<String>();

        /* stringa con virgola e senza spazi */
        /* il risultato deve essere vero */
        stringa = "alfa,beta,gamma";
        separatore = ",";
        richiesta.add("alfa");
        richiesta.add("beta");
        richiesta.add("gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, separatore);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista da stringa");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* stringa con virgole iniziali */
        /* il risultato deve essere vero */
        stringa = ",alfa,beta,gamma,";
        separatore = ",";
        richiesta = new ArrayList<String>();
        richiesta.add("alfa");
        richiesta.add("beta");
        richiesta.add("gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, separatore);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista da stringa con virgole iniziali");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* stringa con spazi */
        /* il risultato deve essere vero */
        stringa = "alfa, beta , gamma";
        separatore = ",";
        richiesta = new ArrayList<String>();
        richiesta.add("alfa");
        richiesta.add("beta");
        richiesta.add("gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, separatore);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista da stringa con spazi");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* stringa vuota */
        /* il risultato deve essere vero */
        stringa = "";
        separatore = ",";
        richiesta = new ArrayList<String>();

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, separatore);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista da stringa vuota");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* stringa nulla */
        /* il risultato deve essere vero */
        separatore = ",";
        richiesta = new ArrayList<String>();

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(null, separatore);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista da stringa nulla");
        }// fine del blocco if

//        /* reset delle condizioni per esempio successivo */
//        testValido = true;
//
//        /* separatore vuoto */
//        /* il risultato deve essere vero */
//        stringa = "alfa, beta , gamma";
//        separatore = "";
//        richiesta = new ArrayList<String>();
//        richiesta.add("alfa, beta , gamma");
//
//        /* recupera la lista dalla stringa */
//        convertita = Lib.Array.creaLista(stringa, separatore);
//
//        /* la lista deve avere le stesse dimensioni della richiesta */
//        if (convertita.size() != richiesta.size()) {
//            testValido = false;
//        }// fine del blocco if
//
//        /* ogni elemento della lista deve corrispondere alla richiesta */
//        if (testValido) {
//            for (int k = 0; k < convertita.size(); k++) {
//                if (!convertita.get(k).equals(richiesta.get(k))) {
//                    testValido = false;
//                }// fine del blocco if
//            } // fine del ciclo for
//        }// fine del blocco if
//
//        if (!testValido) {
//            fail("lista da stringa con separatore vuoto");
//        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* separatore nullo */
        /* il risultato deve essere vero */
        stringa = "alfa, beta , gamma";
        richiesta = new ArrayList<String>();
        richiesta.add("alfa, beta , gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, null);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista da stringa con separatore nullo");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* separatore char */
        /* il risultato deve essere vero */
        stringa = "alfa,beta,gamma";
        sepChar = ',';
        richiesta = new ArrayList<String>();
        richiesta.add("alfa");
        richiesta.add("beta");
        richiesta.add("gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, sepChar);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista con separatore char");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* separatore char vuoto (spazio) */
        /* il risultato deve essere vero */
        stringa = "alfa,beta,gamma";
        sepChar = ' ';
        richiesta = new ArrayList<String>();
        richiesta.add("alfa,beta,gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, sepChar);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista con separatore char vuoto");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* separatore char nullo */
        /* il risultato deve essere vero */
        stringa = "alfa,beta,gamma";
        richiesta = new ArrayList<String>();
        richiesta.add("alfa,beta,gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa, null);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista con separatore char nullo");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* parametri tutti nulli */
        /* il risultato deve essere vero */
        richiesta = new ArrayList<String>();

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(null, null);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista con parametri nulli");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* stringa con virgola e senza separatore */
        /* il risultato deve essere vero */
        stringa = "alfa,beta,gamma";
        richiesta = new ArrayList<String>();
        richiesta.add("alfa");
        richiesta.add("beta");
        richiesta.add("gamma");

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(stringa);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista senza separatore");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* array di stringhe */
        /* il risultato deve essere vero */
        arrayString = new String[3];
        arrayString[0] = "alfa";
        arrayString[1] = "beta";
        arrayString[2] = "gamma";

        richiesta = new ArrayList<String>();
        richiesta.add(arrayString[0]);
        richiesta.add(arrayString[1]);
        richiesta.add(arrayString[2]);

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaLista(arrayString);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("array di stringhe");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* array di interi */
        /* il risultato deve essere vero */
        arrayInt = new int[4];
        arrayInt[0] = 273;
        arrayInt[1] = -2;
        arrayInt[2] = 0;
        arrayInt[3] = 1825;

        richiestaInt = new ArrayList<Integer>();
        richiestaInt.add(arrayInt[0]);
        richiestaInt.add(arrayInt[1]);
        richiestaInt.add(arrayInt[2]);
        richiestaInt.add(arrayInt[3]);

        /* recupera la lista dalla stringa */
        convertitaInt = Lib.Array.creaLista(arrayInt);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertitaInt.size() != richiestaInt.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertitaInt.size(); k++) {
                if (!convertitaInt.get(k).equals(richiestaInt.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("array di interi");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* array di booleani */
        /* il risultato deve essere vero */
        arrayBool = new boolean[3];
        arrayBool[0] = false;
        arrayBool[1] = true;
        arrayBool[2] = true;

        richiestaBool = new ArrayList<Boolean>();
        richiestaBool.add(arrayBool[0]);
        richiestaBool.add(arrayBool[1]);
        richiestaBool.add(arrayBool[2]);

        /* recupera la lista dalla stringa */
        convertitaBool = Lib.Array.creaLista(arrayBool);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertitaBool.size() != richiestaBool.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertitaBool.size(); k++) {
                if (!convertitaBool.get(k).equals(richiestaBool.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("array di booleani");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* array di oggetti */
        /* il risultato deve essere vero */
        arrayObj = new Object[4];
        arrayObj[0] = new JLabel();
        arrayObj[1] = true;
        arrayObj[2] = 27;
        arrayObj[3] = "test";

        richiestaObj = new ArrayList<Object>();
        richiestaObj.add(arrayObj[0]);
        richiestaObj.add(arrayObj[1]);
        richiestaObj.add(arrayObj[2]);
        richiestaObj.add(arrayObj[3]);

        /* recupera la lista dalla stringa */
        convertitaObj = Lib.Array.creaLista(arrayObj);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertitaObj.size() != richiestaObj.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertitaObj.size(); k++) {
                if (!convertitaObj.get(k).equals(richiestaObj.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("array di oggetti");
        }// fine del blocco if

    }


    /**
     * Converte una lista di Integer in un array di interi.
     * <p/>
     * Esegue solo se la lista non è nulla e non è vuota <br>
     * <p/>
     * param lista da convertire
     * <p/>
     * return array di interi contenuti nella lista
     */
    public void testCreaIntArray() throws Exception {
        boolean testValido;
        ArrayList<Integer> originale;
        int[] richiesta;
        int[] convertita;

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* array di interi */
        /* il risultato deve essere vero */
        originale = new ArrayList<Integer>();
        originale.add(273);
        originale.add(-2);
        originale.add(0);
        originale.add(1825);

        richiesta = new int[4];
        richiesta[0] = originale.get(0);
        richiesta[1] = originale.get(1);
        richiesta[2] = originale.get(2);
        richiesta[3] = originale.get(3);

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaIntArray(originale);

        /* la lista deve avere le stesse dimensioni della richiesta */
        if (convertita.length != richiesta.length) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.length; k++) {
                if (!(convertita[k] == (richiesta[k]))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("array di interi");
        }// fine del blocco if

        /* array di interi vuoto */
        /* il ritorno deve essere nullo */
        originale = new ArrayList<Integer>();

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaIntArray(originale);

        if (convertita != null) {
            fail("array di interi vuoto");
        }// fine del blocco if

        /* array di interi nullo */
        /* il ritorno deve essere nullo */

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.creaIntArray(null);

        if (convertita != null) {
            fail("array di interi nullo");
        }// fine del blocco if
    }


    /**
     * Comparazione di due arrays di interi.
     * <p/>
     * Esegue solo se i due arrays non sono nulli <br>
     * <p/>
     * param primo   array di interi param secondo array di interi
     * <p/>
     * return true se hanno le stesse dimensioni e gli stessi valori.
     */
    public void testIsUguali() throws Exception {
        boolean convertita;
        int[] arrayUno;
        int[] arrayDue;

        /* confronto di due array di interi */
        /* il risultato deve essere vero */
        arrayUno = new int[4];
        arrayUno[0] = 273;
        arrayUno[1] = -2;
        arrayUno[2] = 0;
        arrayUno[3] = 1825;

        arrayDue = new int[4];
        arrayDue[0] = arrayUno[0];
        arrayDue[1] = arrayUno[1];
        arrayDue[2] = arrayUno[2];
        arrayDue[3] = arrayUno[3];

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.isUguali(arrayUno, arrayDue);

        if (!convertita) {
            fail("confronto di due array di interi");
        }// fine del blocco if

        /* confronto di due array di interi di diversa lunghezza*/
        /* il risultato deve essere falso */
        arrayUno = new int[5];
        arrayUno[0] = 273;
        arrayUno[1] = -2;
        arrayUno[2] = 0;
        arrayUno[3] = 1825;
        arrayUno[4] = 117;

        arrayDue = new int[4];
        arrayDue[0] = arrayUno[0];
        arrayDue[1] = arrayUno[1];
        arrayDue[2] = arrayUno[2];
        arrayDue[3] = arrayUno[3];

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.isUguali(arrayUno, arrayDue);

        if (convertita) {
            fail("confronto di due array di interi di lunghezza diversa");
        }// fine del blocco if

        /* confronto di due array di interi di contenuto diverso*/
        /* il risultato deve essere falso */
        arrayUno = new int[4];
        arrayUno[0] = 273;
        arrayUno[1] = -2;
        arrayUno[2] = 0;
        arrayUno[3] = 1825;

        arrayDue = new int[4];
        arrayDue[0] = arrayUno[0];
        arrayDue[1] = arrayUno[1];
        arrayDue[2] = arrayUno[2];
        arrayDue[3] = 1826;

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.isUguali(arrayUno, arrayDue);

        if (convertita) {
            fail("confronto di due array di interi di contenuto diverso");
        }// fine del blocco if

        /* confronto di due array di interi di cui uno nullo*/
        /* il risultato deve essere falso */
        arrayUno = new int[4];
        arrayUno[0] = 273;
        arrayUno[1] = -2;
        arrayUno[2] = 0;
        arrayUno[3] = 1825;

        /* recupera la lista dalla stringa */
        convertita = Lib.Array.isUguali(arrayUno, null);

        if (convertita) {
            fail("confronto di due array di interi di cui uno nullo");
        }// fine del blocco if

    }


    public void testListaUnica() throws Exception {
        ArrayList<Object> originale;
        ArrayList<Object> richiesta;
        ArrayList<Object> convertita;
        boolean testValido = true;

        /* lista di oggetti generici */
        /* il risultato  essere vero */
        originale = new ArrayList<Object>();
        originale.add(28);
        originale.add(new JLabel());
        originale.add(51);
        originale.add(28);
        originale.add(28);
        originale.add(new JTextField());
        originale.add(new JTextField());

        richiesta = new ArrayList<Object>();
        richiesta.add(originale.get(0));
        richiesta.add(originale.get(1));
        richiesta.add(originale.get(2));
        richiesta.add(originale.get(5));
        richiesta.add(originale.get(6));

        /* recupera la lista unica */
        convertita = Lib.Array.listaUnica(originale);

        /* la lista convertita deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista di oggetti generici");
        }// fine del blocco if

        /* reset delle condizioni per esempio successivo */
        testValido = true;

        /* lista di oggetti generici con due JTextField identici */
        /* il risultato essere vero */
        originale = new ArrayList<Object>();
        originale.add(28);
        originale.add(new JLabel());
        originale.add(51);
        originale.add(28);
        originale.add(28);
        originale.add(new JTextField());
        originale.add(originale.get(5));

        richiesta = new ArrayList<Object>();
        richiesta.add(originale.get(0));
        richiesta.add(originale.get(1));
        richiesta.add(originale.get(2));
        richiesta.add(originale.get(5));

        /* recupera la lista unica */
        convertita = Lib.Array.listaUnica(originale);

        /* la lista convertita deve avere le stesse dimensioni della richiesta */
        if (convertita.size() != richiesta.size()) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento della lista deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < convertita.size(); k++) {
                if (!convertita.get(k).equals(richiesta.get(k))) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!testValido) {
            fail("lista di oggetti con due JTextField identici");
        }// fine del blocco if
    }


    public void testGetPosizione() throws Exception {
        Object[] matrice;
        ArrayList<Object> lista;
        Object oggetto;
        int richiesto;
        int risultato;
        int[] matriceInt;
        int intero;

        /* posizione normale in un array */
        /* il risultato deve essere 2 */
        matrice = new Object[4];
        matrice[0] = 273;
        matrice[1] = new JLabel();
        matrice[2] = "mario";
        matrice[3] = new JTextField();
        richiesto = 2;
        oggetto = matrice[richiesto];

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(matrice, oggetto);

        if (!(risultato == richiesto)) {
            fail("posizione normale in un array");
        }// fine del blocco if

        /* oggetto  esistente in un array */
        /* la stringa � lo stesso oggetto */
        /* il risultato deve essere 2 */
        matrice = new Object[4];
        matrice[0] = 273;
        matrice[1] = new JLabel();
        matrice[2] = "mario";
        matrice[3] = new JTextField();
        richiesto = 2;
        oggetto = "mario";

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(matrice, oggetto);

        if (!(risultato == richiesto)) {
            fail("oggetto stringa esistente in un array");
        }// fine del blocco if

//        /* oggetto non esistente in un array */
//        /* la stringa creata è un oggetto diverso */
//        /* il risultato deve essere -1 */
//        matrice = new Object[4];
//        matrice[0] = 273;
//        matrice[1] = new JLabel();
//        matrice[2] = "mario";
//        matrice[3] = new JTextField();
//        richiesto = -1;
//        oggetto = new String("mario");
//
//        /* recupera la posizione */
//        risultato = Lib.Array.getPosizione(matrice, oggetto);
//
//        if (!(risultato == richiesto)) {
//            fail("oggetto nuova stringa non esistente in un array");
//        }// fine del blocco if

//        /* oggetto non esistente in un array */
//        /* il risultato deve essere -1 */
//        matrice = new Object[4];
//        matrice[0] = 273;
//        matrice[1] = new JLabel();
//        matrice[2] = "mario";
//        matrice[3] = new JTextField();
//        richiesto = -1;
//        oggetto = new JLabel();
//
//        /* recupera la posizione */
//        risultato = Lib.Array.getPosizione(matrice, oggetto);
//
//        if (!(risultato == richiesto)) {
//            fail("oggetto nuova label non esistente in un array");
//        }// fine del blocco if

        //======================================================================
        /* posizione normale in un array di interi */
        /* il risultato deve essere 2 */
        matriceInt = new int[4];
        matriceInt[0] = 273;
        matriceInt[1] = -24;
        matriceInt[2] = 1250;
        matriceInt[3] = 0;
        richiesto = 2;
        intero = matriceInt[richiesto];

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(matriceInt, intero);

        if (!(risultato == richiesto)) {
            fail("posizione normale in un array di inetri");
        }// fine del blocco if

        /* oggetto non esistente in un array di interi */
        /* il risultato deve essere -1 */
        matriceInt = new int[4];
        matriceInt[0] = 273;
        matriceInt[1] = -24;
        matriceInt[2] = 1250;
        matriceInt[3] = 0;
        richiesto = -1;
        intero = -273;

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(matriceInt, intero);

        if (!(risultato == richiesto)) {
            fail("oggetto non esistente in un array di interi");
        }// fine del blocco if

        //======================================================================

        /* posizione normale in una lista */
        /* il risultato deve essere 2 */
        lista = new ArrayList<Object>();
        lista.add(273);
        lista.add(new JLabel());
        lista.add("mario");
        lista.add(new JTextField());
        richiesto = 2;
        oggetto = lista.get(richiesto - 1);

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(lista, oggetto);

        if (!(risultato == richiesto)) {
            fail("posizione normale in una lista");
        }// fine del blocco if

        /* oggetto esistente in una lista */
        /* la stringa � lo stesso oggetto */
        /* il risultato deve essere 3 */
        lista = new ArrayList<Object>();
        lista.add(273);
        lista.add(new JLabel());
        lista.add("mario");
        lista.add(new JTextField());
        richiesto = 3;
        oggetto = "mario";

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(lista, oggetto);

        if (!(risultato == richiesto)) {
            fail("oggetto stringa esistente in una lista");
        }// fine del blocco if

        /* oggetto non esistente in una lista */
        /* la stringa creata � un oggetto diverso */
        /* il risultato deve essere -1 */
        lista = new ArrayList<Object>();
        lista.add(273);
        lista.add(new JLabel());
        lista.add("mario");
        lista.add(new JTextField());
        richiesto = -1;
        oggetto = new String("mario");

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(lista, oggetto);

        if (!(risultato == richiesto)) {
            fail("oggetto nuova stringa non esistente in una lista");
        }// fine del blocco if

        /* oggetto non esistente in un array */
        /* il risultato deve essere -1 */
        lista = new ArrayList<Object>();
        lista.add(273);
        lista.add(new JLabel());
        lista.add("mario");
        lista.add(new JTextField());
        richiesto = -1;
        oggetto = new JLabel();

        /* recupera la posizione */
        risultato = Lib.Array.getPosizione(lista, oggetto);

        if (!(risultato == richiesto)) {
            fail("oggetto nuova label non esistente in una lista");
        }// fine del blocco if

    }


    public void testIsEsiste() throws Exception {
        Object[] matrice;
        ArrayList<Object> lista;
        Object oggetto;
        boolean richiesto;
        boolean risultato;
        int[] matriceInt;
        int intero;

        /* esistenza in un array di oggetti */
        /* il risultato deve essere true */
        matrice = new Object[4];
        matrice[0] = 273;
        matrice[1] = new JLabel();
        matrice[2] = "mario";
        matrice[3] = new JTextField();
        richiesto = true;
        oggetto = matrice[1];

        /* recupera il risultato */
        risultato = Lib.Array.isEsiste(matrice, oggetto);

        if (!(risultato == richiesto)) {
            fail("esistenza in un array di oggetti");
        }// fine del blocco if

//        /* non esistenza in un array di oggetti */
//        /* il risultato deve essere false */
//        matrice = new Object[4];
//        matrice[0] = 273;
//        matrice[1] = new JLabel();
//        matrice[2] = "mario";
//        matrice[3] = new JTextField();
//        richiesto = false;
//        oggetto = new JLabel();
//
//        /* recupera il risultato */
//        risultato = Lib.Array.isEsiste(matrice, oggetto);
//
//        if (!(risultato == richiesto)) {
//            fail("nonesistenza in un array di oggetti");
//        }// fine del blocco if

        //======================================================================

        /* esistenza in un array di interi */
        /* il risultato deve essere true */
        matriceInt = new int[4];
        matriceInt[0] = 273;
        matriceInt[1] = -24;
        matriceInt[2] = 1250;
        matriceInt[3] = 0;
        richiesto = true;
        intero = 273;

        /* recupera il risultato */
        risultato = Lib.Array.isEsiste(matriceInt, intero);

        if (!(risultato == richiesto)) {
            fail("esistenza in un array di interi");
        }// fine del blocco if

        /* non esistenza in un array di interi */
        /* il risultato deve essere false */
        matriceInt = new int[4];
        matriceInt[0] = 273;
        matriceInt[1] = -24;
        matriceInt[2] = 1250;
        matriceInt[3] = 0;
        richiesto = false;
        intero = -273;

        /* recupera il risultato */
        risultato = Lib.Array.isEsiste(matriceInt, intero);

        if (!(risultato == richiesto)) {
            fail("non esistenza in un array di interi");
        }// fine del blocco if

        //======================================================================

        /* esistenza in una lista di oggetti */
        /* il risultato deve essere true */
        lista = new ArrayList<Object>();
        lista.add(273);
        lista.add(new JLabel());
        lista.add("mario");
        lista.add(new JTextField());
        richiesto = true;
        oggetto = lista.get(1);

        /* recupera la posizione */
        risultato = Lib.Array.isEsiste(lista, oggetto);

        if (!(risultato == richiesto)) {
            fail("esistenza in una lista di oggetti");
        }// fine del blocco if

        //======================================================================

        /* non esistenza in una lista di oggetti */
        /* il risultato deve essere falso */
        lista = new ArrayList<Object>();
        lista.add(273);
        lista.add(new JLabel());
        lista.add("mario");
        lista.add(new JTextField());
        richiesto = false;
        oggetto = new JLabel();

        /* recupera la posizione */
        risultato = Lib.Array.isEsiste(lista, oggetto);

        if (!(risultato == richiesto)) {
            fail("non esistenza in una lista di oggetti");
        }// fine del blocco if
    }


    public void testIntToString() throws Exception {
        int[] matrice;
        String[] richiesto;
        String[] risultato;
        boolean testValido = true;

        /* esistenza in un array di oggetti */
        /* il risultato deve essere true */
        matrice = new int[4];
        matrice[0] = 273;
        matrice[1] = -24;
        matrice[2] = 1250;
        matrice[3] = 0;
        richiesto = new String[4];
        richiesto[0] = "273";
        richiesto[1] = "-24";
        richiesto[2] = "1250";
        richiesto[3] = "0";

        /* recupera il risultato */
        risultato = Lib.Array.intToString(matrice);

        /* l'array risultato deve avere le stesse dimensioni della richiesta */
        if (risultato.length != richiesto.length) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento del risultato deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < risultato.length; k++) {
                if (!risultato[k].equals(richiesto[k])) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!(testValido)) {
            fail("array di interi in array di stringhe");
        }// fine del blocco if
    }


    /**
     * Gli elementi della stringa possono essere separati da virgola o da spazio <br>
     */
    public void testGetArray() throws Exception {
        String stringa;
        int[] richiesto;
        int[] risultato;
        boolean testValido = true;

        richiesto = new int[4];
        richiesto[0] = 273;
        richiesto[1] = -24;
        richiesto[2] = 1250;
        richiesto[3] = 0;

        /* virgola */
        stringa = "273,-24,1250,0";

        /* recupera il risultato */
        risultato = Lib.Array.getArray(stringa);

        /* l'array risultato deve avere le stesse dimensioni della richiesta */
        if (risultato.length != richiesto.length) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento del risultato deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < risultato.length; k++) {
                if (risultato[k] != richiesto[k]) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!(testValido)) {
            fail("array di interi da stringa separati da virgola");
        }// fine del blocco if

        /* spazio */
        stringa = "273 -24 1250 0";

        /* recupera il risultato */
        risultato = Lib.Array.getArray(stringa);

        /* l'array risultato deve avere le stesse dimensioni della richiesta */
        if (risultato.length != richiesto.length) {
            testValido = false;
        }// fine del blocco if

        /* ogni elemento del risultato deve corrispondere alla richiesta */
        if (testValido) {
            for (int k = 0; k < risultato.length; k++) {
                if (risultato[k] != richiesto[k]) {
                    testValido = false;
                }// fine del blocco if
            } // fine del ciclo for
        }// fine del blocco if

        if (!(testValido)) {
            fail("array di interi da stringa separati da spazio");
        }// fine del blocco if

    }


    public void testIntersezione() throws Exception {
        String[] primo;
        String[] secondo;
        String[] richiesto;
        String[] ottenuto;

        primo = new String[3];
        primo[0] = "alfa";
        primo[1] = "alfadue";
        primo[2] = "alfatre";

        secondo = new String[3];
        secondo[0] = "alfa";
        secondo[1] = "alfdddadue";
        secondo[2] = "alfatre";

        richiesto = new String[2];
        richiesto[0] = "alfa";
        richiesto[1] = "alfatre";

        ottenuto = Lib.Array.intersezione(primo, secondo);

        if (!(Lib.Array.isUguali(ottenuto, richiesto))) {
            fail("intersezione di array");
        }// fine del blocco if
    }


    public void testDifferenza() throws Exception {
        String[] primo;
        String[] secondo;
        String[] richiesto;
        String[] ottenuto;

        primo = new String[4];
        primo[0] = "alfa";
        primo[1] = "alfadue";
        primo[2] = "alfatre";
        primo[3] = "alfaquattro";

        secondo = new String[2];
        secondo[0] = "alfa";
        secondo[1] = "alfatre";

        richiesto = new String[2];
        richiesto[0] = "alfadue";
        richiesto[1] = "alfaquattro";

        ottenuto = Lib.Array.differenza(primo, secondo);

        if (!(Lib.Array.isUguali(ottenuto, richiesto))) {
            fail("differnza di array");
        }// fine del blocco if
    }


    public void testPrestazioni() throws Exception {
        String[] primo;
        String[] secondo;
        String[] richiesto;
        String[] ottenuto;
        int dim = 30000;
        long inizio;
        long fine = 0;
        long durata;
        long prog;

        primo = new String[dim];
        for (int k = 0; k < dim; k++) {
            primo[k] = k + "primo";
        } // fine del ciclo for


        secondo = new String[dim];
        for (int k = 0; k < dim; k++) {
            secondo[k] = k + "secondo";
        } // fine del ciclo for

        richiesto = new String[0];

        inizio = System.currentTimeMillis();

        ottenuto = Lib.Array.intersezione(primo, secondo);

        fine = System.currentTimeMillis();
        durata = fine - inizio;
        durata = durata / 1000;
        System.out.println("Tempo: " +
                durata +
                " secondi per " +
                dim +
                " righe della matrice semplice");


        if (!(Lib.Array.isUguali(ottenuto, richiesto))) {
            fail("tempo somma");
        }// fine del blocco if
    }

}