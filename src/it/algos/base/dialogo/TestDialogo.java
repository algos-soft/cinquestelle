/**
 * Title:     TestDialogo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-dic-2004
 */
package it.algos.base.dialogo;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.pagina.Pagina;

/**
 * TestDialogo - Classe di prova.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-dic-2004 ore 10.00.32
 */
public final class TestDialogo extends Object {

    /**
     * Costruttore semplice senza parametri. <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public TestDialogo() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Metodo di prova.
     * <p/>
     */
    private void test() {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo = null;
        String titolo = "";
        String messaggio = "";
        Pagina paginaDue = null;
        Azione azioneScheda = null;
        Azione azione = null;
        Campo campo = null;

        try {    // prova ad eseguire il codice
            dialogo = DialogoFactory.annullaConferma("Test");
//            voce = "voce del dialogo";
//            messaggio = "Testo del messaggio di spiegazione (anche molto lungo)";
//            messaggio += "\nSu due righe";

//            dialogo = DialogoFactory.avviso(messaggio);

//            dialogo.setTitoloPrimaPagina("pagina uno");
//            paginaDue = dialogo.addPagina("voce");

//            campo = dialogo.creaCampoRadioGruppo("testo2","alfa,beta");
//            dialogo.creaCampo("testo", "valore iniziale");

//            dialogo.creaCampo("testoDue");
//            dialogo.creaCampoCheck("testoQuattro");

//            dialogo.setValore("testoQuattro", new Boolean(true));

//            azioneScheda = this.getPortaleScheda().getAzione(Azione.CHIUDE_SCHEDA);
//            azione = azioneScheda.clonaAzione();
//            azione.getAzione().setEnabled(true);
//            dialogo.addAzione(azione, false);

//            dialogo.addBottone("annulla", false);
//            dialogo.addBottoni("esci,ripensaci");

//            dialogo.addBottoni("annullax,aonfermax");

//            dialogo.addAzione(Dialogo.AZIONE_CONFERMA);
//            dialogo.addAzione(Dialogo.AZIONE_ANNULLA);

//            dialogo.setDimensionamentoBottoni(Dialogo.DIMENSIONE_MASSIMA);
            dialogo.avvia();

//            boolean confermato = dialogo.isConfermato();

//            String alfa = dialogo.getBottonePremuto();
//            boolean beta = dialogo.getBool("testoQuattro");
            int a = 87;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Metodo principale di ingresso del programma.
     * <br>
     *
     * @param argomenti eventuali (quasi mai) parametri in ingresso
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stesso */
        TestDialogo istanza = new TestDialogo();

        /* metodo di prova da testare */
        istanza.test();

        /* termine normale del programma*/
        System.exit(0);
    }// fine del metodo main
}// fine della classe
