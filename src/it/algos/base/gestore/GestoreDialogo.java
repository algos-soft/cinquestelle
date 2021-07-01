/**
 * Title:     GestoreDialogo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      14-dic-2004
 */
package it.algos.base.gestore;

import it.algos.base.azione.Azione;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.errore.Errore;
import it.algos.base.portale.Portale;
import it.algos.base.portale.PortaleDialogo;

import java.awt.event.ActionEvent;
import java.util.EventObject;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 14-dic-2004 ore 17.53.24
 */
public final class GestoreDialogo extends GestoreBase {

    /**
     * dialogo di riferimento
     */
    Dialogo dialogo;


    /**
     * Costruttore completo.
     *
     * @param dialogo
     */
    public GestoreDialogo(Dialogo dialogo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setDialogo(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    /**
     * Recupera il dialogo che ha generato un evento.
     * <br>
     * Recupera il componente che ha generato l'evento
     * Risale tutta la gerarchia degli oggetti <i>parente</i>, cioe' i
     * componente che contengono il componente, fino a che trova un oggetto
     * di classe <code>Dialogo</code> oppure termina la catena di
     * componenti.
     *
     * @param unAzione azione che ha generato l'evento
     *
     * @return il dialogo che ha generato l'evento,
     *         null se non riesce a trovarlo
     */
    protected Dialogo getDialogoAzione(Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo = null;
        Portale portale = null;
        PortaleDialogo portaleDialogo = null;

        try {    // prova ad eseguire il codice

            portale = unAzione.getPortale();
            if (portale != null) {
                if (portale instanceof PortaleDialogo) {
                    portaleDialogo = (PortaleDialogo)portale;
                    dialogo = portaleDialogo.getDialogo();
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dialogo;
    }

//    /**
//     * Inserimento del carattere Enter in un campo testo.
//     * <p/>
//     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
//     * Recupera il campo interessato <br>
//     * Invoca il metodo delegato <br>
//     *
//     * @param unEvento evento generato dall'interfaccia utente
//     * @param unAzione oggetto interessato dall'evento
//     *
//     * @deprecated
//     */
//    @Override public void enter(KeyEvent unEvento, Azione unAzione) {
//        /* variabili e costanti locali di lavoro */
//        Dialogo dialogo = null;
//
//        try { // prova ad eseguire il codice
//            dialogo = this.getDialogoAzione(unAzione);
//            if (dialogo != null) {
////                dialogo.registra();
//            }// fine del blocco if
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//    }


    /**
     * Azione di conferma di un form.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
     * Invoca il metodo delegato <br>
     * Associato all'azione Registra della scheda
     * Associato alle azioni Conferma o Registra del dialogo
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    @Override public void confermaForm(EventObject unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try { // prova ad eseguire il codice
            dialogo = this.getDialogoAzione(unAzione);
            if (dialogo != null) {
                dialogo.confermaRegistra();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone conferma in un <code>Dialogo</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzConfermaDialogo</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void confermaDialogo(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try { // prova ad eseguire il codice
            dialogo = this.getDialogoAzione(unAzione);
            if (dialogo != null) {
                dialogo.eventoConferma();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Bottone annulla in un <code>Dialogo</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAnnullaDialogo</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void annullaDialogo(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo;

        try { // prova ad eseguire il codice
            dialogo = this.getDialogoAzione(unAzione);
            if (dialogo != null) {
                dialogo.annullaDialogo();
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Bottone generico in un <code>Dialogo</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzDialogo</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public void azioneDialogo(ActionEvent unEvento, Azione unAzione) {
        /* variabili e costanti locali di lavoro */
        Dialogo dialogo = null;

        try { // prova ad eseguire il codice
            dialogo = this.getDialogoAzione(unAzione);
            if (dialogo != null) {
                dialogo.azioneDialogo(unAzione);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    private Dialogo getDialogo() {
        return dialogo;
    }


    private void setDialogo(Dialogo dialogo) {
        this.dialogo = dialogo;
    }
}// fine della classe
