package it.algos.albergo.odg.odgtesta;

import it.algos.albergo.Albergo;
import it.algos.albergo.AlbergoModulo;
import it.algos.albergo.evento.CambioAziendaAz;
import it.algos.albergo.evento.CambioAziendaEve;
import it.algos.albergo.evento.DelAziendeAz;
import it.algos.albergo.evento.DelAziendeEve;
import it.algos.albergo.odg.odgzona.OdgZona;
import it.algos.albergo.odg.odgzona.OdgZonaModulo;
import it.algos.albergo.tabelle.azienda.AziendaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.navigatore.NavigatoreFactory;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;
import it.algos.base.query.filtro.FiltroFactory;

import java.util.LinkedHashMap;

/**
 * Modulo Ordine del Giorno
 * </p>
 *
 * @author Guido Andrea Ceresa ed Alessandro Valbonesi
 * @author alex
 * @version 1.0 / 11-giu-2009 ore  16:46
 */
public final class OdgModulo extends ModuloBase implements Odg {

    /**
     * filtro per isolare l'azienda corrente
     */
    private Filtro filtroAzienda;

    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public OdgModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(Odg.NOME_MODULO);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(Odg.TITOLO_FINESTRA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public OdgModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(Odg.NOME_MODULO, unNodo);

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

        /* selezione del modello (obbligatorio) */
        super.setModello(new OdgModello());

        /* regola il titolo della finestra del navigatore */
        super.setTitoloFinestra(Odg.TITOLO_FINESTRA);

        /* regola il titolo di questo modulo nei menu di altri moduli */
        super.setTitoloMenu(Odg.TITOLO_MENU);

    }// fine del metodo inizia


    @Override
    public boolean inizializza() {
        /* variabili e costanti locali di lavoro */
        Modulo mod;

        super.inizializza();

        try { // prova ad eseguire il codice

            /* si registra presso il modulo albergo per  */
            /* essere informato quando cambia l' azienda
            /* e quando vengono eliminate aziende  */
            mod = Progetto.getModulo(Albergo.NOME_MODULO);
            if (mod != null) {
                mod.addListener(AlbergoModulo.Evento.cambioAzienda, new AzioneCambioAzienda());
                mod.addListener(AlbergoModulo.Evento.eliminaAziende, new AzioneDelAziende());
            }// fine del blocco if

            /* regola l'azienda attiva */
            this.cambioAzienda();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return true;

    }


    @Override
    protected void creaNavigatori() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;

        super.creaNavigatori();

        try { // prova ad eseguire il codice

            /* navigatore lista per le righe di testa */
            nav = new TestaNavigatore(this);
            this.addNavigatore(nav, Nav.navTesta.get());

            /* navigatore composto odg - zone ODG */
            nav = NavigatoreFactory.navigatoreNavigatore(this, Nav.navTesta.get(), OdgZona.NOME_MODULO, OdgZona.Nav.navDoppio.get());
            this.addNavigatore(nav, Nav.navDoppio.get());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * </p>
     * Metodo invocato dal ciclo inizia del costruttore <br>
     * Metodo sovrascritto nelle classi specifiche <br>
     * <p/>
     * Aggiunge alla collezione moduli (del Progetto), i moduli necessari <br>
     */
    @Override
    protected void creaModuli() {
        try { // prova ad eseguire il codice
            super.creaModulo(new OdgZonaModulo());
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * Elimina tutte le aziende tranne quella principale.
     * <p/>
     * Invocato quando viene premuto l'apposito tasto.
     */
    private void delAziende() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Filtro filtro;
        Campo campo;
        int codAzPrincipale;

        try { // prova ad eseguire il codice

            /* recupera il codice della azienda principale e controlla che esista */
            codAzPrincipale = AziendaModulo.getCodAziendaPrincipale();
            if (codAzPrincipale == 0) {
                continua = false;
            }// fine del blocco if

            /* elimina tutti i record che non appartengono alla azienda principale */
            if (continua) {
                campo = this.getCampo(Odg.Cam.azienda.get());

                // elimina tutti quelli cod cod azienda diverso
                // (non elimina con codAz = null)
                filtro = FiltroFactory.crea(campo, Filtro.Op.DIVERSO, codAzPrincipale);
                this.query().eliminaRecords(filtro);

                // elimina anche tutti quelli cod cod azienda = null
                filtro = FiltroFactory.crea(campo, 0);
                this.query().eliminaRecords(filtro);

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regola il filtro del modulo in funzione dell'azienda attiva.
     * <p/>
     */
    public void regolaFiltro() {
        Filtro filtro = null;
        AlbergoModulo modAlbergo;
        Campo campoAzienda;
        int codAz;

        try { // prova ad eseguire il codice
            modAlbergo = AlbergoModulo.get();
            if (modAlbergo != null) {
                codAz = AlbergoModulo.getCodAzienda();
                if (codAz > 0) {
                    campoAzienda = OdgModulo.get().getCampo(Odg.Cam.azienda.get());
                    filtro = FiltroFactory.crea(campoAzienda, codAz);
                }// fine del blocco if
            }// fine del blocco if

            this.setFiltroAzienda(filtro);

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
            this.getModello().setFiltroModello(this.getFiltroAzienda());
//            this.regolaFinestra();
            this.aggiornaNavigatori();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

    /**
     * Aggiorna i navigatori del modulo
     * <p/>
     */
    private void aggiornaNavigatori() {
        LinkedHashMap<String, Navigatore> navigatori;

        try { // prova ad eseguire il codice
            navigatori = this.getNavigatori();
            for (Navigatore nav : navigatori.values()) {
                nav.aggiornaLista();
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }



    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static OdgModulo get() {
        return (OdgModulo)ModuloBase.get(Odg.NOME_MODULO);
    }


    /**
     * Azione per cambiare azienda
     */
    private class AzioneCambioAzienda extends CambioAziendaAz {
        public void cambioAziendaAz(CambioAziendaEve unEvento) {
            cambioAzienda();
        }
    } // fine della classe interna


    /**
     * Azione per eliminare delle aziende
     */
    private class AzioneDelAziende extends DelAziendeAz {
        public void delAziendeAz(DelAziendeEve unEvento) {
            delAziende();
        }
    } // fine della classe interna


    private Filtro getFiltroAzienda() {
        return filtroAzienda;
    }


    private void setFiltroAzienda(Filtro filtroAzienda) {
        this.filtroAzienda = filtroAzienda;
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new OdgModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main


}// fine della classe