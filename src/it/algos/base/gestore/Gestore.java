/**
 * Title:     Gestore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      2-apr-2004
 */
package it.algos.base.gestore;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.PopupMenuEvent;
import java.awt.event.*;
import java.util.EventObject;

/**
 * Regola la gestione degli eventi e la business logic.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 2-apr-2004 ore 10.49.16
 */
public interface Gestore {


    /**
     * Azione informazioni sul programma.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAbout</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void about(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione aggiornamento del programma.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAbout</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void update(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione help generico.
     * <p/>
     * Metodo invocato da azione/evento <code>AzHelp</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void help(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione help programmatore.
     * <p/>
     * Metodo invocato da azione/evento <code>AzHelpProgrammatore</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void helpProgrammatore(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione chiude il Navigatore in una finestra.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeNavigatore</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void chiudeNavigatore(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione chiude il programma.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeProgramma</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void chiudeProgramma(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione chiude la Scheda.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeScheda</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void chiudeScheda(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione preferenze.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPreferenze</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void preferenze(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione registra dati default.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRegistraDatiDefault</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void registraDatiDefault(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione ricerca.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRicerca</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void ricerca(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione proietta.
     * <p/>
     * Metodo invocato da azione/evento <code>AzProietta</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void proietta(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione imposta come Preferito.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPreferito</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void setPreferito(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione di esportazione dei dati.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEsporta</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void esporta(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione di importazione dei dati.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEsporta</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void importa(ActionEvent unEvento, Azione unAzione);


    /**
     * Azione stampa.
     * <p/>
     * Metodo invocato da azione/evento <code>AzStampa</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void stampa(ActionEvent unEvento, Azione unAzione);


    /**
     * Attiva una Finestra (la porta in primo piano).
     * <p/>
     * Metodo invocato da azione/evento <code>AzAttivaFinestra</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void attivaFinestra(WindowEvent unEvento, Azione unAzione);


    /**
     * Chiude una Finestra.
     * <p/>
     * Metodo invocato da azione/evento <code>AzChiudeFinestra</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void chiudeFinestra(WindowEvent unEvento, Azione unAzione);


    /**
     * Bottone nuovo record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzNuovoRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void nuovoRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone aggiungi record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAggiungiRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void aggiungiRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone modifica record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzModificaRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void modificaRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone duplica record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzDuplicaRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void duplicaRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone elimina record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEliminaRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void eliminaRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone rimuovi record in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRimuoviRecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void rimuoviRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Mouse cliccato in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzListaClick</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void listaClick(MouseEvent unEvento, Azione unAzione);


    /**
     * Mouse cliccato due volte in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzListaDoppioClick</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void listaDoppioClick(MouseEvent unEvento, Azione unAzione);


    /**
     * Inserimento del carattere Enter in una Lista.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzListaEnter</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void listaEnter(KeyEvent unEvento, Azione unAzione);


    /**
     * Inserimento del carattere Return in una Lista.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzListaReturn</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void listaReturn(KeyEvent unEvento, Azione unAzione);


    /**
     * Mouse cliccato in un voce della <code>Lista</code>.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzTitolo</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void titolo(MouseEvent unEvento, Azione unAzione);


    /**
     * Entrata in una cella della lista (che riceve il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzEntrataCella</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void entrataCella(FocusEvent unEvento, Azione unAzione);


    /**
     * Uscita da una cella della lista (che perde il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzUscitaCella</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void uscitaCella(FocusEvent unEvento, Azione unAzione);


    /**
     * Inserimento di un carattere in una Lista.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzListaCarattere</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void listaCarattere(KeyEvent unEvento, Azione unAzione);


    /**
     * Bottone carica tutti in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCaricaTutti</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void caricaTutti(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone mostra solo selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzSoloSelezionati</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void soloSelezionati(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone nasconde selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzNascondeSelezionati</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void nascondeSelezionati(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone salva la selezione della <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzSalvaSelezione</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void salvaSelezione(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone carica la selezione nella <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCaricaSelezione</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void caricaSelezione(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone riga su in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRigaSu</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void rigaSu(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone riga giu in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRigaGiu</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void rigaGiu(ActionEvent unEvento, Azione unAzione);


    /**
     * Freccia in alto.
     * <p/>
     * Sposta in alto di una riga la selezione in una <code>Lista</code> <br>
     * Inserimento del carattere -freccia in alto- <br>
     * Metodo invocato dal comando/evento <code>AzFrecce</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void frecciaAlto(KeyEvent unEvento, Azione unAzione);


    /**
     * Freccia in basso.
     * <p/>
     * Sposta in basso di una riga la selezione in una <code>Lista</code> <br>
     * Inserimento del carattere -freccia in alto- <br>
     * Metodo invocato dal comando/evento <code>AzFrecce</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void frecciaBasso(KeyEvent unEvento, Azione unAzione);


    /**
     * Frecce pagina in una <code>Lista</code>.
     * <p/>
     * Inserimento del carattere -pagina s&ugrave;- o -pagina gi&ugrave;- <br>
     * Metodo invocato dal comando/evento <code>AzPagine</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void pagine(KeyEvent unEvento, Azione unAzione);


    /**
     * Freccia Home in una <code>Lista</code>.
     * <p/>
     * Inserimento del carattere -Home- <br>
     * Metodo invocato dal comando/evento <code>AzHome</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void home(KeyEvent unEvento, Azione unAzione);


    /**
     * Freccia End in una <code>Lista</code>.
     * <p/>
     * Inserimento del carattere -End- <br>
     * Metodo invocato dal comando/evento <code>AzEnd</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void end(KeyEvent unEvento, Azione unAzione);


    /**
     * Ordina sulla colonna a sinistra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzColonna</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void colonnaSinistra(KeyEvent unEvento, Azione unAzione);


    /**
     * Ordina sulla colonna a destra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal comando/evento <code>AzColonna</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void colonnaDestra(KeyEvent unEvento, Azione unAzione);


    /**
     * Bottone annulla modifiche in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAnnullaModifiche</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void annullaModifiche(ActionEvent unEvento, Azione unAzione);

//    /**
//     * Bottone registra record in una <code>Scheda</code>.
//     * <p/>
//     * Metodo invocato da azione/evento <code>AzRegistraScheda</code> <br>
//     * Invoca il metodo delegato <br>
//     *
//     * @param unEvento evento generato dall'interfaccia utente
//     * @param unAzione oggetto interessato dall'evento
//     */
//    public abstract void registraScheda(ActionEvent unEvento, Azione unAzione);


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
    public abstract void confermaForm(EventObject unEvento, Azione unAzione);


