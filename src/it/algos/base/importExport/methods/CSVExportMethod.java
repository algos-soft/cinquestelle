/**
 * Title:     ExportMethod
 * Copyright: Copyright (c) 2008
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-01-2008
 */
package it.algos.base.importExport.methods;

import it.algos.base.errore.Errore;

import java.util.ArrayList;

/**
 * Metodo di esportazione CSV.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 03-01-2008
 */
public class CSVExportMethod extends TextExportMethod {

    /**
     * Costruttore completo con parametri. <br>
     */
    public CSVExportMethod() {
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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setFieldDelimiter(',');
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Scrive una riga di valori stringa.
     * <p/>
     * Raddoppia gli eventuali apici interni alle stringhe
     * Racchiude le stringhe tra apici
     * Rinvia alla superclasse
     *
     * @param stringhe da scrivere nelle celle
     */
    protected void writeStringRow(ArrayList<String> stringhe) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> stringheOut = new ArrayList<String>();

        try {    // prova ad eseguire il codice

            for (String stringa : stringhe) {
                stringa = enclose(stringa);
                stringheOut.add(stringa);
            }

            super.writeStringRow(stringheOut);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Racchiude una stringa tra doppi apici.
     * <p/>
     * Raddoppia gli apici eventualmente presenti all'interno della stringa.
     *
     * @param input stringa in ingresso
     *
     * @return stringa in uscita
     */
    private String enclose(String input) {
        /* variabili e costanti locali di lavoro */
        String output = "";
        Character encloser;
        CharSequence target;
        CharSequence replacement;

        try {    // prova ad eseguire il codice
            output = input;
            encloser = '"';
            target = "" + encloser;
            replacement = "" + encloser + encloser;
            output = output.replace(target, replacement);
            output = encloser + output + encloser;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return output;
    }


} // fine della classe