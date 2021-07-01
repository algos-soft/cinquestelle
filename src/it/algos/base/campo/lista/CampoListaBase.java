/**
 * Title:        CampoListaBase.java
 * Package:      prove.nuovocampo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 20.15
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.lista;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoAstratto;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.query.ordine.Ordine;

import java.awt.*;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Regola le funzionalita di gestione di una colonna a video nella Lista <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 20.15
 */
public abstract class CampoListaBase extends CampoAstratto implements Cloneable, CampoLista {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------

    /**
     * Larghezza minima di default in una Lista
     */
    private static int LARGHEZZA_MINIMA_DEFAULT = 50;

    /**
     * colore del testo della colonna nella lista
     */
    private static Color COLORE_COLONNA = Color.blue;

    /**
     * Ordine associato a questo campo per uso interno.
     * <p/>
     * Di norma nelle liste/viste<br>
     * Di default solo se stesso<br>
     */
    private Ordine ordinePrivato = null;

    /**
     * Ordine associato a questo campo per uso dall'esterno del Modulo.
     * <p/>
     * Di norma nei popup delle schede<br>
     * Di default uguale all'ordine privato<br>
     */
    private Ordine ordinePubblico = null;

    /**
     * voce della colonna
     */
    private String titoloColonna = "";

    /**
     * larghezza della colonna in pixel
     */
    private int larghezzaColonna = 0;

    /**
     * tooltip del voce della colonna
     */
    private String toolTipText = "";

    /**
     * larghezza fissa o variabile della colonna
     */
    private boolean isRidimensionabile = false;

    /**
     * larghezza minima della colonna nella lista
     */
    private int larghezzaMinima = 0;


    /**
     * flag - presente nella Vista di default
     * (regolato alla creazione della vista default in Modello)
     */
    private boolean isPresenteVistaDefault = false;

    /**
     * flag - presente e visibile nella Vista di default
     * (regolato alla creazione della vista default in Modello)
     */
    private boolean isVisibileVistaDefault = false;

    /**
     * flag - mostra nella lista il campo originale
     * (significativo solo per i campi linkati)
     */
    private boolean isVisibileOriginale = false;

    /**
     * flag - mostra nella lista gli eventuali campi espansi da questo campo
     * (significativo solo per i campi linkati)
     */
    private boolean isVisibileEspansione = false;

    /**
     * flag - colonna della lista modificabile (come edit)
     */
    private boolean isModificabile = false;

    /**
     * riferimento al campo che contiene i valori elenco (combobox o radiogruppo)
     */
    private Campo campoValori = null;

    /**
     * flag - se il campo è totalizzabile nelle liste
     */
    private boolean totalizzabile = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CampoListaBase() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CampoListaBase(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** crea una istanza vuota */
//        this.setOperatoreOrdinePrivato(Operatore.ASCENDENTE);
        this.setVisibileOriginale(false);
        this.setVisibileEspansione(true);
        this.setLarghezzaMinima(LARGHEZZA_MINIMA_DEFAULT);
        this.setOrdinePrivato(new Ordine());
        this.setOrdinePubblico(new Ordine());
        this.addOrdinePrivato(this.getCampoParente());

    } /* fine del metodo inizia */


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;

