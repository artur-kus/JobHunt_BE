package it.jobhunt.JobHunt.util;

import java.util.Collection;
import java.util.Objects;

public class GeneralUtil {

    public static boolean isNullOrEmpty(Collection collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isNull(Object classes){
        return Objects.isNull(classes);
    }
}
