package app;

import java.util.Scanner;

public class Application {


    public static void main(String[] args) {

        System.out.print("Input a command: ");
        Scanner in = new Scanner(System.in);

        Boolean run = true;

        while (run){
            String word = in.nextLine();
            if(word.toLowerCase().compareTo("exit")==0){
                in.close();
                run = false;
            }else{
                System.out.printf("Your number: %s \n", word);
                System.out.printf("res %s", executeOperationToDollar(word));
            }

        }

    }

    public static String executeOperationToDollar(String command){

        command = command.replaceAll(" ","");

        String result = command;
        while (result.contains("toDollar")){
            String inner = result;
            while(inner.contains("toDollar")){
                inner = command.substring(command.indexOf("toDollar(")+9,command.indexOf(")"));
            }
            if(inner.contains("$")){
                return "toDollar не может принимать доллары, введите значение в рублях";
            }else {
                if(inner.contains("р")){
                    Double dollars = toDollar(Double.valueOf(inner.replaceAll("р","")));
                    result = result.replace("toDollar("+inner+")",dollars.toString());
                }else{
                    return "toDollar введено не верное значение";
                }
            }

        }



//
//        String[] res = command.split("[\\+\\-\\/]");
//        String [] matrixOfActions = command.split("\\d+\\.?\\d*");
        return result;
    }

    public static Double toDollar(Double command){
        return command*63;
    }

    public static Double toRubles(String command){
        return null;
    }

}
