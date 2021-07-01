package it.algos.base.aggiornamento.test;

import it.algos.base.aggiornamento.Aggiornamento;
import it.algos.base.pref.Pref;
import it.algos.base.progetto.Progetto;
import junit.framework.TestCase;

public class AggiornamentoTest extends TestCase {

    Aggiornamento aggiornamento;


    public void testEsegue() throws Exception {
        Progetto prog;
        Pref pref;
        pref = new Pref();
        Aggiornamento agg;

        agg = new Aggiornamento();
        agg.setCartella("fatture");
        agg.esegue();

//        Aggiornamento.setCartella("fatture");
//
//        if (!Aggiornamento.esegue()) {
//            fail("test non ancora sviluppato");
//        }// fine del blocco if


    }
}