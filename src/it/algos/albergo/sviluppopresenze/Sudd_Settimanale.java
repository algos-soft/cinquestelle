/**
 * Title:     Suddivisione
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      16-feb-2009
 */
package it.algos.albergo.sviluppopresenze;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.wrapper.DueDate;

import java.util.ArrayList;
import java.util.Date;

/**
 * Tipo di suddivisione Giornaliera
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 16-feb-2009 ore 8.29.49
 */
class Sudd_Settimanale extends SuddivisioneBase {

    /**
     * Costruttore completo.
     * <p/>
     */
    Sudd_Settimanale() {
        super("Settimanale", "Settimana", true);
    }// fine del metodo costruttore completo


    /**
     * Crea una lista di elementi suddivisi per questo tipo di suddivisione.
     * <p/>
     *
     * @param estremita estremità della suddivisione - oggetto DueDate obbligatorio
     *
     * @return un array degli elementi suddivisi, ogni elemento con [chiave - sigla - descrizione]
     */
    public WrapSuddivisione[] creaSuddivisione(Object estremita) {
        /* variabili e costanti locali di lavoro */
        WrapSuddivisione[] matrice = new WrapSuddivisione[0];
        ArrayList<WrapSuddivisione> lista = new ArrayList<WrapSuddivisione>();
        WrapSuddivisione wrapper;
        Date data1 = null;
        Date data2 = null;
        boolean continua;

        try {    // prova ad eseguire il codice

            /* recupera le date estreme dal parametro */
            continua = false;
            if ((estremita != null) & (estremita instanceof DueDate)) {
                DueDate date = (DueDate)estremita;
                if (date.isPieno()) {
                    if (date.isSequenza()) {
                        data1 = date.getData1();
                        data2 = date.getData2();
                        continua = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {

                /**
                 * crea la lista
                 * spazzola tutti i giorni e al cambio di numero
                 * di settimana aggiunge un elemento
                 * */
                Date data = data1;
                int numset;
                int old_numset = -1;
                while (Lib.Data.isPrecedenteUguale(data2, data)) {

                    numset = Lib.Data.getNumeroSettimanaInAnno(data);
                    if (numset != old_numset) {
                        int chiave = this.getChiaveGiorno(data);
                        Date d1, d2;
                        String sd1, sd2;
                        d1 = Lib.Data.getPrimoGiornoSettimana(numset, Lib.Data.getAnno(data));
                        d2 = Lib.Data.add(d1, 6);
                        sd1 = Lib.Data.getStringa(d1);
                        sd2 = Lib.Data.getStringa(d2);
                        String sigla = ""+numset;
                        String desc = sd1 + " - " + sd2;
                        wrapper = new WrapSuddivisione(chiave, sigla, desc);
                        lista.add(wrapper);
                        old_numset = numset;
                    }// fine del blocco if

                    data = Lib.Data.add(data, 1);
                }// fine del blocco while

                /* riempie l'array */
                matrice = new WrapSuddivisione[lista.size()];
                for (int k = 0; k < lista.size(); k++) {
                    matrice[k] = lista.get(k);
                } // fine del ciclo for

            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return matrice;

    }


    /**
     * Recupera la chiave di suddivisione relativa a un periodo.
     * <p/>
     *
     * @param codPeriodo il codice del periodo
     *
     * @return la chiave di suddivisione
     */
    public int getChiavePeriodo(int codPeriodo) {
        return 0;
    }


    /**
     * Recupera la chiave di suddivisione nella quale un dato giorno rientra
     * <p/>
     * Significativo solo per suddivisioni di tipo temporale
     *
     * @param data la data da analizzare
     *
     * @return la chiave di mappa
     */
    public int getChiaveGiorno(Date data) {
        /* variabili e costanti locali di lavoro */
        int chiave = 0;
        int numAnno;
        int numSett;

        try { // prova ad eseguire il codice
            numAnno = Lib.Data.getAnno(data);
            numSett = Lib.Data.getNumeroSettimanaInAnno(data);
            chiave = (numAnno * 100) + numSett;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return chiave;
    }


}// fine della classe