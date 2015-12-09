package org.ek.advent;

import java.io.IOException;
import java.util.Iterator;

class TextFile implements Iterable<String> {
    private final String file;
    
    public TextFile(String file) {
        this.file = file;
    }

    @Override
    public Iterator<String> iterator() {
        try {
            return new TextFileIterator(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}