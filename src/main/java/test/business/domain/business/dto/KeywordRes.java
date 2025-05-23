package test.business.domain.business.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KeywordRes {
    private List<String> keyword;
}
