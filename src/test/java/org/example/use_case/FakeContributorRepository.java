package org.example.use_case;

import org.example.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class FakeContributorRepository implements ContributorRepository {
    private List<Contributor> contributors = List.of(
            new Contributor(
                    1L,
                    new User("firstcontri",
                        "lastcontri",
                        new UserId("contri@butor.com"),
                        "contripassword"),

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
