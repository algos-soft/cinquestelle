/**
 * Copyright: Copyright (c) 2005
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      25-gen-2005
 */
package it.algos.base.campo.logica;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoBase;
import it.algos.base.errore.Errore;
import it.algos.base.evento.form.FormModificatoAz;
import it.algos.base.evento.form.FormModificatoEve;
import it.algos.base.evento.navigatore.NavStatoAz;
import it.algos.base.evento.navigatore.NavStatoEve;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.navigatore.Navigatore;
import it.algos.base.portale.PortaleLista;
import it.algos.base.portale.PortaleScheda;
import it.algos.base.progetto.Progetto;
import it.algos.base.scheda.Scheda;

import javax.swing.border.Border;
import java.util.HashMap;

/**
 * //@TODO DESCRIZIONE SINTETICA DELLA CLASSE .
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 25-gen-2005 ore 14.45.41
 */
public final class CLNavigatore extends CLBase {

    /**
     * Riferimento al Navigatore del Modulo dipendente
     */
    private Navigatore navigatore = null;

    /**
     * Nome del modulo dal quale recuperare il navigatore
     */
    private String nomeModulo;


    /**
     * Costruttore base senza parametri.
     * <p/>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo <br>
     * Utilizza eventuali valori di default <br>
     */
    public CLNavigatore() {
        /* rimanda al costruttore di questa classe */
        this(null);
    }// fine del metodo costruttore base


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param unCampoParente campo 'contenitore' di questo oggetto
     */
    public CLNavigatore(Campo unCampoParente) {
        /* rimanda al costruttore della superclasse */
        super(unCampoParente);

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
    }


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alla impostazione
     * corrente delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto <br>
     * Viene eseguito una sola volta <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Border bordo;
        PortaleLista pl;
        String nomeMod;
        Modulo mod;

        super.inizializza();

