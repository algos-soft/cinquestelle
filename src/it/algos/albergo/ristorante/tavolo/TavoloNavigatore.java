/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      16-gen-2005
 */
package it.algos.albergo.ristorante.tavolo;

import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;

/**
 * Business logic per Tavolo.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Gestisce la business-logic della GUI </li>
 * <li> Riceve le invocazioni da una classe di tipo <code>Gestore</code> </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 16-gen-2005 ore 11.36.31
 */
public final class TavoloNavigatore extends NavigatoreLS {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public TavoloNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setNomeChiave(Tavolo.Cam.navtavolo.get());
        this.setUsaFinestra(true);
        this.setUsaPannelloUnico(false);
    }// fine del metodo inizia


    public void inizializza() {
        super.inizializza();
    }// fine del metodo inizializza


    public void apreRicerca() {
        super.apreRicerca();
    }


    /**
     * Test dialoghi vari.
     * <p/>
     */
    private void testDialogo() {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try {    // prova ad eseguire il codice

//            dialogo = DialogoFactory.conferma();
//            dialogo.avvia();

            dialogo = DialogoFactory.conferma("questo e' il messaggio");
            dialogo.avvia();

//            dialogo = DialogoFactory.annullaConferma();
//            dialogo.avvia();
//
//            dialogo = DialogoFactory.annullaConferma("questo e' il messaggio");
//            dialogo.avvia();


            dialogo = new DialogoBase();

            dialogo.creaCampo("testo");
            dialogo.creaCampo("intero");

            dialogo.setMessaggio(
                    "Questo e' il testo del mio messaggio che potrebbe anche essere molto lungo\nquesta e' la seconda riga\nQuesta e' la terza riga che anch'essa potrebbe essere piuttosto lunga");
//            dialogo.addBottone("mio bottone");
//            dialogo.addBottone("bottone2",true);

            dialogo.avvia();

//            String stringa = dialogo.getBottonePremuto();
//            boolean confermato = dialogo.isConfermato();

//            int a = 87;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


}// fine della classe
