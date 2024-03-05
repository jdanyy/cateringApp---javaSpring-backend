package edu.bbte.idde.jdim2141.spring.util;

import edu.bbte.idde.jdim2141.spring.exception.service.InvalidSortPropertyException;
import edu.bbte.idde.jdim2141.spring.model.dto.request.SortCriteria;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class SortingUtil {

    private SortingUtil() {
    }

    public static void validateSortCriteria(SortCriteria sortCriteria, Class<?> targetClass) {
        String sortBy = sortCriteria.getSortBy();

        var validFieldNames = Arrays.stream(targetClass.getDeclaredFields()).map(Field::getName)
            .collect(Collectors.toList());

        var superClassFieldNames = Arrays.stream(targetClass.getSuperclass().getDeclaredFields())
            .map(Field::getName).toList();

        validFieldNames.addAll(superClassFieldNames);

        if (!validFieldNames.contains(sortBy)) {
            throw new InvalidSortPropertyException(
                String.format("\"%s\" attribute not found in \"%s\" class fields.", sortBy,
                    targetClass.getCanonicalName()));
        }
    }

}
