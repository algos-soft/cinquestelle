package it.algos.albergo.odg.odgzona;
/**
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      18-giu-2009
 */

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda specifica di una Zona di Odg
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alkex
 * @version 1.0    / 18-giu-2009 ore 15.17.14
 */
public final class OdgZonaScheda extends SchedaDefault implements OdgZona {



    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public OdgZonaScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
            this.setUsaStatusBar(false);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia




    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try { // prova ad eseguire il codice

            pagina = this.addPagina("generale");
            pagina.add(Cam.note);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }




}// fine della classe