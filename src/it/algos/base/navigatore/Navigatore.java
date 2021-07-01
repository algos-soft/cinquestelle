/**
 * Title:     Navigatore
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-gen-2004
 */
package it.algos.base.navigatore;

import com.wildcrest.j2printerworks.J2TablePrinter;
import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.util.MetodiQuery;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.portale.PortaleLista;
import it.algos.base.portale.PortaleNavigatore;
import it.algos.base.portale.PortaleScheda;
import it.algos.base.progressbar.ProgressBar;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.ricerca.RicercaBase;
import it.algos.base.scheda.Scheda;
import it.algos.base.statusbar.StatusBar;
import it.algos.base.vista.Vista;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Regola la presentazione dei dati di un <CODE>Modulo</CODE>.
 * </p>
 * Questa interfaccia: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Facade</b> </li>
 * <li> Fornisce un interfaccia unificata per tutte le chiamate alle classi di
 * questo package </li>
 * <li> Ogni navigatore ha una ed una sola Lista (oppure nessuna) </li>
 * <li> Ogni navigatore ha una ed una sola Scheda (oppure nessuna) </li>
 * <li> Un navigatore può contenere un'altro Navigatore </li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-gen-2004 ore 14.59.13
 */
public interface Navigatore extends GestioneEventi {

    /**
     * nome chiave interno del navigatore di default
     */
    public static final String NOME_CHIAVE_DEFAULT = "navigatoredefault";

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista e di <bold>n</bold> schede
     * in <bold>n</bold> finestre separate
     */
    public static final int MODO_FINESTRE_MULTIPLE_LISTA_SCHEDA = 100103;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista e di <bold>1</bold> scheda
     * in <bold>1</bold> pannello
     */
    public static final int MODO_PANNELLO_LISTA_SCHEDA = 281046;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista e di <bold>1</bold> scheda
     * in <bold>1</bold> finestra
     */
    public static final int MODO_FINESTRA_LISTA_SCHEDA = 170991;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista e <bold>1</bold> lista
     * in <bold>1</bold> pannello e <bold>1</bold> scheda
     * in <bold>1</bold> finestra
     */
    public static final int MODO_PANNELLO_LISTA_LISTA = 290866;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista e <bold>1</bold> navigatore
     * in <bold>1</bold> pannello
     */
    public static final int MODO_PANNELLO_LISTA_NAVIGATORE = 230680;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista in <bold>1</bold> pannello
     */
    public static final int MODO_PANNELLO_LISTA = 61068;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> scheda in <bold>1</bold> pannello
     */
    public static final int MODO_PANNELLO_SCHEDA = 25158;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> scheda in <bold>1</bold> finestra
     */
    public static final int MODO_FINESTRA_SCHEDA = 20164;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista e di <bold>1</bold> navigatore
     * in <bold>1</bold> finestra
     */
    public static final int MODO_FINESTRA_LISTA_NAVIGATORE = 125;

    /**
     * modalita predefinita di funzionamento del navigatore.<br>
     * gestione di <bold>1</bold> lista e <bold>1</bold> lista
     * in <bold>1</bold> finestra e <bold>1</bold> scheda
     * in <bold>1</bold> finestra
     */
    public static final int MODO_FINESTRA_LISTA_LISTA = 874;

    /**
     * modalita di default di funzionamento del navigatore <br>
     */
    public static final int MODO_DEFAULT = MODO_FINESTRE_MULTIPLE_LISTA_SCHEDA;

    /**
     * codifica del componente di tipo finestra nel navigatore
     */
    public static final int COMPONENTE_FINESTRA = 901;

    /**
     * codifica del componente di tipo pannello nel navigatore
     */
    public static final int COMPONENTE_PORTALE = 902;

    /**
     * codifica del componente di tipo menu nel navigatore
     */
    public static final int COMPONENTE_MENU = 903;

    /**
     * codifica del componente di tipo statu_bar nel navigatore
     */
    public static final int COMPONENTE_STATUS = 904;


    /**
     * Regolazioni iniziali <i>una tantum</i>. <br>
     * Metodo chiamato dalla classe che crea questo oggetto dopo che sono
     * stati regolati dalla sottoclasse i parametri indispensabili <br>
     * Viene eseguito una sola volta <br>
     */
    public abstract void inizializza();


    public abstract void avvia();


    /**
     * Azione help.
     * <p/>
     * Apre una finestra di help <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca la classe delegata per la funzionalità specifica <br>
     */
    public abstract void apreHelp();


    /**
     * Azione help programmatore.
     * <p/>
     * Apre una finestra di help per il programmatore <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca la classe delegata per la funzionalità specifica <br>
     */
    public abstract void apreHelpProgrammatore();


    /**
     * Apre il Navigatore.
     * <p/>
     * Se il Navigatore non è aperto:
     * - esegue il pack della finestra
     * - centra la finestra sullo schermo
     * - attiva il flag "aperto"
     * Sempre:
     * - rende visibile la finestra del Navigatore
     */
    public abstract void apriNavigatore();