        try { // prova ad eseguire il codice

            /* invoca il metodo (quasi) sovrascritto della superclasse */
            super.inizializzaCampoAstratto();

            /*
             * se non e' stato specificato un ordine privato,
             * aggiunge se stesso come primo ordine privato
             */
            if ((this.getOrdinePrivato().getSize()) == 0) {
                this.getOrdinePrivato().add(this.unCampoParente);
            } /* fine del blocco if */

            /*
             * se non e' stato specificato un ordine pubblico,
             * imposta l'ordine pubblico uguale all'ordine privato
             */
            if ((this.getOrdinePubblico().getSize()) == 0) {
                this.setOrdinePubblico(this.getOrdinePrivato());
            } /* fine del blocco if */

            /** regola il voce della colonna - se non e' stato esplicitamente
             * inserito dal cliente, usa il nome interno del campo */
            if (Lib.Testo.isVuota(titoloColonna)) {
                this.setTitoloColonna(unCampoParente.getNomeInterno());
            } /* fine del blocco if */

            /** regola il tooltip del voce - se non e' stato esplicitamente
             * inserito dal cliente, usa il voce della colonna */
            if (Lib.Testo.isVuota(toolTipText)) {
                this.setTestoTooltip(titoloColonna);
            } /* fine del blocco if */

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

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
     * Aggiunge un Ordine all'elenco dei campi Ordine
     *
     * @param unCampo sul quale basare l'ordine
     */
    public void addOrdinePrivato(Campo unCampo) {
        /** variabili e costanti locali di lavoro */
        Ordine unOrdine;
        unOrdine = this.getOrdinePrivato();
        unOrdine.add(unCampo);
    } /* fine del metodo */


    /**
     * Aggiunge un Ordine privato all'elenco dei campi Ordine
     *
     * @param unCampo sul quale basare l'ordine
     * @param unOperatore l'operatore di ordinamento
     */
    public void addOrdinePrivato(Campo unCampo, String unOperatore) {
        /** variabili e costanti locali di lavoro */
        Ordine unOrdine;
        unOrdine = this.getOrdinePrivato();
        unOrdine.add(unCampo, unOperatore);
    } /* fine del metodo */


    /**
     * Aggiunge un Ordine Pubblico all'elenco dei campi Ordine Pubblico
     *
     * @param unCampo sul quale basare l'ordine
     */
    public void addOrdinePubblico(Campo unCampo) {
        /** variabili e costanti locali di lavoro */
        Ordine unOrdine = null;
        unOrdine = this.getOrdinePubblico();
        unOrdine.add(unCampo);
    } /* fine del metodo */


    /**
     * Pulisce l'ordine
     */
    public void resetOrdine() {
        ordinePrivato.reset();
    } /* fine del metodo */


    /**
     * Pulisce l'ordine pubblico
     */
    public void resetOrdinePubblico() {
        ordinePubblico.reset();
    } /* fine del metodo */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setTitoloColonna(String unTitoloColonna) {
        this.titoloColonna = unTitoloColonna;
    } /* fine del metodo setter */


    /**
     * Regola la larghezza preferita del campo in una lista.
     * <p/>
     * Se e' inferiore alla larghezza minima, la larghezza
     * minima viene automaticamente ridotta pari alla
     * larghezza preferita.<br>
     *
     * @param lpref la larghezza preferita
     */
    public void setLarghezzaColonna(int lpref) {
        this.larghezzaColonna = lpref;
        if (this.getLarghezzaMinima() > this.larghezzaColonna) {
            this.setLarghezzaMinima(this.larghezzaColonna);
        }// fine del blocco if

    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setTestoTooltip(String toolTipText) {
        this.toolTipText = toolTipText;
    } /* fine del metodo setter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setRidimensionabile(boolean isRidimensionabile) {
        this.isRidimensionabile = isRidimensionabile;
    } /* fine del metodo setter */


    /**
     * Regola l'ordine privato del campo.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param ordinePrivato l'ordine da assegnare al campo
     */
    public void setOrdinePrivato(Ordine ordinePrivato) {
        this.ordinePrivato = ordinePrivato;
    } /* fine del metodo getter */


    /**
     * Regola l'ordine privato del campo.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public void setOrdinePrivato(Campo campo) {
        this.resetOrdine();
        this.addOrdinePrivato(campo);
    } /* fine del metodo getter */


    /**
     * Regola l'ordine pubblico del campo.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param ordine l'ordine da assegnare al campo
     */
    public void setOrdinePubblico(Ordine ordine) {
        this.ordinePubblico = ordine;
    } /* fine del metodo getter */


    /**
     * Regola l'ordine pubblico del campo.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public void setOrdinePubblico(Campo campo) {
        this.resetOrdinePubblico();
        this.addOrdinePubblico(campo);
    } /* fine del metodo getter */


    /**
     * Regola gli ordini pubblico e privato del campo.
     * <p/>
     *
     * @param ordine l'ordine pubblico e privato da assegnare al campo
     */
    public void setOrdine(Ordine ordine) {
        this.setOrdinePrivato(ordine);
        this.setOrdinePubblico(ordine);
    } /* fine del metodo getter */


    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public String getTitoloColonna() {
        return this.titoloColonna;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public int getLarghezzaColonna() {
        return this.larghezzaColonna;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public String getToolTipText() {
        return this.toolTipText;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public boolean isRidimensionabile() {
        return this.isRidimensionabile;
    } /* fine del metodo getter */


    public int getLarghezzaMinima() {
        return larghezzaMinima;
    }


    /**
     * Regola la larghezza minima del campo in una lista.
     * <p/>
     * Significativo solo se il campo e' ridimensionabile.<p>
     * Se supera la larghezza preferita, viene automaticamente
     * ridotta pari alla larghezza preferita.<br>
     *
     * @param lmin la larghezza minima
     */
    public void setLarghezzaMinima(int lmin) {
        if (lmin <= this.getLarghezzaColonna()) {
            this.larghezzaMinima = lmin;
        } else {
            this.larghezzaMinima = this.getLarghezzaColonna();
        }// fine del blocco if-else
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public boolean isPresenteVistaDefault() {
        return this.isPresenteVistaDefault;
    } /* fine del metodo getter */


    /**
     * metodo setter per modificare il valore della variabile privata
     */
    public void setPresenteVistaDefault(boolean isColonnaVisibile) {
        this.isPresenteVistaDefault = isColonnaVisibile;
    } /* fine del metodo setter */


    public boolean isVisibileVistaDefault() {
        return isVisibileVistaDefault;
    }


    /**
     * regola il flag visibile nella lista di default
     * se il flag viene acceso, accende anche il flag isPresenteVistaDefault
     * (se e' visibile deve necessariamente essere presente)
     */
    public void setVisibileVistaDefault(boolean visibileVistaDefault) {
        isVisibileVistaDefault = visibileVistaDefault;
        if (this.isVisibileVistaDefault) {
            this.setPresenteVistaDefault(true);
        }// fine del blocco if
    }


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Ordine getOrdinePrivato() {
        return this.ordinePrivato;
    } /* fine del metodo getter */


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public Ordine getOrdinePubblico() {
        return this.ordinePubblico;
    } /* fine del metodo getter */


    public boolean isVisibileOriginale() {
        return isVisibileOriginale;
    }


    public void setVisibileOriginale(boolean visibileOriginale) {
        isVisibileOriginale = visibileOriginale;
    }


    public boolean isVisibileEspansione() {
        return isVisibileEspansione;
    }


    public void setVisibileEspansione(boolean visibileEspansione) {
        isVisibileEspansione = visibileEspansione;
    }


    public boolean isModificabile() {
        return isModificabile;
    }


    public void setModificabile(boolean modificabile) {
        isModificabile = modificabile;
    }


    public Campo getCampoValori() {
        return campoValori;
    }


    public void setCampoValori(Campo campoValori) {
        this.campoValori = campoValori;
    }


    /**
     * Controlla se il campo è totalizzabile nelle liste
     * <p/>
     *
     * @return true se totalizzabile
     */
    public boolean isTotalizzabile() {
        return totalizzabile;
    }


    /**
     * Indica se il campo è totalizzabile nelle liste
     * <p/>
     *
     * @param flag di controllo
     */
    public void setTotalizzabile(boolean flag) {
        this.totalizzabile = flag;
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
    public CampoLista clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CampoLista unCampo;
        Ordine ordine;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (CampoLista)super.clone();
        } catch (CloneNotSupportedException unErrore) { // intercetta l'errore
            throw new InternalError();
        }// fine del blocco try-catch

        try { // prova ad eseguire il codice
            /* modifica il riferimento al campo parente */
            unCampo.setCampoParente(unCampoParente);

            /* clona l'oggetto ordine */
            ordine = unCampo.getOrdinePrivato();
            unCampo.setOrdinePrivato(ordine.clona());

            /* clona l'oggetto ordine pubblico */
            ordine = unCampo.getOrdinePubblico();
            unCampo.setOrdinePubblico(ordine.clona());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unCampo;
    }


}// fine della classe
