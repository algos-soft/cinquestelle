/**
 * Title:        CSBase.java
 * Package:
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 20.44
 */

package it.algos.base.campo.scheda;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoAstratto;
import it.algos.base.errore.Errore;


/**
 * Questa classe astratta e' responsabile di: <br>
 * A - Regola le funzionalita di gestione di una campo a video nella Scheda <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 20.44
 */
public abstract class CSBase extends CampoAstratto implements Cloneable, CampoScheda {


    /*
     * Flag - se usa il bordo
     */
    private boolean bordato = false;

    /*
     * Flag - se usa il voce nel bordo
     */
    private boolean bordoTitolato = false;

    /*
     * Eventuale voce nel bordo
     */
    private String titolo = "";

    /**
     * larghezza del/i componente/i interno/i
     */
    private int larghezzaComponenti = 0;

    /**
     * altezza del/i componente/i interno/i
     */
    private int altezzaComponenti = 0;

    /**
     * posizione della eventuale etichetta
     */
    private int posEtichetta = 0;


    /**
     * flag - true per includere il campo nella scheda
     */
    protected boolean isPresenteInScheda = false;


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CSBase() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CSBase(Campo unCampoParente) {
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


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        /* crea il testo di default per l'etichetta (il nome interno del campo)*/
//        this.setTesto(this.getCampoParente().getNomeInternoCampo());

        /* larghezza di default per un campo in scheda */
        this.setLarghezzaComponenti(100);

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
        /** invoca il metodo (quasi) sovrascritto della superclasse */
        super.avviaCampoAstratto();
    } /* fine del metodo */


    /**
     * Altezza dei componenti interni al PannelloComponenti
     */
    public void setAltezzaComponenti(int altezzaComponenti) {
        this.altezzaComponenti = altezzaComponenti;
    } /* fine del metodo setter */


    /**
     * Larghezza dei componenti interni al PannelloComponenti
     */
    public void setLarghezzaComponenti(int larghezzaComponenti) {
        this.larghezzaComponenti = larghezzaComponenti;
    } /* fine del metodo setter */


    /**
     * flag - true per includere il campo nella scheda
     */
    public void setPresenteScheda(boolean isPresenteInScheda) {
        this.isPresenteInScheda = isPresenteInScheda;
    } /* fine del metodo */


    /**
     * Altezza dei componenti interni al PannelloComponenti
     */
    public int getAltezzaComponenti() {
        return this.altezzaComponenti;
    } /* fine del metodo getter */


    /**
     * Larghezza dei componenti interni al PannelloComponenti
     */
    public int getLarghezzaComponenti() {
        return this.larghezzaComponenti;
    } /* fine del metodo getter */


    /**
     * flag - true per includere il campo nella scheda
     */
    public boolean isPresenteInScheda() {
        return this.isPresenteInScheda;
    } /* fine del metodo getter */


    /*
    * Flag - se usa il bordo
    */
    public boolean isBordato() {
        return bordato;
    }


    /*
     * Flag - se usa il bordo
     */
    public void setBordato(boolean bordato) {
        this.bordato = bordato;
    }


    /*
     * Flag - se usa il voce nel bordo
     */
    public boolean isBordoTitolato() {
        return bordoTitolato;
    }


    /*
     * Flag - se usa il voce nel bordo
     */
    public void setBordoTitolato(boolean bordoTitolato) {
        this.bordoTitolato = bordoTitolato;
    }


    /*
     * Eventuale voce nel bordo
     */
    public String getTitolo() {
        return titolo;
    }


    /*
     * Eventuale voce nel bordo
     */
    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }


    public int getPosEtichetta() {
        return posEtichetta;
    }


    public void setPosEtichetta(int posEtichetta) {
        this.posEtichetta = posEtichetta;
    }

//    /**
//     * Campo obbligatorio per registrazione / conferma
//     */
//    public boolean isObbligatorio() {
//        return obbligatorio;
//    }
//
//
//    /**
//     * Campo obbligatorio per registrazione/conferma scheda/dialogo.
//     * <p/>
//     *
//     * @param obbligatorio
//     */
//    public void setObbligatorio(boolean obbligatorio) {
//        this.obbligatorio = obbligatorio;
//    }


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
    public CampoScheda clonaCampo(Campo unCampoParente) {
        /* variabili e costanti locali di lavoro */
        CampoScheda unCampo;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse Object */
            unCampo = (CampoScheda)super.clone();
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


