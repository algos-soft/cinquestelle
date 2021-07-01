/**
 * Title:     PannelloPS
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-giu-2008
 */
package it.algos.albergo.stampeobbligatorie.istat;

import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.albergo.stampeobbligatorie.StampeObbligatorieDialogo;
import it.algos.albergo.stampeobbligatorie.testastampe.NavigatoreDoppio;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.base.errore.Errore;
import it.algos.base.navigatore.Navigatore;

/**
 * Pannello di gestione delle stampe ISTAT
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-giu-2008 ore 12.04.54
 */
public class PannelloISTAT extends PannelloObbligatorie {

    /**
     * Costruttore base.
     * <p/>
     *
     * @param dialogo di riferimento
     */
    public PannelloISTAT(StampeObbligatorieDialogo dialogo) {

        /* rimanda al costruttore della superclasse */
        super(dialogo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.setLogica(new ISTATLogica(this));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Ritorna il navigatore doppio.
     * <p/>
     */
    @Override
    protected NavigatoreDoppio getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        NavigatoreDoppio navDoppio = null;

        try { // prova ad eseguire il codice
            nav = this.getModuloTesta().getNavigatore(TestaStampe.Nav.istat.get());
            if ((nav != null) && (nav instanceof NavigatoreDoppio)) {
                navDoppio = (NavigatoreDoppio)nav;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return navDoppio;
    }


    /**
     * Ritorna il codice del cliente correntemente selezionato.
     * <p/>
     * Utilizzato dalla funzione Vai a Cliente
     *
     * @return il codice del cliente selezionato
     */
    protected int getCodClienteSelezionato() {
        /* variabili e costanti locali di lavoro */
        ISTATNavigatore navIstat;
        int codCliente = 0;

        try { // prova ad eseguire il codice

            Navigatore nav = this.getNavSlave();
            if (nav != null) {
                if (nav instanceof ISTATNavigatore) {
                    navIstat = (ISTATNavigatore)nav;
                    codCliente = navIstat.getCodClienteSelezionato();
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCliente;
    }


    /**
     * Apre la scheda del cliente relativo alla
     * riga correntemente selezionata nella lista righe.
     * <p/>
     *
     * @return true se l'editing del cliente è stato confermato
     */
    protected boolean vaiCliente() {
        /* variabili e costanti locali di lavoro */
        boolean continua = false;

        try { // prova ad eseguire il codice

            continua = super.vaiCliente();

            /* se l'editing è stato confermato, forza la rigenerazione
            * dei records perché la modifica potrebbe avere influito
            * sul gruppo nel quale il cliente è collocato (es. se ho
            * modificato la città) */
            if (continua) {
                this.getLogica().creaRecords();
                this.getNavMaster().aggiornaLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Invocato quando si modifica la scheda di un cliente
     * da parte di un oggetto Monitor Cliente.
     */
    public void clienteModificato() {

        try { // prova ad eseguire il codice

            /**
             * forza la rigenerazione
             * dei records perché la modifica potrebbe avere influito
             * sul gruppo nel quale il cliente è collocato (es. se ho
             * modificato la città)
             */
            this.getLogica().creaRecords();
            this.getNavMaster().aggiornaLista();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

}// fine della classe