package it.algos.albergo.clientealbergo;

import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.presenza.Presenza;
import it.algos.albergo.presenza.PresenzaModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.evento.listasingola.ListaSelModAz;
import it.algos.base.evento.listasingola.ListaSelModEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.listasingola.ListaSingola;
import it.algos.base.listasingola.ListaSingolaModello;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;

import javax.swing.JComponent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * Oggetto grafico/logico delegato a visualizzare lo storico presenze
 * di un singolo cliente o di un gruppo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 31-ott-2008
 */
public final class PanStorico extends PannelloFlusso {

    /* codice del cliente attualmente osservato */
    private int codCliente;

    /* lista per gestione elenco anni */
    private ListaSingola listaAnni;

    /* Pannello placeholder per Lista Presenze  */
    private Pannello panPresenze;

    /* campo opzioni solo questo cliente o tutto il gruppo */
    private Campo campoOpzioni;


    /**
     * Costruttore completo.
     * <p/>
     */
    public PanStorico() {

        super(Layout.ORIENTAMENTO_VERTICALE);

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
        Pannello pan;
        Campo campoOpzioni;

        try { // prova ad eseguire il codice

            this.creaBordo("Storico presenze");

            this.creaListaAnni();

            /* crea il campo Opzioni */
            campoOpzioni = CampoFactory.radioInterno("Mostra");
            this.setCampoOpzioni(campoOpzioni);
            campoOpzioni.setValoriInterni("Tutto il gruppo,Solo questo cliente");
            campoOpzioni.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            campoOpzioni.setLarScheda(400);
            campoOpzioni.decora().eliminaEtichetta();
            campoOpzioni.avvia();
            campoOpzioni.addListener(new AzOpzioniModificato());
            campoOpzioni.setValore(1);

            /* costruzione grafica */
            pan = PannelloFactory.orizzontale(null);
            this.setPanPresenze(pan);

            this.add(campoOpzioni);
            pan = PannelloFactory.orizzontale(null);
            pan.add(this.getListaAnni());
            pan.add(this.getPanPresenze());
            this.add(pan);

            /*ciao*/
//            /* listener per l'evento Cambio Azienda */
//            Modulo mod = AlbergoModulo.get();
//            mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzCambioAzienda());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Popola questo oggetto con lo storico di un cliente.
     * <p/>
     *
     * @param codCliente da monitorare, 0 per svuotare lo storico
     */
    public void avvia(int codCliente) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Filtro filtroVuoto;
        Modulo mod;
        Navigatore nav;

        try { // prova ad eseguire il codice

            /* registra il codice cliente */
            this.setCodCliente(codCliente);

            /**
             * inserisce graficamente il Portale Navigatore
             * nell'apposito pannello placeholder
             * Lo devo fare ad ogni avvio se no il navigatore sparisce
             */
            this.inserisciNavigatore();

            /**
             * assegna inizialmente al Navigatore
             * un filtro che non seleziona alcun record
             */
            nav = this.getNavPresenze();
            continua = (nav != null);

            if (continua) {
                mod = nav.getModulo();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                mod = nav.getModulo();
                filtroVuoto = FiltroFactory.nessuno(mod);
                nav.setFiltroCorrente(filtroVuoto);
            }// fine del blocco if


            if (continua) {
                /* carica l'elenco degli anni */
                this.reloadAnni();

                /* all'avvio mostra sempre tutto il gruppo */
                this.getCampoOpzioni().setValore(1);

                /**
                 * Seleziona il primo anno (il più recente) nella lista degli anni
                 */
                ListaSingola listaAnni = this.getListaAnni();
                listaAnni.getLista().setSelectedIndex(0);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Inserisce il navigatore nell'apposito pannello.
     * <p/>
     */
    private void inserisciNavigatore() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pannello pan = null;
        Navigatore nav = null;
        JComponent comp;

        try {    // prova ad eseguire il codice
            pan = this.getPanPresenze();
            continua = (pan != null);

            if (continua) {
                nav = this.getNavPresenze();
                continua = (nav != null);
            }// fine del blocco if

            if (continua) {
                nav.avvia();
                comp = nav.getPortaleNavigatore();
                pan.removeAll();
                pan.add(comp);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ricarica la lista degli Anni in base alle impostazioni correnti.
     * <p/>
     */
    private void reloadAnni() {
        /* variabili e costanti locali di lavoro */
        int codCliente;
        int[] anni;

        try {    // prova ad eseguire il codice

            /* svuota l'elenco degli anni */
            this.removeAnni();

            /* riempie l'elenco degli anni*/
            codCliente = this.getCodCliente();
            if (codCliente > 0) {
                anni = this.getAnniPresenza();
                this.addAnni(anni);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Visualizza lo storico relativo al cliente, alle opzioni e agli anni selezionati.
     * <p/>
     */
    private void showStorico() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Filtro filtro;
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            filtro = this.creaFiltro();
            continua = (filtro != null);

            if (continua) {
                nav = this.getNavPresenze();
                continua = (nav != null);
            }// fine del blocco if

            if (continua) {
                nav.setFiltroCorrente(filtro);
                nav.aggiornaLista();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea il filtro per visualizzare lo storico.
     * <p/>
     *
     * @return il filtro creato
     */
    private Filtro creaFiltro() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtroClienti;
        Filtro filtroAnni;
        Filtro filtroAzienda;

        try {    // prova ad eseguire il codice

            filtroClienti = this.creaFiltroClienti();
            filtroAnni = this.creaFiltroAnni();
//            filtroAzienda = this.creaFiltroAzienda();

            filtro = new Filtro();
            filtro.add(filtroClienti);
            filtro.add(filtroAnni);
//            filtro.add(filtroAzienda);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


    /**
     * Crea il filtro per isolare i record relativi ai clienti.
     * <p/>
     *
     * @return il filtro Clienti
     */
    private Filtro creaFiltroClienti() {
        /* variabili e costanti locali di lavoro */
        Filtro filtroTot = null;
        Filtro filtro = null;
        int codCliente;
        boolean tuttoGruppo;
        int[] clienti;
        int cod;
        Modulo modClienti;

        try {    // prova ad eseguire il codice

            modClienti = ClienteAlbergoModulo.get();
            codCliente = this.getCodCliente();
            tuttoGruppo = this.isTuttoIlGruppo();

            /* crea un array con il solo cliente o con tutto il gruppo */
            if (tuttoGruppo) {
                clienti = ClienteAlbergoModulo.getCodMembri(codCliente);
            } else {
                clienti = new int[1];
                clienti[0] = codCliente;
            }// fine del blocco if-else

            filtroTot = new Filtro();
            for (int k = 0; k < clienti.length; k++) {
                cod = clienti[k];
                filtro = FiltroFactory.codice(modClienti, cod);
                filtroTot.add(Filtro.Op.OR, filtro);
            } // fine del ciclo for


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtroTot;
    }


    /**
     * Crea il filtro per isolare i record relativi agli anni
     * selezionati nella lista Anni.
     * <p/>
     *
     * @return il filtro Anni
     */
    private Filtro creaFiltroAnni() {
        /* variabili e costanti locali di lavoro */
        Filtro filtro = null;
        Filtro filtro1 = null;
        Filtro filtro2 = null;
        Filtro unFiltro = null;
        int[] anni;
        Date primoGennaio;
        Date trentunDicembre;

        try {    // prova ad eseguire il codice

            filtro = new Filtro();
            anni = this.getAnniSelezionati();
            for (int anno : anni) {
                primoGennaio = Lib.Data.getPrimoGennaio(anno);
                trentunDicembre = Lib.Data.getTrentunoDicembre(anno);
                filtro1 = FiltroFactory.crea(Presenza.Cam.entrata.get(), Filtro.Op.MAGGIORE_UGUALE, primoGennaio);
                filtro2 = FiltroFactory.crea(Presenza.Cam.entrata.get(), Filtro.Op.MINORE_UGUALE, trentunDicembre);
                unFiltro = new Filtro();
                unFiltro.add(filtro1);
                unFiltro.add(filtro2);

                filtro.add(Filtro.Op.OR, unFiltro);

            }
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return filtro;
    }


//    /**
//     * Crea il filtro per isolare i record relativi all'azienda.
//     * <p/>
//     * Se sono attive tutte le aziende ritorna filtro nullo
//     *
//     * @return il filtro Azienda
//     */
//    private Filtro creaFiltroAzienda() {
//        /* variabili e costanti locali di lavoro */
//        boolean continua;
//        Filtro filtro = null;
//        int codAzienda = 0;
//
//        try {    // prova ad eseguire il codice
//            try { // prova ad eseguire il codice
//                codAzienda = AlbergoModulo.getCodAzienda();
//                continua = true;
//            } catch (Exception unErrore) { // intercetta l'errore
//                continua = false;
//            }// fine del blocco try-catch
//
//            if (continua) {
//                if (codAzienda != 0) {
//                    filtro = FiltroFactory.crea(Presenza.Cam.azienda.get(), codAzienda);
//                }// fine del blocco if
//            }// fine del blocco if
//
//        } catch (Exception unErrore) {    // intercetta l'errore
//            Errore.crea(unErrore);
//        } // fine del blocco try-catch
//
//        /* valore di ritorno */
//        return filtro;
//    }


    /**
     * Crea e registra la lista per gli anni.
     * <p/>
     */
    private void creaListaAnni() {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;

        try {    // prova ad eseguire il codice
            listaAnni = new ListaSingola();
            this.setListaAnni(listaAnni);
            listaAnni.setPreferredWidth(60);
            listaAnni.bloccaLarMax();

            listaAnni.addListener(new AzSelLista());
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna l'elenco univoco degli anni di presenza del cliente/gruppo.
     * <p/>
     *
     * @return l'elenco univoco degli anni di presenza ordinato dall'alto in basso
     */
    private int[] getAnniPresenza() {
        /* variabili e costanti locali di lavoro */
        int[] anni = null;
        Filtro filtro;
        Filtro filtroClienti;
        Filtro filtroAzienda;
        Ordine ordine;
        Modulo modPres;
        ArrayList valori;
        Date[] date;
        Date data;
        Object ogg;
        int anno;
        ArrayList<Integer> listaAnni;

        try {    // prova ad eseguire il codice

            /* crea un filtro che isola tutte le presenze dei clienti */
            filtro = this.creaFiltroClienti();
//            filtroAzienda = this.creaFiltroAzienda();
//            filtro = new Filtro();
//            filtro.add(filtroClienti);
//            filtro.add(filtroAzienda);

            /* crea un ordine per data di entrata */
            ordine = new Ordine();
            ordine.add(Presenza.Cam.entrata.get());

            /* costruisce l'elenco di tutte le date di entrata */
            modPres = PresenzaModulo.get();
            valori = modPres.query().valoriCampo(Presenza.Cam.entrata.get(), filtro, ordine);
            date = new Date[valori.size()];
            for (int k = 0; k < valori.size(); k++) {
                ogg = valori.get(k);
                data = Libreria.getDate(ogg);
                date[k] = data;
            } // fine del ciclo for

            /* spazzola l'elenco e crea l'elenco univoco degli anni */
            listaAnni = new ArrayList<Integer>();
            for (Date unaData : date) {
                anno = Lib.Data.getAnno(unaData);
                if (!listaAnni.contains(anno)) {
                    listaAnni.add(anno);
                }// fine del blocco if
            }

            /* ordina la lista dall'alto in basso e la trasforma in array */
            Collections.sort(listaAnni);
            Collections.reverse(listaAnni);
            anni = new int[listaAnni.size()];
            for (int k = 0; k < listaAnni.size(); k++) {
                anni[k] = listaAnni.get(k);
            } // fine del ciclo for


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return anni;
    }


    /**
     * Aggiunge un anno alla lista anni.
     * <p/>
     *
     * @param anno da aggiungere
     */
    private void addAnno(int anno) {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;
        ListaSingolaModello modello;

        try {    // prova ad eseguire il codice
            listaAnni = this.getListaAnni();
            modello = listaAnni.getModello();
            modello.addElement(anno);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Aggiunge un elenco di anni alla lista anni.
     * <p/>
     *
     * @param anni da aggiungere
     */
    private void addAnni(int[] anni) {
        try {    // prova ad eseguire il codice
            for (int anno : anni) {
                this.addAnno(anno);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Svuota la lista degli anni.
     * <p/>
     */
    private void removeAnni() {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;
        ListaSingolaModello modello;

        try {    // prova ad eseguire il codice
            listaAnni = this.getListaAnni();
            modello = listaAnni.getModello();
            modello.removeAllElements();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il navigatore delle Presenze.
     * <p/>
     *
     * @return il Navigatore delle Presenze
     */
    private Navigatore getNavPresenze() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;
        Modulo modPresenza;

        try {    // prova ad eseguire il codice
            modPresenza = PresenzaModulo.get();
            if (modPresenza != null) {
                nav = modPresenza.getNavigatore(Presenza.Nav.storico.get());
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Ritorna l'elenco degli anni selezionati nella lista Anni.
     * <p/>
     *
     * @return l'elenco degli anni
     */
    private int[] getAnniSelezionati() {
        /* variabili e costanti locali di lavoro */
        int[] anni = null;
        ListaSingola listaAnni;
        ArrayList oggetti;
        Object ogg;
        int anno;

        try {    // prova ad eseguire il codice
            listaAnni = this.getListaAnni();
            oggetti = listaAnni.getOggettiSelezionati();
            anni = new int[oggetti.size()];
            for (int k = 0; k < oggetti.size(); k++) {
                ogg = oggetti.get(k);
                anno = Libreria.getInt(ogg);
                anni[k] = anno;
            } // fine del ciclo for
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return anni;
    }


    /**
     * Seleziona un elenco di anni nella lista Anni.
     * <p/>
     *
     * @param anni l'elenco degli anni
     */
    private void setAnniSelezionati(int[] anni) {
        /* variabili e costanti locali di lavoro */
        ListaSingola listaAnni;
        ListaSingolaModello modello;
        ArrayList oggetti;
        ArrayList<Integer> elemAnni;


        Object ogg;
        int anno;

        ArrayList<Integer> indici;
        int indice;
        int[] inds;

        try {    // prova ad eseguire il codice

            /* recupera la lista di interi degli anni */
            listaAnni = this.getListaAnni();
            modello = listaAnni.getModello();
            oggetti = modello.getElementi();
            elemAnni = new ArrayList<Integer>();
            for (int k = 0; k < oggetti.size(); k++) {
                ogg = oggetti.get(k);
                anno = Libreria.getInt(ogg);
                elemAnni.add(anno);
            } // fine del ciclo for

            /**
             * Spazzola la lista di anni passata, li cerca e crea una lista dei
             * corrispondenti indici
             */
            indici = new ArrayList<Integer>();
            for (int unAnno : anni) {
                indice = elemAnni.indexOf(unAnno);
                if (indice >= 0) {
                    indici.add(indice);
                }// fine del blocco if
            }

            /* trasforma in array */
            inds = new int[indici.size()];
            for (int k = 0; k < indici.size(); k++) {
                inds[k] = indici.get(k);
            } // fine del ciclo for

            /* seleziona gli elementi nella lista */
            listaAnni.getLista().setSelectedIndices(inds);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna il valore dell'opzione Solo Questo o Tutto il Gruppo.
     * <p/>
     *
     * @return true se Tutto il Gruppo, false se Solo Questo
     */
    private boolean isTuttoIlGruppo() {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;
        Campo campo;
        int scelta;

        try {    // prova ad eseguire il codice
            campo = this.getCampoOpzioni();
            scelta = campo.getInt();
            if (scelta == 1) {
                flag = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return flag;
    }


    /**
     * Selezione lista anni modificata
     * <p/>
     */
    private void selListaAnniModificata() {
        this.showStorico();
    }


    /**
     * Opzioni Tutto il Gruppo o Solo Questo Cliente modificate
     * <p/>
     */
    private void opzioniModificate() {
        /* variabili e costanti locali di lavoro */
        int opzione;
        int[] anni;
        ListaSingola listaAnni;
        int[] selIdx;

        try { // prova ad eseguire il codice
            opzione = this.getCampoOpzioni().getInt();
            if (opzione != 0) {

                /* memorizza gli annni selezionati */
                anni = this.getAnniSelezionati();

                /* ricarica la lista degli anni */
                this.reloadAnni();

                /* riseleziona gli anni precedentemente selezionati (se ci sono ancora) */
                this.setAnniSelezionati(anni);

                /* se non ha selezionato nessun anno, seleziona il primo */
                listaAnni = this.getListaAnni();
                selIdx = listaAnni.getLista().getSelectedIndices();
                if (selIdx.length == 0) {
                    listaAnni.getLista().setSelectedIndex(0);
                }// fine del blocco if

                this.showStorico();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Cambio azienda.
     * <p/>
     */
    private void cambioAzienda() {
        /* variabili e costanti locali di lavoro */

        try {    // prova ad eseguire il codice
            this.avvia(this.getCodCliente());
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Azione selezione lista anni modificata
     * </p>
     */
    private final class AzSelLista extends ListaSelModAz {

        public void listaSelModAz(ListaSelModEve unEvento) {
            selListaAnniModificata();
        }
    } // fine della classe 'interna'


    /**
     * Azione campo Opzioni modificato
     * </p>
     */
    private final class AzOpzioniModificato extends CampoMemoriaAz {

        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            opzioniModificate();
        }
    } // fine della classe 'interna'


    /**
     * Azione per cambiare azienda
     */
    private final class AzCambioAzienda extends CambioAziendaAz {

        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            cambioAzienda();
        }
    } // fine della classe interna


    private int getCodCliente() {
        return codCliente;
    }


    private void setCodCliente(int codCliente) {
        this.codCliente = codCliente;
    }


    private ListaSingola getListaAnni() {
        return listaAnni;
    }


    private void setListaAnni(ListaSingola listaAnni) {
        this.listaAnni = listaAnni;
    }


    private Pannello getPanPresenze() {
        return panPresenze;
    }


    private void setPanPresenze(Pannello panPresenze) {
        this.panPresenze = panPresenze;
    }


    private Campo getCampoOpzioni() {
        return campoOpzioni;
    }


    private void setCampoOpzioni(Campo campoOpzioni) {
        this.campoOpzioni = campoOpzioni;
    }
}// fine della classe