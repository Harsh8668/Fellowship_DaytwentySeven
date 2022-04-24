package com.javaproject.daytwentyseven;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.*;

public class Java8WatchServiceExample {
    private final WatchService watcher;
    private final Map<WatchKey, Path> dirWatcher;

    public Java8WatchServiceExample(Path dir) throws IOException{
        this.watcher = FileSystems.getDefault().newWatchService();
        this.dirWatcher = new HashMap<WatchKey, Path>();
        scanAndRegisterDirectories(dir);
    }

    private void registerDirWatchers(Path dir) throws IOException{
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        dirWatcher.put(key, dir);
    }

    private void scanAndRegisterDirectories(final Path start) throws IOException {
        // register directory and sub-directories
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                registerDirWatchers(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    @SuppressWarnings({"rawtypes", "unchecked"})
    public void processEvents(){
        while(true){
            WatchKey key;
            try{
                key = watcher.take();
            }catch (InterruptedException e){
                return;
            }
            Path dir = dirWatcher.get(key);
            if (dir == null) continue;
            for (WatchEvent<?> event : key.pollEvents()){
                WatchEvent.Kind kind = event.kind();
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
                System.out.format("%s: %s\n", event.kind().name(), child);

                if (kind == ENTRY_CREATE) {
                    try {
                        if (Files.isDirectory(child)) scanAndRegisterDirectories(child);
                    } catch (IOException x) { }
                } else if (kind.equals(ENTRY_DELETE)) {
                    if (Files.isDirectory(child)) dirWatcher.remove(key);
                }
            }
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                dirWatcher.remove(key);
                if (dirWatcher.isEmpty()) break;
            }
        }
    }
}
