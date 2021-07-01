/**
 * Title:     DialogoStampaLista
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      4-set-2009
 */
package it.algos.albergo.clientealbergo;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;

/**
 * Dialogo di selezione del tipo di stampa della lista Clienti corrente
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 4-set-2009 ore 8.57.49
 */
public final class DialogoStampaLista extends DialogoAnnullaConferma {

    /* nome interno e titolo del campo di selezione del tipo */
    private static final String NOME_CAMPO_OPZIONI="tipo di stampa";

    /**
     * Costruttore completo senza parametri.
     */
    public DialogoStampaLista() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            this.setTitolo("Scelta stampa");

            Campo campo = CampoFactory.radioInterno(NOME_CAMPO_OPZIONI);
            campo.setValoriInterni(TipiStampaListaClienti.values());
            campo.setLarScheda(300);
            this.addCampo(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Ritorna il tipo di stampa selezionato.
     * <p/>
     *
     * @return il tipo di stampa, dalla Enum TipiStampaListaClienti
     */
    public TipiStampaListaClienti getTipoSelezionato() {
        /* variabili e costanti locali di lavoro */
        TipiStampaListaClienti tipo = null;

        try {    // prova ad eseguire il codice
            Campo campo = this.getCampo(NOME_CAMPO_OPZIONI);
            Object valore = campo.getValoreElenco();
            if ((valore!=null) && (valore instanceof TipiStampaListaClienti)) {
                tipo = (TipiStampaListaClienti)valore;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    @Override
    public boolean isConfermabile() {
        return (this.getTipoSelezionato()!=null);
    }


    /**
     * Enumerazione dei possibili tipi di stampa della lista clienti.
     * <p/>
     */
    public enum TipiStampaListaClienti implements Campo.ElementiCombo {

        standard(1,"Stampa standard (come visualizzato)"),
        mailing(2,"Stampa per mailing"),
        export_mailing(3,"Export per mailing");


        int codice;
        String descrizione;

        /**
         * Costruttore completo con parametri.
         *
         * @param codice dell'elemento
         * @param descrizione dell'elemento
         */
        TipiStampaListaClienti(int codice, String descrizione) {
            try { // prova ad eseguire il codice

                /* regola le variabili di istanza coi parametri */
                this.codice=codice;
                this.descrizione=descrizione;

            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Ritorna il codice dell'elemento.
         * <p/>
         * @return il codice
         */
        public int getCodice() {
            return codice;
        }

        /**
         * Ritorna la descrizione dell'elemento.
         * <p/>
         * @return la descrizione
         */
        public String getDescrizione() {
            return descrizione;
        }



        @Override
        public String toString() {
            return this.getDescrizione();
        }
    }


}// fine della classe
