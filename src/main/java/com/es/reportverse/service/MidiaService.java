package com.es.reportverse.service;

import com.es.reportverse.model.Midia;
import java.util.List;

public interface MidiaService {

    List<Midia> registerMidias(List<String> midiasPathList, Long publicationId);

    void saveMidia(Midia midia);

    List<Midia> getMidiasByPublicationId(Long publicationId);

    String encodeMidia(String midiaPath) throws Exception;
}
