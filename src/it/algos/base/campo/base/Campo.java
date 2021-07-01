/**
 * Title:        Campo.java
 * Package:      prove.nuovocampo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 14.52
 */

package it.algos.base.campo.base;

import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.inizializzatore.Init;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.CVBase;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.database.Db;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.GestioneEventi;
import it.algos.base.form.Form;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.validatore.Validatore;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.Viste;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.table.TableCellEditor;
import java.awt.Color;
import java.awt.Font;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Implementare il pattern Facade, per gli oggetti i tipo campo <br>
 * B - Agisce da concentratore e punto di ingresso/uscita comune per tutti i
 * metodi che interagiscono con gli oggetti campo <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 14.52
 */
public interface Campo extends GestioneEventi {

    /**
     * disegna sfondi colorati ai vari componenti del campo
     */
    public static final boolean DEBUG = false;


    public abstract boolean isDebug();


    public abstract boolean inizializza();


    public abstract void avvia();


    /**
     * Assegna il valore iniziale al campo.
     * <p/>
     */
    public abstract void initValoreCampo();


    /**
     * Svuota il valore del campo.
     * <p/>
     * Assegna alla variabile Memoria il proprio valore vuoto <br>
     * Allinea la variabile Backup col nuovo valore <br>
     * Fa risalire i valori fino alla GUI <br>.
     */
    public abstract void reset();


    /**
     * flag per indicare se il campo e' inizializzato
     */
    public abstract void setInizializzato(boolean inizializzato);


    public abstract void setModulo(Modulo unModulo);


    /**
     * Inserimento di un valore.
     * <p/>
     * Il valore viene inserito in archivio <br>
     * L'archivio viene trasferito in memoria <br>
     * La memoria viene copiato nel backup <br>
     * La memoria viene trasferita a video e GUI <br>
     */
    public abstract void setValore(Object valore);


    /**
     * Inserimento di un valore con registrazione del backup.
     * <p/>
     * Il valore viene inserito in memoria <br>
     * La memoria viene copiata nel backup <br>
     * La memoria viene trasferita a video e GUI <br>
     */
    public abstract void setValoreIniziale(Object valore);


    /**
     * Assegna un valore pilota al campo.
     * <p/>
     * Significativo solo se il campo gestisce un Navigatore.
     *
     * @param valore pilota da assegnare al Navigatore gestito dal campo
     */
    public abstract void setValorePilota(int valore);


    /**
     * Ritorna il formattatore del campo
     * <p/>
     * Sovrascritto dalle sottoclassi (CDFormat)
     *
     * @return l'oggetto formattatore del campo
     */
    public abstract Format getFormat();


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


    /**
     * Regola il numero di decimali per il campo
     * <p/>
     *
     * @param numDecimali numero di massimo di cifre decimali inseribili
     * e numero fisso di cifre decimali visualizzate
     */
    public abstract void setNumDecimali(int numDecimali);


    public abstract Form getForm();


    public abstract void setForm(Form form);


    /**
     * Restituisce la scheda attualmente proprietaria del campo.
     * <p/>
     *
     * @return la scheda proprietaria
     */
    public abstract Scheda getScheda();


    public abstract void setCampoLogica(CampoLogica unCampoLogica);


    public abstract void setCampoDB(CampoDB unCampoDB);


    public abstract void setCampoDati(CampoDati unCampoDati);


    public abstract void setCampoLista(CampoLista unCampoLista);


    public abstract void setCampoScheda(CampoScheda unCampoScheda);


    public abstract void setCampoVideo(CampoVideo unCampoVideo);


    public abstract void setCampoVideoAssoluto(CampoVideo unCampoVideo);


    public abstract void setTitoloColonna(String unTitoloColonna);


    public abstract void setLarLista(int larghezzaColonna);


    /**
     * Regola la larghezza del campo.
     * <p/>
     * Regola la larghezza della colonna nella lista <br>
     * Regola la larghezza del pannello campo nella scheda <br>
     *
     * @param larghezza sia della colonna che del pannello campo
     */
    public abstract void setLarghezza(int larghezza);


