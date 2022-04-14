package org.example.use_case;

import org.example.model.Contributor;
import org.example.model.ContributorRepository;
import org.example.model.Role;

import java.util.List;
import java.util.stream.Collectors;

public class FakeContributorRepository implements ContributorRepository {
    private List<Contributor> contributors = List.of(
            new Contributor(
                    1L,
                    "firstcontri",
                    "lastcontri",
                    "contri@butor.com",
                    "contripassword",
                    "organization",
                    Role.DRIVER
            )
    );
    @Override
    public List<Contributor> findAllByIds(List<Long> ids) {
        return contributors.stream()
                .filter(contributor -> ids.contains(contributor.getId()))
                .collect(Collectors.toList());
    }
}
