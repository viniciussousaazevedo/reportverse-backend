package com.es.reportverse.service;

import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.Midia;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import com.es.reportverse.DTO.MidiaDTO;

import com.es.reportverse.repository.MidiaRepository;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class MidiaServiceImpl implements MidiaService {

    private final static String MIDIA_ERROR = "Ocorreu algum erro inesperado no upload da mídia.";

    private MidiaRepository midiaRepository;

    private ModelMapper modelMapper;

    public List<Midia> registerMidias(List<String> midiasPathList, Long publicationId) {
        List<Midia> midias = new ArrayList<>();

        try {

            for (String midiaPath : midiasPathList) {

                String codeMidia = this.encodeMidia(midiaPath);

                Midia midia = new Midia(codeMidia, publicationId);
                this.saveMidia(midia);
                midias.add(midia);
            }

        } catch (Exception ex) {
            throw new ApiRequestException(MIDIA_ERROR);
        }
        return midias;
    }

    public void saveMidia(Midia midia) {
        midiaRepository.save(midia);
    }

    public List<Midia> getMidiasByPublicationId(Long publicationId) {
        return midiaRepository.findByPublicationId(publicationId);
    }

    public String encodeMidia(String midiaPath) throws Exception {

        FileInputStream midiaStream = new FileInputStream(midiaPath);
        byte[] data = midiaStream.readAllBytes();
        String midiaString = Base64.getEncoder().encodeToString(data);
        midiaStream.close();

        return midiaString;
    }

}
