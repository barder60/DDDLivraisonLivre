package org.example.use_case;

import org.example.model.Admin;
import org.example.model.AdminRepository;
import org.example.model.User;
import org.example.model.UserId;

import java.util.List;
import java.util.stream.Collectors;

public class FakeAdminRepository implements AdminRepository {
    private List<Admin> admins = List.of(
            new Admin(1L, new User("first", "last", new UserId("admin@email.com"), "password"))
    );

    @Override
    public List<Admin> findAllByIds(List<Long> ids) {
        return admins.stream()
                .filter(admin -> ids.contains(admin.getId()))
                .collect(Collectors.toList());
    }
}
