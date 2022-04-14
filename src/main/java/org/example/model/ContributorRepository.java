package org.example.model;

import java.util.List;

public interface ContributorRepository {
    List<Contributor> findAllByIds(List<Long> ids);
}
