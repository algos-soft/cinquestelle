/**
 * Title:     ContoDialogoStampa
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      11-giu-2007
 */
package it.algos.albergo.conto;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;

/**
 * Dialogo opzioni di stampa del conto.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 15.00.06
 */
public final class ContoDialogoStampa extends DialogoAnnullaConferma {

    private static final String NOME_CAMPO_OPZIONI = "opzioni";

    private static final String NOME_CAMPO_RAGGRUPPA = "raggruppa pensioni";


    /**
     * Costruttore completo
     * <p/>
     */
    public ContoDialogoStampa() {
        /* rimanda al costruttore della superclasse */

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
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            this.setTitolo("Stampa conto");

            campo = CampoFactory.radioInterno(NOME_CAMPO_OPZIONI);
            campo.decora().eliminaEtichetta();
            campo.setValoriInterni(Conto.TipiStampa.getLista());
            campo.setUsaNonSpecificato(false);
            campo.decora().obbligatorio();
            campo.setValore(1);
            this.addCampo(campo);

            campo = CampoFactory.checkBox(NOME_CAMPO_RAGGRUPPA);
            campo.setLarScheda(150);
            campo.setValore(true);
            this.addCampo(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    @Override
    public void inizializza() {
        super.inizializza();
    } /* fine del metodo */


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Object valore;
        Campo campo;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* se è selezionato solo extra il check raggruppa pensioni
             * viene spento e disabilitato */
            campo = this.getCampo(NOME_CAMPO_RAGGRUPPA);
            campo.setModificabile(true);
            valore = this.getValore(NOME_CAMPO_OPZIONI);
            if (valore.equals(Conto.TipiStampa.extra.ordinal() + 1)) {
                campo.setValore(false);
                campo.setModificabile(false);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il tipo di stampa selezionato.
     * <p/>
     *
     * @return il tipo di stampa selezionato
     */
    public Conto.TipiStampa getTipoStampa() {
        /* variabili e costanti locali di lavoro */
        Conto.TipiStampa tipo = null;
        Conto.TipiStampa[] tipi = null;
        Object valore;
        int pos;

        try {    // prova ad eseguire il codice

            valore = this.getValore(NOME_CAMPO_OPZIONI);
            pos = Libreria.getInt(valore);

            tipi = Conto.TipiStampa.values();
            for (int k = 0; k < tipi.length; k++) {
                if (k == pos - 1) {
                    tipo = tipi[k];
                    break;
                }// fine del blocco if
            } // fine del ciclo for

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Ritorna l'opzione raggruppa pensione.
     * <p/>
     *
     * @return true se si devono raggruppare le voci di pensione
     */
    public boolean isRaggruppaPensione() {
        /* variabili e costanti locali di lavoro */
        boolean raggruppa = false;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = this.getValore(NOME_CAMPO_RAGGRUPPA);
            raggruppa = Libreria.getBool(valore);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return raggruppa;
    }


//    /**
//     * Azione del bottone Anteprima
//     * </p>
//     */
//    private final class BotAnteprima implements ActionListener {
//
//        public void actionPerformed(ActionEvent event) {
//            int a = 87;
//        }
//    }


//    /**
//     * Azione del bottone Annulla
//     * </p>
//     */
//    private final class BotAnnulla implements ActionListener {
//
//        public void actionPerformed(ActionEvent event) {
//            int a = 87;
//        }
//    }


//    /**
//     * Azione del bottone Stampa Conto
//     * </p>
//     */
//    private final class BotStampa implements ActionListener {
//
//        public void actionPerformed(ActionEvent event) {
//            int a = 87;
//        }
//    }


}// fine della classe
