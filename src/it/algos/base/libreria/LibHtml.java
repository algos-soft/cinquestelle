/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      7-lug-2006
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.wrapper.IntStringa;

import java.util.ArrayList;

/**
 * Classe astratta con metodi statici per la gestione del formato Html. </p>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 7-lug-2006 ore 13.31.52
 */
public abstract class LibHtml {

    /**
     * Ritorno a capo.
     */
    private static final String CAPO = "\n";

    /**
     * Separatore virgola.
     */
    private static final String VIRGOLA = ",";

    /**
     * Separatore tab.
     */
    private static final String TAB = "\t";


    /**
     * Costruisce l'inizio di una pagina html.
     *
     * @param utf tipo di codifica del testo
     * @param ref riferimenti esterni (pagine css)
     *
     * @return inizio della pagina
     */
    static String getIniPagina(UTF utf, String ref) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        String html;
        String head = "";
        String headIni;
        String headUtf;
        String headTag;
        String headEnd;
        String body;

        try { // prova ad eseguire il codice
            html = "<HTML>\n";
            headEnd = "</HEAD>\n";
            body = "<BODY>\n";
            headTag = "\">";

            headIni = "<HEAD><META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html";
            headUtf = utf.getTag();
            head += headIni;
            head += headUtf;
            head += headTag;
            head += ref;
            head += headEnd;

            testo += html;
            testo += head;
            testo += body;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce l'inizio di una pagina html.
     *
     * @param utf tipo di codifica del testo
     *
     * @return inizio della pagina
     */
    static String getIniPagina(UTF utf) {
        /* invoca il metodo sovrascritto della classe */
        return LibHtml.getIniPagina(utf, "");
    }


    /**
     * Costruisce l'inizio di una pagina html.
     *
     * @param ref riferimenti esterni (pagine css)
     *
     * @return inizio della pagina
     */
    static String getIniPagina(String ref) {
        /* invoca il metodo sovrascritto della classe */
        return LibHtml.getIniPagina(UTF.utf8, ref);
    }


    /**
     * Costruisce l'inizio di una pagina html.
     *
     * @return inizio della pagina
     */
    static String getIniPagina() {
        /* invoca il metodo sovrascritto della classe */
        return LibHtml.getIniPagina(UTF.utf8, "");
    }


    /**
     * Costruisce l'inizio di una tabella html.
     *
     * @return inizio della tabella
     */
    static String getIniTabella() {
        /* variabili e costanti locali di lavoro */
        String inizio = "";

        try { // prova ad eseguire il codice
            inizio = "\n<TABLE BORDER=1>\n";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return inizio;
    }


    /**
     * Costruisce la referenza al file css.
     *
     * @param nomeFile path locale
     *
     * @return testo della referenza
     */
    static String getCss(String nomeFile) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        String tagIni;
        String tagEnd;

        try { // prova ad eseguire il codice
            continua = Lib.Testo.isValida(nomeFile);

            if (continua) {
                tagIni = "\n<link rel=\"stylesheet\" type=\"text/css\" href=\"";
                tagEnd = "\" />\n";

                testo = tagIni;
                testo += nomeFile;
                testo += tagEnd;
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Allinea a destra il testo.
     *
     * @param testo da allineare
     *
     * @return testo allineato
     */
    static String setDestra(String testo) {
        /* variabili e costanti locali di lavoro */
        String tagIni;
        String tagEnd;

        try { // prova ad eseguire il codice
            tagIni = "<div style=\"text-align: right;\">";
            tagEnd = "</div>";

            testo = tagIni + testo + tagEnd;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce la fine di una tabella html.
     *
     * @return fine della tabella
     */
    static String getEndTabella() {
        /* variabili e costanti locali di lavoro */
        String fine = "";

        try { // prova ad eseguire il codice
            fine = "</TABLE>\n";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return fine;
    }


    /**
     * Costruisce la fine di una pagina html.
     *
     * @return fine della pagina
     */
    static String getEndPagina() {
        /* variabili e costanti locali di lavoro */
        String fine = "";

        try { // prova ad eseguire il codice
            fine = "</BODY></HTML>";
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return fine;
    }


    /**
     * Costruisce i titoli di una tabella html.
     *
     * @param lista dei titoli in formato stringa di testo
     *
     * @return titoli della tabella in formato html
     */
    static String getTitoliTabella(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        String ini;
        String end;
        String iniCampo;
        String endCampo;
        String capo;

        try { // prova ad eseguire il codice
            ini = "<TR>";
            end = "</TR>";
            capo = CAPO;
            iniCampo = "<TH>";
            endCampo = "</TH>";

            /* inizio */
            testo += ini;
            testo += capo;

            /* traverso tutta la collezione */
            for (String stringa : lista) {
                testo += iniCampo;
                testo += stringa;
                testo += endCampo;
                testo += capo;
            } // fine del ciclo for-each

            /* fine */
            testo += end;
            testo += capo;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce i titoli di una tabella html.
     *
     * @param lista dei titoli in formato stringa di testo ed intero
     *
     * @return titoli della tabella in formato html
     */
    static String getTitoliTab(ArrayList<IntStringa> lista) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        String ini;
        String end;
        String iniCampo;
        String iniLar;
        String endCampo;
        String capo;

        try { // prova ad eseguire il codice
            ini = "<TR>";
            end = "</TR>";
            capo = CAPO;
            iniCampo = "<TH width=\"";
            iniLar = "%\">";
            endCampo = "</TH>";

            /* inizio */
            testo += ini;
            testo += capo;

            /* traverso tutta la collezione */
            for (IntStringa wrapper : lista) {
                testo += iniCampo;
                testo += wrapper.getIntero();
                testo += iniLar;
                testo += wrapper.getStringa();
                testo += endCampo;
                testo += capo;
            } // fine del ciclo for-each

            /* fine */
            testo += end;
            testo += capo;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce i titoli di una tabella html.
     * <p/>
     * I titoli possono essere separati da \t o da virgola <br>
     *
     * @param titoli stringa di testo coi titoli in formato normale
     * @param sep separatore dei titoli (tab o virgola)
     *
     * @return titoli della tabella in formato html
     */
    static String getTitoliTabella(String titoli, String sep) {
        return LibHtml.getTitoliTabella(Lib.Array.creaLista(titoli, sep));
    }


    /**
     * Costruisce i titoli di una tabella html.
     * <p/>
     * I titoli devono essere separati da virgola <br>
     *
     * @param titoli stringa di testo coi titoli in formato normale
     *
     * @return titoli della tabella in formato html
     */
    static String getTitoliTabella(String titoli) {
        return LibHtml.getTitoliTabella(titoli, VIRGOLA);
    }


    /**
     * Costruisce la riga di una tabella html.
     * <p/>
     *
     * @param lista dei campi in formato stringa di testo
     *
     * @return campi della tabella in formato html
     */
    static String getRigaTabella(ArrayList<String> lista) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        String ini;
        String end;
        String iniCampo;
        String endCampo;
        String capo;

        try { // prova ad eseguire il codice
            ini = "<TR>";
            end = "</TR>";
            capo = CAPO;
            iniCampo = "<TD>";
            endCampo = "</TD>";

            /* inizio */
            testo += ini;
            testo += capo;

            /* traverso tutta la collezione */
            for (String stringa : lista) {
                testo += iniCampo;
                testo += stringa;
                testo += endCampo;
                testo += capo;
            } // fine del ciclo for-each

            /* fine */
            testo += end;
            testo += capo;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce la riga di una tabella html.
     * <p/>
     * I campi della riga devono essere separati da tab <br>
     *
     * @param riga stringa di testo coi campi in formato normale
     *
     * @return campi della tabella in formato html
     */
    static String getRigaTabella(String riga) {
        return LibHtml.getRigaTabella(Lib.Array.creaListaDura(riga, TAB));
    }


    /**
     * Costruisce il riferimento ad un link esterno.
     * <p/>
     *
     * @param link riferimento attivo
     * @param titolo tool tip testo
     * @param testo testo visibile
     *
     * @return testo html
     */
    static String getLink(String link, String titolo, String testo) {
        /* variabili e costanti locali di lavoro */
        String html = "";
        String ini;
        String tit;
        String midi;
        String end;

        try { // prova ad eseguire il codice
            ini = "<a href=\"";
            tit = "\" title=\"";
            midi = "\">";
            end = "</a>";

            html = ini;
            html += link;
            html += tit;
            html += titolo;
            html += midi;
            html += testo;
            html += end;

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return html;
    }


    /**
     * Costruisce il riferimento ad un link della wikipedia.
     * <p/>
     *
     * @param link riferimento attivo
     * @param titolo tool tip testo
     * @param testo testo visibile
     *
     * @return testo html
     */
    static String getLinkWiki(String link, String titolo, String testo) {
        /* variabili e costanti locali di lavoro */
        String html = "";
        String wiki;
        String enciclopedia;

        try { // prova ad eseguire il codi
            wiki = "http://it.wikipedia.org/wiki/";
            enciclopedia = " su wikipedia";

            link = wiki + link;
            titolo += enciclopedia;

            html = LibHtml.getLink(link, titolo, testo);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return html;
    }


    /**
     * Classe interna Enumerazione.
     */
    public enum UTF {

        ascii("US-ASCII"),
        iso("ISO-8859-1"),
        utf8("UTF-8"),
        utf16("UTF-16");

        /**
         * tag da utilizzare
         */
        private String nome;


        /**
         * Costruttore completo con parametri.
         *
         * @param nome utilizzato in OutputStreamWriter e simili
         */
        UTF(String nome) {
            try { // prova ad eseguire il codice
                /* regola le variabili di istanza coi parametri */
                this.setNome(nome);
            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        public String getTag() {
            return "; charset=" + this.getNome();
        }


        private String getNome() {
            return nome;
        }


        private void setNome(String nome) {
            this.nome = nome;
        }


        public String toString() {
            return this.getNome();
        }

    }// fine della classe

}// fine della classe
