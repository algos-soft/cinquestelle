package it.algos.albergo.stampeobbligatorie.ps;

import it.algos.albergo.clientealbergo.MonitorDatiCliente;
import it.algos.albergo.stampeobbligatorie.ObbligNavigatore;
import it.algos.base.azione.AzModulo;
import it.algos.base.azione.Azione;
import it.algos.base.errore.Errore;
import it.algos.base.evento.pannello.PanModificatoAz;
import it.algos.base.evento.pannello.PanModificatoEve;
import it.algos.base.lista.Lista;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.PannelloBase;

import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * Navigatore registro di P.S.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-lug-2008 ore 9.51.20
 */
public final class PSNavigatore extends ObbligNavigatore {

    private static final String CHIAVE_AZ_VAI_CLIENTE = "vaicliente";

    /* monitor di stato del cliente selezionato */
    private MonitorDatiCliente monitorCliente;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unModulo modulo di riferimento
     */
    public PSNavigatore(Modulo unModulo) {
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
            this.setRigheLista(18);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    /**
     * Inizializzazione dell'oggetto.<br>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche.<br>
     * Viene chiamato dall'inizializzazione del Modulo.<br>
     * Puo' essere chiamato piu' volte.<br>
     * Se l'inizializzazione ha successo imposta il flag inizializzato a true.<br>
     * Il flag puo' essere successivamente modificato dalle sottoclassi se non
     * riescono a portare a termine la propria inizializzazione specifica.<br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        MonitorDatiCliente monitor;

        try { // prova ad eseguire il codice

            super.inizializza();

            /* Azione Vai al cliente */
            this.addAzione(new AzVaiCliente(this.getModulo()));

            /* crea il monitor dati cliente e lo aggiunge al portale */
            monitor = new MonitorDatiCliente(MonitorDatiCliente.TipoMonitor.ps);
            monitor.addListener(PannelloBase.Evento.modifica, new AzClienteModificato());
            this.setMonitorCliente(monitor);
            this.getPortaleNavigatore().add(monitor);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizializza



    /**
     * Modifica della selezione di una lista </li>
     * <p/>
     */
    public void selezioneModificata() {
        /* variabili e costanti locali di lavoro */
        int codRiga;
        int codCliente=0;
        Modulo modPS;
        Date data=null;
        MonitorDatiCliente monitor;

        try { // prova ad eseguire il codice

            super.selezioneModificata();

            /* recupera le informazioni di validità */
            modPS = this.getModulo();
            if (this.getLista().getQuanteRigheSelezionate()==1) {
                codRiga = this.getLista().getChiaveSelezionata();
                codCliente = modPS.query().valoreInt(Ps.Cam.linkCliente.get(), codRiga);
                data = PsLogica.getDataTesta(codRiga);
            }// fine del blocco if

            /* aggiorna il valore del monitor di stato */
            monitor = this.getMonitorCliente();
            monitor.avvia(codCliente, data);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }




    /**
     * Sincronizza il Navigatore.
     * <p/>
     * Controlla lo stato di tutti i suoi componenti <br>
     * Elabora la sua business logic <br>
     * Regola la GUI di conseguenza <br>
     */
    @Override public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        Lista lista;
        boolean continua;
        Azione azVaiCliente = null;

        try { // prova ad eseguire il codice

            super.sincronizza();

            /* recupera il numero di record selezionati */
            lista = this.getLista();
            continua = (lista != null);

            if (continua) {
                azVaiCliente = this.getPortaleLista().getAzione(CHIAVE_AZ_VAI_CLIENTE);
                continua = (azVaiCliente != null);
            } // fine del blocco if

            /*
            * abilita la azione Vai Cliente
            * uno e un solo record deve essere selezionato
            */
            if (continua) {
                azVaiCliente.setEnabled(lista.isRigaSelezionata());
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Apre la scheda del cliente relativo alla
     * riga correntemente selezionata nella lista.
     * <p/>
     */
    protected void vaiCliente() {
    }


    private MonitorDatiCliente getMonitorCliente() {
        return monitorCliente;
    }


    private void setMonitorCliente(MonitorDatiCliente monitorCliente) {
        this.monitorCliente = monitorCliente;
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

    /**
     * Azione vai al cliente relativo.
     * </p>
     */
    private final class AzVaiCliente extends AzModulo {

        /**
         * Costruttore
         * <p/>
         *
         * @param modulo di riferimento
         */
        public AzVaiCliente(Modulo modulo) {
            /* rimanda al costruttore della superclasse */
            super(modulo);

            /* regola le variabili*/
            super.setChiave(CHIAVE_AZ_VAI_CLIENTE);
            super.setIconaMedia("vaicliente24");
            super.setTooltip("apre la scheda del cliente");
            super.setAbilitataPartenza(true);
            super.setUsoLista(true);
        }// fine del metodo costruttore


        /**
         * actionPerformed, da ActionListener.
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void actionPerformed(ActionEvent unEvento) {
            try { // prova ad eseguire il codice
                vaiCliente();
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe 'azione interna'


}
