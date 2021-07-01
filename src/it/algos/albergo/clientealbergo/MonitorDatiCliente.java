package it.algos.albergo.clientealbergo;

import it.algos.base.errore.Errore;
import it.algos.base.font.FontFactory;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.gestione.anagrafica.Anagrafica;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Oggetto grafico in grado di monitorare la completezza dei dati anagrafici
 * di un cliente ai fini PS, Notifica e ISTAT a una certa data.
 * Nel costruttore si definiscono le tipologie di monitoraggio da effettuare
 * e alcuni comportamenti del Monitor.
 * I monitoraggi vengono effettuati invocando il metodo avvia() e passando il
 * codice del cliente e la data alla quale effettuare i controlli.
 * In questo momento vengono effettuati i controlli e viene visualizzata
 * la descrizione degli eventuali problemi.
 * Un pulsante consente di aprire la scheda anagrafica dell'ultimo
 * cliente monitorato.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 23-ott-2008
 */
public final class MonitorDatiCliente extends PannelloFlusso {

    /* tipo di visibilità */
    private Visibilita visibilita;

    /* tipologie di monitoraggio */
    private TipoMonitor[] tipiMonitor;

    /* codice dell'ultimo cliente monitorato */
    private int lastClienteCheck;

    /* data di controllo dell'ultimo cliente monitorato */
    private Date lastDataCheck;

    /* flag "controlla come Capogruppo Scheda" dell'ultimo cliente monitorato */
    private boolean lastCapoCheck;

    /* area di testo con descrizione errori */
    private JTextArea areaTesto;

    /* label che mantiene il nome del cliente visualizzato */
    private JLabel labelNome;

    /* label che mantiene l'icona */
    private JLabel labelIcona;

    /* icona di stato OK */
    private Icon iconaOK;

    /* icona di stato Danger */
    private Icon iconaDanger;

    /* bottone Vai al Cliente */
    private JButton botVaiCliente;



    /**
     * Costruttore completo.
     * <p/>
     *
     * @param tipi elenco di tipologie di monitoraggio da effettuare
     * se si vogliono effettuare tutti i monitoraggi passare TipoMonitor.tutto
     * (deve essere sempre passato da solo!)
     *
     * @see MonitorDatiCliente.TipoMonitor
     */
    public MonitorDatiCliente(TipoMonitor... tipi) {
        this(Visibilita.soloQuandoInvalido,tipi);
    }





