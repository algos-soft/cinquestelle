package it.algos.base.libreria.test;

import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibClasse;
import junit.framework.TestCase;

public class LibClasseTest extends TestCase {

    LibClasse libClasse;


    public void testArgInt() throws Exception {
        int ricInt;
        int ottInt;
        int staInt;
        String ricStr;
        String ottStr;
        String staStr;
        boolean ricBool;
        boolean ottBool;
        boolean staBool;
        String[] argomenti;

        argomenti = new String[3];
        argomenti[0] = "prova";
        argomenti[1] = "13";
        argomenti[2] = "false";

        staInt = 24;
        staStr = "pippoz";
        staBool = true;

        ricInt = 13;
        ottInt = Lib.Clas.argInt(argomenti, 2, staInt);

        if (ricInt != ottInt) {
            fail("intero seconda riga");
        }// fine del blocco if


        ricInt = staInt;
        ottInt = Lib.Clas.argInt(argomenti, 1, staInt);

        if (ricInt != ottInt) {
            fail("intero prima riga");
        }// fine del blocco if

        ricStr = staStr;
        ottStr = Lib.Clas.argStr(argomenti, 4, staStr);

        if (!ricStr.equals(ottStr)) {
            fail("stringa riga inesistente");
        }// fine del blocco if

        ricBool = staBool;
        ottBool = Lib.Clas.argBool(argomenti, 1, staBool);

        if (ottBool != ricBool) {
            fail("booleano prima riga");
        }// fine del blocco if

        ricBool = false;
        ottBool = Lib.Clas.argBool(argomenti, 3, staBool);

        if (ottBool != ricBool) {
            fail("booleano terza riga");
        }// fine del blocco if
    }


    public void testGetArg() throws Exception {
        String argomenti;
        String[] ottenuta;
        String[] richiesta;

        richiesta = new String[3];
        richiesta[0] = "alfa=tre";
        richiesta[1] = "beta=12";
        richiesta[2] = "pippo=true";

        argomenti = " alfa=tre,beta=12,pippo";
        ottenuta = LibClasse.getArg(argomenti);
        if (!Lib.Array.isUguali(richiesta, ottenuta)) {
            fail("virgola");
        }// fine del blocco if

        argomenti = "alfa=tre;beta=12;pippo";
        ottenuta = LibClasse.getArg(argomenti);
        if (!Lib.Array.isUguali(richiesta, ottenuta)) {
            fail("punto e virgola");
        }// fine del blocco if

        argomenti = "alfa=tre&beta=12&pippo";
        ottenuta = LibClasse.getArg(argomenti);
        if (!Lib.Array.isUguali(richiesta, ottenuta)) {
            fail("e commerciale");
        }// fine del blocco if

        argomenti = "-alfa=tre -beta=12 -pippo";
        ottenuta = LibClasse.getArg(argomenti);
        if (!Lib.Array.isUguali(richiesta, ottenuta)) {
            fail("trattino");
        }// fine del blocco if
    }


    public void testIsValidi() throws Exception {
        Object arg1;
        Object arg2;
        Object arg3;
        Object arg4;
        Object arg5;
        boolean ottenuto;
        boolean richiesto;

        arg1 = "alfa";
        arg2 = "beta";
        arg3 = 74;
        arg4 = 0;
        arg5 = false;
        richiesto = true;

        ottenuto = Lib.Clas.isValidi(arg1);
        if (ottenuto != richiesto) {
            fail("alfa");
        }// fine del blocco if

        ottenuto = Lib.Clas.isValidi(arg1, arg2);
        if (ottenuto != richiesto) {
            fail("alfa + beta");
        }// fine del blocco if

        ottenuto = Lib.Clas.isValidi(arg1, arg2, arg3);
        if (ottenuto != richiesto) {
            fail("alfa + beta + 15");
        }// fine del blocco if

        richiesto = false;
        ottenuto = Lib.Clas.isValidi(arg1, arg4, arg2);
        if (ottenuto != richiesto) {
            fail("alfa + zero + beta");
        }// fine del blocco if

        richiesto = true;
        ottenuto = Lib.Clas.isValidi(arg1, arg5, arg3);
        if (ottenuto != richiesto) {
            fail("alfa + boolean + beta");
        }// fine del blocco if

    }
}