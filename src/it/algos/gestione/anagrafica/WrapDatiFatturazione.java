/**
 * Title:     WrapDatiFattura
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      14-mar-2007
 */
package it.algos.gestione.anagrafica;

import it.algos.base.campo.base.Campo;
import it.algos.base.database.dati.Dati;
import it.algos.base.errore.Errore;
import it.algos.base.query.Query;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;
import it.algos.base.query.selezione.QuerySelezione;

/**
 * Wrapper contenente i dati di fatturazione specifici di un cliente.
 * I dati vengono recuperati usando la connessione del modulo Anagrafica.
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  alex
 * @version 1.0  /  14-mar-2007 ore 9.13
 */
public final class WrapDatiFatturazione implements Anagrafica {

    /* codice dell'anagrafica */
    private int codice;

    /* codice fiscale se privato, partita iva se società */
    private String picf;

    /* codice pagamento */
    private int codPag;

    /* codice iva */
    private int codIva;

    /* opzione applica rivalsa */
    private ValoriOpzione applicaRivalsa;

    /* opzione applica r.a. */
    private ValoriOpzione applicaRA;

    /* percentuale di r.a. */
    private double percRA;

    /* etichetta indirizzo principale */
    private String etichetta;


    /**
     * Costruttore completo con parametri
     * <p/>
     *
     * @param codice dell'anagrafica
     */
    public WrapDatiFatturazione(int codice) {
        /* rimanda al costruttore della superclasse */
        super();

        /* regola le variabili di istanza coi parametri */
        this.setCodice(codice);

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
        AnagraficaModulo mod;
        Query query;
        Filtro filtro;
        Dati dati;
        int intero;
        String stringa = "";
        ValoriOpzione opzione;

        try { // prova ad eseguire il codice

            mod = AnagraficaModulo.get();

            Campo cTipo = mod.getCampo(Cam.privatosocieta.get());
            Campo cPI = mod.getCampo(Cam.partitaIva.get());
            Campo cCF = mod.getCampo(Cam.codFiscale.get());
            Campo cCodPag = mod.getCampo(Cam.pagamento.get());
            Campo cCodIva = mod.getCampo(Cam.iva.get());
            Campo cApplicaRivalsa = mod.getCampo(Cam.applicaRivalsa.get());
            Campo cApplicaRA = mod.getCampo(Cam.applicaRA.get());
            Campo cPercRA = mod.getCampo(Cam.percRA.get());

            query = new QuerySelezione(mod);
            filtro = FiltroFactory.codice(mod, this.getCodice());
            query.setFiltro(filtro);
            query.addCampo(cTipo);
            query.addCampo(cPI);
            query.addCampo(cCF);
            query.addCampo(cCodPag);
            query.addCampo(cCodIva);
            query.addCampo(cApplicaRivalsa);
            query.addCampo(cApplicaRA);
            query.addCampo(cPercRA);
            dati = mod.query().querySelezione(query);

            /* recupera c.f. / p.i. a seconda del tipo */
            stringa = "";
            intero = dati.getIntAt(0, cTipo);
            Tipi tipo = Anagrafica.Tipi.getTipo(intero);
            if (tipo != null) {
                switch (tipo) {
                    case privato:
                        stringa = dati.getStringAt(0, cCF);
                        break;
                    case societa:
                        stringa = dati.getStringAt(0, cPI);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if
            this.setPicf(stringa);

            /* recupera cod. pagamento */
            this.setCodPag(dati.getIntAt(0, cCodPag));

            /* recupera cod. iva */
            this.setCodIva(dati.getIntAt(0, cCodIva));

            /* recupera opzione applica rivalsa */
            intero = dati.getIntAt(0, cApplicaRivalsa);
            opzione = ValoriOpzione.getElemento(intero);
            this.setApplicaRivalsa(opzione);

            /* recupera opzione applica r.a. */
            intero = dati.getIntAt(0, cApplicaRA);
            opzione = ValoriOpzione.getElemento(intero);
            this.setApplicaRA(opzione);

            /* recupera percentuale r.a. */
            this.setPercRA(dati.getDoubleAt(0, cPercRA));

            /* recupera l'etichetta */
            this.setEtichetta(mod.getEtichettaIndirizzoPrincipale(this.getCodice()));

            /* chiude i dati */
            dati.close();


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    private int getCodice() {
        return codice;
    }


    private void setCodice(int codice) {
        this.codice = codice;
    }


    public String getPicf() {
        return picf;
    }


    private void setPicf(String picf) {
        this.picf = picf;
    }


    public int getCodPag() {
        return codPag;
    }


    public void setCodPag(int codPag) {
        this.codPag = codPag;
    }


    public int getCodIva() {
        return codIva;
    }


    public void setCodIva(int codIva) {
        this.codIva = codIva;
    }


    public ValoriOpzione getApplicaRivalsa() {
        return applicaRivalsa;
    }


    public void setApplicaRivalsa(ValoriOpzione applicaRivalsa) {
        this.applicaRivalsa = applicaRivalsa;
    }


    public ValoriOpzione getApplicaRA() {
        return applicaRA;
    }


    public void setApplicaRA(ValoriOpzione applicaRA) {
        this.applicaRA = applicaRA;
    }


    public double getPercRA() {
        return percRA;
    }


    public void setPercRA(double percRA) {
        this.percRA = percRA;
    }


    public String getEtichetta() {
        return etichetta;
    }


    private void setEtichetta(String etichetta) {
        this.etichetta = etichetta;
    }
}// fine della classe