    /**
     * Azione chiude il Navigatore.
     * <p/>
     * Chiude la finestra del navigatore <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Controlla che il Modulo di questo Navigatore non sia quello di partenza <br>
     * Invoca il metodo delegato della classe Modulo <br>
     */
    public abstract void chiudiNavigatore();

//    /**
//     * Azione modifica record in una <code>Lista</code>.
//     * <p/>
//     * Metodo invocato dal Gestore Eventi <br>
//     * Sincronizza lo stato della GUI <br>
//     * (la sincronizzazione avviene DOPO, ma viene lanciata da qui) <br>
//     * Controlla e chiude una eventuale scheda aperta <br>
//     * Chiede la chiave alla lista master <br>
//     * Avvia la scheda, passandogli il codice-chiave <br>
//     *
//     * @return true se eseguito correttamente
//     */
//    public abstract boolean modificaRecord();


    /**
     * Selezione della scheda da visualizzare.
     * <p/>
     * Metodo invocato modificaRecord prima di visualizzare la scheda <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param codice del record che sta per essere visualizzato
     *
     * @return il nome chiave della scheda da visualizzare
     */
    public abstract String selezionaScheda(int codice);

//    /**
//     * Azione elimina record in una <code>Lista</code>.
//     * <p/>
//     * Metodo invocato dal Gestore Eventi <br>
//     * Recupera dalla lista il/i record selezionato/i <br>
//     * Chiede conferma all'utente <br>
//     * Elimina i record <br>
//     */
//    public abstract void eliminaRecord();


    /**
     * Tasto carattere generico in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unEvento evento che ha generato il comando
     */
    public abstract void listaCarattere(KeyEvent unEvento);


    /**
     * Mouse cliccato in una <code>Lista</code>.
     * <p/>
     * Sincronizza lo stato della GUI <br>
     */
    public abstract void listaClick();


    /**
     * Bottone riga su in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da LogicaNavigatore <br>
     * Regola il tipo di spostamento <br>
     * Invoca il metodo delegato per invertire i valori <br>
     */
    public abstract void rigaSu();


    /**
     * Bottone riga giu in una <code>Lista</code>.
     * <p/>
     * Metodo invocato da LogicaNavigatore <br>
     * Regola il tipo di spostamento <br>
     * Invoca il metodo delegato per invertire i valori <br>
     */
    public abstract void rigaGiu();


    /**
     * Freccia in alto.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Sposta in alto di una riga la selezione in una <code>Lista</code> <br>
     * Sovrascrive l'evento standard della JTable per regolare la sincronizzazione
     * del Portale <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void frecciaAlto();


    /**
     * Freccia in basso.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Sposta in basso di una riga la selezione in una <code>Lista</code> <br>
     * Sovrascrive l'evento standard della JTable per regolare la sincronizzazione
     * del Portale <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void frecciaBasso();


    /**
     * Frecce pagina in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * <p/>
     * Le righe nella tavola partono da zero <br>
     *
     * @param unEvento evento generato dall'interfaccia utente
     */
    public abstract void pagine(KeyEvent unEvento);


    /**
     * Freccia Home in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * <p/>
     * Le righe nella tavola partono da zero <br>
     */
    public abstract void home();


    /**
     * Freccia End in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     * <p/>
     * Le righe nella tavola partono da zero <br>
     */
    public abstract void end();


    /**
     * Ordina sulla colonna a sinistra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Le colonne partono da 1 <br>
     */
    public abstract void colonnaSinistra();


    /**
     * Ordina sulla colonna a destra in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sincronizza lo stato della GUI <br>
     * Le colonne partono da 1 <br>
     */
    public abstract void colonnaDestra();


    /**
     * Modifica della selezione di una lista </li>
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato della classe <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void selezioneModificata();


    /**
     * Chiusura della scheda tramite il bottone Annulla.
     * <p/>
     * Metodo invocato dal gestore eventi <br>
     */
    public abstract void chiudeScheda();


    /**
     * Chiude la scheda con un dialogo di conferma per la registrazione.
     * <p/>
     * Il dialogo viene presentato solo se la scheda è modificata
     *
     * @param bottoneDefault codice del bottone preselezionato nel dialogo
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return codice di chiusura della scheda:
     *         - Scheda.ANNULLA = chiusura non avvenuta
     *         - Scheda.ABBANDONA = chiusura avvenuta con abbandono delle eventuali modifiche
     *         - Scheda.REGISTRA = chiusura avvenuta con registrazione delle eventuali modifiche
     */
    public abstract int richiediChiusuraSchedaDialogo(int bottoneDefault, boolean dismetti);


    /**
     * Chiude la scheda senza dialogo di conferma per la registrazione.
     * <p/>
     *
     * @param registra registrazione automatica
     * @param dismetti Se true, dopo la chiusura dismette la scheda
     * (carica tutti i campi con valori vuoti)
     *
     * @return true se la scheda e' stata chiusa
     */
    public abstract boolean richiediChiusuraSchedaNoDialogo(boolean registra, boolean dismetti);


    /**
     * Passa il fuoco alla lista.
     * <p/>
     */
    public abstract void fuocoAllaLista();


