/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.CREATE_NEW;
import static java.nio.file.StandardOpenOption.READ;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class IOTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException, Exception, Throwable {
        IOPath();
        IOBytesToArr();
        IOByteToVar();
        IOToString();
        IOLinesToStringList();
        IOWordToVar();
        IOWordsToMap();

        IOBufferedWriter();
        IOPrintWriter();
        IOOutputStreamWriter();
        IOStringToFile();
        IOManyStringsToFile();
        IODataInputStream();
        IORandomAccessFile();
        IOBigFileFileChannel();
        BlokadaStop();
        BlokadaTry();

        IOPathToFile();
        IOCreate();
        IOCopyMove();
        IOParseDir();

        IOURL();
    }

    //I/O - kopiowanie OS do IS
    public static void copy(InputStream in, OutputStream out) throws IOException{
        final int BLOCKSIZE = 1024;
        byte[] bytes = new byte[BLOCKSIZE];
        int len;
        while ((len = in.read(bytes)) != -1)//odczytuje kolejne porcje wielkoĹ›ci BLOCKSIZE
            out.write(bytes, 0, len); //zapisueje do stream caĹ‚Ä… tablicÄ™ (od 0 do len)
    }

    //I/O - odczytanie wszystkich bajtĂłw z pliku
    public static byte[] readAllBytes(InputStream in) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        copy(in, out);
        out.close();
        return out.toByteArray();
    }

    //Budowa obiektu path
    public static void IOPath(){
        //ĹšcieĹĽka nie jest walidowana
        Path p = Paths.get("...");

        //Budowanie Ĺ›cieĹĽki
        Path absolute = Paths.get("/", "home", "cay1");
        Path relative = Paths.get("myapp", "conf", "user.properties");

        //Dokleja Ĺ›cieĹĽkÄ™, czyli bÄ™dzie /home/cay/myapp/work
        Path workPath1 = absolute.resolve("myapp/work");

        //Dokleja sibling /home/myapp/work
        //UWAGA! Dal Ĺ›cieĹĽek Linux jest problem z rozpoczynajÄ…cym "\" i poniĹĽsze da bĹ‚Ä…d
        //Paths.get("/", "sampleFolder").resolveSibling("myapp/work")
        Path workPath2 = absolute.resolveSibling("myapp/work");

        //Relatywizujemy 2 wzglÄ™cem 1 - ../freud/myapp - pastÄ™pije czeĹ›Ä‡ wspĂłlnÄ… (obu Ĺ›cieĹĽek) wielokotopkami
        //MĂłwiÄ…c inaczej opisuje jak ze Ĺ›ciÄ™zki A doĹ›c do Ĺ›cieĹĽki B
        Path workPath3 = Paths.get("/home/cay1/cay2/cay3").relativize(Paths.get("/home/cay1/fred/myapp"));

        int brk = 0;
    }

    //Przelanie bajtow z InputStram do tablicy
    public static void IOBytesToArr() throws IOException{
        //wczytywanie bez dodatkowych opcji
        byte[] byteArr1 = readAllBytes(Files.newInputStream(Paths.get("C:/2.txt")));

        //dodanie opcji (czy zakĹ‚adaÄ‡ nowe pliki etc)
        OpenOption[] oos = new OpenOption[] { /*tu moĹĽna wstawiÄ‡ opcje*/ };
        byte[] byteArr2 = readAllBytes(Files.newInputStream(Paths.get("C:/2.txt"), oos));

        //moĹĽna skrĂłciÄ‡ implementacjÄ™
        byte[] byteArr3 = readAllBytes(Files.newInputStream(Paths.get("C:/2.txt"), new OpenOption[] {}));
    }

    //Odczytanie pojedynczego znaku z IS (w tym przypadku z pliku)
    public static void IOByteToVar() throws IOException{
        int inp;
        InputStream inStream = Files.newInputStream(Paths.get("C:/2.txt"), new OpenOption[] { CREATE_NEW });
        //Stworzenie readera dla inputStream
        Reader in = new InputStreamReader(inStream);
        inp = in.read(); //Read robi jednoczeĹ›nie Next
        char[] chars = new char[5];
        in.read(chars, 2, 3); //tablica docelowa, index od ktĂłrego zaczÄ…Ä‡ wstawianie, ile wstawiÄ‡ - [,,1,2,]
    }

    //Wczytanie caĹ‚ego pliku do Stringa -
    //UWAGA!! Plik nie moĹĽe byÄ‡ duĹĽy
    public static void IOToString() throws IOException{
        String content = new String(Files.readAllBytes(Paths.get("C:/2.txt")));
    }

    //Wczytanie wszystkich linii pliku do listy StringĂłw
    public static void IOLinesToStringList() throws IOException{
        //Za pomocÄ… Files
        List<String> lines1 = Files.readAllLines(Paths.get("C:/2.txt"));

        //Za pomocÄ… streama 1
        List lineList1;
        try (Stream<String> lines2 = Files.lines(Paths.get("C:/2.txt"))) {
            lineList1 = Arrays.asList(lines2.toArray());
        }

        //Za pomocÄ… streama 2
        List lineList2;
        try (Stream<String> lines2 = Files.lines(Paths.get("C:/2.txt"))) {
            lineList2 = lines2.collect(Collectors.toList());
        }

        //Za pomocÄ… BufferedReader
        //try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()) )) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader( Files.newInputStream(Paths.get("C:/2.txt")) ) )) {
            Stream<String> lines = reader.lines();
            //lines.collect(...)

            //lub

            reader.readLine();
        }
    }

    //Wczytanie pojedynczych sĹ‚Ăłw z pliku
    public static void IOWordToVar() throws IOException{
        int intWord;
        Scanner scn = new Scanner(Paths.get("C:/2.txt"));
        scn.next();//pomija sĹ‚owo
        intWord = scn.nextInt();//to da Exception jeĹ›li kolejne "sĹ‚owo" nie bÄ™dzie liczbÄ…
    }

    //Wczytanie pliku jako mapy z listami sĹ‚Ăłw
    public static void IOWordsToMap() throws IOException{
        try (BufferedReader reader = new BufferedReader(new InputStreamReader( Files.newInputStream(Paths.get("C:/2.txt")) ) )) {
            Stream<String> lines = reader.lines();
            AtomicInteger lpline = new AtomicInteger();
            Map<Integer, List<String>> map = lines.
                    peek(System.out::println).
                    //zbudowanie mapy. Key to rosnÄ…cy licznik a value to lista stringĂłw stanowiÄ…cych sĹ‚owa w danej liĹ›cie
                    collect(Collectors.toMap( s -> lpline.getAndIncrement(), s -> Arrays.asList(s.split("[^\\S]+")) )
                    );
        }
    }

    //Writer do pliku - proste metody
    public static void IOBufferedWriter() throws IOException{
        Writer out1 = Files.newBufferedWriter(Paths.get("D:/3.txt"), StandardCharsets.UTF_8, new OpenOption[] { CREATE });
        out1.write("1 111");
        out1.write("1 222");
        out1.flush();
    }

    //Writer do pliku - dodatkowo ma print i println i pozwala formatowaÄ‡ tekst
    public static void IOPrintWriter() throws IOException{
        PrintWriter out2 = new PrintWriter(Files.newBufferedWriter(Paths.get("D:/4.txt"), StandardCharsets.UTF_8, new OpenOption[] { CREATE }));
        out2.println("2 111");
        out2.println("2 222");
        out2.flush();
    }

    //Writer do streama - w tym przypadku stream do pliku
    public static void IOOutputStreamWriter() throws IOException{
        //Wybieramy zapis do pliku
        OutputStream outStream = Files.newOutputStream(Paths.get("D:/5.txt"), new OpenOption[] { CREATE });
        //Tu mamy papis na konsolÄ™
        //OutputStream outStream2 = System.out;
        Writer out2 = new OutputStreamWriter(outStream, StandardCharsets.UTF_8);
        out2.write("3 111\n");
        out2.write("3 222");
        out2.flush();
    }

    //Zapis gotowego stringa (caĹ‚y tekst) do pliku
    public static void IOStringToFile() throws IOException{
        String content2 = ".......\n%%%%%";
        Files.write(Paths.get("D:/6.txt"), content2.getBytes(StandardCharsets.UTF_8));
    }

    //Zapis kolecji linii do pliku - to moĹĽe byÄ‡ cokolwiek co jest Collection<String> lub nawet Iterable<? extends CharSequence>.
    public static void IOManyStringsToFile() throws IOException{
        List<String> lines = Arrays.asList("1\n2","3"); //zapisza siÄ™ 3 linie
        Files.write(Paths.get("D:/7.txt"), lines, StandardCharsets.UTF_8);
    }

    //IO danych inaczej niĹĽ jako String - jest szybszy
    public static void IODataInputStream() throws IOException{
        DataInput in2 = new DataInputStream(Files.newInputStream(Paths.get("D:/7.txt")));
        DataOutput out2 = new DataOutputStream(Files.newOutputStream(Paths.get("D:/7.txt")));
    }

    //DostÄ™p do dowolnej czeĹ›ci pliku
    public static void IORandomAccessFile() throws IOException{
        int i = 1;
        RandomAccessFile file = new RandomAccessFile("D:/7.txt", "rw");
        //file.readLine();//wczytanie caĹ‚ej linii (i robi next)
        //file.read();//oddaje kod znaku (i robi next), np 1 da 49
        //file.readInt();//oddaje dziwne rzeczy - nie rozumiem tego
        //file.seek(i);//przesĂłwa karetÄ™ na wskazanÄ… pozycjÄ™ po poczÄ…tku pliku
        file.writeChar('a');

        //PoniĹĽszy przyklad nie dziaĹ‚. moĹĽe kodowanie jest nierodpowiednie, albo cioĹ› innego.
        //mechanizm jest maĹ‚o potrzebny, wiÄ™c nie bÄ™dÄ™ siÄ™ w to zagĹ‚Ä™biaĹ‚
        //PrzykĹ‚ad ++ dla integera w pliku
        //int value = file.readInt();
        //file.seek(file.getFilePointer() - 4);
        //file.writeInt(value + 1);
    }

    //DostÄ™p do duĹĽych plikĂłw - moĹĽemy zmapowaÄ‡ fragment pliku i tylko w tym fragmencie wprowadaÄ‡ zmiany
    public static void IOBigFileFileChannel() throws IOException{
        //Totalnie nie wiem jednak jak tego uĹĽywaÄ‡ ???
        FileChannel channel = FileChannel.open(Paths.get("D:/3.txt"), READ, StandardOpenOption.WRITE);
        ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_WRITE, /*start*/0, /*dĹ‚ugoĹ›Ä‡ bloku*/channel.size());//to dziaĹ‚a poprawnie, czyli 2 opiszcza znak o indeksach 0 i 1 i zaczyna zapis na znaku o indeksie 2
        int offset = 0;
        byte value = buffer.get(offset); //oddaje bajtowÄ… rezprezentracjÄ™ znaku, czyli dla 0 odda "49"
        //nie wiem co dokĹ‚adnie oddaje get/putInt etc
        //int value = buffer.getInt(offset);
        //buffer.putInt(offset, 3);
    }

    //Blokada - czeka dopoki nie moĹĽna zaĹ‚oĹĽyÄ‡ blokady
    public static void BlokadaStop() throws IOException{
        //JeĹ›li program (np Office) zablokowaĹ‚ plik, to ta linia rzuci java.nio.file.FileSystemException
        //Jest moĹĽliwoĹ›Ä‡, ĹĽe pewne aplikacje (np natywne) majÄ… moĹĽliwoĹ›Ä‡ na ostrzejsze.
        //W takim przypadku nie moĹĽna liczyc na to ĹĽe dojdziemy do locka, ale trzeba badaÄ‡ rzycane wyjÄ…tki

        FileChannel channel2 = FileChannel.open(Paths.get("D:/3.txt"), READ, StandardOpenOption.WRITE);
        FileLock lock1 = channel2.lock(); //Tutaj wÄ…tek stanie i bÄ™dzie czekaÄ‡ dopĂłki nie zaĹ‚oĹĽy blokady
        int i = 0;
    }

    //Blokada - czeka dopoki nie moĹĽna zaĹ‚oĹĽyÄ‡ blokady
    public static void BlokadaTry() throws IOException{
        FileChannel channel = FileChannel.open(Paths.get("D:/3.txt"), READ, StandardOpenOption.WRITE);
        FileLock lock2;
        try {
            lock2 = channel.tryLock();
        }
        catch (OverlappingFileLockException e){
            //Ten wyjÄ…tek wystapi jeĹ›li ten program juĹĽ zablokowaĹ‚ ten plik
        }

        int i = 0;
        try (FileLock lock3 = channel.lock()) {
            i = 0;
        }
    }

    //Z path dostajemy obiekt File
    public static void IOPathToFile(){
        File file = (Paths.get("D:","3.txt")).toFile();
        if (file.exists()) {/*....*/};
    }

    //Tworzenie plikĂłw i katalogĂłw
    public static void IOCreate() throws IOException{
        /*W java Directory i File majÄ… jednÄ… klasÄ™. OdrĂłĹĽniajÄ… siÄ™ po isDirectory()*/ if ( ((Paths.get("D:","test2")).toFile()).isDirectory() ) ((Paths.get("D:","test2")).toFile()).delete();
        Path dirPath = Files.createDirectories(Paths.get("D:","test1") /*, Attributes*/);
        File dir = (Paths.get("D:","test1")).toFile();

        /**/ if ( ((Paths.get("D:","test2")).toFile()).isFile() ) ((Paths.get("D:","test2")).toFile()).delete();
        Files.createFile(Paths.get("D:","test2"));

        //Tworzenie tymczasowego pliku i katalogi
        Path tempFile = Files.createTempFile(Paths.get("D:/"), "MY_", ".log"); // D:/MY_462324563250.log
        Path tempDir = Files.createTempDirectory(Paths.get("D:"), "MY_");
        Path tempDir2 = Files.createTempDirectory("MY_"); //katalog tymczasowy w domyĹ›lnej lokalizacji (...)/Local/Temp/MY_(...)

        int brk = 0;
    }

    //Kopiowanie i przenoszenie
    public static void IOCopyMove() throws IOException{
        //Kopiowanie
        Files.copy(Paths.get("D:/3.txt"), Paths.get("D:/33.txt"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        /*Przeniesienie*/ Files.copy(Paths.get("D:/3.txt"), Paths.get("D:/34.txt"), StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
        Files.move(Paths.get("D:/34.txt"), Paths.get("D:/35.txt"), StandardCopyOption.REPLACE_EXISTING);
        //Prosty delete - rzuca wyjÄ…tkiem
        Files.delete(Paths.get("D:/33.txt"));
        //Delete - nie rzyca wyjÄ…tkiem, ale oddaje info o wykonaniu dziaĹ‚ania
        boolean deleted = Files.deleteIfExists(Paths.get("D:/35.txt"));

        int brk = 0;
    }

    //Parsowanie struktury katalogĂłw (pliki i katalogi)
    public static void IOParseDir() throws IOException{
        //PĹ‚aska lista pliĂłw i katalogĂłw
        try (Stream<Path> entries1 = Files.list(Paths.get("C:\\"))) {
            entries1.forEach(System.out::println);
        }
        //WgĹ‚Ä…b lista pliĂłw i katalogĂłw
        try (Stream<Path> entries2 = Files.walk(Paths.get("C:", "Situ"), 2/*max gĹ‚Ä™bokoĹ›Ä‡*/)) {
            entries2.forEach(System.out::println);
        }
    }

    //URL wymaga innego ĹşrĂłdĹ‚a informacji niĹĽ ksiÄ…ĹĽka - tu jest bieda
    //Tu tylko podstawy pobierania informacji
    public static void IOURL() throws IOException{
        URL google = new URL("http://www.google.com");
        URLConnection connection = google.openConnection();
            //connection.setRequestProperty("Accept-Charset", "UTF-8, ISO-8859-1");// moĹĽna pominÄ…Ä‡
            //connection.setReadTimeout(1000);
            //connection.setConnectTimeout(1000);
        BufferedReader reader = new BufferedReader(new InputStreamReader(google.openStream()));
        reader.lines().forEach(System.out::println);
    }
}
