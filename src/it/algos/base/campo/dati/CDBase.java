/**
 * Title:        CDBase.java
 * Package:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 20.45
 */

package it.algos.base.campo.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoAstratto;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.campo.elemento.Elemento;
import it.algos.base.campo.inizializzatore.Init;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.campo.tipodati.tipoarchivio.TipoArchivio;
import it.algos.base.campo.tipodati.tipomemoria.TipoMemoria;
import it.algos.base.campo.tipodati.tipovideo.TipoVideo;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.database.connessione.Connessione;
import it.algos.base.database.util.Operatore;
import it.algos.base.elenco.Elenco;
import it.algos.base.errore.Errore;
import it.algos.base.filtro.FiltroUscita;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.Tavola;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.validatore.Validatore;

import javax.swing.AbstractCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Regola le funzionalita della conversione dei dati per un Campo <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 20.45
 */
public abstract class CDBase extends CampoAstratto implements Cloneable, CampoDati {

    private static final boolean USA_RANGE_RICERCA = false;

    /**
     * valore del campo in archivio
     */
    protected Object unValoreArchivio = null;

    /**
     * valore del campo in memoria
     */
    protected Object unValoreMemoria = null;

    /**
     * valore iniziale del campo in memoria
     */
    protected Object unValoreBackup = null;

    /**
     * valore corrente del campo a video
     */
    protected Object unValoreVideo = null;

    /**
     * tipo dati Archivio (singleton)
     */
    protected TipoArchivio unTipoArchivio = null;

    /**
     * tipo dati Memoria (singleton)
     */
    protected TipoMemoria tipoMemoria = null;

    /**
     * tipo dati Video (singleton)
     */
    protected TipoVideo tipoVideo = null;

//    /**
//     * numero di decimali (solo per alcuni tipi)
//     */
//    private int numDecimali = 0;

    /**
     * tipo di render per i valori nella lista
     */
    private RendererBase renderer = null;

    /**
     * tipo di editor per i valori nella lista
     */
    private TableCellEditor editor = null;

    /**
     * Inizializzatore del campo per nuovo record
     */
    private Init inizializzatore = null;

    /**
     * Inizializzatore di default del campo per nuovo record
     * Usato in assenza di un inizializzatore specifico
     */
    private Init inizializzatoreDefault = null;

    private FiltroUscita filtroVideo = null;

    /**
     * Validatore del campo
     */
    private Validatore validatore;

    /**
     * flag di controllo per i campi da utilizzare nella ricerca standard
     */
    private boolean isRicercabile = false;

    /**
     * flag di controllo per usare campi di ricerca doppi (range da...a...)
     */
    private boolean usaRangeRicerca = false;

    /**
     * flag - significativo solo se se è un Timestamp ricercabile
     * nella ricerca usa solo la porzione relativa alla data
     */
    private boolean ricercaSoloPorzioneData = false;

    /**
     * lista degli operatori di ricerca disponibili per le ricerche su questo campo
     */
    private ArrayList<String> operatoriRicerca;

    /**
     * Operatore di default da utilizzare per la ricerca.
     * <p/>
     * (Significativo solo per ricerche non su range)
     */
    private String operatoreRicercaDefault = null;

    /**
     * Icona che rappresenta il tipo di campo
     * (usata per esempio nel dialogo di export)
     */
    private Icon icona;

