package it.jobhunt.JobHunt.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
public class PageHelper {
    private Integer number;
    private Integer size;
    private Sort.Direction sort;
    private String sortedFieldName;

    public static PageRequest getPage(PageHelper pageHelper) {
        return PageRequest.of(pageHelper.getNumber(), pageHelper.getSize(), pageHelper.getSort(), pageHelper.getSortedFieldName());
    }

    public String getSortedFieldName() {
        return (sortedFieldName != null) ? sortedFieldName : "id";
    }

    public int getNumber() {
        return (number != null) ? number : 1;
    }

    public int getSize() {
        return (size != null) ? size : 1;
    }

    public Sort.Direction getSort() {
        return (sort != null) ? sort : Sort.Direction.ASC;
    }
}