    /**
     * Azione preferenze.
     * <p/>
     * Apre una finestra di preferenze <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void aprePreferenze();


    /**
     * Azione registra dati default.
     * <p/>
     * Registra i dati del modulo <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void registraDatiDefault();


    /**
     * Azione ricerca.
     * <p/>
     * Apre un dialogo di ricerca <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void apreRicerca();


    /**
     * Azione proietta.
     * <p/>
     * Apre un dialogo di proiezione dati <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void proietta();


    /**
     * Azione Imposta come Preferito.
     * <p/>
     * Imposta il record correntemente selezionato nella lista
     * come Preferito
     */
    public abstract void setPreferito();


    /**
     * Azione esporta.
     * <p/>
     * Apre una finestra di esportazione <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void apreEsporta();


    /**
     * Azione importa.
     * <p/>
     * Apre una finestra di importazione <br>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void apreImporta();


    /**
     * Annulla le modifiche nella scheda.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Ricarica la scheda con i valori dal database <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void annullaModifiche();


    /**
     * Chiude la scheda corrente registrando il record.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @return true se riuscito
     */
    public abstract boolean registraScheda();


    /**
     * Bottone primo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public abstract void primoRecord();


    /**
     * Bottone record precedente in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public abstract void recordPrecedente();


    /**
     * Bottone record successivo in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public abstract void recordSuccessivo();


    /**
     * Bottone ultimo record in una <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public abstract void ultimoRecord();


    /**
     * Mostra un record nella lisa.
     * <p/>
     * Aggiunge il record al filtro corrente della lista <br>
     * Ricarica la lista <br>
     * Porta la riga in area visibile <br>
     * Seleziona la riga <br>
     */
    public abstract void mostraRecord(int cod);


    /**
     * Mouse cliccato in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void mouseClick(Campo unCampo);


    /**
     * Mouse cliccato due volte in un <code>Campo</code> della <code>Scheda</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void mouseDoppioClick(Campo unCampo);


    /**
     * Sincronizzazione del testo di un campo calcolato.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo calcolato da sincronizzare
     */
    public abstract void calcolaCampo(Campo unCampo);


    /**
     * Sincronizzazione della label di un campo calcolato.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Evento lanciato da un componente utilizzato per il calcolo del campo <br>
     * L'evento viene lanciato quando il componente subisce delle modifiche <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo calcolato da sincronizzare
     */
    public abstract void calcolaLabel(Campo unCampo);


    /**
     * Inserimento di un carattere generico in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void carattere(Campo unCampo);


    /**
     * Inserimento di un carattere a video in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void carattereTesto(Campo unCampo);


    /**
     * Inserimento di un carattere a video in un campo area di testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void carattereTestoArea(Campo unCampo);


    /**
     * Inserimento del carattere Enter in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Controlla se il carattere e' compatibile col Campo <br>
     */
    public abstract void enter();


    /**
     * Inserimento del carattere Esc in un campo testo.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public abstract void escape();


    /**
     * Entrata nel campo (che riceve il fuoco).
     * </p>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void entrataCampo(Campo unCampo);


    /**
     * Uscita dal campo (che perde il fuoco).
     * </p>
     * Metodo invocato dal Gestore Eventi <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void uscitaCampo(Campo unCampo);


    /**
     * Modifica di un item in un componente.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Sincronizza il navigatore <br>
     * Invoca il metodo delegato della classe <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void itemModificato(Campo unCampo);


    /**
     * Entrata dal popup di un ComboBox (che diventa visibile).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato della classe <br>
     * Sincronizza il navigatore <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void entrataPopup(Campo unCampo);


    /**
     * Uscita dal popup di un ComboBox (che diventa invisibile).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Aggiorna il campo (da GUI a memoria e poi di nuovo a GUI) <br>
     * Invoca il metodo delegato della classe <br>
     * Sincronizza il navigatore <br>
     *
     * @param unCampo campo che ha generato l'evento
     */
    public abstract void uscitaPopup(Campo unCampo);


    /**
     * Verifica se la scheda e' registrabile.
     * <p/>
     * Invoca il metodo delagato della scheda.
     *
     * @param scheda la scheda da controllare
     *
     * @return true se e' registrabile
     */
    public abstract boolean isSchedaRegistrabile(Scheda scheda);


    /**
     * Sincronizza lo stato del Navigatore.
     */
    public abstract void sincronizza();


    /**
     * Visualizza il solo componente A del portale Navigatore.
     * <p/>
     * Visualizza il componente alto/sinistra dello split pane del portale navigatore.
     */
    public abstract void visualizzaComponenteA();


    /**
     * Visualizza il solo componente B del portale Navigatore.
     * <p/>
     * Visualizza il componente basso/destra dello split pane del portale navigatore.
     */
    public abstract void visualizzaComponenteB();


    /**
     * Creazione fisica di un nuovo record.
     * <p/>
     *
     * @return il codice del record creato, -1 se non creato.
     */
    public abstract int creaRecord();


    /**
     * Mouse cliccato due volte in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Modifica il record in scheda <br>
     */
    public abstract void listaDoppioClick();


