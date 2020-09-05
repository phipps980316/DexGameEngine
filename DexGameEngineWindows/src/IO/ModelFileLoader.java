package IO;

import Models.ModelData;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class ModelFileLoader {
    public static ModelData loadModel(String filename){
        try{
            FileInputStream fileInputStream = new FileInputStream("res/"+filename+".modeldata");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            ModelData modelData = (ModelData) objectInputStream.readObject();
            objectInputStream.close();
            return modelData;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
