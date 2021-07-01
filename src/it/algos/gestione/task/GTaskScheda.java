/**
 * Title:     AtaskScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      25-giu-2007
 */
package it.algos.gestione.task;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

import java.util.Date;

/**
 * Scheda specifica di un task.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 25-giu-2007
 */
public class GTaskScheda extends SchedaBase implements GTask {

    /**
     * Costruttore completo.
     *
     * @param unModulo di riferimento per la scheda
     */
    public GTaskScheda(Modulo unModulo) {
        /* rimanda al costruttore della superclasse */
        super(unModulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        try { // prova ad eseguire il codice

            this.creaPagGenerale();

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea a pagina con i dati generali dell'evento.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     */
    private void creaPagGenerale() {
        Pagina pag;
        Pannello pan;

        try { // prova ad eseguire il codice

            pag = this.addPagina("Task");

            pag.add(Cam.sigla.get());

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.descrizione.get());
            pag.add(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.giornilavorazione.get());
            pan.add(Cam.dataInizio.get());
            pan.add(Cam.dataUtile.get());
            pag.add(pan);

            pag.add(Cam.evaso.get());

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    }


    protected void eventoMemoriaModificata(Campo campo) {

        try { // prova ad eseguire il codice

            /* se modifico i giorni lavorazione aggiorna la data inizio suggerita */
            if (campo.getNomeInterno().equals(Cam.giornilavorazione.get())) {
                this.syncDate();
            }// fine del blocco if

            /* se modifico la data utile aggiorna la data inizio suggerita */
            if (campo.getNomeInterno().equals(Cam.dataUtile.get())) {
                this.syncDate();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Sincronizza la data inizio e la data utile in base
     * alla data evento, ai giorni prima e ai giorni lavorazione.
     * <p/>
     */
    private void syncDate() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Date dataUtile = null;
        Date dataInizioTask;
        int giorniLavo = 0;

        Object valore;

        try {    // prova ad eseguire il codice

            /* recupera la data utile */
            if (continua) {
                dataUtile = this.getData(Cam.dataUtile.get());
                continua = dataUtile != null;
            }// fine del blocco if

            /* recupera i giorni di lavorazione */
            if (continua) {
                valore = this.getValore(Cam.giornilavorazione.get());
                giorniLavo = Libreria.getInt(valore);
            }// fine del blocco if

            /* determina la data inizio e la registra */
            if (continua) {
                dataInizioTask = Lib.Data.add(dataUtile, -giorniLavo);
                this.setValore(Cam.dataInizio.get(), dataInizioTask);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


} // fine della classe
