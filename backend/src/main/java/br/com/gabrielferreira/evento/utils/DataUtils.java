package br.com.gabrielferreira.evento.utils;

import java.time.ZoneId;

public class DataUtils {

    private DataUtils(){}

    public static final ZoneId UTC = ZoneId.of("UTC");

    public static final ZoneId FUSO_HORARIO_PADRAO_SISTEMA = ZoneId.systemDefault();
}