    /**
     * Bottone primo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPrimorecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void primoRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone record precedente in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRecordPrecedente</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void recordPrecedente(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone record successivo in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzRecordSuccessivo</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void recordSuccessivo(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone ultimo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzUltimorecord</code> <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void ultimoRecord(ActionEvent unEvento, Azione unAzione);


    /**
     * Mouse cliccato in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzMouseClick</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void mouseClick(MouseEvent unEvento, Azione unAzione);


    /**
     * Mouse cliccato due volte in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzMouseDoppioClick</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void mouseDoppioClick(MouseEvent unEvento, Azione unAzione);


    /**
     * Sincronizzazione del testo di un campo calcolato.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCampoCalcolato</code> <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     * @param unCampo campo calcolato da sincronizzare
     */
    public abstract void calcolaCampo(KeyEvent unEvento, Azione unAzione, Campo unCampo);


    /**
     * Sincronizzazione della label di un campo calcolato.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCalcolaLabel</code> <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     * @param unCampo campo calcolato da sincronizzare
     */
    public abstract void calcolaLabel(KeyEvent unEvento, Azione unAzione, Campo unCampo);


    /**
     * Inserimento di un carattere a video in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzCarattereTesto</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void carattere(KeyEvent unEvento, Azione unAzione);


    /**
     * Inserimento del carattere Enter in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void enter(KeyEvent unEvento, Azione unAzione);


    /**
     * Inserimento del carattere Escape in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void escape(KeyEvent unEvento, Azione unAzione);


    /**
     * Inserimento del carattere Tab in un campo testo.
     * <p/>
     * Metodo invocato da azione/evento <code>AzEnter</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void tab(KeyEvent unEvento, Azione unAzione);


    /**
     * Entrata nel campo (che riceve il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzEntrataCampo</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void entrataCampo(FocusEvent unEvento, Azione unAzione);


    /**
     * Uscita dal campo (che perde il fuoco).
     * <p/>
     * Metodo invocato da azione/evento <code>AzUscitaCampo</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void uscitaCampo(FocusEvent unEvento, Azione unAzione);


    /**
     * Modifica di un item in un componente.
     * <p/>
     * Metodo invocato da azione/evento <code>AzItemModificato</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void itemModificato(ItemEvent unEvento, Azione unAzione);


    /**
     * Entrata nel popup di un ComboBox (che diventa visibile).
     * <p/>
     * Metodo invocato da azione/evento <code>AzEntrataPopup</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void entrataPopup(PopupMenuEvent unEvento, Azione unAzione);


    /**
     * Uscita dal popup di un ComboBox (che diventa invisibile).
     * <p/>
     * Metodo invocato da azione/evento <code>AzUscitaPopup</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void uscitaPopup(PopupMenuEvent unEvento, Azione unAzione);


    /**
     * Modifica di un popup.
     * <p/>
     * Metodo invocato da azione/evento <code>AzPopupModificato</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void popupModificato(ItemEvent unEvento, Azione unAzione);


    /**
     * Modifica della selezione di una lista </li>
     * <p/>
     * Metodo invocato da azione/evento <code>AzSelezioneModificata</code> <br>
     * Recupera il campo interessato <br>
     * Invoca il metodo delegato <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void selezioneModificata(ListSelectionEvent unEvento, Azione unAzione);


    /**
     * Bottone annulla in un <code>Dialogo</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzAnnullaDialogo</code> <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void annullaDialogo(ActionEvent unEvento, Azione unAzione);


    /**
     * Bottone conferma in un <code>Dialogo</code>.
     * <p/>
     * Metodo invocato da azione/evento <code>AzioneConfermaDialogo</code> <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     * @param unAzione oggetto interessato dall'evento
     */
    public abstract void confermaDialogo(ActionEvent unEvento, Azione unAzione);


    /**
     * Restituisce l'oggetto concreto della classe principale.
     *
     * @return oggetto grafico GestoreBase restituito dall'interfaccia
     */
    public abstract GestoreBase getGestore();

}// fine della interfaccia
