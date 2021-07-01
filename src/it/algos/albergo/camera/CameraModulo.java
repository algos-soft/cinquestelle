/**
 * Copyright(c): 2006
 * Company: Algos s.r.l.
 * Author: gac
 * Date: 2 feb 2006
 */

package it.algos.albergo.camera;

import it.algos.albergo.camera.accessori.Accessori;
import it.algos.albergo.camera.compoaccessori.CompoAccessoriModulo;
import it.algos.albergo.camera.composizione.CompoCamera;
import it.algos.albergo.camera.composizione.CompoCameraModulo;
import it.algos.albergo.camera.righecameracompo.RCCModulo;
import it.algos.albergo.camera.zona.Zona;
import it.algos.albergo.camera.zona.ZonaModulo;
import it.algos.base.albero.AlberoNodo;
import it.algos.base.errore.Errore;
import it.algos.base.modulo.Modulo;
import it.algos.base.modulo.ModuloBase;
import it.algos.base.progetto.Progetto;
import it.algos.base.query.filtro.Filtro;

/**
 * Camera - Contenitore dei riferimenti agli oggetti del package.
 * <p/>
 * Questa classe concreta: <ul>
 * <li> Contiene tutti i riferimenti agli oggetti delle classi che servono
 * per gestire una parte del programma </li>
 * <li> Regola il riferimento al Modello specifico (obbligatorio) </li>
 * <li> Regola i titoli di Menu e Finestra del Navigatore </li>
 * <li> Regola eventualmente alcuni aspetti specifici del Navigatore </li>
 * <li> Crea altri eventuali <strong>Moduli</strong> indispensabili per il
 * funzionamento di questo modulo </li>
 * <li> Rende visibili nel Menu gli altri moduli </li>
 * <li> Regola eventuali funzionalit&agrave; specifiche del Navigatore </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0 / 2 feb 2006
 */
public final class CameraModulo extends ModuloBase {

    /**
     * Nome-chiave interno di questo modulo (obbligatorio)
     */
    private static final String NOME_CHIAVE = Camera.NOME_MODULO;

    /**
     * Titolo della finestra del navigatore
     * (usato nel Navigatore)
     */
    private static final String TITOLO_FINESTRA = Camera.TITOLO_FINESTRA;

    /**
     * Titolo del modulo come appare nel Menu Moduli degli altri moduli
     * (usato nel Navigatore)
     */
    private static final String TITOLO_MENU = Camera.TITOLO_MENU;

    /**
     * nome del programma che compare in basso (se vuoto, usa il default)
     * viene usato il nome del modulo che parte per primo
     */
    private static final String NOME_PROGRAMMA = TITOLO_FINESTRA;


    /**
     * Costruttore provvisorio, usato solo per portarsi il percorso
     * della classe (implicito) ed il nome chiave (esplicito).
     */
    public CameraModulo() {
        /* regola la variabile di istanza con la costante */
        super.setNomeModulo(NOME_CHIAVE);

        /* regola il nome del programma (se questo modulo parte per primo) */
        super.setNomeProgramma(NOME_PROGRAMMA);
    } /* fine del metodo costruttore provvisorio */


