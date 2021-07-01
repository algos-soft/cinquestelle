/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 15 nov 2006
 */

package it.algos.gestione.fattura.riga;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaBase;
import it.algos.gestione.tabelle.iva.Iva;
import it.algos.gestione.tabelle.iva.IvaModulo;

/**
 * Presentazione grafica di un singolo record di RigaFattura.
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
 * @version 1.0 / 15 nov 2006
 */
public final class RigaFatturaScheda extends SchedaBase implements RigaFattura {

    /**
     * Costruttore completo.
     *
     * @param modulo di riferimento per la scheda
     */
    public RigaFatturaScheda(Modulo modulo) {

        /* rimanda al costruttore della superclasse */
        super(modulo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni immediate di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
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
        Pannello pan;

        try { // prova ad eseguire il codice

            pag = this.addPagina("generale");

            pan = PannelloFactory.orizzontale(this);
            pan.add(Cam.descrizione);
            pan.add(this.creaPanImporti());
            pag.add(pan);

//            pan = PannelloFactory.orizzontale(this);
//            pan.add(Cam.quantita);
//            pan.add(Cam.prezzoUnitario);
//            pan.add(Cam.unita);
//            pan.add(Cam.percSconto);
//            pan.add(Cam.imponibile);
//            pan.add(Cam.codIva);
//            pag.add(pan);

//            pan = PannelloFactory.orizzontale(this);
//            pan.add(Cam.imponibile);
//            pan.add(Cam.codIva);

//            // todo provvisori
//            pan.add(Cam.flagNonImponibile);
//            pan.add(Cam.imponibile);
//            pan.add(Cam.nonImponibile);
//            pan.add(Cam.perIva);

//            pan.add(Cam.importoIva);
//            pan.add(Cam.totale);
//            pag.add(pan);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il pannello importi riga.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanImporti() {
        /* variabili e costanti locali di lavoro */
        Pannello pan = null;
        Pannello pan1 = null;
        Pannello pan2 = null;

        try {    // prova ad eseguire il codice


            pan1 = PannelloFactory.orizzontale(this);
            pan1.setUsaGapFisso(true);
            pan1.setGapPreferito(20);
            pan1.add(Cam.quantita);
            pan1.add(Cam.prezzoUnitario);
            pan1.add(Cam.percSconto);
            pan1.add(Cam.unita);

            pan2 = PannelloFactory.orizzontale(this);
            pan2.setUsaGapFisso(true);
            pan2.setGapPreferito(20);
            pan2.add(Cam.imponibile);
            pan2.add(Cam.codIva);

            pan = PannelloFactory.verticale(this);
            pan.setUsaGapFisso(true);
            pan.setGapPreferito(6);
            pan.add(pan1);
            pan.add(pan2);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pan;
    }


    /**
     * Sincronizzazione della percentuale IVA
     * <p/>
     * Invocato quando cambia il codice IVA.
     */
    private void syncIVA() {
        /* variabili e costanti locali di lavoro */
        String nomeCod = RigaFattura.Cam.codIva.get();
        String nomePerc = RigaFattura.Cam.percIva.get();
        Campo campoPerc;
        int codIva;
        double percIva;
        Modulo modIva;


        try {    // prova ad eseguire il codice

            modIva = IvaModulo.get();
            codIva = (Integer)this.getValore(nomeCod);
            percIva = modIva.query().valoreDouble(Iva.Cam.valore.get(), codIva);

            campoPerc = this.getCampo(nomePerc);
            campoPerc.setValore(percIva);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {

        super.eventoMemoriaModificata(campo);

        try { // prova ad eseguire il codice

            /* Sincronizzazione del flag Imponibile e della percentuale IVA
             * quando cambia il codice IVA. */
            if (campo.equals(this.getCampo(RigaFattura.Cam.codIva.get()))) {
                this.syncIVA();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


} // fine della classe
