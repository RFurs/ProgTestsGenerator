package com.example.progtestsgenerator.model.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileExporter
{
    public static void export(String content, String fileName) throws IOException
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName)))
        {
            bw.write(content);
        }
    }
}
