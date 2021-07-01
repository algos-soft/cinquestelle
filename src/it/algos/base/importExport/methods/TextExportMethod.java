/**
 * Title:     ExportMethod
 * Copyright: Copyright (c) 2008
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      03-01-2008
 */
package it.algos.base.importExport.methods;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Metodo di esportazione testo generico.
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 03-01-2008
 */
public abstract class TextExportMethod extends ExportMethod {

    /**
     * carattere delimitatore di campo
     */
    private char fieldDelimiter;

    /**
     * carattere delimitatore di record
     */
    private char recordDelimiter;


    /**
     * Costruttore completo con parametri. <br>
     */
    public TextExportMethod() {
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

            this.setFieldDelimiter('\t');
            this.setRecordDelimiter('\n');

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Scrive i titoli delle colonne.
     * <p/>
     *
     * @param titoli delle colonne
     */
    public void writeTitles(ArrayList<String> titoli) {
        this.writeStringRow(titoli);
    }


    /**
     * Scrive una riga di dati.
     * <p/>
     *
     * @param valori da scrivere nelle celle
     */
    public void writeRow(ArrayList<Object> valori) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> stringhe = new ArrayList<String>();
        String stringa;

        try {    // prova ad eseguire il codice

            /* crea un array di stringhe dai valori */
            for (Object valore : valori) {
                stringa = Lib.Testo.getStringa(valore);
                stringhe.add(stringa);
            }

            /* scrive la riga */
            this.writeStringRow(stringhe);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Scrive una riga di valori stringa.
     * <p/>
     *
     * @param stringhe da scrivere nelle celle
     */
    protected void writeStringRow(ArrayList<String> stringhe) {
        /* variabili e costanti locali di lavoro */
        char fldDelim;
        char recDelim;
        String stringa;
        Campo campo;
        FileOutputStream stream;

        try {    // prova ad eseguire il codice

            fldDelim = this.getFieldDelimiter();
            recDelim = this.getRecordDelimiter();
            stream = this.getStream();

            for (int k = 0; k < stringhe.size(); k++) {

                stringa = stringhe.get(k);

                /* se si tratta di un campo TestoArea converte
                 * eventuali return in spazi */
                campo = this.getCampo(k);
                if (campo != null) {
                    if (campo.isTestoArea()) {
                        stringa = Lib.Testo.replaceAll(stringa, "\n", " ");
                    }// fine del blocco if
                }// fine del blocco if

                stream.write(stringa.getBytes());
                if (k < stringhe.size() - 1) {
                    stream.write(fldDelim);
                }// fine del blocco if

            } // fine del ciclo for

            stream.write(recDelim);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    private char getFieldDelimiter() {
        return fieldDelimiter;
    }


    protected void setFieldDelimiter(char fieldDelimiter) {
        this.fieldDelimiter = fieldDelimiter;
    }


    private char getRecordDelimiter() {
        return recordDelimiter;
    }


    private void setRecordDelimiter(char recordDelimiter) {
        this.recordDelimiter = recordDelimiter;
    }

} // fine della classe