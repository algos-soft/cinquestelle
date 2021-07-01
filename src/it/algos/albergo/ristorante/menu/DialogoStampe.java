/**
 * Title:     DialogoStampe
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-lug-2007
 */
package it.algos.albergo.ristorante.menu;

import it.algos.albergo.ristorante.lingua.Lingua;
import it.algos.albergo.ristorante.lingua.LinguaModulo;
import it.algos.albergo.ristorante.righemenutavolo.RMT;
import it.algos.albergo.ristorante.righemenutavolo.RMTModulo;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.database.dati.Dati;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Libreria;
import it.algos.base.messaggio.MessaggioDialogo;
import it.algos.base.modulo.Modulo;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Dialogo stampa menu
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-lug-2007 ore 22.46.51
 */
public final class DialogoStampe extends DialogoAnnullaConferma implements Menu {

    /* codice del menu da stampare */
    private int codice;

    /* campo tipo stampa */
    private Campo campoTipo;

    /* campo seconda lingua */
    private Campo campoLingua;

    /* campo ripeti piatti */
    private Campo campoRipetiPiatti;

    /* mappa delle lingue secondarie - la chiave è il codice record, il valore è il nome */
    private LinkedHashMap<Integer, String> lingue;


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param codiceMenu codice del menu da stampare
     */
    public DialogoStampe(int codiceMenu) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCodice(codiceMenu);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            this.setTitolo("Stampa menu");
            this.creaMappaLingue();
            this.creaCampi();
            this.addCampo(this.getCampoTipo());
            this.addCampo(this.getCampoLingua());
            this.addCampo(this.getCampoRipetiPiatti());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Crea i campi del dialogo.
     * <p/>
     */
    private void creaCampi() {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try {    // prova ad eseguire il codice

            /* campo tipo di stampa */
            campo = CampoFactory.radioInterno(Campi.tipoStampa.toString());
            campo.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            campo.setValoriInterni(Menu.TipoStampa.getElenco());
            campo.decora().obbligatorio();
            campo.decora().etichetta("Tipo di stampa");
            this.setCampoTipo(campo);

            /* campo seconda lingua */
            campo = CampoFactory.radioInterno(Campi.secondaLingua.toString());
            campo.setOrientamentoComponenti(Layout.ORIENTAMENTO_ORIZZONTALE);
            campo.setValoriInterni(this.getListaLingue());
            campo.decora().etichetta("Seconda lingua");
            this.setCampoLingua(campo);

            /* campo ripeti piatti */
            campo = CampoFactory.checkBox(Campi.ripetiPiatti.toString());
            campo.setTestoComponente("Ripeti piatti su ogni pagina");
            campo.setLarScheda(200);
            this.setCampoRipetiPiatti(campo);


        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Crea e registra la mappa delle lingue secondarie.
     * <p/>
     */
    private void creaMappaLingue() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<Integer, String> lingue = new LinkedHashMap<Integer, String>();
        Modulo modulo;
        Campo campoChiave;
        Campo campoNome;
        Campo campoPref;
        Filtro filtro;
        QuerySelezione query;
        Dati dati;
        int chiave;
        String nome;
        boolean pref;

        try {    // prova ad eseguire il codice
            modulo = LinguaModulo.get();
            campoChiave = modulo.getCampoChiave();
            campoPref = modulo.getCampoPreferito();
            campoNome = modulo.getCampo(Lingua.CAMPO_NOME);
            filtro = FiltroFactory.crea(campoPref, true);
            filtro.setInverso(true);

            query = new QuerySelezione(modulo);
            query.addCampo(campoChiave);
            query.addCampo(campoNome);
            query.addCampo(campoPref);
            dati = modulo.query().querySelezione(query);
            for (int k = 0; k < dati.getRowCount(); k++) {
                chiave = dati.getIntAt(k, campoChiave);
                nome = dati.getStringAt(k, campoNome);
                pref = dati.getBoolAt(k, campoPref);
                if (!pref) {
                    lingue.put(chiave, nome);
                }// fine del blocco if
            } // fine del ciclo for
            dati.close();

            this.setLingue(lingue);

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

    }


    /**
     * Ritorna la lista delle lingue secondarie.
     * <p/>
     *
     * @return la lista delle lingue secondarie
     */
    private ArrayList<String> getListaLingue() {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = new ArrayList<String>();

        try {    // prova ad eseguire il codice
            for (String stringa : this.getLingue().values()) {
                lista.add(stringa);
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        TipoStampa tipo = null;
        Campo campo;
        boolean visibile;

        super.sincronizza();
        try { // prova ad eseguire il codice

            /* visibilità del campo Seconda Lingua */
            tipo = this.getTipoStampa();
            visibile = false;
            if (tipo != null) {
                if (tipo.equals(TipoStampa.cliente)) {
                    visibile = true;
                }// fine del blocco if
            }// fine del blocco if
            campo = this.getCampoLingua();
            campo.setVisibile(visibile);

            /* visibilità del campo Ripeti Piatti */
            tipo = this.getTipoStampa();
            visibile = false;
            if (tipo != null) {
                if (tipo.equals(TipoStampa.servizio)) {
                    visibile = true;
                }// fine del blocco if
            }// fine del blocco if
            campo = this.getCampoRipetiPiatti();
            campo.setVisibile(visibile);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Ritorna il tipo di stampa correntemente selezionato.
     * <p/>
     *
     * @return il tipo di stampa selezionato
     */
    public TipoStampa getTipoStampa() {
        /* variabili e costanti locali di lavoro */
        TipoStampa tipo = null;
        Campo campo;
        int pos;

        try {    // prova ad eseguire il codice
            campo = this.getCampoTipo();
            pos = Libreria.getInt(campo.getValore());
            tipo = TipoStampa.getElemento(pos);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Ritorna il codice della seconda lingua correntemente selezionata.
     * <p/>
     *
     * @return il codice della seconda
     */
    public int getCodSecondaLingua() {
        /* variabili e costanti locali di lavoro */
        int cod = 0;
        Campo campo;
        int pos;
        int i = 0;

        try {    // prova ad eseguire il codice
            campo = this.getCampoLingua();
            pos = Libreria.getInt(campo.getValore());
            for (int key : this.getLingue().keySet()) {
                i++;
                if (i == pos) {
                    cod = key;
                    break;
                }// fine del blocco if
            }
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cod;
    }


    /**
     * Ritorna true se deve ripetere i piatti su ogni pagina.
     * <p/>
     *
     * @return true se deve ripetere i piatti su ogni pagina
     */
    public boolean isRipetiPiatti() {
        /* variabili e costanti locali di lavoro */
        boolean ripeti = false;
        Object valore;

        try {    // prova ad eseguire il codice
            valore = this.getCampoRipetiPiatti().getValore();
            ripeti = Libreria.getBool(valore);
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return ripeti;
    }


    public boolean isConfermabile() {
        /* variabili e costanti locali di lavoro */
        boolean confermabile = false;
        Campo campo;

        try { // prova ad eseguire il codice
            confermabile = super.isConfermabile();
            if (confermabile) {

                /* se è la stampa cliente ci vuole la seconda lingua */
                if (this.getTipoStampa().equals(TipoStampa.cliente)) {
                    campo = this.getCampoLingua();
                    if (campo.isVuoto()) {
                        confermabile = false;
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        return confermabile;
    }


    /**
     * E' stato premuto Conferma.
     * <p/>
     * Se è la stampa Servizio o Cucina, controllo se tutti hanno ordinato.
     * In caso contrario, chiedo conferma per procedere
     */
    public void eventoConferma() {
        /* variabili e costanti locali di lavoro */
        MessaggioDialogo md;
        TipoStampa tipoStampa;
        int risposta;
        boolean continua = true;

        try { // prova ad eseguire il codice

            tipoStampa = this.getTipoStampa();
            if (tipoStampa.equals(TipoStampa.servizio) || (tipoStampa.equals(TipoStampa.cucina))) {
                if (!this.isTuttiTavoliComandati()) {
                    md = new MessaggioDialogo("Non tutti i tavoli hanno ordinato." +
                            "\nVuoi stampare ugualmente?");
                    risposta = md.getRisposta();
                    if (risposta == JOptionPane.NO_OPTION) {
                        continua = false;
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


            if (continua) {
                super.eventoConferma();
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Determina se tutti i tavoli di questo menu sono stati comandati.
     * <p/>
     *
     * @return true se tutti i tavoli sono comandati
     */
    private boolean isTuttiTavoliComandati() {
        /* variabili e costanti locali di lavoro */
        boolean tuttiComandati = false;
        Modulo moduloRMT = null;
        Filtro filtro = null;
        int quanti = 0;

        try {    // prova ad eseguire il codice
            moduloRMT = RMTModulo.get();
            filtro = FiltroFactory.crea(RMT.Cam.menu.get(), this.getCodice());
            filtro.add(FiltroFactory.crea(RMT.Cam.comandato.get(), false));
            quanti = moduloRMT.query().contaRecords(filtro);
            if (quanti == 0) {
                tuttiComandati = true;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tuttiComandati;
    }


    private int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    private Campo getCampoTipo() {
        return campoTipo;
    }


    private void setCampoTipo(Campo campoTipo) {
        this.campoTipo = campoTipo;
    }


    private Campo getCampoLingua() {
        return campoLingua;
    }


    private void setCampoLingua(Campo campoLingua) {
        this.campoLingua = campoLingua;
    }


    private Campo getCampoRipetiPiatti() {
        return campoRipetiPiatti;
    }


    private void setCampoRipetiPiatti(Campo campoRipetiPiatti) {
        this.campoRipetiPiatti = campoRipetiPiatti;
    }


    private LinkedHashMap<Integer, String> getLingue() {
        return lingue;
    }


    private void setLingue(LinkedHashMap<Integer, String> lingue) {
        this.lingue = lingue;
    }


    /**
     * Classe interna Enumerazione.
     * <p/>
     * Codifica dei nomi dei campi del dialogo
     */
    private enum Campi {

        tipoStampa(),
        secondaLingua(),
        ripetiPiatti(),;

    }// fine della classe

}// fine della classe
