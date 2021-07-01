/**
 * Title:     InfoColonna
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      15-gen-2005
 */
package it.algos.base.database.dati;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;

/**
 * Contenitore di informazioni su una colonna di dati.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 15-gen-2005 ore 17.37.47
 */
public final class InfoColonna extends Object {

    /**
     * posizione di questa colonna (0 per la prima)
     */
    private int posizione = 0;

    /**
     * oggetto campo referenziato da questa colonna
     */
    private Campo campo = null;

    /**
     * tipo di dati di questa colonna
     */
    private TipoDati tipoDati = null;


    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public InfoColonna() {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }// fine del metodo inizia


    public int getPosizione() {
        return posizione;
    }


    public void setPosizione(int posizione) {
        this.posizione = posizione;
    }


    public Campo getCampo() {
        return campo;
    }


    public void setCampo(Campo campo) {
        this.campo = campo;
    }


    public TipoDati getTipoDati() {
        return tipoDati;
    }


    public void setTipoDati(TipoDati tipoDati) {
        this.tipoDati = tipoDati;
    }

}// fine della classe