        try { // prova ad eseguire il codice
            nav = this.getNavigatore();

            /* se non trova un navigatore registrato
            * cerca di recuperarlo dal modulo*/
            if (nav == null) {
                nomeMod = this.getNomeModulo();
                if (Lib.Testo.isValida(nomeMod)) {
                    mod = Progetto.getModulo(nomeMod);
                    if (mod != null) {
                        nav = mod.getNavigatoreDefault();
                        this.setNavigatore(nav);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if


            if (nav != null) {

                /* Effettua le regolazioni obbligatorie per un navigatore
                 * inserito in un campo.
                 * Per sicurezza vengono eseguite in inizializza
                 * in modo da sovrascrivere eventuali regolazioni errate
                 * fatte dal programmatore */

//                /* Assegna un bordo al Portale Navigatore */
//                bordo = BorderFactory.createEtchedBorder();
//                nav.getPortaleNavigatore().setBorder(bordo);

                /* regola inizialmente il campo pilota nel Navigatore */
                nav.setCampoPilota(this.getCampoParente());

                /* aggiunge i listener a tutte le schede del Navigatore */
                this.aggiungeListener();

            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa) ogni volta che questo oggetto
     * deve <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * <p/>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
        /* variabili e costanti locali di lavoro */
        Navigatore nav;
        Modulo modNav;

        try { // prova ad eseguire il codice

            /* invoca il metodo sovrascritto della superclasse */
            super.avvia();

            nav = this.getNavigatore();

//            /* se il modulo del navigatore non è avviato lo avvia adesso */
//            modNav = nav.getModulo();
//            if (modNav!=null) {
//                if (!modNav.isAvviato()) {
//                    modNav.avvia();
//                }// fine del blocco if
//            }// fine del blocco if


            if (nav != null) {

                /* inizializza il Navigatore se non ancora inizializzato */
                if (!nav.isInizializzato()) {
                    nav.inizializza();
                }// fine del blocco if

                /** regola il campo pilota nel Navigatore
                 * va fatto ad ogni avvio perché lo stesso Navigatore potrebbe
                 * essere utilizzato alternativamente da campi diversi */
                nav.setCampoPilota(this.getCampoParente());

                this.regolaValorePilota();

                /* avvia il navigatore */
                nav.avvia();

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge alcuni listener ai componenti interni.
     * <p/>
     * Aggiunge i listener di stato al Navigatore.
     * Aggiunge i listener di modifica a tutte le schede.
     * Il Campo si registra presso ogni scheda del Navigatore
     * come interessato a certi eventi.
     */
    private void aggiungeListener() {
        /* variabili e costanti locali di lavoro */
        boolean continua = true;
        Navigatore nav = null;
        PortaleScheda ps = null;
        HashMap<String, Scheda> schede = null;

        try {    // prova ad eseguire il codice

            if (continua) {
                nav = this.getNavigatore();
                continua = (nav != null);
            }// fine del blocco if

            /* aggiunge il listener di stato al navigatore interno */
            if (continua) {
                nav.addListener(new AzNavModificato());
            }// fine del blocco if

            /* aggiunge il listener di modifica a tutte le schede del nav interno */
            if (continua) {
                ps = nav.getPortaleScheda();
                continua = (ps != null);
            }// fine del blocco if

            if (continua) {
                schede = ps.getSchede();
                continua = (ps != null);
            }// fine del blocco if

            if (continua) {
                for (Scheda scheda : schede.values()) {
                    scheda.addListener(new AzSchedaModificata());
                }
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola il valore pilota del navigatore pilotato.
     * <p/>
     * Usa il valore del campo chiave della scheda nella quale si trova questo campo.
     *
     * @return true se il valore pilota e' cambiato.
     */
    private boolean regolaValorePilota() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        boolean cambiato = false;
        Navigatore navCampo = null;
        int codice = 0;
        Campo campo;
        Scheda scheda = null;

        try {    // prova ad eseguire il codice

            campo = this.getCampoParente();
            continua = campo != null;

            if (continua) {
                scheda = campo.getScheda();
                continua = scheda != null;
            }// fine del blocco if

            if (continua) {
                codice = scheda.getCodice();
            }// fine del blocco if

            if (continua) {
                navCampo = this.getNavigatore();
                continua = navCampo != null;
            }// fine del blocco if

            if (continua) {
                cambiato = navCampo.setValorePilota(codice);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return cambiato;
    }


    /**
     * Svuota il valore del campo.
     * <p/>
     * Assegna zero come valore pilota<br>
     * Riavvia il navigatore.
     */
    public void reset() {
        this.getNavigatore().setValorePilota(0);
        this.getNavigatore().avvia();
    }


    public Navigatore getNavigatore() {
        return this.navigatore;
    }


    public void setNavigatore(Navigatore navigatore) {
        this.navigatore = navigatore;
    }


    private String getNomeModulo() {
        return nomeModulo;
    }


    /**
     * Assegna il nome del modulo per recuperare
     * il navigatore
     * <p/>
     *
     * @param nomeModulo nome del modulo
     */
    public void setNomeModulo(String nomeModulo) {
        this.nomeModulo = nomeModulo;
    }


    /**
     * Lanciata da ogni scheda del Navigatore ogni volta che
     * un campo viene modificato.
     * <p/>
     * Rilancia un evento di memoria modificata per il campo.
     */
    private class AzSchedaModificata extends FormModificatoAz {

        /**
         * Esegue l'azione
         * <p/>
         *
         * @param unEvento evento che causa l'azione da eseguire
         */
        public void formModificatoAz(FormModificatoEve unEvento) {
            try { // prova ad eseguire il codice
                getCampoParente().fire(CampoBase.Evento.memoriaModificata);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }
    } // fine della classe interna


    /**
     * Lanciata dal navigatore interno Navigatore ogni volta che
     * viene modificato.
     * <p/>
     * Rilancia un evento di stato modificato per il campo.
     */
    private class AzNavModificato extends NavStatoAz {

        /**
         * navStatoAz, da NavStatoLis.
         * </p>
         * Esegue l'azione <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void navStatoAz(NavStatoEve unEvento) {
            try { // prova ad eseguire il codice
                getCampoParente().fire(CampoBase.Evento.statoModificato);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


    } // fine della classe interna


}// fine della classe
