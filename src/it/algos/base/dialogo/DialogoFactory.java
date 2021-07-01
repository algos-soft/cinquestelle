/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * Author:    gac
 * Date:      2-nov-2003
 */
package it.algos.base.dialogo;

import it.algos.base.errore.Errore;

/**
 * Factory per la creazione di oggetti del package Dialogo.
 * </p>
 * Questa classe astratta factory: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Factory Method</b> </li>
 * <li> Fornisce metodi <code>statici</code> per la  creazione degli oggetti di questo
 * package </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0  /  2 novembre 2003 ore 7.53
 */
public abstract class DialogoFactory {

    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public DialogoFactory() {
        /* rimanda al costruttore della superclasse */
        super();
    } /* fine del metodo costruttore completo */


    /**
     * Costruisce un dialogo con un bottone di conferma.
     * <p/>
     * Costruisce al volo un GestoreDialogo <br>
     * Costruisce un PortaleDialogo <br>
     * Inizializza il PortaleDialogo <br>
     * Costruisce un Dialogo <br>
     * Regola i parametri <br>
     * Crea ed aggiunge un bottone conferma <br>
     *
     * @param titolo della finestra dialogo
     *
     * @return il dialogo appena creato
     */
    public static Dialogo conferma(String titolo) {
        return new DialogoConferma(titolo);
    }

//    /**
//     * Costruisce un dialogo con messaggio e bottone di conferma.
//     * <p/>
//     *
//     * @param messaggio della finestra dialogo
//     *
//     * @return il dialogo appena creato
//     */
//    public static Dialogo conferma(String messaggio) {
//        /* invoca il metodo sovrascritto della classe */
//        return DialogoFactory.conferma("", messaggio);
//    }


    /**
     * Costruisce un dialogo con un bottone di conferma.
     * <p/>
     *
     * @return il dialogo appena creato
     */
    public static Dialogo conferma() {
        /* invoca il metodo sovrascritto della classe */
        return DialogoFactory.conferma("");
    }

//    /**
//     * Costruisce un dialogo con i bottoni annulla e conferma.
//     * <p/>
//     * Costruisce un Dialogo <br>
//     * Crea ed aggiunge un bottone annulla <br>
//     * Crea ed aggiunge un bottone conferma <br>
//     * La posizione dei due bottoni è fissa:
//     * annulla a sinistra e conferma a destra <br>
//     *
//     * @param voce    della finestra dialogo
//     * @param messaggio di avviso/spiegazione in alto
//     *
//     * @return il dialogo appena creato
//     */
//    public static Dialogo annullaConferma(String voce, String messaggio) {
//        return new DialogoAnnullaConferma(voce);
//    }

//    /**
//     * Costruisce un dialogo con messaggio e i bottoni annulla e conferma.
//     * <p/>
//     *
//     * @param messaggio della finestra dialogo
//     *
//     * @return il dialogo appena creato
//     */
//    public static Dialogo annullaConferma(String messaggio) {
//        /* invoca il metodo sovrascritto della classe */
//        return DialogoFactory.annullaConferma(messaggio);
//    }


    /**
     * Costruisce un dialogo con i bottoni annulla e conferma.
     * <p/>
     *
     * @return il dialogo appena creato
     */
    public static Dialogo annullaConferma() {
        /* invoca il metodo sovrascritto della classe */
        return new DialogoAnnullaConferma();
    }


    /**
     * Costruisce un dialogo con i bottoni annulla e conferma.
     * <p/>
     *
     * @param titolo della finestra
     *
     * @return il dialogo appena creato
     */
    public static Dialogo annullaConferma(String titolo) {
        /* invoca il metodo sovrascritto della classe */
        return new DialogoAnnullaConferma(titolo);
    }


    /**
     * Presenta un dialogo con i bottoni annulla e conferma.
     * <p/>
     *
     * @param titolo    della finestra
     * @param messaggio nel dialogo
     *
     * @return true se il dialogo è confermato
     *         false se il dialogo è annullato
     */
    public static boolean getConferma(String titolo, String messaggio) {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        Dialogo dialogo;

        try { // prova ad eseguire il codice
            dialogo = new DialogoAnnullaConferma(titolo);
            dialogo.setMessaggio(messaggio);
            dialogo.avvia();
            confermato = dialogo.isConfermato();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermato;
    }


    /**
     * Presenta un dialogo con due bottoni: annulla e conferma.
     * <p/>
     * Costruisce al volo un GestoreDialogo <br>
     * Costruisce un PortaleDialogo <br>
     * Inizializza il PortaleDialogo <br>
     * Costruisce un Dialogo <br>
     * Crea ed aggiunge un bottone annulla <br>
     * Crea ed aggiunge un bottone conferma <br>
     * La posizione dei due bottoni è fissa:
     * annulla a sinistra e conferma a destra <br>
     * Presenta il dialogo <br>
     *
     * @param titolo della finestra dialogo
     *
     * @return true se il dialogo è confermato
     *         false se il dialogo è annullato
     */
    public static boolean getConferma(String titolo) {
        /* variabili e costanti locali di lavoro */
        boolean confermato = false;
        Dialogo dialogo;

        try {    // prova ad eseguire il codice
            /* crea il dialogo */
            dialogo = DialogoFactory.annullaConferma(titolo);

            /* avvia il dialogo */
            dialogo.avvia();

            /* recupera la risposta */
            confermato = dialogo.isConfermato();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        /* valore di ritorno */
        return confermato;
    }

//    /**
//     * Costruisce un dialogo di avviso con un bottone di conferma.
//     * <p/>
//     * Costruisce al volo un DialogoAvviso <br>
//     * Costruisce un PortaleDialogo <br>
//     * Inizializza il PortaleDialogo <br>
//     * Regola i parametri <br>
//     * Crea ed aggiunge un bottone conferma <br>
//     * Avvia il dialogo <br>
//     *
//     * @param voce    della finestra dialogo
//     * @param messaggio di avviso/spiegazione in alto
//     *
//     * @return il dialogo appena creato
//     */
//    public static Dialogo avviso(String voce, String messaggio) {
//        /* variabili e costanti locali di lavoro */
//        Dialogo dialogo = null;
//        PortaleDialogo portale = null;
//
//        try { // prova ad eseguire il codice
//            /* crea il portale col dialogo */
//            portale = creaPortale(new DialogoAvviso());
//
//            /* recupera l'istanza della classe da ritornare */
//            dialogo = portale.getDialogo();
//
//            /* regola i parametri */
//            dialogo.setTitolo(voce);
//            dialogo.setMessaggio(messaggio);
//
//            /* aggiunge il bottone di conferma */
//            dialogo.addAzione(Dialogo.AZIONE_CONFERMA);
//
//            /* avvia il dialogo */
//            dialogo.avvia();
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch
//
//        /* valore di ritorno */
//        return dialogo;
//    }

//    /**
//     * Costruisce un dialogo di avviso con un bottone di conferma.
//     * <p/>
//     * Costruisce al volo un DialogoAvviso <br>
//     * Costruisce un PortaleDialogo <br>
//     * Inizializza il PortaleDialogo <br>
//     * Regola i parametri <br>
//     * Crea ed aggiunge un bottone conferma <br>
//     * Avvia il dialogo <br>
//     *
//     * @param messaggio di avviso/spiegazione in alto
//     *
//     * @return il dialogo appena creato
//     */
//    public static Dialogo avviso(String messaggio) {
//        /* invoca il metodo sovrascritto della classe */
//        return avviso(Dialogo.TITOLO_AVVISO, messaggio);
//    }

}// fine della classe