    /**
     * Assegna un renderer per la visualizzazione nelle liste
     * <p/>
     *
     * @param renderer da assegnare
     */
    public abstract void setRenderer(RendererBase renderer);


    /**
     * Assegna l'editor per il campo nella cella della lista.
     *
     * @param editor del campo
     *
     * @return editor
     */
    public abstract TableCellEditor setEditor(TableCellEditor editor);


    /**
     * Regola il numero delle righe.
     * <p/>
     * Numero di righe da visualizzare <br>
     * (l'altezza del pannelloComponenti dipende dalle righe) <br>
     *
     * @param numeroRighe visibili
     */
    public abstract void setNumeroRighe(int numeroRighe);


    /**
     * Assegna una scheda per l'editing del record esterno.
     * <p/>
     * (significativo per campi di tipo Combo o Estratto)
     *
     * @param nomeScheda nella collezione schede del modulo esterno
     */
    public abstract void setSchedaPop(String nomeScheda);


    /**
     * Altezza dei componenti interni al PannelloComponenti
     */
    public abstract void setAltezzaComponentiScheda(int altezza);


    /**
     * Larghezza dei componenti interni al PannelloComponenti
     */
    public abstract void setLarScheda(int larghezza);


    public abstract void setVisibileVistaDefault(boolean isVisibile);


    public abstract void setVisibileVistaDefault();


    /**
     * Regola il testo della etichetta del campo scheda.
     * <p/>
     * Se alla creazione del campo nel Modello, non viene richiesto nessun testo
     * di default utilizza il nome interno del campo <br>
     */
    public abstract void setTestoEtichetta(String unTestoEtichetta);


    /**
     * Regola l'ordine privato del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param ordine l'ordine da assegnare al campo
     */
    public abstract void setOrdinePrivato(Ordine ordine);


    /**
     * Regola l'ordine privato del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public abstract void setOrdinePrivato(Campo campo);


    /**
     * Regola l'ordine pubblico del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param ordine l'ordine da assegnare al campo
     */
    public abstract void setOrdinePubblico(Ordine ordine);


    /**
     * Regola l'ordine pubblico del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public abstract void setOrdinePubblico(Campo campo);


    /**
     * Regola gli ordini pubblico e privato del campo per le liste.
     * <p/>
     *
     * @param ordine l'ordine pubblico e privato da assegnare al campo
     */
    public abstract void setOrdine(Ordine ordine);


    /**
     * Aggiunge un ordine privato al campo per le liste.
     * <p/>
     *
     * @param unCampo il campo da aggiungere all'ordine esistente
     */
    public abstract void addOrdinePrivato(Campo unCampo);


    /**
     * Aggiunge un campo all' ordine pubblico per le liste.
     * <p/>
     *
     * @param unCampo il campo da aggiungere
     */
    public abstract void addOrdinePubblico(Campo unCampo);


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param ordine da assegnare
     */
    public abstract void setOrdineElenco(Ordine ordine);


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param campo di ordinamento da assegnare
     */
    public abstract void setOrdineElenco(Campo campo);


    /**
     * Aggiunge il campo al set di default della scheda.
     */
    public abstract void setPresenteScheda(boolean presente);


    /**
     * Rende visibile/invisibile il PannelloCampo a video.
     */
    public abstract void setVisibile(boolean visibile);


    public abstract boolean isVisibile();


    /**
     * Flag per identificare un campo calcolato.
     * <p/>
     *
     * @return vero se il campo è calcolato
     */
    public abstract boolean isCalcolato();


    /**
     * Regola la ridimensionabilita' del campo in una lista.
     * <p/>
     *
     * @param isRidimensionabile true per rendere il campo ridimensionabile,
     * false per renderlo fisso
     */
    public abstract void setRidimensionabile(boolean isRidimensionabile);


    public abstract void setNomeModuloLinkato(String unNomeModuloLinkato);


    /**
     * Regola il tool tip per la lista.
     */
    public abstract void setToolTipLista(String toolTipText);


    /**
     * Regola il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     * Invoca il metodo delegato della classe specifica <br>
     *
     * @param testoComponente testo descrittivo
     */
    public abstract void setTestoComponente(String testoComponente);


