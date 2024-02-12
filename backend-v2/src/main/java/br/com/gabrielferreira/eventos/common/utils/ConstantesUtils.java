package br.com.gabrielferreira.eventos.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class ConstantesUtils {

    private ConstantesUtils(){}

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String MSG_FORBIDDEN = "Você não tem a permissão de realizar esta ação";

    public static boolean isPossuiCaracteresEspecias(String valor){
        boolean isPossuiCaracteresEspeciais = false;
        String padrao = "[@_!#$%^&*()<>?/|}{~:]";

        for (Character caractere : valor.toCharArray()) {
            Pattern pattern = Pattern.compile(padrao);
            if(pattern.matcher(String.valueOf(caractere)).find()){
                isPossuiCaracteresEspeciais = true;
                break;
            }
        }
        return isPossuiCaracteresEspeciais;
    }

    public static boolean isPossuiCaractereMaiusculas(String valor){
        boolean isPossuiCaractereMaiusculas = false;
        for(Character caractere : valor.toCharArray()){
            if(Character.isUpperCase(caractere)){
                isPossuiCaractereMaiusculas = true;
                break;
            }
        }
        return isPossuiCaractereMaiusculas;
    }

    public static boolean isPossuiCaractereMinusculas(String valor){
        boolean isPossuiCaractereMinusculas = false;
        for(Character caractere : valor.toCharArray()){
            if(Character.isLowerCase(caractere)){
                isPossuiCaractereMinusculas = true;
                break;
            }
        }
        return isPossuiCaractereMinusculas;
    }

    public static boolean isPossuiCaractereDigito(String valor){
        boolean isPossuiCaractereDigito = false;
        for(Character caractere : valor.toCharArray()){
            if(Character.isDigit(caractere)){
                isPossuiCaractereDigito = true;
                break;
            }
        }
        return isPossuiCaractereDigito;
    }

    public static boolean isNumerico(String valor){
        return StringUtils.isNumeric(valor);
    }
}
