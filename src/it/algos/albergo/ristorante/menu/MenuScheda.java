/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.albergo.ristorante.menu;

import it.algos.albergo.conto.Conto.Cam;
import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;

import javax.swing.Icon;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.util.Date;

/**
 * Presentazione grafica di un singolo record di Menu.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi&ugrave; pagine, risulter&agrave;
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma &egrave; attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-gen-2005 ore 12.21.21
 */
public final class MenuScheda extends SchedaBase implements Menu {

    /**
     * Titoli delle pagine
     */
    private static final String TITOLO_PIATTI = "piatti";

    private static final String TITOLO_TAVOLI_COMANDE = "comande";


    /**
     * Costruttore completo.
     * <p>
     * @param modulo di riferimento
     */
    public MenuScheda(Modulo modulo) {
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
    }// fine del metodo inizia


    /**
     * .
     * <p/>
     */
    public void avvia(int codice) {
        super.avvia(codice);
        
        // GESTIONE MULTIAZIENDA DISABLED 10-2017 ALEX
        Campo campo = getCampo(Cam.azienda);
        campo.setValore(1);
        campo.setVisibile(false);
        // END DISABLED

    }


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaPagine() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;

        try {    // prova ad eseguire il codice

            /* Crea e disegna la prima pagina con info generali e piatti */
            pagina = super.addPagina(TITOLO_PIATTI);
            pagina.add(this.creaPanGiornoPasto());
            pagina.add(Menu.Cam.titolo.get());
            pagina.add(Menu.Cam.piatti.get());
            pagina.add(this.creaBotStampe());

            /* Crea e disegna la seconda pagina con tavoli e comande */
            pagina = super.addPagina(TITOLO_TAVOLI_COMANDE);
            pagina.add(Menu.Cam.ordini.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il pannello con data e pasto.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanGiornoPasto() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;

        try {    // prova ad eseguire il codice
            pan = PannelloFactory.orizzontale(this);
            pan.add(Menu.Cam.data.get());
            pan.add(Menu.Cam.pasto.get());
            pan.add(Menu.Cam.azienda.get());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il bottone stampe.
     * <p/>
     *
     * @return il bottone creato
     */
    private JButton creaBotStampe() {
        /* variabili e costanti locali di lavoro */
        JButton bot = null;
        Icon icona;

        try {    // prova ad eseguire il codice

            icona = Lib.Risorse.getIconaBase("Print24");
            bot = new JButton("Stampa...");
            bot.setOpaque(false);
            bot.setFocusable(false);
            bot.setIcon(icona);
            bot.setToolTipText("Stampa il menu nei diversi formati");
            bot.addActionListener(new AzStampe());

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return bot;
    }


    protected String getTestoRiferimento() {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        Campo campoData;
        Campo campoPasto;
        Date data;
        String sData;
        String sPasto;

        try { // prova ad eseguire il codice
            campoData = this.getCampo(Menu.Cam.data.get());
            data = (Date)campoData.getValore();
            sData = Lib.Data.getStringa(data);
            campoPasto = this.getCampo(Menu.Cam.pasto.get());
            sPasto = (String)campoPasto.getValoreElenco();
            testo = "Menu del " + sData;
            if (Lib.Testo.isValida(sPasto)) {
                testo += " - " + sPasto;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return testo;
    }


    /**
     * Azione Stampe.
     * </p>
     */
    private final class AzStampe extends AzAdapterAction {

        public void actionPerformed(ActionEvent unEvento) {
            MenuModulo.stampaMenu(getCodice());
        }
    } // fine della classe 'interna'


}// fine della classe
