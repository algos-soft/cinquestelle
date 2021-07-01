package it.algos.albergo.storico5stelle;

import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaDefault;

/**
 * Scheda specifica di una Presenza
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 24-ott-2007 ore 16.42.46
 */
public final class StoricoScheda extends SchedaDefault implements Storico {


    /**
     * Costruttore completo senza parametri.
     * <p/>
     *
     * @param modulo di riferimento
     */
    public StoricoScheda(Modulo modulo) {
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
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
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
    @Override protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pag;
        Pannello pan;

        try { // prova ad eseguire il codice

            /* crea la pagina Generale */
            pag = super.addPagina("generale");

            /* sezione Cliente */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(Storico.Cam.cliente);
            pan.add(Storico.Cam.cognome);
            pan.add(Storico.Cam.nome);
            pan.add(Storico.Cam.datanascita);
            pag.add(pan);

            /* sezione Periodo */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(Storico.Cam.entrata);
            pan.add(Storico.Cam.uscita);
            pan.add(Storico.Cam.cambioEntrata);
            pan.add(Storico.Cam.cambioUscita);
            pag.add(pan);

            /* sezione Camera */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(Storico.Cam.camera);
            pan.add(Storico.Cam.stringacamera);
            pag.add(pan);

            /* sezione Pensione */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(Storico.Cam.pensione);
            pan.add(Storico.Cam.stringapensione);
            pag.add(pan);

            /* sezione Altro */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(Storico.Cam.bambino);
            pan.add(Storico.Cam.ps);
            pag.add(pan);
            
            /* sezione Azienda */
            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(Storico.Cam.azienda);
            pag.add(pan);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }




}// fine della classe