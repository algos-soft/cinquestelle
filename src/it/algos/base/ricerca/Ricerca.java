package it.algos.base.ricerca;

import it.algos.base.errore.Errore;
import it.algos.base.query.filtro.Filtro;

import java.util.ArrayList;

public interface Ricerca {


    /**
     * Avvia la ricerca.
     * <p>
     * Resetta i campi e rende il dialogo visibile
     */
    public void avvia();


    /**
     * Ritorna true se il dialogo di ricerca e' stato confermato.
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
     * @see it.algos.base.ricerca.Ricerca.Opzioni
     */
    public Ricerca.Opzioni getOpzioneRicerca();

    /**
     * Ritorna l'istanza della classe base.
     * <p/>
     *
     * @return l'istanza della classe base
     */
    public RicercaBase getRicercaBase();


    /**
     * Ritorna un testo esplicativo delle condizioni di ricerca impostate.
     * <p/>
     *
     * @return il testo esplicativo
     */
    public String getTestoRicerca();

    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica delle opzioni di ricerca
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
            ArrayList<Opzioni> lista = null;

            try { // prova ad eseguire il codice

                /* crea la lista */
                lista = new ArrayList<Opzioni>();

                /* spazzola tutta la Enum */
                for (Opzioni elem : values()) {
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
