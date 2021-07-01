/**
 * Title:     Proiezione
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-set-2006
 */
package it.algos.base.proiezione;

import it.algos.base.errore.Errore;
import it.algos.base.query.filtro.Filtro;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 10-ago-2005
 * Time: 15.29.09
 */
public interface Proiezione {

    /**
     * Presenta il dialogo di proiezione.
     * </p>
     */
    public void presentaDialogo();


    /**
     * Ritorna true se il dialogo di proiezione e' stato confermato.
     *
     * @return true se confermato
     */
    public boolean isConfermato();


    /**
     * Recupera il filtro corrispondente alle condizioni di ricerca impostate.
     * <p/>
     *
     * @return il filtro corrispondente alla ricerca
     *         (null se non sono state impostate condizioni di ricerca)
     */
    public abstract Filtro getFiltro();


    /**
     * Ritorna l'opzione di ricerca correntemente selezionata.
     * <p/>
     *
     * @return l'opzione selezionata
     *         se null esegue una ricerca standard (sostituisce il filtro corrente)
     *
     * @see it.algos.base.proiezione.Proiezione.Opzioni
     */
    public Proiezione.Opzioni getOpzioneRicerca();


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica delle opzioni di proiezione
     */
    public enum Opzioni {

        standard("Ricerca standard"),
        aggiungiAllaLista("Aggiungi alla lista"),
        rimuoviDallaLista("Rimuovi dalla lista"),
        cercaNellaLista("Cerca nella lista");

        /**
         * descrizione della opzione.
         */
        private String descrizione;


        /**
         * Costruttore completo con parametri.
         *
         * @param descrizione dell'opzione
         */
        Opzioni(String descrizione) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.setDescrizione(descrizione);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        private String getDescrizione() {
            return descrizione;
        }


        private void setDescrizione(String descrizione) {
            this.descrizione = descrizione;
        }


        /**
         * Ritorna una lista contenente tutti gli elementi della Enumerazione.
         * <p/>
         */
        public static ArrayList<Opzioni> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<Proiezione.Opzioni> lista = null;

            try { // prova ad eseguire il codice

                /* crea la lista */
                lista = new ArrayList<Proiezione.Opzioni>();

                /* spazzola tutta la Enum */
                for (Proiezione.Opzioni elem : Proiezione.Opzioni.values()) {
                    lista.add(elem);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        /**
         * Ritorna la stringa descrittiva dell'opzione.
         * <p/>
         */
        public String toString() {
            return this.getDescrizione();
        }


    }// fine della classe

}
