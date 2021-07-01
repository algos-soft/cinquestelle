/**
 * Title:        CampoLista.java
 * Package:      prove.nuovocampo
 * Description:
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 3 luglio 2003 alle 15.11
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
//  Package di questa interfaccia e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.campo.lista;

import it.algos.base.campo.base.Campo;
import it.algos.base.query.ordine.Ordine;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa interfaccia e' responsabile di: <br>
 * A - Regola la gestione degli attributi di una colonna a video nella Lista <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  3 luglio 2003 ore 15.11
 */
public interface CampoLista {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
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
     * metodo setter per modificare il valore della variabile privata
     */
    public abstract void setTitoloColonna(String unTitoloColonna);


    /**
     * Regola la larghezza preferita del campo in una lista.
     * <p/>
     * Se e' inferiore alla larghezza minima, la larghezza
     * minima viene automaticamente ridotta pari alla
     * larghezza preferita.<br>
     *
     * @param lpref la larghezza preferita
     */
    public abstract void setLarghezzaColonna(int lpref);


    /**
     * Regola la larghezza minima del campo in una lista.
     * <p/>
     * Significativo solo se il campo e' ridimensionabile.<p>
     * Se supera la larghezza preferita, viene automaticamente
     * ridotta pari alla larghezza preferita.<br>
     *
     * @param lmin la larghezza minima
     */
    public abstract void setLarghezzaMinima(int lmin);


    public abstract void setTestoTooltip(String toolTipText);


    /**
     * Regola la ridimensionabilita' del campo in una lista.
     * <p/>
     *
     * @param isRidimensionabile true per rendere il campo ridimensionabile,
     * false per renderlo fisso
     */
    public abstract void setRidimensionabile(boolean isRidimensionabile);


    /**
     * Regola l'ordine privato del campo.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param ordine l'ordine da assegnare al campo
     */
    public abstract void setOrdinePrivato(Ordine ordine);


    /**
     * Regola l'ordine privato del campo.
     * <p/>
     * E' l'ordine che viene visto dall'interno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public abstract void setOrdinePrivato(Campo campo);


    /**
     * Regola l'ordine pubblico del campo.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param ordine l'ordine da assegnare al campo
     */
    public abstract void setOrdinePubblico(Ordine ordine);


    /**
     * Regola l'ordine pubblico del campo.
     * <p/>
     * E' l'ordine che viene visto dall'esterno del modulo<br>
     *
     * @param campo il campo sul quale basare l'ordine
     */
    public abstract void setOrdinePubblico(Campo campo);


    /**
     * Regola gli ordini pubblico e privato del campo.
     * <p/>
     *
     * @param ordine l'ordine pubblico e privato da assegnare al campo
     */
    public abstract void setOrdine(Ordine ordine);


    /**
     * Restituisce l'ordine privato del campo.
     * <p/>
     *
     * @return l'ordine privato del campo
     */
    public abstract Ordine getOrdinePrivato();


    /**
     * Restituisce l'ordine pubblico del campo.
     * <p/>
     *
     * @return l'ordine pubblico del campo
     */
    public abstract Ordine getOrdinePubblico();


    /**
     * Aggiunge un ordine privato al campo.
     * <p/>
     *
     * @param unCampo il campo da aggiungere all'ordine esistente
     */
    public abstract void addOrdinePrivato(Campo unCampo);


    /**
     * Aggiunge un ordine privato con operatore al campo.
     * <p/>
     *
     * @param unCampo il campo da aggiungere all'ordine esistente
     * @param unOperatore l'operatore di ordinamento da utilizzare
     *
     * @see it.algos.base.database.util.Operatore
     */
    public abstract void addOrdinePrivato(Campo unCampo, String unOperatore);


    /**
     * Aggiunge un campo all' ordine pubblico.
     * <p/>
     *
     * @param unCampo il campo da aggiungere
     */
    public abstract void addOrdinePubblico(Campo unCampo);


    /**
     * /**
     * Pulisce l'ordine privato.
     * <p/>
     */
    public abstract void resetOrdine();


    /**
     * Pulisce l'ordine pubblico.
     * <p/>
     */
    public abstract void resetOrdinePubblico();


    /**
     * metodo getter per ottenere il valore della variabile privata
     */
    public abstract String getTitoloColonna();


    public abstract int getLarghezzaColonna();


    public abstract String getToolTipText();


    public abstract boolean isRidimensionabile();

//    public abstract boolean isInizializzato();


    /**
     * riferimento al campo 'contenitore' dei vari oggetti che
     * insieme svolgono le funzioni del campo
     */
    public abstract void setCampoParente(Campo unCampoParente);


    public abstract Campo getCampoParente();


    public abstract boolean isPresenteVistaDefault();


    public abstract void setPresenteVistaDefault(boolean isVisibile);


    public abstract boolean isVisibileVistaDefault();


    public abstract void setVisibileVistaDefault(boolean visibileVistaDefault);


    public abstract boolean isVisibileOriginale();


    public abstract void setVisibileOriginale(boolean visibileOriginale);


    public abstract boolean isVisibileEspansione();


    public abstract void setVisibileEspansione(boolean visibileEspansione);


    public abstract boolean isModificabile();


    public abstract void setModificabile(boolean modificabile);


    public abstract int getLarghezzaMinima();


    public abstract Campo getCampoValori();


    public abstract void setCampoValori(Campo campoValori);


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
     * Ritorna una copia profonda dell'oggetto (deep copy) col casting
     */
    public abstract CampoLista clonaCampo(Campo unCampoParente);

//-------------------------------------------------------------------------
}// fine della interfaccia prove.nuovocampo.CampoLista.java

//-----------------------------------------------------------------------------

