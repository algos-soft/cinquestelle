/**
 * Title:     Gestione
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      14-feb-2004
 */
package it.algos.gestione;

import it.algos.base.costante.CostanteModello;
import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;
import it.algos.base.pref.PrefTipo;

import java.util.ArrayList;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA INTERFACCIA .
 * </p>
 * Questa interfaccia: <ul>
 * <li>
 * <li>
 * </ul>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 14-feb-2004 ore 8.01.59
 */
public interface Gestione {

    /**
     * Percorso completo della cartella che contiene il programma
     */
    public static final String PATH_PROGETTO = CostanteModulo.PATH_PROGRAMMI + "gestione/";

    /**
     * codifica delle viste (espanse e clonate) disponibili all'esterno del modulo
     */
    public static final String VISTA_COMPLETA = CostanteModulo.VISTA_BASE_DEFAULT;

    /**
     * codifica dei set di campi per la scheda disponibili all'esterno del modulo
     */
    public static final String SET_COMPLETO = CostanteModulo.SET_BASE_DEFAULT;

    /**
     * codifica dei nomi dei campi disponibili all'esterno del modulo
     */
    public static final String CAMPO_SIGLA = CostanteModello.NOME_CAMPO_SIGLA;

    public static final String CAMPO_DESCRIZIONE = CostanteModello.NOME_CAMPO_DESCRIZIONE;

    /**
     * Nomi dei varii moduli del programma
     * interni e come appaiono nel menu utente
     */
    public static final String MODULO_ANAGRAFICA = "Anagrafica";

    public static final String MODULO_INDIRIZZO = "Indirizzo";

    public static final String MODULO_CONTATTO = "Contatto";

    public static final String MODULO_VALUTA = "Valute";


    /**
     * Classe interna Enumerazione.
     */
    public enum ModFatt implements PrefTipo {

        privato("privato", false, false),
        professionista("professionista", true, false),
        azienda("azienda", false, false);

        /**
         * voce da utilizzare
         */
        private String titolo;

        /**
         * utilizzo ritenuta d'acconto
         */
        private boolean usaRa;

        /**
         * utilizzo rivalsa inps
         */
        private boolean usaRi;


        /**
         * Costruttore completo con parametri.
         *
         * @param titolo utilizzato nei popup
         * @param ra utilizzo ritenuta d'acconto
         * @param ri utilizzo rivalsa inps
         */
        ModFatt(String titolo, boolean ra, boolean ri) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setTitolo(titolo);
                this.setUsaRa(ra);
                this.setUsaRi(ri);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Restituisce una lista di tutti gli oggetti della Enumeration.
         *
         * @return arrayList di stringhe
         */
        public static ArrayList<PrefTipo> getLista() {
            /* variabili e costanti locali di lavoro */
            ArrayList<PrefTipo> lista = null;

            try { // prova ad eseguire il codice
                lista = new ArrayList<PrefTipo>();

                /* traverso tutta la collezione */
                for (ModFatt tipo : ModFatt.values()) {
                    lista.add(tipo);
                } // fine del ciclo for-each

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return lista;
        }


        public Object getValore() {
            return null;
        }


        public String getTitolo() {
            return titolo;
        }


        private void setTitolo(String titolo) {
            this.titolo = titolo;
        }


        public boolean isUsaRa() {
            return usaRa;
        }


        public void setUsaRa(boolean usaRa) {
            this.usaRa = usaRa;
        }


        public boolean isUsaRi() {
            return usaRi;
        }


        public void setUsaRi(boolean usaRi) {
            this.usaRi = usaRi;
        }
    }// fine della classe

}// fine della interfaccia
