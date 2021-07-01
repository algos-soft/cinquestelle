package it.algos.albergo.arrivipartenze;

import it.algos.albergo.AlbergoLib;
import it.algos.albergo.clientealbergo.ClienteAlbergo;
import it.algos.albergo.clientealbergo.ClienteAlbergoModulo;
import it.algos.albergo.conto.Conto;
import it.algos.albergo.conto.ContoModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.errore.Errore;
import it.algos.base.evento.campo.BottoneAz;
import it.algos.base.evento.campo.BottoneEve;
import it.algos.base.evento.campo.CampoMemoriaAz;
import it.algos.base.evento.campo.CampoMemoriaEve;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.gestione.anagrafica.Anagrafica;

import java.util.ArrayList;

/**
 * Dialogo di conferma di un arrivo.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-ott-2007 ore 19.21.46
 */
public class PanOpzioniConto extends PannelloFlusso {


    /* campo crea nuovo conto o usa esistente */
    private Campo campoContoRadio;

    /* campo popup clienti */
    private Campo campoPopCliente;

    /* campo popup conti */
    private Campo campoPopConto;

    /* pannello contenente il campo popup cliente o conto */
    private Pannello panPops;

    /* elenco dei codici cliente disponibili per intestazione conto */
    private ArrayList<Integer> codClientiDisponibili;

    /* elenco dei codici cliente aggiunti manualmente al pannello */
    private ArrayList<Integer> codClientiAggiunti;

    /* codice del cliente di default */
    private int codClienteDefault;


