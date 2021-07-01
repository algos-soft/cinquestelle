/**
 * Copyright: Copyright (c) 2007
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      15-apr-2007
 */

package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.wrapper.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Repository di funzionalità per l'utilizzo del server wiki. </p> Questa classe astratta: <ul> <li>
 * </li> <li>  </li> </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 15-apr-2007 ore 10.30.12
 */
public abstract class LibWiki {

    /**
     * Costruttore semplice senza parametri. <br> Indispensabile anche se non viene utilizzato
     * (anche solo per compilazione in sviluppo) <br>
     */
    public LibWiki() {
        /* rimanda al costruttore della superclasse */
        super();
    }// fine del metodo costruttore semplice


    /**
     * Recupera una pagina dalla wikipedia italiana.
     * <p/>
     * Recupera il testo in modalità modifica (e non lettura) <br> Estrae il testo significativo
     * <br>
     *
     * @param pagina titolo della pagina
     *
     * @return testo significativo della pagina richiesta
     */
    static String getPagina(String pagina) throws Exception {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua = false;
        String titolo;
        String indirizzo;
        URL url;
        URLConnection connessione;
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader buffer;
        StringBuffer testoBuff;
        String stringa;
        String testoGrezzo;

        try { // prova ad eseguire il codice
            titolo = LibWiki.getNomeWikiUTF8(pagina);

            indirizzo = "http://it.wikipedia.org/w/index.php?title=";
            indirizzo += titolo;
            indirizzo += "&action=edit";

            url = new URL(indirizzo);

            connessione = url.openConnection();
            connessione.setAllowUserInteraction(true);
            connessione.setDoInput(true);
            connessione.setDoOutput(true);
            connessione.setRequestProperty("User-Agent", "Gacbot");

            try { // prova ad eseguire il codice
                connessione.connect();
                continua = true;
            } catch (Exception unErrore) { // intercetta l'errore
            }// fine del blocco try-catch

            if (continua) {
                /* regola l'entrata */
                input = connessione.getInputStream();

                inputReader = new InputStreamReader(input, "UTF8");
                buffer = new BufferedReader(inputReader);

                testoBuff = new StringBuffer();
                while ((stringa = buffer.readLine()) != null) {
                    testoBuff.append(stringa);
                    testoBuff.append("\n");
                }

                buffer.close();
                inputReader.close();
                input.close();

                testoGrezzo = testoBuff.toString();
                testo = LibWiki.getContenuto(testoGrezzo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Estrae il contenuto significativo di un articolo.
     * <p/>
     * Estrae l'area di testo editabile <br> Corrisponde al testo modificabile in modifica articolo
     * <br> Vengono testate entrambe le possibili grafie del tag di chiusura  <br>
     *
     * @param testoGrezzo completo della pagina
     *
     * @return contenuto significativo (e visibile)
     */
    private static String getContenuto(String testoGrezzo) {
        /* variabili e costanti locali di lavoro */
        String contenuto = "";
        String inizio = "<textarea";
        String stop = ">";
        String fineA = "</textarea>";
        String fineB = "<textarea/>";

        try { // prova ad eseguire il codice

            /* invoca il metodo delegato */
            contenuto = LibWiki.getTestoBase(testoGrezzo, inizio, stop, fineA, fineB);

            /* questo metodo viene sempre chiamato */
            contenuto = Entity.trasforma(contenuto);
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return contenuto;
    }


    /**
     * Estrae una parte del testo di un articolo.
     * <p/>
     * Estrae il testo compreso fra i parametri <br> L'inizio è di composizione variabile; viene
     * indicato l'inizio e la fine <br> Vengono testate entrambe le possibili grafie del tag di
     * chiusura  <br>
     *
     * @param testoIn della pagina
     * @param inizio del testo da considerare
     * @param iniStop termine dell'inizio del testo da considerare
     * @param fineA una delle due opzioni di chiusura del testo da considerare
     * @param fineB una delle due opzioni di chiusura del testo da considerare
     *
     * @return contenuto significativo
     */
    private static String getTestoBase(String testoIn,
                                       String inizio,
                                       String iniStop,
                                       String fineA,
                                       String fineB) {

        /* variabili e costanti locali di lavoro */
        String testoOut = "";
        int posStart;
        int posEndA;
        int posEndB;
        int posEnd;

        try { // prova ad eseguire il codice
            /* ricopio il testo per sicurezza */
            testoOut = testoIn;

            /* recupera la posizione di inizio del tag */
            posStart = testoIn.indexOf(inizio);

            /* controlla che esista un contenuto */
            if (posStart != -1) {
                if (iniStop.length() > 0) {
                    /* recupera la posizione di inizio del contenuto*/
                    posStart = testoIn.indexOf(iniStop, posStart);

                    /* il testo inizia subito dopo la fine del tag */
                    posStart += iniStop.length();
                } else {
                    /* il testo inizia subito dopo la fine del tag */
                    posStart += inizio.length();
                }// fine del blocco if-else

                /* recupera la posizione di fine del contenuto*/
                if (fineA.equals("") && fineB.equals("")) {
                    testoOut = testoIn.substring(posStart);
                } else {
                    posEndA = testoIn.indexOf(fineA, posStart);
                    posEndB = testoIn.indexOf(fineB, posStart);
                    posEnd = Math.max(posEndA, posEndB);
                    if (posEnd != -1) {
                        if (posStart < posEnd) {
                            testoOut = testoIn.substring(posStart, posEnd);
                        }// fine del blocco if
                    }// fine del blocco if
                }// fine del blocco if-else

            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoOut;
    }


    /**
     * Formatta il nome in maniera corretta secondo la codifica UTF-8.
     * <p/>
     * Aggiunge un underscore per gli spazi vuoti <br> Forza maiuscolo il primo carattere <br>
     * Encoder UTF-8 <br>
     *
     * @param paginaIn nome semplice della pagina (articolo o categoria)
     *
     * @return nome in formato wiki compatibile
     */
    private static String getNomeWikiUTF8(String paginaIn) {
        /* variabili e costanti locali di lavoro */
        String paginaOut = "";
        String vuoto = " ";
        String under = "_";

        try { // prova ad eseguire il codice
            paginaOut = paginaIn;

            /* Aggiunge un underscore per gli spazi vuoti */
            if (paginaIn.indexOf(vuoto) != -1) {
                paginaOut = paginaIn.replaceAll(vuoto, under);
            }// fine del blocco if

            /* Forza maiuscolo il primo carattere */
            paginaOut = Lib.Testo.primaMaiuscola(paginaOut);

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return paginaOut;
    }


    /**
     * Controlla se la tabella continee la colonna numerica di ordinamento a sinistra.
     * <p/>
     *
     * @param righe complete della tabella
     *
     * @return vero se esiste la colonna numerica a sinistra
     */
    private static boolean isOrdinata(String[] righe) {
        /* variabili e costanti locali di lavoro */
        boolean ordinata = false;
        String tagOrd = "#!!";
        String tagOrd2 = "# !!";

        try { // prova ad eseguire il codice
            if (righe.length > 2) {
                ordinata = (righe[1].contains(tagOrd) || righe[1].contains(tagOrd2));
            }// fine del blocco if
        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ordinata;
    }


    /**
     * Offset delle colonne se esiste la colonna numerica di ordinamento a sinistra.
     * <p/>
     *
     * @param righe complete della tabella
     *
     * @return zero se la colonna numerica non esiste uno se esiste la colonna numerica
     */
    private static int getColonna(String[] righe) {
        /* variabili e costanti locali di lavoro */
        int offset = 0;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (righe != null && righe.length > 0);

            if (continua) {
                if (LibWiki.isOrdinata(righe)) {
                    offset = 1;
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) {
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return offset;
    }


    /**
     * Recupera il testo da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return testo della tabella selezionata
     */
    public static String getTesto(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        String testoTabella = "";
        boolean continua;
        String sep = "";
        String[] tabelle = null;
        String tagIniA = "{{prettytable}}";
        String tagIniB = "class=\"wikitable sortable\"";
        String tagIniC = "class=\"prettytable sortable\"";
        String tagIniD = "class=\"wikitable\"";
        String tagEnd = "|}";
        String tagSepA = "prettytable";
        String tagSepB = "wikitable sortable";
        String tagSepC = "prettytable sortable";
        String tagSepD = "wikitable";
        int posEnd;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                if (testoPagina.contains(tagIniA)) {
                    sep = tagSepA;
                } else {
                    if (testoPagina.contains(tagIniB)) {
                        sep = tagSepB;
                    } else {
                        if (testoPagina.contains(tagIniC)) {
                            sep = tagSepC;
                        } else {
                            if (testoPagina.contains(tagIniD)) {
                                sep = tagSepD;
                            }// fine del blocco if
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if-else
                continua = Lib.Testo.isValida(sep);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(sep);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                testoTabella = tabelle[posTabella];
                posEnd = testoTabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    testoTabella = testoTabella.substring(0, posEnd);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testoTabella;
    }


    /**
     * Recupera una lista di dieci colonne dal testo di una pagina o di una tabella.
     * <p/>
     * <p/>
     * Vngono recuperate le prime dieci colonne; eventuali altre colonne vengono ignorate <br> La
     * tabella può contenere o meno una colonna sinistra di numeri d'ordine che vengono ignorati
     * <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di dieci stringhe, una per ognuna delle prime dieci colonne significative della
     *         tabella
     */
    public static ArrayList<DieciStringhe> getPretty(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> lista = null;
        boolean continua;
        DieciStringhe wrap;
        String[] righe = null;
        String[] parti;
        String tagRiga = "\\|-";
        String tagParte = "\\|\\|";
        String tagValida = "||";
        String pipe = "|";
        String prima;
        String seconda;
        String terza;
        String quarta;
        String quinta;
        String sesta;
        String settima;
        String ottava;
        String nona;
        String decima;
        int col;
        boolean isPrimaRiga = true;
        String tagIniA = "{{prettytable}}";
        String tagIniB = "class=\"wikitable sortable\"";
        String tagIniC = "class=\"prettytable sortable\"";
        String tagIniD = "class=\"wikitable\"";
        String tagEnd = "|}";
        int posIni;
        int posEnd;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                if (testo.contains(tagIniA)) {
                    posIni = testo.indexOf(tagIniA);
                    posIni += tagIniA.length();
                    posEnd = testo.indexOf(tagEnd, posIni);
                    if (posEnd != -1) {
                        testo = testo.substring(posIni, posEnd);
                    }// fine del blocco if
                } else {
                    if (testo.contains(tagIniB)) {
                        posIni = testo.indexOf(tagIniB);
                        posIni += tagIniB.length();
                        posEnd = testo.indexOf(tagEnd, posIni);
                        if (posEnd != -1) {
                            testo = testo.substring(posIni, posEnd);
                        }// fine del blocco if
                    } else {
                        if (testo.contains(tagIniC)) {
                            posIni = testo.indexOf(tagIniC);
                            posIni += tagIniC.length();
                            posEnd = testo.indexOf(tagEnd, posIni);
                            if (posEnd != -1) {
                                testo = testo.substring(posIni, posEnd);
                            }// fine del blocco if
                        } else {
                            if (testo.contains(tagIniD)) {
                                posIni = testo.indexOf(tagIniD);
                                posIni += tagIniD.length();
                                posEnd = testo.indexOf(tagEnd, posIni);
                                if (posEnd != -1) {
                                    testo = testo.substring(posIni, posEnd);
                                }// fine del blocco if
                            }// fine del blocco if
                        }// fine del blocco if-else
                    }// fine del blocco if-else
                }// fine del blocco if-else
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<DieciStringhe>();
                col = LibWiki.getColonna(righe);

                for (String riga : righe) {

                    riga = riga.trim();
                    parti = riga.split(tagParte);
                    if (riga.contains(tagValida) && parti != null) {
                        if (isPrimaRiga && Lib.Testo.isVuota(parti[0])) {
                            col++;
                            isPrimaRiga = false;
                        }// fine del blocco if

                        seconda = "";
                        terza = "";
                        quarta = "";
                        quinta = "";
                        sesta = "";
                        settima = "";
                        ottava = "";
                        nona = "";
                        decima = "";

                        prima = parti[col].trim();
                        if (prima.startsWith(pipe)) {
                            prima = Lib.Testo.dopo(prima, pipe);
                        }// fine del blocco if
                        prima = Lib.Testo.levaQuadre(prima);

                        if (parti.length > col + 1) {
                            seconda = parti[col + 1].trim();
                            if (seconda.startsWith(pipe)) {
                                seconda = Lib.Testo.dopo(seconda, pipe);
                            }// fine del blocco if
                            seconda = Lib.Testo.levaQuadre(seconda);
                        }// fine del blocco if

                        if (parti.length > col + 2) {
                            terza = parti[col + 2].trim();
                            if (terza.startsWith(pipe)) {
                                terza = Lib.Testo.dopo(terza, pipe);
                            }// fine del blocco if
                            terza = Lib.Testo.levaQuadre(terza);
                        }// fine del blocco if

                        if (parti.length > col + 3) {
                            quarta = parti[col + 3].trim();
                            if (quarta.startsWith(pipe)) {
                                quarta = Lib.Testo.dopo(quarta, pipe);
                            }// fine del blocco if
                            quarta = Lib.Testo.levaQuadre(quarta);
                        }// fine del blocco if

                        if (parti.length > col + 4) {
                            quinta = parti[col + 4].trim();
                            if (quinta.startsWith(pipe)) {
                                quinta = Lib.Testo.dopo(quinta, pipe);
                            }// fine del blocco if
                            quinta = Lib.Testo.levaQuadre(quinta);
                            quinta = Lib.Testo.levaSmall(quinta);
                        }// fine del blocco if

                        if (parti.length > col + 5) {
                            sesta = parti[col + 5].trim();
                            if (sesta.startsWith(pipe)) {
                                sesta = Lib.Testo.dopo(sesta, pipe);
                            }// fine del blocco if
                            sesta = Lib.Testo.levaQuadre(sesta);
                            sesta = Lib.Testo.levaSmall(sesta);
                        }// fine del blocco if

                        if (parti.length > col + 6) {
                            settima = parti[col + 6].trim();
                            if (settima.startsWith(pipe)) {
                                settima = Lib.Testo.dopo(settima, pipe);
                            }// fine del blocco if
                            settima = Lib.Testo.levaQuadre(settima);
                            settima = Lib.Testo.levaSmall(settima);
                        }// fine del blocco if

                        if (parti.length > col + 7) {
                            ottava = parti[col + 7].trim();
                            if (ottava.startsWith(pipe)) {
                                ottava = Lib.Testo.dopo(ottava, pipe);
                            }// fine del blocco if
                            ottava = Lib.Testo.levaQuadre(ottava);
                            ottava = Lib.Testo.levaSmall(ottava);
                        }// fine del blocco if

                        if (parti.length > col + 8) {
                            nona = parti[col + 8].trim();
                            if (nona.startsWith(pipe)) {
                                nona = Lib.Testo.dopo(nona, pipe);
                            }// fine del blocco if
                            nona = Lib.Testo.levaQuadre(nona);
                            nona = Lib.Testo.levaSmall(nona);
                        }// fine del blocco if

                        if (parti.length > col + 9) {
                            decima = parti[col + 9].trim();
                            if (decima.startsWith(pipe)) {
                                decima = Lib.Testo.dopo(decima, pipe);
                            }// fine del blocco if
                            decima = Lib.Testo.levaQuadre(decima);
                            decima = Lib.Testo.levaSmall(decima);
                        }// fine del blocco if

                        wrap = new DieciStringhe(prima,
                                seconda,
                                terza,
                                quarta,
                                quinta,
                                sesta,
                                settima,
                                ottava,
                                nona,
                                decima);
                        lista.add(wrap);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di una colonna dal testo di una pagina o di una tabella.
     * <p/>
     * La tabella deve contenere almeno una colonna significativa che viene recuperata; eventuali
     * altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna sinistra di
     * numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di stringhe,
     */
    static ArrayList<String> getPrettyUno(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;
        int numColonne = 1;
        String[] righe = null;
        String[] parti;
        String tagRiga = "\\|-";
        String tagParte = "\\|\\|";
        String pipe = "|";
        String prima;
        int col;
        String tagIni = "{{prettytable}}";
        String tagEnd = "|}";
        int posIni;
        int posEnd;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                if (testo.contains(tagIni)) {
                    posIni = testo.indexOf(tagIni);
                    posIni += tagIni.length();
                    posEnd = testo.indexOf(tagEnd, posIni);
                    if (posEnd != -1) {
                        testo = testo.substring(posIni, posEnd);
                    }// fine del blocco if
                }// fine del blocco if
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<String>();
                col = LibWiki.getColonna(righe);

                for (String riga : righe) {

                    riga = riga.trim();
                    parti = riga.split(tagParte);
                    if (parti != null && parti.length >= (numColonne + col)) {

                        prima = parti[col].trim();
                        if (prima.startsWith(pipe)) {
                            prima = Lib.Testo.dopo(prima, pipe);
                        }// fine del blocco if
                        prima = Lib.Testo.levaQuadre(prima);

                        lista.add(prima);
                    }// fine del blocco if
                } // fine del ciclo for
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di una colonna da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno una colonna significativa che viene recuperata;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di stringhe,
     */
    static ArrayList<String> getPrettyUno(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        boolean continua;
        String tagPretty = "prettytable";
        String[] tabelle = null;
        String tagEnd = "|}";
        int posEnd;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                continua = testoPagina.contains(tagPretty);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(tagPretty);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                tabella = tabelle[posTabella];
                posEnd = tabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    tabella = tabella.substring(0, posEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPrettyUno(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di due colonne dal testo di una pagina o di una tabella.
     * <p/>
     * La tabella deve contenere almeno due colonne significative che vengono recuperate; eventuali
     * altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna sinistra di
     * numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di due stringhe, una per ognuna delle prime due colonne significative della
     *         tabella
     */
    public static ArrayList<DueStringhe> getPrettyDue(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DueStringhe> lista = null;
        boolean continua;
        ArrayList<DieciStringhe> listaTemp;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                listaTemp = LibWiki.getPretty(testo);
                if (listaTemp != null) {
                    lista = Lib.Array.getDueStringhe(listaTemp);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di due colonne da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno due colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di due stringhe, una per ognuna delle prime due colonne significative della
     *         tabella selezionata
     */
    public static ArrayList<DueStringhe> getPrettyDue(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DueStringhe> lista = null;
        boolean continua;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                tabella = getTesto(testoPagina, posTabella);
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPrettyDue(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di tre colonne dal testo di una pagina o di una tabella.
     * <p/>
     * <p/>
     * La tabella deve contenere almeno tre colonne significative che vengono recuperate; eventuali
     * altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna sinistra di
     * numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di tre stringhe, una per ognuna delle prime tre colonne significative della
     *         tabella
     */
    static ArrayList<TreStringhe> getPrettyTre(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<TreStringhe> lista = null;
        boolean continua;
        ArrayList<DieciStringhe> listaTemp;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                listaTemp = LibWiki.getPretty(testo);
                if (listaTemp != null) {
                    lista = Lib.Array.getTreStringhe(listaTemp);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di tre colonne da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno tre colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di tre stringhe, una per ognuna delle prime tre colonne significative della
     *         tabella selezionata
     */
    static ArrayList<TreStringhe> getPrettyTre(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<TreStringhe> lista = null;
        boolean continua;
        String tagPretty = "prettytable";
        String[] tabelle = null;
        String tagEnd = "|}";
        int posEnd;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                continua = testoPagina.contains(tagPretty);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(tagPretty);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                tabella = tabelle[posTabella];
                posEnd = tabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    tabella = tabella.substring(0, posEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPrettyTre(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di quattro colonne dal testo di una pagina o di una tabella.
     * <p/>
     * <p/>
     * La tabella deve contenere almeno quattro colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di quattro stringhe, una per ognuna delle prime quattro colonne significative
     *         della tabella
     */
    static ArrayList<QuattroStringhe> getPrettyQuattro(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<QuattroStringhe> lista = null;
        boolean continua;
        ArrayList<DieciStringhe> listaTemp;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                listaTemp = LibWiki.getPretty(testo);
                if (listaTemp != null) {
                    lista = Lib.Array.getQuattroStringhe(listaTemp);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di quattro colonne da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno quattro colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di quattro stringhe, una per ognuna delle prime quattro colonne significative
     *         della tabella selezionata
     */
    static ArrayList<QuattroStringhe> getPrettyQuattro(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<QuattroStringhe> lista = null;
        boolean continua;
        String tagPretty = "prettytable";
        String[] tabelle = null;
        String tagEnd = "|}";
        int posEnd;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                continua = testoPagina.contains(tagPretty);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(tagPretty);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                tabella = tabelle[posTabella];
                posEnd = tabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    tabella = tabella.substring(0, posEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPrettyQuattro(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di cinque colonne dal testo di una pagina o di una tabella.
     * <p/>
     * <p/>
     * La tabella deve contenere almeno cinque colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di cinque stringhe, una per ognuna delle prime cinque colonne significative
     *         della tabella
     */
    static ArrayList<CinqueStringhe> getPrettyCinque(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CinqueStringhe> lista = null;
        boolean continua;
        ArrayList<DieciStringhe> listaTemp;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                listaTemp = LibWiki.getPretty(testo);
                if (listaTemp != null) {
                    lista = Lib.Array.getCinqueStringhe(listaTemp);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di cinque colonne da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno cinque colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di cinque stringhe, una per ognuna delle prime cinque colonne significative
     *         della tabella selezionata
     */
    static ArrayList<CinqueStringhe> getPrettyCinque(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CinqueStringhe> lista = null;
        boolean continua;
        String tagPretty = "prettytable";
        String[] tabelle = null;
        String tagEnd = "|}";
        int posEnd;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                continua = testoPagina.contains(tagPretty);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(tagPretty);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                tabella = tabelle[posTabella];
                posEnd = tabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    tabella = tabella.substring(0, posEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPrettyCinque(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di sei colonne dal testo di una pagina o di una tabella.
     * <p/>
     * <p/>
     * La tabella deve contenere almeno sei colonne significative che vengono recuperate; eventuali
     * altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna sinistra di
     * numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di sei stringhe, una per ognuna delle prime sei colonne significative della
     *         tabella
     */
    static ArrayList<SeiStringhe> getPrettySei(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<SeiStringhe> lista = null;
        boolean continua;
        ArrayList<DieciStringhe> listaTemp;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                listaTemp = LibWiki.getPretty(testo);
                if (listaTemp != null) {
                    lista = Lib.Array.getSeiStringhe(listaTemp);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di sei colonne da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno sei colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di sei stringhe, una per ognuna delle prime sei colonne significative della
     *         tabella selezionata
     */
    static ArrayList<SeiStringhe> getPrettySei(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<SeiStringhe> lista = null;
        boolean continua;
        String tagPretty = "prettytable";
        String[] tabelle = null;
        String tagEnd = "|}";
        int posEnd;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                continua = testoPagina.contains(tagPretty);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(tagPretty);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                tabella = tabelle[posTabella];
                posEnd = tabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    tabella = tabella.substring(0, posEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPrettySei(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di otto colonne dal testo di una pagina o di una tabella.
     * <p/>
     * <p/>
     * La tabella deve contenere almeno otto colonne significative che vengono recuperate; eventuali
     * altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna sinistra di
     * numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di otto stringhe, una per ognuna delle prime otto colonne significative della
     *         tabella
     */
    static ArrayList<OttoStringhe> getPrettyOtto(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<OttoStringhe> lista = null;
        boolean continua;
        ArrayList<DieciStringhe> listaTemp;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                listaTemp = LibWiki.getPretty(testo);
                if (listaTemp != null) {
                    lista = Lib.Array.getOttoStringhe(listaTemp);
                }// fine del blocco if
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di otto colonne da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno otto colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di otto stringhe, una per ognuna delle prime otto colonne significative della
     *         tabella selezionata
     */
    static ArrayList<OttoStringhe> getPrettyOtto(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<OttoStringhe> lista = null;
        boolean continua;
        String tagPretty = "prettytable";
        String[] tabelle = null;
        String tagEnd = "|}";
        int posEnd;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                continua = testoPagina.contains(tagPretty);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(tagPretty);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                tabella = tabelle[posTabella];
                posEnd = tabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    tabella = tabella.substring(0, posEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPrettyOtto(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di sei colonne dal testo di una pagina o di una tabella.
     * <p/>
     * <p/>
     * La tabella deve contenere almeno sei colonne significative che vengono recuperate; eventuali
     * altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna sinistra di
     * numeri d'ordine che vengono ignorati <br>
     *
     * @param testo della pagina o della tabella  <br>
     *
     * @return lista di sei stringhe, una per ognuna delle prime sei colonne significative della
     *         tabella
     */
    static ArrayList<DieciStringhe> getPrettyDieci(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> lista = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(testo));

            if (continua) {
                lista = getPretty(testo);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di dieci colonne da una tabella di una pagina.
     * <p/>
     * La pagina può contenere più di una tabella; quella selezionata viene indica nel parametro
     * <br> La tabella deve contenere almeno dieci colonne significative che vengono recuperate;
     * eventuali altre colonne vengono ignorate <br> La tabella può contenere o meno una colonna
     * sinistra di numeri d'ordine che vengono ignorati <br>
     *
     * @param testoPagina testo della pagina dove sono presenti più tabelle <br>
     * @param posTabella tabella selezionata <br>
     *
     * @return lista di dieci stringhe, una per ognuna delle prime dieci colonne significative della
     *         tabella selezionata
     */
    public static ArrayList<DieciStringhe> getPrettyDieci(String testoPagina, int posTabella) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> lista = null;
        boolean continua;
        String tagPretty = "prettytable";
        String[] tabelle = null;
        String tagEnd = "|}";
        int posEnd;
        String tabella = "";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testoPagina, posTabella));

            if (continua) {
                continua = testoPagina.contains(tagPretty);
            }// fine del blocco if

            if (continua) {
                tabelle = testoPagina.split(tagPretty);
                continua = (tabelle != null && tabelle.length > 0);
            }// fine del blocco if

            if (continua) {
                tabella = tabelle[posTabella];
                posEnd = tabella.indexOf(tagEnd);
                if (posEnd != -1) {
                    tabella = tabella.substring(0, posEnd);
                }// fine del blocco if
                continua = (Lib.Testo.isValida(tabella));
            }// fine del blocco if

            if (continua) {
                lista = getPretty(tabella);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Costruisce una tabella di -n- colonne.
     * <p/>
     * Massimo numero di colonne 10 <br> Riceve una lista di 10 stringhe <br> Costruisce solo le
     * prima -n- colonne indicate <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di 10 stringhe <br>
     * @param numColonne numero effettivo di colonne da costruire <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    private static String setPrettyBase(ArrayList<DieciStringhe> lista,
                                        int numColonne,
                                        String titoli,
                                        boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        String tagVir = ",";
        String aCapo = "\n";
        String pipe = "|";
        String sep = " || ";
        StringBuffer buffer;
        int cont = 0;
        String[] titoliColonne = null;
        String[] valoriColonne;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && numColonne > 0 && Lib.Testo.isValida(
                    titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null && titoliColonne.length > 0);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();

                buffer.append("{| {{prettytable}}");
                buffer.append(aCapo);
                buffer.append("|- bgcolor=\"#CCCCCC");
                buffer.append(aCapo);
                buffer.append("! ");

                if (flagOrdinamento) {
                    buffer.append("#");
                    buffer.append("!!");
                }// fine del blocco if

                for (int k = 0; k < numColonne; k++) {
                    if (titoliColonne.length > k) {
                        buffer.append(titoliColonne[k]);
                    } else {
                        buffer.append(" - ");
                    }// fine del blocco if-else
                    if (k < (numColonne - 1)) {
                        buffer.append("!!");
                    }// fine del blocco if
                } // fine del ciclo for
                buffer.append(aCapo);

                for (DieciStringhe riga : lista) {
                    valoriColonne = riga.getArray();
                    buffer.append("|-");
                    buffer.append(aCapo);
                    buffer.append(pipe);

                    if (flagOrdinamento) {
                        buffer.append(Lib.Testo.formatNumero(++cont));
                        buffer.append(sep);
                    }// fine del blocco if

                    buffer.append(riga.getPrima());

                    for (int k = 1; k < numColonne; k++) {
                        buffer.append(sep);
                        buffer.append(valoriColonne[k]);
                    } // fine del ciclo for

                    buffer.append(aCapo);
                } // fine del ciclo for-each

                buffer.append("|}");
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di una colonna.
     * <p/>
     * Riceve una lista di stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyUno(ArrayList<String> lista, String titoli, boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 1;
        String tagVir = ",";
        String aCapo = "\n";
        String pipe = "|";
        String sep = " || ";
        StringBuffer buffer;
        int cont = 0;
        String[] titoliColonne = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null && titoliColonne.length == numColonne);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();

                buffer.append("{| {{prettytable}}");
                buffer.append(aCapo);
                buffer.append("|- bgcolor=\"#CCCCCC");
                buffer.append(aCapo);
                buffer.append("! ");

                if (flagOrdinamento) {
                    buffer.append("#");
                    buffer.append("!!");
                }// fine del blocco if

                buffer.append(titoliColonne[0]);
                buffer.append(aCapo);

                for (String riga : lista) {
                    buffer.append("|-");
                    buffer.append(aCapo);
                    buffer.append(pipe);

                    if (flagOrdinamento) {
                        buffer.append(Lib.Testo.formatNumero(++cont));
                        buffer.append(sep);
                    }// fine del blocco if

                    buffer.append(riga);
                    buffer.append(aCapo);
                } // fine del ciclo for-each

                buffer.append("|}");
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di una colonna, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di stringhe <br>
     *
     * @param lista di stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyUno(ArrayList<String> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyUno(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di due colonne.
     * <p/>
     * Riceve una lista di due stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di due stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyDue(ArrayList<DueStringhe> lista,
                               String titoli,
                               boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 2;
        String tagVir = ",";
        String aCapo = "\n";
        String pipe = "|";
        String sep = " || ";
        StringBuffer buffer;
        int cont = 0;
        String[] titoliColonne = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null && titoliColonne.length == numColonne);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();

                buffer.append("{| {{prettytable}}");
                buffer.append(aCapo);
                buffer.append("|- bgcolor=\"#CCCCCC");
                buffer.append(aCapo);
                buffer.append("! ");

                if (flagOrdinamento) {
                    buffer.append("#");
                    buffer.append("!!");
                }// fine del blocco if

                buffer.append(titoliColonne[0]);
                buffer.append("!!");
                buffer.append(titoliColonne[1]);
                buffer.append(aCapo);

                for (DueStringhe riga : lista) {
                    buffer.append("|-");
                    buffer.append(aCapo);
                    buffer.append(pipe);

                    if (flagOrdinamento) {
                        buffer.append(Lib.Testo.formatNumero(++cont));
                        buffer.append(sep);
                    }// fine del blocco if

                    buffer.append(riga.getPrima());
                    buffer.append(sep);
                    buffer.append(riga.getSeconda());
                    buffer.append(aCapo);
                } // fine del ciclo for-each

                buffer.append("|}");
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di due colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di due stringhe <br>
     *
     * @param lista di due stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyDue(ArrayList<DueStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyDue(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di tre colonne.
     * <p/>
     * Riceve una lista di tre stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di tre stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyTre(ArrayList<TreStringhe> lista,
                               String titoli,
                               boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 3;
        String tagVir = ",";
        String aCapo = "\n";
        String pipe = "|";
        String sep = " || ";
        StringBuffer buffer;
        int cont = 0;
        String[] titoliColonne = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null && titoliColonne.length == numColonne);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();

                buffer.append("{| {{prettytable}}");
                buffer.append(aCapo);
                buffer.append("|- bgcolor=\"#CCCCCC");
                buffer.append(aCapo);
                buffer.append("! ");

                if (flagOrdinamento) {
                    buffer.append("#");
                    buffer.append("!!");
                }// fine del blocco if

                buffer.append(titoliColonne[0]);
                buffer.append("!!");
                buffer.append(titoliColonne[1]);
                buffer.append("!!");
                buffer.append(titoliColonne[2]);
                buffer.append(aCapo);

                for (TreStringhe riga : lista) {
                    buffer.append("|-");
                    buffer.append(aCapo);
                    buffer.append(pipe);

                    if (flagOrdinamento) {
                        buffer.append(Lib.Testo.formatNumero(++cont));
                        buffer.append(sep);
                    }// fine del blocco if

                    buffer.append(riga.getPrima());
                    buffer.append(sep);
                    buffer.append(riga.getSeconda());
                    buffer.append(sep);
                    buffer.append(riga.getTerza());
                    buffer.append(aCapo);
                } // fine del ciclo for-each

                buffer.append("|}");
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di tre colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di tre stringhe <br>
     *
     * @param lista di tre stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyTre(ArrayList<TreStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyTre(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di quattro colonne.
     * <p/>
     * Riceve una lista di quattro stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di quattro stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyQuattro(ArrayList<QuattroStringhe> lista,
                                   String titoli,
                                   boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 4;
        String tagVir = ",";
        String aCapo = "\n";
        String pipe = "|";
        String sep = " || ";
        StringBuffer buffer;
        int cont = 0;
        String[] titoliColonne = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null && titoliColonne.length == numColonne);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();

                buffer.append("{| {{prettytable}}");
                buffer.append(aCapo);
                buffer.append("|- bgcolor=\"#CCCCCC");
                buffer.append(aCapo);
                buffer.append("! ");

                if (flagOrdinamento) {
                    buffer.append("#");
                    buffer.append("!!");
                }// fine del blocco if

                buffer.append(titoliColonne[0]);
                buffer.append("!!");
                buffer.append(titoliColonne[1]);
                buffer.append("!!");
                buffer.append(titoliColonne[2]);
                buffer.append("!!");
                buffer.append(titoliColonne[3]);
                buffer.append(aCapo);

                for (QuattroStringhe riga : lista) {
                    buffer.append("|-");
                    buffer.append(aCapo);
                    buffer.append(pipe);

                    if (flagOrdinamento) {
                        buffer.append(Lib.Testo.formatNumero(++cont));
                        buffer.append(sep);
                    }// fine del blocco if

                    buffer.append(riga.getPrima());
                    buffer.append(sep);
                    buffer.append(riga.getSeconda());
                    buffer.append(sep);
                    buffer.append(riga.getTerza());
                    buffer.append(sep);
                    buffer.append(riga.getQuarta());
                    buffer.append(aCapo);
                } // fine del ciclo for-each

                buffer.append("|}");
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di quattro colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di quattro stringhe <br>
     *
     * @param lista di quattro stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyQuattro(ArrayList<QuattroStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyQuattro(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di cinque colonne.
     * <p/>
     * Riceve una lista di cinque stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di cinque stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyCinque(ArrayList<CinqueStringhe> lista,
                                  String titoli,
                                  boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 5;
        String tagVir = ",";
        String aCapo = "\n";
        String pipe = "|";
        String sep = " || ";
        StringBuffer buffer;
        int cont = 0;
        String[] titoliColonne = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null && titoliColonne.length == numColonne);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();

                buffer.append("{| {{prettytable}}");
                buffer.append(aCapo);
                buffer.append("|- bgcolor=\"#CCCCCC");
                buffer.append(aCapo);
                buffer.append("! ");

                if (flagOrdinamento) {
                    buffer.append("#");
                    buffer.append("!!");
                }// fine del blocco if

                buffer.append(titoliColonne[0]);
                buffer.append("!!");
                buffer.append(titoliColonne[1]);
                buffer.append("!!");
                buffer.append(titoliColonne[2]);
                buffer.append("!!");
                buffer.append(titoliColonne[3]);
                buffer.append("!!");
                buffer.append(titoliColonne[4]);
                buffer.append(aCapo);

                for (CinqueStringhe riga : lista) {
                    buffer.append("|-");
                    buffer.append(aCapo);
                    buffer.append(pipe);

                    if (flagOrdinamento) {
                        buffer.append(Lib.Testo.formatNumero(++cont));
                        buffer.append(sep);
                    }// fine del blocco if

                    buffer.append(riga.getPrima());
                    buffer.append(sep);
                    buffer.append(riga.getSeconda());
                    buffer.append(sep);
                    buffer.append(riga.getTerza());
                    buffer.append(sep);
                    buffer.append(riga.getQuarta());
                    buffer.append(sep);
                    buffer.append(riga.getQuinta());
                    buffer.append(aCapo);
                } // fine del ciclo for-each

                buffer.append("|}");
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di cinque colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di cinque stringhe <br>
     *
     * @param lista di cinque stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyCinque(ArrayList<CinqueStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyCinque(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di sei colonne.
     * <p/>
     * Riceve una lista di sei stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di sei stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettySeiOld(ArrayList<SeiStringhe> lista,
                                  String titoli,
                                  boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 6;
        String tagVir = ",";
        String aCapo = "\n";
        String pipe = "|";
        String sep = " || ";
        StringBuffer buffer;
        int cont = 0;
        String[] titoliColonne = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null && titoliColonne.length == numColonne);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();

                buffer.append("{| {{prettytable}}");
                buffer.append(aCapo);
                buffer.append("|- bgcolor=\"#CCCCCC");
                buffer.append(aCapo);
                buffer.append("! ");

                if (flagOrdinamento) {
                    buffer.append("#");
                    buffer.append("!!");
                }// fine del blocco if

                buffer.append(titoliColonne[0]);
                buffer.append("!!");
                buffer.append(titoliColonne[1]);
                buffer.append("!!");
                buffer.append(titoliColonne[2]);
                buffer.append("!!");
                buffer.append(titoliColonne[3]);
                buffer.append("!!");
                buffer.append(titoliColonne[4]);
                buffer.append("!!");
                buffer.append(titoliColonne[5]);
                buffer.append(aCapo);

                for (SeiStringhe riga : lista) {
                    buffer.append("|-");
                    buffer.append(aCapo);
                    buffer.append(pipe);

                    if (flagOrdinamento) {
                        buffer.append(Lib.Testo.formatNumero(++cont));
                        buffer.append(sep);
                    }// fine del blocco if

                    buffer.append(riga.getPrima());
                    buffer.append(sep);
                    buffer.append(riga.getSeconda());
                    buffer.append(sep);
                    buffer.append(riga.getTerza());
                    buffer.append(sep);
                    buffer.append(riga.getQuarta());
                    buffer.append(sep);
                    buffer.append(riga.getQuinta());
                    buffer.append(sep);
                    buffer.append(riga.getSesta());
                    buffer.append(aCapo);
                } // fine del ciclo for-each

                buffer.append("|}");
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di sei colonne.
     * <p/>
     * Riceve una lista di sei stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di sei stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettySei(ArrayList<SeiStringhe> lista,
                               String titoli,
                               boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 6;
        ArrayList<DieciStringhe> listaDieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaDieci = Lib.Array.setSeiStringhe(lista);
                testo = LibWiki.setPrettyBase(listaDieci, numColonne, titoli, flagOrdinamento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di sei colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di sei stringhe <br>
     *
     * @param lista di sei stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettySei(ArrayList<SeiStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettySei(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di sette colonne.
     * <p/>
     * Riceve una lista di sette stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di sette stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettySette(ArrayList<SetteStringhe> lista,
                                 String titoli,
                                 boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 7;
        ArrayList<DieciStringhe> listaDieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaDieci = Lib.Array.setSetteStringhe(lista);
                testo = LibWiki.setPrettyBase(listaDieci, numColonne, titoli, flagOrdinamento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di sette colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di sette stringhe <br>
     *
     * @param lista di sette stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettySette(ArrayList<SetteStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettySette(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di otto colonne.
     * <p/>
     * Riceve una lista di otto stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di otto stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyOtto(ArrayList<OttoStringhe> lista,
                                String titoli,
                                boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 8;
        ArrayList<DieciStringhe> listaDieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaDieci = Lib.Array.setOttoStringhe(lista);
                testo = LibWiki.setPrettyBase(listaDieci, numColonne, titoli, flagOrdinamento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di otto colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di otto stringhe <br>
     *
     * @param lista di otto stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyOtto(ArrayList<OttoStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyOtto(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di nove colonne.
     * <p/>
     * Riceve una lista di nove stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di nove stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyNove(ArrayList<NoveStringhe> lista,
                                String titoli,
                                boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 9;
        ArrayList<DieciStringhe> listaDieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci la lista fissa di dieci stringhe */
            if (continua) {
                listaDieci = Lib.Array.setNoveStringhe(lista);
                testo = LibWiki.setPrettyBase(listaDieci, numColonne, titoli, flagOrdinamento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di nove colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di nove stringhe <br>
     *
     * @param lista di nove stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyNove(ArrayList<NoveStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyNove(lista, titoli, true);
    }


    /**
     * Costruisce una tabella di dieci colonne.
     * <p/>
     * Riceve una lista di dieci stringhe <br> Può costruire o meno una colonna iniziale di numeri
     * progressivi <br>
     *
     * @param lista di dieci stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     * @param flagOrdinamento per costruire o meno una colonna iniziale di numeri progressivi <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyDieci(ArrayList<DieciStringhe> lista,
                                 String titoli,
                                 boolean flagOrdinamento) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        int numColonne = 10;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            if (continua) {
                testo = LibWiki.setPrettyBase(lista, numColonne, titoli, flagOrdinamento);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce una tabella di dieci colonne, con una colonna iniziale di numeri progressivi.
     * <p/>
     * Riceve una lista di dieci stringhe <br>
     *
     * @param lista di dieci stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    static String setPrettyDieci(ArrayList<DieciStringhe> lista, String titoli) {
        /* invoca il metodo delegato della classe */
        return setPrettyDieci(lista, titoli, true);
    }


    /**
     * Ordina una lista di dieci stringhe.
     * <p/>
     * Ordinamento alfabetico effettuato sulla prima stringa <br>
     *
     * @param listaIn da ordinare <br>
     *
     * @return lista ordinata
     */
    static ArrayList<DieciStringhe> ordinaDieci(ArrayList<DieciStringhe> listaIn) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> listaOut = null;
        boolean continua;
        LinkedHashMap<String, DieciStringhe> mappa;
        ArrayList<String> listaTemp;
        String unNome;
        DieciStringhe unDieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (listaIn != null && listaIn.size() > 0);

            if (continua) {
                listaOut = new ArrayList<DieciStringhe>();
                listaTemp = new ArrayList<String>();
                mappa = new LinkedHashMap<String, DieciStringhe>();

                for (DieciStringhe dieci : listaIn) {
                    unNome = dieci.getPrima() + dieci.getSeconda();
                    mappa.put(unNome, dieci);
                    listaTemp.add(unNome);
                } // fine del ciclo for-each

                /* ordina la lista di riferimento */
                listaTemp = Lib.Array.ordina(listaTemp);

                for (String nome : listaTemp) {
                    unDieci = mappa.get(nome);
                    listaOut.add(unDieci);
                } // fine del ciclo for-each

            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return listaOut;
    }


    /**
     * Classe interna Enumerazione.
     */
    private enum Entity {

        lt('\u003C', "<"),
        gt('\u003E', ">"),
        amp('\u0026', "&"),
        quot('\u0022', "\"");

        /**
         * valore UNICODE
         */
        private String unicode;

        /**
         * carattere sulla tastiera
         */
        private String tasto;

        /**
         * carattere per la sostituzione
         */
        private char[] car = new char[1];


        /**
         * Costruttore completo con parametri.
         *
         * @param carattere per la sostituzione
         * @param tasto carattere sulla tastiera
         */
        Entity(char carattere, String tasto) {
            try { // prova ad eseguire il codice
                /* regola il primo ed unico carattere dell'array di caratteri */
                car[0] = carattere;

                /* costruisce la stringa unicode */
                this.setUnicode(new String(car));

                /* regola le variabili di istanza coi parametri */
                this.setTasto(tasto);

            } catch (Exception unErrore) { // intercetta l'errore
                Errore.crea(unErrore);
            }// fine del blocco try-catch
        }


        /**
         * Trasforma le entity speciali.
         * <p/>
         * Sostituisce le entity HTML con i rispettivi caratteri speciali <br> Questo metodo deve
         * essere chiamato sempre <br>
         */
        public static String trasforma(String testo) {
            /* variabili e costanti locali di lavoro */
            String entity;
            String ante = "\\&";
            String post = ";";
            String uni;
            String testo2;

            try { // prova ad eseguire il codice

                /* spazzola tutta la Enumerazione */
                for (Entity ent : Entity.values()) {
                    entity = ante + ent.getEntity() + post;
                    uni = ent.getUnicode();
                    testo2 = testo.replaceAll(entity, uni);
                    testo = testo2;
                } // fine del ciclo for-each
            } catch (Exception unErrore) {
                Errore.crea(unErrore);
            }// fine del blocco try-catch

            /* valore di ritorno */
            return testo;
        }


        public String getEntity() {
            return this.toString();
        }


        public String getUnicode() {
            return unicode;
        }


        private void setUnicode(String unicode) {
            this.unicode = unicode;
        }


        public String getTasto() {
            return tasto;
        }


        private void setTasto(String tasto) {
            this.tasto = tasto;
        }

    }// fine della classe

}// fine della classe
