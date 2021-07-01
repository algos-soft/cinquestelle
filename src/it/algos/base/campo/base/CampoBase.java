/**
 * Title:        CampoBase.java
 * Package:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 15.20
 */

package it.algos.base.campo.base;

import it.algos.base.campo.dati.CDTesto;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.db.CDBDefault;
import it.algos.base.campo.db.CampoDB;
import it.algos.base.campo.inizializzatore.Init;
import it.algos.base.campo.inizializzatore.InitFactory;
import it.algos.base.campo.lista.CampoLista;
import it.algos.base.campo.lista.CampoListaDefault;
import it.algos.base.campo.logica.CLDefault;
import it.algos.base.campo.logica.CampoLogica;
import it.algos.base.campo.scheda.CSDefault;
import it.algos.base.campo.scheda.CampoScheda;
import it.algos.base.campo.video.CVBase;
import it.algos.base.campo.video.CVDefault;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.campo.video.decorator.CVDEtichetta;
import it.algos.base.campo.video.decorator.CVDecoratore;
import it.algos.base.campo.video.decorator.VideoFactory;
import it.algos.base.database.Db;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;
import it.algos.base.evento.BaseListener;
import it.algos.base.evento.Eventi;
import it.algos.base.evento.campo.BottoneAz;
import it.algos.base.evento.campo.BottoneEve;
import it.algos.base.evento.campo.BottoneLis;
import it.algos.base.evento.campo.CampoBaseAz;
import it.algos.base.evento.campo.CampoBaseEve;
import it.algos.base.evento.campo.CampoBaseLis;
import it.algos.base.evento.campo.CampoDoppioClickAz;
import it.algos.base.evento.campo.CampoDoppioClickEve;
import it.algos.base.evento.campo.CampoDoppioClickLis;
import it.algos.base.evento.campo.CampoGUIAz;
import it.algos.base.evento.campo.CampoGUIEve;
import it.algos.base.evento.campo.CampoGUILis;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.evento.campo.CampoMemoriaLis;
import it.algos.base.evento.campo.CampoModificatoAz;
import it.algos.base.evento.campo.CampoModificatoEve;
import it.algos.base.evento.campo.CampoModificatoLis;
import it.algos.base.evento.campo.CampoPerdeFuocoAz;
import it.algos.base.evento.campo.CampoPerdeFuocoEve;
import it.algos.base.evento.campo.CampoPerdeFuocoLis;
import it.algos.base.evento.campo.CampoPrendeFuocoAz;
import it.algos.base.evento.campo.CampoPrendeFuocoEve;
import it.algos.base.evento.campo.CampoPrendeFuocoLis;
import it.algos.base.evento.campo.CampoPresentatoRecordAz;
import it.algos.base.evento.campo.CampoPresentatoRecordEve;
import it.algos.base.evento.campo.CampoPresentatoRecordLis;
import it.algos.base.evento.campo.CampoStatoAz;
import it.algos.base.evento.campo.CampoStatoEve;
import it.algos.base.evento.campo.CampoStatoLis;
import it.algos.base.evento.campo.CampoVideoAz;
import it.algos.base.evento.campo.CampoVideoEve;
import it.algos.base.evento.campo.CampoVideoLis;
import it.algos.base.form.Form;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.ordine.Ordine;
import it.algos.base.scheda.Scheda;
import it.algos.base.tavola.renderer.RendererBase;
import it.algos.base.validatore.Validatore;
import it.algos.base.validatore.ValidatoreFactory;
import it.algos.base.wrapper.Campi;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.Viste;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;
import java.awt.Color;
import java.awt.Font;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;

/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Agisce da 'raccoglitore' per gli oggetti che implementano le singole
 * funzionalita' di un campo <br>
 * B - Applicando il design pattern di tipo Facade e Strategy, le funzionalita'
 * dei campi vengono suddivise ed assegnate a singoli oggetti: ognuno
 * delegato per gestire le funzionalita' della sua area di competenza <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 15.20
 */
public class CampoBase implements Cloneable, Campo {

    /**
     * Abstract Data Types per le informazioni di un modulo
     */
    private Modulo modulo = null;

    /**
     * Form a cui appartiene il campo
     */
    private Form form = null;

    /**
     * riferimento all'oggetto delegato per le funzionalita' necessarie a
     * gestire tutto quanto non previsto negli altri oggetti delegati
     */
    private CampoLogica unCampoLogica = null;

    /**
     * riferimento all'oggetto delegato per le funzionalita' necessarie a
     * gestire il flusso delle informazioni con il database
     */
    private CampoDB unCampoDB = null;

    /**
     * riferimento all'oggetto delegato per le funzionalita' necessarie a
     * gestire il flusso interno delle informazioni del campo
     */
    private CampoDati unCampoDati = null;

    /**
     * riferimento all'oggetto delegato per gli attributi
     * di visualizzazione del campo nella lista
     */
    private CampoLista unCampoLista = null;

    /**
     * riferimento all'oggetto delegato per gli attributi
     * di visualizzazione del campo nella scheda
     */
    private CampoScheda unCampoScheda = null;

    /**
     * riferimento all'oggetto delegato per le funzionalita' necessarie a
     * gestire gli oggetti a video di questo campo
     */
    private CampoVideo unCampoVideo = null;

    /**
     * nome chiave interno del campo (per le collezioni HashMap del programma)
     */
    private String unNomeInternoCampo = "";

    /**
     * flag per indicare se il campo e' stato inizializzato
     */
    private boolean inizializzato = false;

    /**
     * A list of event listeners for this component.
     */
    private EventListenerList listaListener;

