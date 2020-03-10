package app;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Application {


    public static void main(String[] args) {

        System.out.print("Input a command: ");
        Scanner in = new Scanner(System.in);

        Boolean run = true;


        while (run){
            String command = in.nextLine();
            if(command.toLowerCase().compareTo("exit")==0){
                in.close();
                run = false;
            }else{
                System.out.printf("Your number: %s \n", command);
                command = command.replaceAll(" ","");
                String error = checkCommand(command);
                if(error == null){
                    String result = executeOperationToDollar(command);
                    if(result.compareTo(command)==0 || result.contains("toRubles")){
                        result = executeOperationToRubles(result);
                        if(result.compareTo(command)==0){
                            if(!result.contains("+") && !result.contains("-") && !result.contains("toDollar") && !result.contains("toRubles")){
                                result = "Выберете действие";
                            }else {
                                if(result.contains("р") && result.contains("$")){
                                    result = "Невозможно сложить разные валюты";
                                }else{
                                    if(result.contains("$")){
                                        result = convertToDollarOrRubles(result.replaceAll("\\Q$\\E",""),"").toString();
                                    }
                                    if(command.contains("р")){
                                        result = convertToDollarOrRubles(result.replaceAll("р",""),"").toString();
                                    }
                                }
                            }


                        }
                    }
                    System.out.printf("Результат: %s\n", result);
                    System.out.print("Input a command: ");
                }else{
                    System.out.printf("Error: %s", error);
                }

            }

        }

    }

    public static String checkCommand(String command){


        Pattern pat=Pattern.compile("\\d+");
        Matcher matcher=pat.matcher(command);
        String answer = null;
        while (matcher.find()) {
            Integer indexDigitInString = command.indexOf(matcher.group());
            char lastSympol;
            char firstSympol;
            if(indexDigitInString+matcher.group().length()==command.length()){
                lastSympol = command.substring(command.length()-1).charAt(0);
            }else{
                lastSympol = command.substring(indexDigitInString+matcher.group().length(),indexDigitInString+matcher.group().length()+1).charAt(0);
            }

            if(indexDigitInString==0){
                firstSympol = command.substring(0,1).charAt(0);
            }else{
                firstSympol = command.substring(indexDigitInString-1,indexDigitInString).charAt(0);
            }


//            if(lastSympol.compareTo(")")!=0 && lastSympol.compareTo("$")!=0 ){
//                if(firstSympol.compareTo("(")!=0 && firstSympol.compareTo("р")!=0 ){
//                    if(firstSympol.compareTo("р")==0){
//                        return "Знак рубля должен ставится после числа";
//                    }else {
//                        if(lastSympol.compareTo("$")==0){
//                            return "Знак доллара должен ставится перед числом";
//                        }else {
//                            if(firstSympol.compareTo("$")==0 && lastSympol.compareTo("р")==0){
//                                return "Синтаксическая ошибка";
//                            }else{
//                                return null;
//                            }
//                        }
//                    }
//                }
//
//            }

            int haveACurrency = 0;
            switch (lastSympol) {
                case  ('р'):
                    if (firstSympol=='$'){
                        answer = "Синтаксическая ошибка";
                        haveACurrency = 2;
                    }else{
                       haveACurrency = 1;
                    }
                    break;
                case (')'):
                    break;
                case ('+'):
                    break;
                case ('-'):
                    break;
                case ('$'):
                    answer = "Знак доллара должен ставится перед числом";
                    haveACurrency = 2;
                    break;
                default:
                    if(!Character.isDigit(lastSympol)){
                        answer = "Синтаксическая ошибка";
                        haveACurrency = 2;
                        break;
                    }
            }
            switch (firstSympol) {
                case  ('$'):
                    if(haveACurrency==1 || haveACurrency==2){
                        answer = "Синтаксическая ошибка";
                        haveACurrency = 2;
                    }else{
                        haveACurrency = 1;
                    }
                    break;
                case (')'):
                    if(haveACurrency!=1 && haveACurrency!=2){
                        answer = "Синтаксическая ошибка";
                        haveACurrency = 2;
                    }
                    break;
                case ('+'):
                    if(haveACurrency!=1 && haveACurrency!=2){
                        answer = "Синтаксическая ошибка";
                        haveACurrency = 2;
                    }
                    break;
                case ('-'):
                    if(haveACurrency!=1 && haveACurrency!=2){
                        answer = "Синтаксическая ошибка";
                        haveACurrency = 2;
                    }
                    break;
                case ('р'):
                    answer = "Знак рубля должен ставится после числа";
                    haveACurrency = 2;
                    break;
                default:
                    if(!Character.isDigit(firstSympol)){
                        answer = "Синтаксическая ошибка";
                        haveACurrency = 2;
                        break;
                    }

            }
//            if(lastSympol.compareTo("р")!=0 && lastSympol.compareTo(")")!=0 && lastSympol.compareTo("+")!=0 && lastSympol.compareTo("-")!=0){
//
//            }




        }

        return answer;
    }

    public static String executeOperationToDollar(String command){



        String result = command;
        while (result.contains("toDollar")){
            String inner = result;
            while(inner.contains("toDollar")){
                inner = inner.substring(inner.indexOf("toDollar(")+9,inner.lastIndexOf(")"));
            }
            if (inner.contains("toRubles")){
                String rubles = executeOperationToRubles(inner);

                Double dollars = convertToDollarOrRubles(rubles.replaceAll("р",""),"dollar");
                result = result.replace("toDollar("+inner+")",dollars.toString());

            }else{
                if(inner.contains("$")){
                    return "toDollar не может принимать доллары, введите значение в рублях";
                }else {
                    if(inner.contains("р")){
                        Double dollars = convertToDollarOrRubles(inner.replaceAll("р","").replaceAll("\\(","").replaceAll("\\)",""),"dollar");
                        result = result.replace("toDollar("+inner.replaceAll("\\(","").replaceAll("\\)","")+")",dollars.toString());
                    }else{
                        return "toDollar введено не верное значение";
                    }
                }
            }


        }

        return result;
    }

    public static Double convertToDollarOrRubles(String command, String value){
        command = command.replaceAll("\\(","").replaceAll("\\)","");
        String[] res = command.split("[\\+\\-\\/]");
        String [] matrixOfActions = command.split("\\d+\\.?\\d*");
        Double result = 0.0;
        if(matrixOfActions.length>0){
            for(int i=1;i<matrixOfActions.length;i++){

                if(i==1){
//                    res[0] = res[0].replaceAll("\\(","").replaceAll("\\)","");
                    result = Double.valueOf(res[0]);
                }
//                res[i] = res[i].replaceAll("\\(","").replaceAll("\\)","");
                Double secondRes = Double.valueOf(res[i]);
                if(matrixOfActions[i].compareTo("+")==0){
                    result = result+secondRes;
                }else{
                    result = result-secondRes;
                }
            }
            if(value.toLowerCase().compareTo("dollar")==0){
                result = result/64;
            }else{
                if(value.toLowerCase().compareTo("rubles")==0) {
                    result = result * 64;
                }
            }
        }else{
//            res[0] = res[0].replaceAll("\\(","").replaceAll("\\)","");
            if(value.toLowerCase().compareTo("dollar")==0){
                result = Double.valueOf(res[0])/64;
            }else{
                if(value.toLowerCase().compareTo("rubles")==0) {
                    result = Double.valueOf(res[0])*64;
                }

            }

        }

        return result;
    }

    public static String executeOperationToRubles(String command){

        String result = command;
        while (result.contains("toRubles")){
            String inner = result;
            while(inner.contains("toRubles")){
                inner = inner.substring(inner.indexOf("toRubles(")+9,inner.lastIndexOf(")"));
            }
            if (inner.contains("toDollar")){
                String rubles = executeOperationToDollar(inner);

                Double dollars = convertToDollarOrRubles(rubles.replaceAll("\\Q$\\E",""),"rubles");
                result = result.replace("toRubles("+inner+")",dollars.toString());

            }else{
                if(inner.contains("р")){
                    return "toRubles не может принимать рубли, введите значение в долларах";
                }else {
                    if(inner.contains("$")){
                        Double dollars = convertToDollarOrRubles(inner.replaceAll("\\Q$\\E",""),"rubles");
                        result = result.replace("toRubles("+inner+")",dollars.toString());
                    }else{
                        return "toRubles введено не верное значение";
                    }
                }
            }


        }

        return result;
    }




}
