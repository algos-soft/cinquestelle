package it.algos.albergo.odg;

import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.odg.odgtesta.Odg;
import it.algos.albergo.odg.odgtesta.OdgModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Dialogo di gestione degli Ordini del Giorno.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 08-giu-2007 ore 11.21.46
 */
public class OdgDialogo extends DialogoBase {

    /**
     * istanza unica di questa classe
     */
    private static OdgDialogo ISTANZA = null;

    private static final String NOME_CAMPO_DATA_1 = "dal";

    private static final String NOME_CAMPO_DATA_2 = "al";

    private JButton botGenera;

    /* Navigatore master (quello delle date) */
    private Navigatore navMaster;


    /**
     * Costruttore base.
     * <p/>
     */
    private OdgDialogo() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni iniziali di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal
     * costruttore (init) <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        Navigatore nav;
        Campo campo;

        try { // prova ad eseguire il codice

            this.setTitolo("Ordini del Giorno");
            this.getDialogo().setModal(false);

            /* crea i campi data e li inserisce */
            Pannello pan = PannelloFactory.orizzontale(null);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            campo = CampoFactory.data(NOME_CAMPO_DATA_1);
            pan.add(campo);
            campo = CampoFactory.data(NOME_CAMPO_DATA_2);
            pan.add(campo);
            JButton bot = new JButton("Genera OdG");
            this.setBotGenera(bot);
            bot.addActionListener(new AzGeneraOdg());
            bot.setOpaque(false);
            pan.add(bot);
            this.addPannello(pan);

            /* inserisce il Navigatore nel dialogo */
            Modulo mod = OdgModulo.get();
            nav = mod.getNavigatore(Odg.Nav.navDoppio.get());
            nav.avvia();
            this.addComponente(nav.getPortaleNavigatore());

            /**
             * si registra presso il modulo albergo per
             * essere informato quando cambia l' azienda
             */
            mod = AlbergoModulo.get();
            mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());

            /* regola il riferimento al navigatore master delle date */
            Navigatore navMaster = nav.getNavMaster();
            this.setNavMaster(navMaster);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo inizia


    @Override
    protected void eventoUscitaCampoModificato(Campo campo) {

        try { // prova ad eseguire il codice

            /* se modifico la prima data la ricopio nella seconda */
            if (this.isCampo(campo, NOME_CAMPO_DATA_1)) {
                this.setValore(NOME_CAMPO_DATA_2, this.getData1());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    @Override
    public void sincronizza() {
        /* variabili e costanti locali di lavoro */

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* abilitazione bottone Genera Odg*/
            Date d1, d2;
            d1 = this.getData1();
            d2 = this.getData2();
            this.getBotGenera().setEnabled(Lib.Data.isSequenza(d1, d2));

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }




    /**
     * Genera gli ODG per tutti i giorni nell'intervallo corrente.
     * <p/>
     */
    private void generaOdgs() {
        /* variabili e costanti locali di lavoro */
        int[] codici;
        Navigatore nav = this.getNavMaster();

        try {    // prova ad eseguire il codice

            /* genera o rigenera gli Odg e acquisisce i codici generati */
            codici = OdgLogica.generaOdgs(getData1(), getData2());

            /* crea un filtro per far vedere solo i record generati */
            Filtro filtro = FiltroFactory.elenco(OdgModulo.get(), codici);
            nav.setFiltroCorrente(filtro);
            nav.aggiornaLista();

            /* se ha generato un solo record lo seleziona nella lista */
            if (codici.length==1) {
                nav.getLista().setRecordSelezionato(codici[0]);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna la prima data.
     * <p/>
     *
     * @return la prima data
     */
    private Date getData1() {
        return this.getData(NOME_CAMPO_DATA_1);
    }


    /**
     * Ritorna la seconda data.
     * <p/>
     *
     * @return la seconda data
     */
    private Date getData2() {
        return this.getData(NOME_CAMPO_DATA_2);
    }


    private JButton getBotGenera() {
        return botGenera;
    }


    private void setBotGenera(JButton botGenera) {
        this.botGenera = botGenera;
    }


    private Navigatore getNavMaster() {
        return navMaster;
    }


    private void setNavMaster(Navigatore navMaster) {
        this.navMaster = navMaster;
    }


    /**
     * Azione Genera ODG.
     */
    private final class AzGeneraOdg implements ActionListener {

        public void actionPerformed(ActionEvent actionEvent) {
            generaOdgs();
        }
    } // fine della classe 'interna'

    /**
     * Azione per cambiare azienda
     */
    private class AzioneCambioAzienda extends CambioAziendaAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         *
         * @param unEvento evento che causa l'azione da eseguire
         */
        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            int a = 87;     // todo se dovesse servire...
        }
    } // fine della classe interna



    /**
     * Crea una istanza del dialogo o la rende visibile se esiste già
     * <p/>
     *
     * @return l'istanza del dialogo
     */
    public static OdgDialogo getIstanza() {

        if (ISTANZA == null) {
            ISTANZA = new OdgDialogo();
            ISTANZA.avvia();
        }// fine del blocco if

        ISTANZA.getDialogo().setVisible(true);
        ISTANZA.getNavMaster().aggiornaLista();

        return ISTANZA;
    } /* fine del metodo getter */


}// fine della classe