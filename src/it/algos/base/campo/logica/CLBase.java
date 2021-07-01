/**
 * Title:        CLBase.java
 * Package:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 20.46
 */

package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoAstratto;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoBaseEve;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;

import java.util.ArrayList;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Regola le funzionalita dei rapporti interni dei Campi <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 20.46
 */
public abstract class CLBase extends CampoAstratto implements Cloneable, CampoLogica {

    /**
     * eventuale modulo del campo gestito internamente
     */
    private Modulo unModuloInterno = null;


    /**
     * riferimento all'azione da associare al campo
     */
    private AzioneCalcolata azione = null;

    /**
     * nome della scheda per l'editing del record esterno
     */
    private String nomeSchedaPop = "";


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CLBase() {
        /* rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLBase(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni iniziali di riferimenti e variabili <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {
        /* invoca il metodo (quasi) sovrascritto della superclasse */
        super.inizializzaCampoAstratto();
    } /* fine del metodo */


    /**
     * Regolazioni di avvio, ogni volta che questo oggetto deve 'ripartire', per
     * essere sicuri che sia 'pulito' <br>
     * Metodo chiamato da altre classi <br>
     * Viene eseguito tutte le volte che necessita  <br>
     */
    public void avvia() {
    } /* fine del metodo */


    /**
     * Regola l'azione.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Associa l'azione al componente dei campi osservati <br>
     */
    public void regolaAzione() {
    }


