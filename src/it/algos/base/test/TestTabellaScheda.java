/**
 * Title:     ClienteScheda
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      26-apr-2004
 */
package it.algos.base.test;

import it.algos.base.campo.base.Campo;
import it.algos.base.errore.Errore;
import it.algos.base.layout.Layout;
import it.algos.base.libreria.Lib;
import it.algos.base.modulo.Modulo;
import it.algos.base.pagina.Pagina;
import it.algos.base.pannello.Pannello;
import it.algos.base.pannello.PannelloBase;
import it.algos.base.pannello.PannelloFactory;
import it.algos.base.scheda.SchedaDefault;
import it.algos.base.wrapper.EstrattoBase;

import javax.swing.*;
import java.awt.*;

/**
 * Presentazione grafica di un singolo record di Cliente.
 * </p>
 * Questa classe concreta: <ul>
 * <li> Crea le pagine del <code>Libro</code> che vengono visualizzate nel
 * PortaleScheda del Navigatore </li>
 * <li> Ogni pagina viene creata con un set di campi o aggiungendo i singoli campi </li>
 * <li> I campi vengono posizionati in automatico oppure singolarmente </li>
 * <li> Se uno stesso campo viene posizionato su pi� pagine, risulter�
 * visibile solo nell'ultima pagina in cui viene posizionato </li>
 * <li> Se il <code>Modello>/code> prevede il campo note, crea la pagina note </li>
 * <li> Se il flag programma � attivo, crea la pagina programmatore </li>
 * <ul/>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 26-apr-2004 ore 10.31.58
 */
public class TestTabellaScheda extends SchedaDefault {

    private JLabel prova;

    private JLabel prova2;

    private JPanel prova3;


