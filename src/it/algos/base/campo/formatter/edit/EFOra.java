/**
 * Title:     EFData
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22.04.2006
 */
package it.algos.base.campo.formatter.edit;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;

import java.text.ParseException;
import java.util.Date;

/**
 * Formattatore generico per l'editing di una data.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 13-set-2005 ore 16.15.57
 */
public class EFOra extends AllowBlankMaskFormatter {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public EFOra() {

        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo costruttore base */


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            this.setMask("##:##");
            this.setPlaceholderCharacter('_');
            this.setAllowBlankField(true);
            setCommitsOnValidEdit(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Sovrascrive stringToValue per interpretare l'ora.
     * <p/>
     * Interpreta la stringa ed estrae l'ora
     * Se non e' valida passa alla superclasse la rappresentazione vuota
     * Se e' valida passa alla superclasse la stringa valida
     */
    @Override
    public Object stringToValue(String stringaIn) throws ParseException {
        /* variabili e costanti locali di lavoro */
        Date dataVuota;
        Object result = stringaIn;
        String txtOra;
        String txtMin;
        int ora;
        int minuti;




//        try { // prova ad eseguire il codice
//            txtOra = stringaIn.substring(0, 3);
//            txtMin = stringaIn.substring(3, 5);
//            ora = Libreria.getInt(txtOra);
//            minuti = Libreria.getInt(txtMin);
//
//            ora *= (60 * 60);
//            minuti *= 60;
//
//            result = ora + minuti;
//
////            result = Libreria.getInt(stringaIn);
//
//        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
//        }// fine del blocco try-catch

        /* valore di ritorno */
        return super.stringToValue(stringaIn);
    }

}// fine della classe