    /**
     * Tasto Enter in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public abstract void listaEnter();


//    /**
//     * Comando del mouse cliccato nei titoli.
//     * <p/>
//     * Metodo invocato dal Gestore Eventi <br>
//     * Sincronizza lo stato della GUI <br>
//     *
//     * @param unEvento evento generato dall'interfaccia utente
//     */
//    public abstract void colonnaCliccata(MouseEvent unEvento);


    /**
     * Entrata in una cella della lista (che riceve il fuoco).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void entrataCella();


    /**
     * Uscita da una cella della lista (che perde il fuoco).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void uscitaCella();


    /**
     * Entrata in una cella della lista (che riceve il fuoco).
     * <p/>
     * Metodo invocato dal Logicanavigatore <br>
     * Sovrascritto nelle sottoclassi.
     *
     * @param codice chiave del record
     * @param unCampo campo editato
     */
    public abstract void entrataCella(int codice, Campo unCampo);


    /**
     * Uscita da una cella della lista (che perde il fuoco).
     * <p/>
     * Metodo invocato dal Logicanavigatore <br>
     * Sovrascritto nelle sottoclassi.
     *
     * @param codice chiave del record
     * @param unCampo campo editato
     */
    public abstract void uscitaCella(int codice, Campo unCampo);


    /**
     * Bottone carica tutti in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Sincronizza lo stato della GUI <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void caricaTutti();


    /**
     * Bottone mostra solo selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public abstract void soloSelezionati();


    /**
     * Bottone nasconde selezionati in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public abstract void nascondeSelezionati();


    /**
     * Ricarica la selezione della Lista.
     * <p/>
     * Usa il filtro base piu' quello corrente<br>
     * Invoca il metodo delegato <br>
     */
    public abstract void aggiornaLista();


    /**
     * Assegna un filtro base alla Lista
     * <p/>
     *
     * @param filtro base da assegnare
     */
    public abstract void setFiltroBase(Filtro filtro);


    /**
     * Assegna un filtro corrente alla Lista.
     * <p/>
     *
     * @param filtro corrente da assegnare
     */
    public abstract void setFiltroCorrente(Filtro filtro);


    /**
     * Attiva l'aggiornamento continuo dei totali.
     * <p/>
     * Se attivato, i totali vengono aggiornati ad ogni caricamento dei
     * dati nella lista.
     * Di default i totali vengono aggiornati solo quando
     * cambiano i filtri.
     *
     * @param flag per attivare l'aggiornamento continuo dei totali
     */
    public abstract void setAggiornamentoTotaliContinuo(boolean flag);


    /**
     * Bottone salva la selezione della <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public abstract void salvaSelezioneEsterna();


    /**
     * Bottone carica la selezione nella <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Sovrascritto nelle sottoclassi <br>
     * Invoca il metodo delegato <br>
     * Sincronizza lo stato della GUI <br>
     */
    public abstract void caricaSelezioneEsterna();


    /**
     * Azione stampa in una <code>Lista</code>.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     */
    public abstract void stampaLista();


    /**
     * Ritorna un TablePrinter per stampare il contenuto corrente della lista.
     * <p/>
     *
     * @return il TablePrinter
     */
    public abstract J2TablePrinter getTablePrinter();


    /**
     * Attiva una Finestra (la porta in primo piano).
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void attivaFinestra();


    /**
     * Chiude una Finestra.
     * <p/>
     * Metodo invocato dal Gestore Eventi <br>
     * Invoca il metodo delegato <br>
     */
    public abstract void chiudeFinestra();


    /**
     * Ritorna la Status Bar della finestra del Navigatore.
     * <p/>
     */
    public abstract StatusBar getStatusBar();


    /**
     * Ritorna la Progress Bar della finestra del Navigatore.
     * <p/>
     */
    public abstract ProgressBar getProgressBar();

//    /**
//     * Azione nuovo record in una <code>Lista</code>.
//     * <p/>
//     * Metodo invocato dal Gestore Eventi <br>
//     * Invoca il metodo delegato <br>
//     */
//    public abstract int nuovoRecord();


    /**
     * Registrazione fisica di un record.
     * <p/>
     *
     * @param codice il codice chiave del record
     * @param campi la lista dei campi da registrare
     *
     * @return true se riuscito
     */
    public abstract boolean registraRecord(int codice, ArrayList<Campo> campi);


    /**
     * Registra il record attualmente visualizzato nella scheda.
     * <p/>
     * Delega alla scheda un controllo di possibile registrazione <br>
     * Recupera dalla scheda il codice <br>
     * Recupera dalla scheda i campi fisici <br>
     * Registra il record<br>
     *
     * @return true se riuscito
     */
    public abstract boolean registraRecord();


    /**
     * nome chiave interno del navigatore
     */
    public abstract void setNomeChiave(String nomeChiave);


    /**
     * nome chiave interno del navigatore
     */
    public abstract String getNomeChiave();


    /**
     * Chiede al navigatore il permesso di chiuderlo.
     * <p/>
     * Presenta eventuali messaggi all'utente per risolvere situazioni aperte <br>
     *
     * @return true se riuscito.
     */
    public boolean richiediChiusura();


    public abstract Modulo getModulo();


    /**
     * Indica se il Navigatore deve effettuare le operazioni standard
     * sotto transazione
     * <p/>
     *
     * @param flag di controllo
     */
    public abstract void setUsaTransazioni(boolean flag);


