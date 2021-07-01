/**
 * Title:        CampoDati.java
 * Package:      it.algos.base.campo.dati
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 15.12
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.elemento.Elemento;
import it.algos.base.campo.inizializzatore.Init;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.elenco.Elenco;
import it.algos.base.libreria.Lib;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.validatore.Validatore;

import javax.swing.Icon;
import javax.swing.JFormattedTextField;
import javax.swing.table.TableCellEditor;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Regola le funzionalita della conversione dei dati per un Campo <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 15.12
 */
public interface CampoDati {

    /* icone per i vari tipi di campo */
    public static final Icon ICONA_CAMPO_GENERICO = Lib.Risorse.getIconaBase("Campo");

    public static final Icon ICONA_CAMPO_BOOL = Lib.Risorse.getIconaBase("Campo_bool");

    public static final Icon ICONA_CAMPO_NUM = Lib.Risorse.getIconaBase("Campo_num");

    public static final Icon ICONA_CAMPO_DATE = Lib.Risorse.getIconaBase("Campo_date");

    public static final Icon ICONA_CAMPO_ORA = Lib.Risorse.getIconaBase("Campo_ora");

    public static final Icon ICONA_CAMPO_TEXT = Lib.Risorse.getIconaBase("Campo_text");


    /**
     * metodi pubblici implementati nella classe astratta CDBase
     * e/o nelle sue sottoclassi
     */
    public abstract void inizializza();


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public abstract void avvia();


    /**
     * Assegna il valore iniziale al campo.
     * <p/>
     */
    public abstract void initValoreCampo();


    /**
     * Allinea le variabili del Campo: da Archivio verso Memoria.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Archivio (gia' regolata), e regola
     * di conseguenza Memoria, Backup <br>
     */
    public abstract void archivioMemoria();


    /**
     * Allinea le variabili del Campo: da Memoria verso Video.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Video <br>
     */
    public abstract void memoriaVideo();


    /**
     * Allinea le variabili del Campo: da Video verso Memoria.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Video (gia' regolata), e regola
     * di conseguenza Memoria <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     */
    public abstract void videoMemoria();


    /**
     * Allinea le variabili del Campo: da Memoria verso Archivio.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Archivio <br>
     * La variabile archivio e' allineata per la registrazione <br>
     */
    public abstract void memoriaArchivio();


    /**
     * Ritorna il valore Memoria corrispondente a un dato valore Archivio.
     * <p/>
     *
     * @param archivio il valore Archivio
     *
     * @return il valore Memoria corrispondente
     */
    public abstract Object getMemoriaDaArchivio(Object archivio);


    /**
     * Ritorna il valore Archivio corrispondente a un dato valore Memoria.
     * <p/>
     *
     * @param memoria il valore Memoria
     *
     * @return il valore Archivio corrispondente
     */
    public abstract Object getArchivioDaMemoria(Object memoria);


    /**
     * copia da memoria a backup
     */
    public abstract void memoriaBackup();


    /**
     * Ripristina il contenuto della memoria con il backup
     * <p/>
     * Trasporta il valore fino al video
     */
    public abstract void restoreBackup();


    /**
     * Svuota il valore del campo.
     * <p/>
     * Assegna alla variabile Memoria il proprio valore vuoto <br>
     * Allinea la variabile Backup col nuovo valore <br>
     */
    public abstract void reset();


    /**
     * Invocato quando il valore della variabile video viene modificato.
     * <p/>
     */
    public abstract void videoModificato();


    /**
     * Verifica se il campo e' vuoto.
     * <p/>
     *
     * @return true se il campo e' vuoto
     */
    public abstract boolean isVuoto();


    public abstract boolean isModificato();


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Usato per controllare la validita' della scheda <br>
     *
     * @return true se valido, false se non valido.
     */
    public abstract boolean isValido();


    public abstract CampoDati clonaCampo(Campo unCampoParente);


    /**
     * Converte un valore di memoria.
     * <p/>
     * Trasforma in un valore accettabile per questo tipo di campo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param valore in ingresso
     *
     * @return valore accettabile convertito
     *         nullo se non accettabile
     */
    public abstract Object convertiMemoria(Object valore);


    /**
     * setter
     */
    public abstract void setArchivio(Object unValoreArchivio);


    public abstract void setMemoria(Object unValoreMemoria);


    public abstract void setVideo(Object unValoreVideo);


    /**
     * Regola il tipo dati Archivio.
     * <p/>
     *
     * @param unTipoArchivio il tipo dati archivio da assegnare
     */
    public abstract void setTipoArchivio(TipoArchivio unTipoArchivio);


    /**
     * tipo dati Memoria
     */
    public abstract void setTipoMemoria(TipoMemoria tipoMemoria);


