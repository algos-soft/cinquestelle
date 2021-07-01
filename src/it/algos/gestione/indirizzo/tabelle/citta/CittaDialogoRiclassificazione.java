package it.algos.gestione.indirizzo.tabelle.citta;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.ordine.Ordine;

import javax.swing.JLabel;

/**
 * Dialogo di impostazione della riclassificazione di una serie di città.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-mar-2008 ore 10.28.27
 */
public final class CittaDialogoRiclassificazione extends DialogoAnnullaConferma {

    /* filtro per isolare le città da riclassificare */
    private Filtro filtroDaRiclassificare;

    /* nome interno (ed etichetta) del campo città sostitutiva */
    private static final String NOME_CAMPO_CITTA = "Città sostitutiva";


    /**
     * Costruttore completo con parametri.
     *
     * @param unModulo di riferimento per la scheda
     */
    public CittaDialogoRiclassificazione(Modulo unModulo) {
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
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Pannello pan;
        Campo campo;

        try { // prova ad eseguire il codice

            this.setTitolo("Riclassificazione città");
            this.setMessaggio(
                    "Sostituisce una lista di città non verificate con una città verificata");

            pan = this.creaPanRiclassificare();
            this.addPannello(pan);
            campo = this.creaCampoCitta();
            this.addCampo(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Modulo modulo;
        Navigatore nav;

        try { // prova ad eseguire il codice

            modulo = this.getModulo();
            nav = modulo.getNavigatore(Citta.Nav.riclassificazione.toString());
            if (nav != null) {
                nav.setFiltroCorrente(this.getFiltroDaRiclassificare());
                nav.aggiornaLista();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        super.avvia();

    }// fine del metodo avvia


    /**
     * Crea il pannello città da riclassificare.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanRiclassificare() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Modulo modulo;
        Navigatore nav;

        try {    // prova ad eseguire il codice

            pan = PannelloFactory.verticale(null);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(5);
            pan.add(new JLabel("Lista delle città da riclassificare"));

            modulo = this.getModulo();
            nav = modulo.getNavigatore(Citta.Nav.riclassificazione.toString());
            if (nav != null) {
                pan.add(nav);
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Crea il campo per selezionare la città verso la quale
     * effettuare la riclassificazione.
     * <p/>
     *
     * @return il campo creato
     */
    private Campo creaCampoCitta() {
        /* variabili e costanti locali di lavoro */
        Campo campo = null;
        Filtro filtro;
        Ordine ordine;

        try {    // prova ad eseguire il codice

            campo = CampoFactory.comboLinkSel(NOME_CAMPO_CITTA);
            campo.setNomeModuloLinkato(Citta.NOME_MODULO);
            campo.setNomeVistaLinkata(Citta.Vis.citta.toString());
            campo.setNomeCampoValoriLinkato(Citta.Cam.citta.get());
            campo.setRidimensionabile(false);
            campo.setUsaNuovo(false);

            filtro = FiltroFactory.creaVero(Citta.Cam.verificato);
            campo.setFiltroBase(filtro);

            ordine = new Ordine();
            ordine.add(Citta.Cam.citta.get());
            campo.setOrdineElenco(ordine);

            campo.addColonnaCombo(Citta.Cam.cap.get());
            campo.addColonnaCombo(Citta.Cam.linkProvincia.get());
            campo.addColonnaCombo(Citta.Cam.linkNazione.get());
            campo.setLarScheda(250);
            campo.decora().estrattoSotto(Citta.Est.capProvinciaNazione);

            campo.avvia();

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return campo;
    }


    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();

            if (confermabile) {
                confermabile = (this.getCodCittaSostitutiva() > 0);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return confermabile;
    }


    /**
     * Ritorna i codici delle città da sostituire.
     * <p/>
     *
     * @return i codici delle città da sostituire
     */
    public int[] getCodCittaSostituire() {
        /* variabili e costanti locali di lavoro */
        int[] codici = new int[0];
        Modulo modulo;
        Filtro filtro;

        try {    // prova ad eseguire il codice
            modulo = this.getModulo();
            filtro = this.getFiltroDaRiclassificare();
            codici = modulo.query().valoriChiave(filtro);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codici;
    }


    /**
     * Ritorna il codice della città sostitutiva.
     * <p/>
     *
     * @return il codice della città
     */
    public int getCodCittaSostitutiva() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;

        try {    // prova ad eseguire il codice
            codice = this.getInt(NOME_CAMPO_CITTA);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    public Filtro getFiltroDaRiclassificare() {
        return filtroDaRiclassificare;
    }


    public void setFiltroDaRiclassificare(Filtro filtroDaRiclassificare) {
        this.filtroDaRiclassificare = filtroDaRiclassificare;
    }

}// fine della classe