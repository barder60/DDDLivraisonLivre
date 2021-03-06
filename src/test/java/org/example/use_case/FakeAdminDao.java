package org.example.use_case;

import org.example.model.Admin;
import org.example.model.AdminDao;
import org.example.model.User;
import org.example.model.UserEmailId;

import java.util.List;
import java.util.stream.Collectors;

public class FakeAdminDao implements AdminDao {
    private List<Admin> admins = List.of(
            new Admin(1L, new User("first", "last", new UserEmailId("admin@email.com"), "password"))
    );

    @Override
    public List<Admin> findAllByIds(List<Long> ids) {
        return admins.stream()
                .filter(admin -> ids.contains(admin.getId()))
                .collect(Collectors.toList());
    }
}