    /**
     * Costruttore completo senza parametri.
     */
    public TestTabellaScheda(Modulo modulo) {
        /* rimanda al costruttore della superclasse */
        super(modulo);

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
    }// fine del metodo inizia


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     * Sincronizza la status bar <br>
     */
    public void sincronizza() {
        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto nella superclasse */
            super.sincronizza();
//            this.sigla();
//            this.nazione();
//            this.composto();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * .
     * <p/>
     */
    public void inizializza() {
        try {    // prova ad eseguire il codice
            super.inizializza();
        } catch (Exception unErrore) {    // intercetta l'errore
            new Errore(unErrore);
        } // fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     * Sincronizza la status bar <br>
     */
    private void sigla() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        EstrattoBase est = null;
        Modulo mod = null;
        Campo campo;
        Object oggetto = null;
        String testo = "";
        String valore = "";

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto nella superclasse */
            super.sincronizza();
            campo = this.getCampo(TestTabella.CAMPO_SIGLA);

            continua = (campo != null);

            if (continua) {
                oggetto = campo.getCampoDati().getMemoria();
                continua = (oggetto != null);
            }// fine del blocco if

            if (continua) {
                continua = (oggetto instanceof String);
            }// fine del blocco if

            if (continua) {
                valore = (String)oggetto;
                continua = Lib.Testo.isValida(valore);
            }// fine del blocco if


            if (continua) {
//                mod = Progetto.getModulo(Nazione.NOME_MODULO);
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
//                est = mod.getEstratto(Nazione.Estratti.sigla, valore);
                continua = (est != null);
            }// fine del blocco if

            if (continua) {
                testo = est.getStringa();
            }// fine del blocco if

            if (continua) {
                prova.setText(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     * Sincronizza la status bar <br>
     */
    private void nazione() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        EstrattoBase est = null;
        Modulo mod = null;
        Campo campo;
        Object oggetto = null;
        String testo = "";
        int cod = 0;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto nella superclasse */
            super.sincronizza();
            campo = this.getCampo(TestTabella.CAMPO_NUMERO);

            continua = (campo != null);

            if (continua) {
                oggetto = campo.getCampoDati().getMemoria();
                continua = (oggetto != null);
            }// fine del blocco if

            if (continua) {
                continua = (oggetto instanceof Integer);
            }// fine del blocco if

            if (continua) {
                try { // prova ad eseguire il codice
                    cod = (Integer)oggetto;
                    continua = (cod != 0);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

            if (continua) {
//                mod = Progetto.getModulo(Nazione.NOME_MODULO);
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
//                est = mod.getEstratto(Nazione.Estratti.nazione, cod);
                continua = (est != null);
            }// fine del blocco if

            if (continua) {
                testo = est.getStringa();
            }// fine del blocco if

            if (continua) {
                prova2.setText(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Sincronizzazione della scheda.
     * <p/>
     * Chiamato dalla sincronizzazione del portale
     * che contiene la scheda. <br>
     * Sincronizza la status bar <br>
     */
    private void composto() {
        /* variabili e costanti locali di lavoro */
        boolean continua;
        EstrattoBase est = null;
        Modulo mod = null;
        Campo campo;
        Object oggetto = null;
        Pannello pan = null;
        int cod = 0;

        try { // prova ad eseguire il codice
            /* invoca il metodo sovrascritto nella superclasse */
            super.sincronizza();
            campo = this.getCampo(TestTabella.CAMPO_NUMERO_DUE);

            continua = (campo != null);

            if (continua) {
                oggetto = campo.getCampoDati().getMemoria();
                continua = (oggetto != null);
            }// fine del blocco if

            if (continua) {
                continua = (oggetto instanceof Integer);
            }// fine del blocco if

            if (continua) {
                try { // prova ad eseguire il codice
                    cod = (Integer)oggetto;
                    continua = (cod != 0);
                } catch (Exception unErrore) { // intercetta l'errore
                }// fine del blocco try-catch
            }// fine del blocco if

            if (continua) {
//                mod = Progetto.getModulo(Nazione.NOME_MODULO);
                continua = (mod != null);
            }// fine del blocco if

            if (continua) {
//                est = mod.getEstratto(Nazione.Estratti.composto, cod);
                continua = (est != null);
            }// fine del blocco if

            if (continua) {
                pan = est.getPannello();
            }// fine del blocco if

            if (continua) {
                prova3.removeAll();
                prova3.add(pan.getPanFisso());
                this.repaint();
                this.invalidate();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Costruisce la pagina generale.
     * <br>
     */
    private void creaPaginaGenerale() {
        /* variabili e costanti locali di lavoro */
        Pagina pagina;
        Pannello pan1;
        Pannello pan2;
        PannelloBase pan3;

        try { // prova ad eseguire il codice
            /* crea una pagina vuota col voce */
            pagina = super.addPagina("prova");

            pan1 = PannelloFactory.orizzontale(this);
            pan1.setAllineamento(Layout.ALLINEA_CENTRO);
            pan1.add(TestTabella.CAMPO_SIGLA);
            prova = new JLabel("vuota");
            pan1.add(prova);

            pan2 = PannelloFactory.orizzontale(this);
            pan2.setAllineamento(Layout.ALLINEA_CENTRO);
            pan2.add(TestTabella.CAMPO_NUMERO);
            prova2 = new JLabel("vuota");
            pan2.add(prova2);

            pan3 = (PannelloBase)PannelloFactory.orizzontale(this);
            pan3.setAllineamento(Layout.ALLINEA_CENTRO);
//            pan3.setRidimensionaComponenti(false);
            pan3.add(TestTabella.CAMPO_NUMERO_DUE);
            pan3.getPanFisso().setOpaque(true);
            pan3.getPanFisso().setBackground(Color.YELLOW);
            prova3 = new JPanel();
            prova3.setOpaque(true);
            prova3.setBackground(Color.GREEN);

            pan3.add(prova3);

            pagina.add(pan1);
            pagina.add(pan2);
            pagina.add(pan3);


        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch

    } /* fine del metodo */


    /**
     * Crea le pagine.
     * <p/>
     * Metodo invocato dal ciclo inizializza <br>
     * Metodo sovrascritto nelle sottoclassi <br>
     */
    protected void creaPagine() {
        try {    // prova ad eseguire il codice
            super.creaPagine();
//            this.creaPaginaGenerale();
        } catch (Exception unErrore) { // intercetta l'errore
            new Errore(unErrore);
        }// fine del blocco try-catch
    }

}// fine della classe
