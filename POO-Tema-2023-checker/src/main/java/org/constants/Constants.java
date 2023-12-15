package org.constants;

import java.io.File;

public class Constants {
    private static final String accountPath1= "src/test/resources/testResources/accounts.json";
    private static final String accountPath2= "POO-Tema-2023-checker/src/main/resources/input/accounts.json";
    private static final String actorsPath1= "src/test/resources/testResources/actors.json";
    private static final String actorsPath2= "POO-Tema-2023-checker/src/main/resources/input/actors.json";

    private static final String productionPath1= "src/test/resources/testResources/production.json";
    private static final String productionPath2= "POO-Tema-2023-checker/src/main/resources/input/production.json";

    private static final String requestsPath1= "src/test/resources/testResources/requests.json";
    private static final String requestsPath2= "POO-Tema-2023-checker/src/main/resources/input/requests.json";
    public static final String account = findFile(accountPath1, accountPath2);
    public static final String actors = findFile(actorsPath1, actorsPath2);
    public static final String production = findFile(productionPath1, productionPath2);
    public static final String requests = findFile(requestsPath1, requestsPath2);

    public static String findFile(String path1, String path2){
        try{
            File file = new File(path1);
            if(file.exists()){
                return path1;
            }
            else{
                return path2;
            }
        }
        catch(Exception e){
            return path2;
        }
    }
}