    /**
     * codice di allineamento per del campo nella lista
     * da costanti SwingConstants
     * Il valore -1 indica di non modificare l'allineamento
     */
    private int allineamentoLista = -1;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDBase() {
        /* rimanda al costruttore di questa classe */
        this(null, null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore con solo campo parente <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDBase(Campo unCampoParente) {
        /* rimanda al costruttore di questa classe */
        this(unCampoParente, null, null, null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     * @param unTipoArchivio il tipo di dati per la variabile Archivio
     * @param tipoMemoria il tipo di dati per la variabile Memoria
     * (interfaccia TipoMemoria)
     * @param tipoVideo il tipo di dati per la variabile Video
     * (interfaccia TipoVideo)
     */
    public CDBase(Campo unCampoParente,
                  TipoArchivio unTipoArchivio,
                  TipoMemoria tipoMemoria,
                  TipoVideo tipoVideo) {

        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try {                                   // prova ad eseguire il codice
            this.inizia(unTipoArchivio, tipoMemoria, tipoVideo);
        } catch (Exception unErrore) {           // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * <p/>
     * Metodo chiamato direttamente dal costruttore
     *
     * @param unTipoArchivio il tipo di dati per la variabile Archivio
     * @param tipoMemoria il tipo di dati per la variabile Memoria
     * (interfaccia TipoMemoria)
     * @param tipoVideo il tipo di dati per la variabile Video
     * (interfaccia TipoVideo)
     *
     * @throws Exception unaEccezione
     */
    private void inizia(TipoArchivio unTipoArchivio,
                        TipoMemoria tipoMemoria,
                        TipoVideo tipoVideo) throws Exception {

        /* regola il tipo Archivio */
        this.setTipoArchivio(unTipoArchivio);

        /* regola il tipo Memoria */
        this.setTipoMemoria(tipoMemoria);

        /* regola il tipo Video */
        this.setTipoVideo(tipoVideo);

        /*
         * assegna il valore iniziale Memoria vuoto e lo fa
         * risalire fino al al video
         */
        this.setMemoria(this.getValoreMemoriaVuoto());
        this.memoriaVideo();

        /*
         * Assegna l'inizializzatore di default
         * (usa il valore Memoria vuoto)
         */
        this.setInitDefault(InitFactory.standard(this.getCampoParente()));

        this.setEditor(new FieldCellEditor());

        /* regola l'uso di default del range di ricerca */
        this.setUsaRangeRicerca(USA_RANGE_RICERCA);

        /* crea l'elenco degli operatori filtro disponibili */
        this.setOperatoriRicerca(new ArrayList<String>());

        /* operatore di ricerca di default */
        this.setOperatoreRicercaDefault(Operatore.UGUALE);

        /* assegna una icona generica */
        this.setIcona(ICONA_CAMPO_GENERICO);

    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {

        try { // prova ad eseguire il codice

            /* invoca il metodo (quasi) sovrascritto della superclasse */
            super.inizializzaCampoAstratto();

            /* regola l'allineamento */
            this.regolaAllineamentoLista();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
    }


    /**
     * Cell editor per il campo.
     * </p>
     */
    protected final class FieldEditor extends DefaultCellEditor {

        public FieldEditor(JTextField jTextField) {
            super(jTextField);
        }


        public FieldEditor(JCheckBox jCheckBox) {
            super(jCheckBox);
        }


        public FieldEditor(JComboBox jComboBox) {
            super(jComboBox);
        }


        public Object getCellEditorValue() {
            /* variabili e costanti locali di lavoro */
            Object valoreIn;
            String stringa;
            Object valoreOut = null;

            try { // prova ad eseguire il codice
                valoreIn = super.getCellEditorValue();
                stringa = Lib.Testo.getStringa(valoreIn);
                valoreOut = getValoreEditor(stringa);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return valoreOut;
        }


    } // fine della classe 'interna'


    /**
     * Ritorna il valore a livello di memoria
     * per il campo editato nella cella.
     * <p/>
     *
     * @param stringaIn il valore in uscita dall'editor in formato stringa
     *
     * @return il valore convertito a livello memoria
     */
    protected Object getValoreEditor(String stringaIn) {
        /* variabili e costanti locali di lavoro */
        Object valore = null;

        try {    // prova ad eseguire il codice
            valore = stringaIn;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Assegna il valore iniziale al campo.
     * <p/>
     */
    public void initValoreCampo() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Init init;
        Object valore;

        try {    // prova ad eseguire il codice
            /* se il campo ha un inizializzatore,
             * assegna il valore iniziale */
            init = this.getInit();
            if (init == null) {
                init = this.getInitDefault();
                if (init == null) {
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if-else

            if (continua) {
                if (init != null) {
                    valore = init.getValore();
                    this.getCampoParente().setValore(valore);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Allinea le variabili del Campo: da Archivio verso Memoria.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Archivio (gia' regolata), e regola
     * di conseguenza Memoria, Backup <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    public void archivioMemoria() {
        /* variabili e costanti locali di lavoro */
        Object archivio;
        Object memoria;

        try {    // prova ad eseguire il codice

            archivio = this.getArchivio();
            memoria = this.getMemoriaDaArchivio(archivio);
            this.setMemoria(memoria);
            this.memoriaBackup();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il valore Memoria corrispondente a un dato valore Archivio.
     * <p/>
     *
     * @param archivio il valore Archivio
     *
     * @return il valore Memoria corrispondente
     */
    public Object getMemoriaDaArchivio(Object archivio) {
        /* variabili e costanti locali di lavoro */
        Object memoria = null;

        try {    // prova ad eseguire il codice

            if (archivio != null) {

                /* Se il valore Archivio e' vuoto,
                 * assegna a Memoria il proprio valore vuoto,
                 * altrimenti converte il valore */
                if (archivio.equals(this.getValoreArchivioVuoto())) {
                    memoria = this.getValoreMemoriaVuoto();
                } else {
                    memoria = this.memoriaDaArchivio(archivio);
                } /* fine del blocco if */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return memoria;
    }


    /**
     * Converte un valore da Archivio a Memoria.
     * <p/>
     * Chiamato solo se Archivio non � nullo e non e' vuoto.
     * Sovrascritto dalle sottoclassi per effettuare conversioni specifiche.
     * Nella classe base assume che le classi di Archivio e Memoria siano uguali.
     *
     * @param archivio il valore Archivio
     *
     * @return il valore Memoria corrispondente
     */
    protected Object memoriaDaArchivio(Object archivio) {
        /* variabili e costanti locali di lavoro */
        Object memoria = null;
        Class classeArchivio = null;
        Class classeMemoria = null;

        try {    // prova ad eseguire il codice
            classeArchivio = this.getTipoArchivio().getClasse();
            classeMemoria = this.getTipoMemoria().getClasse();
            if (classeArchivio == classeMemoria) {
                memoria = archivio;
            } else {
                /* non dovrebbe mai succedere */
                throw new Exception("Classe Archivio diversa da classe Memoria");
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return memoria;
    }


    /**
     * Allinea le variabili del Campo: da Memoria verso Archivio.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Archivio <br>
     * La variabile archivio e' allineata per la registrazione <br>
     */
    public void memoriaArchivio() {
        /* variabili e costanti locali di lavoro */
        Object archivio;
        Object memoria;

        try {    // prova ad eseguire il codice

            memoria = this.getMemoria();
            archivio = this.getArchivioDaMemoria(memoria);
            this.setArchivio(archivio);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il valore Archivio corrispondente a un dato valore Memoria.
     * <p/>
     *
     * @param memoria il valore Memoria
     *
     * @return il valore Archivio corrispondente
     */
    public Object getArchivioDaMemoria(Object memoria) {
        /* variabili e costanti locali di lavoro */
        Object archivio = null;

        try {    // prova ad eseguire il codice

            if (memoria != null) {

                /* Se il valore Memoria e' vuoto,
                 * assegna a Archivio il proprio valore vuoto,
                 * altrimenti converte il valore */
                if (memoria.equals(this.getValoreMemoriaVuoto())) {
                    archivio = this.getValoreArchivioVuoto();
                } else {
                    archivio = this.archivioDaMemoria(memoria);
                } /* fine del blocco if */
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return archivio;
    }


    /**
     * Converte un valore da Memoria a Archivio.
     * <p/>
     * Chiamato solo se Memoria non � nullo e non e' vuoto.
     * Sovrascritto dalle sottoclassi per effettuare conversioni specifiche.
     * Nella classe base assume che le classi di Archivio e Memoria siano uguali.
     *
     * @param memoria il valore Memoria
     *
     * @return il valore Archivio corrispondente
     */
    protected Object archivioDaMemoria(Object memoria) {
        /* variabili e costanti locali di lavoro */
        Object archivio = null;
        Class classeArchivio = null;
        Class classeMemoria = null;

        try {    // prova ad eseguire il codice
            classeArchivio = this.getTipoArchivio().getClasse();
            classeMemoria = this.getTipoMemoria().getClasse();
            if (classeArchivio == classeMemoria) {
                archivio = memoria;
            } else {
                /* non dovrebbe mai succedere */
                throw new Exception("Classe Archivio diversa da classe Memoria");
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return archivio;
    }


    /**
     * Allinea le variabili del Campo: da Memoria verso Video.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Video <br>
     */
    public void memoriaVideo() {
        /* variabili e costanti locali di lavoro */
        Class classeMemoria = null;
        Class classeVideo = null;

        try {    // prova ad eseguire il codice

            /* Se il valore Memoria e' vuoto,
             * assegna a Video il proprio valore vuoto,
             * altrimenti trasporta il valore */
            if (this.isValoreMemoriaVuoto()) {
                this.setVideo(this.getValoreVideoVuoto());
            } else {

                /* se i valori Memoria e Video sono della stessa classe,
                 * trasporta il valore da Memoria a Video, altrimenti
                 * questa operazione e' di responsabilita' della
                 * sottoclasse specifica che sa come effettuare la conversione */
                classeMemoria = this.getTipoMemoria().getClasse();
                classeVideo = this.getTipoVideo().getClasse();
                if (classeMemoria == classeVideo) {
                    this.setVideo(this.getMemoria());
                }// fine del blocco if

            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Ritorna il valore Video corrispondente a un dato valore Memoria.
     * <p/>
     *
     * @param memoria il valore Memoria
     *
     * @return il valore Video corrispondente
     */
    public Object getVideoDaMemoria(Object memoria) {
        /* variabili e costanti locali di lavoro */
        Object video = null;

        try {    // prova ad eseguire il codice

            if (memoria != null) {

                /* Se il valore Memoria e' vuoto,
                 * assegna a Video il proprio valore vuoto,
                 * altrimenti converte il valore */
                if (memoria.equals(this.getValoreMemoriaVuoto())) {
                    video = this.getValoreVideoVuoto();
                } else {
                    video = this.videoDaMemoria(memoria);
                } /* fine del blocco if */
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return video;
    }


    /**
     * Converte un valore da Memoria a Video.
     * <p/>
     * Chiamato solo se Memoria non � nullo e non e' vuoto.
     * Sovrascritto dalle sottoclassi per effettuare conversioni specifiche.
     * Nella classe base assume che le deu classi siano uguali.
     *
     * @param memoria il valore Memoria
     *
     * @return il valore Video corrispondente
     */
    protected Object videoDaMemoria(Object memoria) {
        /* variabili e costanti locali di lavoro */
        Object video = null;
        Class classeMemoria = null;
        Class classeVideo = null;

        try {    // prova ad eseguire il codice
            classeMemoria = this.getTipoMemoria().getClasse();
            classeVideo = this.getTipoVideo().getClasse();
            if (classeMemoria == classeVideo) {
                video = memoria;
            } else {
                /* non dovrebbe mai succedere */
                throw new Exception("Classe Memoria diversa da classe Video");
            }// fine del blocco if-else

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return video;
    }


    /**
     * Allinea le variabili del Campo: da Video verso Memoria.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * Parte dalla variabile Video (gia' regolata), e regola
     * di conseguenza Memoria <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     */
    public void videoMemoria() {
        try { // prova ad eseguire il codice
            /* Se il valore Video e' vuoto,
             *  assegna a Memoria il proprio valore vuoto,
             *  altrimenti trasporta il valore */
            if (this.isValoreVideoVuoto()) {
                this.setMemoria(this.getValoreMemoriaVuoto());
            } else {
                this.setMemoria(this.getVideo());
            } /* fine del blocco if */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * copia da memoria a backup
     */
    public void memoriaBackup() {
        /* recupera la memoria e registra il backup */
        try {    // prova ad eseguire il codice
            this.setBackup(this.getMemoria());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo memoriaBackup */


    /**
     * Ripristina il contenuto della memoria con il backup
     * <p/>
     * Trasporta il valore fino allla GUI
     */
    public void restoreBackup() {
        /** recupera la memoria e registra il backup */
        try {    // prova ad eseguire il codice
            this.setMemoria(this.getBackup());
            this.memoriaVideo();
            this.getCampoParente().getCampoLogica().memoriaGui();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo memoriaBackup */


    /**
     * Svuota il valore del campo.
     * <p/>
     * Assegna alla variabile Memoria il proprio valore vuoto <br>
     * Allinea la variabile Backup col nuovo valore <br>
     */
    public void reset() {
        /* variabili e costanti locali di lavoro */
        Object valoreVuoto = null;
        TipoMemoria tipoMemoria;

        try {    // prova ad eseguire il codice
            tipoMemoria = this.getTipoMemoria();
            if (tipoMemoria != null) {
                valoreVuoto = tipoMemoria.getValoreVuoto();
            }// fine del blocco if

            this.setMemoria(valoreVuoto);
            this.memoriaBackup();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Invocato quando il valore della variabile video viene modificato.
     * <p/>
     */
    public void videoModificato() {
        try {    // prova ad eseguire il codice

            this.videoMemoria();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Verifica se il valore Archivio e' vuoto
     */
    protected boolean isValoreArchivioVuoto() {
        boolean isVuoto = true;
        Object unValore = null;

        try {    // prova ad eseguire il codice
            unValore = this.getArchivio();
            if (unValore != null) {
                if (!unValore.equals(this.getValoreArchivioVuoto())) {
                    isVuoto = false;
                } /* fine del blocco if */
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isVuoto;
    } /* fine del metodo */


    /**
     * Verifica se il valore Memoria e' vuoto.
     * <p/>
     *
     * @return true se il valore memoria e' vuoto
     *         (per questo tipo di campo)
     */
    protected boolean isValoreMemoriaVuoto() {
        boolean isVuoto = true;
        Object unValore = null;

        try {    // prova ad eseguire il codice
            unValore = this.getMemoria();
            if (unValore != null) {
                if (!unValore.equals(this.getValoreMemoriaVuoto())) {
                    isVuoto = false;
                } /* fine del blocco if */
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isVuoto;
    } /* fine del metodo */


    /**
     * Verifica se il campo e' vuoto.
     * <p/>
     *
     * @return true se il campo e' vuoto
     */
    public boolean isVuoto() {
        return this.isValoreMemoriaVuoto();
    }


    /**
     * Verifica se il valore Video e' vuoto
     */
    protected boolean isValoreVideoVuoto() {
        boolean isVuoto = true;
        Object unValore = null;

        try {    // prova ad eseguire il codice
            unValore = this.getVideo();
            if (unValore != null) {
                if (!unValore.equals(this.getValoreVideoVuoto())) {
                    isVuoto = false;
                } /* fine del blocco if */
            } /* fine del blocco if */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return isVuoto;
    } /* fine del metodo */


    /**
     * Controllo di validit� del valore video.
     * <p/>
     * Controlla che il valore sia compatibile col tipo di dati del Campo <br>
     *
     * @param valoreVideo oggetto da controllare
     *
     * @return true se il valore � compatibile
     */
    public boolean isVideoValido(Object valoreVideo) {
        return false;
    }


    /**
     * Restituisce il numero di elementi validi.
     * <p/>
     * Sono esclusi i separatori, non specificato e nuovo <br>
     *
     * @return numero di elementi validi
     */
    public int getElementiValidi() {
        return 0;
    }


    /**
     * Controlla se esiste uno ed un solo elemento valido.
     * <p/>
     *
     * @return vero se esiste un elemento valido
     *         falso se ne esistono zero o più di uno
     */
    public boolean isUnicoValido() {
        return false;
    }


    /**
     * Controllo di validità del valore video.
     * <p/>
     * Controlla che il valore sia compatibile col tipo di dati del Campo <br>
     *
     * @param valoreVideo oggetto da controllare
     *
     * @return true se il valore è compatibile
     */
    public boolean isVuoto(Object valoreVideo) {
        return false;
    }


    /**
     * Trasforma un valore nella sua rappresentazione stringa.
     * <p/>
     * Opera secondo le regole del campo.
     * Sovrascritto dalle sottoclassi
     *
     * @param valore da trasformare
     *
     * @return il valore rappresentato come stringa
     */
    public String format(Object valore) {
        return "";// sovrascritto da CDFormat
    }


    /**
     * Determina se il campo e' modificato
     * confrontando il valore Memoria col valore Backup
     * nella classe del tipo dati Memoria specifico del campo
     *
     * @return true se il campo e' modificato
     */
    public boolean isModificato() {

        /** variabili e costanti locali di lavoro */
        boolean modificato = false;
        Campo unCampo;
        TipoMemoria unTipoMemoria = null;

        try {    // prova ad eseguire il codice

            unCampo = this.getCampoParente();

            unTipoMemoria = this.getTipoMemoria();

            // Fa eseguire il controllo al tipo dati memoria specifico del campo
            if (unTipoMemoria != null) {
                modificato = unTipoMemoria.isCampoModificato(unCampo);
            } /* fine del blocco if */

        } catch (Exception unErrore) {    // intercetta l'errore
            /** mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */

        return modificato;
    } /* fine del metodo */


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Usato per controllare la validita' della scheda <br>
     *
     * @return true se valido, false se non valido.
     */
    public boolean isValido() {
        /* variabili e costanti locali di lavoro */
        boolean valido = true;
        Validatore validatore = null;
        CampoVideo campoVideo = null;
        JComponent componente = null;


        try { // prova ad eseguire il codice
            validatore = this.getValidatore();
            if (validatore != null) {
                campoVideo = this.getCampoParente().getCampoVideo();
                componente = campoVideo.getComponente();
                valido = validatore.isValido(componente);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * regola il valore del campo in archivio
     */
    public void setArchivio(Object unValoreArchivio) {
        this.unValoreArchivio = unValoreArchivio;
    } /* fine del metodo setter */


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
    public Object convertiMemoria(Object valore) {
        return valore;
    }


    /**
     * regola il valore del campo in memoria.
     */
    public void setMemoria(Object valore) {
        /* variabili e costanti locali di lavoro */
        boolean statoPrima;
        boolean statoDopo;
        Object memoriaPrima;
        Object memoriaDopo;

        try { // prova ad eseguire il codice

            /*
             * determina lo stato di uguaglianza backup / memoria
             * prima della modifica
             */
            statoPrima = this.isModificato();

            /*
             * recupera il valore memoria
             * prima della modifica
             */
            memoriaPrima = this.unValoreMemoria;


            if (valore == null) {
                this.unValoreMemoria = this.getValoreMemoriaVuoto();
            } else {
                this.unValoreMemoria = valore;
            }// fine del blocco if-else

            /*
             * determina lo stato di uguaglianza backup / memoria
             * dopo la modifica
             */
            statoDopo = this.isModificato();

            /*
             * recupera il valore memoria
             * dopo la modifica
             */
            memoriaDopo = this.unValoreMemoria;

            /*
             * se e' cambiato il valore memoria
             * lancia l'evento
             */
            if ((memoriaDopo != null) && (memoriaPrima != null)) {
                if (!memoriaDopo.equals(memoriaPrima)) {
                    this.getCampoParente().fire(CampoBase.Evento.memoriaModificata);
                }// fine del blocco if
            }// fine del blocco if

            /*
             * se e' cambiato lo stato di uguaglianza backup / memoria
             * lancia l'evento
             */
            if (statoPrima != statoDopo) {
                this.getCampoParente().fire(CampoBase.Evento.statoModificato);
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo setter */


    /**
     * valore iniziale del campo in memoria
     */
    protected void setBackup(Object unValoreBackup) {
        this.unValoreBackup = unValoreBackup;
    } /* fine del metodo setter */


    /**
     * valore corrente del campo a video
     */
    public void setVideo(Object valore) {
        /* variabili e costanti locali di lavoro */
        Object valorePrima, valoreDopo;

        try { // prova ad eseguire il codice

            /* recupera il valore prima della modifica */
            valorePrima = this.unValoreVideo;

            /* assegna il nuovo valore se non nullo */
            if (valore != null) {
                this.unValoreVideo = valore;
            } else {
                this.unValoreVideo = this.getValoreMemoriaVuoto();
            }// fine del blocco if-else

            /* recupera il valore dopo modifica */
            valoreDopo = this.unValoreVideo;

            /* se il valore e' cambiato lancia l'evento */
            if ((valorePrima != null) && (valoreDopo != null)) {
                if (!valoreDopo.equals(valorePrima)) {
                    this.getCampoParente().fire(CampoBase.Evento.videoModificato);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        this.unValoreVideo = valore;
    } /* fine del metodo setter */


    /**
     * Regola il tipo dati Archivio.
     * <p/>
     *
     * @param unTipoArchivio il tipo dati archivio da assegnare
     */
    public void setTipoArchivio(TipoArchivio unTipoArchivio) {
        this.unTipoArchivio = unTipoArchivio;
    } /* fine del metodo setter */


    /**
     * tipo dati Memoria
     */
    public void setTipoMemoria(TipoMemoria tipoMemoria) {
        this.tipoMemoria = tipoMemoria;
    } /* fine del metodo setter */


    /**
     * tipo dati Video
     */
    public void setTipoVideo(TipoVideo tipoVideo) {
        this.tipoVideo = tipoVideo;
    } /* fine del metodo setter */


    private int getAllineamentoLista() {
        return allineamentoLista;
    }


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
    public void setAllineamentoLista(int allineamento) {
        try { // prova ad eseguire il codice
            this.allineamentoLista = allineamento;
            this.regolaAllineamentoLista();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola l'allineamento per la lista.
     * <p/>
     * Legge il valore della variabile
     * Se la variabile è >=0 zero ed esiste un renderer,
     * assegna il valore di allineamento al renderer
     */
    private void regolaAllineamentoLista() {
        /* variabili e costanti locali di lavoro */
        int allineamento;
        RendererBase renderer;

        try {    // prova ad eseguire il codice
            allineamento = this.getAllineamentoLista();
            if (allineamento >= 0) {
                renderer = this.getRenderer();
                if (renderer != null) {
                    renderer.setHorizontalAlignment(allineamento);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    public RendererBase getRenderer() {
        return renderer;
    }


    /**
     * Assegna un renderer per la visualizzazione nelle liste
     * <p/>
     *
     * @param renderer da assegnare
     */
    public void setRenderer(RendererBase renderer) {
        this.renderer = renderer;
    }


    /**
     * Recupera l'editor per il campo nella cella della lista.
     * <p/>
     *
     * @return l'editor del campo
     */
    public TableCellEditor getEditor() {
        return this.editor;
    }


    /**
     * Assegna l'editor per il campo nella cella della lista.
     *
     * @param editor del campo
     *
     * @return editor
     */
    public TableCellEditor setEditor(TableCellEditor editor) {
        this.editor = editor;
        return getEditor();
    }


    /**
     * Editor per la modifica del campo nella cella della lista.
     * </p>
     */
    private class FieldCellEditor extends AbstractCellEditor implements TableCellEditor {

        /**
         * Ritorna il valore di memoria correntemente presente nel componente editabile
         * <p/>
         *
         * @return il valore nel componente editabile
         */
        public Object getCellEditorValue() {
            /* variabili e costanti locali di lavoro */
            Object valore = null;

            try { // prova ad eseguire il codice

                valore = getValoreEditor();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return valore;
        }


        /**
         * Ritorna il componente da usare per l'editing della cella.
         * <p/>
         * Invocato ogni volta che comincia l'editing di una cella.
         * E' il componente video del campo clonato della vista.
         *
         * @return il componente da utilizzare per l'editing.
         */
        public Component getTableCellEditorComponent(JTable table,
                                                     Object value,
                                                     boolean isSelected,
                                                     int row,
                                                     int column) {
            /* variabili e costanti locali di lavoro */
            Component comp = null;
            Tavola tavola;
            int codice;
            Campo campo;
            Object valore;

            try { // prova ad eseguire il codice

                /* recupera il valore corrente dal database e lo
                 * mette nel campo */
                if (table instanceof Tavola) {
                    tavola = (Tavola)table;
                    codice = tavola.getModello().getChiave(row);
                    campo = getCampoParente();
                    Modulo modulo = campo.getModulo();
                    valore = modulo.query().valoreCampo(campo, codice);
                    campo.setValore(valore);
                    campo.avvia();
                }// fine del blocco if

                /* invoca il metodo delegato */
                comp = getComponenteEditor();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return comp;
        }


    } // fine della classe 'interna'


    /**
     * Ritorna il componente da usare per l'editing della cella.
     * <p/>
     * Di default è il pannello componenti del campo.
     *
     * @return il componente da utilizzare per l'editing.
     */
    protected Component getComponenteEditor() {
        return this.getCampoParente().getPannelloComponenti();
    }


    /**
     * Ritorna il valore a livello di memoria
     * per il campo editato nella cella.
     * <p/>
     *
     * @return il valore editato a livello memoria
     */
    protected Object getValoreEditor() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        Campo campoParente;

        try {    // prova ad eseguire il codice
            /* fa discendere il valore dalla GUI alla memoria e lo recupera */
            campoParente = getCampoParente();
            campoParente.getCampoLogica().guiMemoria();
            valore = campoParente.getValore();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    }


    /**
     * Regola l'inizializzatore del campo.
     * <p/>
     *
     * @param init l'inizializzatore da assegnare
     */
    public void setInit(Init init) {
        this.inizializzatore = init;
    } /* fine del metodo setter */


    /**
     * Regola l'inizializzatore di default del campo.
     * <p/>
     * Usato in assenza di un inizializzatore specifico.
     *
     * @return l'inizializzatore di default
     */
    public Init getInitDefault() {
        return inizializzatoreDefault;
    }


    /**
     * Regola l'inizializzatore di default del campo.
     * <p/>
     * Usato in assenza di un inizializzatore specifico.
     *
     * @param inizializzatoreDefault da assegnare
     */
    public void setInitDefault(Init inizializzatoreDefault) {
        this.inizializzatoreDefault = inizializzatoreDefault;
    }


    /**
     * elenco dei valori alla creazione (di solito stringa)
     */
    public void setValoriInterni(String unaStringaValori) {
    }


    /**
     * lista dei valori alla creazione
     */
    public void setValoriInterni(ArrayList unaListaValori) {
    } /* fine del metodo setter */


    /**
     * lista dei valori alla creazione
     */
    public void setValoriInterni(Campo.ElementiCombo[] valori) {
    } /* fine del metodo setter */


    /**
     * array dei valori alla creazione
     */
    public void setValoriInterni(String[] unaListaValori) {
    } /* fine del metodo setter */


    /**
     * array dei valori interni
     */
    public ArrayList getValoriInterni() {
        return null;
    }


    /**
     * Assegna la lista dei valori interni.
     * <p/>
     *
     * @param listaValori la lista dei valori interni
     */
    public void setValoriLegenda(ArrayList<String> listaValori) {
    }


    /**
     * Assegna i valori della legenda.
     * <p/>
     *
     * @param valori stringa di valori della legenda, separati da virgola
     */
    public void setValoriLegenda(String valori) {
    }

//    /**
//     * flag - se si vuole aggiungere l'elemento "non specificato"
//     * alla lista valori
//     *
//     * @param isUsaNonSpecificato
//     */
//    public void setUsaNonSpecificato(boolean isUsaNonSpecificato) {
//    } /* fine del metodo setter */


    /**
     * flag - implementato nella sottoclasse specifica
     */
    public void setNonSpecificatoIniziale(boolean isNonSpecificatoIniziale) {
    } /* fine del metodo setter */


    /**
     * flag - implementato nella sottoclasse specifica
     */
    public void setNascondeNonSpecificato(boolean isNascondeNonSpecificato) {
    } /* fine del metodo getter */

//    /**
//     * flag - se si vuole aggiungere l'elemento "nuovo"
//     * alla lista valori
//     */
//    public void setUsaNuovo(boolean isUsaNuovo) {
//    } /* fine del metodo setter */


    /**
     * Determina se il comando "Nuovo record" viene posizionato prima o dopo
     * la lista dei valori.
     * <p/>
     *
     * @param flag true per posizionare prima, false per dopo
     */
    public void setNuovoIniziale(boolean flag) {
    } /* fine del metodo setter */


    /**
     * Se si vuole aggiungere un separatore tra gli
     * elementi speciali ed i valori normali della lista valori
     * <p>
     * @param flag di uso del separatore
     */
    public void setUsaSeparatore(boolean flag) {
    } /* fine del metodo setter */


    /**
     * valore del campo in archivio
     */
    public Object getArchivio() {
        return this.unValoreArchivio;
    } /* fine del metodo getter */


    /**
     * valore del campo in memoria
     */
    public Object getMemoria() {
        return this.unValoreMemoria;
    } /* fine del metodo getter */


    /**
     * valore iniziale del campo in memoria
     */
    public Object getBackup() {
        return this.unValoreBackup;
    } /* fine del metodo getter */


    /**
     * valore corrente del campo a video
     */
    public Object getVideo() {
        return this.unValoreVideo;
    } /* fine del metodo getter */


    /**
     * tipo dati Archivio
     */
    public TipoArchivio getTipoArchivio() {
        return this.unTipoArchivio;
    } /* fine del metodo getter */


    /**
     * Ritorna il tipo dati Memoria
     */
    public TipoMemoria getTipoMemoria() {
        return this.tipoMemoria;
    } /* fine del metodo getter */


    /**
     * Ritorna il tipo dati Video
     */
    public TipoVideo getTipoVideo() {
        return this.tipoVideo;
    } /* fine del metodo getter */


    /**
     * Ritorna il valore vuoto per l'oggetto Archivio di questo tipo di campo.
     * <p/>
     *
     * @return il valore vuoto per l'oggetto Archivio
     */
    public Object getValoreArchivioVuoto() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        TipoArchivio tipo = null;

        try { // prova ad eseguire il codice
            tipo = this.getTipoArchivio();
            if (tipo != null) {
                valore = tipo.getValoreVuoto();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;
    } /* fine del metodo getter */


    /**
     * Ritorna il valore vuoto per l'oggetto Memoria di questo tipo di campo.
     * <p/>
     *
     * @return il valore vuoto per l'oggetto Memoria
     */
    public Object getValoreMemoriaVuoto() {
        /* variabili e costanti locali di lavoro */
        Object valore = null;
        TipoMemoria tipo = null;

        try { // prova ad eseguire il codice
            tipo = this.getTipoMemoria();
            if (tipo != null) {
                valore = tipo.getValoreVuoto();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;

    } /* fine del metodo getter */


    /**
     * Ritorna il valore vuoto per l'oggetto Video di questo tipo di campo.
     * <p/>
     *
     * @return il valore vuoto per l'oggetto Video
     */
    public Object getValoreVideoVuoto() {
        Object valore = null;
        TipoVideo tipo = null;

        try { // prova ad eseguire il codice
            tipo = this.getTipoVideo();
            if (tipo != null) {
                valore = tipo.getValoreVuoto();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valore;

    } /* fine del metodo getter */


    /**
     * ritorna il valore a livello Memoria per l'inizializzazione
     * del campo per un nuovo record.
     * <p/>
     *
     * @param conn la connessione da utilizzare
     *
     * @return il valore Memoria di inizializzazione per nuovo record
     */
    public Object getValoreNuovoRecord(Connessione conn) {

        /** variabili e costanti locali di lavoro */
        Object unValore = null;
        Init init = null;

        try { // prova ad eseguire il codice

            /*
             * Recupera l'inizializzatore da utilizzare.
             * Se c'e' un inizializzatore specifico, lo usa
             * Altrimenti usa quello di default
             */
            init = this.getInit();
            if (init == null) {
                init = this.getInitDefault();
            }// fine del blocco if

            /*
             * Recupera il valore dall'inizializzatore
             */
            if (init != null) {
                unValore = init.getValore(conn);
            } else {
                throw new Exception("Inizializzatore non presente.\n" +
                        "Campo: " +
                        this.getCampoParente().getChiaveCampo());
            } /* fine del blocco if/else */
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unValore;
    } /* fine del metodo getter */


    /**
     * Ritorna un oggetto che rappresenta il valore del campo
     * per l'utilizzo in un filtro.
     * <p/>
     * Si usa per costruire un filtro rappresentante il valore
     * di memoria corrente del campo.<br>
     *
     * @return il valore per l'utilizzo nel filtro
     */
    public Object getValoreFiltro() {
        /* variabili e costanti locali di lavoro */
        Object valoreFiltro = null;

        try {    // prova ad eseguire il codice
            valoreFiltro = this.getMemoria();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreFiltro;
    }


    /**
     * Ritorna l'inizializzatore del campo.
     * <p/>
     *
     * @return l'inizializzatore del campo
     */
    public Init getInit() {
        return this.inizializzatore;
    } /* fine del metodo getter */


    protected FiltroUscita getFiltroVideo() {
        return filtroVideo;
    }


    protected void setFiltroVideo(FiltroUscita filtroVideo) {
        this.filtroVideo = filtroVideo;
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Elenco getElenco() {
        return null;
    } /* fine del metodo getter */


    /**
     * array dei valori (oggetti di tipo Object)
     */
    public ArrayList getListaValori() {
        return null;
    } /* fine del metodo getter */


    /**
     * flag - implementato nella sottoclasse specifica
     */
    public boolean isNascondeNonSpecificato() {
        return false;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Elemento getElementoNuovo() {
        return null;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Elemento getElementoNonSpecificato() {
        return null;
    } /* fine del metodo getter */


    /**
     * Ritorna true se il campo e' booleano.
     *
     * @return true se booleano
     */
    public boolean isBooleano() {
        return false;
    }


    /**
     * Ritorna true se il campo e' di testo.
     *
     * @return true se è campo testo
     */
    public boolean isTesto() {
        return false;
    }


    /**
     * Ritorna true se il campo e' di tipo testoArea.
     *
     * @return true se è campo testoArea
     */
    public boolean isTestoArea() {
        return false;
    }


    /**
     * Ritorna true se il campo e' numerico.
     *
     * @return true se è campo numerico
     */
    public boolean isNumero() {
        return false;
    }


    /**
     * Ritorna true se il campo e' data.
     *
     * @return true se è campo data
     */
    public boolean isData() {
        return false;
    }


    /**
     * Ritorna true se il campo e' Timestamp.
     *
     * @return true se è campo Timestamp
     */
    public boolean isTimestamp() {
        return false;
    }

    /**
     * Ritorna true se il campo e' orea.
     *
     * @return true se è campo Time
     */
    public boolean isOra() {
        return false;
    }

    public boolean isElencoInterno() {
        return false;
    }


    /**
     * Recupera il valore di elenco per una data posizione nella lista valori.
     * <p/>
     *
     * @param posizione richiesta
     *
     * @return il valore corrispondente
     */
    public Object getValoreElenco(int posizione) {
        return null;
    } /* fine del metodo getter */


    /**
     * Recupera il valore di elenco correntemente selezionato.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @return il valore corrispondente
     */
    public Object getValoreElenco() {
        return null;
    } /* fine del metodo getter */


    /**
     * Assegna il valore memoria del campo in base a un valore di elenco.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param valore da cercare nell'elenco
     */
    public void setValoreDaElenco(Object valore) {
    } /* fine del metodo getter */


    /**
     * Recupera il valore corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public Object getValore() {
        return this.getMemoria();
    }


    /**
     * Recupera il valore booleano corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public boolean getBool() {
        return Libreria.getBool(this.getValore());
    }


    /**
     * Recupera il valore stringa corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public String getString() {
        return Lib.Testo.getStringa(this.getValore());
    }


    /**
     * Recupera il valore intero corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public int getInt() {
        return Libreria.getInt(this.getValore());
    }


    /**
     * Recupera il valore doppio corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public double getDouble() {
        return Libreria.getDouble(this.getValore());
    }


    /**
     * Recupera il valore data corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public Date getData() {
        return Libreria.getDate(this.getValore());
    }


    public void setElencoInterno(boolean elencoInterno) {
    }


    public boolean isUsaRendererElenco() {
        return false;
    }


    public void setUsaRendererElenco(boolean usaRendererValoreInterno) {
    }


    /**
     * Restituisce il numero di decimali per il campo
     * <p/>
     *
     * @return il numero massimo di decimali per il campo
     */
    public int getNumDecimali() {
        /* valore di ritorno */
        return 0;
    }


    /**
     * Regola il numero di decimali per il campo
     * <p/>
     * Sovrascritto dalle sottoclassi (CDNumero)
     *
     * @param numDecimali numero di massimo di cifre decimali inseribili
     * e numero fisso di cifre decimali visualizzate
     */
    public void setNumDecimali(int numDecimali) {
    }

    /**
     * Uso del separatore delle migliaia nel rendering
     * <p/>
     * @param flag true per usare il separatore delle migliaia
     */
    public void setUsaSeparatoreMigliaia(boolean flag){
    }

    /**
     * Ritorna il codice del tipo dati Db gestito dal campo.
     * <p/>
     *
     * @return il codice chiave nella collezione dei tipi dati Db.
     */
    public int getChiaveTipoDatiDb() {
        return this.getTipoArchivio().getChiaveTipoDatiDb();
    }


    /**
     * flag di controllo per i campi da utilizzare nella ricerca standard.
     *
     * @return true se ricercabile
     */
    public boolean isRicercabile() {
        return isRicercabile;
    }


    /**
     * flag di controllo per i campi da utilizzare nella ricerca standard.
     *
     * @param ricercabile flag booleano
     */
    public void setRicercabile(boolean ricercabile) {
        isRicercabile = ricercabile;
    }


    /**
     * flag di controllo per usare campi di ricerca doppi (range da...a...).
     *
     * @return true se ricercabile con range
     */
    public boolean isUsaRangeRicerca() {
        return usaRangeRicerca;
    }


    /**
     * flag di controllo per usare campi di ricerca doppi (range da...a...).
     *
     * @param usaRangeRicerca flag booleano
     */
    public void setUsaRangeRicerca(boolean usaRangeRicerca) {
        this.usaRangeRicerca = usaRangeRicerca;
    }


    /**
     * Ritorna true se usa solo la porzione data nella ricerca
     * di campi Timestamp
     * <p/>
     *
     * @return true se usa solo la porzione Data nella ricerca
     */
    public boolean isRicercaSoloPorzioneData() {
        return ricercaSoloPorzioneData;
    }


    /**
     * flag di controllo per usare solo la porzione data nella ricerca
     * di campi Timestamp
     * <p/>
     *
     * @param flag flag per usare solo la porzione Data
     */
    public void setRicercaSoloPorzioneData(boolean flag) {
        this.ricercaSoloPorzioneData = flag;
    }


    /**
     * Ritorna l'elenco degli operatori filtro disponibili per il campo
     *
     * @return l'elenco delle chiavi degli operatori filtro disponibili
     */
    public ArrayList<String> getOperatoriRicerca() {
        return operatoriRicerca;
    }


    private void setOperatoriRicerca(ArrayList<String> operatoriRicerca) {
        this.operatoriRicerca = operatoriRicerca;
    }


    /**
     * aggiunge un operatore di ricerca al campo.
     *
     * @param operatore la chiave dell'operatore
     *
     * @see it.algos.base.query.filtro.Filtro.Op
     */
    public void addOperatoreRicerca(String operatore) {
        this.getOperatoriRicerca().add(operatore);
    }


    /**
     * svuota la lista degli operatori di ricerca del campo.
     */
    public void clearOperatoriRicerca() {
        this.getOperatoriRicerca().clear();
    }


    /**
     * Ritorna l'operatore di ricerca di default per questo campo.
     * <p/>
     *
     * @return l'operatore di ricerca
     *
     * @see it.algos.base.database.util.Operatore
     */
    public String getOperatoreRicercaDefault() {
        return operatoreRicercaDefault;
    }


    /**
     * Imposta l'operatore di ricerca di default
     * <p/>
     *
     * @param operatore di ricerca di default
     */
    public void setOperatoreRicercaDefault(String operatore) {
        this.operatoreRicercaDefault = operatore;
    }

    /**
     * Assegna il formattatore del campo
     * <p>
     * @param format il formattatore
     */
    public void setFormat(Format format){}

    /**
     * Assegna il formato al campo data
     * <p>
     * @param pattern come da SimpleDateFormat
     */
    public void setDateFormat(String pattern){}

    /**
     * Ritorna il formattatore per l'editing.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @return il formattatore per l'editing
     */
    public JFormattedTextField.AbstractFormatter getEditFormatter() {
        return null;
    }


    /**
     * Ritorna il formattatore per il display.
     * <p/>
     * Sovrascritto dalle sottoclassi.
     *
     * @return il formattatore per il display
     */
    public JFormattedTextField.AbstractFormatter getDisplayFormatter() {
        return null;
    }


    /**
     * Ritorna il formattatore del campo
     * <p/>
     * Sovrascritto dalle sottoclassi (CDFormat)
     *
     * @return l'oggetto formattatore del campo
     */
    public Format getFormat() {
        return null;
    }


    /**
     * Recupera il validatore del campo.
     *
     * @return il validatore associato al campo
     */
    public Validatore getValidatore() {
        return validatore;
    }


    /**
     * Assegna un validatore al campo.
     *
     * @param validatore da assegnare
     */
    public void setValidatore(Validatore validatore) {
        this.validatore = validatore;
    }


    /**
     * Ritorna un'icona che rappresenta il tipo di campo.
     * <p/>
     *
     * @return l'icona del campo
     */
    public Icon getIcona() {
        return icona;
    }


    protected void setIcona(Icon icona) {
        this.icona = icona;
    }


    /**
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     * Per fare una copia completa di questo oggetto occorre:
     * Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     * sovrascritto che copia e regola tutte le variabili dell'oggetto con
     * gli stessi valori delle variabili originarie
     * Secondo copiare tutte le variabili che sono puntatori ad altri
     * oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     * originale (in genere tutti gli oggetti che vengono creati nella
     * classe col comando new)
     * Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     * esistenti nelle sottoclassi stesse
     *
     * @param unCampoParente CampoBase che cantiene questo CampoLogica
     */
    public CampoDati clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CampoDati unCampo;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (CampoDati)super.clone();

        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
            throw new InternalError();
        }// fine del blocco try-catch

        try { // prova ad eseguire il codice
            /* modifica il riferimento al campo parente */
            unCampo.setCampoParente(unCampoParente);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }

}// fine della classe

