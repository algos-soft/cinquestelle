package it.algos.base.libreria.test;
/**
 * Created by IntelliJ IDEA.
 * User: gac
 * Date: 11-mag-2006
 * Time: 6.32.05
 * To change this template use File | Settings | File Templates.
 */

import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibDate;
import junit.framework.TestCase;

import java.util.Date;
import java.util.GregorianCalendar;

public class LibDateTest extends TestCase {

    LibDate libDate;


    public void testAdd() throws Exception {
        Date originale;
        Date convertita;
        Date richiesta;
        int giorni;

        /* aggiunta normale */
        /* il risultato deve essere vero */
        originale = new Date(2006, 5, 17);
        richiesta = new Date(2006, 5, 19);

        giorni = 2;
        convertita = Lib.Data.add(originale, giorni);

        if (!convertita.equals(richiesta)) {
            fail("aggiunta normale");
        }// fine del blocco if

        /* sottrazione */
        /* il risultato deve essere vero */
        originale = new Date(2006, 5, 17);
        richiesta = new Date(2006, 5, 8);

        giorni = -9;
        convertita = Lib.Data.add(originale, giorni);

        if (!convertita.equals(richiesta)) {
            fail("sottrazione");
        }// fine del blocco if

        /* cambio mese */
        /* il risultato deve essere vero */
        originale = new Date(2006, 3, 10);
        richiesta = new Date(2006, 4, 15);

        giorni = 35;
        convertita = Lib.Data.add(originale, giorni);

        if (!convertita.equals(richiesta)) {
            fail("cambio mese");
        }// fine del blocco if

        /* cambio anno */
        /* il risultato deve essere vero */
        originale = new Date(2006, 11, 24);
        richiesta = new Date(2007, 0, 1);

        giorni = 8;
        convertita = Lib.Data.add(originale, giorni);

        if (!convertita.equals(richiesta)) {
            fail("cambio anno");
        }// fine del blocco if
    }


    public void testIsPrecedente() throws Exception {
        Date riferimento;
        Date test;
        boolean convertita;
        boolean richiesta;

        /* data test precedente al riferimento */
        /* il risultato deve essere vero */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 5, 6);
        richiesta = true;

        convertita = Lib.Data.isPrecedente(riferimento, test);

        if (richiesta != convertita) {
            fail("test precedente al riferimento");
        }// fine del blocco if

