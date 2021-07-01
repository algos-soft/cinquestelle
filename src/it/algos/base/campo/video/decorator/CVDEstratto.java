/**
 * Copyright:    Copyright (c) 2003
 * Company:      Algos s.r.l.
 * @author Ceresa, Colombo, Valbonesi
 * @version 1.0  /
 * Creato:       il 15 agosto 2003 alle 21.04
 */
package it.algos.base.campo.video.decorator;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.dati.CampoDati;
import it.algos.base.campo.video.CampoVideo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.pannello.Pannello;
import it.algos.base.progetto.Progetto;
import it.algos.base.wrapper.Estratti;
import it.algos.base.wrapper.EstrattoBase;

import javax.swing.*;

/**
 * Decoratore estratto della classe CampoVideo.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Implementa il <i>design pattern</i> <b>Decorator</b> </li>
 * <li> Inserisce, nel pannello campo del CampoVideo, un pannello
 * (grafico o di testo) proveniente da un'altro modulo </li>
 * <li> L'estratto può essere posizionata sotto o a destra </li>
 * <ul/>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  15 agosto 2003 ore 21.04
 */
public final class CVDEstratto extends CVDCalcolato {


    /**
     * riferimento al nome dell'estratto specifico per il modulo
     */
    private Estratti estratto;

    /**
     * componente grafico per contenere l'estratto
     * (se è un testo usa la JLabel della superclasse)
     */
    private JPanel pannello = null;


