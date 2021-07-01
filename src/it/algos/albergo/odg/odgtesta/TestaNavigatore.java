package it.algos.albergo.odg.odgtesta;

import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.odg.OdgLogica;
import it.algos.albergo.odg.odgrisorse.OdgRisorseDialogo;
import it.algos.base.azione.Azione;
import it.algos.base.azione.AzioneBase;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreL;

import java.awt.event.ActionEvent;
import java.util.Date;

import javax.swing.Icon;

/**
 * Navigatore per la lista dei dati di testata degli ODG
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-lug-2008 ore 9.51.20
 */
public final class TestaNavigatore extends NavigatoreL {

    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public TestaNavigatore(Modulo unModulo) {
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
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice

            this.setUsaToolBarLista(true);
            this.setUsaStatusBarLista(false);
            this.setUsaNuovo(false);
            this.setUsaModifica(false);
            this.setUsaSelezione(false);
            this.getLista().setOrdinabile(false);
            this.setRigheLista(18);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    @Override
    public void inizializza() {

        super.inizializza();
        
        Azione azione = this.getPortaleLista().getAzione(Azione.STAMPA);
        azione.setTooltip("Stampa l'Ordine del Giorno selezionato");
        
		// aggiunge l'azione Stampa ordini di Servizio
        getPortaleLista().getToolBar().addBottone(new AzOrdServizio());


    }


    @Override
    public void sincronizza() {
        
        super.sincronizza();

        /* la stampa è abilitata solo se c'è 1 e 1 solo record selezionato */
        int quante = this.getLista().getQuanteRigheSelezionate();
        Azione azione = this.getPortaleLista().getAzione(Azione.STAMPA);
        azione.setEnabled(quante == 1);

    }


    @Override
    public void stampaLista() {
        int codOdg = this.getLista().getChiaveSelezionata();
        OdgLogica.stampaOdg(codOdg);
    }
    
    /**
     * Classe Azione Ordini del Giorno
     * <p/>
     */
    private class AzOrdServizio extends AzioneBase {

        /**
         * Costruttore completo.
         * <p/>
         */
        public AzOrdServizio() {
            super();
            Icon icona = Lib.Risorse.getIcona(AlbergoModulo.get(), "stampaordiniservizio24");
            this.setIconaPiccola(icona);
            this.setIconaMedia(icona);
            this.setIconaGrande(icona);
            this.setTooltip("Stampa gli ordini di servizio per le altre risorse.");
        }// fine del metodo costruttore completo


        @Override
        public void actionPerformed(ActionEvent unEvento) {
        	
        	// se c'è una riga selezionata usa la data
        	Date dataDefault=Lib.Data.getCorrente();
            int codOdg = getLista().getChiaveSelezionata();
            if (codOdg>0) {
            	dataDefault = OdgModulo.get().query().valoreData(Odg.Cam.data.get(), codOdg);
			}
        	new OdgRisorseDialogo(dataDefault);
        }
    }


}