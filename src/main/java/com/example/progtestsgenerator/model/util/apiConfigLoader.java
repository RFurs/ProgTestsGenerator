package com.example.progtestsgenerator.model.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class apiConfigLoader
{

    public static String loadFirstLine(String path) throws IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            return br.readLine();
        }
    }
}