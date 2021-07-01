package it.algos.base.libreria;

import it.algos.base.campo.base.Campo;
import it.algos.base.campo.base.CampoFactory;
import it.algos.base.dialogo.Dialogo;
import it.algos.base.dialogo.DialogoAnnullaConferma;
import it.algos.base.dialogo.DialogoFactory;
import it.algos.base.errore.Errore;

import java.util.Date;

/**
 * Repository per costruire dialoghi.
 * </p>
 * Tutti i metodi sono statici <br>
 * I metodi non hanno modificatore;
 * sono visibili all'esterno del package solo utilizzando l'interfaccia unificata <br>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 28-feb-2004 ore 8.50.10
 */
public abstract class LibDialogo {


    /**
     * Costruisce ed avvia un dialogo con i bottoni annulla e conferma.
     * <p/>
     *
     * @param titolo della finestra di dialogo
     *
     * @return valido o annullato
     */
    static boolean annullaConferma(String titolo) {
        /* variabili locali di lavoro */
        boolean valido = false;
        Dialogo dialogo;

        try { // prova ad eseguire il codice

            dialogo = DialogoFactory.annullaConferma(titolo);
            dialogo.avvia();

            valido = dialogo.isConfermato();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return valido;
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo.
     * <p/>
     * Se titolo della finestra di dialogo è nullo, usa il default <br>
     * Se l'etichetta del campo è nulla, usa il default <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     * @param valore di default  suggerito
     * @param campo specifico di input
     * @param titolo della finestra di dialogo
     * @param etichetta del campo di input
     *
     * @return dialogo costruito
     */
    private static DialogoInserimento crea(String messaggio,
                                           Object valore,
                                           Campo campo,
                                           String titolo,
                                           String etichetta) {
        /* variabili locali di lavoro */
        DialogoInserimento dialogo = null;
        boolean messaggioValido;

        try { // prova ad eseguire il codice
            /* costruisce l'istanza dialogo */
            dialogo = new DialogoInserimento(campo);

            /* controlla se esiste un messaggio */
            messaggioValido = Lib.Testo.isValida(messaggio);

            /* regola il titolo del dialogo */
            if (Lib.Testo.isVuota(titolo)) {
                titolo = "Inserimento";
            } // fine del blocco if
            dialogo.setTitolo(titolo);

            if (messaggioValido) {
                dialogo.setMessaggio(messaggio);
            } // fine del blocco if

            /* costruisce e regola il campo di input */
            if (messaggioValido) {
                campo.decora().eliminaEtichetta();
            } else {
                if (Lib.Testo.isVuota(etichetta)) {
                    etichetta = "Valore da inserire";
                } // fine del blocco if
                campo.decora().etichetta(etichetta);
            } // fine del blocco if-else

            /* inserisce eventuale valore di default */
            if (Lib.Clas.isValidi(valore)) {
                campo.setValore(valore);
            } // fine del blocco if

            dialogo.addCampo(campo);

            /* avvia il dialogo */
            dialogo.avvia();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dialogo;
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     * @param valore di default  suggerito
     * @param campo specifico di input
     * @param titolo della finestra di dialogo
     *
     * @return dialogo costruito
     */
    private static DialogoInserimento crea(String messaggio,
                                           Object valore,
                                           Campo campo,
                                           String titolo) {
        /* invoca il metodo sovrascritto */
        return LibDialogo.crea(messaggio, valore, campo, titolo, "");
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     * @param valore di default  suggerito
     * @param campo specifico di input
     *
     * @return dialogo costruito
     */
    private static DialogoInserimento crea(String messaggio, Object valore, Campo campo) {
        /* invoca il metodo sovrascritto */
        return LibDialogo.crea(messaggio, valore, campo, "");
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo testo.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     * @param testoDefault suggerito
     *
     * @return testo inserito dall'utente
     */
    static String creaTesto(String messaggio, String testoDefault) {
        /* variabili locali di lavoro */
        String testoValido = "";
        String titolo = "Testo";
        String etichetta = "Testo da inserire";
        String nomeCampo = "nonserve";
        DialogoInserimento dialogo;
        Campo campo;

        try { // prova ad eseguire il codice
            /* costruisce l'istanza dialogo */
            campo = CampoFactory.testo(nomeCampo);
            dialogo = crea(messaggio, testoDefault, campo, titolo, etichetta);

            /* se il dialogo è confermato, restituisce il valore */
            if (dialogo.isConfermato()) {
                testoValido = dialogo.getString(nomeCampo);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return testoValido;
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo testo.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     *
     * @return testo inserito dall'utente
     */
    static String creaTesto(String messaggio) {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaTesto(messaggio, "");
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo testo.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * L'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @return testo inserito dall'utente
     */
    static String creaTesto() {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaTesto("", "");
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo numero intero.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo numero è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo numero viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     * @param numeroDefault suggerito
     *
     * @return valore numerico intero inserito dall'utente
     */
    static int creaIntero(String messaggio, int numeroDefault) {
        /* variabili locali di lavoro */
        int interoValido = 0;
        String titolo = "Intero";
        String etichetta = "Numero intero da inserire";
        String nomeCampo = "nonserve";
        DialogoInserimento dialogo;
        Campo campo;

        try { // prova ad eseguire il codice
            /* costruisce l'istanza dialogo */
            campo = CampoFactory.intero(nomeCampo);
            dialogo = crea(messaggio, numeroDefault, campo, titolo, etichetta);

            /* se il dialogo è confermato, restituisce il valore */
            if (dialogo.isConfermato()) {
                interoValido = dialogo.getInt(nomeCampo);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return interoValido;
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo numero intero.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo numero è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo numero viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     *
     * @return valore numerico intero inserito dall'utente
     */
    static int creaIntero(String messaggio) {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaIntero(messaggio, 0);
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo numero intero.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * L'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @return valore numerico intero inserito dall'utente
     */
    static int creaIntero() {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaIntero("", 0);
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo data.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     * @param dataDefault suggerita
     *
     * @return data inserita dall'utente
     */
    static Date creaData(String messaggio, Date dataDefault) {
        /* variabili locali di lavoro */
        Date dataValida = null;
        String titolo = "Data";
        String etichetta = "Data da inserire";
        String nomeCampo = "nonserve";
        DialogoInserimento dialogo;
        Campo campo;

        try { // prova ad eseguire il codice
            /* costruisce l'istanza dialogo */
            campo = CampoFactory.testo(nomeCampo);
            dialogo = crea(messaggio, dataDefault, campo, titolo, etichetta);

            /* se il dialogo è confermato, restituisce il valore */
            if (dialogo.isConfermato()) {
                dataValida = dialogo.getData(nomeCampo);
            } // fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        } // fine del blocco try-catch

        /* valore di ritorno */
        return dataValida;
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo data.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     *
     * @return data inserita dall'utente
     */
    static Date creaData(String messaggio) {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaData(messaggio, Lib.Data.getVuota());
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo data.
     * <p/>
     * Il titolo della finestra di dialogo è fisso <br>
     * L'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @return data inserita dall'utente
     */
    static Date creaData() {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaData("", Lib.Data.getVuota());
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo data.
     * <p/>
     * Viene suggerita la data odierna (modificabile) <br>
     * Il titolo della finestra di dialogo è fisso <br>
     * Se il messaggio è valido, l'etichetta del campo testo è vuota <br>
     * Se il messaggio non è valido, l'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @param messaggio di testo posizionato sopra il campo, nella parte superiore del dialogo
     *
     * @return data inserita dall'utente
     */
    static Date creaDataOggi(String messaggio) {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaData(messaggio, Lib.Data.getCorrente());
    }


    /**
     * Costruisce ed avvia un dialogo per l'input di un campo data.
     * <p/>
     * Viene suggerita la data odierna (modificabile) <br>
     * Il titolo della finestra di dialogo è fisso <br>
     * L'etichetta del campo testo viene inserita di default <br>
     * Il dialogo è del tipo annulla/conferma <br>
     *
     * @return data inserita dall'utente
     */
    static Date creaDataOggi() {
        /* invoca il metodo sovrascritto */
        return LibDialogo.creaDataOggi("");
    }


    /**
     * Classe 'interna'. </p>
     */
    public final static class DialogoInserimento extends DialogoAnnullaConferma {

        /**
         * Campo di controllo per la validità del dialogo ai fini della confermabilità.
         */
        private Campo campo;


        /**
         * Costruttore senza parametri.
         */
        DialogoInserimento() {
            /* rimanda al costruttore di questa classe */
            this(null);
        } /* fine del metodo costruttore */


        /**
         * Costruttore completo con parametri.
         *
         * @param campo di controllo per la validità del dialogo ai fini della confermabilità
         */
        DialogoInserimento(Campo campo) {
            /* invoca il costruttore coi parametri */
            super();

            /* regola le variabili di istanza coi parametri */
            this.setCampo(campo);
        } /* fine del metodo costruttore completo */


        /**
         * Determina se il dialogo è confermabile o registrabile.
         * <p/>
         *
         * @return true se confermabile / registrabile
         */
        @Override public boolean isConfermabile() {
            /* variabili e costanti locali di lavoro */
            boolean confermabile = false;
            Campo unCampo;
            Object ogg;

            try { // prova ad eseguire il codice
                unCampo = this.getCampo();

                if (unCampo != null) {
                    ogg = unCampo.getValore();
                    confermabile = (Lib.Clas.isValidi(ogg));
                } // fine del blocco if

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return confermabile;
        }


        private Campo getCampo() {
            return campo;
        }


        public void setCampo(Campo campo) {
            this.campo = campo;
        }
    } // fine della classe 'interna'

}// fine della classe