    public abstract void setValoriInterni(String unaListavalori);


    public abstract void setValoriInterni(ArrayList unaListavalori);


    public abstract void setValoriInterni(Campo.ElementiCombo[] valori);


    public abstract void setValoriLegenda(ArrayList<String> unaListavalori);


    public abstract void setValoriLegenda(String valori);


    public abstract void setNomeColonnaListaLinkata(String unNomeCampoLink);


    public abstract void setNomeVistaLinkata(String unNomeVistaLink);


    public abstract void setVistaLinkata(Viste vista);


    public abstract void setNomeCampoValoriLinkato(String nomeCampoSchedaLinkato);


    public abstract void setCampoLinkato(Campi campo);


    /**
     * assegna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @param campo da visualizzare
     */
    public abstract void setCampoValoriLinkato(Campo campo);


    /**
     * assegna un estratto che fornisce i valori linkati
     * <p/>
     * l'estratto ha la precedenza sul campo linkato
     *
     * @param estratto che fornisce una matrice doppia con codici e valori
     */
    public abstract void setEstrattoLinkato(Estratti estratto);


    /**
     * Aggiunge una colonna del modulo linkato alla Vista per il combo.
     * <p/>
     *
     * @param nome del campo del modulo linkato
     */
    public abstract void addColonnaCombo(String nome);


    /**
     * Aggiunge una colonna alla Vista per il combo.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     * @param nomeCampo nome del campo
     */
    public abstract void addColonnaCombo(String nomeModulo, String nomeCampo);


    public abstract CampoLogica getCampoLogica();


    public abstract CampoDB getCampoDB();


    public abstract CampoDati getCampoDati();


    public abstract CampoLista getCampoLista();


    public abstract CampoScheda getCampoScheda();


    public abstract CampoVideo getCampoVideo();


    public abstract Modulo getModulo();


    public abstract CampoVideo getCampoVideoNonDecorato();


    public abstract String getNomeInterno();


    /**
     * Nome chiave interno del campo.
     * <p/>
     * (usato per le collezioni HashMap del programma)
     *
     * @param nome il nome interno del campo
     */
    public abstract void setNomeInternoCampo(String nome);


    /**
     * Ritorna la chiave del campo
     * <p/>
     * Nome modulo (se esiste) + nome interno
     */
    public String getChiaveCampo();


    /**
     * Restituisce il componente video del campo.
     * <p/>
     *
     * @return il componente video del campo
     */
    public abstract JComponent getComponenteVideo();

//    /**
//     * Restituisce il componente toggle del campo.
//     * <p/>
//     * Per check boxes, radio bottoni ecc..
//     *
//     * @return il componente toggle del campo
//     */
//    public abstract JToggleButton getComponenteToggle();


    /**
     * Restituisce il pannello campo del campo video
     * <p/>
     * E' il pannello che contiene tutti i componenti del campo
     * (etichetta, decoratori, componenti interni)
     *
     * @return il pannello campo
     */
    public abstract JPanel getPannelloCampo();


    /**
     * Restituisce il pannello componenti campo del campo video
     * <p/>
     * E' il pannello che contiene i soli componenti interni del campo
     *
     * @return il pannello componenti
     */
    public abstract JPanel getPannelloComponenti();


    /**
     * Ritorna il testo dell'etichetta
     * <p/>
     *
     * @return il testo dell'etichetta
     *         Se non ha decoratore etichetta ritorna il nome interno
     */
    public abstract String getTestoEtichetta();


    /**
     * Ritorna la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @return la larghezza in pixel dell'etichetta
     */
    public abstract int getLarghezzaEtichetta();


    /**
     * Regola la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @param larghezza in pixel dell'etichetta
     */
    public abstract void setLarghezzaEtichetta(int larghezza);


    /**
     * Regola (sinistra o destra) l'allineamento del testo dell'etichetta
     * <p/>
     *
     * @param bandiera tipo di allineamento
     */
    public abstract void setAllineamentoEtichetta(Pannello.Bandiera bandiera);


