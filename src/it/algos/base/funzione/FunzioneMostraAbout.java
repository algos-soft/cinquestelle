/**
 * Title:        FunzioneMostraAbout.java
 * Package:      it.algos.base.funzione
 * Description:
 * Copyright:    Copyright (c) 2002, 2003
 * Company:      Algos s.r.l.
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /
 * Creato:       il 6 febbraio 2003 alle 9.54
 */
//-----------------------------------------------------------------------------
// Copyright (c) 2002, 2003  Algos s.r.l. All Rights Reserved.
//-----------------------------------------------------------------------------
//-----------------------------------------------------------------------------
// Package di questa classe e classi importate
//-----------------------------------------------------------------------------
package it.algos.base.funzione;

import it.algos.base.componente.bottone.BottoneChiudeFinestra;
import it.algos.base.costante.CostanteBase;
import it.algos.base.dialogo.DialogoAvviso;
import it.algos.base.errore.ErroreInizia;
import it.algos.base.libreria.Lib;
import it.algos.base.progetto.Progetto;

import javax.swing.*;
import java.awt.*;

//-----------------------------------------------------------------------------
// Classe principale
//-----------------------------------------------------------------------------


/**
 * Questa classe concreta e' responsabile di: <br>
 * A - Mostrare le informazioni del programma <br>
 * B - ... <br>
 *
 * @author Guido Andrea Ceresa & Alessandro Valbonesi  /  gac
 * @version 1.0  /  6 febbraio 2003 ore 9.54
 */
public final class FunzioneMostraAbout extends DialogoAvviso implements CostanteBase {

    //-------------------------------------------------------------------------
    // Costanti statiche della classe    (private,pubbliche)  (class variables)
    //-------------------------------------------------------------------------
    /**
     * nome di questa classe (per i messaggi di errore)
     */
    private static final String NOME_CLASSE = "FunzioneMostraAbout";

    /**
     * voce del dialogo
     */
    private static final String TITOLO = "About";

    /**
     * percorso del disegno
     */
    private static final String DISEGNO = "icone/about.gif";

    private static final String NOME_PROGRAMMA = "MenuRistorante";

    private static final String NOME_SOCIETA = "Algos s.r.l.";

    private static final String NOME_BOTTONE = "Continua";

    private static final String COPYRIGHT = "Copyright@2007,  All Rights Reserved";

    //-------------------------------------------------------------------------
    // Variabili statiche della classe         (private)      (class variables)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Riferimenti ad altri oggetti    (private,protette)  (instance variables)
    //-------------------------------------------------------------------------
    /**
     * finestra di servizio di riferimento
     */
    private JFrame unaFinestra = null;

    /**
     * dialogo mostrato
     */
    private JDialog unDialogo = null;

    /**
     * pannello interno
     */
    private JPanel unPannello = null;

    //-------------------------------------------------------------------------
    // Variabili degli oggetti (private,protette,pubbliche)(instance variables)
    //-------------------------------------------------------------------------
    /**
     * variabili di testo
     */
    private String programma = "";

    private String societa = "";

    private String nomeBottone = "";

    private String copyright = "";

    private String tab = "          ";


    //-------------------------------------------------------------------------
    // Costruttori della classe                                   constructors)
    //-------------------------------------------------------------------------
    /**
     * Costruttore base senza parametri <br>
     * Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     * Rimanda al costruttore completo utilizzando eventuali valori di default
     */
    public FunzioneMostraAbout() {
        /** rimanda al costruttore di questa classe */
        this(NOME_PROGRAMMA);
    } /* fine del metodo costruttore base */


    public FunzioneMostraAbout(String nomeProgramma) {
        /** rimanda al costruttore di questa classe */
        this(nomeProgramma, NOME_SOCIETA, NOME_BOTTONE, COPYRIGHT);
    } /* fine del metodo costruttore base */


    /**
     * Costruttore completo
     */
    public FunzioneMostraAbout(String programma,
                               String societa,
                               String nomeBottone,
                               String copyrightVar) {
        /** rimanda al costruttore della superclasse */
        super();

        /** regola le variabili di istanza coi parametri */
        this.programma = programma;
        this.societa = societa;
        this.nomeBottone = nomeBottone;
        this.copyright = copyrightVar;

        /** regolazioni iniziali di riferimenti e variabili */
        try {                                   // prova ad eseguire il codice
            this.inizia();
        } catch (Exception unErrore) {           // intercetta l'errore
            /** messaggio di errore */
            new ErroreInizia(NOME_CLASSE, unErrore);
        } /* fine del blocco try-catch */
    } /* fine del metodo costruttore completo */


