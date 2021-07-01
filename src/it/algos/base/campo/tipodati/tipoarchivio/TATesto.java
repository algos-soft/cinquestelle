/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-feb-2005
 */
package it.algos.base.campo.tipodati.tipoarchivio;

import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;

/**
 * Archivio del campo di tipo testo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-feb-2005 ore 11.30.35
 */
public final class TATesto extends TABase {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TATesto ISTANZA = new TATesto();


    /**
     * Costruttore completo senza parametri.
     */
    private TATesto() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setClasse(String.class);
        super.setChiaveTipoDatiDb(TipoDati.TIPO_TESTO);
        super.setValoreVuoto("");
    }// fine del metodo inizia


    public static TATesto getIstanza() {
        return ISTANZA;
    } /* fine del metodo getter */

}// fine della classe
