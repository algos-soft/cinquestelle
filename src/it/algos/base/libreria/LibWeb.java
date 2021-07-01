/**
 * Copyright: Copyright (c) 2006
 * Company:   Algos s.r.l.
 * Author:    gac
 * Date:      8-lug-2006
 */
package it.algos.base.libreria;

import it.algos.base.errore.Errore;
import it.algos.base.pref.Pref;
import it.algos.base.wrapper.*;
import org.apache.commons.net.ftp.FTPFile;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * Classe astratta con metodi statici. </p> Questa classe astratta: <ul> <li>  </li> <li>  </li>
 * </ul>
 *
 * @author Guido Andrea Ceresa, Alessandro Valbonesi
 * @author gac
 * @version 1.0    / 8-lug-2006 ore 8.01.50
 */
public abstract class LibWeb {

    private static final String UTENTE = "alex";

    private static final String PASSWORD = "hal";

    private static final String DIR_DATA = "algosData/";


    /**
     * Costruisce un oggetto Url dall'indirizzo.
     * <p/>
     *
     * @param indirizzo nome della pagina
     *
     * @return URL costruito
     */
    static URL getUrl(String indirizzo) {
        /* variabili e costanti locali di lavoro */
        URL url = null;

        try { // prova ad eseguire il codice
            url = new URL(indirizzo);
        } catch (MalformedURLException unErrore) { // intercetta  l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return url;
    }


    /**
     * Carica sul server un file locale.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param server indirizzo completo del server, compreso nick e password
     * @param file path completo del file su disco
     *
     * @return risultato dell'operazione
     */
    static boolean upLoadOld(String server, String file) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        URLConnection connessione;
        URLConnection urlc;
        URL url;
        String indirizzo = "http://www.quattroprovince.it/pippo";
        OutputStreamWriter outputWriter;
        OutputStream output;
        OutputStream os;
        String post;

        try { // prova ad eseguire il codice


            url =
                    new URL("ftp://parcodellerose-ftp:08+parco@ftp.parcodellerose.it/pippo.txt;type=i");
            urlc = url.openConnection();
            os = urlc.getOutputStream(); // To upload
            outputWriter = new OutputStreamWriter(os);

            post = "prova testo2";

            /* carica la pagina sul server */
            outputWriter.write(post);
            outputWriter.flush();
            outputWriter.close();
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Costruisce la connessione.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @return connessione
     */
    static FtpWrap getFtp() {
        /* invoca il metodo sovrascritto della libreria specifica */
        FtpWrap ftp = null;
        String server = "ftp.viappiani.it";
        String utente = UTENTE;
        String password = PASSWORD;

        try { // prova ad eseguire il codice
            /* istanza di connessione */
            ftp = new FtpWrap();

            /* prova a connettersi */
            if (ftp.connectAndLogin(server, utente, password)) {
                try {
                    ftp.setPassiveMode(true);
                    ftp.ascii();
                } catch (Exception ftpe) {
                    ftpe.printStackTrace();
                }
            } else {
                System.out.println("Non riesco a collegarmi a " + server);
            }
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return ftp;
    }


    /**
     * Chiude la connessione.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param ftp connessione
     */
    static void chiudeFtp(FtpWrap ftp) {
        try { // prova ad eseguire il codice
            if (ftp != null) {
                ftp.logout();
                ftp.disconnect();
                ftp = null;
            }// fine del blocco if
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch
    }


    /**
     * Carica o scarica sul/dal server un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathIn path completo del file sorgente
     * @param pathOut path completo del file destinazione
     * @param isUpLoad true per caricarlo, false per scaricarlo
     * @param ftp connessione
     *
     * @return risultato dell'operazione
     */
    private static boolean upDown(String pathIn, String pathOut, boolean isUpLoad, FtpWrap ftp) {
        /* invoca il metodo sovrascritto della libreria specifica */
        boolean riuscito = false;

        try { // prova ad eseguire il codice
            if (isUpLoad) {
                riuscito = ftp.uploadFile(pathIn, pathOut);
            } else {
                riuscito = ftp.downloadFile(pathIn, pathOut);
            }// fine del blocco if-else
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Carica o scarica sul/dal server un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathIn path completo del file sorgente
     * @param pathOut path completo del file destinazione
     * @param isUpLoad true per caricarlo, false per scaricarlo
     *
     * @return risultato dell'operazione
     */
    private static boolean upDown(String pathIn, String pathOut, boolean isUpLoad) {
        /* invoca il metodo sovrascritto della libreria specifica */
        boolean riuscito = false;
        FtpWrap ftp;

        try { // prova ad eseguire il codice
            /* istanza di connessione */
            ftp = getFtp();

            if (isUpLoad) {
                riuscito = ftp.uploadFile(pathIn, pathOut);
            } else {
                riuscito = ftp.downloadFile(pathIn, pathOut);
            }// fine del blocco if-else

            /* chiude la connessione */
            chiudeFtp(ftp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Carica sul server un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathLocale path completo del file locale
     * @param pathServer path completo del file sul server
     *
     * @return risultato dell'operazione
     */
    static boolean upLoad(String pathLocale, String pathServer) {
        /* invoca il metodo sovrascritto della superclasse */
        return upDown(pathLocale, pathServer, true);
    }


    /**
     * Carica sul server un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathLocale path completo del file locale
     * @param pathServer path completo del file sul server
     * @param ftp connessione
     *
     * @return risultato dell'operazione
     */
    static boolean upLoad(String pathLocale, String pathServer, FtpWrap ftp) {
        /* invoca il metodo sovrascritto della superclasse */
        return upDown(pathLocale, pathServer, true, ftp);
    }


    /**
     * Scarica dal server un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathServer path completo del file sul server
     * @param pathLocale path completo del file locale
     *
     * @return risultato dell'operazione
     */
    static boolean downLoad(String pathServer, String pathLocale) {
        /* invoca il metodo sovrascritto della superclasse */
        return upDown(pathServer, pathLocale, false);
    }


    /**
     * Scarica dal server un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathServer path completo del file sul server
     * @param pathLocale path completo del file locale
     * @param ftp connessione
     *
     * @return risultato dell'operazione
     */
    static boolean downLoad(String pathServer, String pathLocale, FtpWrap ftp) {
        /* invoca il metodo sovrascritto della superclasse */
        return upDown(pathServer, pathLocale, false, ftp);
    }


    /**
     * Carica sul server (nella apposita cartella) un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathServer path completo del file sul server
     * @param testo da caricare
     *
     * @return risultato dell'operazione
     */
    static boolean upLoadTesto(String pathServer, String testo) {
        /* variabili e costanti locali di lavoro */
        boolean riuscito = false;
        boolean continua;
        FtpWrap ftp = null;
        String pathTempLoc = "xyz";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pathServer, testo));

            /* istanza di connessione */
            if (continua) {
                ftp = getFtp();
                continua = (ftp != null);
            }// fine del blocco if

            if (continua) {
                Lib.File.scrive(pathTempLoc, testo);
                riuscito = LibWeb.upLoad(pathTempLoc, pathServer, ftp);
                Lib.File.cancella(pathTempLoc);
                chiudeFtp(ftp);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Carica sul server (nella apposita cartella) un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathLocale path completo del file locale
     * @param pathServer path completo del file sul server
     *
     * @return risultato dell'operazione
     */
    static boolean upLoadData(String pathLocale, String pathServer) {
        /* invoca il metodo sovrascritto della libreria specifica */
        return LibWeb.upLoad(pathLocale, DIR_DATA + pathServer);
    }


    /**
     * Carica sul server (nella apposita cartella) un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathLocale path completo del file locale
     * @param pathServer path completo del file sul server
     * @param ftp connessione
     *
     * @return risultato dell'operazione
     */
    static boolean upLoadData(String pathLocale, String pathServer, FtpWrap ftp) {
        /* invoca il metodo sovrascritto della libreria specifica */
        return LibWeb.upLoad(pathLocale, DIR_DATA + pathServer, ftp);
    }


    /**
     * Scarica dal server (dalla apposita cartella) un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathServer path completo del file sul server
     * @param pathLocale path completo del file locale
     *
     * @return risultato dell'operazione
     */
    static boolean downLoadData(String pathServer, String pathLocale) {
        /* invoca il metodo sovrascritto della libreria specifica */
        return LibWeb.downLoad(DIR_DATA + pathServer, pathLocale);
    }


    /**
     * Scarica dal server (dalla apposita cartella) un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathServer path completo del file sul server
     * @param pathLocale path completo del file locale
     * @param ftp connessione
     *
     * @return risultato dell'operazione
     */
    static boolean downLoadData(String pathServer, String pathLocale, FtpWrap ftp) {
        /* invoca il metodo sovrascritto della libreria specifica */
        return LibWeb.downLoad(DIR_DATA + pathServer, pathLocale, ftp);
    }


    /**
     * Scarica dal server (dalla apposita cartella) il testo di un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathServer path completo del file sul server
     *
     * @return testo recuperato
     */
    static String downLoadData(String pathServer) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        FtpWrap ftp = null;
        String pathTempLoc = "xyz";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pathServer));

            /* istanza di connessione */
            if (continua) {
                ftp = getFtp();
                continua = (ftp != null);
            }// fine del blocco if

            if (continua) {
                LibWeb.downLoadData(pathServer, pathTempLoc, ftp);
                testo = Lib.File.legge(pathTempLoc);
                Lib.File.cancella(pathTempLoc);
                chiudeFtp(ftp);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Scarica dal server (dalla apposita cartella) il testo di un file.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathServer path completo del file sul server
     * @param ftp connessione
     *
     * @return testo recuperato
     */
    static String downLoadData(String pathServer, FtpWrap ftp) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        String pathTempLoc = "xyz";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pathServer) && ftp != null);

            if (continua) {
                LibWeb.downLoadData(pathServer, pathTempLoc, ftp);
                testo = Lib.File.legge(pathTempLoc);
                Lib.File.cancella(pathTempLoc);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Lista dei files contenuti in una cartella.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathDir path completo della cartella
     * @param ftp connessione
     *
     * @return lista files
     */
    static ArrayList<FTPFile> getFiles(String pathDir, FtpWrap ftp) {
        /* variabili e costanti locali di lavoro */
        ArrayList<FTPFile> lista = null;
        boolean continua;
        FTPFile[] elementi = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (ftp != null);

            if (continua) {
                continua = ftp.changeWorkingDirectory(pathDir);
            }// fine del blocco if

            if (continua) {
                elementi = ftp.listFiles();
                continua = (elementi != null && elementi.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<FTPFile>();
                for (FTPFile file : elementi) {
                    lista.add(file);
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Lista dei files contenuti in una cartella.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathDir path completo della cartella
     *
     * @return lista files
     */
    static ArrayList<FTPFile> getFiles(String pathDir) {
        /* variabili e costanti locali di lavoro */
        ArrayList<FTPFile> lista = null;
        boolean continua;
        FtpWrap ftp = null;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(pathDir));

            /* istanza di connessione */
            if (continua) {
                ftp = getFtp();
                continua = (ftp != null);
            }// fine del blocco if

            if (continua) {
                lista = getFiles(pathDir, ftp);
            }// fine del blocco if

            /* chiude la connessione */
            chiudeFtp(ftp);

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Lista dei nomi dei files contenuti in una cartella.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathDir path completo della cartella
     * @param ftp connessione
     *
     * @return lista nomi dei files
     */
    static ArrayList<String> getNomiFiles(String pathDir, FtpWrap ftp) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        ArrayList<FTPFile> listaFiles = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Testo.isValida(pathDir) && ftp != null);

            if (continua) {
                listaFiles = getFiles(pathDir, ftp);
                continua = (listaFiles != null && listaFiles.size() > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<String>();
                for (FTPFile file : listaFiles) {
                    lista.add(file.getName());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Lista dei nomi dei files contenuti in una cartella.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param pathDir path completo della cartella
     *
     * @return lista nomi dei files
     */
    static ArrayList<String> getNomiFiles(String pathDir) {
        /* variabili e costanti locali di lavoro */
        ArrayList<String> lista = null;
        ArrayList<FTPFile> listaFiles = null;
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = Lib.Testo.isValida(pathDir);

            if (continua) {
                listaFiles = getFiles(pathDir);
                continua = (listaFiles != null && listaFiles.size() > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<String>();
                for (FTPFile file : listaFiles) {
                    lista.add(file.getName());
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Ultima data di modifica del file.
     * <p/>
     * Presume che il path si riferisca ad un file <br>
     *
     * @param path il percorso assoluto del file
     * @param ftp connessione
     *
     * @return data ultima modifica
     */
    static Date getData(String path, FtpWrap ftp) {
        /* variabili e costanti locali di lavoro */
        Date data = null;
        boolean continua;
        FTPFile[] files = null;
        Calendar calendario;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(path));

            if (continua) {
                files = ftp.listFiles(path);
                continua = (files != null && files.length == 1);
            }// fine del blocco if

            if (continua) {
                continua = files[0].isFile();
            }// fine del blocco if

            if (continua) {
                calendario = files[0].getTimestamp();
                data = calendario.getTime();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return data;
    }


    /**
     * Carica sul server un testo.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param host del sito web
     * @param utente nickName
     * @param password dell'utente
     * @param server nome del file sul server (inizia con /)
     * @param post testo da caricare sullla pagina
     *
     * @return risultato dell'operazione
     */
    static boolean upLoadTesto(String host,
                               String utente,
                               String password,
                               String server,
                               String post) {
        boolean riuscito = false;
        URLConnection connessione;
        URL url;
        String indirizzo;
        String autority;
        String tagFtp;
        String tagWeb;
        OutputStreamWriter outputWriter;
        OutputStream output;

        try { // prova ad eseguire il codice

            /* inizio protocollo FTP */
            tagFtp = "ftp://";

            /* tag interno */
            tagWeb = "@";

            /* autorizzazione al collegamento */
            autority = utente + ":" + password;

            /* creazione indirizzo */
            indirizzo = tagFtp;
            indirizzo += autority;
            indirizzo += tagWeb;
            indirizzo += host;
            indirizzo += server;

            /* creazione url */
            url = LibWeb.getUrl(indirizzo);

            /* regola connessione e parametri */
            connessione = url.openConnection();
            connessione.setAllowUserInteraction(false);
            connessione.setDoOutput(true);
            connessione.connect();

            /* regola l'uscita */
            output = connessione.getOutputStream();
            outputWriter = new OutputStreamWriter(output, LibHtml.UTF.utf8.toString());

            /* carica la pagina sul server */
            outputWriter.write(post);
            outputWriter.flush();
            outputWriter.close();

            riuscito = true;
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Carica sul server un file locale.
     * <p/>
     * Utilizza il protocollo FPT <br>
     *
     * @param host del sito web
     * @param utente nickName
     * @param password dell'utente
     * @param server nome del file sul server (inizia con /)
     * @param locale nome del file locale
     *
     * @return risultato dell'operazione
     */
    static boolean upLoadFile(String host,
                              String utente,
                              String password,
                              String server,
                              String locale) {
        boolean riuscito = false;
        String post;

        try { // prova ad eseguire il codice
            /* recupera il contenuto del file */
            post = Lib.File.legge(locale);

            riuscito = LibWeb.upLoadTesto(host, utente, password, server, post);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return riuscito;
    }


    /**
     * Recupera una lista di tre colonne da un testo formattato csv.
     * <p/>
     * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima riga
     * (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre informazioni
     * presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni record ha come
     * delimitatore/separatore il carattere \n <br> Ogni campo ha come delimitatore/separatore il
     * carattere \t <br> Vengono recuperati i primi tre campi di ogni record <br> Eventuali altri
     * cmpi vengono ignorati <br>
     *
     * @param testo da elaborare <br>
     *
     * @return lista di tre stringhe, una per ognuno dei primi tre campi
     */
    static ArrayList<TreStringhe> getCsvTre(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<TreStringhe> lista = null;
        boolean continua;
        String tagCampo = "\t";
        String tagRiga = "\n";
        String[] righe = null;
        String[] parti;
        TreStringhe tre;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testo));

            if (continua) {
                testo = Lib.Testo.dopoPrima(testo, tagRiga);
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<TreStringhe>();
                for (String riga : righe) {

                    if (Lib.Testo.isVuota(testo)) {
                        continue;
                    }// fine del blocco if

                    parti = riga.split(tagCampo);
                    if (parti.length >= 3) {
                        tre = new TreStringhe(parti[0].trim(), parti[1].trim(), parti[2].trim());
                        lista.add(tre);
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di quattro colonne da un testo formattato csv.
     * <p/>
     * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima riga
     * (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre informazioni
     * presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni record ha come
     * delimitatore/separatore il carattere \n <br> Ogni campo ha come delimitatore/separatore il
     * carattere \t <br> Vengono recuperati i primi quattro campi di ogni record <br> Eventuali
     * altri cmpi vengono ignorati <br>
     *
     * @param testo da elaborare <br>
     *
     * @return lista di quattro stringhe, una per ognuno dei primi quattro campi
     */
    public static ArrayList<QuattroStringhe> getCsvQuattro(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<QuattroStringhe> lista = null;
        boolean continua;
        String tagCampo = "\t";
        String tagRiga = "\n";
        String[] righe = null;
        String[] parti;
        QuattroStringhe quattro;
        String prima;
        String seconda;
        String terza;
        String quarta;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testo));

            if (continua) {
                testo = Lib.Testo.dopoPrima(testo, tagRiga);
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<QuattroStringhe>();
                for (String riga : righe) {

                    if (Lib.Testo.isVuota(testo)) {
                        continue;
                    }// fine del blocco if

                    prima = "";
                    seconda = "";
                    terza = "";
                    quarta = "";

                    parti = riga.split(tagCampo);

                    if (parti.length >= 1) {
                        if (parti.length > 0) {
                            prima = parti[0].trim();
                        }// fine del blocco if

                        if (parti.length > 1) {
                            seconda = parti[1].trim();
                        }// fine del blocco if

                        if (parti.length > 2) {
                            terza = parti[2].trim();
                        }// fine del blocco if

                        if (parti.length > 3) {
                            quarta = parti[3].trim();
                        }// fine del blocco if

                        quattro = new QuattroStringhe(prima, seconda, terza, quarta);
                        lista.add(quattro);

                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di cinque colonne da un testo formattato csv.
     * <p/>
     * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima riga
     * (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre informazioni
     * presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni record ha come
     * delimitatore/separatore il carattere \n <br> Ogni campo ha come delimitatore/separatore il
     * carattere \t <br> Vengono recuperati i primi cinque campi di ogni record <br> Eventuali altri
     * cmpi vengono ignorati <br>
     *
     * @param testo da elaborare <br>
     *
     * @return lista di cinque stringhe, una per ognuno dei primi cinque campi
     */
    public static ArrayList<CinqueStringhe> getCsvCinque(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<CinqueStringhe> lista = null;
        boolean continua;
        String tagCampo = "\t";
        String tagRiga = "\n";
        String[] righe = null;
        String[] parti;
        CinqueStringhe cinque;
        String prima;
        String seconda;
        String terza;
        String quarta;
        String quinta;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testo));

            if (continua) {
                testo = Lib.Testo.dopoPrima(testo, tagRiga);
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<CinqueStringhe>();
                for (String riga : righe) {

                    if (Lib.Testo.isVuota(testo)) {
                        continue;
                    }// fine del blocco if

                    prima = "";
                    seconda = "";
                    terza = "";
                    quarta = "";
                    quinta = "";

                    parti = riga.split(tagCampo);

                    if (parti.length >= 1) {

                        if (parti.length > 0) {
                            prima = parti[0].trim();
                        }// fine del blocco if

                        if (parti.length > 1) {
                            seconda = parti[1].trim();
                        }// fine del blocco if

                        if (parti.length > 2) {
                            terza = parti[2].trim();
                        }// fine del blocco if

                        if (parti.length > 3) {
                            quarta = parti[3].trim();
                        }// fine del blocco if

                        if (parti.length > 4) {
                            quinta = parti[4].trim();
                        }// fine del blocco if

                        cinque = new CinqueStringhe(prima, seconda, terza, quarta, quinta);
                        lista.add(cinque);

                    }// fine del blocco if
                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di sette colonne da un testo formattato csv.
     * <p/>
     * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima riga
     * (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre informazioni
     * presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni record ha come
     * delimitatore/separatore il carattere \n <br> Ogni campo ha come delimitatore/separatore il
     * carattere \t <br> Vengono recuperati i primi sette campi di ogni record <br> Eventuali altri
     * cmpi vengono ignorati <br>
     *
     * @param testo da elaborare <br>
     *
     * @return lista di sette stringhe, una per ognuno dei primi sette campi
     */
    public static ArrayList<SetteStringhe> getCsvSette(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<SetteStringhe> lista = null;
        boolean continua;
        String tagCampo = "\t";
        String tagRiga = "\n";
        String[] righe = null;
        String[] parti;
        SetteStringhe sette;
        String prima;
        String seconda;
        String terza;
        String quarta;
        String quinta;
        String sesta;
        String settima;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testo));

            if (continua) {
                testo = Lib.Testo.dopoPrima(testo, tagRiga);
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<SetteStringhe>();
                for (String riga : righe) {

                    if (Lib.Testo.isVuota(testo)) {
                        continue;
                    }// fine del blocco if

                    prima = "";
                    seconda = "";
                    terza = "";
                    quarta = "";
                    quinta = "";
                    sesta = "";
                    settima = "";

                    parti = riga.split(tagCampo);
                    if (parti.length >= 1) {

                        if (parti.length > 0) {
                            prima = parti[0].trim();
                        }// fine del blocco if

                        if (parti.length > 1) {
                            seconda = parti[1].trim();
                        }// fine del blocco if

                        if (parti.length > 2) {
                            terza = parti[2].trim();
                        }// fine del blocco if

                        if (parti.length > 3) {
                            quarta = parti[3].trim();
                        }// fine del blocco if

                        if (parti.length > 4) {
                            quinta = parti[4].trim();
                        }// fine del blocco if

                        if (parti.length > 5) {
                            sesta = parti[5].trim();
                        }// fine del blocco if

                        if (parti.length > 6) {
                            settima = parti[6].trim();
                        }// fine del blocco if

                        sette = new SetteStringhe(prima,
                                seconda,
                                terza,
                                quarta,
                                quinta,
                                sesta,
                                settima);
                        lista.add(sette);
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di otto colonne da un testo formattato csv.
     * <p/>
     * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima riga
     * (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre informazioni
     * presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni record ha come
     * delimitatore/separatore il carattere \n <br> Ogni campo ha come delimitatore/separatore il
     * carattere \t <br> Vengono recuperati i primi otto campi di ogni record <br> Eventuali altri
     * cmpi vengono ignorati <br>
     *
     * @param testo da elaborare <br>
     *
     * @return lista di otto stringhe, una per ognuno dei primi otto campi
     */
    public static ArrayList<OttoStringhe> getCsvOtto(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<OttoStringhe> lista = null;
        boolean continua;
        String tagCampo = "\t";
        String tagRiga = "\n";
        String[] righe = null;
        String[] parti;
        OttoStringhe quattro;

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testo));

            if (continua) {
                testo = Lib.Testo.dopoPrima(testo, tagRiga);
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<OttoStringhe>();
                for (String riga : righe) {

                    if (Lib.Testo.isVuota(testo)) {
                        continue;
                    }// fine del blocco if

                    parti = riga.split(tagCampo);
                    if (parti.length >= 8) {
                        quattro = new OttoStringhe(parti[0],
                                parti[1].trim(),
                                parti[2].trim(),
                                parti[3].trim(),
                                parti[4].trim(),
                                parti[5].trim(),
                                parti[6].trim(),
                                parti[7].trim());
                        lista.add(quattro);
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Recupera una lista di dieci colonne da un testo formattato csv.
     * <p/>
     * Il testo non deve contenere niente oltre alle informazioni significativa <br> La prima riga
     * (che non viene letta) contiene i nomi dei campi/colonne <br> Eventuali altre informazioni
     * presenti nella pagina, vanno escluse prima di questa chiamata <br> Ogni record ha come
     * delimitatore/separatore il carattere \n <br> Ogni campo ha come delimitatore/separatore il
     * carattere \t <br> Vengono recuperati i primi dieci campi di ogni record <br> Eventuali altri
     * cmpi vengono ignorati <br>
     *
     * @param testo da elaborare <br>
     *
     * @return lista di dieci stringhe, una per ognuno dei primi dieci campi
     */
    public static ArrayList<DieciStringhe> getCsvDieci(String testo) {
        /* variabili e costanti locali di lavoro */
        ArrayList<DieciStringhe> lista = null;
        boolean continua;
        String tagCampo = "\t";
        String tagRiga = "\n";
        String[] righe = null;
        String[] parti;
        DieciStringhe dieci;
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

        try { // prova ad eseguire il codice

            /* controllo di congruità */
            continua = (Lib.Clas.isValidi(testo));

            if (continua) {
                testo = Lib.Testo.dopoPrima(testo, tagRiga);
                continua = (Lib.Testo.isValida(testo));
            }// fine del blocco if

            if (continua) {
                righe = testo.split(tagRiga);
                continua = (righe != null && righe.length > 0);
            }// fine del blocco if

            if (continua) {
                lista = new ArrayList<DieciStringhe>();
                for (String riga : righe) {

                    if (Lib.Testo.isVuota(testo)) {
                        continue;
                    }// fine del blocco if

                    prima = "";
                    seconda = "";
                    terza = "";
                    quarta = "";
                    quinta = "";
                    sesta = "";
                    settima = "";
                    ottava = "";
                    nona = "";
                    decima = "";

                    parti = riga.split(tagCampo);
                    if (parti.length > 0) {

                        if (parti.length > 0) {
                            prima = parti[0].trim();
                        }// fine del blocco if

                        if (parti.length > 1) {
                            seconda = parti[1].trim();
                        }// fine del blocco if

                        if (parti.length > 2) {
                            terza = parti[2].trim();
                        }// fine del blocco if

                        if (parti.length > 3) {
                            quarta = parti[3].trim();
                        }// fine del blocco if

                        if (parti.length > 4) {
                            quinta = parti[4].trim();
                        }// fine del blocco if

                        if (parti.length > 5) {
                            sesta = parti[5].trim();
                        }// fine del blocco if

                        if (parti.length > 6) {
                            settima = parti[6].trim();
                        }// fine del blocco if

                        if (parti.length > 7) {
                            ottava = parti[7].trim();
                        }// fine del blocco if

                        if (parti.length > 8) {
                            nona = parti[8].trim();
                        }// fine del blocco if

                        if (parti.length > 9) {
                            decima = parti[9].trim();
                        }// fine del blocco if

                        dieci = new DieciStringhe(prima,
                                seconda,
                                terza,
                                quarta,
                                quinta,
                                sesta,
                                settima,
                                ottava,
                                nona,
                                decima);

                        lista.add(dieci);
                    }// fine del blocco if

                } // fine del ciclo for-each
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return lista;
    }


    /**
     * Costruisce una tabella di -n- colonne (dipende dai titoli).
     * <p/>
     * Massimo numero di colonne 10 <br> Riceve una lista di 10 stringhe <br> Costruisce solo le
     * prima -n- colonne indicate <br>
     *
     * @param lista di 10 stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella costruita
     */
    private static String setCsvBase(ArrayList<DieciStringhe> lista, String titoli) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        String tagVir = ",";
        StringBuffer buffer = null;
        String[] titoliColonne = null;
        int numColonne = 0;
        String tabCampo = "\t";
        String tabRecord = "\n";

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            /* costruisci i titoli delle singole colonne */
            if (continua) {
                titoliColonne = titoli.split(tagVir);
                continua = (titoliColonne != null);
            }// fine del blocco if

            if (continua) {
                numColonne = titoliColonne.length;
                continua = (titoliColonne.length > 0);
            }// fine del blocco if

            if (continua) {
                buffer = new StringBuffer();
                buffer.append(titoli);
                buffer.append(tabRecord);
            }// fine del blocco if

            if (continua) {
                for (DieciStringhe dieci : lista) {
                    buffer.append(dieci.getPrima());

                    if (numColonne > 1) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getSeconda());
                    }// fine del blocco if

                    if (numColonne > 2) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getTerza());
                    }// fine del blocco if

                    if (numColonne > 3) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getQuarta());
                    }// fine del blocco if

                    if (numColonne > 4) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getQuinta());
                    }// fine del blocco if

                    if (numColonne > 5) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getSesta());
                    }// fine del blocco if

                    if (numColonne > 6) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getSettima());
                    }// fine del blocco if

                    if (numColonne > 7) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getOttava());
                    }// fine del blocco if

                    if (numColonne > 8) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getNona());
                    }// fine del blocco if

                    if (numColonne > 9) {
                        buffer.append(tabCampo);
                        buffer.append(dieci.getDecima());
                    }// fine del blocco if

                    buffer.append(tabRecord);
                } // fine del ciclo for-each
            }// fine del blocco if

            if (continua) {
                testo = buffer.toString();
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce un testo di due colonne formattate csv.
     * <p/>
     * Riceve una lista di dieci stringhe <br>
     *
     * @param lista di due stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella csv costruita
     */
    static String setCsvDue(ArrayList<DueStringhe> lista, String titoli) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;
        ArrayList<DieciStringhe> listaDieci;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            if (continua) {
                listaDieci = Lib.Array.setDueStringhe(lista);
                testo = LibWeb.setCsvBase(listaDieci, titoli);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Costruisce un testo di dieci colonne formattate csv.
     * <p/>
     * Riceve una lista di dieci stringhe <br>
     *
     * @param lista di dieci stringhe <br>
     * @param titoli delle colonne, separati da virgola <br>
     *
     * @return testo della tabella csv costruita
     */
    static String setCsv(ArrayList<DieciStringhe> lista, String titoli) {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        boolean continua;

        try { // prova ad eseguire il codice
            /* controllo di congruità */
            continua = (lista != null && lista.size() > 0) && Lib.Testo.isValida(titoli);

            if (continua) {
                testo = LibWeb.setCsvBase(lista, titoli);
            }// fine del blocco if

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }


    /**
     * Spedisce una lettera.
     *
     * @param destinatari della lettera
     * @param oggetto della lettera
     * @param testo della lettera
     * @param mittente della lettera
     * @param allegato alla lettera
     *
     * @return vero se spedita
     */
    static boolean postMail(String[] destinatari,
                            String oggetto,
                            String testo,
                            String mittente,
                            String allegato) throws MessagingException {
        boolean spedita = false;
        boolean debug = false;
        Properties props;
        Session session;
        Message msg;
        Message msgCheck;
        InternetAddress addressFrom;
        InternetAddress[] addressTo;
        InternetAddress[] addressToCheck;
        Multipart multipart;
        BodyPart messageBodyPart;
        DataSource source;
        int num;
        int maxInvio;
        boolean ulteriore;
        int extra;
        String[] dest = null;
        Message.RecipientType recipiente = null;
        Object ogg;
        String testoCheck;
        StringBuffer buffer;
        int cont;

        try { // prova ad eseguire il codice

            /* regola l'indirizzo del server host SMPT */
            props = new Properties();

            // todo disabilitato Alex 09-05 (riferimento fuori da Base)
            // todo messo dentro Base gac 25-8
            props.put("mail.smtp.host", Pref.Mail.serverPosta.str());

            /* crea la sessione standard */
            session = Session.getDefaultInstance(props, null);
            session.setDebug(debug);

            /* crea il messaggio */
            msg = new MimeMessage(session);
            msgCheck = new MimeMessage(session);

            /* regola l'oggetto della lettera */
            msg.setSubject(oggetto);
            msgCheck.setSubject(oggetto);

            /* corpo della lettera con o senza allegati */
            if (Lib.Testo.isValida(allegato)) {
                /* crea il formato multiplo */
                multipart = new MimeMultipart();

                /* crea la prima parte */
                messageBodyPart = new MimeBodyPart();

                /* contenuto testuale */
                // todo disabilitato Alex 09-05 (riferimento fuori da Base)
                // todo messo dentro Base gac 25-8
                messageBodyPart.setContent(testo, Pref.Mail.testo.comboStr());

                /* aggiunge la prima parte*/
                multipart.addBodyPart(messageBodyPart);

                /* la seconda parte è un allegato */
                messageBodyPart = new MimeBodyPart();
                source = new FileDataSource(allegato);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName(allegato);

                /* aggiunge la seconda parte */
                multipart.addBodyPart(messageBodyPart);

                /* mette insieme le due parti nella lettera */
                msg.setContent(multipart);
            } else {
                // todo disabilitato Alex 09-05 (riferimento fuori da Base)
                // todo messo dentro Base gac 25-8
                msg.setContent(testo, Pref.Mail.testo.comboStr());
            }// fine del blocco if-else

            /* regola il mittente */
            addressFrom = new InternetAddress(mittente);
            msg.setFrom(addressFrom);

            /* regola i destinatari */
            num = destinatari.length;
            // todo disabilitato Alex 09-05 (riferimento fuori da Base)
            // todo messo dentro Base gac 25-8
            maxInvio = Pref.Mail.multiple.intero();

            /* per invii numerosi divide in pacchetti */
            ulteriore = (num > maxInvio);
            if (ulteriore) {
                extra = num - maxInvio;
                dest = new String[extra];
            }// fine del blocco if

            if (ulteriore) {
                addressTo = new InternetAddress[maxInvio];
            } else {
                addressTo = new InternetAddress[num];
            }// fine del blocco if-else

            for (int k = 0; k < num; k++) {
                if (k < maxInvio) {
                    addressTo[k] = new InternetAddress(destinatari[k]);
                } else {
                    dest[k - maxInvio] = destinatari[k];
                }// fine del blocco if-else
            } // fine del ciclo for

            // todo disabilitato Alex 09-05 (riferimento fuori da Base)
            // todo messo dentro Base gac 25-8
            ogg = Pref.Mail.copie.comboOgg();

            if (ogg instanceof Message.RecipientType) {
                recipiente = (Message.RecipientType)ogg;
                msg.setRecipients(recipiente, addressTo);
            }// fine del blocco if

            /* spedisce */
            Transport.send(msg);

            /* spedisce il controllo */
            // todo disabilitato Alex 09-05 (riferimento fuori da Base)
            // todo messo dentro Base gac 25-8
            boolean flag = Pref.Mail.controllo.is();

            if (flag) {
                buffer = new StringBuffer();
                buffer.append("La lettera e' stata spedita a:<br>");
                cont = 1;
                for (InternetAddress ind : addressTo) {
                    buffer.append(cont++);
                    buffer.append(" - ");
                    buffer.append(ind.getAddress());
                    buffer.append("<br>");
                } // fine del ciclo for-each
                testoCheck = buffer.toString();

                // todo disabilitato Alex 09-05 (riferimento fuori da Base)
                // todo messo dentro Base gac 25-8
                msgCheck.setContent(testoCheck, Pref.Mail.testo.comboStr());

                addressToCheck = new InternetAddress[1];
                addressToCheck[0] = new InternetAddress(mittente);
                msgCheck.setFrom(addressFrom);
                msgCheck.setRecipients(recipiente, addressToCheck);
                Transport.send(msgCheck);
            }// fine del blocco if

            if (ulteriore) {
                LibWeb.postMail(dest, oggetto, testo, mittente, allegato);
            }// fine del blocco if

            /* se ci sono errori salta nel catch e non passa di qui */
            spedita = true;
        } catch (Exception unErrore) { // intercetta l'errore
//            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spedita;
    }


    /**
     * Spedisce una lettera.
     *
     * @param destinatario della lettera
     * @param oggetto della lettera
     * @param testo della lettera
     * @param mittente della lettera
     * @param allegato alla lettera
     *
     * @return vero se spedita
     */
    static boolean postMail(String destinatario,
                            String oggetto,
                            String testo,
                            String mittente,
                            String allegato) {
        boolean spedita = false;
        String[] destinatari;

        try { // prova ad eseguire il codice
            destinatari = new String[1];
            destinatari[0] = destinatario;

            spedita = LibWeb.postMail(destinatari, oggetto, testo, mittente, allegato);
        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return spedita;
    }


    /**
     * Spedisce una lettera.
     *
     * @param destinatari della lettera
     * @param oggetto della lettera
     * @param testo della lettera
     * @param mittente della lettera
     *
     * @return vero se spedita
     */
    static boolean postMail(String[] destinatari,
                            String oggetto,
                            String testo,
                            String mittente) throws MessagingException {
        /* invoca il metodo delegato */
        return LibWeb.postMail(destinatari, oggetto, testo, mittente, "");
    }


    /**
     * Spedisce una lettera.
     *
     * @param destinatario della lettera
     * @param oggetto della lettera
     * @param testo della lettera
     * @param mittente della lettera
     *
     * @return vero se spedita
     */
    static boolean postMail(String destinatario,
                            String oggetto,
                            String testo,
                            String mittente) throws MessagingException {
        /* invoca il metodo delegato */
        return LibWeb.postMail(destinatario, oggetto, testo, mittente, "");
    }


    /**
     * Spedisce una lettera.
     *
     * @param oggetto della lettera
     * @param testo della lettera
     *
     * @return vero se spedita
     */
    static boolean postMail(String oggetto, String testo) {
        /* variabili e costanti locali di lavoro */
        String destinatario = "Gac@algos.it";
        String mittente = "Guido.Ceresa@libero.it";

        /* invoca il metodo delegato */
        return LibWeb.postMail(destinatario, oggetto, testo, mittente, "");
    }


    /**
     * Recupera una pagina da un sito web.
     * <p/>
     *
     * @param pagina titolo della pagina
     *
     * @return testo html della pagina richiesta
     */
    static String getPagina(String pagina) throws Exception {
        /* variabili e costanti locali di lavoro */
        String testo = "";
        URL url;
        URLConnection connessione;
        InputStream input;
        InputStreamReader inputReader;
        BufferedReader buffer;
        StringBuffer testoBuff;
        String stringa;

        try { // prova ad eseguire il codice

            url = new URL(pagina);

            connessione = url.openConnection();
            connessione.setAllowUserInteraction(true);
            connessione.setDoInput(true);
            connessione.setDoOutput(true);
            connessione.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; U; PPC Mac OS X; it-it) AppleWebKit/419 (KHTML, like Gecko) Safari/419.3");
            connessione.connect();

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

            testo = testoBuff.toString();

        } catch (Exception unErrore) { // intercetta l'errore
            Errore.crea(unErrore);
        }// fine del blocco try-catch

        /* valore di ritorno */
        return testo;
    }

}// fine della classe
