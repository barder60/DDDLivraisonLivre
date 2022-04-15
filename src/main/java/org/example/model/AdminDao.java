package org.example.model;

import java.util.List;

public interface AdminDao {
    List<Admin> findAllByIds(List<Long> ids);
}