    /**
     * Costruttore completo con parametri.
     *
     * @param campoVideoDecorato oggetto da decorare
     */
    public CVDEstratto(CampoVideo campoVideoDecorato) {
        /* rimanda al costruttore della superclasse */
        super(campoVideoDecorato);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        String nomeCampo;
        JPanel pan;

        try { // prova ad eseguire il codice

            /** di default l'estratto viene posizionato
             *  nella parte destra del pannelloCampo */
            this.setPos(Pos.DESTRA);

            /** crea l'istanza del contenitore fisso,
             * dove verra' inserito l'estratto se di tipo grafico */
            this.setPannello(new JPanel());
            pan = this.getPannello();
            pan.setOpaque(false);

            /* osserva se stesso */
            nomeCampo = this.getCampoParente().getNomeInterno();
            this.setCampoOsservato(nomeCampo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo inizia */


    /**
     * Regolazioni dinamiche dell'oggetto.
     * <p/>
     * Regola le caratteristiche dinamiche in base alle impostazioni
     * correnti delle caratteristiche statiche <br>
     * Metodo invocato dalla classe che crea questo oggetto (di norma) <br>
     * Viene eseguito una sola volta <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void inizializza() {
        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto della superclasse */
            super.inizializza();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Regolazioni di ri-avvio.
     * <p/>
     * Metodo invocato da altre classi (o anche da questa)
     * ogni volta che questo oggetto deve <i>ripartire</i>,
     * per essere sicuri che sia <i>pulito</i> <br>
     * Viene eseguito tutte le volte che necessita <br>
     * Sovrascritto nelle sottoclassi <br>
     */
    public void avvia() {
        /* invoca il metodo sovrascritto della superclasse */
        super.avvia();
    }


    /**
     * Crea gli elementi GUI del pannelloCampo.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Gli elementi possono essere anche più di uno <br>
     * Gli elementi vengono aggiunti al pannelloCampo <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     * (metodo chiamato dalla superclasse) <br>
     *
     * @see #regolaElementi()
     */
    protected void creaComponentiGUI() {
        /* variabili e costanti locali di lavoro */
        JPanel pan;
        Pos pos;
        Estratti estratto;
        EstrattoBase.Tipo tipo;

        try { // prova ad eseguire il codice

            /* tipo di estratto */
            estratto = this.getEstratto();
            tipo = estratto.getTipo();

            /* selettore della variabile */
            switch (tipo) {
                case stringa:
                    /* estratto di tipo testo - usa la JLabel della superclasse */
                    /* invoca il metodo sovrascritto della superclasse */
                    super.creaComponentiGUI();
                    break;

                case pannello:
                    /* estratto di tipo grafico - usa un contenitore */
                    /* recupera l'oggetto */
                    pan = this.getPannello();

                    /* recupera la posizione */
                    pos = this.getPos();

                    /* aggiunge l'etichetta al pannelloCampo */
                    this.addComponente(pan, pos);
                    break;

                default: // caso non definito
                    break;

            } // fine del blocco switch

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Esegue l'azione generata dall'evento.
     * <p/>
     * Metodo invocato dalla classe interna <br>
     */
    protected void esegui(Campo campo) {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        CampoDati campoDati = null;
        Object oggetto = null;
        String testo;
        Estratti estrattoStatico = null;
        EstrattoBase.Tipo tipo = null;
        EstrattoBase estratto = null;
        JPanel pan;
        String nomeMod;
        Modulo mod = null;
        String valore;
        int cod;
        Pannello pannello;

        try { // prova ad eseguire il codice
            continua = campo != null;

            /* elimina il precedente valore */
            this.regolaTesto(" ");

            /* tipo di estratto */
            if (continua) {
                estrattoStatico = this.getEstratto();
                tipo = estrattoStatico.getTipo();
            }// fine del blocco if

            /* recupera il modulo dell'estratto */
            if (continua) {
                nomeMod = estrattoStatico.getNomeModulo();
                mod = Progetto.getModulo(nomeMod);
                continua = mod != null;
            }// fine del blocco if

            /* recupera il campo dati */
            if (continua) {
                campoDati = campo.getCampoDati();
                continua = campoDati != null;
            }// fine del blocco if

            /* recupera il valore del campo  */
            if (continua) {
                oggetto = campoDati.getMemoria();
                continua = oggetto != null;
            }// fine del blocco if

            /* recupera il valore del campo  */
            if (continua) {
                continua = false;

                if (oggetto instanceof String) {
                    valore = (String)oggetto;
                    if (tipo == EstrattoBase.Tipo.stringa) {
                        try { // prova ad eseguire il codice
                            cod = Integer.decode(valore);
                            continua = cod != 0;
                        } catch (Exception unErrore) { // intercetta l'errore
                            continua = false;
                        }// fine del blocco try-catch
                    } else {
                        continua = true;
                    }// fine del blocco if-else

                    /* recupera l'estratto specifico */
                    if (continua) {
                        estratto = mod.getEstratto(estrattoStatico, valore);
                        continua = estratto != null;
                    }// fine del blocco if
                }// fine del blocco if

                if (oggetto instanceof Integer) {
                    cod = (Integer)oggetto;
                    continua = cod != 0;
                    /* recupera l'estratto specifico */
                    if (continua) {
                        estratto = mod.getEstratto(estrattoStatico, cod);
                        continua = estratto != null;
                    }// fine del blocco if
                }// fine del blocco if

            }// fine del blocco if

            /* selettore della variabile */
            if (continua) {
                switch (tipo) {
                    case stringa:
                        /* estratto di tipo testo - usa la JLabel della superclasse */
                        testo = estratto.getStringa();
                        this.regolaTesto(testo);

                        break;
                    case pannello:
                        /* estratto di tipo grafico - usa un contenitore */
                        /* recupera l'oggetto */
                        pan = this.getPannello();

                        /* pulisce dal precedente */
                        pan.removeAll();

                        pannello = estratto.getPannello();
                        pan.add(pannello.getPanFisso());

                        this.getCampoParente().getForm().getPanFisso().repaint();
                        break;
                    default: // caso non definito
                        break;
                } // fine del blocco switch
                this.pack();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    private Estratti getEstratto() {
        return estratto;
    }


    public void setEstratto(Estratti estratto) {
        this.estratto = estratto;
    }


    private JPanel getPannello() {
        return pannello;
    }


    private void setPannello(JPanel pannello) {
        this.pannello = pannello;
    }
}// fine della classe