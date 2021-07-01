package it.algos.base.progetto;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pref.Pref;
import it.algos.base.pref.PrefGruppi;
import it.algos.base.pref.PrefTipo;
import it.algos.base.pref.PrefWrap;

import java.util.ArrayList;
import java.util.LinkedHashMap;


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
 * @version 1.0    / 16-apr-2007 ore 16.00.03
 */
public final class ProgettoSetup extends DialogoBase {

    private static final boolean BORDO = true;

    private static final boolean TITOLO = true;


    /**
     * Costruttore completo senza parametri.
     */
    public ProgettoSetup() {
        /* rimanda al costruttore della superclasse */
        super();

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
        BottoneDialogo botConferma;
        try { // prova ad eseguire il codice

            /* questo dialogo e' ridimensionabile */
            this.setRidimensionabile(true);
            this.setUsaScorrevole(true);
            this.setOrdineFisso(false);

            this.creaPanMessaggio();

            /* aggiunge il bottone chiudi (che dismette il dialogo) */
            this.add(this.getPannelloComandi());

            /* aggiunge il bottone annulla (che dismette il dialogo) */
            this.addBottoneAnnulla();

            /* aggiunge il bottone registra (che dismette il dialogo) */
            botConferma = this.addBottoneConferma();
            botConferma.setDismetti(true);  // @todo perché non funziona? gac/16-2-07

            this.setTitolo("Installazione");

            /* crea il pannello gruppi di preferenze  */
            this.getPannelloContenuto().setPreferredSize(500, 600);

            this.dialogoPreferenze();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo chiamato da altre classi ogni volta che questo oggetto deve
     * <i>ripartire</i>, per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     */
    @Override
    public void avvia() {
        super.avvia();
        if (this.isConfermato()) {
            Pref.Gen.setup.getWrap().setValore(true);
            this.registra();
        }// fine del blocco if

    }// fine del metodo avvia


    /**
     * Crea il pannello di avviso in alto.
     * <p/>
     */
    private void creaPanMessaggio() {
        /* variabili e costanti locali di lavoro */
        String messaggio;

        try {    // prova ad eseguire il codice

            messaggio = "Questa è la prima volta che il programma viene avviato";
            messaggio +=
                    "\nOccorre regolare alcune preferenze indispensabili per il suo funzionamento";
            messaggio +=
                    "\nLe preferenze possono essere successivamente modificate nel menu Preferenze";

            this.setMessaggio(messaggio);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regolazione delle preferenze indispensabili al primo avvio del programma.
     * <p/>
     * Metodo eseguito PRIMA di aver creato ed inizializzato i moduli <br>
     */
    private void dialogoPreferenze() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<PrefGruppi> lista;

        try {    // prova ad eseguire il codice
            /* recupera  le preferenze */
            lista = Pref.getPrefStartup();
            continua = (lista != null);

            if (continua) {
                /* traverso tutta la collezione */
                for (PrefGruppi pref : lista) {
                    this.creaCampoPref(pref.getWrap());
                } // fine del ciclo for-each
            }// fine del blocco if

            /* avvia i campi */
            super.avviaCampi();

            this.aggiungeListenerForm();

            /* forza la sincronizzazione */
            super.sincronizza();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea un singolo campo.
     * <p/>
     * Crea il campo per la preferenza solo se questa è visibile <br>
     * Crea il campo per la preferenza solo se questa è di livello superiore all'utente collegato <br>
     * Crea il valore di default se il flag è attivo <br>
     *
     * @param wrap di informazioni sulla singola preferenza
     */
    private void creaCampoPref(PrefWrap wrap) {
        boolean continua;
        Campo campo = null;
        Object livello;
        int livProg; // livello del programma in atto
        int livPref; // livello di questa singola preferenza
        Pref.TipoDati tipo;
        String nome;
        ArrayList<PrefTipo> lista;

        try {    // prova ad eseguire il codice
            /* recupera il livello del programma in atto*/
            livello = Pref.Gen.tipoUtente.getWrap().getValore();

            if (livello instanceof Integer) {
                livProg = (Integer)livello;
            } else {
                livProg = 1; // in caso di problemi, mostra tutte le preferenze
            }// fine del blocco if-else

            livPref = wrap.getLivello().ordinal() + 1;
            tipo = wrap.getTipoDati();
            nome = wrap.getSigla();
            lista = wrap.getLista();

            /* mostra solo le preferenze visibili */
            continua = wrap.isVisibile();

            /* mostra solo le preferenze previste per il tipo di utente attuale */
            /* valore numerico in base al popup (che parte da 1) e non all'ordinale
             * della Enumeration (che parte da 0) */
            /* il programmatore è il valore più basso */
            if (continua) {
                continua = (livPref >= livProg);
            }// fine del blocco if

            if (continua) {
                switch (tipo) {
                    case stringa:
                        campo = CampoFactory.testo(nome);
                        campo.setLarScheda(200);
                        break;
                    case intero:
                        campo = CampoFactory.intero(nome);
                        break;
                    case booleano:
                        campo = CampoFactory.checkBox(nome);
                        break;
                    case data:
                        campo = CampoFactory.data(nome);
                        break;
                    case combo:
                        campo = CampoFactory.comboInterno(nome);
                        campo.setValoriInterni(lista);
                        campo.setUsaNuovo(false);
                        campo.setUsaNonSpecificato(false);
                        campo.setLarScheda(150);
                        break;
                    case area:
                        campo = CampoFactory.testoArea(nome);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            if (continua) {
                this.regolaCampo(campo, wrap);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Regola un campo.
     * <p/>
     * Crea un pannello <br>
     * Regola alcune caratteristiche del campo <br>
     * Aggiunge il pannello al dialogo <br>
     *
     * @param campo creato e da regolare
     * @param wrap coi valori tipici del campo
     */
    private void regolaCampo(Campo campo, PrefWrap wrap) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pannello pan = null;
        LinkedHashMap<String, Campo> campi;
        String nome = "";
        String legenda = "";
        Object valore = null;
        Object standard = null;
        boolean mostraDefault = false;
        Campo campoTesto;
        ArrayList<PrefTipo> lista = null;
        PrefTipo pref;
        Pref.TipoDati tipo = null;
        int pos;

        try { // prova ad eseguire il codice

            continua = (campo != null) && (wrap != null);

            if (continua) {
                nome = campo.getNomeInterno();
            }// fine del blocco if

            if (continua) {
                legenda = wrap.getDescrizione();
                valore = wrap.getValore();
                standard = wrap.getStandard();
                mostraDefault = wrap.isMostraDefault();
                lista = wrap.getLista();
                tipo = wrap.getTipoDati();
            }// fine del blocco if

            /* regola alcuni parametri */
            if (continua) {
                campo.setValoreIniziale(valore);
                campo.decora().legenda(legenda);
            }// fine del blocco if

            if (continua) {
                /* crea il pannello grafico */
                pan = PannelloFactory.orizzontale(null);
                if (BORDO) {
                    if (TITOLO) {
                        pan.creaBordo(nome);
                        campo.decora().eliminaEtichetta();
                    } else {
                        pan.creaBordo();
                    }// fine del blocco if-else
                }// fine del blocco if

                pan.add(campo);
                this.add(pan);

                /* aggiunge il pannello alla pagina */
                this.getPrimaPagina().aggiungeComponenti(pan);
            }// fine del blocco if

            if (continua) {
                if (mostraDefault) {
                    campoTesto = CampoFactory.testo(nome + "1");

                    if (tipo == Pref.TipoDati.combo) {
                        if (standard instanceof Integer) {
                            pos = (Integer)standard;
                            if (pos > 0) {
                                pos--;
                            }// fine del blocco if
                            pref = lista.get(pos);
                            standard = pref.toString();
                        }// fine del blocco if
                    }// fine del blocco if
                    campoTesto.setValoreIniziale(standard);
                    campoTesto.setLarScheda(100);
                    campoTesto.setModificabile(false);
                    campoTesto.decora().eliminaEtichetta();
                    campoTesto.decora().legenda("Valore suggerito");
                    campoTesto.avvia();
                    pan.add(campoTesto);
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge il campo alla collezione del dialogo */
            if (continua) {
                campi = this.getCampi();
                if (campi != null) {
                    campi.put(nome, campo);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone registra.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * Regolo i valori correnti delle preferenze coi
     * contenuti dei campi del dialogo <br>
     * Registro le preferenze <br>
     */
    public void registra() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        LinkedHashMap<String, Campo> campi;
        ArrayList<PrefGruppi> lista = null;
        Campo campo;
        Object valore;
        PrefWrap wrap;

        try { // prova ad eseguire il codice

            /* recupera i campi mostrati nel dialogo */
            campi = this.getCampi();
            continua = (campi != null);

            /* recupera  le preferenze */
            if (continua) {
                lista = Pref.getPrefStartup();
                continua = (lista != null);
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (PrefGruppi pref : lista) {
                    wrap = pref.getWrap();
                    campo = campi.get(wrap.getSigla().toLowerCase());
                    if (campo != null) {
                        valore = campo.getValore();
                        wrap.setValore(valore);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

            /* registra le preferenze in maniera permanente */
            Pref.registra();

            /* ricarica per sicurezza (ed omogeneità) le preferenze */
            Pref.carica();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Creazione dei dati iniziali.
     * <p/>
     * Metodo eseguito DOPO aver creato ed inizializzato i moduli <br>
     * Esegue il metodo iniziale di istallazione per ogni modulo <br>
     */
    public void creaDati() {
        /* variabili e costanti locali di lavoro */
        ArrayList<Modulo> listaAscendente;

        try { // prova ad eseguire il codice
            /* recupera l'elenco dal basso verso l'alto */
            listaAscendente = Progetto.getModuliPostorder();

            /* traverso tutta la collezione */
            for (Modulo mod : listaAscendente) {
                if (mod != null) {
                    mod.setup();
                }// fine del blocco if
            } // fine del ciclo for-each

            /* regola la preferenza in memoria */
            Pref.Gen.setup.getWrap().setValore(true);

            /* registra tutte le preferenze (non standard) su file */
            Pref.registra();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