    /**
     * tipo dati Video
     */
    public abstract void setTipoVideo(TipoVideo tipoVideo);


    /**
     * Regola l'allineamento del testo nella lista.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param allineamento codice di allineamento
     *
     * @see javax.swing.JTextField
     *      JTextField.LEFT
     *      JTextField.CENTER
     *      JTextField.RIGHT
     *      JTextField.LEADING
     *      JTextField.TRAILING
     */
    public abstract void setAllineamentoLista(int allineamento);


    /**
     * Regola l'inizializzatore del campo.
     * <p/>
     *
     * @param init l'inizializzatore da assegnare
     */
    public abstract void setInit(Init init);


    /**
     * Regola l'inizializzatore di default del campo.
     * <p/>
     * Usato in assenza di un inizializzatore specifico.
     *
     * @return l'inizializzatore di default
     */
    public abstract Init getInitDefault();


    /**
     * Regola l'inizializzatore di default del campo.
     * <p/>
     * Usato in assenza di un inizializzatore specifico.
     *
     * @param inizializzatoreDefault da assegnare
     */
    public abstract void setInitDefault(Init inizializzatoreDefault);


    /**
     * Assegna la lista dei valori interni.
     * <p/>
     *
     * @param listaValori la lista dei valori interni
     *                    stringa separata da virgole
     */
    public abstract void setValoriInterni(String listaValori);


    /**
     * Assegna la lista dei valori interni.
     * <p/>
     *
     * @param listaValori la lista dei valori interni
     */
    public abstract void setValoriInterni(ArrayList listaValori);


    /**
     * Assegna la lista dei valori interni.
     * <p/>
     *
     * @param valori la lista dei valori interni
     */
    public abstract void setValoriInterni(Campo.ElementiCombo[] valori);


    /**
     * Assegna la lista dei valori interni.
     * <p/>
     *
     * @param listaValori la lista dei valori interni
     */
    public abstract void setValoriLegenda(ArrayList<String> listaValori);


    /**
     * Assegna i valori della legenda.
     * <p/>
     *
     * @param valori stringa di valori della legenda, separati da virgola
     */
    public abstract void setValoriLegenda(String valori);


    /**
     * lista della legenda (eventuale) affiancata ai valori
     *
     * @param listaValori la lista dei valori
     */
    public abstract void setValoriInterni(String[] listaValori);


    /**
     * array dei valori interni
     */
    public abstract ArrayList getValoriInterni();

//    /**
//     * flag - se si vuole aggiungere l'elemento "non specificato"
//     * alla lista valori
//     *
//     * @param isUsaNonSpecificato
//     */
//    public abstract void setUsaNonSpecificato(boolean isUsaNonSpecificato);


    public abstract void setNonSpecificatoIniziale(boolean isNonSpecificatoIniziale);


    public abstract void setNascondeNonSpecificato(boolean isNascondeNonSpecificato);

//    /**
//     * flag - se si vuole aggiungere l'elemento "nuovo"
//     * alla lista valori
//     */
//    public abstract void setUsaNuovo(boolean isUsaNuovo);


    /**
     * Determina se il comando "Nuovo record" viene posizionato prima o dopo
     * la lista dei valori.
     * <p/>
     *
     * @param flag true per posizionare prima, false per dopo
     */
    public abstract void setNuovoIniziale(boolean flag);

    /**
     * Se si vuole aggiungere un separatore tra gli
     * elementi speciali ed i valori normali della lista valori
     * <p>
     * @param flag di uso del separatore
     */
    public abstract void setUsaSeparatore(boolean flag);


    /**
     * Ritorna true se il campo e' booleano.
     *
     * @return true se booleano
     */
    public abstract boolean isBooleano();


    /**
     * Ritorna true se il campo e' di testo.
     *
     * @return true se è campo testo
     */
    public abstract boolean isTesto();


    /**
     * Ritorna true se il campo e' di tipo testoArea.
     *
     * @return true se è campo testoArea
     */
    public abstract boolean isTestoArea();


    /**
     * Ritorna true se il campo e' numerico.
     *
     * @return true se è campo numerico
     */
    public abstract boolean isNumero();


    /**
     * Ritorna true se il campo e' data.
     *
     * @return true se è campo data
     */
    public abstract boolean isData();


    /**
     * Ritorna true se il campo e' Timestamp.
     *
     * @return true se è campo Timestamp
     */
    public abstract boolean isTimestamp();


    /**
     * Ritorna true se il campo e' orea.
     *
     * @return true se è campo Time
     */
    public abstract boolean isOra();


    public abstract boolean isElencoInterno();


    public abstract void setElencoInterno(boolean elencoInterno);


    public abstract boolean isUsaRendererElenco();


    public abstract void setUsaRendererElenco(boolean usaRendererValoreInterno);