    /**
     * Costruttore completo.
     *
     * @param unNodo nodo dell'albero moduli
     */
    public CameraModulo(AlberoNodo unNodo) {
        /* rimanda al costruttore della superclasse */
        super(NOME_CHIAVE, unNodo);

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

        try { // prova ad eseguire il codice

            /* selezione del modello (obbligatorio) */
            super.setModello(new CameraModello());

            /* regola il titolo della finestra del navigatore */
            super.setTitoloFinestra(TITOLO_FINESTRA);

            /* regola il titolo di questo modulo nei menu di altri moduli */
            super.setTitoloMenu(TITOLO_MENU);

            /* regola il flag (le tabelle usano finestre piu' piccole) (facoltativo) */
            super.setTabella(true);

            /* selezione della scheda (facoltativo) */
            super.addSchedaNavCorrente(new CameraScheda(this));
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }


    /**
     * Crea tutti i Moduli indispensabili per il funzionamento di questo modulo.
     * <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * un'istanza provvisoria del modulo <br>
     * Questa istanza viene usata solo per portarsi il percorso della
     * classe (implicito) ed il nome chiave (esplicito) <br>
     * La creazione definitiva del Modulo viene delegata alla classe
     * Progetto nel metodo creaModulo() <br>
     */
    protected void creaModuli() {
        try { // prova ad eseguire il codice

            super.creaModulo(new CompoCameraModulo());
            super.creaModulo(new ZonaModulo());
            super.creaModulo(new RCCModulo());
            super.creaModulo(new CompoAccessoriModulo());  // crea CompoCamera e Accessori

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Aggiunge i moduli (e le tabelle) al menu.
     * <br>
     * Aggiunge alla collezione moduli (di questo modulo), gli eventuali
     * moduli (o tabelle), che verranno poi inserite nel menu moduli e
     * tabelle, dalla classe Navigatore <br>
     * I moduli e le tabelle appaiono nei rispettivi menu, nell'ordine in
     * cui sono elencati in questo metodo <br>
     * Per ogni modulo, invoca il metodo della superclasse, passandogli
     * il nome-chiave del modulo <br>
     */
    protected void addModuliVisibili() {
        try { // prova ad eseguire il codice
            super.addModuloVisibile(Zona.NOME_MODULO);
            super.addModuloVisibile(CompoCamera.NOME_MODULO);
            super.addModuloVisibile(Accessori.NOME_MODULO);

//            super.addModuloVisibile(CompoAccessori.NOME_MODULO);
            
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo


    /**
     * Restituisce il numero di letti totali di una camera.
     * <p/>
     *
     * @param codCamera codice della camera
     *
     * @return il numero di letti totali della camera
     */
    public static int getNumLetti(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int numLetti = 0;
        int quantiAdulti;
        int quantiBambini;

        try { // prova ad eseguire il codice

            quantiAdulti = CameraModulo.getNumLettiAdulti(codCamera);
            quantiBambini = CameraModulo.getNumLettiBambini(codCamera);
            numLetti = quantiAdulti + quantiBambini;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numLetti;
    }


    /**
     * Restituisce il numero di letti per adulti di una camera.
     * <p/>
     *
     * @param codCamera codice della camera
     *
     * @return il numero di letti per adulti della camera
     */
    public static int getNumLettiAdulti(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int numLetti = 0;
        int codCompo;
        Modulo modCompo;

        try { // prova ad eseguire il codice

            codCompo = getComposizioneStandard(codCamera);
            modCompo = CompoCameraModulo.get();
            numLetti = modCompo.query().valoreInt(CompoCamera.Cam.numadulti.get(), codCompo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numLetti;
    }


    /**
     * Restituisce il numero di letti per bambini di una camera.
     * <p/>
     *
     * @param codCamera codice della camera
     *
     * @return il numero di letti per adulti della camera
     */
    public static int getNumLettiBambini(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int numLetti = 0;
        int codCompo;
        Modulo modCompo;

        try { // prova ad eseguire il codice

            codCompo = getComposizioneStandard(codCamera);
            modCompo = CompoCameraModulo.get();
            numLetti = modCompo.query().valoreInt(CompoCamera.Cam.numbambini.get(), codCompo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return numLetti;
    }


    /**
     * Restituisce la composizione standard di una camera.
     * <p/>
     *
     * @param codCamera codice della camera
     *
     * @return il numero di letti della camera
     */
    public static int getComposizioneStandard(int codCamera) {
        /* variabili e costanti locali di lavoro */
        int codCompo = 0;
        Modulo modCamera;

        try { // prova ad eseguire il codice

            modCamera = CameraModulo.get();
            codCompo = modCamera.query().valoreInt(Camera.Cam.composizione.get(), codCamera);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return codCompo;
    }


    /**
     * Restituisce il filtro che seleziona le possibili composizioni per una data camera.
     * <p/>
     * Il filtro si applica al modulo CompoCameraModulo
     * @param codCamera codice della camera
     *
     * @return il filtro che seleziona le composizioni possibili,
     * null se non sono state definite composizioni possibili
     */
    public static Filtro getFiltroComposizioniPossibili(int codCamera) {
        return CameraLib.getFiltroComposizioniPossibili(codCamera);
    }



    /**
     * Restituisce in runtime l'istanza presente in progetto.
     * <p/>
     *
     * @return modulo presente in Progetto
     */
    public static CameraModulo get() {
        return (CameraModulo)ModuloBase.get(CameraModulo.NOME_CHIAVE);
    }


    /**
     * Main method.
     *
     * @param argomenti eventuali parametri in ingresso (quasi mai)
     */
    public static void main(String[] argomenti) {
        /* crea una istanza di se stessa */
        Modulo primoModuloProvvisorio = new CameraModulo();

        /**
         * inizializza tutti i moduli usati da questo modulo <br>
         * lancia il modulo iniziale del programma (obbligatorio) <br>
         */
        Progetto.lancia(argomenti, primoModuloProvvisorio);
    } // fine del metodo main

} // fine della classe