    /**
     * Costruttore base senza parametri.
     * <p/>
     */
    public PanOpzioniConto() {
        /* rimanda al costruttore della superclasse */
        super(Layout.ORIENTAMENTO_VERTICALE);

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
        /* variabili e costanti locali di lavoro */
        Pannello pan;

        try { // prova ad eseguire il codice

            /* regola il layout */
            this.setUsaGapFisso(true);
            this.setGapPreferito(10);

            /* crea i campi */
            this.creaCampi();

            /* crea il pannello switch per i popups */
            pan = PannelloFactory.orizzontale(null);
            this.setPanPops(pan);

            /* crea la lista dei clienti disponibili al nuovo conto */
            this.setCodClientiDisponibili(new ArrayList<Integer>());

            /* crea la lista dei clienti aggiunti manualmente */
            this.setCodClientiAggiunti(new ArrayList<Integer>());

            /* crea il popup clienti e lo riempie */
            this.regolaPopClienti();

            /* aggiunge gli elementi grafici */
            this.add(this.getCampoContoRadio());
            this.add(this.getPanPops());

            this.syncGUI();
            this.bloccaDim();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea e registra i campi del pannello.
     * <p/>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;
        Filtro filtro;

        try {    // prova ad eseguire il codice

            /* radiobottone crea conto o usa conto esistente */
            campo = CampoFactory.radioInterno("ContoRadio");
            this.setCampoContoRadio(campo);
            campo.setValoriInterni("Crea nuovo conto,Associa a conto esistente");
            campo.setLarScheda(200);
            campo.setValoreIniziale(1);
            campo.getCampoVideo().setGapGruppo(4);
            campo.decora().eliminaEtichetta();
            campo.inizializza();
            campo.addListener(new AzRadioModificato());
            campo.avvia();

            /* popup cliente intestatario del conto */
            campo = CampoFactory.comboLinkPop("popclienti");
            this.setCampoPopCliente(campo);
            campo.setNomeModuloLinkato(ClienteAlbergo.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Anagrafica.Cam.soggetto.get());
            campo.setUsaNuovo(false);
            campo.setUsaNonSpecificato(false);
            campo.setLarScheda(180);
            campo.decora().eliminaEtichetta();
            campo.decora().bottone("addTondo16", "Intesta ad altro cliente");
            campo.setOrdineElenco(AlbergoLib.getOrdineGruppo());
            campo.inizializza();
            campo.addListener(new AzPopClientiModificato());
            campo.addListener(new AzBotAltroPremuto());
            campo.avvia();

            /* popup conti aperti per uso conto esistente */
            campo = CampoFactory.comboLinkPop("popconto");
            this.setCampoPopConto(campo);
            campo.setNomeModuloLinkato(Conto.NOME_MODULO);
            campo.setNomeCampoValoriLinkato(Conto.Cam.sigla.get());
            campo.setUsaNuovo(false);
            campo.setUsaNonSpecificato(true);
            campo.setLarScheda(180);
            campo.decora().eliminaEtichetta();
            filtro = FiltroFactory.crea(Conto.Cam.chiuso.get(), false);
            filtro.add(ContoModulo.get().getFiltroAzienda());
            campo.setFiltroCorrente(filtro);
            campo.inizializza();
            campo.addListener(new AzPopContiModificato());
            campo.avvia();


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Legge l'elenco dei clienti disponibili.
     * <p/>
     * Riempie il popup clienti del conto<br>
     * Assegna il cliente di default<br>
     */
    private void regolaPopClienti() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<Integer> listaDisponibili;
        ArrayList<Integer> listaAggiunti = null;
        ArrayList<Integer> listaTotale = null;
        int codCliDefault;
        int codCliSel = 0;

        try { // prova ad eseguire il codice

            /* elenco clienti disponibili */
            listaDisponibili = this.getCodClientiDisponibili();
            continua = (listaDisponibili != null);

            /* elenco clienti aggiunti */
            if (continua) {
                listaAggiunti = this.getCodClientiAggiunti();
                continua = (listaAggiunti != null);
            }// fine del blocco if

            /* elenco definitivo */
            if (continua) {
                listaTotale = new ArrayList<Integer>();
                listaTotale.addAll(listaDisponibili);
                listaTotale.addAll(listaAggiunti);
            }// fine del blocco if

            /* cliente selezionato di default */
            if (continua) {
                codCliSel = this.getCodCliente();
                if (codCliSel == 0) {
                    codCliDefault = this.getCodClienteDefault();
                    if (codCliDefault != 0) {
                        if (listaTotale.contains(codCliDefault)) {
                            codCliSel = codCliDefault;
                        } else {
                            if (listaTotale.size() > 0) {
                                codCliSel = listaTotale.get(0);
                            }// fine del blocco if
                        }// fine del blocco if-else
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                this.setClienti(listaTotale, codCliSel);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Radiobottone conto modificato.
     * <p/>
     */
    private void radioModificato() {
        this.syncGUI();
        this.fireModificato();
    }


    /**
     * Popup clienti modificato.
     * <p/>
     */
    private void popClientiModificato() {
        this.fireModificato();
    }


    /**
     * Popup conti modificato.
     * <p/>
     */
    private void popContiModificato() {
        this.fireModificato();
    }


    /**
     * Pulsante "intesta ad altro cliente" premuto
     * <p/>
     */
    private void botAltroClientePremuto() {
        this.intestaAltroCliente();
    }


    /**
     * Controlla se è selezionata l'opzione crea conto.
     * <p/>
     *
     * @return true se selezionata
     */
    private boolean isCreaConto() {
        return this.isCreaUsa(1);
    }


    /**
     * Controlla se è selezionata l'opzione crea conto.
     * <p/>
     *
     * @return true se selezionata
     */
    private boolean isUsaContoEsistente() {
        return this.isCreaUsa(2);
    }


    /**
     * Controlla se è selezionata l'opzione conto indicata.
     * <p/>
     *
     * @param codOpzione da controllare
     *
     * @return true se selezionata
     */
    private boolean isCreaUsa(int codOpzione) {
        /* variabili e costanti locali di lavoro */
        boolean flag = false;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoContoRadio();
            if (campo != null) {
                flag = (campo.getInt() == codOpzione);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return flag;
    }


    /**
     * Controlla se il pannello contiene dati validi.
     * <p/>
     *
     * @return true se contiene dati validi
     */
    public boolean isValido() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try {    // prova ad eseguire il codice

            if (this.isCreaConto()) {
                confermabile = (this.getCodCliente() > 0);
            }// fine del blocco if

            if (this.isUsaContoEsistente()) {
                confermabile = (this.getCodConto() > 0);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Invocato quando si preme il bottone "intesta conto ad altro cliente".
     * <p/>
     * Presenta un selettore dei clienti
     */
    private void intestaAltroCliente() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Modulo mod;
        int cod = 0;

        try { // prova ad eseguire il codice
            mod = ClienteAlbergoModulo.get();
            continua = mod != null;

            /* presenta un selettore di clienti */
            if (continua) {
                cod = mod.selezionaRecord(Anagrafica.Cam.soggetto.get(),
                        null,
                        "Seleziona il cliente al quale intestare il conto",
                        "",
                        true,
                        null,
                        ClienteAlbergo.Cam.parentela.get(),
                        ClienteAlbergo.Cam.capogruppo.get());
                continua = cod > 0;
            }// fine del blocco if

            /* aggiunge alla lista dei clienti aggiunti e lo seleziona */
            if (continua) {
                this.getCodClientiAggiunti().add(cod);
                this.regolaPopClienti();
                this.setCodCliente(cod);
                this.fireModificato();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna il codice del cliente al quale intestare il conto.
     * <p/>
     *
     * @return il codice del cliente
     */
    public int getCodCliente() {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoPopCliente();
            if (campo != null) {
                cod = campo.getInt();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Assegna il codice del cliente al quale intestare il conto.
     * <p/>
     *
     * @param codCliente il codice del cliente
     */
    private void setCodCliente(int codCliente) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoPopCliente();
            if (campo != null) {
                campo.setValore(codCliente);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Ritorna il codice del conto al quale associare l'arrivo.
     * <p/>
     *
     * @return il codice del conto
     */
    public int getCodConto() {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoPopConto();
            if (campo != null) {
                cod = campo.getInt();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Assegna il codice del conto al quale aggiungersi.
     * <p/>
     *
     * @param codConto il codice del conto
     */
    private void setCodConto(int codConto) {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try {    // prova ad eseguire il codice
            campo = this.getCampoPopConto();
            if (campo != null) {
                campo.setValore(codConto);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Assegna al popup clienti un elenco di clienti.
     * <p/>
     *
     * @param listaCod codici dei clienti da caricare nel popup
     * @param codDefault codice del cliente da selezionare di default
     */
    private void setClienti(ArrayList<Integer> listaCod, int codDefault) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Campo campoPop = null;
        Modulo mod = null;
        Filtro filtro = null;

        try {    // prova ad eseguire il codice

            continua = (listaCod != null);

            if (continua) {
                campoPop = this.getCampoPopCliente();
                continua = (campoPop != null);
            }// fine del blocco if

            if (continua) {
                mod = ClienteAlbergoModulo.get();
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
                filtro = FiltroFactory.codice(mod, 0);
            }// fine del blocco if

            if (continua) {
                for (int cod : listaCod) {
                    filtro.add(Filtro.Op.OR, FiltroFactory.codice(mod, cod));
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                campoPop.setFiltroCorrente(filtro);
                campoPop.setValore(codDefault);
                campoPop.avvia();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Sincronizza la GUI.
     * <p/>
     */
    private void syncGUI() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Pannello panPops;

        try {    // prova ad eseguire il codice

            if (this.isCreaConto()) {
                campo = this.getCampoPopCliente();
            }// fine del blocco if

            if (this.isUsaContoEsistente()) {
                campo = this.getCampoPopConto();
            }// fine del blocco if

            /* svuota il pannello e inserisce il popup appropriato */
            panPops = this.getPanPops();
            if (panPops != null) {
                panPops.removeAll();
                if (campo != null) {
                    panPops.add(campo.getPannelloCampo());
                }// fine del blocco if
                panPops.getPanFisso().revalidate();
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    private Campo getCampoContoRadio() {
        return campoContoRadio;
    }


    private void setCampoContoRadio(Campo campoContoRadio) {
        this.campoContoRadio = campoContoRadio;
    }


    private Campo getCampoPopCliente() {
        return campoPopCliente;
    }


    private void setCampoPopCliente(Campo campoPopCliente) {
        this.campoPopCliente = campoPopCliente;
    }


    private Campo getCampoPopConto() {
        return campoPopConto;
    }


    private void setCampoPopConto(Campo campoPopConto) {
        this.campoPopConto = campoPopConto;
    }


    private Pannello getPanPops() {
        return panPops;
    }


    private void setPanPops(Pannello panPops) {
        this.panPops = panPops;
    }


    private ArrayList<Integer> getCodClientiDisponibili() {
        return codClientiDisponibili;
    }


    public void setCodClientiDisponibili(ArrayList<Integer> codClientiDisponibili) {
        this.codClientiDisponibili = codClientiDisponibili;
        this.regolaPopClienti();
    }


    private ArrayList<Integer> getCodClientiAggiunti() {
        return codClientiAggiunti;
    }


    private void setCodClientiAggiunti(ArrayList<Integer> codClientiAggiunti) {
        this.codClientiAggiunti = codClientiAggiunti;
    }


    private int getCodClienteDefault() {
        return codClienteDefault;
    }


    /**
     * Assegna il codice cliente di default
     * <p/>
     * Posiziona il popup clienti sul cliente specificato
     *
     * @param codice codice del cliente di default
     */
    public void setCodClienteDefault(int codice) {
        this.codClienteDefault = codice;
        this.regolaPopClienti();
    }


    public boolean isNuovoConto() {
        /* variabili e costanti locali di lavoro */
        boolean nuovo = false;
        boolean continua;
        Campo campo;
        Object ogg;
        int val;

        try { // prova ad eseguire il codice
            campo = this.getCampoContoRadio();
            continua = (campo != null);

            if (continua) {
                ogg = campo.getValore();
                val = Libreria.getInt(ogg);
                switch (val) {
                    case 1:
                        nuovo = true;
                        break;
                    case 2:
                        nuovo = false;
                        break;
                    default: // caso non definito
                        nuovo = false;
                        break;
                } // fine del blocco switch

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return nuovo;
    }


    public int getCodContoCliente() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        boolean nuovo;

        try { // prova ad eseguire il codice
            nuovo = this.isNuovoConto();

            if (nuovo) {
                codice = this.getCodCliente();
            } else {
                codice = this.getCodConto();
            }// fine del blocco if-else

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Azione campo radio modificato
     */
    private final class AzRadioModificato extends CampoMemoriaAz {

        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            radioModificato();
        }
    } // fine della classe 'interna'


    /**
     * Azione popup clienti modificato
     */
    private final class AzPopClientiModificato extends CampoMemoriaAz {

        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            popClientiModificato();
        }
    } // fine della classe 'interna'


    /**
     * Azione popup conti modificato
     */
    private final class AzPopContiModificato extends CampoMemoriaAz {

        public void campoMemoriaAz(CampoMemoriaEve unEvento) {
            popContiModificato();
        }
    } // fine della classe 'interna'


    /**
     * Azione bottone "Intesta conto ad altro cliente" premuto
     */
    private final class AzBotAltroPremuto extends BottoneAz {

        public void bottoneAz(BottoneEve unEvento) {
            botAltroClientePremuto();
        }
    } // fine della classe 'interna'


}// fine della classe