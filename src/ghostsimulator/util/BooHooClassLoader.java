package ghostsimulator.util;

import ghostsimulator.controller.EditorManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BooHooClassLoader extends ClassLoader{

    public BooHooClassLoader(ClassLoader parent) {
        super(parent);
    }

    public Class<?> loadBooHooClass() throws ClassNotFoundException {

        try {
        	File file = new File(EditorManager.DIRECTORY+"/"+EditorManager.PROGRAM_NAME+".class");
            URL myUrl = file.toURI().toURL();
            URLConnection connection = myUrl.openConnection();
            InputStream input = connection.getInputStream();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int data = input.read();

            while(data != -1){
                buffer.write(data);
                data = input.read();
            }

            input.close();

            byte[] classData = buffer.toByteArray();

            return defineClass(EditorManager.PROGRAM_NAME,
                    classData, 0, classData.length);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}