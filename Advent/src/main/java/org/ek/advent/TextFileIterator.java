package org.ek.advent;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

class TextFileIterator implements Iterator<String> {
    
    private final BufferedReader reader;
    
    private String nextLine;
    
    public TextFileIterator(String file) throws IOException {
        reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public boolean hasNext() {
        try {
            reader.mark(1024);
            nextLine = reader.readLine();
            reader.reset();
        } catch (IOException e) {
            e.printStackTrace();
            nextLine = null;
        }
        return (nextLine != null);
    }

    @Override
    public String next() {
        try {
            return reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
    
}