package com.lms.learnkonnet.utils;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ModelMapperUtil {
    public static ModelMapper modelMapper;

    @Autowired
    public ModelMapperUtil(ModelMapper modelMapper) {
        ModelMapperUtil.modelMapper = modelMapper;
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public static <T, S> S mapOne(T data, Class<S> type) {
        return modelMapper.map(data, type);
    }

    public static <D, T> List<D> mapList(List<T> typeList, Class<D> outClass) {
        return typeList.stream().map(entity -> mapOne(entity, outClass)).collect(Collectors.toList());
    }
}
