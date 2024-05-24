package org.project.romannumeral.service;

import java.util.List;

public interface Converter {

    /**
     *
     * @param from starting number of the range to convert
     * @param to inclusive end of range to convert
     * @return List of String objects converted by the services that implement this interface
     */
    List<String> convert(Integer from, Integer to);

}