    /**
     * Allinea le variabili del Campo: da Archivio verso Memoria.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Archivio (gia' regolata), e regola
     * di conseguenza Memoria, Backup <br>
     * Alla fine viene aggiornato l'oggetto GUI del CampoVideo <br>
     */
    public void archivioMemoria() {
        try {    // prova ad eseguire il codice
            /* invoca il metodo delegato */
            this.getCampoDati().archivioMemoria();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Allinea le variabili del Campo: da Memoria verso GUI.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Video <br>
     * In CampoVideo vengono aggiornati i componenti GUI <br>
     * I componenti GUI sono allineati coi valori della memoria
     * dopo eventuali elaborazioni con altri campi <br>
     */
    public void memoriaGui() {
        /* variabili e costanti locali di lavoro */
        Object unValore;

        try {    // prova ad eseguire il codice
            /* invoca il metodo delegato */
            this.getCampoDati().memoriaVideo();

            /* recupera il valore */
            unValore = this.getCampoDati().getVideo();

            /* invoca il metodo delegato */
            this.getCampoVideo().aggiornaGUI(unValore);

        } catch (Exception unErrore) {    // intercetta l'errore
            /* mostra il messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Allinea le variabili del Campo: da Archivio verso GUI.
     * <p/>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Archivio (gia' regolata), e regola
     * di conseguenza Memoria, Backup <br>
     * In CampoVideo vengono aggiornati i componenti GUI <br>
     * I componenti GUI sono allineati coi valori della memoria
     * dopo eventuali elaborazioni con altri campi <br>
     * Alla fine viene aggiornato l'oggetto GUI del CampoVideo <br>
     */
    public void archivioGui() {
        try {    // prova ad eseguire il codice
            /* invoca il metodo delegato */
            this.archivioMemoria();

            /* invoca il metodo delegato */
            this.memoriaGui();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    }


    /**
     * Allinea le variabili del Campo: da GUI verso video.
     * <p/>
     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
     * variabili Video di CampoDati <br>
     * Recupera il valore attuale del componente GUI in CampoVideo <br>
     * Aggiorna il valore del video in CampoDati <br>
     */
    public void guiVideo() {
        /* variabili e costanti locali di lavoro */
        Object unValore;

        try { // prova ad eseguire il codice

            /* recupera il valore */
            unValore = this.getCampoVideoNonDecorato().recuperaGUI();

            /* regola il valore */
            this.getCampoDati().setVideo(unValore);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Allinea le variabili del Campo: da GUI verso Memoria.
     * <p/>
     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
     * variabili Video e Memoria di CampoDati <br>
     * Recupera il valore attuale del componente GUI in CampoVideo <br>
     * Aggiorna il valore del video e della memoria in CampoDati <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     */
    public void guiMemoria() {

        try { // prova ad eseguire il codice

            /* trasporta da GUI a video*/
            this.guiVideo();

            /* trasporta da Video a Memoria */
            this.getCampoDati().videoMemoria();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Allinea le variabili del Campo: da Memoria verso Archivio.
     * </p>
     * Vengono effettuate le conversioni tra i varii tipi di dati <br>
     * In CampoDati parte dalla variabile Memoria (gia' regolata), e regola
     * di conseguenza Archivio <br>
     * La variabile archivio e' allineata per la registrazione <br>
     */
    public void memoriaArchivio() {
        try { // prova ad eseguire il codice
            /* invoca il metodo delegato */
            this.getCampoDati().memoriaArchivio();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Allinea le variabili del Campo: da GUI verso Archivio.
     * <p/>
     * Partendo dalla componente GUI di CampoVideo, regola di conseguenza le
     * variabili Video e Memoria di CampoDati <br>
     * Recupera il valore attuale del componente GUI in CampoVideo <br>
     * Aggiorna il valore del video e della memoria in CampoDati <br>
     * La variabile memoria e' allineata per calcoli varii <br>
     * La variabile archivio e' allineata per la registrazione <br>
     */
    public void guiArchivio() {
        /* invoca il metodo delegato di questa classe */
        this.guiMemoria();

        /* invoca il metodo delegato di questa classe */
        this.memoriaArchivio();
    }


    /**
     * Svuota il valore del campo.
     * <p/>
     * Assegna alla variabile Memoria il proprio valore vuoto <br>
     * Allinea la variabile Backup col nuovo valore <br>
     * Fa risalire i valori fino alla GUI <br>.
     */
    public void reset() {

        /* invoca il metodo delegato */
        this.getCampoDati().reset();

        /* invoca il metodo delegato di questa classe */
        this.memoriaGui();

    }


    /**
     * Gestione evento gui modificata.
     * <p/>
     * Allinea le variabili del Campo: da GUI verso Memoria <br>
     * Invoca il metodo delegato del campo video <br>
     */
    public void guiModificata() {
        try { // prova ad eseguire il codice
            this.getCampoVideoNonDecorato().guiModificata();
            this.guiMemoria();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * campoModificato, da CampoListener.
     * </p>
     * Esegue il comando <br>
     *
     * @param unEvento evento che causa il comando da eseguire <br>
     */
    public void campoModificato(CampoBaseEve unEvento) {
        // da sviluppare nella sottoclasse specifica
    } // fine del metodo


    /**
     * Recupera il CampoDati.
     *
     * @return il campo dati
     */
    protected CampoDati getCampoDati() {
        return this.getCampoParente().getCampoDati();
    }


    /**
     * Recupera il CampoVideo.
     *
     * @return il campo video decorato
     */
    private CampoVideo getCampoVideo() {
        return this.getCampoParente().getCampoVideo();
    }


    /**
     * Recupera il CampoVideo non decorato.
     *
     * @return il campo video non decorato
     */
    protected CampoVideo getCampoVideoNonDecorato() {
        return this.getCampoParente().getCampoVideoNonDecorato();
    }


    /**
     * ...
     */
    public void setModuloInterno(Modulo unModuloInterno) {
        this.unModuloInterno = unModuloInterno;
    } /* fine del metodo */


    /**
     * ...
     */
    public Modulo getModuloInterno() {
        return this.unModuloInterno;
    } /* fine del metodo */


    /**
     * Aggiorna il campo (da GUi a memoria e poi di nuovo a GUI) <br>
     */
    public void aggiornaCampo() {
        this.guiMemoria();
    } /* fine del metodo */


    public void setNomeCampoOriginale(String nomeCampoOriginale) {
    }


    public void esegui() {
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
        return null;
    }


    /**
     * Ritorna l'elenco dei nomi dei campi osservati
     * <p/>
     * I campi sono dello stesso modulo di questo campo.
     *
     * @return i nomi dei campi osservati
     */
    public ArrayList<String> getCampiOsservati() {
        return null;
    }


    public AzioneCalcolata getAzione() {
        return azione;
    }


    protected void setAzione(AzioneCalcolata azione) {
        this.azione = azione;
    }


    public Navigatore getNavigatore() {
        return null;
    }


    /**
     * Assegna il nome del modulo per recuperare il navigatore.
     * <p/>
     *
     * @param nomeModulo nome del modulo
     */
    public void setNomeModulo(String nomeModulo) {
    }


    protected String getNomeSchedaPop() {
        return nomeSchedaPop;
    }


    /**
     * Assegna una scheda per l'editing del record esterno.
     * <p/>
     * (significativo per campi di tipo Combo o Estratto)
     *
     * @param nomeSchedaPop nella collezione schede del modulo esterno
     */
    public void setNomeSchedaPop(String nomeSchedaPop) {
        this.nomeSchedaPop = nomeSchedaPop;
    }


    /**
     * Flag per identificare un campo calcolato.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * La variabile d'istanza viene mantenuta nella sottoclasse specifica <br>
     *
     * @return vero se è un campo calcolato
     */
    public boolean isCalcolato() {
        return false;
    }


    /**
     * Restituisce un campo dal nome.
     * <p/>
     *
     * @param nomeCampo richiesto
     *
     * @return campo richiesto
     */
    protected Campo getCampo(String nomeCampo) {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        boolean continua;
        Campo campoParente;
        Modulo mod = null;

        try { // prova ad eseguire il codice
            campoParente = this.getCampoParente();
            continua = (campoParente != null);

            if (continua) {
                mod = campoParente.getModulo();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                campo = mod.getCampo(nomeCampo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    /**
     * Inner class per gestire l'azione.
     */
    public class AzioneCalcolata extends CampoMemoriaAz {

        /**
         * campoAz, da CampoLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            try { // prova ad eseguire il codice
                /* invoca il metodo delegato della classe gestione eventi */
                esegui();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


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
     * @param unCampoParente CampoBase che contiene questo CampoLogica
     *
     * @return campo logica clonato
     */
    public CampoLogica clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CampoLogica unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (CampoLogica)super.clone();
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