    /**
     * Ritorna la connessione da utilizzare per l'accesso al database
     * <p/>
     * Se non è stata registrata una connessione specifica,
     * usa la connessione del Modulo.<br>
     *
     * @return la connessione da utilizzare
     */
    public abstract Connessione getConnessione();


    /**
     * Registra una connessione specifica da utilizzare
     * per l'accesso al database.
     * <p/>
     *
     * @param connessione da utilizzare
     */
    public abstract void setConnessione(Connessione connessione);


    public abstract LogicaNavigatore getLogica();


    public abstract void setLogica(LogicaNavigatore logica);

//    public abstract NavigatoreBase getNavigatoreBase();


    public abstract PortaleNavigatore getPortaleNavigatore();


    public abstract PortaleLista getPortaleLista();


    public abstract PortaleScheda getPortaleScheda();


    /**
     * Ritorna la lista gestita dal navigatore.<br>
     *
     * @return la lista gestita dal Navigatore
     */
    public abstract Lista getLista();


    /**
     * Assegna una Lista specifica al navigatore<p>
     *
     * @param lista la lista da assegnare
     */
    public abstract void setLista(Lista lista);


    /**
     * Assegna una Vista specifica alla lista del navigatore
     * <p/>
     *
     * @param vista da assegnare
     */
    public abstract void setVista(Vista vista);


    /**
     * Crea un'azione specifica e la aggiunge al navigatore.
     * <p/>
     * L'azione può essere aggiunta: <ul>
     * <li> Alla toolbar della lista (in coda) </li>
     * <li> Alla toolbar della scheda (in coda) </li>
     * <li> Ad un menu (posizione specificata nell'azione stessa) </li>
     * <ul/>
     * Metodo invocato dal ciclo inizializza <br>
     *
     * @param azione completa di icona, titolo ed help
     * @param tool switch di selezione lista/scheda/menu
     */
    public abstract void addAzione(Azione azione, Navigatore.Tool tool);


    /**
     * Abilita l'uso dei caratteri per filtrare la lista.
     *
     * @param usaCarattereFiltro
     */
    public abstract void setUsaCarattereFiltro(boolean usaCarattereFiltro);


    /**
     * Ritorna il navigatore Master di questo navigatore.
     * <p/>
     *
     * @return il navigatore Master
     */
    public abstract Navigatore getNavMaster();


    /**
     * Ritorna il navigatore Slave di questo navigatore.
     * <p/>
     *
     * @return il navigatore Slave
     */
    public abstract Navigatore getNavSlave();


    /**
     * Rende questo navigatore pilotato da un dato Modulo.
     * <p/>
     *
     * @param modulo il modulo che deve pilotare questo navigatore.
     */
    public abstract void pilotaNavigatore(Modulo modulo);


    /**
     * Ritorna il navigatore pilota di questo navigatore.
     * <p/>
     *
     * @return il navigatore pilota
     */
    public abstract Navigatore getNavPilota();


    /**
     * regola il navigatore pilota di questo navigatore.
     * <p/>
     *
     * @param navPilota il navigatore pilota
     */
    public abstract void setNavPilota(Navigatore navPilota);


    /**
     * Ritorna il Navigatore pilotato da questo navigatore.
     * <p/>
     *
     * @return il Navigatore pilotato
     */
    public abstract Navigatore getNavPilotato();


    /**
     * Assegna il Navigatore pilotato.
     * <p/>
     *
     * @param navPilotato il Navigatore pilotato
     */
    public abstract void setNavPilotato(Navigatore navPilotato);


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     *
     * @param unaScheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda  �
     */
    public abstract String addScheda(Scheda unaScheda);


    /**
     * Aggiunge una scheda alla collezione.
     * <p/>
     * Regola la scheda appena aggiunta come corrente <br>
     * Se il nomeChiave è nullo o vuoto, usa come chiave un numero progressivo <br>
     *
     * @param nomeChiave della scheda
     * @param scheda da aggiungere
     *
     * @return il nome definitivo assegnato alla scheda
     */
    public abstract String addScheda(String nomeChiave, Scheda scheda);


    /**
     * Aggiunge una scheda e la rende corrente.
     * <p/>
     * Usa il nome chiave della scheda <br>
     */
    public abstract void addSchedaCorrente(Scheda unaScheda);


    /**
     * Aggiunge un set alla collezione interna.
     * <p/>
     *
     * @param nomeSet da recuperare dal modello
     *
     * @return lo stesso nome del set passato come parametro
     */
    public abstract String addSet(String nomeSet);


    /**
     * Ritorna la scheda gestita dal navigatore.<br>
     *
     * @return la scheda gestita dal Navigatore
     */
    public abstract Scheda getScheda();


    /**
     * Recupera una scheda dalla collezione del navigatore
     * <p/>
     *
     * @param chiave per la collezione
     *
     * @return la scheda corrispondente
     */
    public abstract Scheda getScheda(String chiave);


    /**
     * Determina se il navigatore deve creare una propria finestra.
     * <p/>
     *
     * @param usaFinestra true se il navigatore deve cereare una propria finestra
     */
    public abstract void setUsaFinestra(boolean usaFinestra);


