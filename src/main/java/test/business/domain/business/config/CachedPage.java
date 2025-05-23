package test.business.domain.business.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CachedPage<T> implements Serializable {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
}
