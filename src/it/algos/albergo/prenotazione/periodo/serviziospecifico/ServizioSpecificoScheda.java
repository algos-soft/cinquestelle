package it.algos.albergo.prenotazione.periodo.serviziospecifico;
/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      24-ott-2007
 */

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda specifica di un Periodo
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 24-ott-2007 ore 16.42.46
 */
public final class ServizioSpecificoScheda extends SchedaDefault implements ServizioSpecifico {


    /**
     * Costruttore completo senza parametri.
     */
    public ServizioSpecificoScheda(Modulo modulo) {
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
        this.setUsaStatusBar(false);
        this.setMargine(Pagina.Margine.piccolo);
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
        Pannello pan;

        try { // prova ad eseguire il codice
            /* aggiunge  una pagina al libro con il set di default */
            pagina = this.addPagina("generale");

            /* aggiunge i campi */
            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.descrizione);
            pan.add(Cam.arrivo);
            pan.add(Cam.settore);
            pagina.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


}// fine della classe