    /**
     * Controlla se il campo ha l'etichetta e se questa è a sinistra.
     * <p/>
     *
     * @return vero se esiste l'etichetta ed è a sinistra
     */
    public abstract boolean isEtichettaSinistra();


    public abstract boolean isPresenteVistaDefault();


    public abstract boolean isInizializzato();


    /**
     * Verifica se il valore Memoria e' vuoto.
     * <p/>
     *
     * @return true se il valore memoria e' vuoto
     *         (per questo tipo di campo)
     */
    public abstract boolean isVuoto();


    /**
     * Determina se il campo e' obbligatorio per registrazione / conferma
     * <p/>
     *
     * @return true se obbligatorio
     */
    public abstract boolean isObbligatorio();


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Non evidenzia il campo e non mostra messaggi.<br>
     *
     * @return true se valido, false se non valido.
     */
    public abstract boolean isValido();


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Se non e' valido:<br>
     * se il validatore lo prevede, evidenzia il campo colorando lo sfondo<br>
     */
    public abstract void checkValido();


    public abstract void aggiornaCampo();


    /**
     * Recupera l'eventuale navigatore gestito dal campo.
     * <p/>
     *
     * @return il navigatore gestito dal campo
     */
    public abstract Navigatore getNavigatore();


    /**
     * Regola l'uso dell'indice sul campo
     * <p/>
     *
     * @param flag true se il campo deve essere indicizzato
     */
    public abstract void setIndicizzato(boolean flag);


    /**
     * Usa un indice unico sul campo
     * <p/>
     *
     * @param flag per usare un indice unico
     */
    public abstract void setUnico(boolean flag);


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di cancellazione del record sulla tavola uno.
     * <p/>
     * Valori possibili (da interfaccia Db)<br>
     * - Db.Azione.noAction non fa nulla<br>
     * - Db.Azione.cascade elimina i record<br>
     * - Db.Azione.setNull pone il valore a NULL <br>
     * - Db.Azione.setDefault assegna il valore di default<br>
     */
    public abstract void setAzioneDelete(Db.Azione azione);


    /**
     * Azione da intraprendere sulla tavola molti in caso
     * di modifica del record sulla tavola uno.
     * <p/>
     * Valori possibili (da interfaccia Db)<br>
     * - Db.Azione.noAction non fa nulla<br>
     * - Db.Azione.cascade modifica i record <br>
     * - Db.Azione.setNull pone il valore a NULL <br>
     * - Db.Azione.setDefault assegna il valore di default<br>
     */
    public abstract void setAzioneUpdate(Db.Azione azione);


    /**
     * Ritorna l'inizializzatore del campo.
     * <p/>
     *
     * @return l'inizializzatore del campo
     */
    public abstract Init getInit();


    /**
     * Regola l'inizializzatore del campo.
     * <p/>
     *
     * @param init l'inizializzatore da assegnare
     */
    public abstract void setInit(Init init);


    /**
     * Regola il valore intero di default per i nuovi record.
     * <p/>
     *
     * @param valore il valore intero da assegnare
     */
    public abstract void setValoreDefault(int valore);


    /**
     * Regola il valore stringa di default per i nuovi record.
     * <p/>
     *
     * @param valore il valore stringa da assegnare
     */
    public abstract void setValoreDefault(String valore);


    /**
     * Regola il valore booleano di default per i nuovi record.
     * <p/>
     *
     * @param valore il valore booleano da assegnare
     */
    public abstract void setValoreDefault(boolean valore);


    /**
     * regola il valore del campo in memoria.
     * <p/>
     *
     * @param unValoreMemoria il valore Memoria da assegnare
     */
    public abstract void setMemoria(Object unValoreMemoria);


    /**
     * Converte un valore da livello Business Logic
     * a livello Database per questo campo.
     * <p/>
     *
     * @param valoreIn il valore a livello di Business Logic da convertire
     * @param db il database a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Db
     */
    public abstract Object bl2db(Object valoreIn, Db db);


    /**
     * Converte un valore da livello Database
     * a livello Business Logic per questo campo.
     * <p/>
     *
     * @param valoreIn il valore a livello di Database da convertire
     * @param db il database a fronte del quale effettuare la conversione
     *
     * @return il valore convertito al livello di Business Logic
     */
    public abstract Object db2bl(Object valoreIn, Db db);