    /**
     * Determina se il navigatore deve creare una propria finestra.
     * <p/>
     *
     * @param usa true se il navigatore deve creare una propria finestra
     * @param dialogo true se la finestra deve essere di tipo dialogo modale
     */
    public abstract void setUsaFinestra(boolean usa, boolean dialogo);


    /**
     * Assegna il titolo della finestra del Navigatore.
     * <p/>
     *
     * @param titolo il titolo della finestra
     */
    public abstract void setTitoloFinestra(String titolo);


    /**
     * Recupera lo stato corrente di modificabilità del Navigatore.
     * <p/>
     *
     * @return lo stato di modificabilità del Navigatore
     */
    public abstract boolean isModificabile();


    /**
     * Rende il Navigatore modificabile o meno.
     * <p/>
     * Un Navigatore modificabile consente di modificare i
     * record visualizzati in lista o in scheda.<br>
     * Un Navigatore non modificabile permette
     * la sola visualizzazione dei record.<br>
     * <p/>
     * Se il flag è true, risponde solo se il Navigatore è abilitato.<br>
     *
     * @param flag true se il Navigatore deve essere modificabile
     */
    public abstract void setModificabile(boolean flag);


    public abstract boolean isNavigatoreMain();


    /**
     * Regola l'altezza della lista.
     * <p/>
     * Il valore e' espresso in righe <br>
     * Se non regolato, usa l'altezza di default della Lista <br>
     *
     * @param righe l'altezza della lista espressa in righe
     */
    public abstract void setRigheLista(int righe);


    /**
     * Regola la larghezza del Portale Lista.
     * <p/>
     * Il valore e' espresso in pixel <br>
     * Se non regolato, usa la dimensione interna <br>
     *
     * @param larghezza la larghezza della lista in pixel
     */
    public abstract void setLarghezzaLista(int larghezza);


    public abstract boolean isOrizzontale();


    /**
     * Regola l'orientamento del navigatore.
     * <p/>
     *
     * @param orizzontale true per orizzontale, false per verticale.
     */
    public abstract void setOrizzontale(boolean orizzontale);


    public String getNomeVista();


    public abstract void setNomeVista(String nomeVista);


    public abstract String getNomeSet();


    public abstract void setNomeSet(String nomeSet);


    public abstract boolean isNuovoRecord();


    public abstract void setNuovoRecord(boolean nuovoRecord);


    /**
     * Ritorna lo stato del flag pannello unico
     *
     * @return true se il Navigatore usa un pannello unico
     */
    public abstract boolean isUsaPannelloUnico();


    /**
     * Regola il flag di uso di pannello unico.
     * <p/>
     * Se disattivato, il Navigatore usa uno split pane
     * per mostrare contempèoraneamente i suoi due componenti.
     * Se attivato, il Navigatore usa un pannello unico
     * per mostrare alternativamente i suoi due componenti.
     *
     * @param flag true per usare un pannello unico
     */
    public abstract void setUsaPannelloUnico(boolean flag);


    /**
     * Visualizza il componente B in finestra separata.<p>
     * Significativo solo se il navigatore usa un pannello unico
     *
     * @param flag true per usare finestra separata
     */
    public abstract void setUsaFinestraPop(boolean flag);


    /**
     * Regola la visibilita' della progress bar nella finestra.
     * <p/>
     * La Progress Bar normalmente non e' visibile
     * (Viene comunque resa visibile durante l'esecuzione una Operazione)
     *
     * @param flag per regolare la visibilita'
     */
    public abstract void setProgressBarVisibile(boolean flag);


    /**
     * Controlla se è usato il bottone nuovo record nella lista.
     * <p/>
     *
     * @return true se è usato
     */
    public abstract boolean isUsaNuovo();


    /**
     * Abilita l'uso del bottone nuovo record in lista
     * <p/>
     * Visualizza il bottone nella toolbar della lista.<br>
     *
     * @param usa per usare il bottone Nuovo Record
     */
    public abstract void setUsaNuovo(boolean usa);


    /**
     * Abilita l'uso del bottone modifica record in lista
     * <p/>
     * Visualizza il bottone nella toolbar della lista.<br>
     *
     * @param usa per usare il bottone
     */
    public abstract void setUsaModifica(boolean usa);


    /**
     * Determina il tipo di bottoni nuovo/elimina della lista
     * <p/>
     * Usa la coppia nuovo/elimina oppure la coppia aggiungi/rimuovi <br>
     *
     * @param tipoBottoni nuovo/elimina oppure aggiungi/rimuovi
     */
    public abstract void setTipoBottoni(Lista.Bottoni tipoBottoni);


    /**
     * Controlla se sono usate le frecce di spostamento ordine nella lista.
     * <p/>
     *
     * @return true se sono usate
     */
    public abstract boolean isUsaFrecceSpostaOrdineLista();


    /**
     * Abilita l'uso delle dei bottoni di spostamento di ordine
     * del record su e giu nella lista.
     * <p/>
     * Visualizza i bottoni nella toolbar della lista.<br>
     * La lista e' spostabile solo se ordinata sul campo ordine.<br>
     *
     * @param usaFrecce per usare i bottoni di spostamento
     */
    public abstract void setUsaFrecceSpostaOrdineLista(boolean usaFrecce);


