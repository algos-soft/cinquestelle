package it.algos.albergo.stampeobbligatorie.notifica;

import it.algos.albergo.clientealbergo.MonitorDatiCliente;
import it.algos.albergo.stampeobbligatorie.NavInterno;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampe;
import it.algos.albergo.stampeobbligatorie.testastampe.TestaStampeModulo;
import it.algos.base.errore.Errore;
import it.algos.base.evento.pannello.PanModificatoAz;
import it.algos.base.evento.pannello.PanModificatoEve;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.PannelloBase;

import java.util.Date;

/**
 * Navigatore del modulo interno.
 * </p>
 */
public final class NotificaNavInterno extends NavInterno {

    /**
     * Costruttore completo con parametri.
     *
     * @param modulo modulo di riferimento
     */
    public NotificaNavInterno(NotificaModuloInterno modulo) {
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
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inizializzazione dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            super.inizializza();

            /* crea il monitor stato e lo aggiunge al portale */
            MonitorDatiCliente monitor = new MonitorDatiCliente(MonitorDatiCliente.TipoMonitor.notifica);
            monitor.addListener(PannelloBase.Evento.modifica, new AzClienteModificato());
            this.setMonitorCliente(monitor);
            this.getPortaleNavigatore().add(monitor);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza


    /**
     * Modifica della selezione di una lista
     * <p/>
     */
    public void selezioneModificata() {
        /* variabili e costanti locali di lavoro */
        int codRiga;
        int codCliente=0;
        boolean capo=false;
        Modulo mod;

        int codNotifica;

        Modulo moduloNotifica;
        int codTesta;
        Modulo moduloTesta;
        Date dataNotifica=null;

        try { // prova ad eseguire il codice

            super.selezioneModificata();

            /* recupera le informazioni di validità */
            mod = this.getModulo();
            if (this.getLista().getQuanteRigheSelezionate() == 1) {

                codRiga = this.getLista().getChiaveSelezionata();
                codCliente = this.query().valoreInt(NotificaModuloInterno.Nomi.codcliente.get(), codRiga);
                capo = this.query().valoreBool(mod.getCampoPreferito(), codRiga);
                codNotifica = this.query() .valoreInt(NotificaModuloInterno.Nomi.linkNotifica.get(), codRiga);

                moduloNotifica = NotificaModulo.get();
                codTesta = moduloNotifica.query().valoreInt(Notifica.Cam.linkTesta.get(), codNotifica);

                moduloTesta = TestaStampeModulo.get();
                dataNotifica = moduloTesta.query().valoreData(TestaStampe.Cam.data.get(), codTesta);

            }// fine del blocco if

            /* aggiorna il valore del campo stato */
            MonitorDatiCliente monitor = this.getMonitorCliente();
            monitor.avvia(codCliente, dataNotifica, capo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }

    /**
     * Invocato quando si apre la scheda cliente e si esce registrando.
     * <p/>
     */
    void clienteModificato () {
        /* variabili e costanti locali di lavoro */
        Modulo unModulo;
        NotificaModuloInterno modulo;

        try { // prova ad eseguire il codice
            unModulo = this.getModulo();
            if ((unModulo!=null) && (unModulo instanceof NotificaModuloInterno)) {
                modulo = (NotificaModuloInterno)unModulo;
                modulo.clienteModificato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
}




    /**
     * Azione Cliente Modificato dal pannello Monitor Cliente
     * <p>
     * Invocato quando si apre la scheda cliente e si esce registrando.
     */
    private class AzClienteModificato extends PanModificatoAz {
        public void panModificatoAz(PanModificatoEve unEvento) {
            clienteModificato();
        }

    } // fine della classe interna



} // fine della classe