package it.algos.base.campo.dati; /**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      28-nov-2007
 */
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.logica.CLEstratto;
import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.modulo.Modulo;
import it.algos.base.tavola.renderer.RendererBase;

/**
 * Campo dati Estratto;
 * <p/>
 *
 * @author Guido Andrea Ceresa
 * @author alex
 * @version 1.0  /  23 nov 2007 ore 14.23
 */
public final class CDEstrattoInterno extends CDEstratto {


    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public CDEstrattoInterno() {
        /** rimanda al costruttore di questa classe */
        this(null);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CDEstrattoInterno(Campo unCampoParente) {
        /** rimanda al costruttore della superclasse */
        super(unCampoParente);

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            Errore.crea(unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni iniziali di riferimenti e variabili
     * Metodo chiamato direttamente dal costruttore
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        try { // prova ad eseguire il codice
            this.setRenderer(new Renderer());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


    /**
     * Classe 'interna'. </p>
     */
    public final class Renderer extends RendererBase {

        /**
         * Costruttore completo con parametri.
         * <p/>
         */
        public Renderer() {
            /* rimanda al costruttore della superclasse */
            super(getCampoParente());

            try { // prova ad eseguire il codice
                /* regolazioni iniziali di riferimenti e variabili */
                this.inizia();
            } catch (Exception unErrore) { // intercetta l'errore
                new Errore(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Regolazioni iniziali di riferimenti e variabili
         * Metodo chiamato direttamente dal costruttore
         *
         * @throws Exception unaEccezione
         */
        private void inizia() throws Exception {

            try { // prova ad eseguire il codice
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        } /* fine del metodo inizia */


        /**
         * Effettua il rendering di un valore.
         * <p/>
         *
         * @param objIn valore in ingresso
         *
         * @return valore in uscita
         */
        @Override
        public Object rendValue(Object objIn) {
            /* variabili e costanti locali di lavoro */
            Object objOut = null;
            boolean continua;
            String nomeCampoVisibile = "";
            Modulo mod = null;
            CLEstratto logica = null;
            Campo unCampo;
            int codice = 0;
            String nomeLink;
            Campo campoLink = null;

            try { // prova ad eseguire il codice
                objOut = objIn;


                unCampo = this.getCampo();
                continua = (unCampo != null);

                if (continua) {
                    codice = Libreria.getInt(objIn);
                    continua = (codice > 0);
                }// fine del blocco if

                if (continua) {
                    nomeCampoVisibile = unCampo.getCampoDB().getNomeColonnaListaLinkata();
                    continua = Lib.Testo.isValida(nomeCampoVisibile);
                }// fine del blocco if

                if (continua) {
                    logica = (CLEstratto)unCampo.getCampoLogica();
                    continua = (logica != null);
                }// fine del blocco if

                if (continua) {
                    mod = logica.getModuloEsterno();
                    continua = (mod != null);
                }// fine del blocco if

                if (continua) {
                    nomeLink = nomeCampoVisibile;
                    campoLink = mod.getCampo(nomeLink);

                    while (campoLink.getCampoDB().isLinkato()) {
                        codice = mod.query().valoreInt(nomeLink, codice);
                        mod = campoLink.getCampoDB().getModuloLinkato();

                        nomeLink = campoLink.getCampoDB().getNomeColonnaListaLinkata();
                        if (Lib.Testo.isVuota(nomeLink)) {
                            nomeLink = campoLink.getCampoDB().getNomeCampoValoriLinkato();
                        }// fine del blocco if

                        if (Lib.Testo.isValida(nomeLink)) {
                            campoLink = mod.getCampo(nomeLink);
                        } else {
                            break;
                        }// fine del blocco if-else

                    }// fine del blocco while
                }// fine del blocco if

                if (continua) {
                    objOut = mod.query().valoreCampo(campoLink, codice);
                }// fine del blocco if


            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return objOut;
        }
    } // fine della classe 'interna'

}// fine della classe