    /**
     * Restituisce il numero massimo di decimali per il campo
     * <p/>
     *
     * @return il numero massimo di decimali per il campo
     */
    public abstract int getNumDecimali();


    /**
     * Regola il numero di decimali per il campo
     * <p/>
     *
     * @param numDecimali numero di massimo di cifre decimali inseribili
     *                    e numero fisso di cifre decimali visualizzate
     */
    public abstract void setNumDecimali(int numDecimali);

    /**
     * Uso del separatore delle migliaia nel rendering
     * <p/>
     * @param flag true per usare il separatore delle migliaia
     */
    public abstract void setUsaSeparatoreMigliaia(boolean flag);

    /**
     * Ritorna il codice del tipo dati Db gestito dal campo.
     * <p/>
     *
     * @return il codice chiave nella collezione dei tipi dati Db.
     */
    public abstract int getChiaveTipoDatiDb();


    /**
     * Controllo di validit� del valore video.
     * <p/>
     * Controlla che il valore sia compatibile col tipo di dati del Campo <br>
     *
     * @param valoreVideo oggetto da controllare
     *
     * @return true se il valore � compatibile
     */
    public abstract boolean isVideoValido(Object valoreVideo);


    /**
     * Restituisce il numero di elementi validi.
     * <p/>
     * Sono esclusi i separatori, non specificato e nuovo <br>
     *
     * @return numero di elementi validi
     */
    public abstract int getElementiValidi();


    /**
     * Controlla se esiste uno ed un solo elemento valido.
     * <p/>
     *
     * @return vero se esiste un elemento valido
     *         falso se ne esistono zero o più di uno
     */
    public abstract boolean isUnicoValido();


    /**
     * Trasforma un valore nella sua rappresentazione stringa.
     * <p/>
     * Opera secondo le regole del campo.
     *
     * @param valore da trasformare
     *
     * @return il valore rappresentato come stringa
     */
    public abstract String format(Object valore);

    /** getter */

    /**
     * ritorna il codice chiave del tipo dati usato nel database
     */


    public abstract Object getArchivio();


    public abstract Object getMemoria();


    public abstract Object getBackup();


    public abstract Object getVideo();


    public abstract TipoArchivio getTipoArchivio();


    public abstract TipoMemoria getTipoMemoria();


    public abstract TipoVideo getTipoVideo();


    /**
     * ritorna il valore a livello Memoria per l'inizializzazione
     * del campo per un nuovo record.
     * <p/>
     *
     * @param conn la connessione da utilizzare
     *
     * @return il valore Memoria di inizializzazione per nuovo record
     */
    public abstract Object getValoreNuovoRecord(Connessione conn);


    /**
     * Ritorna un oggetto che rappresenta il valore del campo
     * per l'utilizzo in un filtro.
     * <p/>
     * Si usa per costruire un filtro con il valore di memoria corrente del campo.
     *
     * @return il valore per l'utilizzo nel filtro
     */
    public abstract Object getValoreFiltro();


    /**
     * Ritorna l'inizializzatore del campo.
     * <p/>
     *
     * @return l'inizializzatore del campo
     */
    public abstract Init getInit();


    public abstract ArrayList getListaValori();


    public abstract Elenco getElenco();


    public abstract boolean isNascondeNonSpecificato();


    /**
     * Ritorna il valore vuoto per l'oggetto Archivio di questo tipo di campo.
     * <p/>
     *
     * @return il valore vuoto per l'oggetto Archivio
     */
    public abstract Object getValoreArchivioVuoto();


    /**
     * Ritorna il valore vuoto per l'oggetto Memoria di questo tipo di campo.
     * <p/>
     *
     * @return il valore vuoto per l'oggetto Memoria
     */
    public abstract Object getValoreMemoriaVuoto();


    /**
     * Ritorna il valore vuoto per l'oggetto Video di questo tipo di campo.
     * <p/>
     *
     * @return il valore vuoto per l'oggetto Video
     */
    public abstract Object getValoreVideoVuoto();


    public abstract Elemento getElementoNuovo();


    public abstract Elemento getElementoNonSpecificato();

//    public abstract boolean isInizializzato();


    /**
     * Recupera il valore di elenco selezionato.
     * <p/>
     *
     * @return il valore corrispondente
     */
    public abstract Object getValoreElenco();


    /**
     * Assegna il valore memoria del campo in base a un valore di elenco.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param valore da cercare nell'elenco
     */
    public abstract void setValoreDaElenco(Object valore);


    /**
     * Recupera il valore per una data posizione nella lista valori.
     * <p/>
     *
     * @param posizione richiesta
     *
     * @return il valore corrispondente
     */
    public abstract Object getValoreElenco(int posizione);


