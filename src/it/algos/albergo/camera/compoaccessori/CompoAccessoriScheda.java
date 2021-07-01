package it.algos.albergo.camera.compoaccessori;

import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.scheda.SchedaBase;

/**
 * Scheda specifiche del modulo incrocio Composizione - Accessori
 * </p>
 * Usata dal navigatore degli accessori interno alla scheda Composizione Camera
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author Alex
 * @version 1.0 / 22-giu-2009 ore 22:00
 */
public final class CompoAccessoriScheda extends SchedaBase implements CompoAccessori {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo di riferimento per la scheda
     */
    public CompoAccessoriScheda(Modulo modulo) {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        this.setUsaStatusBar(false);
    }// fine del metodo inizia


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see it.algos.base.scheda.SchedaBase#add
     */
    @Override protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try {    // prova ad eseguire il codice

            /* crea una pagina vuota col titolo */
            pagina = super.addPagina("generale");

            /* aggiunge i campi */
            pagina.add(Cam.accessorio);
            pagina.add(Cam.quantita);

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo creaPagine


}// fine della classe