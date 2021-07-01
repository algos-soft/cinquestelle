/**
 * Title:     LibRisorse
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      29-gen-2004
 */
package it.algos.base.libreria;

import it.algos.base.costante.CostanteCarattere;
import it.algos.base.errore.Errore;
import it.algos.base.progetto.Progetto;

import java.awt.*;
import java.io.File;

/**
 * Repository di funzionalità per la gestione del sistema. </p> Tutti i metodi sono statici <br> La
 * classe contiene metodi di utilità specifici per la gestione del sistema <br> I metodi non hanno
 * modificatore così sono visibili all'esterno del package solo utilizzando l'interfaccia unificata
 * <b>Lib</b><br>
 *
 * @author Alberto Colombo, Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 17-gen-2004 ore 9.41.24
 */
public abstract class LibSistema {


    /**
     * Restituisce il percorso assoluto della User Home Directory dell'utente del sistema.
     * <p/>
     * Il percorso restituito non comprende il separatore finale <br>
     *
     * @return il percorso della User Home
     */
    static String getUserHome() {
        /* variabili e costanti locali di lavoro */
        String unaDir = null;

        try { // prova ad eseguire il codice
            unaDir = System.getProperty("user.home");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaDir;
    }


    /**
     * Restituisce il percorso assoluto della Directory Algos.
     * <p/>
     * Il percorso restituito non comprende il separatore finale <br>
     *
     * @return il percorso della Directory Algos
     */
    static String getDirAlgos() {
        /* variabili e costanti locali di lavoro */
        String unaDir = null;

        try { // prova ad eseguire il codice
            unaDir = getUserHome() + "/algos";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaDir;
    }


    /**
     * Restituisce il percorso assoluto della cartella moduli.
     * <p/>
     * Il percorso restituito non comprende il separatore finale <br>
     *
     * @return il percorso della cartella moduli
     */
    static String getDirModuli() {
        /* variabili e costanti locali di lavoro */
        String unaDir = null;

        try { // prova ad eseguire il codice
            unaDir = getDirAlgos() + "/moduli";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaDir;
    }


    /**
     * Restituisce il percorso assoluto della Working Directory.
     * <p/>
     * La directory e' quella dalla quale è stato eseguito Java <br> Il percorso restituito non
     * comprende il separatore finale <br>
     *
     * @return il percorso della User Directory
     */
    static String getUserDir() {
        /* variabili e costanti locali di lavoro */
        String unaDir = null;

        try { // prova ad eseguire il codice
            unaDir = System.getProperty("user.dir");
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return unaDir;
    }


    /**
     * Restituisce il percorso assoluto della home directory del programma.
     * <p/>
     * La home del programma e' la directory dove il programma mantiene tutti i propri dati
     * (preferenze, dati, ecc...) <br> Il percorso restituito non comprende il separatore finale
     * <br>
     *
     * @return il percorso della home directory del programma
     */
    static String getHomeProgramma() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;

        try { // prova ad eseguire il codice
            percorso = LibSistema.getUserHome();
            percorso += CostanteCarattere.SEP_DIR;
            percorso += "algos";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Restituisce il percorso assoluto della directory Preferenze del programma.
     * <p/>
     * La cartella preferenze contiene i file delle preferenze di base e specifiche dell'utente <br>
     * Il percorso restituito non comprende il separatore finale <br>
     *
     * @return il percorso della directory Preferenze
     */
    static String getDirPreferenze() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;

        try { // prova ad eseguire il codice
            percorso = LibSistema.getHomeProgramma();
            percorso += CostanteCarattere.SEP_DIR;
            percorso += "preferenze";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Restituisce il percorso assoluto della directory Dati del programma.
     * <p/>
     * La directory dati e' la cartella dove il programma immagazzina i dati quando usa un database
     * stand-alone <br> Il percorso restituito non comprende il separatore finale <br>
     *
     * @return il percorso della directory Preferenze
     */
    static String getDirDati() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;

        try { // prova ad eseguire il codice
            percorso = LibSistema.getHomeProgramma();
            percorso += CostanteCarattere.SEP_DIR;
            percorso += "dati";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Restituisce il percorso assoluto della directory Dati Standard del programma.
     * <p/>
     * La directory dati standard e' la cartella dove il programma mantiene i dati standard che usa
     * per popolare inizialmente la tavola di un modulo quando e' vuota <br> Il percorso restituito
     * non comprende il separatore finale <br>
     *
     * @return il percorso della directory dati standard
     */
    static String getDirDatiStandard() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;

        try { // prova ad eseguire il codice
            percorso = LibSistema.getHomeProgramma();
            percorso += CostanteCarattere.SEP_DIR;
            percorso += "datistandard";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Restituisce la cartella che contiene i file di aggiornamento del programma specifico.
     * <p/>
     *
     * @return indirizzo completo
     */
    static String getDirUpdate() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;

        try { // prova ad eseguire il codice
            percorso = LibSistema.getHomeProgramma();
            percorso += CostanteCarattere.SEP_DIR;
            percorso += "update";
            percorso += CostanteCarattere.SEP_DIR;
            percorso += Progetto.getCatUpdate();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Restituisce l'indirizzo del database delle preferenze del programma.
     * <p/>
     *
     * @return indirizzo completo
     */
    static String getIndirizzoPreferenze() {
        /* variabili e costanti locali di lavoro */
        String percorso = null;

        try { // prova ad eseguire il codice
            percorso = LibSistema.getHomeProgramma();
            percorso += CostanteCarattere.SEP_DIR;
            percorso += "preferenze";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return percorso;
    }


    /**
     * Restituisce il nome completo del file di configurazione del programma.
     * <p/>
     *
     * @return il nome del file
     */
    private static String getNomeFileConfig() {
        /* variabili e costanti locali di lavoro */
        String nomeCompletoFile = "";
        String nomeProgramma;
        String nomeFile;
        String nomeCartellaModuli;
        String suffisso = ".txt";
        String sep = "/";

        try {    // prova ad eseguire il codice
            /* determina il nome completo del file di configurazione */
            nomeProgramma = Progetto.getNomePrimoModulo();
            nomeFile = nomeProgramma + suffisso;
            nomeCartellaModuli = getDirModuli();
            nomeCompletoFile = nomeCartellaModuli + sep + nomeFile;
        } catch (Exception unErrore) {    // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return nomeCompletoFile;
    }


    /**
     * Restituisce il file di configurazione del programma.
     * <p/>
     *
     * @return il file
     */
    static File getFileConfig() {
        return new File(getNomeFileConfig());
    }


    /**
     * Emette un beep di sistema.
     * <p/>
     */
    static void beep() {
        Toolkit.getDefaultToolkit().beep();
    }


    /**
     * Ritorna la quantità di memoria occupata in Kb.
     * <p/>
     *
     * @return la quantità di memoria occupata in Kb.
     */
    static long getBusyMemory() {
        /* variabili e costanti locali di lavoro */
        long totmem, freemem, busymem = 0;

        try { // prova ad eseguire il codice

            totmem = Runtime.getRuntime().totalMemory();
            freemem = Runtime.getRuntime().freeMemory();
            busymem = totmem - freemem;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return busymem;
    }


}// fine della classe
