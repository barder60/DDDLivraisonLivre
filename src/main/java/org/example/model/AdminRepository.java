package org.example.model;

import java.util.List;

public interface AdminRepository {
    List<Admin> findAllByIds(List<Long> ids);
}
