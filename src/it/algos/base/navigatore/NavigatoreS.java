/**
 * Title:     NavigatoreTipoB
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-apr-2004
 */
package it.algos.base.navigatore;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.Portale;
import it.algos.base.scheda.Scheda;

/**
 * Navigatore con sola Scheda.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 21-apr-2004 ore 14.15.43
 */
public class NavigatoreS extends NavigatoreBase {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public NavigatoreS(Modulo unModulo) {
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
     * Regolazioni immediate di riferimenti e variabili.
     * <p/>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Portale portaleScheda;
//        Portale portaleNav;

        try {    // prova ad eseguire il codice

            this.setUsaPannelloUnico(true);
            this.setUsaFinestraPop(false);

            /* crea il Portale contenente la scheda nella superclasse */
            portaleScheda = this.creaPortaleScheda();

            /* aggiunge i due portali al portale Navigatore */
            this.addPortali(portaleScheda, null);

//            portaleNav = this.getPortaleNavigatore();
//            if (portaleNav != null) {
//                portaleNav.setUsaDialogo(true);
//            }// fine del blocco if
//
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice

            super.inizializza();

            /* Regola il flag di dismissione della scheda dopo la
             * chiusura con il pulsante di registrazione */
            this.setDismettiSchedaDopoRegistrazione(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Avvia ogni componente del navigatore, se presente <br>
     * Avvia il Gestore <br>
     */
    public void avvia() {

        /* un navigatore S all'avvio chiude sempre la scheda */
        this.richiediChiusuraSchedaNoDialogo(false, true);

        /* rimanda alla superclasse */
        super.avvia();

    }// fine del metodo lancia


    /**
     * Chiede al navigatore il permesso di chiuderlo.
     * <p/>
     * Presenta eventuali messaggi all'utente per risolvere situazioni aperte <br>
     * Tenta la chiusura della scheda.<br>
     * Se riuscito, rimand alla superclasse, altrimenti ritorna false.
     *
     * @return true se riuscito.
     */
    public boolean richiediChiusura() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        Scheda scheda;
        int codUscita;

        try {    // prova ad eseguire il codice

            scheda = this.getScheda();
            if (scheda != null) {
                codUscita = scheda.richiediChiusuraConDialogo(Scheda.BOTTONE_REGISTRA, true);
                if (codUscita == Scheda.ANNULLA) {
                    riuscito = false;
                }// fine del blocco if

            }// fine del blocco if

            if (riuscito) {
                riuscito = super.richiediChiusura();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Chiude effettivamente la scheda.
     * <p/>
     * Un Navigatore S dopo la chiusura della scheda chiude anche la finestra
     *
     * @param confermato true se confermato false se annullato
     */
    @Override protected void chiusuraEffettivaScheda(boolean confermato) {
        super.chiusuraEffettivaScheda(confermato);
        super.chiudeFinestra();
    }


    /**
     * Flag - seleziona l'icona piccola, media o grande.
     * <p/>
     *
     * @param unTipoIcona codice tipo icona (Codifica in ToolBar)
     *
     * @see it.algos.base.toolbar.ToolBar
     */
    public void setTipoIcona(int unTipoIcona) {
        try { // prova ad eseguire il codice
            this.getPortaleScheda().setTipoIcona(unTipoIcona);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
