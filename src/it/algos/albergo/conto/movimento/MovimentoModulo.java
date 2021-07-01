/**
 * Title:     MovimentoModulo
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      28-set-2007
 */
package it.algos.albergo.conto.movimento;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.conto.ContoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;

import java.util.LinkedHashMap;

/**
 * Modulo astratto Movimento
 * <p/>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 28-set-2007
 */
public abstract class MovimentoModulo extends ModuloBase {


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public MovimentoModulo() {

        super();

    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public MovimentoModulo(String nomeChiave, AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(nomeChiave, unNodo);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {

        super.setTabella(false);

    }


    /**
     * Regolazioni iniziali <i>una tantum</i>.
     * </p>
     * Metodo chiamato dalla classe Progetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Inizializza il gestore , prima di tutto (servono i Comandi per
     * inzializzare i Campi) <br>
     * Tenta di inizializzare il modulo <br>
     * Prima inizializza il modello, se e' riuscito
     * inizializza anche gli altri oggetti del modulo <br>
     *
     * @return true se il modulo e' stato inizializzato
     */
    @Override public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;

        super.inizializza();

        try { // prova ad eseguire il codice

            /* si registra presso il modulo albergo per  */
            /* sapere quando e' cambiata azienda */
            mod = Progetto.getModulo(Albergo.NOME_MODULO);
            if (mod != null) {
                mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;
    } // fine del metodo


    /**
     * Creazione e regolazione dei Navigatori.
     * <p/>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Regola il Navigatore di default <br>
     * Crea altri Navigatori (oltre a quello standard) <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        super.creaNavigatori();

        try { // prova ad eseguire il codice

            /* crea un navigatore per il conto */
            nav = this.creaNavConto();
            this.addNavigatore(nav);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Invocato prima della inizializzazione dei Navigatori.
     * <p/>
     * Opportunità per la sottoclasse di regolare i Navigatori
     * prima della inizializzazione
     */
    protected void regolaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        super.regolaNavigatori();

        try { // prova ad eseguire il codice

            /* regolazioni del navigatore di default */
            nav = this.getNavigatoreDefault();
            nav.setUsaPannelloUnico(true);
            nav.setAggiornamentoTotaliContinuo(true);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch


    }


    /**
     * Crea il Navigatore da visualizzare all'interno
     * della scheda del conto.
     * <p/>
     *
     * @return il Navigatore creato
     */
    protected Navigatore creaNavConto() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav = null;

        try {    // prova ad eseguire il codice
            nav = new MovimentoNavConto(this);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nav;
    }


    /**
     * Aggiorna i navigatori del modulo
     * <p/>
     * I Navigatori utilizzano il filtro azienda.
     */
    private void aggiornaNavigatori() {
        /* variabili e costanti locali di lavoro */
        LinkedHashMap<String, Navigatore> navigatori;
//        ContoModulo mod;

        try { // prova ad eseguire il codice
//            mod = (ContoModulo)Progetto.getModulo(Conto.NOME_MODULO);

            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
//                    nav.setFiltroBase(mod.getFiltroAzienda());
                nav.aggiornaLista();
            }

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Invocato quando cambia l'azienda attiva.
     * <p/>
     */
    private void cambioAzienda() {
        try { // prova ad eseguire il codice
            this.getModello().setFiltroModello(ContoModulo.get().getFiltroAzienda());
            this.aggiornaNavigatori();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Ritorna un filtro per identificare i dati dei quali eseguire
     * il backup per questo modulo.
     * <p/>
     *
     * @return il filtro da applicare in fase di backup
     */
    public Filtro getFiltroBackup() {
        ContoModulo modulo = ContoModulo.get();
        return modulo.getFiltroAzienda();
    }


    /**
     * Azione per cambiare azienda
     */
    private class AzioneCambioAzienda extends CambioAziendaAz {

        /**
         * cambioAziendaAz, da CambioAziendaLis.
         * </p>
         * Esegue l'azione <br>
         * Rimanda al metodo sovrascritto, nell'oggetto specifico
         * della classe che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            cambioAzienda();
        }
    } // fine della classe interna


} // fine della classe