    //-------------------------------------------------------------------------
    // Metodi statici della classe                              (class methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati chiamati dal costruttore prima di tornare alla sottoclasse
    //-------------------------------------------------------------------------
    /**
     * Regolazioni iniziali di riferimenti e variabili
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /** esegue la funzione della task */
        this.creaDialogo();
    } /* fine del metodo inizia */


    //-------------------------------------------------------------------------
    // Metodi protetti chiamati dalla sottoclasse subito dopo il costruttore
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi privati degli oggetti (usati nella classe)     (instance methods)
    //-------------------------------------------------------------------------
    /**
     * Crea la finestra di dialogo
     */
    protected void creaDialogo() {
        /* variabili e costanti locali di lavoro */
        Package pac;
        boolean info;
        String titolo;
        String societa;
        String autore;
        String versione;
        String data;
        JTextArea testo;

        /* finestra di servizio di riferimento */
        unaFinestra = new JFrame();

        /* dialogo con voce e flag modale */
        unDialogo = new JDialog(unaFinestra, TITOLO, true);

        /* pannello interno */
        unPannello = new JPanel();

        unPannello.setBackground(new Color(255, 224, 80));

        /* dimensione del dialogo */
        unDialogo.setSize(300, 300);

        /* layout nullo = oggetti fissi */
        unPannello.setLayout(null);

        /* aggiungo un pannello di contorno in cui andrà il bottone*/
        unDialogo.getContentPane().add(unPannello);

        /* il dialogo non può modificare la dimensione */
        unDialogo.setResizable(false);

        /* scritte in alto */
        JLabel testoSup = new JLabel(programma);
        JLabel testoInf = new JLabel(this.societa);
        JLabel testoCopy = new JLabel(copyright);

        ImageIcon disegno = null;

        try {
            disegno = new ImageIcon(FunzioneMostraAbout.class.getResource(DISEGNO));
        } catch (Exception e) {
        } // fine di catch

        pac = Progetto.getPrimoModulo().getClass().getPackage();
        titolo = pac.getSpecificationTitle();
        societa = pac.getSpecificationVendor();
        versione = pac.getSpecificationVersion();
        autore = pac.getImplementationVendor();
        data = pac.getImplementationVersion();
        info = Lib.Testo.isValida(versione);

        System.out.println(pac.getImplementationTitle());
        System.out.println(pac.getImplementationVendor());
        System.out.println(pac.getImplementationVersion());
        System.out.println(pac.getSpecificationVersion());
        System.out.println(pac.getSpecificationTitle());
        System.out.println(pac.getSpecificationVendor());
        System.out.println(pac.getName());

        JLabel icona = new JLabel(disegno);
        if (info) {
            testo = new JTextArea(titolo +
                    " è stato realizzato da:" +
                    "\n" +
                    autore +
                    "\n" +
                    "per conto di:" +
                    "\n" +
                    societa +
                    "\n\nVersione: " +
                    versione +
                    "\n\nData: " +
                    data);
        } else {
            testo = new JTextArea("Questo progetto è stato" +
                    "\nrealizzato da:" +
                    "\n" +
                    "\nGuido Andrea Ceresa" +
                    "\n" +
                    tab +
                    "&" +
                    "\nAlessandro Valbonesi");
        }// fine del blocco if-else


        testo.setOpaque(false);

        testoSup.setFont(new Font("Serif", Font.ITALIC, 22));
        testoInf.setFont(new Font("Serif", Font.BOLD, 15));
        testoCopy.setFont(new Font("Serif", Font.PLAIN, 10));
        testo.setFont(new Font("Serif", Font.PLAIN, 12));

        testoSup.setBounds(new Rectangle(110, 20, 300, 30));
        testoInf.setBounds(new Rectangle(150, 180, 300, 30));
        testoCopy.setBounds(new Rectangle(110, 210, 235, 15));
        icona.setBounds(new Rectangle(0, 0, 100, 300));
        testo.setBounds(new Rectangle(125, 65, 160, 100));

        unPannello.add(testoSup);
        unPannello.add(testoInf);
        unPannello.add(testoCopy);
        unPannello.add(icona);
        unPannello.add(testo);

        /** crea il bottone (con azione) e lo aggiunge al pannello */
        /** bottone di chiusura della finestra */
        JButton unBottoneChiudeFinestra = new BottoneChiudeFinestra();
        unBottoneChiudeFinestra.setLocation(60, 230);

//        JButton unBottone = new JButton(nomeBottone);
//	int largBott = nomeBottone.length()*12;
//	int largFin = unDialogo.getWidth();
//	int bordoSin = (largFin - largBott)/2 + 40;
//	unBottoneChiudeFinestra.setBounds(new Rectangle(bordoSin,235,largBott,30));
        unPannello.add(unBottoneChiudeFinestra);

//	unBottoneChiudeFinestra.addActionListener(new FunzioneChiudeFinestra());

        /** rende visibile il dialogo */
        unDialogo.setVisible(true);
    } /* fine del metodo creaDialogo */
    //-------------------------------------------------------------------------
    // Metodi protetti degli oggetti (usati nelle sottoclassi)    (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che verranno sovrascritti nelle sottoclassi          (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi che sovrascrivono metodi della superclasse           (overriding)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi pubblici degli oggetti (usati in altre classi)      (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di regolazione delle variabili locali                    (setter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di restituzione delle variabili locali                   (getter)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi di gestione interni, invocati dai metodi azione     (instance m.)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Metodi azione                                         (instance methods)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
    // Classi azione interne degli eventi                         (inner class)
    //-------------------------------------------------------------------------
    //-------------------------------------------------------------------------
}// fine della classe it.algos.base.funzione.FunzioneMostraAbout.java

//-----------------------------------------------------------------------------