    /**
     * Recupera il valore corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public abstract Object getValore();


    /**
     * Recupera il valore booleano corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public abstract boolean getBool();


    /**
     * Recupera il valore stringa corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public abstract String getString();


    /**
     * Recupera il valore intero corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public abstract int getInt();


    /**
     * Recupera il valore doppio corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public abstract double getDouble();


    /**
     * Recupera il valore data corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public abstract Date getData();


    public abstract RendererBase getRenderer();


    /**
     * Assegna un renderer per la visualizzazione nelle liste
     * <p/>
     *
     * @param renderer da assegnare
     */
    public abstract void setRenderer(RendererBase renderer);


    /**
     * Recupera l'editor del campo.
     * <p/>
     *
     * @return l'editor del campo
     */
    public abstract TableCellEditor getEditor();


    /**
     * Assegna l'editor per il campo nella cella della lista.
     *
     * @param editor del campo
     *
     * @return editor
     */
    public abstract TableCellEditor setEditor(TableCellEditor editor);


    /**
     * flag di controllo per usare campi di ricerca doppi (range da...a...).
     *
     * @return true se ricercabile con range
     */
    public abstract boolean isUsaRangeRicerca();


    /**
     * flag di controllo per usare campi di ricerca doppi (range da...a...).
     *
     * @param usaRangeRicerca flag booleano
     */
    public abstract void setUsaRangeRicerca(boolean usaRangeRicerca);


    /**
     * Ritorna true se usa solo la porzione data nella ricerca
     * di campi Timestamp
     * <p/>
     *
     * @return true se usa solo la porzione Data nella ricerca
     */
    public abstract boolean isRicercaSoloPorzioneData();


    /**
     * flag di controllo per usare solo la porzione data nella ricerca
     * di campi Timestamp
     * <p/>
     *
     * @param flag flag per usare solo la porzione Data
     */
    public abstract void setRicercaSoloPorzioneData(boolean flag);


    /**
     * Ritorna l'elenco degli operatori filtro disponibili per il campo
     *
     * @return l'elenco delle chiavi degli operatori filtro disponibili
     */
    public abstract ArrayList<String> getOperatoriRicerca();


    /**
     * aggiunge un operatore di ricerca al campo.
     *
     * @param operatore la chiave dell'operatore
     *
     * @see it.algos.base.query.filtro.Filtro.Op
     */
    public abstract void addOperatoreRicerca(String operatore);


    /**
     * svuota la lista degli operatori di ricerca del campo.
     */
    public abstract void clearOperatoriRicerca();


    /**
     * Ritorna il formattatore per l'editing.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @return il formattatore per l'editing
     */
    public abstract JFormattedTextField.AbstractFormatter getEditFormatter();


    /**
     * Ritorna il formattatore per il display.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @return il formattatore per il display
     */
    public abstract JFormattedTextField.AbstractFormatter getDisplayFormatter();


    /**
     * Ritorna il formattatore del campo
     * <p/>
     * Sovrascritto dalle sottoclassi (CDFormat)
     *
     * @return l'oggetto formattatore del campo
     */
    public abstract Format getFormat();


    /**
     * Recupera il validatore al campo.
     *
     * @return il validatore associato al campo
     */
    public abstract Validatore getValidatore();


    /**
     * Assegna un validatore al campo.
     *
     * @param validatore da assegnare
     */
    public abstract void setValidatore(Validatore validatore);


    /**
     * Ritorna un'icona che rappresenta il tipo di campo.
     * <p/>
     *
     * @return l'icona del campo
     */
    public abstract Icon getIcona();


    /**
     * metodi pubblici implementati nella classe astratta CampoAstratto
     */
    public abstract void setCampoParente(Campo unCampoParente);


    /**
     * flag di controllo per i campi da utilizzare nella ricerca standard.
     *
     * @return true se ricercabile
     */
    public abstract boolean isRicercabile();


    /**
     * flag di controllo per i campi da utilizzare nella ricerca standard.
     *
     * @param ricercabile flag booleano
     */
    public abstract void setRicercabile(boolean ricercabile);


    /**
     * Ritorna l'operatore di ricerca per questo campo.
     * <p/>
     *
     * @return l'operatore di ricerca
     *
     * @see it.algos.base.database.util.Operatore
     */
    public abstract String getOperatoreRicercaDefault();


    /**
     * Imposta l'operatore di ricerca di default
     * <p/>
     *
     * @param operatore di ricerca di default
     */
    public abstract void setOperatoreRicercaDefault(String operatore);


    /**
     * Assegna il formattatore del campo
     * <p>
     * @param format il formattatore
     */
    public void setFormat(Format format);

    /**
     * Assegna il formato al campo data
     * <p>
     * @param pattern come da SimpleDateFormat
     */
    public void setDateFormat(String pattern);

}