    public abstract boolean isModificabileLista();


    public abstract void setModificabileLista(boolean modificabile);


    /**
     * Controlla se il campo è totalizzabile nelle liste
     * <p/>
     *
     * @return true se totalizzabile
     */
    public abstract boolean isTotalizzabile();


    /**
     * Indica se il campo è totalizzabile nelle liste
     * <p/>
     *
     * @param flag di controllo
     */
    public abstract void setTotalizzabile(boolean flag);


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
     * Ritorna lo stato corrente di abilitazione del campo.
     * <p/>
     *
     * @return true se abilitato, false se disabiitato
     */
    public abstract boolean isAbilitato();


    /**
     * Contrassegna il campo come abilitato o disabilitato.
     * <p/>
     * Questa regolazione supera setModificabile().
     * Se il campo è disabilitato, non è modificabile e non risponde al
     * comando setModificabile()<br>
     * Se il campo è abilitato, risponde al comando setModificabile() e
     * può quindi essere reso modificabile o meno.<br>
     * Utilizzato tipicamente in fase statica, può essere modificato
     * anche in fase dinamica.
     *
     * @param flag true per abilitare, false per disabilitare
     */
    public abstract void setAbilitato(boolean flag);


    /**
     * Rende il campo focusable o non focusable.
     * <p/>
     *
     * @param flag di controllo dell'attributo Focusable
     */
    public abstract void setFocusable(boolean flag);


    /**
     * Assegna il colore di primo piano.
     * <p/>
     *
     * @param colore da assegnare
     */
    public abstract void setForegroundColor(Color colore);


    /**
     * Assegna il colore di sfondo.
     * <p/>
     *
     * @param colore da assegnare
     */
    public abstract void setBackgroundColor(Color colore);


    /**
     * Assegna il font al campo.
     * <p/>
     *
     * @param font da assegnare
     */
    public abstract void setFont(Font font);


    /**
     * Recupera lo stato corrente di modificabilità del campo.
     * <p/>
     *
     * @return lo stato di modificabilità del campo
     */
    public abstract boolean isModificabile();


    /**
     * Rende il campo modificabile o meno.
     * <p/>
     * Se il flag è true, risponde solo se il campo è abilitato.<br>
     *
     * @param flag true se il campo deve essere modificabile
     */
    public abstract void setModificabile(boolean flag);


    /**
     * Regola l'allineamento del testo nella scheda.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param allineamento codice di allineamento
     *
     * @see javax.swing.SwingConstants
     *      SwingConstants.LEFT
     *      SwingConstants.CENTER
     *      SwingConstants.RIGHT
     *      SwingConstants.LEADING
     *      SwingConstants.TRAILING
     */
    public abstract void setAllineamento(int allineamento);


    /**
     * Regola l'allineamento del testo nella lista.
     * <p/>
     *
     * @param allineamento codice di allineamento
     *
     * @see javax.swing.SwingConstants
     *      SwingConstants.LEFT
     *      SwingConstants.CENTER
     *      SwingConstants.RIGHT
     *      SwingConstants.LEADING
     *      SwingConstants.TRAILING
     */
    public abstract void setAllineamentoLista(int allineamento);


    /**
     * Determina se e come il campo si espande nel proprio contenitore
     * <p/>
     *
     * @param espandibilita codifica del tipo di espandibilità
     */
    public abstract void setEspandibilita(CampoVideo.Espandibilita espandibilita);


    /**
     * Pone il fuoco sul componente del campo video.
     * <p/>
     * Attenzione! il componente deve essere focusable, visibile e displayable,
     * altrimenti la chiamata non ha effetto. <br>
     * Per essere displayable, deve essere effettivamente visualizzato
     * a video, cioe' contenuto in un contenitore che faccia capo a una
     * finestra gia' visibile o gia' packed. <br>
     */
    public abstract void grabFocus();


    /**
     * flag di controllo per i campi da utilizzare nella ricerca standard.
     *
     * @param ricercabile flag booleano
     */
    public abstract void setRicercabile(boolean ricercabile);


