/**
 * Title:     PannelloPS
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-giu-2008
 */
package it.algos.albergo.stampeobbligatorie.ps;

import it.algos.albergo.stampeobbligatorie.PannelloObbligatorie;
import it.algos.albergo.stampeobbligatorie.StampeObbligatorieDialogo;
import it.algos.albergo.stampeobbligatorie.testastampe.NavigatoreDoppio;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;

/**
 * Pannello di gestione delle stampe di PS
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-giu-2008 ore 12.04.54
 */
public class PannelloPS extends PannelloObbligatorie {


    /**
     * Costruttore base.
     * <p/>
     *
     * @param dialogo di riferimento
     */
    public PannelloPS(StampeObbligatorieDialogo dialogo) {

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
        try { // prova ad eseguire il codice
            this.setLogica(new PsLogica(this));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia






    /**
     * Ritorna il codice del cliente correntemente selezionato.
     * <p/>
     * Utilizzato dalla funzione Vai a Cliente
     * @return il codice del cliente selezionato
     */
    protected int getCodClienteSelezionato () {
        /* variabili e costanti locali di lavoro */
        int codCliente = 0;
        boolean continua;
        int codPs = 0;
        Modulo mod;

        try { // prova ad eseguire il codice

            mod = this.getModuloSlave();
            continua = (mod != null);

            if (continua) {
                codPs = mod.getLista().getChiaveSelezionata();
                continua = (codPs > 0);
            }// fine del blocco if

            if (continua) {
                codCliente = mod.query().valoreInt(Ps.Cam.linkCliente.get(), codPs);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCliente;
    }


    /**
     * Ritorna il navigatore doppio.
     * <p/>
     */
    @Override protected NavigatoreDoppio getNavigatore() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        NavigatoreDoppio navDoppio=null;

        try { // prova ad eseguire il codice
            nav=this.getModuloTesta().getNavigatore(TestaStampe.Nav.ps.get());
            if ((nav!=null) && (nav instanceof NavigatoreDoppio)) {
                navDoppio = (NavigatoreDoppio)nav;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return navDoppio;
    }





}// fine della classe