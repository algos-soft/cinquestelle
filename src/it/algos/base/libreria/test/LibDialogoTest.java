package it.algos.base.libreria.test;

import it.algos.base.libreria.Lib;
import it.algos.base.libreria.LibDialogo;
import junit.framework.TestCase;

import java.util.Date;

public class LibDialogoTest extends TestCase {

    LibDialogo libDialogo;


    public void annullaConferma() throws Exception {
        boolean richiesto;
        boolean ottenuto;

        richiesto = false;
        ottenuto = Lib.Dial.annullaConferma("Prova. Premi annulla");

        if (richiesto != ottenuto) {
            fail("incipit errato");
        }// fine del blocco if
    }


    public void testCreaTesto() throws Exception {
        String messaggio;
        String suggerito;
        String richiesto;
        String ottenuto;

        messaggio = "Scrivi: prova";
        suggerito = "scrivi qui";
        richiesto = "prova";

        ottenuto = Lib.Dial.creaTesto(messaggio, suggerito);
        if (!ottenuto.equals(richiesto)) {
            fail("inserimento con default");
        }// fine del blocco if

        ottenuto = Lib.Dial.creaTesto(messaggio);
        if (!ottenuto.equals(richiesto)) {
            fail("inserimento con messaggio");
        }// fine del blocco if

        ottenuto = Lib.Dial.creaTesto();
        if (!ottenuto.equals(richiesto)) {
            fail("inserimento senza nulla");
        }// fine del blocco if

    }


    public void testCreaData() throws Exception {
        String messaggio;
        Date suggerita;
        Date richiesta;
        Date ottenuta;

        messaggio = "Inserisci la data di domani";
        suggerita = Lib.Data.getCorrente();
        richiesta = Lib.Data.add(suggerita, 1);

        ottenuta = Lib.Dial.creaData(messaggio, suggerita);
        if (!ottenuta.equals(richiesta)) {
            fail("data con default");
        }// fine del blocco if

        ottenuta = Lib.Dial.creaData(messaggio);
        if (!ottenuta.equals(richiesta)) {
            fail("data con messaggio");
        }// fine del blocco if

        ottenuta = Lib.Dial.creaData();
        if (!ottenuta.equals(richiesta)) {
            fail("data senza nulla");
        }// fine del blocco if

    }


    public void testCreaDataOggi() throws Exception {
        String messaggio;
        Date richiesta;
        Date ottenuta;

        messaggio = "Inserisci la data di domani";
        richiesta = Lib.Data.add(Lib.Data.getCorrente(), 1);

        ottenuta = Lib.Dial.creaDataOggi(messaggio);
        if (!ottenuta.equals(richiesta)) {
            fail("data con messaggio");
        }// fine del blocco if

        ottenuta = Lib.Dial.creaDataOggi();
        if (!ottenuta.equals(richiesta)) {
            fail("data senza nulla");
        }// fine del blocco if
    }


    public void testCreaIntero() throws Exception {
        String messaggio;
        int suggerito;
        int richiesto;
        int ottenuto;

        messaggio = "Scrivi: 87";
        suggerito = 87;
        richiesto = 87;

        ottenuto = Lib.Dial.creaIntero(messaggio, suggerito);
        if (ottenuto != richiesto) {
            fail("inserimento con default");
        }// fine del blocco if

        ottenuto = Lib.Dial.creaIntero(messaggio);
        if (ottenuto != richiesto) {
            fail("inserimento con messaggio");
        }// fine del blocco if

        ottenuto = Lib.Dial.creaIntero();
        if (ottenuto != richiesto) {
            fail("inserimento senza nulla");
        }// fine del blocco if

    }

}