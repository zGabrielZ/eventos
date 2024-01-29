package br.com.gabrielferreira.eventos.common.utils;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DataUtils {

    private DataUtils(){}

    public static final ZoneId FUSO_HORARIO_PADRAO_SISTEMA = ZoneId.systemDefault();

    public static final ZoneId UTC = ZoneId.of("UTC");

    public static ZonedDateTime toFusoPadraoSistema(ZonedDateTime data){
        return data != null ? data.withZoneSameInstant(FUSO_HORARIO_PADRAO_SISTEMA) : null;
    }
}
