/**
 * Title:     IndirizzoNavAnagrafica
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      8-mar-2007
 */
package it.algos.gestione.indirizzo;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.form.Form;
import it.algos.base.messaggio.MessaggioAvviso;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.NavigatoreLS;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.toolbar.ToolBar;
import it.algos.base.wrapper.SetValori;
import it.algos.gestione.anagrafica.AnagraficaScheda;

import java.util.ArrayList;

/**
 * Navigatore degli indirizzi dentro a una scheda anagrafica.
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 3-feb-2005 ore 12.28.15
 */
public final class NavInAnagrafica extends NavigatoreLS implements Indirizzo {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param modulo il modulo di riferimento
     */
    public NavInAnagrafica(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

        /* regola le variabili di istanza coi parametri */

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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice

            this.setUsaFrecceSpostaOrdineLista(true);
            this.setUsaPannelloUnico(true);
            this.getPortaleLista().setUsaStatusBar(false);
            this.setRigheLista(3);
            this.setNomeVista(Vis.indirizziInAnag.toString());
            this.addSchedaCorrente(new IndirizzoScheda(this.getModulo()));
            this.setUsaPreferito(true);
            this.setUsaFinestraPop(true);
            this.getPortaleLista().setPosToolbar(ToolBar.Pos.nord);
            this.getPortaleLista().setEstratto(Indirizzo.Est.indirizzo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }// fine del metodo inizia


    /**
     * Invocato prima di creare un nuovo record
     * <p/>
     *
     * @param set di valori per il record da creare
     *
     * @return true se si può procedere
     */
    protected boolean nuovoRecordAnte(SetValori set) {
        /* variabili e costanti locali di lavoro */
        Object valore;
        int codAnag;
        TipiSede tipo;

        try { // prova ad eseguire il codice

            /* alla creazione nuovo indirizzo, imposta automaticamente il tipo */
            valore = set.getValore(Cam.anagrafica);
            if (valore != null) {
                codAnag = (Integer)valore;
                tipo = this.getTipoNuovoIndirizzo(codAnag);
                if (tipo != null) {
                    valore = tipo.getValore();
                    set.setValore(Cam.tipo, valore);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    }


    /**
     * Invocato prima di registrare un record esistente
     * <p/>
     *
     * @param codIndirizzo del record che sta per essere registrato
     * @param set di valori per il record da registrare
     *
     * @return true se si può procedere
     */
    protected boolean registraRecordAnte(int codIndirizzo, SetValori set) {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        int codAnag;
        Object valore;
        ArrayList lista;
        ArrayList<TipiSede> listaTipi;
        boolean privato;
        boolean principale = false;
        TipiSede tipoPrincipale;
        Filtro filtroTipo;
        Filtro filtroCodice;
        Filtro filtroEscluso;
        Filtro filtro;
        int quanti;

        try { // prova ad eseguire il codice

            privato = this.isPrivato();
            codAnag = this.getCodiceAnagrafica();

            /* se il check di sede principale viene acceso, si accerta che
             * non ci sia già un altro indirizzo contrassegnato come
             * sede principale  */
            valore = set.getValore(Cam.tipo);
            if (valore != null) {
                if (valore instanceof ArrayList) {
                    lista = (ArrayList)valore;
                    listaTipi = TipiSede.getTipiSede(lista);
                    for (TipiSede tipo : listaTipi) {
                        principale = tipo.isPrincipale(privato);
                        if (principale) {
                            break;
                        }// fine del blocco if
                    }
                }// fine del blocco if
            }// fine del blocco if

            /* si vuole registrare una sede principale;
             * controlla che non ce ne siano già altre */
            if (principale) {
                tipoPrincipale = TipiSede.getPrincipale(privato);
                filtroTipo = tipoPrincipale.getFiltro();
                filtroCodice = FiltroFactory.crea(Cam.anagrafica.get(), codAnag);
                filtroEscluso = FiltroFactory.crea(this.getModulo().getCampoChiave(),
                        Filtro.Op.DIVERSO,
                        codIndirizzo);
                filtro = new Filtro();
                filtro.add(filtroTipo);
                filtro.add(filtroCodice);
                filtro.add(filtroEscluso);
                quanti = this.query().contaRecords(filtro);
                if (quanti > 0) {
                    new MessaggioAvviso("Può esistere un solo indirizzo di tipo " +
                            tipoPrincipale.getDescrizione() +
                            "!");
                    continua = false;
                }// fine del blocco if
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return continua;
    }


    /**
     * Determina il tipo per un nuovo indirizzo.
     * <p/>
     * <p/>
     * Se è un indirizzo di Privato, e questi non ha già un indirizzo
     * di residenza, pone il tipo a Residenza.
     * Se è un indirizzo di una Società, e questa non ha già una Sede
     * Legale, pone il tipo a Sede Legale.
     *
     * @param codAnag il codice di Anagrafica
     *
     * @return il tipo da assegnare al nuovo indirizzo, null se non è
     *         necessario assegnare un tipo
     */
    private TipiSede getTipoNuovoIndirizzo(int codAnag) {
        /* variabili e costanti locali di lavoro */
        TipiSede tipo = null;
        boolean continua = true;
        boolean privato = false;
        TipiSede tipoPrincipale = null;
        Filtro filtro;
        Filtro filtroAnag;
        Filtro filtroSede;
        int quanti;

        try {    // prova ad eseguire il codice

            /* determina il tipo di indirizzo principale relativo
             * al tipo di anagrafica corrente */
            if (continua) {
                continua = false;
                privato = this.isPrivato();
                tipoPrincipale = TipiSede.getPrincipale(privato);
                if (tipoPrincipale != null) {
                    continua = true;
                }// fine del blocco if
            }// fine del blocco if

            /* determina se l'anagrafica corrente ha già un
             * indirizzo di tipo principale */
            if (continua) {
                filtroAnag = FiltroFactory.crea(Cam.anagrafica.get(), codAnag);
                filtroSede = tipoPrincipale.getFiltro();
                filtro = new Filtro();
                filtro.add(filtroAnag);
                filtro.add(filtroSede);
                quanti = this.query().contaRecords(filtro);
                if (quanti == 0) {
                    tipo = tipoPrincipale;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return tipo;
    }


    /**
     * Controlla se la scheda che contiene il navigatore
     * è di privato o di società.
     * <p/>
     *
     * @return true per privato false per società
     */
    private boolean isPrivato() {
        /* variabili e costanti locali di lavoro */
        boolean privato = false;
        AnagraficaScheda schedaAnag;

        try {    // prova ad eseguire il codice
            /* determina se si tratta di privato o società,
             * dalla scheda del Navigatore pilota */
            schedaAnag = this.getSchedaParente();
            if (schedaAnag != null) {
                privato = schedaAnag.isPrivato();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return privato;
    }


    /**
     * Recupera il codice anagrafica dalla scheda
     * che contiene questo navigatore.
     * <p/>
     *
     * @return il codice anagrafica della scheda
     */
    private int getCodiceAnagrafica() {
        /* variabili e costanti locali di lavoro */
        int codice = 0;
        AnagraficaScheda schedaAnag;

        try {    // prova ad eseguire il codice
            /* determina se si tratta di privato o società,
             * dalla scheda del Navigatore pilota */
            schedaAnag = this.getSchedaParente();
            if (schedaAnag != null) {
                codice = schedaAnag.getCodice();
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return codice;
    }


    /**
     * Recupera la scheda che contiene questo navigatore.
     * <p/>
     */
    private AnagraficaScheda getSchedaParente() {
        /* variabili e costanti locali di lavoro */
        AnagraficaScheda schedaAnag = null;
        Campo campo;
        Form form;

        try {    // prova ad eseguire il codice
            campo = this.getCampoPilota();
            form = campo.getForm();
            if (form instanceof AnagraficaScheda) {
                schedaAnag = (AnagraficaScheda)form;
            }// fine del blocco if
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return schedaAnag;
    }


}// fine della classe
