package com.hatip.inhousenavigation.model.mapper;

import com.hatip.inhousenavigation.model.entity.ReportEntryEntity;
import com.hatip.inhousenavigation.model.pojo.ReportEntry;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ReportEntryMapper {

    private static final ModelMapper modelMapper = new ModelMapper();

    static {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public static ReportEntryEntity convertToEntity(ReportEntry reportEntry) {
        return modelMapper.map(reportEntry, ReportEntryEntity.class);
    }

    public static ReportEntry convertToDto(ReportEntryEntity reportEntry) {
        return modelMapper.map(reportEntry, ReportEntry.class);
    }
}

