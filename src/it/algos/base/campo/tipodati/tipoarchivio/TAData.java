/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-feb-2005
 */
package it.algos.base.campo.tipodati.tipoarchivio;

import it.algos.base.database.tipodati.TipoDati;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.util.Date;

/**
 * Archivio del campo di tipo data (calendario).
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-feb-2005 ore 11.30.35
 */
public final class TAData extends TABase {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private final static TAData ISTANZA = new TAData();


    /**
     * Costruttore completo senza parametri.
     */
    private TAData() {
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

        try { // prova ad eseguire il codice

            this.setClasse(Date.class);
            this.setChiaveTipoDatiDb(TipoDati.TIPO_DATA);
            this.setValoreVuoto(Lib.Data.getVuota());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

//        this.setValoreVuoto(new Date(0));
    }// fine del metodo inizia


    public static TAData getIstanza() {
        return ISTANZA;
    } /* fine del metodo getter */

}// fine della classe
