/**
 * Title:     EFData
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      22.04.2006
 */
package it.algos.base.campo.formatter.edit;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.progetto.Progetto;

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
public class EFData extends AllowBlankMaskFormatter {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public EFData() {

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
            this.setMask("##-##-####");
            this.setPlaceholderCharacter('_');
            this.setAllowBlankField(true);
            setCommitsOnValidEdit(true);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Sovrascrive stringToValue per interpretare le date.
     * <p/>
     * Interpreta la stringa ed estrae la data
     * Se non e' valida passa alla superclasse la rappresentazione vuota
     * Se e' valida passa alla superclasse la stringa valida
     */
    @Override
    public Object stringToValue(String stringaIn) throws ParseException {
        /* variabili e costanti locali di lavoro */
        Date data;
        Date dataVuota;
        Object result = stringaIn;
        String stringa;

        try { // prova ad eseguire il codice

            dataVuota = Lib.Data.getVuota();
            data = Libreria.getDate(stringaIn);
            if (data.equals(dataVuota)) {
                stringa = super.getBlankRepresentation();
            } else {
                stringa = Progetto.getDateFormat().format(data);
            }// fine del blocco if-else
            result = super.stringToValue(stringa);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return result;
    }


}// fine della classe
