/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ja1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class ProcessTest {

  public static void main(String[] args) throws Exception {
    ProcessStart();

    System.out.println("Koniec");
  }

  //
  public static void ProcessStart() throws IOException, InterruptedException {
    String[] winDirCommand = { "cmd", "/C", "dir" };
    String[] linuxLsCommand = { "bash", "-c", "ls" };
    String[] linuxCompileCApp = { "gcc", "myapp.c" };
    ProcessBuilder builder = new ProcessBuilder(winDirCommand);

    // ustalanie working directory procesu. Domyślnie jest to WD wirtualnej maszyny
    builder = builder.directory(Paths.get("D:/").toFile());

    // uruchamianie procesu
    // Każda a metod ProcessBuilder oddaje swoją instancję, więc możliwe jest
    // ustawanie w łańcuchu
    Process p = new ProcessBuilder(winDirCommand).directory(Paths.get("D:/").toFile()).start();
    // albo
    Process p2 = builder.start();

    // Oczekiwanie na zakończenie polecenia
    // Po zakończeniu procesu poniższe streamy są czyszczeone
    // int result = p.waitFor();

    // Pobranie końcówek IO z procesu
    OutputStream inToProc = p2.getOutputStream();
    InputStream outFromProc = p2.getInputStream();
    InputStream processErr = p2.getErrorStream();

    // parsowanie sdanych z procesu
    // 1 Scanner
    try (Scanner in = new Scanner(p.getInputStream())) {
      while (in.hasNextLine())
        System.out.println(in.nextLine());
    }
    // 2 Stream
    Stream<String> lines = (new BufferedReader(new InputStreamReader(outFromProc))).lines();
    lines.forEach(System.out::println);

    // Uwaga! Bufor może się przepełnić, więc należy co jakiś czas go
    // oodczytać/oczyścić
    // Uwaga! Jednorazowe odczytanie może nie wystarczyć i nalezy odczytywać w pętli

    int brk = 0;
  }

}
