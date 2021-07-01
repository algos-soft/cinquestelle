/**
 * Title:     DialogoBackup
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      26-gen-2007
 */
package it.algos.base.backup;

import it.algos.base.errore.Errore;
import it.algos.base.libreria.Lib;

/**
 * Dialogo di impostazione del backup.
 * </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author alex
 * @version 1.0    / 26-gen-2007
 */
public class DialogoBackup extends DialogoBR {


    /**
     * Costruttore completo con parametri.
     * <p/>
     *
     * @param backup oggetto Backup di riferimento
     */
    public DialogoBackup(Backup backup) {
        /* rimanda al costruttore della superclasse */
        super(backup);

        try { // prova ad eseguire il codice
            /* regolazioni iniziali di riferimenti e variabili */
            this.inizia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }// fine del metodo costruttore completo


    /**
     * Regolazioni immediate di riferimenti e variabili. <br>
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */

        try { // prova ad eseguire il codice
            this.setTitolo("Backup");
            this.setMessaggio("Effettua un backup del database");
            this.setIcona(Lib.Risorse.getIconaBase("backup32"));
            this.avvia();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Recupera il voce del dialogo.
     * <p/>
     *
     * @return il voce del dialogo
     */
    protected String getTitoloDialogo() {
        return ("Seleziona la destinazione del backup");
    }


    /**
     * Recupera il voce del campo percorso.
     * <p/>
     *
     * @return il voce del campo percorso
     */
    protected String getTitoloCampoPercorso() {
        /* valore di ritorno */
        return "Seleziona una cartella di destinazione del backup";
    }

}// fine della classe