        /* data test uguale al riferimento */
        /* il risultato deve essere falso */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 5, 17);
        richiesta = false;

        convertita = Lib.Data.isPrecedente(riferimento, test);

        if (richiesta != convertita) {
            fail("test precedente uguale al riferimento");
        }// fine del blocco if

        /* data test successivo al riferimento */
        /* il risultato deve essere falso */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 6, 17);
        richiesta = false;

        convertita = Lib.Data.isPrecedente(riferimento, test);

        if (richiesta != convertita) {
            fail("test precedente con data successiva");
        }// fine del blocco if
    }


    public void testIsPosteriore() throws Exception {
        Date riferimento;
        Date test;
        boolean convertita;
        boolean richiesta;

        /* data test posteriore al riferimento */
        /* il risultato deve essere vero */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 6, 6);
        richiesta = true;

        convertita = Lib.Data.isPosteriore(riferimento, test);

        if (richiesta != convertita) {
            fail("test posteriore al riferimento");
        }// fine del blocco if

        /* data test uguale al riferimento */
        /* il risultato deve essere falso */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 5, 17);
        richiesta = false;

        convertita = Lib.Data.isPosteriore(riferimento, test);

        if (richiesta != convertita) {
            fail("test posteriore uguale al riferimento");
        }// fine del blocco if

        /* data test precedente al riferimento */
        /* il risultato deve essere falso */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 4, 17);
        richiesta = false;

        convertita = Lib.Data.isPosteriore(riferimento, test);

        if (richiesta != convertita) {
            fail("test posteriore con data precedente");
        }// fine del blocco if
    }


    public void testIsPrecedenteUguale() throws Exception {
        Date riferimento;
        Date test;
        boolean convertita;
        boolean richiesta;

        /* data test precedente al riferimento */
        /* il risultato deve essere vero */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 5, 6);
        richiesta = true;

        convertita = Lib.Data.isPrecedenteUguale(riferimento, test);

        if (richiesta != convertita) {
            fail("test precedente al riferimento");
        }// fine del blocco if

        /* data test uguale al riferimento */
        /* il risultato deve essere vero */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 5, 17);
        richiesta = true;

        convertita = Lib.Data.isPrecedenteUguale(riferimento, test);

        if (richiesta != convertita) {
            fail("test precedente uguale al riferimento");
        }// fine del blocco if

        /* data test successivo al riferimento */
        /* il risultato deve essere falso */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 6, 17);
        richiesta = false;

        convertita = Lib.Data.isPrecedenteUguale(riferimento, test);

        if (richiesta != convertita) {
            fail("test precedente con data successiva");
        }// fine del blocco if
    }


    public void testIsPosterioreUguale() throws Exception {
        Date riferimento;
        Date test;
        boolean convertita;
        boolean richiesta;

        /* data test posteriore al riferimento */
        /* il risultato deve essere vero */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 6, 6);
        richiesta = true;

        convertita = Lib.Data.isPosterioreUguale(riferimento, test);

        if (richiesta != convertita) {
            fail("test posteriore al riferimento");
        }// fine del blocco if

        /* data test uguale al riferimento */
        /* il risultato deve essere vero */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 5, 17);
        richiesta = true;

        convertita = Lib.Data.isPosterioreUguale(riferimento, test);

        if (richiesta != convertita) {
            fail("test posteriore uguale al riferimento");
        }// fine del blocco if

        /* data test precedente al riferimento */
        /* il risultato deve essere falso */
        riferimento = new Date(2006, 5, 17);
        test = new Date(2006, 4, 17);
        richiesta = false;

        convertita = Lib.Data.isPosterioreUguale(riferimento, test);

        if (richiesta != convertita) {
            fail("test posteriore con data precedente");
        }// fine del blocco if
    }


    public void testIsCompresa() throws Exception {
        Date inizio;
        Date fine;
        Date test;
        boolean convertita;
        boolean richiesta;

        /* data test precedente all'intervallo */
        /* il risultato deve essere falso */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 6, 6);
        richiesta = false;

        convertita = Lib.Data.isCompresa(inizio, fine, test);

        if (richiesta != convertita) {
            fail("data test precedente all'intervallo");
        }// fine del blocco if

        /* data test compresa nell'intervallo */
        /* il risultato deve essere vero */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 5, 10);
        richiesta = true;

        convertita = Lib.Data.isCompresa(inizio, fine, test);

        if (richiesta != convertita) {
            fail("data test compresa nell'intervallo");
        }// fine del blocco if

        /* data test posteriore all'intervallo */
        /* il risultato deve essere falso */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 6, 10);
        richiesta = false;

        convertita = Lib.Data.isCompresa(inizio, fine, test);

        if (richiesta != convertita) {
            fail("test posteriore con data precedente");
        }// fine del blocco if

        /* data test al limite dell'intervallo */
        /* il risultato deve essere falso */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 5, 15);
        richiesta = false;

        convertita = Lib.Data.isCompresa(inizio, fine, test);

        if (richiesta != convertita) {
            fail("data test al limite dell'intervallo");
        }// fine del blocco if
    }


    public void testIsCompresaUguale() throws Exception {
        Date inizio;
        Date fine;
        Date test;
        boolean convertita;
        boolean richiesta;

        /* data test precedente all'intervallo */
        /* il risultato deve essere falso */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 6, 6);
        richiesta = false;

        convertita = Lib.Data.isCompresaUguale(inizio, fine, test);

        if (richiesta != convertita) {
            fail("data test precedente all'intervallo");
        }// fine del blocco if

        /* data test compresa nell'intervallo */
        /* il risultato deve essere vero */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 5, 10);
        richiesta = true;

        convertita = Lib.Data.isCompresaUguale(inizio, fine, test);

        if (richiesta != convertita) {
            fail("data test compresa nell'intervallo");
        }// fine del blocco if

        /* data test posteriore all'intervallo */
        /* il risultato deve essere falso */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 6, 10);
        richiesta = false;

        convertita = Lib.Data.isCompresaUguale(inizio, fine, test);

        if (richiesta != convertita) {
            fail("test posteriore con data precedente");
        }// fine del blocco if

        /* data test al limite dell'intervallo */
        /* il risultato deve essere vero */
        inizio = new Date(2006, 4, 15);
        fine = new Date(2006, 5, 15);
        test = new Date(2006, 5, 15);
        richiesta = true;

        convertita = Lib.Data.isCompresaUguale(inizio, fine, test);

        if (richiesta != convertita) {
            fail("data test al limite dell'intervallo");
        }// fine del blocco if
    }


    public void testGetMax() throws Exception {
        Date prima;
        Date seconda;
        Date risultato;
        Date richiesta;

        /* data test maggiore */
        /* il risultato deve essere falso */
        prima = new Date(2006, 4, 15);
        seconda = new Date(2006, 5, 15);
        richiesta = seconda;

        risultato = Lib.Data.getMax(prima, seconda);

        if (richiesta != risultato) {
            fail("data maz tra due");
        }// fine del blocco if
    }


    public void testGetGiorno() throws Exception {
        Date data;
        String risultato;
        String richiesta;
        GregorianCalendar cal;
        long sab22luglio2006;

        data = new Date(2006, 6, 15);
        richiesta = "sabato";

        risultato = Lib.Data.getGiorno(data);

        if (!richiesta.equals(risultato)) {
            fail("giorno sbagliato");
        }// fine del blocco if
    }


    public void testGetData() throws Exception {
        String valore;
        Date risultato;
        Date richiesta;

        valore = "12-07-2007T12";
        risultato = Lib.Data.getData(valore);

        int a = 87;

    }


    public void testIsPeriodoEscluso() throws Exception {
        Date iniRif;
        Date endRif;
        Date iniTest;
        Date endtest;
        boolean risultato;
        boolean richiesta;

        iniRif = new Date(2008, 5, 6);
        endRif = new Date(2008, 5, 18);

        /* test A */
        iniTest = new Date(2008, 4, 12);
        endtest = new Date(2008, 5, 8);
        richiesta = false;

        risultato = Lib.Data.isPeriodoEscluso(iniTest, endtest, iniRif, endRif);
        if (risultato != richiesta) {
            fail("test a");
        }// fine del blocco if

        /* test B */
        iniTest = new Date(2008, 5, 10);
        endtest = new Date(2008, 5, 22);
        richiesta = false;

        risultato = Lib.Data.isPeriodoEscluso(iniTest, endtest, iniRif, endRif);
        if (risultato != richiesta) {
            fail("test B");
        }// fine del blocco if

        /* test C */
        iniTest = new Date(2008, 5, 1);
        endtest = new Date(2008, 5, 5);
        richiesta = true;

        risultato = Lib.Data.isPeriodoEscluso(iniTest, endtest, iniRif, endRif);
        if (risultato != richiesta) {
            fail("test C");
        }// fine del blocco if

        /* test D */
        iniTest = new Date(2008, 5, 21);
        endtest = new Date(2008, 5, 23);
        richiesta = true;

        risultato = Lib.Data.isPeriodoEscluso(iniTest, endtest, iniRif, endRif);
        if (risultato != richiesta) {
            fail("test D");
        }// fine del blocco if

        /* test E */
        iniTest = new Date(2008, 5, 7);
        endtest = new Date(2008, 5, 17);
        richiesta = false;

        risultato = Lib.Data.isPeriodoEscluso(iniTest, endtest, iniRif, endRif);
        if (risultato != richiesta) {
            fail("test E");
        }// fine del blocco if

        /* test F */
        iniTest = new Date(2008, 5, 3);
        endtest = new Date(2008, 5, 19);
        richiesta = false;

        risultato = Lib.Data.isPeriodoEscluso(iniTest, endtest, iniRif, endRif);
        if (risultato != richiesta) {
            fail("test F");
        }// fine del blocco if

    }
}