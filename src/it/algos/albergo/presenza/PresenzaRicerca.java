/**
 * Title:     PresenzaRicerca
 * Copyright: Copyright (c) 2009
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      20-lug-2009
 */
package it.algos.albergo.presenza;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.ricerca.CampoRicerca;
import it.algos.base.ricerca.RicercaBase;

/**
 * Ricerca specifica per il navigatore Presenze
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 20-lug-2009 ore 22.11.47
 */
public final class PresenzaRicerca extends RicercaBase {

    private CampoRicerca crDataEntrata;
    private CampoRicerca crCambioEntrata;
    private CampoRicerca crDataUscita;
    private CampoRicerca crCambioUscita;
    private CampoRicerca crCliente;
    private CampoRicerca crCamera;
    private CampoRicerca crTrattamento;
    private CampoRicerca crBambino;
    private CampoRicerca crTavolo;

    private CampoRicerca crDataPresente;
    private CampoRicerca crGiaPartito;


    /**
     * Costruttore completo con parametri. <br>
     */
    public PresenzaRicerca() {
        /* rimanda al costruttore della superclasse */
        super(PresenzaModulo.get());

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice

            crDataEntrata = this.addCampoRicerca(Presenza.Cam.entrata.get(),true);
            crDataEntrata.setEtichetta("Data di entrata");

            crCambioEntrata = this.addCampoRicerca(Presenza.Cam.cambioEntrata.get());
            crCambioEntrata.setEtichetta("Cambio in entrata");

            crDataUscita = this.addCampoRicerca(Presenza.Cam.uscita.get(),true);
            crDataUscita.setEtichetta("Data di uscita");

            crCambioUscita = this.addCampoRicerca(Presenza.Cam.cambioUscita.get());
            crCambioUscita.setEtichetta("Cambio in uscita");

            crCliente = this.addCampoRicerca(Presenza.Cam.cliente.get());
            crCliente.setEtichetta("Cliente");
            crCliente.setLarghezza(180);

            crCamera = this.addCampoRicerca(Presenza.Cam.camera.get());
            crCamera.setEtichetta("Camera");
            crCamera.setLarghezza(92);

            crTrattamento = this.addCampoRicerca(Presenza.Cam.pensione.get());
            crTrattamento.setEtichetta("Trattamento");
            crTrattamento.setLarghezza(120);

            crBambino = this.addCampoRicerca(Presenza.Cam.bambino.get());
            crBambino.setEtichetta("Bambino");

            crTavolo = this.addCampoRicerca(Presenza.Cam.tavolo.get());
            crTavolo.setEtichetta("Tavolo");
            crTavolo.setLarghezza(120);


            /* campo di ricerca specializzato */
            Campo campo = CampoFactory.data("dataPresente");
            crDataPresente = new CampoRicercaPeriodo(this, campo);
            crDataPresente.setEtichetta("Presente nel periodo");
            this.addCampoRicerca(crDataPresente);

            crGiaPartito = this.addCampoRicerca(Presenza.Cam.chiuso.get());
            crGiaPartito.setEtichetta("Già partito");

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Compone la pagina del dialogo.
     * <p/>
     * Aggiunge sequenzialmente gli oggetti CampoRicerca
     * Sovrascritto dalle sottoclassi che non vogliono usare
     * la disposizione automatica dei campi uno sotto l'altro.
     */
    protected void creaPagina() {
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try { // prova ad eseguire il codice

            pan = PannelloFactory.orizzontale(this);
            pan.add(crDataEntrata);
            pan.add(crCambioEntrata);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crDataUscita);
            pan.add(crCambioUscita);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crCliente);
            pan.add(crCamera);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crTrattamento);
            pan.add(crBambino);
            pan.add(crTavolo);
            this.addPannello(pan);

            pan = PannelloFactory.orizzontale(this);
            pan.add(crDataPresente);
            pan.add(crGiaPartito);
            this.addPannello(pan);



        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }
}// fine della classe