    /**
     * Abilita l'uso delle del bottone Duplica Record nella lista.
     * <p/>
     *
     * @param flag per usare il bottone Duplica Record
     */
    public abstract void setUsaDuplicaRecord(boolean flag);


    /**
     * Abilita l'uso del pulsante ricerca nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public abstract void setUsaRicerca(boolean flag);


    /**
     * Abilita l'uso del pulsante Proietta nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public abstract void setUsaProietta(boolean flag);


    /**
     * Abilita l'uso della toolbar nella lista.
     * <p/>
     *
     * @param flag per usare la toolbar
     */
    public abstract void setUsaToolBarLista(boolean flag);


    /**
     * Abilita l'uso della status bar nella lista.
     * <p/>
     *
     * @param flag per usare la status bar
     */
    public abstract void setUsaStatusBarLista(boolean flag);


    /**
     * Abilita l'uso della dei filtri pop nella lista.
     * <p/>
     *
     * @param flag per usare i filtri pop
     */
    public abstract void setUsaFiltriPopLista(boolean flag);


    /**
     * Abilita l'uso della status bar nella scheda.
     * <p/>
     *
     * @param flag per usare la status bar
     */
    public abstract void setUsaStatusBarScheda(boolean flag);


    /**
     * Controlla l'uso del pulsante Preferito nella lista
     * <p/>
     *
     * @return true se usa il pulsante Preferito
     */
    public abstract boolean isUsaPreferito();


    /**
     * Abilita l'uso del pulsante Preferito nella lista.
     * <p/>
     *
     * @param flag per usare il pulsante
     */
    public abstract void setUsaPreferito(boolean flag);


    /**
     * Ritorna il flag di comportamento dopo la chiusura della scheda
     * con il pulsante di registrazione.
     * <p/>
     *
     * @return true se la scheda va dismessa dopo la chiusura con registrazione
     */
    public abstract boolean isDismettiSchedaDopoRegistrazione();


    /**
     * Ritorna il flag di comportamento di registrazione
     * allo spostamento in scheda in presenza di modifiche.
     * <p/>
     *
     * @return true se registra automaticamente, false se chiede conferma
     */
    public abstract boolean isConfermaRegistrazioneSpostamento();


    /**
     * Ritorna il campo di link verso il modulo pilota.
     * <p/>
     *
     * @return il campo di link verso il modulo pilota
     */
    public abstract Campo getCampoLink();


    /**
     * Regola il campo di link verso il modulo pilota.
     * <p/>
     *
     * @param campoLink il campo di link verso il modulo pilota
     */
    public abstract void setCampoLink(Campo campoLink);


    /**
     * Ritorna l'elenco dei valori pilota.
     * <p/>
     *
     * @return l'elenco dei valori pilota
     */
    public abstract int[] getValoriPilota();


    /**
     * Ritorna il primo valore dall'elenco dei valori pilota.
     * <p/>
     *
     * @return il primo valore pilota
     */
    public abstract int getValorePilota();


    /**
     * Regola l'elenco di valori pilota.
     * <p/>
     * I valori pilota vengono modificati solo se i valori
     * ricevuti sono diversi da quelli attuali.<br>
     * Se i valori sono stati modificati, ritorna true
     * per segnalare il fatto al chiamante.
     *
     * @param valoriPilota l'elenco dei valori pilota
     *
     * @return true se i valori pilota sono stati modificati.
     */
    public abstract boolean setValoriPilota(int[] valoriPilota);


    /**
     * Metodo chiamato ogni volta che i valori pilota sono cambiati.
     * <p/>
     * Da sovrascrivere nelle sottoclassi per intercettare
     * il momento del cambio dei valori pilota.
     *
     * @param nuoviValori i nuovi valori pilota
     */
    public abstract void valoriPilotaCambiati(int[] nuoviValori);


    /**
     * Regola l'elenco di valori pilota con un singolo valore.
     * <p/>
     *
     * @param valorePilota il valore pilota
     *
     * @return true se i valori pilota sono stati modificati.
     */
    public abstract boolean setValorePilota(int valorePilota);


    /**
     * Crea un filtro corrispondente ai valori pilota.
     * <p/>
     *
     * @return il filtro corrispondente ai valori pilota
     */
    public abstract Filtro creaFiltroPilota();


    /**
     * Recupera i valori pilota di questo navigatore.
     * <p/>
     * Sovrascritto nelle sottoclassi.<br>
     *
     * @return i valori pilota
     */
    public abstract int[] recuperaValoriPilota();


    /**
     * Regola i valori pilota di questo Navigatore.
     * <p/>
     * Recupera i valori dal navigatore pilota<br>
     * Registra i valori in questo navigatore, solo se sono cambiati<br>
     * Se il flag isPilotaDaRigheMultiple e' attivato, puo' usare
     * piu' di una riga pilota, altrimenti la lista pilotata e' vuota.<br>
     * Se non ci sono righe selezionate il valore pilota viene impostato
     * a zero in modo da non caricare righe.
     *
     * @return true se i valori pilota sono stati modificati
     *         rispetto ai precedenti
     */
    public abstract boolean regolaValoriPilota();