    /**
     * Costruttore completo.
     * <p/>
     *
     * @param visibilita tipo di visibilità
     * @see MonitorDatiCliente.Visibilita
     * @param tipi elenco di tipologie di monitoraggio da effettuare
     * @see MonitorDatiCliente.TipoMonitor
     * se si vogliono effettuare tutti i monitoraggi passare TipoMonitor.tutto
     * (deve essere sempre passato da solo!)
     */
    public MonitorDatiCliente(Visibilita visibilita, TipoMonitor... tipi) {

        super(Layout.ORIENTAMENTO_VERTICALE);

        /* variabili e costanti locali di lavoro */
        TipoMonitor tipo;
        ArrayList<TipoMonitor> listaTipi;


        /**
         * se tipi ha un solo elemento ed è l'elemento "tutto", crea
         * un array con tutti i tipi e lo registra
         */
        if (tipi.length == 1) {
            if (tipi[0].equals(TipoMonitor.tutto)) {
                tipi = new TipoMonitor[3];
                tipi[0] = TipoMonitor.ps;
                tipi[1] = TipoMonitor.notifica;
                tipi[2] = TipoMonitor.istat;
            }// fine del blocco if
        }// fine del blocco if

        /**
         * Se l'array contiene l'elemento "tutto" lo rimuove ora
         */
        listaTipi = new ArrayList<TipoMonitor>();
        for (int k = 0; k < tipi.length; k++) {
            tipo = tipi[k];
            if (!tipo.equals(TipoMonitor.tutto)) {
                listaTipi.add(tipo);
            }// fine del blocco if
        } // fine del ciclo for
        tipi = new TipoMonitor[listaTipi.size()];
        for (int k = 0; k < listaTipi.size(); k++) {
            tipi[k] = listaTipi.get(k);
        } // fine del ciclo for

        /* registra l'array */
        this.setTipiMonitor(tipi);

        /* registra il parametro Visibilità */
        this.setVisibilita(visibilita);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Pannello pannello;

        try { // prova ad eseguire il codice


            /* recupera e registra le icone */
            this.setIconaOK(Lib.Risorse.getIconaBase("checkmarkgreen16"));
            this.setIconaDanger(Lib.Risorse.getIconaBase("Danger16"));

            this.setUsaGapFisso(true);
            this.setGapPreferito(0);

            /* crea il pannello di testata e lo aggiunge */
            pannello = this.creaPanTesta();
            this.add(pannello);

            /**
             * crea l'area di testo per la visualizzazione degli errori
             * e la aggiunge
             */
            JTextArea area = new JTextArea();
            this.setAreaTesto(area);
            area.setFont(FontFactory.creaScreenFont(10f));
            area.setOpaque(true);
            area.setBackground(Color.lightGray);
            area.setEnabled(false);
            area.setBorder(BorderFactory.createEtchedBorder());
            Lib.Comp.sbloccaLarMax(area);
            this.add(area);


            this.bloccaAltMax();

            /* avvia con codice zero */
            this.avvia(0, null);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Crea il pannello di testata.
     * <p/>
     * @return il pannello creato
     */
    private Pannello creaPanTesta () {
        /* variabili e costanti locali di lavoro */
        Pannello panTesta  = null;
        JLabel labelIcona;
        JLabel labelNome;
        JButton bot;

        try {    // prova ad eseguire il codice

            panTesta = PannelloFactory.orizzontale(null);
            panTesta.setAllineamento(Layout.ALLINEA_CENTRO);
            panTesta.setGapPreferito(2);

            labelIcona = new JLabel();
            this.setLabelIcona(labelIcona);

            labelNome =  new JLabel();
            this.setLabelNome(labelNome);
            bot = new JButton("vai al cliente");
            bot.addActionListener(new AzVaiCliente());
            bot.setOpaque(false);
            this.setBotVaiCliente(bot);

            panTesta.add(labelIcona);
            panTesta.add(labelNome);
            panTesta.add(Box.createHorizontalGlue());
            panTesta.add(bot);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return panTesta;
    }





    /**
     * Effettua il monitoraggio di un cliente e visualizza il risultato.
     * <p/>
     *
     * @param codCliente da monitorare, 0 per resettare il monitor
     * @param dataCheck data alla quale effettuare il controllo
     *
     * @return true se tutti i controlli sono passati
     */
    public boolean avvia(int codCliente, Date dataCheck) {
        return this.avvia(codCliente, dataCheck, false);
    }


    /**
     * Effettua il monitoraggio di un cliente e visualizza il risultato.
     * <p/>
     *
     * @param codCliente da monitorare, 0 per resettare il monitor
     * @param dataCheck data alla quale effettuare il controllo
     * @param capogruppo true per eseguire il controllo Scheda Notifica in qualità
     * di Capogruppo Scheda, false in qualità di Membro
     *
     * @return true se tutti i controlli sono passati
     */
    public boolean avvia(int codCliente, Date dataCheck, boolean capogruppo) {
        /* variabili e costanti locali di lavoro */
        boolean passed = true;
        TipoMonitor[] tipiMonitor;
        String testo;
        boolean errore = false;
        ArrayList<ClienteAlbergo.ErrDatiAnag> erroriTot = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
        ArrayList<ClienteAlbergo.ErrDatiAnag> errori;
        ArrayList<ClienteAlbergo.ErrDatiAnag> erroriClean;
        Modulo modCliente;
        String nome;
        boolean visibile;

        try {    // prova ad eseguire il codice

            /* memorizza i parametri di avvio per eventuale riutilizzo */
            this.setLastClienteCheck(codCliente);
            this.setLastDataCheck(dataCheck);
            this.setLastCapoCheck(capogruppo);

            if (codCliente > 0) {

                /* determina quali controlli effettuare */
                tipiMonitor = this.getTipiMonitor();
                for(TipoMonitor tipoMonitor : tipiMonitor){
                    switch (tipoMonitor) {
                        case ps:
                            errori = ClienteAlbergoModulo.checkValidoPS(codCliente, dataCheck);
                            erroriTot.addAll(errori);
                            break;
                        case notifica:
                            errori = ClienteAlbergoModulo.checkValidoNotifica(codCliente, capogruppo, dataCheck);
                            erroriTot.addAll(errori);
                            break;
                        case istat:
                            errori = ClienteAlbergoModulo.checkValidoISTAT(codCliente, dataCheck);
                            erroriTot.addAll(errori);
                            break;
                        default: // caso non definito
                            break;
                    } // fine del blocco switch
                }

                /* pulisce l'elenco dagli eventuali doppioni */
                erroriClean = new ArrayList<ClienteAlbergo.ErrDatiAnag>();
                for(ClienteAlbergo.ErrDatiAnag unErrore : erroriTot){
                    if (!erroriClean.contains(unErrore)) {
                        erroriClean.add(unErrore);
                    }// fine del blocco if
                }

                /* costruisce il testo concatenato */
                String[] stringhe = new String[erroriClean.size()];
                for (int k = 0; k < erroriClean.size(); k++) {
                    ClienteAlbergo.ErrDatiAnag unErrore = erroriClean.get(k);
                    stringhe[k] = unErrore.get();
                } // fine del ciclo for
                testo = Lib.Testo.concatReturn(stringhe);

                /* se non ci sono errori scrive OK */
                if (Lib.Testo.isVuota(testo)) {
                    testo = "OK";
                } else {
                    errore = true;
                }// fine del blocco if-else

                /* aggiorna il nome visualizzato */
                modCliente = ClienteAlbergoModulo.get();
                nome = modCliente.query().valoreStringa(Anagrafica.Cam.soggetto.get(), codCliente);
                this.setNomeCliente(nome);

                /* aggiorna il testo di errore */
                this.setTesto(testo);

                /* aggiorna l'icona e il colore del testo */
                if (errore) {
                    this.setColore(Color.RED);
                    this.setIcona(this.getIconaDanger());
                } else {
                    this.setColore(Color.BLACK);
                    this.setIcona(this.getIconaOK());
                }// fine del blocco if-else

            }// fine del blocco if


            /* regola la visibilità dell'oggetto */
            visibile = true;
            switch (this.getVisibilita()) {
                case sempre:
                    break;
                case soloQuandoInvalido:
                    visibile = false;
                    if (codCliente != 0) {
                        if (errore) {
                            visibile = true;
                        }// fine del blocco if
                    }// fine del blocco if
                    break;
                default : // caso non definito
                    break;
            } // fine del blocco switch
            this.setVisible(visibile);



        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return passed;
    }


    /**
     * Assegna il testo visualizzato.
     * <p/>
     *
     * @param testo da assegnare
     */
    private void setTesto(String testo) {
        /* variabili e costanti locali di lavoro */
        JTextArea area;

        try {    // prova ad eseguire il codice
            area = this.getAreaTesto();
            area.setText(testo);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna il colore del testo visualizzato.
     * <p/>
     *
     * @param colore da assegnare
     */
    private void setColore(Color colore) {
        /* variabili e costanti locali di lavoro */
        JTextArea area;

        try {    // prova ad eseguire il codice
            area = this.getAreaTesto();
            area.setDisabledTextColor(colore);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna l'icona.
     * <p/>
     *
     * @param icona da assegnare, null per nessuna icona
     */
    private void setIcona(Icon icona) {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try {    // prova ad eseguire il codice
            label = this.getLabelIcona();
            if (label!=null) {
                label.setIcon(icona);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna il nome visualizzato del cliente.
     * <p/>
     *
     * @param nome da assegnare
     */
    private void setNomeCliente(String nome) {
        /* variabili e costanti locali di lavoro */
        JLabel label;

        try {    // prova ad eseguire il codice
            label = this.getLabelNome();
            if (label!=null) {
                label.setText(nome);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Apre la scheda del cliente corrente
     * <p/>
     */
    protected void vaiCliente() {
        /* variabili e costanti locali di lavoro */
        boolean confermato=false;
        boolean continua;
        int codCliente;
        Modulo modCliente=null;

        try { // prova ad eseguire il codice

            codCliente = this.getLastClienteCheck();
            continua = (codCliente>0);

            if (continua) {
                modCliente = ClienteAlbergoModulo.get();
                continua = (modCliente!=null);
            }// fine del blocco if

            if (continua) {
                confermato = modCliente.presentaRecord(codCliente);
            }// fine del blocco if

            /**
             * se ha modificato il cliente riavvia l'oggetto
             * per riflettere le modifiche e lancia un evento
             * per notificare all'esterno
             */
            if (confermato) {
                int codCli = this.getLastClienteCheck();
                Date data = this.getLastDataCheck();
                boolean capo = this.getLastCapoCheck();
                this.avvia(codCli, data, capo);

                /* lancia un evento verso l'esterno */
                this.fire(PannelloBase.Evento.modifica);

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }





    private Visibilita getVisibilita() {
        return visibilita;
    }


    private void setVisibilita(Visibilita visibilita) {
        this.visibilita = visibilita;
    }


    private TipoMonitor[] getTipiMonitor() {
        return tipiMonitor;
    }


    private void setTipiMonitor(TipoMonitor[] tipiMonitor) {
        this.tipiMonitor = tipiMonitor;
    }


    private int getLastClienteCheck() {
        return lastClienteCheck;
    }


    private void setLastClienteCheck(int lastClienteCheck) {
        this.lastClienteCheck = lastClienteCheck;
    }


    private Date getLastDataCheck() {
        return lastDataCheck;
    }


    private void setLastDataCheck(Date lastDataCheck) {
        this.lastDataCheck = lastDataCheck;
    }


    private boolean getLastCapoCheck() {
        return lastCapoCheck;
    }


    private void setLastCapoCheck(boolean lastCapoCheck) {
        this.lastCapoCheck = lastCapoCheck;
    }


    private JTextArea getAreaTesto() {
        return areaTesto;
    }


    private void setAreaTesto(JTextArea areaTesto) {
        this.areaTesto = areaTesto;
    }


    private JLabel getLabelNome() {
        return labelNome;
    }


    private void setLabelNome(JLabel labelNome) {
        this.labelNome = labelNome;
    }


    private JLabel getLabelIcona() {
        return labelIcona;
    }


    private void setLabelIcona(JLabel labelIcona) {
        this.labelIcona = labelIcona;
    }


    private Icon getIconaOK() {
        return iconaOK;
    }


    private void setIconaOK(Icon iconaOK) {
        this.iconaOK = iconaOK;
    }


    private Icon getIconaDanger() {
        return iconaDanger;
    }


    private void setIconaDanger(Icon iconaDanger) {
        this.iconaDanger = iconaDanger;
    }


    private JButton getBotVaiCliente() {
        return botVaiCliente;
    }


    private void setBotVaiCliente(JButton botVaiCliente) {
        this.botVaiCliente = botVaiCliente;
    }


    /**
     * Classe 'interna'. </p>
     */
    private final class AzVaiCliente implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            vaiCliente();
        }
    } // fine della classe 'interna'



    /**
     * Tipologie di monitoraggio disponibili
     * <p/>
     */
    public enum TipoMonitor {

        ps,          //esistenza dei dati ai fini del registro di PS
        notifica,     //esistenza dei dati ai fini della scheda di notifica
        istat,      //esistenza dei dati ai fini della dichiarazione ISTAT
        tutto         //tutti i controlli
    }// fine della classe

    /**
     * Tipi di visibilità del monitor
     * <p/>
     */
    public enum Visibilita {
        sempre,          //sempre visibile (quando vuoto, valido, invalido)
        soloQuandoInvalido,     //visibile solo quando invalido
    }// fine della classe


}// fine della classe