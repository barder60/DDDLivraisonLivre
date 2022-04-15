package org.example.model;

import java.util.List;

public interface ContributorDao {
    List<Contributor> findAllByIds(List<Long> ids);
}
