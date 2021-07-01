/**
 * Title:     ContoScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      9-mar-2006
 */
package it.algos.albergo.conto;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.addebito.AddebitoModulo;
import it.algos.albergo.conto.movimento.Movimento;
import it.algos.albergo.conto.pagamento.PagamentoModulo;
import it.algos.albergo.conto.sconto.ScontoModulo;
import it.algos.albergo.conto.sospeso.SospesoModulo;
import it.algos.albergo.pensioni.DialogoAddebiti;
import it.algos.albergo.pensioni.PanAddebiti;
import it.algos.albergo.pensioni.WrapAddebiti;
import it.algos.albergo.pensioni.WrapAddebitiConto;
import it.algos.base.campo.base.Campo;
import it.algos.base.database.Db;
import it.algos.base.errore.Errore;
import it.algos.base.evento.db.DbTriggerAz;
import it.algos.base.evento.db.DbTriggerEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.lista.Lista;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modello.Modello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.scheda.SchedaBase;
import it.algos.base.wrapper.CampoValore;

import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

/**
 * Scheda specifica del conto.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 9-mar-2006 ore 14.39.46
 */
public final class ContoScheda extends SchedaBase implements Conto {

    /**
     * pannello specializzato per la visualizzazionde dei totali del conto
     */
    private ContoPanTotali panTotali;

    /**
     * riferimento al pannello di editing addebiti
     */
    private PanAddebiti panAddebiti;


    /**
     * Bottone per aprire lo storico del cliente
     */
    private JButton botStorico;

    /**
     * Costruttore completo
     * <p/>
     *
     * @param modulo di riferimento
     */
    public ContoScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
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
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Modello modello;
        AzTriggerMovimento azione;

        super.inizializza();

