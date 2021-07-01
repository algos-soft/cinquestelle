/**
 * Title:     NavigatoreTipoB
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      21-apr-2004
 */
package it.algos.base.navigatore;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.info.InfoLista;
import it.algos.base.portale.Portale;
import it.algos.base.pref.Pref;
import it.algos.base.scheda.Scheda;

/**
 * Navigatore con Lista e Scheda, senza finestra.
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
public class NavigatoreLS extends NavigatoreBase {

    /**
     * Costruttore base senza parametri.
     * <p/>
     * Utilizzato solo per debug
     */
    public NavigatoreLS() {
        super();
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public NavigatoreLS(Modulo unModulo) {
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
        Portale portaleLista;
        Portale portaleScheda;

        try {    // prova ad eseguire il codice

            /* crea il Portale contenente la lista nella superclasse */
            portaleLista = this.creaPortaleLista();

            /* crea il Portale contenente la scheda nella superclasse */
            portaleScheda = this.creaPortaleScheda();

            /* aggiunge i due portali al portale Navigatore */
            this.addPortali(portaleLista, portaleScheda);

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
        /* variabili e costanti locali di lavoro */
        boolean dismetti = false;

        try { // prova ad eseguire il codice

            super.inizializza();

            /* Regola il flag di dismissione della scheda dopo la
             * chiusura con il pulsante di registrazione.
             * Se il navigatore e' a pannello unico
             * la scheda viene dismessa dopo la chiusura,
             * Se il navigatore non e' a pannello unico,
             * usa il valore nelle preferenze. */
            if (this.isUsaPannelloUnico()) {
                dismetti = true;
            } else {
                dismetti = Pref.GUI.chiude.is();
            }// fine del blocco if-else
            this.setDismettiSchedaDopoRegistrazione(dismetti);

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
     */
    public void avvia() {

        /* un navigatore LS all'avvio chiude sempre la scheda */
        this.richiediChiusuraSchedaNoDialogo(false, true);

        /* rimanda alla superclasse */
        super.avvia();

    }// fine del metodo lancia


    /**
     * Ritorna il pacchetto di informazioni del portale Lista.
     * <p/>
     * Puo' essere sovrascritto dalle sottoclassi per leggere
     * e/o modificare le informazioni di stato prima che vengano
     * utilizzate per sincronizzare la GUI.
     *
     * @param info il pacchetto di informazioni per la sincronizzazione
     */
    public InfoLista getInfoLista(InfoLista info) {

        try { // prova ad eseguire il codice

            /* regola ulteriormente il pacchetto di informazioni di stato lista
             * se il record selezionato in lista e' in aperto in scheda,
             * disabilita il bottone modifica.
             * Se il record e' in modifica, disabilita anche il bottone cancella.*/
            Lista lista = this.getLista();
            Scheda scheda = this.getScheda();
            if ((lista != null) && (scheda != null)) {
                if (info.isRigheSelezionate()) {
                    if (info.getQuanteRigheSelezionate() == 1) {
                        int riga = lista.getTavola().getSelectedRow();
                        int codiceLista = lista.getModello().getChiave(riga);
                        int codiceScheda = scheda.getCodice();
                        if (codiceLista == codiceScheda) {

                            /* a questo punto il record selezionato in lista
                             * e' quello che e' caricato in scheda */

                            /* non abilito il bottone modifica di un record
                             * che e' gia' in modifica */
                            info.setEstratto(false);

                            /* se la scheda non e' chiudibile (per esempio e' stata
                             * modificata) allora non posso eliminare il record nella lista.
                             * Significativo solo se il navigatore opera a pannello doppio
                             * quindi ho a disposizione i comandi della lista anche
                             * quando sto operando nella scheda */
                            if (scheda.isChiudibile() == false) {
                                info.setPossoCancellareRecord(false);
                            }// fine del blocco if

                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return info;
    }


    /**
     * Dialogo di conferma utente prima dell'eliminazione.
     * <p/>
     * Controlla che nessuno dei record da eliminare sia aperto
     * nella scheda in stato non chiudibile (es. modificato).
     * Se il controllo passa, rimanda il controllo alla superclasse.
     * Se non passa, visualizza un messaggio e ritorna false.
     *
     * @param chiavi array di chiavi dei record da eliminare
     *
     * @return true se e' possibile eliminare i records
     */
    protected boolean confermaEliminazione(int[] chiavi) {
        /* variabili e costanti locali di lavoro */
        boolean conferma = false;
        String mex;
        int codiceScheda;
        boolean esisteScheda;

        mex = "Non puoi cancellare il record ";
        mex += "modificato nella scheda";

        try { // prova ad eseguire il codice

            conferma = true;
            codiceScheda = this.getScheda().getCodice();
            esisteScheda = Lib.Mat.isEsiste(chiavi, codiceScheda);
            if (esisteScheda) {
                if (!this.getScheda().isChiudibile()) {
                    new MessaggioAvviso(mex);
                    conferma = false;
                }// fine del blocco if
            }// fine del blocco if

            if (conferma) {
                conferma = super.confermaEliminazione(chiavi);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return conferma;
    } /* fine del metodo-azione */


    /**
     * Chiede al navigatore il permesso di chiuderlo.
     * <p/>
     * Presenta eventuali messaggi all'utente per risolvere situazioni aperte <br>
     * Tenta la chiusura della scheda.<br>
     * Se riuscito, rimanda alla superclasse, altrimenti ritorna false.
     *
     * @return true se riuscito.
     */
    public boolean richiediChiusura() {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = true;
        int codUscita;

        try {    // prova ad eseguire il codice

            codUscita = richiediChiusuraSchedaDialogo(Scheda.BOTTONE_REGISTRA, true);
            if (codUscita == Scheda.ANNULLA) {
                riuscito = false;
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
     * Flag - seleziona l'icona piccola, media o grande.
     * <p/>
     *
     * @param unTipoIcona codice tipo icona (Codifica in ToolBar)
     *
     * @see it.algos.base.toolbar.ToolBar
     */
    public void setTipoIcona(int unTipoIcona) {
        try { // prova ad eseguire il codice
            this.getPortaleLista().setTipoIcona(unTipoIcona);
            this.getPortaleScheda().setTipoIcona(unTipoIcona);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public void setUsaSelezione(boolean usaSelezione) {
        try { // prova ad eseguire il codice
            this.getPortaleLista().setUsaSelezione(usaSelezione);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * flag - usa il bottone di stampa lista.
     *
     * @param flag true per usare il bottone di stampa lista <br>
     */
    public void setUsaStampaLista(boolean flag) {
        try { // prova ad eseguire il codice
            this.getPortaleLista().setUsaStampa(flag);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera la lista pilota di questo Navigatore.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi.
     *
     * @return la lista pilota di questo navigatore
     */
    protected Lista getListaPilota() {
        return this.getLista();
    }

}// fine della classe