    /**
     * Ritorna il flag che indica se questo navigatore puo'
     * essere pilotato da piu' di un valore.
     * <p/>
     *
     * @return true se puo' essere pilotato da piu' di un valore
     */
    public abstract boolean isPilotatoDaRigheMultiple();


    /**
     * Permette di pilotare questo navigatore con piu' di un valore.
     * <p/>
     * Regolazione significativa solo su un navigatore pilotato.<br>
     * Questo flag controlla il comportamento della lista pilotata
     * quando esistono piu' valori pilota
     * (es. quando si seleziona piu' di una riga nella lista pilota)<br>
     * Se attivato, vengono caricate tutte le corrispondenti righe.<br>
     * Se disattivato, non viene caricata nessuna riga.<br>
     *
     * @param flag true per permettere piu' di una riga pilota.
     */
    public abstract void setPilotatoDaRigheMultiple(boolean flag);


    /**
     * Ritorna il riferimento al campo che contiene e pilota questo navigatore.
     * <p/>
     *
     * @return il campo che contiene e pilota il navigatore.
     */
    public abstract Campo getCampoPilota();


    /**
     * Regola il riferimento al campo che contiene e pilota questo navigatore.
     * <p/>
     *
     * @param campoPilota il campo che contiene e pilota il navigatore.
     */
    public abstract void setCampoPilota(Campo campoPilota);


    /**
     * Costruisce il menu tabelle specifico.
     * </p>
     * Metodo invocato dal ciclo inizializza <br>
     */
    public abstract void setMenuTabelle(JMenuItem menu);


    /**
     * Ritorna true se questo Navigatore e' pilotato.
     * <p/>
     *
     * @return true se questo Navigatore e' pilotato
     */
    public abstract boolean isPilotato();

//    /**
//     * Regola il navigatore proprietario di questo navigatore.
//     * <p/>
//     *
//     * @param navProprietario il navigatore proprietario
//     */
//    public abstract void setNavProprietario(Navigatore navProprietario);


    /**
     * Flag - seleziona l'icona piccola, media o grande.
     * <p/>
     *
     * @param unTipoIcona codice tipo icona (Codifica in ToolBar)
     *
     * @see it.algos.base.toolbar.ToolBar
     */
    public abstract void setTipoIcona(int unTipoIcona);


    /**
     * Regola la dimensione delle icone.
     * <p/>
     * Dimensione piccola <br>
     * Regola tutte le icone di tutte le ToolBar del navigatore <br>
     */
    public abstract void setIconePiccole();


    /**
     * Regola la dimensione delle icone.
     * <p/>
     * Dimensione media <br>
     * Regola tutte le icone di tutte le ToolBar del navigatore <br>
     */
    public abstract void setIconeMedie();


    /**
     * Regola la dimensione delle icone.
     * <p/>
     * Dimensione grande <br>
     * Regola tutte le icone di tutte le ToolBar del navigatore <br>
     */
    public abstract void setIconeGrandi();


    /**
     * flag - usa il bottone elimina.
     *
     * @param flag true per usare il bottone elimina
     */
    public abstract void setUsaElimina(boolean flag);


    /**
     * flag - usa i bottoni di selezione.
     *
     * @param usaSelezione true per usare i tre bottoni di selezione dei records <br>
     */
    public abstract void setUsaSelezione(boolean usaSelezione);


    /**
     * flag - usa il bottone di stampa lista.
     *
     * @param flag true per usare il bottone di stampa lista <br>
     */
    public abstract void setUsaStampaLista(boolean flag);


    /**
     * Abilita o disabilita  l'uso della barra dei totali nella Lista.
     * <p/>
     *
     * @param flag true per usare la barra dei totali, false per non usarla
     */
    public abstract void setUsaTotaliLista(boolean flag);


    /**
     * Ritorna true se il navigatore e' aperto.
     * <p/>
     * Il Navigatore e' aperto se la sua finestra e' visibile sullo schermo.
     *
     * @return true se il navigatore e' aperto
     */
    public abstract boolean isAperto();


    /**
     * Verifica se il navigatore e' stato gia' inizializzato.
     * <p/>
     *
     * @return true se il navigatore e' stato gia' inizializzato
     */
    public abstract boolean isInizializzato();


    /**
     * Recupera il dialogo di Ricerca del navigatore.
     * <p/>
     *
     * @return la ricerca del Navigatore
     */
    public abstract RicercaBase getRicerca();


    /**
     * Assegna un dialogo di ricerca a questo navigatore
     * <p/>
     *
     * @param ricerca il dialogo di ricerca
     */
    public abstract void setRicerca(RicercaBase ricerca);


    /**
     * Ritorna l'oggetto contenente i metodi Query.
     * <p/>
     *
     * @return l'oggetto contenente i metodi Query
     */
    public abstract MetodiQuery query();


    /**
     * Classe interna Enumerazione.
     */
    public enum Tool {

        lista(),
        scheda(),
        menu()

    }// fine della classe


}// fine della interfaccia