    /**
     * flag di controllo per usare campi di ricerca doppi (range da...a...).
     *
     * @param usaRangeRicerca flag booleano
     */
    public abstract void setUsaRangeRicerca(boolean usaRangeRicerca);


    /**
     * Ritorna un oggetto che rappresenta il valore del campo per un filtro.
     * <p/>
     * Si usa per costruire un filtro con il valore di memoria corrente del campo.
     *
     * @return il valore di filtro
     */
    public abstract Object getValoreFiltro();


    /**
     * flag - se si vuole aggiungere l'elemento "non specificato"
     * alla lista valori
     *
     * @param flag
     */
    public abstract void setUsaNonSpecificato(boolean flag);


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public abstract void setUsaNuovo(boolean flag);


    /**
     * Determina se il comando "Nuovo record" viene posizionato prima o dopo
     * la lista dei valori.
     * <p/>
     *
     * @param flag true per posizionare prima, false per dopo
     */
    public abstract void setNuovoIniziale(boolean flag);


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public abstract void setUsaModifica(boolean flag);


    /**
     * Ritorna il filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro base
     */
    public abstract Filtro getFiltroBase();


    /**
     * Assegna un filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public abstract void setFiltroBase(Filtro filtro);


    /**
     * Ritorna il filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro corrente
     */
    public abstract Filtro getFiltroCorrente();


    /**
     * Assegna un filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public abstract void setFiltroCorrente(Filtro filtro);


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
     * Assegna un validatore non vuoto al campo.
     */
    public abstract void setCampoNonVuoto();

//    /**
//     * Campo obbligatorio per registrazione/conferma scheda/dialogo.
//     * <p/>
//     *
//     * @param obbligatorio
//     */
//    public abstract void setObbligatorio(boolean obbligatorio);
//
//
//    /**
//     * Campo obbligatorio per registrazione/conferma scheda/dialogo.
//     * <p/>
//     */
//    public abstract void setObbligatorio();


    /**
     * Assegna la posizione dell'etichetta.
     *
     * @param posEtichetta sopra o sotto
     */
    public abstract void setPosEtichetta(int posEtichetta);


    /**
     * Regola l'orientamento del layout del Pannello Componenti.
     * <p/>
     *
     * @param orientamento il codice di orientamento
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    public abstract void setOrientamentoComponenti(int orientamento);


    public abstract CampoBase getCampoBase();


    /**
     * Ritorna un'icona che rappresenta il tipo di campo.
     * <p/>
     *
     * @return l'icona del campo
     */
    public abstract Icon getIcona();


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param azione azione da aggiungere
     */
    public abstract void aggiungeListener(BaseListener azione);


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


    /**
     * Recupera il valore di elenco correntemente selezionato.
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
    public void setValoreDaElenco(Object valore);


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
     * @see Filtro.Op
     */
    public abstract void addOperatoreRicerca(String operatore);


    /**
     * svuota la lista degli operatori di filtro al campo.
     */
    public abstract void clearOperatoriRicerca();


    /**
     * Ritorna l'operatore di ricerca di default per questo campo.
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
     * Classe interna per passare i metodi della classe VideoFactory
     *
     * @return decoratore video
     */
    public abstract CVBase.Decora decora();


    /**
     * Esegue l'operazione prevista.
     * <p/>
     * Riceve una lista di valori arbitrari (devono essere dello stesso tipo) <br>
     * Recupera dal campo il tipo di operazione da eseguire <br>
     * Esegue l'operazione su tutti i valori <br>
     * restituisce il valore risultante <br>
     *
     * @param valori in ingresso
     *
     * @return risultato dell'operazione
     */
    public abstract Object esegueOperazione(ArrayList<Object> valori);


    /**
     * Clona il campo.
     * <p/>
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting <br>
     *
     * @return campo clonato
     */
    public abstract Campo clonaCampo();


    /**
     * Interfaccia per le enumeration dei combo interni.
     * <p/>
     */
    public interface ElementiCombo {
        public int getCodice();
    }// fine della Enumeration

}// fine della interfaccia