        try { // prova ad eseguire il codice

            /* aggiunge un listener ai modelli movimento per aggiornare
             * i totali nella scheda quando cambia qualcosa */
            azione = new AzTriggerMovimento();
            modello = AddebitoModulo.get().getModello();
            modello.addListener(Modello.Evento.trigger, azione);
            modello = PagamentoModulo.get().getModello();
            modello.addListener(Modello.Evento.trigger, azione);
            modello = ScontoModulo.get().getModello();
            modello.addListener(Modello.Evento.trigger, azione);
            modello = SospesoModulo.get().getModello();
            modello.addListener(Modello.Evento.trigger, azione);


            /* aggiunge il bottone Storico alla scheda */
            JButton bot = AlbergoLib.addBotInfoScheda(this);
            bot.addActionListener(new AzInfoStorico());
            this.setBotStorico(bot);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    public void avvia(int codice) {
        super.avvia(codice);
        try { // prova ad eseguire il codice

            this.getPanTotali().avvia(codice);
            
            // GESTIONE MULTIAZIENDA DISABLED 10-2017 ALEX
            Campo campo = getCampo(Cam.azienda);
            campo.setValore(1);
            campo.setVisibile(false);
            // END DISABLED

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea le pagine della scheda.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     */
    protected void creaPagine() {
        Pagina pag;
        Pannello panGen;
        Pannello panDate;
        Pannello panValidita;
        Pannello pan;
        Pannello pan1;
        Pannello pan2;
        Pannello panPag;
        Pannello panSx;
        Pannello panDx;
        ContoPanTotali panTotali;

        try { // prova ad eseguire il codice

            /* pannello generale */
            pan1 = PannelloFactory.orizzontale(this);
            pan1.setAllineamento(Layout.ALLINEA_CENTRO);
            pan1.add(Cam.pagante);
            pan1.add(Cam.chiuso);
            pan2 = PannelloFactory.orizzontale(this);
            pan2.add(Cam.camera);
            pan2.add(Cam.numPersone);
            pan2.add(Cam.azienda.get());
            
            panGen = PannelloFactory.verticale(this);
            panGen.creaBordo();
            panGen.add(pan1);
            panGen.add(pan2);
            panGen.add(Cam.sigla);

            /* pannello date */
            panDate = PannelloFactory.orizzontale(this);
            panDate.creaBordo("date");
            panDate.add(Cam.dataApertura);
            panDate.add(Cam.arrivoCon);
            panDate.add(Cam.dataChiusura);

//            /* pannello camera .. date */
//            panClienteDate = PannelloFactory.verticale(this);
//            panClienteDate.add(panGen);
//            panClienteDate.add(panDate);

//            /* pannello generale sopra */
//            panSopra =  PannelloFactory.orizzontale(this);
//            panSopra.add(panClienteDate);
//            panSopra.add(Cam.presenze);

//            /* pannello stato (chiuso/pagato) */
//            panStato = PannelloFactory.orizzontale(this);
//            panStato.creaBordo("stato");
//            panStato.sbloccaDim();
//            panStato.add(Cam.chiuso);
//            panStato.add(Cam.dataChiusura);

//            /* pannello note */
//            panNote = PannelloFactory.orizzontale(this);
//            panNote.creaBordo("note");
//            panNote.sbloccaDim();
//            panNote.add(Cam.note);

            /* pannello totali*/
            panTotali = new ContoPanTotali(false);
            this.setPanTotali(panTotali);

//            /* pannello composito 2*/
//            panComp2 = PannelloFactory.verticale(this);
//            panComp2.add(Cam.note);
////            panComp2.add(panStato);
//
//            panComp1 = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
//            panComp1.add(panComp2);
//            panComp1.add(panTotali);

            panSx = PannelloFactory.verticale(this);
            panSx.add(panGen);
            panSx.add(panDate);
            panSx.add(Cam.note);

            panDx = PannelloFactory.verticale(this);
            panDx.add(Cam.presenze);
            panDx.add(panTotali);

            pag = this.addPagina("generale");
            panPag = PannelloFactory.orizzontale(this);
            panPag.add(panSx);
            panPag.add(panDx);
            pag.add(panPag);


            pag = this.addPagina("Addebiti");
            pag.add(Cam.addebiti);

            pag = this.addPagina("Pagamenti/Sconti/Sospesi");
            pag.add(Cam.pagamenti);

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.sconti);
            pan.add(Cam.sospesi);
            pag.add(pan);

            pag = this.addPagina("Addebiti continuativi");

            panValidita = PannelloFactory.orizzontale(this);
            panValidita.creaBordo("Validità");
            panValidita.add(Cam.validoDal);
            panValidita.add(Cam.validoAl);

            JButton bot = new JButton("Modifica addebiti");
            bot.addActionListener(new AzBotModifica());

            pan = PannelloFactory.orizzontale(this);
            pan.setAllineamento(Layout.ALLINEA_BASSO);
            pan.add(panValidita);
            pan.add(bot);

            pag.add(pan);
            pag.add(Cam.addebitifissi);


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Metodo eseguito quando un campo modificato perde il fuoco.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     *
     * @param campo interessato
     */
    protected void eventoMemoriaModificata(Campo campo) {
        /* variabili e costanti locali di lavoro */

        super.eventoMemoriaModificata(campo);

        try { // prova ad eseguire il codice

            /* se ho modificato il cliente o la camera sincronizza la sigla */
            if (this.isCampo(campo, Cam.pagante, Cam.camera)) {
                this.syncSigla();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    protected void eventoUscitaCampoModificato(Campo campo) {

        /** se modifico data di arrivo, la data di partenza o il numero di persone
         * ed esistono già degli addebiti previsti, avvisa, chiede conferma e li modifica */
        if (this.isCampo(campo, Conto.Cam.validoDal, Conto.Cam.validoAl)) {
            this.checkAddebiti();
        }// fine del blocco if

    }


    /**
     * Sincronizza il valore del campo Sigla con i dati presenti nella scheda.
     * <p/>
     */
    private void syncSigla() {
        /* variabili e costanti locali di lavoro */
        int cliente;
        int camera;
        String sigla;

        try {    // prova ad eseguire il codice
            cliente = this.getInt(Cam.pagante.get());
            camera = this.getInt(Cam.camera.get());
            sigla = ContoModulo.creaSigla(camera, cliente);
            this.setValore(Cam.sigla.get(), sigla);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Controlla la sincronizzazione addebiti.
     * <p/>
     * Controlla se esistono addebiti previsti
     * Chiede conferma per la sincronizzazione
     * Esegue la sincronizzazione
     */
    private void checkAddebiti() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        MessaggioDialogo dialogo;

        try {    // prova ad eseguire il codice

            /* controlla se esistono addebiti */
            continua = this.isEsistonoAddebitiContinuativi();

            /* chiede conferma */
            if (continua) {
                dialogo = new MessaggioDialogo(
                        "Attenzione! Il periodo è stato modificato.\nModifico anche gli addebiti?");
                continua = dialogo.isConfermato();
            }

            /* esegue */
            if (continua) {
                this.syncAddebitiContinuativi();
            }// fine del blocco if

            /* esegue */
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Controlla se per il conto corrente esistono già degli
     * addebiti continuativi.
     * <p/>
     *
     * @return true se esistono
     */
    private boolean isEsistonoAddebitiContinuativi() {
        /* variabili e costanti locali di lavoro */
        boolean esistono = false;
        Navigatore nav;
        Lista lista;
        int quanti;

        try {    // prova ad eseguire il codice
            nav = this.getNavAddebitiContinuativi();
            lista = nav.getLista();
            quanti = lista.getNumRecordsDisponibili();
            esistono = (quanti > 0);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esistono;
    }


    /**
     * Sincronizza gli Addebiti Continuativi con date e persone.
     * <p/>
     */
    private void syncAddebitiContinuativi() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        int codConto;
        WrapAddebiti wrapAddebiti;
        Date dataIni;
        Date dataFinePeriodo;
        Date dataFineAddebiti;
        int persone;
        boolean riuscito;

        try { // prova ad eseguire il codice
            codConto = this.getCodice();
            continua = (codConto > 0);

            /* esegue */
            if (continua) {
                dataIni = this.getData(Conto.Cam.validoDal.get());
                dataFinePeriodo = this.getData(Conto.Cam.validoAl.get());
                dataFineAddebiti = Lib.Data.add(dataFinePeriodo, -1);

                dataFineAddebiti = dataFinePeriodo;

                persone = this.getInt(Conto.Cam.numPersone.get());

                wrapAddebiti = new WrapAddebitiConto(codConto, this.getConnessione());
                riuscito = wrapAddebiti.modifica(dataIni,
                        dataFineAddebiti,
                        persone,
                        this.getConnessione());

                /* ricarica la lista */
                if (riuscito) {
                    this.getNavAddebitiContinuativi().aggiornaLista();
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Restituisce testo di riferimento visualizzato
     * su tutte le pagine della scheda nella status bar.
     * <p/>
     * Sovrascritto dalle sottoclassi<br>
     * Nella classe base ritorna stringa vuota <br>
     * Sovrascrivere per personalizzare il testo mostrato nella
     * statusBar della scheda<br>
     *
     * @return il testo di riferimento
     */
    protected String getTestoRiferimento() {
        /* variabili e costanti locali di lavoro */
        String stringa = "";
        String camera = "";
        String cliente = "";
        Campo campo;
        Object valore;

        try { // prova ad eseguire il codice

            /* recupera la stringa della camera */
            campo = this.getCampo(Cam.camera);
            valore = campo.getValoreElenco();
            if (Lib.Testo.isValida(valore)) {
                camera = (String)valore;
            }// fine del blocco if

            /* recupera la stringa del cliente */
            campo = this.getCampo(Cam.pagante);
            valore = campo.getValoreElenco();
            if (Lib.Testo.isValida(valore)) {
                cliente = (String)valore;
            }// fine del blocco if

            stringa = camera;
            if (Lib.Testo.isValida(cliente)) {
                if (Lib.Testo.isValida(stringa)) {
                    stringa += " - ";
                }// fine del blocco if
                stringa += cliente;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return stringa;
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean chiuso;
        Object valore;
        Campo campo;

        super.sincronizza();

        try { // prova ad eseguire il codice

            /* recupera il valore del flag chiuso */
            campo = this.getCampo(Conto.Cam.chiuso.get());
            valore = campo.getValore();
            chiuso = Libreria.getBool(valore);

            /* abilita o disabilita tutta la scheda in funzione del flag Conto Chiuso */
            this.setModificabile(!chiuso);

            /* il flag Chiuso si può solo togliere, per metterlo
             * occorre usare la apposita procedura di chiusura conto */
            campo.setModificabile(chiuso); // modificabile solo se è chiuso

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Azione eseguita quando viene creato/modificato/cancellato
     * un movimento.
     * <p/>
     * Se il movimento contiene un importo valorizzato, aggiorna i valori
     * del pannello totali in scheda
     */
    private class AzTriggerMovimento extends DbTriggerAz {

        /**
         * Metodo invocato dal trigger.
         * <p/>
         *
         * @param evento evento che causa l'azione da eseguire <br>
         */
        public void dbTriggerAz(DbTriggerEve evento) {
            /* variabili e costanti locali di lavoro */
            Db.TipoOp tipo;

            try { // prova ad eseguire il codice

                /* recupera le informazioni dall'evento */
                tipo = evento.getTipo();

                /* nuovo movimento */
                if (tipo.equals(Db.TipoOp.nuovo)) {
                    if (isEsisteImportoValorizzato(evento.getValoriNew())) {
                        syncTotali();
                    }// fine del blocco if
                }// fine del blocco if

                /* modifica movimento */
                if (tipo.equals(Db.TipoOp.modifica)) {
                    if (isEsisteImportoValorizzato(evento.getValoriNew())) {
                        syncTotali();
                    }// fine del blocco if
                }// fine del blocco if

                /* elimina movimento */
                if (tipo.equals(Db.TipoOp.elimina)) {
                    if (isEsisteImportoValorizzato(evento.getValoriOld())) {
                        syncTotali();
                    }// fine del blocco if
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch


        }

    }


    /**
     * Controlla se in una lista di CampiValore esiste
     * il campo Importo con valore diverso da zero.
     * <p/>
     *
     * @param lista di oggetti CampoValore
     *
     * @return true se esiste il campo im porto diverso da zero
     */
    private boolean isEsisteImportoValorizzato(ArrayList<CampoValore> lista) {
        /* variabili e costanti locali di lavoro */
        boolean esiste = false;
        String nomeCampo = Movimento.Cam.importo.get();
        Campo campo;
        String unNome;
        Object valore;
        double importo;

        try {    // prova ad eseguire il codice
            for (CampoValore cv : lista) {
                campo = cv.getCampo();
                unNome = campo.getNomeInterno();
                if (unNome.equals(nomeCampo)) {
                    valore = cv.getValore();
                    importo = Libreria.getDouble(valore);
                    if (importo != 0) {
                        esiste = true;
                        break;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return esiste;
    }


    /**
     * Sincronizza i totali nel pannello totali.
     * <p/>
     */
    private void syncTotali() {
        /* variabili e costanti locali di lavoro */
        ContoPanTotali pt;

        try {    // prova ad eseguire il codice
            pt = this.getPanTotali();
            if (pt != null) {
                pt.sync();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Edita gli addebiti continuativi in un dialogo specializzato.
     * <p/>
     */
    private void editAddebiti() {
        boolean continua;
        DialogoAddebiti dialogo;
        PanAddebiti panAddebiti = null;
        int codConto = 0;
        WrapAddebiti wrapIn = null;
        WrapAddebitiConto wrapOut;
        int quanti;
        Lista lista;
        Filtro filtro;
        Date dataIni;
        Date dataEnd;
        int persone = 0;
        Navigatore nav = null;

        try { // prova ad eseguire il codice

            /* recupera il codice del conto */
            codConto = this.getCodice();
            continua = (codConto > 0);

            /**
             * Controlla se ci sono già delle righe di addebito continuativo relative al conto.
             * - se ce ne sono, crea un wrapper precaricato in base alle righe esistenti
             * - se non ce ne sono, crea un wrapper vuoto precaricato con date e persone
             */
            if (continua) {
                nav = this.getNavAddebitiContinuativi();
                lista = nav.getLista();
                filtro = lista.getFiltro();
                quanti = nav.query().contaRecords(filtro);

                if (quanti == 0) { // non ci sono righe

                    /* recupera il periodo di validità */
                    dataIni = this.getData(Cam.validoDal.get());
                    dataEnd = this.getData(Cam.validoAl.get());

                    /* controlla che ci sia il perdiodo di validità */
                    if ((Lib.Data.isValida(dataIni)) && (Lib.Data.isValida(dataEnd))) {
                        persone = this.getInt(Cam.numPersone.get());
                        wrapIn = new WrapAddebitiConto(dataIni,
                                dataEnd,
                                persone,
                                codConto,
                                this.getConnessione());
                    } else {
                        new MessaggioAvviso("Inserire prima il periodo di validità del conto.");
                        continua = false;
                    }// fine del blocco if-else
                } else { // ci sono delle righe
                    wrapIn = new WrapAddebitiConto(codConto, this.getConnessione());
                }// fine del blocco if-else
            }// fine del blocco if

            /**
             * crea un oggetto grafico PanAddebiti regolato con il precedente wrapper
             * crea un dialogo per modificare le informazioni
             * presenta il dialogo
             */
            if (continua) {

                panAddebiti = this.getPanAddebiti();
                panAddebiti.inizializza(wrapIn);

                dialogo = new DialogoAddebiti(panAddebiti);
                dialogo.avvia();
                continua = dialogo.isConfermato();

            }// fine del blocco if

            /* sostituisce gli addebiti esistenti con quelli generati dal dialogo */
            if (continua) {
//                nav.query().eliminaRecords(nav.getLista().getFiltro());
                wrapOut = panAddebiti.creaWrapAddebitiConto();
                wrapOut.write(codConto, this.getConnessione());
                nav.aggiornaLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il navigatore Addebiti Continuativi della scheda.
     * <p/>
     *
     * @return il navigatore Addebiti Continuativi
     */
    private Navigatore getNavAddebitiContinuativi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            campo = this.getCampo(Cam.addebitifissi);
            if (campo != null) {
                nav = campo.getNavigatore();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Azione del bottone Modifica Addebiti
     * </p>
     */
    private final class AzBotModifica implements ActionListener {

        public void actionPerformed(ActionEvent event) {
            editAddebiti();
        }
    } // fine della classe 'interna'


    private ContoPanTotali getPanTotali() {
        return panTotali;
    }


    private void setPanTotali(ContoPanTotali panTotali) {
        this.panTotali = panTotali;
    }


    /**
     * Recupera il pannello di editing degli addebiti continuativi
     * <p/>
     * Se non esiste lo crea ora e lo registra
     *
     * @return il pannello di editing
     */
    private PanAddebiti getPanAddebiti() {
        /* variabili e costanti locali di lavoro */
        PanAddebiti pan;

        try { // prova ad eseguire il codice
            pan = this.panAddebiti;
            if (pan == null) {
                pan = new PanAddebiti();
                this.setPanAddebiti(pan);
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return panAddebiti;
    }


    private void setPanAddebiti(PanAddebiti panAddebiti) {
        this.panAddebiti = panAddebiti;
    }


    private JButton getBotStorico() {
        return botStorico;
    }


    private void setBotStorico(JButton botStorico) {
        this.botStorico = botStorico;
    }

    /**
     * Azione Informazioni Storiche
     * </p>
     */
    private final class AzInfoStorico implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            /* variabili e costanti locali di lavoro */
            int codice;

            try { // prova ad eseguire il codice
                codice = getInt(Conto.Cam.pagante.get());
                if (codice>0) {
                    ClienteAlbergoModulo.showStorico(codice);
                }// fine del blocco if
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

        }
    } // fine della classe 'interna'


}// fine della classe
