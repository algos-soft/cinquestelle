/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      22-feb-2007
 */
package it.algos.base.help;

import it.algos.base.costante.CostanteModulo;
import it.algos.base.errore.Errore;

import javax.help.CSH;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Vector;

/**
 * Questa classe astratta e' responsabile di: <br>
 * A - ... <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  21 gennaio 2003 ore 7.10
 */
public final class Help implements CostanteModulo {

    /**
     * istanza unica di questa classe, creata ad avvio del programma
     */
    private static Help ISTANZA = null;


    /**
     * helpset principale e broker
     */
    private HelpSet helpSet = null;

    private HelpBroker broker = null;

    /**
     * helpset visibile solo al programmatore
     */
    private HelpSet helpSetProg = null;

    private HelpBroker brokerProg = null;

    private final static String dirUtente = "utente";

    private final static String dirProg = "programmatore";

    private final static String hsUtente = "UteHelp";

    private final static String hsProg = "ProgHelp";


    /**
     * Costruttore completo senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public Help() {
        /* rimanda al costruttore della superclasse */
        super();

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo costruttore completo */


    /**
     * Regolazioni statiche di riferimenti e variabili.
     * </p>
     * Metodo invocato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        try { // prova ad eseguire il codice
            /* crea l'help base per l'utente */
            this.creaHelpBase();

            /* crea l'help base per il programmatore */
            this.creaHelpProgrammatore();

            /* Operazioni alla partenza ed eventuale interfaccia utente */
            this.abilitaComponenti();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    } /* fine del metodo inizia */


    /**
     * crea l'help base per l'utente
     */
    private void creaHelpBase() {
        /* variabili e costanti locali di lavoro */
        HelpSet helpSet;
        HelpBroker broker;

        try { // prova ad eseguire il codice
            helpSet = getHelpSet(dirUtente, hsUtente);
            this.setHelpSet(helpSet);

            if (helpSet != null) {
                broker = helpSet.createHelpBroker();
                this.setBroker(broker);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * crea l'help base per il programmatore
     */
    private void creaHelpProgrammatore() {
        /* variabili e costanti locali di lavoro */
        HelpSet helpSetProg;
        HelpBroker brokerProg;

        try { // prova ad eseguire il codice
            helpSetProg = getHelpSet(dirProg, hsProg);
            this.setHelpSetProg(helpSetProg);

            if (helpSetProg != null) {
                brokerProg = helpSetProg.createHelpBroker();
                this.setBrokerProg(brokerProg);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * find the helpset file and create a HelpSet object.
     *
     * @param cartella path locale della cartella
     * @param file nome del file
     *
     * @return helpSet creato
     */
    public HelpSet getHelpSet(String cartella, String file) {
        HelpSet helpSet = null;
        ClassLoader classLoader;
        URL helpSetUrl;
        String pathDir = "";
        String pathHelp = "it/algos/base/help/";
        String filename = file + ".hs";
        String path;
        URL[] urlArray;

        try { // prova ad eseguire il codice

            path = "";
            path += pathHelp;
            path += cartella;
            path += "/";

            urlArray = new URL[1];
            urlArray[0] = ClassLoader.getSystemResource(path);
            classLoader = new URLClassLoader(urlArray);
            helpSetUrl = HelpSet.findHelpSet(classLoader, filename);
            helpSet = new HelpSet(null, helpSetUrl);

            /* test */
            urlArray[0] = new URL("file:/Volumes/Gac/Users/gac/Documents/HelpTest/hs/");
            classLoader = new URLClassLoader(urlArray);
            filename = "main_it.hs";
            helpSetUrl = HelpSet.findHelpSet(classLoader, filename);
            helpSet = new HelpSet(null, helpSetUrl);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore, pathDir);
            System.out.println("HelpSet: " + unErrore.getMessage());
            System.out.println("HelpSet: " + file + " not found");
        }// fine del blocco try-catch

        /* valore di ritorno */
        return helpSet;
    }


    // It doesn't really parse the URLs it just creates a URL from a string
    // and then puts it into a URL array
    private static URL[] parseURLs(String spec) {
        Vector v = new Vector();
        URL url;

        try {
            url = new URL(spec);
            v.addElement(url);
        } catch (Exception ex) {
            System.err.println("cannot create URL for " + spec);
        }
        URL back[] = new URL[v.size()];
        v.copyInto(back);
        return back;
    }


    /**
     * Operazioni alla partenza ed eventuale interfaccia utente
     */
    protected void abilitaComponenti() {
//        unBroker.enableHelpOnButton(new MenuItem(), unHelpBase);
    } /* fine del metodo partenza */


    /**
     * ...
     */
    public void abilitaHelp(Component unComponente, String unAiuto) {
        this.broker.enableHelpOnButton(unComponente, unAiuto, helpSet);
    } /* fine del metodo */


    /**
     * ...
     */
    public void abilitaHelp(JMenuItem unMenu, String unAiuto) {
        CSH.setHelpIDString(unMenu, "top");
        new CSH.DisplayHelpFromSource(broker);
//        this.unBroker.enableHelpOnButton(unMenu, unAiuto, unHelp);
    } /* fine del metodo */


    /**
     * ...
     */
    public void abilitaHelpProgrammatore(JMenuItem unMenu, String unAiuto) {
        this.brokerProg.enableHelpOnButton(unMenu, unAiuto, helpSetProg);
    } /* fine del metodo */


    public HelpBroker getBroker() {
        return broker;
    }


    private void setBroker(HelpBroker broker) {
        this.broker = broker;
    }


    public HelpBroker getBrokerProg() {
        return brokerProg;
    }


    private void setBrokerProg(HelpBroker brokerProg) {
        this.brokerProg = brokerProg;
    }


    public HelpSet getHelpSetIstanza() {
        return helpSet;
    }


    public HelpSet getHelpSetProgIstanza() {
        return helpSetProg;
    }


    public static HelpSet getHelpSet() {
        return Help.getIstanza().getHelpSetIstanza();
    }


    public static HelpSet getHelpSetProg() {
        return Help.getIstanza().getHelpSetProgIstanza();
    }


    private void setHelpSet(HelpSet helpSet) {
        this.helpSet = helpSet;
    }


    private void setHelpSetProg(HelpSet helpSetProg) {
        this.helpSetProg = helpSetProg;
    }


    /**
     * metodo getter per ottenere il valore della variabile statica privata
     */
    public static Help getIstanza() {

        if (ISTANZA == null) {
            ISTANZA = new Help();
        }// fine del blocco if

        return ISTANZA;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum Tipo {

        utente(),
        programmatore()

    }// fine della classe

}// fine della classe it.algos.base.help.Help.java