    /**
     * flag di abilitazione del campo
     */
    private boolean abilitato = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CampoBase() {
        /* rimanda al costruttore di questa classe */
        this("");
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unNomeInternoCampo nome chiave per recuperare il campo
     */
    public CampoBase(String unNomeInternoCampo) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setNomeInternoCampo(unNomeInternoCampo.toLowerCase());

        /* regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* crea un oggetto interno per le funzionalita' generali */
        this.setCampoLogica(new CLDefault(this));

        /* crea un oggetto interno per le funzionalita' di database */
        this.setCampoDB(new CDBDefault(this));

        /* crea un oggetto interno per le funzionalita' di gestione dati
         * di default usa il tipo testo-testo che e' il piu' diffuso
         * (non posso creare una classe CampoDatiDefault perche' il
         * metodo isModificato() non ha senso tra due Object generici) */
        this.setCampoDati(new CDTesto(this));

        /* crea un oggetto interno per gli attributi di visualizzazione in Lista */
        this.setCampoLista(new CampoListaDefault(this));

        /* crea un oggetto interno per gli attributi di visualizzazione in Scheda */
        this.setCampoScheda(new CSDefault(this));

        /* crea un oggetto interno per le funzionalita'di visualizzazione in Scheda */
        this.setCampoVideo(new CVDefault(this));

        /* lista dei propri eventi */
        this.setListaListener(new EventListenerList());

        /* di default il campo è abilitato */
        this.setAbilitato(true);

        /* di default il campo non è totalizzabile nelle liste */
        this.setTotalizzabile(false);

    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     */
    public boolean inizializza() {
        try { // prova ad eseguire il codice

            if (!this.isInizializzato()) {

                /*
                 * todo gac/21-4-06
                 * ma.... ho provato
                 * penso che ricreare non sia un dramma */
                this.setListaListener(new EventListenerList());

                /* inizializza gli oggetti associati */
                this.getCampoDB().inizializza();
                this.getCampoDati().inizializza();
                this.getCampoLista().inizializza();
                this.getCampoScheda().inizializza();
                this.getCampoVideo().inizializza();
                this.getCampoLogica().inizializza();

                /*
                * todo alex 20-04-06
                * quando un campo viene clonato, viene automaticamente
                * reinizializzato.
                * Ad ogni inizializzazione viene aggiunto un
                * listener AzioneGUICampo.
                * Dopo un po' c'erano diversi listener AzioneGUICampo.
                * Per adesso ho ricreato la lista dei listener
                * ad ogni inizializzazione... non sono riuscito a svuotarla.
                * hai un'idea migliore?
                */

                /* aggiunge i listeners per ascoltare la propria GUI */
                this.addListener(new AzioneGUICampo());
                /* video del campo modificato */
                this.addListener(new AzioneVideoCampo());
                /* memoria del campo modificata */
                this.addListener(new AzioneMemoriaCampo());

                /* contrassegna il campo come inizializzato */
                this.setInizializzato(true);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito'
     * Metodo chiamato da altre classi
     * Viene eseguito tutte le volte che necessita
     * Avvia tutte le componenti del campo
     */
    public void avvia() {
        try { // prova ad eseguire il codice
            /* se non ancora inizializzato lo inizializza ora */
            if (!this.isInizializzato()) {
                this.inizializza();
            }// fine del blocco if

            /* avvio oggetto Logica */
            this.getCampoLogica().avvia();

            /* avvio oggetto DB */
            this.getCampoDB().avvia();

            /* avvio oggetto Dati */
            this.getCampoDati().avvia();

            /* avvio oggetto Lista */
            this.getCampoLista().avvia();

            /* avvio oggetto Scheda */
            this.getCampoScheda().avvia();

            /* avvio oggetto Video */
            this.getCampoVideo().avvia();

            /* trasporta il valore da memoria fino a GUI */
            this.getCampoLogica().memoriaGui();

            this.fire(CampoBase.Evento.generico);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Assegna il valore iniziale al campo.
     * <p/>
     */
    public void initValoreCampo() {
        this.getCampoDati().initValoreCampo();
    }


    /**
     * Svuota il valore del campo.
     * <p/>
     * Assegna alla variabile Memoria il proprio valore vuoto <br>
     * Allinea la variabile Backup col nuovo valore <br>
     * Fa risalire i valori fino alla GUI <br>.
     */
    public void reset() {
        this.getCampoLogica().reset();
    }


    /**
     * campoModificato, da CampoListener.
     * </p>
     * Esegue il comando <br>
     *
     * @param unEvento evento che causa il comando da eseguire <br>
     */
    public void campoModificato(CampoBaseEve unEvento) {
        /* metodo delegato  */
        this.getCampoLogica().campoModificato(unEvento);
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    private void eventoMemoriaModificata() {
        /* metodo delegato  */
        this.getCampoVideo().eventoMemoriaModificata();
    }


    /**
     * Metodo eseguito quando il valore video di un campo cambia.
     * <p/>
     * Invoca i metodi delegati <br>
     */
    private void eventoVideoModificato() {
        /* metodo delegato  */
        this.getCampoVideo().eventoVideoModificato();
    }


    /**
     * Inserimento di un valore.
     * <p/>
     * Il valore viene inserito in memoria <br>
     * La memoria viene trasferita a video e GUI <br>
     */
    public void setValore(Object valore) {
        try { // prova ad eseguire il codice

            /* metodo delegato  */
            this.getCampoDati().setMemoria(valore);
            this.getCampoLogica().memoriaGui();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserimento di un valore con registrazione del backup.
     * <p/>
     * Il valore viene inserito in memoria <br>
     * La memoria viene copiata nel backup <br>
     * La memoria viene trasferita a video e GUI <br>
     */
    public void setValoreIniziale(Object valore) {
        try { // prova ad eseguire il codice

            /* metodo delegato  */
            this.getCampoDati().setMemoria(valore);
            this.getCampoDati().memoriaBackup();
            this.getCampoLogica().memoriaGui();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Assegna un valore pilota al campo.
     * <p/>
     * Significativo solo se il campo gestisce un Navigatore.
     *
     * @param valore pilota da assegnare al Navigatore gestito dal campo
     */
    public void setValorePilota(int valore) {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        try { // prova ad eseguire il codice

            nav = this.getNavigatore();
            if (nav != null) {
                nav.setValorePilota(valore);
                nav.sincronizza();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il formattatore del campo
     * <p/>
     * Sovrascritto dalle sottoclassi (CDFormat)
     *
     * @return l'oggetto formattatore del campo
     */
    public Format getFormat() {
        return this.getCampoDati().getFormat();
    }


    /**
     * Trasforma un valore nella sua rappresentazione stringa.
     * <p/>
     * Opera secondo le regole del campo.
     *
     * @param valore da trasformare
     *
     * @return il valore rappresentato come stringa
     */
    public String format(Object valore) {
        return this.getCampoDati().format(valore);
    }


    /**
     * Regola il numero di decimali per il campo
     * <p/>
     *
     * @param numDecimali numero di massimo di cifre decimali inseribili
     * e numero fisso di cifre decimali visualizzate
     */
    public void setNumDecimali(int numDecimali) {
        this.getCampoDati().setNumDecimali(numDecimali);
    }


    /**
     * Abstract Data Types per le informazioni di un modulo
     */
    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
     * gestire tutto quanto non previsto negli altri oggetti delegati */
    public void setCampoLogica(CampoLogica unCampoLogica) {
        this.unCampoLogica = unCampoLogica;
    } /* fine del metodo setter */


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
    * gestire il flusso delle informazioni con il database */
    public void setCampoDB(CampoDB unCampoDB) {
        this.unCampoDB = unCampoDB;
    } /* fine del metodo setter */


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
    * gestire il flusso interno delle informazioni del campo */
    public void setCampoDati(CampoDati unCampoDati) {
        this.unCampoDati = unCampoDati;
    } /* fine del metodo setter */


    /* riferimento all'oggetto delegato per gli attributi
    * di visualizzazione del campo nella lista */
    public void setCampoLista(CampoLista unCampoLista) {
        this.unCampoLista = unCampoLista;
    } /* fine del metodo setter */


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
    * gestire gli oggetti a video di questo campo
    * Allora, succede cosi:
    * Il campo decoratore etichetta viene creato in automatico per ogni
    * scheda - Se voglio usare un campo video combo, viene creato prima
    * il campo video testo, poi il decoratore etichetta, poi il campo
    * video combo, poi il decoratore etichetta del combo e poi si arriva
    * qui
    * Se qui c'e' un campo normale ed arriva un campo normale, lo sostituisco
    * Se qui c'e' un campo normale ed arriva un decoratore, lo sostituisco
    * senza problemi, perche' il decoratore ha gia' il suo riferimento al
    * campo che decora e qui non devo fare nulla di particolare
    * Se qui c'e' un decoratore ed arriva un decoratore, lo sostituisco
    * senza fare nulla di particolare qui, perche' i decoratori sono pensati
    * a catena, ognuno ha un riferimento al campo/decoratore che decora
    * Se qui c'e' un decoratore ed arriva un campo normale, NON devo fare
    * nessuna sostituzione, perche' il decoratore ha gia' (dovrebbe) il
    * giusto riferimento al campo decorato
    */
    public void setCampoVideo(CampoVideo unCampoVideo) {
        /** variabili e costanti locali di lavoro */
        boolean videoEsistenteDecoratore;
        boolean videoArrivoDecoratore;

        try {    // prova ad eseguire il codice
            videoEsistenteDecoratore = (getCampoVideo() instanceof CVDecoratore);
            videoArrivoDecoratore = (unCampoVideo instanceof CVDecoratore);

            if (videoEsistenteDecoratore && !videoArrivoDecoratore) {
            } else {
                this.unCampoVideo = unCampoVideo;
            } /* fine del blocco if/else */
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo setter */


    /**
     * contrariamente al metodo sopra, forza la regolazione assoluta del
     * riferimento al campo video
     */
    public void setCampoVideoAssoluto(CampoVideo unCampoVideo) {
        this.unCampoVideo = unCampoVideo;
    } /* fine del metodo setter */


    /* riferimento all'oggetto delegato per gli attributi
    * di visualizzazione del campo nella scheda */
    public void setCampoScheda(CampoScheda unCampoScheda) {
        this.unCampoScheda = unCampoScheda;
    } /* fine del metodo setter */


    /**
     * Nome chiave interno del campo.
     * <p/>
     * (usato per le collezioni HashMap del programma)
     *
     * @param nome il nome interno del campo
     */
    public void setNomeInternoCampo(String nome) {
        this.unNomeInternoCampo = nome;
    } /* fine del metodo setter */


    /**
     * Regola il voce della colonna nella lista.
     */
    public void setTitoloColonna(String unTitoloColonna) {
        this.unCampoLista.setTitoloColonna(unTitoloColonna);
    } /* fine del metodo setter */


    /**
     * Regola il testo della etichetta del campo.
     * <p/>
     *
     * @param testo dell'etichetta da regolare
     */
    public void setTestoEtichetta(String testo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        CampoVideo campoVideo;
        CVDecoratore dec = null;
        CVDEtichetta etichetta;

        try { // prova ad eseguire il codice
            /* recupera l'oggetto intermedio */
            campoVideo = this.getCampoVideo();
            continua = (campoVideo != null);

            if (continua) {
                dec = VideoFactory.getDecoratore(campoVideo, CVDEtichetta.class);
                continua = (dec != null);
            }// fine del blocco if

            if (continua) {
                continua = (dec instanceof CVDEtichetta);
            }// fine del blocco if

            /* controlla la validita' dell'oggetto di passaggio */
            if (continua) {
                etichetta = (CVDEtichetta)dec;
                etichetta.regolaTesto(testo);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void setLarLista(int larghezzaColonna) {
        this.getCampoLista().setLarghezzaColonna(larghezzaColonna);
    }


    /**
     * Regola il numero delle righe.
     * <p/>
     * Numero di righe da visualizzare <br>
     * (l'altezza del pannelloComponenti dipende dalle righe) <br>
     *
     * @param numeroRighe visibili
     */
    public void setNumeroRighe(int numeroRighe) {
        this.getCampoVideo().setNumeroRighe(numeroRighe);
    }


    /**
     * Assegna una scheda per l'editing del record esterno.
     * <p/>
     * (significativo per campi di tipo Combo o Estratto)
     *
     * @param nomeScheda nella collezione schede del modulo esterno
     */
    public void setSchedaPop(String nomeScheda) {
        this.getCampoLogica().setNomeSchedaPop(nomeScheda);
    }


    /**
     * Altezza dei componenti interni al PannelloComponenti
     */
    public void setAltezzaComponentiScheda(int altezza) {
        this.getCampoScheda().setAltezzaComponenti(altezza);
    } /* fine del metodo setter */


    /**
     * Attribuisce la larghezza ai componenti interni al PannelloComponenti
     * <p/>
     * Metodo dinamico invocabile in qualsiasi momento
     */
    public void setLarScheda(int larghezza) {
        /* variabili e costanti locali di lavoro */
        CampoScheda cs;
        CampoVideo cv;

        try { // prova ad eseguire il codice
            cs = this.getCampoScheda();
            if (cs != null) {
                cs.setLarghezzaComponenti(larghezza);
            }// fine del blocco if

            cv = this.getCampoVideo();
            if (cv != null) {
                cv.regolaDimensioneComponenti();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regola la larghezza del campo.
     * <p/>
     * Controlla che la larghezza sia uguale tra lista e campo <br>
     * Regola la larghezza della colonna nella lista <br>
     * Regola la larghezza del pannello campo nella scheda <br>
     *
     * @param larghezza sia della colonna che del pannello campo
     */
    public void setLarghezza(int larghezza) {
        this.setLarLista(larghezza);
        this.setLarScheda(larghezza);
    }


    /**
     * Assegna un renderer per la visualizzazione nelle liste
     * <p/>
     *
     * @param renderer da assegnare
     */
    public void setRenderer(RendererBase renderer) {
        this.getCampoDati().setRenderer(renderer);
    }


    /**
     * Assegna l'editor per il campo nella cella della lista.
     *
     * @param editor del campo
     *
     * @return editor
     */
    public TableCellEditor setEditor(TableCellEditor editor) {
        return this.getCampoDati().setEditor(editor);
    }


    /**
     * regola il valore del campo in memoria.
     * <p/>
     *
     * @param unValoreMemoria il valore Memoria da assegnare
     */
    public void setMemoria(Object unValoreMemoria) {
        this.getCampoDati().setMemoria(unValoreMemoria);
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setVisibileVistaDefault(boolean visibileVistaDefault) {
        this.getCampoLista().setVisibileVistaDefault(visibileVistaDefault);
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setVisibileVistaDefault() {
        this.setVisibileVistaDefault(true);
    } /* fine del metodo setter */


    /**
     * Regola l'ordine privato del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param ordine l'ordine da assegnare al campo
     */
    public void setOrdinePrivato(Ordine ordine) {
        this.getCampoLista().setOrdinePrivato(ordine);
    }


    /**
     * Regola l'ordine privato del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public void setOrdinePrivato(Campo campo) {
        this.getCampoLista().setOrdinePrivato(campo);
    }


    /**
     * Regola l'ordine pubblico del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param ordine l'ordine da assegnare al campo
     */
    public void setOrdinePubblico(Ordine ordine) {
        this.getCampoLista().setOrdinePubblico(ordine);
    }


    /**
     * Regola l'ordine pubblico del campo per le liste.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public void setOrdinePubblico(Campo campo) {
        this.getCampoLista().setOrdinePubblico(campo);
    }


    /**
     * Regola gli ordini pubblico e privato del campo per le liste.
     * <p/>
     *
     * @param ordine l'ordine pubblico e privato da assegnare al campo
     */
    public void setOrdine(Ordine ordine) {
        this.getCampoLista().setOrdine(ordine);
    }


    /**
     * Aggiunge un ordine privato al campo per le liste.
     * <p/>
     *
     * @param unCampo il campo da aggiungere all'ordine esistente
     */
    public void addOrdinePrivato(Campo unCampo) {
        this.getCampoLista().addOrdinePrivato(unCampo);
    }


    /**
     * Aggiunge un campo all' ordine pubblico per le liste.
     * <p/>
     *
     * @param unCampo il campo da aggiungere
     */
    public void addOrdinePubblico(Campo unCampo) {
        this.getCampoLista().addOrdinePubblico(unCampo);
    }


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param ordine da assegnare
     */
    public void setOrdineElenco(Ordine ordine) {
        this.getCampoDB().setOrdineElenco(ordine);
    }


    /**
     * Assegna l'ordine per l'elenco valori linkato.
     * <p/>
     * Usato nei combo
     *
     * @param campo di ordinamento da assegnare
     */
    public void setOrdineElenco(Campo campo) {
        this.getCampoDB().setOrdineElenco(campo);
    }


    /**
     * flag per indicare se il campo e' inizializzato
     */
    public void setInizializzato(boolean inizializzato) {
        this.inizializzato = inizializzato;

        /* regola il flag su tutti i componenti
         * (per ora ce l'ha solo il campo video) */
        this.getCampoVideo().setInizializzato(inizializzato);
    }


    /**
     * flag per indicare se il campo e' inizializzato
     */
    public boolean isInizializzato() {
        return inizializzato;
    }


    /**
     * Regola il testo descrittivo del componente.
     * <p/>
     * (checkbox o radio bottone) <br>
     * (cosa diversa dall'etichetta) <br>
     * Invoca il metodo delegato della classe specifica <br>
     *
     * @param testoComponente testo descrittivo
     */
    public void setTestoComponente(String testoComponente) {
        this.getCampoVideo().setTestoComponente(testoComponente);
    }


    public void setNomeModuloLinkato(String unNomeModuloLinkato) {
        this.getCampoDB().setNomeModuloLinkato(unNomeModuloLinkato);
    }


    /**
     * elenco dei valori alla creazione (di solito stringa)
     */
    public void setValoriInterni(String unaStringaValori) {
        this.getCampoDati().setValoriInterni(unaStringaValori);
    }


    /**
     * lista dei valori alla creazione
     */
    public void setValoriInterni(ArrayList unaListaValori) {
        this.getCampoDati().setValoriInterni(unaListaValori);
    }


    /**
     * lista dei valori alla creazione
     */
    public void setValoriInterni(Campo.ElementiCombo[] valori) {
        this.getCampoDati().setValoriInterni(valori);
    }


    /**
     * lista della legenda (eventuale) affiancata ai valori
     */
    public void setValoriLegenda(ArrayList<String> unaListaValori) {
        this.getCampoDati().setValoriLegenda(unaListaValori);
    }


    /**
     * valori della legenda (eventuale) affiancata ai valori del popup
     */
    public void setValoriLegenda(String valori) {
        this.getCampoDati().setValoriLegenda(valori);
    }


    /**
     * array dei valori alla creazione (di solito stringa)
     */
    public void setValoriInterni(String[] unaListaValori) {
        this.getCampoDati().setValoriInterni(unaListaValori);
    }


    public void setNomeColonnaListaLinkata(String unNomeCampoLink) {
        this.getCampoDB().setNomeColonnaListaLinkata(unNomeCampoLink);
    } /* fine del metodo setter */


    public void setNomeVistaLinkata(String unNomeVistaLink) {
        this.getCampoDB().setNomeVistaLinkata(unNomeVistaLink);
    } /* fine del metodo setter */


    public void setVistaLinkata(Viste vista) {
        this.getCampoDB().setVistaLinkata(vista);
    } /* fine del metodo setter */


    public void setNomeCampoValoriLinkato(String nomeCampoSchedaLinkato) {
        this.getCampoDB().setNomeCampoValoriLinkato(nomeCampoSchedaLinkato);
    } /* fine del metodo */


    public void setCampoLinkato(Campi campo) {
        this.getCampoDB().setCampoLinkato(campo);
    } /* fine del metodo */


    /**
     * assegna il campo che viene visualizzato nella scheda o nel combo
     * <p/>
     *
     * @param campo da visualizzare
     */
    public void setCampoValoriLinkato(Campo campo) {
        this.getCampoDB().setCampoValoriLinkato(campo);
    }


    /**
     * Aggiunge una colonna del modulo linkato alla Vista per il combo.
     * <p/>
     *
     * @param nome del campo del modulo linkato
     */
    public void addColonnaCombo(String nome) {
        this.getCampoVideo().addColonnaCombo(nome);
    }


    /**
     * Aggiunge una colonna alla Vista per il combo.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     * @param nomeCampo nome del campo
     */
    public void addColonnaCombo(String nomeModulo, String nomeCampo) {
        this.getCampoVideo().addColonnaCombo(nomeModulo, nomeCampo);
    }


    /**
     * assegna un estratto che fornisce i valori linkati
     * <p/>
     * l'estratto ha la precedenza sul campo linkato
     *
     * @param estratto che fornisce una matrice doppia con codici e valori
     */
    public void setEstrattoLinkato(Estratti estratto) {
        this.getCampoDB().setEstrattoLinkato(estratto);
    }


    /**
     * Abstract Data Types per le informazioni di un modulo
     */
    public Modulo getModulo() {
        return this.modulo;
    } /* fine del metodo getter */


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
    * gestire tutto quanto non previsto negli altri oggetti delegati */
    public CampoLogica getCampoLogica() {
        return this.unCampoLogica;
    } /* fine del metodo getter */


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
    * gestire il flusso delle informazioni con il database */
    public CampoDB getCampoDB() {
        return unCampoDB;
    } /* fine del metodo getter */


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
    * gestire il flusso interno delle informazioni del campo */
    public CampoDati getCampoDati() {
        return unCampoDati;
    } /* fine del metodo getter */


    /* riferimento all'oggetto delegato per gli attributi
    * di visualizzazione del campo nella lista */
    public CampoLista getCampoLista() {
        return unCampoLista;
    } /* fine del metodo getter */


    /* riferimento all'oggetto delegato per gli attributi
    * di visualizzazione del campo nella scheda */
    public CampoScheda getCampoScheda() {
        return unCampoScheda;
    } /* fine del metodo getter */


    /* riferimento all'oggetto delegato per le funzionalita' necessarie a
    * gestire gli oggetti a video di questo campo */
    public CampoVideo getCampoVideo() {
        return unCampoVideo;
    } /* fine del metodo getter */


    /* ritorna il campo video originale senza
    * gli eventuali decoratori aggiunti */
    public CampoVideo getCampoVideoNonDecorato() {
        /** variabili e costanti locali di lavoro */
        CVDecoratore unDecoratore;
        CampoVideo unCampoVideo;

        /** elabora ricorsivamente il campo fino ad estrarre
         *  il campo originale non decorato*/
        unCampoVideo = this.unCampoVideo;
        while (unCampoVideo instanceof CVDecoratore) {
            unDecoratore = (CVDecoratore)unCampoVideo;
            unCampoVideo = unDecoratore.getCampoVideoDecorato();
        } /* fine del blocco while */

        /** valore di ritorno */
        return unCampoVideo;

    } /* fine del metodo getter */


    /**
     * Ritorna il nome interno del campo.
     * <p/>
     *
     * @return il nome interno del campo
     */
    public String getNomeInterno() {
        return this.unNomeInternoCampo;
    } /* fine del metodo getter */


    /**
     * Ritorna la chiave del campo
     * <p/>
     * Nome modulo (se esiste) + nome interno
     *
     * @return la chiave del campo
     */
    public String getChiaveCampo() {
        /* variabili e costanti locali di lavoro */
        String chiave = null;
        String nomeInterno;
        Modulo modulo;

        try {    // prova ad eseguire il codice
            nomeInterno = this.getNomeInterno();
            modulo = this.getModulo();
            chiave = Lib.Camp.chiaveCampo(nomeInterno, modulo);
        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        return chiave;
    } /* fine del metodo getter */


    /**
     * Restituisce il componente video del campo.
     * <p/>
     *
     * @return il componente video del campo
     */
    public JComponent getComponenteVideo() {
        return this.getCampoVideo().getComponente();
    } /* fine del metodo getter */

//    /**
//     * Restituisce il componente toggle del campo.
//     * <p/>
//     * Per check boxes, radio bottoni ecc..
//     *
//     * @return il componente toggle del campo
//     */
//    public JToggleButton getComponenteToggle() {
//        return this.getCampoVideo().getComponenteToggle();
//    } /* fine del metodo getter */


    /**
     * Restituisce il pannello campo del campo video
     * <p/>
     * E' il pannello che contiene tutti i componenti del campo
     * (etichetta, decoratori, componenti interni)
     *
     * @return il pannello campo
     */
    public JPanel getPannelloCampo() {
        return this.getCampoVideo().getPannelloBaseCampo();
    } /* fine del metodo getter */


    /**
     * Restituisce il pannello componenti campo del campo video
     * <p/>
     * E' il pannello che contiene i soli componenti interni del campo
     *
     * @return il pannello componenti
     */
    public JPanel getPannelloComponenti() {
        return this.getCampoVideo().getPannelloBaseComponenti();
    } /* fine del metodo getter */


    /**
     * Ritorna il testo dell'etichetta
     * <p/>
     *
     * @return il testo dell'etichetta
     *         Se non ha decoratore etichetta ritorna il nome interno
     */
    public String getTestoEtichetta() {
        return this.getCampoVideo().getTestoEtichetta();
    } /* fine del metodo getter */


    /**
     * Ritorna la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @return la larghezza in pixel dell'etichetta
     */
    public int getLarghezzaEtichetta() {
        return this.getCampoVideo().getLarghezzaEtichetta();
    }


    /**
     * Regola la larghezza in pixel dell'etichetta
     * <p/>
     *
     * @param larghezza in pixel dell'etichetta
     */
    public void setLarghezzaEtichetta(int larghezza) {
        this.getCampoVideo().setLarghezzaEtichetta(larghezza);
    }


    /**
     * Regola (sinistra o destra) l'allineamento del testo dell'etichetta
     * <p/>
     *
     * @param bandiera tipo di allineamento
     */
    public void setAllineamentoEtichetta(Pannello.Bandiera bandiera) {
        this.getCampoVideo().setAllineamentoEtichetta(bandiera);
    }


    /**
     * Controlla se il campo ha l'etichetta e se questa è a sinistra.
     * <p/>
     *
     * @return vero se esiste l'etichetta ed è a sinistra
     */
    public boolean isEtichettaSinistra() {
        return this.getCampoVideo().isEtichettaSinistra();
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public boolean isPresenteVistaDefault() {
        return unCampoLista.isPresenteVistaDefault();
    } /* fine del metodo getter */


    /**
     * Verifica se il valore Memoria e' vuoto.
     * <p/>
     *
     * @return true se il valore memoria e' vuoto
     *         (per questo tipo di campo)
     */
    public boolean isVuoto() {
        return this.getCampoDati().isVuoto();
    }


    /**
     * Determina se il campo e' obbligatorio per registrazione / conferma
     * <p/>
     *
     * @return true se obbligatorio
     */
    public boolean isObbligatorio() {
        return this.getCampoVideo().isObbligatorio();
    }


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Non evidenzia il campo e non mostra messaggi.<br>
     *
     * @return true se valido, false se non valido.
     */
    public boolean isValido() {
        return this.getCampoDati().isValido();
    }


    /**
     * Controlla se il contenuto del campo e' valido.
     * <p/>
     * Se non e' valido:<br>
     * se il validatore lo prevede, evidenzia il campo colorando lo sfondo<br>
     */
    public void checkValido() {
        /* variabili e costanti locali di lavoro */
        Validatore validatore;
        JComponent componente;

        try { // prova ad eseguire il codice
            validatore = this.getValidatore();
            if (validatore != null) {
                componente = this.getCampoVideo().getComponente();
                if (componente != null) {
                    validatore.checkValido(componente, false);
                }// fine del blocco if
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiorna il campo (da GUi a memoria e poi di nuovo a GUI) <br>
     */
    public void aggiornaCampo() {
        this.getCampoLogica().aggiornaCampo();
    }


    /**
     * Aggiunge il campo al set di default della scheda.
     */
    public void setPresenteScheda(boolean presente) {
        this.getCampoScheda().setPresenteScheda(presente);
    }


    /**
     * Rende visibile/invisibile il PannelloCampo a video.
     */
    public void setVisibile(boolean visibile) {
        this.getCampoVideo().setVisibile(visibile);
    }


    /**
     * Determina se il Pannello Campo e' visibile.
     */
    public boolean isVisibile() {
        return this.getCampoVideo().isVisibile();
    }


    /**
     * Flag per identificare un campo calcolato.
     * <p/>
     *
     * @return vero se il campo è calcolato
     */
    public boolean isCalcolato() {
        return this.getCampoLogica().isCalcolato();
    }


    /**
     * Regola la ridimensionabilita' del campo in una lista.
     * <p/>
     * <p/>
     * Invoca il metodo delegato della classe specifica <br>
     *
     * @param isRidimensionabile true per rendere il campo ridimensionabile,
     * false per renderlo fisso
     */
    public void setRidimensionabile(boolean isRidimensionabile) {
        this.getCampoLista().setRidimensionabile(isRidimensionabile);
    }


    /**
     * Regola il tool tip per la lista.
     */
    public void setToolTipLista(String toolTipText) {
        this.getCampoLista().setTestoTooltip(toolTipText);
    }


    /**
     * Recupera l'eventuale navigatore gestito dal campo.
     * <p/>
     *
     * @return il navigatore gestito dal campo
     */
    public Navigatore getNavigatore() {
        /* variabili e costanti locali di lavoro */
        CampoLogica logica;
        Navigatore navigatore = null;

        try { // prova ad eseguire il codice
            logica = this.getCampoLogica();
            if (logica != null) {
                navigatore = logica.getNavigatore();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return navigatore;
    }


    /**
     * Regola l'uso dell'indice sul campo
     * <p/>
     *
     * @param flag true se il campo deve essere indicizzato
     */
    public void setIndicizzato(boolean flag) {
        this.getCampoDB().setIndicizzato(flag);
    }


    /**
     * Usa un indice unico sul campo
     * <p/>
     *
     * @param flag per usare un indice unico
     */
    public void setUnico(boolean flag) {
        this.getCampoDB().setUnico(flag);
    }


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
    public void setAzioneDelete(Db.Azione azione) {
        this.getCampoDB().setAzioneDelete(azione);
    }


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
    public void setAzioneUpdate(Db.Azione azione) {
        this.getCampoDB().setAzioneUpdate(azione);
    }


    /**
     * Ritorna l'inizializzatore del campo.
     * <p/>
     *
     * @return l'inizializzatore del campo
     */
    public Init getInit() {
        return this.getCampoDati().getInit();
    }


    /**
     * Regola l'inizializzatore del campo.
     * <p/>
     *
     * @param init l'inizializzatore da assegnare
     */
    public void setInit(Init init) {
        this.getCampoDati().setInit(init);
    }


    /**
     * Regola il valore intero di default per i nuovi record.
     * <p/>
     *
     * @param valore il valore intero da assegnare
     */
    public void setValoreDefault(int valore) {
        /* variabili e costanti locali di lavoro */
        Init init;

        try {    // prova ad eseguire il codice
            init = InitFactory.intero(valore);
            this.setInit(init);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il valore stringa di default per i nuovi record.
     * <p/>
     *
     * @param valore il valore stringa da assegnare
     */
    public void setValoreDefault(String valore) {
        /* variabili e costanti locali di lavoro */
        Init init;

        try {    // prova ad eseguire il codice
            init = InitFactory.testo(valore);
            this.setInit(init);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il valore booleano di default per i nuovi record.
     * <p/>
     *
     * @param valore il valore booleano da assegnare
     */
    public void setValoreDefault(boolean valore) {
        /* variabili e costanti locali di lavoro */
        Init init;

        try {    // prova ad eseguire il codice
            init = InitFactory.bool(valore);
            this.setInit(init);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


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
    public Object bl2db(Object valoreIn, Db db) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;
        int chiaveTipoDati;
        TipoDati tipoDati;

        try {    // prova ad eseguire il codice

            /* recupera il tipo dati dal database */
            chiaveTipoDati = this.getCampoDati().getChiaveTipoDatiDb();
            tipoDati = db.getTipoDati(chiaveTipoDati);

            /* delega la conversione al tipo dati del db */
            valoreOut = tipoDati.bl2db(valoreIn, this);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


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
    public Object db2bl(Object valoreIn, Db db) {
        /* variabili e costanti locali di lavoro */
        Object valoreOut = null;
        int chiaveTipoDati;
        TipoDati tipoDati;

        try {    // prova ad eseguire il codice

            /* recupera il tipo dati dal database */
            chiaveTipoDati = this.getCampoDati().getChiaveTipoDatiDb();
            tipoDati = db.getTipoDati(chiaveTipoDati);

            /* delega la conversione al tipo dati del db */
            valoreOut = tipoDati.db2bl(valoreIn, this);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valoreOut;
    }


    public boolean isModificabileLista() {
        return this.getCampoLista().isModificabile();
    }


    public void setModificabileLista(boolean modificabile) {
        this.getCampoLista().setModificabile(modificabile);
    }


    /**
     * Controlla se il campo è totalizzabile nelle liste
     * <p/>
     *
     * @return true se totalizzabile
     */
    public boolean isTotalizzabile() {
        return this.getCampoLista().isTotalizzabile();
    }


    /**
     * Indica se il campo è totalizzabile nelle liste
     * <p/>
     *
     * @param flag di controllo
     */
    public void setTotalizzabile(boolean flag) {
        this.getCampoLista().setTotalizzabile(flag);
    }


    /**
     * Ritorna true se il campo e' booleano.
     *
     * @return true se booleano
     */
    public boolean isBooleano() {
        return this.getCampoDati().isBooleano();
    }


    /**
     * Ritorna true se il campo e' di testo.
     *
     * @return true se è campo testo
     */
    public boolean isTesto() {
        return this.getCampoDati().isTesto();
    }


    /**
     * Ritorna true se il campo e' di tipo testoArea.
     *
     * @return true se è campo testoArea
     */
    public boolean isTestoArea() {
        return this.getCampoDati().isTestoArea();
    }


    /**
     * Ritorna true se il campo e' numerico.
     *
     * @return true se è campo numerico
     */
    public boolean isNumero() {
        return this.getCampoDati().isNumero();
    }


    /**
     * Ritorna true se il campo e' data.
     *
     * @return true se è campo data
     */
    public boolean isData() {
        return this.getCampoDati().isData();
    }


    /**
     * Ritorna true se il campo e' Timestamp.
     *
     * @return true se è campo Timestamp
     */
    public boolean isTimestamp() {
        return this.getCampoDati().isTimestamp();
    }


    /**
     * Rende il campo focusable o non focusable.
     * <p/>
     * Metodo sovrascritto dalle sottoclassi<br>
     *
     * @param flag di controllo dell'attributo Focusable
     */
    public void setFocusable(boolean flag) {
        this.getCampoVideo().setFocusable(flag);
    }


    /**
     * Assegna il colore di primo piano.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setForegroundColor(Color colore) {
        this.getCampoVideo().setForegroundColor(colore);
    }


    /**
     * Assegna il colore di sfondo.
     * <p/>
     *
     * @param colore da assegnare
     */
    public void setBackgroundColor(Color colore) {
        this.getCampoVideo().setBackgroundColor(colore);
    }


    /**
     * Assegna il font al campo.
     * <p/>
     *
     * @param font da assegnare
     */
    public void setFont(Font font) {
        this.getCampoVideo().setFont(font);
    }


    /**
     * Ritorna lo stato di abilitazione del campo.
     * <p/>
     *
     * @return true se abilitato, false se disabiitato
     */
    public boolean isAbilitato() {
        return this.abilitato;
    }


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
    public void setAbilitato(boolean flag) {
        this.abilitato = flag;

        /* in runtime: se viene disabilitato, rende
         * immediatamente non modificabile */
        if (!flag) {
            if (this.isModificabile()) {
                this.setModificabile(false);
            }// fine del blocco if
        }// fine del blocco if

    }


    /**
     * Recupera lo stato corrente di modificabilità del campo.
     * <p/>
     *
     * @return lo stato di modificabilità del campo
     */
    public boolean isModificabile() {
        return this.getCampoVideo().isModificabile();
    }


    /**
     * Rende il campo modificabile o meno.
     * <p/>
     * Se il flag è true, risponde solo se il campo è abilitato.<br>
     *
     * @param flag true se il campo deve essere modificabile
     */
    public void setModificabile(boolean flag) {
        this.getCampoVideo().setModificabile(flag);
    }


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
    public void setAllineamento(int allineamento) {
        this.getCampoVideo().setAllineamento(allineamento);
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
        this.getCampoDati().setAllineamentoLista(allineamento);
    }


    /**
     * Determina se e come il campo si espande nel proprio contenitore
     * <p/>
     *
     * @param espandibilita codifica del tipo di espandibilità
     */
    public void setEspandibilita(CampoVideo.Espandibilita espandibilita) {
        this.getCampoVideo().setEspandibilita(espandibilita);
    }


    /**
     * Pone il fuoco sul componente del campo video.
     * <p/>
     * Attenzione! il componente deve essere focusable, visibile e displayable,
     * altrimenti la chiamata non ha effetto. <br>
     * Per essere displayable, deve essere effettivamente visualizzato
     * a video, cioe' contenuto in un contenitore che faccia capo a una
     * finestra gia' visibile o gia' packed. <br>
     */
    public void grabFocus() {
        this.getCampoVideo().grabFocus();
    }


    /**
     * Regola l'orientamento del layout del Pannello Componenti.
     * <p/>
     *
     * @param orientamento il codice di orientamento
     * Layout.ORIENTAMENTO_ORIZZONTALE o Layout.ORIENTAMENTO_VERTICALE
     */
    public void setOrientamentoComponenti(int orientamento) {
        this.getCampoVideo().setOrientamentoComponenti(orientamento);
    }


    /**
     * flag di controllo per i campi da utilizzare nella ricerca standard.
     *
     * @param ricercabile flag booleano
     */
    public void setRicercabile(boolean ricercabile) {
        this.getCampoDati().setRicercabile(ricercabile);
    }


    /**
     * flag di controllo per usare campi di ricerca doppi (range da...a...).
     *
     * @param usaRangeRicerca flag booleano
     */
    public void setUsaRangeRicerca(boolean usaRangeRicerca) {
        this.getCampoDati().setUsaRangeRicerca(usaRangeRicerca);
    }


    /**
     * Ritorna un oggetto che rappresenta il valore del campo per un filtro.
     * <p/>
     * Si usa per costruire un filtro con il valore di memoria corrente del campo.
     *
     * @return il valore di filtro
     */
    public Object getValoreFiltro() {
        return this.getCampoDati().getValoreFiltro();
    }


    /**
     * Controlla l'uso della voce Non Specificato.
     * <p/>
     *
     * @param flag per usare la voce Non Specificato
     */
    public void setUsaNonSpecificato(boolean flag) {
        this.getCampoVideo().setUsaNonSpecificato(flag);
    }


    /**
     * Controlla l'uso della funzione Nuovo Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public void setUsaNuovo(boolean flag) {
        this.getCampoVideo().setUsaNuovo(flag);
    }


    /**
     * Determina se il comando "Nuovo record" viene posizionato prima o dopo
     * la lista dei valori.
     * <p/>
     *
     * @param flag true per posizionare prima, false per dopo
     */
    public void setNuovoIniziale(boolean flag) {
        this.getCampoDati().setNuovoIniziale(flag);
    }


    /**
     * Controlla l'uso della funzione Modifica Record.
     * <p/>
     *
     * @param flag per usare la funzione Nuovo Record
     */
    public void setUsaModifica(boolean flag) {
        this.getCampoVideo().setUsaModifica(flag);
    }


    /**
     * Ritorna il filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro base
     */
    public Filtro getFiltroBase() {
        return getCampoDB().getFiltroBase();
    }


    /**
     * Assegna un filtro base per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public void setFiltroBase(Filtro filtro) {
        this.getCampoDB().setFiltroBase(filtro);
    }


    /**
     * Ritorna il filtro corrente per la selezione dei record
     * linkati da visualizzare
     * <p/>
     *
     * @return il filtro corrente
     */
    public Filtro getFiltroCorrente() {
        return getCampoDB().getFiltroCorrente();
    }


    /**
     * Assegna un filtro corrente per la selezione dei
     * record linkati da visualizzare
     * <p/>
     *
     * @param filtro da utilizzare
     */
    public void setFiltroCorrente(Filtro filtro) {
        this.getCampoDB().setFiltroCorrente(filtro);
    }


    /**
     * Recupera il validatore al campo.
     *
     * @return il validatore associato al campo
     */
    public Validatore getValidatore() {
        return this.getCampoDati().getValidatore();
    }


    /**
     * Assegna un validatore al campo.
     *
     * @param validatore da assegnare
     */
    public void setValidatore(Validatore validatore) {
        this.getCampoDati().setValidatore(validatore);
    }


    /**
     * Assegna un validatore non vuoto al campo.
     */
    public void setCampoNonVuoto() {
        this.getCampoDati().setValidatore(ValidatoreFactory.testoNonVuoto());
    }

//    /**
//     * Campo obbligatorio per registrazione/conferma scheda/dialogo.
//     * <p/>
//     *
//     * @param obbligatorio
//     */
//    public void setObbligatorio(boolean obbligatorio) {
//        this.getCampoScheda().setObbligatorio(obbligatorio);
//    }
//
//
//    /**
//     * Campo obbligatorio per registrazione/conferma scheda/dialogo.
//     * <p/>
//     */
//    public void setObbligatorio() {
//        this.getCampoScheda().setObbligatorio(true);
//    }


    /**
     * Assegna la posizione dell'etichetta.
     *
     * @param posEtichetta sopra o sotto
     */
    public void setPosEtichetta(int posEtichetta) {
        /* variabili e costanti locali di lavoro */
        CampoScheda campoScheda;

        try { // prova ad eseguire il codice
            /* recupera l'oggetto intermedio */
            campoScheda = this.getCampoScheda();

            /* controlla la validità dell'oggetto di passaggio */
            if (campoScheda != null) {
                campoScheda.setPosEtichetta(posEtichetta);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera il valore corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public Object getValore() {
        return this.getCampoDati().getValore();
    }


    /**
     * Recupera il valore booleano corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public boolean getBool() {
        return this.getCampoDati().getBool();
    }


    /**
     * Recupera il valore stringa corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public String getString() {
        return this.getCampoDati().getString();
    }


    /**
     * Recupera il valore intero corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public int getInt() {
        return this.getCampoDati().getInt();
    }


    /**
     * Recupera il valore doppio corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public double getDouble() {
        return this.getCampoDati().getDouble();
    }


    /**
     * Recupera il valore data corrente del campo.
     * <p/>
     *
     * @return il valore corrente
     */
    public Date getData() {
        return this.getCampoDati().getData();
    }


    /**
     * Recupera il valore di elenco correntemente selezionato.
     * <p/>
     *
     * @return il valore corrispondente
     */
    public Object getValoreElenco() {
        return this.getCampoDati().getValoreElenco();
    }


    /**
     * Assegna il valore memoria del campo in base a un valore di elenco.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param valore da cercare nell'elenco
     */
    public void setValoreDaElenco(Object valore) {
        this.getCampoDati().setValoreDaElenco(valore);
    }


    public Form getForm() {
        return form;
    }


    public void setForm(Form form) {
        this.form = form;
    }


    /**
     * Restituisce la scheda attualmente proprietaria del campo.
     * <p/>
     *
     * @return la scheda proprietaria
     */
    public Scheda getScheda() {
        /* variabili e costanti locali di lavoro */
        Scheda scheda = null;
        Form form;

        try {    // prova ad eseguire il codice
            form = this.getForm();
            if ((form != null) && (form instanceof Scheda)) {
                scheda = (Scheda)form;
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return scheda;
    }


    /**
     * Flag di controllo per disegnare provvisoriamente sfondi colorati.
     *
     * @return vero se attivo
     */
    public boolean isDebug() {
        return Campo.DEBUG;
    }


    public CampoBase getCampoBase() {
        return this;
    }


    /**
     * Ritorna un'icona che rappresenta il tipo di campo.
     * <p/>
     *
     * @return l'icona del campo
     */
    public Icon getIcona() {
        /* variabili e costanti locali di lavoro */
        Icon icona = null;

        try {    // prova ad eseguire il codice
            icona = this.getCampoDati().getIcona();
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return icona;
    }


    /**
     * aggiunge un operatore di ricerca al campo.
     *
     * @param operatore la chiave dell'operatore
     *
     * @see Filtro.Op
     */
    public void addOperatoreRicerca(String operatore) {
        this.getCampoDati().addOperatoreRicerca(operatore);
    }


    /**
     * svuota la lista degli operatori di ricerca del campo.
     */
    public void clearOperatoriRicerca() {
        this.getCampoDati().clearOperatoriRicerca();
    }


    /**
     * Ritorna l'elenco degli operatori filtro disponibili per il campo
     *
     * @return l'elenco delle chiavi degli operatori filtro disponibili
     */
    public ArrayList<String> getOperatoriRicerca() {
        return this.getCampoDati().getOperatoriRicerca();
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
        return this.getCampoDati().getOperatoreRicercaDefault();
    }


    /**
     * Imposta l'operatore di ricerca di default
     * <p/>
     *
     * @param operatore di ricerca di default
     */
    public void setOperatoreRicercaDefault(String operatore) {
        this.getCampoDati().setOperatoreRicercaDefault(operatore);
    }


    public EventListenerList getListaListener() {
        return listaListener;
    }


    public void setListaListener(EventListenerList listaListener) {
        this.listaListener = listaListener;
    }


    /**
     * Aggiunge i <code>Listener</code>.
     * <p/>
     * Aggiunge ai componenti video di questo campo gli eventuali
     * ascoltatori delle azioni (eventi) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param azione azione da aggiungere
     */
    public void aggiungeListener(BaseListener azione) {
        this.getCampoVideo().aggiungeListener(azione);
    }


    /**
     * Classe interna per passare i metodi della classe VideoFactory
     */
    public CVBase.Decora decora() {
        return this.getCampoVideo().decora();
    }


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
    public Object esegueOperazione(ArrayList<Object> valori) {
        return this.getCampoLogica().esegueOperazione(valori);
    }

//    public AzioneVideoCampo getAzioneVideoCampo() {
//        return azioneVideoCampo;
//    }
//
//
//    private void setAzioneVideoCampo(AzioneVideoCampo azioneVideoCampo) {
//        this.azioneVideoCampo = azioneVideoCampo;
//    }
//
//
//    public AzioneGUICampo getAzioneGUICampo() {
//        return azioneGUICampo;
//    }
//
//
//    private void setAzioneGUICampo(AzioneGUICampo azioneGUICampo) {
//        this.azioneGUICampo = azioneGUICampo;
//    }


    /**
     * Clona questo campo.
     * <p/>
     *
     * @return il campo clonato.
     *         <p/>
     *         Il campo clonato è marcato come non inizializzato per dare la
     *         possibilità di modificarlo dopo la clonazione e di reinizializzarlo
     *         con le nuove caratteristiche.
     *         <p/>
     *         Se il campo era già inizializzato e non interessa modificarne le
     *         caratteristiche, è sufficiente marcarlo nuovamente come inizializzato
     *         dopo la clonazione.
     *         <p/>
     *         Ritorna una copia profonda dell'oggetto (deep copy) col casting
     *         Per fare una copia completa di questo oggetto occorre:
     *         Prima copiare l'oggetto nel suo insieme, richiamando il metodo
     *         sovrascritto che copia e regola tutte le variabili dell'oggetto con
     *         gli stessi valori delle variabili originarie
     *         Secondo copiare tutte le variabili che sono puntatori ad altri
     *         oggetti, per evitare che nella copia ci sia il puntatore all'oggetto
     *         originale (in genere tutti gli oggetti che vengono creati nella
     *         classe col comando new)
     *         Terzo in ogni sottoclasse occorre fare le copie dei puntatori
     *         esistenti nelle sottoclassi stesse
     */
    public Campo clonaCampo() {
        /* variabili e costanti locali di lavoro */
        Campo unCampo;
        CampoLogica logica;
        CampoDB db;
        CampoDati dati;
        CampoLista lista;
        CampoScheda scheda;
        CampoVideo video;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (Campo)super.clone();
        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
            throw new InternalError();
        }// fine del blocco try-catch

        try {    // prova ad eseguire il codice

            /* clona i singoli oggetti */
            logica = this.getCampoLogica();
            logica = logica.clonaCampo(unCampo);
            unCampo.setCampoLogica(logica);

            db = this.getCampoDB();
            db = db.clonaCampo(unCampo);
            unCampo.setCampoDB(db);

            dati = this.getCampoDati();
            dati = dati.clonaCampo(unCampo);
            unCampo.setCampoDati(dati);

            lista = this.getCampoLista();
            lista = lista.clonaCampo(unCampo);
            unCampo.setCampoLista(lista);

            scheda = this.getCampoScheda();
            scheda = scheda.clonaCampo(unCampo);
            unCampo.setCampoScheda(scheda);

            video = this.getCampoVideo();
            video = video.clonaCampo(unCampo);
            unCampo.setCampoVideo(video);

            /* regola il campo come non inizializzato */
            unCampo.setInizializzato(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @param listener specifico
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.addLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Aggiunge un listener.
     * <p/>
     * Aggiunge uno specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void addListener(Eventi evento, BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            evento.add(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Rimuove un listener.
     * <p/>
     * Rimuove lo specifico listener <br>
     * L'oggetto mantiene una propria collezione di listener <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void removeListener(BaseListener listener) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Evento.removeLocale(listaListener, listener);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     * @deprecated
     */
    public void fire() {
    }


    /**
     * Avvisa tutti i listener.
     * <p/>
     * Avvisa tutti i listener che si sono registrati
     * per questo tipo di evento <br>
     * L'evento viene creato al momento <br>
     * È responsabilità della classe invocare questo metodo quando
     * si creano le condizioni per generare l'evento <br>
     *
     * @see javax.swing.event.EventListenerList
     */
    public void fire(Eventi unEvento) {
        /* variabili e costanti locali di lavoro */
        EventListenerList listaListener;

        try {    // prova ad eseguire il codice
            listaListener = this.getListaListener();
            Lib.Eventi.fire(listaListener, unEvento, Campo.class, this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna una rappresentazione del campo sotto forma di stringa.
     * <p/>
     *
     * @return una stringa che rappresenta il campo
     */
    public String toString() {
        /* variabili e costanti locali di lavoro */
        String nomeModulo;
        String nomeCampo = "";
        Modulo modulo;

        try {    // prova ad eseguire il codice
            nomeCampo = this.getNomeInterno();
            modulo = this.getModulo();
            if (modulo != null) {
                nomeModulo = modulo.getNomeChiave();
                if (Lib.Testo.isValida(nomeModulo)) {
                    nomeCampo = nomeModulo + "." + nomeCampo;
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomeCampo;
    }


    /**
     * Determina se un oggetto e' "uguale" a questo oggetto.
     * <p/>
     * Due campi sono uguali se hanno la stessa chiave.
     *
     * @return una stringa che rappresenta il campo
     */
    public boolean equals(Object obj) {
        /* variabili e costanti locali di lavoro */
        boolean uguali = false;
        boolean continua;
        Campo campo;
        String chiaveQuesto;
        String chiaveAltro;

        try {    // prova ad eseguire il codice

            /* controla che non sia nullo */
            continua = obj != null;

            /* controla che sia un campo */
            if (continua) {
                continua = obj instanceof Campo;
            }// fine del blocco if

            /* controlla che abbia la stessa chiave di questo campo */
            if (continua) {
                campo = (Campo)obj;
                chiaveQuesto = this.getChiaveCampo();
                chiaveAltro = campo.getChiaveCampo();
                if (chiaveQuesto.equals(chiaveAltro)) {
                    uguali = true;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return uguali;
    }

//    /**
//     * Inner class per gestire l'azione.
//     */
//    private class AzioneVideoCampo extends CampoVideoAz {
//
//        /**
//         * campoVideoAz, da CampoListener.
//         * </p>
//         * Esegue l'azione <br>
//         * Rimanda al metodo delegato, nel gestore specifico associato
//         * all' oggetto che genera questo evento <br>
//         * Sovrascritto nelle sottoclassi <br>
//         *
//         * @param unEvento evento che causa l'azione da eseguire <br>
//         */
//        public void campoVideoAz(CampoVideoEve unEvento) {
//            try { // prova ad eseguire il codice
//                /* invoca il metodo delegato della classe gestione eventi */
//                getCampoDati().videoModificato();
//            } catch (Exception unErrore) { // intercetta l'errore
//                Errore.crea(unErrore);
//            }// fine del blocco try-catch
//        }
//    } // fine della classe interna


    /**
     * Azione di cambio stato GUI del campo
     */
    public class AzioneGUICampo extends CampoGUIAz {

        /**
         * AzioneGUICampo, da CampoListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoGUIAz(CampoGUIEve unEvento) {
            try { // prova ad eseguire il codice

                /* invoca il metodo delegato del campo video */
                getCampoLogica().guiModificata();

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Azione di cambio valore memoria del campo.
     */
    private class AzioneMemoriaCampo extends CampoMemoriaAz {

        /**
         * CampoMemoriaAz, da CampoListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                eventoMemoriaModificata();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Azione di cambio valore memoria del campo.
     */
    private class AzioneVideoCampo extends CampoVideoAz {

        /**
         * CampoMemoriaAz, da CampoListener.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoVideoAz(CampoVideoEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                eventoVideoModificato();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Eventi che possono essere lanciati dal campo <br>
     * Per ogni evento: <ul>
     * <li> classe interfaccia </li>
     * <li> classe evento </li>
     * <li> classe azione </li>
     * <li> metodo azione </li>
     * </ul>
     */
    public enum Evento implements Eventi {

        /* qualsiasi cosa accade nel campo */
        generico(CampoBaseLis.class, CampoBaseEve.class, CampoBaseAz.class, "campoAz"),

        /* quando il campo prende il fuoco */
        prendeFuoco(CampoPrendeFuocoLis.class,
                CampoPrendeFuocoEve.class,
                CampoPrendeFuocoAz.class,
                "prendeFuocoAz"),

        /* quando il campo perde il fuoco */
        perdeFuoco(CampoPerdeFuocoLis.class,
                CampoPerdeFuocoEve.class,
                CampoPerdeFuocoAz.class,
                "perdeFuocoAz"),

        /* quando il campo perde il fuoco dopo una modifica  */
        fuocoModificato(CampoModificatoLis.class,
                CampoModificatoEve.class,
                CampoModificatoAz.class,
                "campoModificatoAz"),

        /* quando il valore memoria del campo cambia o torna uguale
         * rispetto al backup */
        statoModificato(CampoStatoLis.class,
                CampoStatoEve.class,
                CampoStatoAz.class,
                "campoStatoAz"),

        /* quando viene eseguito un doppio clic nel campo */
        doppioClick(CampoDoppioClickLis.class,
                CampoDoppioClickEve.class,
                CampoDoppioClickAz.class,
                "campoDoppioClickAz"),
        /* quando la GUI del campo viene modificata (tutti i caratteri) */
        GUIModificata(CampoGUILis.class, CampoGUIEve.class, CampoGUIAz.class, "campoGUIAz"),

        /* quando il valore video del campo viene modificato (quello c he vedo) */
        videoModificato(CampoVideoLis.class,
                CampoVideoEve.class,
                CampoVideoAz.class,
                "campoVideoAz"),

        /* quando il valore memoria del campo viene modificato */
        memoriaModificata(CampoMemoriaLis.class,
                CampoMemoriaEve.class,
                CampoMemoriaAz.class,
                "campoMemoriaAz"),

        /* quando viene premuto un bottone associato al campo */
        bottonePremuto(BottoneLis.class, BottoneEve.class, BottoneAz.class, "bottoneAz"),

        /* dopo che un campo (combo) ha presentato un record in modifica */
        presentatoRecord(CampoPresentatoRecordLis.class,
                CampoPresentatoRecordEve.class,
                CampoPresentatoRecordAz.class,
                "presentatoRecordAz");

        /**
         * interfaccia listener per l'evento
         */
        private Class listener;

        /**
         * classe evento
         */
        private Class evento;

        /**
         * classe azione
         */
        private Class azione;

        /**
         * metodo
         */
        private String metodo;


        /**
         * Costruttore completo con parametri.
         *
         * @param listener interfaccia
         * @param evento classe
         * @param azione classe
         * @param metodo nome metodo azione
         */
        Evento(Class listener, Class evento, Class azione, String metodo) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setListener(listener);
                this.setEvento(evento);
                this.setAzione(azione);
                this.setMetodo(metodo);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public static void addLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Metodo statico <br>
         * Serve per utilizzare questa Enumeration <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public static void removeLocale(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        /**
         * Aggiunge un listener alla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi a cui aggiungersi
         * @param listener dell'evento da lanciare
         */
        public void add(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.add(Evento.values(), lista, listener);
        }


        /**
         * Rimuove un listener dalla lista.
         * <p/>
         * Serve per utilizzare la Enumeration della sottoclasse <br>
         * Metodo (sovra)scritto nelle Enumeration specifiche
         * (le Enumeration delle sottoclassi della classe dove
         * e' questa Enumeration) <br>
         * Controlla che il listener appartenga all'enumerazione <br>
         *
         * @param lista degli eventi da cui rimuoverlo
         * @param listener dell'evento da non lanciare
         */
        public void remove(EventListenerList lista, BaseListener listener) {
            Lib.Eventi.remove(Evento.values(), lista, listener);
        }


        public Class getListener() {
            return listener;
        }


        private void setListener(Class listener) {
            this.listener = listener;
        }


        public Class getEvento() {
            return evento;
        }


        private void setEvento(Class evento) {
            this.evento = evento;
        }


        public Class getAzione() {
            return azione;
        }


        private void setAzione(Class azione) {
            this.azione = azione;
        }


        public String getMetodo() {
            return metodo;
        }


        private void setMetodo(String metodo) {
            this.metodo = metodo;
        }


    }// fine della classe

}// fine della classe
