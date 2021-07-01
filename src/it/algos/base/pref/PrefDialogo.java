/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      9-feb-2007
 */
package it.algos.base.pref;

import it.algos.base.azione.adapter.AzAdapterAction;
import it.algos.base.azione.adapter.AzAdapterItem;
import it.algos.base.bottone.BottoneAzione;
import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.componente.bottone.Bottone;
import it.algos.base.componente.bottone.BottoneDialogo;
import it.algos.base.dialogo.DialogoBase;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.layout.LayoutFlusso;
import it.algos.base.libreria.Lib;
import it.algos.base.libreria.Libreria;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.pannello.PannelloFlusso;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Dialogo di visione e modifica delle preferenze del programma.
 * </p>
 * Questa classe: <ul>
 * <li> </li>
 * <li> </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 9-feb-2007 ore 8.12.14
 */
public final class PrefDialogo extends DialogoBase {

    private static final boolean BORDO = true;

    private static final boolean TITOLO = true;

    /**
     * Larghezza dei campi testo.
     */
    private static final int LAR_CAMPO_TESTO = 300;

    /**
     * Larghezza dei campi area.
     */
    private static final int LAR_CAMPO_AREA = 300;

    /**
     * Larghezza dei campi numerici.
     */
    private static final int LAR_CAMPO_NUMERO = 100;

    /**
     * Larghezza dei campi data.
     */
    private static final int LAR_CAMPO_DATA = 80;

    /**
     * Larghezza dei campi checkbox.
     */
    private static final int LAR_CAMPO_CHECK = 200;

    /**
     * Larghezza dei campi popup (combobox).
     */
    private static final int LAR_CAMPO_COMBO = 300;

    /**
     * flag per incolonnare le note esplicative a destra o sotto il campo della preferenza
     */
    private static final boolean NOTE_A_DESTRA = false;

    private LinkedHashMap<String, BottoneAzione> bottoni;

    private String gruppo;

    private Bottone bottoneDefault;


