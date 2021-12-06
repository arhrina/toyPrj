package com.example.lottomanual.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LottoService {

    public String getSix() {
        List<Integer> choice = new ArrayList<>();
        for(int i = 0; i < 45; i++) {
            choice.add(i + 1);
        }
        Collections.shuffle(choice);
        Collections.shuffle(choice);
        Collections.shuffle(choice);

        Queue<Integer> lottoQue = new LinkedList<>();

        for(int i = 0; i < 6; i++) {
            lottoQue.add(choice.get(i));
        }

        choice.clear();
        StringBuilder retStr = new StringBuilder();
        Object[] objects = lottoQue.stream().toArray();


        for(int i = 0; i < objects.length; i++) {
            retStr.append(String.format("%03d", objects[i]) + " ");
        }
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("data", retStr.toString());

        String jsonStr = null;
        try {
            jsonStr = new ObjectMapper().writeValueAsString(tempMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }
}
