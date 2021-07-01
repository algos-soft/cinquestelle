/**
 * Title:     DialogoImpostaData
 * Copyright: Copyright (c) 2004
 * Company:   Algos s.r.l.
 * Author:    alex
 * Date:      7-lug-2009
 */
package it.algos.albergo.ristorante.menu;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.errore.Errore;

/**
 * Dialogo per la duplicazione di un gruppo di menu
 * Presenta un dialogo di selezione/scelta <br>
 * Inserire: data inizio, durata (giorni), data inizio duplicazione  <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 6-lug-2010
 */
public final class DialogoDuplicaGruppo extends DialogoAnnullaConferma {

    /* nome dei campi */
    public static final String NOME_CAMPO_DATA_INIZIO_COPIA = "Giorno inizio copia";
    public static final String NOME_CAMPO_DURATA = "Numero di giorni da copiare";
    public static final String NOME_CAMPO_DATA_INIZIO_INCOLLA = "Giorno inizio incolla";

    /**
     * Costruttore completo senza parametri.
     * <p/>
     */
    public DialogoDuplicaGruppo() {
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
     * Metodo chiamato direttamente dal costruttore <br>
     *
     * @throws Exception unaEccezione
     */
    private void inizia() throws Exception {
        /* variabili e costanti locali di lavoro */
        Campo campo;

        try { // prova ad eseguire il codice

            this.setPreferredSize(440, 350);
            this.setTitolo("Duplicazione menu");
            this.setMessaggio(
                    "Duplica un gruppo di menu.");
//                            + "Data da cui inizia la copia.\n"
//                            + "Durata (in giorni) del periodo da copiare.\n"
//                            + "Data in cui inizia la duplicazione.");


            /* data inizio periodo da copiare */
            campo = CampoFactory.data(NOME_CAMPO_DATA_INIZIO_COPIA);
            campo.decora().obbligatorio();
            this.addCampo(campo);

            /* durata periodo */
            campo = CampoFactory.intero(NOME_CAMPO_DURATA);
            campo.decora().obbligatorio();
            this.addCampo(campo);

            /* data inizio periodo da incollare */
            campo = CampoFactory.data(NOME_CAMPO_DATA_INIZIO_INCOLLA);
            campo.decora().obbligatorio();
            this.addCampo(campo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

    }// fine del metodo inizia

}// fine della classe