    /**
     * Costruttore completo senza parametri.
     */
    public PrefDialogo() {
        /* rimanda al costruttore della superclasse */
        super();

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
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Pannello pan;
        String cancella = "Ripristina";
        BottoneDialogo bottone;
        try { // prova ad eseguire il codice

            /* questo dialogo e' ridimensionabile */
            this.setRidimensionabile(true);
            this.setUsaScorrevole(true);
            this.setOrdineFisso(false);

            /* aggiunge il pannello (sezione) dei bottoni di comando */
            this.add(this.getPannelloComandi());

            /* aggiunge il bottone annulla (che dismette il dialogo) */
            bottone = this.addBottoneAnnulla();
            bottone.setToolTipText("Chiude il dialogo senza registrare");

            /* aggiunge il bottone ripristina/cancella (che non dismette il dialogo) */
            bottone = this.addBottoneCancella();
            bottone.setText(cancella);
            bottone.setToolTipText("Ripristina i precedenti valori registrati");

            /* aggiunge il bottone default (che non dismette il dialogo) */
            bottone = this.addDefault();
            bottone.setToolTipText("Ripristina i valori di default");

            /* aggiunge il bottone registra (che dismette il dialogo) */
            bottone = this.addBottoneRegistra();
            bottone.setToolTipText("Chiude il dialogo e registra le modifiche");

            this.setTitolo("Preferenze");

            /* crea il pannello gruppi di preferenze  */
            if (NOTE_A_DESTRA) {
                this.getPannelloContenuto().setPreferredSize(800, 500);
            } else {
                this.getPannelloContenuto().setPreferredSize(600, 500);
            }// fine del blocco if-else

            /* crea il pannello gruppi di preferenze  */
            this.creaPanGruppi();

            /* crea il pannello comandi inferiore e lo aggiunge */
            pan = this.creaPanComandiGenerali();
            this.addComponente(pan.getPanFisso());

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia


    public void avvia() {
        try { // prova ad eseguire il codice
            /* inizializza il dialogo (se non già inizializzato)*/
            if (!this.isInizializzato()) {
                this.inizializza();
            }// fine del blocco if

            super.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Crea il pannello gruppi di preferenze.
     * <p/>
     * Crea il pannello con tutti i gruppi di preferenze <br>
     * Contiene sia i gruppi (Enumeration) generali, che quelli specifici <br>
     * Il pannello viene posto in alto con sfondo verde chiaro <br>
     */
    private void creaPanGruppi() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        PannelloFlusso pan = null;
        int wGruppi;
        int wContenuto;
        LinkedHashMap<String, ArrayList<PrefGruppi>> gruppi;
        LinkedHashMap<String, BottoneAzione> bottoni = null;
        BottoneAzione bot;
        String nomeBreve;

        try {    // prova ad eseguire il codice

            gruppi = Pref.getGruppi();
            continua = (gruppi != null);

            /* crea il pannello */
            if (continua) {
                pan = new PannelloFlusso(Layout.ORIENTAMENTO_ORIZZONTALE);
                pan.setUsaGapFisso(true);
                pan.setGapPreferito(5);
            }// fine del blocco if

            /* bottoni (memorizzati, per poterli selezionare successvamente) */
            if (continua) {
                bottoni = this.setBottoni(new LinkedHashMap<String, BottoneAzione>());
            }// fine del blocco if

            /* traverso tutta la collezione */
            if (continua) {
                for (String nomePath : gruppi.keySet()) {
                    nomeBreve = Lib.Pref.getNome(nomePath);
                    bot = new BottoneAzione(this, "selezionaGruppo", nomeBreve);
                    bot.setName(nomePath);
                    bot.setOpaque(false);
                    bottoni.put(nomePath, bot);
                    pan.add(bot);
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                this.getPannelloMessaggio().add(pan);
            }// fine del blocco if


        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea il pannello comandi generali.
     * <p/>
     *
     * @return il pannello creato
     */
    private Pannello creaPanComandiGenerali() {
        /* variabili e costanti locali di lavoro */
        PannelloFlusso pannello = null;

        try {    // prova ad eseguire il codice

            /* crea il pannello */
            pannello = new PannelloFlusso(Layout.ORIENTAMENTO_VERTICALE);
            pannello.setUsaGapFisso(true);
            pannello.setGapPreferito(10);
            pannello.setRidimensionaComponenti(true);

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return pannello;
    }


    /**
     * Regola il pannello contenuto.
     * <p/>
     * Riempie il pannello contenuto con le preferenze del gruppo selezionato <br>
     */
    private void regolaPanContenuti() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        ArrayList<PrefGruppi> gruppo;

        try {    // prova ad eseguire il codice
            /* Recupera il gruppo di preferenze selezionato */
            gruppo = this.getPref();
            continua = (gruppo != null);

            if (continua) {
                for (PrefGruppi pref : gruppo) {
                    this.creaPreferenza(pref.getWrap());
                } // fine del ciclo for-each
            }// fine del blocco if

            /* avvia i campi */
            super.avviaCampi();

            this.aggiungeListenerForm();

            /* forza la sincronizzazione */
            this.sincronizza();

            /* ridisegna il dialogo */
            this.getJPanelContenuto().repaint();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Crea una singola preferenza.
     * <p/>
     * Crea il campo per la preferenza solo se questa è visibile <br>
     * Crea il campo per la preferenza solo se questa
     * è di livello superiore all'utente collegato <br>
     * Crea il valore di default se il flag è attivo <br>
     *
     * @param wrap di informazioni sulla singola preferenza
     */
    private void creaPreferenza(PrefWrap wrap) {
        boolean continua;
        Campo campo = null;
        Pref.TipoDati tipo = null;
        String nome = "";
        ArrayList<PrefTipo> lista = null;

        try {    // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (wrap != null);

            /* mostra solo le preferenze visibili */
            if (continua) {
                continua = wrap.isVisibile();
            }// fine del blocco if

            /* mostra solo le preferenze previste per il tipo di utente attuale */
            if (continua) {
                continua = this.isLivelloValido(wrap);
            }// fine del blocco if

            /* recupera alcuni parametri */
            if (continua) {
                tipo = wrap.getTipoDati();
                nome = wrap.getSigla();
                lista = wrap.getLista();
            }// fine del blocco if

            /* crea il campo base della preferenza */
            if (continua) {
                switch (tipo) {
                    case stringa:
                        campo = CampoFactory.testo(nome);
                        campo.setLarScheda(LAR_CAMPO_TESTO);
                        break;
                    case intero:
                        campo = CampoFactory.intero(nome);
                        campo.setLarScheda(LAR_CAMPO_NUMERO);
                        break;
                    case booleano:
                        campo = CampoFactory.checkBox(nome);
                        campo.setLarScheda(LAR_CAMPO_CHECK);
                        break;
                    case data:
                        campo = CampoFactory.data(nome);
                        campo.setLarScheda(LAR_CAMPO_DATA);
                        break;
                    case combo:
                        campo = CampoFactory.comboInterno(nome);
                        campo.setLarScheda(LAR_CAMPO_COMBO);
                        campo.setValoriInterni(lista);
                        campo.setUsaNuovo(false);
                        campo.setUsaNonSpecificato(false);
                        break;
                    case area:
                        campo = CampoFactory.testoArea(nome);
                        campo.setLarScheda(LAR_CAMPO_AREA);
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
            }// fine del blocco if

            /* crea e regola il pannello contenente il campo preferenza e il default */
            if (continua) {
                this.regolaPreferenza(campo, wrap);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Controlla che il livello dell'utente sia valido per la singola preferenza.
     * <p/>
     *
     * @param wrap di informazioni sulla singola preferenza
     *
     * @return vero se l'utente è abilitato per vedere questa singola preferenza
     */
    private boolean isLivelloValido(PrefWrap wrap) {
        /* variabili e costanti locali di lavoro */
        boolean valido = false;
        boolean continua;
        Object livello = null;
        int livUtente = 0; // livello dell'utente collegato
        int livPreferenza = 0; // livello di questa singola preferenza

        try {    // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (wrap != null);

            /* recupera il livello del programma in atto*/
            if (continua) {
                livello = Pref.Gen.tipoUtente.getWrap().getValore();
                continua = (livello != null);
            }// fine del blocco if

            if (continua) {
                if (livello instanceof Integer) {
                    livUtente = (Integer)livello;
                } else {
                    livUtente = 1; // in caso di problemi, mostra tutte le preferenze
                }// fine del blocco if-else
            }// fine del blocco if

            /* recupera il livello specifico della preferenza */
            if (continua) {
                livPreferenza = wrap.getLivello().ordinal() + 1;
            }// fine del blocco if

            /* mostra solo le preferenze previste per il tipo di utente attuale */
            /* valore numerico in base al popup (che parte da 1) e non all'ordinale
             * della Enumeration (che parte da 0) */
            /* il programmatore è il valore più basso */
            if (continua) {
                valido = (livPreferenza >= livUtente);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Crea e regola il pannello contenente il campo preferenza e il default.
     * <p/>
     * Crea un pannello <br>
     * Regola alcune caratteristiche del campo <br>
     * Il campo della preferenza ed il suo valore di default, sono sempre in un pannello
     * orizzontale <br>
     * Le note possono essere a fianco o sotto <br>
     * Aggiunge il pannello al dialogo <br>
     *
     * @param campo creato e da regolare
     * @param wrap  coi valori tipici del campo
     */
    private void regolaPreferenza(Campo campo, PrefWrap wrap) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pannello panPref = null;
        Pannello panGlobale = null;
        LinkedHashMap<String, Campo> campi;
        String nome = "";
        String legenda = "";
        Object valore = null;
        Object standard = null;
        String notaUte = "";
        boolean mostraDefault = false;
        Campo campoDefault = null;
        Campo campoNota = null;
        Pref.TipoDati tipo = null;
        int newLar;
        int pos;
        Object valPop;
        String legendaDefault = "Valore di default";
        String legendaPrecedenti;
        int dimLista = 0;
        ArrayList<String> lista;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (campo != null) && (wrap != null);

            if (continua) {
                nome = campo.getNomeInterno();
                continua = (Lib.Testo.isValida(nome));
            }// fine del blocco if

            if (continua) {
                legenda = wrap.getDescrizione();
                valore = wrap.getValore();
                standard = wrap.getStandard();
                notaUte = wrap.getNota();
                mostraDefault = wrap.isMostraDefault();
                tipo = wrap.getTipoDati();
            }// fine del blocco if

            /* regola alcuni parametri */
            if (continua) {
                campo.setValoreIniziale(valore);
                campo.decora().legenda(legenda);
            }// fine del blocco if

            /* crea il pannello grafico */
            if (continua) {
                if (NOTE_A_DESTRA) {
                    panGlobale = PannelloFactory.orizzontale(null);
                } else {
                    panGlobale = PannelloFactory.verticale(null);
                }// fine del blocco if-else

                if (BORDO) {
                    if (TITOLO) {
                        panGlobale.creaBordo(nome);
                        campo.decora().eliminaEtichetta();
                    } else {
                        panGlobale.creaBordo();
                    }// fine del blocco if-else
                }// fine del blocco if
            }// fine del blocco if

            /* preferenza base */
            if (continua) {
                panPref = PannelloFactory.orizzontale(null);
                panPref.add(campo);
            }// fine del blocco if

            /* valore di default */
            if (continua) {
                if (mostraDefault) {

                    if (tipo == Pref.TipoDati.stringa) {
                        if (wrap.isUsaValoriMultipli()) {
                            lista = this.getLista(wrap);
                            if (lista != null) {
                                dimLista = lista.size();
                            }// fine del blocco if

                            if (dimLista > 0) {
                                campoDefault = CampoFactory.comboInterno(nome + 1);
                                campoDefault.setValoriInterni(lista);
                                campoDefault.setValoreIniziale(1);
                                campoDefault.setUsaNuovo(false);
                                campoDefault.setUsaNonSpecificato(false);
                                legendaPrecedenti = "Eventuale valore di default e ";
                                legendaPrecedenti += "valori precedentemente utilizzati";
                                campoDefault.decora().legenda(legendaPrecedenti);
                            } else {
                                if (Lib.Testo.isValida(standard)) {
                                    campoDefault = CampoFactory.testo(nome + 1);
                                    campoDefault.setValoreIniziale(standard);
                                    campoDefault.setModificabile(false);
                                    campoDefault.decora().legenda(legendaDefault);
                                }// fine del blocco if
                            }// fine del blocco if-else
                        } else {
                            if (Lib.Testo.isValida(standard)) {
                                campoDefault = CampoFactory.testo(nome + 1);
                                campoDefault.setValoreIniziale(standard);
                                campoDefault.setModificabile(false);
                                campoDefault.decora().legenda(legendaDefault);
                            }// fine del blocco if
                        }// fine del blocco if-else
                    } else {
                        if (tipo == Pref.TipoDati.combo) {
                            campoDefault = CampoFactory.testo(nome + 1);
                            pos = Libreria.getInt(standard);
                            pos--;
                            valPop = campo.getCampoDati().getValoriInterni().get(pos);
                            campoDefault.setValoreIniziale(valPop.toString());
                            campoDefault.setModificabile(false);
                            campoDefault.decora().legenda(legendaDefault);
                        } else {
                            campoDefault = campo.clonaCampo();
                            campoDefault.setValoreIniziale(standard);
                            campoDefault.setModificabile(false);
                            campoDefault.decora().legenda(legendaDefault);
                        }// fine del blocco if-else
                    }// fine del blocco if-else

                    if (campoDefault != null) {
                        newLar = campo.getCampoScheda().getLarghezzaComponenti();
                        campoDefault.setLarScheda(newLar);
                        campoDefault.decora().eliminaEtichetta();
                        campoDefault.avvia();
                        panPref.add(campoDefault);
                    }// fine del blocco if

                    /* listener del campo pop
                     * occorre inserirlo DOPO che il campo è stato avviato */
                    if (campoDefault != null) {
                        if (tipo == Pref.TipoDati.stringa) {
                            this.addListener(campoDefault);
                        }// fine del blocco if
                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if

            /* nota esplicativa */
            if (continua) {
                if (Lib.Testo.isValida(notaUte)) {
                    campoNota = CampoFactory.testoArea(nome + "2");
                    campoNota.setValoreIniziale(notaUte);
                    if (NOTE_A_DESTRA) {
                        campoNota.setLarScheda(200);
                        campoNota.setNumeroRighe(6);
                        campoNota.decora().legenda("Nota esplicativa");
                    } else {
                        campoNota.setLarScheda(400);
                        campoNota.setNumeroRighe(3);
                    }// fine del blocco if-else
                    campoNota.setModificabile(false);
                    campoNota.decora().eliminaEtichetta();
                    campoNota.avvia();
                }// fine del blocco if
            }// fine del blocco if

            /* aggiunge il campo alla collezione del dialogo */
            if (continua) {
                campi = this.getCampi();
                if (campi != null) {
                    campi.put(nome, campo);
                }// fine del blocco if
            }// fine del blocco if

            /* compone i pannelli */
            if (continua) {
                panGlobale.add(panPref);
                panGlobale.add(campoNota);
            }// fine del blocco if

            /* aggiunge il pannello alla pagina */
            if (continua) {
                this.getPrimaPagina().aggiungeComponenti(panGlobale);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge un listener specifico al combo del campo.
     *
     * @param campoDefault a cui cui aggiungere il listener
     */
    private void addListener(Campo campoDefault) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        CampoVideo cv;
        JComponent comp = null;
        JComboBox combo;

        try { // prova ad eseguire il codice
            cv = campoDefault.getCampoVideoNonDecorato();
            continua = (cv != null);

            if (continua) {
                comp = cv.getComponente();
                continua = (comp != null);
            }// fine del blocco if

            if (continua) {
                continua = (comp instanceof JComboBox);
            }// fine del blocco if

            if (continua) {
                combo = (JComboBox)comp;
                combo.addItemListener(new AzPrecedente());
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda/dialogo.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override public void sincronizza() {
        /* variabili e costanti locali di lavoro */
        boolean diverso;
        Bottone bottoneDefault;

        try { // prova ad eseguire il codice
            super.sincronizza();

            /* controlla ed abilita il bottone */
            diverso = this.isDiversoDalDefault();
            bottoneDefault = this.getBottoneDefault();
            if (bottoneDefault != null) {
                bottoneDefault.setEnabled(diverso);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Determina se il form e' registrabile.
     * <p/>
     * Nel dialogo e' sinonimo di isConfermabile <br>
     *
     * @return true se registrabile / confermabile
     */
    @Override public boolean isRegistrabile() {
        return super.isModificata();
    }


    /**
     * Recupera la lista dei valori precedentemente utilizzati.
     * <p/>
     * Legge il file di preferenza <br>
     * Se manca, legge il valore di default <br>
     *
     * @param wrap coi valori tipici del campo
     *
     * @return lista di valori
     */
    private ArrayList<String> getLista(PrefWrap wrap) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;

        try {    // prova ad eseguire il codice
            lista = wrap.getValoriUsati();
            lista = Lib.Array.ordina(lista);
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Determina se il dialogo ha delle preferenze diverse dai valori di default.
     * <p/>
     * Controlla ogni singola preferenza <br>
     * Abilita il bottone Default per inserire i valori di default in ogni preferenza <br>
     *
     * @return true se almeno una preferenza è diversa dal valore di default
     */
    public boolean isDiversoDalDefault() {
        /* variabili e costanti locali di lavoro */
        boolean diverso = false;
        boolean continua;
        boolean uguale;
        LinkedHashMap<String, Campo> campi;
        ArrayList<PrefGruppi> gruppo = null;
        Campo campo;
        Object valCorrente;
        Object valDefault;
        PrefWrap wrap;

        try { // prova ad eseguire il codice

            /* recupera i campi mostrati nel dialogo */
            campi = this.getCampi();
            continua = (campi != null);

            /* Recupera il gruppo di preferenze selezionato */
            if (continua) {
                gruppo = this.getPref();
                continua = (gruppo != null);
            }// fine del blocco if

            if (continua) {
                continua = (gruppo.size() >= campi.size());
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (PrefGruppi pref : gruppo) {
                    wrap = pref.getWrap();
                    campo = campi.get(wrap.getSigla().toLowerCase());
                    if (campo != null) {
                        valCorrente = campo.getValore();
                        valDefault = wrap.getStandard();
                        uguale = (valCorrente.equals(valDefault));
                        if (!uguale) {
                            diverso = true;
                            break;
                        }// fine del blocco if
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if


        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return diverso;
    }


    /**
     * Metodo eseguito quando il valore memoria di un campo cambia.
     * <p/>
     * Sovrascritto dalle sottoclassi
     *
     * @param campo cambiato
     */
    @Override protected void eventoMemoriaModificata(Campo campo) {
        try { // prova ad eseguire il codice
            super.eventoMemoriaModificata(campo);
            campo.getCampoVideo().eventoMemoriaModificata();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizza il gruppo di preferenze da mostrare.
     * <p/>
     * Pulisce il pannello contenuto <br>
     * Riempie il pannello contenuto con le preferenze del gruppo selezionato <br>
     * Abilita tutti i bottoni <br>
     * Disabilità il bottone del gruppo mostrato <br>
     */
    private void sincroGruppi() {
        /* variabili e costanti locali di lavoro */
        String nomeGruppo;
        LinkedHashMap<String, BottoneAzione> bottoni;
        BottoneAzione bottone;

        try {    // prova ad eseguire il codice
            /* recupera i botttoni */
            bottoni = this.getBottoni();

            /* recupera il gruppo selezionato */
            nomeGruppo = this.getGruppo();

            /* traverso tutta la collezione */
            for (BottoneAzione bot : bottoni.values()) {
                bot.setEnabled(true);
            } // fine del ciclo for-each

            bottone = bottoni.get(nomeGruppo);
            if (bottone != null) {
                bottone.setEnabled(false);
            }// fine del blocco if

            /* cancella le preferenze del gruppo esistente */
            this.eliminaGruppoPrecedente();

            /* disegna le preferenze del gruppo */
            this.regolaPanContenuti();

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * cancella le preferenze del gruppo esistente.
     * <p/>
     */
    protected void eliminaGruppoPrecedente() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Pagina pagina;
        Component[] compPagina;
        Component[] compScroll;
        Component[] compView;
        Component comp;
        JPanel pan;
        JViewport porta;

        try { // prova ad eseguire il codice

            pagina = this.getPrimaPagina();
            compPagina = pagina.getComponents();
            continua = (compPagina != null) && (compPagina.length > 0);

            if (continua) {
                if (compPagina[0] instanceof JScrollPane) {
                    compScroll = ((JScrollPane)compPagina[0]).getComponents();

                    if (compScroll.length == 3) {
                        comp = compScroll[0];

                        if (comp instanceof JViewport) {
                            porta = (JViewport)comp;
                            compView = porta.getComponents();
                            if (compView[0] instanceof JPanel) {
                                pan = (JPanel)compView[0];
                                pan.removeAll();
                            }// fine del blocco if

                        }// fine del blocco if

                    }// fine del blocco if

                }// fine del blocco if
            }// fine del blocco if

            pagina.removeAll();
            super.eliminaCampi();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Invocato quando si preme il bottone cancella.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    @Override protected void eventoCancella() {
        super.eventoCancella();
    }


    /**
     * Invocato quando si preme il bottone registra.
     * <p/>
     * Metodo sovrascritto nelle sottoclassi <br>
     * Regolo i valori correnti delle preferenze coi
     * contenuti dei campi del dialogo <br>
     * Registro le preferenze <br>
     */
    @Override public void eventoRegistra() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        LinkedHashMap<String, Campo> campi;
        ArrayList<PrefGruppi> gruppo = null;
        Campo campo;
        Object valore;
        PrefWrap wrap;

        try { // prova ad eseguire il codice
            /* recupera i campi mostrati nel dialogo */
            campi = this.getCampi();
            continua = (campi != null);

            /* Recupera il gruppo di preferenze selezionato */
            if (continua) {
                gruppo = this.getPref();
                continua = (gruppo != null);
            }// fine del blocco if

            if (continua) {
                continua = (gruppo.size() >= campi.size());
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (PrefGruppi pref : gruppo) {
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

            /* qui si può scegliere se dismettere il dialogo oppure lasciarlo */
            super.eventoAnnulla();
//            this.sincroGruppi();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Recupera le preferenze del gruppo selezionato.
     * <p/>
     *
     * @return gruppo di preferenze
     */
    private ArrayList<PrefGruppi> getPref() {
        /* variabili e costanti locali di lavoro */
        ArrayList<PrefGruppi> gruppo = null;
        boolean continua;
        String nomeGruppo;

        try {    // prova ad eseguire il codice
            /* recupera il gruppo selezionato */
            nomeGruppo = this.getGruppo();
            continua = (Lib.Testo.isValida(nomeGruppo));

            if (continua) {
                gruppo = Pref.getGruppo(nomeGruppo);
            }// fine del blocco if

        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return gruppo;
    }


    /**
     * Forza tutte le preferenze del gruppo al valore di default.
     * <p/>
     * Metodo invocato dal bottone default nella sezione inferiore <br>
     */
    private void eventoDefault() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        LinkedHashMap<String, Campo> campi;
        ArrayList<PrefGruppi> gruppo = null;
        Campo campo;
        Object valDefault;
        PrefWrap wrap;

        try { // prova ad eseguire il codice

            /* recupera i campi mostrati nel dialogo */
            campi = this.getCampi();
            continua = (campi != null);

            /* Recupera il gruppo di preferenze selezionato */
            if (continua) {
                gruppo = this.getPref();
                continua = (gruppo != null);
            }// fine del blocco if

            if (continua) {
                continua = (gruppo.size() >= campi.size());
            }// fine del blocco if

            if (continua) {
                /* traverso tutta la collezione */
                for (PrefGruppi pref : gruppo) {
                    wrap = pref.getWrap();
                    campo = campi.get(wrap.getSigla().toLowerCase());
                    if (campo != null) {
                        valDefault = wrap.getStandard();
                        campo.setValore(valDefault);
                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Inserisce il valore del popup nel campo (testo).
     * <p/>
     * Metodo invocato dal popup dei valori precedenti di una preferenza di tipo testo <br>
     *
     * @param evento che causa l'azione da eseguire <br>
     */
    private void eventoPrecedente(ItemEvent evento) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        Object ogg = null;
        JComboBox combo;
        String nomeCampo = "";
        Campo campo = null;
        String valCombo = "";

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (evento != null);

            if (continua) {
                ogg = evento.getSource();
                continua = (ogg != null);
            }// fine del blocco if

            if (continua) {
                continua = (ogg instanceof JComboBox);
            }// fine del blocco if

            if (continua) {
                combo = (JComboBox)ogg;
                valCombo = combo.getSelectedItem().toString();
                nomeCampo = combo.getName();
                continua = (Lib.Testo.isValida(nomeCampo));
            }// fine del blocco if

            if (continua) {
                nomeCampo = nomeCampo.substring(0, nomeCampo.length() - 1);
                campo = this.getCampo(nomeCampo);
                continua = (campo != null);
            }// fine del blocco if

            if (continua) {
                campo.setValore(valCombo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Seleziona il gruppo di preferenze da visualizzare..
     * <p/>
     * Metodo invocato dal bottone specifico nella sezione superiore <br>
     *
     * @param unEvento generato dal bottone
     */
    public void selezionaGruppo(ActionEvent unEvento) {
        /* variabili e costanti locali di lavoro */
        String nome = "";
        Object ogg;
        JButton bot;

        try { // prova ad eseguire il codice
            ogg = unEvento.getSource();

            if (ogg instanceof JButton) {
                bot = (JButton)ogg;
                nome = bot.getName();
            }// fine del blocco if

            if (Lib.Testo.isVuota(nome)) {
                nome = "";
            }// fine del blocco if

            /* memorizza la scelta */
            this.setGruppo(nome);
            this.sincroGruppi();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Aggiunge il bottone default.
     * <p/>
     * Recupera icona e testo <br>
     * Crea il bottone con icona, testo ed azione <br>
     * L'azione rimanda al metodo annulla <br>
     * Questo bottone non dismette il dialogo <br>
     *
     * @return bottone di default, appena creato
     */
    protected BottoneDialogo addDefault() {
        /* variabili e costanti locali di lavoro */
        BottoneDialogo bottone = null;

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato della classe */
            bottone = this.addBottoneBase("Default", "Rewind24", false, false, new AzDefault());

            bottone.setEnabled(false);
            this.add(bottone);
            this.setBottoneDefault(bottone);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return bottone;
    }


    private LinkedHashMap<String, BottoneAzione> getBottoni() {
        return bottoni;
    }


    private LinkedHashMap<String, BottoneAzione> setBottoni(LinkedHashMap<String, BottoneAzione> bottoni) {
        this.bottoni = bottoni;
        return this.getBottoni();
    }


    private String getGruppo() {
        return gruppo;
    }


    private void setGruppo(String gruppo) {
        this.gruppo = gruppo;
    }


    private Bottone getBottoneDefault() {
        return bottoneDefault;
    }


    private void setBottoneDefault(Bottone bottoneDefault) {
        this.bottoneDefault = bottoneDefault;
    }


    /**
     * Listener invocato quando si clicca sul bottone annulla.
     */
    private class AzDefault extends AzAdapterAction {

        public void actionPerformed(ActionEvent e) {
            eventoDefault();
        }
    }


    /**
     * Listener invocato quando si clicca sul popup dei valori precedenti di una preferenza.
     */
    private class AzPrecedente extends AzAdapterItem {

        /**
         * itemStateChanged, da ItemListener.
         * <p/>
         * Esegue l'azione <br>
         * Rimanda al metodo delegato, nel gestore specifico associato
         * all' oggetto che genera questo evento <br>
         * Sovrascritto nelle sottoclassi <br>
         *
         * @param unEvento evento che causa l'azione da eseguire <br>
         */
        public void itemStateChanged(ItemEvent unEvento) {
            eventoPrecedente(unEvento);
        }
    }

}// fine della classe
