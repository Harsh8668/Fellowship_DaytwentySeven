package com.javaproect.daytwentyseven;

import com.javaproject.daytwentyseven.FileUtils;
import com.javaproject.daytwentyseven.Java8WatchServiceExample;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;

public class NIOFileAPITest {
    private static String HOME = System.getProperty("user.home");
    private static String PLAY_WITH_NIO = "TempPlayGround";

    @Test
    public void givenPathWhenCheckedThenConfirm() throws IOException {
        //Checking file Exists
        Path homePath = Paths.get(HOME);
        Assert.assertTrue(Files.exists(homePath));

        //Deleting the File
        Path playPath = Paths.get(HOME + "/" + PLAY_WITH_NIO);
        if (Files.exists(playPath)) FileUtils.deleteFiles(playPath.toFile());
        Assert.assertTrue(Files.notExists(playPath));

        //Creating Directory
        Files.createDirectories(playPath);
        Assert.assertTrue(Files.exists(playPath));

        //Create File
        IntStream.range(1,11).forEach(cntr -> {
            Path tempFiles = Paths.get(playPath + "/temp" + cntr);
            Assert.assertTrue(Files.notExists(tempFiles));
            try { Files.createFile(tempFiles); }
            catch (IOException e) { }
            Assert.assertTrue(Files.exists(tempFiles));
        });

        //List, Directories as well as Files with extensions
        Files.list(playPath).filter(Files::isRegularFile).forEach(System.out::println);
        Files.newDirectoryStream(playPath).forEach(System.out::println);
        Files.newDirectoryStream(playPath, path -> path.toFile().isFile()
                && path.toString().startsWith("Temp")).forEach(System.out::println);
    }

    @Test
    public void givenDirectoryWhenWatchedListsAllTheActivities() throws IOException{
        Path dir = Paths.get(HOME + "/"+ PLAY_WITH_NIO);
        Files.list(dir).filter(Files::isRegularFile).forEach(System.out::println);
        new Java8WatchServiceExample(dir).processEvents();
    }
}