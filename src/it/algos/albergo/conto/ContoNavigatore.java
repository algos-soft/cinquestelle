/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-mar-2006
 */
package it.algos.albergo.conto;

import it.algos.base.azione.Azione;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.scheda.Scheda;

/**
 * Business logic per Conto.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Gestisce la business-logic della GUI </li>
 * <li> Riceve le invocazioni da una classe di tipo <code>Gestore</code> </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 22-mar-2006 ore 18.14.31
 */
public final class ContoNavigatore extends NavigatoreLS {

    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo modulo di riferimento
     */
    public ContoNavigatore(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

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
        Scheda scheda;

        try { // prova ad eseguire il codice

            this.setUsaPannelloUnico(true);
            this.setAggiornamentoTotaliContinuo(true);

            scheda = new ContoScheda(this.getModulo());
            this.addScheda(scheda);

//            /* non usa il bottone Nuovo, ha un sistema di aggiunta specifico*/
//            this.setUsaNuovo(false);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    public void inizializza() {
        super.inizializza();
    }// fine del metodo inizializza


    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro;
        Lista lista;

        super.avvia();

        try { // prova ad eseguire il codice

//            /* imposta il filtro corrente per i soli conti aperti */
//            filtro = FiltroFactory.crea(Conto.CAMPO_STATO, false);
//            this.getLista().setFiltroCorrente(filtro);
//            this.aggiornaLista();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo lancia


    /**
     * Sincronizza lo stato del Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di ogni conseguenza (menu, bottoni, ecc) <br>
     * Invoca il metodo sovrascritto della superclasse <br>
     */
    public void sincronizza() {

        /* invoca il metodo sovrascritto della superclasse */
        super.sincronizza();

        try { // prova ad eseguire il codice
            this.abilitaBottoniConto();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Abilita i bottoni specifici di questo navigatore.
     * <p/>
     */
    private void abilitaBottoniConto() {
        /* variabili e costanti locali di lavoro */
        Azione azione;
        Lista lista;
        int quanti;
        boolean abilita = false;
        boolean chiuso = false;
        int riga = 0;
        Campo campo;
        Object valore;
        double doppio;

        try {    // prova ad eseguire il codice

            /* recupera il numero di record selezionati */
            lista = this.getLista();
            quanti = lista.getQuanteRigheSelezionate();

            /* se un solo record, recupera il flag chiuso */
            if (quanti == 1) {
                riga = lista.getRigaSelezionata();
                campo = this.getModulo().getCampo(Conto.Cam.chiuso.get());
                valore = lista.getValueAt(riga, campo);
                chiuso = Libreria.getBool(valore);
            }// fine del blocco if

            /* abilita il bottone Stampa Conto
             * - uno e un solo conto deve essere selezionato
             */
            abilita = false;
            if (quanti == 1) {
                abilita = true;
            }// fine del blocco if
            azione = this.getPortaleLista().getAzione(ContoModulo.StampaContoAz.CHIAVE);
            azione.setEnabled(abilita);

            /* abilita il bottone Incasso Sospesi
             * - uno e un solo conto deve essere selezionato
             * - il conto deve essere chiuso
             * - il conto deve avere un importo sospeso
             */
            abilita = false;
            if (quanti == 1) {
                if (chiuso) {
                    campo = this.getModulo().getCampo(Conto.Cam.totSospeso.get());
                    valore = lista.getValueAt(riga, campo);
                    doppio = Libreria.getDouble(valore);
                    if (doppio != 0) {
                        abilita = true;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if
            azione = this.getPortaleLista().getAzione(ContoModulo.SaldoSospesiAz.CHIAVE);
            azione.setEnabled(abilita);

            /* abilita il bottone Chiusura Conto
            * - uno e un solo conto deve essere selezionato
            * - il conto deve essere aperto
            */
            abilita = false;
            if (quanti == 1) {
                if (!chiuso) {
                    abilita = true;
                }// fine del blocco if
            }// fine del blocco if
            azione = this.getPortaleLista().getAzione(ContoModulo.ChiudiContoAz.CHIAVE);
            azione.setEnabled(abilita);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


}// fine della classe
